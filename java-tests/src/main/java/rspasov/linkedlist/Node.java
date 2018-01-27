package rspasov.linkedlist;

public class Node<T> {

	private T data;

	private Node<T> next;

	public Node(T data) {
		super();
		this.data = data;
	}

	public Node(T data, Node<T> next) {
		super();
		this.data = data;
		this.next = next;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Node<T> getNext() {
		return next;
	}

	public void setNext(Node<T> next) {
		this.next = next;
	}

	public void add(Node<T> node) {
		if (next == null) {
			next = node;
		} else {
			next.add(node);
		}
	}

	@Override
	public String toString() {
		if (next == null) {
			return String.valueOf(data);
		} else {
			return String.valueOf(data) + ", " + next;
		}
	}

}
