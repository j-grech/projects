/**
 * 
 */
package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.NumberFormat;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import CSV.CSVException;
import CSV.CSVLoader;
import CSV.CSVwriter;
import Delivery.DeliveryException;
import Delivery.Manifest;
import Stock.Item;
import Stock.StockException;
import Stock.Store;

/**
 * @author Matthew Lord
 * GUI Class containing GUI setup, exception handling and event handling.
 */
public class StoreGUI extends JFrame {
	
	private static final int WIDTH = 600;
	private static final int HEIGHT = 300;
	
	private JButton exportButton;
	private JButton importButton;
	private JButton itemButton;
	private JButton salesButton;
	private JTable invenTable;
	private JLabel capitalText;
	private NumberFormat numberFormatter;
	Object[][] stockData = {};
	
	static Store store;
	
	
	public StoreGUI() {
		super("SuperMart Inventory Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create a tab pane to create two tabs
		JTabbedPane tabPane = new JTabbedPane();
		
		//Panel containing all of the landing pages panels
		JPanel wrapperPanel = new JPanel();
		
		//title text
		JPanel titlePanel = new JPanel();
		titlePanel.setPreferredSize(new Dimension(WIDTH, 40));
		
		JLabel titleText = new JLabel("SuperMart Inventory Manager");
		titleText.setHorizontalAlignment(SwingConstants.CENTER);
		titleText.setFont(titleText.getFont().deriveFont(18.0f));
		titlePanel.add(titleText);
		
		//capital text
		JPanel capitalPanel = new JPanel();
		capitalPanel.setPreferredSize(new Dimension(WIDTH, 60));
		
		//format capital text - intialised here, use helper method once intialised.
		numberFormatter = NumberFormat.getNumberInstance();
		String quantityOut = numberFormatter.format(store.getCapital());
		capitalText = new JLabel("Store Capital: $" + quantityOut);
		setCapitalLabel();
		capitalText.setFont(capitalText.getFont().deriveFont(24.0f));
		capitalPanel.add(capitalText);
		
		//item and sales panel share the same row
		JPanel itemPanel = new JPanel();
		itemPanel.setPreferredSize(new Dimension(WIDTH, 50));
		itemPanel.setLayout(new GridLayout(1, 2));
		
		itemButton = new JButton("Load in Items");
		itemButton.addActionListener(new ButtonListener());
		salesButton = new JButton("Load in Sales");
		salesButton.addActionListener(new ButtonListener());
		salesButton.setEnabled(false);
		
		itemPanel.add(itemButton);
		itemPanel.add(salesButton);
		

		//manifest import and export
		JPanel manifestPanel = new JPanel();
		manifestPanel.setPreferredSize(new Dimension(WIDTH, 50));
		itemPanel.setLayout(new GridLayout(1, 2));
		
		exportButton = new JButton("Export Manifest");
		exportButton.addActionListener(new ButtonListener());
		exportButton.setEnabled(false);
		importButton = new JButton("Load in Manifest");
		importButton.addActionListener(new ButtonListener());
		importButton.setEnabled(false);
		
		itemPanel.add(exportButton);
		itemPanel.add(importButton);
		
		
		
		
		//Manifest + sales log import + export buttons
		wrapperPanel.add(titlePanel);
		wrapperPanel.add(capitalPanel);
		wrapperPanel.add(itemPanel);
		wrapperPanel.add(manifestPanel);


		
		//Panel to see inventory table
		JPanel inventoryPanel = new JPanel();
		
		//Create table with appropriate columns displaying required properties
		String[] columnNames = {"Name",
								"Quantity",
								"Manufacturing Cost ($)",
								"Sell Price ($)",
								"ReOrd Point",
								"ReOrd Amount",
								"Temperature (Celsius)"};
		
		//build the table
		invenTable = new JTable(new DefaultTableModel(stockData, columnNames));
		JScrollPane tablePanel = new JScrollPane(invenTable);
		tablePanel.setPreferredSize(new Dimension(WIDTH - 5, HEIGHT - 5));
		inventoryPanel.add(tablePanel);
		
		//add the tabs to the main Pane
		tabPane.add("Main", wrapperPanel);
		tabPane.addTab("Inventory", inventoryPanel);
		getContentPane().add(tabPane);
		
		//Display the window: +5 are margins as some text was getting cut off.
		setPreferredSize(new Dimension(WIDTH +5, HEIGHT +5));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * @author Matthew Lord
	 * @param args
	 * @throws StockException
	 * Simple main method that is program entry point
	 * Create a store and the GUI.
	 */
	public static void main(String[] args) throws StockException {
		JFrame.setDefaultLookAndFeelDecorated(true);
		store = Store.getInstance();
		new StoreGUI();
		
		
	}
	
	/**
	 * ButtonListener handles events for the GUI as there is only four basic events,
	 * this was done as an inline class so I could easily access the data from the GUI.
	 * @author Matthew Lord
	 *
	 */
	public class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Component source = (Component) e.getSource();
			if(source == exportButton) {
				exportManifest();
			} else if (source == importButton) {
				importManifest();	
			} else if (source == itemButton) {
				loadProperties();
			} else if (source == salesButton) {
				importSales();
			}

		}
	}
	
	/**
	 * @author Matthew Lord
	 * Generate a manifest from the store
	 * If this fails, give exception error message in popup
	 * Otherwise update capital amount and inventory table
	 * @throws CSVException 
	 */
	public void exportManifest() {
		try {
			Manifest manifest = store.generateManifest();
			CSVwriter.writeManifestCSV(getFile(), manifest);
			JOptionPane.showMessageDialog(null, "Manifest Exported");
			setCapitalLabel();
			updateTable();
		} catch (StockException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (DeliveryException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (CSVException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

	}
	
	/**
	 * @author Matthew Lord
	 * Import the sales, showing an error if there is an exception.
	 * Otherwise, give a popup confirmating import
	 * then update capital label and table
	 */
	public void importSales() {
		try {
			CSVLoader.readSalesCSV(getFile());
			JOptionPane.showMessageDialog(null, "Sales Imported");
			setCapitalLabel();
			updateTable();
		} catch (CSVException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (StockException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		

	}
	
	/**
	 * @author Matthew Lord
	 * Loads in item properties when button has been clicked.
	 * User gets the file, will throw CSVException if CSV file does not match properties
	 * Then, enable other buttons.
	 */
	public void loadProperties() {
		String filename = getFile();
		try {
			store.updateItemList(CSVLoader.readItemPropertiesCSV(filename));
			JOptionPane.showMessageDialog(null, "Items loaded in");
			exportButton.setEnabled(true);
			importButton.setEnabled(true);
			salesButton.setEnabled(true);
			updateTable();
		} catch (CSVException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

	}
	
	/**
	 * @author Matthew Lord
	 * Loads the manifest, if this fails, then alert the user of the exception.
	 * Then adds the manifest to the table
	 * Then changes the store capital
	 */
	public void importManifest() {
		try {
			store.loadManifest(CSVLoader.readManifestCSV(getFile()));
			JOptionPane.showMessageDialog(null, "Manifest Imported");
			updateTable();
			setCapitalLabel();
		} catch (StockException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (DeliveryException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (CSVException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

	}
	
	/**
	 * @author Matthew Lord
	 * @return absolute path of the file that has been selected
	 * Helper method that utilises JFilechooser to let the user select a file
	 */
	public String getFile() {
		final JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(this);
		if(returnVal==JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			String filename = file.getAbsolutePath();
			return filename;
		} else if(returnVal==JFileChooser.CANCEL_OPTION) {
			return null;
		}
		return null;
	}
	
	/**
	 * @author Matthew Lord
	 * private helper method to change the capital label
	 */
	private void setCapitalLabel() {
		numberFormatter = NumberFormat.getCurrencyInstance();
		String quantityOut = numberFormatter.format(store.getCapital());
		capitalText.setText("Store Capital: " + quantityOut);
	}
	
	/**
	 * @author Matthew Lord
	 * Private helper method that updates the table to reflect inventory
	 */
	private void updateTable() {
		DefaultTableModel model = (DefaultTableModel) invenTable.getModel();
		while(invenTable.getRowCount() > 0) {
			model.removeRow(0);
		}
		for(Item item: store.getInventory().getStock()) {
			model.addRow(new Object[]{item.getName(),
									  item.getAmount(),
									  item.getCost(),
									  item.getSellPrice(),
									  item.getOrderPoint(),
									  item.getOrderAmount(),
									  item.getTemperature()});
		}
	}
}
