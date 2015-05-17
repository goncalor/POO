package project;

import java.util.Iterator;

public class Inference {
	
	public static void calcInference(TransitionNetwork tn){
		float allThetas[][][] = Theta.calcAllThetas(tn);
		int nrNodes = tn.nrNodes();
		
		int i = 1; //fixar i
		
		int combos=1;
		for(int sizeOfMat = 0; sizeOfMat < nrNodes/2; sizeOfMat++){
			if( sizeOfMat != i){
				combos *= (tn.varDomain[sizeOfMat]+1);
			}
		}
		System.out.println("Combos: " + combos);
		
		int k = 0; // fixar k
		int ji[] = new int[nrNodes/2];
		for(int iter=0; iter<nrNodes/2;iter++){
			if(iter == i){
				ji[iter] = k;
			}else{
				ji[iter] = (tn.varDomain[iter]);
			}
		}
		
		System.out.println("all vals");
		for(int a : ji){
			System.out.print(" " + a);
		}
		
//		jMax = Nijk.convertJitoJ(ji, maxValues);
		
//		
//		for(int p=nrNodes/2; p < nrNodes;p++ ){
//			Node tempNode = tn.getNode(p);
//			for(Iterator<Node> iter=tempNode.iterator(); iter.hasNext(); ){
//				Node parent = iter.next();
//				if(parent.selfIndex < nrNodes/2){
//					System.out.println("get from test data " + p);
//				}else{
//					System.out.println("get all vals " + p);
//				}
//			}
//		}
//		
//		for(int i=0; i < nrNodes/2;i++){		// i is the node
//			for(int k=0; k<=tn.varDomain[i];k++){	// k is the  kth node val
//				
//				
//				
//				
//			}
//		}
		
	}
	
	
	
}

