//
//  Node.h
//  ResistorGrid
//
//  Created by Nitin Shyamkumar on 5/19/14.
//  Copyright (c) 2014 Nitin Shyamkumar. All rights reserved.
//

#ifndef __ResistorGrid__Node__
#define __ResistorGrid__Node__

#include<iostream>
class Node {
public:
    Node(void); //default constructor (dynamic node)
    
    Node (bool t, float volt);
    
    /*returns the voltage at the node */
    float getVoltage(void);
    
    /*sets the node potential to 10.0 V and makes it fixed */
    void setSource(void);
    
    /*sets the node potential to 0.0 V and makes it fixed */
    void setDrain(void);
    
    /*sets the node potential to 0.0 V and makes it free */
    void setDynamic(void);
    
    /* "this node's potential is fixed" */
    bool isFixed(void);
    
    /* sets the voltage of given node to the float setTo */
    void setVoltage(float setTo) throw (int);

private:
    bool fixed;
    bool temp;
    float voltage;
};

#endif /* defined(__ResistorGrid__Node__) */
