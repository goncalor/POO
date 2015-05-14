package project;

import java.lang.Math;

public class MDL extends LL {

	@Override
	public float execute(Train g, TransitionNetwork T) {
		// TODO Auto-generated method stub
		float ll = super.execute(g, T);
		int nr = T.nrNodes();
		
		return (float) (ll - (Math.log10((double)nr)*calcB(nr, T)));
	}
	
	public float calcB(int nrNodes, TransitionNetwork T){
		int returnValue=0;
		int qi=1;			//Calc qi
		for(int i=0; i< nrNodes; i++){
			returnValue += (T.varDomain[i%T.varDomain.length]-1)*qi;
		}
		return returnValue;
	}

	
}
