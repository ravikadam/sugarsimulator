package com.health.simulate;

public class HaltEvent extends Event {

    HaltEvent(int fireTime) {
        super(fireTime, EventType.HALT);
    }
    
}
