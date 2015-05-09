package project;

public class TransitionNetwork extends Network {

	/** the nodes in this transition network. first half of the array refers to time t; second half to time t+1 */
	private Node[] nodes;
	CheckStructure checkDAG;
	
	/** */
	public TransitionNetwork(Data data, int index) {
		//Slice slice = data.get(index);
		nodes = new Node[2*Slice.numVar];

		for(int i=0; i<nodes.length; i++)
			nodes[i] = new Node();	
	}
	
	public Node getNode(int i) {
		return nodes[i];
	}
	
	public boolean inNodes(Node n)	{
		for(Node node: nodes) {
			if(node == n)
				return true;
		}
		return false;
	}
	
	/** adds a directed edge from {@code p} to {@code c} */
	@Override
	public boolean addEdge(Node p, Node c) throws NodeOutOfBoundsException {
		if(!inNodes(p) || !inNodes(c))
			throw new NodeOutOfBoundsException();

		p.addChild(c);
		c.addParent(p);
		if(checkDAG.checkDAG())
			return true;
		p.remChild(c);
		c.remParent(p);
		return false;
	}

	/** removes a directed edge from {@code p} to {@code c} */
	@Override
	public boolean remEdge(Node p, Node c) throws NodeOutOfBoundsException {
		if(!inNodes(p) || !inNodes(c))
			throw new NodeOutOfBoundsException();
		
		p.remChild(c);
		c.remParent(p);
		return true;
	}

	/** inverts an edge from {@code p} to {@code c} */
	@Override
	public boolean invEdge(Node p, Node c) throws NodeOutOfBoundsException {
		if(!inNodes(p) || !inNodes(c))
			throw new NodeOutOfBoundsException();
		
		// remove edge one way
		p.remChild(c);
		c.remParent(p);
		// add it reversed
		c.addChild(p);
		p.addParent(c);
		if(checkDAG.checkDAG())
			return true;
		p.remParent(c);
		c.remChild(p);
		c.addParent(p);
		p.addChild(c);
		return false;
	}
	
	/** returns true if directed edge exists from {@code p} to {@code c} */
	@Override
	public boolean existsEdge(Node p, Node c) throws NodeOutOfBoundsException {
		if(!inNodes(p) || !inNodes(c))
			throw new NodeOutOfBoundsException();
		
		if(p.isParent(c) && c.isChild(p))
			return true;
		return false;
	}

	/** sets the method to use when checking if the network is a DAG */
	public void setCheckDAG(CheckStructure T) {
		this.checkDAG = T;
	}

	/** sets the method to use when training the network */
	public void train(Train T) {
		T.execute(this);
	}
}
