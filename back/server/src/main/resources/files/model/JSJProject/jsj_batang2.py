"""
20211222
解决 X = df.loc[:, [x for x in df.columns.tolist() if x != 'NDX']].as_matrix() 报错

20220102
修改Attention-LSTM模型部分

20220110
ntimestep, ln, grid, color
test......

"""

## Hyper-parameters settings
import time

dataroot = './2巴塘1998-2016.csv'


batchsize = 128 #initial = 128
nhidden_encoder = 128
nhidden_decoder = 128
ntimestep = 10         #date
lr = 0.001
epochs = 1000


## Model Architecture

import matplotlib.pyplot as plt
from torch.autograd import Variable
import torch
import numpy as np
from torch import nn
from torch import optim
import torch.nn.functional as F
import pandas as pd

class Encoder(nn.Module):
    """encoder in DA_RNN."""

    def __init__(self, T,
                 input_size,
                 encoder_num_hidden,
                 parallel=False):
        """Initialize an encoder in DA_RNN."""
        super(Encoder, self).__init__()
        self.encoder_num_hidden = encoder_num_hidden
        self.input_size = input_size
        self.parallel = parallel
        self.T = T

        # Fig 1. Temporal Attention Mechanism: Encoder is LSTM
        self.encoder_lstm = nn.LSTM(
            input_size=self.input_size,
            hidden_size=self.encoder_num_hidden,
            num_layers = 1
        )

        # Construct Input Attention Mechanism via deterministic attention model
        # Eq. 8: W_e[h_{t-1}; s_{t-1}] + U_e * x^k
        self.encoder_attn = nn.Linear(
            in_features=2 * self.encoder_num_hidden + self.T - 1,
            out_features=1
        )

    def forward(self, X):
        """forward.

        Args:
            X: input data

        """
        X_tilde = Variable(X.data.new(
            X.size(0), self.T - 1, self.input_size).zero_())
        X_encoded = Variable(X.data.new(
            X.size(0), self.T - 1, self.encoder_num_hidden).zero_())

        # Eq. 8, parameters not in nn.Linear but to be learnt
        # v_e = torch.nn.Parameter(data=torch.empty(
        #     self.input_size, self.T).uniform_(0, 1), requires_grad=True)
        # U_e = torch.nn.Parameter(data=torch.empty(
        #     self.T, self.T).uniform_(0, 1), requires_grad=True)

        # h_n, s_n: initial states with dimention hidden_size
        h_n = self._init_states(X)
        s_n = self._init_states(X)

        for t in range(self.T - 1):
            # batch_size * input_size * (2 * hidden_size + T - 1) cat拼接
            x = torch.cat((h_n.repeat(self.input_size, 1, 1).permute(1, 0, 2),
                           s_n.repeat(self.input_size, 1, 1).permute(1, 0, 2),
                           X.permute(0, 2, 1)), dim=2)

            x = self.encoder_attn(
                x.view(-1, 2 * self.encoder_num_hidden + self.T - 1))

            # get weights by softmax
            alpha = F.softmax(x.view(-1, self.input_size))

            # get new input for LSTM
            x_tilde = torch.mul(alpha, X[:, t, :])

            # Fix the warning about non-contiguous memory
            # https://discuss.pytorch.org/t/dataparallel-issue-with-flatten-parameter/8282
            self.encoder_lstm.flatten_parameters()

            # encoder LSTM
            _, final_state = self.encoder_lstm(x_tilde.unsqueeze(0), (h_n, s_n))
            h_n = final_state[0]
            s_n = final_state[1]

            X_tilde[:, t, :] = x_tilde
            X_encoded[:, t, :] = h_n

        return X_tilde, X_encoded

    def _init_states(self, X):
        """Initialize all 0 hidden states and cell states for encoder.

        Args:
            X

        Returns:
            initial_hidden_states
        """
        # https://pytorch.org/docs/master/nn.html?#lstm
        return Variable(X.data.new(1, X.size(0), self.encoder_num_hidden).zero_())


class Decoder(nn.Module):
    """decoder in DA_LSTM."""

    def __init__(self, T, decoder_num_hidden, encoder_num_hidden):
        """Initialize a decoder in DA_LSTM."""
        super(Decoder, self).__init__()
        self.decoder_num_hidden = decoder_num_hidden
        self.encoder_num_hidden = encoder_num_hidden
        self.T = T

        self.attn_layer = nn.Sequential(
            nn.Linear(2 * decoder_num_hidden + encoder_num_hidden, encoder_num_hidden),
            nn.Tanh(),
            nn.Linear(encoder_num_hidden, 1)
        )
        self.lstm_layer = nn.LSTM(
            input_size=1,
            hidden_size=decoder_num_hidden
        )
        self.fc = nn.Linear(encoder_num_hidden + 1, 1)
        self.fc_final = nn.Linear(decoder_num_hidden + encoder_num_hidden, 1)

        self.fc.weight.data.normal_()

    def forward(self, X_encoded, y_prev):
        """forward."""
        d_n = self._init_states(X_encoded)
        c_n = self._init_states(X_encoded)

        for t in range(self.T - 1):

            x = torch.cat((d_n.repeat(self.T - 1, 1, 1).permute(1, 0, 2),
                           c_n.repeat(self.T - 1, 1, 1).permute(1, 0, 2),
                           X_encoded), dim=2)

            beta = F.softmax(self.attn_layer(
                x.view(-1, 2 * self.decoder_num_hidden + self.encoder_num_hidden)).view(-1, self.T - 1))

            # Eqn. 14: compute context vector
            # batch_size * encoder_hidden_size
            context = torch.bmm(beta.unsqueeze(1), X_encoded)[:, 0, :]
            if t < self.T - 1:
                # Eqn. 15
                # batch_size * 1
                y_tilde = self.fc(
                    torch.cat((context, y_prev[:, t].unsqueeze(1)), dim=1))

                # Eqn. 16: LSTM
                self.lstm_layer.flatten_parameters()
                _, final_states = self.lstm_layer(
                    y_tilde.unsqueeze(0), (d_n, c_n))

                d_n = final_states[0]  # 1 * batch_size * decoder_num_hidden
                c_n = final_states[1]  # 1 * batch_size * decoder_num_hidden

        # Eqn. 22: final output
        y_pred = self.fc_final(torch.cat((d_n[0], context), dim=1))

        return y_pred

    def _init_states(self, X):
        """Initialize all 0 hidden states and cell states for encoder.

        Args:
            X
        Returns:
            initial_hidden_states

        """
        # hidden state and cell state [num_layers*num_directions, batch_size, hidden_size]
        # https://pytorch.org/docs/master/nn.html?#lstm
        return Variable(X.data.new(1, X.size(0), self.decoder_num_hidden).zero_())


class Attention_LSTM(nn.Module):
    """da_rnn."""

    def __init__(self, X, y, T,
                 encoder_num_hidden,
                 decoder_num_hidden,
                 batch_size,
                 learning_rate,
                 epochs,
                 parallel=False):
        """da_rnn initialization."""
        super(Attention_LSTM, self).__init__()
        self.encoder_num_hidden = encoder_num_hidden
        self.decoder_num_hidden = decoder_num_hidden
        self.learning_rate = learning_rate
        self.batch_size = batch_size
        self.parallel = parallel
        self.shuffle = False
        self.epochs = epochs
        self.T = T
        self.X = X
        self.y = y

        self.device = torch.device('cuda:0' if torch.cuda.is_available() else 'cpu')
        print("==> Use accelerator: ", self.device)

        self.Encoder = Encoder(input_size=X.shape[1],
                               encoder_num_hidden=encoder_num_hidden,
                               T=T).to(self.device)
        self.Decoder = Decoder(encoder_num_hidden=encoder_num_hidden,
                               decoder_num_hidden=decoder_num_hidden,
                               T=T).to(self.device)

        # Loss function
        self.criterion = nn.MSELoss()

        if self.parallel:
            self.encoder = nn.DataParallel(self.encoder)
            self.decoder = nn.DataParallel(self.decoder)

        self.encoder_optimizer = optim.Adam(params=filter(lambda p: p.requires_grad,
                                                          self.Encoder.parameters()),
                                            lr=self.learning_rate)
        self.decoder_optimizer = optim.Adam(params=filter(lambda p: p.requires_grad,
                                                          self.Decoder.parameters()),
                                            lr=self.learning_rate)

        # Training set
        self.train_timesteps = int(self.X.shape[0] * 17/19)
        self.y = self.y
        self.input_size = self.X.shape[1]

    def train(self):
        """training process."""
        iter_per_epoch = int(np.ceil(self.train_timesteps * 1. / self.batch_size))
        self.iter_losses = np.zeros(self.epochs * iter_per_epoch)
        self.epoch_losses = np.zeros(self.epochs)

        n_iter = 0

        for epoch in range(self.epochs):
            if self.shuffle:
                ref_idx = np.random.permutation(self.train_timesteps - self.T)
            else:
                ref_idx = np.array(range(self.train_timesteps - self.T))

            idx = 0

            while idx < self.train_timesteps:
                # get the indices of X_train
                indices = ref_idx[idx:(idx + self.batch_size)]
                # x = np.zeros((self.T - 1, len(indices), self.input_size))
                x = np.zeros((len(indices), self.T - 1, self.input_size))
                y_prev = np.zeros((len(indices), self.T - 1))
                y_gt = self.y[indices + self.T]

                # format x into 3D tensor
                for bs in range(len(indices)):
                    x[bs, :, :] = self.X[indices[bs]:(indices[bs] + self.T - 1), :]
                    y_prev[bs, :] = self.y[indices[bs]: (indices[bs] + self.T - 1)]

                loss = self.train_forward(x, y_prev, y_gt)
                self.iter_losses[int(epoch * iter_per_epoch + idx / self.batch_size)] = loss

                idx += self.batch_size
                n_iter += 1

                if n_iter % 10000 == 0 and n_iter != 0:
                    for param_group in self.encoder_optimizer.param_groups:
                        param_group['lr'] = param_group['lr'] * 0.9
                    for param_group in self.decoder_optimizer.param_groups:
                        param_group['lr'] = param_group['lr'] * 0.9

                self.epoch_losses[epoch] = np.mean(self.iter_losses[range(
                    epoch * iter_per_epoch, (epoch + 1) * iter_per_epoch)])

            if epoch % 10 == 0:
                print("Epochs: ", epoch, " Iterations: ", n_iter,
                      " Loss: ", self.epoch_losses[epoch])

            if epoch % 50 == 0:
                y_train_pred = self.test(on_train=True)
                y_test_pred = self.test(on_train=False)
                y_pred = np.concatenate((y_train_pred, y_test_pred))
                plt.ioff()
                plt.figure()
                plt.plot(range(1, 1 + len(self.y)), self.y, label="batangTrue")
                plt.plot(range(self.T, len(y_train_pred) + self.T),
                         y_train_pred, label='batangPred - Train')
                plt.plot(range(self.T + len(y_train_pred), len(self.y) + 1),
                         y_test_pred, label='batangPred - Test')
                plt.legend(loc='upper left')
                plt.title("batang")
                plt.show()

            # # Save files in last iterations（迭代）
            if epoch == self.epochs - 1:
                np.savetxt('./{ntimestep}batangloss.txt'.format(ntimestep = ntimestep),
                           np.array(self.epoch_losses), delimiter=',')
                np.savetxt('./{ntimestep}batangy_pred.txt'.format(ntimestep = ntimestep),
                           np.array(y_pred), delimiter=',')
                np.savetxt('./{ntimestep}batangy_true.txt'.format(ntimestep = ntimestep),
                           np.array(self.y), delimiter=',')

    def train_forward(self, X, y_prev, y_gt):
        """
        Forward pass.

        Args:
            X:
            y_prev:
            y_gt: Ground truth label

        """
        # zero gradients
        self.encoder_optimizer.zero_grad()
        self.decoder_optimizer.zero_grad()

        input_weighted, input_encoded = self.Encoder(
            Variable(torch.from_numpy(X).type(torch.FloatTensor).to(self.device)))
        y_pred = self.Decoder(input_encoded, Variable(
            torch.from_numpy(y_prev).type(torch.FloatTensor).to(self.device)))

        y_true = Variable(torch.from_numpy(
            y_gt).type(torch.FloatTensor).to(self.device))

        y_true = y_true.view(-1, 1)
        loss = self.criterion(y_pred, y_true)
        loss.backward()

        self.encoder_optimizer.step()
        self.decoder_optimizer.step()

        return loss.item()


    def test(self, on_train=False):
        """test."""

        if on_train:
            y_pred = np.zeros(self.train_timesteps - self.T + 1)
        else:
            y_pred = np.zeros(self.X.shape[0] - self.train_timesteps)

        i = 0
        while i < len(y_pred):
            batch_idx = np.array(range(len(y_pred)))[i: (i + self.batch_size)]
            X = np.zeros((len(batch_idx), self.T - 1, self.X.shape[1]))
            y_history = np.zeros((len(batch_idx), self.T - 1))

            for j in range(len(batch_idx)):
                if on_train:
                    X[j, :, :] = self.X[range(
                        batch_idx[j], batch_idx[j] + self.T - 1), :]
                    y_history[j, :] = self.y[range(
                        batch_idx[j], batch_idx[j] + self.T - 1)]
                else:
                    X[j, :, :] = self.X[range(
                        batch_idx[j] + self.train_timesteps - self.T, batch_idx[j] + self.train_timesteps - 1), :]
                    y_history[j, :] = self.y[range(
                        batch_idx[j] + self.train_timesteps - self.T, batch_idx[j] + self.train_timesteps - 1)]

            y_history = Variable(torch.from_numpy(
                y_history).type(torch.FloatTensor).to(self.device))
            _, input_encoded = self.Encoder(
                Variable(torch.from_numpy(X).type(torch.FloatTensor).to(self.device)))
            y_pred[i:(i + self.batch_size)] = self.Decoder(input_encoded,
                                                           y_history).cpu().data.numpy()[:, 0]
            i += self.batch_size

        return y_pred


## Util Function

from sklearn.preprocessing import MinMaxScaler

def read_data(input_path, debug=True):
    """Read data.

    Args:
        input_path (str): directory to  dataset.

    Returns:
        X (np.ndarray): features.
        y (np.ndarray): ground truth.

    """
    df = pd.read_csv(input_path, nrows=250 if debug else None)

    # 平滑操作
    df["runoff"] = df["runoff"].apply(np.log1p)

    # X = df.iloc[:, 0:-1].values
    # X = df.loc[:, [x for x in df.columns.tolist() if x != 'NDX']].as_matrix() # AttributeError: 'DataFrame' object has no attribute 'as_matrix'
    X = df.loc[:, [x for x in df.columns.tolist() if x != 'DT']].iloc[:, :].values
    # y = df.iloc[:, -1].values
    y = np.array(df.runoff)

    # 0-1标准化
    scaler = MinMaxScaler(feature_range=(0, 1))
    X = scaler.fit_transform(X)
    ynew = []
    for yy in y:
        yy = float(yy - np.min(y)) / (np.max(y) - np.min(y))
        ynew.append(yy)
    ynew = np.array(ynew)
    ymin = np.min(y)
    ymax = np.max(y)
    print(f'ynew:{ynew}')

    return X, ynew, ymin, ymax


## Main

# Read dataset
print("==> Load dataset ...")
X, y, ymin, ymax = read_data(dataroot, debug=False)

# Initialize model
print("==> Initialize DA-RNN model ...")
model = Attention_LSTM(
    X,
    y,
    ntimestep,
    nhidden_encoder,
    nhidden_decoder,
    batchsize,
    lr,
    epochs
)

# Train
print("==> Start training ...")
t1=time.time()
model.train()
t2=time.time()
print (t2-t1)


# Prediction
y_pred = model.test()

y_pred = y_pred * (ymax - ymin) + ymin
model.y = model.y * (ymax - ymin) + ymin

# 对数还原
y_pred = np.exp(y_pred)
model.y = np.exp(model.y)

np.savetxt('./{ntimestep}batangy_pred_finally.txt'.format(ntimestep = ntimestep),
           np.array(y_pred), delimiter=',')

fig1 = plt.figure()
plt.semilogy(range(len(model.iter_losses)), model.iter_losses)
plt.savefig("./{ntimestep}batang1.jpg".format(ntimestep = ntimestep))
plt.close(fig1)

fig2 = plt.figure()
plt.semilogy(range(len(model.epoch_losses)), model.epoch_losses)
plt.savefig("./{ntimestep}batang2.jpg".format(ntimestep = ntimestep))
plt.close(fig2)

np.savetxt('./{ntimestep}batangy_true_finally.txt'.format(ntimestep = ntimestep),
           np.array(model.y[model.train_timesteps:]), delimiter=',')

fig3 = plt.figure()
plt.plot(model.y[model.train_timesteps:], color='blue', label="True")
plt.plot(y_pred, color='red', label='Pred')
plt.plot(model.y[model.train_timesteps:] - y_pred, color='green', label="True-Pred")
plt.title("batang")
plt.xlabel('time')
plt.ylabel('runoff')
plt.legend(loc='upper center')
plt.grid()
plt.savefig("./{ntimestep}batang3.jpg".format(ntimestep = ntimestep))
plt.show()
plt.close(fig3)
print('Finished Training')


from math import sqrt
from sklearn.metrics import mean_squared_error,mean_absolute_error
from scipy.stats import pearsonr
from sklearn.preprocessing import MinMaxScaler
#
# 计算RMSE，MAE误差值

true = model.y[model.train_timesteps:]
pred = y_pred

rmse = sqrt(mean_squared_error(true, pred))
mae = mean_absolute_error(true, pred)
pearson = pearsonr(true, pred)
nse = 1 - sum(np.square(true - pred))/sum(np.square(true - np.mean(true)))
print(f'MAE:{mae}\n', f'RMSE:{rmse}\n', f'pearsonr:{pearson}\n', f'nse:{nse}')
print(f'pred长度:{len(pred)}')

# 绘制散点图与拟合直线
def Curve_Fitting(x,y,deg):
    parameter = np.polyfit(x, y, deg)    #拟合deg次多项式
    p = np.poly1d(parameter)             #拟合deg次多项式
    aa=''                               #方程拼接  ——————————————————
    for i in range(deg+1):
        bb=round(parameter[i],2)
        if bb>0:
            if i==0:
                bb=str(bb)
            else:
                bb='+'+str(bb)
        else:
            bb=str(bb)
        if deg==i:
            aa=aa+bb
        else:
            aa=aa+bb+'x^'+str(deg-i)    #方程拼接  ——————————————————
    plt.scatter(x, y, color='red', label='true--pred')     #原始数据散点图
    plt.plot(x, p(x), 'b--')  # 画拟合曲线
   # plt.text(-1,0,aa,fontdict={'size':'10','color':'b'})
   # plt.legend(round(np.corrcoef(y, p(x))[0,1]**2,2))   #拼接好的方程和R方放到图例
    plt.title('true--pred Attention-LSTM')
    plt.xlabel('true')
    plt.ylabel('pred')
    # 去除图边框的顶部刻度和右边刻度
    plt.tick_params(top='off', right='off')
    # 添加图例
    plt.legend(loc='upper left')
    plt.savefig("./{ntimestep}batang4.jpg".format(ntimestep=ntimestep))
    plt.show()
#    print('曲线方程为：',aa)
#    print('    r^2为：',round(np.corrcoef(y, p(x))[0,1]**2,2)
Curve_Fitting(true,pred,1)