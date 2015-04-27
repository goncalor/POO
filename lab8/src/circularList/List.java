package circularList;

public class List <T>{
	protected int size;
	protected Element <T> root;
	
	List() {
		this.root = new Element<T>(null);
		this.size = 0;
	}
	
	public void add(T element){
		Element <T> aux = new Element<T>(element);
		Element <T> nextElement = root.next;

		if( size == 0){
			root.pred = aux;
			root.next = aux;
			aux.next = root;
			aux.pred = root;
		}else{
			aux.pred = root;
			root.next = aux;
			aux.next = nextElement;
			nextElement.pred = aux;
		}
		this.size++;
	}
	
	public Element<T> remove(){
		if(root.next == null){
			return null;
		}
		
		Element <T> aux = root.pred;

		this.size --;
		if((aux.pred.element == null) && (aux.next.element == null)){
			root.pred = null;
			root.next = null;
			return aux;
		}
		Element <T> predElement = aux.pred;
		root.pred = aux.pred;
		predElement.next = root;
		aux.next=null;
		aux.pred=null;
		return aux;
	}
}
