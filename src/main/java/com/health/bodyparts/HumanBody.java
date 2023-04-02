package com.health.bodyparts;

import java.util.HashMap;

public class HumanBody implements IBodyPart,IHumanBody{


    enum BodyState {FED_RESTING, FED_EXERCISING, POSTABSORPTIVE_RESTING, POSTABSORPTIVE_EXERCISING};

    enum BodyOrgan {HUMAN_BODY, STOMACH, INTESTINE, PORTAL_VEIN, LIVER, BLOOD, MUSCLES, BRAIN, HEART, ADIPOSE_TISSUE, KIDNEY};

    HashMap<Integer, FoodType> foodTypes = new HashMap< Integer, FoodType>();
    HashMap< Integer, ExerciseType> exerciseTypes = new HashMap< Integer, ExerciseType>();
    
    HashMap<BodyState, HashMap<BodyOrgan, HashMap<String,Double> > > metabolicParameters = new HashMap<BodyState, HashMap<BodyOrgan, HashMap<String,Double> > >();
    
    BodyState bodyState ;


    double glut4Impact_;
    double liverGlycogenSynthesisImpact_;
    double maxLiverGlycogenBreakdownDuringExerciseImpact_;
    double glycolysisMinImpact_;
    double glycolysisMaxImpact_;
    double excretionKidneysImpact_;

    int age; // in years
    int gender; // 0 male, 1 female
    int fitnessLevel; // between 0 and 100
    double vo2Max; // estimated from age, gender and fitnessLevel
    double percentVO2Max; // for the current exercise 

    double bodyWeight;
    double fatFraction_;
    int currExercise;
    double currEnergyExpenditure; // current energy expenditure in kcal/minute per kg of body weight
    int exerciseOverAt; // when does the current exercise event get over
    int lastHardExerciseAt; // when was the last "hard" exercise
    
    // Stomach* stomach;
    // Intestine* intestine;
    // PortalVein* portalVein;
    // Liver* liver;
    AdiposeTissue adiposeTissue;
    // Brain* brain;
    // Muscles* muscles;
    Blood blood;
    // Heart* heart;
    // Kidneys* kidneys;
    
  
        
   

    double totalGlycolysisSoFar;
    double totalGNGSoFar;
    double totalOxidationSoFar;
    double totalExcretionSoFar;
    double totalLiverGlycogenStorageSoFar;
    double totalLiverGlycogenBreakdownSoFar;
    double totalMusclesGlycogenStorageSoFar;
    double totalMusclesGlycogenBreakdownSoFar;
    double totalGlucoseFromIntestineSoFar;
    double totalEndogeneousGlucoseReleaseSoFar;
    double totalGlucoseReleaseSoFar;

	double tempGNG;
	double tempGlycolysis;
	double tempOxidation;
	double tempExcretion;
	double tempGlycogenStorage;
	double tempGlycogenBreakdown;

	double baseBGL; 
	double peakBGL;

 	double dailyCarbs;
    double intensityPeakGlucoseProd_; // exercise intensity in %VO2Maxat which peak GNG, glycogen breakdown takes place

    double gngImpact_;
    double liverGlycogenBreakdownImpact_;

	double insulinImpactOnGlycolysis_Mean;
	double insulinImpactOnGNG_Mean;
	double insulinImpactGlycogenBreakdownInLiver_Mean;
	double insulinImpactGlycogenSynthesisInLiver_Mean;
	double insulinImpactOnGlycolysis_StdDev;
	double insulinImpactOnGNG_StdDev;
	double insulinImpactGlycogenBreakdownInLiver_StdDev;
	double insulinImpactGlycogenSynthesisInLiver_StdDev;
    @Override
    public void setVO2Max() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setVO2Max'");
    }
    @Override
    public void processFoodEvent(int foodID, int howmuch) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'processFoodEvent'");
    }
    @Override
    public void processExerciseEvent(int exerciseID, int duration) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'processExerciseEvent'");
    }
    @Override
    public void readFoodFile(String file) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readFoodFile'");
    }
    @Override
    public void readExerciseFile(String file) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readExerciseFile'");
    }
    @Override
    public void readParams(String file) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readParams'");
    }
    @Override
    public void stomachEmpty() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stomachEmpty'");
    }
    @Override
    public boolean isExercising() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isExercising'");
    }
    @Override
    public double currentEnergyExpenditure() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'currentEnergyExpenditure'");
    }
    @Override
    public double getGlucoseNeedsOutsideMuscles() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGlucoseNeedsOutsideMuscles'");
    }
    @Override
    public void resetTotals(boolean print) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resetTotals'");
    }
    @Override
    public double glycolysis(double min, double max) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'glycolysis'");
    }
    @Override
    public double insulinImpactOnGlycolysis() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insulinImpactOnGlycolysis'");
    }
    @Override
    public double insulinImpactOnGNG() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insulinImpactOnGNG'");
    }
    @Override
    public double insulinImpactOnGlycogenSynthesisInLiver() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insulinImpactOnGlycogenSynthesisInLiver'");
    }
    @Override
    public double insulinImpactOnGlycogenBreakdownInLiver() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insulinImpactOnGlycogenBreakdownInLiver'");
    }
    @Override
    public void processTick() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'processTick'");
    }
    @Override
    public void setParams() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setParams'");
    }



    
    
}
