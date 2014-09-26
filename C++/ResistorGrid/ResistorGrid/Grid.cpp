//
//  Grid.cpp
//  ResistorGrid
//
//  Created by Nitin Shyamkumar on 5/19/14.
//  Copyright (c) 2014 Nitin Shyamkumar. All rights reserved.
//

#include "Grid.h"
#include <vector>

Grid::Grid (int size){
    grid_size = size;
    grid = new Node*[size];
    for (int i = 0; i<size; i++) {
        grid[i] = new Node[grid_size];
    }
    setSource(Position(0,0));
    setDrain(Position(1,1));

}

Grid::~Grid(){
    for (int i = 0; i<grid_size; i++) {
        delete [] grid[i];
    }
    delete [] grid;
    grid = nullptr;
    
}

void Grid::printPotentialMap(void){
    for (int i = 0; i<grid_size; i++){
        for (int j = 0; j < grid_size; j++){
            std::cout << grid[i][j].getVoltage() << '\t';
        }
        std::cout<<'\n';
    }
}


void Grid::writeToFile(void){
    std::ofstream data;
    std::cout<< "Writing to file. \n";
    data.open("data.txt");
    for (int i = 0; i<grid_size; i++){
        for (int j = 0; j < grid_size; j++){
            data << grid[i][j].getVoltage() << '\t';
        }
        data<<'\n';
    }
    data.close();
    std::cout<< "Finished writing to file. \n";
}

void Grid::setSource(Position s){
    grid[source.row][source.col].setDynamic();
    source = s;
    grid[source.row][source.col].setSource();

}

void Grid::setDrain(Position d){
    grid[drain.row][drain.col].setDynamic();
    drain = d;
    grid[drain.row][drain.col].setDrain();
}

void Grid::Equalize(int iter){
    while (iter > 0){
        for (int i = 0; i<grid_size; i++){
            for (int j = 0; j < grid_size; j++){
                Position temp(i, j);
                adjustNodePotential(temp);
            }
        }
        iter--;
    }
}

std::vector<Position> Grid::getNeighbors(Position &loc){
    std::vector<Position> temp;
    std::vector<Position> *tptr = &temp;
    addNode(tptr, loc, -1, 0);
    addNode(tptr, loc, 1, 0);
    addNode(tptr, loc, 0, 1);
    addNode(tptr, loc, 0, -1);
    return temp;
    
}

void Grid::addNode( std::vector<Position>* temp, Position &loc, int rchange, int cchange){
    Position foo (loc.row+rchange, loc.col+cchange);
    temp->push_back(foo);
}


void Grid::adjustNodePotential(Position &loc){
    if (grid[loc.row][loc.col].isFixed() ) return;
    
    std::vector<Position> neighbors = getNeighbors(loc);
    float average = 0.0; float counter = 0.0;
    for (std::vector<Position>::iterator iter = neighbors.begin();
         iter!=neighbors.end(); ++iter) {
        if (iter->Position::row >= 0 && iter->Position::row < grid_size &&
            iter->Position::col >= 0 && iter->Position::col < grid_size) {
            average += grid[iter->Position::row][iter->Position::col].getVoltage();
            counter +=1.0;
        }
    }
    average = (counter < 0.1)? average/1.0 : average/counter;
    grid[loc.row][loc.col].setVoltage(average);
}
