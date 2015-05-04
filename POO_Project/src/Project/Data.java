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
			System.out.println(str);
        }
		input.close();
	}
	
	public void parseVarNames(String s){
		String[] temp = s.split(",");
		boolean belongs=false;
		
		for(int i = 0; i < temp.length && !belongs;i++){
			String aux = temp[i].substring(0, temp[i].length() - 2);
			if(!varNames.contains(aux)){
				varNames.add(aux);
				varNumb++;
			}else{
				belongs = true;
			}
		}
	}
	
	@Override
	public String toString() {
		return varNames.toString();
	}

}
