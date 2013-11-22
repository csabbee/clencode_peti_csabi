package com.clean.communication.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.clean.communication.TorpedoProtocol;
import com.clean.interfaces.GameStrategy;

public class MessageHandler {

    PrintWriter out;
    BufferedReader in;
    GameStrategy gameStrategy;
    TorpedoProtocol torpedoProtocol;
    
    public MessageHandler(PrintWriter out, BufferedReader in, GameStrategy gameStrategy, TorpedoProtocol torpedoProtocol) {
        super();
        this.out = out;
        this.in = in;
        this.gameStrategy = gameStrategy;
        this.torpedoProtocol = torpedoProtocol;
    }
    
    public void run() {
        try {
            processMessages();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void processMessages() throws IOException {
        String inputLine;
        String outputLine;
        while((inputLine = in.readLine()) != null){
            if(inputLine.toLowerCase().equals("win")){
                System.out.format("Win!%n");
                System.out.format("Peter=%s %n", gameStrategy.getPeter());
                break;
            }
            if(isValidProtocolMessage(inputLine)) {
                outputLine = torpedoProtocol.processInput(inputLine);
                if(inputLine.toLowerCase().contains("fire")){
                    out.println(outputLine);
                    out.println(gameStrategy.nextTarget(inputLine));
                } else if(outputLine.equals("win")){
                    out.println(outputLine);
                    System.out.format("Defeat!%n");
                    break;
                }

            }
        }
    }
    
    private boolean isValidProtocolMessage(String inputLine) {
        return inputLine.toLowerCase().contains("fire") || inputLine.toLowerCase().contains("hit") ||
                inputLine.toLowerCase().contains("miss") || inputLine.toLowerCase().contains("sunk");
    }
}
