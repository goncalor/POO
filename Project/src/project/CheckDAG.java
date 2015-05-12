package project;

public interface CheckDAG extends CheckStructure {

	@Override
	public boolean execute(TransitionNetwork network);
}
