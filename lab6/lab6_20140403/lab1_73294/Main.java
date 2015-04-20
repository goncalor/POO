package lab1_73294;

import java.util.Random;	// or java.util.*

public class Main {

	public static void main(String[] args)
	{
		int n = 0;

		if(args.length != 1)
		{
			System.out.println("An integer must be passed as an argument.");
			System.exit(1);
		}

		try
		{
			n = Integer.parseInt(args[0]);
		}
		catch(NumberFormatException e)
		{
			System.out.println("Argument must be an integer.");
			System.exit(1);
		}

		if(n < 1)
		{
			System.out.println("Input number must be greater than 1.");
			System.exit(1);
		}

		System.out.println(args[0]);

		Lab2[] labs2 = new Lab2[n];	// this only creates a reference to an array with n positions

		int looped = 1;
		Random randomno = new Random();
		while(true)
		{
			// construct n objects from class Lab2
			for(int i=0; i<n; i++)
			{
				labs2[i] = new Lab2();
			}

			// try to assoaciate 10 Lab1 to each Lab2
			for(int i=0; i<n; i++)
			{
				for(int j=0; j<10; j++)
					labs2[i].associateLab1(new Lab1(randomno.nextInt(10), 0));

				if(labs2[i].getLen() == 10)	// was able to make 10 associations
				{
					System.out.println("Found it: "+ labs2[i] +" \nafter " + looped + " iteration(s)");
					System.exit(3);
				}
			}

			looped++;
		}
	}
}
