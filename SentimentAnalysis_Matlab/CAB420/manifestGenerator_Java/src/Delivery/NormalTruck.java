/**
 * 
 */
package Delivery;

import Stock.*;

/**
 * @author Matthew Lord
 * Normal truck cannot contain cargo that is required to be temperature controlled.
 * And, has a different cost calculation and max capacity.
 */
public class NormalTruck extends Truck {
	
	Stock cargo;
	private final static int CAPACITY = 1000;
	
	/**
	 * The constructor takes a cargo and then checks to see if it is a viable cargo for the truck type.
	 * If it isn't, it throws a DeliveryException to be handled in the GUI.
	 * If it is, it allows creation of the instance and assigns the cargo.
	 * @author Matthew Lord
	 * @param cargo The cargo that the truck is to be created with
	 * @throws DeliveryException - Thrown when the cargo is not compatible with the truck type.
	 */
	public NormalTruck(Stock cargo) throws DeliveryException {
		try {
			this.checkStock(cargo);
		} catch (DeliveryException e) {
			throw e;
		}
		
		this.cargo = cargo;
	}
	
	/**
	 * Checks a given stock for temperature controlled goods or overcapacity
	 * @parameter stock that you wish to check
	 * @returns true if there are no wet goods.
	 * @throws DeliveryException if the stock is not a viable cargo for the truck
	 */
	public void checkStock(Stock stock) throws DeliveryException {
		for(Item item: stock.getStock()) {
			//checks for wet good first
			if(item.getTemperature() != null&&item.getAmount()!=0) {
				throw new DeliveryException("Wet/Cold goods in dry truck");
			}
		}
		//then check item quantity to check for overcapacity
		if(stock.getQuantity() > CAPACITY) {
			throw new DeliveryException("Cargo exceeds capacity");
		}
	}


	/**
	 * @author Matthew Lord
	 * @returns the cost of the truck
	 */
	@Override
	public double getCost() {
		return 750 + 0.25 * cargo.getQuantity();
	}
	
	/**
	 * @author Matthew Lord
	 * @returns the trucks cargo
	 */
	@Override
	public Stock getCargo() {
		return cargo;
	}

	/**
	 * returns type of truck as a String
	 * @author Joe Grech
	 */
	@Override
	public String getType() {
		return ">Ordinary";
	}

}
