package project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Arrays;

/** parses {@link project.Data} from a *.csv file */
public class Parser{
	
	/**
	 * opens *.csv file {@code fileName} for parsing
	 * 
	 * @param fileName
	 *            the path to the file to be parsed
	 * @return the information extracted from {@code fileName}
	 * @throws IOException
	 *             if an error occurred when opening or reading {@code fineName}
	 */
	public Data fromFile(String fileName) throws IOException{
		BufferedReader input = new BufferedReader(new FileReader(fileName));
		String str;
		Data data;
		
		str = input.readLine();
		data = parseVarNames(str);
		
		while ((str = input.readLine()) != null) {
			parseData(str, data.getVarNames().length, data);
        }
		input.close();
		return data;
	}
	
	
	/**
	 * parses one line of the *.csv file other than the first
	 * 
	 * @param str
	 *            the line to parse
	 * @param nrCols
	 *            the number of slices to parse from {@code str}
	 * @param data
	 *            where the data is saved for each file line that is parsed
	 */
	private void parseData(String str, int nrCols, Data data) {
		String[] parsedLine = str.replace("\t", "").replace(" ", "").split(",");	// remove spaces and tabs and separate at commas

		int maxValues[] = data.getVarDomain();
		int parsedLineInt[] = new int[parsedLine.length];
		int nrSlices = parsedLine.length/nrCols;
		
		for(int i=0; i< parsedLine.length; i++){
			parsedLineInt[i] = Integer.parseInt(parsedLine[i]);	//TODO check exception
			
			if(parsedLineInt[i] > maxValues[i % nrCols])
				maxValues[i % nrCols] = parsedLineInt[i];
		}
		
		for(int i=0; i < nrSlices; i++)
		{
			data.setSliceLine(i, Arrays.copyOfRange(parsedLineInt,i*nrCols,(i+1)*nrCols));
		}

		data.setVarDomain(maxValues);
		
	}

	/**
	 * parses the first string in the *.csv file
	 * 
	 * @param s
	 *            a string of the form
	 *            {@code a_0, b_0, c_0, ..., a_1, b_1, c_1, ... }. This should
	 *            be the first line in the *.csv file
	 * @return an incomplete {@link project.Data} instance with information
	 *         about the variable names and number of slices
	 */
	private Data parseVarNames(String s){
		String[] temp = s.replace("\t", "").replace(" ", "").split(",");	// remove spaces and tabs and separate at commas
		HashSet<String> varNames = new HashSet<String>();
		StringBuffer names = new StringBuffer("");
		int sizeOfString = temp.length;
		int colNumb=0;
		int nrSlices=0;
		
		for(int i = 0; i < temp.length;i++){
			String aux = temp[i].substring(0, temp[i].length() - 2);	// Variable name terminates with _0 and the next is _1 so we just remove the last 2 chars
			if(!varNames.contains(aux)){
				varNames.add(aux);
				names.append(aux).append(" ");
				colNumb++;
			}else{
				nrSlices = sizeOfString/colNumb;
				break;
			}
		}
		Data data = new Data(nrSlices, colNumb);
		data.setVarNames(names.toString().split(" "));
		return data;
	}
	
	
	public Slice sliceFromFile(String fileName) throws IOException{
		BufferedReader input = new BufferedReader(new FileReader(fileName));
		String str;		
		Slice slice = new Slice(input.readLine().split(",").length);
		
		String[] parsedLine;
		
		while ((str = input.readLine()) != null) {

			parsedLine = str.replace("\t", "").replace(" ", "").split(",");
			
			int[] parsedLineInt = new int[parsedLine.length];
			
			for(int i=0; i<parsedLine.length; i++){
				parsedLineInt[i] = Integer.parseInt(parsedLine[i]);
			}
			
			slice.add(parsedLineInt);
        }
		input.close();
		
		return slice;
	}

	
	
	
	
}
