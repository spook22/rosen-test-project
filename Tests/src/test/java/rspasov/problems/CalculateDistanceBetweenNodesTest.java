package rspasov.problems;

import org.junit.Test;

import rspasov.problems.CalculateDistanceBetweenNodes.BinaryTree;

public class CalculateDistanceBetweenNodesTest {

	@Test
	public void test() {
		BinaryTree tree = null;
		int[] values = new int[] { 5, 6, 3, 1, 2, 4 };
		for (int value : values) {
			if (tree == null) {
				tree = new BinaryTree(value);
			} else {
				tree.add(value);
			}
		}
		System.out.println(tree);
		System.out.println(CalculateDistanceBetweenNodes.calculateDistance(tree, 3, 2));

		System.out.println();
		values = new int[] { 9, 7, 5, 3, 1 };
		tree = null;
		for (int value : values) {
			if (tree == null) {
				tree = new BinaryTree(value);
			} else {
				tree.add(value);
			}
		}
		System.out.println(tree);
		System.out.println("Distance: " + CalculateDistanceBetweenNodes.calculateDistance(tree, 7, 20));
	}

}
