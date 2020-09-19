public class Washer extends Item {
	private String eclass, capacity, speed;
	
	public Washer(){
		super();
	}
	
	//Constructor
	public Washer(int numAvailable, String code, String name, int year, String manufacturer, double price, String category, String type, String eclass, String capacity , String speed){
		super(numAvailable, code , name , year , manufacturer , price, category, type);
		this.eclass = eclass;
		this.capacity = capacity;
		this.speed = speed;
	}
	
	//Setters	
	public void setEclass(String newEclass){
		eclass = newEclass;
	}
	
	public void setCapacity(String newCapacity){
		capacity = newCapacity;
	}
	
	public void setSpeed(String newSpeed){
		speed = newSpeed;
	}

	//Getters
	
	public String getEclass(){
		return eclass;
	}
	
	public String getCapacity(){
		return capacity;
	}
	
	public String getSpeed(){
		return speed;
	}
	
	//Returns washer's info in a string
	public String toString(){
		return super.toString()
				+ "The energy class is: " + eclass + "<br>"
				+ "The capacity is: " + capacity + "<br>"
				+ "The speed is: " + speed;

	}
}