Contents:
=========
The project folder contains the following files:
	- EulerPath.java
	- Graph.java
	- Edge.java
	- Vertex.java
	- Node.java
	- DoublyLinkedList.java
	- Report.docx
	- readme.txt

Environment:
============
Operating System : Windows 10
Compiler	 	 : JRE 1.8.0_45

Description:
============

a. EulerPath.java
	To find the Euler Tour/Path and to verify the same.
	Methods - 
	1. 	DFSVisit(Vertex, ArrayDeque<Vertex>, boolean, boolean)
	2.	tour(Vertex, DoublyLinkedList<Edge>)
	3.	subTour(Vertex, DoublyLinkedList<Edge>, Queue<Vertex>)
	4.	findEulerTour(Graph)
	5.	findStartNode(Graph)
	6.	verifyTour(Graph, DoublyLinkedList<Edge>, Vertex)
	
b. Graph.java
	To read and store the Graph
	Methods -
	1.	addEdge(int, int, int)
	2.	calcDegree()
	3.	readGraph(Scanner, boolean)
	
c. DoublyLinkedList.java
	Methods -
	1. 	clear()
	2.	add(T)
	3.	isEmpty()
	4.	size()
	5.	toString()
