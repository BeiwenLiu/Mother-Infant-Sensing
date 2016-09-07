# -*- coding: utf-8 -*-
"""
Created on Tue Aug 30 21:36:31 2016

@author: Beiwen Liu
"""
import numpy as np
import pandas as pd
import datetime as datetime
from datetime import date, timedelta
from time import mktime
import time
import calendar

#specify files here
def multipleParse():
    files = ['tierTesting.html']
    
    for file in files:
        parse(file)
        
#identifies time range and action from HTML table
def parse(fileName):
#specify Date and Time at Start Here    
    unixTime = unix('00:00:00.0','1970-01-01')
    df = pd.read_html(fileName)
    number = len(df[0][1])
    increment = timedelta(microseconds=100000)
    tierNames = findTiers(fileName)
    startFrame, timeOffset = findBeginning(fileName)
    for element in tierNames:
        startFrame, time = findBeginning(fileName)
        sa = pd.DataFrame(columns=['Time','Action'])
        for x in range(0,number,4):
            if element == df[0][1][x]:
                print "tier:", df[0][1][x]
                action = df[0][1][x + 1]
                timestamp = df[0][1][x+2]
                start,end = timestamp.split(' - ')
                print 'Initializing conversion'
                firstDf = createDF(time, start,increment,timeOffset,unixTime)
                tempDf = createDF(start,end,increment,timeOffset,unixTime,action)
                sa = pd.concat([sa,firstDf],ignore_index=True)
                sa = pd.concat([sa,tempDf],ignore_index=True)
                time = end
        print "element:", element
        print sa
        sa.to_csv("{}.csv".format(element))
    
#Creates dataframe and spans time across interval
def createDF(start,end,delta,offset,unixTime,action = ""):
    start = stringToTime(start)
    end = stringToTime(end)
    start = roundTime(start)
    end = roundTime(end)
    current = start
    df = pd.DataFrame(columns=['Time','Action'])
    a = []
    offsetSeconds = computeSecondOffset(offset)
    offsetSeconds = timedelta(seconds = offsetSeconds)
    while current < end:
        temp = current - offsetSeconds
        #a.append(unix(stringConverter(temp))+unixTime) 
        a.append(stringConverter(current))
        #stringConverter(current) for time representation
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
#Must designate starting time with a separate tier named "Start"
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
                      
#Takes in string '00:00:00' and computes total amount of seconds (including microseconds) into float form
def computeSecondOffset(offset):
    offset2 = float(offset[-2:])
    offset = time.strptime(offset.split('.')[0],'%H:%M:%S')
    offsetSeconds = datetime.timedelta(hours=offset.tm_hour,minutes=offset.tm_min,seconds=offset.tm_sec).total_seconds()
    return offsetSeconds + offset2


def unix(timeStamp,dateStamp="1970-01-01"):
    a = datetime.datetime.strptime(dateStamp,'%Y-%m-%d').date()
    #dateUnix = mktime(a.timetuple()) for local timezone
    dateUnix = calendar.timegm(a.timetuple()) #for UTC timezone
    x = time.strptime(timeStamp.split('.')[0],'%H:%M:%S')
    timeUnix = datetime.timedelta(hours=x.tm_hour,minutes=x.tm_min,seconds=x.tm_sec).total_seconds()
    timeUnix2 = float(timeStamp[-2:])
    return dateUnix+timeUnix+timeUnix2

multipleParse()