package circularList;

public class Element <T>{
	protected T element;
	protected Element<T> next;
	protected Element<T> pred;
	
	public Element(T element){
		this(element, null, null);
	}
	public Element(T element, Element <T> next, Element <T> pred){
		this.element = element;
		this.next = next;
		this.pred = pred;
	}
}
