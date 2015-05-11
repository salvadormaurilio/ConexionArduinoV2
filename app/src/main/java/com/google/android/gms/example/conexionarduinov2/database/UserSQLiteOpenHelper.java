package com.google.android.gms.example.conexionarduinov2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

        String sqlNameTableUser = "CREATE TABLE " + Constans.NAME_TABLE_USER_DB + " ( " + Constans.ID_DB + " INTEGER PRIMARY KEY, " +
                Constans.NAME_USER_DB + " TEXT, " + Constans.PASSWORD_DB + " TEXT, " + Constans.HEIGHT_DB + " TEXT, " + Constans.TYPE_UNITS_DB + " INTEGER )";

        String sqlNameTableExercises = "CREATE TABLE " + Constans.NAME_TABLE_USER_EXERCISES_DB + " ( " + Constans.ID_DB + " INTEGER PRIMARY KEY, " +
                Constans.ID_USER_DB + " INTEGER, " + Constans.DATE_DB + " TEXT, " + Constans.TYPE_EXERCISE_DB + " INTEGER, " + Constans.TYPE_TRAINING_DB + " INTEGER, "
                + Constans.WEIGHT_DB + " INTEGER, " + Constans.REPETITIONS_DB + " TEXT )";


        String sqlNameTableOther = "CREATE TABLE " + Constans.NAME_TABLE_USER_OTHER_EXERCISES_DB + " ( " + Constans.ID_DB + " INTEGER PRIMARY KEY, " +
                Constans.ID_USER_DB + " INTEGER, " + Constans.DATE_DB + " TEXT, " + Constans.NAME_DB + " TEXT, " + Constans.TYPE_EXERCISE_DB + " INTEGER, "
                + Constans.TYPE_TRAINING_DB + " INTEGER, " + Constans.WEIGHT_DB + " INTEGER, " + Constans.REPETITIONS_DB + " TEXT )";


        Log.d("TAG_TABLE", sqlNameTableUser);
        Log.d("TAG_TABLE", sqlNameTableExercises);
        Log.d("TAG_TABLE", sqlNameTableOther);

        db.execSQL(sqlNameTableUser);
        db.execSQL(sqlNameTableExercises);
        db.execSQL(sqlNameTableOther);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
