package velocity.parkinglot.pojo;

public class Vehicle {
     
	private String vehicleNumber;  //number plate
	private VehicleType type;
	private String color;
	 
	 public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public VehicleType getType() {
		return type;
	}

	public void setType(VehicleType type) {
		this.type = type;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	
	 
	public Vehicle(String type,String vehicleNumber, String color) {
		super();
		switch (type){
         case "CAR":
             this.type = VehicleType.CAR;
             break;
        }
		this.vehicleNumber = vehicleNumber;
		this.color = color;
	}
	
}
