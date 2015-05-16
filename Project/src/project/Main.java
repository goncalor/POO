package project;

import java.io.File;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {

		if (args.length < 4 || args.length > 5) {
			System.out
					.println("Not enough arguments. Should have: train test score randrest [var]");
			System.exit(0);
		}

		String train = args[0];
		String test = args[1];
		String score = args[2];
		int randrest;
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

		String s = new String(train);
		Data data;
		Parser parse = new Parser();
		data = parse.fromFile(s);

		System.out.println("#Different sets of elements:\n" + data);

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
