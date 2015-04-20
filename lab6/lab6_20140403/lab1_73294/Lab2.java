package lab1_73294;

import java.util.Arrays;

public class Lab2 {

	private Lab1[] labs1;
	private int nextno;

	public Lab2()
	{
		labs1 = new Lab1[10];			
		nextno = 0;	// not really needed
	}

	boolean associateLab1(Lab1 obj)
	{
		if(nextno >= 10)
		{
			System.out.println("Trying to associate more than 10 objects!");
			System.exit(2);
		}

		for(int i=0; i<nextno; i++)
		{
			if(labs1[i].equals(obj))
				return false;
		}

		labs1[nextno] = obj;
		nextno++;
		return true;	
	}

	public int getLen()
	{
		return nextno+1;
	}

	@Override
	public String toString()
	{
		return "Lab2 [labs1=" + Arrays.toString(labs1) + "]";
	}
}
