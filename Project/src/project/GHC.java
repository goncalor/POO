package project;

public class GHC implements Train {
	
	/**
	 * trains the transition network {@code tn} with the scoring method
	 * {@code sm}. {@code tn} is not changed
	 *
	 * @return the trained network
	 */
	public TransitionNetwork execute(TransitionNetwork tn, Score sm){
		float maxScore = Integer.MIN_VALUE; 
		float newScore = scoring(sm, tn);
		tn = tn.cloneResetEdges();
		
		while(maxScore < newScore) {	// Nres < N'
			maxScore = newScore;
			try {
				newScore = calcMaxNeighbourhood(tn, sm, maxScore);
			} catch (NodeOutOfBoundsException e) {
				e.printStackTrace();
				System.out.println("Error in GHC");
				System.exit(-1);
			}
		}
		return tn;
	}
	
	/**
	 * calculates the best score of the neighbours of {@code net}, using
	 * {@code s} as scoring criteria. {@code net} will be modified only if a
	 * score better than {@code threshold} is attained
	 * 
	 * @return the score of the best neighbour of {@code net}
	 */
	public float calcMaxNeighbourhood(TransitionNetwork net, Score s, float threshold) throws NodeOutOfBoundsException {
		int nrNodes = net.nrNodes();
		float tmpScore;
		operation op = operation.NOP;
		int from=0, to=0;
		float maxScore = Integer.MIN_VALUE;
		
		// add edges from t to t+1 nodes
		for(int t=0; t<nrNodes/2; t++) {
			for(int t1=nrNodes/2; t1<nrNodes; t1++) {
				if(net.addEdge(net.getNode(t1), net.getNode(t))) {
					if((tmpScore = scoring(s, net)) > maxScore) {
						maxScore = tmpScore;
						op = operation.ADD;
						from = t1;
						to = t;
					}
					net.remEdge(net.getNode(t1), net.getNode(t));
				}
			}
		}
		
		// add intra-temporal edges for t+1
		for(int t1_0=nrNodes/2; t1_0<nrNodes; t1_0++) {
			for(int t1_1=nrNodes/2; t1_1<nrNodes; t1_1++) {
				if(t1_0 == t1_1)
					continue;
				if(net.addEdge(net.getNode(t1_1), net.getNode(t1_0))) {
					if((tmpScore = scoring(s, net)) > maxScore) {
						maxScore = tmpScore;
						op = operation.ADD;
						from = t1_1;
						to = t1_0;
					}
					net.remEdge(net.getNode(t1_1), net.getNode(t1_0));
				}
			}
		}
		
		// invert intra-temporal edges
		for(int t1_0=nrNodes/2; t1_0<nrNodes; t1_0++) {
			for(int t1_1=nrNodes/2; t1_1<nrNodes; t1_1++) {
				if(t1_0 == t1_1)
					continue;
				if(net.invEdge(net.getNode(t1_1), net.getNode(t1_0))) {
					if((tmpScore = scoring(s, net)) > maxScore) {
						maxScore = tmpScore;
						op = operation.INV;
						from = t1_1;
						to = t1_0;
					}
					net.invEdge(net.getNode(t1_0), net.getNode(t1_1));
				}
			}
		}
		
		// remove edges from t to t+1 nodes
		for(int t=0; t<nrNodes; t++) {
			for(int t1=nrNodes/2; t1<nrNodes; t1++) {
				if(net.remEdge(net.getNode(t1), net.getNode(t))) {
					if((tmpScore = scoring(s, net)) > maxScore) {
						maxScore = tmpScore;
						op = operation.REM;
						from = t1;
						to = t;
					}
					net.addEdge(net.getNode(t1), net.getNode(t));
				}
			}
		}
		
		// remove intra-temporal edges for t+1
		for(int t1_0=nrNodes/2; t1_0<nrNodes; t1_0++) {
			for(int t1_1=nrNodes/2; t1_1<nrNodes; t1_1++) {
				if(t1_0 == t1_1)
					continue;
				if(net.remEdge(net.getNode(t1_1), net.getNode(t1_0))) {
					if((tmpScore = scoring(s, net)) > maxScore) {
						maxScore = tmpScore;
						op = operation.REM;
						from = t1_1;
						to = t1_0;
					}
					net.addEdge(net.getNode(t1_1), net.getNode(t1_0));
				}
			}
		}
		
		// if a network was found with score better than threshold perform the
		// operation that improves the current network
		if (maxScore > threshold) {
			switch (op) {
			case ADD:
				net.addEdge(net.getNode(from), net.getNode(to));
				break;

			case INV:
				net.invEdge(net.getNode(from), net.getNode(to));
				break;

			case REM:
				net.remEdge(net.getNode(from), net.getNode(to));
				break;

			default:
				break;
			}
		}
		return maxScore;
	}
	
	/**
	 * @return the score of the transition network {@code tn} by applying the
	 *         scoring criteria {@code sm}
	 */
	public float scoring(Score sc, TransitionNetwork tn)
	{
		return sc.execute(tn);
	}
	
	public enum operation {
	    ADD, REM, INV, NOP
	}
}
