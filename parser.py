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

def parse(fileName):
    df = pd.read_html(fileName)
    number = len(df[0][1])
    tierNames = findTiers(fileName)
    for element in tierNames:
        sa = pd.DataFrame(columns=['Time','Action'])
        for x in range(0,number,4):
            if element == df[0][1][x]:
                print "tier", df[0][1][x]
                action = df[0][1][x + 1]
                timestamp = df[0][1][x+2]
                start,end = timestamp.split(' - ')
                print 'Initializing conversion'
                tempDf = createDF(start,end,timedelta(microseconds=100000),action)
                sa=sa.append(tempDf)
        print "element:", element
        print sa
    
#Creates dataframe and spans time across interval
def createDF(start,end,delta,action):
    
    start = datetime.datetime.strptime(start, '%H:%M:%S.%f')
    end = datetime.datetime.strptime(end, '%H:%M:%S.%f')
    current = start
    df = pd.DataFrame(columns=['Time','Action'])
    a = []
    while current < end:
        a.append(stringConverter(current))
        current += delta
    
    df['Time'] = a
    df['Action'] = action
    return df

def roundTime(time):
    print "----------------"
    print time
    x = timedelta(seconds = 10)
    print time % x
    print "----------------"
#
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
        for element in a:
            if element == tempElement:
                answer = True
        if answer == False:
            a.append(tempElement)
            
    return a
    
def unix():
    a = date(2010,9,01)
    dateUnix = mktime(a.timetuple())
    x = time.strptime('00:01:00.000'.split('.')[0],'%H:%M:%S')
    timeUnix = datetime.timedelta(hours=x.tm_hour,minutes=x.tm_min,seconds=x.tm_sec).total_seconds()
    print dateUnix+timeUnix
    
    
unix()