/**
 * A class that implements a Deque using a native Java array.
 * 
 * @author jstenglein 12/7/16
 * 
 * @param <E>
 *            The data type that the ArrayBasedDeque stores.
 */
public class ArrayBasedDeque<E> {

	// instance variables
	private E[] con;
	private int size;
	private int first;
	private int last;

	private static final int DEFAULT_CAPACITY = 10;

	/**
	 * Creates a new ArrayBasedDeque with an initial capacity of 10. <br>
	 * pre: none <br>
	 * O(1)
	 */
	public ArrayBasedDeque() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Creates a new ArrayBasedDeque with the given initial capacity. <br>
	 * pre: initialCapacity > 0 <br>
	 * O(1)
	 * 
	 * @param initialCapacity
	 *            The amount of storage to initially create for this
	 *            ArrayBasedDeque. Must be greater than 0.
	 */
	public ArrayBasedDeque(int initialCapacity) {
		// check precondition
		if (initialCapacity <= 0)
			throw new IllegalArgumentException("initialCapacity must be greater than 0");

		con = getContainer(initialCapacity);
		last = -1;
		first = 1;
	}

	/**
	 * Adds the given Object to the front of the ArrayBasedDeque. <br>
	 * pre: val != null <br>
	 * O(1) amortized
	 * 
	 * @param val
	 *            The Object to add to the ArrayBasedDeque. May not be null.
	 */
	public void addFront(E val) {
		// check precondition
		if (val == null)
			throw new IllegalArgumentException("The value to add may not be null.");

		// resize if necessary
		if (size == con.length)
			ensureCapacity(size * 2 + 1);

		// find the new spot for first and set the value
		if (first == 0) {
			first = con.length - 1;
		} else {
			first--;
		}
		con[first] = val;

		// special case: was an empty ArrayBasedDeque
		if (size == 0) {
			last = first;
		}

		size++;
	}

	/**
	 * Adds the given Object to the back of the ArrayBasedDeque. <br>
	 * pre: val != null <br>
	 * O(1) amortized
	 * 
	 * @param val
	 *            The Object to add to the ArrayBasedDeque. May not be null.
	 */
	public void addBack(E val) {
		// check precondition
		if (val == null)
			throw new IllegalArgumentException("The value to add may not be null.");

		// resize if necessary
		if (size == con.length)
			ensureCapacity(size * 2 + 1);

		// find the new spot for last and set the value
		last = (last + 1) % con.length;
		con[last] = val;

		// special case: was an empty ArrayBasedDeque
		if (size == 0) {
			first = last;
		}

		size++;
	}

	/**
	 * Checks whether the given Object is in the ArrayBasedDeque. <br>
	 * pre: val != null <br>
	 * O(N)
	 * 
	 * @param val
	 *            The value to check for in the ArrayBasedDeque. May not be
	 *            null.
	 * @return True if val is in the ArrayBasedDeque. False otherwise.
	 */
	public boolean contains(E val) {
		// check precondition
		if (val == null)
			throw new IllegalArgumentException("The value to search for may not be null.");

		int index = first;
		for (int i = 0; i < size; i++) {
			if (val.equals(con[index]))
				return true;
			else
				index = (index + 1) % con.length;
		}

		return false;
	}

	/**
	 * Increases the capacity of the ArrayBasedDeque if necessary to hold at
	 * least the given number of elements. <br>
	 * pre: none
	 * 
	 * @param capacity
	 *            The minimum number of elements the ArrayBasedDeque must be
	 *            able to hold.
	 */
	public void ensureCapacity(int capacity) {
		if (con.length < capacity) {
			resize(capacity);
		}
	}

	public boolean equals(Object other) {
		if (other instanceof ArrayBasedDeque) {
			ArrayBasedDeque<?> otherDeque = (ArrayBasedDeque<?>) other;

			if (otherDeque.size != size)
				return false;
			else {
				int index = first;
				while (index != last) {
					if (!otherDeque.con[index].equals(con[index]))
						return false;

					index = (index + 1) % con.length;
				}

				return true;
			}
		} else if (other instanceof LinkedDeque) {
			return toString().equals(other.toString());
		}

		return false;
	}

	/**
	 * Returns, but does not remove, the element in the front of the
	 * ArrayBasedDeque. Throws an IllegalStateException if there are no elements
	 * in the ArrayBasedDeque. <br>
	 * pre: isEmpty() == false <br>
	 * O(1)
	 * 
	 * @return The front element of the ArrayBasedDeque
	 */
	public E getFront() {
		// check precondition
		if (isEmpty())
			throw new IllegalStateException("There must be elements in the ArrayBasedDeque.");

		return con[first];
	}

	/**
	 * Returns, but does not remove, the element in the back of the
	 * ArrayBasedDeque. Throws an IllegalStateException if there are no elements
	 * in the ArrayBasedDeque. <br>
	 * pre: isEmpty() == false <br>
	 * O(1)
	 * 
	 * @return The back element of the ArrayBasedDeque
	 */
	public E getBack() {
		// check precondition
		if (isEmpty())
			throw new IllegalStateException("There must be elements in the ArrayBasedDeque.");

		return con[last];
	}

	public int hashCode() {
		int hashCode = 1;
		int index = first;
		if (size != 0) {
			while (index != last) {
				hashCode = 31 * hashCode + con[index].hashCode();
				index = (index + 1) % con.length;
			}
			hashCode = 31 * hashCode + con[last].hashCode();
		}
		return hashCode;
	}

	/**
	 * Checks whether the ArrayBasedDeque is empty or not. <br>
	 * pre: none <br>
	 * O(1)
	 * 
	 * @return True if the ArrayBasedDeque is empty. False otherwise.
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Removes and returns the front element of the ArrayBasedDeque. Throws an
	 * IllegalStateException if there are no elements in the ArrayBasedDeque.
	 * <br>
	 * pre: isEmpty() == false <br>
	 * post: the front element of the ArrayBasedDeque is removed <br>
	 * O(1)
	 * 
	 * @return The front element of the ArrayBasedDeque
	 */
	public E removeFront() {
		// check precondition
		if (isEmpty())
			throw new IllegalStateException("There must be elements in the ArrayBasedDeque.");

		E val = con[first];
		con[first] = null;
		first = (first + 1) % con.length;
		size--;

		// special case: we are making the ArrayBasedDeque empty
		if (size == 0) {
			first = 1;
			last = -1;
		}

		return val;
	}

	/**
	 * Removes and returns the back element of the ArrayBasedDeque. Throws an
	 * IllegalStateException if there are no elements in the ArrayBasedDeque.
	 * <br>
	 * pre: isEmpty() == false <br>
	 * post: the back element of the ArrayBasedDeque is removed <br>
	 * O(1)
	 * 
	 * @return The back element of the ArrayBasedDeque
	 */
	public E removeBack() {
		// check precondition
		if (isEmpty())
			throw new IllegalStateException("There must be elements in the ArrayBasedDeque.");

		E val = con[last];
		con[last] = null;
		last--;
		if (last == -1)
			last = con.length - 1;
		size--;

		// special case: we are making the ArrayBasedDeque empty
		if (size == 0) {
			first = 1;
			last = -1;
		}

		return val;
	}

	/**
	 * Creates and returns a new array of type E with the given capacity. <br>
	 * pre: satisfied by calling methods <br>
	 * O(capacity)
	 * 
	 * @param capacity
	 *            The amount of storage to create. Must be greater than 0.
	 * @return A new array of type E with the given capacity.
	 */
	private E[] getContainer(int capacity) {
		return (E[]) new Object[capacity];
	}

	/**
	 * Resizes the internal array to fit the desired capacity. <br>
	 * pre: capacity >= size <br>
	 * O(N)
	 * 
	 * @param capacity
	 *            The new capacity for the internal array.
	 */
	private void resize(int capacity) {

		// check precondition
		if (capacity < size)
			throw new IllegalArgumentException("New capacity cuts off values from the deque");

		E[] temp = getContainer(capacity);
		int limit = Math.min(capacity, con.length);
		int index = first;

		for (int i = 0; i < limit; i++) {
			temp[i] = con[index];
			index = (index + 1) % con.length;
		}

		first = 0;
		last = size - 1;
		con = temp;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		if (size != 0) {
			int index = first;
			while (index != last) {
				sb.append(con[index]);
				sb.append(", ");
				index = (index + 1) % con.length;
			}

			sb.append(con[last]);
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Trims the capacity of the ArrayBasedDeque to the current size. <br>
	 * pre: none <br>
	 * O(N)
	 */
	public void trimToSize() {
		if (size < con.length)
			resize(size);
	}
}
