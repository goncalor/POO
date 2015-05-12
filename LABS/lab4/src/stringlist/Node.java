package stringlist;

public class Node {
	protected String string;
	protected Node next;
	Node(String s){
		this.string = s;
		this.next = null;
	}
	
	@Override
	public boolean equals(Object o){
		Node temp = (Node) o;
		if(string == temp.string){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "(" + this.string +")";
	}
}
