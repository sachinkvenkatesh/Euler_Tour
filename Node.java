
public class Node<T> {
	T value;
	Node<T> next;
	Node<T> prev;

	/**
	 * Constructor to this class
	 * 
	 * @param val:
	 *            value of the node
	 * @param nxt:
	 *            Next node connected to this node
	 * @param prv:
	 *            Previous node connected to this node
	 */
	Node(T val, Node<T> nxt, Node<T> prv) {
		value = val;
		next = nxt;
		prev = prv;
	}

	/**
	 * Set the next node field of this field. Connect to current node to the
	 * next node.
	 * 
	 * @param n:
	 *            Node to be linked to the next field
	 */
	public void setNext(Node<T> n) {
		this.next = n;
	}

	/**
	 * Set the prev field for the current node. Indicating previous node
	 * connected to this node.
	 * 
	 * @param p:
	 *            Node to be connected
	 */
	public void setPrev(Node<T> p) {
		this.prev = p;
	}

	/**
	 * To get the current node details.
	 * 
	 * @return: Node
	 */
	public Node<T> get() {
		return this;
	}

	/**
	 * To print the value of the node
	 * 
	 * @return: String
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(value + " ");
		return s.toString();
	}
}
