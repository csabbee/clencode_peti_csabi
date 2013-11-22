package com.clean.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.clean.communication.message.MessageHandler;
import com.clean.interfaces.GameStrategy;
import com.clean.shipgame.GameWithShips;
import com.clean.shipgame.Status;

public class TorpedoClient {

    private final int portNumber;
    private final String hostName;
    private MessageHandler messageHandler;
    
    public TorpedoClient(String string) {
        hostName = string.split(":")[0];
        portNumber = Integer.parseInt(string.split(":")[1]);
    }

    public void initClient(GameWithShips gameWithShips, GameStrategy gameStrategy, String boardSize){
    	try (
            Socket clientSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
          ) {
            out.println("greeting "+boardSize);
            out.println(gameStrategy.getTarget(Status.MISS));
            gameWithShips.initialise();
            TorpedoProtocol torpedoProtocol = new TorpedoProtocol(gameWithShips);
            messageHandler = new MessageHandler(out, in, gameStrategy, torpedoProtocol);
            messageHandler.run();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
    
    }
}
