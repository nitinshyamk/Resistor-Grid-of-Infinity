// node.h
// Nitin Shyamkumar (github.com/nitinshyamk)
// 12.20.13

#ifndef NODE_H
#define NODE_H

class Node
{
public:
    //constructor - by default intended to set all nodes as mutable and voltage of 0
    Node(void)
    {
        m_dVoltage = 0.0;
        m_bFixed = false;
    }
    //public source & drain setters:
    void setNodeAsSource(void) 
    {
        m_dVoltage = 1.0;
        m_bFixed = true;
    }
    void setNodeAsDrain(void) 
    {
        m_dVoltage = 0.0;
        m_bFixed = true;
    }
    void setNodeAsNormal(void)
    {
        m_dVoltage = 0.0;
        m_bFixed = true;
    }
    //following are getters
    float getVoltage(void) {return m_dVoltage;}
    bool getState(void) {return m_bFixed;}

private:
    float m_dVoltage;
    bool m_bFixed;

};

#endif