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
public class CalculatorTest {

	@Test
	public void testCalculateMean() throws Exception {
		InputStream input = this.getClass().getResourceAsStream("input03.txt");
		InputReader reader = new InputReader(input);
		LinkedList list = reader.read();
		double mean = Calculator.calculateMean(list);
		assertEquals(638.9, mean, 0.0);
	} // end

	@Test
	public void testCalculateStandardDeviation() throws Exception {
		InputStream input = this.getClass().getResourceAsStream("input03.txt");
		InputReader reader = new InputReader(input);
		LinkedList list = reader.read();
		double deviation = Calculator.calculateStandardDeviation(list);
		assertEquals(625.633981, deviation, 0.000001);
	} // end

	@Test
	public void testCalculateSumAndMean() throws Exception {
		InputStream input = this.getClass().getResourceAsStream("input01.txt");
		InputReader reader = new InputReader(input);
		LinkedList first = reader.read();
		double sum = Calculator.calculateSum(first);
		assertEquals(3828, sum, 0.0);
		double mean = Calculator.calculateMean(first);
		assertEquals(382.8, mean, 0.0);

		input = this.getClass().getResourceAsStream("input03.txt");
		reader = new InputReader(input);
		LinkedList second = reader.read();
		sum = Calculator.calculateSum(second);
		assertEquals(6389, sum, 0.0);
		mean = Calculator.calculateMean(second);
		assertEquals(638.9, mean, 0.0);
	} // end

	@Test
	public void testCalculateSquaredSum() throws Exception {
		InputStream input = this.getClass().getResourceAsStream("input01.txt");
		InputReader reader = new InputReader(input);
		LinkedList first = reader.read();
		double sum = Calculator.calculateSquaredSum(first);
		assertEquals(2540284, sum, 0.0);

		input = this.getClass().getResourceAsStream("input03.txt");
		reader = new InputReader(input);
		LinkedList second = reader.read();
		sum = Calculator.calculateSquaredSum(second);
		assertEquals(7604693, sum, 0.0);
	} // end

	@Test
	public void testCalculateSumXY() throws Exception {
		InputStream input = this.getClass().getResourceAsStream("input01.txt");
		InputReader reader = new InputReader(input);
		LinkedList first = reader.read();

		input = this.getClass().getResourceAsStream("input03.txt");
		reader = new InputReader(input);
		LinkedList second = reader.read();

		double sum = Calculator.calculateSumXY(first, second);
		assertEquals(4303108, sum, 0.0);
	} // end

	@Test
	public void testCalculateRegression0() throws Exception {
		InputStream input = this.getClass().getResourceAsStream("input01.txt");
		InputReader reader = new InputReader(input);
		LinkedList first = reader.read();

		input = this.getClass().getResourceAsStream("input03.txt");
		reader = new InputReader(input);
		LinkedList second = reader.read();

		double regression = Calculator.calculateRegression0(first, second);
		assertEquals(-22.5525, regression, 0.00004);
	} // end

	@Test
	public void testCalculateRegression1() throws Exception {
		InputStream input = this.getClass().getResourceAsStream("input01.txt");
		InputReader reader = new InputReader(input);
		LinkedList first = reader.read();

		input = this.getClass().getResourceAsStream("input03.txt");
		reader = new InputReader(input);
		LinkedList second = reader.read();

		double regression = Calculator.calculateRegression1(first, second);
		assertEquals(1.727932, regression, 0.0000005);
	} // end

	@Test
	public void testCalculateCorrelation() throws Exception {
		InputStream input = this.getClass().getResourceAsStream("input01.txt");
		InputReader reader = new InputReader(input);
		LinkedList first = reader.read();

		input = this.getClass().getResourceAsStream("input03.txt");
		reader = new InputReader(input);
		LinkedList second = reader.read();

		double correlation = Calculator.calculateCorrelation(first, second);
		assertEquals(0.9545, correlation, 0.0001);
		assertEquals(0.9111, Math.pow(correlation, 2), 0.0001);
	} // end

	@Test
	public void testCalculatePrediction() throws Exception {
		InputStream input = this.getClass().getResourceAsStream("input01.txt");
		InputReader reader = new InputReader(input);
		LinkedList first = reader.read();

		input = this.getClass().getResourceAsStream("input03.txt");
		reader = new InputReader(input);
		LinkedList second = reader.read();

		double prediction = Calculator.calculatePrediction(first, second, 386);
		assertEquals(644.4294, prediction, 0.0001);
	} // end

	@Test
	public void testCalculateItemsPerPart() throws Exception {
		InputStream input = this.getClass().getResourceAsStream("input05.txt");
		InputReader reader = new InputReader(input);
		LinkedList first = reader.read();

		input = this.getClass().getResourceAsStream("input06.txt");
		reader = new InputReader(input);
		LinkedList second = reader.read();

		input = this.getClass().getResourceAsStream("input07.txt");
		reader = new InputReader(input);
		LinkedList third = reader.read();

		LinkedList result = Calculator.calculateItemsPerPart(first, second);

		LinkedListNode currentNodeThird = third.getHead();
		LinkedListNode currentNodeResult = result.getHead();
		while (currentNodeThird != null && currentNodeResult != null) {
			assertEquals(currentNodeThird.getData(), currentNodeResult.getData(), 0.0001);
			currentNodeThird = currentNodeThird.getNext();
			currentNodeResult = currentNodeResult.getNext();
		}
	} // end

	@Test
	public void testCreateLogarithmicList() throws Exception {
		InputStream input = this.getClass().getResourceAsStream("input07.txt");
		InputReader reader = new InputReader(input);
		LinkedList list = reader.read();

		input = this.getClass().getResourceAsStream("input08.txt");
		reader = new InputReader(input);
		LinkedList expected = reader.read();

		LinkedList result = Calculator.createLogarithmicList(list);

		LinkedListNode currentNodeExpected = expected.getHead();
		LinkedListNode currentNodeResult = result.getHead();
		while (currentNodeExpected != null && currentNodeResult != null) {
			assertEquals(currentNodeExpected.getData(), currentNodeResult.getData(), 0.0001);
			currentNodeExpected = currentNodeExpected.getNext();
			currentNodeResult = currentNodeResult.getNext();
		}
	} // end

	@Test
	public void testCalculateMeanUsingLogList() throws Exception {
		InputStream input = this.getClass().getResourceAsStream("input08.txt");
		InputReader reader = new InputReader(input);
		LinkedList list = reader.read();

		double mean = Calculator.calculateMean(list);
		assertEquals(2.8015, mean, 0.0001);
	} // end

	@Test
	public void testCalculateVarianceUsingLogList() throws Exception {
		InputStream input = this.getClass().getResourceAsStream("input08.txt");
		InputReader reader = new InputReader(input);
		LinkedList list = reader.read();

		double variance = Calculator.calculateVariance(list);
		assertEquals(0.4363, variance, 0.0001);
	} // end

	@Test
	public void testCalculateStandardDeviationUsingLogList() throws Exception {
		InputStream input = this.getClass().getResourceAsStream("input08.txt");
		InputReader reader = new InputReader(input);
		LinkedList list = reader.read();

		double deviation = Calculator.calculateStandardDeviation(list);
		assertEquals(0.6605, deviation, 0.0001);
	} // end

	@Test
	public void testCalculateRanges() throws Exception {
		InputStream input = this.getClass().getResourceAsStream("input08.txt");
		InputReader reader = new InputReader(input);
		LinkedList list = reader.read();

		double vs = Calculator.calculateVS(list);
		assertEquals(4.3953, vs, 0.0002);

		double s = Calculator.calculateS(list);
		assertEquals(8.5081, s, 0.0003);

		double m = Calculator.calculateM(list);
		assertEquals(16.4696, m, 0.0002);

		double l = Calculator.calculateL(list);
		assertEquals(31.8811, l, 0.002);

		double vl = Calculator.calculateVL(list);
		assertEquals(61.7137, vl, 0.002);
	} // end

	@Test
	public void testCalculateFactorial() throws Exception {
		assertEquals(1, Calculator.calculateFactorial(0));
		assertEquals(1, Calculator.calculateFactorial(1));
		assertEquals(2, Calculator.calculateFactorial(2));
		assertEquals(120, Calculator.calculateFactorial(5));
		assertEquals(2432902008176640000l, Calculator.calculateFactorial(20));
	} // end

	@Test(expected = RuntimeException.class)
	public void testCalculateFactorialWithNegativeNumber() throws Exception {
		Calculator.calculateFactorial(-1);
	} // end

	@Test
	public void testCalculateGammaForNonInteger() throws Exception {
		assertEquals(Math.sqrt(Math.PI), Calculator.calculateGamma(1), 0.0000000000001);
		// Even
		assertEquals(1, Calculator.calculateGamma(2), 0.0000000000001);
		assertEquals(1, Calculator.calculateGamma(4), 0.0000000000001);
		assertEquals(2, Calculator.calculateGamma(6), 0.0000000000001);
		assertEquals(6, Calculator.calculateGamma(8), 0.0000000000001);
		assertEquals(24, Calculator.calculateGamma(10), 0.0000000000001);
		assertEquals(120, Calculator.calculateGamma(12), 0.0000000000001);
		// Odd
		assertEquals(1.0 / 2 * Math.sqrt(Math.PI), Calculator.calculateGamma(3), 0.0000000000001);
		assertEquals(1.329340388179137, Calculator.calculateGamma(5), 0.0000000000001);
		assertEquals(3.323350970447843, Calculator.calculateGamma(7), 0.0000000000001);
		assertEquals(11.63172839656745, Calculator.calculateGamma(9), 0.0000000000001);
		assertEquals(52.34277778455353, Calculator.calculateGamma(11), 0.0000000000001);
		assertEquals(287.8852778150444, Calculator.calculateGamma(13), 0.0000000000001);
	} // end

	@Test(expected = RuntimeException.class)
	public void testCalculateGammaForIntegerWithInvalidNumber() throws Exception {
		Calculator.calculateGamma(0);
	} // end

	@Test
	public void testCalculateStaticDofFunction() throws Exception {
		assertEquals(0.388035, Calculator.calculateStaticDofFunction(9), 0.000001);
		assertEquals(0.389108, Calculator.calculateStaticDofFunction(10), 0.000001);
		assertEquals(0.395632, Calculator.calculateStaticDofFunction(30), 0.000001);
	} // end

	@Test
	public void testCalculateDynamicDofFunction() throws Exception {
		assertEquals(1.00000, Calculator.calculateDynamicDofFunction(9, 0.00), 0.00001);
		assertEquals(0.99330, Calculator.calculateDynamicDofFunction(9, 0.11), 0.00001);
		assertEquals(0.94164, Calculator.calculateDynamicDofFunction(9, 0.33), 0.00001);
		assertEquals(0.84765, Calculator.calculateDynamicDofFunction(9, 0.55), 0.00001);
		assertEquals(0.72688, Calculator.calculateDynamicDofFunction(9, 0.77), 0.00001);
		assertEquals(0.97354, Calculator.calculateDynamicDofFunction(9, 0.22), 0.00001);
		assertEquals(0.89905, Calculator.calculateDynamicDofFunction(9, 0.44), 0.00001);
		assertEquals(0.78952, Calculator.calculateDynamicDofFunction(9, 0.66), 0.00001);
	} // end

	@Test
	public void testCalculateDofFunction() throws Exception {
		assertEquals(0.38803, Calculator.calculateDofFunction(9, 0.00), 0.00001);
		assertEquals(0.30636, Calculator.calculateDofFunction(9, 0.66), 0.00001);
		assertEquals(0.23142, Calculator.calculateDofFunction(9, 0.99), 0.00001);
		assertEquals(0.20652, Calculator.calculateDofFunction(9, 1.10), 0.00001);
	} // end

	@Test
	public void testCalculateProbability() throws Exception {
		assertEquals(0.35006, Calculator.calculateProbability(9, 1.1000, 2), 0.00001);
		assertEquals(0.36757, Calculator.calculateProbability(10, 1.1812, 2), 0.01);
		assertEquals(0.49500, Calculator.calculateProbability(30, 2.7500, 2), 0.01);
	} // end

	@Test
	public void testCalculateTempProbability() throws Exception {
		assertEquals(0.35006000, Calculator.calculateTempProbability(9, 1.1, 10), 0.00001);
		assertEquals(0.35005864, Calculator.calculateTempProbability(9, 1.1, 20), 0.000001);
	} // end

} // end
