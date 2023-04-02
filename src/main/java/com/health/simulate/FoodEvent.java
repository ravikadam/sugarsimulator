package com.health.simulate;

public class FoodEvent extends Event {

    
    int quantity_; // in grams
    int foodID_;

    public FoodEvent(int fireTime,int quantity,int foodID) {
        super(fireTime, EventType.FOOD);
        quantity_ = quantity;
        foodID_ = foodID;

    }
    
}
