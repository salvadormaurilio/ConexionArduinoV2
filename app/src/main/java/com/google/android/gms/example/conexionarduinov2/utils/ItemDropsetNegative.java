package com.google.android.gms.example.conexionarduinov2.utils;

/**
 * Created by sati on 23/10/2014.
 */
public class ItemDropsetNegative {

    private int weight;
    private int repetitionsCounts;

    public ItemDropsetNegative(int weight) {
        this.weight = weight;
        repetitionsCounts = 0;
    }

    public ItemDropsetNegative() {
        this.weight = -1;
        repetitionsCounts = 0;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getRepetitionsCounts() {
        return repetitionsCounts;
    }

    public void incrementRepetitionsCounts() {
        repetitionsCounts++;
    }

    public void initilizeRepetitionsCounts() {
        repetitionsCounts = 0;
    }

}
