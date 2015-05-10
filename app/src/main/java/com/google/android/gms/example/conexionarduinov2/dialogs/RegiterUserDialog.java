package com.google.android.gms.example.conexionarduinov2.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.android.gms.example.conexionarduinov2.R;
import com.google.android.gms.example.conexionarduinov2.adapters.AdapterHeight;
import com.google.android.gms.example.conexionarduinov2.utils.Constans;
import com.google.android.gms.example.conexionarduinov2.utils.interfaces.OnUserInfoListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sati on 10/01/2015.
 */
public class RegiterUserDialog extends DialogFragment implements RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {


    private EditText editTextUser;
    private EditText editTextPassword;
    private EditText editTextConfirmPassWord;
    private Spinner spinnerHeight;

    private List<String> unitsCm;
    private List<String> unitsFt;
    private AdapterHeight adapterHeight;
    private String height;
    private int typeUnits;
    private int positionHeight;

    private OnUserInfoListener userInfoListener;


    public RegiterUserDialog() {
        unitsCm = new ArrayList<>();
        unitsFt = new ArrayList<>();
    }

    public static RegiterUserDialog newInstance(String user, int positionHeigth, int typeUnits) {
        RegiterUserDialog regiterUserDialog = new RegiterUserDialog();
        Bundle bundle = new Bundle();
        bundle.putString(Constans.NAME_USER_DIALOG, user);
        bundle.putInt(Constans.POSITION_HEIGTH_DIALOG, positionHeigth);
        bundle.putInt(Constans.TYPE_UNITS_DIALOG, typeUnits);
        regiterUserDialog.setArguments(bundle);
        return regiterUserDialog;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        userInfoListener = (OnUserInfoListener) activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.text_register);
        builder.setIcon(R.drawable.ic_launcher);

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_regiter_user, null);
        builder.setView(view);

        Bundle arguments = getArguments();

        editTextUser = (EditText) view.findViewById(R.id.editTextUser);
        editTextUser.setText(arguments.getString(Constans.NAME_USER_DIALOG));

        editTextPassword = (EditText) view.findViewById(R.id.editTextPassword);
        editTextConfirmPassWord = (EditText) view.findViewById(R.id.editTextConfirmPassWord);

        for (int i = 0; i <= 100; i++) unitsCm.add((100 + i) + "");
        for (int i = 0; i <= 36; i++) unitsFt.add((3.0 + (i / 10.0)) + "");

        spinnerHeight = (Spinner) view.findViewById(R.id.spinnerHeight);
        adapterHeight = new AdapterHeight(getActivity(), arguments.getInt(Constans.TYPE_UNITS_DIALOG, 1) == 1 ? unitsCm : unitsFt);
        spinnerHeight.setAdapter(adapterHeight);
        spinnerHeight.setOnItemSelectedListener(this);

        positionHeight = arguments.getInt(Constans.POSITION_HEIGTH_DIALOG, 0);
        spinnerHeight.setSelection(positionHeight);
        typeUnits = 1;
        height = "";

        RadioGroup radioGroupUnits = (RadioGroup) view.findViewById(R.id.radioGroupUnits);
        radioGroupUnits.setOnCheckedChangeListener(this);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                userInfoListener.onUserInfoRegiter(editTextUser.getText().toString(), editTextPassword.getText().toString(), editTextConfirmPassWord.getText().toString(), positionHeight, height, typeUnits);
            }
        });

        builder.setNegativeButton(android.R.string.cancel, null);

        return builder.create();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        height = (String) adapterHeight.getItem(position);
        positionHeight = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {
            case R.id.radioButtonCm:
                adapterHeight.setUnits(unitsCm);
                spinnerHeight.setSelection(0);
                typeUnits = 1;
                break;
            case R.id.radioButtonFt:
                adapterHeight.setUnits(unitsFt);
                spinnerHeight.setSelection(0);
                typeUnits = 2;
                break;

        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        userInfoListener = null;
    }
}
