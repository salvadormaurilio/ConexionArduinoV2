package com.google.android.gms.example.conexionarduinov2.utils.interfaces;

import com.google.android.gms.example.conexionarduinov2.models.InfoExerciseModel;

import java.util.List;

/**
 * Created by sati on 07/05/2015.
 */
public interface HistoryExerciseInterface {

    void setInfoExerciseModelList(List<InfoExerciseModel> infoExerciseModelList);

    long getIdExercise(int position);

    int getTypeTraining(int position);

    int getWeight(int position);

    String getNameExercise(int position);


}
