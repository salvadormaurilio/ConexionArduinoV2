package com.google.android.gms.example.conexionarduinov2.utils.interfaces;

/**
 * Created by sati on 22/02/2015.
 */

public interface EventsOnFragment {


    public void onStartExercise();


    public void nextWeight();

    public void incrementRep();

    public void saveExercise(long idUser, int typeExercise);

}
