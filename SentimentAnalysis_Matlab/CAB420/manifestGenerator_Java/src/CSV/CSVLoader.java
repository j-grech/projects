package CSV;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Delivery.DeliveryException;
import Delivery.Manifest;
import Delivery.NormalTruck;
import Delivery.RefrigeratedTruck;
import Delivery.Truck;
import Stock.*;

public class CSVLoader {
	//Delimiter used in CSV file
	private static final String COMMA_DELIMITER = ",";
	//Item attributes index



	public static Stock readItemPropertiesCSV(String fileName) throws CSVException {
		int ITEM_NAME_IDX = 0;
		int ITEM_COST_IDX = 1;
		int ITEM_SELL_PRICE_IDX = 2;
		int ITEM_ORDER_POINT_IDX = 3; 
		int ITEM_ORDER_AMOUNT_IDX = 4;
		int ITEM_TEMPERATURE_IDX = 5;
		BufferedReader fileReader = null;
		if(!fileName.contains(".csv")) {
			throw new CSVException("Please Enter a file of type CSV");
		}
		Stock stock = new Stock();
		try {

			//Create a new stock to be filled by CSV item data 


			String line = "";

			//Create the file reader
			fileReader = new BufferedReader(new FileReader(fileName));

			//Read the file line by line starting from the second line
			while ((line = fileReader.readLine()) != null) {
				//Get all tokens available in line
				String[] tokens = line.split(COMMA_DELIMITER);
				if (tokens.length!=5&&tokens.length!=6) {
					throw new CSVException("not the correct format");
				}
				if (tokens.length==5) {
					//Create a new Item object and fill its data
					Item item = new Item((tokens[ITEM_NAME_IDX]), Integer.parseInt(tokens[ITEM_COST_IDX]), Integer.parseInt(tokens[ITEM_SELL_PRICE_IDX]), Integer.parseInt(tokens[ITEM_ORDER_POINT_IDX]), Integer.parseInt(tokens[ITEM_ORDER_AMOUNT_IDX]),0);
					stock.add(item);
				} else if (tokens.length==6) {
					//Create a new item object and fill its data
					Item item = new Item((tokens[ITEM_NAME_IDX]), Integer.parseInt(tokens[ITEM_COST_IDX]), Integer.parseInt(tokens[ITEM_SELL_PRICE_IDX]), Integer.parseInt(tokens[ITEM_ORDER_POINT_IDX]), Integer.parseInt(tokens[ITEM_ORDER_AMOUNT_IDX]),0,Integer.parseInt(tokens[ITEM_TEMPERATURE_IDX]));
					stock.add(item);
				}
			}


		} 
		catch (Exception e) {
			System.out.println("Error in CsvFileReader !!!");
			throw new CSVException("Error in CSVFileReader" + e.getLocalizedMessage());
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				System.out.println("Error while closing fileReader !!!");
				e.printStackTrace();
			}
		}
		return stock;
	}


	public static void readSalesCSV(String fileName) throws CSVException, StockException {
		int ITEM_NAME_IDX = 0;
		int ITEM_QUANTITY_IDX = 1;
		if(!fileName.contains(".csv")) {
			throw new CSVException("Please Enter a file of type CSV");
		}
		BufferedReader fileReader = null;
		Store store = Store.getInstance();
		try {

			String line = "";

			//Create the file reader
			fileReader = new BufferedReader(new FileReader(fileName));

			Stock stock = store.getItemList();
			int income=0;
			//Read the file line by line starting from the second line
			while ((line = fileReader.readLine()) != null) {
				//Get all tokens available in line
				String[] tokens = line.split(COMMA_DELIMITER);
				stock.add(tokens[ITEM_NAME_IDX], Integer.parseInt(tokens[ITEM_QUANTITY_IDX])*-1);
			}
			store.addInventory(stock);


			for (Item item:stock.getStock()) {
				income-= item.getSellPrice()*item.getAmount();
			} 
			store.updateCapital(income);
		} 
		catch (Exception e) {
			throw new CSVException("Please import sales logs");
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				System.out.println("Error while closing fileReader");
				e.printStackTrace();
				throw new CSVException("Error when closing fileReader");
			}
		}

	}

	public static Manifest readManifestCSV(String fileName) throws CSVException, StockException, DeliveryException {
		int ITEM_NAME_IDX = 0;
		int ITEM_QUANTITY_IDX = 1;
		if(!fileName.contains(".csv")) {
			throw new CSVException("Please Enter a file of type CSV");
		}
		BufferedReader fileReader = null;
		Store store = Store.getInstance();
		Manifest manifest = new Manifest();
		try {

			String line = "";

			//Create the file reader
			fileReader = new BufferedReader(new FileReader(fileName));
			boolean rTruck = false;
			boolean firstRun =true;
			Stock stock = store.getItemList();
			
			while ((line = fileReader.readLine()) != null) {
				//Get all tokens available in line
				String[] tokens = line.split(COMMA_DELIMITER);
				if(firstRun) {
					if(line.equals(">Refrigerated")){
						rTruck=true;
					}else {
						rTruck=false;
					}
					firstRun=false;
				}
				if(tokens.length==2) {
					stock.add(tokens[ITEM_NAME_IDX], Integer.parseInt(tokens[ITEM_QUANTITY_IDX]));
					
				}
				else {

					if(!firstRun&&stock.getQuantity()!=0) {
						Iterator<Item> iter = stock.getStock().iterator();
						while(iter.hasNext()) {
							Item i = iter.next();
							if(i.getAmount()==0) iter.remove();
						}
						if(rTruck) {
							manifest.add(new RefrigeratedTruck(stock));
						} else {
							manifest.add(new NormalTruck(stock));
						}
						stock= store.getItemList();
					}
					if(line.equals(">Refrigerated")){
						rTruck=true;

					}else {
						rTruck=false;
					}
					
				}
			}
			Iterator<Item> iter = stock.getStock().iterator();
			while(iter.hasNext()) {
				Item i = iter.next();
				if(i.getAmount()==0) iter.remove();
			}
			if(rTruck) {
				manifest.add(new RefrigeratedTruck(stock));
			} else {
				manifest.add(new NormalTruck(stock));
			}

			return manifest;
		} 
		catch (Exception e) {
			throw new CSVException(e.getMessage());
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				throw new CSVException("Error while closing fileReader");
			}
		}

	}

}
