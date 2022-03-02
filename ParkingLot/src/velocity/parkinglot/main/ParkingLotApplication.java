package velocity.parkinglot.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

import velocity.parkinglot.util.InputParser;
import velocity.parkinglot.util.ParkingLot;

public class ParkingLotApplication {
    
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
        
	   // create automated ticketing system
	  // without human intervention
		  
		// 1) It should provide us with an interactive command prompt based shell where
		//    commands can be typed in
		if(args.length == 0){   // 
			
			Scanner sc = new Scanner (System.in).useDelimiter("\n");
            
			String command = sc.next();
			
			String total_slots = command.split("\\s+")[1];
			
		    ParkingLot parkingLot = new ParkingLot(Integer.parseInt(total_slots));
    		parkingLot.initialseParkingLot();
	    		
		      while (sc.hasNext() ) {
		    	  
		            command = sc.next();
		            InputParser.parse(parkingLot,command);
		        
			        if(command.startsWith("exit")) {
			          break;
			        }
		      }
		
	  
	    }else{
		    
	    	//2) It should accept a filename as a parameter at the command prompt and read the
	    	//   commands from that file
	    	
	    	
	    	String fileName = args[0];   // Assuming file is in same directory as the .jar file
			
			File jarFile = null;
			try {
				jarFile = new File(ParkingLotApplication.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        String filePath = jarFile.getParent() + File.separator + fileName;
			
	        
		    // String filePath = "C:\\Users\\Yash\\Documents\\file_inputs.txt";

		    BufferedReader br = new BufferedReader(new FileReader(filePath));
		  
		  
    		
			  try{
			    
			    String line  = br.readLine();
			    
			    String total_slots = line.split("\\s+")[1];
			    ParkingLot parkingLot = new ParkingLot(Integer.parseInt(total_slots));
	    		parkingLot.initialseParkingLot();
	    		
			    while((line=br.readLine())!= null){
			    	
			    	//System.out.println(line);
			    	
			    	InputParser.parse(parkingLot,line);
			    	    	
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

}
