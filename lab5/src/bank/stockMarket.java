package bank;
import java.util.List;

public class stockMarket {
	List <Share> Collection;
	stockMarket(){}
	
	public int putInMarket(stockowner John,company Apple, int quantity){
		if( (quantity = John.hasEnough(quantity, Apple)) == 0){
			System.out.println("Owner doesn't have shares.");
			return 0;
		}
		John.addBalance(Apple.getValue() * quantity);
		John.subShares(quantity, Apple);
		this.join(quantity, Apple);
		return quantity;
	}
	public int putInMarket(company Apple, int quantity){
		return this.putInMarket(Apple, Apple, quantity);
	}
	public int buy(stockowner John,company Apple, int quantity){
		if((quantity = Apple.hasEnough(quantity, Apple)) == 0){
			System.out.println("Company doesn't have shares to sell.");
			return 0;
		}
		John.subBalance(Apple.getValue() * quantity);
		John.addShares(quantity, Apple);
		this.remove(quantity, Apple);
		return quantity;
	}
	public int buy(company Apple, int quantity){
		return this.buy(Apple,  Apple, quantity);
	}
	public void join(int quantity, company Apple){
		int i = this.Collection.indexOf(Apple);
		if(i == -1){
			Collection.add(new Share(quantity, Apple));
		}else{
			Collection.get(i).addShares(quantity);
		}
	}
	public void remove(int quantity, company Apple){
		int i = this.Collection.indexOf(Apple);
		Collection.get(i).subShares(quantity);
	}
	public void main (int args[], String s){
		company Microsoft = new company("Microsoft", 10);
		stockowner Fiolhais = new stockowner(10, "Fiolhais");
		
	}
}
