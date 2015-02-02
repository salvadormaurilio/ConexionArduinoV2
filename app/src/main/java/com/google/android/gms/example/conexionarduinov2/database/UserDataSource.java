package com.google.android.gms.example.conexionarduinov2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.android.gms.example.conexionarduinov2.models.UserInfoModel;
import com.google.android.gms.example.conexionarduinov2.utils.Constans;

/**
 * Created by sati on 01/02/2015.
 */
public class UserDataSource {

    private UserSQLiteOpenHelper userSQLiteOpenHelper;

    public UserDataSource(Context context) {
        this.userSQLiteOpenHelper = new UserSQLiteOpenHelper(context, "DBUsers", null, 1);
    }


    public long insertUser(String userName, String password, String height, int typeUnit) {

        long id;
        SQLiteDatabase database = userSQLiteOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constans.NAME_USER_DB, userName);
        contentValues.put(Constans.PASSWORD_DB, password);
        contentValues.put(Constans.HEIGHT_DB, height);
        contentValues.put(Constans.TYPE_UNITS_DB, typeUnit);
        id = database.insert(Constans.NAME_TABLE_USER_DB, null, contentValues);
        database.close();

        Log.d("TAG_DB", id + "");
        return id;
    }


    public boolean exitsUser(String userName) {
        SQLiteDatabase database = userSQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = database.query(Constans.NAME_TABLE_USER_DB, new String[]{Constans.NAME_USER_DB}, Constans.NAME_USER_DB + "=?", new String[]{userName}, null, null, null);
        boolean existUser = cursor.moveToFirst();
        database.close();
        return existUser;
    }


    public UserInfoModel queryInfoUser(String userName, String password) {
        UserInfoModel userInfoModel = null;
        SQLiteDatabase database = userSQLiteOpenHelper.getReadableDatabase();

        String[] columns = new String[]{Constans.ID_DB, Constans.NAME_USER_DB, Constans.HEIGHT_DB, Constans.TYPE_UNITS_DB};
        Cursor cursor = database.query(Constans.NAME_TABLE_USER_DB, columns, Constans.NAME_USER_DB + " =? AND " + Constans.PASSWORD_DB + " =?", new String[]{userName, password}, null, null, null);

        if (cursor.moveToFirst()) {
            userInfoModel = new UserInfoModel(cursor.getLong(0), cursor.getString(1), cursor.getColumnName(2), cursor.getInt(3));
            Log.d("TAG_DB", userInfoModel.getId() + "");

        }

        return userInfoModel;
    }

}
