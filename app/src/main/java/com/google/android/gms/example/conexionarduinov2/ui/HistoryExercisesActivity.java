package com.google.android.gms.example.conexionarduinov2.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.example.conexionarduinov2.R;
import com.google.android.gms.example.conexionarduinov2.database.UserDataSource;
import com.google.android.gms.example.conexionarduinov2.utils.Constans;

public class HistoryExercisesActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_exercises);

        SharedPreferences sharedPreferences = getSharedPreferences(Constans.USER_PREFERENCES, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(Constans.IS_LOGIN_PREFERENCES, false)) {
            UserDataSource userDataSource = new UserDataSource(HistoryExercisesActivity.this);
            getSupportActionBar().setTitle(userDataSource.getUserName(sharedPreferences.getLong(Constans.ID_PREFERENCES, 0)));
        }
    }

}
