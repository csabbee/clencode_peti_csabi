package com.clean.printer;

import org.junit.Before;

import com.clean.ship.ShipLocations;

public class TestConsolePrinter {

    private ConsolePrinter underTest;
    private ShipLocations shipLocations;
    private final int boardSize = 20;
    
    @Before
    public void setUp(){
        
        underTest = new ConsolePrinter(shipLocations, boardSize);
    }
}
