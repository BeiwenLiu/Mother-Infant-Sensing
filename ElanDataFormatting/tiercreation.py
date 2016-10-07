# -*- coding: utf-8 -*-
"""
Created on Fri Sep 30 19:36:03 2016

@author: MacbookRetina
@description: Creates separate files for each tier
"""

#File must be in multipleTiers directory

FILE_NAME = "UCL_ CHN output _Beiwen3.txt"

def parse(filename = FILE_NAME):
    s = open('multipletiers/{}'.format(filename), 'r')
    
    x = s.readline()
    y = x.split('\t')
    z = y[9]
    z1 = z[:-5]
    counter2 = 0
    counter = 0
    array = []
    array.append(z1)
    filewrite = open('txt/{}.txt'.format(z1), 'w')
    
    while len(x) != 0:
        y = x.split('\t')
        z = y[9]
        z1 = z[:-5]
        if z1 != array[counter]:
            counter = counter + 1
            array.append(z1)
            filewrite = open('txt/{}.txt'.format(z1), 'w')
            
        x = x.replace('{}'.format(z), "").rstrip()
        
        re = x.split('\t')
        if len(re) == 8:
            x = x+"\t"
        
        filewrite.write('{}\n'.format(x))
        counter2 = counter2 + 1
        x = s.readline()
        print counter2
        counter2 = counter2 + 1
        
    print "done"
        
    
    
    
parse()