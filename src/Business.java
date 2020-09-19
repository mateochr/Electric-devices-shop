import java.util.Date;

public class Business {
	private int number;
	private Item device;
	private String fullname,phonenumber;
	private Date date;
	private double cost;
	
	//Empty constructor.
	public Business(){
		
	}
	
	//Constructor
	public Business(int number, Item device, String fullname, String phonenumber, Date date, double cost){
		this.number = number;
		this.device = device;
		this.fullname = fullname;
		this.phonenumber = phonenumber;
		this.date = date;
		this.cost = cost;
	}
	
	//Getters
	public int getNumber(){
		return number;
	}
	public String getName(){
		return fullname;
	}
	
	public String getPhone(){
		return phonenumber;
	}
	
	public Item getItem(){
		return device;
	}
	
	public Date getDate(){
		return date;
	}
	
	public double getCost(){
		return cost;
	}
	
	public String getModelName(){
		return device.getName();
	}
	
	
	//Returns order's/sale's info in a string
	public String toString(){
		return  "The device is: " + device.getName() + "<br>"
				+ "The device's category is: " + device.getCategory() + "<br>"
				+ "The device's manufacturer is: " + device.getManu() + "<br>"
				+ "The device's type is: " + device.getType() + "<br>"
				+ "The person's full name is: " + fullname + "<br>"
				+ "The person's phone number is: " + phonenumber + "<br>"
				+ "The cost with the discount is: " + cost + "<br>";
	}
}