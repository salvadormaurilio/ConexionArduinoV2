package com.google.android.gms.example.conexionarduinov2.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.example.conexionarduinov2.R;
import com.google.android.gms.example.conexionarduinov2.adapters.AdapterDropsetAndNegative;

/**
 * Created by sati on 15/02/2015.
 */
public class FragmentDropset extends Fragment {


    private AdapterDropsetAndNegative adapterDropsetAndNegative;
    private ListView listViewDropset;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dropset_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listViewDropset = (ListView) view.findViewById(R.id.listViewTable);

        adapterDropsetAndNegative = new AdapterDropsetAndNegative(getActivity(), 1);
        listViewDropset.setAdapter(adapterDropsetAndNegative);
    }
}
