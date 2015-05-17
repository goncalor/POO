package project;

import java.lang.Math;

public class MDL extends LL {

	@Override
	public float execute(Train g, TransitionNetwork tn) {
		// TODO Auto-generated method stub
		float ll = super.execute(g, tn);
		int nr = tn.nrNodes();
		
		return (float) (ll - ((Math.log10((double)nr)/Math.log10(2))*calcB(nr, tn)));
	}
	
	public float llToMDL(float ll, TransitionNetwork tn) {
		int nr = tn.nrNodes();
		
		return (float) (ll - ((Math.log10((double)nr)/Math.log10(2))*calcB(nr, tn)));
	}
	
	public float calcB(int nrNodes, TransitionNetwork tn){
		int returnValue=0;
		int qi=1;			//Calc qi
		for(int i=0; i< nrNodes; i++){
			returnValue += (tn.varDomain[i%tn.varDomain.length]-1)*qi;
		}
		return returnValue;
	}

	
}
