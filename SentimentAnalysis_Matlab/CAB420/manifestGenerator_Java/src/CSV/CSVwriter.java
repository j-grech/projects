package CSV;

import java.io.*;
import java.util.*;

import Delivery.*;
import Stock.*;

public class CSVwriter {
	//Delimiter used in CSV file
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final String REFRIGERATED = ">Refrigerated";
	private static final String NORMAL = ">Ordinary";
	//CSV file header
	private static final String FILE_HEADER = "name,cost,sellPrice,orderPoint,orderAmount,amount,temperature";

	private static String fileName;
	private static FileWriter fileWriter;




	public static void writeStockCSV(String filename,Stock stock) {
		try {
			File f = new File(filename);
			if(!filename.contains(".csv")) {
				throw new CSVException("Please Enter a file of type CSV");
			}
			f.createNewFile();
			fileWriter = new FileWriter(f);

			//Write a new stock item list to the CSV file
			for (Item item : stock.getStock()) {
				fileWriter.append(item.getName());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(item.getCost()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(item.getSellPrice()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(item.getOrderPoint()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(item.getOrderAmount()));
				if(item.getTemperature()!=null) {
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(String.valueOf(item.getTemperature()));
				}
				if(stock.getStock().indexOf(item)!=stock.getStock().size()-1) {
					fileWriter.append(NEW_LINE_SEPARATOR);
				}
			}



			System.out.println("CSV file was created successfully !!!");

		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!" +e.getMessage());
			e.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
			}

		}
	}
	
	public void writeTruckCSV(Truck truck) throws IOException, CSVException {
		fileWriter = new FileWriter(fileName);
		if(truck.getClass().getName()=="NormalTruck") {
			fileWriter.append(NORMAL);
		} else if (truck.getClass().getName()=="RefrigeratedTruck") {
			fileWriter.append(REFRIGERATED);
			fileWriter.append(NEW_LINE_SEPARATOR);	
		}
			try {
				for(Item item:truck.getCargo().getStock()) {
					fileWriter.append(item.getName());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(String.valueOf(item.getAmount()));
					fileWriter.append(COMMA_DELIMITER);
				}
				
			} catch(Exception e){
				throw new CSVException("Error in CsvFileWriter !!!"+e.getMessage());
			} finally {
				try {
					fileWriter.flush();
					fileWriter.close();
				} catch (IOException e) {
					throw new CSVException("Error while flushing/closing fileWriter !!!");
				}
		
			}
	
	}	

	public static void writeManifestCSV(String filename, Manifest manifest) throws CSVException {
		try {
			File f = new File(filename);

			f.createNewFile();
			fileWriter = new FileWriter(f);
			if(!filename.contains(".csv")) {
				throw new CSVException("Please Enter a file of type CSV");
			}
			//Write a new stock item list to the CSV file
			for(Truck truck :manifest.getManifest()) {
				
				if(truck.getCargo().getQuantity()==0) {
					
				}else {
					fileWriter.append(truck.getType());
					fileWriter.append(NEW_LINE_SEPARATOR);
					for(Item item:truck.getCargo().getStock()) {
						if(item.getAmount()!=0) {
							fileWriter.append(item.getName());
							fileWriter.append(COMMA_DELIMITER);
							fileWriter.append(String.valueOf(item.getAmount()));
							fileWriter.append(NEW_LINE_SEPARATOR);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new CSVException("Error in CsvFileWriter !!!"+e.getMessage());
		} finally {

			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				throw new CSVException("Error while flushing/closing fileWriter !!!");
			}

		}
		
	}	
	
}
