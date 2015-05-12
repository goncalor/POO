package stringlist;

public class StringList {
	private Node ListNode;
	private int length;
	public StringList(){
		length = 0;
	}
	
	public StringList(String s){
		this.ListNode = new Node(s);
		length = 1;
	}
	
	public void insert(String s){
		Node temp = this.ListNode;
		
		if( this.ListNode == null){
			ListNode = new Node(s);
		}else{
			while(temp.next != null){
				temp = temp.next;
			}
			temp.next = new Node(s);
		}
		length++;
	}
	
	public int remove(String s){
		Node temp = this.ListNode;
		Node tempNext = temp.next;
		Node compare = new Node(s);
		int count = 0;
		// TODO: remover o primeiro
		while(temp.next != null){
			if(tempNext.equals(compare)){
				temp.next = tempNext.next;
				count++;
			}
			temp = temp.next;
			tempNext = temp.next;
		}
		length = length - count;
		return count;
	}
	
	public int length(){
		return this.length;
	}
	
	@Override
	public boolean equals(Object o){
		return this.ListNode.equals(o);
	}
	
	@Override
	public String toString() {
		Node temp = this.ListNode;
		StringBuffer s = new StringBuffer();
		while(temp != null){
			s.append(temp.toString());
			temp = temp.next;
		}
		return s.toString();
	}
	
	public static void main(String[] args) {
		StringList s1 = new StringList();
		s1.insert("ola");
		s1.insert("Adeus");
		s1.insert("Adeus");
		s1.insert("Adeus");
		System.out.println("Our list is: " + s1);
	}
}
