package velocity.parkinglot.util;

import java.util.ArrayList;
import java.util.HashMap;

import velocity.parkinglot.exceptions.IllegitimateVehicleException;
import velocity.parkinglot.exceptions.SpotNotAvailableException;
import velocity.parkinglot.pojo.ParkingSlot;
import velocity.parkinglot.pojo.Registration;
import velocity.parkinglot.pojo.Vehicle;
import velocity.parkinglot.pojo.VehicleRegistration;
import velocity.parkinglot.service.NearestAvailableSlotStrategy;
import velocity.parkinglot.service.ParkingStrategy;
import velocity.parkinglot.service.VehicleValidationStrategy;

public class ParkingLot {
     
    private final Integer totalSlots;
    
    public static ArrayList<ParkingSlot> parkingSlots = new ArrayList<ParkingSlot>();
    
    private static HashMap<Integer,VehicleRegistration> occupiedParkingSlots = new HashMap<Integer,VehicleRegistration>();
    
	private static ParkingStrategy parkingStrategy;
	private VehicleValidationStrategy vehicleValidationStrategy;
	
	public ParkingLot(int totalSlots){
		this.totalSlots = totalSlots;
		parkingSlots = new ArrayList<>();
		parkingStrategy = new NearestAvailableSlotStrategy();
	}
	
	
	public void initialseParkingLot() {
		// TODO Auto-generated method stub
		
		for(int slotNum =1; slotNum<= totalSlots; slotNum++){
			
			ParkingSlot parkingslot = new ParkingSlot(slotNum,true);
			parkingSlots.add(parkingslot);
			
		}
		
		System.out.println("Created a parking lot with "+totalSlots+" slots");
		
	}



	public static void parkVehicle(Vehicle vehicle) {
		// TODO Auto-generated method stub
		
		ParkingSlot parkingSpot = parkingStrategy.getAvailableSpot(parkingSlots,vehicle);
		
		if(parkingSpot == null){
			System.out.println("Sorry, parking lot is full");
			return;
		}
		
		VehicleRegistration registration  =  new VehicleRegistration(vehicle.getVehicleNumber(),vehicle.getColor());
		
		occupiedParkingSlots.put(parkingSpot.getId(), registration);
		
		parkingSlots.get(parkingSpot.getId()-1).setAvailable(false);  // marking occupied spot as not available
		
		System.out.println("Allocated slot number: "+parkingSpot.getId());
		
	}



	public static void vacateVehicle(int SlotNum) {
		// TODO Auto-generated method stub
		if(!occupiedParkingSlots.containsKey(SlotNum)){
			System.out.println("Invalid Ticket");
			return;
		}
		
		parkingSlots.get(SlotNum-1).setAvailable(true);
		occupiedParkingSlots.remove(SlotNum);
		System.out.println("Slot number "+SlotNum+" is free");
		
	}


	public static void displayVehicleStatus() {
		// TODO Auto-generated method stub
		
		if(occupiedParkingSlots.isEmpty()){
			System.out.println("Parking lot is Empty");
			return;
		}
		
		System.out.println("Slot No.   Registration No   Colour");
		
		for(HashMap.Entry<Integer,VehicleRegistration> occupiedParkingSlot: occupiedParkingSlots.entrySet()){
			
			Integer slotNumber  = occupiedParkingSlot.getKey();
			
			VehicleRegistration registration = occupiedParkingSlot.getValue();
			
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
		
        for(HashMap.Entry<Integer,VehicleRegistration> occupiedParkingSlot: occupiedParkingSlots.entrySet()){
			
			Integer slotNumber  = occupiedParkingSlot.getKey();
			
			VehicleRegistration registration = occupiedParkingSlot.getValue();
			
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
		
        for(HashMap.Entry<Integer,VehicleRegistration> occupiedParkingSlot: occupiedParkingSlots.entrySet()){
			
			Integer slotNumber  = occupiedParkingSlot.getKey();
			
			VehicleRegistration registration = occupiedParkingSlot.getValue();
			
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
		
        for(HashMap.Entry<Integer,VehicleRegistration> occupiedParkingSlot: occupiedParkingSlots.entrySet()){
			
			Integer slotNumber  = occupiedParkingSlot.getKey();
			
			VehicleRegistration registration = occupiedParkingSlot.getValue();
			
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
