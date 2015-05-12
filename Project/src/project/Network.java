package project;

public abstract class Network {

	public abstract boolean addEdge(Node p, Node c) throws NodeOutOfBoundsException;
	public abstract boolean remEdge(Node p, Node c) throws NodeOutOfBoundsException;
	public abstract boolean invEdge(Node p, Node c) throws NodeOutOfBoundsException;
	
	public abstract boolean existsEdge(Node p, Node c) throws NodeOutOfBoundsException;
}
