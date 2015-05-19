package project.scoringMethods;

import java.util.Iterator;
import java.lang.Math;

import project.Nijk;
import project.TransitionNetwork;
import project.network.Node;

public class LL implements Score {

	@Override
	public float execute(TransitionNetwork tn) {
		float returnValue = 0;

		for (int i = 0; i < tn.nrNodes(); i++) {
			Node self = tn.getNode(i);
			int[] nodeSelf = (int[]) self.content;
			int[][] nijkVals;
			int[][] parents = new int[self.nrEdges()][nodeSelf.length];
			int iterator = 0;
			int[] parentsMax = new int[self.nrEdges()];

			for (Iterator<Node> iter = self.iterator(); iter.hasNext();) {
				Node parent = iter.next();
				parents[iterator] = (int[]) parent.content;
				parentsMax[iterator] = tn.getVarDomain()[parent.getIndex()
						% tn.getVarDomain().length];
				iterator++;
			}

			int tempIndex = i % (tn.getVarDomain().length);
			if (self.nrEdges() == 0) {
				nijkVals = Nijk.calcNijk(tn.getVarDomain()[tempIndex], nodeSelf);
			} else {
				nijkVals = Nijk.calcNijk(tn.getVarDomain()[tempIndex], nodeSelf,
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
