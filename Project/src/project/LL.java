package project;

import java.util.ListIterator;
import java.io.IOException;
import java.lang.Math;

public class LL implements Score{

	@Override
	public float execute(Train g, TransitionNetwork T) {
		float returnValue = 0;
		float []currentVal = new float[T.nrNodes()];
		
		System.out.print("#Nodes: ");
		System.out.println(T.nrNodes());

		System.out.println("Max values:");
		System.out.print("[");
		for(int kk : T.varDomain){
			System.out.print(kk);
		}
		System.out.println("]");
		
		for(int i=0; i<T.nrNodes(); i++){
			Node self = T.getNode(i);
			int[] nodeSelf = (int[]) self.content;
			int[][] nijkVals;
			int[][] parents = new int[self.nrEdges()][nodeSelf.length];
			int iterator = 0;
			int[] parentsMax = new int[self.nrEdges()];		
			
			for(ListIterator<Node> iter=self.iterator(); iter.hasNext(); ){
				Node parent = iter.next();
				parents[iterator] = (int[]) parent.content;
				parentsMax[iterator] = T.varDomain[parent.getIndex()%T.varDomain.length];
				iterator++;
			}
			
			int tempIndex = i%(T.varDomain.length);
			if(self.nrEdges() == 0){
				nijkVals = Nijk.calcNijk(T.varDomain[tempIndex], nodeSelf);
			}else{
				nijkVals = Nijk.calcNijk(T.varDomain[tempIndex], nodeSelf, parentsMax, parents);
			}
			
//			System.out.println("Nijk Matrix:");
//			for(int[]x1:nijkVals){
//				System.out.print("[");
//				for(int x2:x1){
//					System.out.print(" " + x2);
//				}
//				System.out.println("]");
//			}
			
			int[] nij = LL.calcNij(nijkVals);
			int index = 0;

//			System.out.println("Nij Vector:");
//			System.out.print("[");
//			for(int x3:nij){
//				System.out.print(" " + x3);
//			}
//			System.out.println("]");
			
			for(int[] x : nijkVals){
				for(int y = 0; y<x.length;y++){
					if(nijkVals[index][y] != 0){
						float temp1;
						temp1 = (float) (nijkVals[index][y]*Math.log10( (double)(nijkVals[index][y])/nij[index] ));
						currentVal[index] += temp1;
						System.out.println("Calc value: "+temp1+" from " + nijkVals[index][y] + " | " + nij[index]);
					}
				}
				System.out.println("Sum: "+currentVal[index]);
				returnValue += currentVal[index];
				index++;
			}
			
		}
		return returnValue;
	}
	
	public static int[] calcNij(int [][] Nijk){
		int[] totalVal = new int[Nijk.length];
		for(int i=0; i<Nijk.length; i++){
			for(int j=0; j<Nijk[i].length;j++){
				totalVal[i]+=Nijk[i][j];
			}
		}
		return totalVal;
	}

	public static void main(String[] args) throws NodeOutOfBoundsException{
		Data data = null;
		Parser parse = new Parser();
		try {
			data = parse.fromFile(new String("datasets/testLL.csv"));
		} catch (IOException e) {
			System.out.println("No such file");
			System.exit(-1);
		}

//		System.out.println("#Different sets of elements:\n" + data);

		TransitionNetwork tn = new TransitionNetwork(data, 0);
		
//		System.out.println(tn);
		boolean retval;
		retval = tn.addEdge(tn.getNode(1), tn.getNode(0));
		System.out.println(retval);
		
		retval = tn.addEdge(tn.getNode(2), tn.getNode(1));
//		System.out.println(retval);
		
		retval = tn.addEdge(tn.getNode(3), tn.getNode(2));
//		System.out.println(retval);
				
		LL algorithm = new LL();
		float numb = algorithm.execute(null, tn);
		System.out.print("LL result: ");
		System.out.println(numb);
	}
}
