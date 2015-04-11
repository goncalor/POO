package bank;

public class company extends stockowner {
	protected int value;
	company( String name, int amount){
		this(0,name,amount);
	}
	company(int value, String name, int amount){
		super(amount, name);
		this.setValue(value);
		Share s = new Share(this);
		this.ownerCollector.add(s);
	}
	public int getValue(){
		return this.value;
	}
	public void setValue(int value){
		this.value = value;
	}
	public void createShares(int amount){
		int i = this.ownerCollector.indexOf(this);
		if(i==-1){
			Share s = new Share(amount, this);
			this.ownerCollector.add(s);
		}else{
			this.ownerCollector.get(i).addShares(amount);
		}
	}
}
