# -*- coding: utf-8 -*-
"""
Created on Tue Aug 30 21:36:31 2016

@author: Beiwen Liu
"""

import pandas as pd
import datetime as datetime
from datetime import date, timedelta

def multipleParse():
    files = ['testing.html','testing2.html']
    
    for file in files:
        parse(file)

def parse(fileName):
    df = pd.read_html(fileName)
    number = len(df[0][1])
    
    for x in range(0,number,4):
        action = df[0][1][x + 1]
        timestamp = df[0][1][x+2]
        start,end = timestamp.split(' - ')
        print 'Initializing conversion'
        tempDf = createDF(start,end,timedelta(microseconds=100000),action)
        print tempDf.head()
    
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

def stringConverter(datet):
    ans = datet.strftime('%H:%M:%S.%f')[:-3]
    return ans

multipleParse()
    
    
    