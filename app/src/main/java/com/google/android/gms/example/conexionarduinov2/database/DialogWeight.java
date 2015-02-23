package com.google.android.gms.example.conexionarduinov2.database;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.example.conexionarduinov2.R;
import com.google.android.gms.example.conexionarduinov2.utils.OnNewWeightFromDialog;


/**
 * Created by sati on 28/10/2014.
 */
public class DialogWeight extends DialogFragment implements View.OnClickListener {

    private static final String MIN_WEIGHT = "min_weight";
    private static final String MAX_WEIGHT = "max_weight";
    private static final String ISNEGATIVE = "is_negative";

    private TextView textViewDialogLoadedWeight;
    private String lb;
    private String loadedWeight;
    private int minWeight;
    private int maxWeight;

    private int weight;

    private OnNewWeightFromDialog onNewWeightFromDialog;

    public static DialogWeight newInstance(int minWeight, int maxWight, boolean isNegative) {
        DialogWeight dialogFragment = new DialogWeight();
        Bundle bundle = new Bundle();
        bundle.putInt(MIN_WEIGHT, minWeight);
        bundle.putInt(MAX_WEIGHT, maxWight);
        bundle.putBoolean(ISNEGATIVE, isNegative);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_dialog_weight);
        builder.setIcon(R.drawable.ic_launcher);

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_weight, null);
        builder.setView(view);

        lb = " " + getString(R.string.lb);
        loadedWeight = getString(R.string.title_loaded_weight_dia);

        minWeight = getArguments().getInt(MIN_WEIGHT);
        maxWeight = getArguments().getInt(MAX_WEIGHT);

        textViewDialogLoadedWeight = (TextView) view.findViewById(R.id.textViewDialogLoadedWeight);
        textViewDialogLoadedWeight.setText(loadedWeight + " " + weight + lb);

        TextView textViewDialogMinMaxWeight = (TextView) view.findViewById(R.id.textViewMinMaxDialogWeight);
        textViewDialogMinMaxWeight.setText((getArguments().getBoolean(ISNEGATIVE) ? getString(R.string.title_loaded_max_weight_dia) + " " + maxWeight :
                getString(R.string.title_loaded_min_weight_dia) + " " + minWeight) + lb);


        view.findViewById(R.id.btn_dia_key_0).setOnClickListener(this);
        view.findViewById(R.id.btn_dia_key_1).setOnClickListener(this);
        view.findViewById(R.id.btn_dia_key_2).setOnClickListener(this);
        view.findViewById(R.id.btn_dia_key_3).setOnClickListener(this);
        view.findViewById(R.id.btn_dia_key_4).setOnClickListener(this);
        view.findViewById(R.id.btn_dia_key_5).setOnClickListener(this);
        view.findViewById(R.id.btn_dia_key_6).setOnClickListener(this);
        view.findViewById(R.id.btn_dia_key_7).setOnClickListener(this);
        view.findViewById(R.id.btn_dia_key_8).setOnClickListener(this);
        view.findViewById(R.id.btn_dia_key_9).setOnClickListener(this);
        view.findViewById(R.id.btn_dia_key_clear).setOnClickListener(this);


        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (weight >= minWeight) {
                    onNewWeightFromDialog.onNewWeghtFromDialog(weight);
                } else {
                    Toast.makeText(getActivity(), getString(R.string.warning_message_weight_min_dia) + " " + minWeight + lb, Toast.LENGTH_SHORT).show();
                    DialogWeight dialogWeight = DialogWeight.newInstance(minWeight, maxWeight, getArguments().getBoolean(ISNEGATIVE));
                    dialogWeight.show(getFragmentManager(), "dia_wei");
                }

            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }


    public void setOnNewWeightFromDialog(OnNewWeightFromDialog onNewWeightFromDialog) {
        this.onNewWeightFromDialog = onNewWeightFromDialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dia_key_0:
                valueWeight(0);
                break;
            case R.id.btn_dia_key_1:
                valueWeight(1);
                break;
            case R.id.btn_dia_key_2:
                valueWeight(2);
                break;
            case R.id.btn_dia_key_3:
                valueWeight(3);
                break;
            case R.id.btn_dia_key_4:
                valueWeight(4);
                break;
            case R.id.btn_dia_key_5:
                valueWeight(5);
                break;
            case R.id.btn_dia_key_6:
                valueWeight(6);
                break;
            case R.id.btn_dia_key_7:
                valueWeight(7);
                break;
            case R.id.btn_dia_key_8:
                valueWeight(8);
                break;
            case R.id.btn_dia_key_9:
                valueWeight(9);
                break;
            case R.id.btn_dia_key_clear:
                if (weight > 0) {
                    weight = 0;
                    textViewDialogLoadedWeight.setText(loadedWeight + " " + weight + lb);
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

        if (weightAux <= maxWeight) {
            weight = weightAux;
            textViewDialogLoadedWeight.setText(loadedWeight + " " + weight + lb);
        } else {
            Toast.makeText(getActivity(), getString(R.string.warning_message_weight_max_dia) + " " + maxWeight + lb, Toast.LENGTH_SHORT).show();
        }

    }



}
