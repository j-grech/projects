package Stock;

import Delivery.DeliveryException;
import Delivery.Manifest;
import Delivery.Truck;

/**
 * @author Joe - all methods
 * Store Class
 */
public class Store {
	//Store follows the Singleton pattern, so have a protected constructor
	//to prevent unauthorised calls to it
	private double capital = 100000;
	private Stock inventory = null;
	private String name = "SuperMart";
	private Stock itemList=null;
	
	/**
	 * protected Store method for singleton pattern
	 */
	protected Store() {
		inventory = new Stock();
		itemList= new Stock();
	}
	
	
	/**
	 * @author Joe
	 *protected store
	 */
	private static class StoreHolder{
		private final static Store INSTANCE = new Store();
	}
	
	/**
	 * @return store instance
	 * 
	 */
	public static Store getInstance(){
		return StoreHolder.INSTANCE;
	}
	
	
	/**
	 * @author Joe
	 * @param stock - stock to add to the Store stock
	 * combines the store stock and the param stock
	 * @throws StockException 
	 */
	public void addInventory(Stock stock) throws StockException {
		if(stock == null) {
			inventory=stock;
		} else {
			for(Item item :inventory.combine(stock).getStock()) {
				if(item.getAmount()<0) {
					throw new StockException("cannot have negative stock in inventory");
				}
			}
			inventory = inventory.combine(stock);
		}
	}
	
	/**
	 * @return inventory
	 */
	public Stock getInventory() {
		return inventory;
	}
	
	/**
	 * @param d - amount to be added to the capital
	 * adds d to capital
	 */
	public void updateCapital(double d) {
		capital+=d;
	}
	
	public double getCapital() {
		return capital;
	}
	
	/**
	 * changes name of store
	 * @param name
	 */
	public void updateName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * @param itemList - used for importing the list of items at the beginning of the store( usually only called once)
	 * adds the empty list of Items to the inventory as well, providing the item details
	 */
	public void updateItemList(Stock itemList) {
		this.itemList = itemList;
		this.inventory = itemList.combine(inventory);
	}
	
	/**
	 * @return returns all items that have greater than 0 quantity
	 */
	public Stock getItemList() {
		if(itemList.getQuantity()!=0) {
			for(Item item :itemList.getStock()) {
				item.addAmount(item.getAmount()*-1);
			}
		}
		return itemList.clone();
	}
	
	
	/**
	 * @return manifest - creates the manifest based on item quantity vs the item reorder point.
	 * @throws StockException
	 * @throws DeliveryException
	 * 
	 * resolve manifest moves the lists of items created by the manifest.add method 
	 * into trucks in the most efficient manner
	 */
	public Manifest generateManifest() throws StockException, DeliveryException {
		Manifest manifest = new Manifest();
		for(Item item :inventory.getStock()) {
        	if(item.getOrderPoint()>=item.getAmount()) {
        		manifest.add(item.getName());
        	}
        }
		manifest.resolveManifest();
		return manifest;
		
	}

	/**
	 * @param manifest - loaded manifest
	 * @throws StockException
	 * @throws DeliveryException
	 * adds the delivered items to the inventory and subtracts the cost of the items from the capital
	 */
	public void loadManifest(Manifest manifest) throws StockException, DeliveryException {
		for(Truck truck : manifest.getManifest()) {
			for(Item item:truck.getCargo().getStock()) {
				inventory.add(item.getName(), item.getAmount());
				capital -= item.getCost()*item.getAmount();
			}
			capital -= truck.getCost();
		}
	}
}
