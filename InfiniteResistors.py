#InfiniteResistors.py
#Authored by Nitin Shyamkumar (Github: nitinshyamk)
#Last Updated 11/15/13 21:00

"""InfiniteResistors.py: 
This module addresses the problem of the infinite grid of resistors. (See the
    readme file for more info)
This module contains two classes, Node, and Grid. Grid is simply a class built
to store nodes in an organized fashion and process data appropriately via the 
Equalize method. 
The potential map can then be accessed using the method getPotentialMap in the 
Grid class.
"""

class Node(object):
    """
    Node object represents a single point.
    Attributes:
        _fixed: type bool, True if Voltage cannot change, False if it can
        _volt: potential (represents V), float
    """
    def __init__(self, Fixed = False, Voltage = 0.0):
        self._fixed = Fixed
        self._volt = Voltage

    @property
    def fixed(self):
        """returns the condition of self._fixed"""
        return self._fixed

    @fixed.setter
    def fixed(self,value):
        """value must be a bool"""
        assert(type(value)==bool),"fixed() method received non bool type:"+str(value)
        self._fixed = value

    @property
    def volt(self):
        """returns the value of self._volt"""
        return self._volt

    @volt.setter
    def volt(self,value):
        assert (type(value)==float or type(value) == int),"volt setter method received non float type: "+str(value)
        self._volt = value

class Grid(object):
    """
    Grid is an object to represent a resistor grid.
    Attributes:
        _grid: is a 2d list of ints to represent a grid of all Node objects
            # A small, but very important note on calling individual nodes
            # from self._grid
            # because of the way it's initialized,
            # self._grid[0][1] calls the grid that is traditionally at (0,1) as shown
            #  (0,0) (0,1) (0,2) ...
            #  (1,0) (1,1) (1,2) ...
            #  (2,0) (2,1) (2,2) ...
            #   ...   ...   ...  ...
        _sizex: the x length of _grid - not intended to ever be changed, solely for reference
        _sizey: the y length of _grid - not intended to ever be changed, solely for reference
        _drain: a single list of two ints that represents the point where current exits (potential is 0)
        _source: a single list of two ints that represents the point where current enters (potential is 1)
    Methods(non standard):
        Equalize: this method iterates through the entire grid, calculating the 
            potential at each point based on the difference equation discussed in the paper.
        Reset: resets all potentials of all nodes in the grid. Does NOT alter 
            the source/drain or fixed attributes
        getPotentialMap: returns a 2d list of the node potentials (organized, of course)

        ##### The following are helper methods, intended for use in the Equalize method #####
        _getNeighbors: returns the ids of the nodes in a list (can be as little as two, max of 4)
        _calcVolt: a method intended to be used iteratively - calculates the potential of a node 
            based on the voltages of its neighbors and then CHANGES the node attribute volt. 
            It returns the error(change).
            **** Instead of having it determine error bounds, Equalize can then be altered 
            as desired to change error bounds as needed.

    """

    def __init__(self, sizex = 50, sizey = 50, drain_pos = [50-1,50-1], source_pos=[0,0]):
        """This function constructs a Grid object.
        Call in the form Grid(sizex,sizey,drain_pos,source_pos)
            sizex and sizey must both be ints greater than 1.
            drain_pos and source_pos must both be valid """
        assert (type(sizex)==int and sizex>1)
        assert(type(sizey)==int and sizey>1)
        assert(type(drain_pos)==list and drain_pos[0]<sizex and drain_pos[1]<sizey)
        assert(type(source_pos)==list and source_pos[0]<sizex and source_pos[1]<sizey)

        self._sizex = sizex
        self._sizey = sizey

        self._grid = []
        for ii in range(sizex):
            row = []
            for jj in range(sizey):
                row.append(Node())
            self._grid.append(row)
        #self._grid is now fully constructed - it can exist now, though voltages, drain, and source must be altered

        self._drain=drain_pos
        self._grid[drain_pos[0]][drain_pos[1]].volt = 0.0
        self._grid[drain_pos[0]][drain_pos[1]].fixed = True

        self._source=source_pos
        self._grid[source_pos[0]][source_pos[1]].volt = 1.0
        self._grid[source_pos[0]][source_pos[1]].fixed = True

    #GETTERS AND SETTERS
    @property
    def grid(self):
        """Unlike the other setters, this masks far more of the attributes.
        It returns a summary of the grid (which cannot be altered)."""
        string = 'Grid Size: '+str(self._sizex)+' X '+str(self._sizey)
        return string

    @property
    def sizex(self):
        return self._sizex
    @property
    def sizey(self):
        return self._sizey

    @property
    def source(self):
        return self._source

    @source.setter
    def source(self,pos):
        """This setter changes the previous source's node attribute fixed to false.
        Precondition: pos must be a valid position list type."""

        assert (type(pos)==list and len(pos)==2 and type(pos[0])==int and type(pos[1]==int))
        
        self._grid[pos[0]][pos[1]].volt = 1.0
        self._grid[pos[0]][pos[1]].fixed = True

        self._grid[self._source[0]][self._source[1]].fixed = False
        self._source = pos

    @property
    def drain(self):
        return self._drain

    @drain.setter
    def drain(self,pos):
        """This setter changes the previous drain's node attribute fixed to false. It then
        alters the new drain node's voltage and fixed attributes.
        Precondition: pos must be a valid position list type."""
        assert (type(pos)==list and len(pos)==2 and type(pos[0])==int and type(pos[1]==int))
        
        self._grid[pos[0]][pos[1]].volt = 1.0
        self._grid[pos[0]][pos[1]].fixed = True

        self._grid[self._drain[0]][self._drain[1]].fixed = False
        self._drain = pos

    ### HIDDEN HELPER METHODS ###
    # these methods (_getNeighbors and _calcVolt) are not intended to be publicly accessible methods
    def _getNeighbors(self, xpos, ypos):
        """Returns a list of the neighbors - direct reference, ie returns the ids
        See definition of neighbors: Refine in readme.txt
        Precondition: xpos and ypos must both be less than _sizex and _sizey respectively"""
        assert(type(xpos)==int and xpos<len(self._grid))
        assert(type(ypos)==int and ypos<len(self._grid[xpos]))

        neighbors =[]

        # corner cases
        if xpos == 0 and ypos == 0:
            neighbors.append(self._grid[0][1])
            neighbors.append(self._grid[1][0])
        elif xpos == 0 and ypos == (self._sizey - 1):
            neighbors.append(self._grid[0][self._sizey - 2])
            neighbors.append(self._grid[1][self._sizey - 1])
        elif xpos == (self._sizex-1) and ypos == 0:
            neighbors.append(self._grid[self._sizex-2][0])
            neighbors.append(self._grid[self._sizex-1][1])
        elif xpos == (self._sizex-1) and  ypos == (self._sizey-1):
            neighbors.append(self._grid[self._sizex-2][self._sizey-1])
            neighbors.append(self._grid[self._sizex-1][self._sizey-2])

        #now address the border 
        elif xpos == 0 or ypos ==0 or xpos == self._sizex-1 or ypos == self._sizey-1:
            if xpos==0:
                neighbors.append(self._grid[1][ypos])
                neighbors.append(self._grid[0][ypos-1])
                neighbors.append(self._grid[0][ypos+1])
            elif ypos==0:
                neighbors.append(self._grid[xpos][1])
                neighbors.append(self._grid[xpos+1][0])
                neighbors.append(self._grid[xpos-1][0])
            elif xpos==self._sizex-1:
                neighbors.append(self._grid[self._sizex-2][ypos])
                neighbors.append(self._grid[self._sizex-1][ypos+1])
                neighbors.append(self._grid[self._sizex-1][ypos-1])
            elif ypos==self._sizey-1:
                neighbors.append(self._grid[xpos][self._sizey-2])
                neighbors.append(self._grid[xpos+1][self._sizey-1])
                neighbors.append(self._grid[xpos-1][self._sizey-1])
        
        #now we can address any general case
        else:
            neighbors.append(self._grid[xpos+1][ypos])
            neighbors.append(self._grid[xpos-1][ypos])
            neighbors.append(self._grid[xpos][ypos+1])
            neighbors.append(self._grid[xpos][ypos+1])

        return neighbors

    def _calcVolt(self,xpos,ypos):
        """This method changes the voltage of the node at (xpos,ypos) and then 
            Returns the error.
            This is where the calculation using the difference equation is used.
            Precondition: xpos,ypos must both be valid position types and less 
            than _sizex and _sizey respectively."""
        assert(type(xpos)==int and xpos<len(self._grid))
        assert(type(ypos)==int and ypos<len(self._grid[xpos]))
        neighbors = self._getNeighbors(xpos,ypos)
        old = self._grid[xpos][ypos].volt

        #calculate voltage of neighbors using the difference equation - see paper linked in readme site
        accumulator = 0.0
        for i in range(len(neighbors)):
            accumulator+=neighbors[i].volt
        accumulator = float(accumulator)/len(neighbors)

        #now change the voltage of the node at (xpos,ypos)
        if not self._grid[xpos][ypos].fixed:
            self._grid[xpos][ypos].volt = accumulator
            return old - accumulator
        else:
            return 0.0

    #INTERFACE METHODS
    def Reset(self):
        """This procedure changes every node in _grid to have a voltage of 0.0.
        It does NOT alter the node._fixed attributes of each node or change the
        _source or _drain attributes."""
        for ii in range(self._sizex):
            for jj in range(self._sizey):
                self._grid[ii][jj].volt = 0.0

    def Equalize(self, err = 0.001):
        """This method iterates through the grid, changing each node potential 
        using the helper method _calcVolt. It uses the difference equation described
        in the Readme File."""
        someErr = True
        while someErr:
            someErr = False
            for ii in range(self._sizex):
                for jj in range(self._sizey):
                    returnErr = self._calcVolt(ii,jj)
                    if returnErr > err:
                        someErr = True
        #this while loop should continue until there is no longer any error.

    def getPotentialMap(self):
        """Returns a 2d list of the potentials of each node"""
        table = []
        for ii in range(self._sizex):
            column = []
            for jj in range(self._sizey):
                column.append(self._grid[ii][jj].volt)
            table.append(column)
        return table
            

