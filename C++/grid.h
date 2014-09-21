// grid.h
// Nitin Shyamkumar (github.com/nitinshyamk)
// 12.20.13

#include <stdio.h>
#include "node.h"
#include <iostream>

#ifndef GRID_H
#define GRID_H


class Grid
{
public:
    Grid(void); // see constructor in grid.cpp
    void printAddresses(void);
    void equalizePotential(void);
    void setSource(int xpos, int ypos);
    void setDrain(int xpos, int ypos);
private:
    Node nodeArray[20][20];
    int sourceArray[2];
    int drainArray[2];
    int findNeighbors(int xpos, int ypos);
    void setSourceArray(int xpos, int ypos);
    void setDrainArray(int xpos, int ypos);

};

#endif