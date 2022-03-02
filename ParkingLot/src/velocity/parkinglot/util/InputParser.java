package velocity.parkinglot.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import velocity.parkinglot.pojo.Vehicle;

public class InputParser {
    
	//static String command;
	public static void parse(ParkingLot parkingLot,String line) {
		// TODO Auto-generated method stub
		
		//System.out.println(line);
    	 if(line.startsWith("park")){
    		
    		String registrationNumber  = line.split("\\s+")[1];
    		//verifyPlateNumber(registrationNumber);
    		String vehicleColor = line.split("\\s+")[2];
    		
    		Vehicle vehicle = new Vehicle("CAR",registrationNumber,vehicleColor);
    		ParkingLot.parkVehicle(vehicle);
    		
    	}else if(line.startsWith("leave")){
    		
    		String vacate_slot_number = line.split("\\s+")[1];
    		
    		ParkingLot.vacateVehicle(Integer.parseInt(vacate_slot_number));
    		
    	}else if(line.startsWith("status")){
    		
    		ParkingLot.displayVehicleStatus();
    		
    	}else if(line.startsWith("registration_numbers_for_cars_with_colour")){
    		
    		String color = line.split("\\s+")[1];
    		ParkingLot.searchRegistrationNumberByColor(color);
    		
    	}else if(line.startsWith("slot_numbers_for_cars_with_colour")){
    		
    		String color = line.split("\\s+")[1];
    		ParkingLot.searchSlotNumbersByColor(color);
  	
    	}else if(line.startsWith("slot_number_for_registration_number")){
    		
    		String registrationNumber  = line.split("\\s+")[1];
    		ParkingLot.searchSlotNumberByRegistrationNumber(registrationNumber);
    		
    	}else {
    		System.out.println("Command Not Found !!");
    	}
	}

	private static boolean verifyPlateNumber(String vehicleNumber) {
		// TODO Auto-generated method stub
		Matcher m = Pattern.compile("^[A-Z]{2}[ -][0-9]{1,2}(?: [A-Z])?(?: [A-Z]*)? [0-9]{4}$").matcher(vehicleNumber);
		
		if (m.find()) {
		    System.out.println(vehicleNumber + " is a valid number plate");
		    return true;
		} 
		System.out.println(vehicleNumber + " is NOT a valid number plate");
		return false;
	}

}
