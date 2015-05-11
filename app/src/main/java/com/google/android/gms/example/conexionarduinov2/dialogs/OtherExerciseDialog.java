package com.google.android.gms.example.conexionarduinov2.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.LabeledIntent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.example.conexionarduinov2.R;


/**
 * Created by sati on 17/11/2014.
 */
public class OtherExerciseDialog extends DialogFragment {

    private OnListenerOtherExercise listenerOtherExercise;
    private EditText editTextExerciseName;

    public OtherExerciseDialog() {
    }

    public interface OnListenerOtherExercise {
        public void otherExercise(String exercise);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listenerOtherExercise = (OnListenerOtherExercise) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_other_excercise);
        builder.setIcon(R.drawable.ic_launcher);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_other_exercise, null);
        editTextExerciseName = (EditText) view.findViewById(R.id.editTextExerciseName);

        builder.setView(view);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                listenerOtherExercise.otherExercise(editTextExerciseName.getText().toString());

            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);

        return builder.create();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        listenerOtherExercise = null;
    }


}
