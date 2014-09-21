//
//  Node.cpp
//  ResistorGrid
//
//  Created by Nitin Shyamkumar on 5/19/14.
//  Copyright (c) 2014 Nitin Shyamkumar. All rights reserved.
//

#include "Node.h"
//basic constructor creates a new node
Node::Node (bool t, float volt){
    fixed = t;
    voltage = volt;
}
Node::Node(void){
    Node(false, 0.0);
}

//returns the voltage of a given node
float Node::getVoltage(void){
    return voltage;
}

void Node::setSource(void){
    fixed = true;
    voltage = 10.0;
}

void Node::setDrain(void){
    fixed = true;
    voltage = 0.0;
}

void Node::setDynamic(void){
    fixed = false;
    voltage = 0.0;
}

bool Node::isFixed(void){
    return fixed;
}

void Node::setVoltage(float setTo) throw (int){
    if (setTo > 10.0 || setTo < 0.0){
        throw (-1);
    }
    voltage = setTo;
}


