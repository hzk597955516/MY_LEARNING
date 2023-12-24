import numpy as np
from scipy.optimize import minimize
import matplotlib.pyplot as plt
def f_objective(theta, X, y, l2_param=1):
    '''
    Args:
        theta: 1D numpy array of size num_features
        X: 2D numpy array of size (num_instances, num_features)
        y: 1D numpy array of size num_instances
        l2_param: regularization parameter

    Returns:
        objective: scalar value of objective function
    '''
    return np.sum(np.logaddexp(0, X @ theta * (-y))) / X.shape[0] + l2_param * np.sum(np.square(theta))
    
def fit_logistic_reg(X, y, objective_function, l2_param=1):
    '''
    Args:
        X: 2D numpy array of size (num_instances, num_features)
        y: 1D numpy array of size num_instances
        objective_function: function returning the value of the objective
        l2_param: regularization parameter
        
    Returns:
        optimal_theta: 1D numpy array of size num_features
    '''
    def cf(w):
        return objective_function(w, X, y, l2_param)
    w0 = np.zeros(X.shape[1])
    return minimize(cf, w0).x

def get_data():
    X_train = np.loadtxt('X_train.txt', delimiter=',')
    X_val = np.loadtxt('X_val.txt', delimiter=',')
    y_train = np.loadtxt('y_train.txt', delimiter=',')
    y_val = np.loadtxt('y_val.txt', delimiter=',')
    # 0 -> -1
    for i in range(len(y_train)):
        if y_train[i] == 0:
            y_train[i] = -1

    for i in range(len(y_val)):
        if y_val[i] == 0:
            y_val[i] = -1
    # standardizing
    means = X_train.mean(axis=0)
    stds = X_train.std(axis=0)
    X_train_sd = (X_train - means) / stds
    X_val_sd = (X_val - means) / stds
    # add 1 col
    X_train_sd = np.hstack((np.ones(len(X_train)).reshape(-1, 1), X_train_sd))
    X_val_sd = np.hstack((np.ones(len(X_val)).reshape(-1, 1), X_val_sd))

    return X_train_sd, X_val_sd, y_train, y_val

def predict(w, X):
    return 1 / (1 + np.exp(-(X @ w)))

def get_performance(y_pre, y, thres=0.5):
    pre_c = 1 * (y_pre >= thres) + (-1) * (y_pre < thres)
    return (pre_c == y).sum() / len(y)

def find_best_l2(start=1e-3, end=1, n=10):
    l2_params = np.linspace(start, end, n)
    X_t, X_v, y_t, y_v = get_data()
    pre_acc = []
    for l2 in l2_params:
        w = fit_logistic_reg(X_t, y_t, f_objective, l2_param=l2)
        pre_acc.append(get_performance(predict(w, X_v), y_v))
    plt.plot(l2_params, pre_acc, 'o-')
    plt.show()

def cal(l2=0.22):
    X_t, X_v, y_t, y_v = get_data()
    w = fit_logistic_reg(X_t, y_t, f_objective, l2_param=l2)
    train_acc = get_performance(predict(w, X_t), y_t, thres=0.5)
    val_acc = get_performance(predict(w, X_v), y_v, thres=0.5)
    print(train_acc, val_acc)

def calibration():
    X_train_sd, X_val_sd, y_train, y_val = get_data()
    w = fit_logistic_reg(X_train_sd, y_train, f_objective, l2_param=0.2)
    val_probs = 1 / (1 + np.exp(-1 * X_val_sd @ w))
    bins = np.arange(0.05, 1.1, 0.1)
    inds = np.digitize(val_probs, bins)
    pred_prob = [[0, 0] for _ in range(10)]
    for i, ind in enumerate(inds):
        pred_prob[ind - 1][0] += val_probs[i]
        pred_prob[ind - 1][1] += 1

    mean_probs = [p[0] / p[1] for p in pred_prob]
    expect_probs = np.arange(0.1, 1.1, 0.1)
    plt.plot(expect_probs, expect_probs,
             color='gray', marker='o', linestyle='dashed')
    plt.plot(expect_probs, mean_probs,
             color='green', marker='+')
    plt.show()
if __name__ == '__main__':
    calibration()