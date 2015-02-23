package com.google.android.gms.example.conexionarduinov2.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
import com.google.android.gms.example.conexionarduinov2.fragments.FragmentNegative;
import com.google.android.gms.example.conexionarduinov2.fragments.FragmentPosNeg;
import com.google.android.gms.example.conexionarduinov2.utils.Constans;
import com.google.android.gms.example.conexionarduinov2.utils.EventsOnFragment;
import com.google.android.gms.example.conexionarduinov2.utils.OnStarExerciseActivity;

public class ExerciseActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, View.OnClickListener,
        DialogExit.OnListenerExit, OnStarExerciseActivity {


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
    private Button buttonStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences(Constans.USER_PREFERENCES, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(Constans.IS_LOGIN_PREFERENCES, false)) {
            UserDataSource userDataSource = new UserDataSource(ExerciseActivity.this);
            getSupportActionBar().setTitle(userDataSource.getUserName(sharedPreferences.getLong(Constans.ID_PREFERENCES, 0)));
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

        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(this);

        isExit = false;
        isReceivingWeight = false;
        newWeight = 0;

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
                        FragmentDropset fragmentDropset = new FragmentDropset();
                        eventsOnFragment = fragmentDropset;
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerTraining, fragmentDropset).commit();
                        break;
                    case 1:
                        FragmentPosNeg fragmentPosNeg = new FragmentPosNeg();
                        eventsOnFragment = fragmentPosNeg;
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerTraining, fragmentPosNeg).commit();
                        break;
                    case 2:
                        FragmentNegative fragmentNegative = new FragmentNegative();
                        eventsOnFragment = fragmentNegative;
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerTraining, fragmentNegative).commit();
                        break;
                }

//                sendData(new byte[]{(byte) (positionItem + 1)});
            }
        } else {
            listViewTraining.setItemChecked(position, true);
        }

    }

    @Override
    public void onClick(View v) {
//        if ((isExit && v.getId() != R.id.buttonExit) || isStart) {
        if ((v.getId() != R.id.buttonExit) && isStart) {
            return;
        }


        switch (v.getId()) {
            case R.id.buttonStart:
                if (weight < 10) {
                    Toast.makeText(this, R.string.warning_message_weight_min, Toast.LENGTH_SHORT).show();
                } else if (positionItem != -1) {
                    v.setVisibility(View.GONE);
                    isStart = true;
//                    initListDropset();
                } else {
                    Toast.makeText(this, R.string.select_training, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buttonExit:
//                sendData(new byte[]{3});
                if (isStart) {

                    finish();
                }
                finish();
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
//                    adapterDropsetAndNegative.changeWeight(weight);
                }
                break;
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
//                adapterDropsetAndNegative.changeWeight(weight);

        } else {
            Toast.makeText(this, R.string.warning_message_weight, Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void OnStart() {
        buttonStart.setVisibility(View.GONE);
        isStart = true;
    }


    @Override
    public void onBackPressed() {

        if (isStart) {
            DialogExit dialogExit = new DialogExit();
            dialogExit.show(getSupportFragmentManager(), null);
        } else {
            if (isExit) {
//                sendData(new byte[]{3});
            } else {
//                sendData(new byte[]{6});
            }
            super.onBackPressed();
        }
    }

    @Override
    public void onListenerExit() {
        isStart = false;
//        sendData(new byte[]{3});
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if (isStart) {
                    DialogExit dialogExit = new DialogExit();
                    dialogExit.show(getSupportFragmentManager(), null);
                } else {
                    if (isExit) {
//                        sendData(new byte[]{3});
                    } else {
//                        sendData(new byte[]{6});
                    }
                    finish();
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
