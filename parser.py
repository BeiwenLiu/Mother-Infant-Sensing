# -*- coding: utf-8 -*-
"""
Created on Tue Aug 30 21:36:31 2016

@author: Beiwen Liu
"""
import math
import numpy as np
import pandas as pd
import datetime as datetime
from datetime import date, timedelta
from time import mktime
import time

def multipleParse():
    files = ['tierTesting.html']
    
    for file in files:
        parse(file)
        
#identifies time range and action from HTML table
def parse(fileName):
    df = pd.read_html(fileName)
    number = len(df[0][1])
    increment = timedelta(microseconds=100000)
    tierNames = findTiers(fileName)
    
    for element in tierNames:
        startFrame, time = findBeginning(fileName)
        sa = pd.DataFrame(columns=['Time','Action'])
        for x in range(0,number,4):
            if element == df[0][1][x]:
                print "tier", df[0][1][x]
                action = df[0][1][x + 1]
                timestamp = df[0][1][x+2]
                start,end = timestamp.split(' - ')
                print 'Initializing conversion'
                firstDf = createDF(time, start,increment)
                tempDf = createDF(start,end,increment,action)
                sa = pd.concat([sa,firstDf],ignore_index=True)
                sa = pd.concat([sa,tempDf],ignore_index=True)
                time = end
        print "element:", element
        print sa
        sa.to_csv("{}.csv".format(element))
    
#Creates dataframe and spans time across interval
def createDF(start,end,delta,action = ""):
    unixT = unix('2056-05-05','00:53:12')
    print unixT
    start = stringToTime(start)
    end = stringToTime(end)
    start = roundTime(start)
    end = roundTime(end)
    current = start
    df = pd.DataFrame(columns=['Time','Action'])
    a = []
    while current < end:
        a.append(stringConverter(current))
        current += delta
    
    df['Time'] = a
    df['Action'] = action
    return df
    

#Rounds to nearest tenth of second. Input must be of type dateTime
def roundTime(time):
    x = timedelta(microseconds = 100000)
    ans = int(time.strftime('%f')[-5])
    if (ans >= 5):
        time = time + x
    time = stringConverter(time)
    time = stringToTime(time)
    
    return time

#Give input of String "00:00:00.00" and it will return type datetime
def stringToTime(arg):
    return datetime.datetime.strptime(arg, '%H:%M:%S.%f')
    
#Give an input of type datetime and it will return String "00:00:00.0"
def stringConverter(datet):
    ans = datet.strftime('%H:%M:%S.%f')[:-5]
    return ans

#finds and returns an array with all the tiers
def findTiers(filename):
    temp = pd.read_html(filename)
    number = len(temp[0][1])
    a = []
    for x in range(0,number,4):
        tempElement = temp[0][1][x]
        answer = False
        if tempElement == "Start":
            answer = True
        for element in a:
            if element == tempElement:
                answer = True
        if answer == False:
            a.append(tempElement)
    return a

#finds Start and its time for offset
def findBeginning(filename):
    temp = pd.read_html(filename)
    number = len(temp[0][1])
    for x in range(0,number,4):
        tempElement = temp[0][1][x]
        timestamp = temp[0][1][x+2]
        if tempElement == "Start":
            start,end = timestamp.split(' - ')
            start = stringToTime(start)
            start = roundTime(start)
            start = stringConverter(start)
            df = pd.DataFrame(columns=['Time','Action'])
            df['Time'] = [start]
            df['Action'] = [tempElement]
            return df, start
                      

 
def unix(dateStamp,timeStamp):
    a = datetime.datetime.strptime(dateStamp,'%Y-%m-%d').date()
    dateUnix = mktime(a.timetuple())
    x = time.strptime(timeStamp.split('.')[0],'%H:%M:%S')
    timeUnix = datetime.timedelta(hours=x.tm_hour,minutes=x.tm_min,seconds=x.tm_sec).total_seconds()
    return dateUnix+timeUnix
  

#Add rounding
#improve unix timestamp
#export to csv
#finds start time

multipleParse()