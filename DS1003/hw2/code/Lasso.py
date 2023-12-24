import random
import numpy as np
import time
import matplotlib.pyplot as plt
import pickle

from setup_problem import load_problem
from ridge_regression import plot_prediction_functions, compare_parameter_vectors, do_grid_search_ridge, RidgeRegression

def loss4square(X, y, w):
    return (X @ w - y).T @ (X @ w - y)
def J4lasso(X, y, w, lamb):
    return loss4square(X, y, w) + lamb * np.linalg.norm(w, 1)
def soft(a, b):
    return np.sign(a) * max(0, np.abs(a) - b)

def cyclic_coordinate_descent(X, y, lamb=0.1, max_iter=1000, tolerence=1e-5):
    I = np.eye(X.shape[1])
    w = np.linalg.inv(X.T @ X + lamb * I) @ X.T @ y
    old_J = J4lasso(X, y, w, lamb)
    i = 0
    diff = tolerence
    start_time = time.time()
    while (i < max_iter) and (diff >= tolerence):
        for j in range(X.shape[1]):
            Xj = X[:, j]
            if Xj.sum() == 0:
                w[j] = 0
            else:
                a = 2 * Xj.T @ Xj
                c = 2 * Xj.T @ (y - X @ w) + w[j] * a
                w[j] = soft(c / a, lamb / a)
        new_j = J4lasso(X, y, w, lamb)
        diff = np.abs(old_J - new_j)
        i += 1
    end_time = time.time()
    print(f"Steps: {i} \n Loss: {new_j} \n Time: {end_time - start_time}")
    return w, new_j

def cyclic_coordinate_descent_2(X, y, lamb=1, max_iter=1000, tolerence=1e-5):
    I = np.eye(X.shape[1])
    w = np.linalg.inv(X.T @ X + lamb * I) @ X.T @ y
    old_J = J4lasso(X, y, w, lamb)
    i = 0
    diff = tolerence
    start_time = time.time()
    vail = []
    for i in range(X.shape[1]):
        Xi = X[:, i]
        temp = 2 * y.T @ Xi
        if (-lamb <= temp) and (temp <= lamb):
            w[i] = 0
            vail.append(i)

    while (i < max_iter) and (diff >= tolerence):
        for j in range(X.shape[1]):
            if j in vail:
                continue
            Xj = X[:, j]
            if Xj.sum() == 0:
                w[j] = 0
            else:
                a = 2 * Xj.T @ Xj
                c = 2 * Xj.T @ (y - X @ w) + w[j] * a
                w[j] = soft(c / a, lamb / a)
        new_j = J4lasso(X, y, w, lamb)
        diff = np.abs(old_J - new_j)
        i += 1
    end_time = time.time()
    print(f"Steps: {i} \n Loss: {new_j} \n Time: {end_time - start_time}")
    return w, new_j

def cyclic_coordinate_descent_close_form(X, y, lamb=1): # 错的，忘了cs189中题目中的XTX = nI 的假设条件了
    w = np.zeros(X.shape[1])
    for i in range(X.shape[1]):
        X_i = X[:, i]
        d = y.T @ X_i
        if (d - lamb / 2) > 0:
            w[i] = (1 / X.shape[0]) * (d - lamb / 2)
        elif d + lamb / 2 < 0:
            w[i] = (1 / X.shape[0]) * (d + lamb / 2)
        else:
            w[i] = 0
    return w



def main():
    grid, results = do_grid_search_ridge(X_train, y_train, X_val, y_val)

    pred_fns = []
    x = np.sort(np.concatenate([np.arange(0, 1, .001), x_train]))
    name = "Target Parameter Values (i.e. Bayes Optimal)"
    pred_fns.append({"name": name, "coefs": coefs_true, "preds": target_fn(x)})

    l2regs = [0, grid.best_params_['l2reg'], 1]
    X = featurize(x)
    for l2reg in l2regs:
        ridge_regression_estimator = RidgeRegression(l2reg=l2reg)
        ridge_regression_estimator.fit(X_train, y_train)
        name = "Ridge with L2Reg=" + str(l2reg)
        pred_fns.append({"name": name,
                         "coefs": ridge_regression_estimator.w_,
                         "preds": ridge_regression_estimator.predict(X)})

    name = "Ridge with L1Reg=" + str(1)
    w, loss = cyclic_coordinate_descent_2(X_train, y_train, lamb=1)
    pred_fns.append({"name": name,
                     "coefs": w,
                     "preds": X @ w})

    f = plot_prediction_functions(x, pred_fns, x_train, y_train, legend_loc="best")
    f.show()

    f = compare_parameter_vectors(pred_fns)
    f.show()

if __name__ == '__main__':
    lasso_data_fname = "lasso_data.pickle"
    x_train, y_train, x_val, y_val, target_fn, coefs_true, featurize = load_problem(lasso_data_fname)

    # Generate features
    X_train = featurize(x_train)
    X_val = featurize(x_val)

    w2 = cyclic_coordinate_descent_close_form(X_train, y_train)

    print(f"w2_loss: {J4lasso(X_train, y_train, w2, 1)}")



