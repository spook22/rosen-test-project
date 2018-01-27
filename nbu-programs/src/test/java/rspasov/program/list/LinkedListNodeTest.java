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
public class LinkedListNodeTest {

	@Test
	public void testLinkedListNode() {
		assertNotNull(new LinkedListNode(1.0));
	} // end

	@Test
	public void testGetData() {
		double value = 1.0;
		assertEquals(value, new LinkedListNode(value).getData(), 0.00000001);
	} // end

	@Test
	public void testGetNext() {
		LinkedListNode node = new LinkedListNode(1.0);
		assertNull(node.getNext());
		LinkedListNode next = new LinkedListNode(2.0);
		node.setNext(next);
		assertNotNull(node.getNext());
		assertEquals(next, node.getNext());
	} // end

	@Test
	public void testSetNext() {
		LinkedListNode node = new LinkedListNode(1.0);
		LinkedListNode next = new LinkedListNode(2.0);
		node.setNext(next);
		assertNotNull(node.getNext());
		assertEquals(next, node.getNext());
	} // end

} // end
