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
 * Description: This program is used for calculating the regression parameters, correlation, and prediction used in PSP2.
 * 
 * @author Rosen Spasov
 * @version 1.0
 */
public class MainTest {

	private LinkedList readResource(String resource) throws Exception {
		InputStream input = getClass().getResourceAsStream(resource);
		InputReader reader = new InputReader(input);
		return reader.read();
	}

	@Test
	public void test1Program6() throws Exception {
		LinkedList listX = readResource("input10.txt");
		LinkedList listDof = readResource("input11.txt");
		LinkedList listProbability = readResource("input12.txt");

		for (LinkedListNode node = listX.getHead(), dofNode = listDof.getHead(), probabilityNode = listProbability.getHead(); node != null
				&& dofNode != null && probabilityNode != null; node = node.getNext(), dofNode = dofNode.getNext(), probabilityNode = probabilityNode
				.getNext()) {
			double probability = Calculator.calculateProbability((int) dofNode.getData(), node.getData(), 2);
			assertEquals(probabilityNode.getData(), probability, 0.01);
		}
	} // end

	@Test
	public void test1Program7() throws Exception {
		LinkedList listX = readResource("input13.txt");
		LinkedList listY = readResource("input14.txt");

		double r = Calculator.calculateCorrelation(listX, listY);
		assertEquals(0.954496574, r, 0.000000001);
		assertEquals(0.911063710, r * r, 0.00000001);

		int n = 10;
		double t = Calculator.calculateT(r, n);
		System.out.println("T = " + t);
		double p = Calculator.calculateProbability(n - 2, t, 10);
		System.out.println("P = " + p);
		double significance = Calculator.calculateSignificance(p);
		System.out.println("Significance = " + significance);
		assertEquals(0.0000177517, significance, 1.0);

		double E = 386;
		double b0 = Calculator.calculateRegression0(listX, listY);
		assertEquals(-22.55253275, b0, 0.00000001);
		double b1 = Calculator.calculateRegression1(listX, listY);
		assertEquals(1.727932426, b1, 0.00000001);
		double P = Calculator.calculatePrediction(listX, listY, E);
		assertEquals(644.4293838, P, 0.0000001);
		double range = Calculator.calculateRange(listX, listY, E, n);
		assertEquals(230.0017197, range, 20.0);
		double upi = Calculator.calculateUPI(P, range);
		assertEquals(874.4311035, upi, 20.0);
		double lpi = Calculator.calculateLPI(P, range);
		assertEquals(414.427664, lpi, 20.0);
	}
} // end
