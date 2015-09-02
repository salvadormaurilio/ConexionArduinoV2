package com.google.android.gms.example.conexionarduinov2.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.example.conexionarduinov2.R;
import com.google.android.gms.example.conexionarduinov2.adapters.ExercisesAdapter;
import com.google.android.gms.example.conexionarduinov2.database.UserDataSource;
import com.google.android.gms.example.conexionarduinov2.dialogs.NewExerciseDialog;
import com.google.android.gms.example.conexionarduinov2.utils.Constans;
import com.google.android.gms.example.conexionarduinov2.utils.ConstantsService;
import com.google.android.gms.example.conexionarduinov2.utils.UsbConexionUtils;


public class SelectExerciseActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, NewExerciseDialog.OnNewExerciseListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_exercise);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences(Constans.USER_PREFERENCES, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(Constans.IS_LOGIN_PREFERENCES, false)) {
            UserDataSource userDataSource = new UserDataSource(SelectExerciseActivity.this);
            getSupportActionBar().setTitle(userDataSource.getUserName(sharedPreferences.getLong(Constans.ID_USER_PREFERENCES, 0)));
        }

        ListView listViewExercise = (ListView) findViewById(R.id.listViewExercise);

        ExercisesAdapter exercisesAdapter = new ExercisesAdapter(SelectExerciseActivity.this, getResources().getStringArray(R.array.exercises));
        listViewExercise.setAdapter(exercisesAdapter);
        listViewExercise.setOnItemClickListener(this);

        IntentFilter filter = new IntentFilter();
        filter.addAction(ConstantsService.USB_DEVICE_DETACHED);
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (Constans.IS_ENABLE_SEND_DATA)
            UsbConexionUtils.sendData(SelectExerciseActivity.this, new byte[]{(byte) (position + 1)});
        SharedPreferences sharedPreferences = getSharedPreferences(Constans.USER_PREFERENCES, MODE_PRIVATE);

        int typeExercise = sharedPreferences.getInt(Constans.TYPE_EXERCISE_PREFERENCES, -1);

        if (typeExercise == -1 || typeExercise == position) {

            SharedPreferences.Editor editor = sharedPreferences.edit();

            if (typeExercise == -1) {
                editor.putInt(Constans.TYPE_EXERCISE_PREFERENCES, position);
            }
            editor.apply();

            Intent intent;
            if (sharedPreferences.getBoolean(Constans.IS_LOGIN_PREFERENCES, false)) {
                intent = new Intent(SelectExerciseActivity.this, HistoryExercisesActivity.class);
                intent.putExtra(Constans.EXTRA_TYPE_EXERCISE, position);
            } else {
                intent = new Intent(SelectExerciseActivity.this, TrainingActivity.class);
                intent.putExtra(Constans.EXTRA_TYPE_EXERCISE, position);
                intent.putExtra(Constans.EXTRA_TYPE_TRAINING, -1);
            }

            startActivity(intent);
        } else {
            NewExerciseDialog newExerciseDialog = NewExerciseDialog.newInstance(position);
            newExerciseDialog.show(getSupportFragmentManager(), null);
        }

    }

    @Override
    public void onNewExercise(int typeExercise) {

        SharedPreferences sharedPreferences = getSharedPreferences(Constans.USER_PREFERENCES, MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Constans.TYPE_EXERCISE_PREFERENCES, typeExercise);
        editor.putInt(Constans.COUNT_POS_NEG_PREFERENCES, 0);
        editor.putBoolean(Constans.IS_TRAINING_SESSION_PREFERENCES, false);
        editor.apply();

        Intent intent;
        if (sharedPreferences.getBoolean(Constans.IS_LOGIN_PREFERENCES, false)) {
            intent = new Intent(SelectExerciseActivity.this, HistoryExercisesActivity.class);
            intent.putExtra(Constans.EXTRA_TYPE_EXERCISE, typeExercise);
        } else {
            intent = new Intent(SelectExerciseActivity.this, TrainingActivity.class);
            intent.putExtra(Constans.EXTRA_TYPE_EXERCISE, typeExercise);
            intent.putExtra(Constans.EXTRA_TYPE_TRAINING, -1);
        }
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (Constans.IS_ENABLE_SEND_DATA)
                    UsbConexionUtils.sendData(SelectExerciseActivity.this, new byte[]{0});
                finish();
                break;
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
            UsbConexionUtils.sendData(SelectExerciseActivity.this, new byte[]{0});
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);

        Log.d("TAG_FINISH", "Destroy_SelectExerciseActivity");
    }


}
