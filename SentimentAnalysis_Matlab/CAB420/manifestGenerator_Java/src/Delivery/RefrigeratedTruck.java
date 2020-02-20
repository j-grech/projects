/**
 * 
 */
package Delivery;

import Stock.*;

/**
 * @author Matthew Lord
 * Truck that can take temperature controlled goods.
 * Has a different cost to the dry truck and a lower capacity.
 */
public class RefrigeratedTruck extends Truck {
	
	private final int CAPACITY = 800;
	private Stock cargo;

	/**
	 * Checks to see if the cargo capacity has been exceeded, if it has not, then create an instance
	 * with the given stock as cargo.
	 * @param cargo - Cargo to be checked
	 * @throws DeliveryException - DeliveryException if capacity exceeded
	 */
	public RefrigeratedTruck(Stock cargo) throws DeliveryException {
		try {
			checkStock(cargo);
		} catch (DeliveryException e){
			throw e;
		}
		
		this.cargo = cargo;
	}
	
	 
	@Override
	/**
	 * Checks to see if the capacity of the truck has been exceeded.
	 * @param stock - Stock to be checked
	 * @throws DeliveryException when capacity is exceeded
	 * @author Matthew Lord
	 */
	public void checkStock(Stock stock) throws DeliveryException {
		if(stock.getQuantity() > CAPACITY) {
			throw new DeliveryException("Quantity exceeds max capacity");
		}
	}
	
	/**
	 * Simple method to return the lowest temperature of the truck.
	 * @author Matthew Lord
	 * @param stock - Stock to be checked
	 * @return - lowest temperature of the given stock.
	 */
	public int getTemperature(Stock stock) {
		return stock.getLowestTemp();
	}


	@Override
	/**
	 * Returns the cost of the refrigerated truck, dependent on lowest temperature.
	 * @author Matthew Lord
	 * @return cost of the truck as a double
	 */
	public double getCost() {
		return 900 + 200 * Math.pow(0.7, this.getTemperature(cargo) / 5.0);
	}


	@Override
	/**
	 * @author Matthew Lord
	 * @return this trucks cargo
	 */
	public Stock getCargo() {
		return cargo;
	}

	@Override
	/**
	 * @author Joe Grech
	 * @return this trucks type as a string
	 */
	public String getType() {
		return ">Refrigerated";
	}

}
