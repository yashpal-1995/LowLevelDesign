package velocity.parkinglot.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import velocity.parkinglot.util.ParkingLotUtil;

public class ParkingLotApplication {
    
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
        
	   // create automated ticketing system
	  // without human intervention
		
	  String filePath = "C:\\Users\\Yash\\Documents\\file_inputs.txt";
	  /*
	   Scanner sc = new Scanner (System.in);

      while (sc.hasNext() ) {
        String s1 = sc.next();
        System.out.println(s1);
        if(s1.equals("exit")) {
        break;
        }
      }
	   */
	  BufferedReader br = new BufferedReader(new FileReader(filePath));
	  
	  try{
		  
	    String line;
	    
	    while((line=br.readLine())!= null){
	    	
	    	System.out.println(line);
	    	
	    	String expression = line;
	    	
	    	if(line.startsWith("create_parking_lot")){
	    		
	    		String total_slots = line.split("\\s+")[1];
	    		
	    		ParkingLotUtil.createParkingLot(Integer.parseInt(total_slots));
	    		
	    	}else if(line.startsWith("park")){
	    		
	    		String registrationNumber  = line.split("\\s+")[1];
	    		String vehicleColor = line.split("\\s+")[2];
	    		
	    		ParkingLotUtil.parkVehicle(registrationNumber,vehicleColor);
	    		
	    	}else if(line.startsWith("leave")){
	    		
	    		String vacate_slot_number = line.split("\\s+")[1];
	    		
	    		ParkingLotUtil.makeParkingSlotAvailable(Integer.parseInt(vacate_slot_number));
	    		
	    	}else if(line.startsWith("status")){
	    		
	    		ParkingLotUtil.getVehicleStatus();
	    		
	    	}else if(line.startsWith("registration_numbers_for_cars_with_colour")){
	    		
	    		String color = line.split("\\s+")[1];
	    		ParkingLotUtil.searchRegistrationNumberByColor(color);
	    		
	    	}else if(line.startsWith("slot_numbers_for_cars_with_colour")){
	    		
	    		String color = line.split("\\s+")[1];
	    		ParkingLotUtil.searchSlotNumbersByColor(color);
	  	
	    	}else if(line.startsWith("slot_number_for_registration_number")){
	    		
	    		String registrationNumber  = line.split("\\s+")[1];
	    		ParkingLotUtil.searchSlotNumberByRegistrationNumber(registrationNumber);
	    		
	    	}else {
	    		System.out.println("Command Not Found !!");
	    	}
	
	    	
	    }
	  
	  }catch(Exception e){
		  e.printStackTrace();
	  }finally{
		  try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  
	}

}
