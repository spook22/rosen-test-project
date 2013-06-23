package rspasov.program.list;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Program Assignment: Regression, Correlation, and Prediction Calculator
 * <p>
 * Description: This program is used for calculating the regression parameters,
 * correlation, and prediction used in PSP2.
 * 
 * @author Rosen Spasov
 * @version 1.0
 */
public class LinkedListTest {

	@Test
	public void testGetHead() {
		LinkedList list = new LinkedList();
		assertNull(list.getHead());
		assertEquals(0, list.getSize());

		double headValue = 1.0;
		list.add(headValue);
		assertNotNull(list.getHead());
		assertEquals(1, list.getSize());
		assertEquals(headValue, list.getHead().getData(), 0.00000000001);
		assertNull(list.getHead().getNext());

		double nextValue = 2.0;
		list.add(nextValue);
		assertNotNull(list.getHead());
		assertEquals(2, list.getSize());
		assertEquals(headValue, list.getHead().getData(), 0.00000000001);
		assertNotNull(list.getHead().getNext());
		assertEquals(nextValue, list.getHead().getNext().getData(), 0.00000000001);
		assertNull(list.getHead().getNext().getNext());
	} // end

	@Test
	public void testGetSize() {
		LinkedList list = new LinkedList();
		assertEquals(0, list.getSize());
		list.add(1.0);
		assertEquals(1, list.getSize());
		list.add(2.0);
		assertEquals(2, list.getSize());
	} // end

	@Test
	public void testAdd() {
		LinkedList list = new LinkedList();
		double headValue = 1.0;
		list.add(headValue);
		assertNotNull(list.getHead());
		assertEquals(headValue, list.getHead().getData(), 0.00000000001);

		double nextValue = 2.0;
		list.add(nextValue);
		assertNotNull(list.getHead().getNext());
		assertEquals(nextValue, list.getHead().getNext().getData(), 0.00000000001);
	} // end

	@Test
	public void testToString() {
		LinkedList list = new LinkedList();
		assertEquals("", list.toString());
		double headValue = 1.0;
		list.add(headValue);
		assertEquals(String.valueOf(headValue), list.toString());
		double nextValue = 2.0;
		list.add(nextValue);
		assertEquals(String.valueOf(headValue) + ", " + String.valueOf(nextValue), list.toString());
	} // end

} // end
