// main.cpp
// Nitin Shyamkumar (github.com/nitinshyamk)
// 12.20.13

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "node.h"
#include "grid.h"

int main()
{
    Grid gridobj;
    gridobj.setSource(10,10);
    gridobj.setDrain(11,11);
    gridobj.printAddresses();
    return 1;
}
