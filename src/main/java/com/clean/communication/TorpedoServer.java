package com.clean.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.clean.communication.message.MessageHandler;
import com.clean.shipgame.GameWithShips;
import com.clean.strategy.FirePositionStrategy;
import com.clean.strategy.XYGuessGenerator;

public class TorpedoServer {

    private final int portNumber;
    private MessageHandler messageHandler;
    public TorpedoServer(String string) {
        portNumber = Integer.parseInt(string);
    }

    public void initServer(GameWithShips gameWithShips){
        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                ) {
            String inputLine;
            inputLine = waitForGreeting(gameWithShips, in);
            XYGuessGenerator generator = new XYGuessGenerator(Integer.parseInt(inputLine.split(" ")[1]));
            FirePositionStrategy gameStrategy = new FirePositionStrategy();
            gameStrategy.setGenerator(generator);
            gameStrategy.initialise();
            TorpedoProtocol torpedoProtocol = new TorpedoProtocol(gameWithShips);
            messageHandler = new MessageHandler(out, in, gameStrategy, torpedoProtocol);
            messageHandler.startProcessingMessages();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String waitForGreeting(GameWithShips gameWithShips, BufferedReader in) throws IOException {
        String inputLine;
        boolean greetingHappened = false;
        while(((inputLine = in.readLine()) != null) && !greetingHappened){
            if(inputLine.contains("greeting")){
                System.out.format("Greeeting happened%n");
                gameWithShips.setBoardSize(Integer.parseInt(inputLine.split(" ")[1]));
                gameWithShips.initialise();
                greetingHappened = true;
            }
        }
        return inputLine;
    }
}
