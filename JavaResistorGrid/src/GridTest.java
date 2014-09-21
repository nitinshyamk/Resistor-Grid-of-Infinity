import static org.junit.Assert.*;

import org.junit.Test;

public class GridTest {

	@Test
	public void testConstructor() {
		Grid g = new Grid();
		assertEquals(100, g.Width());
		assertEquals(100, g.Height());
		g = new Grid(200, 300);
		assertEquals(200, g.Width());
		assertEquals(300, g.Height());
		
		g = new Grid();
		assertEquals("(0, 0)", g.tellSource());
		assertEquals("(99, 99)", g.tellDrain());
		
		g.setDrain(67, 3);
		assertEquals("(67, 3)", g.tellDrain());
		//assertEquals(false, g.getNode(99, 99).isFixed());
		//assertEquals(true, g.getNode(67, 3).isFixed());
		//System.out.println(g.getNode(67, 3).getVoltage());

		
		assertEquals("(32, 5)", g.tellSource());
		//assertEquals(false, g.getNode(0, 0).isFixed());
		//assertEquals(true, g.getNode(32, 5).isFixed());
		//System.out.println(g.getNode(32, 5).getVoltage());
		
		//ArrayList<Position> neighbors = g.getNeighbors(4, 5);
		/*for (Position e : neighbors){
			System.out.println(e.toString());
		}
		System.out.println("BREAK");
		neighbors = g.getNeighbors(0, 0);
		System.out.println("BREAK");
		neighbors = g.getNeighbors(0, 99);
		System.out.println("BREAK");
		neighbors = g.getNeighbors(99, 0 );		
		System.out.println("BREAK");
		neighbors = g.getNeighbors(99, 99 );
		
		System.out.println("BREAK");
		neighbors = g.getNeighbors(0, 30 );
		System.out.println("BREAK");
		neighbors = g.getNeighbors(99, 21 );
		System.out.println("BREAK");
		neighbors = g.getNeighbors(32, 99 );
		System.out.println("BREAK");
		neighbors = g.getNeighbors(32, 0 );*/
	}
	@Test
	public void testCalculatingFunctions(){
		Grid g = new Grid(20,20);
		g.setDrain(10, 11);
		g.Equalize();
		g.PrintPotentialMap();
	}

}
