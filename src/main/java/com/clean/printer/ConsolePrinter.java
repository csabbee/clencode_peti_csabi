package com.clean.printer;

import com.clean.ship.ShipLocations;

public class ConsolePrinter {
	
	private ShipLocations shipLocations;
	private final int boardSize;
	
    public ConsolePrinter(ShipLocations shipLocations, int boardSize) {
        super();
        this.shipLocations = shipLocations;
        this.boardSize = boardSize;
    }
	
	private String converter(boolean value) {
		return value ? "X" : String.format("%c",'.');
	}	
	public void printBoard() {
		StringBuilder stringBuilder = new StringBuilder();
		for(int i=0; i<boardSize*2+2; i++){
		    stringBuilder.append("-");
		}
		stringBuilder.append("\n");
		for (int i = 0; i < boardSize; i++) {
		    stringBuilder.append("|");
			for (int j = 0; j < boardSize; j++) {
				stringBuilder.append(converter(shipLocations.checkPoint(j, boardSize-i))+" ");
			}
			stringBuilder.append("|");
			stringBuilder.append("\n");
		}
		for(int i=0; i<boardSize*2+2; i++){
            stringBuilder.append("-");
        }
		System.out.format("Table:%n%s%n", stringBuilder.toString());
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}
