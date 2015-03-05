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
import com.google.android.gms.example.conexionarduinov2.adapters.AdapterDropsetAndNegative;
import com.google.android.gms.example.conexionarduinov2.database.DialogWeight;
import com.google.android.gms.example.conexionarduinov2.database.ExercisesDataSource;
import com.google.android.gms.example.conexionarduinov2.database.TrainingDataSource;
import com.google.android.gms.example.conexionarduinov2.utils.Constans;
import com.google.android.gms.example.conexionarduinov2.utils.EventsOnFragment;
import com.google.android.gms.example.conexionarduinov2.utils.OnConexiWithActivity;
import com.google.android.gms.example.conexionarduinov2.utils.OnNewWeightFromDialog;
import com.google.android.gms.example.conexionarduinov2.utils.PlaceWeightListener;
import com.melnykov.fab.FloatingActionButton;

import java.util.Calendar;

/**
 * Created by sati on 15/02/2015.
 */
public class FragmentNegative extends Fragment implements PlaceWeightListener, EventsOnFragment, OnNewWeightFromDialog, View.OnClickListener {
    private AdapterDropsetAndNegative adapterDropsetAndNegative;
    private ListView listViewDropset;
    private OnConexiWithActivity onConexiWithActivity;
    private FloatingActionButton floatingActionButton;


    public static FragmentNegative newInstance (long idExercise) {
        FragmentNegative fragmentNegative = new FragmentNegative();
        Bundle bundle = new Bundle();
        bundle.putLong(Constans.ID_EXERCISE_FRAG, idExercise);
        fragmentNegative.setArguments(bundle);
        return fragmentNegative;
    }

    public FragmentNegative() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onConexiWithActivity = (OnConexiWithActivity) activity;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_negative, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listViewDropset = (ListView) view.findViewById(R.id.listViewTable);

        Bundle bundle = getArguments();
        if (getArguments() != null) {
            TrainingDataSource trainingDataSource = new TrainingDataSource(getActivity());
            adapterDropsetAndNegative = new AdapterDropsetAndNegative(getActivity(), 2, this,
                    trainingDataSource.queryTrainingDropsetNegative(bundle.getLong(Constans.ID_EXERCISE_FRAG), 2));
        } else {
            adapterDropsetAndNegative = new AdapterDropsetAndNegative(getActivity(), 2, this);


        }

        listViewDropset.setAdapter(adapterDropsetAndNegative);

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.floatingActionButton) {
            if (adapterDropsetAndNegative.getCount() < 4) {
                adapterDropsetAndNegative.addItemDropsetNegative();
            } else {
                Toast.makeText(getActivity(), R.string.message_no_more_weight, Toast.LENGTH_SHORT).show();
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
        adapterDropsetAndNegative.setWeightInitial(weight);

    }

    @Override
    public void onStartExercise() {
        if (adapterDropsetAndNegative.isFullTable()) {
            onConexiWithActivity.OnStart();
            initExercise();
        } else {
            Toast.makeText(getActivity(), R.string.warning_message_full_table, Toast.LENGTH_SHORT).show();
        }
    }

    private void initExercise() {
        adapterDropsetAndNegative.setClickable(false);
        listViewDropset.setItemChecked(adapterDropsetAndNegative.getPositionItem(), true);
        floatingActionButton.setOnClickListener(null);

    }

    @Override
    public void onClearWeight() {
        adapterDropsetAndNegative.setWeightInitial(0);
    }

    @Override
    public void nextWeight() {
        if (adapterDropsetAndNegative.getPositionItem() < adapterDropsetAndNegative.getCount()) {
            adapterDropsetAndNegative.incrementItemPosition();
            listViewDropset.setItemChecked(adapterDropsetAndNegative.getPositionItem(), true);
        }
    }

    @Override
    public void incrementRep() {
        adapterDropsetAndNegative.incrementRepetitions();
    }

    @Override
    public void onNewWeghtFromDialog(int weight) {
        adapterDropsetAndNegative.setWeight(weight);

    }

    @Override
    public void createNewDialog(int minWeight, int maxWight, boolean isNegative) {
        DialogWeight dialogWeight = DialogWeight.newInstance(minWeight, maxWight, isNegative);
        dialogWeight.setOnNewWeightFromDialog(this);
        dialogWeight.show(getFragmentManager(), "dia_wei");
    }

    @Override
    public void saveExercise(long idUser, int typeExercise) {
        Calendar calendar = Calendar.getInstance();
        String date = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);

        ExercisesDataSource exercisesDataSource = new ExercisesDataSource(getActivity());
        long idExercise = exercisesDataSource.insertExercise(idUser, typeExercise, 2, date, adapterDropsetAndNegative.getWeightInitial());

        TrainingDataSource trainingDataSource = new TrainingDataSource(getActivity());

        trainingDataSource.insertTrainingDropsetNegative(idExercise, adapterDropsetAndNegative.getWeights(), 2);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onConexiWithActivity = null;
    }
}
