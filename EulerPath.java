import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class EulerPath {

	/**
	 * DFS - to check if the Graph is connected or not
	 * 
	 * @param u:
	 *            Vertex - DFS on this vertex
	 * @param stack:
	 *            ArrayDeque<Vertex> - to store the topological order of
	 *            vertices
	 */
	public static void DFSVisit(Vertex u, ArrayDeque<Vertex> stack, boolean isCycleAllowed, boolean isDirected) {
		u.color = Vertex.Color.GRAY; // vertex being processed
		for (Edge e : u.Adj) {
			Vertex v = e.otherEnd(u);
			if (v.color == Vertex.Color.WHITE) {
				v.parent = u;
				DFSVisit(v, stack, isCycleAllowed, isDirected);
			} else if (!isCycleAllowed
					&& ((isDirected && v.color == Vertex.Color.GRAY) || (!isDirected && v != u.parent))) {
				// To detect cycle in DAG
				return;
			}
		}
		u.color = Vertex.Color.BLACK;// vertex processed
		stack.push(u);
	}

	/**
	 * TO find the Euler tour or path
	 * 
	 * @param u:
	 *            Starting point of the tour or path
	 * @param tourVertices:
	 *            list of tour or path edges
	 */
	public static void tour(Vertex u, DoublyLinkedList<Edge> tourEdges) {
		Queue<Vertex> verticesTobeVisited = new LinkedList<>();
		// mini-tour edges
		DoublyLinkedList<Edge> tourTemp = new DoublyLinkedList<>();

		Vertex v;
		Node<Edge> mergeAt = new Node<>(null, null, null);
		Node<Edge> mergeAtNext = new Node<>(null, null, null);
		// node next to the merging node
		subTour(u, tourTemp, verticesTobeVisited);
		// Copy the sub-tour(or mini-tour) to the final tour
		tourEdges.setHead(tourTemp.head);
		tourEdges.setTail(tourTemp.tail);
		tourEdges.setSize(tourTemp.size);
		// get one of the vertices with non-zero degree and call sub-tour method
		// again
		while (!verticesTobeVisited.isEmpty()) {
			// get a vertex from list of to be visited
			v = verticesTobeVisited.remove();// get the next vertex
			if (v.degree == 0)
				continue;
			// stores the node where the next tour has to be merged

			mergeAtNext = v.curEdgeIndex;
			// if the merging edge is the head of the tour then its previous is
			// null - edge case
			if (mergeAtNext.prev != null)
				mergeAt = (v.curEdgeIndex.prev);
			tourTemp.clear();
			subTour(v, tourTemp, verticesTobeVisited);// sub-tour on the new
														// vertex

			// Merging the tours - head of the sub-tour to the main tour
			if (mergeAtNext.prev != null) {
				tourTemp.head.setPrev(mergeAt);
				mergeAt.setNext(tourTemp.head.get());
			} else// if merging at the head of the main tour
				tourEdges.setHead(tourTemp.head); // the head will be head of
													// the (new)sub-tour
			// tail of the sub-tour to the main tour
			// setting the double link - prev and next
			tourTemp.tail.setNext(mergeAtNext);
			mergeAtNext.setPrev(tourTemp.tail.get());
			tourEdges.size += tourTemp.size;
		}
	}

	/**
	 * To find the sub-tour or mini-tour
	 * 
	 * @param u:
	 *            Start vertex
	 * @param curTourEdges:
	 *            Current tour Edges
	 * @param verticesTobeVisited:
	 *            Vertices to be visited - i.e. vertices which have edges that
	 *            are not visited
	 */
	public static void subTour(Vertex u, DoublyLinkedList<Edge> curTourEdges, Queue<Vertex> verticesTobeVisited) {
		Vertex v;
		Edge e;
		if (u.degree == 0) // stop when the degree of the vertex is 0
			return;

		e = u.Adj.get(u.AdjIndex++);
		// if the edge is already seen, go to the next edge in the adjacency
		// list
		while (e.seen && (u.Adj.size() != u.AdjIndex))
			e = u.Adj.get(u.AdjIndex++);
		curTourEdges.add(e);// add to the list having current tour edges

		// Storing the node to indicate where the 2 sub-tours to be merged
		if (curTourEdges.size == 1)
			u.curEdgeIndex = curTourEdges.head;
		else
			u.curEdgeIndex = curTourEdges.tail;
		if (!e.seen) {
			v = e.otherEnd(u);
			u.degree--;
			v.degree--;
			e.seen = true;
			verticesTobeVisited.add(u); // add to the list of verrtices to be
										// visited
			subTour(v, curTourEdges, verticesTobeVisited); // call the sub-tour
															// on the new vertex
		}
		return;
	}

	/**
	 * Method to check whether the graph is Eulerian or not. If Eulerian find
	 * the Eulerian path.
	 * 
	 * @param g:
	 *            Graph
	 */
	public static DoublyLinkedList<Edge> findEulerTour(Graph g) {
		DoublyLinkedList<Edge> finalTour = new DoublyLinkedList<>();
		ArrayDeque<Vertex> stack = new ArrayDeque<>();
		// Graph is not connected if DFS doesn't return the stack having all the
		// vertices
		Vertex u = g.verts.get(1);
		DFSVisit(u, stack, true, false);
		if (stack.size() != g.numNodes) {
			System.out.println("Graph is not Eulerian");
			return null;
		}
		// to calculate the degree of all the vertices
		g.calcDegree();
		// Starting vertex for the tour/path
		int start = findStartNode(g);
		if (start == 0)
			return null;
		Vertex s = g.verts.get(start);
		tour(s, finalTour);
		return finalTour;
	}

	/**
	 * Method to find the index of start node for Eulerian path of a graph,
	 * returns -1 if the graph is not Eulerian, otherwise returns the index of
	 * the start node.
	 * 
	 * @param g
	 *            Graph to be checked
	 * @return int : 0 if not Eulerian; index of start node if Eulerian
	 */
	private static int findStartNode(Graph g) {
		int countOddDegree = 0;
		// Index of start node, in case only Euler path exists, which will be
		// the index of one of two vertices containing odd degree
		int index = 1;
		for (int i = g.numNodes; i > 0; i--) {
			if (g.verts.get(i).Adj.size() % 2 != 0) {
				countOddDegree++;
				index = i;
			}
			// If number of vertices with odd degree is greater than 2,
			// Euler path does not exist
			if (countOddDegree > 2) {
				return 0; // Since first vertex is null in graph
			}
		}
		// Return index as 1, if there are zero vertices with odd degree
		// Return "index", if there are exactly two vertices of odd degree
		// Otherwise return index as 0, which points to null in the graph
		return countOddDegree == 0 ? 1 : countOddDegree == 2 ? index : 0;
	}

	/**
	 * 
	 * @param g:
	 *            Graph
	 * @param tour:
	 *            DoublyLinkedList - Euler Tour Edges
	 * @param start:
	 *            Start of the Tour
	 * @return: true -if the tour is valid, else false
	 */
	static boolean verifyTour(Graph g, DoublyLinkedList<Edge> tour, Vertex start) {
		Node<Edge> tourEdges = tour.head;
		Vertex prevVertex;
		int cnt = 0;// to keep count of the edges in the tour
		// make all edges as not seen
		for (Vertex u : g) {
			for (Edge e : u.Adj)
				e.seen = false;
		}

		// find the vertex in the first edge and choose the otherEnd of this
		// edge
		if (start == tour.head.value.To)
			prevVertex = tour.head.value.From;
		else
			prevVertex = tour.head.value.To;
		cnt++;
		tourEdges.value.seen = true;// make the first edge as seen
		// Check if the consecutive edges have a common vertex
		while (tourEdges.next != null) {
			tourEdges = tourEdges.next;
			tourEdges.value.seen = true;// set the edges to seen
			cnt++;
			// check for common vertex and consider the other vertex to check
			// with the next edge
			if (tourEdges.value.From == prevVertex)
				prevVertex = tourEdges.value.To;
			else if (tourEdges.value.To == prevVertex)
				prevVertex = tourEdges.value.From;
			else
				return false;
		}
		// Check if all the edges are visited
		for (Vertex u : g) {
			for (Edge e : u.Adj)
				if (!e.seen)
					return false;
		}
		// Check if #input-edges is equal to #tour-edges
		if (cnt != g.numEdges)
			return false;
		return true;
	}

	/**
	 * Main - driver function
	 * 
	 * @param args:
	 *            file name from the console
	 * @throws FileNotFoundException:
	 *             throws error if file not found
	 */
	public static void main(String[] args) throws FileNotFoundException {
		DoublyLinkedList<Edge> tour = new DoublyLinkedList<>();
		Scanner in = null;
		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}
		// read graph
		Graph g = Graph.readGraph(in, false);
		Long start = System.currentTimeMillis();
		// Find the tour/path
		tour = findEulerTour(g);
		// verify path/tour if exists
		if (tour != null)
			verifyTour(g, tour, g.verts.get(findStartNode(g)));
		Long end = System.currentTimeMillis();
		System.out.println(tour);
		System.out.println("Time taken(in ms): " + (end - start));
	}
}