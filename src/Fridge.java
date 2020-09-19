public class Fridge extends Item {
	private String Fridgetype,eclass,fricapacity,freecapacity;
	
	public Fridge(){
		super();
	}
	
	//Constructor
	public Fridge(int numAvailable, String code, String name, int year, String manufacturer, double price, String category, String type, String Fridgetype, String eclass, String fricapacity, String freecapacity){
		super(numAvailable, code , name , year , manufacturer , price, category, type);
		this.Fridgetype = Fridgetype;
		this.eclass = eclass;
		this.fricapacity = fricapacity;
		this.freecapacity = freecapacity;
	}
	
	//Setters
	public void setFridgetype(String newFridgetype){
		Fridgetype = newFridgetype;
	}
	
	public void setEclass(String newEclass){
		eclass = newEclass;
	}
	
	public void setFricapacity(String newFricapacity){
		fricapacity = newFricapacity;
	}
	
	public void setFreecapacity(String newFreecapacity){
		freecapacity = newFreecapacity;
	}

	//Getters
	public String getFridgetype(){
		return Fridgetype;
	}
	
	public String getEclass(){
		return eclass;
	}
	
	public String getFricapacity(){
		return fricapacity;
	}
	
	public String getFreecapacity(){
		return freecapacity;
	}
	
	//Returns fridge's info in a string
	public String toString(){
		return super.toString()
				+ "The fridge's type is: " + Fridgetype + "<br>"
				+ "The energy class is: " + eclass + "<br>"
				+ "The fridge's capacity is: " + fricapacity + "<br>"
				+ "The freezer's capacity is: " + freecapacity;
	}
}