#Class for Node
#Voltage - that's it 
# change voltage 

"""Documentation Improvements: 
clarify what the significance of the problem is - outline how a grid is defined - how neighbors are defined
take care of errors (throw errors) - mostly when trying to change grid
Clarify details and notation in Readme.txt for problem
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
            # self._grid[0][1] calls the grid that is traditionally at (0,1), but
            #  (0,0) (0,1) (0,2) ...
            #  (1,0) (1,1) (1,2) ...
            #  (2,0) (2,1) (2,2) ...
            #   ...   ...   ...  ...
        _sizex: the x length of _grid - not intended to ever be changed, solely for reference
        _sizey: the y length of _grid - not intended to ever be changed, solely for reference
        _drain: a single list of two ints that represents the point where current exits (potential is 0)
        _source: a single list of two ints that represents the point where current enters (potential is 1)
    Methods(non standard):
        Equalize: this method iterates through the entire grid, calculating the potential at each point based
        on the difference equation discussed in the paper

        ##### The following are helper methods, intended for use in the Equalize method #####
        Reset: resets all potentials of all nodes in the grid. Does NOT alter the source/drain or fixed attributes
        Neighbors: returns the ids of the nodes in a list (can be as little as two, max of 4)
        CalculatePotential: a method intended to be used iteratively - calculates the potential of a node 
            based on the voltages of its neighbors

    """

    def __init__(self, sizex = 200, sizey = 100, drain_pos = [200-1,100-1], source_pos=[0,0]):
        """This function constructs a Grid object"""
        assert (type(sizex)==int and sizex>0)
        assert(type(sizey)==int and sizey>0)
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

    @property
    def source(self):
        return self._source

    @source.setter
    def source(self,pos):
        """This setter changes the previous source's node attribute fixed to false."""

        assert (type(pos)==list and len(pos)==2 and type(pos[0])==int and type(pos[1]==int))
        
        self._grid[pos[0]][pos[1]].volt = 1.0
        self._grid[pos[0]][pos[1]].fixed = True

        self._grid[self._source[0]][self._source[1]].fixed = False
        self._source = pos


    @property
    def drain(self):
        return self._drain

    @source.setter
    def drain(self,pos):
        """This setter changes the previous drain's node attribute fixed to false. It then
        alters the new drain node's voltage and fixed attributes."""
        assert (type(pos)==list and len(pos)==2 and type(pos[0])==int and type(pos[1]==int))
        
        self._grid[pos[0]][pos[1]].volt = 1.0
        self._grid[pos[0]][pos[1]].fixed = True

        self._grid[self._drain[0]][self._drain[1]].fixed = False
        self._drain = pos

    def Reset(self):
        for ii in range(sizex):
            for jj in range(sizey):
                self._grid[ii][jj].volt = 0.0

    def getNeighbors(self, xpos, ypos):
        """Returns a list of the neighbors - direct reference. 
        See definition of neighbors: Refine in readme.txt"""
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
                neighbors.append()

        return neighbors


#Fixed attribute - y/or no
#Class for Grid - 
    #Attribute 2d list that stores all of the node objects
    # Attributes - voltage source, and drain (both list of two ints that give coordinates)
    #Methods - Update a voltage 
    #takes a row and a column argument - getter
    # note that the voltage of a point is given by the sum of its neighbors voltage, and then divided by the number of its neighbors
