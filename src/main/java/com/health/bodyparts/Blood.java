package com.health.bodyparts;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.math3.distribution.PoissonDistribution;


import com.health.bodyparts.HumanBody.BodyOrgan;
import com.health.simulate.SimulateApplication;

public class Blood implements IBodyPart, IBlood {

    static final int MAXAGE = 120;
    static final int ONEDAY = 24 * 60;
    static final int HUNDREDDAYS = 100;

    RBCBin[] AgeBins = new RBCBin[MAXAGE + 1];// Aging Bins
    int bin0; // Current age 0 bin

    double rbcBirthRate_; // how many million RBCs take birth each minute
    double glycationProbSlope_; // g*l_i + c is the probability that an unglycated RBC glycates during a minute
    double glycationProbConst_;
    double glycolysisMin_;
    double glycolysisMax_;
    double glycolysisToLactate_; // what fraction of glycolysed glucose becomes lactate?

    HumanBody body;

    double minGlucoseLevel_;
    double baseGlucoseLevel_;
    double highGlucoseLevel_;
    double highLactateLevel_;
    double peakInsulinLevel_;

    double fluidVolume_; // in deciliters

    double avgBGL;
    double avgBGLOneDay;
    double avgBGLOneDaySum;
    double avgBGLOneDayCount;

    // All the metabolites are in units of milligrams of glucose
    double glucose; // in milligrams
    double insulinLevel;
    double baseInsulinLevel_;
    double lactate;
    double branchedAminoAcids;
    // see Table 2.2 in Frayn
    double glutamine;
    double alanine;
    double unbranchedAminoAcids;
    double gngSubstrates; // glycerol and other gng substrates (not including lactate, glutamine and
                          // alanine), all in units of glucose
    double glycolysisPerTick; // keep track of the glucose consumed via glycolysis this tick
    double totalGlycolysisSoFar;

    public Blood(HumanBody myBody) {
        body = myBody;

        // tracking RBCs
        bin0 = 1;
        rbcBirthRate_ = 144.0 * 60 * 24; // in millions per day (144 million RBCs take birth every minute)
        glycationProbSlope_ = 0.085 / 10000.0;
        glycationProbConst_ = 0;

        // all contents are in units of milligrams of glucose
        fluidVolume_ = 50.0; // in deciliters

        gngSubstrates = 0;
        alanine = 0;
        branchedAminoAcids = 0;
        unbranchedAminoAcids = 0;
        glutamine = 0;
        // baseInsulinLevel_ = 1.0/6.0;
        baseInsulinLevel_ = 0;
        peakInsulinLevel_ = 1.0;
        insulinLevel = baseInsulinLevel_;

        // Gerich: insulin dependent: 0.5 to 5 micromol per kg per minute
        glycolysisMin_ = 0.35 * 0.5 * 0.1801559;
        // glycolysisMax_ = 0.35 * 5 * 0.1801559;
        glycolysisMax_ = 0.9 * 0.35 * 2 * 0.1801559;

        glycolysisToLactate_ = 1.0;

        baseGlucoseLevel_ = 100; // mg/dl
        glucose = baseGlucoseLevel_ * fluidVolume_;
        highGlucoseLevel_ = 200; // mg/dl
        minGlucoseLevel_ = 40; // mg/dl
        highLactateLevel_ = 4053.51; // mg
        // 9 mmol/l of lactate = 4.5 mmol/l of glucose = 4.5*180.1559*5 mg of glucose =
        // 4053.51mg of glucose
        lactate = 450.39; // mg
        // 1mmol/l of lactate = 0.5mmol/l of glucose = 0.5*180.1559*5 mg of glucose =
        // 450.39 mg of glucose

        // initial number of RBCs
        for (int i = 0; i <= MAXAGE; i++) {
            AgeBins[i].RBCs = 0.94 * rbcBirthRate_;
            AgeBins[i].glycatedRBCs = 0.06 * rbcBirthRate_;
        }

        avgBGL = 100.0;

        avgBGLOneDay = 0;
        avgBGLOneDaySum = 0;
        avgBGLOneDayCount = 0;

        totalGlycolysisSoFar = 0;
    }

    public static int glycolysisMin__(double mean) {
        PoissonDistribution poisson = new PoissonDistribution(mean);
        return poisson.sample();
    }

    @Override
    public double currentHbA1c() {
        double rbcs = 0;
        double glycated_rbcs = 0;

        for (int i = 0; i <= MAXAGE; i++) {
            rbcs += AgeBins[i].RBCs;
            rbcs += AgeBins[i].glycatedRBCs;
            glycated_rbcs += AgeBins[i].glycatedRBCs;
        }

        if (rbcs == 0) {
            // std::cerr << "Error in Bloody::currentHbA1c\n";
            // exit(1);
            throw new UnsupportedOperationException("Error in Bloody::currentHbA1c\n");

        }

        return glycated_rbcs / rbcs;
    }

    @Override
    public void updateRBCs() {
        // will be called once a day

        bin0--;

        if (bin0 < 0)
            bin0 = MAXAGE;

        // New RBCs take birth
        AgeBins[bin0].RBCs = rbcBirthRate_;
        AgeBins[bin0].glycatedRBCs = 0;

        // std::cout << "New RBCs: " << AgeBins[bin0].RBCs << " bin0 " << bin0 <<
        // std::endl;

        // Old (100 to 120 days old) RBCs die
        int start_bin = bin0 + HUNDREDDAYS;

        if (start_bin > MAXAGE)
            start_bin -= (MAXAGE + 1);

        // std::cout << "Old RBCs Die\n";

        for (int i = 0; i <= (MAXAGE - HUNDREDDAYS); i++) {
            int j = start_bin + i;

            if (j < 0) {

                // SimCtl::time_stamp();
                System.out.println("RBC bin value negative " + j);
                // cout << " RBC bin value negative " << j << endl;
                throw new UnsupportedOperationException("RBC bin value negative");
            }

            if (j > MAXAGE)
                j -= (MAXAGE + 1);

            double kill_rate = ((double) (i)) / ((double) (MAXAGE - HUNDREDDAYS));

            AgeBins[j].RBCs *= (1.0 - kill_rate);
            AgeBins[j].glycatedRBCs *= (1.0 - kill_rate);

            // std::cout << "bin: " << j << ", RBCs " << AgeBins[j].RBCs << ", Glycated RBCs
            // " << AgeBins[j].glycatedRBCs << " killrate " << kill_rate << std::endl;
        }

        // glycate the RBCs
        double glycation_prob = avgBGLOneDay * glycationProbSlope_ + glycationProbConst_;

        // std::cout << "RBCs glycate\n";

        for (int i = 0; i <= MAXAGE; i++) {
            double newly_glycated = glycation_prob * AgeBins[i].RBCs;

            AgeBins[i].RBCs -= newly_glycated;
            AgeBins[i].glycatedRBCs += newly_glycated;

            // std::cout << "bin: " << i << ", RBCs " << AgeBins[i].RBCs << ", Glycated RBCs
            // " << AgeBins[i].glycatedRBCs << std::endl;
        }

        // SimCtl::time_stamp();
        // std::cout << " New HbA1c: " << currentHbA1c() << std::endl;
    }

    @Override
    public void removeGlucose(double howmuch) {
        glucose -= howmuch;
        
        //std::cout << "Glucose consumed " << howmuch << " ,glucose left " << glucose << std::endl;
        
        if( getBGL() <= minGlucoseLevel_ )
        {
            throw new UnsupportedOperationException("bgl dips to: " + getBGL());
        }
        
    }

    @Override
    public void addGlucose(double howmuch) {
        glucose += howmuch;
    }

    @Override
    public double getBGL(){return glucose/fluidVolume_;}
    @Override
    public double getGNGSubstrates(){ return (gngSubstrates + lactate + alanine + glutamine);}
    @Override
    public double gngFromHighLactate(double rate_)
    {

    //Gluconeogenesis will occur even in the presence of high insulin in proportion to lactate concentration. High lactate concentration (e.g. due to high glycolytic activity) would cause gluconeogenesis to happen even if insulin concentration is high. But then Gluconeogenesis would contribute to glycogen store of the liver (rather than generating glucose).
    // rate_ is in units of mg per kg per minute
    
    double x = rate_ * lactate/highLactateLevel_;
    
    if( x > lactate )
        x = lactate;
    
    lactate -= x;
    return x;
    };

    @Override
    public double baseBGL() {return baseGlucoseLevel_;}
    @Override
    public double highBGL() {return highGlucoseLevel_;}
    @Override
    public double volume() {return fluidVolume_;}
    

   

   

   

    

    

    @Override
    public void updateInsulinLevel() {
    	double bgl = glucose/fluidVolume_;
        if( bgl >= highGlucoseLevel_)
                insulinLevel = peakInsulinLevel_;
        else
        {
                if( bgl <= minGlucoseLevel_ )
                        insulinLevel = 0;
                else
                {
                                if( bgl >= baseGlucoseLevel_ )
                                        insulinLevel = baseInsulinLevel_ + (peakInsulinLevel_ - baseInsulinLevel_)
                                                *(bgl - baseGlucoseLevel_)/(highGlucoseLevel_ - baseGlucoseLevel_);
                                else
                                {
                                        if( body.isExercising() )
                                        {
                                                if( body.percentVO2Max >= body.intensityPeakGlucoseProd_ )
                                                {
                                                        insulinLevel = 0;
                                                }
                                                else
                                                {
                                                        double restIntensity = 3.5*2.0/(body.vo2Max);
                                                        if( body.percentVO2Max < restIntensity )
                                                        {
                                                            throw new UnsupportedOperationException("%VO2 less than restIntensity when body is exercising");
                                                        }
                                                        insulinLevel = baseInsulinLevel_ - (body.percentVO2Max - restIntensity)*
                                                                        (baseInsulinLevel_)/(body.intensityPeakGlucoseProd_ - restIntensity);
                                                }
                                        }
                                        else
                                        {
                                           insulinLevel = (baseInsulinLevel_)*(bgl - minGlucoseLevel_)/(baseGlucoseLevel_ - minGlucoseLevel_);
                                        }
                                }
                }
        }
}

    @Override
    public void processTick() {
    
        double x; // to hold the random samples


// Java function name glycolysisMin__ which will return possion distribtuiion









        
        //RBCs consume about 25mg of glucose every minute and convert it to lactate via glycolysis.
        //Gerich: Glycolysis. Depends on insulin level. Some of the consumed glucose becomes lactate.
        
        x = (double)(glycolysisMin__(1000.0*glycolysisMin_,sim.generator))/1000.0;
        double toGlycolysis = body.glycolysis(x,glycolysisMax_);
    
        if( toGlycolysis > glucose)
            toGlycolysis = glucose;
        
        glucose -= toGlycolysis;
        glycolysisPerTick = toGlycolysis;
        body.blood.lactate += glycolysisToLactate_*toGlycolysis;
        
        
        //update insulin level
    
        updateInsulinLevel();
    
        //calculating average bgl during a day
        
        if( avgBGLOneDayCount == ONEDAY )
        {
            avgBGLOneDay = avgBGLOneDaySum/avgBGLOneDayCount;
            avgBGLOneDaySum = 0;
            avgBGLOneDayCount = 0;
            updateRBCs();
            //SimCtl::time_stamp();
            //cout << " Blood::avgBGL " << avgBGLOneDay << endl;
        }
        
        double bgl = glucose/fluidVolume_;
        avgBGLOneDaySum += bgl;
        avgBGLOneDayCount++;
    
        //SimCtl::time_stamp();
        //cout << " Blood:: glycolysis " << glycolysisPerTick << endl;
        totalGlycolysisSoFar += glycolysisPerTick;
        //SimCtl::time_stamp();
        //cout << " Blood:: totalGlycolysis " << totalGlycolysisSoFar << endl;
        // SimCtl::time_stamp();
        // cout << " Blood:: insulinLevel " << insulinLevel << endl;
        System.out.println(" Blood:: insulinLevel " + insulinLevel);
        //" lactate " << lactate << " glutamine " << glutamine << " alanine " << alanine << " gngsubs " << gngSubstrates << " bAA " << branchedAminoAcids << " uAA " <<  unbranchedAminoAcids << endl;
    }

    @Override
    public void setParams() {

    HashMap<String,Double> temp = body.metabolicParameters.get(body.bodyState).get(BodyOrgan.BLOOD);

    Iterator<Map.Entry<String,Double>> itr1 = temp.entrySet().iterator();

    while(itr1.hasNext())
{
    Map.Entry<String,Double> itr = (Map.Entry<String,Double>) itr1.next();
    
    if(itr.getKey() == "rbcBirthRate_")
        rbcBirthRate_= itr.getValue();
    
    if(itr.getKey() == "glycationProbSlope_")
        glycationProbSlope_= itr.getValue();

    if(itr.getKey() == "glycationProbConst_")
        glycationProbConst_= itr.getValue();

    if(itr.getKey() == "minGlucoseLevel_")
         minGlucoseLevel_= itr.getValue();

    if(itr.getKey() == "baseGlucoseLevel_")
{
         baseGlucoseLevel_= itr.getValue();
     glucose = fluidVolume_ * baseGlucoseLevel_; 
}

    if(itr.getKey() == "baseInsulinLevel_")
{
         baseInsulinLevel_= itr.getValue();
     insulinLevel = baseInsulinLevel_; 
}
    if(itr.getKey() == "peakInsulinLevel_")
{
         peakInsulinLevel_= itr.getValue();
}

    if(itr.getKey() == "highGlucoseLevel_")
        highGlucoseLevel_= itr.getValue();
    
    if(itr.getKey() == "highLactateLevel_")
        highLactateLevel_= itr.getValue();
    
    if(itr.getKey() == "glycolysisMin_")
         glycolysisMin_= itr.getValue();
    
    if(itr.getKey() == "glycolysisMax_")
        glycolysisMax_= itr.getValue();
    
    if(itr.getKey() == "glycolysisToLactate_")
        glycolysisToLactate_= itr.getValue();
}

if( highGlucoseLevel_ <= baseGlucoseLevel_ )
{

throw new UnsupportedOperationException("highGlucoseLevel_ <= baseGlucoseLevel_");

}

    }

}
