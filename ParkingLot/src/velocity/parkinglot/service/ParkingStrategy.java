package velocity.parkinglot.service;

import java.util.ArrayList;

import velocity.parkinglot.pojo.ParkingSlot;
import velocity.parkinglot.pojo.Vehicle;

public interface ParkingStrategy {
    
	 ParkingSlot getAvailableSpot(ArrayList<ParkingSlot> parkingSlots,Vehicle vehicle);
}
