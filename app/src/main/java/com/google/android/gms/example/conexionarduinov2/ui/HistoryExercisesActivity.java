package com.google.android.gms.example.conexionarduinov2.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.example.conexionarduinov2.R;
import com.google.android.gms.example.conexionarduinov2.adapters.HistoryExerciseAdapter;
import com.google.android.gms.example.conexionarduinov2.adapters.HistoryExerciseOtherAdapter;
import com.google.android.gms.example.conexionarduinov2.database.ExercisesDataSource;
import com.google.android.gms.example.conexionarduinov2.database.UserDataSource;
import com.google.android.gms.example.conexionarduinov2.dialogs.OtherExerciseDialog;
import com.google.android.gms.example.conexionarduinov2.models.InfoExerciseModel;
import com.google.android.gms.example.conexionarduinov2.utils.Constans;
import com.google.android.gms.example.conexionarduinov2.utils.ConstantsService;
import com.google.android.gms.example.conexionarduinov2.utils.UsbConexionUtils;
import com.google.android.gms.example.conexionarduinov2.utils.interfaces.HistoryExerciseInterface;
import com.google.android.gms.example.conexionarduinov2.utils.interfaces.OnOpenExerciseListener;

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

        BaseAdapter exerciseAdapter;

        exerciseAdapter = typeExercise < 7 ?
                new HistoryExerciseAdapter(HistoryExercisesActivity.this, this) :
                new HistoryExerciseOtherAdapter(HistoryExercisesActivity.this, this);

        historyExerciseInterface = (HistoryExerciseInterface) exerciseAdapter;
        listView.setAdapter(exerciseAdapter);

        findViewById(R.id.buttonNewSet).setOnClickListener(this);

        IntentFilter filter = new IntentFilter();
        filter.addAction(ConstantsService.USB_DEVICE_DETACHED);
        registerReceiver(mReceiver, filter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences(Constans.USER_PREFERENCES, MODE_PRIVATE);
        ExercisesDataSource exercisesDataSource = new ExercisesDataSource(HistoryExercisesActivity.this);

        List<InfoExerciseModel> infoExerciseList = typeExercise < 7 ? exercisesDataSource.queryExercises(HistoryExercisesActivity.this, sharedPreferences.getLong(Constans.ID_USER_PREFERENCES, -1), typeExercise)
                : exercisesDataSource.queryOtherExercises(HistoryExercisesActivity.this, sharedPreferences.getLong(Constans.ID_USER_PREFERENCES, -1), typeExercise);
        historyExerciseInterface.setInfoExerciseModelList(infoExerciseList);


    }


    @Override
    public void onViewSet(int position) {

        Intent intent = new Intent(HistoryExercisesActivity.this, ViewSetActivity.class);
        intent.putExtra(Constans.EXTRA_TYPE_EXERCISE, typeExercise);
        intent.putExtra(Constans.EXTRA_ID_EXERCISE, historyExerciseInterface.getIdExercise(position));
        startActivity(intent);

    }

    @Override
    public void onRepeatSet(int position) {

        if (Constans.IS_ENABLE_SEND_DATA)
            UsbConexionUtils.sendData(HistoryExercisesActivity.this, new byte[]{1});


        Intent intent = new Intent(HistoryExercisesActivity.this, TrainingActivity.class);
        intent.putExtra(Constans.EXTRA_ID_EXERCISE, historyExerciseInterface.getIdExercise(position));
        intent.putExtra(Constans.EXTRA_TYPE_EXERCISE, typeExercise);
        intent.putExtra(Constans.EXTRA_WEIGHT, historyExerciseInterface.getWeight(position));
        intent.putExtra(Constans.EXTRA_TYPE_TRAINING, historyExerciseInterface.getTypeTraining(position));

        if (typeExercise > 6) {
            intent.putExtra(Constans.EXTRA_NAME_EXERCISE, historyExerciseInterface.getNameExercise(position));
        }

        startActivity(intent);

    }

    @Override
    public void onClick(View v) {

        if (typeExercise < 7) {
            if (Constans.IS_ENABLE_SEND_DATA)
                UsbConexionUtils.sendData(HistoryExercisesActivity.this, new byte[]{1});
            Intent intent = new Intent(HistoryExercisesActivity.this, TrainingActivity.class);
            intent.putExtra(Constans.EXTRA_TYPE_EXERCISE, typeExercise);
            intent.putExtra(Constans.EXTRA_TYPE_TRAINING, -1);
            startActivity(intent);
        } else {
            showDialogOtherExercise();
        }
    }

    private void showDialogOtherExercise() {

        OtherExerciseDialog otherExerciseDialog = new OtherExerciseDialog();
        otherExerciseDialog.show(getSupportFragmentManager(), "");

    }

    @Override
    public void otherExercise(String exercise) {

        if (!TextUtils.isEmpty(exercise)) {
            if (Constans.IS_ENABLE_SEND_DATA)
                UsbConexionUtils.sendData(HistoryExercisesActivity.this, new byte[]{1});
            Intent intent = new Intent(HistoryExercisesActivity.this, TrainingActivity.class);
            intent.putExtra(Constans.EXTRA_TYPE_EXERCISE, typeExercise);
            intent.putExtra(Constans.EXTRA_TYPE_TRAINING, -1);
            intent.putExtra(Constans.EXTRA_NAME_EXERCISE, exercise);
            startActivity(intent);
        } else {
            showDialogOtherExercise();
            Toast.makeText(HistoryExercisesActivity.this, R.string.message_other_exercise, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if (Constans.IS_ENABLE_SEND_DATA)
                    UsbConexionUtils.sendData(HistoryExercisesActivity.this, new byte[]{0});
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (ConstantsService.DEBUG) Log.d(ConstantsService.TAG, "onReceive() " + action);
            if (ConstantsService.USB_DEVICE_DETACHED.equals(action)) {
                finish();
            }
        }

    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Constans.IS_ENABLE_SEND_DATA)
            UsbConexionUtils.sendData(HistoryExercisesActivity.this, new byte[]{0});
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mReceiver);


        Log.d("TAG_FINISH", "Destroy_HistoryExercisesActivity");

    }
}
