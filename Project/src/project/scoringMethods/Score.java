package project.scoringMethods;

import project.TransitionNetwork;

/**
 * Interface Score allows for the usage of multiple scoring methods and criteria
 * without the user having to worry about which one is being used
 * 
 */
public interface Score {
	
	public float execute(TransitionNetwork tn);

}
