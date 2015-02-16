package com.google.android.gms.example.conexionarduinov2.models;

/**
 * Created by sati on 08/02/2015.
 */
public class InfoExerciseModel {

    private String date;
    private int typeTraining;
    private String training;
    private int weight;

    public InfoExerciseModel(String date, int typeTraining, String training, int weight) {
        this.date = date;
        this.typeTraining = typeTraining;
        this.training = training;
        this.weight = weight;
    }


    public String getDate() {
        return date;
    }

    public int getTypeTraining() {
        return typeTraining;
    }

    public String getTraining() {
        return training;
    }

    public int getWeight() {
        return weight;
    }
}
