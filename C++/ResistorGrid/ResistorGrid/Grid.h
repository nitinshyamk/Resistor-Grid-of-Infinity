//
//  Grid.h
//  ResistorGrid
//
//  Created by Nitin Shyamkumar on 5/19/14.
//  Copyright (c) 2014 Nitin Shyamkumar. All rights reserved.
//

#ifndef __ResistorGrid__Grid__
#define __ResistorGrid__Grid__

#include <iostream>
#include <vector>


#include "Position.h"
#include "Node.h"
#include <iostream>
#include <fstream>

class Grid{
public:
    Grid(int grid_size); // constructor
    ~Grid(void); //destructor (takes care of dynamically allocated memory)
    
    /*
     Sets the grid drain to Position s
    */
    void setSource(Position s);
    
    /*
     Sets the grid drain to Position d
     */
    void setDrain(Position d);
    
    /*
     Equalizes the grid after #iter iterations.
     */
    void Equalize(int iter = 30);
    
    
    /*
     Prints the potential map of the grid
     */
    void printPotentialMap(void);
    
    void writeToFile(void);
    
private:
    Position source;
    Position drain;
    int grid_size;
    Node** grid;
    void adjustNodePotential(Position &temp);
    std::vector<Position> getNeighbors(Position &loc);
    void addNode(std::vector<Position>* temp, Position &loc, int, int);
};

#endif /* defined(__ResistorGrid__Grid__) */
