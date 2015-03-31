package bank;
import java.util.List;
import java.util.LinkedList;

public class stockMarket {
	List <Share> shares = new LinkedList<Share>();
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
		int i = this.shares.indexOf(Apple);
		if(i == -1){
			shares.add(new Share(quantity, Apple));
		}else{
			shares.get(i).addShares(quantity);
		}
	}
	public void remove(int quantity, company Apple){
		int i = this.shares.indexOf(Apple);
		shares.get(i).subShares(quantity);
	}
	public static void  main (String[] args){
		company Microsoft = new company("Microsoft", 10);
		stockowner Fiolhais = new stockowner(10, "Fiolhais");
		stockMarket WallStreet = new stockMarket();
		
		Microsoft.createShares(20);
		System.out.println("Microsoft has "+ Microsoft.getShares(Microsoft) +"shares now");
		
		System.out.println("Fiolhais has bought "+ WallStreet.buy(Fiolhais, Microsoft, 1) +" shares");
	}
}
