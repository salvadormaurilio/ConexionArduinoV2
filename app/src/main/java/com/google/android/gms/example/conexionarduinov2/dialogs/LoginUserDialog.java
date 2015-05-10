package com.google.android.gms.example.conexionarduinov2.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.example.conexionarduinov2.R;
import com.google.android.gms.example.conexionarduinov2.utils.Constans;
import com.google.android.gms.example.conexionarduinov2.utils.interfaces.OnUserInfoListener;

/**
 * Created by sati on 10/01/2015.
 */
public class LoginUserDialog extends DialogFragment {

    private EditText editTextUser;
    private EditText editTextPassword;
    private OnUserInfoListener userInfoListener;


    public LoginUserDialog() {
    }

    public static LoginUserDialog newInstance(String user) {
        LoginUserDialog loginUserDialog = new LoginUserDialog();
        Bundle bundle = new Bundle();
        bundle.putString(Constans.NAME_USER_DIALOG, user);
        loginUserDialog.setArguments(bundle);
        return loginUserDialog;
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
        builder.setTitle(R.string.text_login);
        builder.setIcon(R.drawable.ic_launcher);

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_login_user, null);
        builder.setView(view);

        editTextUser = (EditText) view.findViewById(R.id.editTextUser);
        editTextUser.setText(getArguments().getString(Constans.NAME_USER_DIALOG));

        editTextPassword = (EditText) view.findViewById(R.id.editTextPassword);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userInfoListener.onUserInfoLogin(editTextUser.getText().toString(), editTextPassword.getText().toString());
            }
        });

        builder.setNegativeButton(android.R.string.cancel, null);

        return builder.create();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        userInfoListener = null;

    }
}
