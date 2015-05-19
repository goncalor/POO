package project.network;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Stores the information for a Node, content can be set to anything. Has a set
 * of parents ( {@code edges}), content and the index of that Node
 */

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

	/** sets the index for this node */
	public void setIndex(int a){
		this.selfIndex = a;
	}
	
	/** gets the index for this node */
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

	/** creates a new iterator tha iterates over the edges of this node */
	public Iterator<Node> iterator() {
		return edges.iterator();
	}

	/**
	 * clones this node and all the nodes in the connected component this node
	 * belongs to
	 * 
	 * @param isomorphism
	 *            pairs each old node to a new one: (old, new)
	 */
	public Node clone(Map<Node, Node> isomorphism) {
		Node copy = isomorphism.get(this);	// this contains the reference of this node
		if (copy == null) {
			copy = new Node();
			isomorphism.put(this, copy);
			copy.content = this.content;
			copy.selfIndex = this.selfIndex;
			for (Node edge : edges) {
				copy.edges.add(edge.clone(isomorphism));
			}
		}
		return copy;
	}
}
