package velocity.parkinglot.service;

import java.util.ArrayList;

import velocity.parkinglot.pojo.ParkingSlot;
import velocity.parkinglot.pojo.Vehicle;
import velocity.parkinglot.util.ParkingLot;



public class NearestAvailableSlotStrategy implements ParkingStrategy {

    @Override
    public ParkingSlot getAvailableSpot(ArrayList<ParkingSlot> parkingSlots, Vehicle vehicle) {
    	
    	
       for(int i=0;i<parkingSlots.size();++i){
			
			if(parkingSlots.get(i).isAvailable()){
				return parkingSlots.get(i);
			}
		}
        return null;
    }
}