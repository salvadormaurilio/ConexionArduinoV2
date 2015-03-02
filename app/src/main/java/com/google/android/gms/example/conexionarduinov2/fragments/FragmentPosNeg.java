package com.google.android.gms.example.conexionarduinov2.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.example.conexionarduinov2.R;
import com.google.android.gms.example.conexionarduinov2.adapters.AdapterNegativePositive;
import com.google.android.gms.example.conexionarduinov2.database.DialogWeight;
import com.google.android.gms.example.conexionarduinov2.database.ExercisesDataSource;
import com.google.android.gms.example.conexionarduinov2.database.TrainingDataSource;
import com.google.android.gms.example.conexionarduinov2.utils.EventsOnFragment;
import com.google.android.gms.example.conexionarduinov2.utils.OnConexiWithActivity;
import com.google.android.gms.example.conexionarduinov2.utils.OnNewWeightFromDialog;
import com.google.android.gms.example.conexionarduinov2.utils.PlaceWeightListener;
import com.melnykov.fab.FloatingActionButton;

import java.util.Calendar;

/**
 * Created by sati on 15/02/2015.
 */
public class FragmentPosNeg extends Fragment implements PlaceWeightListener, EventsOnFragment, OnNewWeightFromDialog, View.OnClickListener {

    private AdapterNegativePositive adapterNegativePositive;
    private ListView listViewPosNeg;
    private OnConexiWithActivity onConexiWithActivity;
    private FloatingActionButton floatingActionButton;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onConexiWithActivity = (OnConexiWithActivity) activity;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pos_neg, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listViewPosNeg = (ListView) view.findViewById(R.id.listViewTable);
        adapterNegativePositive = new AdapterNegativePositive(getActivity(), this);
        listViewPosNeg.setAdapter(adapterNegativePositive);

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.floatingActionButton) {
            if (adapterNegativePositive.getCount() < 3) {
                adapterNegativePositive.addItemPositiveNegative();
            } else {
                Toast.makeText(getActivity(), R.string.message_no_more_weight,Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDialogoInputWeight(int minWeight, int maxWeight, boolean isNegative) {
        DialogWeight dialogWeight = DialogWeight.newInstance(minWeight, maxWeight, isNegative);
        dialogWeight.setOnNewWeightFromDialog(this);
        dialogWeight.show(getFragmentManager(), "dia_wei");
    }


    @Override
    public void onSetWeight(int weight) {
        adapterNegativePositive.setNewWeightInitial(weight);
    }

    @Override
    public void onStartExercise() {
        if (adapterNegativePositive.isFullTable()) {
            onConexiWithActivity.OnStart();
            initExercise();
        } else {
            Toast.makeText(getActivity(), R.string.warning_message_full_table, Toast.LENGTH_SHORT).show();
        }
    }

    private void initExercise() {
        adapterNegativePositive.setClickable(false);
        listViewPosNeg.setItemChecked(adapterNegativePositive.getPositionItemPositiveNegatives(), true);
        floatingActionButton.setOnClickListener(null);

    }

    @Override
    public void onClearWeight() {
        adapterNegativePositive.setNewWeightInitial(0);
    }

    @Override
    public void nextWeight() {
        if (adapterNegativePositive.getPositionItemPositiveNegatives() < adapterNegativePositive.getCount()) {
            adapterNegativePositive.incrementItemPosition();
            listViewPosNeg.setItemChecked(adapterNegativePositive.getPositionItemPositiveNegatives(), true);
        }

    }

    @Override
    public void incrementRep() {
        adapterNegativePositive.incrementRepetitions();
    }

    @Override
    public void onNewWeghtFromDialog(int weight) {
        adapterNegativePositive.setWeight(weight);
    }

    @Override
    public void createNewDialog(int minWeight, int maxWight, boolean isNegative) {
        DialogWeight dialogWeight = DialogWeight.newInstance(minWeight,maxWight,isNegative);
        dialogWeight.setOnNewWeightFromDialog(this);
        dialogWeight.show(getFragmentManager(), "dia_wei");
    }


    @Override
    public void saveExercise(int idUser, int idExercise, int typeExercise) {
        Calendar calendar = Calendar.getInstance();
        String date = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);

        ExercisesDataSource exercisesDataSource = new ExercisesDataSource(getActivity());
        exercisesDataSource.insertExercise(idUser, typeExercise, 1, date, adapterNegativePositive.getWeightInitial());

        TrainingDataSource trainingDataSource = new TrainingDataSource(getActivity());

        trainingDataSource.insertTrainingNegativePositive(idExercise, adapterNegativePositive.getWeights());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onConexiWithActivity = null;
    }


}
