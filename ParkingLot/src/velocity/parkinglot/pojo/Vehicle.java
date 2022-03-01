package velocity.parkinglot.pojo;

public abstract class Vehicle {
        
	 private String vehicleNumber;  //number plate
	 private VehicleType type;
	 private String color;
	 
	 public Vehicle(String vehicleNumber, VehicleType type, String color) {
		super();
		this.vehicleNumber = vehicleNumber;
		this.type = type;
		this.color = color;
	}
	
}
