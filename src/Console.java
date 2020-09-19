public class Console extends Item {
	private String Consoletype,CPU,graphics,sound,drive;
	
	public Console(){
		super();
	}
	
	//Constructor
	public Console(int numAvailable, String code, String name, int year, String manufacturer, double price, String category, String type, String Consoletype, String CPU, String graphics, String sound, String drive){
		super(numAvailable, code , name , year , manufacturer , price, category, type);
		this.Consoletype = Consoletype;
		this.CPU = CPU;
		this.graphics = graphics;
		this.sound = sound;
		this.drive = drive;
	}
	
	//Setters
	public void setConsoleType(String newConsoletype){
		Consoletype = newConsoletype;
	}
	
	public void setCPU(String newCPU){
		CPU = newCPU;
	}
	
	public void setGraphics(String newGraphics){
		graphics = newGraphics;
	}
	
	public void setSound(String newSound){
		sound = newSound;
	}
	
	public void setDrive(String newDrive){
		drive = newDrive;
	}
	
	//Getters
	public String getConsoletype(){
		return Consoletype;
	}
	
	public String getCPU(){
		return CPU;
	}
	
	public String getGraphics(){
		return graphics;
	}
	
	public String getSound(){
		return sound;
	}
	
	public String getDrive(){
		return drive;
	}
	
	//Returns console's info in a string
	public String toString(){
		return super.toString()
				+ "The console's type is: " + Consoletype + "<br>"
				+ "The CPU is: " + CPU + "<br>"
				+ "The graphics are: " + graphics + "<br>"
				+ "The audio is: " + sound + "<br>"
				+ "The hard drive is: " + drive;
	}
}