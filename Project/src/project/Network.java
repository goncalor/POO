package project;

public abstract class Network {

	public abstract boolean addEdge(Node<T> p, Node<T> c) throws NodeOutOfBoundsException;
	public abstract boolean remEdge(Node<T> p, Node<T> c) throws NodeOutOfBoundsException;
	public abstract boolean invEdge(Node<T> p, Node<T> c) throws NodeOutOfBoundsException;
	
	public abstract boolean existsEdge(Node<T> p, Node<T> c) throws NodeOutOfBoundsException;
}
