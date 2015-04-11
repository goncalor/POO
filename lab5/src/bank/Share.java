package bank;

public class Share {
	protected int quantity;
	protected company owner;
	Share(company Apple){
		this(0,Apple);
	}
	Share(int a, company Apple){
		this.quantity = a;
		this.owner = Apple;
	}
	public int getShares(){
		return this.quantity;
	}
	public void addShares(int a){
		this.quantity +=a;
	}
	public void subShares(int a){
		this.quantity -=a;
	}
}
