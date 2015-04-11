package stringlist;
import java.util.HashSet;

public class StringList {
	HashSet<Node>ListNode;
	
	public StringList(){
		this.ListNode = new HashSet<Node>();
	}
	
	public StringList(String s){
		this.ListNode = new HashSet<Node>();
		this.ListNode.add( new Node(s));
	}
	
	public void insert(String s){
		this.ListNode.add( new Node(s));
	}
	
	public void remove(String s){
		
	}
	
	@Override
	public String toString() {
		return ListNode.toString();
	}
	
	public static void main(String[] args) {
		StringList s1 = new StringList();
		s1.insert("Ola");
		s1.insert("Adeus");
		System.out.println("Our list is " + s1);
	}


}
