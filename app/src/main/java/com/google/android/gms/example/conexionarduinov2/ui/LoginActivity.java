package com.google.android.gms.example.conexionarduinov2.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.example.conexionarduinov2.R;
import com.google.android.gms.example.conexionarduinov2.database.UserDataSource;
import com.google.android.gms.example.conexionarduinov2.dialogs.LoginUserDialog;
import com.google.android.gms.example.conexionarduinov2.dialogs.RegiterUserDialog;
import com.google.android.gms.example.conexionarduinov2.models.UserInfoModel;
import com.google.android.gms.example.conexionarduinov2.utils.Constans;
import com.google.android.gms.example.conexionarduinov2.utils.ConstantsService;
import com.google.android.gms.example.conexionarduinov2.utils.UsbConexionUtils;
import com.google.android.gms.example.conexionarduinov2.utils.interfaces.OnUserInfoListener;

import java.io.UnsupportedEncodingException;


public class LoginActivity extends ActionBarActivity implements View.OnClickListener, OnUserInfoListener {

    private boolean isConnectedArduino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        clearSharedPreferences();

        findViewById(R.id.buttonLogin).setOnClickListener(this);
        findViewById(R.id.textViewRegister).setOnClickListener(this);
        findViewById(R.id.buttonGuest).setOnClickListener(this);

        isConnectedArduino = UsbConexionUtils.findDevice(LoginActivity.this);
//        isConnectedArduino = true;

        if (isConnectedArduino) {
            if (Constans.IS_ENABLE_SEND_DATA)
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        UsbConexionUtils.sendData(LoginActivity.this, new byte[]{1});
                    }
                }, 800);
        }

        IntentFilter filter = new IntentFilter();
        filter.addAction(ConstantsService.USB_DEVICE_DETACHED);
        registerReceiver(mReceiver, filter);

    }


    private void clearSharedPreferences() {
        SharedPreferences.Editor editor = getSharedPreferences(Constans.USER_PREFERENCES, MODE_PRIVATE).edit();
        editor.clear().apply();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (ConstantsService.DEBUG) Log.d(ConstantsService.TAG, "onNewIntent() " + intent);

        Toast.makeText(LoginActivity.this, "onNewIntent", Toast.LENGTH_SHORT).show();

        if (UsbManager.ACTION_USB_DEVICE_ATTACHED.contains(intent.getAction())) {
            if (ConstantsService.DEBUG) Log.d(ConstantsService.TAG, "onNewIntent() " + intent);
            isConnectedArduino = UsbConexionUtils.findDevice(LoginActivity.this);
            if (isConnectedArduino) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        UsbConexionUtils.sendData(LoginActivity.this, new byte[]{1});
                    }
                }, 800);
            }
        }
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (ConstantsService.DEBUG) Log.d(ConstantsService.TAG, "onReceive() " + action);
            if (ConstantsService.USB_DEVICE_DETACHED.equals(action)) {
                Toast.makeText(LoginActivity.this, R.string.message_lost_connection, Toast.LENGTH_LONG).show();
                isConnectedArduino = false;
                finish();
            }
        }

    };


    @Override
    public void onClick(View v) {

        if (isConnectedArduino) {
            switch (v.getId()) {
                case R.id.buttonLogin:
                    showLoginDialog("");
                    break;
                case R.id.textViewRegister:
                    showRegisterDialog("", 0, 1);
                    break;
                case R.id.buttonGuest:
                    if (Constans.IS_ENABLE_SEND_DATA)
                        UsbConexionUtils.sendData(LoginActivity.this, new byte[]{2});
                    Intent intent = new Intent(LoginActivity.this, SelectExerciseActivity.class);
                    startActivity(intent);
                    break;
            }
        } else {
            Toast.makeText(LoginActivity.this, R.string.no_device_found, Toast.LENGTH_SHORT).show();

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
        } else if (!validateUserName(userName)) {
            showRegisterDialog(userName, positionHeight, typeUnits);
            Toast.makeText(LoginActivity.this, R.string.message_username_invalidate, Toast.LENGTH_SHORT).show();
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
                editor.putLong(Constans.ID_USER_PREFERENCES, id);
                editor.putBoolean(Constans.IS_LOGIN_PREFERENCES, true);
                editor.putInt(Constans.TYPE_EXERCISE_PREFERENCES, -1);
                editor.putInt(Constans.COUNT_POS_NEG_PREFERENCES, 0);
                editor.putBoolean(Constans.IS_TRAINING_SESSION_PREFERENCES, false);
                editor.apply();

                if (!sendUserName(userName)) {
                    Toast.makeText(LoginActivity.this, R.string.message_relocate_user_name, Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(LoginActivity.this, SelectExerciseActivity.class);
                startActivityForResult(intent, 1111);
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
                    editor.putLong(Constans.ID_USER_PREFERENCES, userInfoModel.getId());
                    editor.putBoolean(Constans.IS_LOGIN_PREFERENCES, true);
                    editor.putInt(Constans.TYPE_EXERCISE_PREFERENCES, -1);
                    editor.putInt(Constans.COUNT_POS_NEG_PREFERENCES, 0);
                    editor.putBoolean(Constans.IS_TRAINING_SESSION_PREFERENCES, false);
                    editor.apply();

                    if (!sendUserName(userName)) {
                        Toast.makeText(LoginActivity.this, R.string.message_relocate_user_name, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent intent = new Intent(LoginActivity.this, SelectExerciseActivity.class);
                    startActivityForResult(intent, 1111);
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


    private boolean validateUserName(String userName) {

        try {
            byte[] ansiBytes = userName.getBytes("UTF-8");

            for (byte chapter : ansiBytes) {
                if (chapter < 1) {
                    return false;
                }
            }
            return true;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }

    }


    private boolean sendUserName(String userName) {
        if (Constans.IS_ENABLE_SEND_DATA)
            UsbConexionUtils.sendData(LoginActivity.this, new byte[]{1});
        try {
            byte[] ansiBytes = userName.getBytes("UTF-8");

            for (byte chapter : ansiBytes) {
                if (Constans.IS_ENABLE_SEND_DATA)
                    UsbConexionUtils.sendData(LoginActivity.this, new byte[]{chapter});
            }
            if (Constans.IS_ENABLE_SEND_DATA)
                UsbConexionUtils.sendData(LoginActivity.this, new byte[]{0});

            return true;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        clearSharedPreferences();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("TAG_FINISH", "Destroy_LoginActivity");
        if (isConnectedArduino) {
            if (Constans.IS_ENABLE_SEND_DATA)
                UsbConexionUtils.sendData(LoginActivity.this, new byte[]{0});
        }

        unregisterReceiver(mReceiver);
    }
}
