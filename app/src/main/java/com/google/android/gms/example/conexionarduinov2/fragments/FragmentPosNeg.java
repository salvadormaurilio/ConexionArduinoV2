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
import com.google.android.gms.example.conexionarduinov2.models.ItemDropsetAndNegativePositive;
import com.google.android.gms.example.conexionarduinov2.utils.interfaces.EventsOnFragment;
import com.google.android.gms.example.conexionarduinov2.utils.interfaces.OnConexiWithActivity;

import java.util.List;

/**
 * Created by sati on 15/02/2015.
 */
public class FragmentPosNeg extends Fragment implements EventsOnFragment {

    private DropsetAndNegativeAdapter dropsetAndNegativeAdapter;
    private ListView listViewDropset;
    private OnConexiWithActivity onConexiWithActivity;



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
        listViewDropset = (ListView) view.findViewById(R.id.listViewTable);

        dropsetAndNegativeAdapter = new DropsetAndNegativeAdapter(getActivity(), 2);
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
    public List<ItemDropsetAndNegativePositive> getItemRepetions() {
        return dropsetAndNegativeAdapter.getItemDropsetAndNegativePositives();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        onConexiWithActivity = null;
    }

}
