package com.google.android.gms.example.conexionarduinov2.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.example.conexionarduinov2.R;
import com.google.android.gms.example.conexionarduinov2.adapters.ExercisesAdapter;
import com.google.android.gms.example.conexionarduinov2.database.UserDataSource;
import com.google.android.gms.example.conexionarduinov2.utils.Constans;


public class SelectExerciseActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_exercise);

        SharedPreferences sharedPreferences = getSharedPreferences(Constans.USER_PREFERENCES, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(Constans.IS_LOGIN_PREFERENCES, false)) {
            UserDataSource userDataSource = new UserDataSource(SelectExerciseActivity.this);
            getSupportActionBar().setTitle(userDataSource.getUserName(sharedPreferences.getLong(Constans.ID_USER_PREFERENCES, 0)));
        }

        ListView listViewExercise = (ListView) findViewById(R.id.listViewExercise);
        ExercisesAdapter exercisesAdapter = new ExercisesAdapter(SelectExerciseActivity.this, getResources().getStringArray(R.array.exercises));
        listViewExercise.setAdapter(exercisesAdapter);
        listViewExercise.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        Intent intent;
        SharedPreferences sharedPreferences = getSharedPreferences(Constans.USER_PREFERENCES, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(Constans.IS_LOGIN_PREFERENCES, false)) {
            intent = new Intent(SelectExerciseActivity.this, HistoryExercisesActivity.class);
            intent.putExtra(Constans.EXTRA_TYPE_EXERCISE, position);
        } else {
            intent = new Intent(SelectExerciseActivity.this, ExerciseActivity.class);
            intent.putExtra(Constans.EXTRA_TYPE_EXERCISE, position);
            intent.putExtra(Constans.ID_EXERCISE, -1L);
            intent.putExtra(Constans.EXTRA_TYPE_TRAINING, -1);
        }
        startActivity(intent);

    }




}
