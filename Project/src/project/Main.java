package project;

import java.io.File;
import java.io.IOException;

import project.network.NodeOutOfBoundsException;
import project.scoringMethods.LL;
import project.scoringMethods.MDL;
import project.trainMethods.GHCRandRestart;

public class Main {

	public static void main(String[] args) throws IOException, NodeOutOfBoundsException {

		if (args.length < 4 || args.length > 5) {
			System.out
					.println("Not enough arguments. Should have: train test score randrest [var]");
			System.exit(0);
		}

		String train = args[0];
		String test = args[1];
		String score = args[2];
		int randrest = 0;
		int var = -1;

		if (!new File(train).isFile()) {
			System.out.println(train + " not found");
			System.exit(-1);
		} else if (!new File(test).isFile()) {
			System.out.println(test + " not found");
			System.exit(-2);
		} else if (!score.equals("LL") && !score.equalsIgnoreCase("MDL")) {
			System.out.println("Unknown scoring method " + score
					+ ". Expected LL or MDL");
			System.exit(-3);
		}

		try {
			randrest = Integer.parseInt(args[3]);
		} catch (NumberFormatException e) {
			System.out.println("Randrest value must be an integer");
			System.exit(-4);
		}

		if (args.length == 5) {
			try {
				var = Integer.parseInt(args[4]);
			} catch (NumberFormatException e) {
				System.out.println("Var value must be an integer");
				System.exit(-5);
			}
		}

		System.out.printf("Paramaters:\t\t%s   %s   %s   %d   %d\n" , train , test , score , randrest , var);
		// end parse arguments
		
		// build DBN
		String s = new String(train);
		Data data;
		Parser parse = new Parser();
		data = parse.fromFile(s);
		
		System.out.print("Builing DBN:\t\t");
		long buildtime = System.currentTimeMillis();
	
		TransitionNetwork tn = new TransitionNetwork(data, 0);
		
		if( score.equals("LL"))
			tn = tn.train(new GHCRandRestart(randrest), new LL());
		else 
			tn = tn.train(new GHCRandRestart(randrest), new MDL());
		
		buildtime = System.currentTimeMillis() - buildtime;
		System.out.println(buildtime + " ms");
		
		System.out.println("Transition network:");
		System.out.print(tn);

		
		// perform inference
		Slice testData = parse.sliceFromFile(test);
		
		System.out.println("Performing inference:");

		long inferedTime = System.currentTimeMillis();
		int [][]inferedVals;
		
		if(var != -1){
			inferedVals = new int[1][testData.getNrLines()];
		}else{
			inferedVals = new int[tn.nrNodes()/2][testData.getNrLines()];
		}
		
		if (var != -1){
			inferedVals[0] = Inference.calcInference(tn, testData, var);
		}else{
			for(int i=0; i<tn.nrNodes()/2; i++)
				inferedVals[i] = Inference.calcInference(tn, parse.sliceFromFile(test), i);
		}
		
		inferedTime = System.currentTimeMillis() - inferedTime;
		
		if(var != -1){
			System.out.println("Var: " + data.varNames[var]);
			for(int i=0; i<testData.getNrLines(); i++){
				System.out.println("-> instance "+i+":\t\t"+inferedVals[0][i]);
			}
		}else{
			for(int l=0; l< tn.nrNodes()/2; l++){
				System.out.println("Var: " + data.varNames[l]);
				for(int i=0; i<testData.getNrLines(); i++){
					System.out.println("-> instance "+i+":\t\t"+inferedVals[l][i]);
				}
			}
		}
		
		System.out.println("Inferring DBN:\t\t" + inferedTime + " ms");	
	}

}
