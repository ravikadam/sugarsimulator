package com.health.simulate;

public class Event {

    int fireTime_;
    enum EventType {FOOD, EXERCISE, HALT, METFORMIN, INSULIN_SHORT, INSULIN_LONG};

    EventType eventType_;

     Event (int fireTime, EventType food) 
{
    fireTime_ = fireTime;
    eventType_ = food;
    // int cost0 = fireTime;
    // int cost1 = 0;
}

}
