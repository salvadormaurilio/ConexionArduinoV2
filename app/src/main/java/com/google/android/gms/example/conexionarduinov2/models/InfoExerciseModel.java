package com.google.android.gms.example.conexionarduinov2.models;

/**
 * Created by sati on 08/02/2015.
 */
public class InfoExerciseModel {

    private  String date;
    private  int typeTraining;
    private  String weight;

    public InfoExerciseModel(String date, int typeTraining, String weight) {
        this.date = date;
        this.typeTraining = typeTraining;
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public int getTypeTraining() {
        return typeTraining;
    }

    public String getWeight() {
        return weight;
    }
}
