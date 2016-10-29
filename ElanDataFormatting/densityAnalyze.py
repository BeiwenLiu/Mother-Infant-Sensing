# -*- coding: utf-8 -*-
"""
Created on Mon Oct 24 13:07:56 2016

@author: Beiwen Liu
"""
import os
import os.path

import pandas as pd
import numpy as np

import matplotlib.pyplot as plt
import datetime as datetime
from datetime import date, timedelta
from time import mktime
import time

#Search for all files within csv/categories
def pregenerate():
    
    
    yesNo = raw_input("1) Process\n2) Statistics\n")
    maxValue = float(raw_input("Value please:\n"))
    
    df = pd.DataFrame(columns=['Category','Duration','Density', 'Max Duration', 'Mean Duration'])
    df2 = pd.DataFrame(columns=['Participant'])
    
    counter = 0
    for file in os.listdir("csv/categories"):
        if file.endswith("1_Min_Density.csv"):
            if (yesNo == "1"):
                tempDf = process(file,counter) #Only for analyzing category annotated rows
                df = pd.concat([df,tempDf],ignore_index=True)
            elif (yesNo == "2"):
                tempDf = processFile(file,maxValue)
                df2 = pd.concat([df2,tempDf],ignore_index=True)
            
    if (yesNo == "1"):      
        df.to_csv("csv/categories/compile.csv")
    elif (yesNo == "2"):
        someDf = findTotalDuration()
        df2 = df2.merge(someDf,how="left",left_index=True,right_index=True) #Merge original dataframe with the dataframe that contains the total duration of participant
        df2['% Episode Start to End / Total'] = df2['Episode (Start to End) Duration']/df2['Total Duration of Participant'] * 100
        df2['% Episode Analyzed / Total'] = df2['Total Duration - Actual Duration Analyzed']/df2['Total Duration of Participant'] * 100
        df2.to_csv("csv/categories/{}stats.csv".format("stats.csv"))
    
#Find mean, median, and std for count and duration of episode per 16 hour recording
def processFile(filename,maxValue):
    
    s = open("csv/categories/{}".format(filename), 'r')
    dfCat = pd.DataFrame(columns=['Duration1'])
    df = pd.DataFrame(columns=['Duration'])
    dfMax = pd.DataFrame(columns=['Duration'])
    x = s.readline().split(",")
    counter = 1
    participant = filename
    duration = [] #episode duration
    duration1 = [] #categorized episode total duration
    count = 0
    y = s.readline().split(",")
    foundStart1 = False
    foundStart = False
    start = stringToTime(y[2])
    maxCount = 0
    durationMax = []
    while len(y) != 1:
        if (y[1] != ""):
            count = count + 1
            duration1.append(stringToTime(y[3]) - stringToTime(y[2]))
            if float(y[6]) > maxValue:
                durationMax.append(stringToTime(y[3])-stringToTime(y[2]))
                maxCount = maxCount + 1
            if foundStart1 == False:
                start1 = stringToTime(y[2])
                foundStart1 = True
                
        if foundStart1:
            end1 = stringToTime(y[3])
        
        duration.append(stringToTime(y[3]) - stringToTime(y[2]))
        
        counter = counter + 1
        end = stringToTime(y[3])
        y = s.readline().split(",")
    
    df['Duration'] = duration
    dfCat['Duration1'] = duration1
    dfMax['Duration'] = durationMax

    df2 = pd.DataFrame(columns=['Participant'])
    df2['Participant'] = [participant]
    df2['Total Duration - Actual Duration Analyzed'] = [df['Duration'].sum()]
    df2['Total Occurring Episodes'] = [counter]
    df2['Total Categorized Episodes'] = [count]
    df2['Total Categorized Episodes Duration Sum - Actual Duration Analyzed'] = [dfCat['Duration1'].sum()]
    df2['Episode (Start to End) Duration'] = [end-start]
    df2['Categorized Episode (Start to End) Duration'] = [end1-start1]
    df2['Episode Count above Max Threshold'] = maxCount
    df2['Episode Duration above Max Threshold - Actual Duration Analyzed'] = [dfMax['Duration'].sum()]
    return df2
    
#iterate through all text files to find total duration of participant
def findTotalDuration():
    df = pd.DataFrame(columns=['Total Duration of Participant'])
    totalDuration = []
    for file in os.listdir("txt/participants"):
        if file.endswith("txt"):
            totalDuration.append(findTxt(file))
    df['Total Duration of Participant'] = totalDuration
    return df
def findTxt(filename):
    s = open("txt/participants/{}".format(filename), "r")

    y = s.readline().split("\t")
    start = stringToTime(y[2])
    while len(y) != 1:
        end = stringToTime(y[4])
        y = s.readline().split("\t")
    return end-start
    
#Find all occurrences where category is labeled yes or no and store in dataframe
def process(filename, counter): 
    print counter
    df = pd.DataFrame(columns=['Category','Duration','Density','Max Duration', 'Mean Duration'])
    categories=[]
    duration=[]
    density=[]
    meanDuration = []
    maxDuration = []
    s = open("csv/categories/{}".format(filename), 'r')
    x = s.readline().split(",")
    
    y = s.readline().split(",")
    while len(y) != 1:
        if y[1] != "":
            categories.append(y[1])
            duration.append(float(y[-4]))
            density.append(float(y[-3]))
            maxDuration.append(float(y[-2]))
            meanDuration.append(float(y[-1]))
        y = s.readline().split(",")
    df['Category'] = categories
    df['Duration'] = duration
    df['Density'] = density
    df['Max Duration'] = maxDuration
    df['Mean Duration'] = meanDuration
    return df
    
def execute():
    yes = np.zeros(50)
    detected = np.zeros(50)
    df = pd.DataFrame(columns=['Density','Category'])
    if os.path.isfile("csv/categories/compile.csv"):
        s = open("csv/categories/compile.csv", 'r')
        x = s.readline().split(",")
        x = s.readline().split(",")
    yesNo = raw_input("Would you like to specify duration constraint?\n(y/n)\n")
    if yesNo == "y":
        mode = raw_input("Set your x axis:\n1) Density\n2) Max Duration\n3) Mean Duration\n")
        if yesNo:
            constraint = raw_input("Please provide a constraint\n").split(",")
            for som in constraint:
                
                while len(x) != 1:
                    if mode == "1":
                        ans = int(10*round(float(x[3]),1))
                    elif mode == "2":
                        ans = int(round(float(x[4])))
                    elif mode == "3":
                        ans = int(round(float(x[5])))
                    if (x[1] == "yes") and (float(som) <= float(x[2])):
                        yes[ans] = yes[ans] + 1
                    if (float(som) <= float(x[2])):
                        detected[ans] = detected[ans] + 1
                    x = s.readline().split(",")
                graph(yes, detected, mode)
        else:
            while len(x) != 1:
                ans = int(10*round(float(x[3]),1))
                if (x[1] == "yes"):
                    yes[ans] = yes[ans] + 1
                    detected[ans] = detected[ans] + 1
                x = s.readline().split(",")
            graph(yes, detected, mode)
        
def graph(yes, detected, mode):
    if (mode == "1"): #density
        xVals = []
        for x in range(0,50,1):
            xVals.append(x*.1)
    elif (mode == "2" or mode == "3"): #mean or max
        xVals = []
        for x in range(0,50,1):
            xVals.append(x)
        
    density = yes/detected
    df = pd.DataFrame(columns=['xVals','Density','detected'])
    df['detected']= detected
    df['xVals'] = xVals
    df['Density'] = density
    df = df.set_index(['xVals'])
    df=df.dropna()
    #find size of array and iterate with scatter while changing size of s
    ax = df['Density'].plot()
    dfDens = df['Density'].values
    dfIndex = df.index.values
    dfdetected = df['detected'].values

    print df
    
    ax.set_ylim(0,1.2)
    ax.set_xlim(-(dfIndex[1]-dfIndex[0]),dfIndex.max())
    for cnt in range(0,len(dfdetected)): #Plot marker here!
        plt.scatter(float(dfIndex[cnt]), float(dfDens[cnt]),# x, y, marker size, color, marker type
                   s = 100* float(dfdetected[cnt]), 
                    c = 1, 
                    marker = "o")
    
def stringToTime(arg):
    offset2 = float(arg[-4:])
    x = time.strptime(arg.split('.')[0],'%H:%M:%S')
    total = datetime.timedelta(hours=x.tm_hour,minutes=x.tm_min,seconds=x.tm_sec).total_seconds()
    return offset2+total
#use this when you want to compile all files into compile.csv
#or generate files with statstics
#pregenerate()

#use execute when you have already compiled all the files into one category file called compile.csv          
execute()