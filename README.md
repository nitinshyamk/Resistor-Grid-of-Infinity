Resistor-Grid-of-Infinity
=========================
This project is an approximate solution to the physics problem of the infinite grid of
resistors. The problem of finding an effective resistance between any two nodes on this 
proposed infinite grid of resistors has a simple difference equation in the form of
 4*v_m,n = v_m-1,n + v_m,n-1 + v_m+1,n + v_m,n+1. This equation results from simple node
analysis. The potential on this infinite grid can then be approximated by defining a grid of 
nodes.
The following website has a good summary of the problem and a more refined solution as well.
http://www.mathpages.com/home/kmath668/kmath668.htm

In this module, the problem is solved by normalizing the source potential to 1V and making
each resistors resistance 1 ohm.

test.py consists of a simple 10x10 case and returns the potential map as a 2d list. (10 lists
in each cell within a list of 10 cells)
