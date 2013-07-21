package rspasov.program.list;

/**
 * Program Assignment: Regression, Correlation, and Prediction Calculator
 * <p>
 * Description: This program is used for calculating the regression parameters,
 * correlation, and prediction used in PSP2.
 * 
 * @author Rosen Spasov
 * @version 1.0
 */
public class LinkedList {

	private LinkedListNode head;

	private LinkedListNode tail;

	private int size;

	public LinkedListNode getHead() {
		return head;
	} // end

	public int getSize() {
		return size;
	} // end

	public void add(double data) {
		if (head == null) {
			head = new LinkedListNode(data);
			tail = head;
		} else {
			LinkedListNode next = new LinkedListNode(data);
			tail.setNext(next);
			tail = next;
		}
		size++;
	} // end

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		LinkedListNode currentNode = head;
		while (currentNode != null) {
			if (currentNode != head) {
				builder.append(", ");
			}
			builder.append(currentNode.getData());
			currentNode = currentNode.getNext();
		}
		return builder.toString();
	} // end

} // end
