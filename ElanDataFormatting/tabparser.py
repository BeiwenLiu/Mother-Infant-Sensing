# -*- coding: utf-8 -*-
"""
Created on Sun Sep 11 16:06:45 2016

@author: user
"""
import numpy as np
import pandas as pd
import datetime as datetime
from datetime import date, timedelta
from time import mktime
import time
import calendar

def practice(filename):

    
    
    timeOffset = findBeginning(filename)
    timeOffset,date,time = userInput(timeOffset)
               
    unixTime = unix(time,date)
    tiers = findTiers(filename)
    increment = timedelta(microseconds=100000)
    
    index = 0
        
    s = open('txt/{}'.format(filename), 'r')
    x = s.readline().split("\t")
    sa = pd.DataFrame(columns=['Time','Action'])
    #uniqueColumns = findAnnotations(filename,tier)
    
    
    count = 0
    while len(x) != 1:
        tier = tiers[index]
        count = count + 1
            
        if tier == x[0] and len(x) == 9:
            action = x[-1][:-1]
            start = x[2][-12:]
            end = x[4][-12:]
            tempDf = createDFUnix(start,end,increment,unixTime,timeOffset,action)
            sa = pd.concat([sa,tempDf],ignore_index=True)
            time = end
            x = s.readline().split("\t")
        else:
            timeDf = createDFTime(timeOffset,end,increment,unixTime,timeOffset)
            sa = timeDf.merge(sa,on="Time",how="left")
            labelAnnotations(tier,sa)
            #sa.to_csv("csv/{}.csv".format(tier))
            sa = pd.DataFrame(columns=['Time','Action'])
            index = index + 1
    timeDf = createDFTime(timeOffset,end,increment,unixTime,timeOffset)
    sa = timeDf.merge(sa,on="Time",how="left")
    labelAnnotations(tier,sa)
    #sa.to_csv("csv/{}.csv".format(tier))
    
            
    
def userInput(timeOffset):
    keepGoing = True
    if timeOffset == "":
        while keepGoing:
            timeOffset = raw_input("Indicate the starting time: \nFormat : hh:mm:ss.ms\n")
            if len(timeOffset) == 10 and timeOffset[2] == ':' and timeOffset[5] == ':' and timeOffset[8] == '.':
                keepGoing = False
                something = raw_input("Would you like to indicate Unix date and time?\n(y/n)\n")
                if something == 'y':
                    keepGoing1 = True
                    while keepGoing1:
                        date = raw_input("Indicate the Unix Date:\nFormat : yyyy-mm-dd\n")
                        if len(date) == 10 and date[4] == "-" and date[7] == "-":
                            keepGoing1 = False
                            keepGoing2 = True
                            while keepGoing2:
                                time = raw_input("Indicate the time of day: \nFormat : hh:mm:ss.ms\n")
                                if len(time) == 10 and time[2] == ':' and time[5] == ':' and time[8] == '.':
                                    keepGoing2 = False
                                else:
                                    print ("\nIncorrect format")
                        else:
                            print ("\nIncorrect format")
                else:
                    date = "1970-01-01"
                    time = "00:00:00.0"
            else:
                print ("\nIncorrect format")
    else:
        something = raw_input("Would you like to indicate Unix date and time?\n(y/n)\n")
        if something == 'y':
            keepGoing1 = True
            while keepGoing1:
                date = raw_input("Indicate the Unix Date:\nFormat : yyyy-mm-dd\n")
                if len(date) == 10 and date[4] == "-" and date[7] == "-":
                    keepGoing1 = False
                    keepGoing2 = True
                    while keepGoing2:
                        time = raw_input("Indicate the time of day: \nFormat : hh:mm:ss.ms\n")
                        if len(time) == 10 and time[2] == ':' and time[5] == ':' and time[8] == '.':
                            keepGoing2 = False
                        else:
                            print ("\nIncorrect format")
                else:
                    print ("\nIncorrect format")
        else:
            date = "1970-01-01"
            time = "00:00:00.0"
    return timeOffset,date,time
       
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
        
def findAnnotations(filename,tier):
    s = open('txt/{}'.format(filename), 'r')
    x = s.readline().split("\t")
    a = []
    while len(x) != 1:
        tempElement = x[0]
        answer = False
        if tier == tempElement and len(x) == 9:
            for element in a:
                if element == x[-1][:-1]:
                    answer = True
            if answer == False:
                a.append(x[-1][:-1])
        x = s.readline().split("\t")
        
    s.close()
    return a
    
def createDFTime(start,end,delta,unixTime,offset):
    start = stringToTime(start)
    end = stringToTime(end)
    start = roundTime(start)
    end = roundTime(end)
    current = start
    df = pd.DataFrame(columns=['Time'])
    a = []
    offsetSeconds = computeSecondOffset(offset)
    offsetSeconds = timedelta(seconds = offsetSeconds)
    while current <= end:
        temp = current - offsetSeconds
        a.append(unix(stringConverter(temp))+unixTime) 
        #a.append(stringConverter(current))
        #stringConverter(current) for time representations
        current += delta
    
    df['Time'] = a
    return df

def createDFUnix(start,end,delta,unixTime,offset,action = ""):
    start = stringToTime(start)
    end = stringToTime(end)
    start = roundTime(start)
    end = roundTime(end)
    current = start
    df = pd.DataFrame(columns=['Time','Action'])
    a = []
    offsetSeconds = computeSecondOffset(offset)
    offsetSeconds = timedelta(seconds = offsetSeconds)
    while current <= end:
        temp = current - offsetSeconds
        a.append(unix(stringConverter(temp))+unixTime) 
        a.append(stringConverter(current))
        #stringConverter(current) for time representations
        current += delta
    
    df['Time'] = a
    df['Action'] = action
    return df

#Takes in string '00:00:00' and computes total amount of seconds (including microseconds) into float form
def computeSecondOffset(offset):
    offset2 = float(offset[-2:])
    offset = time.strptime(offset.split('.')[0],'%H:%M:%S')
    offsetSeconds = datetime.timedelta(hours=offset.tm_hour,minutes=offset.tm_min,seconds=offset.tm_sec).total_seconds()
    return offsetSeconds + offset2


def findBeginning(filename):
    s = open('txt/{}'.format(filename), 'r')
    x = s.readline().split("\t")
    
    start = ""
    
    while len(x) != 1:
        tempElement = x[0]
        if tempElement == "Start":
            start = x[2][-12:]
            start = stringToTime(start)
            start = roundTime(start)
            start = stringConverter(start)
            break
            
        x = s.readline().split("\t")
       
    s.close()
    return start
       
       
       
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
    
    
def unix(timeStamp,dateStamp="1970-01-01"):
    a = datetime.datetime.strptime(dateStamp,'%Y-%m-%d').date()
    #dateUnix = mktime(a.timetuple()) for local timezone
    dateUnix = calendar.timegm(a.timetuple()) #for UTC timezone
    x = time.strptime(timeStamp.split('.')[0],'%H:%M:%S')
    timeUnix = datetime.timedelta(hours=x.tm_hour,minutes=x.tm_min,seconds=x.tm_sec).total_seconds()
    timeUnix2 = float(timeStamp[-2:])
    return dateUnix+timeUnix+timeUnix2
    
    
def labelAnnotations(tierName,dataframe):
    temp = dataframe
    
    uniqueValues = np.unique(temp[['Action']])
    
    counter = 0
    index = 0
    for m in uniqueValues:
        m = str(m)
        if m == 'nan':
            index = counter
        counter = counter + 1
        
    uniqueValues = np.delete(uniqueValues, index)
    
    print uniqueValues
    
    df = pd.DataFrame(columns=uniqueValues)
    zero = np.zeros(len(temp), dtype=np.int)
   
    for element in uniqueValues:
        df[element] = zero
        
    sa = temp.join(df,how="left")
    
    action = temp['Action']
    
    
    for x in range(0,len(temp)):
        if action[x] in uniqueValues[:]:
            print "Changing index: ", x
            sa[action[x]].iloc[x] = 1
            
    sa.to_csv("csv/{}.csv".format(tierName))


practice('P1_e20160630_174419_013088.txt')

#labelAnnotations("csv/Comments.csv")
