# -*- coding: utf-8 -*-
"""
Created on Thu Nov 10 02:27:04 2016

@author: user
"""

import matplotlib.pyplot as plt
from sklearn import datasets
from sklearn import svm

digits = datasets.load_digits()
clf = svm.SVC(gamma = .001, C = 100)
X,y = digits.data[:-10], digits.target[:-10]
clf.fit(X,y)
print(clf.predict(digits.data[-5]))