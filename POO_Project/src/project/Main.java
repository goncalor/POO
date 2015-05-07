package project;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		String s = new String("datasets/test0.csv");
		Data data;
		Parser parse = new Parser();
		data = parse.fromFile(s);
		
		System.out.println("#Different sets of elements:\n" + data);
		
		
//		Slice a = new Slice(3);
//		int vals[] = {1,2,3};
//		
//		a.add(vals);
//		a.add(vals);
//		a.add(vals);
//
//		System.out.println(a);
		
	}

}
