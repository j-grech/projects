package Delivery;

import java.util.ArrayList;
import java.util.Iterator;

import Stock.*;

/**
 * @author Joe - all methods
 * 
 */
public class Manifest {
	private ArrayList<Truck> manifest;
	private Stock itemList;
	private Store store;
	private Stock rTruckInv, nTruckInv;
	/**
	 * @author Joe
	 * @throws StockException
	 * @throws DeliveryException
	 */
	public Manifest() throws StockException, DeliveryException{
		manifest = new ArrayList<Truck>();
		store=Store.getInstance();
		itemList = store.getItemList();
		nTruckInv = itemList.clone();
		rTruckInv = itemList.clone();
	}

	/**
	 * @param item
	 * @throws StockException
	 * adds an item to the corresponding unordered inventory
	 * if it needs refrigeration then added to r inventory, else n inv
	 */
	public void add(String item) throws StockException { //adding items to non resolved manifest
		if(itemList.get(item).getTemperature()==null) {
			nTruckInv.add(item,itemList.get(item).getOrderAmount());
		} else {
			rTruckInv.add(item,itemList.get(item).getOrderAmount());
		}
	}
	
	/**
	 * @param truck
	 * @throws StockException
	 * adds a truck to the manifest - used in creating manifest from CSV
	 */
	public void add(Truck truck)throws StockException{//reading manifest back 
		 manifest.add(truck);
	}

	/**
	 * @throws StockException
	 * @throws DeliveryException
	 * resolves the most efficient manifest given the items in the two lists  rTruckInv, nTruckInv
	 */
	public void resolveManifest() throws StockException, DeliveryException {
		Store store = Store.getInstance(); // gets instance of store
		Stock preparedStock = store.getItemList(); // gets all items
		Item currItem = null;
		boolean noTrucks = false;
		Iterator<Item> iter = rTruckInv.getStock().iterator(); // removes all items with no quantity from the ref stock
		while(iter.hasNext()) {
			Item i = iter.next();
			if(i.getAmount()==0) iter.remove();
			if (rTruckInv.getStock().isEmpty()) noTrucks=true;
			
		}
		Iterator<Item> iter2 = nTruckInv.getStock().iterator(); // removes all items with no quantity from the normal stock
		while(iter2.hasNext()) {
				Item i = iter2.next();
				if(i.getAmount()==0) iter2.remove();
		}
		while(!noTrucks) {
			if(rTruckInv.getStock().isEmpty()) { // breaks if there are no items in either inventory
				if(nTruckInv.getStock().isEmpty()) {
					noTrucks=true;
					break;
					
				}
				currItem=nTruckInv.get(0); // if there are no items in the rTruck, get ntruck items until the current truck is filled
			}else {
				currItem = rTruckInv.getLowestTempItem(); // get the lowest temp item in the list
			}
			if(preparedStock.getQuantity()<=800) { //max truck quantity is 800
				if(preparedStock.getQuantity()+currItem.getOrderAmount()>800) {
					int overFlowQuant = preparedStock.getQuantity()+currItem.getOrderAmount()-800; // fills the truck to capacity, puts overflow in new stock

					preparedStock.add(currItem.getName(), currItem.getOrderAmount()-overFlowQuant);
					manifest.add(new RefrigeratedTruck(preparedStock));
					preparedStock =  store.getItemList();
					preparedStock.add(currItem.getName(), overFlowQuant);
					if(rTruckInv.getStock().isEmpty()) {
						noTrucks = true; // lets no more rtrucks be created after the current one
					}
				} else {
					preparedStock.add(currItem.getName(),currItem.getOrderAmount());
				}
				if(rTruckInv.getStock().isEmpty()) {
					nTruckInv.remove(currItem); //removes curritem from the corresponding list
				}else {
					rTruckInv.remove(currItem);
				}	
			}
		}
		noTrucks = false;
		if(nTruckInv.getStock().isEmpty()) { // same for normal trucks
			noTrucks = true;
		} else {
			currItem = nTruckInv.get(0);
		}
		while(!noTrucks){
			
			if(preparedStock.getQuantity()+currItem.getOrderAmount()>1000) { // order amount is 1000
				int overFlowQuant = preparedStock.getQuantity()+currItem.getOrderAmount()-1000;
				preparedStock.add(currItem.getName(), currItem.getOrderAmount()-overFlowQuant);
				manifest.add(new NormalTruck(preparedStock));
				preparedStock =  store.getItemList();
				preparedStock.add(currItem.getName(), overFlowQuant);
			}else {
				preparedStock.add(currItem.getName(),currItem.getOrderAmount());
			}
			nTruckInv.remove(currItem);
			if(!nTruckInv.getStock().isEmpty()) {
				currItem=nTruckInv.get(0);
			} else {
				noTrucks=true;
			}
		}
		if(preparedStock.getQuantity()!=0) manifest.add(new NormalTruck(preparedStock)); // makes one more truck if the prepared stock is not empty
		
	}//finish creating manifest

	public ArrayList<Truck> getManifest() throws StockException, DeliveryException{
		resolveManifest();
		return manifest;
	}
}
