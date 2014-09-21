import java.util.ArrayList;

/**Grid stores nodes in an organized fashion and process data appropriately via the 
	Reset and Equalize methods.
*/ 
public class Grid {
	private int width; private int height;
	private Position drain; private Position source;
	private ArrayList<Position> Sources;
	private Node[][] grid;
	
	/**
	 * Creates a grid and prints potential map. Either 0 cmdl arguments, or
	 * 2 arguments: "r,c"
	 */
	public static void main(String[] args){
		String errInfo = "Grid.java takes command line arguments"
				+ "in the following manner: \n"
				+ "Either 0 arguments \n"
				+ "Or: java Grid <width> <height> <r>,<c> <r>,<c> "
				+ "where the first <r>,<c> refers to the source position "
				+ "and the second refers to the drain position.\n\n";
		if (args.length == 0){
			Grid g = new Grid(20,20);
			g.addSource(10, 10); g.setDrain(10, 11);
			g.Equalize();
			g.PrintPotentialMap();
		}
		//square grid
		else if (args.length == 1){
			int size = -1;
			try {
				size = Integer.parseInt(args[0]);

			} 
			catch (Exception e){
				System.out.println("Failed to parse ints:");
				throw new IllegalArgumentException(errInfo);
			}
			if (size <3){
				throw new IllegalArgumentException("Invalid size arg.");
			}
			Grid g = new Grid(size,size);
			g.addSource(size/2, size/2); g.setDrain(size/2 + 1, size/2 + 1);
			g.Equalize();
			g.PrintPotentialMap();
			
		}
		//handles sources
		else if (args.length == 2){
			int width = -1, height = -1;
			try {
				width = Integer.parseInt(args[0]);
				height = Integer.parseInt(args[1]);
			} 
			catch (Exception e){
				System.out.println("Failed to parse ints:");
				throw new IllegalArgumentException(errInfo);
			}
			if (width <3 || height <3){
				throw new IllegalArgumentException("Invalid size argument ");
			}
			Grid g = new Grid(width,height);
			g.addSource(width/2, height/2); g.setDrain(width/2 + 1, height/2 + 1);
			g.Equalize();
			g.PrintPotentialMap();
			
		}
		//handles most specific case
		else if (args.length == 4){
			int width = -1, height = -1; 
			Grid g;
			try {
				width = Integer.parseInt(args[0]);
				height = Integer.parseInt(args[1]);
			} 
			catch (Exception e){
				System.out.println("Failed to parse ints:");
				throw new IllegalArgumentException(errInfo);
			}
			g = new Grid(width, height);
			
			try{
				Position source = convert(args[2]);
				Position drain = convert(args[3]);
				g.addSource(source.r, source.c);
				g.setDrain(drain.r, drain.c);
			}
			catch (Exception e){
				throw new IllegalArgumentException(errInfo);
			}
			g.Equalize(); g.PrintPotentialMap();
		}
		
		else{
			throw new IllegalArgumentException(errInfo);
		}
	}
	
	/**used to parse a position argument from a string**/
	private static Position convert(String s){
		s= s.trim(); int i = s.indexOf(',');
		int row; int col;
		try{
			row = Integer.parseInt(s.substring(0,i));
			col = Integer.parseInt(s.substring(i+1));
		}
		catch (Exception e){
			throw new IllegalArgumentException("Failed to parse cmdl arguments "
					+ "as int arguments for position.");
		}
		return new Position(row, col);
	}
	
	/**
	 * constructor creates a node Grid with width w and height h
	 * A drain is added at the bottom right corner, but no voltage sources are added.
	 */
	public Grid(int w, int h){
		width = w; height = h;
		grid = new Node[h][w];
		for (int i = 0; i<h; i++){
			for (int j = 0; j<w; j++){
				grid[i][j] = new Node();
			}
		}
		drain = new Position(h-1, w-1);
		grid[drain.r][drain.c].setDrain();
		Sources = new ArrayList<Position>();

	}
	/**
	 * default constructor creates a node Grid that is 100 x 100
	 */
	public Grid(){
		this(100,100);
	}
	
	/**returns height (int)
	 */
	public int Height(){
		return height;
	}
	
	/**returns width (int)
	 */
	public int Width(){
		return width;
	}
	
	/**
	 * adds a source
	 */
	public void addSource(int r, int c){
		this.addSource(new Position(r,c));
	}
	
	private void addSource(Position p){
		Sources.add(p);
		grid[p.r][p.c].setSource();
	}
	
	public void clearSources(){
		for (Position p: Sources){
			grid[p.r][p.c].setDynamic();
		}
		Sources = new ArrayList<Position>();
		Equalize();
		
	}
	
	/**
	 * sets the node at row r and column c to the source
	 */
	public void setDrain(int r, int c){
		grid[drain.r][drain.c].setDynamic();
		//System.out.println(drain.r+" "+ drain.c);
		//System.out.println(this.getNode(drain.r, drain.c).isFixed());
		drain = new Position(r,c);
		grid[drain.r][drain.c].setDrain();	
	}
	/**
	 * returns a string describing source position(s)
	 */
	public String tellSource(){
		String sourcePos = "";
		for (Position p: Sources){
			sourcePos += (p.toString() + " | ");
		}
		return sourcePos;
	}
	
	/**
	 * returns the source Position object
	 */
	protected ArrayList<Position> getSource(){
		return Sources;
	}
	
	/**
	 * returns a string describing drain position
	 */
	public String tellDrain(){
		return drain.toString();
	}
	
	/**
	 * returns the drain Position object
	 */
	protected Position getDrain(){
		return drain;
	}
	
	/**returns Node at row r, column c*/
	public Node getNode(int r, int c) throws IllegalArgumentException{
		if (r < 0 || r >= height || c < 0 || c >= width){
			throw new IllegalArgumentException("bad position index/indices");
		}
		return grid[r][c];
	}
	
	/**returns the neighbors of the node at row r, column c
	 */
	private ArrayList<Position> getNeighbors(int r, int c){
		ArrayList<Position> neighbors = new ArrayList<Position>();
		if (r == 0){
			if (c == 0){
				neighbors.add(new Position(r+1,c));
				neighbors.add(new Position(r,c+1));
			}
			else if (c == width-1){
				neighbors.add(new Position(r,c-1));
				neighbors.add(new Position(r+1,c));
			}
			else {
				neighbors.add(new Position(r+1,c));
				neighbors.add(new Position(r,c-1));
				neighbors.add(new Position(r,c+1));
			}
		}
		else if (r == height-1){
			if (c == 0){
				neighbors.add(new Position(r-1,c));
				neighbors.add(new Position(r,c+1));
			}
			else if (c == width-1){
				neighbors.add(new Position(r-1,c));
				neighbors.add(new Position(r,c-1));
			}
			else{
				neighbors.add(new Position(r-1,c));
				neighbors.add(new Position(r,c-1));
				neighbors.add(new Position(r,c+1));
			}
		}
		else if (c == 0){
			neighbors.add(new Position(r+1, c));
			neighbors.add(new Position(r-1, c));
			neighbors.add(new Position(r, c+1));	
		}
		else if (c == width - 1){
			neighbors.add(new Position(r+1, c));
			neighbors.add(new Position(r-1, c));
			neighbors.add(new Position(r, c-1));	
		}
		else{
			neighbors.add(new Position(r-1,c));
			neighbors.add(new Position(r+1,c));
			neighbors.add(new Position(r,c-1));
			neighbors.add(new Position(r,c+1));
		}
		return neighbors;
	}
	
	/**attempts to iteratively calculate the potential of a node at 
	 * row r and column c, throws an error if the function attempts to change
	 * a fixed node.
	 */
	private double adjustNodePotential(int r, int c) throws IllegalArgumentException{
		if (r < 0 || r >= height || c <0 || c > width){
			throw new IllegalArgumentException("Bad position arguments");
		}
		if (getNode(r,c).isFixed()){
			throw new IllegalArgumentException("Attempted to change a fixed node");
		}
		
		ArrayList<Position> neighbors = getNeighbors(r, c);
		double volt = 0; int i = 0;
		for (Position e : neighbors){
			volt += getNode(e.r, e.c).getVoltage();
			i++;
		}
		volt = volt/i;
		grid[r][c].setVoltage(volt);
		return grid[r][c].getVoltage() - volt;
	}
	
	/**
	 * iterates through the entire grid, and processes the potential.
	 * Equalizes the entire grid in the process
	 * @param iterations
	 */
	public void Equalize(int iterations){
		int init = iterations;
		while (iterations > 0){
			for (int i = 0; i <= height-1; i++){
				for (int j = 0; j <= width-1; j++){
					if (!grid[i][j].isFixed()){adjustNodePotential(i, j);}
				}
			}
			String record = Double.toString(100.0*(init - iterations)/init);
			if (record.length() > 4){record = record.substring(0,5);}
			System.out.println(record.substring(0) + "% calculated");
			iterations--;
		}
		System.out.println("100% calculated");
		
	}
	/**
	 * iterates 30 times through the entire grid, and processes the potential.
	 * Equalizes the entire grid in the process
	 * @param iterations
	 */
	public void Equalize(){
		Equalize(30);
	}
	
	/**conveniently prints the potential value at each node
	 * Can easily be printed to a txt file via cmdl and copied into Excel
	 * (Though consider GUI for this purpose as well)
	 */
	public void PrintPotentialMap(){
		if (width*height > 1600){
			System.out.println("The table is too big to print");
		}
		else{
			for (int i = 0; i < height; i++){
				String accumulator = "";
				for (int j = 0; j < width; j++){
					double val = grid[i][j].getVoltage();
					String temp = String.valueOf(val);
					temp = (temp.length() > 4)? temp.substring(0, 4) : temp+"0";
					while (temp.length() < 4){temp+="0";}
					accumulator+=(temp + "\t");
				}
				System.out.println(accumulator);
			}
		}
	}
	//class def ends
}
