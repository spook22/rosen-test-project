package rspasov.problems;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CalculateDistanceBetweenNodes {

	public static class BinaryTree {
		private int data;
		private BinaryTree left;
		private BinaryTree right;

		public BinaryTree(int value) {
			data = value;
		}

		public void add(int value) {
			if (data <= value) {
				if (right != null) {
					right.add(value);
				} else {
					right = new BinaryTree(value);
				}
			} else {
				if (left != null) {
					left.add(value);
				} else {
					left = new BinaryTree(value);
				}
			}
		}

		public BinaryTree find(int value, Queue<BinaryTree> tree) {
			tree.add(this);
			if (data == value) {
				return this;
			} else if (data < value) {
				return (right != null) ? right.find(value, tree) : null;
			} else {
				return (left != null) ? left.find(value, tree) : null;
			}
		}

		@Override
		public String toString() {
			return data + "(" + left + ", " + right + ")";
		}
	}

	public static int calculateDistance(BinaryTree tree, int node1, int node2) {
		if (tree == null) {
			return -1;
		}
		Queue<BinaryTree> queue1 = new ConcurrentLinkedQueue<>();
		Queue<BinaryTree> queue2 = new ConcurrentLinkedQueue<>();
		BinaryTree firstNode = tree.find(node1, queue1);
		BinaryTree secondNode = tree.find(node2, queue2);
		if (firstNode == null || secondNode == null) {
			return -1;
		}

		while (queue1.peek() == queue2.peek()) {
			queue1.poll();
			queue2.poll();
		}

		return queue1.size() + queue2.size();
	}
}
