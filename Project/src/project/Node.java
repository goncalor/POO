package project;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Node<T> {

	/** list of neighbours of this node */
	protected List<Node<T>> edges;
	/** the content for this node */
	public T content;

	public Node() {
		edges = new ArrayList<Node<T>>();
	}

	/** add edge from this node to {@code node} */
	public void addEdge(Node<T> node) {
		edges.add(node);
	}

	/** remove edge from this node to {@code node}, if it exists */
	public void remEdge(Node<T> node) {
		edges.remove(node);
	}

	/** @return true if there is an edge from this node to {@code node} */
	public boolean isEdge(Node<T> node) {
		return this.edges.contains(node);
	}

	/** @return the out-degree or this node */
	public int nrEdges() {
		return this.edges.size();
	}
	
	public Iterator<Node<T>> iterator() {
		return edges.listIterator();
	}

	public Node<T> clone(Map<Node<T>, Node<T>> isomorphism) {
		Node<T> copy = isomorphism.get(this);	// this contains the reference of this node
		if (copy == null) {
			copy = new Node<T>();
			isomorphism.put(this, copy);
			for (Node<T> edge : edges) {
				copy.edges.add(edge.clone(isomorphism));
				copy.content = this.content;
			}
		}
		return copy;
	}
}
