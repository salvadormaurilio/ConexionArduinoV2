package com.google.android.gms.example.conexionarduinov2.utils.interfaces;

import com.google.android.gms.example.conexionarduinov2.models.ItemDropsetAndNegativePositive;

import java.util.List;

/**
 * Created by sati on 22/02/2015.
 */

public interface EventsOnFragment {

    void onStartExercise();

    void nextWeight();

    void incrementRep();

    List<ItemDropsetAndNegativePositive> getItemRepetions();

    void onStopExercise();

}
