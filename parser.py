# -*- coding: utf-8 -*-
"""
Created on Tue Aug 30 21:36:31 2016

@author: MacbookRetina
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
    df = pd.DataFrame(columns=['Date','Action'])
    a = []
    while current < end:
        a.append(current)
        current += delta
    
    df['Date'] = a
    df['Action'] = action
    return df


multipleParse()
    
    
    