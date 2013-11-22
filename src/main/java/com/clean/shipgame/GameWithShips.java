package com.clean.shipgame;

import com.clean.interfaces.Torpedo;
import com.clean.printer.ConsolePrinter;
import com.clean.ship.ShipFileReader;
import com.clean.ship.ShipLocations;
import com.clean.tablewithships.ShipImplementation;

public class GameWithShips implements Torpedo {
	
	private ShipLocations shipLocations;
    private ShipImplementation impl;
	private int loseCondition = 0;
	private int numberOfTargets;
	private int boardSize;
	private String filename;
	private ShipFileReader shipFileReader;
	private ConsolePrinter printer;
	
    public Status fire(int x, int y) {
        Status status = Status.MISS;
        if(shipLocations.hit(x, y)){
            status = Status.HIT;
            loseCondition++;
            System.out.format("LoseCondition=%s %n", loseCondition);
            printer.printBoard();
            if(checkIfSunken()){
                status = Status.SUNK;
                System.out.format("SUNK!%n");
                if(loseCondition == numberOfTargets){
                    status = Status.WIN;
                }
            }
        }
    	return status;
    }
    
    public boolean checkIfSunken() {
		return shipLocations.checkSunken();
    }
    
    public void initialise() {
    	impl.setReader(shipFileReader);
    	impl.setFilename(filename);
    	impl.setShipLocations(shipLocations);
    	impl.setBoardSize(boardSize);
    	impl.initialisation();
    	impl.placeShips();
        printer = new ConsolePrinter(shipLocations, boardSize);
    	numberOfTargets = impl.getTotalNumberOfTargets();
    	System.out.format("numberOfTargets=%s %n", numberOfTargets);
    }

	public void setShipLocations(ShipLocations shipLocations) {
		this.shipLocations = shipLocations;
	}

	public void setImpl(ShipImplementation impl) {
		this.impl = impl;
	}

	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setShipFileReader(ShipFileReader shipFileReader) {
		this.shipFileReader = shipFileReader;
		
	}

}
