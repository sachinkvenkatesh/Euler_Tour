
public class DoublyLinkedList<T> {

	Node<T> head;
	Node<T> tail;
	int size;

	/**
	 * To set the size field
	 * 
	 * @param size:
	 *            int
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Constructor - initialize head and tail to null and size to zero
	 */
	public DoublyLinkedList() {
		super();
		head = new Node<>(null, null, null);
		tail = new Node<>(null, null, head);
		head.next = tail;
		size = 0;
	}

	/**
	 * To set the head of the list
	 * 
	 * @param h:
	 *            Node
	 */
	public void setHead(Node<T> h) {
		this.head = h;
	}

	/**
	 * To set the tail of the list
	 * 
	 * @param t:
	 *            Node
	 */
	public void setTail(Node<T> t) {
		this.tail = t;
	}

	/**
	 * To clear the list - set head and tail to null and size to zero
	 */
	public void clear() {
		head = new Node<>(null, null, null);
		tail = new Node<>(null, null, head);
		head.next = tail;
		size = 0;
	}

	/**
	 * To add an element to the list
	 * 
	 * @param x:
	 *            type T
	 */
	public void add(T x) {
		// First element will be head of the list
		if (size == 0) {
			head.value = x;
			size++;
			return;
		}
		// Second element will be tail of the list
		if (size == 1) {
			tail.value = x;
			size++;
			return;
		}
		// rest elements are added at the end of the list
		Node<T> newNode = new Node<>(x, null, tail);
		tail.next = newNode;
		tail = newNode;
		size++;
		return;
	}

	/**
	 * To check if the list is empty or not
	 * 
	 * @return: size
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * To obtain the current size of the list
	 * 
	 * @return: size
	 */
	public int size() {
		return size;
	}

	/**
	 * To print the list
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		Node<T> temp = head;
		while (temp != null) {
			str.append(temp.value + "\n");
			temp = temp.next;
		}
		return str.toString();
	}
}
