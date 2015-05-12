package lab9_prep;
import java.util.*;

public class IAIterator<T> implements Iterator<T>{
	int index;
	T array[];
	boolean calledNext=false;
	
	public IAIterator(T array[]){
		this.array = array;
		index = 0;
	}
	
	@Override
	public boolean hasNext(){
		if(index < this.array.length && this.array[index+1] == null){
			return false;
		}
		return true;
	}

	@Override
	public T next() {
		@SuppressWarnings("unchecked")
		Iterator <T> aux = (Iterator<T>) this.array[index];
		
		calledNext = true;
		
		if(aux.hasNext()){
			index++;
			return this.array[index];
		}
		return null;
	}
	
	@Override
	public void remove() throws IllegalStateException{
		
		if(!calledNext){
			throw new IllegalStateException();
		}
		calledNext = false;
		int aux_index = index;
		this.array[index] = null;
		
		while(aux_index < this.array.length){
			if(this.array[aux_index+1] == null){
				this.array[aux_index] = this.array[aux_index+1];
			}else{
				this.array[aux_index] = null;
				return;
			}
			aux_index++;
		}
	}
}
