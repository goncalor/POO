package stringlist;

public class Node {

	String str;	// no modifier: visible inside the class and the package
	Node next;
	
	Node(String str)
	{
		this(str, null);
	}
	
	Node(String str, Node next)
	{
		this.str = str;
		this.next = next;
	}
	
	public Node setNext(Node next)
	{
		this.next = next;
		return next;
	}
	
	public Node getNext()
	{
		return next;
	}
	
	
	
	public String getString()
	{
		return str;
	}

	@Override
	public String toString() {
		return '"' + getString() + '"';
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((str == null) ? 0 : str.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(!(obj instanceof Node))
			return false;
		Node other = (Node) obj;	// cast Object to Node
		if(str == null)
		{
			if(other.str != null)
				return false;
		}
		else if(!str.equals(other.str))
			return false;
		return true;
	}
	
	@Override
	protected void finalize() throws Throwable
	{
		System.out.println("The node " + this + " is being deleted...");
		super.finalize();
	}
}
