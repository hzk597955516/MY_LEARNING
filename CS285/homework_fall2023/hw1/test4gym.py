import gym
from cs285.policies.loaded_gaussian_policy import LoadedGaussianPolicy


print('Loading expert policy from...', "./cs285/expert_data/expert_data_Ant-v4.pkl")
expert_policy = LoadedGaussianPolicy("./cs285/expert_data/expert_data_Ant-v4.pkl")

print(expert_policy)