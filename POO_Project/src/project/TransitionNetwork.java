package project;

public abstract class TransitionNetwork extends Network {

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

	public abstract boolean checkDAG();
}
