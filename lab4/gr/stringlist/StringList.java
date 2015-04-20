package stringlist;

public class StringList {

	protected Node list, listend;	// list points to the first node. listend points to the last

	public StringList()
	{
		list = listend = null;
	}

	public StringList(String str)
	{
		list = listend = new Node(str);
	}

	public void insert(String str)
	{
		if(list == null)
		{
			list = listend = new Node(str);
			return;
		}

		listend = listend.setNext(new Node(str));
	}

	public int remove(String str)
	{
		Node aux;	// aux points to the element before the one that might be removed
		int i = 0;

		if(list == null)
			return 0;

		// remove from the beginning of the list
		while(list.getString().equals(str))
		{
			i++;
			list = list.getNext();
			if(list == null)
			{
				listend = null;
				return i;
			}
		}

		// remove from any point in list but the beginning
		for(aux = list; aux.getNext()!= null; aux = aux.getNext())
		{
			if(aux.getNext().getString().equals(str))
			{
				i++;
				if(aux.setNext(aux.getNext().getNext()) == null)	// remove the element after aux
				{
					listend = aux;
					return i;
				}
			}
		}

		listend = aux;
		return i;
	}

	public int length()
	{
		Node aux;
		int len = 0;

		for(aux = list; aux != null; aux = aux.getNext())
			len++;

		return len;
	}

	@Override
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		Node aux;

		for(aux = list; aux != null; aux = aux.getNext())
		{
			str.append(aux.toString() + ", ");
		}

		if(str.length() != 0)
			return "{" + str.substring(0, str.length()-2) + "}";
		return "{<empty>}";
	}

	// this hashCode is pretty bad for this
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((list == null) ? 0 : list.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof StringList))
			return false;
		StringList other = (StringList) obj;
		Node aux1 = this.list, aux2 = other.list;

		while(aux1 != null && aux2 != null)
		{
			if(aux1.equals(aux2))
			{
				aux1 = aux1.getNext();
				aux2 = aux2.getNext();
			}
			else
				return false;
		}

		if(aux1 == aux2)	// if both are null
			return true;
		return false;

	}

	@Override
	protected void finalize() throws Throwable
	{
		System.out.println("The stringList " + this + " is being deleted...");
		super.finalize();
	}

	public static void main(String args[])
	{
		StringList test = new StringList();

		test.insert("cenas");
		test.insert("coisas variadas");
		System.out.println(test.length());

		test.remove("coisas variadas");

		System.out.println(test);
		test.insert("coisas variadas");

		// test equality
		StringList test2 = new StringList("cenas");
		test2.insert("coisas variadas");

		System.out.println("test: " + test);
		System.out.println("test2: " + test2);
		System.out.println(test == test2);
		System.out.println(test.equals(test2));

		// see the effects of garbage collecting
		test = null;
		System.gc();	// the garbage collector is non-deterministic. call it multiple times and you will get different results

	}
}
