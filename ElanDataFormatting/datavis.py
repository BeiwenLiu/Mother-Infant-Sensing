# -*- coding: utf-8 -*-
"""
Created on Sat Sep 17 00:31:28 2016

@author: user
"""

import numpy as np
import pandas as pd
import datetime as datetime
from datetime import date, timedelta
from time import mktime
import time
import matplotlib.pyplot as plt

#Indicate Tier Here
FILE_NAME = "/p2_pre _e20160630_175407_013089/p2_pre _e20160630_175407_013089*CHN.csv"

#Indicate Episode here
FILE_EPISODE = "/P1_e20160630_174419_013088/P1_e20160630_174419_013088&=cryingepisode.csv"

#Indicate Text file here for histogram
FILE_HISTOGRAM = "p6 pre_ e20160718_142108_013089.txt"

def start():
    filen = raw_input("Which file would you like to graph?\n1) Section Graph\n2) Regular Graph\n3) Zoomed in Graph\n4) Histogram\n")
    yesEpisode = raw_input("Would you like to include an episode?(y/n)\n")    
    if filen == '1':
        graph(FILE_NAME,yesEpisode)
    elif filen == '2':
        graph2(FILE_NAME,yesEpisode)
    elif filen == '3':
        graph3(FILE_NAME,yesEpisode)
    elif filen == '4':
        histogram(FILE_HISTOGRAM)
        
def graph(filename,yesEpisode):
    
    
    df = pd.read_csv('csv/{}'.format(filename),index_col='Time')
    
    uniqueValues = np.unique(df[['Action']])

    if len(uniqueValues) > 1:
        uniqueValues = uniqueValues[1:]
        
    
    answer = raw_input("Would you like to select an annotation? (y/n)\n")
    if answer == 'y':
        answer = True
        annotation = raw_input("Please select one or more (separate with commas):\n{}\n".format(uniqueValues))
        annotationList = annotation.split(",")
    else: answer = False
    
    tempDf = pd.DataFrame(columns=[uniqueValues])
    for element in uniqueValues:
        tempDf[element] = [0]
        
    
    indicies = df.index.values.tolist()
    indexIncrement = indicies[-1] + .1
    index = indicies[-1] + 1
    
    tempDf = tempDf.set_index([[indexIncrement]])
    tempDf.index.name = "Time"
    
    
    df = df[uniqueValues]
    df = df.append(tempDf)
    
    
    number = len(df[uniqueValues])
    
    indexVal = df.index.values[-1] / 8
    print indexVal
    
    plt.figure(1)
    for x in range(1,9):
        plt.subplot(4,2,x)
        if answer:
            ax = df[annotationList][0:indexVal*x].plot(ax=plt.gca(), title="Graph %d" % x, legend=False)
        else:
            ax = df[uniqueValues][0:indexVal*x].plot(ax=plt.gca(), title="Graph %d" % x, legend=False)
        ax.set_xlabel("")
        ax.set_ylim(0,2)
        ax.set_xlim(indexVal*(x-1),indexVal*x)

        
    plt.subplots_adjust(hspace=.7)
    plt.suptitle("{} plot".format(filename))
    plt.legend(loc='upper center', bbox_to_anchor=(0,0))
    plt.show()
    
def graph2(filename,yesEpisode):
    
    df = pd.read_csv('csv/{}'.format(filename),index_col='Time')
    
    uniqueValues = np.unique(df[['Action']])
    if len(uniqueValues) > 1:
        uniqueValues = uniqueValues[1:]
        
        
    
    answer = raw_input("Would you like to select an annotation? (y/n)\n")
    if answer == 'y':
        answer = True
        annotation = raw_input("Please select one or more (separate with commas):\n{}\n".format(uniqueValues))
        annotationList = annotation.split(",")
    else: answer = False
    

    
    tempDf = pd.DataFrame(columns=[uniqueValues])
    for element in uniqueValues:
        tempDf[element] = [0]
        
    
    indicies = df.index.values.tolist()
    indexIncrement = indicies[-1] + .1
    index = indicies[-1] + 1
    
    tempDf = tempDf.set_index([[indexIncrement]])
    tempDf.index.name = "Time"
    
    df = df[uniqueValues]
    
    df = df.append(tempDf)
    
    if yesEpisode == 'y':
        newDf = pd.read_csv('csv/{}'.format(FILE_EPISODE), index_col="Time")
        newDfColumns = list(newDf.columns.values)
        if newDfColumns[0] == 'Unnamed: 0':
            newDfColumns = newDfColumns[1:]
            
        df = pd.merge(df, newDf[newDfColumns], left_index=True, right_index=True, how="left")
                
    
    df.to_csv("aaaa.csv")
    yindex = 2
    if answer:
        if yesEpisode == 'y':
            yindex = len(newDfColumns) + 4
            annotationList = annotationList + newDfColumns
        ax = df[annotationList].plot(title="{}".format(filename[:-4]))
    else:
        if yesEpisode == 'y':
            yindex = len(newDfColumns) + 4
            uniqueValues = uniqueValues + newDfColumns
        ax = df[uniqueValues].plot(title="{}".format(filename[:-4]))
    
        
    ax.set_ylim(0,yindex)
    ax.set_xlim(0,index)
    plt.legend(loc='upper center', mode="expand")
    plt.show()
    
def graph3(filename,yesEpisode):
    
    df = pd.read_csv('csv/{}'.format(filename),index_col='Time')
    
    uniqueValues = np.unique(df[['Action']])
    if len(uniqueValues) > 1:
        uniqueValues = uniqueValues[1:]
        
        
    
    answer = raw_input("Would you like to select an annotation? (y/n)\n")
    if answer == 'y':
        answer = True
        annotation = raw_input("Please select one or more (separate with commas):\n{}\n".format(uniqueValues))
        annotationList = annotation.split(",")
    else: answer = False
    

    
    tempDf = pd.DataFrame(columns=[uniqueValues])
    for element in uniqueValues:
        tempDf[element] = [0]
        
    
    indicies = df.index.values.tolist()
    indexIncrement = indicies[-1] + .1
    index = indicies[-1] + 1
    
    tempDf = tempDf.set_index([[indexIncrement]])
    tempDf.index.name = "Time"
    
    df = df[uniqueValues]
    
    df = df.append(tempDf)
    
    if answer:
        ax = df[annotationList][0:1000].plot()
        ax.legend()
    else:
        ax = df[uniqueValues].plot()
        
    ax.set_ylim(0,2)
    ax.set_xlim(0,1000)
    #ax.title("{}".format(filename))
    plt.show()
    
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
            duration = stringToTime(x[6][-12:])
            print start
            
            tempDf = createDF(action,start,end,duration)
            sa = pd.concat([sa,tempDf],ignore_index=True)
            keepGoing = True
        else:
            if keepGoing:
                labelAnnotations(tier,sa)
                keepGoing = False
            
        x = s.readline().split("\t")
    if keepGoing:
            labelAnnotations(tier,sa)
            keepGoing = False
    #timeDf = createDFTime(timeOffset,end,increment,unixTime,timeOffset)
    #sa = timeDf.merge(sa,on="Time",how="left")
  
    
def labelAnnotations(tierName,dataframe):
    temp = dataframe
    uniqueValues = np.unique(temp[['Action']])
    
    annotation = raw_input("Select an annotation: {}\n".format(uniqueValues))
    
    counter = 0
    index = 0
    
    temp = temp.loc[temp['Action'] == annotation]
    
    print temp
    
    sa = pd.DataFrame(columns=['Action','Start','End','Duration'])
    sa['Action'] = [annotation]
    sa['Start'] = ["00:00:00.000"]
    sa['End'] = ["00:00:00.000"]
    sa['Duration'] = [stringToTime("00:00:00.000")]
    
    sa = pd.concat([sa,temp],ignore_index=True)
    
    calculateTotal(sa)
    
    plotHistogram(tierName, sa)
    
    plt.show()
    
def plotHistogramWithNumpy(tierName, sa):
    # Create non-uniform bins.  Unit in seconds.
    bins = np.array([0, 1, 10, 60, 60*10, 60*60, 24*60*60])
    print 'hisogram bins:', bins
    
    # Get histogram of random data
    y, x = np.histogram(sa, bins=bins)
    
    # Correct bin placement
    x = x[1:]
    
    # Turn into pandas Series
    hist = pd.Series(y, x)
    
    # Plot
    ax = hist.plot(kind='bar', width=1, alpha=0.5, align='center')
    ax.set_title('Non-Uniform Bin Histogram')
    ax.set_xlabel('Time Length')
    ax.set_xticklabels(['1 s', '10 s', '1 Min', '1 Hr', '1 Day', '>1 Day'], rotation='horizontal')
        
        
def plotHistogram(tierName, sa):
    row1 = sa['Start']
    row2 = sa['End']
    answer = []
    answer.append(0)
    baseLog = 2
    times = 0
    for number in range(len(row1) - 1):
        answer.append(stringToTime(row1[number + 1]) - stringToTime(row2[number]))
        
    
    sa['Gap'] = answer
    sa['Gap'] = sa['Gap']/60
    print sa['Gap']
    maxSA = sa['Gap'].max()
    minSA = sa['Gap'].min()
    
    tempMax = 0
    while tempMax < maxSA:
        times = times + 1
        tempMax = baseLog**times
    print tempMax
    print maxSA
    print times
    
    binBoundaries = np.logspace(0, times, 50,base=baseLog)
    sa['Gap'].to_csv("sdfsdf.csv")
    ax = sa['Gap'].hist(bins=binBoundaries, facecolor='g')
    plt.xlabel('Time in Minutes')
    plt.ylabel('Occurences')
    plt.xlim(minSA,maxSA)
    plt.title('Histogram of gaps between occurences for {} in {}'.format(tierName, FILE_HISTOGRAM[:-4]))
    plt.show()
    
def calculateTotal(sa):
    row3 = sa['Duration']
    total = 0
    
    for number in range(len(row3)):
        total = total + row3[number]
        
    print total
    
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
    
def graphEpisode(filename):
    df = pd.read_csv('csv/{}'.format(filename),index_col='Time')
    dflist = list(df.columns.values)
    print dflist
    df[dflist[1:]].plot()
    plt.show()
    
#graph3("P1*CHN.csv")
#graph2("P1*CHN.csv")
#graphEpisode("P1&=crying histogram.csv")
#graph("P1*CHF.csv")
#histogram("P1.txt")
start()