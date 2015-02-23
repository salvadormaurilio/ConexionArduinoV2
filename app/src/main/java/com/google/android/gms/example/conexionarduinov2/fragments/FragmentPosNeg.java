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
import com.google.android.gms.example.conexionarduinov2.utils.EventsOnFragment;
import com.google.android.gms.example.conexionarduinov2.utils.PlaceWeightListener;

/**
 * Created by sati on 15/02/2015.
 */
public class FragmentPosNeg extends Fragment implements PlaceWeightListener, EventsOnFragment {


    private AdapterNegativePositive adapterNegativePositive;
    private ListView listViewDropset;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pos_neg, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listViewDropset = (ListView) view.findViewById(R.id.listViewTable);


        adapterNegativePositive = new AdapterNegativePositive(getActivity(), this);
        listViewDropset.setAdapter(adapterNegativePositive);
    }

    @Override
    public void onDialogoInputWeight(int minWeight, int maxWeight, boolean isNegative) {

    }


    @Override
    public void setWeight(int weight) {

    }

    @Override
    public void onStartExercise() {
        if (adapterNegativePositive.isFullTable()) {
            initExercise();
        } else {
            Toast.makeText(getActivity(), R.string.warning_message_full_table, Toast.LENGTH_SHORT).show();
        }
    }


    private void initExercise() {
        adapterNegativePositive.setClickable(false);
        listViewDropset.setItemChecked(adapterNegativePositive.getPositionItemPositiveNegatives(), true);

    }
}
