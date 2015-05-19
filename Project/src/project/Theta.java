package project;

import java.util.Iterator;

import project.network.Node;
import project.scoringMethods.LL;

public class Theta {
	
	public static float calcThetaIJK(int i, int j, int k, TransitionNetwork tn){
		Node node = tn.getNode(i+tn.nrNodes()/2);
		int[] nodeValues = (int[]) node.content;
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
		
		returnVal = (float) ((nijkVals[j][k] + 0.5)/((float)nij[j]+ ((tn.varDomain[node.getIndex()%tn.varDomain.length]+1) *0.5)));
		
		return returnVal;
	}
	
	public static float[][][] calcAllThetas(TransitionNetwork tn){
		float returnVal[][][] = new float[tn.nrNodes()/2][][];
		for(int i=tn.nrNodes()/2; i < tn.nrNodes();i++){
			returnVal[i-tn.nrNodes()/2] = Theta.calcThetasForNodeI(tn, i);
		}
		return returnVal;
	}
	
	private static float[][] calcThetasForNodeI(TransitionNetwork tn, int i){
		int j, maxJ;
		Node currentNode = tn.getNode(i);
		if(currentNode.nrEdges() == 0){
			maxJ = 0;
			float [][]AllThetas = new float[maxJ+1][tn.varDomain[currentNode.getIndex()%tn.varDomain.length]+1];
			for(int k=0; k<=tn.varDomain[currentNode.getIndex()%tn.varDomain.length];k++){
				AllThetas[maxJ][k] = Theta.calcThetaIJK(i-tn.nrNodes()/2, maxJ, k, tn);
			}
			return AllThetas;
		}else{
			int parentsMax[] = new int[currentNode.nrEdges()];
			int iterator=0;
			for(Iterator<Node> iter=currentNode.iterator(); iter.hasNext(); ){
				Node parent = iter.next();
				parentsMax[iterator] = tn.varDomain[parent.getIndex()%tn.varDomain.length];
				iterator++;
			}
			maxJ = Nijk.convertJitoJ(parentsMax, parentsMax);

			float [][]AllThetas = new float[maxJ+1][tn.varDomain[currentNode.getIndex()%tn.varDomain.length]+1];
			for(j=0; j <= maxJ; j++){
				for(int k=0; k<= tn.varDomain[currentNode.getIndex()%tn.varDomain.length];k++){
					AllThetas[j][k] = Theta.calcThetaIJK(i-tn.nrNodes()/2, j, k, tn);
				}
			}
			return AllThetas;
		}	
	}
}