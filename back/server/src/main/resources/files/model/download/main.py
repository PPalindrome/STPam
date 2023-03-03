import sys

# 默认模型参数
model = [3.0, 0.0001, 150.0]


def modify(layerNum, learningRate, maxIteration):
    print("修改前的模型参数：", "单元层数：", model[0], "学习率：", model[1], "最大迭代次数", model[2])
    model[0] = layerNum
    model[1] = learningRate
    model[2] = maxIteration
    print("修改后的模型参数：", "单元层数：", model[0], "学习率：", model[1], "最大迭代次数", model[2])


if __name__ == '__main__':
    a = []
    for i in range(1, len(sys.argv)):
        a.append((float(sys.argv[i])))
    modify(a[0], a[1], a[2])
