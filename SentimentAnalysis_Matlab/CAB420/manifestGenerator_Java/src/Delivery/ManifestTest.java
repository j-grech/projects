package Delivery;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import CSV.CSVException;
import CSV.CSVLoader;
import Stock.Item;
import Stock.Stock;
import Stock.StockException;
import Stock.Store;

/**
 * 
 * @author Matthew
 * Test class for Manifest class.
 */
public class ManifestTest {
	
	Manifest manifest;
	NormalTruck dryTruck;
	RefrigeratedTruck wetTruck;
	Stock wetStock;
	Stock dryStock;
	Item fish = new Item("fish", 13, 16, 375, 475, 2);
	Item ice = new Item("ice", 2, 5, 225, 325, -10);
	ArrayList<Truck> testList;

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testTruckAdd() throws StockException, DeliveryException {
		//test w/ dry truck
		manifest = new Manifest();
		testList = new ArrayList<Truck>();
		testList.add(dryTruck);
		manifest.add(dryTruck);
		assertEquals(testList, manifest.getManifest());
		
		//test w/ wet truck and the dry truck from the earlier test
		testList.add(wetTruck);
		manifest.add(wetTruck);
		assertEquals(testList, manifest.getManifest());
	}
	
	@Test
	public void resolveManifestTest() throws StockException, DeliveryException, CSVException {
		Store store = Store.getInstance();
		Stock itemList = CSVLoader.readItemPropertiesCSV("C:\\Users\\UserPC\\Documents\\item_properties.csv");
		store.updateItemList(itemList);
		
		wetStock = new Stock();
		wetStock.add(fish);
		wetStock.add(ice);
		wetTruck = new RefrigeratedTruck(wetStock);
		
		manifest = new Manifest();
		Manifest manifest1 = new Manifest();
		manifest.add("fish");
		manifest.add("ice");
		manifest1.add(wetTruck);
		assertEquals(true, manifest.getManifest().get(0).getCargo().toString().equals(manifest1.getManifest().get(0).getCargo().toString()));
		//
	}
	
	@Test
	public void getManifest() throws StockException, DeliveryException {
		manifest = new Manifest();
		testList = new ArrayList();
		
		manifest.add(dryTruck);
		testList.add(dryTruck);
		
		assertEquals(testList, manifest.getManifest());
	}

}
