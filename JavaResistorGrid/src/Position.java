/**
 * a simple class to store positions
 */
public class Position {
	public int r; //meant to be accessed directly because the class is so simple
	public int c;
	public Position(int row, int col){
		r = row; c = col;
	}
	@Override public String toString(){
		return "("+r+", "+c+")";
	}
	
}
