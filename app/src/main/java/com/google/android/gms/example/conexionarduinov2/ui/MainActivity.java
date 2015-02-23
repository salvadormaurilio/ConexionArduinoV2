package com.google.android.gms.example.conexionarduinov2.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.example.conexionarduinov2.utils.Constans;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences(Constans.USER_PREFERENCES, MODE_PRIVATE);
        Intent intent = new Intent(getApplicationContext(), sharedPreferences.getBoolean(Constans.IS_LOGIN_PREFERENCES, false) ? SelectExerciseActivity.class : LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
