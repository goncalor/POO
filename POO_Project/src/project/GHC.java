package project;

public class GHC implements Train {

	public void train()
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
		
		TransitionNetwork nRes, nMax, n = null;
		while(true){
			nMax = calcMaxNeighbourhood(n);
		}
	}
	
	public TransitionNetwork calcMaxNeighbourhood(TransitionNetwork nInit){
		
		return null;
	}
	
	Score score;
	public void setScore(Score T)
	{
		this.score = T;
	}
}
