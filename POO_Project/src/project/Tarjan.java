package project;

import java.util.ListIterator;

/** checks if a network is a DAG using an algorithm based on Tarjan algorithm */
public class Tarjan implements CheckDAG {

	private Node[] nodes;
	
	/** a modified implementation of Tarjan's algorithm to check if a TransitionNetwork is a DAG */
	@Override
	public boolean execute(TransitionNetwork network) {

		nodes = network.getNodes();
		
		// initialise nodes with info necessary for the algorithm
		for(int i=0; i<nodes.length; i++) {
			nodes[i].content = new NodeInfo();
		}
		
		// apply algorithm to all connected graph components
		for(int i=0; ((NodeInfo)nodes[i].content).visited == false; i++) {
			if(isDAG(nodes[i]) == false)
				return false;
		}
		
		return false;
	}

	/** @return {@code true} if the component containing {@code n} is acyclic */
	private boolean isDAG(Node n) {
		((NodeInfo)n.content).visited = true;	// mark node as visited
		((NodeInfo)n.content).inStack = true;	// put node in recursion stack
		Node child;
		
		// recurse to unvisited children
		for(ListIterator<Node> iter=n.iterator(); iter.hasNext(); ) {
			child = iter.next();
			if(((NodeInfo)child.content).visited == false)	// unvisited child
				if(isDAG(child) == false)
					return false;
			else if(((NodeInfo)child.content).inStack == true)	// came back to a node that is in the stack. this is not a DAG!
				return false;
			
		}
		
		((NodeInfo)n.content).inStack = false;
		return true;
	}
	
	/** information needed in each node for
	 *  {@link project.Tarjan#isDAG(Node)} to use */
	private class NodeInfo {
		/** {@code true} if this node has been visited */
		boolean visited;
		/** {@code true} if this node is on the recursion stack */
		boolean inStack;
		
		NodeInfo() {
			this.visited = false;
			this.inStack = false;
		}
	}
}
