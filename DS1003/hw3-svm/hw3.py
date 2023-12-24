import util
import load
import numpy as np
from matplotlib import pyplot as plt
from collections import Counter


def list2dict(words):
    return dict(Counter(words))

def load_data():
    datas = load.shuffle_data()
    X_train = []
    y_train = []
    X_test = []
    y_test = []

    for i in range(1500):
        X_train.append(list2dict(datas[i][:-1]))
        y_train.append(datas[i][-1])
    for i in range(1500, 2000):
        X_test.append(list2dict(datas[i][:-1]))
        y_test.append(datas[i][-1])
    print("loading is over !")

    return X_train, X_test, y_train, y_test

def Pegasos(X_train, y_train, lamb, epoch=1000):
    w = {}
    t = 1
    num = len(X_train)
    for _ in range(epoch):
        for i in range(num):
            t += 1
            eta_t = 1 / (t * lamb)
            util.increment(w, -(eta_t * lamb), w)
            if y_train[i] * util.dotProduct(w, X_train[i]) < 1:
                util.increment(w, eta_t * y_train[i], X_train[i])
            print(f"{i}-th data of {_} epoch over !")
    return w

def Pegasos_fast(X_train, y_train, lamb, epoch=1000):
    W = {}
    t = 1
    s = 1
    num = len(X_train)
    for _ in range(epoch):
        for i in range(num):
            t += 1
            eta_t = 1 / (t * lamb)
            if s * y_train[i] * util.dotProduct(W, X_train[i]) < 1:
                s *= (1 - eta_t * lamb)
                util.increment(W, (1 / s) * eta_t * y_train[i], X_train[i])
            else:
                s *= 1 - eta_t * lamb
        print(f"{_ + 1} epoch over !")

    for key in W.keys():
        W[key] = W.get(key, 0) * s

    return W

def Pegasos_fromHW4(X_train, y_train, lamb, epoch=1000):
    w = {}
    seta = {}
    t = 1
    num = len(X_train)
    for _ in range(epoch):
        for i in range(num):
            if -y_train[i] * util.dotProduct(seta, X_train[i]) < lamb * (t - 1):
                util.increment(seta, -y_train[i], X_train[i])
            t += 1
            print(f"{i}-th data of {_} epoch over !")
    util.increment(w, -1 / (lamb * (t - 1)), seta)
    return w

def test(X_test, y_test, w):
    num = len(X_test)
    pos = 0

    for i in range(num):
        if np.sign(util.dotProduct(w, X_test[i])) == y_test[i]: pos += 1
    return pos / num

def plot_lambda_precision(lamb_regs,
                          X_train, y_train,
                          X_val, y_val, max_step=100):
    precision = []
    for lamb_reg in lamb_regs:
        w = Pegasos_fromHW4(X_train, y_train, lamb=lamb_reg, epoch=max_step)
        precision.append(test(X_val, y_val, w))
    plt.plot(lamb_regs, precision)
    plt.grid()
    plt.xlabel("$\lambda$")
    plt.ylabel("Prediction")
    plt.show()

if __name__ == '__main__':
    X_train, X_test, y_train, y_test = load_data()
    # plot_lambda_precision(np.linspace(1, 2, 5), X_train, y_train, X_test, y_test)
    w1 = Pegasos_fromHW4(X_train, y_train, lamb=1, epoch=10)
    print(test(X_test, y_test, w1))

