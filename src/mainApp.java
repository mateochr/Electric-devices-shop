import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;


@SuppressWarnings("unused")
public class mainApp
{

	private static final String CATEGORY1 = "Audio and Visual";
	private static final String CATEGORY2 = "Gaming";
	private static final String CATEGORY3 = "Household Devices";
	
	private static AvailableCatalogue items_catal; //Create an object of the type AvailableCatalogue
	private static OrderedCatalogue orders_catal;	//Create an object of the type OrderedCatalogue
	private static SoldCatalogue sales_catal;		//Create an object of the type SoldCatalogue
	
	static StoreFileR store;
	
	static int Salenum = 0; //Ascending number of sales
	static int Ordernum = 0; //Ascending number of orders
	
	static String type = null;
	static int index;
	static int ordindex;
	
	private static final double categoryAudioVisual = 0.25; //discount for audio and visual devices
	private static final double categoryGaming = 0.1;       //discount for gaming devices
	private static final double categoryHousehold = 0.2;	//discount for household devices
	
	private static JTabbedPane tabbedPane; //Tabbed panel
	private static JPanel panel1; //Items panel
	private	static JPanel panel2; //Sales panel
	private	static JPanel panel3; //Orders panel
	@SuppressWarnings("rawtypes")
	private	static JList listbox; //Items panel list
	@SuppressWarnings("rawtypes")
	private static DefaultListModel listModel;	//Goes inside listbox
	
	@SuppressWarnings("rawtypes")
	private	static JList listbox2; //Sales panel list
	@SuppressWarnings("rawtypes")
	private static DefaultListModel listModel2; //Goes inside listbox2
	
	@SuppressWarnings("rawtypes")
	private	static JList listbox3; //Orders panel list
	@SuppressWarnings("rawtypes") 
	private static DefaultListModel listModel3; //Goes inside listbox3
	
	static JButton gob = new JButton("Go Back");
	static JButton buy = new JButton("Buy");
	static JButton execord = new JButton("Execute");
	
	static JButton searchs = new JButton("Search");
	static JButton searcho = new JButton("Search");
	
	static boolean done = false;  //false for not executed order, true for executed order

	public static void main(String[] args) throws IOException {
		
		//Lead the user to select the right file(ITEMS_LIST.txt)
		String itemsF_name = "";
		while(!itemsF_name.equalsIgnoreCase("ITEM_LIST.txt")){
				itemsF_name = FileChooser();
				if(!itemsF_name.equalsIgnoreCase("ITEM_LIST.txt")){
					newWindow("Wrong file. Please select ITEM_LIST.txt", "ERROR");					
				}
		}
		
		//Lead the user to select the right file(ORDER_LIST.txt)
		String ordersF_name = "";
		while(!ordersF_name.equalsIgnoreCase("ORDER_LIST.txt")){
			ordersF_name = FileChooser();
			if(!ordersF_name.equalsIgnoreCase("ORDER_LIST.txt")){
				newWindow("Wrong file. Please select ORDER_LIST.txt", "ERROR");
			}
		}

		//Lead the user to select the right file(SALES_LIST.txt)
		String salesF_name = "";
		while(!salesF_name.equalsIgnoreCase("SALES_LIST.txt")){
				salesF_name = FileChooser();
				if(!salesF_name.equalsIgnoreCase("SALES_LIST.txt")){
					newWindow("Wrong file. Please select SALES_LIST.txt", "ERROR");
				}
		}
		
		store = new StoreFileR();
		
		//load files
		store.loadItems(itemsF_name);
		store.loadOrders(ordersF_name);
		store.loadSales(salesF_name);
		
		
		
		items_catal = new AvailableCatalogue(store.getItems());
		orders_catal = new OrderedCatalogue(store.getOrders());
		sales_catal = new SoldCatalogue(store.getSales());
		
		
		JFrame ep = new JFrame(); //Main frame
		//(we have the main frame to pop in full window, sometimes it shows nothing, you have to minimize/restore down and then maximize it again)

		JPanel topPanel = new JPanel(); //a pane to add the tabbed panel
		
		topPanel.setLayout(new BorderLayout());
		
		
		ep.setSize(750, 450);	
		ep.getContentPane().add(topPanel, BorderLayout.CENTER);
		ep.setExtendedState(JFrame.MAXIMIZED_BOTH);		
		ep.setTitle("Electronics Store"); 
		ep.setVisible(true);
		ep.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		//Create the tab pages
		createItemsTab();
		createSalesTab();
		createOrdersTab();
		
		
	

		//Create a tabbed pane
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Items", panel1);
		tabbedPane.addTab("Sales", panel2);
		tabbedPane.addTab("Orders", panel3);
		
		
		topPanel.add(tabbedPane);	
		
		
		//Check which tab is open and re-initialize the other 2 panels
		tabbedPane.addChangeListener(new ChangeListener(){
	        public void stateChanged(ChangeEvent e) {
	           int n = tabbedPane.getSelectedIndex();
	           //Sales tab
	           if(n == 1){
	        	   gob.setVisible(false);
	        	   buy.setVisible(false);
	        	   listModel.clear();
	        	   type = null;
	        	   setItems();
	        	   execord.setVisible(false);
	           }
	           //Items tab
	           else if(n == 0){
	        	   execord.setVisible(false);
	           }
	           //Orders tab
	           else{
	        	   gob.setVisible(false);
	        	   buy.setVisible(false);
	        	   listModel.clear();
	        	   type = null;
	        	   setItems();        	   
	           }
	        }
	    });
		
		//When we close the window, save the files
		ep.addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {
		    		store.saveOrders("ORDER_LIST.txt"); 
		    		store.saveSales("SALES_LIST.txt");
		    		store.saveItems("ITEM_LIST.txt");
		      }
		 });
		
	}
	
	
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	//Items tab
	public static void createItemsTab(){
		
		panel1 = new JPanel();
		
		panel1.setLayout(new GridBagLayout());
		
		listModel = new DefaultListModel();
		
		//Initialize the list
		setItems();
	
		
		// Create a new listbox 
		listbox = new JList(listModel);
		
		GridBagConstraints con = new GridBagConstraints();
		
		con.fill = GridBagConstraints.BOTH; //used to specialize every part of the frame in a way that every object fits well
		con.anchor = GridBagConstraints.NORTHWEST; //anchor is used to determine the position
		con.weightx = 1.0; //horizontal space that is used by the object
		con.weighty = 1.0; //vertical space that is used by the object
		con.gridwidth = 3; //use 3 parts of the grid from left to right
		
		//coordinates of the part of the grid the item will use
		con.gridx = 0;
		con.gridy = 0;
		
		
		panel1.add(listbox,con);
		
		
		con = new GridBagConstraints();
		

		con.anchor = GridBagConstraints.LAST_LINE_END;
		con.weightx = 0.001;	
		con.gridx = 2;
		con.gridy = 1;
		gob.setPreferredSize(new Dimension(100,30)); //Set size of button
		
		panel1.add(gob, con);
			
		
		con.weightx = 0.5;		
		con.gridx = 1;
		con.gridy = 1;
		buy.setPreferredSize(new Dimension(100,30));
		
		panel1.add(buy, con);
		
		gob.setVisible(false);
		buy.setVisible(false);

		
		listbox.addMouseListener(new MouseAdapter(){
			//The first time we click in listbox, when the items are
			// 1. TV's, 2. DVD's/Blue rays, 3. Cameras .... 
			//type is null so the second if won't happen
			public void mouseClicked(MouseEvent e){	
				if (e.getClickCount() == 2)
				{
					gob.setVisible(true);					
					int slct = listbox.getSelectedIndex();					
					Selection(slct);
	 			}
				//This if is used after the user has selected a type of item (TV, Camera...),
				//just to make sure that the program works also when the user clicks one time on
				//an item 
				if(e.getClickCount() == 1 && type != null){
					buy.setVisible(true);
					int slct = listbox.getSelectedIndex();
					Choose(type, slct);
				}
				
			}
		});
		
		//When go back button is pressed we re-initialize the first tab
		gob.addActionListener (new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gob.setVisible(false);
		 		buy.setVisible(false);
		 		listModel.clear();
		 		type = null;
		 		setItems();
		 	}
		});
		
		//When buy button is pressed, if there are available copies then buy, else pop window
		buy.addActionListener (new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		if(items_catal.getNumAvailable(index) <= 0){
		 			newWindow("This product is unavailable, you can order it if you want.", "Order");   			
		 		}else{
		 			Sale(index);  //Index is the position of the device in the items catalogue    			
		 		}
		
		 	}
		  });
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void createSalesTab(){
		
		panel2 = new JPanel();
		
		panel2.setLayout(new GridBagLayout());
		
		listModel2 = new DefaultListModel();
		
		//Initialize the sales tab
		for(int i = 0; i < sales_catal.getnumberOfSales(); i++){
			listModel2.addElement(i+1 +"." + "  Sale number: " + "[" + sales_catal.getNumber(i) + "]"  + "    Item's name: " +  sales_catal.getSaleName(i) + "    Person's name: " + sales_catal.getName(i));
		}
		
		// Create a new listbox 
		listbox2 = new JList(listModel2);
		GridBagConstraints con = new GridBagConstraints();
		
		//Puts search button in the panel
		putSearchButton(panel2, listbox2, searchs, con);
		
		//when double-click happens in Sales tab
		listbox2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				if (e.getClickCount() == 2)
				{
					int slct = listbox2.getSelectedIndex();	
					salesOverview(slct);
	 			}	

			}
		});
		
		//when search button is pressed in sales tab
		searchs.addActionListener(new ActionListener(){
			   public void actionPerformed(ActionEvent ae){
				   //While input is empty
				   String input = readString("Insert the customer's name", "Search sale by name");	
				   while(input.equals("")){
					   input = readString("Insert the customer's name", "Search sale by name");	
				   }
				   if(!input.equalsIgnoreCase("cancel")){ //if didn't close the window and didn't hit cancel button
					   searchInput(input, "sale"); //Searches the sales catalogue for a sale made by a customer named input
				   }	
			   }
		});
	
		
		
		
	
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void createOrdersTab(){
		panel3 = new JPanel();
		
		panel3.setLayout(new GridBagLayout());
		
		listModel3 = new DefaultListModel();
		
		//Initialize the orders tab
		for(int i = 0; i < orders_catal.getnumberOfOrders(); i++){
			listModel3.addElement(i+1 + "."+ "  Order number: " + "[" + orders_catal.getNumber(i) + "]"  + "    Item's name: " +  orders_catal.getOrderName(i) + "    Person's name: " + orders_catal.getName(i) +  "    Executed: " + orders_catal.getStatus(i));
		}
		
		// Create a new listbox 
		listbox3 = new JList(listModel3);
		
		GridBagConstraints con = new GridBagConstraints();
		
		//Puts search button in the panel
		putSearchButton(panel3, listbox3, searcho, con);
		
		
		GridBagConstraints c = new GridBagConstraints();
		
		
		c.anchor = GridBagConstraints.LAST_LINE_END;
		c.weightx = 0.5;		
		c.gridx = 1;
		c.gridy = 1;
		execord.setPreferredSize(new Dimension(100,30));
		
		panel3.add(execord, c);
		
		execord.setVisible(false);
		
		//when something happens in orders tab
		listbox3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				int slct;
				//If double-click then then pop a window with info and image
				if(e.getClickCount() == 2)
				{
					slct = listbox3.getSelectedIndex();	
					if(slct >= 0){ //if not empty
						execord.setVisible(true);
					}
					
					ordindex = slct;
					ordersOverview(slct);
	 			}
				//If one click then save in ordindex the selected item
				if(e.getClickCount() == 1)
				{
					slct = listbox3.getSelectedIndex();
					if(slct >= 0){		//if not empty		
						execord.setVisible(true);
					}
					ordindex = slct;
				}

			}
		});
		
		//when search button is pressed in sales tab
		searcho.addActionListener(new ActionListener(){
			   public void actionPerformed(ActionEvent ae){
				   //While input is empty
				   String input = readString("Insert the customer's name", "Search order by name");	
				   while(input.equals("")){
					   input = readString("Insert the customer's name", "Search order by name");	
				   }
				   if(!input.equalsIgnoreCase("cancel")){ //if not cancel and not closed window
					   searchInput(input, "order");	//Searches the orders catalogue for an order made by a customer named input
				   }	
			   }
			
		});
		
		//When execute button is pressed
		execord.addActionListener (new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		executeOrder(ordindex); //call executeOrder with ordindex having the selected item    
         	}
	      });
		
		
	}
	
	//Choosing the text files
	public static String FileChooser(){		
		JButton open = new JButton();		
		JFileChooser fc = new JFileChooser();
		
		fc.setCurrentDirectory(new File(System.getProperty("user.dir"))); //working directory
		fc.setDialogTitle("File Loader");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt"); // only text files
		fc.setFileFilter(filter);
		
		if(fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION){
			
		}
		 return fc.getSelectedFile().getName();		
		 
	}
	
	//Poping a window in different cases
	public static void newWindow(String key, String flag){
		JFrame jf = new JFrame();
		//Used at the beginning of the program to pop an error window if the user selected the wrong file
		if(flag.equalsIgnoreCase("error")){			
			JOptionPane.showMessageDialog(jf, key, flag, JOptionPane.ERROR_MESSAGE);
			
		}
		//When there are no available copies and the user wants to buy, pop this window
		else if(flag.equalsIgnoreCase("order")){
			
		    Object option[] = { "Order", "Cancel" };
		    
		    int s =  JOptionPane.showOptionDialog(jf, key, flag,
		    	JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, option,
		        option[0]);
		    //If he chose to order
			if(s == 0){
				Order(index); //Index is the position of the device in the items catalogue   
			}
			
		}
		//Used for notifications such as showing the final cost of an item when sale is done
		else if(flag.equalsIgnoreCase("notification")){
			JOptionPane.showMessageDialog(jf, key, flag, JOptionPane.PLAIN_MESSAGE);
		}
		//Everything else such as when double-clicking on an item/sale/order, the info and a picture are poped in a new window
		else{

			JLabel label = new JLabel(key);
			
			JPanel panel = new JPanel(new GridBagLayout());
			
			GridBagConstraints con = new GridBagConstraints();
			
			ImageIcon icon = new ImageIcon("images/"+flag+".jpg"); //path of the image(every image in the image directory has the name of the item it's displaying)
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance(300, 300,  java.awt.Image.SCALE_SMOOTH);
			icon = new ImageIcon(newimg);
			
			con.anchor = GridBagConstraints.NORTHWEST;
			con.fill = GridBagConstraints.VERTICAL;
			con.ipadx = 15;
			con.gridheight = 2;
			con.gridx = 0;
			con.gridy = 0;
			panel.add(new JLabel(icon), con);
			
			con.anchor = GridBagConstraints.NORTHWEST;
			con.fill = GridBagConstraints.VERTICAL;
			con.weightx = 1.0;
			con.gridx = 1;
			con.gridy = 0;
			panel.add(label, con);
			
			jf.add(panel);
			
			jf.pack();
		
			Object[] options = {"OK"};
			JOptionPane.showOptionDialog(jf, panel, flag,
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
			        null, options, null);
			
		}
	}
	
	//Check what the user chose
	public static void Selection(int key){		
		if(key == 0 && type == null){ //if he chose 1. TV's
			listModel.clear();
			fillItems("TV");
			type = "TV";
		}else if(key == 1 && type == null){ //if he chose 2. DVD's/Blue rays
			listModel.clear();
			fillItems("DVD/BR");
			type = "DVD/BR";
		}else if(key == 2 && type == null){
			listModel.clear();
			fillItems("Camera");
			type = "Camera";
		}else if(key == 3 && type == null){
			listModel.clear();
			fillItems("Console");
			type = "Console";
		}else if(key == 4 && type == null){
			listModel.clear();
			fillItems("Fridge");
			type = "Fridge";
		}else if(key == 5 && type == null){
			listModel.clear();
			fillItems("Washer");
			type = "Washer";
		}
		//This else is after the user chose(1. TV's, 2. DVD's/Blue rays...)
		else{
			itemsOverview(type, key); //find the device in the catalogue and pop a window with it's info
		}	
	}
	
	
	
	@SuppressWarnings({ "unchecked" })
	//Initialize the Items tab
	public static void setItems(){
		listModel.addElement("1.  TV's");
		listModel.addElement("2.  DVD's/Blue rays");
		listModel.addElement("3.  Cameras");
		listModel.addElement("4.  Consoles");
		listModel.addElement("5.  Fridges");
		listModel.addElement("6.  Washers");
	}
	
	@SuppressWarnings("unchecked")
	//When the user chooses TV, Camera... this method is called to find every item of the same type that he chose
	//and fill the list
	public static void fillItems(String choice){
		int z = 1;
		for(int i=0; i<items_catal.getSize();i++){
			if (choice.equalsIgnoreCase(items_catal.getType(i))){
				listModel.addElement(choice + " number " + z + ": " + items_catal.getModelName(i));
				z++;
			}
		}
	}
	
	//Choose() is called to find the position of the device in the items catalogue
	public static void Choose(String typeofitem, int pos){
		int z = 0; //Counter
		//Run the items_catal, counting all the products of the type = typeof item and if z equals pos then save the position of the item in index
		for(int i = 0; i < items_catal.getSize(); i++){
			if (typeofitem.equalsIgnoreCase(items_catal.getType(i))){
				if(pos == z){
					index=i;										
					return;
				}
				z++;
			}
		}
	}
	
	//This method is called when the user chooses a device
	//Choose() is called to find the position of the device in the items catalogue
	//and pop a new window with the device's info along with an image
	public static void itemsOverview(String typeofitem, int pos){	
		Choose(typeofitem, pos);
		String x = items_catal.print(index);
		//the device's discount
		if(items_catal.getCategory(index).equalsIgnoreCase(CATEGORY1)){
			x = x + "<br>" + "The discount is : " + (int) (categoryAudioVisual*100) + "%" + "</html>";
		}else if(items_catal.getCategory(index).equalsIgnoreCase(CATEGORY2)){
			x = x + "<br>" + "The discount is : " + (int) (categoryGaming*100) + "%" + "</html>";
		}else if(items_catal.getCategory(index).equalsIgnoreCase(CATEGORY3)){
			x = x + "<br>" + "The discount is : " + (int) (categoryHousehold*100) + "%" + "</html>";
		}				
		newWindow(x,items_catal.getModelName(index));
	}
	

	


	
	@SuppressWarnings("unchecked")
	//Sale an item
	public static void Sale(int i){		
		double cost = 0;
		
		//While the user gives nothing as input then pop the window again
		String name = readString("Insert your full name", "Read name");
		while(name.equals("")){
			name = readString("Insert your full name", "Read name");
		}
		if(!name.equalsIgnoreCase("cancel")){ //If the user closed the window or hit cancel then don't continue
			
			String phone = readString("Insert your phone number", "Read phone number");
			while(phone.equals("")){
				phone = readString("Insert your phone number", "Read phone number");
			}
			if(!phone.equalsIgnoreCase("cancel")){
			
				String sdate = readString("Insert the current date like the default one", "Read date"); //current date			
				while(sdate.equals("")){
					sdate = readString("Insert the current date", "Read date");
				}
				if(!sdate.equalsIgnoreCase("cancel")){
					//String to date
					Date date = null;
					SimpleDateFormat format;
					format = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
					try {
						date = format.parse(sdate);
					} catch (ParseException e) {
						
						e.printStackTrace();
					}
								
					//Salenum will be the max sale number we find in the sales catalogue +1
					int max = 0;
					for(int i1 = 0; i1 < sales_catal.getnumberOfSales(); i1++){
						if(sales_catal.getNumber(i1) > max){
							max = sales_catal.getNumber(i1);
						}
					}
					Salenum = max + 1;	//ascending number
		
					
					//getting the cost with the discount
					if(items_catal.getCategory(i).equalsIgnoreCase(CATEGORY1)){
						cost = items_catal.getCost(i) - categoryAudioVisual*items_catal.getCost(i);
					}else if(items_catal.getCategory(i).equalsIgnoreCase(CATEGORY2)){
						cost = items_catal.getCost(i) - categoryGaming*items_catal.getCost(i);
					}else if (items_catal.getCategory(i).equalsIgnoreCase(CATEGORY3)){
						cost = items_catal.getCost(i) - categoryHousehold*items_catal.getCost(i);
					}
					Sale sale = new Sale(Salenum, items_catal.getItem(i), name, phone, date, cost);
					sales_catal.addSale(sale);
					items_catal.setNumAvailable(i, -1); //change available copies(-1) 
					//add the new sale in the sales tab
					listModel2.addElement(listbox2.getModel().getSize() + 1 + "." + "  Sale number: " + "[" + Salenum  + "]"  + "  Item's name: " +  items_catal.getItem(i).getName() + "    Person's name: " + name);
					//pop a window with the final cost
					String finalcost = String.valueOf(cost);
					newWindow("The final cost is: " + finalcost, "Notification");
				}
			}
		}
	}
	
	
	@SuppressWarnings("unchecked")
	//Order an item
	public static void Order(int i){
		double cost = 0;
		
		//While the user gives nothing as input then pop the window again
		String name = readString("Insert your full name", "Read name");
		while(name.equals("")){
			name = readString("Insert your full name", "Read name");
		}
		if(!name.equalsIgnoreCase("cancel")){//If the user closed the window or hit cancel then don't continue

			String phone = readString("Insert your phone number", "Read phone number");
			while(phone.equals("")){
				phone = readString("Insert your phone number", "Read phone number");
			}
			if(!phone.equalsIgnoreCase("cancel")){
				
				String sdate = readString("Insert the current date like the default one", "Read date"); //current date			
				while(sdate.equals("")){
					sdate = readString("Insert the current date", "Read date");
				}
				if(!sdate.equalsIgnoreCase("cancel")){
					//String to date
					Date date = null;			
					SimpleDateFormat format;
					format = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
					try {
						date = format.parse(sdate);
					} catch (ParseException e) {
						
						e.printStackTrace();
					}
					
					String sarrival = readString("Insert the date of arrival like the default one", "Read arrival date");
					while(sarrival.equals("")){
						sarrival = readString("Insert the date of arrival like the default one", "Read arrival date");
					}
					if(!sarrival.equalsIgnoreCase("cancel")){
						//String to date
						Date arrival = null; 					
						SimpleDateFormat format1;
						format1 = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
						try {
							arrival = format1.parse(sarrival);
						} catch (ParseException e) {
							
							e.printStackTrace();
						}	
						
						//Ordernum will be the max order number we find in the order catalogue +1
						int max = 0;
						for(int i1 = 0; i1 < orders_catal.getnumberOfOrders(); i1++){
							if(orders_catal.getNumber(i1) > max){
								max = orders_catal.getNumber(i1);
							}
						}
						Ordernum = max + 1; //ascending number
						//getting the cost with the discount
						if(items_catal.getCategory(i).equalsIgnoreCase(CATEGORY1)){
							cost = items_catal.getCost(i) - categoryAudioVisual*items_catal.getCost(i);
						}else if(items_catal.getCategory(i).equalsIgnoreCase(CATEGORY2)){
							cost = items_catal.getCost(i) - categoryGaming*items_catal.getCost(i);
						}else if (items_catal.getCategory(i).equalsIgnoreCase(CATEGORY3)){
							cost = items_catal.getCost(i) - categoryHousehold*items_catal.getCost(i);
						}
						Order order = new Order(Ordernum,items_catal.getItem(i), name, phone, date, cost, arrival, done);
						orders_catal.addOrder(order);
						//Add an order in Orders tab
						listModel3.addElement(listbox3.getModel().getSize() + 1 + "." + "  Order number: " + "[" + Ordernum + "]" + "    Item's name: " + items_catal.getItem(i).getName() + "    Person's name: " + name +  "    Executed: " + done);
						//pop a window with the final cost
						String finalcost = String.valueOf(cost);
						newWindow("The final cost is: " + finalcost, "Notification");
					}
				}
			}
		}
			
		
	}
	
	//Input dialogs, so the user can give name, phone, date, arrival date
	public static String readString(String key, String title ){
		JFrame frame = new JFrame();
		JLabel label = new JLabel(key);
		String string;
		
		//To read the current date
		if(title.equalsIgnoreCase("read date")){	
			//Date to string so there is a default value 
			Date date = new Date();
			Format formatter = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
			String s = formatter.format(date);

			string = (String) JOptionPane.showInputDialog(frame, label,
					title, JOptionPane.PLAIN_MESSAGE,null,null,s);
		}
		//To read the arrival date
		else if(title.equalsIgnoreCase("Read arrival date")){
			//Date to arrival date(+14 days)
			Date date = new Date();
			Date arrival = new Date();
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, 14);
			arrival = cal.getTime();
			
			//Arrival date to string so there is a default value 
			Format formatter = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
			String s = formatter.format(arrival);
			
			 string = (String) JOptionPane.showInputDialog(frame, label,
						title, JOptionPane.PLAIN_MESSAGE,null,null,s);
			
		}
		//To read name, phone
		else{			
			 string = (String)JOptionPane.showInputDialog(frame, label, title, -1);
		}	
		
		if(string != null ){ //if not closed and not cancel button
			if(!string.equals("")){		//if not empty		
				return string;
			}else{
				return "";
			}
		}
		else{ 
			return "cancel";
		}
	}
	
	//Called when double-click happens in Sales tab
	public static void salesOverview(int pos){		
		for(int i = 0; i < sales_catal.getnumberOfSales(); i++){
			if(i == pos){ 
				String x = sales_catal.printSale(i); //all the sales info
				newWindow(x, sales_catal.getSaleName(i)); //new window with the sale's info and the picture of the item sold
				break;
			}
		}
	}

	//Called when double-click happens in Orders tab
	public static void ordersOverview(int pos){
		for(int i = 0; i < orders_catal.getnumberOfOrders(); i++){
			if(i == pos){
				String x = orders_catal.printOrder(i); //all the order's info
				newWindow(x, orders_catal.getOrderName(i)); //new window with the order's info and the picture of the item ordered
				break;
			}
		}
	}
	
	//Searches the input of the user when he wants to search a sale/order by the customer's name
	//bool is either sale or order
	//key is the name the user gave
	public static void searchInput(String key, String bool){
		int flag = 0;
		if(bool.equalsIgnoreCase("sale")){
			for(int i =0; i < sales_catal.getnumberOfSales(); i++ ){
				if(key.equalsIgnoreCase(sales_catal.getName(i))){
					flag =1;
					String x = sales_catal.printSale(i); //all the sales info
					newWindow(x, sales_catal.getSaleName(i));
					
				}
			}
		}else{
			for(int i =0; i < orders_catal.getnumberOfOrders(); i++ ){
				if(key.equalsIgnoreCase(orders_catal.getName(i))){
					ordindex = i; //save the position in ordindex
					flag =1;
					String x = orders_catal.printOrder(i); //all the sales info
					newWindow(x, orders_catal.getOrderName(i));
					
				}
			}
		}
		//If we didn't find any match of the input
		if(flag == 0 && bool.equalsIgnoreCase("sale")){
			newWindow("Sale not found", "Notification");
		}else if(flag == 0 && bool.equalsIgnoreCase("order")){
			newWindow("Order not found", "Notification");
		}
	}
	
	@SuppressWarnings("rawtypes")
	//Puts the search buttons in Sales and Orders panel
	public static void putSearchButton(JPanel panel, JList list, JButton button, GridBagConstraints c){		
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 0;
		
		panel.add(list,c);
		
		c = new GridBagConstraints();
		
		c.anchor = GridBagConstraints.LAST_LINE_END;		
		c.weightx = 0.001;		
		c.gridx =  2;
		c.gridy = 1;
		button.setPreferredSize(new Dimension(100,30));
		
		panel.add(button, c);
	}
	
	@SuppressWarnings("unchecked")
	//Called when execord button is pressed
	public static void executeOrder(int i){
		//If already executed
		if(orders_catal.getStatus(i) == true){
			newWindow("This order has been executed", "Notification");
			return;
		}else{
			//While input is empty
			String sdate = readString("Insert the current date like the default one", "Read date"); //current date			
			while(sdate.equals("")){
				sdate = readString("Insert the current date", "Read date");
			}
			if(!sdate.equalsIgnoreCase("cancel")){ //If didn't close the window or didn't hit cancel button
				//Date to string for default value
				Date date = null;
				SimpleDateFormat format;
				format = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
				try {
					date = format.parse(sdate);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
				
				done = true;
				orders_catal.setStatus(i,done);
				String name = orders_catal.getName(i);
				String phone = orders_catal.getPhone(i);
				Item device = orders_catal.getItem(i);
				
				double cost = orders_catal.getCost(i);				
				int max = 0;
				for(int i1 = 0; i1 < sales_catal.getnumberOfSales(); i1++){
					if(sales_catal.getNumber(i1) > max){
						max = sales_catal.getNumber(i1);
					}
				}
				Salenum = max + 1; //ascending number 
				Sale sale = new Sale(Salenum, device, name, phone, date, cost);
				sales_catal.addSale(sale);
				//Add sale into Sales tab
				listModel2.addElement(listbox2.getModel().getSize() + 1 + "." + "  Sale number: " + "[" + Salenum + "]" + ",    Item's name: " +  items_catal.getItem(i).getName() + ",    Person's name: " + name);
				//re-initialize the Orders tab, cause now we have an order
				//that has been executed and we need to change from false to true the value
				listModel3.clear();
				for(int i1 = 0; i1 < orders_catal.getnumberOfOrders(); i1++){
					listModel3.addElement(listbox3.getModel().getSize() + 1 + "." + "  Order number: " + "[" + orders_catal.getNumber(i1) + "]" + "    Item's name: " +  orders_catal.getOrderName(i1) + "    Person's name: " + orders_catal.getName(i1) +  "    Executed: " + orders_catal.getStatus(i1));
				}
				//A window with the final cost
				String finalcost = String.valueOf(cost);
				newWindow("The final cost is: " + finalcost, "Notification");
				done = false; //re-initialize the status
				
			}
		}
	}
}