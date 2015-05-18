package project;

import java.lang.Math;
import java.util.Iterator;

public class MDL extends LL {

	@Override
	public float execute(TransitionNetwork tn) {
		// TODO Auto-generated method stub
		float ll = super.execute(tn);
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
			Node currentNode = tn.getNode(i);
			if( currentNode.nrEdges() == 0){
				qi = 1;
			}else{
				int[] parentsMax = new int[currentNode.nrEdges()];
				int iterator = 0;
				for(Iterator<Node> iter=currentNode.iterator(); iter.hasNext(); ){
					Node parent = iter.next();
					parentsMax[iterator] = tn.varDomain[parent.getIndex()%tn.varDomain.length];
					iterator++;
				}
				qi = Nijk.convertJitoJ(parentsMax, parentsMax);
			}
			returnValue += (tn.varDomain[i%tn.varDomain.length]-1)*qi;
		}
		return returnValue;
	}

	
}
