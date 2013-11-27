package com.clean.communication;

import com.clean.interfaces.Torpedo;


public class TorpedoProtocol {
    private Torpedo torpedo;
    
    public TorpedoProtocol(Torpedo torpedo) {
        super();
        this.torpedo = torpedo;
    }

    public String processInput(String theInput) {
        String outputLine = "init";
        if(theInput.split(" ").length >= 3){
        	int x = Integer.parseInt(theInput.split(" ")[1]);
        	int y = Integer.parseInt(theInput.split(" ")[2]);
        	outputLine = torpedo.fire(x, y).name().toLowerCase();
        }
        return outputLine;
    }

}
