package project;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.IdentityHashMap;
import java.util.Map;

public class TransitionNetwork extends Network {

	/** the nodes in this transition network. first half of the array refers to time t; second half to time t+1 */
	private Node<int[]>[] nodes;
	CheckStructure checkDAG;
	private Slice past;
	private Slice present;
	int[] varDomain;
	
	/** */
	@SuppressWarnings("unchecked")
	public TransitionNetwork(Data data, int index) {
		
		//TODO verify if a slice for index+1 exists
		past = data.get(index);
		present = data.get(index+1);
		nodes = (Node<int[]>[]) new Node<?>[2*Slice.numVar];
		
		checkDAG = new Tarjan();	// Tarjan is the default algorithm for checkDAG()
		
		varDomain = data.getVarDomain();
		
		for(int i=0; i<nodes.length; i++)	// create nodes for this TN
			nodes[i] = new Node<int[]>();
	}
	
	/** @return the i th node in the network */
	public Node<int[]> getNode(int i) {
		return nodes[i];
	}
	
	/** @return an array containing the nodes of the network, cloned */
	public Node[] cloneNodes () {
		Node[] clone = new Node[nodes.length];
		Map<Node, Node> isomorphism = new IdentityHashMap<Node, Node>();
		for(int i=0; i<clone.length; i++) {
			clone[i] = nodes[i].clone(isomorphism);
		}
		
		return clone;
	}
	
	/** @return {@code true} if {@code n} is in this network */
	public boolean inNodes(Node<int[]> n)	{
		for(Node<int[]> node: nodes) {
			if(node == n)
				return true;
		}
		return false;
	}
	
	/** adds a directed edge from {@code from} to {@code to} if this does not disrupt the
	 * the property of the TN being a DAG
	 * @return {@code true} if the edge was added */
	@Override
	public boolean addEdge(Node from, Node to) throws NodeOutOfBoundsException {
		if(!inNodes(from) || !inNodes(to))
			throw new NodeOutOfBoundsException();

		from.addEdge(to);
		if(isDAG())
			return true;
		from.remEdge(to);
		return false;
	}

	/** removes a directed edge from {@code from} to {@code to}
	 * @return {@code true} */
	@Override
	public boolean remEdge(Node from, Node to) throws NodeOutOfBoundsException {
		if(!inNodes(from) || !inNodes(to))
			throw new NodeOutOfBoundsException();
		
		from.remEdge(to);
		return true;
	}

	/** inverts an edge from {@code from} to {@code to} if this does not disrupt the
	 * the property of the TN being a DAG
	 * @return {@code true} if the edge was inverted */
	@Override
	public boolean invEdge(Node from, Node to) throws NodeOutOfBoundsException {
		if(!inNodes(from) || !inNodes(to))
			throw new NodeOutOfBoundsException();
		
		from.remEdge(to);
		to.addEdge(from);
		if(isDAG())
			return true;
		to.remEdge(from);
		from.addEdge(to);
		return false;
	}
	
	/** @return {@code true} if a directed edge exists from {@code from} to {@code to} */
	@Override
	public boolean existsEdge(Node from, Node to) throws NodeOutOfBoundsException {
		if(!inNodes(from) || !inNodes(to))
			throw new NodeOutOfBoundsException();
		
		if(from.isEdge(to))
			return true;
		return false;
	}
	
	/** @return the number of elements stored in the network */
	public int nrNodes(){
		return this.nodes.length;
	}
	
	/** @return the out-degree of the {@code i}th node */
	public int nrEdges(int i){
		return this.nodes[i].nrEdges();
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
		
		boolean retval;
		retval = tn.addEdge(tn.nodes[0], tn.nodes[1]);
		System.out.println(retval);
		
		retval = tn.addEdge(tn.nodes[1], tn.nodes[0]);
		System.out.println(retval);
		
	}
}
