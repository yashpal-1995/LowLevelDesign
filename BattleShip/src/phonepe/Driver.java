package phonepe;

import java.lang.reflect.Array;
import java.util.*;


class Position {
	
	int xCordinate;
    int yCordinate;

    public Position(int x, int y) {
        this.xCordinate = x;
        this.yCordinate = y;
    }
    
    public int getxCordinate() {
		return xCordinate;
	}

	public void setxCordinate(int xCordinate) {
		this.xCordinate = xCordinate;
	}

	public int getyCordinate() {
		return yCordinate;
	}

	public void setyCordinate(int yCordinate) {
		this.yCordinate = yCordinate;
	}

	
}
// https://app.codesignal.com/live-interview/cGWCaPAHm8ki2PJX8?userRole=candidate
interface Strategy{
	
	public Position getCoordinateByRandomStrategy(int size);
}

class RandomFireStrategy implements Strategy{
    
	@Override
	public Position getCoordinateByRandomStrategy(int size) {
		// TODO Auto-generated method stub
		  Random ran = new Random();
		  
		  int randomX = ran.nextInt(size);  // gives random number between 0 and (size-1) inclusive
	      int randomY = ran.nextInt(size);    // half region 
	      
	      Position position  = new Position(randomX,randomY);
	      
	      // also return unique postion always , so maintian set<postion> check if pistion already there
	      
		  return position;
	}
	
}
class CustomException extends Exception {

    public CustomException(String message) {
        super(message);
    }
}

class Driver {
    public static void main(String[] args) throws CustomException {

        BattleField battleField = new BattleField(6);    // initGame
        battleField.addShip("SH1", 2, 1,5,4,4);
        battleField.addShip("SH2", 1, 3,5,5,1);
        battleField.startGame();
        
        // overlapping check was missing 
    }
}


class BattleField {

    String grid[][];
    int size;
   // Random randomCordinate;
    Map<String, List<Position>> shipPositions;  //  { shipName, list<postion> } -> coordinated where this battle ship present
    
    Map<String,Boolean> playerStatus ; // it's for N players
    
    public BattleField(int size) {
        this.size = size;
        playerStatus = new HashMap<>();
        initialiseGrid();
       // randomCordinate = new Random();
        shipPositions = new HashMap<>();
      
    }

    private void initialiseGrid() {

        grid = new String[size][size];    // creating N*N grid 

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size ; j++) {
                grid[i][j] = "0";                    // initializing all the values with "0" -> Empty means no ship present here
            }
        }
        
        
        // can put for loop for N players
        playerStatus.put("A", true);  // player A
        playerStatus.put("B", true);
    }
     
    
    public void addShip(String name, int shipSize, int ship1x, int ship1y, int ship2x, int ship2y) throws CustomException {

        Position positionTopLeftShip1 = getTopLeftPoints(ship1x, ship1y, shipSize);   // top left corner ship 1
        
        Position positionTopLeftShip2 = getTopLeftPoints(ship2x, ship2y, shipSize);    // ship 2

        fillShipPosition(positionTopLeftShip1, shipSize, "A-"+name);                // fill position for ship 1
        fillShipPosition(positionTopLeftShip2, shipSize, "B-"+name);                // ship 2
    }

    private void fillShipPosition(Position positionTopLeftShip, int shipSize, String shipName) throws CustomException {

        List<Position> positions = new ArrayList<>();
        
        // fill up 2-D matrix
        for (int i = positionTopLeftShip.getxCordinate(); i < positionTopLeftShip.getxCordinate() + shipSize ; i++) {
        	
            for (int j = positionTopLeftShip.getyCordinate(); j < positionTopLeftShip.getyCordinate() + shipSize ; j++) {
            	
                if (isValid(i, j)) {                             // to check if it's allowed boundary
                    positions.add(new Position(i, j));
                    grid[i][j] = shipName;
                }else {
                    throw new CustomException("Not a valid ship location to fill");
                }
            }
        }

        shipPositions.put(shipName, positions);
    }

    private Position getTopLeftPoints(int x, int y, int padding) {

        int leftXCordinate = x - (padding/2);
        int leftYCordinate = y - (padding/2);
        Position leftPos = new Position(leftXCordinate, leftYCordinate);
        return leftPos;
    }

    public void startGame() {

       // int randomX = randomCordinate.nextInt(size);  // gives random number between 0 and (size-1) inclusive
      //  int randomY = randomCordinate.nextInt(size);
        
    	RandomFireStrategy  randomFireStrategy = new RandomFireStrategy();
    	
        Position randomPosition = randomFireStrategy.getCoordinateByRandomStrategy(size); // just to pass range for this method 
        
        while(true) {
        	/*
           destroyShip(randomPosition.getxCordinate(), randomPosition.getyCordinate(), "B");
           if(checkIfAllShipDestroyed("B")) return;
           destroyShip(randomPosition.getxCordinate(), randomPosition.getyCordinate(), "A");
          if(checkIfAllShipDestroyed("A")) return;
          */
          
        	System.out.print("PlayresA's Turn : Missile Fired at (3,0) : ");
            destroyShip(3, 0, "B");
           
            if(checkIfAllShipDestroyed("B")) break;
            
            
            System.out.print("PlayresB's Turn : Missile Fired at (1,1) : ");
            destroyShip(1, 1, "A");
            if(checkIfAllShipDestroyed("A")) break;
            
            System.out.print("PlayresA's Turn : Missile Fired at (5,3) : ");
            destroyShip(5, 3, "B");
            if(checkIfAllShipDestroyed("B")) break;
        }
        
        System.out.print("Game Over : ");
        for(Map.Entry<String, Boolean> entry : playerStatus.entrySet()){
        	if(entry.getValue()){
        		System.out.println("Player"+ entry.getKey() + " wins.");
        	}
        }
    }

    private void destroyShip(int randomX, int randomY, String enemyName) {

        Position leftMostOfOpponent = getTopLeftPoints(randomX, randomY, size);
        makeShipEmptyDuringFight(leftMostOfOpponent, size, enemyName);
    }

    private void makeShipEmptyDuringFight(Position leftMostOfOpponent, int padding, String enemyName) {

        boolean shipDestroyed = false;
        for (int i = leftMostOfOpponent.getxCordinate() ; i < leftMostOfOpponent.getxCordinate() + padding; i++) {   // check in sub-magtrix
            for (int j = leftMostOfOpponent.getyCordinate() ; j < leftMostOfOpponent.getyCordinate() + padding ; j++) {
            	
                if (isValid(i, j) && grid[i][j].startsWith(enemyName)) {
                	
                    shipDestroyed = true;
                    
                    System.out.println("wohoo Hit : " + grid[i][j]+ " ship destroyed!!!!!!!!!" );
                    killShipLocations(grid[i][j]);
                }
            }
        }

        if(shipDestroyed == false)
            System.out.println("its a misss!!!");
    }

    private void killShipLocations(String shipName) {
        List<Position> positions = shipPositions.get(shipName);

        for (int i = 0 ; i < positions.size() ; i++) {
            Position pos = positions.get(i);
            grid[pos.getxCordinate()][pos.getyCordinate()] = "0";
        }

        shipPositions.remove(shipName);

    }

    private boolean checkIfAllShipDestroyed(String enemyName) {

        if (shipPositions.keySet().stream().filter(x -> x.startsWith(enemyName)).count() == 0){
        	
        	playerStatus.put(enemyName, false);
        	return true;
        }
        return false;
    }

    private boolean isValid(int x, int y) {   // need to be checked later
        return (x < 0 || x >= size || y < 0 || y >= size) ? false: true;
    }



}