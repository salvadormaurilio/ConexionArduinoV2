package com.google.android.gms.example.conexionarduinov2.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.example.conexionarduinov2.R;
import com.google.android.gms.example.conexionarduinov2.adapters.HistoryExerciseAdapter;
import com.google.android.gms.example.conexionarduinov2.adapters.HistoryExerciseOtherAdapter;
import com.google.android.gms.example.conexionarduinov2.database.UserDataSource;
import com.google.android.gms.example.conexionarduinov2.dialogs.OtherExerciseDialog;
import com.google.android.gms.example.conexionarduinov2.models.InfoExerciseModel;
import com.google.android.gms.example.conexionarduinov2.utils.Constans;
import com.google.android.gms.example.conexionarduinov2.utils.interfaces.HistoryExerciseInterface;
import com.google.android.gms.example.conexionarduinov2.utils.interfaces.OnOpenExerciseListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryExercisesActivity extends ActionBarActivity implements OnOpenExerciseListener, View.OnClickListener,
        OtherExerciseDialog.OnListenerOtherExercise {

    private HistoryExerciseInterface historyExerciseInterface;
    private int typeExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        typeExercise = getIntent().getIntExtra(Constans.EXTRA_TYPE_EXERCISE, 0);

        setContentView(typeExercise < 7 ? R.layout.activity_history_exercises : R.layout.activity_history_exercises_other);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences(Constans.USER_PREFERENCES, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(Constans.IS_LOGIN_PREFERENCES, false)) {
            UserDataSource userDataSource = new UserDataSource(HistoryExercisesActivity.this);
            getSupportActionBar().setTitle(userDataSource.getUserName(sharedPreferences.getLong(Constans.ID_USER_PREFERENCES, 0)));
        }

        String[] arrayExercise = getResources().getStringArray(R.array.exercises);

        TextView textViewTypeExercise = (TextView) findViewById(R.id.textViewTypeExercise);
        textViewTypeExercise.setText(arrayExercise[typeExercise]);

        ListView listView = (ListView) findViewById(R.id.listViewHistoryExercises);

//        ExercisesDataSource exercisesDataSource = new ExercisesDataSource(HistoryExercisesActivity.this);
//        List<InfoExerciseModel> infoExerciseList = exercisesDataSource.queryExercises(HistoryExercisesActivity.this, sharedPreferences.getLong(Constans.ID_USER_PREFERENCES, -1), typeExercise);
        BaseAdapter exerciseAdapter;

        exerciseAdapter = typeExercise < 7 ?
                new HistoryExerciseAdapter(HistoryExercisesActivity.this, this) :
                new HistoryExerciseOtherAdapter(HistoryExercisesActivity.this, this);

        historyExerciseInterface = (HistoryExerciseInterface) exerciseAdapter;
        listView.setAdapter(exerciseAdapter);

        findViewById(R.id.buttonNewSet).setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        List<InfoExerciseModel> infoExerciseList = typeExercise < 7 ? loadHarcodExercise() : loadHarcodExerciseOther();
        historyExerciseInterface.setInfoExerciseModelList(infoExerciseList);

//        SharedPreferences sharedPreferences = getSharedPreferences(Constans.USER_PREFERENCES, MODE_PRIVATE);
//        ExercisesDataSource exercisesDataSource = new ExercisesDataSource(HistoryExercisesActivity.this);
//        List<InfoExerciseModel> infoExerciseList = exercisesDataSource.queryExercises(HistoryExercisesActivity.this, sharedPreferences.getLong(Constans.ID_USER_PREFERENCES, -1), typeExercise);
//        historyExerciseInterface.setInfoExerciseModelList(infoExerciseList);

    }


    private List<InfoExerciseModel> loadHarcodExercise() {
        List<InfoExerciseModel> infoExerciseModels = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            infoExerciseModels.add(new InfoExerciseModel(1, 0, "Dropset", "7/5/2015", 10 * i));
        }

        return infoExerciseModels;
    }

    private List<InfoExerciseModel> loadHarcodExerciseOther() {
        List<InfoExerciseModel> infoExerciseModels = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            infoExerciseModels.add(new InfoExerciseModel(1, "7/5/2015", "Exercise " + i, 0, "Dropset", 10 * i));
        }

        return infoExerciseModels;
    }


    @Override
    public void onViewSet(int position) {

        Intent intent = new Intent(HistoryExercisesActivity.this, ViewSetActivity.class);
        startActivity(intent);

    }

    @Override
    public void onRepeatSet(int position) {

//        Intent intent = new Intent(HistoryExercisesActivity.this, ExerciseActivity.class);
//        intent.putExtra(Constans.EXTRA_TYPE_EXERCISE, typeExercise);
//        intent.putExtra(Constans.ID_EXERCISE, historyExerciseInterface.getIdExercise(position));
//        intent.putExtra(Constans.EXTRA_TYPE_TRAINING, historyExerciseInterface.getTypeTraining(position));
//        startActivity(intent);

        newExercise();

    }

    @Override
    public void onClick(View v) {

        if (typeExercise < 7) {
            newExercise();
        } else {
            showDialogOtherExercise();
        }
    }


    private void newExercise() {
        Intent intent = new Intent(HistoryExercisesActivity.this, ExerciseActivity.class);
        intent.putExtra(Constans.EXTRA_TYPE_EXERCISE, typeExercise);
        intent.putExtra(Constans.ID_EXERCISE, -1L);
        intent.putExtra(Constans.EXTRA_TYPE_TRAINING, -1);
        startActivity(intent);
    }


    private void showDialogOtherExercise() {

        OtherExerciseDialog otherExerciseDialog = new OtherExerciseDialog();
        otherExerciseDialog.show(getSupportFragmentManager(), "");

    }

    @Override
    public void otherExercise(String exercise) {

        if (!TextUtils.isEmpty(exercise)) {
            newExercise();
        } else {
            showDialogOtherExercise();
            Toast.makeText(HistoryExercisesActivity.this, R.string.message_other_exercise, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
