package project;

import java.util.HashSet;
import java.util.Set;

public class TabuList {
	protected Set<Integer> tabu;
	
	TabuList(){
		this.tabu = new HashSet<Integer>();
	}
	
	public boolean addNetwork(Network n){
		return this.tabu.add(n.hashCode());
	}
	
	public boolean containsNetwork(Network n){
		return this.tabu.contains(n.hashCode());
	}
}
