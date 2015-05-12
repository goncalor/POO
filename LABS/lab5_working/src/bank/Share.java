package bank;

public class Share {
	protected int quantity;
	protected String owner;
	Share(String s, int a){
		this.quantity = a;
		this.owner = s;
	}
	public int getShares(){
		return this.quantity;
	}
	public String getName(){
		return this.owner;
	}
	public void addShares(int a){
		this.quantity += a;
	}
	public boolean subShares(int a){
		if( a < this.quantity){
			this.quantity -= a;
			return true;
		}else{
			return false;
		}
	}
	public static void  main (String[] args){
		Share try1 = new Share("John Doe", 10);
		StockOwner jane = new StockOwner("Jane Doe", 10);
		int id;
		
		try1.addShares(5);
		if( try1.subShares(6)){
			System.out.println(try1.getName() +" has "+try1.getShares()+" shares");
		}else{
			System.out.println("Not enough shares sorry "+try1.getName());
		}
		jane.addBalance(5);
		if( jane.subBalance(6)){
			System.out.println(jane.getName() +" has "+jane.getBalance()+" shares");
		}else{
			System.out.println("Not enough balance sorry "+jane.getName());
		}
		jane.addShare(jane.getName(), 10);
		if( (id = jane.shareBelongs("Jane Doe")) != -1){
			System.out.println("we have "+ jane.getShareAmount(id)+ " shares");
		}else{
			System.out.println("we dont have shares");
		}
		
	}
}
