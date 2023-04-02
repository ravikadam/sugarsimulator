package com.health.bodyparts;

public interface IAdiposeTissue {
    void lipogenesis(float glucoseInMG);
    void consumeFat(float kcal);
    void addFat(float newFatInMG);
}
