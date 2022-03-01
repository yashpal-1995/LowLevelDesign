package velocity.parkinglot.util;

import java.util.ArrayList;
import java.util.HashMap;

import velocity.parkinglot.pojo.ParkingSlot;
import velocity.parkinglot.pojo.Registration;

public class ParkingLotUtil {
    
	static int total_parking_lot;
	static ArrayList<ParkingSlot> parkingSlots = new ArrayList<ParkingSlot>();
	
	static HashMap<Integer,Registration> occupiedParkingSlots = new HashMap<Integer,Registration>();
	
	public static void createParkingLot(int total_parking_lots) {
		// TODO Auto-generated method stub
		total_parking_lot = total_parking_lots;
		
		for(int i=1;i<=total_parking_lots;++i){
			ParkingSlot parkingslot = new ParkingSlot(i,true);
			parkingSlots.add(parkingslot);
		}
		
		System.out.println("Created a parking lot with "+total_parking_lot+" slots");
		
	}

	public static int getAvailableSlot() {
		// TODO Auto-generated method stub
		
		for(int i=0;i<parkingSlots.size();++i){
			
			if(parkingSlots.get(i).isAvailable()){
				return parkingSlots.get(i).getId();
			}
		}
		return -1;
	}

	public static void parkVehicle(String registrationNumber,
			String vehicleColor) {
		// TODO Auto-generated method stub
		
		int availableSlot = getAvailableSlot();
		
		if(availableSlot == -1){
			System.out.println("Sorry, parking lot is full");
			return;
		}
		
		Registration registration  =  new Registration(registrationNumber,vehicleColor);
		
		occupiedParkingSlots.put(availableSlot, registration);   // car registration info
		
		parkingSlots.get(availableSlot-1).setAvailable(false);  // marking occupied spot as not available
		
		System.out.println("Allocated slot number: "+availableSlot);
		
	}

	public static void makeParkingSlotAvailable(int vacate_slot_number) {
		// TODO Auto-generated method stub
		parkingSlots.get(vacate_slot_number-1).setAvailable(true);
		occupiedParkingSlots.remove(vacate_slot_number);
		System.out.println("Slot number "+vacate_slot_number+" is free");
	}

	public static void getVehicleStatus() {
		// TODO Auto-generated method stub
		if(occupiedParkingSlots.isEmpty()){
			System.out.println("Parking lot is Empty");
			return;
		}
		
		System.out.println("Slot No.   Registration No   Colour");
		
		for(HashMap.Entry<Integer,Registration> occupiedParkingSlot: occupiedParkingSlots.entrySet()){
			
			Integer slotNumber  = occupiedParkingSlot.getKey();
			
			Registration registration = occupiedParkingSlot.getValue();
			
			System.out.println(slotNumber+ "   "+registration.getRegistrationNumber()+"   "+registration.getVehicleColor());
		}
		
	}

	public static void searchRegistrationNumberByColor(String color) {
		// TODO Auto-generated method stub
		
		ArrayList<String> registrationList = new ArrayList<>();
		if(occupiedParkingSlots.isEmpty()){
			System.out.println("There is No Car with color "+color+ " in parking lot");
			return;
		}
		
        for(HashMap.Entry<Integer,Registration> occupiedParkingSlot: occupiedParkingSlots.entrySet()){
			
			Integer slotNumber  = occupiedParkingSlot.getKey();
			
			Registration registration = occupiedParkingSlot.getValue();
			
			if(registration.getVehicleColor().equalsIgnoreCase(color)){
				registrationList.add(registration.getRegistrationNumber());
			}
		}
        
        if(registrationList.size()==0){
        	System.out.println("There is No Car with color "+color+ " in parking lot");
			return;
        }
        
        for(int i=0;i<registrationList.size();i++){
        	System.out.print(registrationList.get(i));
        	if(i!=registrationList.size()-1){
        		System.out.print(", ");
        	}
        }
        System.out.println();
	}

	public static void searchSlotNumbersByColor(String color) {
		// TODO Auto-generated method stub
		
		ArrayList<Integer> registrationList = new ArrayList<>();
		if(occupiedParkingSlots.isEmpty()){
			System.out.println("There is No Car with color "+color+ " present in parking lot");
			return;
		}
		
        for(HashMap.Entry<Integer,Registration> occupiedParkingSlot: occupiedParkingSlots.entrySet()){
			
			Integer slotNumber  = occupiedParkingSlot.getKey();
			
			Registration registration = occupiedParkingSlot.getValue();
			
			if(registration.getVehicleColor().equalsIgnoreCase(color)){
				registrationList.add(slotNumber);
			}
		}
        
        if(registrationList.size()==0){
        	System.out.println("There is No Car with color "+color+ " present in parking lot");
			return;
        }
        
        for(int i=0;i<registrationList.size();i++){
        	System.out.print(registrationList.get(i));
        	if(i!=registrationList.size()-1){
        		System.out.print(", ");
        	}
        }
        System.out.println();
	}

	public static void searchSlotNumberByRegistrationNumber(
			String registrationNumber) {
		// TODO Auto-generated method stub
		
		Integer slot_number = Integer.MIN_VALUE;
		if(occupiedParkingSlots.isEmpty()){
			System.out.println("Not Found");
			return;
		}
		
        for(HashMap.Entry<Integer,Registration> occupiedParkingSlot: occupiedParkingSlots.entrySet()){
			
			Integer slotNumber  = occupiedParkingSlot.getKey();
			
			Registration registration = occupiedParkingSlot.getValue();
			
			if(registration.getRegistrationNumber().equalsIgnoreCase(registrationNumber)){
				slot_number = slotNumber;
				break;
			}
		}
        
        if(slot_number == Integer.MIN_VALUE){
        	System.out.println("Not Found");
			return;
        }
        
        System.out.println(slot_number);
        
	}
	
	
     
}
