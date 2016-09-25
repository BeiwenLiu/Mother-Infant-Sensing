# -*- coding: utf-8 -*-
"""
Created on Sun Sep 25 03:09:01 2016

@author: Beiwen Liu
"""

import numpy as np
import pandas as pd
import datetime as datetime
from datetime import date, timedelta
from time import mktime
import time
import matplotlib.pyplot as plt
import collections

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
    
    plotHistogram(sa)
    
        
        
def plotHistogram(sa):
    row1 = sa['Start']
    row2 = sa['End']
    found = False
    keyHolder = ''
    answer = []
    answer.append(0)
    episode = collections.OrderedDict()
    for number in range(len(row1) - 1):
        temp = stringToTime(row1[number + 1]) - stringToTime(row2[number])
        if temp < 60:
            if found:
                episode[keyHolder] = str(row2[number + 1])
            else:
                keyHolder = str(row1[number])
                episode[keyHolder] = str(row2[number + 1])
                found = True
        else:
            found = False
    print episode
    
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
    
def p():
    episode = collections.OrderedDict()
    episode['hey']=1
    episode['bye']=2
    print episode.values()
    
histogram("P1.txt")
