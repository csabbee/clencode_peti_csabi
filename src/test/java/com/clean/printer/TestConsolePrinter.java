package com.clean.printer;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.clean.ship.ShipLocations;

public class TestConsolePrinter {

    private ConsolePrinter underTest;
    private ShipLocations shipLocations;
    private final int boardSize = 20;
    
    @Before
    public void setUp(){
        shipLocations = EasyMock.createMock(ShipLocations.class);
        underTest = new ConsolePrinter(shipLocations, boardSize);
    }
    
    @Test
    public void testPrintBoardWhenThereIsNoMiss(){
        //GIVEN
        EasyMock.expect(shipLocations.checkPoint(EasyMock.anyInt(), EasyMock.anyInt())).andReturn(true).anyTimes();
        //WHEN
        EasyMock.replay(shipLocations);
        underTest.printBoard();
        //THEN
        EasyMock.verify(shipLocations);
    }
    
    @Test
    public void testPrintBoardWhenThereIsNoHit(){
        //GIVEN
        EasyMock.expect(shipLocations.checkPoint(EasyMock.anyInt(), EasyMock.anyInt())).andReturn(true).anyTimes();
        //WHEN
        EasyMock.replay(shipLocations);
        underTest.printBoard();
        //THEN
        EasyMock.verify(shipLocations);
    }
}
