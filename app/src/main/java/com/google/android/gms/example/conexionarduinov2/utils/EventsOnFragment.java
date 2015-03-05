package com.google.android.gms.example.conexionarduinov2.utils;

/**
 * Created by sati on 22/02/2015.
 */

public interface EventsOnFragment {

    public void onSetWeight(int weight);

    public void onStartExercise();

    public void onClearWeight();

    public void nextWeight();

    public void incrementRep();

    public void saveExercise(long idUser, int typeExercise);

}
