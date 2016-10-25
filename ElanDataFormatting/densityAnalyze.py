# -*- coding: utf-8 -*-
"""
Created on Mon Oct 24 13:07:56 2016

@author: Beiwen Liu
"""
import os
import os.path

import pandas as pd
import numpy as np

import datetime as datetime
from datetime import date, timedelta
from time import mktime
import time

#Search for all files within csv/categories
def pregenerate():
    
    
    yesNo = raw_input("1) Process\n2) Statistics\n")
    
    
    df = pd.DataFrame(columns=['Category','Duration','Density'])
    df2 = pd.DataFrame(columns=['Participant','Total Duration (Seconds)','Duration Mean', 'Duration Median', 'Duration Standard Dev', 'Total Occurring Episodes','Total Categorized Episodes', 'Total Categorized Episodes Duration Mean', 'Total Categorized Episodes Duration Max', 'Total Categorized Episodes Duration Median'])
    
    counter = 0
    for file in os.listdir("csv/categories"):
        if file.endswith("1_Min_Density.csv"):
            if (yesNo == "1"):
                tempDf = process(file,counter) #Only for analyzing category annotated rows
                df = pd.concat([df,tempDf],ignore_index=True)
            elif (yesNo == "2"):
                tempDf = processFile(file)
                df2 = pd.concat([df2,tempDf],ignore_index=True)
            
    if (yesNo == "1"):      
        df.to_csv("csv/categories/compile.csv")
    elif (yesNo == "2"):
        df2.to_csv("csv/categories/{}stats.csv".format("stats.csv"))
    
#Find mean, median, and std for count and duration of episode per 16 hour recording
def processFile(filename):
    s = open("csv/categories/{}".format(filename), 'r')
    dfCat = pd.DataFrame(columns=['Duration1'])
    df = pd.DataFrame(columns=['Duration'])
    x = s.readline().split(",")
    counter = 1
    participant = filename
    duration = []
    duration1 = []
    count = 0
    y = s.readline().split(",")
    while len(y) != 1:
        if (y[1] != ""):
            count = count + 1
            duration1.append(float(y[-2]))
        duration.append(stringToTime(y[3]) - stringToTime(y[2]))
        
        counter = counter + 1
        y = s.readline().split(",")
    df['Duration'] = duration
    dfCat['Duration1'] = duration1
        
    print "hey"
    df2 = pd.DataFrame(columns=['Participant','Total Duration (Seconds)','Duration Mean', 'Duration Median', 'Duration Standard Dev', 'Total Occurring Episodes','Total Categorized Episodes', 'Total Categorized Episodes Duration Mean', 'Total Categorized Episodes Duration Max', 'Total Categorized Episodes Duration Median'])
    df2['Participant'] = [participant]
    print df2['Participant']
    df2['Total Duration (Seconds)'] = [df['Duration'].sum()]
    df2['Duration Mean'] = [df['Duration'].mean()]
    df2['Duration Median'] = [df['Duration'].median()]
    df2['Duration Standard Dev'] = [df['Duration'].std()]
    df2['Total Occurring Episodes'] = [counter]
    df2['Total Categorized Episodes'] = [count]
    df2['Total Categorized Episodes Duration Mean'] = [dfCat['Duration1'].mean()]
    df2['Total Categorized Episodes Duration Max'] = [dfCat['Duration1'].max()]
    df2['Total Categorized Episodes Duration Median'] = [dfCat['Duration1'].median()]
    return df2
    
#Find all occurrences where category is labeled yes or no and store in dataframe
def process(filename, counter): 
    print counter
    df = pd.DataFrame(columns=['Category','Duration','Density'])
    categories=[]
    duration=[]
    density=[]
    s = open("csv/categories/{}".format(filename), 'r')
    x = s.readline().split(",")
    
    y = s.readline().split(",")
    while len(y) != 1:
        if y[1] != "":
            categories.append(y[1])
            duration.append(float(y[-2]))
            density.append(float(y[-1][:-1]))
        y = s.readline().split(",")
    df['Category'] = categories
    df['Duration'] = duration
    df['Density'] = density
    return df
    
def execute():
    yes = np.zeros(11)
    detected = np.zeros(11)
    df = pd.DataFrame(columns=['Density','Category'])
    if os.path.isfile("csv/categories/compile.csv"):
        s = open("csv/categories/compile.csv", 'r')
        x = s.readline().split(",")
        x = s.readline().split(",")
    yesNo = raw_input("Would you like to specify duration constraint?\n(y/n)\n")
    if yesNo == "y":
        constraint = raw_input("Please provide a constraint\n")
        if yesNo:
            while len(x) != 1:
                ans = int(10*round(float(x[3]),1))
                if (x[1] == "yes") and (float(constraint) <= float(x[2])):
                    print x[2]
                    yes[ans] = yes[ans] + 1
                if (float(constraint) <= float(x[2])):
                    detected[ans] = detected[ans] + 1
                x = s.readline().split(",")
            graph(yes, detected)
        else:
            while len(x) != 1:
                ans = int(10*round(float(x[3]),1))
                if (x[1] == "yes"):
                    print x[2]
                    yes[ans] = yes[ans] + 1
                    detected[ans] = detected[ans] + 1
                x = s.readline().split(",")
            graph(yes, detected)
        
def graph(yes, detected):
    xVals = [0,.1,.2,.3,.4,.5,.6,.7,.8,.9,1]
    print yes
    print detected
    density = yes/detected
    df = pd.DataFrame(columns=['xVals','Density'])
    print xVals
    print density
    df['xVals'] = xVals
    df['Density'] = density
    df = df.set_index(['xVals'])
    ax = df['Density'].plot()
    ax.set_ylim(0,1.2)
    
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