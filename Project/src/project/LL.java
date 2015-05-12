package project;
import java.lang.Math;

public class LL implements Score{

	@Override
	public float execute(Train g, TransitionNetwork T) {
		float [] Nijk = null;
		float [] Nij = null;		// HOW TO STORE Nijk && Nij
		int n=T.nrNodes(); 			// TO BE REPLACED BY ACTUAL SIZE OF N
		int qi=0; 					// NR OF PARENTS OF A CERTAIN NODE
		int []ri=T.varDomain;
		float returnValue = 0;
		
		for(int localMax : T.varDomain){	// this is WRONG!
			qi += localMax;
		}
		
		for(int i=0; i< n;i++){
			for(int j=0; j<qi;j++){
				for(int k=0; k<ri.length;k++){
					returnValue = (float) (Nijk[i+j+k]+Math.log10((Nijk[i+j+k]/Nij[i+j])));
				}
			}
		}
		
//		Nijk = T.calcNijk();
//		Nij = caclNij(Nijk);
		
		return returnValue;
	}
	
	public float calcNij(int [] Nijk){
		float totalVal = 0;
		for(int i : Nijk){
			totalVal +=i;
		}
		return totalVal;
	}

	
}
