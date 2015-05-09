package project;

public class GHC implements Train {

	public void execute(TransitionNetwork T)
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
			if(scoring(new LL(), nTemp) > scoring(new LL(),nMax))
				nMax = randomRestart();
			if(scoring(new LL(), nMax) > scoring(new LL(), nRes))
				nRes = nMax;
			nTemp = nMax;
		}
	}
	
	public TransitionNetwork randomRestart(){
		
		return null;
	}
	
	public TransitionNetwork calcMaxNeighbourhood(TransitionNetwork nInit){
		
		return null;
	}
	
	public float scoring(Score S, TransitionNetwork T)
	{
		return S.execute(this, T);
	}
}
