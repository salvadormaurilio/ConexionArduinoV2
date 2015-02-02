package com.google.android.gms.example.conexionarduinov2.models;

/**
 * Created by sati on 01/02/2015.
 */
public class UserInfoModel {

    private long id;
    private String userName;
    private String Height;
    private int typeUnits;

    public UserInfoModel(long id, String userName, String height, int typeUnits) {
        this.id = id;
        this.userName = userName;
        Height = height;
        this.typeUnits = typeUnits;
    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getHeight() {
        return Height;
    }

    public int getTypeUnits() {
        return typeUnits;
    }
}
