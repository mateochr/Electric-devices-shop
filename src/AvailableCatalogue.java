import java.util.ArrayList;

public class AvailableCatalogue {
	private ArrayList<Item> avail_devices = new ArrayList<Item>();//avail_devices is an arraylist full of Item items 
	
	//Constructor.
	public AvailableCatalogue(ArrayList<Item> avail_devices) {
			this.avail_devices = avail_devices;
	}

	//Add
	public void addItem(Item item){
		avail_devices.add(item);
	}
	
	//Getters
	public int getSize(){
		return avail_devices.size();
	}
	
	public String getType(int i){
		return avail_devices.get(i).getType();
	}
	
	public String getModelName(int i){
		return avail_devices.get(i).getName();
	}
	
	public String getCode(int i){
		return avail_devices.get(i).getCode();
	}
	
	//Print all the info of the item
	public String print(int i){
		return avail_devices.get(i).toString();
	}
	
	//Getter
	public int getNumAvailable(int i){
		return avail_devices.get(i).getNumAvailable();
	}
	
	//Setter, value of x is always -1 to decrease the number of the available copies of an item when someone buys one
	public void setNumAvailable(int i , int x){
		avail_devices.get(i).setNumAvailable(avail_devices.get(i).getNumAvailable() + x);
	}
	
	//Getters
	public String getCategory(int i){
		return avail_devices.get(i).getCategory();
	}
	
	public double getCost(int i){
		return avail_devices.get(i).getPrice();
	}
	
	
	public Item getItem(int i){
		return avail_devices.get(i);
	}
}