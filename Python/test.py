#test.py
#Nitin Shyamkumar
#Last Updated: 11.16.13

from InfiniteResistors import *
from constants import *

"""This module is a simple script to test the system in
InfiniteResistors.py with a 10 by 10 grid. 
The source position is in the middle (5,5) and
the drain position is at the point (0,0)"""

def mainTest(): 
    g = Grid(GRID_WIDTH,GRID_HEIGHT,drain_pos=DRAIN_POS,source_pos=SOURCE_POS)
    g.Equalize()
    table = g.getPotentialMap()
    #return table
    print 'The potentials of the grid have been calculated.'

    for ii in range(len(table)):
        print "Printing Row "+str(ii)
        for jj in range(len(table[0])):
            print table[ii][jj]
        raw_input('Hit Enter to get the next row')

if __name__ == '__main__':
    mainTest()

