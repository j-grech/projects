package Stock;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Matthew Lord
 * Test Item class - in particular construction and get methods
 * Name, manufacturing cost, sell price, reorder point, reorder amount, temp)
 */
public class ItemTest {
	Item item;
	
	/*
	 * Test construction for cold items that have a temp
	 * in their parameters
	 */
	@Test
	public void testColdConstruct() {
		item = new Item("ColdTest", 0, 0, 0, 0, 0, 0);
	}
	
	/*
	 * Test constructor for items without temp parameter
	 */
	@Test
	public void testWarnConstruct() {
		item = new Item("WarmTest", 0, 0, 0, 0, 0);
	}

	@Test
	public void testGetName() {
		item = new Item("testName", 0, 0, 0, 0, 0);
		assertEquals("testName", item.getName());
	}
	
	@Test
	public void testGetCost() {
		item = new Item("test", 10, 0, 0, 0, 0);
		assertEquals(10, item.getCost());
	}
	
	@Test
	public void testGetSellPrice() {
		item = new Item("test", 0, 20, 0, 0, 0);
		assertEquals(20, item.getSellPrice());
	}
	
	@Test
	public void testGetOrderPoint() {
		item = new Item("test", 0, 0, 15, 0, 0);
		assertEquals(15, item.getOrderPoint());
	}
	
	@Test
	public void getOrderAmount() {
		item = new Item("test", 0, 0, 0, 18, 0);
		assertEquals(18, item.getOrderAmount());
	}
	
	
	@Test
	public void testGetTemperature() {
		item = new Item("test", 0, 0, 0, 0, 20, 0);
		assertEquals(20, item.getTemperature());
		
		//test for when getTemp is called for a warm good
		item = new Item("item", 0, 0, 0, 0, 0);
		assertEquals(null, item.getTemperature());
	}

	//need test for add amount
}
