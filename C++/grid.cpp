// grid.cpp
// Nitin Shyamkumar (github.com/nitinshyamk)
// 12.20.13

#include <stdio.h>
#include "node.h"
#include <iostream>

//THE FOLLOWING ARE PUBLIC METHODS
Grid::Grid(void)
{
    setSourceArray(0,0);
    setDrainArray(20,20);
}
//void Grid::equalizePotential(void);

void Grid::printAddresses(void)
//prints all the pointer addresses for the Grid
{
    for (int i = 0; i<20; i++){
        for (int j = 0; j<20; j++){
            std::cout << &nodeArray[i][j]<<"\n";
        }
    }
}

void Grid::setSource(int xpos, int ypos)
//sets a node at (xpos,ypos) as the source, resets the previous source, and stores the previous source
{
    nodeArray[xpos][ypos].setNodeAsSource();
    nodeArray[sourceArray[0]][sourceArray[1]].setNodeAsNormal();
    setSourceArray(xpos,ypos);
}

void Grid::setDrain(int xpos, int ypos)
//sets a node at (xpos,ypos) as the drain, resets the previous drain, and stores the current drain
{
    nodeArray[xpos][ypos].setNodeAsDrain();
    nodeArray[drainArray[0]][drainArray[1]].setNodeAsNormal();
    setDrainArray(xpos,ypos);
}


//THE FOLLOWING ARE PRIVATE METHODS
/* simple 2 line methods that hide the manipulation of the markers for the source and 
the drains*/  
void Grid::setSourceArray(int xpos, int ypos)
{
    sourceArray[0] = xpos;
    sourceArray[1] = ypos;
}

void Grid::setDrainArray(int xpos, int ypos)
{
    drainArray[0] = xpos;
    drainArray[1] = ypos;
}

int Grid::findNeighbors(int xpos, int ypos)
{
    return 0;
}
