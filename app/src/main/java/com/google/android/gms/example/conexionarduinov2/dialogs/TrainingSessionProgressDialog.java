package com.google.android.gms.example.conexionarduinov2.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.example.conexionarduinov2.R;
import com.google.android.gms.example.conexionarduinov2.utils.interfaces.OnStartTrainingSessionListener;

/**
 * Created by sati on 10/05/2015.
 */
public class TrainingSessionProgressDialog extends DialogFragment {

    private OnStartTrainingSessionListener onStartTrainingSessionListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onStartTrainingSessionListener = (OnStartTrainingSessionListener) activity;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.text_training_session);

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_session_progress, null);

        TextView textView = (TextView) view.findViewById(R.id.textViewSessionProgress);
        textView.setText(R.string.text_training_machine);

        builder.setView(view);
//
//        builder.setNegativeButton(R.string.text_exit, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                onStartTrainingSessionListener.onExitTrainingSession();
//            }
//        });

        return builder.create();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onStartTrainingSessionListener = null;
    }
}
