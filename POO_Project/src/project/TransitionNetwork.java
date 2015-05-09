package project;

public class TransitionNetwork extends Network {

	/* the nodes in this transition network. first half of the array refers to time t; second half to time t+1 */
	private Node[] nodes;
	CheckStructure checkDAG;
	Train train;
	
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
	
	@Override
	public boolean addEdge(Node p, Node c) throws NodeOutOfBoundsException {
		if(!inNodes(p) || !inNodes(c))
			throw new NodeOutOfBoundsException();

		p.addParent(c);
		if(checkDAG.checkDAG())
			return true;
		p.remParent(c);
		return false;
	}

	@Override
	public boolean remEdge(Node p, Node c) throws NodeOutOfBoundsException {
		if(!inNodes(p) || !inNodes(c))
			throw new NodeOutOfBoundsException();
		
		
		return false;
	}

	@Override
	public boolean invEdge(Node p, Node c) throws NodeOutOfBoundsException {
		if(!inNodes(p) || !inNodes(c))
			throw new NodeOutOfBoundsException();
		
		
		return false;
	}
	
	@Override
	public boolean isEdge(Node p, Node c) throws NodeOutOfBoundsException {
		if(!inNodes(p) || !inNodes(c))
			throw new NodeOutOfBoundsException();
		
		
		return false;
	}

	public void setCheckDAG(CheckStructure T) {
		this.checkDAG = T;
	}

	public void setTrain(Train T) {
		this.train = T;
	}
}
