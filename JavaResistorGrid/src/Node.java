/**
 * Node is a simple object to store the voltage at each node and whether the 
 * node is fixed, i.e. can the voltage change?
 * A node is either dynamic or fixed. If a node is fixed, 
 * it is either a source or drain node.
 */
public class Node {
	private boolean fixed;
	private double voltage;
	/**
	 * constructor to create a Node with initial voltage 'init_v'
	 * and fixed property 't'
	 */
	public Node(boolean t, double init_v){
		fixed = t; voltage = init_v;
	}
	/**
	 * default constructor creates node with voltage 0 and dynamic type
	 */
	Node(){
		this(false, 0.0);
	}
	/**
	 * changes a node type to fixed and changes the voltage to the 10.0 V max.
	 */
	protected void setSource(){
		fixed = true; voltage = 10.0;
	}
	
	/**
	 * changes a node type to fixed and changes the voltage to the 0.0 V min.
	 */
	protected void setDrain(){
		fixed = true; voltage = 0.0;
	}
	/**
	 * changes a node type to dynamic and resets the voltage to 0.0
	 */
	protected void setDynamic(){
		fixed = false; voltage = 0.0;
	}
	/**
	 * "node type" (boolean - true if fixed, false if dynamic)
	 */
	protected boolean isFixed(){
		return fixed;
	}
	
	/**
	 * changes the node voltage to v if the node is not fixed
	 */
	public void setVoltage(double v) throws IllegalArgumentException{
		if (v > 10.0 || v <0.0){ 
			throw new IllegalArgumentException("Voltage must be in the range 0...10.0");
		}
		else if (fixed){
			throw new IllegalArgumentException("Cannot set fixed node's voltage");
		}
		else
		{voltage = v;}
	}
	
	/**
	 * = "node voltage"
	 */
	public double getVoltage(){
		return voltage;
	}
	
	
}
