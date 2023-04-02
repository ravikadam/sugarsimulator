package com.health.simulate;

public class SimCtl {
    static int 	ticks;
    //std::default_random_engine generator;
    
    //PriQ  eventQ;
    static int TICKS_PER_DAY = 24*60; // Simulated time granularity
    static int TICKS_PER_HOUR = 60; // Simulated time granularity

    enum EventType {FOOD , EXERCISE, HALT, METFORMIN, INSULIN_SHORT, INSULIN_LONG};

    public int elapsed_days() {
        return(ticks/TICKS_PER_DAY);
    }
    
    public int elapsed_hours() {
        int x = ticks % TICKS_PER_DAY;
        return(x/TICKS_PER_HOUR);
    }
    
    public int elapsed_minutes() {
        int x = ticks % TICKS_PER_DAY;
        return (x % TICKS_PER_HOUR);
    }

    
    
    
}
