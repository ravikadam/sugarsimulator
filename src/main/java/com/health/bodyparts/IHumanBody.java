package com.health.bodyparts;

public interface IHumanBody {
    void setParams(); // send organs their new rates when the state changes
    void setVO2Max();
    void processTick();
    void processFoodEvent(int foodID, int howmuch);
    void processExerciseEvent(int exerciseID, int duration);
    void readFoodFile(final String file);
    void readExerciseFile(final String file);
    void readParams(final String file);
    void stomachEmpty();
    boolean isExercising();
    double currentEnergyExpenditure();
    double getGlucoseNeedsOutsideMuscles();
    void resetTotals(boolean print);

    double glycolysis(double min, double max);

    double insulinImpactOnGlycolysis();
    double insulinImpactOnGNG();
    double insulinImpactOnGlycogenSynthesisInLiver();
    double insulinImpactOnGlycogenBreakdownInLiver();

}
