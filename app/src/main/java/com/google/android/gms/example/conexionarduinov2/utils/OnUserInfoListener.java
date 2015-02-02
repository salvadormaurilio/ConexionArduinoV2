package com.google.android.gms.example.conexionarduinov2.utils;

/**
 * Created by sati on 25/01/2015.
 */
public interface OnUserInfoListener {
    public void onUserInfoRegiter(String userName, String password, String confirmPassword, int positionHeight, String height, int typeUnits);

    public void onUserInfoLogin(String userName, String password);

}
