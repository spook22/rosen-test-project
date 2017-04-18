package rspasov.problems;

public class ReverseLinkedList {
	
	static class Node {
		int value;
		Node next;
		Node(int value, Node next) {
			this.value = value;
			this.next = next;
		}
		void print() {
			Node current = this;
			while (current != null) {
				System.out.print(current.value);
				current = current.next;
			}
			System.out.println();
		}
		public String toString() {
			return String.valueOf(value);
		}
	}

	public static void main(String[] args) {

		Node node = new Node(3, null);
		node = new Node(2, node);
		node = new Node(1, node);
		
		node.print();
		reverse(node).print();

	}

	private static Node reverse(Node node) {
		if (node == null) {
			return node;
		}
		Node prev = null, current = null;
		Node next = node;
		do {
			current = next;
			next = current.next;
			current.next = prev;
			prev = current;
		} while (next != null);
		return current;
	}

}
