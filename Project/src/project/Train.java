package project;

import project.scoringMethods.Score;

/**
 * Allows for multiple implementations of training algorithms of a {@code TransitionNetwork}.
 * @return the trained {@code TransitionNetwork}
 */

public interface Train {

	public TransitionNetwork execute(TransitionNetwork tn, Score sm);
}
