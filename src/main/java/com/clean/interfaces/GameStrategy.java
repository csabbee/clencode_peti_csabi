package com.clean.interfaces;

import com.clean.shipgame.Status;

public interface GameStrategy {

    public String getTarget(Status input);
    public String firstTarget();
    public void initialise();
    public int getPeter();
}
