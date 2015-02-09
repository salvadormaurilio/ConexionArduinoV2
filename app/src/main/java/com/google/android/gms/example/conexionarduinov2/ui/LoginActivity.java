package com.google.android.gms.example.conexionarduinov2.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.example.conexionarduinov2.R;
import com.google.android.gms.example.conexionarduinov2.database.UserDataSource;
import com.google.android.gms.example.conexionarduinov2.dialogs.LoginUserDialog;
import com.google.android.gms.example.conexionarduinov2.dialogs.RegiterUserDialog;
import com.google.android.gms.example.conexionarduinov2.models.UserInfoModel;
import com.google.android.gms.example.conexionarduinov2.utils.Constans;
import com.google.android.gms.example.conexionarduinov2.utils.OnUserInfoListener;


public class LoginActivity extends ActionBarActivity implements View.OnClickListener, OnUserInfoListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.buttonLogin).setOnClickListener(this);
        findViewById(R.id.buttonRegister).setOnClickListener(this);
        findViewById(R.id.buttonGuest).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonLogin:
                showLoginDialog("");
                break;
            case R.id.buttonRegister:
                showRegisterDialog("", 0, 1);
                break;
            case R.id.buttonGuest:
                Intent intent = new Intent(LoginActivity.this, SelectExerciseActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void showLoginDialog(String userName) {
        LoginUserDialog loginUserDialog = LoginUserDialog.newInstance(userName);
        loginUserDialog.show(getSupportFragmentManager(), "dialog_login");
    }

    private void showRegisterDialog(String userName, int positionHeigth, int typeUnits) {
        RegiterUserDialog regiterUserDialog = RegiterUserDialog.newInstance(userName, positionHeigth, typeUnits);
        regiterUserDialog.show(getSupportFragmentManager(), "dialog_regiter");

    }


    @Override
    public void onUserInfoRegiter(String userName, String password, String confirmPassword, int positionHeight, String height, int typeUnits) {
        if (TextUtils.isEmpty(userName)) {
            showRegisterDialog("", positionHeight, typeUnits);
            Toast.makeText(LoginActivity.this, R.string.message_empty_user_name, Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password) && TextUtils.isEmpty(confirmPassword)) {
            showRegisterDialog(userName, positionHeight, typeUnits);
            Toast.makeText(LoginActivity.this, R.string.message_empty_password, Toast.LENGTH_SHORT).show();
        } else if (password.compareTo(confirmPassword) != 0) {
            showRegisterDialog(userName, positionHeight, typeUnits);
            Toast.makeText(LoginActivity.this, R.string.message_different_password, Toast.LENGTH_SHORT).show();
        } else {
            UserDataSource userDataSource = new UserDataSource(LoginActivity.this);
            if (!userDataSource.exitsUser(userName)) {
                long id = userDataSource.insertUser(userName, password, height, typeUnits);
                SharedPreferences.Editor editor = getSharedPreferences(Constans.USER_PREFERENCES, MODE_PRIVATE).edit();
                editor.putLong(Constans.ID_PREFERENCES, id);
                editor.putBoolean(Constans.IS_LOGIN_PREFERENCES, true);
                editor.apply();

                Intent intent = new Intent(LoginActivity.this, SelectExerciseActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(LoginActivity.this, getString(R.string.message_welcome) + " " + userName, Toast.LENGTH_LONG).show();

            } else {
                showRegisterDialog(userName, positionHeight, typeUnits);
                Toast.makeText(LoginActivity.this, R.string.message_user_exits, Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onUserInfoLogin(String userName, String password) {

        if (TextUtils.isEmpty(userName)) {
            showLoginDialog("");
            Toast.makeText(LoginActivity.this, R.string.message_empty_user_name, Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            showLoginDialog(userName);
            Toast.makeText(LoginActivity.this, R.string.message_empty_password, Toast.LENGTH_SHORT).show();
        } else {
            UserDataSource userDataSource = new UserDataSource(LoginActivity.this);
            if (userDataSource.exitsUser(userName)) {
                UserInfoModel userInfoModel = userDataSource.queryInfoUser(userName, password);

                if (userInfoModel != null) {
                    SharedPreferences.Editor editor = getSharedPreferences(Constans.USER_PREFERENCES, MODE_PRIVATE).edit();
                    editor.putLong(Constans.ID_PREFERENCES, userInfoModel.getId());
                    editor.putBoolean(Constans.IS_LOGIN_PREFERENCES, true);
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, SelectExerciseActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(LoginActivity.this, getString(R.string.message_welcome) + " " + userName, Toast.LENGTH_LONG).show();
                } else {
                    showLoginDialog(userName);
                    Toast.makeText(LoginActivity.this, R.string.message_incorrect_password, Toast.LENGTH_SHORT).show();
                }
            } else {
                showLoginDialog("");
                Toast.makeText(LoginActivity.this, R.string.message_user_not_exits, Toast.LENGTH_SHORT).show();
            }

        }
    }


}
