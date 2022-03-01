package velocity.parkinglot.pojo;

public class ParkingSlot {
     
	private int id;
	private boolean Available;
	
	public ParkingSlot(int id, boolean available) {
		super();
		this.id = id;
		Available = available;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isAvailable() {
		return Available;
	}
	public void setAvailable(boolean available) {
		Available = available;
	}
	
	
}
