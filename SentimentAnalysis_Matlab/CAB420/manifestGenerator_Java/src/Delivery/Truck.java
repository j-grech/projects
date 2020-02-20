package Delivery;

import Stock.*;

/**
 * 
 * @author Matthew Lord
 * Abstract class for trucks, containing common methods between both subclasses.
 */
public abstract class Truck {
	
	public abstract double getCost(); //returns cost of the truck
	public abstract Stock getCargo(); //returns cargo of the truck
	public abstract String getType(); //returns type of the truck as a string
	public abstract void checkStock(Stock stock) throws DeliveryException; //checks the given stock for overcapacity or temperature exceptions

}