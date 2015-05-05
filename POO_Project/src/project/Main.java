package project;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		String s = new String("datasets/train-data.csv");
		Data data = new Data();
		
		data.parseFromFile(s);
		System.out.println("#Different sets of elements: " + data.varNumb + " | " + data.toString());
	}

}
