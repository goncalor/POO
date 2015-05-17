package project;

import java.util.Iterator;

public class Theta {
	
	public static float calcThetaIJK(int i, int j, int k, TransitionNetwork tn){
		Node node = tn.getNode(i);
		int []nodeValues = (int[]) node.content;
		int[][] parents = new int[node.nrEdges()][nodeValues.length];
		int[][] nijkVals;
		int iterator = 0;
		int[] parentsMax = new int[node.nrEdges()];
		
		for(Iterator<Node> iter=node.iterator(); iter.hasNext(); ){
			Node parent = iter.next();
			parents[iterator] = (int[]) parent.content;
			parentsMax[iterator] = tn.varDomain[parent.getIndex()%tn.varDomain.length];
			iterator++;
		}
		
		int tempIndex = i%(tn.varDomain.length);
		if(node.nrEdges() == 0){
			nijkVals = Nijk.calcNijk(tn.varDomain[tempIndex], nodeValues);
		}else{
			nijkVals = Nijk.calcNijk(tn.varDomain[tempIndex], nodeValues, parentsMax, parents);
		}
		
		
		int[] nij = LL.calcNij(nijkVals);
		
		float returnVal = 0;
		
		returnVal = (float) ((nijkVals[j-1][k-1] + 0.5)/((float)nij[j-1]+ ((tn.varDomain[node.getIndex()%tn.varDomain.length]+1) *0.5)));
		
		return returnVal;
	}

}