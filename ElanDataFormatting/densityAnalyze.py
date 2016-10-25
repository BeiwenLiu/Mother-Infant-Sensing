# -*- coding: utf-8 -*-
"""
Created on Mon Oct 24 13:07:56 2016

@author: Beiwen Liu
"""
import os
import os.path

import pandas as pd

#Search for all files within csv/categories
def pregenerate():
    
    df = pd.DataFrame(columns=['Category','Duration','Density'])
    
    counter = 0
    for file in os.listdir("csv/categories"):
        if file.endswith("1_Min_Density.csv"):
            tempDf = process(file,counter) #Only for analyzing category annotated rows
            df = pd.concat([df,tempDf],ignore_index=True)
            
    print df['Duration'].sum()
    df.to_csv("csv/categories/compile.csv")
    
#Find mean, median, and std for count and duration of episode per 16 hour recording
def processFile():
    
            
            
#Find all occurrences where category is labeled yes or no and store in dataframe
def process(filename, counter): 
    print counter
    df = pd.DataFrame(columns=['Category','Duration','Density'])
    categories=[]
    duration=[]
    density=[]
    s = open("csv/categories/{}".format(filename), 'r')
    x = s.readline().split(",")
    
    y = s.readline().split(",")
    while len(y) != 1:
        if y[1] != "":
            categories.append(y[1])
            duration.append(float(y[-2]))
            density.append(float(y[-1][:-1]))
        y = s.readline().split(",")
    df['Category'] = categories
    df['Duration'] = duration
    df['Density'] = density
    return df
    
def execute():
    yes = np.zeros(11)
    detected = np.zeros(11)
    df = pd.DataFrame(columns=['Density','Category'])
    if os.path.isfile("csv/categories/compile.csv"):
        s = open("csv/categories/compile.csv", 'r')
        x = s.readline().split(",")
        x = s.readline().split(",")
        while len(x) != 1:
            ans = int(10*round(float(x[3]),1))
            detected[ans] = detected[ans] + 1
            if x[1] == "yes":
                yes[ans] = yes[ans] + 1
            x = s.readline().split(",")
        graph(yes, detected)
        
def graph(yes, detected):
    xVals = [0,.1,.2,.3,.4,.5,.6,.7,.8,.9,1]
    density = yes/detected
    df = pd.DataFrame(columns=['xVals','Density'])
    df['xVals'] = xVals
    df['Density'] = density
    df = df.set_index(['xVals'])
    df.plot()
    
    
    
def practice():
    
    print float("1.03")
            
execute()
#practice()