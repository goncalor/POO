package project;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Node {

	/** list of neighbours of this node */
	protected Set<Node> edges;
	/** the content for this node */
	public Object content;
	/** index for current node in TN*/
	protected int selfIndex;

	public Node() {
		edges = new HashSet<Node>();
	}

	/** Set index*/
	public void setIndex(int a){
		this.selfIndex = a;
	}
	
	/** Get index*/
	public int getIndex(){
		return this.selfIndex;
	}
	
	/** add edge from this node to {@code node} */
	public void addEdge(Node node) {
		edges.add(node);
	}

	/** remove edge from this node to {@code node}, if it exists */
	public void remEdge(Node node) {
		edges.remove(node);
	}

	/** @return true if there is an edge from this node to {@code node} */
	public boolean isEdge(Node node) {
		return this.edges.contains(node);
	}

	/** @return the out-degree or this node */
	public int nrEdges() {
		return this.edges.size();
	}

	public Iterator<Node> iterator() {
		return edges.iterator();
	}

	public Node clone(Map<Node, Node> isomorphism) {
		Node copy = isomorphism.get(this);	// this contains the reference of this node
		if (copy == null) {
			copy = new Node();
			isomorphism.put(this, copy);
			for (Node edge : edges) {
				copy.edges.add(edge.clone(isomorphism));
				copy.content = this.content;
			}
		}
		return copy;
	}
}
