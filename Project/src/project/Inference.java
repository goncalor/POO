package project;

import java.util.Arrays;
import java.util.Iterator;

public class Inference {
	
	public static void calcInference(TransitionNetwork tn, Slice slice){

		float[][][] thetas = Theta.calcAllThetas(tn);
		
		int[] varDomain = tn.varDomain;
		
		int nrT1Configs = 1;	// the number of conbinations of time T+1
		for(int i=0; i<varDomain.length; i++)
			nrT1Configs *= varDomain[i]+1;
		
		// the number of nodes in the transition network (2*numVars)
		int nrNodes = tn.nrNodes();
		
		// d will have all T1 configs
		int[][] d = new int[nrT1Configs][nrNodes];
		
		System.out.println(nrT1Configs);
		
		// populate d
		for(int i=0; i<nrT1Configs; i++)
			d[i] = Nijk.convertJtoJi(i, varDomain);
		
		// print d
//		for(int i=0; i<nrT1Configs; i++)
//			System.out.println(Arrays.toString(d[i]));
		
		int i = 1, j, k = 0, jprime;
		int testLine = 0;	// the line of the test data we're int
		
		int[] testDataLine = slice.getLine(testLine);
		
		int nrParents;
		
		Node[] t1 = Arrays.copyOfRange(tn.cloneNodes(), nrNodes/2, nrNodes);
		
		int[] parentValues, parentMaxs;
		
		float Oijk;	// theta ijk, outside the product operator
		
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
				if(parent.selfIndex < nrNodes/2)
				{
//					System.out.println("parent in t");
					// parent is from t. get the value from test data
					parentValues[auxIndex] = testDataLine[parent.selfIndex];
					parentMaxs[auxIndex] = varDomain[parent.selfIndex];
				}
				else
				{
//					System.out.println("parent in t+1");
					// parent is from t+1. get value from d
					parentValues[auxIndex] = d[t1Config][parent.selfIndex-nrNodes/2];
					parentMaxs[auxIndex] = varDomain[parent.selfIndex-nrNodes/2];
				}
				auxIndex++;
			}

			// now we got the value of j
			j = Nijk.convertJitoJ(parentValues, parentMaxs);
			
//			System.out.println(Arrays.toString(parentValues));
////			System.out.println(Arrays.toString(parentMaxs));
//			System.out.println(j);
			
			Oijk = thetas[i][j][k];

			System.out.println("Oijk  " +i+" " +j+" "+ k+" "+Oijk);
			
			
			
			
			float OljPrimedl;
			
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
					if(parent.selfIndex < nrNodes/2)
					{
//						System.out.println("parent in t");
						// parent is from t. get the value from test data
						parentValuesPrime[auxIndex2] = testDataLine[parent.selfIndex];
						parentMaxsPrime[auxIndex2] = varDomain[parent.selfIndex];
					}
					else
					{
//						System.out.println("parent in t+1");
						// parent is from t+1. get value from d
						parentValuesPrime[auxIndex2] = dprime[t1Config][parent.selfIndex-nrNodes/2];
						parentMaxsPrime[auxIndex2] = varDomain[parent.selfIndex-nrNodes/2];
					}
					auxIndex2++;
				}

				// now we got the value of j'
				jprime = Nijk.convertJitoJ(parentValuesPrime, parentMaxsPrime);
								
//				System.out.println(Arrays.toString(parentValues));
////				System.out.println(Arrays.toString(parentMaxs));
//				System.out.println(j);
				
				OljPrimedl = thetas[l][jprime][dprime[t1Config][l]];

				System.out.println("Oljdl  " +l+" " +jprime+" "+ dprime[t1Config][l]+" "+OljPrimedl);

				
			}
			
		}
		
		
	}
	
	
	
}

