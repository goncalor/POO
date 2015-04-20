package lab6;

abstract class Forma {
	protected int pos_x;
	protected int pos_y;
	
	Forma(int pos_x, int pos_y){
		this.pos_x = pos_x;
		this.pos_y = pos_y;
	}
	
	abstract int[] interseccao(int y);
}
