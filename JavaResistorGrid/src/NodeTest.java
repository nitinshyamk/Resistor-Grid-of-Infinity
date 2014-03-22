import static org.junit.Assert.*;

import org.junit.Test;


public class NodeTest {

	@Test
	public void test() {
		Node n = new Node();
		double volt = n.getVoltage();
		System.out.println(volt);
		assertEquals(false, n.isFixed());
		try{
			n.setVoltage(11.0);
			fail("exception not thrown");
		}
		catch (IllegalArgumentException e){
			System.out.println(e.toString() + " - error caught");
		}
		
		n.setDynamic();
		assertEquals(false, n.isFixed());
		n.setSource(); n.getVoltage();
		assertEquals(true, n.isFixed());
		System.out.println(n.getVoltage());
		try{
			n.setVoltage(4.0);
			fail("exception not thrown");
		}
		catch (IllegalArgumentException e){
			System.out.println(e.toString() + " - error caught");
		}		
		n.setSource(); n.getVoltage();
		n.setDynamic(); 
		assertEquals(false, n.isFixed());
		n.setVoltage(5.0);
		n.setDrain(); n.getVoltage();
		assertEquals(true, n.isFixed());
		System.out.println(n.getVoltage());
		System.out.println(n.getVoltage());

		
	}

}
