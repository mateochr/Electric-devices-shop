import java.util.ArrayList;

public class SoldCatalogue {
	private ArrayList<Sale> sold_devices = new ArrayList<Sale>();//sold_devices is an arraylist full of Sale items
	
	//Constructor.
	public SoldCatalogue(ArrayList<Sale> sold_devices) {
		this.sold_devices = sold_devices;
	}
	
	//Add a sale
	public void addSale(Sale x){
		sold_devices.add(x);
	}
	
	//Getter
	public int getnumberOfSales(){
		return sold_devices.size();
	}
	
	//Print all the info of the sale
	public String printSale(int saleNumber){
			return sold_devices.get(saleNumber).toString();
	}
	
	//Getters
	public int getNumber(int saleNumber){
		return sold_devices.get(saleNumber).getNumber();
	}
	
	public String getSaleName(int saleNumber){
		return sold_devices.get(saleNumber).getModelName();
	}
	
	public String getName(int saleNumber){
		return sold_devices.get(saleNumber).getName();
	}
}