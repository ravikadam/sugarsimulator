package com.health.bodyparts;

public interface IBlood {
    double currentHbA1c();

    void updateRBCs();

    void processTick();

    void setParams();

    void removeGlucose(double howmuch);

    void addGlucose(double howmuch);

    double getBGL();

    double getGNGSubstrates();

    double gngFromHighLactate(double rate_);

    double baseBGL();

    double highBGL();
    double volume() ;

    void updateInsulinLevel();

}
