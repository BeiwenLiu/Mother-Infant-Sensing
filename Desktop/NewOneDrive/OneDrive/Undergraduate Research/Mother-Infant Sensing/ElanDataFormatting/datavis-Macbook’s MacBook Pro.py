# -*- coding: utf-8 -*-
"""
Created on Sat Sep 17 00:31:28 2016

@author: user
"""

import numpy as np
import pandas as pd
import datetime as datetime
from datetime import date, timedelta
from time import mktime
import time
import calendar
import matplotlib.pyplot as plt

def graph(filename):
    
    df = pd.read_csv('csv/{}'.format(filename),index_col='Time')
    uniqueValues = np.unique(df[['Action']])
    if len(uniqueValues) > 1:
        uniqueValues = uniqueValues[1:]
    
    tempDf = pd.DataFrame(columns=[uniqueValues])
    for element in uniqueValues:
        tempDf[element] = [0]
        
    
    indicies = df.index.values.tolist()
    indexIncrement = indicies[-1] + .1
    index = indicies[-1] + 1
    
    tempDf = tempDf.set_index([[indexIncrement]])
    tempDf.index.name = "Time"
    
    df = df[uniqueValues]
    df = df.append(tempDf)
    
    ax = df[uniqueValues].plot()
    ax.set_ylim(0,2)
    ax.set_xlim(0,index)
    plt.show()
    
#graph("comment.csv")


    
    
def histogram(filename):
    s = open('txt/{}'.format(filename), 'r')
    x = s.readline().split("\t")
    sa = pd.DataFrame(columns=['Action','Start','End','Duration'])
    
    
    tier = raw_input("Select a tier: {}\n".format(findTiers(filename)))
    count = 0
    keepGoing = False
    while len(x) != 1:
        
        count = count + 1
            
        if tier == x[0] and len(x) == 9:
            action = x[-1][:-1]
            start = x[2][-12:]
            end = x[4][-12:]
            duration = x[6][-12:]
            
            tempDf = createDF(action,start,end,duration)
            sa = pd.concat([sa,tempDf],ignore_index=True)
            keepGoing = True
        else:
            if keepGoing:
                labelAnnotations(tier,sa)
                keepGoing = False
            
        x = s.readline().split("\t")
    #timeDf = createDFTime(timeOffset,end,increment,unixTime,timeOffset)
    #sa = timeDf.merge(sa,on="Time",how="left")
  
  
def findTiers(filename):
    s = open('txt/{}'.format(filename), 'r')
    x = s.readline().split("\t")
    a = []
    while len(x) != 1:
        tempElement = x[0]
        answer = False
        for element in a:
            if element == tempElement:
                answer = True
        if answer == False:
            a.append(tempElement)
            
        x = s.readline().split("\t")
        
    
    s.close()
    return a
    
def createDF(action,start,end,duration):
    df = pd.DataFrame(columns=['Action','Start','End'])
    df['Action'] = [action]
    df['Start'] = [start]
    df['End'] = [end]
    df['Duration'] = [duration]
    return df
    
def labelAnnotations(tierName,dataframe):
    temp = dataframe
    uniqueValues = np.unique(temp[['Action']])
    annotation = raw_input("Select an annotation: {}\n".format(uniqueValues))
    
    counter = 0
    index = 0
    
    temp = temp.loc[temp['Action'] == annotation]
    
    sa = pd.DataFrame(columns=['Action','Start','End','Duration'])
    sa['Action'] = [annotation]
    sa['Start'] = ["00:00:00.000"]
    sa['End'] = ["00:00:00.000"]
    sa['Duration'] = ["00:00:00.000"]
    
    sa = pd.concat([sa,temp],ignore_index=True)
    
    temp1 = sa.shift(1)
    
    row1 = sa['Start'].values
    print row1
    
def stringToTime(arg):
    return datetime.datetime.strptime(arg, '%H:%M:%S.%f')
    

histogram("P1_e20160630_174419_013088.txt")