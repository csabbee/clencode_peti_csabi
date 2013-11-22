package com.clean.interfaces;

import com.clean.shipgame.Status;

public interface GameStrategy {

    public String getTarget(Status input);
    public void initialise();
    public int getPeter();
}
