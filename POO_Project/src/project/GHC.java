package project;

import java.util.Random;

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
		
		TransitionNetwork nRes=T, nMax, nTemp = nRes;
		while(true){
			nMax = calcMaxNeighbourhood(nTemp);
			if(scoring(S, nMax) > scoring(S, nRes))
				nRes = nMax;
			nTemp = nMax;
		}
	}
	
	public TransitionNetwork randomRestart(TransitionNetwork T){
		int nrNodes = T.nrNodes();
		int half = nrNodes/2;
		Node[] past = new Node[half];
		Node[] present = new Node[half];
		Random rand = new Random();
		
		for(int i=0; i<half; i++){
			past[i].addParent(present[rand.nextInt()%half]);
			present[i].addParent(present[rand.nextInt()%half]);
			present[i].addParent(past[rand.nextInt()%half]);
			present[i].addChild(present[rand.nextInt()%half]);
		}
		
		return null;
	}
	
	public TransitionNetwork calcMaxNeighbourhood(TransitionNetwork nInit){
		Node tempNode;
		
		for(int i=0; i<nInit.nrNodes(); i++){
			tempNode = nInit.getNode(i);
		}
		
		return null;
	}
	
	public float scoring(Score S, TransitionNetwork T)
	{
		return S.execute(this, T);
	}
}
