package project;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

import project.network.Network;
import project.network.Node;
import project.network.NodeOutOfBoundsException;
import project.network.structureUtils.CheckStructure;
import project.network.structureUtils.Tarjan;
import project.scoringMethods.LL;
import project.scoringMethods.MDL;
import project.scoringMethods.Score;

/**
 * A more specific instance of Network
 * 
 */

public class TransitionNetwork extends Network {

	/** the nodes in this transition network. first half of the array refers to time t; second half to time t+1 */
	private Node[] nodes;
	/** the algorithm to use to check if the TN is a DAG when adding or inverting an edge*/
	private CheckStructure checkDAG;
	/** the maximum number of parents a node can have. default value is 3 */
	private int maxNrParents;
	/** the maximum value for each variable */
	protected int[] varDomain;
	
	// used by cloneResetEdges()
	private Data data;
	
	/**
	 * creates a new transition network from the slices in {@code index} and
	 * {@code index+1} in the data {@code data}. the maximum number of parents of
	 * each node defaults to 3
	 * 
	 * @throws NodeOutOfBoundsException
	 */
	public TransitionNetwork(Data data) throws NodeOutOfBoundsException {
		this.data = data;
		
		nodes = new Node[2*Slice.numVar];
		checkDAG = new Tarjan();	// Tarjan is the default algorithm for checkDAG()
		maxNrParents = 3;
		
		varDomain = data.getVarDomain();
		
		for(int i=0; i<nodes.length; i++) {	// create nodes for this TN
			int[] values = new int[data.maxSlices()*data.getSlice(0).getNrLines()];
			nodes[i] = new Node();
			nodes[i].setIndex(i);
			nodes[i].content = values;
		}
		
		for(int i=0; i<nodes.length/2; i++){	// create nodes for this TN
			for(int j=0; j < data.maxSlices(); j++){
				int[] sliceColT = data.getSlice(j).getCol(i);
				int[] sliceColT1 = data.getSlice(j+1).getCol(i);
			
				for(int k=0; k<sliceColT.length; k++){
					((int[])nodes[i].content)[j*data.getSlice(0).getNrLines()+k] = sliceColT[k];
					((int[])nodes[i+nodes.length/2].content)[j*data.getSlice(0).getNrLines()+k] = sliceColT1[k];
				}
			}
		}
	}

	/**
	 * creates a new transition network from the slices in {@code index} and
	 * {@code index+1} in the data {@code data} and sets the maximum number of
	 * parents of each node to {@code maxNrParents}
	 * 
	 * @throws NodeOutOfBoundsException
	 */
	public TransitionNetwork(Data data, int maxNrParents) throws NodeOutOfBoundsException {
		this(data);
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
			return new TransitionNetwork(data);
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
	public TransitionNetwork train(Train T, Score S) {
		return T.execute(this, S);
	}
	
	/**
	 * @return an array of the maximum values for each variable in this
	 *         transition network
	 */
	public int[] getVarDomain() {
		return varDomain;
	}

	/**
	 * prints the inter-slice and intra-slice connectivities of the network,
	 * aswell as the ll and mdl score of the network.
	 */
	@Override
	public String toString() {
		int nrNodes = nodes.length;
		StringBuffer inter = new StringBuffer("=== Inter-slice connectivity\n");
		StringBuffer intra = new StringBuffer("=== Intra-slice connectivity\n");
		int index;

		for (int t1 = nrNodes / 2; t1 < nrNodes; t1++) {
			inter.append(data.varNames[t1 - nrNodes / 2] + "_" + "t+1" + "  :  ");
			intra.append(data.varNames[t1 - nrNodes / 2] + "_" + "t+1" + "  :  ");
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
		float llRes = ll.execute(this);
		MDL mdl = new MDL();
		float mdlRes = mdl.llToMDL(llRes, this);
		scores.append("LL score\t:\t" + llRes);
		scores.append("\nMDL score\t:\t" + mdlRes + "\n");
		
		return inter.toString() + intra.toString() + scores.toString();
	}
}
