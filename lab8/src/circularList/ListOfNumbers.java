package circularList;

public class ListOfNumbers <T extends Number> extends List<T>{
	
	ListOfNumbers(){
		super();
	}
	
	public double average(){
		double a=0;
		int count=0;
		if( root.next == null){
			return 0;
		}
		for(Element <T> aux = root.next; aux.element !=null; aux=aux.next){
			count++;
			a+= aux.element.doubleValue();
			//System.out.println(a + " < sum" + count + " < count");
		}
		return a/count;
	}
	
}
