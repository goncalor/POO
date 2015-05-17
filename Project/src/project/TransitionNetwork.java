package project;

import java.io.IOException;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

public class TransitionNetwork extends Network {

	/** the nodes in this transition network. first half of the array refers to time t; second half to time t+1 */
	private Node[] nodes;
	/** the algorithm to use to check if the TN is a DAG when adding or inverting an edge*/
	private CheckStructure checkDAG;
	/** the maximum number of parents a node can have. default value is 3 */
	private int maxNrParents;
	/** the maximum value for each variable */
	int[] varDomain;
	
	// used by cloneResetEdges()
	private Data data;
	private int index;
	
	/**
	 * creates a new transition network from the slices in {@code index} and
	 * {@code index+1} in the data {@code data}. the maximum number of parents of
	 * each node defaults to 3
	 * 
	 * @throws NodeOutOfBoundsException
	 */
	public TransitionNetwork(Data data, int index) throws NodeOutOfBoundsException {
		Slice sliceT;
		Slice sliceT1;
		this.data = data;
		this.index = index;
		
		try {
			sliceT = data.get(index);
			sliceT1 = data.get(index+1);
		}
		catch(ArrayIndexOutOfBoundsException e) {
			throw new NodeOutOfBoundsException();
		}
		
		nodes = new Node[2*Slice.numVar];
		checkDAG = new Tarjan();	// Tarjan is the default algorithm for checkDAG()
		maxNrParents = 3;
		
		varDomain = data.getVarDomain();
		
		for(int i=0; i<nodes.length; i++) {	// create nodes for this TN
			nodes[i] = new Node();
			nodes[i].setIndex(i);
		}
		
		for(int i=0; i<nodes.length/2; i++) {	// create nodes for this TN
			nodes[i].content = sliceT.get(i);
			nodes[i + nodes.length/2].content = sliceT1.get(i);
		}		
	}

	/**
	 * creates a new transition network from the slices in {@code index} and
	 * {@code index+1} in the data {@code data} and sets the maximum number of
	 * parents of each node to {@code maxNrParents}
	 * 
	 * @throws NodeOutOfBoundsException
	 */
	public TransitionNetwork(Data data, int index, int maxNrParents) throws NodeOutOfBoundsException {
		this(data, index);
		this.maxNrParents = maxNrParents;
	}
	
	/** @return the i th node in the network */
	public Node getNode(int i) {
		return nodes[i];
	}
	
	/** @return the maximum number of parents each node can have */
	public int getMaxNrParents() {
		return maxNrParents;
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
	
	/** clones this transition network but with no edges between the nodes */
	public TransitionNetwork cloneResetEdges() {
		try {
			return new TransitionNetwork(data, index);
		} catch (NodeOutOfBoundsException e) {
			return null;
		}
	}
	
	/** @return {@code true} if {@code n} is in this network */
	public boolean inNodes(Node n)	{
		for(Node node: nodes) {
			if(node == n)
				return true;
		}
		return false;
	}
	
	/**
	 * adds a directed edge from {@code child} to {@code parent} if this does not
	 * disrupt the the property of the TN being a DAG
	 * 
	 * @param child
	 *            this will be the child
	 * @param parent
	 *            this will be the parent
	 * @return {@code true} if the edge was added. {@code false} if adding would
	 *         make the network not be a DAG or if one edge already exists
	 *         between {@code from} and {@code to}
	 */
	@Override
	public boolean addEdge(Node child, Node parent) throws NodeOutOfBoundsException {
		if(!inNodes(child) || !inNodes(parent))
			throw new NodeOutOfBoundsException();
		
		if(child.nrEdges() == maxNrParents)	// child has the maximum number of parents
			return false;
		if(child.getIndex() < this.nrNodes()/2)	// nodes in t cannot be children
			return false;
		if(existsEdge(child, parent))
			return false;
		child.addEdge(parent);
		if(isDAG())
			return true;
		child.remEdge(parent);	// revert
		return false;
	}

	/**
	 * removes a directed edge from {@code child} to {@code parent}
	 * 
	 * @return {@code true} if the edge the edge was removed. {@code false} if
	 *         there was no edge to remove
	 */
	@Override
	public boolean remEdge(Node child, Node parent) throws NodeOutOfBoundsException {
		if(!inNodes(child) || !inNodes(parent))
			throw new NodeOutOfBoundsException();
		
		if(!existsEdge(child, parent))
			return false;
		child.remEdge(parent);
		return true;
	}

	/**
	 * inverts an edge from {@code from} to {@code to} if this does not disrupt
	 * the the property of the TN being a DAG
	 * 
	 * @return {@code true} if the edge was inverted. {@code false} if there was
	 *         no edge to invert of if inverting it would make the network not
	 *         be a DAG
	 */
	@Override
	public boolean invEdge(Node child, Node parent) throws NodeOutOfBoundsException {
		if(!inNodes(child) || !inNodes(parent))
			throw new NodeOutOfBoundsException();

		if(parent.nrEdges() == maxNrParents)	// parent has the maximum number of parents
			return false;
		if(parent.getIndex() < this.nrNodes()/2)	// nodes in t cannot be children
			return false;
		if(!existsEdge(child, parent))
			return false;
		child.remEdge(parent);
		parent.addEdge(child);
		if(isDAG())
			return true;
		parent.remEdge(child);
		child.addEdge(parent);
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

	/**
	 * sets the method to use when checking if the network is a DAG during
	 * {@link project.TransitionNetwork#addEdge(Node, Node)} and
	 * {@link project.TransitionNetwork#invEdge(Node, Node)}
	 */
	public void setCheckDAG(CheckStructure checkMethod) {
		this.checkDAG = checkMethod;
	}
	
	/**
	 * checks if the network is a DAG
	 * 
	 * @return {@code true} if the network is a DAG. {@code false} otherwise
	 */
	private boolean isDAG() {
		return checkDAG.execute(this);
	}

	/** sets the method to use when training the network */
	public void train(Train T, Score S) {
		T.execute(this, S);
	}
	
//	@Override
//	public String toString() {
//		String s = "";
//		for(Node n: nodes) {
//			s += Arrays.toString((int[]) n.content) + ", ";
//		}
//		return "TransitionNetwork [nodes=" + s + "]";
//	}
	
	@Override
	public String toString() {
		int nrNodes = nodes.length;
		StringBuffer inter = new StringBuffer("=== Inter-slice connectivity\n");
		StringBuffer intra = new StringBuffer("=== Intra-slice connectivity\n");
		int index;

		for (int t1 = nrNodes / 2; t1 < nrNodes; t1++) {
			inter.append(data.varNames[t1 - nrNodes / 2] + "_" + (this.index + 1) + "  :  ");
			intra.append(data.varNames[t1 - nrNodes / 2] + "_" + (this.index + 1) + "  :  ");
			for (Iterator<Node> iter = nodes[t1].iterator(); iter.hasNext();) {
				index = iter.next().getIndex();
				if (index < nrNodes / 2) { // parent is from t
					inter.append(data.varNames[index] + " ");
				} else { // parent is from t+1
					intra.append(data.varNames[index - nrNodes / 2] + " ");
				}
			}
			inter.append('\n');
			intra.append('\n');
		}

		StringBuffer scores = new StringBuffer("=== Scores\n");

		LL ll = new LL();
		float llRes = ll.execute(new GHC(), this);
		MDL mdl = new MDL();
		float mdlRes = mdl.llToMDL(llRes, this);
		scores.append("LL score\t:\t" + llRes);
		scores.append("\nMDL score\t:\t" + mdlRes + "\n");
		
		return inter.toString() + intra.toString() + scores.toString();
	}

	/** main() for testing purposes 
	 * @throws NodeOutOfBoundsException */
	public static void main(String[] args) throws NodeOutOfBoundsException {
		
		Data data = null;
		Parser parse = new Parser();
		try {
			data = parse.fromFile(new String("datasets/test02.csv"));
		} catch (IOException e) {
			System.out.println("No such file");
			System.exit(-1);
		}
		
		System.out.println("#Different sets of elements:\n" + data);
		
		/* transition from t=0 to t=1 */
		TransitionNetwork tn = new TransitionNetwork(data, 0);
		
		System.out.println(tn);
		
		boolean retval;
		retval = tn.addEdge(tn.nodes[0], tn.nodes[1]);
		System.out.println(retval);
		
		retval = tn.addEdge(tn.nodes[1], tn.nodes[2]);
		System.out.println(retval);
		
		retval = tn.addEdge(tn.nodes[2], tn.nodes[0]);
		System.out.println(retval);
				
		/* transition from t=1 to t=2 */
		
		tn.train(new GHC(), new LL());
	}

}
