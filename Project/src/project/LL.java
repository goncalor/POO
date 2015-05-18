package project;

import java.util.Iterator;
import java.lang.Math;

public class LL implements Score {

	@Override
	public float execute(TransitionNetwork T) {
		float returnValue = 0;

		for (int i = 0; i < T.nrNodes(); i++) {
			Node self = T.getNode(i);
			int[] nodeSelf = (int[]) self.content;
			int[][] nijkVals;
			int[][] parents = new int[self.nrEdges()][nodeSelf.length];
			int iterator = 0;
			int[] parentsMax = new int[self.nrEdges()];

			for (Iterator<Node> iter = self.iterator(); iter.hasNext();) {
				Node parent = iter.next();
				parents[iterator] = (int[]) parent.content;
				parentsMax[iterator] = T.varDomain[parent.getIndex()
						% T.varDomain.length];
				iterator++;
			}

			int tempIndex = i % (T.varDomain.length);
			if (self.nrEdges() == 0) {
				nijkVals = Nijk.calcNijk(T.varDomain[tempIndex], nodeSelf);
			} else {
				nijkVals = Nijk.calcNijk(T.varDomain[tempIndex], nodeSelf,
						parentsMax, parents);
			}

			int[] nij = LL.calcNij(nijkVals);
			int index = 0;
			float[] currentVal = new float[nijkVals.length];

			for (int[] x : nijkVals) {
				for (int y = 0; y < x.length; y++) {
					if (nijkVals[index][y] != 0) {
						float temp1;
						temp1 = (float) (nijkVals[index][y] * (Math
								.log10((double) (nijkVals[index][y])
										/ nij[index]) / Math.log10(2)));
						currentVal[index] += temp1;
					}
				}
				returnValue += currentVal[index];
				index++;
			}
		}
		return returnValue;
	}

	public static int[] calcNij(int[][] Nijk) {
		int[] totalVal = new int[Nijk.length];
		for (int i = 0; i < Nijk.length; i++) {
			for (int j = 0; j < Nijk[i].length; j++) {
				totalVal[i] += Nijk[i][j];
			}
		}
		return totalVal;
	}
}
