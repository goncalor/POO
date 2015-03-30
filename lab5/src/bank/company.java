package bank;

public class company extends stockowner {
	protected int value;
	company( String name, int amount){
		this(0,name,amount);
	}
	company(int value, String name, int amount){
		super(amount, name);
		this.setValue(value);
	}
	public int getValue(){
		return this.value;
	}
	public void setValue(int value){
		this.value = value;
	}
	public void createShares(int amount){
		int i = this.ownerCollector.indexOf(this);
		this.ownerCollector.get(i).addShares(amount);
	}
}
