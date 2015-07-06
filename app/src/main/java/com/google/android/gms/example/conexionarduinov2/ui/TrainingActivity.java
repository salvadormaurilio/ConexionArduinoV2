package com.google.android.gms.example.conexionarduinov2.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.example.conexionarduinov2.R;
import com.google.android.gms.example.conexionarduinov2.adapters.TrainingAdapter;
import com.google.android.gms.example.conexionarduinov2.database.ExercisesDataSource;
import com.google.android.gms.example.conexionarduinov2.database.UserDataSource;
import com.google.android.gms.example.conexionarduinov2.dialogs.ExitDialog;
import com.google.android.gms.example.conexionarduinov2.dialogs.TrainingSessionProgressDialog;
import com.google.android.gms.example.conexionarduinov2.dialogs.TrainingSessionStartDialog;
import com.google.android.gms.example.conexionarduinov2.dialogs.WarmUpSessionAgainDialog;
import com.google.android.gms.example.conexionarduinov2.dialogs.WarmUpSessionProgressDialog;
import com.google.android.gms.example.conexionarduinov2.dialogs.WarmUpSessionStartDialog;
import com.google.android.gms.example.conexionarduinov2.fragments.FragmentDropset;
import com.google.android.gms.example.conexionarduinov2.fragments.FragmentPosNeg;
import com.google.android.gms.example.conexionarduinov2.utils.Constans;
import com.google.android.gms.example.conexionarduinov2.utils.ConstantsService;
import com.google.android.gms.example.conexionarduinov2.utils.UsbConexionUtils;
import com.google.android.gms.example.conexionarduinov2.utils.interfaces.EventsOnFragment;
import com.google.android.gms.example.conexionarduinov2.utils.interfaces.OnConexiWithActivity;
import com.google.android.gms.example.conexionarduinov2.utils.interfaces.OnStartTrainingSessionListener;
import com.google.android.gms.example.conexionarduinov2.utils.interfaces.OnStartWarmUpSessionEvents;

import java.util.Calendar;

public class TrainingActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, View.OnClickListener,
        ExitDialog.OnListenerExit, OnConexiWithActivity, OnStartWarmUpSessionEvents, OnStartTrainingSessionListener {


    private ListView listViewTraining;
    private int typeTraining;
    private int weight;
    private String lb;
    private TextView textViewLoadedWeight;
    private boolean isStart;
    private boolean isExit;

    private EventsOnFragment eventsOnFragment;
    private int minWeight;
    private int typeExercise;
    private String nameExercise;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences(Constans.USER_PREFERENCES, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(Constans.IS_LOGIN_PREFERENCES, false)) {
            UserDataSource userDataSource = new UserDataSource(TrainingActivity.this);
            getSupportActionBar().setTitle(userDataSource.getUserName(sharedPreferences.getLong(Constans.ID_USER_PREFERENCES, 0)));
        }

        listViewTraining = (ListView) findViewById(R.id.listViewTraining);
        listViewTraining.setAdapter(new TrainingAdapter(TrainingActivity.this, getResources().getStringArray(R.array.trainings)));
        listViewTraining.setOnItemClickListener(this);

        typeTraining = -1;

        lb = " " + getString(R.string.lb);
        weight = 0;

        nameExercise = "";

        textViewLoadedWeight = (TextView) findViewById(R.id.textViewLoadedWeight);
        textViewLoadedWeight.setText(getString(R.string.title_loaded_weight) + " " + weight + lb);

        isStart = false;

        findViewById(R.id.buttonStart).setOnClickListener(this);
        findViewById(R.id.buttonExit).setOnClickListener(this);
        findViewById(R.id.buttonWarmUpSession).setOnClickListener(this);

        isExit = false;
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

        IntentFilter filter = new IntentFilter();
        filter.addAction(ConstantsService.DATA_RECEIVED_INTENT);
        filter.addAction(ConstantsService.USB_DEVICE_DETACHED);
        registerReceiver(mReceiver, filter);


    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (ConstantsService.DEBUG) Log.d(ConstantsService.TAG, "onReceive() " + action);
            if (ConstantsService.DATA_RECEIVED_INTENT.equals(action)) {
                final byte[] data = intent.getByteArrayExtra(ConstantsService.DATA_EXTRA);

                switch (data[0]) {
                    case 0:
                        isExit = true;
                        eventsOnFragment.onStopExercise();
                        break;
                    case 1:
                        if (isStart) {
                            eventsOnFragment.nextWeight();
                        }
                        break;
                    case 2:
                        if (isStart) {
                            eventsOnFragment.incrementRep();
                        }
                        break;
                    case 3:
                        DialogFragment dialogFragmentWarmUp = (DialogFragment) getSupportFragmentManager().findFragmentByTag(Constans.TAG_DIALOG_WARM_UP);
                        dialogFragmentWarmUp.dismiss();
                        showWarnUpSessionAgainDialog();
                        break;
                    case 4:
                        DialogFragment dialogFragmentTrainingSession = (DialogFragment) getSupportFragmentManager().findFragmentByTag(Constans.TAG_DIALOG_TRAINING_SESSION);
                        dialogFragmentTrainingSession.dismiss();
                        onExitTrainingSession();
                        break;
                }

            } else if (ConstantsService.USB_DEVICE_DETACHED.equals(action)) {
                finish();
            }
        }

    };

    private void obtainExercise() {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            typeExercise = getIntent().getIntExtra(Constans.EXTRA_TYPE_EXERCISE, 0);
            typeTraining = getIntent().getIntExtra(Constans.EXTRA_TYPE_TRAINING, -1);
            if (typeExercise > 6) {
                nameExercise = getIntent().getStringExtra(Constans.EXTRA_NAME_EXERCISE);
            }

            if (typeTraining != -1) {

                if (Constans.IS_ENABLE_SEND_DATA)
                    UsbConexionUtils.sendData(TrainingActivity.this, new byte[]{(byte) (typeTraining)});

                weight = getIntent().getIntExtra(Constans.EXTRA_WEIGHT, 0);
                switch (typeTraining) {
                    case 1:
                        minWeight = 5;
                        FragmentDropset fragmentDropset = FragmentDropset.newIntance(getIntent().getLongExtra(Constans.EXTRA_ID_EXERCISE, -1), typeExercise);
                        eventsOnFragment = fragmentDropset;
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerTraining, fragmentDropset).commit();
                        break;
                    case 2:
                        minWeight = 2;
                        FragmentPosNeg fragmentPosNeg = FragmentPosNeg.newIntance(getIntent().getLongExtra(Constans.EXTRA_ID_EXERCISE, -1), typeExercise);
                        eventsOnFragment = fragmentPosNeg;
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerTraining, fragmentPosNeg).commit();
                        break;
                }

                listViewTraining.setItemChecked(this.typeTraining - 1, true);
                textViewLoadedWeight.setText(getString(R.string.title_loaded_weight) + " " + weight + lb);
            }

        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        if (!isStart) {
            if (typeTraining != position + 1) {

                switch (position + 1) {
                    case 1:
                        typeTraining = position + 1;

                        if (Constans.IS_ENABLE_SEND_DATA) {
                            UsbConexionUtils.sendData(TrainingActivity.this, new byte[]{(byte) (typeTraining)});
                        }

                        weight = 0;
                        textViewLoadedWeight.setText(getString(R.string.title_loaded_weight) + " " + weight + lb);

                        minWeight = 5;
                        FragmentDropset fragmentDropset = new FragmentDropset();
                        eventsOnFragment = fragmentDropset;
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerTraining, fragmentDropset).commit();
                        break;
                    case 2:

                        SharedPreferences sharedPreferences = getSharedPreferences(Constans.USER_PREFERENCES, MODE_PRIVATE);
                        int countExercise = sharedPreferences.getInt(Constans.COUNT_POS_NEG_PREFERENCES, 0);

                        Log.d("HABER", "" + countExercise);


                        if (countExercise > 0) {

                            typeTraining = position + 1;

                            if (Constans.IS_ENABLE_SEND_DATA) {
                                UsbConexionUtils.sendData(TrainingActivity.this, new byte[]{(byte) (typeTraining)});
                                UsbConexionUtils.sendData(TrainingActivity.this, new byte[]{(byte) (countExercise)});
                            }

                            weight = 0;
                            textViewLoadedWeight.setText(getString(R.string.title_loaded_weight) + " " + weight + lb);

                            minWeight = 2;
                            FragmentPosNeg fragmentPosNeg = new FragmentPosNeg();
                            eventsOnFragment = fragmentPosNeg;
                            getSupportFragmentManager().beginTransaction().replace(R.id.containerTraining, fragmentPosNeg).commit();
                        } else {
                            if (typeTraining != -1) {
                                listViewTraining.setItemChecked(typeTraining - 1, true);
                            } else {
                                listViewTraining.setItemChecked(1, false);
                            }

                            TrainingSessionStartDialog trainingSessionStartDialog = new TrainingSessionStartDialog();
                            trainingSessionStartDialog.show(getSupportFragmentManager(),null);
                        }
                        break;
                }
            }
        } else {
            listViewTraining.setItemChecked(typeTraining - 1, true);
        }

    }


    @Override
    public void onStartTrainingSession() {

        TrainingSessionProgressDialog trainingSessionProgressDialog = new TrainingSessionProgressDialog();
        trainingSessionProgressDialog.setShowsDialog(false);
        trainingSessionProgressDialog.show(getSupportFragmentManager(), Constans.TAG_DIALOG_TRAINING_SESSION);


        SharedPreferences sharedPreferences = getSharedPreferences(Constans.USER_PREFERENCES, MODE_PRIVATE);

        typeTraining = 2;

        if (Constans.IS_ENABLE_SEND_DATA) {
            UsbConexionUtils.sendData(TrainingActivity.this, new byte[]{(byte) (typeTraining)});
            UsbConexionUtils.sendData(TrainingActivity.this, new byte[]{(byte) (sharedPreferences.getInt(Constans.COUNT_POS_NEG_PREFERENCES, -1))});
        }

    }

    @Override
    public void onExitTrainingSession() {

        SharedPreferences.Editor editor = getSharedPreferences(Constans.USER_PREFERENCES, MODE_PRIVATE).edit();
        editor.putInt(Constans.COUNT_POS_NEG_PREFERENCES, 1).apply();

        listViewTraining.setItemChecked(typeTraining - 1, true);

        weight = 0;
        textViewLoadedWeight.setText(getString(R.string.title_loaded_weight) + " " + weight + lb);

        minWeight = 2;
        FragmentPosNeg fragmentPosNeg = new FragmentPosNeg();
        eventsOnFragment = fragmentPosNeg;
        getSupportFragmentManager().beginTransaction().replace(R.id.containerTraining, fragmentPosNeg).commit();

    }


    @Override
    public void onClick(View v) {
//        if ((isExit && v.getId() != R.id.buttonExit) || isStart) {

        if (v.getId() != R.id.buttonExit) {
            if (isStart) {
                return;
            } else if (typeTraining == -1 && v.getId() != R.id.buttonWarmUpSession) {
                Toast.makeText(TrainingActivity.this, R.string.message_select_trainig, Toast.LENGTH_SHORT).show();
                return;
            }
        }


        switch (v.getId()) {
            case R.id.buttonStart:
                if (typeTraining == -1) {
                    Toast.makeText(this, R.string.select_training, Toast.LENGTH_SHORT).show();
                } else if (weight < minWeight) {
                    Toast.makeText(this, getString(R.string.warning_message_weight_min) + " " + minWeight, Toast.LENGTH_SHORT).show();
                } else {
                    eventsOnFragment.onStartExercise();
                }
                break;
            case R.id.buttonWarmUpSession:
                showWarnUpSessionStartDialog();
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

    private void exitActivty() {
        if (isExit) {

            SharedPreferences sharedPreferences = getSharedPreferences(Constans.USER_PREFERENCES, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putInt(Constans.COUNT_POS_NEG_PREFERENCES, sharedPreferences.getInt(Constans.COUNT_POS_NEG_PREFERENCES, 0) + 1);
            editor.apply();

            saveExercise();
            if (Constans.IS_ENABLE_SEND_DATA)
                UsbConexionUtils.sendData(TrainingActivity.this, new byte[]{0});
            finish();
        } else if (isStart) {
            ExitDialog exitDialog = new ExitDialog();
            exitDialog.show(getSupportFragmentManager(), null);
        } else {
            if (Constans.IS_ENABLE_SEND_DATA)
                UsbConexionUtils.sendData(TrainingActivity.this, new byte[]{0});
            finish();
        }
    }


    @Override
    public void OnStart() {

        if (Constans.IS_ENABLE_SEND_DATA) {
            UsbConexionUtils.sendData(TrainingActivity.this, new byte[]{4});

            int auxWeight = weight;
            while (auxWeight > 127) {
                UsbConexionUtils.sendData(TrainingActivity.this, new byte[]{127});
                auxWeight -= 127;
            }
            UsbConexionUtils.sendData(TrainingActivity.this, new byte[]{(byte) auxWeight});

            UsbConexionUtils.sendData(TrainingActivity.this, new byte[]{0});

        }

        isStart = true;
    }


    private void showWarnUpSessionStartDialog() {
        WarmUpSessionStartDialog warmUpSessionStartDialog = new WarmUpSessionStartDialog();
        warmUpSessionStartDialog.show(getSupportFragmentManager(), null);
    }


    private void saveExercise() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constans.USER_PREFERENCES, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(Constans.IS_LOGIN_PREFERENCES, false)) {

            ExercisesDataSource exercisesDataSource = new ExercisesDataSource(TrainingActivity.this);

            Calendar calendar = Calendar.getInstance();
//            String date = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);

            if (typeExercise < 7) {
                exercisesDataSource.insertExercise(sharedPreferences.getLong(Constans.ID_USER_PREFERENCES, -1), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR), typeExercise, typeTraining,
                        eventsOnFragment.getItemRepetions(), weight);
            } else {
                exercisesDataSource.insertOtherExercise(sharedPreferences.getLong(Constans.ID_USER_PREFERENCES, -1), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR), nameExercise, typeExercise, typeTraining,
                        eventsOnFragment.getItemRepetions(), weight);
            }


        }
    }

    @Override
    public void onListenerExit() {

        if (Constans.IS_ENABLE_SEND_DATA)
            UsbConexionUtils.sendData(TrainingActivity.this, new byte[]{0});

        saveExercise();
        finish();
    }


    @Override
    public void startWarmUpSession() {
        showWarnUpSessionProgressDialog();
    }

    @Override
    public void exitWarmUpSession() {
        showWarnUpSessionAgainDialog();
    }

    @Override
    public void againWarmUpSession() {
        showWarnUpSessionStartDialog();
    }


    private void showWarnUpSessionAgainDialog() {
        WarmUpSessionAgainDialog warmUpSessionAgainDialog = new WarmUpSessionAgainDialog();
        warmUpSessionAgainDialog.show(getSupportFragmentManager(), null);
    }

    private void showWarnUpSessionProgressDialog() {

        if (Constans.IS_ENABLE_SEND_DATA)
            UsbConexionUtils.sendData(TrainingActivity.this, new byte[]{3});

        WarmUpSessionProgressDialog warmUpSessionProgressDialog = new WarmUpSessionProgressDialog();
        warmUpSessionProgressDialog.setCancelable(false);
        warmUpSessionProgressDialog.show(getSupportFragmentManager(), Constans.TAG_DIALOG_WARM_UP);

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
    public void onBackPressed() {
        exitActivty();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("TAG_FINISH", "Destroy_ExerciseActivity");
        unregisterReceiver(mReceiver);


    }


}
