package project;

import java.io.IOException;

public class TransitionNetwork extends Network {

	/** the nodes in this transition network. first half of the array refers to time t; second half to time t+1 */
	private Node[] nodes;
	CheckStructure checkDAG;
	private Slice past;
	private Slice present;
	int[] varDomain;
	
	/** */
	public TransitionNetwork(Data data, int index) {
		
		//TODO verify if a slice for index+1 exists
		past = data.get(index);
		present = data.get(index+1);
		nodes = new Node[2*Slice.numVar];
		checkDAG = new Tarjan();	// Tarjan is the default algorithm for checkDAG()
		
		varDomain = data.getVarDomain();
		
		for(int i=0; i<nodes.length; i++)	// create nodes for this TN
			nodes[i] = new Node();	
	}
	
	/** @return the i th node in the network */
	public Node getNode(int i) {
		return nodes[i];
	}
	
	/** @return an array containing the nodes of the network, cloned */
	public Node[] getNodes() {
		Node[] clone = new Node[nodes.length];
		for(int i=0; i<clone.length; i++) {
			clone[i] = nodes[i].clone();
		}
		
		return clone;
	}
	
	public boolean inNodes(Node n)	{
		for(Node node: nodes) {
			if(node == n)
				return true;
		}
		return false;
	}
	
	/** adds a directed edge from {@code p} to {@code c} if this does not disrupt the
	 * the property of the TN being a DAG
	 * @return {@code true} if the edge was added */
	@Override
	public boolean addEdge(Node p, Node c) throws NodeOutOfBoundsException {
		if(!inNodes(p) || !inNodes(c))
			throw new NodeOutOfBoundsException();

		p.addChild(c);
		c.addParent(p);
		if(isDAG())
			return true;
		p.remChild(c);
		c.remParent(p);
		return false;
	}

	/** removes a directed edge from {@code p} to {@code c}
	 * @return {@code true} */
	@Override
	public boolean remEdge(Node p, Node c) throws NodeOutOfBoundsException {
		if(!inNodes(p) || !inNodes(c))
			throw new NodeOutOfBoundsException();
		
		p.remChild(c);
		c.remParent(p);
		return true;
	}

	/** inverts an edge from {@code p} to {@code c} if this does not disrupt the
	 * the property of the TN being a DAG
	 * @return {@code true} if the edge was inverted */
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
		if(isDAG())
			return true;
		p.remParent(c);
		c.remChild(p);
		c.addParent(p);
		p.addChild(c);
		return false;
	}
	
	/** @return {@code true} if a directed edge exists from {@code p} to {@code c} */
	@Override
	public boolean existsEdge(Node p, Node c) throws NodeOutOfBoundsException {
		if(!inNodes(p) || !inNodes(c))
			throw new NodeOutOfBoundsException();
		
		if(p.isParent(c) && c.isChild(p))
			return true;
		return false;
	}
	
	/** returns the number of elements stored in {@code Node[] nodes} */
	public int nrNodes(){
		return this.nodes.length;
	}
	
	/** returns the number of parent nodes in {@code nodes[i]} */
	public int nrParents(int i){
		return this.nodes[i].nrParents();
	}
	
	/** returns the number of children nodes in {@code nodes[i]} */
	public int nrChildren(int i){
		return this.nodes[i].nrChilds();
	}

	/** sets the method to use when checking if the network is a DAG during 
	 * {@link project.TransitionNetwork#addEdge(Node, Node)} and 
	 * {@link project.TransitionNetwork#invEdge(Node, Node)} */
	public void setCheckDAG(CheckStructure checkMethod) {
		this.checkDAG = checkMethod;
	}
	
	/** checks if the network is a DAG 
	 * @return {@code true} if the network is a DAG. {@code false} otherwise */
	private boolean isDAG() {
		return checkDAG.execute(this);
	}

	/** sets the method to use when training the network */
	public void train(Train T, Score S) {
		T.execute(this, S);
	}
	
	
	
	/** main() for testing purposes 
	 * @throws NodeOutOfBoundsException */
	public static void main(String[] args) throws NodeOutOfBoundsException {
		
		Data data = null;
		Parser parse = new Parser();
		try {
			data = parse.fromFile(new String("datasets/test01.csv"));
		} catch (IOException e) {
			System.out.println("No such file");
			System.exit(-1);
		}
		
		System.out.println("#Different sets of elements:\n" + data);
		
		TransitionNetwork tn = new TransitionNetwork(data, 0);
		
		tn.addEdge(tn.nodes[0], tn.nodes[1]);
		
	}
}
