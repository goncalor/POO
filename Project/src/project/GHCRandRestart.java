package project;

import java.util.Random;

public class GHCRandRestart extends GHC implements Train {

	private int randRestNr;
	
	public GHCRandRestart(int randRestNr) {
		if(randRestNr < 0)
			randRestNr = 1;
		this.randRestNr = randRestNr;
	}
	
	@Override
	public void execute(TransitionNetwork tn, Score sm) {

		float maxScore = Integer.MIN_VALUE;
		float newScore = scoring(sm, tn);
	
		TransitionNetwork newnet;
		
		for (int i = 0; i < randRestNr; i++) {
			// TODO restart network
			while (maxScore < newScore) { // Nres < N'
				maxScore = newScore;
				try {
					newScore = calcMaxNeighbourhood(tn, sm, maxScore);
				} catch (NodeOutOfBoundsException e) {
					e.printStackTrace();
					System.out.println("Error in GHCRandRestart");
					System.exit(-1);
				}
			}
		}
		
		System.out.println("train score:" + maxScore);
	}

	
	/** */
	public TransitionNetwork randomRestart(TransitionNetwork net) {
		TransitionNetwork newnet = net.cloneResetEdges();
		int nrNodes = newnet.nrNodes();
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
				newnet.addEdge(newnet.getNode(nrNodes/2+rand.nextInt(nrNodes/2)), newnet.getNode(nrNodes/2+rand.nextInt(nrNodes/2)));
			} catch (NodeOutOfBoundsException e) {
				continue;
			}
		}
		
		return newnet;
	}
}
