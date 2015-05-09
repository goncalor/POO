package project;

public class TransitionNetwork extends Network {

	public TransitionNetwork() {
		
	}
	
	@Override
	public boolean addEdge(Node p, Node c) {

		return false;
	}

	@Override
	public void remEdge(Node p, Node c) {

	}

	@Override
	public boolean invEdge(Node p, Node c) {

		return false;
	}

	CheckStructure checkDAG;
	public void setCheckDAG(CheckStructure T) {
		this.checkDAG = T;
	}

	Train train;
	public void setTrain(Train T) {
		this.train = T;
	}
}
