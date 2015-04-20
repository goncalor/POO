package lab6;

public class Triangulo extends Forma {
	int base;
	int altura;
	Triangulo(int pos_x, int pos_y, int base, int altura){
		super(pos_x,pos_y);
		this.base = base;
		this.altura = altura;
	}
	
	@Override
	int[] interseccao(int y) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
