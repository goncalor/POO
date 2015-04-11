package bank;

import java.util.LinkedList;
import java.util.List;

public class StockOwner {
	List <Share> ownerCollector = new LinkedList<Share>();
	protected int balance;
	protected String name;
	
	StockOwner(String s, int amount){
		this.balance = amount;
		this.name = s;
	}
	public int getBalance(){
		return this.balance;
	}
	public void addBalance(int amount){
		this.balance += amount;
	}
	public boolean subBalance(int amount){
		if(amount < this.balance){
			this.balance -= amount;
			return true;
		}else{
			return false;
		}
	}
	public String getName(){
		return this.name;
	}
	public void addShare(String s, int a){
		Share share = new Share(s, a);
		this.ownerCollector.add(share);
	}
	public int shareBelongs(String s){
		int i = 0;
		for(Share o :this.ownerCollector){
			if(o.getName().equals(s)){
				return i;
			}
			i++;
		}
		return -1;
	}
	public int getShareAmount(int i){
		return this.ownerCollector.get(i).getShares();
	}
}
