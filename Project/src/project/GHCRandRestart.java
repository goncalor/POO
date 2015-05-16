package project;

import java.util.Random;

public class GHCRandRestart extends GHC implements Train {

	@Override
	public void execute(TransitionNetwork tn, Score sm) {

		float maxScore = scoring(sm, tn);
		float newScore = maxScore;
		
		//TODO
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
	}

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
