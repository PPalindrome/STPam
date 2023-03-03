'''train完整模块'''
import sys
import time

import torch.optim as optim
from torch.utils.data import DataLoader

'''
   数据的导入
   可调优数据的定义
   网络实例化
   优化器的定义
   数据搬移至gpu
   损失函数的定义
   开始训练
'''

'''data_preparation完整模块'''

import numpy as np
import torch
from torch.utils.data import Dataset

'''
    lengths :决定了用于预测序列的长度
    targets :表示待预测的序列长度
    例如lengths = 8， targets = 1，则表示用8个数预测一个数
'''
lengths = 8
targets = 1

def data_start():
    T = 1000
    x = torch.arange(1, T + 1, dtype=torch.float32)
    y = torch.sin(0.01 * x) + torch.normal(0, 0.1, (T,))  # 每个y加上一个0到0.2(左闭右开)的噪声
    return x, y

def data_prediction_to_f_and_t(data, num_features, num_targets):
    '''
    准备数据集的函数
    '''
    features, target = [], []
    for i in range(((len(data)-num_features-num_targets)//num_targets) + 1):
        f = data[i*num_targets:i*num_targets+num_features]
        t = data[i*num_targets+num_features:i*num_targets+num_features+num_targets]
        features.append(list(f))
        target.append(list(t))

    return np.array(features), np.array(target)

class dataset_to_Dataset(Dataset):
    '''
    将传入的数据集，转成Dataset类，方面后续转入Dataloader类
    注意定义时传入的data_features,data_target必须为numpy数组
    '''
    def __init__(self, data_features, data_target):
        self.len = len(data_features)
        self.features = torch.from_numpy(data_features)
        self.target = torch.from_numpy(data_target)

    def __getitem__(self, index):
        return self.features[index], self.target[index]

    def __len__(self):
        return self.len

def dataset_split_4sets(data_features, data_target, ratio=0.8):
    '''
    功能：训练集与测试集的特征与target分离
    ratio:表示训练集所占的百分比
    '''
    split_index = int(ratio*len(data_features))
    train_features = data_features[:split_index]
    train_target = data_target[:split_index]
    test_features = data_features[split_index:]
    test_target = data_target[split_index:]
    return train_features, train_target, test_features, test_target



'''GRU完整模块'''
import torch.nn as nn

'''
GRU:
'''
INPUT_SIZE = 1# The number of expected features in the input x，就是我们表示子序列中一个数的描述的特征数量，只有一个就填1，一个数字就是1
HIDDEN_SIZE = 64# The number of features in the hidden state h，隐藏状态的特征数
# h0 = torch.zeros([])# h0的shape与hn的shape一样为(D * num_layers, batch_size, hidden_size)
                    # 其中的D = 2 if bidirectional=True otherwise 1，num_layers为GRU的层数
                    # 如果这边不对h0进行定义，则网络中的forward中h0可以直接用None替代，默认全零。
# 定义我们的类
class GRU(nn.Module):
    def __init__(self):
        super(GRU, self).__init__()
        self.gru = nn.GRU(
            input_size=INPUT_SIZE,# 传入我们上面定义的参数
            hidden_size=HIDDEN_SIZE,# 传入我们上面定义的参数
            batch_first=True,# 为什么设置为True上面解释过了
        )
        self.mlp = nn.Sequential(
            nn.Linear(HIDDEN_SIZE, 32), # 加入线性层的原因是，GRU的输出，参考官网为(batch_size, seq_len, hidden_size)
            nn.LeakyReLU(),             # 这边的多层全连接，根据自己的输出自己定义就好，
            nn.Linear(32, 16),          # 我们需要将其最后打成（batch_size, output_size）比如单值预测，这个output_size就是1，
            nn.LeakyReLU(),             # 这边我们等于targets
            nn.Linear(16, targets)      # 这边输出的（batch_size, targets）且这个targets是上面一个模块已经定义好了
        )

    def forward(self, input):
        output, h_n = self.gru(input, None)# output:(batch_size, seq_len, hidden_size)，h0可以直接None
        # print(output.shape)
        output = output[:, -1, :]# output:(batch_size, hidden_size)
        output = self.mlp(output)# 进过一个多层感知机，也就是全连接层，output:(batch_size, output_size)
        return output



'''LSTM完整模块'''

# 用户：Ejemplarr
# 编写时间:2022/3/24 22:09
import torch.nn as nn





'''
    GRU与LSTM的在代码上的差别，就是将nn.GRU换成nn.LSTM而已
'''

class LSTM(nn.Module):
    def __init__(self):
        super(LSTM, self).__init__()
        self.gru = nn.LSTM(
            input_size=INPUT_SIZE,# 传入我们上面定义的参数
            hidden_size=HIDDEN_SIZE,# 传入我们上面定义的参数
            batch_first=True,# 为什么设置为True上面解释过了
        )
        self.mlp = nn.Sequential(
            nn.Linear(HIDDEN_SIZE, 32), # 加入线性层的原因是，GRU的输出，参考官网为(batch_size, seq_len, hidden_size)
            nn.LeakyReLU(),             # 这边的多层全连接，根据自己的输出自己定义就好，
            nn.Linear(32, 16),          # 我们需要将其最后打成（batch_size, output_size）比如单值预测，这个output_size就是1，
            nn.LeakyReLU(),             # 这边我们等于targets
            nn.Linear(16, targets)      # 这边输出的（batch_size, targets）且这个targets是上面一个模块已经定义好了
        )

    def forward(self, input):
        output, h_n = self.gru(input, None)# output:(batch_size, seq_len, hidden_size)，h0可以直接None
        # print(output.shape)
        output = output[:, -1, :]# output:(batch_size, hidden_size)
        output = self.mlp(output)# 进过一个多层感知机，也就是全连接层，output:(batch_size, output_size)
        return output




# 可调参数的定义
BATCH_SIZE = 16
EPOCH = 100
LEARN_RATE = 1e-3


# 数据的导入
x, y = data_start()
dataset_features, dataset_target = data_prediction_to_f_and_t(y, lengths, targets)
trian_features, train_target, test_features, test_target = dataset_split_4sets(dataset_features, dataset_target)
train_set = dataset_to_Dataset(data_features=trian_features, data_target=train_target)

train_set_iter = DataLoader(dataset=train_set,# 将数据封装进Dataloader类
                            batch_size=BATCH_SIZE,
                            shuffle=True,  # 打乱batch与batch之间的顺序
                            drop_last=True)# drop_last = True表示最后不够一个batch就舍弃那些多余的数据

# gpu的定义
device = ('cuda'if torch.cuda.is_available else 'cpu')

# 网络的实例化
net_gru = GRU().to(device)
net_lstm = LSTM().to(device)

# 优化器的定义
optim_gru = optim.Adam(params=net_gru.parameters(), lr=LEARN_RATE)
optim_lstm = optim.Adam(params=net_lstm.parameters(),lr=LEARN_RATE)

# 损失函数的定义
loss_fuc = nn.MSELoss()

# 训练函数的定义
def train_for_gru(data, device, loss_fuc, net, optim, Epoch):
    for epoch in range(Epoch):
        loss_print = []
        for batch_idx, (x, y) in enumerate(data):
            x = x.reshape([BATCH_SIZE, lengths, 1])
            x = x.to(device)
            # print(y.shape)
            y = y.reshape((len(y),targets))
            y = y.to(device)
            # print(y.shape)
            y_pred = net(x)
            loss = loss_fuc(y, y_pred)
            loss_print.append(loss.item())
            # 三大步
            # 梯度值更为0
            optim.zero_grad()
            # loss反向传播
            loss.backward()
            # 优化器更新
            optim.step()
        print('GRU:loss:',sum(loss_print)/len(data))

def train_for_lstm(data, device, loss_fuc, net, optim, Epoch):
    for epoch in range(Epoch):
        loss_print = []
        for batch_idx, (x, y) in enumerate(data):
            x = x.reshape([BATCH_SIZE, lengths, 1])
            x = x.to(device)
            # print(y.shape)
            y = y.reshape((len(y),targets))
            y = y.to(device)
            # print(y.shape)
            y_pred = net(x)
            loss = loss_fuc(y, y_pred)
            loss_print.append(loss.item())
            # 三大步
            # 网络的梯度值更为0
            optim.zero_grad()
            # loss反向传播
            loss.backward()
            # 优化器更新
            optim.step()
        print('LSTM:loss:',sum(loss_print)/len(data))


def main():
    start = time.perf_counter()
    train_for_gru(train_set_iter, device, loss_fuc, net_gru, optim_gru, EPOCH)
    train_for_lstm(train_set_iter, device, loss_fuc, net_lstm, optim_lstm, EPOCH)
    end = time.perf_counter()
    print('训练时间为：{:.2f}s'.format(end-start))
    #保存模型
    torch.save(net_gru.state_dict(), 'gru.pt')
    torch.save(net_lstm.state_dict(), 'lstm.pt')
if __name__ == '__main__':
    print("Python脚本名：", sys.argv[0])
    main()
