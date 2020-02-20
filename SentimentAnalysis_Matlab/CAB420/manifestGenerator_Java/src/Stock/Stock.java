package Stock;

import java.util.ArrayList;

/**
 * @author Joe Grech - all methods
 * stock class
 */
public class Stock {
	private ArrayList<Item> stock;
	private int lowestTemp=10;
	
	
	/**Stock constructor, constructs ArrayList
	 * @author Joe Grech
	 * 
	 */
	public Stock() {
		stock = new ArrayList<Item>();
	}
	
	
	/**
	 * Adds an item to the list if it is not already in the list, 
	 * if the item is in the list it adds the Quantity of the item 
	 * to the stocked item
	 * @author Joe Grech
	 * @param item
	 * 
	 */
	public void add(Item item) {
			Object stockContain = stockContain(item.getName()); 
		
			if(stockContain==null) {
				stock.add(item.clone());
			} else {
				stock.get((int) stockContain).addAmount(item.getAmount());
			}
		if(item.getTemperature()==null) {
		} else if ((int) item.getTemperature()<lowestTemp) {
			lowestTemp = (int) item.getTemperature();
		}
	}
	
	
	
	/**
	 * @author Joe Grech
	 * @param name - name of the item as a string
	 * @param quantity - quantity of the item to be added
	 * @throws StockException - throws if the item does not exist
	 */
	public void add(String name, int quantity) throws StockException {
			
			Object stockContain = stockContain(name); 
			if(stockContain ==null) {
				throw new StockException("attemped to add an item not in the Stock");
			} else {
				stock.get((int) stockContain).addAmount(quantity);
			}

	}
	
	public void remove(Item item) {
		stock.remove(item);
	}
	
	public boolean contains(Item item) {
		return stock.contains(item);
	}
	
	/**
	 * @return stock as an ArrayList<Item>
	 */
	public ArrayList<Item> getStock(){
		return stock;
	}
	
	public Item get(int index) {
		return stock.get(index);
	}
	
	/**
	 * @author Joe
	 * @param name
	 * @return the Item based on the given string name, else null
	 */
	public Item get(String name) {
		for (Item item:stock) {
			if(item.getName()==name) {
				return item;
			}
		}
		return null;
		
	}
	
	/**
	 * @author Joe
	 * @param name - name as String
	 * @return
	 */
	private Object stockContain(String name){
		for (Item item:stock) {
			if(item.getName().equals(name)) {
				return stock.indexOf(item);
			}
		}
		return null;
	}
	
	/**
	 * combines two stock's items to one stock with the quantities of both
	 * @author Joe
	 * @param stock2 - the stock to be combined with.
	 * @return combinedStock 
	 */
	public Stock combine(Stock stock2) {
		Stock combinedStock = new Stock();
		for(Item item:stock) {
			combinedStock.add(item);
		}
		for (Item item:stock2.getStock()) {
			combinedStock.add(item);
		}
		return combinedStock;
	}
	
	/**
	 * @author Joe
	 * @return lowestTemperature in a stock as an int
	 */
	public int getLowestTemp() {
		return (int) getLowestTempItem().getTemperature();
	}
	
	/**
	 * @return total quantity for the Stock
	 */
	public int getQuantity() {
		if(stock.isEmpty()) {
			return 0;
		} else {
			int sum = 0;
			for(Item item :stock) {
				sum+= item.getAmount();
			}
			return sum;
		}
	}
	
	/** 
	 * @author Joe
	 * @return the Stock as a string in the format:
	 * item.toString()
	 * item.toString()
	 */
	public String toString() {
		String string = new String();
		for(Item item:stock) {
			string = string + item.toString();
			if (stock.indexOf(item)!=stock.size()-1) {
				string = string+"\n";
			}
		}
		return string;
	}
	
	/**
	 * @return lowest temp item
	 */
	public Item getLowestTempItem() {
		Item lowestTemp = stock.get(0); // get first item
		for (Item item:stock) {
			if (lowestTemp.getTemperature()==null||lowestTemp.getAmount()==0) {
				lowestTemp = item;
			}else if (item.getTemperature()==null) {
				
			}else if((int)item.getTemperature()<(int)lowestTemp.getTemperature()&&(int)item.getTemperature()!=0) {
				lowestTemp=item;
			}
		}
		return lowestTemp;
	}
	
	
	/** 
	 * @author Joe
	 * @return returns a clone of the array
	 */
	public Stock clone(){
		ArrayList<Item> cloneArray = new ArrayList<Item>(stock.size());
		for(Item item: stock) {
			cloneArray.add(item);
		}
		Stock clone = new Stock();
		for(Item item:cloneArray) {
			clone.add(item);
		}
		return clone;
	}
}
