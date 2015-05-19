package project.network.structureUtils;

import project.network.Network;

/** for methods that check some kind of property for a {@link project.TransitionNetwork} */
public interface CheckStructure {

	public boolean execute(Network network);
}
