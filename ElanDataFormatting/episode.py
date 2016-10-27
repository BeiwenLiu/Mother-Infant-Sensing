# -*- coding: utf-8 -*-
"""
Created on Sun Sep 25 03:09:01 2016

@author: Beiwen Liu
@description: Given original data file.txt, will create an episode file with given time gaps.
"""

import numpy as np
import pandas as pd
import datetime as datetime
from datetime import date, timedelta
from time import mktime
import time
import matplotlib.pyplot as plt
import collections
import os

#creates the episodes given a text file associated with tier
FILE_NAME = 'p6 pre_ e20160718_142108_013089.txt'
              
        
def histogram():
    filename = FILE_NAME
    s = open('txt/{}'.format(filename), 'r')
    x = s.readline().split("\t")
    sa = pd.DataFrame(columns=['Action','Start','End','Duration'])
    
    
    tier = raw_input("Select a tier: {}\n".format(findTiers(filename)))
    global FILE_DENSITY
    FILE_DENSITY = '{}{}.csv'.format(FILE_NAME[:-4],tier)
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
            print x
            if keepGoing:
                labelAnnotations(tier,sa)
                keepGoing = False
            
        x = s.readline().split("\t")
        
    if keepGoing:
        labelAnnotations(tier,sa)
        keepGoing = False
  
    
def labelAnnotations(tierName,dataframe):
    temp = dataframe
    uniqueValues = np.unique(temp[['Action']])
    
    end = temp['End'].iloc[-1]
    
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
    
    sa.to_csv("aaasfasf.csv")
    sel = raw_input("Select one or multiple episodes - (1,2,5,10,20) minutes\n")
    sel = sel.split(",")
    episodeArray = []
    for s in sel:
        episode = makeEpisode(sa, s)
        episodeArray.append(episode)
        
    createcsv = raw_input("Would you like to export to csv or create density?\n1) TimeSeries CSV\n2) density\n3) both\n4) Episode CSV\n")
    if createcsv == '1':
        createCSV(episodeArray, end, sel, annotation)
    elif createcsv == '2':
        densityann = raw_input("Please select an annotation: {}\n".format(uniqueValues))
        density(episodeArray, densityann, sel)
    elif createcsv =='3':
        densityann = raw_input("Please select an annotation: {}\n".format(uniqueValues))
        createCSV(episodeArray, end, sel, annotation)
        density(episodeArray, densityann, sel)
    elif createcsv == '4':
        exportEpisodeToCSV(episodeArray, sel)
        
        
        
def exportEpisodeToCSV(episode, sel):
    print sel[0]
    df = pd.DataFrame(columns=['Begin Time','End Time'])
    beginTime = []
    endTime = []
    for e in episode:
        for ef in e:
            beginTime.append(str(ef))
            endTime.append(str(e[ef]))
            
    df['Begin Time'] = beginTime
    df['End Time'] = endTime
    df['Annotation'] = '{} Minute Episode'.format(sel[0])
    
    df.to_csv("testing.csv")
        
    
            
def makeEpisode(sa, s):
    input1 = float(s) * float(60)
    print input1
    row1 = sa['Start']
    row2 = sa['End']
    sa.to_csv("something.csv")
    found = False
    keyHolder = ''
    answer = []
    answer.append(0)
    episode = collections.OrderedDict()
    for number in range(len(row1) - 1):
        temp = stringToTime(row1[number + 1]) - stringToTime(row2[number])
        if float(temp) < input1:
            if found:
                episode[keyHolder] = str(row2[number + 1])
            else:
                keyHolder = str(row1[number])
                episode[keyHolder] = str(row2[number + 1])
                found = True
        else:
            found = False
    return episode
   
   
def createCSV(episode, end, sel, annotation):
    df = pd.DataFrame(columns=['Time'])
    newRange = np.arange(0,roundTime(stringToTimeOrig(end)), .1)
    
    for num in range(len(newRange)):
        newRange[num] = str(newRange[num])
    df['Time'] = newRange
    
    counter = 0
    for episodeElement in episode:
        df2 = pd.DataFrame(columns=['Time','{} Minute Episode'.format(sel[counter])])
        allRange = np.array([0])
        for element in episodeElement:
            tempRange = np.arange(roundTime(stringToTimeOrig(element[:-1])),roundTime(stringToTimeOrig(episodeElement[element][:-1])),.1)
            allRange = np.concatenate((allRange, tempRange))
    
        for num2 in range(len(allRange)):
            sp = str(round(allRange[num2],1))
            allRange[num2] = sp
        
        
        df2['Time'] = allRange[1:]
        df2['{} Minute Episode'.format(sel[counter])] = counter + 2
        counter = counter + 1
        df = df.merge(df2,on="Time", how="left")
     
    directory = 'csv/{}'.format(FILE_NAME[:-4])
    if not os.path.exists(directory):
        os.makedirs(directory) 
    df.to_csv("csv/{}/{}{}episode.csv".format(FILE_NAME[:-4],FILE_NAME[:-4],annotation))
    
#This will create the csv #-minute density csv
def density(episode, annotation, sel):
    columnNames = []
    columnNames.append('Category')
    columnNames.append('Begin Time')
    columnNames.append('End Time')
    columnNames.append('{} duration'.format(annotation))
    columnNames.append('{} density'.format(annotation))
    columnNames.append('{} max duration'.format(annotation))
    columnNames.append('{} mean duration'.format(annotation))
    df = pd.DataFrame(columns=columnNames)
    fileDest = FILE_NAME[:-4]
    totalindexvalues = returnPandasIndex()
    tempDf = getDataFrame(annotation)
    for minepisode in episode:
        beginTime = []
        endTime = []
        duration = []
        density = []
        maxDuration = []
        meanDuration = []
        for key in minepisode:
            start = key
            end = minepisode[key]
            beginTime.append(str(start))
            endTime.append(str(end))
            totalduration = str(stringToTime(end) - stringToTime(start))
            start,end = defineTime(start,end)
            start = np.where(totalindexvalues == start)[0][0]
            end = np.where(totalindexvalues == end)[0][0]
            dura = findDuration(tempDf,start,end, annotation)
            ma = findMax(tempDf,start,end,annotation)
            me = findMean(tempDf,start,end,annotation)
            duration.append(dura)
            maxDuration.append(ma)
            meanDuration.append(me)
            density.append("{0:.2f}".format(dura/float(totalduration)))
        
        #print timeRange, duration, density
        df['Begin Time'] = beginTime
        df['End Time'] = endTime
        df['{} duration'.format(annotation)] = duration
        df['{} density'.format(annotation)] = density
        df['{} max duration'.format(annotation)] = maxDuration
        df['{} mean duration'.format(annotation)] = meanDuration
        
    df.to_csv("csv/{}/{}_{}_Min_Density.csv".format(fileDest,fileDest,sel[0]))
    
#finds total duration of crying occurrences in an episode
def findDuration(dataframe, start, end, annotation):
    listtemp = dataframe.ix[start:end, annotation]
    total = 0
    print listtemp
    for element in listtemp:
        if element == 1:
            total = total + 1
            
    number = total * .1
    return number

#finds the max duration of crying occurrence in an episode
def findMax(dataframe, start, end, annotation):
    listtemp = dataframe.ix[start:end, annotation]
    total = 0
    maxDuration = 0
    compare = False
    for element in listtemp:
        if element == 1:
            total = total + 1
            compare = True
        elif element == 0 and compare:
            compare = False
            if total > maxDuration:
                maxDuration = total
            total = 0
    if compare:
        if total > maxDuration:
            maxDuration = total
            
    maxDuration = maxDuration * .1
    return maxDuration
    
#finds the mean duration of crying occurrence in an episode
def findMean(dataframe, start, end, annotation):
    listtemp = dataframe.ix[start:end, annotation]
    meanList = []
    total = 0
    compare = False
    for element in listtemp:
        if element == 1:
            total = total + 1
            compare = True
        elif element == 0 and compare:
            compare = False
            meanList.append(total)
            total = 0
    if compare:
        meanList.append(total)
            
    mean = sum(meanList)/len(meanList) * .1
    return mean
    
def getDataFrame(annotation):
    return pd.read_csv('csv/{}/{}'.format(FILE_NAME[:-4],FILE_DENSITY), usecols=[annotation])
    
def returnPandasIndex():
    df = pd.read_csv('csv/{}/{}'.format(FILE_NAME[:-4],FILE_DENSITY), index_col='Time')
    
    return df.index.values
    
def defineTime(start,end):
    start = roundTime(stringToTimeOrig(start))
    end = roundTime(stringToTimeOrig(end))
    return start,end
    
    
    
#Use this when converting directly
def stringToTime(arg):
    offset2 = float(arg[-4:])
    x = time.strptime(arg.split('.')[0],'%H:%M:%S')
    total = datetime.timedelta(hours=x.tm_hour,minutes=x.tm_min,seconds=x.tm_sec).total_seconds()
    return offset2+total
    
#For roundTime since decimal places are already removed
def stringToTime2(arg):
    x = time.strptime(arg.split('.')[0],'%H:%M:%S')
    total = datetime.timedelta(hours=x.tm_hour,minutes=x.tm_min,seconds=x.tm_sec).total_seconds()
    return total

def roundTime(time):
    x = timedelta(microseconds = 100000)
    ans = int(time.strftime('%f')[-5])
    if (ans >= 5):
        time = time + x
        
    time = stringConverter(time)
    time = stringToTime2(time)
    
    return time

#Give input of String "00:00:00.00" and it will return type datetime
def stringToTimeOrig(arg):
    return datetime.datetime.strptime(arg, '%H:%M:%S.%f')
    
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
    
histogram()
