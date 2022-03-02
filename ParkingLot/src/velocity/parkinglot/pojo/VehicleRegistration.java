package velocity.parkinglot.pojo;

public class VehicleRegistration {
    
	private String registrationNumber;  // Number plate
	private String vehicleColor;
	//private int slotNumber;  // assuming customer going to park in allocated slot
	//private String vehicleType;
	
	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getVehicleColor() {
		return vehicleColor;
	}

	public void setVehicleColor(String vehicleColor) {
		this.vehicleColor = vehicleColor;
	}

	
	public VehicleRegistration(String registrationNumber, String vehicleColor) {
		super();
		this.registrationNumber = registrationNumber;
		this.vehicleColor = vehicleColor;
	}
	

	
	
}
