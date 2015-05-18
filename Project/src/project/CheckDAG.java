package project;

/** for methods that check if an instance of {@link project.TransitionNetwork} is a DAG */
public interface CheckDAG extends CheckStructure {

	@Override
	public boolean execute(Network network);
}
