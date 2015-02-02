package com.google.android.gms.example.conexionarduinov2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.example.conexionarduinov2.utils.Constans;

/**
 * Created by sati on 31/01/2015.
 */
public class UserSQLiteOpenHelper extends SQLiteOpenHelper {


    public UserSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlNameTable = "CREATE TABLE " + Constans.NAME_TABLE_USER_DB + " ( " + Constans.ID_DB + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Constans.NAME_USER_DB + " TEXT, " + Constans.PASSWORD_DB + " TEXT, " + Constans.HEIGHT_DB + " TEXT, "+Constans.TYPE_UNITS_DB+" INTEGER)";
        db.execSQL(sqlNameTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
