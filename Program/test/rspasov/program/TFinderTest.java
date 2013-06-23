package rspasov.program;

import static org.junit.Assert.*;

import org.junit.Test;

public class TFinderTest {

	@Test
	public void testFind() {
		double error = 0.0000001;
		TFinder finder = new TFinder(error);
//		0.20 6 0.55338 
//		0.45 15 1.75305 
//		0.495 4 4.60409 
		
		double expected = 0.55338;
		double actual = finder.find(0.20, 6, 1.0, -0.5);
		assertEquals(expected, actual, 0.01);
		
		expected = 1.75305;
		actual = finder.find(0.45, 15, 1.0, -0.5);
		assertEquals(expected, actual, 0.01);
		
		expected = 4.60409;
		actual = finder.find(0.495, 4, 1.0, -0.5);
		assertEquals(expected, actual, 3.0);
	}
	
	@Test
	public void testAdjustD() throws Exception {
		double error = 0.0000001;
		TFinder finder = new TFinder(error);
		
		double expected = -finder.getD() / 2;
		
		finder.adjustD(true);
		double actual = finder.getD();
		assertEquals(expected, actual, error);
		
		finder.adjustD(true);
		actual = finder.getD();
		assertEquals(expected, actual, error);
		
		expected = -finder.getD() / 2;
		finder.adjustD(false);
		actual = finder.getD();
		assertEquals(expected, actual, error);
		
		finder.adjustD(false);
		actual = finder.getD();
		assertEquals(expected, actual, error);
	}

}
