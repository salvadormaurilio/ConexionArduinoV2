package com.google.android.gms.example.conexionarduinov2.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.google.android.gms.example.conexionarduinov2.R;


/**
 * Created by sati on 17/11/2014.
 */
public class DialogExit extends DialogFragment {


    private OnListenerExit listenerExit;


    public DialogExit() {
    }

    public interface OnListenerExit {
        public void onListenerExit();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listenerExit = (OnListenerExit) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_exit);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setMessage(R.string.message_exit);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listenerExit.onListenerExit();

            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);

        return builder.create();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listenerExit = null;
    }


}
