package Delivery;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import Stock.*;

public class TruckTest {

	@Before
	public void setUp() throws Exception {
		Stock stock = new Stock();
		RefrigeratedTruck rTruck = new RefrigeratedTruck(stock);
		NormalTruck nTruck = new NormalTruck(stock);
	}
	
	@Test
	public void testGetCargo() throws DeliveryException {
		Stock cargo = new Stock();
		RefrigeratedTruck rTruck = new RefrigeratedTruck(cargo);
		NormalTruck nTruck = new NormalTruck(cargo);
		Stock nCargo = rTruck.getCargo();
		Stock rCargo = nTruck.getCargo();
		assertEquals(rCargo,nCargo);
		assertEquals(cargo,rCargo);
	}
	@Test 
	public void testGetTemperature() throws DeliveryException { // only between -20 & 10
		Stock dryCargo = new Stock();
		Stock coldCargo = new Stock();
		dryCargo.add(new Item("apple",1,0,0,0,1));
		dryCargo.add(new Item("banana",1,0,0,0,1));
		coldCargo.add(new Item("ice cream",1,0,0,0,1,-5));
		RefrigeratedTruck dryRefTruck = new RefrigeratedTruck(dryCargo);
		RefrigeratedTruck coldRefTruck = new RefrigeratedTruck(coldCargo);
		int dryTemp = dryRefTruck.getTemperature(dryCargo);
		int coldTemp = coldRefTruck.getTemperature(coldCargo);
		assertEquals(10,dryTemp);
		assertEquals(-5, coldTemp);
	}
	
	@Test
	public void testCargoAssignment() throws DeliveryException{
		Stock nCargo = new Stock(), rCargo = new Stock();
		Item apple = new Item("apple",1,0,0,0,1001);
		Item ice = new Item("ice",1,0,0,0,1001,-9);
		nCargo.add(apple); //add Items >800, 1000
		rCargo.add(ice);
		try { //check too much capacity ref truck
		RefrigeratedTruck rTruck = new RefrigeratedTruck(rCargo);
		} catch(DeliveryException e){
			
		}
		try { // check too much capacity reg truck
			NormalTruck nTruck = new NormalTruck(nCargo);
			fail("didnt catch exception");
		} catch(DeliveryException e){
				
		}
		try { // wet cargo in normal truck
			NormalTruck nTruck = new NormalTruck(rCargo);
			fail("didnt catch exception");
		} catch (DeliveryException e) {	
			
		}
	}
		

	@Test
	public void testGetCost() throws DeliveryException{
		double temp = 10.0;
		double quantity = 100.0;
		Stock cargo = new Stock();
		Item apple = new Item("apple",1,0,0,0,100);
		double rTruckCost = 900+(200*Math.pow(0.7, temp/5.0));
		double nTruckCost = 750 + 0.25*quantity;
		cargo.add(apple); //add dry stock to cargo
		RefrigeratedTruck rTruck = new RefrigeratedTruck(cargo);
		NormalTruck nTruck = new NormalTruck(cargo);
		assertEquals(rTruckCost, rTruck.getCost(), 0);//check costs
		assertEquals(nTruckCost,nTruck.getCost(), 0);//check 
	}
	
}
