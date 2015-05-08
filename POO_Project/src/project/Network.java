package project;

public abstract class Network {

	// 
	public abstract boolean addEdge(Node p, Node c);
	public abstract void remEdge(Node p, Node c);
	public abstract boolean invEdge(Node p, Node c);
	
	public abstract boolean checkDAG();

	Train train;
	public void setTrain(Train T){
		this.train = T;
	}
}