import sys


def my_test(a:str,b:str )->int:
     print("Python函数运行：java调Python测试：")
     return int(a) + int(b)


if __name__=="__main__":
    print("Python脚本名：", sys.argv[0])

    my_arg = []
    for i in range(0, len(sys.argv)):
        print("参数"+str(i))
        my_arg.append(sys.argv[i])
    print("Java传入的参数长度为:"+str(len(my_arg)))
    print("java传入的两个参数值为"+my_arg[1],my_arg[2])

    result = my_test(my_arg[1],my_arg[2])
    print(result)
