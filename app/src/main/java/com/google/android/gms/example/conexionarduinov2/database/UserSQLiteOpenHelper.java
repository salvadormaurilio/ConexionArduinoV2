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

        String sqlNameTableUser = "CREATE TABLE " + Constans.NAME_TABLE_USER_DB + " ( " + Constans.ID_DB + " INTEGER PRIMARY KEY, " +
                Constans.NAME_USER_DB + " TEXT, " + Constans.PASSWORD_DB + " TEXT, " + Constans.HEIGHT_DB + " TEXT, "+Constans.TYPE_UNITS_DB+" INTEGER )";

        String sqlNameTableExercises = "CREATE TABLE " + Constans.NAME_TABLE_USER_EXERCISES_DB + " ( " + Constans.ID_DB + " INTEGER PRIMARY KEY, " +
                Constans.ID_USER_DB + " INTEGER, " + Constans.TYPE_EXERCISE_DB + " INTEGER, " +Constans.TYPE_TRAINING_DB+ " INTEGER, " +
                Constans.DATE_DB + " TEXT, " + Constans.WEIGHT_DB + " INTEGER )";

        String sqlNameTableDropset = "CREATE TABLE " + Constans.NAME_TABLE_TRAINING_DROSET_DB + " ( " + Constans.ID_DB + " INTEGER PRIMARY KEY, " +
                Constans.ID_EXERCISE_DB + " INTEGER, " + Constans.WEIGHTS_DB + " TEXT)";

        String sqlNameTableNegative = "CREATE TABLE " + Constans.NAME_TABLE_TRAINING_NEGATIVE_DB + " ( " + Constans.ID_DB + " INTEGER PRIMARY KEY, " +
                Constans.ID_EXERCISE_DB + " INTEGER, " + Constans.WEIGHTS_DB + " TEXT)";

        String sqlNameTablePosNeg = "CREATE TABLE " + Constans.NAME_TABLE_TRAINING_NEG_POS_DB + " ( " + Constans.ID_DB + " INTEGER PRIMARY KEY, " +
                Constans. ID_EXERCISE_DB + " INTEGER, " + Constans.WEIGHTS_NEGA_DB + " TEXT, " +Constans.WEIGHTS_POSI_DB + " TEXT)";


        db.execSQL(sqlNameTableUser);
        db.execSQL(sqlNameTableExercises);
        db.execSQL(sqlNameTableDropset);
        db.execSQL(sqlNameTableNegative);
        db.execSQL(sqlNameTablePosNeg);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
