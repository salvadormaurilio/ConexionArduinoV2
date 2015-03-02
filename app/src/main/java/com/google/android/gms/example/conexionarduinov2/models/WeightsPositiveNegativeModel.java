package com.google.android.gms.example.conexionarduinov2.models;

/**
 * Created by sati on 02/03/2015.
 */
public class WeightsPositiveNegativeModel {


    private int weightNegative;
    private int weightPositive;


    public WeightsPositiveNegativeModel(int weightNegative, int weightPositive) {
        this.weightNegative = weightNegative;
        this.weightPositive = weightPositive;
    }


    public int getWeightNegative() {
        return weightNegative;
    }

    public int getWeightPositive() {
        return weightPositive;
    }
}
