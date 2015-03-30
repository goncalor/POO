package bank;
import java.util.List;


public class stockowner {
	List <Share> ownerCollector;
	protected int balance;
	protected String name;
	
	stockowner(String s){
		this(0,s);
	}
	stockowner(int a, String s){
		this.balance=a;
		this.name = s;
	}
	public int hasEnough(int quantity, company Apple){
		int i = this.ownerCollector.indexOf(Apple);
		if( i == -1){
			return 0;
		}else{
			if(this.ownerCollector.get(i).getShares() < quantity){
				return quantity;
			}else{
				return this.ownerCollector.get(i).getShares();
			}
		}
	}
	public void addBalance(int amount){
		this.balance += amount;
	}
	public void subBalance(int amount){
		this.balance -= amount;
	}
	
	public void addShares(int amount, company Apple){
		int i = this.ownerCollector.indexOf(Apple);
		if(i == -1){
			Share a = new Share(amount, Apple);
			this.ownerCollector.add(a);
		}else{
			this.ownerCollector.get(i).addShares(amount);
		}
	}
	public void subShares(int amount, company Apple){
		int i = this.ownerCollector.indexOf(Apple);
		if(i == -1){
			System.out.println("You do not own shares of " + Apple.name);
		}else{
			this.ownerCollector.get(i).subShares(amount);	
			if(this.ownerCollector.get(i).getShares() == 0){
				this.ownerCollector.remove(i);
			}
		}
	}
	public int getShares(company Apple){
		int i = this.ownerCollector.indexOf(Apple);
		return this.ownerCollector.get(i).getShares();
	}
}
