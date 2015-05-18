package project;

import java.util.*;

public class Slice {
	private List<List<Integer>> xi;
	public static int numVar;

	/** creates a new slice for {@code n} variables */
	public Slice(int n) {
		numVar = n;
		xi = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			xi.add(new ArrayList<Integer>());
		}
	}

	/** adds a new line {@code values} to this slice */
	public void add(int values[]) {
		for (int i = 0; i < numVar; i++) {
			xi.get(i).add(new Integer(values[i]));
		}
	}

	/** gets the {@code col}th from this slice */
	public int[] getCol(int col) {
		int[] ret = new int[xi.get(col).size()];

		Iterator<Integer> iterator = xi.get(col).iterator();
		for (int i = 0; i < ret.length; i++) {
			ret[i] = iterator.next().intValue();
		}
		return ret;
	}
	
	/** gets the {@code i}th line of the slice */
	public int[] getLine(int line) {
		int[] ret = new int[numVar];
		
		for(int i=0; i<numVar; i++)
			ret[i] = this.getCol(i)[line];

		return ret;
	}

	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < numVar; i++) {
			s = s + xi.get(i).toString() + ',';
		}
		return "Slice [Xi=" + s + "]";
	}

}
