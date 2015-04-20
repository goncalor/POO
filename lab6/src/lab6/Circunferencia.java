package lab6;
import java.lang.Math;

public class Circunferencia extends Forma{
	int raio;
	Circunferencia(int pos_x, int pos_y, int raio){
		super(pos_x,pos_y);
		this.raio = raio;
	}
	
	@Override
	int[] interseccao(int y) {
		int [] return_vect;
		if((y < pos_y-raio)||(y<pos_y+raio)){
			return_vect = new int[0];
			return return_vect;
		}else if((y == pos_y+raio) || (y==pos_y-raio)){
			return_vect = new int[1];
			return_vect[0] = pos_x;
			return return_vect;
		}else{
			return_vect = new int[2];
			float x1 = (float)Math.sqrt(raio^2 - (y - pos_y)^2)+pos_x;
			return_vect[0] = (int) x1;
			float x2 = x1 - 2*raio;
			return_vect[1] = (int) x2;
			return return_vect;
		}
	}
}
