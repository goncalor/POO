package stringlist;

public class Node {
	protected String string;
	Node(String s){
		this.string = s;
	}
	
	@Override
	public String toString() {
		return "(" + this.string +")";
	}
}
