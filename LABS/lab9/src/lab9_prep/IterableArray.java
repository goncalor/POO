package lab9_prep;
import java.util.*;

public class IterableArray <T>{
	T array[];
	
	IterableArray(T element[]){
		this.array = element;
	}
	
	public void add(T element) throws IAException{

		boolean full=true;
		for(int i = 0; i < this.array.length; i++){
			if(array[i] == null){
				array[i] = element;
				full=false;
				break;
			}
		}

		try {
			if(full) throw new IAException();
		} catch (IAException e){
			System.out.println("FULL");
		}
	}
	
	public Iterator<T> iterator(){
		return new IAIterator(this.array);
	}
}
