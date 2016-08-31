# -*- coding: utf-8 -*-
"""
Created on Tue Aug 30 21:36:31 2016

@author: MacbookRetina
"""

import pandas as pd
import datetime as datetime
from datetime import date, timedelta


def parse():
    df = pd.read_html('testing2.html')
    number = len(df[0][1])
    
    for x in range(0,number,4):
        action = df[0][1][x + 1]
        timestamp = df[0][1][x+2]
        start,end = timestamp.split(' - ')
        print 'Initializing conversion'
        convert(start,end,timedelta(microseconds=100000))
    
def convert(start,end,delta):
    
    start = datetime.datetime.strptime(start, '%H:%M:%S.%f')
    end = datetime.datetime.strptime(end, '%H:%M:%S.%f')
    current = start
    while current < end:
        current += delta
        print current
    
parse()
    
    
    