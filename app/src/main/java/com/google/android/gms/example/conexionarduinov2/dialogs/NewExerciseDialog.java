package com.google.android.gms.example.conexionarduinov2.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;

import com.google.android.gms.example.conexionarduinov2.R;
import com.google.android.gms.example.conexionarduinov2.utils.Constans;

/**
 * Created by sati on 10/05/2015.
 */
public class NewExerciseDialog extends DialogFragment {

    private OnNewExerciseListener onNewExerciseListener;


    public static NewExerciseDialog newInstance(int typeExercise) {
        NewExerciseDialog newExerciseDialog = new NewExerciseDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(Constans.ARG_TYPE_EXERCISE, typeExercise);
        newExerciseDialog.setArguments(bundle);
        return newExerciseDialog;

    }

    public interface OnNewExerciseListener {
        public void onNewExercise(int typeExercise);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onNewExerciseListener = (OnNewExerciseListener) activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.text_new_exercise);

        TextView textView = (TextView) getActivity().getLayoutInflater().inflate(R.layout.dialog_warm_up_session_start_again, null);
        textView.setText(R.string.text_want_new_exercise);
        builder.setView(textView);

        builder.setNegativeButton(R.string.text_exit, null);
        builder.setPositiveButton(R.string.text_start, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onNewExerciseListener.onNewExercise(getArguments().getInt(Constans.ARG_TYPE_EXERCISE));
            }
        });

        return builder.create();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onNewExerciseListener = null;
    }
}
