package project;

import java.util.Random;

public class GHCRandRestart extends GHC implements Train {

	protected int randRestNr;
	
	public GHCRandRestart(int randRestNr) {
		if(randRestNr < 0)
			randRestNr = 1;
		this.randRestNr = randRestNr;
	}
	
	@Override
	public TransitionNetwork execute(TransitionNetwork tn, Score sm) {

		float maxScore = Integer.MIN_VALUE;	// local
		float bestScore = Integer.MIN_VALUE;	// best of locals
		float newScore = scoring(sm, tn);
	
		TransitionNetwork newnet = tn;
		
		int i = 0;
		do {

			while (maxScore < newScore) { // Nres < N'
				maxScore = newScore;
				try {
					newScore = calcMaxNeighbourhood(newnet, sm, maxScore);
				} catch (NodeOutOfBoundsException e) {
					e.printStackTrace();
					System.out.println("Error in GHCRandRestart");
					System.exit(-1);
				}
			}
			
			if(maxScore > bestScore) {
				bestScore = maxScore;
				tn = newnet;
				System.out.println("THIS IS IT!!\n"+tn);
			}
			
			newnet = randomRestart(tn);
			newScore = scoring(sm, newnet);
			maxScore = Integer.MIN_VALUE;
			
			i++;
		}
		while(i < randRestNr);
		
		System.out.println("train score:" + bestScore);
		
		return tn;
	}

	
	/**
	 * @return a new transtion network created with the same nodes as {@code net}
	 *         but with random intra and inter slice edges
	 */
	public TransitionNetwork randomRestart(TransitionNetwork net) {
		TransitionNetwork newnet = net.cloneResetEdges();
		int nrNodes = newnet.nrNodes();
//		System.out.println("RANDOM RESTART BEGIN:\n" +newnet);
		Random rand = new Random();
		int nrEdgesInter = rand.nextInt(nrNodes/2+1);
		// (N-1) + (N-2) + .. + 1 + 0 == (N-1)(N)/2 is the max number of edges that can be in a DAG
		int nrEdgesIntra = rand.nextInt((nrNodes-1)*(nrNodes)/2);
		
		// add random edges from t to t+1 (in fact from t+1 to t, because we point to parents)
		for(int inter=0; inter<nrEdgesInter; inter++) {
			try {
				newnet.addEdge(newnet.getNode(nrNodes/2+rand.nextInt(nrNodes/2)), newnet.getNode(rand.nextInt(nrNodes/2)));
			} catch (NodeOutOfBoundsException e) {
				continue;
			}
		}
		
		// add random edged in time t+1
		for(int intra=0; intra<nrEdgesIntra; intra++) {
			try {
				int child = nrNodes/2+rand.nextInt(nrNodes/2);
				int parent = nrNodes/2+rand.nextInt(nrNodes/2);
				if(child != parent)
					newnet.addEdge(newnet.getNode(child), newnet.getNode(parent));
			} catch (NodeOutOfBoundsException e) {
				continue;
			}
		}

//		System.out.println("RANDOM RESTART END :\n" +newnet);
		return newnet;
	}
}
