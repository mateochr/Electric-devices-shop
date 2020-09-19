public class DVD_BR extends Item {
	private String dvd_brtype,resolution , format;

	public DVD_BR(){
		super();
	}
	
	//Constructor
	public DVD_BR(int numAvailable, String code, String name, int year, String manufacturer, double price, String category, String type, String dvd_brtype, String resolution, String format){
		super(numAvailable, code , name , year , manufacturer , price, category, type);
		this.dvd_brtype = dvd_brtype;
		this.resolution = resolution;
		this.format = format;
	}
	
	//Getters
	public String getDVD_BRtype(){
		return dvd_brtype;
	}
	
	public String getRes(){
		return resolution;
	}
	
	public String getFormat(){
		return format;
	}
	
	//Setters
	public void setDVD_BRtype(String newDVD_BRtype){
		dvd_brtype = newDVD_BRtype;
	}
	
	public void setRes(String newRes){
		resolution = newRes;
	}
	
	public void setFormat(String newFormat){
		format = newFormat;
	}
	
	//Returns dvd's/blue ray's info in a string
	public String toString(){
		return super.toString()
				+ "The type is: " + dvd_brtype + "<br>"
				+ "The resolution is: " + resolution + "<br>"
				+ "The format is: " + format;
	}
}