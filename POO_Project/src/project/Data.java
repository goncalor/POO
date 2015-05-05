package Project;
import java.io.*;
import java.util.*;

public class Data {
	Set <String> varNames = new HashSet<String>();
	int varNumb=0;
	int data[];
	
	public Data(){
		this.varNumb = 0;
	}
	
	public void parseFromFile(String fileName) throws IOException{
		BufferedReader input = new BufferedReader(new FileReader(fileName));
		String str;
		str = input.readLine();
		parseVarNames(str);
		
		while ((str = input.readLine()) != null) {
			System.out.print("Read line");
			parseData(str);
			System.out.println();
        }
		input.close();
	}
	
	public void parseData(String str) {
		String[] temp = str.split(",");
		
		int tempData[] = new int[temp.length];
		
		for(int i=0; i< temp.length; i++){
			System.out.print(" "+temp[i]);
			tempData[i] = Integer.parseInt(temp[i]);
		}
	}

	public void parseVarNames(String s){
		String[] temp = s.split(",");
		
		for(int i = 0; i < temp.length;i++){
			String aux = temp[i].substring(0, temp[i].length() - 2);
			if(!varNames.contains(aux)){
				varNames.add(aux);
				varNumb++;
			}else{
				break;
			}
		}
	}
	
	@Override
	public String toString() {
		return varNames.toString();
	}

}
