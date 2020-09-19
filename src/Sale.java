import java.util.Date;

public class Sale extends Business {
	
	//Empty constructor.
	public Sale(){
		super();
	}
	
	//Constructor
	public Sale(int number, Item device, String fullname, String phonenumber, Date date, double cost){
		super(number, device, fullname, phonenumber, date, cost);
	}
	
	//Returns sale's info in a string
	public String toString(){
		return  "<html>" + "The sale's number is: "+super.getNumber() + "<br>"
		+ super.toString()
		+ "The date the sale was made is: " + super.getDate() + "</html>";
	}
}