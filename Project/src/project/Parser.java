package project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Arrays;

public class Parser{
	
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
	
	public void parseData(String str, int nrCols, Data data) {
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

	public Data parseVarNames(String s){
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
}
