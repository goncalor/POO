package project;

import java.util.Arrays;

public class Data{
	protected Slice[] slices;
	protected String[] varNames;
	protected int[] varDomain;
	
	public Data(int nrSlices, int nrVars){
		slices = new Slice[nrSlices];
		for(int i =0; i<nrSlices; i++){
			slices[i] = new Slice(nrVars);
		}
		varNames = new String[nrVars];
		varDomain = new int[nrVars];
	}
	
	public void setSliceLine(int indexSlice, int lineToAdd[]){
		this.slices[indexSlice].add(lineToAdd);
	}

	public String[] getVarNames() {
		return varNames;
	}

	public void setVarNames(String[] varNames) {
		this.varNames = varNames;
	}

	public int[] getVarDomain() {
		return varDomain;
	}

	public void setVarDomain(int[] varDomain) {
		this.varDomain = varDomain;
	}
	
	public Slice get(int i) {
		return slices[i];
	}

	@Override
	public String toString() {
		return "Data [slices=" + Arrays.toString(slices) + "\nvarNames="
				+ Arrays.toString(varNames) + "\nvarDomain="
				+ Arrays.toString(varDomain) + "\n]";
	}


	
	
}
