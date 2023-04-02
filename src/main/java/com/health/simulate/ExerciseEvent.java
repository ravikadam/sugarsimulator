package com.health.simulate;

public class ExerciseEvent extends Event {

    int duration_; // in grams
    int exerciseID_;

     ExerciseEvent(int fireTime,int duration,int exerciseID) {
        super(fireTime, EventType.EXERCISE);
        duration_ = duration;
        exerciseID_ = exerciseID;
        
    }
    
}
