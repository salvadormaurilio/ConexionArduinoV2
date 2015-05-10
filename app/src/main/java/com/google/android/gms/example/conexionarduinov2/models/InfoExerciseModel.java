package com.google.android.gms.example.conexionarduinov2.models;

/**
 * Created by sati on 08/02/2015.
 */
public class InfoExerciseModel {

    private long idExercise;
    private String date;
    private String name;
    private int typeTraining;
    private String training;
    private int weight;

    public InfoExerciseModel(long idExercise, int typeTraining, String training, String date, int weight) {
        this.idExercise = idExercise;
        this.typeTraining = typeTraining;
        this.training = training;
        this.date = date;
        this.weight = weight;
    }

    public InfoExerciseModel(long idExercise, String date, String name, int typeTraining, String training, int weight) {
        this.idExercise = idExercise;
        this.date = date;
        this.name = name;
        this.typeTraining = typeTraining;
        this.training = training;
        this.weight = weight;
    }

    public long getIdExercise() {
        return idExercise;
    }

    public String getName() {
        return name;
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
