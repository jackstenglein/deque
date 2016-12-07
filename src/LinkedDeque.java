/**
 * A class that implements a Deque through a linked structure of nodes.
 * 
 * @author jstenglein 12/7/16
 * 
 * @param <E>
 *            The data type that the LinkedDeque stores.
 */
public class LinkedDeque<E> {

	private Node<E> first;
	private Node<E> last;
	private int size;

	/**
	 * Create a new LinkedDeque object.
	 */
	public LinkedDeque() {
		first = null;
		last = null;
		size = 0;
	}

	/**
	 * Adds the given Object to the front of the LinkedDeque. <br>
	 * pre: val != null <br>
	 * O(1)
	 * 
	 * @param val
	 *            The Object to add to the LinkedDeque. May not be null.
	 */
	public void addFront(E val) {
		// check precondition
		if (val == null)
			throw new IllegalArgumentException("null values may not be added to the LinkedDeque.");

		Node<E> newNode = new Node<E>(null, val, first);
		size++;

		// special case: was an empty Deque
		if (last == null)
			last = newNode;
		else
			first.prev = newNode;

		first = newNode;
	}

	/**
	 * Adds the given Object to the back of the LinkedDeque. <br>
	 * pre: val != null <br>
	 * O(1)
	 * 
	 * @param val
	 *            The Object to add to the LinkedDeque. May not be null.
	 */
	public void addBack(E val) {
		// check precondition
		if (val == null)
			throw new IllegalArgumentException("null values may not be added to the LinkedDeque.");

		Node<E> newNode = new Node<E>(last, val, null);
		size++;

		// special case: was an empty Deque
		if (first == null)
			first = newNode;
		else
			last.next = newNode;

		last = newNode;
	}

	/**
	 * Checks whether the given Object is in the LinkedDeque. <br>
	 * pre: val != null <br>
	 * O(N)
	 * 
	 * @param val
	 *            The value to check for in the LinkedDeque. May not be null.
	 * @return True if val is in the LinkedDeque. False otherwise.
	 */
	public boolean contains(E val) {
		// check precondition
		if (val == null)
			throw new IllegalArgumentException("The value to check for may not be null.");

		Node<E> temp = first;
		while (temp != null) {
			if (temp.val.equals(val))
				return true;
			else
				temp = temp.next;
		}

		return false;
	}

	public boolean equals(Object other) {
		
		if (other instanceof LinkedDeque) {
			LinkedDeque<?> otherDeque = (LinkedDeque<?>) other;
			Node<E> thisTemp = first;
			Node<?> otherTemp = otherDeque.first;

			while (otherTemp != null && thisTemp != null) {
				if (!otherTemp.val.equals(thisTemp.val))
					return false;
				else {
					otherTemp = otherTemp.next;
					thisTemp = thisTemp.next;
				}
			}

			// checked values so far were the same, so just return whether
			// we went through all values in both Deques
			return otherTemp == null && thisTemp == null;
		}

		return false;
	}

	/**
	 * Returns, but does not remove, the element in the front of the
	 * LinkedDeque. Throws an IllegalStateException if there are no elements in
	 * the LinkedDeque. <br>
	 * pre: isEmpty() == false <br>
	 * O(1)
	 * 
	 * @return The front element of the LinkedDeque
	 */
	public E getFront() {
		// check precondition
		if (isEmpty())
			throw new IllegalStateException("The LinkedDeque may not be empty.");

		return first.val;
	}

	/**
	 * Returns, but does not remove, the element in the back of the LinkedDeque.
	 * Throws an IllegalStateException if there are no elements in the
	 * LinkedDeque. <br>
	 * pre: isEmpty() == false <br>
	 * O(1)
	 * 
	 * @return The back element of the LinkedDeque
	 */
	public E getBack() {
		// check precondition
		if (isEmpty())
			throw new IllegalStateException("The LinkedDeque may not be empty.");

		return last.val;
	}

	public int hashCode() {
		int hashCode = 1;
		Node<E> temp = first;
		while (temp != null) {
			hashCode = 31 * hashCode + temp.val.hashCode();
			temp = temp.next;
		}
		return hashCode;
	}

	/**
	 * Checks whether the LinkedDeque is empty or not. <br>
	 * pre: none <br>
	 * O(1)
	 * 
	 * @return True if the LinkedDeque is empty. False otherwise.
	 */
	public boolean isEmpty() {
		return first == null;
	}

	/**
	 * Removes and returns the front element of the LinkedDeque. Throws an
	 * IllegalStateException if there are no elements in the LinkedDeque. <br>
	 * pre: isEmpty() == false <br>
	 * post: the front element of the LinkedDeque is removed <br>
	 * O(1)
	 * 
	 * @return The front element of the LinkedDeque
	 */
	public E removeFront() {
		// check precondition
		if (isEmpty())
			throw new IllegalStateException("The LinkedDeque may not be empty.");

		E val = first.val;
		first = first.next;

		// special case: now have an empty LinkedDeque
		if (first == null) {
			last = null;
		} else {
			first.prev = null;
		}

		size--;
		return val;
	}

	/**
	 * Removes and returns the back element of the LinkedDeque. Throws an
	 * IllegalStateException if there are no elements in the LinkedDeque. <br>
	 * pre: isEmpty() == false <br>
	 * post: the back element of the LinkedDeque is removed <br>
	 * O(1)
	 * 
	 * @return The back element of the LinkedDeque
	 */
	public E removeBack() {
		// check precondition
		if (isEmpty())
			throw new IllegalStateException("The LinkedDeque may not be empty.");

		E val = last.val;
		last = last.prev;

		// special case: now have an empty LinkedDeque
		if (last == null) {
			first = null;
		} else {
			last.next = null;
		}

		size--;
		return val;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		Node<E> temp = first;
		while (temp != last) {
			sb.append(temp.val);
			sb.append(", ");
			temp = temp.next;
		}

		if (last != null) {
			sb.append(last.val);
		}

		sb.append("]");
		return sb.toString();
	}

	/**
	 * A nested class that represents a Node in the LinkedDeque.
	 * 
	 * @author jstenglein 12/7/16
	 *
	 * @param <E>
	 *            The data type stored in the Node.
	 */
	private static class Node<E> {

		// instance variables
		private Node<E> prev;
		private Node<E> next;
		private E val;

		/**
		 * Create a new Node object with the given parameters. <br>
		 * pre: val != null <br>
		 * O(1)
		 * 
		 * @param prev
		 *            A reference to the previous Node.
		 * @param val
		 *            The data for this Node to store. May not be null.
		 * @param next
		 *            A reference to the next Node.
		 */
		private Node(Node<E> prev, E val, Node<E> next) {
			// check precondition
			if (val == null)
				throw new IllegalArgumentException("The value of the node may not be null.");

			this.prev = prev;
			this.next = next;
			this.val = val;
		}
	}
}