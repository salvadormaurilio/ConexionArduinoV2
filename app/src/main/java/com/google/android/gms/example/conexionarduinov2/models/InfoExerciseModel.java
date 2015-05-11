package com.google.android.gms.example.conexionarduinov2.models;

import java.util.List;

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
    private String repetitions;
    private List<ItemDropsetAndNegativePositive> itemDropsetAndNegativePositives;

    public InfoExerciseModel(long idExercise, String date, int typeTraining, String training, int weight, String repetitions) {
        this.idExercise = idExercise;
        this.date = date;
        this.typeTraining = typeTraining;
        this.training = training;
        this.weight = weight;
        this.repetitions = repetitions;
    }

    public InfoExerciseModel(long idExercise, String date, String name, int typeTraining, String training, int weight, String repetitions) {
        this.idExercise = idExercise;
        this.date = date;
        this.name = name;
        this.typeTraining = typeTraining;
        this.training = training;
        this.weight = weight;
        this.repetitions = repetitions;
    }

    public InfoExerciseModel(String date, int typeTraining, String training, int weight, List<ItemDropsetAndNegativePositive> itemDropsetAndNegativePositives) {
        this.date = date;
        this.typeTraining = typeTraining;
        this.training = training;
        this.weight = weight;
        this.itemDropsetAndNegativePositives = itemDropsetAndNegativePositives;
    }

    public InfoExerciseModel( String date, String name, int typeTraining, String training, int weight, List<ItemDropsetAndNegativePositive> itemDropsetAndNegativePositives) {
        this.date = date;
        this.name = name;
        this.typeTraining = typeTraining;
        this.training = training;
        this.weight = weight;
        this.itemDropsetAndNegativePositives = itemDropsetAndNegativePositives;
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

    public String getRepetitions() {
        return repetitions;
    }

    public List<ItemDropsetAndNegativePositive> getItemDropsetAndNegativePositives() {
        return itemDropsetAndNegativePositives;
    }
}
