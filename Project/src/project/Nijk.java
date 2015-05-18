package project;

/**
 * This class consists exclusively of static methods to calculate the Nijk
 * parameters of a certain {@code Node}.
 * <p>
 * This class is intended to be used when you have a certain {@code Node} and a
 * parent configuration of that {@code Node} and you wish to calculate the Nijk
 * parameters of that Node, in general you need to iterate over all {@code Node}
 * 's and call {@code Nijk.calcNijk(int, int[], int[], int...)}.
 * 
 * @see Node
 *
 */
public class Nijk {
	/**
	 * Converts a parent configuration into a simple integer in order to be
	 * arranged into a single matrix.
	 * 
	 * @param ji
	 *             parent configuration
	 * @param maxValues
	 *             maximum values of the parents {@code ji}
	 * @return j  integer that matches the configuration given
	 */
	public static int convertJitoJ(int[] ji, int[] maxValues) {
		int j;

		if (ji.length == 0)
			return 0;

		j = ji[0];

		if (maxValues.length < 2) {
			return j;
		}

		for (int i = 1; i < ji.length; i++) {
			j *= (maxValues[i] + 1);
			j += ji[i];
		}
		return j;
	}

	/**
	 * Converts a simplified parent configuration into the actual parent
	 * configuration that generated it, can be considered the inverse function
	 * of {@code Nijk.convertJitoJ(int[], int[])}.
	 * 
	 * @param j
	 *             simplified parent configuration
	 * @param maxValues
	 *             maximum values of parents
	 * @return ji  original parent configuration
	 */

	public static int[] convertJtoJi(int j, int[] maxValues) {
		int jAux = j;
		int[] ji = new int[maxValues.length];

		for (int i = maxValues.length - 1; i > 0; i--) {
			ji[i] = jAux % (maxValues[i] + 1);
			jAux = (jAux - ji[i]) / (maxValues[i] + 1);
		}
		ji[0] = jAux;
		return ji;
	}

	/**
	 * Calculates the number of instances where the variable {@code xi[]} takes
	 * its k-th value from {@code [0-maxValue]} and the variables in
	 * {@code parents} take their j-th configuration from
	 * {@code [0-maxValuesParents[j]]}
	 * 
	 * @param maxValue
	 *             Maximum possible value of xi dataset
	 * @param xi
	 *             xi dataset
	 * @param maxValuesParents
	 *             Maximum possible value of parents dataset
	 * @param parents
	 *             parents dataset
	 * @return nijk[][]
	 */

	public static int[][] calcNijk(int maxValue, int[] xi,
			int[] maxValuesParents, int[]... parents) {
		int parentSize = Nijk.convertJitoJ(maxValuesParents, maxValuesParents) + 1;
		int[][] nijk = new int[parentSize][maxValue + 1];
		int[] parentConfig = new int[parents.length];
		int jVal = 0;

		for (int i = 0; i < xi.length; i++) {
			if (xi[i] == -1) {
				continue;
			}
			boolean isValid = true;
			for (int j = 0; j < parents.length; j++) {
				if (parents[j][i] == -1) {
					isValid = false;
					break;
				}
				parentConfig[j] = parents[j][i];
			}
			if (!isValid)
				continue;

			jVal = Nijk.convertJitoJ(parentConfig, maxValuesParents);

			nijk[jVal][xi[i]]++;
		}

		return nijk;
	}

	/**
	 * Calculates the number of instances where the variable {@code xi[]} takes
	 * its k-th value from {@code [0-maxValue]} and the variables in
	 * {@code parents} take their j-th configuration from
	 * {@code [0-maxValuesParents[j]]}
	 * 
	 * @param maxValue
	 *            Maximum possible value of xi dataset
	 * @param xi
	 *            xi dataset
	 * @param maxValuesParents
	 *            Maximum possible value of parents dataset
	 * @param parents
	 *            parents dataset
	 * @return nijk[][]
	 */

	public static int[][] calcNijk(int maxValue, int[] xi) {
		int parentSize = 0;
		int[][] nijk = new int[parentSize + 1][maxValue + 1];
		int jVal = 0;
		for (int i = 0; i < xi.length; i++) {
			if (xi[i] == -1) {
				continue;
			}
			nijk[jVal][xi[i]]++;
		}
		return nijk;
	}
}
