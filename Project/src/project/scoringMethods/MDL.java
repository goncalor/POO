package project.scoringMethods;

import java.lang.Math;
import java.util.Iterator;

import project.Nijk;
import project.TransitionNetwork;
import project.network.Node;

public class MDL extends LL {

	@Override
	public float execute(TransitionNetwork tn) {
		float ll = super.execute(tn);
		int nr = ((int[])tn.getNode(0).content).length;
		
		return (float) (ll - ((Math.log10((double)nr)/Math.log10(2))*calcB(tn.nrNodes(), tn)));
	}
	
	public float llToMDL(float ll, TransitionNetwork tn) {
		int nr = ((int[])tn.getNode(0).content).length;
		
		return (float) (ll - ((Math.log10((double)nr)/Math.log10(2))*calcB(tn.nrNodes(), tn)));
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
					parentsMax[iterator] = tn.getVarDomain()[parent.getIndex()%tn.getVarDomain().length];
					iterator++;
				}
				qi = Nijk.convertJitoJ(parentsMax, parentsMax);
			}
			returnValue += (tn.getVarDomain()[i%tn.getVarDomain().length]-1)*qi;
		}
		return returnValue;
	}
}
