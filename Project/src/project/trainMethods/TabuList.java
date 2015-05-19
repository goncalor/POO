package project.trainMethods;

import java.util.HashSet;
import java.util.Set;

import project.network.Network;

/**
 * Auxiliar class for the implementation of Greddy Hill Climbing using Tabu List.
 * 
 */

public class TabuList {
	protected Set<Integer> tabu;
	
	TabuList(){
		this.tabu = new HashSet<Integer>();
	}
	
	/** @return true if the Network {@code tn} isn't already on the HashSet*/
	public boolean addNetwork(Network tn){
		return this.tabu.add(tn.hashCode());
	}
	
	/** @return true if the Network {@code tn} is already on the HastSet*/
	public boolean containsNetwork(Network tn){
		return this.tabu.contains(tn.hashCode());
	}
}
