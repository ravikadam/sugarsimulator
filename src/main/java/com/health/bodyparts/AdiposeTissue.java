package com.health.bodyparts;

public class AdiposeTissue implements IBodyPart, IAdiposeTissue {

    HumanBody body;
    double fat;

    public AdiposeTissue(HumanBody body) {
        this.body = body;
        fat = 0.0f;
    }

    @Override
    public void lipogenesis(float glucoseInMG) {

        // one gram of glucose has 4kcal of energy
        // one gram of TAG has 9 kcal of energy
        body.bodyWeight -= fat / 1000.0;
        fat += (glucoseInMG / 1000.0) * 4.0 / 9.0;
        body.bodyWeight += fat / 1000.0;
    }

    @Override
    public void consumeFat(float kcal) {
        body.bodyWeight -= fat / 1000.0;
        fat -= kcal / 9.0;
        body.bodyWeight += fat / 1000.0;
    }

    @Override
    public void addFat(float newFatInMG) {
        body.bodyWeight -= fat / 1000.0;
        fat += newFatInMG / 1000.0;
        body.bodyWeight += fat / 1000.0;
    }

    @Override
    public void processTick() {
        return ;

    }

    @Override
    public void setParams() {
        return ;
    }

}
