package rspasov.program;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.junit.Test;

import rspasov.program.list.LinkedList;
import rspasov.program.list.LinkedListNode;
import rspasov.program.reader.InputReader;

/**
 * Program Assignment: Regression, Correlation, and Prediction Calculator
 * <p>
 * Description: This program is used for calculating the regression parameters,
 * correlation, and prediction used in PSP2.
 * 
 * @author Rosen Spasov
 * @version 1.0
 */
public class MainTest {

	@Test
	public void test1() throws Exception {
		InputStream input = this.getClass().getResourceAsStream("input10.txt");
		InputReader reader = new InputReader(input);
		LinkedList listX = reader.read();

		input = this.getClass().getResourceAsStream("input11.txt");
		reader = new InputReader(input);
		LinkedList listDof = reader.read();

		input = this.getClass().getResourceAsStream("input12.txt");
		reader = new InputReader(input);
		LinkedList listProbability = reader.read();

		for (LinkedListNode node = listX.getHead(), dofNode = listDof.getHead(), probabilityNode = listProbability.getHead(); node != null
				&& dofNode != null && probabilityNode != null; node = node.getNext(), dofNode = dofNode.getNext(), probabilityNode = probabilityNode
				.getNext()) {
			double probability = Calculator.calculateProbability((int) dofNode.getData(), node.getData(), 2);
			System.out.println(probability);
			assertEquals(probabilityNode.getData(), probability, 0.01);
		}
	} // end
} // end
