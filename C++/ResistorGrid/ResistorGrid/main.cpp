//
//  main.cpp
//  ResistorGrid
//
//  Created by Nitin Shyamkumar on 5/19/14.
//  Copyright (c) 2014 Nitin Shyamkumar. All rights reserved.
//

#include <iostream>
#include "Node.h"
#include "Grid.h"
#include "Position.h"

int main(int argc, const char * argv[])
{
    Grid test(20);
    test.setSource(Position(0,0));
    test.setDrain(Position(9,9));
    test.Equalize(20);
    test.writeToFile();
    return 0;
}

