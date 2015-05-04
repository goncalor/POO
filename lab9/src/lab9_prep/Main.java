package lab9_prep;

public class Main {

	public static void main(String[] args) throws IAException {
		String[] string = new String[10];
		
		IterableArray<String> iterableArray = new IterableArray<String>(string);
		iterableArray.add("Hello");
		iterableArray.add("Iterable");
		iterableArray.add("Array");
		iterableArray.add("!");
	}

}
