//
//  Position.h
//  ResistorGrid
//
//  Created by Nitin Shyamkumar on 5/19/14.
//  Copyright (c) 2014 Nitin Shyamkumar. All rights reserved.
//

#ifndef __ResistorGrid__Position__
#define __ResistorGrid__Position__

#include <iostream>
class Position{
public:
    int row;
    int col;
    Position(int r, int c);
    Position(){
        row = 0; col = 0;
    }
};

#endif /* defined(__ResistorGrid__Position__) */
