package project;

import java.util.ListIterator;
import java.lang.Math;

public class LL implements Score{

	@Override
	public float execute(Train g, TransitionNetwork T) {
		float returnValue = 0;
		float []currentVal = new float[T.nrNodes()];
		
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
				parentsMax[iterator] = T.varDomain[parent.getIndex()];
				i++;
			}
			nijkVals = Nijk.calcNijk(T.varDomain[i], nodeSelf, parentsMax, parents);
			int[] nij = LL.calcNij(nijkVals);
			int index = 0;
			for(int[] x : nijkVals){
				for(int y : x){
					currentVal[index] += nijkVals[index][y]*Math.log10( (nijkVals[index][y])/nij[i] );
				}
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

	
}
