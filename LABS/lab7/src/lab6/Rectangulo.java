package lab6;

public class Rectangulo extends Forma {
	int altura;
	int largura;
	
	Rectangulo(int pos_x, int pos_y, int largura, int altura){
		super(pos_x,pos_y);
		this.altura = altura;
		this.largura = largura;
	}

	@Override
	int[] interseccao(int y) {
		int[] return_vect ;
		int i;
		
		if( y == pos_y){
			return_vect = new int[largura];
			for(i = 0; i<largura; i++){
				return_vect[i] = new Integer(pos_x+i);
			}
		}else
		if( y == pos_y+altura){
			return_vect = new int[largura];
			for(i = 0; i<largura; i++){
				return_vect[i] = new Integer(pos_x+i);
			}
		}else
		if( (pos_y < y) || (y < pos_y +altura)){
			return_vect = new int[2];
			return_vect[0] = pos_x;
			return_vect[1] = pos_x+largura;
		}else{
			return_vect = new int[0];
		}
		return return_vect;
	}
	
}
