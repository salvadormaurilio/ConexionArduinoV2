package com.google.android.gms.example.conexionarduinov2.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.example.conexionarduinov2.R;
import com.google.android.gms.example.conexionarduinov2.adapters.TrainingAdapter;
import com.google.android.gms.example.conexionarduinov2.database.UserDataSource;
import com.google.android.gms.example.conexionarduinov2.dialogs.DialogExit;
import com.google.android.gms.example.conexionarduinov2.fragments.FragmentDropset;
import com.google.android.gms.example.conexionarduinov2.fragments.FragmentPosNeg;
import com.google.android.gms.example.conexionarduinov2.utils.Constans;
import com.google.android.gms.example.conexionarduinov2.utils.interfaces.EventsOnFragment;
import com.google.android.gms.example.conexionarduinov2.utils.interfaces.OnConexiWithActivity;

public class ExerciseActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, View.OnClickListener,
        DialogExit.OnListenerExit, OnConexiWithActivity {


    private ListView listViewTraining;
    private int positionItem;
    private int weight;
    private String lb;
    private TextView textViewLoadedWeight;
    private boolean isStart;
    private boolean isExit;

    private boolean isReceivingWeight;

    private int newWeight;
    private EventsOnFragment eventsOnFragment;
    private int minWeight;
    private int typeExercise;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences(Constans.USER_PREFERENCES, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(Constans.IS_LOGIN_PREFERENCES, false)) {
            UserDataSource userDataSource = new UserDataSource(ExerciseActivity.this);
            getSupportActionBar().setTitle(userDataSource.getUserName(sharedPreferences.getLong(Constans.ID_USER_PREFERENCES, 0)));
        }

        listViewTraining = (ListView) findViewById(R.id.listViewTraining);
        listViewTraining.setAdapter(new TrainingAdapter(ExerciseActivity.this, getResources().getStringArray(R.array.trainings)));
        listViewTraining.setOnItemClickListener(this);

        positionItem = -1;

        lb = " " + getString(R.string.lb);
        weight = 0;


        textViewLoadedWeight = (TextView) findViewById(R.id.textViewLoadedWeight);
        textViewLoadedWeight.setText(getString(R.string.title_loaded_weight) + " " + weight + lb);

        isStart = false;

        findViewById(R.id.buttonStart).setOnClickListener(this);
        findViewById(R.id.buttonExit).setOnClickListener(this);

        isExit = false;
        isReceivingWeight = false;
        newWeight = 0;
        minWeight = 0;

        findViewById(R.id.btn_key_0).setOnClickListener(this);
        findViewById(R.id.btn_key_1).setOnClickListener(this);
        findViewById(R.id.btn_key_2).setOnClickListener(this);
        findViewById(R.id.btn_key_3).setOnClickListener(this);
        findViewById(R.id.btn_key_4).setOnClickListener(this);
        findViewById(R.id.btn_key_5).setOnClickListener(this);
        findViewById(R.id.btn_key_6).setOnClickListener(this);
        findViewById(R.id.btn_key_7).setOnClickListener(this);
        findViewById(R.id.btn_key_8).setOnClickListener(this);
        findViewById(R.id.btn_key_9).setOnClickListener(this);
        findViewById(R.id.btn_key_clear).setOnClickListener(this);


        obtainExercise();

    }

    private void obtainExercise() {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            typeExercise = getIntent().getIntExtra(Constans.EXTRA_TYPE_EXERCISE, 0);
            long idExercise = bundle.getLong(Constans.ID_EXERCISE, -1);

            if (idExercise != -1) {
                int typeTraining = getIntent().getIntExtra(Constans.EXTRA_TYPE_TRAINING, 1);
                switch (typeTraining) {
                    case 0:
                        FragmentDropset fragmentDropset = new FragmentDropset();
                        eventsOnFragment = fragmentDropset;
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerTraining, fragmentDropset).commit();
                        break;
                    case 1:
                        FragmentPosNeg fragmentPosNeg = new FragmentPosNeg();
                        eventsOnFragment = fragmentPosNeg;
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerTraining, fragmentPosNeg).commit();
                        break;
                }

                positionItem = typeTraining;
                listViewTraining.setItemChecked(positionItem, true);
                weight = 0;
                textViewLoadedWeight.setText(getString(R.string.title_loaded_weight) + " " + weight + lb);
            }
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (!isStart && !isExit) {
            if (positionItem != position) {

                positionItem = position;
                weight = 0;
                textViewLoadedWeight.setText(getString(R.string.title_loaded_weight) + " " + weight + lb);

                switch (positionItem) {
                    case 0:
                        minWeight = 5;
                        FragmentDropset fragmentDropset = new FragmentDropset();
                        eventsOnFragment = fragmentDropset;
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerTraining, fragmentDropset).commit();
                        break;
                    case 1:
                        minWeight = 2;
                        FragmentPosNeg fragmentPosNeg = new FragmentPosNeg();
                        eventsOnFragment = fragmentPosNeg;
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerTraining, fragmentPosNeg).commit();
                        break;
                }

            }
        } else {
            listViewTraining.setItemChecked(position, true);
        }

    }

    @Override
    public void onClick(View v) {
//        if ((isExit && v.getId() != R.id.buttonExit) || isStart) {

        if (positionItem == -1 && (v.getId() != R.id.buttonExit)) {
            Toast.makeText(ExerciseActivity.this, R.string.message_select_trainig, Toast.LENGTH_SHORT).show();
            return;
        } else if ((v.getId() != R.id.buttonExit) && isStart) {
            return;
        }

        switch (v.getId()) {
            case R.id.buttonStart:
                if (positionItem == -1) {
                    Toast.makeText(this, R.string.select_training, Toast.LENGTH_SHORT).show();
                } else if (weight < minWeight) {
                    Toast.makeText(this, getString(R.string.warning_message_weight_min) + " " + minWeight, Toast.LENGTH_SHORT).show();
                } else {
                    eventsOnFragment.onStartExercise();
                }
                break;
            case R.id.buttonExit:
                exitActivty();
                break;
            case R.id.btn_key_0:
                valueWeight(0);
                break;
            case R.id.btn_key_1:
                valueWeight(1);
                break;
            case R.id.btn_key_2:
                valueWeight(2);
                break;
            case R.id.btn_key_3:
                valueWeight(3);
                break;
            case R.id.btn_key_4:
                valueWeight(4);
                break;
            case R.id.btn_key_5:
                valueWeight(5);
                break;
            case R.id.btn_key_6:
                valueWeight(6);
                break;
            case R.id.btn_key_7:
                valueWeight(7);
                break;
            case R.id.btn_key_8:
                valueWeight(8);
                break;
            case R.id.btn_key_9:
                valueWeight(9);
                break;
            case R.id.btn_key_clear:
                if (weight > 0) {
                    weight = 0;
                    textViewLoadedWeight.setText(getString(R.string.title_loaded_weight) + " " + weight + lb);
                }
                break;
        }
    }


    private void exitActivty() {
        if (isExit) {
            saveExercise();
            finish();
        } else if (isStart) {
            DialogExit dialogExit = new DialogExit();
            dialogExit.show(getSupportFragmentManager(), null);
        } else {
            finish();
        }
    }

    public void valueWeight(int num) {

        int weightAux;

        if (weight > 0) {
            weightAux = (weight * 10) + num;
        } else {
            if (num == 0) {
                return;
            }
            weightAux = num;
        }

        if (weightAux <= 720) {
            weight = weightAux;
            textViewLoadedWeight.setText(getString(R.string.title_loaded_weight) + " " + weight + lb);

        } else {
            Toast.makeText(this, R.string.warning_message_weight, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void OnStart() {
        isStart = true;
    }


    private void saveExercise() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constans.USER_PREFERENCES, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(Constans.IS_LOGIN_PREFERENCES, false)) {
            eventsOnFragment.saveExercise(sharedPreferences.getLong(Constans.ID_USER_PREFERENCES, 0), typeExercise);
        }
    }


    @Override
    public void onListenerExit() {
        saveExercise();
        finish();

    }


    @Override
    public void onBackPressed() {
        exitActivty();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_exercise, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                exitActivty();
                break;

            case R.id.action_increment:

                if (isStart) {
                    eventsOnFragment.incrementRep();
                }

                break;
            case R.id.action_next_weight:
                if (isStart) {
                    eventsOnFragment.nextWeight();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isStart) {
//            sendData(new byte[]{3});
        }
//        unregisterReceiver(mReceiver);
    }


}
