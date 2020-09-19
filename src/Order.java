import java.util.Date;

public class Order extends Business {
	private Date arrivaldate;
	private boolean status;
	
	//Empty constructor.
	public Order(){
		super();
	}
	
	//Constructor
	public Order(int number, Item device, String fullname, String phonenumber,Date date, double cost, Date arrivaldate, boolean status){
		super(number, device, fullname, phonenumber, date, cost);
		this.arrivaldate = arrivaldate;
		this.status = status;
	}
	
	//Getters
	public Date getArrivalDate(){
		return arrivaldate;
	}
	
	public boolean getStatus(){
		return status;
	}
	
	//Setters
	public void setStatus(boolean newStatus){
		 status = newStatus;
	}
	
	//Returns order's info in a string
	public String toString(){
		if(status == false){
			return "<html>" + "The order's number is: "+ super.getNumber() + "<br>"
					+super.toString()
					+ "The date the order was made is: " + super.getDate() +"<br>"
					+ "The arrival date is: " + arrivaldate + "<br>"
					+ "The order's status is: NOT EXECUTED" + "</html>";
			
		}else{
			return "<html>" + "The order's number is: "+ super.getNumber() + "<br>"
					+super.toString()
					+ "The date the order was made is: " + super.getDate() + "<br>"
					+ "The arrival date is: " + arrivaldate + "<br>"
					+ "The order's status is: EXECUTED" + "</html>";
		}	
	}
}