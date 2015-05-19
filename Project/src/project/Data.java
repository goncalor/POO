package project;

import java.util.Arrays;

/**
 * Stores integer values into a Slice vector, for this particular project it is used to
 * store the values read from the .csv files.
 *
 */

public class Data{
	protected Slice[] slices;
	protected String[] varNames;
	protected int[] varDomain;
	
	/**
	 * Constructor for a instance of Data that has {@code nrSlices}
	 * {@code Slices} and {@code nrVars} Variables store inside slices.
	 */
	public Data(int nrSlices, int nrVars){
		slices = new Slice[nrSlices];
		for(int i =0; i<nrSlices; i++){
			slices[i] = new Slice(nrVars);
		}
		varNames = new String[nrVars];
		varDomain = new int[nrVars];
	}
	
	/** @return number of slices minus one */
	public int maxSlices(){
		return slices.length-1;
	}
	
	/** sets up a Slice at index {@code indexSlice} with values {@code} */
	public void setSliceLine(int indexSlice, int lineToAdd[]){
		this.slices[indexSlice].add(lineToAdd);
	}

	/** @return all varNames contained in the Slices */
	public String[] getVarNames() {
		return varNames;
	}

	/** Setter for varNames*/
	public void setVarNames(String[] varNames) {
		this.varNames = varNames;
	}

	/** @return varDomain, which is the maximum values vector of all variables*/
	public int[] getVarDomain() {
		return varDomain;
	}

	/** Sets the varDomain, or the maximum value possible of each var*/
	public void setVarDomain(int[] varDomain) {
		this.varDomain = varDomain;
	}
	
	/** @return Slice at index i*/
	public Slice getSlice(int i) {
		return slices[i];
	}

	@Override
	public String toString() {
		return "Data [slices=" + Arrays.toString(slices) + "\nvarNames="
				+ Arrays.toString(varNames) + "\nvarDomain="
				+ Arrays.toString(varDomain) + "\n]";
	}	
}
