package circularList;

public class MainClass {

	public static void main(String[] args) {
		ListOfNumbers <Double> l = new ListOfNumbers <Double>();
		
		l.add(2.);
		l.add(3.);
		l.add(4.);
		System.out.println(l.average() + " = 3? ok");
		l.remove();
		System.out.println(l.average() + " = 3.5? ok");
		l.remove();
		System.out.println(l.average() + " = 4? ok");
		l.remove();
		System.out.println(l.average() + " = 0? ok");
		l.remove();
		l.add(2.);
		l.add(3.);
		l.add(4.);
		System.out.println(l.average() + " = 3? ok");
		l.remove();
		System.out.println(l.average() + " = 3.5? ok");
		l.remove();
		System.out.println(l.average() + " = 4? ok");
		
		List <String> ls = new List <String>();
		ls.add("Hello");
		ls.add(" ");
		ls.add("World");
		ls.add("!");
		System.out.println( ls.remove().element + ls.remove().element + ls.remove().element + ls.remove().element);
		
		
	}

}
