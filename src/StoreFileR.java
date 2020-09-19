import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class StoreFileR {
	
	private ArrayList<Item> items = new ArrayList<Item>();
	private ArrayList<Order> orders = new ArrayList<Order>();
	private ArrayList<Sale> sales = new ArrayList<Sale>();
	
	
	
	//Load available items from file.
	public void loadItems(String file_name){
		
		File f = null;
		BufferedReader reader = null;
		String line;
		int z = 0; //Used to check if the entity order { } is correct.
		
		try { //Create a new file.
			f = new File(file_name);
		} catch (NullPointerException e){
			System.err.println("File not found.");
		}
		
		try { //Create a new BufferedReader.
			reader = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e){
			System.err.println("Error opening file.");
		}
			
		try { //Starts reading file.
			
			line = reader.readLine();			
			if(!line.trim().equals(" ")){
				if(line != null){
					while(line != null){
						if(line.trim().equalsIgnoreCase("ITEM_LIST")){
							line = reader.readLine();				
							if(line != null){
								while(line != null){
									if(line.trim().equals("{")){
										line = reader.readLine();
										if(line != null){								
											while(line != null){
												
												Item product=null;									
												String code = null, model =null; 
												
												if(line.trim().equalsIgnoreCase("item")){													
													line = reader.readLine();
													if(line != null){
														while(line != null){
															
															reader.mark(500);	//mark() is called with a limit of 500 chars, at the beginning of the entity
																				//so if the necessary info is found we can reset here, necessary info is item_type, model, code.
															
															if(line != null){
																if(line.trim().equals("{")){											
																	line = reader.readLine();
																	int counter = 0; //counts the necessary info.
																	
																	if(line != null){
																		while(counter != 3 && line.trim().equals("}") == false && line != null){ //While we haven't found all the necessary info and we haven't reached the end of the entity.
																				if(line.trim().toLowerCase().startsWith("item_type")){
																					if(line != null){
																						if(line.trim().substring(9).trim().equalsIgnoreCase("TV")){
																							product = new TV();
																							product.setType("TV");																							
																							counter++;	
																						}else if(line.trim().substring(9).trim().equalsIgnoreCase("Camera")){
																							product = new Camera();
																							product.setType("Camera");																						
																							counter++;	
																						}else if(line.trim().substring(9).trim().equalsIgnoreCase("DVD/BR")){
																							product = new DVD_BR();
																							product.setType("DVD/BR");																							
																							counter++;	
																						}else if(line.trim().substring(9).trim().equalsIgnoreCase("Console")){
																							product = new Console();
																							product.setType("Console");																						
																							counter++;	
																						}else if(line.trim().substring(9).trim().equalsIgnoreCase("Fridge")){
																							product = new Fridge();
																							product.setType("Fridge");																							
																							counter++;	
																						}else if(line.trim().substring(9).trim().equalsIgnoreCase("Washer")){
																							product = new Washer();
																							product.setType("Washer");
																							
																							counter++;	
																						}
																																												
																					}
																				}
																				else if(line.trim().toLowerCase().startsWith("code")){
																					code = line.trim().substring(4).trim();
																					counter++;
																				}
																				else if(line.trim().toLowerCase().startsWith("model")){
																					if(!line.toLowerCase().contains("year")){
																						model = line.trim().substring(5).trim();
																						counter++;
																					}	
																				}
																				
																				line = reader.readLine();																															
																		}
																		if(line != null){
																			if(counter == 3){ //if we found the 3 necessary tags then reset to find the other info.
																				product.setCode(code);
																				product.setName(model);
																				reader.reset();
																			
																				line = reader.readLine();
																				if(line != null){
																					while(line.trim().equals("}") == false && line!=null){ //Search for other info until the end of the entity.
																						if(line != null){
																							//If we don't find the other info then default value for strings is "NOT AVAILABLE", for double and integers it's 0
																							//So for strings we change null to "NOT AVAILABLE", for double and integers we change nothing.
																							if(product instanceof TV){ 
																								if(line != null){
																									if(line.trim().toLowerCase().startsWith("panel_type")){
																										((TV)product).setTVtype(line.trim().substring(10).trim());
																									}else if(((TV) product).getTVtype() == null){ 
																										((TV)product).setTVtype("NOT AVAILABLE");
																									}
																								
																									if(line.trim().toLowerCase().startsWith("dimensions")){
																										((TV)product).setInches(line.trim().substring(10).trim());
																									}else if(((TV) product).getInches() == null){
																										((TV)product).setInches("NOT AVAILABLE");
																									}
																									
																									
																									if(line.trim().toLowerCase().startsWith("resolution")){
																										((TV)product).setRes(line.trim().substring(10).trim());
																									}else if(((TV) product).getRes() == null){
																										((TV)product).setRes("NOT AVAILABLE");
																									}
																									
																									if(line.trim().toLowerCase().startsWith("interfaces")){
																										((TV)product).setPort(line.trim().substring(10).trim());
																									}else if(((TV) product).getPort() == null){
																										((TV)product).setPort("NOT AVAILABLE");
																									}
																								}
																							}else if(product instanceof Camera){
																								if(line != null){
																									if(line.trim().toLowerCase().startsWith("panel_type")){
																										((Camera)product).setCamtype(line.trim().substring(10).trim());
																									}else if(((Camera) product).getCamtype() == null){
																										((Camera)product).setCamtype("NOT AVAILABLE");
																									}
																								
																									if(line.trim().toLowerCase().startsWith("megapixels")){
																										((Camera)product).setPixels(Double.parseDouble(line.trim().substring(10).trim()));
																									}
																									
																									
																									if(line.trim().toLowerCase().startsWith("optical_zoom")){
																										((Camera)product).setOptiZoom(line.trim().substring(12).trim());
																									}else if(((Camera) product).getOptiZoom() == null){
																										((Camera)product).setOptiZoom("NOT AVAILABLE");
																									}
																									
																									if(line.trim().toLowerCase().startsWith("digital_zoom")){
																										((Camera)product).setDigiZoom(line.trim().substring(12).trim());
																									}else if(((Camera) product).getDigiZoom() == null){
																										((Camera)product).setDigiZoom("NOT AVAILABLE");
																									}
																									
																									if(line.trim().toLowerCase().startsWith("dimensions")){
																										((Camera)product).setInches(line.trim().substring(10).trim());
																									}else if(((Camera) product).getInches() == null){
																										((Camera)product).setInches("NOT AVAILABLE");
																									}
																								}
																							}else if(product instanceof DVD_BR){
																								if(line != null){
																									if(line.trim().toLowerCase().startsWith("panel_type")){
																										((DVD_BR)product).setDVD_BRtype(line.trim().substring(10).trim());
																									}else if(((DVD_BR) product).getDVD_BRtype() == null){
																										((DVD_BR)product).setDVD_BRtype("NOT AVAILABLE");
																									}
																								
																									if(line.trim().toLowerCase().startsWith("resolution")){
																										((DVD_BR)product).setRes(line.trim().substring(10).trim());
																									}else if(((DVD_BR) product).getRes() == null){
																										((DVD_BR)product).setRes("NOT AVAILABLE");
																									}
																									
																									
																									if(line.trim().toLowerCase().startsWith("format")){
																										((DVD_BR)product).setFormat(line.trim().substring(6).trim());
																									}else if(((DVD_BR) product).getFormat() == null){
																										((DVD_BR)product).setFormat("NOT AVAILABLE");
																									}
																								}
																							}else if(product instanceof Console){
																								if(line != null){
																									if(line.trim().toLowerCase().startsWith("panel_type")){
																										((Console)product).setConsoleType(line.trim().substring(10).trim());
																									}else if(((Console) product).getConsoletype() == null){
																										((Console)product).setConsoleType("NOT AVAILABLE");
																									}
																								
																									if(line.trim().toLowerCase().startsWith("cpu")){
																										((Console)product).setCPU(line.trim().substring(3).trim());
																									}else if(((Console) product).getCPU() == null){
																										((Console)product).setCPU("NOT AVAILABLE");
																									}
																									
																									
																									if(line.trim().toLowerCase().startsWith("graphics")){
																										((Console)product).setGraphics(line.trim().substring(8).trim());
																									}else if(((Console) product).getGraphics() == null){
																										((Console)product).setGraphics("NOT AVAILABLE");
																									}
																									
																									if(line.trim().toLowerCase().startsWith("sound")){
																										((Console)product).setSound(line.trim().substring(5).trim());
																									}else if(((Console) product).getSound() == null){
																										((Console)product).setSound("NOT AVAILABLE");
																									}
																									
																									if(line.trim().toLowerCase().startsWith("drive")){
																										((Console)product).setDrive(line.trim().substring(5).trim());
																									}else if(((Console) product).getSound() == null){
																										((Console)product).setSound("NOT AVAILABLE");
																									}
																								}
																							}else if(product instanceof Fridge){
																								if(line != null){
																									if(line.trim().toLowerCase().startsWith("panel_type")){
																										((Fridge)product).setFridgetype(line.trim().substring(10).trim());
																									}else if(((Fridge) product).getFridgetype() == null){
																										((Fridge)product).setFridgetype("NOT AVAILABLE");
																									}
																								
																									if(line.trim().toLowerCase().startsWith("energy_class")){
																										((Fridge)product).setEclass(line.trim().substring(12).trim());
																									}else if(((Fridge) product).getEclass() == null){
																										((Fridge)product).setEclass("NOT AVAILABLE");
																									}
																										
																									if(line.trim().toLowerCase().startsWith("fridge_capacity")){
																										((Fridge)product).setFricapacity(line.trim().substring(15).trim());
																									}else if(((Fridge) product).getFricapacity() == null){
																										((Fridge)product).setFricapacity("NOT AVAILABLE");
																									}
																									
																									if(line.trim().toLowerCase().startsWith("freezer_capacity")){
																										((Fridge)product).setFreecapacity(line.trim().substring(16).trim());
																									}else if(((Fridge) product).getFreecapacity() == null){
																										((Fridge)product).setFreecapacity("NOT AVAILABLE");
																									}																																							
																								}
																							}else if(product instanceof Washer){
																								if(line != null){																														
																									if(line.trim().toLowerCase().startsWith("energy_class")){
																										((Washer)product).setEclass(line.trim().substring(12).trim());
																									}else if(((Washer) product).getEclass() == null){
																										((Washer)product).setEclass("NOT AVAILABLE");
																									}
																										
																									if(line.trim().toLowerCase().startsWith("capacity")){
																										((Washer)product).setCapacity(line.trim().substring(8).trim());
																									}else if(((Washer) product).getCapacity() == null){
																										((Washer)product).setCapacity("NOT AVAILABLE");
																									}
																									
																									if(line.trim().toLowerCase().startsWith("speed")){
																										((Washer)product).setSpeed(line.trim().substring(5).trim());
																									}else if(((Washer) product).getSpeed() == null){
																										((Washer)product).setSpeed("NOT AVAILABLE");
																									}																																							
																								}
																							}
																							if(line.trim().toLowerCase().startsWith("category")){
																								product.setCategory(line.trim().substring(8).trim());																	
																							}else if((product).getCategory() == null){
																								(product).setCategory("NOT AVAILABLE");
																							}
																							
																							if(line.trim().toLowerCase().startsWith("manufacturer")){
																								product.setManu(line.trim().substring(12).trim());																	
																							}else if(( product).getManu() == null){
																								(product).setManu("NOT AVAILABLE");
																							}
																							
																							if(line.trim().toLowerCase().startsWith("pieces")){
																								product.setNumAvailable(Integer.parseInt(line.trim().substring(6).trim()));	
																							}
																							
																							if(line.trim().toLowerCase().startsWith("price")){
																								product.setPrice(Double.parseDouble(line.trim().substring(5).trim()));							
																							}
																							
																							if(line.trim().toLowerCase().startsWith("model_year")){
																								product.setYear(Integer.parseInt(line.trim().substring(10).trim()));
																							}
																																			
																							line = reader.readLine();
																						}
																					}
																				}
																			}
																		}	
																		if(counter == 3 ){ //if all necessary info is found then add product, else z++
																			items.add(product);
																		}else{
																			z++;
																		}
																	}
																	
																}
																
															}
															line = reader.readLine();
														}	
													}
												}
												line = reader.readLine();
											}
											
										}
									}
									line = reader.readLine();
								}
							}
						}
						line = reader.readLine();
					}
				}
				
			}
			if(z != 0){ //if there is at least one entity with  insufficient info then print message.
				JFrame jf = new JFrame();
				if(z == 1){
					JOptionPane.showMessageDialog(jf, "<html>" + "Insufficient info while reading "  + file_name + "<br>" + z + " item has not been added.", "Notification", JOptionPane.PLAIN_MESSAGE);
				}else if( z > 1){
					JOptionPane.showMessageDialog(jf, "<html>" + "Insufficient info while reading "  + file_name + "<br>" + z + " items have not been added.", "Notification", JOptionPane.PLAIN_MESSAGE);
				}
			}
				
			
		
			
		} catch (IOException e){
			System.out.println("Error reading line.");
		}
		
		try {
			reader.close();
		} catch(IOException e) {
			System.err.println("Error closing file.");
		}
	
	}
	
	
	//Load orders from file.
	public void loadOrders(String file_name){
		int z = 0;
		File f = null;
		BufferedReader reader = null;
		String line;
		
		
		try { //Create new file.
			f = new File(file_name);
		} catch (NullPointerException e){
			System.err.println("File not found.");
		}
		
		try { //Create new BufferedReader.
			reader = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e){
			System.err.println("Error opening file.");
		}		
		try {
			line = reader.readLine();			
			if(!line.trim().equals(" ")){
				if(line != null){
					while(line != null){
						if(line.trim().equalsIgnoreCase("ORDER_LIST")){
							line = reader.readLine();				
							if(line != null){
								while(line != null){
									if(line.trim().equals("{")){
										line = reader.readLine();
										if(line != null){								
											while(line != null){																						
												if(line.trim().equalsIgnoreCase("order")){
													
													line = reader.readLine();
													if(line != null){
														while(line != null){
															
															reader.mark(500);	
															
															if(line != null){										
																if(line.trim().equals("{")){											
																	line = reader.readLine();
																	
																	Item product = null;
																	String name = null, phone = null, manufacturer = null, model = null, category = null;
																	double price = 0.0;
																	int ord_num = 0;
																	Date ord_date = null, arrival = null;
																	String teda1 = null, teda2 = null; //store dates from file here and convert them to Date.
																	boolean status = false;
																	int counter = 0; //count necessary info item_type, model, manufacturer, category.
																	int flag = 0; //Used to check if model, manufacturer, category exist in available items catalogue.
		
																	
																	if(line != null){
																		while(counter != 4 && line.trim().equals("}") == false && line != null){ //While we have't found necessary info and haven't reached end of entity.
																				if(line.trim().toLowerCase().startsWith("item_type")){
																					if(line != null){
																						if(line.trim().substring(9).trim().equalsIgnoreCase("TV")){
																							product = new TV();
																							product.setType("TV");																							
																							counter++;	
																						}else if(line.trim().substring(9).trim().equalsIgnoreCase("Camera")){
																							product = new Camera();
																							product.setType("Camera");																						
																							counter++;	
																						}else if(line.trim().substring(9).trim().equalsIgnoreCase("DVD/BR")){
																							product = new DVD_BR();
																							product.setType("DVD/BR");																							
																							counter++;	
																						}else if(line.trim().substring(9).trim().equalsIgnoreCase("Console")){
																							product = new Console();
																							product.setType("Console");																							
																							counter++;	
																						}else if(line.trim().substring(9).trim().equalsIgnoreCase("Fridge")){
																							product = new Fridge();
																							product.setType("Fridge");
																							
																							counter++;	
																						}else if(line.trim().substring(9).trim().equalsIgnoreCase("Washer")){
																							product = new Washer();
																							product.setType("Washer");																																														
																							counter++;	
																						}																																											
																					}
																				}														
																				else if(line.trim().toLowerCase().startsWith("model")){
																					model = line.trim().substring(5).trim();
																					counter++;
																					
																				}
																				else if(line.trim().toLowerCase().startsWith("manufacturer")){
																					manufacturer = line.trim().substring(12).trim();
																					counter++;
																					
																				}
																				else if(line.trim().toLowerCase().startsWith("category")){
																					category = line.trim().substring(8).trim();
																					counter++;
																				}
																				
																				line = reader.readLine();																			
																		}
																		if(line != null){
																			if(counter == 4){ //If we found all necessary tags.
																				
																				
																				for(int i = 0; i < items.size(); i++){
																					//if model and category and manufacturer exist in available items catalogue for the same device, then set and change flag to 1.
																					if(model.equalsIgnoreCase(items.get(i).getName()) && manufacturer.equalsIgnoreCase(items.get(i).getManu()) && category.equalsIgnoreCase(items.get(i).getCategory())){
																						product.setName(model);
																						product.setManu(manufacturer);
																						product.setCategory(category);
																						flag = 1;																					
																					}
																				}
																				if(flag == 1){ //If they exist for the same device then reset to search for other info.
																					reader.reset();
																			
																					line = reader.readLine();
																					if(line != null){
																						while(line.trim().equals("}") == false && line!=null){ //While haven't reached the end of the entity.
																							if(line != null){
																								if(line.trim().toLowerCase().startsWith("order_number")){
																									
																									ord_num = Integer.parseInt(line.trim().substring(12).trim());
																								}
																								else if(line.trim().toLowerCase().startsWith("name")){
																									
																									name = line.trim().substring(4).trim();
																								}
																								else if(line.trim().toLowerCase().startsWith("phonenumber")){
																									
																									phone = line.trim().substring(11).trim();
																								}																																									
																								else if(line.trim().toLowerCase().startsWith("price")){
																									
																									price = Double.parseDouble(line.trim().substring(5).trim());
																								}
																								else if(line.trim().toLowerCase().startsWith("status")){
																									if(line.trim().substring(6).trim().equalsIgnoreCase("not executed")){																										
																										status = false;
																										
																									}else if(line.trim().substring(6).trim().equalsIgnoreCase("executed")){																										
																										status = true;
																										
																									}																																																	
																								}
																								else if(line.trim().toLowerCase().startsWith("order_date")){
																									teda1 = line.trim().substring(10).trim();
																									 
																									//string to date.
																								 	SimpleDateFormat format;
																									format = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
																									try {
																										ord_date = format.parse(teda1);
																									} catch (ParseException e) {
																										
																										e.printStackTrace();
																									}
																											
																									   
																								}
																								else if(line.trim().toLowerCase().startsWith("delivery_date")){
																									teda2 = line.trim().substring(13).trim();
																									
																									//string to date
																								 	SimpleDateFormat format;
																									format = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
																									try {
																										arrival = format.parse(teda2);
																									} catch (ParseException e) {
																										
																										e.printStackTrace();
																									}
																											
																								}
																									
																																				
																								line = reader.readLine();
																							}
																						}
																					}
																				}
																			}
																		}
																		//if we found all necessary info.
																		if(counter == 4 && flag == 1){	
																			
																			// if name or phone or ord_date or arrival is null then change null to a default value
																			// for price and ord_number default value is 0.
																			if(name == null){
																				name = "NOT AVAILABLE";
																			}
																			if(phone == null){
																				phone = "NOT AVAILABLE";
																			}
																			if(ord_date == null){
																				String defaultd = "Mon Jun 21 00:00:00 EEST 2016";  //default value
																			 	SimpleDateFormat format;
																				format = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
																				try {
																					ord_date = format.parse(defaultd);
																				} catch (ParseException e) {
																					
																					e.printStackTrace();
																				}																																								 
																			}
																			if(arrival == null){
																				String defaultd = "Mon Jun 21 00:00:00 EEST 2016"; //default value.
																			 	SimpleDateFormat format;
																				format = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
																				try {
																					arrival = format.parse(defaultd);
																				} catch (ParseException e) {
																					
																					e.printStackTrace();
																				}																	 
																			}
																			//create new order and add it
																			Order order = new Order(ord_num, product, name, phone, ord_date, price, arrival, status);
																			orders.add(order);
																		}else{
																			z++;	
																		}
																		
																		
																	}
																	
																	
																}
																
																
															}
															line = reader.readLine();
															
														}
														
													}
													
												}
												line = reader.readLine();
												
											}
											
											
										}
										
									}
									line = reader.readLine();
									
								}
								
							}
							
						}
						line = reader.readLine();
						
					}
					
				}
				
			}				
			if(z != 0){ //if there is at least one entity with  insufficient info then print message.
				JFrame jf = new JFrame();
				if(z == 1){
					JOptionPane.showMessageDialog(jf, "<html>" + "Insufficient info while reading "  + file_name + "<br>" + z + " order has not been added.", "Notification", JOptionPane.PLAIN_MESSAGE);
				}else if( z > 1){
					JOptionPane.showMessageDialog(jf, "<html>" + "Insufficient info while reading "  + file_name + "<br>" + z + " orders have not been added.", "Notification", JOptionPane.PLAIN_MESSAGE);
				}
			}						
			
				
			
		}catch (IOException e){
			System.out.println("Error reading line.");
		}
		
		try {
			reader.close();
		} catch(IOException e) {
			System.err.println("Error closing file.");
		}
	}
	
	
	//Load sales from file.
	public void loadSales(String file_name){
		
		int z = 0;
		File f = null;
		BufferedReader reader = null;
		String line;
		
		
		try {
			f = new File(file_name);
		} catch (NullPointerException e){
			System.err.println("File not found.");
		}
		
		try {
			reader = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e){
			System.err.println("Error opening file.");
		}		
		try {
			line = reader.readLine();			
			if(!line.trim().equals(" ")){
				if(line != null){
					while(line != null){
						if(line.trim().equalsIgnoreCase("SALES_LIST")){
							line = reader.readLine();				
							if(line != null){
								while(line != null){
									if(line.trim().equals("{")){
										line = reader.readLine();
										if(line != null){								
											while(line != null){							
												if(line.trim().equalsIgnoreCase("sale")){
													
													line = reader.readLine();
													if(line != null){
														while(line != null){
															reader.mark(500);													
															if(line != null){
																if(line.trim().equals("{")){											
																	line = reader.readLine();
																	
																	Item product = null;
																	String name = null, phone = null, manufacturer = null, model = null, category = null;
																	double price = 0.0;
																	int sale_num = 0;
																	Date sale_date = null;
																	String teda = null;
																	int counter = 0; //counts necessary tags.
																	int flag = 0; 
																	
																	if(line != null){
																		while(counter != 4 && line.trim().equals("}") == false && line != null){ //while haven't found necessary tags and haven't reached the end of the entity.
																				if(line.trim().toLowerCase().startsWith("item_type")){
																					if(line != null){
																						if(line.trim().substring(9).trim().equalsIgnoreCase("TV")){
																							product = new TV();
																							product.setType("TV");
																							counter++;	
																						}else if(line.trim().substring(9).trim().equalsIgnoreCase("Camera")){
																							product = new Camera();
																							product.setType("Camera");																						
																							counter++;	
																						}else if(line.trim().substring(9).trim().equalsIgnoreCase("DVD/BR")){
																							product = new DVD_BR();
																							product.setType("DVD/BR");																							
																							counter++;	
																						}else if(line.trim().substring(9).trim().equalsIgnoreCase("Console")){
																							product = new Console();
																							product.setType("Console");																							
																							counter++;	
																						}else if(line.trim().substring(9).trim().equalsIgnoreCase("Fridge")){
																							product = new Fridge();
																							product.setType("Fridge");																							
																							counter++;	
																						}else if(line.trim().substring(9).trim().equalsIgnoreCase("Washer")){
																							product = new Washer();
																							product.setType("Washer");																							
																							counter++;	
																						}																					
																					}
																				}														
																				else if(line.trim().toLowerCase().startsWith("model")){
																					model = line.trim().substring(5).trim();
																					counter++;
																					
																				}
																				else if(line.trim().toLowerCase().startsWith("manufacturer")){
																					manufacturer = line.trim().substring(12).trim();
																					counter++;
																					
																				}
																				else if(line.trim().toLowerCase().startsWith("category")){
																					category = line.trim().substring(8).trim();
																					counter++;
																				}
																				
																				line = reader.readLine();
																		}
																		if(line != null){
																			if(counter == 4){ //if necessary tags are found.
																				
																				//if model and category and manufacturer exist in available items catalogue for the same device, then set and change flag to 1.
																				for(int i = 0; i < items.size(); i++){
																					if(model.equalsIgnoreCase(items.get(i).getName()) && manufacturer.equalsIgnoreCase(items.get(i).getManu()) && category.equalsIgnoreCase(items.get(i).getCategory())){
																						product.setName(model);
																						product.setManu(manufacturer);
																						product.setCategory(category);
																						flag = 1;
																				
																					}
																				}
																				if(flag == 1){ //If they exist for the same device then reset to search for other info.
																					reader.reset();
																			
																					line = reader.readLine();
																					if(line != null){
																						while(line.trim().equals("}") == false && line!=null){
																							if(line != null){
																								if(line.trim().toLowerCase().startsWith("sales_number")){
																									
																									sale_num = Integer.parseInt(line.trim().substring(12).trim());
																								}
																								else if(line.trim().toLowerCase().startsWith("name")){
																									
																									name = line.trim().substring(4).trim();
																								}
																								else if(line.trim().toLowerCase().startsWith("phonenumber")){
																									
																									phone = line.trim().substring(11).trim();
																								}																																									
																								else if(line.trim().toLowerCase().startsWith("price")){
																									
																									price = Double.parseDouble(line.trim().substring(5).trim());
																								}
																								else if(line.trim().toLowerCase().startsWith("date")){
																									teda = line.trim().substring(4).trim();
																									 
																										 	SimpleDateFormat formatter;
																											formatter = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
																											try {
																												sale_date = formatter.parse(teda);
																											} catch (ParseException e) {
																												
																												e.printStackTrace();
																											}																									   
																								}																																																													
																								line = reader.readLine();
																								
																							}
																						}
																					}
																				}
																			}
																		}	
																		if(counter == 4 && flag == 1){ //If all necessary info is found.
																			//Default value for name, phone, date
																			//For price, sales_num is 0.
																			if(name == null){
																				name = "NOT AVAILABLE";
																			}
																			if(phone == null){
																				phone = "NOT AVAILABLE";
																			}
																			if(sale_date == null){
																				String defaultd = "Mon Jun 21 00:00:00 EEST 2016"; 
																			 	SimpleDateFormat format;
																				format = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
																				try {
																					sale_date = format.parse(defaultd);
																				} catch (ParseException e) {
																					
																					e.printStackTrace();
																				}
																			}
																			//Create new sale and add it.
																			Sale sale = new Sale(sale_num, product, name, phone, sale_date, price);
																			sales.add(sale);
																		}else{
																			z++;
																		}
																		
																		
																	}
																	
																	
																}
																
																
															}
															line = reader.readLine();
															
														}
														
													}
													
												}
												line = reader.readLine();
												
											}
											
											
										}
										
									}
									line = reader.readLine();
									
								}
								
							}
							
						}
						line = reader.readLine();
						
					}
					
				}
				
			}
			//if there is at least one entity with  insufficient info then print message.
			if(z != 0){
				JFrame jf = new JFrame();
				if(z == 1){
					JOptionPane.showMessageDialog(jf, "<html>" + "Insufficient info while reading "  + file_name + "<br>" + z + " sale has not been added.", "Notification", JOptionPane.PLAIN_MESSAGE);
				}else if( z > 1){
					JOptionPane.showMessageDialog(jf, "<html>" + "Insufficient info while reading "  + file_name + "<br>" + z + " sales have not been added.", "Notification", JOptionPane.PLAIN_MESSAGE);
				}
			}
			
		}catch (IOException e){
			System.out.println("Error reading line.");
		}
		
		try {
			reader.close();
		} catch(IOException e) {
			System.err.println("Error closing file.");
		}
	}
	
	
	//Save orders array list in the file.	
	public void saveOrders(String file_name){
		File f = null;
		BufferedWriter writer = null;

		try	{
			f = new File(file_name);
		}
		catch (NullPointerException e) {
			System.err.println ("File not found.");
		}

		try	{
			writer = new BufferedWriter(new OutputStreamWriter
				(new FileOutputStream(f)));
		}
		catch (FileNotFoundException e) {
			System.err.println("Error opening file for writing!");
		}
		
		try	{
			writer.write("ORDER_LIST");
			writer.newLine();
			writer.write("{");
		}
		catch (IOException e) {
			System.err.println("Write error!");
		}

		for (Order order: orders) {
			
			try	{
				writer.newLine();
            	writer.write("\t"+"ORDER");
            	writer.newLine();
            	writer.write("\t"+"{");
            	writer.newLine();
            	writer.write("\t"+"\t"+"ITEM_TYPE " + order.getItem().getType());
            	writer.newLine();
            	writer.write("\t"+"\t"+"ORDER_NUMBER "+ order.getNumber());
            	writer.newLine();
            	writer.write("\t"+"\t"+"NAME "+ order.getName());
            	writer.newLine();
            	writer.write("\t"+"\t"+"MANUFACTURER "+ order.getItem().getManu());
            	writer.newLine();
            	writer.write("\t"+"\t"+"CATEGORY "+ order.getItem().getCategory());
            	writer.newLine();
            	writer.write("\t"+"\t"+"PRICE " + order.getCost());
            	writer.newLine();
            	writer.write("\t"+"\t"+"MODEL " + order.getItem().getName());
            	writer.newLine();
            	writer.write("\t"+"\t"+"PHONENUMBER " + order.getPhone());
            	writer.newLine();
            	writer.write("\t"+"\t"+"ORDER_DATE " + order.getDate());
            	writer.newLine();
            	writer.write("\t"+"\t"+"DELIVERY_DATE " + order.getArrivalDate());
            	writer.newLine();
            	writer.write("\t"+"\t"+"STATUS ");
            	if(order.getStatus() == false){
            		writer.write("NOT EXECUTED");
            	}else if(order.getStatus() == true){
            		writer.write("EXECUTED");
            	}
            	
            	
              
			}
		
			catch (IOException e) {
				System.err.println("Write error!");
			}
			
			try	{
				writer.newLine();
				writer.write("\t"+"}");
			}
			catch (IOException e) {
				System.err.println("Write error!");
			}
		}
		try {
			
			writer.newLine();
			writer.write("}");
			writer.close();

		}
		catch (IOException e) {
			System.err.println("Error closing file.");
		}
	}
	
	
	
	//Save sales array list in the file.
	public void saveSales(String file_name){
		File f = null;
		BufferedWriter writer = null;

		try	{
			f = new File(file_name);
		}
		catch (NullPointerException e) {
			System.err.println ("File not found.");
		}

		try	{
			writer = new BufferedWriter(new OutputStreamWriter
				(new FileOutputStream(f)));
		}
		catch (FileNotFoundException e) {
			System.err.println("Error opening file for writing!");
		}
		
		try	{
			writer.write("SALES_LIST");
			writer.newLine();
			writer.write("{");
		}
		catch (IOException e) {
			System.err.println("Write error!");
		}

		for (Sale sale: sales) {
			
			try	{
				writer.newLine();
            	writer.write("\t"+"SALE");
            	writer.newLine();
            	writer.write("\t"+"{");
            	writer.newLine();
            	writer.write("\t"+"\t"+"ITEM_TYPE " + sale.getItem().getType());
            	writer.newLine();
            	writer.write("\t"+"\t"+"SALES_NUMBER "+ sale.getNumber());
            	writer.newLine();
            	writer.write("\t"+"\t"+"NAME "+ sale.getName());
            	writer.newLine();
            	writer.write("\t"+"\t"+"MANUFACTURER "+ sale.getItem().getManu());
            	writer.newLine();
            	writer.write("\t"+"\t"+"CATEGORY "+ sale.getItem().getCategory());
            	writer.newLine();
            	writer.write("\t"+"\t"+"PRICE " + sale.getCost());
            	writer.newLine();
            	writer.write("\t"+"\t"+"MODEL " + sale.getItem().getName());
            	writer.newLine();
            	writer.write("\t"+"\t"+"PHONENUMBER " + sale.getPhone());
            	writer.newLine();
            	writer.write("\t"+"\t"+"DATE " + sale.getDate());	
            	
              
			}
		
			catch (IOException e) {
				System.err.println("Write error!");
			}
			
			try	{
				writer.newLine();
				writer.write("\t"+"}");
			}
			catch (IOException e) {
				System.err.println("Write error!");
			}
		}
		try {
			
			writer.newLine();
			writer.write("}");
			writer.close();

		}
		catch (IOException e) {
			System.err.println("Error closing file.");
		}
	}
	
	
	//Save items array list in the file, in case we sold an item, available copies of this item is -1 so we save this change.
	public void saveItems(String file_name){
		File f = null;
		BufferedWriter writer = null;

		try	{
			f = new File(file_name);
		}
		catch (NullPointerException e) {
			System.err.println ("File not found.");
		}

		try	{
			writer = new BufferedWriter(new OutputStreamWriter
				(new FileOutputStream(f)));
		}
		catch (FileNotFoundException e) {
			System.err.println("Error opening file for writing!");
		}
		
		try	{
			writer.write("ITEM_LIST");
			writer.newLine();
			writer.write("{");
		}
		catch (IOException e) {
			System.err.println("Write error!");
		}

		for (Item item: items) {
			
			try	{
				writer.newLine();
            	writer.write("\t"+"ITEM");
            	writer.newLine();
            	writer.write("\t"+"{");
            	writer.newLine();
            	writer.write("\t"+"\t"+"ITEM_TYPE " + item.getType());
	        	writer.newLine();
	        	writer.write("\t"+"\t"+"PRICE " + item.getPrice());
	        	writer.newLine();
	        	writer.write("\t"+"\t"+"CATEGORY " + item.getCategory());
	        	writer.newLine();
	        	writer.write("\t"+"\t"+"MODEL " + item.getName());
	        	writer.newLine();
	        	writer.write("\t"+"\t"+"MANUFACTURER " + item.getManu());
	        	writer.newLine();
	        	writer.write("\t"+"\t"+"MODEL_YEAR " + item.getYear());
	        	writer.newLine();
	        	writer.write("\t"+"\t"+"PIECES " + item.getNumAvailable());
	        	writer.newLine();
	        	writer.write("\t"+"\t"+"CODE " + item.getCode());
            	if(item instanceof TV){
            		writer.newLine();
    	        	writer.write("\t"+"\t"+"DIMENSIONS " + ((TV) item).getInches());
    	        	writer.newLine();
    	        	writer.write("\t"+"\t"+"PANEL_TYPE " + ((TV) item).getType());
    	        	writer.newLine();
    	        	writer.write("\t"+"\t"+"RESOLUTION " + ((TV) item).getRes());
    	        	writer.newLine();
    	        	writer.write("\t"+"\t"+"INTERFACES " + ((TV) item).getPort());		        	
            	}else if(item instanceof Camera){
            		writer.newLine();
    	        	writer.write("\t"+"\t"+"PANEL_TYPE " + ((Camera) item).getType());
    	        	writer.newLine();
    	        	writer.write("\t"+"\t"+"MEGAPIXELS " + ((Camera) item).getPixels());
    	        	writer.newLine();
    	        	writer.write("\t"+"\t"+"OPTICAL_ZOOM " + ((Camera) item).getOptiZoom());
    	        	writer.newLine();
    	        	writer.write("\t"+"\t"+"DIGITAL_ZOOM " + ((Camera) item).getDigiZoom());
    	        	writer.newLine();
    	        	writer.write("\t"+"\t"+"DIMENSIONS " + ((Camera) item).getInches());
            	}else if(item instanceof DVD_BR){
            		writer.newLine();
    	        	writer.write("\t"+"\t"+"PANEL_TYPE " + ((DVD_BR) item).getType());
    	        	writer.newLine();
    	        	writer.write("\t"+"\t"+"RESOLUTION " + ((DVD_BR) item).getRes());
    	        	writer.newLine();
    	        	writer.write("\t"+"\t"+"FORMAT " + ((DVD_BR) item).getFormat());   	        	
            	}else if(item instanceof Console){
            		writer.newLine();
    	        	writer.write("\t"+"\t"+"PANEL_TYPE " + ((Console) item).getType());
    	        	writer.newLine();
    	        	writer.write("\t"+"\t"+"CPU " + ((Console) item).getCPU());
    	        	writer.newLine();
    	        	writer.write("\t"+"\t"+"GRAPHICS " + ((Console) item).getGraphics());
    	        	writer.newLine();
    	        	writer.write("\t"+"\t"+"SOUND " + ((Console) item).getSound());
    	        	writer.newLine();
    	        	writer.write("\t"+"\t"+"DRIVE " + ((Console) item).getDrive());
            	}else if(item instanceof Fridge){
            		writer.newLine();
    	        	writer.write("\t"+"\t"+"PANEL_TYPE " + ((Fridge) item).getType());
    	        	writer.newLine();
    	        	writer.write("\t"+"\t"+"ENERGY_CLASS " + ((Fridge) item).getEclass());
    	        	writer.newLine();
    	        	writer.write("\t"+"\t"+"FRIDGE_CAPACITY " + ((Fridge) item).getFricapacity());
    	        	writer.newLine();
    	        	writer.write("\t"+"\t"+"FREEZER_CAPACITY " + ((Fridge) item).getFreecapacity());    	    	
            	}else if(item instanceof Washer){
            		writer.newLine();
    	        	writer.write("\t"+"\t"+"PANEL_TYPE " + ((Washer) item).getType());
    	        	writer.newLine();
    	        	writer.write("\t"+"\t"+"ENERGY_CLASS " + ((Washer) item).getEclass());
    	        	writer.newLine();
    	        	writer.write("\t"+"\t"+"CAPACITY " + ((Washer) item).getCapacity());
    	        	writer.newLine();
    	        	writer.write("\t"+"\t"+"SPEED " + ((Washer) item).getSpeed());    	    	
            	}
			}
		
			catch (IOException e) {
				System.err.println("Write error!");
			}
			
			try	{
				writer.newLine();
				writer.write("\t"+"}");
			}
			catch (IOException e) {
				System.err.println("Write error!");
			}
		}
		try {
			
			writer.newLine();
			writer.write("}");
			writer.close();

		}
		catch (IOException e) {
			System.err.println("Error closing file.");
		}
	}
	
	
	public ArrayList<Item> getItems(){
		return items;
	}
	
	public ArrayList<Order> getOrders(){
		return orders;
	}
	
	public ArrayList<Sale> getSales(){
		return sales;
	}
}