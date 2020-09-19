import java.util.ArrayList;
import java.util.Date;

public class OrderedCatalogue {
	private ArrayList<Order> ord_devices = new ArrayList<Order>();//ord_devices is an arraylist full of Order items
	
	//Constructor.
	public OrderedCatalogue(ArrayList<Order> ord_devices) {
		this.ord_devices = ord_devices;
	}
	
	//Add an order
	public void addOrder(Order x){
		ord_devices.add(x);
	}
	
	//Getter
	public int getnumberOfOrders(){
		return ord_devices.size();
	}
	
	//Print all the info of the order
	public String printOrder(int orderNumber){
		return ord_devices.get(orderNumber).toString();
	}
	
	//Sets the status of the order. false is not executed, true is executed
	public void setStatus(int orderNumber, boolean status){
		ord_devices.get(orderNumber).setStatus(status);
	}
	
	//Getters
	public String getPhone(int orderNumber){
		return ord_devices.get(orderNumber).getPhone();
	}
	
	public Item getItem(int orderNumber){
		return ord_devices.get(orderNumber).getItem();
	}
	
	public Date getArrivalDate(int orderNumber){
		return ord_devices.get(orderNumber).getArrivalDate();
	}
	
	public double getCost(int orderNumber){
		return ord_devices.get(orderNumber).getCost();
	}
	
	public int getNumber(int orderNumber){
		return ord_devices.get(orderNumber).getNumber();
	}
	
	public String getOrderName(int orderNumber){
		return ord_devices.get(orderNumber).getModelName();
	}
	
	public String getName(int orderNumber){
		return ord_devices.get(orderNumber).getName();
	}
	
	public boolean getStatus(int orderNumber){
		return ord_devices.get(orderNumber).getStatus();
	}
}