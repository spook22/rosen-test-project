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
public class LinkedListNode {

	private final double data;

	private LinkedListNode next;

	public LinkedListNode(double data) {
		super();
		this.data = data;
	} // end

	public double getData() {
		return data;
	} // end

	public LinkedListNode getNext() {
		return next;
	} // end

	public void setNext(LinkedListNode next) {
		this.next = next;
	} // end

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(data);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((next == null) ? 0 : next.hashCode());
		return result;
	} // end

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LinkedListNode other = (LinkedListNode) obj;
		if (Double.doubleToLongBits(data) != Double.doubleToLongBits(other.data))
			return false;
		if (next == null) {
			if (other.next != null)
				return false;
		} else if (!next.equals(other.next))
			return false;
		return true;
	} // end

} // end
