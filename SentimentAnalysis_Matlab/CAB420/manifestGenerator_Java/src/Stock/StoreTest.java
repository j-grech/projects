/**
 * 
 */
package Stock;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Matt
 *
 */
public class StoreTest {
	
	Store store;
	Stock stock;
	Item cabbage = new Item("cabbage",1,1,1,1,1);
	Item onion = new Item("onion",1,1,1,1,1);

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	
	/*
	 * Store follows the singleton pattern, 
	 * test to ensure only one can be made and that it is still accessible.
	 */
	@Test
	public void testContructor() throws StockException {
		store = Store.getInstance();
		assertEquals(store, Store.getInstance());
	}
	
	@Test
	public void testAddInventory() throws StockException {
		stock = new Stock();
		stock.add(cabbage);
		store = Store.getInstance();
		store.addInventory(stock);
		assertEquals(stock, store.getInventory());
	}
	
	@Test
	public void testGetInventory() throws StockException {
		store = Store.getInstance();
		stock = new Stock();
		assertEquals(stock, store.getInventory());
		stock.add(cabbage);
		store.addInventory(stock);
		assertEquals(stock, store.getInventory());	
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testUpdateCapital() {
		store = Store.getInstance();
		store.updateCapital((int) -50000.00);
		assertEquals(50000, store.getCapital(),0);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testGetCapital() throws StockException {
		//test for 100,000.00 initial funds
		store = Store.getInstance();
		assertEquals(100000.00, store.getCapital(),0);
		//test after capital is updated
		store.updateCapital(-30000.00);
		assertEquals(70000.00, store.getCapital(),0);
		
		
	}

}
