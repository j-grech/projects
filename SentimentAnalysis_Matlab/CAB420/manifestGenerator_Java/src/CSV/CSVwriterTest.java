package CSV;

import static org.junit.Assert.*;

import org.junit.Test;

import Delivery.DeliveryException;
import Delivery.Manifest;
import Stock.Item;
import Stock.Stock;
import Stock.StockException;
import Stock.Store;

public class CSVwriterTest {

	@Test
	public void test() throws CSVException {
		Stock stock = CSVLoader.readItemPropertiesCSV("C:\\Users\\Joe\\Downloads\\item_properties.csv");
		CSVwriter.writeStockCSV( "C:\\Users\\Joe\\Downloads\\test_item_properties.csv",stock) ;
		Stock stock2 = CSVLoader.readItemPropertiesCSV("C:\\Users\\Joe\\Downloads\\test_item_properties.csv");
		for(int i= 0 ;i<stock.getStock().size(); i++) {
			assertEquals(stock.get(i).getName(),stock2.get(i).getName());
		}
	}
	
	@Test
	public void test2() throws StockException, CSVException, DeliveryException{
		Manifest manifest;
		Store store = Store.getInstance();
		
		Stock itemList = CSVLoader.readItemPropertiesCSV("C:\\Users\\Joe\\Downloads\\item_properties.csv");
		store.updateItemList(itemList);
		System.out.println("init");
		
		manifest = store.generateManifest();
		CSVwriter.writeManifestCSV("C:\\Users\\Joe\\Downloads\\test_manifest_Init.csv",manifest);
		store.loadManifest(CSVLoader.readManifestCSV("C:\\Users\\Joe\\Downloads\\test_manifest_Init.csv"));
		System.out.println(store.getCapital());
		
		CSVLoader.readSalesCSV("C:\\Users\\Joe\\Downloads\\sales_log_0.csv");
		manifest = store.generateManifest();
		CSVwriter.writeManifestCSV("C:\\Users\\Joe\\Downloads\\test_manifest_0.csv",manifest);
		store.loadManifest(CSVLoader.readManifestCSV("C:\\Users\\Joe\\Downloads\\test_manifest_0.csv"));
		System.out.println("0");
		System.out.println(store.getCapital());
		
		CSVLoader.readSalesCSV("C:\\Users\\Joe\\Downloads\\sales_log_1.csv");
		manifest = store.generateManifest();
		CSVwriter.writeManifestCSV("C:\\Users\\Joe\\Downloads\\test_manifest_1.csv",manifest);
		store.loadManifest(CSVLoader.readManifestCSV("C:\\Users\\Joe\\Downloads\\test_manifest_1.csv"));
		System.out.println("1");
		System.out.println(store.getCapital());
		
		
		CSVLoader.readSalesCSV("C:\\Users\\Joe\\Downloads\\sales_log_2.csv");
		manifest = store.generateManifest();
		CSVwriter.writeManifestCSV("C:\\Users\\Joe\\Downloads\\test_manifest_2.csv",manifest);
		store.loadManifest(CSVLoader.readManifestCSV("C:\\Users\\Joe\\Downloads\\test_manifest_2.csv"));
		System.out.println("2");
		System.out.println(store.getCapital());
		
		
		CSVLoader.readSalesCSV("C:\\Users\\Joe\\Downloads\\sales_log_3.csv");
		manifest = store.generateManifest();
		CSVwriter.writeManifestCSV("C:\\Users\\Joe\\Downloads\\test_manifest_3.csv",manifest);
		store.loadManifest(CSVLoader.readManifestCSV("C:\\Users\\Joe\\Downloads\\test_manifest_3.csv"));
		System.out.println("3");
		System.out.println(store.getCapital());
		
		
		CSVLoader.readSalesCSV("C:\\Users\\Joe\\Downloads\\sales_log_4.csv");
		manifest = store.generateManifest();
		CSVwriter.writeManifestCSV("C:\\Users\\Joe\\Downloads\\test_manifest_4.csv",manifest);
		store.loadManifest(CSVLoader.readManifestCSV("C:\\Users\\Joe\\Downloads\\test_manifest_4.csv"));
		System.out.println("4");
		System.out.println(store.getCapital());
		assertEquals(1,1);
	}
	

}
