package rspasov.program.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.util.Arrays;

import org.junit.Test;

import rspasov.program.list.LinkedList;
import rspasov.program.list.LinkedListNode;

/**
 * Program Assignment: Regression, Correlation, and Prediction Calculator
 * <p>
 * Description: This program is used for calculating the regression parameters,
 * correlation, and prediction used in PSP2.
 * 
 * @author Rosen Spasov
 * @version 1.0
 */
public class InputReaderTest {

	private final double[] testData = { 186, 699, 132, 272, 291, 331, 199, 1890, 788, 1601 };

	@Test
	public void testRead() throws Exception {
		System.out.println("Expected input: " + Arrays.toString(testData));
		InputStream input = this.getClass().getResourceAsStream("input3.txt");
		InputReader reader = new InputReader(input);
		LinkedList list = reader.read();
		System.out.println("Actual input: " + list);
		assertNotNull(list);
		assertNotNull(list.getHead());
		LinkedListNode currentNode = list.getHead();
		int index = 0;
		while (currentNode != null) {
			assertEquals(testData[index++], currentNode.getData(), 0.0);
			currentNode = currentNode.getNext();
		}
	} // end

} // end
