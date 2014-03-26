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

In the Python module, the problem is solved by normalizing the source potential to 1V and making
each resistors resistance 1 ohm.

test.py consists of a simple 10x10 case and returns the potential map as a 2d list. (10 lists
in each cell within a list of 10 cells)

The Java module has better support.
Grid.java - takes commandline arguments to create a grid of some dimensions W x H, and then prints an equalized potential map.
GUI.java - takes commandline arguments (though no arguments is also valid) and displays the potential map as a GUI.
Note: GUI.java is general enough that it may be of interest to anyone who wants to plot a two dimensional grid of data, making this useful for anyone who wants to display a large amount of scientific data.
The class can easily be changed so that the private field which currently stores type Grid, can store some other array.
It would be another two-three lines of edits to change the way GUI currently calls data. 

*Unfortunately, since I wrote the Java module much later than the Python module, the Java module is slightly different, it is normalized to 10 volts instead.
