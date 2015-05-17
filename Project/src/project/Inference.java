package project;

import java.util.Iterator;

public class Inference {
	
	public float[] calcInference(TransitionNetwork tn, Slice testData, int var){
		Node[] inferenceNetwork = tn.cloneNodes();
		int[] maxValues = tn.varDomain;
		for(int i=0; i< inferenceNetwork.length/2;i++){
			inferenceNetwork[i].content = testData.get(i);
		}
		
		int[] d = new int[tn.nrNodes()/2];
		
		float probabilities[] = new float[maxValues[var+tn.nrNodes()/2]+1];
		
		for(int varValue=0; varValue <= maxValues[var+tn.nrNodes()/2];varValue++){
			for(int ptr=0; ptr<d.length;ptr++){
				if(ptr == var){
					continue;
				}
				for( ; d[ptr] < maxValues[ptr]; d[ptr]++){
					int[] ji = new int[inferenceNetwork[var].nrEdges()];
					int[] parentMax = new int[inferenceNetwork[var].nrEdges()];
					int x=0;
					for(Iterator<Node> iter=inferenceNetwork[var].iterator(); iter.hasNext(); ){
						Node parent = iter.next();
						if(parent.selfIndex < tn.nrNodes()/2){
							ji[x] = (int) parent.content;
							parentMax[x] = tn.varDomain[parent.selfIndex];
						}else{
							ji[x] = d[parent.selfIndex-tn.nrNodes()/2];
							parentMax[x] = tn.varDomain[parent.selfIndex];
						}
						x++;
					}
					
					int j = Nijk.convertJitoJ(ji, parentMax);
					
					float thetaVal = Theta.calcThetaIJK(var, j, varValue, tn);
					
					for(int l=0; l<tn.nrNodes()/2;l++){
						if( l == var){
							continue;
						}
						thetaVal *= Theta.calcThetaIJK(l, j, d[l], tn);
					}
					probabilities[varValue] += thetaVal;
				}
			}
		}
		
		return probabilities;
	}
	
}

