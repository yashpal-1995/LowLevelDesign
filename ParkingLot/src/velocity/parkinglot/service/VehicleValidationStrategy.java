package velocity.parkinglot.service;

import velocity.parkinglot.exceptions.IllegitimateVehicleException;
import velocity.parkinglot.pojo.Vehicle;

public interface VehicleValidationStrategy {
     
	public void validate(Vehicle vehicle) throws IllegitimateVehicleException;
}
