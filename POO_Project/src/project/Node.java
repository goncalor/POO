package project;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Node implements Cloneable {

	/** list of neighbours of this node */
	protected List<Node> edges;
	/** the content for this node */
	public Object content;
	
	public Node()
	{
		edges = new ArrayList<Node>();
	}
	
	/** add edge from this node to {@code node} */
	public void addEdge(Node node)
	{
		edges.add(node);
	}
		
	/** remove edge from this node to {@code node}, if it exists */
	public void remEdge(Node node)
	{
		edges.remove(node);
	}

	/** @return true if there is an edge from this node to {@code node} */
	public boolean isEdge(Node node)
	{
		return this.edges.contains(node);
	}
	
	/** @return the out-degree or this node */
	public int nrEdges(){
		return this.edges.size();
	}

	public ListIterator<Node> iterator(){
		return edges.listIterator();
	}
	
	@Override
	public Node clone() {
		Node newNode = new Node();
		newNode.content = this.content;
		for(Node edge: edges)
			newNode.addEdge(edge);
		
		return newNode;
	}
}
