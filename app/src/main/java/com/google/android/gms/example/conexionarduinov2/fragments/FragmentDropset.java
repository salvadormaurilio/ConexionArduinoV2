package com.google.android.gms.example.conexionarduinov2.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.example.conexionarduinov2.R;
import com.google.android.gms.example.conexionarduinov2.adapters.DropsetAndNegativeAdapter;
import com.google.android.gms.example.conexionarduinov2.database.ExercisesDataSource;
import com.google.android.gms.example.conexionarduinov2.models.ItemDropsetAndNegativePositive;
import com.google.android.gms.example.conexionarduinov2.utils.Constans;
import com.google.android.gms.example.conexionarduinov2.utils.interfaces.EventsOnFragment;
import com.google.android.gms.example.conexionarduinov2.utils.interfaces.OnConexiWithActivity;

import java.util.List;

/**
 * Created by sati on 15/02/2015.
 */
public class FragmentDropset extends Fragment implements EventsOnFragment {

    private DropsetAndNegativeAdapter dropsetAndNegativeAdapter;
    private ListView listViewDropset;
    private OnConexiWithActivity onConexiWithActivity;
    private boolean isClearTable;
    private TextView textViewTitleNumReps;

    public FragmentDropset() {

    }

    public static FragmentDropset newIntance(long idExercise, int typeExercise) {
        FragmentDropset fragmentDropset = new FragmentDropset();

        Bundle bundle = new Bundle();
        bundle.putLong(Constans.ARG_ID_EXERCISE, idExercise);
        bundle.putInt(Constans.ARG_TYPE_EXERCISE, typeExercise);

        fragmentDropset.setArguments(bundle);

        return fragmentDropset;
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

        if (hasIdArguments()) {

            textViewTitleNumReps = (TextView) view.findViewById(R.id.textViewTitleNumReps);
            textViewTitleNumReps.setText(R.string.title_table_executed_reps);

            int typeExercise = getArguments().getInt(Constans.ARG_TYPE_EXERCISE);

            ExercisesDataSource exercisesDataSource = new ExercisesDataSource(getActivity());
            List<ItemDropsetAndNegativePositive> itemDropsetAndNegativePositiveList = typeExercise < 7 ?
                    exercisesDataSource.queryRepetitions(getActivity(), getArguments().getLong(Constans.ARG_ID_EXERCISE)) : exercisesDataSource.queryOtherRepetitions(getActivity(), getArguments().getLong(Constans.ARG_ID_EXERCISE, -1));

            dropsetAndNegativeAdapter = new DropsetAndNegativeAdapter(getActivity(), 2, itemDropsetAndNegativePositiveList);

            isClearTable = false;
        } else {
            dropsetAndNegativeAdapter = new DropsetAndNegativeAdapter(getActivity(), 2);
            isClearTable = true;
        }
        listViewDropset.setAdapter(dropsetAndNegativeAdapter);

    }


    private boolean hasIdArguments() {
        return getArguments() != null && getArguments().getLong(Constans.ARG_ID_EXERCISE, -1) != -1;
    }

    @Override
    public void onStartExercise() {
        listViewDropset.setItemChecked(dropsetAndNegativeAdapter.getPositionItem(), true);
        onConexiWithActivity.OnStart();
    }

    @Override
    public void nextWeight() {
        if (dropsetAndNegativeAdapter.getPositionItem() < dropsetAndNegativeAdapter.getCount() - 1) {
            dropsetAndNegativeAdapter.incrementItemPosition();
            listViewDropset.setItemChecked(dropsetAndNegativeAdapter.getPositionItem(), true);
        }
    }

    @Override
    public void incrementRep() {
        dropsetAndNegativeAdapter.incrementRepetitions();
    }

    @Override
    public List<ItemDropsetAndNegativePositive> getItemRepetions() {
        return dropsetAndNegativeAdapter.getItemDropsetAndNegativePositives();
    }

    @Override
    public void newWeightOrTraining() {
        if (!isClearTable) {
            textViewTitleNumReps.setText(R.string.title_table_num_rep);
            dropsetAndNegativeAdapter.clearTable();

        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        onConexiWithActivity = null;
    }
}
