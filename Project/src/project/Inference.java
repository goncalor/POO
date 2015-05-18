package project;

import java.util.Arrays;
import java.util.Iterator;

public class Inference {
	
	public static void calcInference(TransitionNetwork tn, Slice slice){

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
		
//		for(int i=0; i<nrT1Configs; i++)
//			System.out.println(Arrays.toString(d[i]));
		
		int i = 0, j, k, testLine = 0;
		
		int[] testDataLine = slice.getLine(testLine);
		
		int nrParents;
		
		Node[] t1 = Arrays.copyOfRange(tn.cloneNodes(), nrNodes/2, nrNodes);
		
		int[] parentValues, parentMaxs;
		
		for(int t1Config=0; t1Config<nrT1Configs; t1Config++)
		{
			// calculate j for Oijk
		
			nrParents = t1[i].nrEdges();
			parentValues = new int[nrParents];
			parentMaxs= new int[nrParents];
			
			for(Iterator<Node> iter=t1[i].iterator(); iter.hasNext(); )
			{
				Node parent = iter.next();
				
				
				
			}
			
			
			
			
			
		}
		
		
	}
	
	
	
}

