package com.google.android.gms.example.conexionarduinov2.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.google.android.gms.example.conexionarduinov2.R;
import com.google.android.gms.example.conexionarduinov2.utils.interfaces.OnStartWarmUpSessionEvents;

/**
 * Created by sati on 10/05/2015.
 */
public class WarmUpSessionProgressDialog extends DialogFragment {


    private OnStartWarmUpSessionEvents onStartWarmUpSessionEvents;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onStartWarmUpSessionEvents = (OnStartWarmUpSessionEvents) activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.text_warm_up_session);

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_warm_up_session_progress, null);
        builder.setView(view);

//        builder.setNegativeButton(R.string.text_exit, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                onStartWarmUpSessionEvents.exitWarmUpSession();
//            }
//        });

        return builder.create();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onStartWarmUpSessionEvents = null;
    }
}
