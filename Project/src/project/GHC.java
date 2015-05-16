package project;

public class GHC implements Train {
	
	public void execute(TransitionNetwork tn, Score sm)	{
		/*	Input: Initial Structure ninit, dataset D, Scoring function, stopping criteria  
		 * 	Output: Nres
		 * 
		 * 	Nres = Ninit , N' = Nres
		 * 	while stopping criteria is not satisfied
		 * 		N'' = arg max scoring(N)
		 * 		if scoring(N') > scoring(N'') then N'' = random(N)
		 * 		if scoring(N'') > scoring(Nres) then Nres = N''
		 * 		N' = N''
		 * 	end while
		 * 	return Nres
		 */
		
		// we can also store the values of scoring and have nMax be a parameter so that we can return the score in order to not have to compute it again
//		TransitionNetwork nRes=T, nMax, nTemp = nRes;
//		while(true){
//			nMax = calcMaxNeighbourhood(nTemp);
//			if(scoring(S, nMax) > scoring(S, nRes))
//				nRes = nMax;
//			nTemp = nMax;
//		}
		
		float maxScore = scoring(sm, tn);
		float newScore = maxScore;
		
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
		
		System.out.println("train score:" + maxScore);
	}
	
	
	/** calculates the best score of the neighbours of {@code net}, using
	 *  {@code s} as scoring criteria. {@code net} will be modified only if
	 *  a score better than {@code threshold} is attained 
	 *  @return the score of the best neighbour of {@code net} */
	public float calcMaxNeighbourhood(TransitionNetwork net, Score s, float threshold) throws NodeOutOfBoundsException {
		int nrNodes = net.nrNodes();
		float tmpScore;
		operation op = operation.NOP;
		int from=0, to=0;
		float maxScore = Integer.MIN_VALUE;
		
		// add edges from t to t+1 nodes
		for(int t=0; t<nrNodes/2; t++) {
			for(int t1=nrNodes; t1<nrNodes; t1++) {
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
		for(int t1_0=nrNodes; t1_0<nrNodes; t1_0++) {
			for(int t1_1=nrNodes; t1_1<nrNodes; t1_1++) {
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
		for(int t1_0=nrNodes; t1_0<nrNodes; t1_0++) {
			for(int t1_1=nrNodes; t1_1<nrNodes; t1_1++) {
				if(t1_0 == t1_1)
					continue;
				if(net.invEdge(net.getNode(t1_1), net.getNode(t1_0))) {
					if((tmpScore = scoring(s, net)) > maxScore) {
						maxScore = tmpScore;
						op = operation.INV;
						from = t1_1;
						to = t1_0;
					}
					net.invEdge(net.getNode(t1_1), net.getNode(t1_0));
				}
			}
		}
		
		// remove edges from t to t+1 nodes
		for(int t=0; t<nrNodes/2; t++) {
			for(int t1=nrNodes; t1<nrNodes; t1++) {
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
		for(int t1_0=nrNodes; t1_0<nrNodes; t1_0++) {
			for(int t1_1=nrNodes; t1_1<nrNodes; t1_1++) {
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
	
	public float scoring(Score S, TransitionNetwork T)
	{
		return S.execute(this, T);
	}
	
	
	public enum operation {
	    ADD, REM, INV, NOP
	}
}
