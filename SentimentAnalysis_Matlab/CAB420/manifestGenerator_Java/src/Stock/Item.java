package Stock;

/**
 * @author Joe Grech - all methods
 * Item class
 *
 */
public class Item {
	String name;
	int cost, sellPrice, orderPoint,orderAmount, amount;
	Object temperature; // returns null if a dry good
	
	/**
	 * Constructor for item that doesn't require temperature
	 * @author Joe Grech
	 * @param name
	 * @param cost
	 * @param sellPrice
	 * @param orderPoint
	 * @param orderAmount
	 * @param amount - quantity of item
	 */
	public Item(String name,int cost,int sellPrice, int orderPoint,int orderAmount, int amount){
		this.name =name;
		this.cost=cost;
		this.sellPrice=sellPrice;
		this.orderPoint=orderPoint;
		this.orderAmount=orderAmount;
		this.amount = amount;
		this.temperature = null;
	}
	
	/**
	 * Constructor for item that requires temperature
	 * @author Joe Grech
	 * @param name
	 * @param cost
	 * @param sellPrice
	 * @param orderPoint
	 * @param orderAmount
	 * @param amount
	 * @param temperature
	 */
	public Item(String name,int cost,int sellPrice, int orderPoint,int orderAmount, int amount, int temperature){
		this.name =name;
		this.cost=cost;
		this.sellPrice=sellPrice;
		this.orderPoint=orderPoint;
		this.orderAmount=orderAmount;
		this.amount = amount;
		this.temperature=temperature;
	}
	
	public String getName(){
		return name;
	}
	
	public int getCost() {
		return cost;
	}
	
	public int getSellPrice() {
		return sellPrice;
	}
	
	public int getOrderPoint() {
		return orderPoint;
	}
	
	public int getOrderAmount() {
		return orderAmount;
	}
	public int getAmount() {
		return amount;
	}
	
	public Object getTemperature() {
		return temperature;
	}
	
	/**
	 * @author Joe Grech
	 * @param amount - adds to the amount of items in item
	 */
	public void addAmount(int amount) {
		this.amount+= amount; 
	}
	
	/**
	 * @author Joe Grech
	 * writes the item as a sring in format 
	 * "name, cost, sellPrice, orderPoint, orderAmount, amount, (temperature)"
	 */
	public String toString() {
		String string = new String();
		string = name +", "+cost +", "+sellPrice+", "+orderPoint+", "+orderAmount+", "+amount;
		if(temperature==null) {
			
		}else {
			string = string + ", "+temperature;
		}
		return string;
	}
	
	/** 
	 * @author Joe Grech
	 * returns the clone of an item
	 */
	public Item clone() {
		if(temperature ==null) {
		Item cloneItem = new Item(name,cost, sellPrice, orderPoint,orderAmount, amount);
		return cloneItem;
		}
		else {
		Item cloneItem = new Item(name,cost, sellPrice, orderPoint,orderAmount, amount,(int)temperature);
		return cloneItem;
		}
	}
}
