package project;

import project.scoringMethods.Score;

public interface Train {

	public TransitionNetwork execute(TransitionNetwork tn, Score sm);
}
