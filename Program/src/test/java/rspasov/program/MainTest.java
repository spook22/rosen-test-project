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
		System.out.println("r = " + r);
		System.out.println("r2 = " + r * r);
		assertEquals(0.954496574, r, 0.000000001);
		assertEquals(0.911063710, r * r, 0.00000001);

		int n = 10;
		double t = Calculator.calculateT(r, n);
		System.out.println("t = " + t);
		double p = Calculator.calculateProbability(n - 2, t, 10);
		System.out.println("p = " + p);
		double significance = Calculator.calculateSignificance(p);
		System.out.println("Significance = " + significance);
		assertEquals(0.0000177517, significance, 1.0);

		double E = 386;
		double b0 = Calculator.calculateRegression0(listX, listY);
		System.out.println("b0 = " + b0);
		assertEquals(-22.55253275, b0, 0.00000001);
		double b1 = Calculator.calculateRegression1(listX, listY);
		System.out.println("b1 = " + b1);
		assertEquals(1.727932426, b1, 0.00000001);
		double P = Calculator.calculatePrediction(listX, listY, E);
		System.out.println("P = " + P);
		assertEquals(644.4293838, P, 0.0000001);
		double range = Calculator.calculateRange(listX, listY, E, n);
		System.out.println("range = " + range);
		assertEquals(230.0017197, range, 20.0);
		double upi = Calculator.calculateUPI(P, range);
		System.out.println("upi = " + upi);
		assertEquals(874.4311035, upi, 20.0);
		double lpi = Calculator.calculateLPI(P, range);
		System.out.println("lpi = " + lpi);
		assertEquals(414.427664, lpi, 20.0);
	} // end

	@Test
	public void test2Program7() throws Exception {
		LinkedList listX = readResource("input13.txt");
		LinkedList listY = readResource("input15.txt");

		double r = Calculator.calculateCorrelation(listX, listY);
		System.out.println("r = " + r);
		System.out.println("r2 = " + r * r);
		assertEquals(0.933306898, r, 0.000000001);
		assertEquals(0.871061766, r * r, 0.00000001);

		int n = 10;
		double t = Calculator.calculateT(r, n);
		System.out.println("T = " + t);
		double p = Calculator.calculateProbability(n - 2, t, 10);
		System.out.println("P = " + p);
		double significance = Calculator.calculateSignificance(p);
		System.out.println("Significance = " + significance);
		assertEquals(7.98203E-05, significance, 1.0);

		double E = 386;
		double b0 = Calculator.calculateRegression0(listX, listY);
		System.out.println("b0 = " + b0);
		assertEquals(-4.038881575, b0, 0.00000001);
		double b1 = Calculator.calculateRegression1(listX, listY);
		System.out.println("b1 = " + b1);
		assertEquals(0.16812665, b1, 0.00000001);
		double P = Calculator.calculatePrediction(listX, listY, E);
		System.out.println("P = " + P);
		assertEquals(60.85800528, P, 0.0000001);
		double range = Calculator.calculateRange(listX, listY, E, n);
		System.out.println("range = " + range);
		assertEquals(27.55764748, range, 3.0);
		double upi = Calculator.calculateUPI(P, range);
		System.out.println("upi = " + upi);
		assertEquals(88.41565276, upi, 3.0);
		double lpi = Calculator.calculateLPI(P, range);
		System.out.println("lpi = " + lpi);
		assertEquals(33.3003578, lpi, 3.0);
	} // end

	@Test
	public void test3Program7() throws Exception {
		LinkedList listX = readResource("input13.txt");
		LinkedList listY = readResource("input16.txt");

		double r = Calculator.calculateCorrelation(listX, listY);
		System.out.println("r = " + r);
		System.out.println("r2 = " + r * r);
		assertEquals(0.933306898, r, 0.000000001);
		assertEquals(0.871061766, r * r, 0.00000001);

		int n = 10;
		double t = Calculator.calculateT(r, n);
		System.out.println("T = " + t);
		double p = Calculator.calculateProbability(n - 2, t, 10);
		System.out.println("P = " + p);
		double significance = Calculator.calculateSignificance(p);
		System.out.println("Significance = " + significance);
		assertEquals(7.98203E-05, significance, 1.0);

		double E = 386;
		double b0 = Calculator.calculateRegression0(listX, listY);
		assertEquals(-4.038881575, b0, 0.00000001);
		System.out.println("b0 = " + b0);
		double b1 = Calculator.calculateRegression1(listX, listY);
		assertEquals(0.16812665, b1, 0.00000001);
		System.out.println("b1 = " + b1);
		double P = Calculator.calculatePrediction(listX, listY, E);
		System.out.println("P = " + P);
		assertEquals(60.85800528, P, 0.0000001);
		double range = Calculator.calculateRange(listX, listY, E, n);
		System.out.println("range = " + range);
		assertEquals(27.55764748, range, 3.0);
		double upi = Calculator.calculateUPI(P, range);
		System.out.println("upi = " + upi);
		assertEquals(88.41565276, upi, 3.0);
		double lpi = Calculator.calculateLPI(P, range);
		System.out.println("lpi = " + lpi);
		assertEquals(33.3003578, lpi, 3.0);
	} // end

	@Test
	public void test4Program7() throws Exception {
		LinkedList listX = readResource("input13.txt");
		LinkedList listY = readResource("input17.txt");

		double r = Calculator.calculateCorrelation(listX, listY);
		System.out.println("r = " + r);
		System.out.println("r2 = " + r * r);
		assertEquals(0.933306898, r, 0.000000001);
		assertEquals(0.871061766, r * r, 0.00000001);

		int n = 10;
		double t = Calculator.calculateT(r, n);
		System.out.println("t = " + t);
		double p = Calculator.calculateProbability(n - 2, t, 10);
		System.out.println("p = " + p);
		double significance = Calculator.calculateSignificance(p);
		System.out.println("Significance = " + significance);
		assertEquals(7.98203E-05, significance, 1.0);

		double E = 386;
		double b0 = Calculator.calculateRegression0(listX, listY);
		assertEquals(-4.038881575, b0, 0.00000001);
		System.out.println("b0 = " + b0);
		double b1 = Calculator.calculateRegression1(listX, listY);
		assertEquals(0.16812665, b1, 0.00000001);
		System.out.println("b1 = " + b1);
		double P = Calculator.calculatePrediction(listX, listY, E);
		System.out.println("P = " + P);
		assertEquals(60.85800528, P, 0.0000001);
		double range = Calculator.calculateRange(listX, listY, E, n);
		System.out.println("range = " + range);
		assertEquals(27.55764748, range, 3.0);
		double upi = Calculator.calculateUPI(P, range);
		System.out.println("upi = " + upi);
		assertEquals(88.41565276, upi, 3.0);
		double lpi = Calculator.calculateLPI(P, range);
		System.out.println("lpi = " + lpi);
		assertEquals(33.3003578, lpi, 3.0);
	} // end

} // end
