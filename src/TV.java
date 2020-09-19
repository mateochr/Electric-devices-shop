public class TV extends Item {
	private String TVtype, inches , resolution , port;
	
	public TV(){
		super();
	}
	//Constructor
	public TV(int numAvailable, String code, String name, int year, String manufacturer, double price, String category, String type, String TVtype, String inches, String resolution, String port){
		super(numAvailable, code , name , year , manufacturer , price, category,type);
		this.TVtype = TVtype;
		this.inches = inches;
		this.resolution = resolution;
		this.port = port;
	}
	
	//Getters
	public String getTVtype(){
		return TVtype;
		
	}
	
	public String getInches(){
		return inches;
	}
	
	public String getRes(){
		return resolution;
	}
	
	public String getPort(){
		return port;
	}
	//Setters
	public void setTVtype(String newTVtype){
		TVtype = newTVtype;
	}
	
	public void setInches(String newInches){
		inches = newInches;
	}
	
	public void setRes(String newRes){
		resolution = newRes;
	}
	
	public void setPort(String newPort){
		port = newPort;
	}
	//Returns tv's info in a string
	public String toString(){
		return  super.toString() +
				"The TV's type is: " + TVtype + "<br>" +
				"The inches are: " + inches + "<br>" +
				"The resolution is: " + resolution + "<br>" +
				"The port is: " + port;
	}
	
	
}