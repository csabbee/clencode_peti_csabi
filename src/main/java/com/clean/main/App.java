package com.clean.main;

import com.clean.communication.TorpedoClient;
import com.clean.communication.TorpedoServer;
import com.clean.ship.ShipFileReader;
import com.clean.ship.ShipLocations;
import com.clean.shipgame.GameWithShips;
import com.clean.strategy.FirePositionStrategy;
import com.clean.strategy.XYGuessGenerator;
import com.clean.tablewithships.ShipImplementation;



public class App 
{
    public static void main( String[] args ) {
    	if (args.length != 3) {
    		throw new IllegalArgumentException("You need to specify a file, port or host:port and boardsize");
    	}
    	ShipFileReader shipFileReader = new ShipFileReader();
        ShipLocations shipLocations = new ShipLocations();
        ShipImplementation impl = new ShipImplementation();
        GameWithShips game = new GameWithShips();
        game.setImpl(impl);
        game.setFilename(args[0]);
        game.setShipFileReader(shipFileReader);
        game.setShipLocations(shipLocations);
    	if(args[1].contains(":")){
            TorpedoClient torpedoClient = new TorpedoClient(args[1]);
            game.setBoardSize(Integer.parseInt(args[2]));
            FirePositionStrategy gameStrategy = new FirePositionStrategy();
            XYGuessGenerator generator = new XYGuessGenerator(Integer.parseInt(args[2]));
            gameStrategy.setGenerator(generator);
            gameStrategy.initialise();
            torpedoClient.initClient(game, gameStrategy, args[2]);
    	} else {
            TorpedoServer torpedoServer = new TorpedoServer(args[1]);
            torpedoServer.initServer(game);

    	}
    }
}
