package com.google.android.gms.example.conexionarduinov2.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.example.conexionarduinov2.R;
import com.google.android.gms.example.conexionarduinov2.adapters.DropsetAndNegativeAdapter;
import com.google.android.gms.example.conexionarduinov2.database.ExercisesDataSource;
import com.google.android.gms.example.conexionarduinov2.database.UserDataSource;
import com.google.android.gms.example.conexionarduinov2.models.InfoExerciseModel;
import com.google.android.gms.example.conexionarduinov2.utils.Constans;

public class ViewSetActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_set);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences(Constans.USER_PREFERENCES, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(Constans.IS_LOGIN_PREFERENCES, false)) {
            UserDataSource userDataSource = new UserDataSource(ViewSetActivity.this);
            getSupportActionBar().setTitle(userDataSource.getUserName(sharedPreferences.getLong(Constans.ID_USER_PREFERENCES, 0)));
        }

        TextView textViewTypeTraining = (TextView) findViewById(R.id.textViewTypeTraining);
        TextView textViewLoadedWeight = (TextView) findViewById(R.id.textViewLoadedWeight);
        findViewById(R.id.buttonExit).setOnClickListener(ViewSetActivity.this);
        ListView listViewTable = (ListView) findViewById(R.id.listViewTable);

        long idExercise = getIntent().getLongExtra(Constans.EXTRA_ID_EXERCISE, 0);
        int typerExercise = getIntent().getIntExtra(Constans.EXTRA_TYPE_EXERCISE, 0);

        ExercisesDataSource exercisesDataSource = new ExercisesDataSource(ViewSetActivity.this);

        InfoExerciseModel infoExerciseModel = typerExercise < 7 ?
                exercisesDataSource.queryExercise(ViewSetActivity.this, idExercise, sharedPreferences.getLong(Constans.ID_USER_PREFERENCES, -1))
                : exercisesDataSource.queryOtherExercise(ViewSetActivity.this, idExercise, sharedPreferences.getLong(Constans.ID_USER_PREFERENCES, -1));

        textViewTypeTraining.setText(infoExerciseModel.getTraining());
        textViewLoadedWeight.setText(infoExerciseModel.getWeight() + " " + getString(R.string.lb));

        DropsetAndNegativeAdapter dropsetAndNegativeAdapter = new DropsetAndNegativeAdapter(ViewSetActivity.this, infoExerciseModel.getTypeTraining(), infoExerciseModel.getItemDropsetAndNegativePositives());
        listViewTable.setAdapter(dropsetAndNegativeAdapter);


    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
