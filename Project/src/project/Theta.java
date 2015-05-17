package project;

import java.io.IOException;
import java.util.Iterator;

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
		
//		System.out.print("Nijk: [");
//		for(int []a: nijkVals){
//			for(int b:a){
//				System.out.print(" "+ b);
//			}
//			System.out.println("");
//		}
//		System.out.println("]");
		
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
//				System.out.println("i: "+i+" j: "+maxJ + " k: "+k+ " " +Theta.calcThetaIJK(i-tn.nrNodes()/2, maxJ, k, tn));
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

			float [][]AllThetas = new float[maxJ][tn.varDomain[currentNode.getIndex()%tn.varDomain.length]+1];
			for(j=0; j < maxJ; j++){
				for(int k=0; k<= tn.varDomain[currentNode.getIndex()%tn.varDomain.length];k++){
//					System.out.println("i: "+i+" j: "+j + " k: "+k+ " " + Theta.calcThetaIJK(i-tn.nrNodes()/2, j, k, tn));
					AllThetas[j][k] = Theta.calcThetaIJK(i-tn.nrNodes()/2, j, k, tn);
				}
			}
			return AllThetas;
		}
		
	}

	public static void main(String[] args) throws IOException, NodeOutOfBoundsException{
		String s = new String("datasets/train-data-fortest.csv");
		Data data;
		Parser parse = new Parser();
		data = parse.fromFile(s);
		
		TransitionNetwork tn = new TransitionNetwork(data, 0);
		tn.addEdge(tn.getNode(4), tn.getNode(0));
		tn.addEdge(tn.getNode(4), tn.getNode(1));
		tn.addEdge(tn.getNode(4), tn.getNode(3));
		tn.addEdge(tn.getNode(5), tn.getNode(4));
		tn.addEdge(tn.getNode(5), tn.getNode(3));
		
		System.out.println(tn);
		
		float allThetas[][][] = Theta.calcAllThetas(tn);
		
		Inference.calcInference(tn);
		
//		int i=0,j = 0,k=0;
//		for(float[][] a:allThetas){
//			for(float[] b : a){
//				for(float c : b){
//					System.out.println("i: "+i+" j: "+j + " k: "+k+ " " +c);
//					k++;
//				}
//				k=0;
//				j++;
//			}
//			j=0;
//			i++;
//		}
		
//		System.out.println(allThetas[1][0][0]*allThetas[0][0][0]*allThetas[2][0][0]
//				+allThetas[1][0][0]*allThetas[0][0][0]*allThetas[2][0][1]
//				+allThetas[1][1][0]*allThetas[0][0][1]*allThetas[2][1][0]
//				+allThetas[1][1][0]*allThetas[0][0][1]*allThetas[2][1][1]);
		
//		int j, maxJ;
//		
//		for(int i=tn.nrNodes()/2; i < tn.nrNodes();i++){
//			Node currentNode = tn.getNode(i);
//			if(currentNode.nrEdges() == 0){
//				maxJ = 0;
//				for(int k=0; k<=tn.varDomain[currentNode.getIndex()%tn.varDomain.length];k++){
////					System.out.println("i: "+i+" j: "+maxJ + " k: "+k+ " " +Theta.calcThetaIJK(i-tn.nrNodes()/2, maxJ, k, tn));
//					Theta.calcThetaIJK(i-tn.nrNodes()/2, maxJ, k, tn);
//				}
//			}else{
//
//				int parentsMax[] = new int[currentNode.nrEdges()];
//				int iterator=0;
//				for(Iterator<Node> iter=currentNode.iterator(); iter.hasNext(); ){
//					Node parent = iter.next();
//					parentsMax[iterator] = tn.varDomain[parent.getIndex()%tn.varDomain.length];
//					iterator++;
//				}
//				maxJ = Nijk.convertJitoJ(parentsMax, parentsMax);
//				for(j=0; j < maxJ; j++){
//					for(int k=0; k<= tn.varDomain[currentNode.getIndex()%tn.varDomain.length];k++){
////						System.out.println("i: "+i+" j: "+j + " k: "+k+ " " + Theta.calcThetaIJK(i-tn.nrNodes()/2, j, k, tn));
//						Theta.calcThetaIJK(i-tn.nrNodes()/2, j, k, tn);
//					}
//				}
//			}
//		}
		
	}

}