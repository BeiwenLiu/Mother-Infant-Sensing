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
    
    
    number = len(df[uniqueValues])
    
    indexVal = df.index.values[-1] / 8
    print indexVal
    
    plt.figure(1)
    for x in range(1,9):
        plt.subplot(4,2,x)
        ax = df[uniqueValues][0:indexVal*x].plot(ax=plt.gca(), title="Graph %d" % x, legend=False)
        ax.set_xlabel("")
        ax.set_ylim(0,2)
        ax.set_xlim(indexVal*(x-1),indexVal*x)

        
    plt.subplots_adjust(hspace=.7)
    
    plt.show()
    
def graph2(filename):
    
    df = pd.read_csv('csv/{}'.format(filename),index_col='Time')
    
    uniqueValues = np.unique(df[['Action']])
    if len(uniqueValues) > 1:
        uniqueValues = uniqueValues[1:]
        
        
    
    answer = raw_input("Would you like to select an annotation? (y/n)\n")
    if answer == 'y':
        answer = True
        annotation = raw_input("Please select one or more (separate with commas):\n{}\n".format(uniqueValues))
        annotationList = annotation.split(",")
    else: answer = False
    

    
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
    
    if answer:
        ax = df[annotationList].plot()
        ax.legend()
    else:
        ax = df[uniqueValues].plot()
        
    ax.set_ylim(0,2)
    ax.set_xlim(0,index)
    plt.show()
    
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
    
    
    calculateTotal(sa)
    
    plotHistogram(sa)
    
    plt.show()
        
        
def plotHistogram(sa):
    row1 = sa['Start']
    row2 = sa['End']
    answer = []
    answer.append(0)
    for number in range(len(row1) - 1):
        answer.append(stringToTime(row1[number + 1]) - stringToTime(row2[number]))
        
    sa['Gap'] = answer
    sa['Gap'].hist(bins = 20, facecolor='g')
    
    plt.xlabel('Time in Seconds')
    plt.ylabel('Occurences')
    plt.title('Histogram of gaps between occurences')
    plt.show()
    
def calculateTotal(sa):
    row3 = sa['Duration']
    total = 0
    
    for number in range(len(row3)):
        total = total + stringToTime(row3[number])
        
    print total
    
def stringToTime(arg):
    offset2 = float(arg[-4:])
    x = time.strptime(arg.split('.')[0],'%H:%M:%S')
    total = datetime.timedelta(hours=x.tm_hour,minutes=x.tm_min,seconds=x.tm_sec).total_seconds()
    return offset2+total

def roundTime(time):
    x = timedelta(microseconds = 100000)
    ans = int(time.strftime('%f')[-5])
    if (ans >= 5):
        time = time + x
    time = stringConverter(time)
    time = stringToTime(time)
    
    return time
    
def stringConverter(datet):
    ans = datet.strftime('%H:%M:%S.%f')[:-5]
    return ans
    
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
graph2("comment.csv")
#graph("comment.csv")
#histogram("P1_e20160630_174419_013088.txt")