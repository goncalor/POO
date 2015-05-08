package project;

import java.util.*;

public class Slice {
	private List<List<Integer>> xi;
	static int numVar;

	public Slice(int n) {
		numVar = n;
		xi = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			xi.add(new ArrayList<Integer>());
		}
	}

	public void add(int values[]) {
		for (int i = 0; i < numVar; i++) {
			xi.get(i).add(new Integer(values[i]));
		}
	}

	public int[] get(int col) {
		int[] ret = new int[xi.get(col).size()];

		Iterator<Integer> iterator = xi.get(col).iterator();
		for (int i = 0; i < ret.length; i++) {
			ret[i] = iterator.next().intValue();
		}
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
