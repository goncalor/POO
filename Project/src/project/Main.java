package project;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

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
				var = Integer.parseInt(args[3]);
			} catch (NumberFormatException e) {
				System.out.println("Var value must be an integer");
				System.exit(-5);
			}
		}

		
		//
		
		System.out.printf("Paramaters:\t\t%s   %s   %s   %d   %d\n" , train , test , score , randrest , var);
		
		String s = new String(train);
		Data data;
		Parser parse = new Parser();
		data = parse.fromFile(s);
		
		TransitionNetwork tn = new TransitionNetwork(data, 0);		
		
		long buildtime = System.currentTimeMillis();
		System.out.print("Building DBN:\t\t");
		
//		tn = tn.train(new GHCRandRestart(100), new LL());
		
		tn.addEdge(tn.getNode(4), tn.getNode(0));
		tn.addEdge(tn.getNode(4), tn.getNode(1));
		tn.addEdge(tn.getNode(4), tn.getNode(3));
		tn.addEdge(tn.getNode(5), tn.getNode(4));
		tn.addEdge(tn.getNode(5), tn.getNode(3));
		
		for(int i=0; i<tn.nrNodes()/2; i++)
			System.out.println(Arrays.toString(Inference.calcInference(tn, parse.sliceFromFile(test), i)));
		
		buildtime = System.currentTimeMillis() - buildtime;
		
		System.out.println(buildtime + " ms");
		


		System.out.println("Initial network:");
		System.out.println("??????");
		
		System.out.println("Transition network:");
		System.out.println("??????");
		
		System.out.println("Performing inference:");
		System.out.println("-> intance 1: ????");
		
		int infertime = 0;
		System.out.println("Inferring DBN:\t\t" + infertime + " units");
		
		System.out.println(tn);
		
//		float mostProbable[] = Inference.calcInference(tn, parse.sliceFromFile(test), 1,0);	
		
//		System.out.print("VARIABLESSSSS:");
//		for(float x : mostProbable)
//			System.out.println(" " + x);
		
		// testing
		
//		String s = new String(train);
//		Data data;
//		Parser parse = new Parser();
//		data = parse.fromFile(s);
//
//		System.out.println(data);
//
//		TransitionNetwork tn = new TransitionNetwork(data, 0);

//		System.out.println(tn);
//		
//		tn = tn.train(new GHCRandRestart(100), new LL());
//		
//		System.out.println(tn);
//		
//		System.out.println(Theta.calcThetaIJK(1, 1, 1, tn));
		
		// Slice a = new Slice(3);
		// int vals[] = {1,2,3};
		//
		// a.add(vals);
		// a.add(vals);
		// a.add(vals);
		//
		// System.out.println(a);

	}

}
