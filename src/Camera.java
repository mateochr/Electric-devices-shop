public class Camera extends Item {
	private String camType,optizoom,digizoom,inches;
	private double mpixel;
	
	public Camera(){
		super();
	}
	//Constructor
	public Camera(int numAvailable, String code, String name, int year, String manufacturer, double price, String category, String type, String camType, double mpixel, String optizoom, String digizoom, String inches){
		super(numAvailable, code , name , year , manufacturer , price, category, type);
		this.camType = camType;
		this.optizoom = optizoom;
		this.digizoom = digizoom;
		this.inches = inches;
		this.mpixel = mpixel;
	}
	
	//Setters
	public void setCamtype(String newCamtype){
		camType = newCamtype;
	}
	
	public void setOptiZoom(String newOptizoom){
		optizoom = newOptizoom;
	}
	
	public void setDigiZoom(String newDigizoom){
		digizoom = newDigizoom;
	}
	
	public void setPixels(double newPixels){
		mpixel = newPixels;
	}
	
	public void setInches(String newInches){
		inches = newInches;
	}
	
	//Getters
	public String getCamtype(){
		return camType;
	}
	
	public double getPixels(){
		return mpixel;
	}
	
	public String getOptiZoom(){
		return optizoom;
	}
	
	public String getDigiZoom(){
		return digizoom;
	}
	
	public String getInches(){
		return inches;
	}
	
	//Returns camera's info in a string
	public String toString(){
		return super.toString()
				+ "The camera's type is: " + camType + "<br>"
				+ "The inches are: " + inches + "<br>"
				+ "The optical zoom is: " + optizoom + "<br>"
				+ "The digital zoom is: " + digizoom + "<br>"
				+ "The megapixels are: " + mpixel;
	}
}