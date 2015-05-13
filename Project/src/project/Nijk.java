package project;

public class Nijk {
	public static int convertJitoJ(int[] ji, int[] maxValues){
		int j;
		j = ji[0];

		if(maxValues.length < 2){
			return j;
		}

		for(int i=1; i<ji.length;i++){
			j*=(maxValues[i]+1);
			j+=ji[i];
		}
		return j;
	}

	public static int[] convertJtoJi(int j, int[] maxValues){
		int jAux=j;
		int[] ji = new int[maxValues.length];

		for(int i=maxValues.length-1; i>0 ;i--){
			ji[i] = jAux%(maxValues[i]+1);
			jAux = (jAux - ji[i])/maxValues[i];
		}
		ji[0] = jAux;
		return ji;
	}

	public static int[][] calcNijk(int maxValue, int[] xi,int[] maxValuesParents, int[]...parents){
		int[] tempVals = {1,1,2};
		int parentSize = Nijk.convertJitoJ(tempVals,maxValuesParents)+1;
		int[][] nijk = new int[parentSize][maxValue+1];
		int[] parentConfig = new int[parents.length];
		int jVal=0;

		for(int i=0; i<xi.length; i++){
			for(int j=0; j<parents.length;j++){
				parentConfig[j] = parents[j][i];
			}
			System.out.print("[");
			for(int x : parentConfig)
				System.out.print(x);
			System.out.print("] - ");

			jVal = Nijk.convertJitoJ(parentConfig, maxValuesParents);
			System.out.println(xi[i]);

			nijk[jVal][xi[i]]++;

			System.out.print("[ ");
			for(int[] a : nijk){
				for(int b : a)
					System.out.print(b);
				System.out.print(" ");
			}
			System.out.println("]");
		}

		return nijk;
	}
}
