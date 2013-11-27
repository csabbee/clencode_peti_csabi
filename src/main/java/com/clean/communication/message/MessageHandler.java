package com.clean.communication.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.clean.communication.TorpedoProtocol;
import com.clean.interfaces.GameStrategy;
import com.clean.shipgame.Status;

public class MessageHandler {

    
    private PrintWriter out;
    private BufferedReader in;
    private GameStrategy gameStrategy;
    private TorpedoProtocol torpedoProtocol;
    private boolean firstShot = true;
    private  String target;
    
    public MessageHandler(PrintWriter out, BufferedReader in, GameStrategy gameStrategy, TorpedoProtocol torpedoProtocol) {
        super();
        this.out = out;
        this.in = in;
        this.gameStrategy = gameStrategy;
        this.torpedoProtocol = torpedoProtocol;
    }


    private void processMessages(){
        String inLine = null;
        String outLine;
        try {
            while((inLine = in.readLine()) != null){
                System.out.format("inLine=%s %n", inLine);
                if(inLine.contains("fire")){
                    outLine = torpedoProtocol.processInput(inLine);
                    out.println(outLine);
                    if(outLine.equals("win")){
                        System.out.format("Defeat! Blame Peti%n");
                        out.println(outLine);
                        break;
                    }
                    out.println(target);
                } 
                if(isResponseToFire(inLine)){
                    if("win".equalsIgnoreCase(inLine)){
                        System.out.format("We won!%n");
                        break;
                    }
                    target = gameStrategy.getTarget(convertStringToStatus(inLine));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean isResponseToFire(String inLine) {
        Status inStatus = convertStringToStatus(inLine);
        return inLine.contains("miss") || inLine.contains("hit") || 
                inLine.contains("sunk") || inLine.contains("win");
    }
    
    private Status convertStringToStatus(String inLine) {
        Status answer = Status.MISS;
        if("miss".equals(inLine.toLowerCase())){
            answer = Status.MISS;
        } else if("hit".equals(inLine.toLowerCase())){
            answer = Status.HIT;
        } else if("sunk".equals(inLine.toLowerCase())){
            answer = Status.SUNK;
        } else if("win".equals(inLine.toLowerCase())){
            answer = Status.WIN;
        }
        return answer;
    }


    public void startProcessingMessages() {
        processMessages();
    }


    public void setFirstShot(boolean firstShot) {
        this.firstShot = firstShot;
    }


    public void setTarget(String target) {
        this.target = target;
    }
    
    

    
}
