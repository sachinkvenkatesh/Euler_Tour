
/**
 * Class to represent a vertex of a graph
 * 
 *
 */

import java.util.*;

public class Vertex {
	public enum Color {
		WHITE, GRAY, BLACK
	}

	public int name; // name of the vertex
	public boolean seen; // flag to check if the vertex has already been visited
	public Vertex parent; // parent of the vertex
	public int distance; // distance to the vertex from the source vertex
	public List<Edge> Adj, revAdj; // adjacency list; use LinkedList or
									// ArrayList
	public Color color;
	public int degree;	// degree of the vertex
	public int AdjIndex;//index to indicate the edge in the Adjacency list of the edges
	public Node<Edge> curEdgeIndex;	//to store its index or position in the doublyLinkedList

	/**
	 * Constructor for the vertex
	 * 
	 * @param n
	 *            : int - name of the vertex
	 */
	Vertex(int n) {
		name = n;
		seen = false;
		parent = null;
		color = Color.WHITE;
		Adj = new ArrayList<Edge>();
		revAdj = new ArrayList<Edge>(); /* only for directed graphs */
		distance = 0;
		degree = 0;
		AdjIndex = 0;
		curEdgeIndex = new Node<>(null,null,null);
	}

	/**
	 * Method to represent a vertex by its name
	 */
	public String toString() {
		return Integer.toString(name);
	}
}
