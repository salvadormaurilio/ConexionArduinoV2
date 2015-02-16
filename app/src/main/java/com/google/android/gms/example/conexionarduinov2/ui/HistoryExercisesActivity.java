package com.google.android.gms.example.conexionarduinov2.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.example.conexionarduinov2.R;
import com.google.android.gms.example.conexionarduinov2.adapters.HistoryExerciseAdapter;
import com.google.android.gms.example.conexionarduinov2.database.UserDataSource;
import com.google.android.gms.example.conexionarduinov2.models.InfoExerciseModel;
import com.google.android.gms.example.conexionarduinov2.utils.Constans;

import java.util.ArrayList;
import java.util.List;

public class HistoryExercisesActivity extends ActionBarActivity implements HistoryExerciseAdapter.OnOpenExerciseListener, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_exercises);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        SharedPreferences sharedPreferences = getSharedPreferences(Constans.USER_PREFERENCES, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(Constans.IS_LOGIN_PREFERENCES, false)) {
            UserDataSource userDataSource = new UserDataSource(HistoryExercisesActivity.this);
            getSupportActionBar().setTitle(userDataSource.getUserName(sharedPreferences.getLong(Constans.ID_PREFERENCES, 0)));
        }

        int typeExercise = getIntent().getIntExtra(Constans.EXTRA_TYPE_EXERCISE, 0);
        String[] arrayExercise = getResources().getStringArray(R.array.exercises);

        TextView textViewTypeExercise = (TextView) findViewById(R.id.textViewTypeExercise);
        textViewTypeExercise.setText(arrayExercise[typeExercise]);

        ListView listView = (ListView) findViewById(R.id.listViewHistoryExercises);

        List<InfoExerciseModel> infoExerciseList = new ArrayList<>();
        infoExerciseList.add(new InfoExerciseModel("08/12/14", 1, "Dropset", 200));
        infoExerciseList.add(new InfoExerciseModel("08/12/14", 1, "Dropset", 200));
        infoExerciseList.add(new InfoExerciseModel("08/12/14", 1, "Dropset", 200));
        infoExerciseList.add(new InfoExerciseModel("08/12/14", 1, "Dropset", 200));
        infoExerciseList.add(new InfoExerciseModel("08/12/14", 1, "Dropset", 200));
        infoExerciseList.add(new InfoExerciseModel("08/12/14", 1, "Dropset", 200));
        infoExerciseList.add(new InfoExerciseModel("08/12/14", 1, "Dropset", 200));
        infoExerciseList.add(new InfoExerciseModel("08/12/14", 1, "Dropset", 200));
        infoExerciseList.add(new InfoExerciseModel("08/12/14", 1, "Dropset", 200));
        infoExerciseList.add(new InfoExerciseModel("08/12/14", 1, "Dropset", 200));
        infoExerciseList.add(new InfoExerciseModel("08/12/14", 1, "Dropset", 200));

        HistoryExerciseAdapter exerciseAdapter = new HistoryExerciseAdapter(HistoryExercisesActivity.this, infoExerciseList,this);
        listView.setAdapter(exerciseAdapter);

        findViewById(R.id.buttonNewSet).setOnClickListener(this);

    }


    @Override
    public void onOpenExercise(int position) {
        Intent intent = new Intent(HistoryExercisesActivity.this, ExerciseActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(HistoryExercisesActivity.this, ExerciseActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();;
        }
        return super.onOptionsItemSelected(item);
    }



}
