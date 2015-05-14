package project;
public class GHC implements Train {
	
	public void execute(TransitionNetwork T, Score S)
	{
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
		TransitionNetwork nRes=T, nMax, nTemp = nRes;
//		while(true){
//			nMax = calcMaxNeighbourhood(nTemp);
//			if(scoring(S, nMax) > scoring(S, nRes))
//				nRes = nMax;
//			nTemp = nMax;
//		}
	}
	
	public TransitionNetwork randomRestart(TransitionNetwork T){
		int nrNodes = T.nrNodes();
		int half = nrNodes/2;
//		Node[] past = new Node[half];
//		Node[] present = new Node[half];
//		Random rand = new Random();
		
		for(int i=0; i<half; i++){
//			past[i].addParent(present[rand.nextInt()%half]);
//			present[i].addParent(present[rand.nextInt()%half]);
//			present[i].addParent(past[rand.nextInt()%half]);
//			present[i].addChild(present[rand.nextInt()%half]);
		}
		
		return null;
	}
	
	/** calculates the best score of the neighbours of {@code net}, using
	 *  {@code s} as scoring criteria. {@code net} will be modified by 
	 *  this method */
	public float calcMaxNeighbourhood(TransitionNetwork net, Score s) throws NodeOutOfBoundsException {
		int nrNodes = net.nrNodes();
		float tmpScore;
		operation op = operation.NOP;
		int from=0, to=0;
		float maxScore = Integer.MIN_VALUE;
		
		// add edges from t to t+1 nodes
		for(int t=0; t<nrNodes/2; t++) {
			for(int t1=nrNodes; t1<nrNodes; t1++) {
				if(net.addEdge(net.getNode(t), net.getNode(t1))) {
					if((tmpScore = scoring(s, net)) > maxScore) {
						maxScore = tmpScore;
						op = operation.ADD;
						from = t;
						to = t1;
					}
					net.remEdge(net.getNode(t), net.getNode(t1));
				}
			}
		}
		
		// add intra-temporal edges for t+1
		for(int t1_0=nrNodes; t1_0<nrNodes; t1_0++) {
			for(int t1_1=nrNodes; t1_1<nrNodes; t1_1++) {
				if(t1_0 == t1_1)
					continue;
				if(net.addEdge(net.getNode(t1_0), net.getNode(t1_1))) {
					if((tmpScore = scoring(s, net)) > maxScore) {
						maxScore = tmpScore;
						op = operation.ADD;
						from = t1_0;
						to = t1_1;
					}
					net.remEdge(net.getNode(t1_0), net.getNode(t1_1));
				}
			}
		}
		
		// invert intra-temporal edges
		for(int t1_0=nrNodes; t1_0<nrNodes; t1_0++) {
			for(int t1_1=nrNodes; t1_1<nrNodes; t1_1++) {
				if(t1_0 == t1_1)
					continue;
				if(net.invEdge(net.getNode(t1_0), net.getNode(t1_1))) {
					if((tmpScore = scoring(s, net)) > maxScore) {
						maxScore = tmpScore;
						op = operation.INV;
						from = t1_0;
						to = t1_1;
					}
					net.invEdge(net.getNode(t1_0), net.getNode(t1_1));
				}
			}
		}
		
		// remove edges from t to t+1 nodes
		for(int t=0; t<nrNodes/2; t++) {
			for(int t1=nrNodes; t1<nrNodes; t1++) {
				if(net.remEdge(net.getNode(t), net.getNode(t1))) {
					if((tmpScore = scoring(s, net)) > maxScore) {
						maxScore = tmpScore;
						op = operation.REM;
						from = t;
						to = t1;
					}
					net.addEdge(net.getNode(t), net.getNode(t1));
				}
			}
		}
		
		// remove intra-temporal edges for t+1
		for(int t1_0=nrNodes; t1_0<nrNodes; t1_0++) {
			for(int t1_1=nrNodes; t1_1<nrNodes; t1_1++) {
				if(t1_0 == t1_1)
					continue;
				if(net.remEdge(net.getNode(t1_0), net.getNode(t1_1))) {
					if((tmpScore = scoring(s, net)) > maxScore) {
						maxScore = tmpScore;
						op = operation.REM;
						from = t1_0;
						to = t1_1;
					}
					net.addEdge(net.getNode(t1_0), net.getNode(t1_1));
				}
			}
		}
		
		switch(op) {
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
