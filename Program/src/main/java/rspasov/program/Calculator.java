package rspasov.program;

import rspasov.program.list.LinkedList;
import rspasov.program.list.LinkedListNode;

/**
 * Program Assignment: Regression, Correlation, and Prediction Calculator
 * <p>
 * Description: This program is used for calculating the regression parameters, correlation, and prediction used in PSP2.
 * 
 * @author Rosen Spasov
 * @version 1.0
 */
public class Calculator {

	public static double calculateMean(LinkedList list) {
		if (list.getSize() == 0) {
			return 0;
		}
		LinkedListNode currentNode = list.getHead();
		double sum = 0;
		while (currentNode != null) {
			sum += currentNode.getData();
			currentNode = currentNode.getNext();
		}
		return sum / list.getSize();
	} // end

	public static double calculateVariance(LinkedList list) {
		if (list.getSize() == 1) { // Otherwise division by zero.
			return 0;
		}
		double mean = calculateMean(list);
		LinkedListNode currentNode = list.getHead();
		double sum = 0;
		while (currentNode != null) {
			sum += Math.pow(currentNode.getData() - mean, 2);
			currentNode = currentNode.getNext();
		}
		return sum / (list.getSize() - 1);
	} // end

	public static double calculateStandardDeviation(LinkedList list) {
		if (list.getSize() == 1) { // Otherwise division by zero.
			return 0;
		}
		return Math.sqrt(calculateVariance(list));
	} // end

	public static double calculateRegression0(LinkedList first, LinkedList second) {
		double firstMean = calculateMean(first);
		double secondMean = calculateMean(second);
		double regression1 = calculateRegression1(first, second);
		return secondMean - (regression1 * firstMean);
	} // end

	public static double calculateRegression1(LinkedList first, LinkedList second) {
		int size = first.getSize();
		double firstMean = calculateMean(first);
		double secondMean = calculateMean(second);
		return (calculateSumXY(first, second) - (size * firstMean * secondMean)) / (calculateSquaredSum(first) - (size * Math.pow(firstMean, 2)));
	} // end

	public static double calculateSquaredSum(LinkedList list) {
		LinkedListNode currentNodeFirst = list.getHead();
		double sum = 0;
		while (currentNodeFirst != null) {
			sum += Math.pow(currentNodeFirst.getData(), 2);
			currentNodeFirst = currentNodeFirst.getNext();
		}
		return sum;
	} // end

	public static double calculateSum(LinkedList list) {
		LinkedListNode currentNodeFirst = list.getHead();
		double sum = 0;
		while (currentNodeFirst != null) {
			sum += currentNodeFirst.getData();
			currentNodeFirst = currentNodeFirst.getNext();
		}
		return sum;
	} // end

	public static double calculateSumXY(LinkedList first, LinkedList second) {
		LinkedListNode currentNodeFirst = first.getHead();
		LinkedListNode currentNodeSecond = second.getHead();
		double sum = 0;
		while (currentNodeFirst != null && currentNodeSecond != null) {
			sum += currentNodeFirst.getData() * currentNodeSecond.getData();
			currentNodeFirst = currentNodeFirst.getNext();
			currentNodeSecond = currentNodeSecond.getNext();
		}
		return sum;
	} // end

	public static double calculateCorrelation(LinkedList first, LinkedList second) {
		int size = first.getSize();
		double up = (size * calculateSumXY(first, second)) - (calculateSum(first) * calculateSum(second));
		double a = (size * calculateSquaredSum(first)) - Math.pow(calculateSum(first), 2);
		double b = (size * calculateSquaredSum(second)) - Math.pow(calculateSum(second), 2);
		double down = Math.sqrt(a * b);
		return up / down;
	} // end

	public static double calculatePrediction(LinkedList first, LinkedList second, double estimatedProxy) {
		return calculateRegression0(first, second) + calculateRegression1(first, second) * estimatedProxy;
	} // end

	public static LinkedList createLogarithmicList(LinkedList list) {
		LinkedList result = new LinkedList();
		LinkedListNode currentNode = list.getHead();
		while (currentNode != null) {
			result.add(Math.log(currentNode.getData()));
			currentNode = currentNode.getNext();
		}
		return result;
	} // end

	public static LinkedList calculateItemsPerPart(LinkedList first, LinkedList second) {
		LinkedList result = new LinkedList();
		if (first.getSize() != second.getSize()) {
			throw new RuntimeException("The two lists should have one and the same size. This is not the case currently.");
		}
		LinkedListNode currentNodeFirst = first.getHead();
		LinkedListNode currentNodeSecond = second.getHead();
		while (currentNodeFirst != null && currentNodeSecond != null) {
			result.add(currentNodeFirst.getData() / currentNodeSecond.getData());
			currentNodeFirst = currentNodeFirst.getNext();
			currentNodeSecond = currentNodeSecond.getNext();
		}
		return result;
	} // end

	public static double calculateVS(LinkedList list) {
		return calculateAntiLogarithm(calculateMean(list) - 2 * calculateStandardDeviation(list));
	} // end

	public static double calculateS(LinkedList list) {
		return calculateAntiLogarithm(calculateMean(list) - calculateStandardDeviation(list));
	} // end

	public static double calculateM(LinkedList list) {
		return calculateAntiLogarithm(calculateMean(list));
	} // end

	public static double calculateL(LinkedList list) {
		return calculateAntiLogarithm(calculateMean(list) + calculateStandardDeviation(list));
	} // end

	public static double calculateVL(LinkedList list) {
		return calculateAntiLogarithm(calculateMean(list) + 2 * calculateStandardDeviation(list));
	} // end

	public static double calculateAntiLogarithm(double value) {
		return Math.exp(value);
	} // end

	public static long calculateFactorial(int value) {
		if (value < 0) {
			throw new RuntimeException("Cannot calculate factorial for the negative number: " + value + ". The number should be non-negative.");
		}
		long result = 1;
		for (long i = result; i <= value; i++) {
			result *= i;
		}
		return result;
	} // end

	public static boolean isEven(int value) {
		return value % 2 == 0;
	} // end

	public static double calculateGamma(int value) {
		if (isEven(value)) {
			return calculateFactorial(value / 2 - 1);
		} else if (value == 1) {
			return Math.sqrt(Math.PI);
		} else {
			int temp = value - 2;
			return (double) temp / 2 * calculateGamma(temp);
		}
	} // end

	public static double calculateStaticDofFunction(int dof) {
		return calculateGamma(dof + 1) / (Math.pow(dof * Math.PI, 0.5) * calculateGamma(dof));
	} // end

	public static double calculateDynamicDofFunction(int dof, double x) {
		return Math.pow(1 + (x * x / dof), -(dof + 1) / 2);
	} // end

	public static double calculateDofFunction(int dof, double x) {
		return calculateStaticDofFunction(dof) * calculateDynamicDofFunction(dof, x);
	} // end

	private static double calculateSegmentStep(double x, int segments) {
		return x / segments;
	} // end

	private static double error = 0.0000001;

	public static double calculateProbability(int dof, double x, int segments) {
		double first = calculateTempProbability(dof, x, segments);
		double second = calculateTempProbability(dof, x, segments * 2);
		double delta = first - second;
		if (delta > error) {
			return calculateProbability(dof, x, segments * 2);
		}
		return second;
	} // end

	public static double calculateTempProbability(int dof, double x, int segments) {
		double segmentStep = calculateSegmentStep(x, segments);
		double sum = 0.0;
		for (int segment = 0; segment <= segments; segment++) {
			double nextX = segmentStep * segment;
			sum += multiplier(segment, segments) * calculateDofFunction(dof, nextX);
		}
		return (segmentStep / 3.0) * sum;
	} // end

	private static int multiplier(int segment, int segments) {
		if (segment < 0 || segment > segments) {
			throw new RuntimeException(String.format("Segment %s should be between 0 and %s.", segment, segments));
		}
		if (segment == 0 || segment == segments) {
			return 1;
		} else {
			if (isEven(segment)) {
				return 2;
			} else {
				return 4;
			}
		}
	} // end
} // end
