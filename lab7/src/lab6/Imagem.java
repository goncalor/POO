package lab6;
import java.util.LinkedList;
import java.util.List;


public class Imagem {
	public int altura;
	public int largura;
	List <Forma> form = new LinkedList<Forma>();
	
	Imagem(int height, int width){
		this.altura = height;
		this.largura = width;
	}
	
	public void adicionarForma(Forma formToAdd){
		form.add(formToAdd);
	}
	
	public String linha(int y){
		
		return "cya";
	}
	
	public String toString(){
		return "ola";
	}
}
