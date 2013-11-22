package com.clean.communication.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.clean.communication.TorpedoProtocol;
import com.clean.interfaces.GameStrategy;
import com.clean.shipgame.Status;

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
                System.out.format("outputLine:%s %n", outputLine);
                System.out.format("inputLine:%s %n", inputLine);
                if(inputLine.toLowerCase().contains("fire")){
                    out.println(outputLine);
                } 
                if(outputLine.equals("win")){
                    out.println(outputLine);
                    System.out.format("Defeat!%n");
                    break;
                }
                if(isResponsToFire(inputLine)){
                    Status inputStatus = convert(inputLine);
                    String nextMove = gameStrategy.getTarget(inputStatus);
                    out.println(nextMove);
                }
            }
        }
    }

    private Status convert(String inputLine) {
        Status answer;
        if(inputLine.toLowerCase().equals("hit")){
            answer = Status.HIT;
        } else if(inputLine.toLowerCase().equals("miss")){
            answer = Status.MISS;
        } else {
            answer = Status.SUNK;
        }
        
        return answer;
    }

    private boolean isResponsToFire(String inputLine) {
        return inputLine.toLowerCase().contains("hit") ||
                inputLine.toLowerCase().contains("miss") || inputLine.toLowerCase().contains("sunk");
    }

    private boolean isValidProtocolMessage(String inputLine) {
        return inputLine.toLowerCase().contains("fire") || inputLine.toLowerCase().contains("hit") ||
                inputLine.toLowerCase().contains("miss") || inputLine.toLowerCase().contains("sunk");
    }
}
