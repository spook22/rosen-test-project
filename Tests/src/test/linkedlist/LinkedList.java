package test.linkedlist;

public class LinkedList<T> {

	private Node<T> root;

	private Node<T> last;

	public LinkedList() {
	}

	public LinkedList(Node<T> root) {
		this.root = root;
		this.last = last();
	}

	@SafeVarargs
	public LinkedList(T... data) {
		if (data != null) {
			for (T datum : data) {
				add(new Node<T>(datum));
			}
		}
	}

	private Node<T> last() {
		Node<T> node = root;
		if (node != null) {
			while (node.getNext() != null) {
				node = node.getNext();
			}
		}
		return node;
	}

	public Node<T> getRoot() {
		return root;
	}

	public void add(T value) {
		add(new Node<T>(value));
	}

	public void add(Node<T> node) {
		if (root == null) {
			root = node;
		} else {
			last.setNext(node);
		}
		last = node;
	}

	public void reverse() {
		Node<T> prev = null;
		Node<T> curr = root;
		while (curr != null) {
			Node<T> next = curr.getNext();
			curr.setNext(prev);
			prev = curr;
			curr = next;
		}
		root = prev;
	}

	// This will lead to stack overflow if the size of the list is huge.
	public void recursiveReverse() {
		if (root != null) {
			LinkedList<T> temp = new LinkedList<T>();
			revAdd(temp, root);
			root = temp.root;
		}
	}

	private void revAdd(LinkedList<T> list, Node<T> node) {
		Node<T> next = node.getNext();
		if (next != null) {
			revAdd(list, next);
		}
		list.add(node);
		node.setNext(null);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("[");
		if (root != null) {
			builder.append(root);
		}
		builder.append("]");
		return builder.toString();
	}

	public void sort() {
		boolean swapped = (root != null);
		while (swapped) {
			swapped = false;
			Node<T> prev = null;
			Node<T> curr = root;
			Node<T> next = curr.getNext();
			while (next != null) {
				if (curr.getData() instanceof Comparable<?> && next.getData() instanceof Comparable<?>) {
					@SuppressWarnings("unchecked")
					Comparable<T> currData = (Comparable<T>) curr.getData();
					if (currData.compareTo(next.getData()) > 0) {
						swapped = true;
						if (prev == null) {
							root = next;
						} else {
							prev.setNext(next);
						}
						curr.setNext(next.getNext());
						next.setNext(curr);
					}
				}
				if (curr.getData().toString().compareTo(next.getData().toString()) > 0) {
				}
				prev = curr;
				curr = next;
				next = next.getNext();
			}
		}
	}

}
