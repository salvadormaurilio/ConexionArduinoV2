package com.google.android.gms.example.conexionarduinov2.utils;

/**
 * Created by sati on 22/02/2015.
 */
public interface OnNewWeightFromDialog {
    public void onNewWeghtFromDialog(int weight);

    public void createNewDialog(int minWeight, int maxWight, boolean isNegative);
}
