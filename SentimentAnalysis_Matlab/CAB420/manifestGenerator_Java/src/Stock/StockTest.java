/**
 * 
 */
package Stock;

import static org.junit.Assert.*;
import java.util.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Joseph Grech
 *
 */
public class StockTest {
	
	Stock stock;
	Item cabbage = new Item("cabbage", 1, 1, 1, 1, 1);
	Item onion  = new Item("onion", 1, 1, 1, 1, 1);
	Item milk  = new Item("milk", 1, 1, 1, 1, 1, -5);
	Item icecream = new Item("ice cream", 1, 1, 1, 1, 1, -10);
	

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	
	/*
	 * Test add() with no quantity parameter
	 */
	@Test
	public void testAdd() {
		//empty Stock
		stock = new Stock();
		stock.add(cabbage);
		
		assertEquals(cabbage, stock.get(0));
		
		//Stock with one item in it
		stock.add(onion);
		assertEquals(cabbage, stock.get(0));
		assertEquals(onion, stock.get(1));	
		
		//Adding a new quantity of an item to the stock
		stock.add(cabbage);
		assertEquals(2, stock.get("cabbage").getAmount()); //this wont work because the parameters of the item are different
	}
	
	//test add() with both item and quantity parameter
	public void testAddQuantity() throws StockException {
		stock = new Stock();
		stock.add("cabbage", 4);
		assertEquals(4, stock.get("cabbage").getAmount());
		
		//test when adding 0
		stock.add("cabbage", 0);
		assertEquals(4, stock.get("cabbage").getAmount());
		
		//test when adding -1
		stock.add("cabbage", -2);
		assertEquals(2, stock.get("cabbage").getAmount());
	}
	
	@Test
	public void testGetQuantity() throws StockException {
		stock = new Stock();
		stock.add(cabbage);
		stock.add(icecream);
		stock.add(milk);
		stock.add("milk", 5);
		
		assertEquals(8, stock.getQuantity());
	}

	
	@Test
	public void testRemove() {
		stock = new Stock();
		stock.add(cabbage);
		stock.add(onion);
		stock.remove(cabbage);
		
		//test to see that there is no cabbage but, onion is still present
		assertEquals(false, stock.contains(cabbage));
		assertEquals(true, stock.contains(onion));
	}
	
	@Test
	public void testGetStock() {
		ArrayList<Item> items= new ArrayList<Item>();
		items.add(cabbage);
		items.add(onion);
		stock = new Stock();
		stock.add(cabbage);
		stock.add(onion);
		assertEquals(items, stock.getStock());
	}
	
	@Test
	public void getItem() {
		stock = new Stock();
		stock.add(onion);
		//test if null
		if(!stock.contains(cabbage)) {
			assertEquals(null, stock.get("cabbage"));
		} else if (stock.contains(onion)) {
			assertEquals(onion, stock.get("onion"));
		}
	}
	
	@Test
	public void TestGetLowestTemp() {
		stock = new Stock();
		stock.add(cabbage);
		stock.add(milk);
		
		//first test to check with one cold item
		assertEquals(-5, stock.getLowestTemp());
		
		//add another colder item to test multiple items
		stock.add(icecream);
		assertEquals(-10, stock.getLowestTemp());
	}
	
	@Test
	public void testCombine() {
		stock = new Stock();
		Stock stock1 = new Stock();
		Stock stock2 = new Stock();
		
		//test with empty secondary stock
		stock1.add(cabbage);
		stock.add(cabbage);
		assertEquals(stock.toString(), stock1.combine(stock2).toString());
		
		//test with both stocks containing items
		stock1.add(milk);
		stock2.add(icecream);
		
		stock.add(milk);
		stock.add(icecream);
			
		assertEquals(stock.toString(), stock1.combine(stock2).toString());
	}
}
