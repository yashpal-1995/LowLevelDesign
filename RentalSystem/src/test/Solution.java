package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


enum VehicleType {
    Sedan, Hatchback, SUV;
}

class Branch {

    private String branchName;
    List<Vehicle> branchVehicles = new ArrayList<>();
    Map<Enum, Integer> vehicleTypePrices = new HashMap<>();

    public Branch(String branchName) {
        this.branchName  = branchName;
        vehicleTypePrices.put(VehicleType.Sedan, 0);
        vehicleTypePrices.put(VehicleType.Hatchback, 0);
        vehicleTypePrices.put(VehicleType.SUV, 0);
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public List<Vehicle> getBranchVehicles() {
        return branchVehicles;
    }

    public void setBranchVehicles(List<Vehicle> branchVehicles) {
        this.branchVehicles = branchVehicles;
    }

    public Map<Enum, Integer> getVehicleTypePrices() {
        return vehicleTypePrices;
    }

    public void setVehicleTypePrices(Map<Enum, Integer> vehicleTypePrices) {
        this.vehicleTypePrices = vehicleTypePrices;
    }
}

class Singelton{
	private static Singelton obj;
	
	private Singelton(){};
	
	public static synchronized Singelton getInstance(){
		if(obj==null){
			obj = new Singelton();
		}
		return obj;
	}
}
interface Strategy {  // interface class

    public Vehicle selectVehicleByLowestPrice(ArrayList<Vehicle> vehicleList);
}


class PriceStrategy implements Strategy {

	  
	@Override
    public Vehicle selectVehicleByLowestPrice(ArrayList<Vehicle> vehicleList) {
          
		  Vehicle selectedVehicle  = null;
		  
		  int Min_Price = Integer.MAX_VALUE;
		  
		  System.out.println("list size: " + vehicleList.size());
		  for(Vehicle vehicle : vehicleList) {
              
			  int curr_price = vehicle.getPrice();
			  
			  if(curr_price < Min_Price){
				  selectedVehicle = vehicle;
				  Min_Price = curr_price;
			  }
          }
		  
		  return selectedVehicle;
    }
}

class Vehicle {

    private String vehicleId;  // number plate 
    private Enum vehicleType;
    private int price;
    private String branchName;
    private Date startTime;
    private Date endTime;
    private boolean allocated;

    // for simplicity putting the date in this format
    public Vehicle(String vehicleId, Enum vehicleType, int price, String branchName, Date start, Date end) {
        this.vehicleId = vehicleId;
        this.vehicleType = vehicleType;
        this.price = price;
        this.branchName = branchName;
        this.startTime = start;
        this.endTime = end;
    }

    
    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Enum getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Enum vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean isAllocated() {
        return allocated;
    }

    public void setAllocated(boolean allocated) {
        this.allocated = allocated;
    }
}

class TestMain{

    public static void main(String[] args) throws ParseException {

        VehicleManagementService service = new VehicleManagementService();

        service.addBranch("Vasanth Vihar");
        service.addBranch("Cyber City");
        service.allocatePrice("Vasanth Vihar", VehicleType.Sedan, 100);
        service.allocatePrice("Vasanth Vihar", VehicleType.Hatchback, 80);
        service.allocatePrice("Cyber City", VehicleType.Sedan, 200);
        service.allocatePrice("Cyber City", VehicleType.Hatchback, 50);
        service.addVehicle("DL 01 MR 9310", VehicleType.Sedan, "Vasanth Vihar");
        service.addVehicle("DL 01 MR 9311", VehicleType.Sedan, "Cyber City");
        service.addVehicle("DL 01 MR 9312", VehicleType.Hatchback, "Cyber City");
        service.bookVehicle(VehicleType.Sedan, "29-02-2022 10:00:00", "29-02-2022 13:00:00");  // 24 hour format
        service.bookVehicle(VehicleType.Sedan, "29-02-2022 14:00:00", "29-02-2022 15:00:00");
        service.bookVehicle(VehicleType.Sedan, "29-02-2022 14:00:00", "29-02-2022 15:00:00");
        service.bookVehicle(VehicleType.Sedan, "29-02-2022 14:00:00", "29-02-2022 15:00:00");
        
        service.bookVehicle(VehicleType.Sedan, "30-02-2022 02:00:00", "30-02-2022 03:00:00");
        
        service.viewVehicleInventory("29-02-2022 14:00:00","29-02-2022 15:00:00");
    }
}


class VehicleManagementService {

    Map<Enum, ArrayList<Vehicle>> vehicleTypeMap;
    Map<String, Branch> branchMap;
    SimpleDateFormat formatter;

    public VehicleManagementService() {
        vehicleTypeMap = new HashMap<>();
        branchMap = new HashMap<>();
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm a");
    }


	public void addBranch(String branchName) {

        if(branchMap.containsKey(branchName)) {
            System.out.println("Already a branch.");
        }else {
            branchMap.put(branchName, new Branch(branchName));
        }
    }

    public void allocatePrice(String branchName, Enum vehicleType, int price) {

        if(!branchMap.containsKey(branchName)) {
            System.out.println("Not a branch.");
        }else {

            branchMap.get(branchName).getVehicleTypePrices().put(vehicleType, price);
        }

    }

    public void addVehicle(String vehicleId, Enum vehicleType, String branchName) throws ParseException {

        Date startTime = formatter.parse("01-01-1901 00:00:00");
        Date endTime = formatter.parse("01-01-1901 00:00:00");

        if(!branchMap.containsKey(branchName)) {
            System.out.println("Not a branch");
        }else {

            int price = branchMap.get(branchName).getVehicleTypePrices().get(vehicleType);
            Vehicle branchVehicle = new Vehicle(vehicleId, vehicleType, price, branchName, startTime, endTime);

            if(!vehicleTypeMap.containsKey(vehicleType)){
            	ArrayList<Vehicle> list = new ArrayList<>();
                vehicleTypeMap.put(vehicleType, list);
            }

           // System.out.println("added " + branchVehicle.getVehicleId());
            vehicleTypeMap.get(vehicleType).add(branchVehicle);
            branchMap.get(branchName).getBranchVehicles().add(branchVehicle);
        }
    }

    public void bookVehicle(Enum vehicleType, String start, String end) throws ParseException {

        Date startTime = formatter.parse(start);
        Date endTime = formatter.parse(end);

        if(!vehicleTypeMap.containsKey(vehicleType)) {
            System.out.println("No vehicle available");
        }else {

           // List<Vehicle> inUseVehicles = new ArrayList<>();
            ArrayList<Vehicle> vehicleList = vehicleTypeMap.get(vehicleType);
        
            ArrayList<Vehicle> newVehicleList = new ArrayList<>();  // filtered list based on availability
        
  		    for(Vehicle vehicle : vehicleList) {
  			  if(vehicle.getEndTime().before(startTime) ){
  				 newVehicleList.add(vehicle);
  			  }
            }
  		    
            PriceStrategy priceStrategy = new PriceStrategy();
            
            Vehicle selectedVehicle = priceStrategy.selectVehicleByLowestPrice(newVehicleList);
            
            if(selectedVehicle!=null ) {
                setVehicle(selectedVehicle, startTime, endTime);
               // inUseVehicles.add(selectedVehicle);
                //vehicleAllocated = true;
                System.out.println("vehicle allocated " + selectedVehicle.getVehicleId() + " " + selectedVehicle.getVehicleType() + " " + selectedVehicle.getBranchName());
           
            }else if(selectedVehicle == null) {
                System.out.println("Vehicle did not get allocated " + vehicleType);
            }

            //vehicleList.addAll(inUseVehicles);
        }
    }
    
    public void viewVehicleInventory(String string, String string2) {
  		// TODO Auto-generated method stub
    	
    	for(Map.Entry<Enum, ArrayList<Vehicle>> entry : vehicleTypeMap.entrySet()){
    		
    		System.out.println(entry.getKey()+ " -->");
    		
    		System.out.println("VehicleID        Branch         Allocated");
    		for(Vehicle vehicle : entry.getValue()){
    			
    			System.out.println(vehicle.getVehicleId() + "   "+ vehicle.getBranchName() + "     "+ vehicle.isAllocated());
    		}
    	}
  		
  	}

    private void setVehicle(Vehicle vehicle, Date start, Date end) {
        vehicle.setStartTime(start);
        vehicle.setEndTime(end);
        vehicle.setAllocated(true);
    }

    public Map<Enum, ArrayList<Vehicle>> getVehicleTypeMap() {
        return vehicleTypeMap;
    }

    public void setVehicleTypeMap(Map<Enum, ArrayList<Vehicle>> vehicleTypeMap) {
        this.vehicleTypeMap = vehicleTypeMap;
    }

    public Map<String, Branch> getBranchMap() {
        return branchMap;
    }

    public void setBranchMap(Map<String, Branch> branchMap) {
        this.branchMap = branchMap;
    }
}
