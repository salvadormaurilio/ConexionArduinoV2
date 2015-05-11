package com.google.android.gms.example.conexionarduinov2.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.example.conexionarduinov2.R;
import com.google.android.gms.example.conexionarduinov2.adapters.DropsetAndNegativeAdapter;
import com.google.android.gms.example.conexionarduinov2.utils.interfaces.EventsOnFragment;
import com.google.android.gms.example.conexionarduinov2.utils.interfaces.OnConexiWithActivity;

/**
 * Created by sati on 15/02/2015.
 */
public class FragmentDropset extends Fragment implements EventsOnFragment {

    private DropsetAndNegativeAdapter dropsetAndNegativeAdapter;
    private ListView listViewDropset;
    private OnConexiWithActivity onConexiWithActivity;

    public FragmentDropset() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onConexiWithActivity = (OnConexiWithActivity) activity;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dropset, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listViewDropset = (ListView) view.findViewById(R.id.listViewTable);

        dropsetAndNegativeAdapter = new DropsetAndNegativeAdapter(getActivity(), 1);
        listViewDropset.setAdapter(dropsetAndNegativeAdapter);

    }

    @Override
    public void onStartExercise() {
        listViewDropset.setItemChecked(dropsetAndNegativeAdapter.getPositionItem(), true);
        onConexiWithActivity.OnStart();
    }

    @Override
    public void nextWeight() {
        if (dropsetAndNegativeAdapter.getPositionItem() < dropsetAndNegativeAdapter.getCount()-1) {
            dropsetAndNegativeAdapter.incrementItemPosition();
            listViewDropset.setItemChecked(dropsetAndNegativeAdapter.getPositionItem(), true);
        }
    }

    @Override
    public void incrementRep() {
        dropsetAndNegativeAdapter.incrementRepetitions();
    }

    @Override
    public void saveExercise(long idUser, int typeExercise) {

//        long idExercise = exercisesDataSource.insertExercise(idUser, typeExercise, 0, date, adapterDropsetAndNegative.getWeightInitial());


//        Calendar calendar = Calendar.getInstance();
//        String date = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
//
//        ExercisesDataSource exercisesDataSource = new ExercisesDataSource(getActivity());
//        long idExercise = exercisesDataSource.insertExercise(idUser, typeExercise, 0, date, adapterDropsetAndNegative.getWeightInitial());
//
//        TrainingDataSource trainingDataSource = new TrainingDataSource(getActivity());
//
//        trainingDataSource.insertTrainingDropsetNegative(idExercise, adapterDropsetAndNegative.getWeights(), 0);

    }


    @Override
    public void onDetach() {
        super.onDetach();
        onConexiWithActivity = null;
    }
}
