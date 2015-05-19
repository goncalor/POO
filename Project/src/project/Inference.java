package project;

import java.util.Arrays;
import java.util.Iterator;

import project.network.Node;

public class Inference {
	
	/**
	 * infers over some test data with a trained network
	 * 
	 * @param tn
	 *            a trained network we want to use for inference
	 * @param testData
	 *            a slice containing the data we want to infer from
	 * @param i
	 *            the number of the node we want to infer about
	 * 
	 * @return the inferred values for each line of the slice
	 */
	public static int[] calcInference(TransitionNetwork tn, Slice testData, int i){

		float[][][] thetas = Theta.calcAllThetas(tn);
		
		int[] varDomain = tn.varDomain;
		
		int nrT1Configs = 1;	// the number of conbinations of time T+1
		for(int tmp=0; tmp<varDomain.length; tmp++)
			nrT1Configs *= varDomain[tmp]+1;
		
		// the number of nodes in the transition network (2*numVars)
		int nrNodes = tn.nrNodes();
		
		// d will have all T1 configs
		int[][] d = new int[nrT1Configs][nrNodes];
		
		// populate d
		for(int tmp=0; tmp<nrT1Configs; tmp++)
			d[tmp] = Nijk.convertJtoJi(tmp, varDomain);
		
		
		int j, k = 0, jprime;
		int testLine = 1;	// the line of the test data we're int
		
		int[] testDataLine = testData.getLine(testLine);
		
		int nrParents;
		
		Node[] t1 = Arrays.copyOfRange(tn.cloneNodes(), nrNodes/2, nrNodes);
		
		int[] parentValues, parentMaxs;
		
		int[] inferedVals = new int[testData.getCol(0).length];
		
		for(testLine=0; testLine<testData.getCol(0).length; testLine++)
		{
			float bestVal = 0; // this is the best value for the local probability of this line of test data
			for(k=0; k <= varDomain[i]; k++)
			{
				float Oijk;	// theta ijk, outside the product operator
				float probabilityOfI=0;
				// iterate over all configurations in d
				for(int t1Config=0; t1Config<nrT1Configs; t1Config++)
				{
					if(d[t1Config][i] != k)
					{
						continue;
					}
					
					// calculate j for Oijk		
					nrParents = t1[i].nrEdges();
					parentValues = new int[nrParents];
					parentMaxs= new int[nrParents];
					
					int auxIndex = 0;
					for(Iterator<Node> iter=t1[i].iterator(); iter.hasNext(); )
					{
						Node parent = iter.next();
						
						// populate parentValues and parentMaxs
						if(parent.getIndex() < nrNodes/2)
						{
							// parent is from t. get the value from test data
							parentValues[auxIndex] = testDataLine[parent.getIndex()];
							parentMaxs[auxIndex] = varDomain[parent.getIndex()];
						}
						else
						{
							// parent is from t+1. get value from d
							parentValues[auxIndex] = d[t1Config][parent.getIndex()-nrNodes/2];
							parentMaxs[auxIndex] = varDomain[parent.getIndex()-nrNodes/2];
						}
						auxIndex++;
					}
		
					// now we got the value of j
					j = Nijk.convertJitoJ(parentValues, parentMaxs);
					
					Oijk = thetas[i][j][k];
					
					float OljPrimedl;
					float multOverL=1;
					
					// iterate over all nodes in t+1 except for the ith node
					for(int l=0; l<t1.length; l++)
					{
						if(l == i)
						{
							continue;
						}
						
						int nrT1ConfigsPrime = 1;	// the number of conbinations of time T+1
						for(int tmp=0; tmp<varDomain.length; tmp++)
							nrT1ConfigsPrime *= varDomain[tmp]+1;
						
						// d will have all T1 configs
						int[][] dprime = new int[nrT1ConfigsPrime][nrNodes];
						
						// populate d
						for(int tmp=0; tmp<nrT1ConfigsPrime; tmp++)
							dprime[tmp] = Nijk.convertJtoJi(tmp, varDomain);
		
						// calculate j' for Ol,j',d(l)
						int nrParentsPrime = t1[l].nrEdges();
						int[] parentValuesPrime = new int[nrParentsPrime];
						int[] parentMaxsPrime = new int[nrParentsPrime];
						
						int auxIndex2 = 0;
						for(Iterator<Node> iter=t1[l].iterator(); iter.hasNext(); )
						{
							Node parent = iter.next();
							
							// populate parentValues and parentMaxs
							if(parent.getIndex() < nrNodes/2)
							{
								// parent is from t. get the value from test data
								parentValuesPrime[auxIndex2] = testDataLine[parent.getIndex()];
								parentMaxsPrime[auxIndex2] = varDomain[parent.getIndex()];
							}
							else
							{
								// parent is from t+1. get value from d
								parentValuesPrime[auxIndex2] = dprime[t1Config][parent.getIndex()-nrNodes/2];
								parentMaxsPrime[auxIndex2] = varDomain[parent.getIndex()-nrNodes/2];
							}
							auxIndex2++;
						}
		
						// now we got the value of j'
						jprime = Nijk.convertJitoJ(parentValuesPrime, parentMaxsPrime);
						
						OljPrimedl = thetas[l][jprime][dprime[t1Config][l]];
						multOverL *=OljPrimedl;
					}
					probabilityOfI += multOverL*Oijk;
				}
				if(probabilityOfI > bestVal){
					bestVal = probabilityOfI;
					inferedVals[testLine] = k;
				}
				
			}
		}
		return inferedVals;
	}
}

