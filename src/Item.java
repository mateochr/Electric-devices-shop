public class Item {
	private int numAvailable;
	private String code;
	private String name;
	private int year;
	private String manufacturer;
	private double price;
	private String category;
	private String type;
	
	public Item(){
	}
	//Constructor
	public Item(int numAvailable, String code, String name, int year, String manufacturer, double price, String category, String type){
		this.numAvailable = numAvailable;
		this.code = code;
		this.name = name;
		this.year = year;
		this.manufacturer = manufacturer;
		this.price = price;
		this.category = category;
		this.type = type;
	}
	
	//getters
	public int getNumAvailable(){
		return numAvailable;
	}
	
	public String getCategory(){
		return category;
	}
	
	public String getCode(){
		return code;
	}
	
	public String getName(){
		return name;
	}
	
	public double getPrice(){
		return price;
	}
	
	public String getType(){
		return type;
	}
	
	public String getManu(){
		return manufacturer;
	}
	
	public int getYear(){
		return year;
	}
	
	//Setters
	public void setType(String newType){
		type = newType;
	}
	
	public void setCategory(String newCategory){
		category = newCategory;
	}
	
	public void setCode(String newCode){
		code = newCode;
	}
	
	public void setManu(String newManu){
		manufacturer = newManu;
	}
	
	public void setName(String newName){
		name = newName;
	}
	
	public void setPrice(double newPrice){
		price = newPrice;
	}
	
	public void setYear(int newYear){
		year = newYear;
	}
	
	public void setNumAvailable(int newNumAvailable){
		numAvailable = newNumAvailable;
	}
	
	
	//Returns item's info in as string
	public String toString(){
		return "<html>" + "The product's code is: " + code + "<br>"
				+ "The product's name is: " + name + "<br>"
				+ "The model's year is: " + year + "<br>"
				+ "The manufacturer is: " + manufacturer + "<br>"
				+ "The price is: " + price + "<br>"
				+ "The product's category is: " + category + "<br>"
				+ "There are " + numAvailable + " available products" + "<br>"
				+ "The product's type is: " + type + "<br>";		
	}
}