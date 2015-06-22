package com.google.android.gms.example.conexionarduinov2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.android.gms.example.conexionarduinov2.R;
import com.google.android.gms.example.conexionarduinov2.models.InfoExerciseModel;
import com.google.android.gms.example.conexionarduinov2.models.ItemDropsetAndNegativePositive;
import com.google.android.gms.example.conexionarduinov2.utils.Constans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sati on 01/02/2015.
 */
public class ExercisesDataSource {

    private UserSQLiteOpenHelper userSQLiteOpenHelper;

    public ExercisesDataSource(Context context) {
        this.userSQLiteOpenHelper = new UserSQLiteOpenHelper(context, "DBUsers", null, 1);
    }

    public long insertExercise(long idUser, int day, int month, int year, int typeExercise, int typeTraining, List<ItemDropsetAndNegativePositive> itemDropsetAndNegativePositives, int weight) {

        Log.d(Constans.TAG_DB, "insertExercise");
        long id;

//        int numDate = getNumDate(day, month, year);

        int numDate = 1;
        String repetitions = ItemDropsetAndNegativePositive.toStringRetitions(itemDropsetAndNegativePositives);

        SQLiteDatabase database = userSQLiteOpenHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constans.ID_USER_DB, idUser);
        contentValues.put(Constans.TYPE_EXERCISE_DB, typeExercise);
        contentValues.put(Constans.TYPE_TRAINING_DB, typeTraining);
        contentValues.put(Constans.DAY_DB, day);
        contentValues.put(Constans.MONTH_DB, month);
        contentValues.put(Constans.YEAR_DB, year);
        contentValues.put(Constans.WEIGHT_DB, weight);
        contentValues.put(Constans.REPETITIONS_DB, repetitions);
        contentValues.put(Constans.NUM_DATE_DB, numDate);

        Log.d(Constans.TAG_DB, "Curso: "+contentValues.toString());

        id = database.insert(Constans.NAME_TABLE_USER_EXERCISES_DB, null, contentValues);
        database.close();

        Log.d(Constans.TAG_DB, "IdExcer " + id);
        return id;
    }

    private int getNumDate(int day, int month, int year) {

        SQLiteDatabase database = userSQLiteOpenHelper.getReadableDatabase();

        String[] columns = new String[]{Constans.ID_DB, Constans.DAY_DB, Constans.MONTH_DB, Constans.YEAR_DB, Constans.NUM_DATE_DB};
        Cursor cursor = database.query(Constans.NAME_TABLE_USER_EXERCISES_DB, columns, null, null, null, null, null, "1");

        if (cursor.moveToFirst()) {
            Log.d(Constans.TAG_DB, "HaveData");
            Log.d(Constans.TAG_DB, "day: " + cursor.getInt(0) + ", month: " + cursor.getInt(1) + "year: " + cursor.getInt(2));

            if (cursor.getInt(0) != day || cursor.getInt(1) != month || cursor.getInt(2) != year) {
                int numDate = cursor.getInt(3) + 1;
                deleteExercise(numDate);

                if (numDate > Constans.NUM_DATE_ALL_DB) {
                    numDate = 1;
                }
                return numDate;
            } else {
                return cursor.getInt(3);
            }

        } else {
            Log.d(Constans.TAG_DB, "NoHaveData");

            return 1;
        }
    }

    private void deleteExercise(int numDate) {

        SQLiteDatabase database = userSQLiteOpenHelper.getWritableDatabase();
        database.delete(Constans.NAME_TABLE_USER_EXERCISES_DB, Constans.NUM_DATE_DB + " =? ", new String[]{String.valueOf(numDate)});
        database.close();

    }


    public long insertOtherExercise(long idUser, int day, int month, int year, String name, int typeExercise, int typeTraining, List<ItemDropsetAndNegativePositive> itemDropsetAndNegativePositives, int weight) {

        Log.d(Constans.TAG_DB, "insertOtherExercise");
        long id;

//        int numDate = getNumDateOther(day, month, year);
        int numDate = 1;
        String repetitions = ItemDropsetAndNegativePositive.toStringRetitions(itemDropsetAndNegativePositives);

        SQLiteDatabase database = userSQLiteOpenHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constans.ID_USER_DB, idUser);
        contentValues.put(Constans.DAY_DB, day);
        contentValues.put(Constans.MONTH_DB, month);
        contentValues.put(Constans.YEAR_DB, year);
        contentValues.put(Constans.NAME_DB, name);
        contentValues.put(Constans.TYPE_EXERCISE_DB, typeExercise);
        contentValues.put(Constans.TYPE_TRAINING_DB, typeTraining);
        contentValues.put(Constans.WEIGHT_DB, weight);
        contentValues.put(Constans.REPETITIONS_DB, repetitions);
        contentValues.put(Constans.NUM_DATE_DB, numDate);

        Log.d(Constans.TAG_DB, "Curso: "+contentValues.toString());

        id = database.insert(Constans.NAME_TABLE_USER_OTHER_EXERCISES_DB, null, contentValues);
        database.close();

        Log.d(Constans.TAG_DB, "IdExcer " + id);
        return id;
    }

    private int getNumDateOther(int day, int month, int year) {

        SQLiteDatabase database = userSQLiteOpenHelper.getReadableDatabase();

        String[] columns = new String[]{Constans.ID_DB, Constans.DAY_DB, Constans.MONTH_DB, Constans.YEAR_DB, Constans.NUM_DATE_DB};
        Cursor cursor = database.query(Constans.NAME_TABLE_USER_OTHER_EXERCISES_DB, columns, null, null, null, null, null, "1");

        if (cursor.moveToFirst()) {
            Log.d(Constans.TAG_DB, "HaveData");
            Log.d(Constans.TAG_DB, "day: " + cursor.getInt(0) + ", month: " + cursor.getInt(1) + "year: " + cursor.getInt(2));

            if (cursor.getInt(0) != day || cursor.getInt(1) != month || cursor.getInt(2) != year) {
                int numDate = cursor.getInt(3) + 1;
                deleteExerciseOther(numDate);

                if (numDate > Constans.NUM_DATE_ALL_DB) {
                    numDate = 1;
                }
                return numDate;
            } else {
                return cursor.getInt(3);
            }

        } else {
            Log.d(Constans.TAG_DB, "NoHaveData");

            return 1;
        }
    }

    private void deleteExerciseOther(int numDate) {

        SQLiteDatabase database = userSQLiteOpenHelper.getWritableDatabase();
        database.delete(Constans.NAME_TABLE_USER_OTHER_EXERCISES_DB, Constans.NUM_DATE_DB + " =? ", new String[]{String.valueOf(numDate)});
        database.close();

    }

    public List<InfoExerciseModel> queryExercises(Context context, long idUser, int typeExercise) {


        Log.d(Constans.TAG_DB, "queryExercises");
        Log.d(Constans.TAG_DB, "idUser: " + idUser + ", TypeExer: " + typeExercise);

        List<InfoExerciseModel> infoExerciseModelList = new ArrayList<>();

        SQLiteDatabase database = userSQLiteOpenHelper.getReadableDatabase();

        String[] columns = new String[]{Constans.ID_DB, Constans.DAY_DB, Constans.MONTH_DB, Constans.YEAR_DB, Constans.TYPE_TRAINING_DB, Constans.WEIGHT_DB, Constans.REPETITIONS_DB};
        Cursor cursor = database.query(Constans.NAME_TABLE_USER_EXERCISES_DB, columns, Constans.ID_USER_DB + " =? AND " + Constans.TYPE_EXERCISE_DB + " =?", new String[]{idUser + "", typeExercise + ""}, null, null, null);

        if (cursor.moveToFirst()) {
            Log.d(Constans.TAG_DB, "EnterFirst");
            int typeTraining;
            String[] trainings = context.getResources().getStringArray(R.array.trainings);
            do {
                typeTraining = cursor.getInt(4);
                infoExerciseModelList.add(new InfoExerciseModel(cursor.getLong(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), typeTraining, trainings[typeTraining - 1], cursor.getInt(5), cursor.getString(6)));
            } while (cursor.moveToNext());
        }

        database.close();

        return infoExerciseModelList;
    }


    public List<InfoExerciseModel> queryOtherExercises(Context context, long idUser, int typeExercise) {


        Log.d(Constans.TAG_DB, "queryOtherExercises");
        Log.d(Constans.TAG_DB, "idUser: " + idUser + ", TypeExer: " + typeExercise);

        List<InfoExerciseModel> infoExerciseModelList = new ArrayList<>();

        SQLiteDatabase database = userSQLiteOpenHelper.getReadableDatabase();

        String[] columns = new String[]{Constans.ID_DB, Constans.DAY_DB, Constans.MONTH_DB, Constans.YEAR_DB, Constans.NAME_DB, Constans.TYPE_TRAINING_DB, Constans.WEIGHT_DB, Constans.REPETITIONS_DB};
        Cursor cursor = database.query(Constans.NAME_TABLE_USER_OTHER_EXERCISES_DB, columns, Constans.ID_USER_DB + " =? AND " + Constans.TYPE_EXERCISE_DB + " =?", new String[]{idUser + "", typeExercise + ""}, null, null, null);

        if (cursor.moveToFirst()) {
            Log.d(Constans.TAG_DB, "EnterFirstOther");

            int typeTraining;
            String[] trainings = context.getResources().getStringArray(R.array.trainings);
            do {
                typeTraining = cursor.getInt(5);
                infoExerciseModelList.add(new InfoExerciseModel(cursor.getLong(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4), typeTraining, trainings[typeTraining - 1], cursor.getInt(6), cursor.getString(7)));
            } while (cursor.moveToNext());

        }
        database.close();

        return infoExerciseModelList;
    }


    public InfoExerciseModel queryExercise(Context context, long idExercise, long idUser) {


        Log.d(Constans.TAG_DB, "queryExercise");
        Log.d(Constans.TAG_DB, "idUser: " + idUser + ", idExercise: " + idExercise);

        InfoExerciseModel infoExerciseModel = null;

        SQLiteDatabase database = userSQLiteOpenHelper.getReadableDatabase();

        String[] columns = new String[]{Constans.DAY_DB, Constans.MONTH_DB, Constans.YEAR_DB, Constans.TYPE_TRAINING_DB, Constans.WEIGHT_DB, Constans.REPETITIONS_DB};
        Cursor cursor = database.query(Constans.NAME_TABLE_USER_EXERCISES_DB, columns, Constans.ID_DB + " =? AND " + Constans.ID_USER_DB + " =?", new String[]{idExercise + "", idUser + ""}, null, null, null);

        if (cursor.moveToFirst()) {
            Log.d(Constans.TAG_DB, "EnterFirst");
            int typeTraining = cursor.getInt(3);
            String[] trainings = context.getResources().getStringArray(R.array.trainings);
            infoExerciseModel = new InfoExerciseModel(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), typeTraining, trainings[typeTraining - 1], cursor.getInt(4), ItemDropsetAndNegativePositive.toListRepetitions(cursor.getString(5)));

        }
        database.close();

        return infoExerciseModel;
    }


    public InfoExerciseModel queryOtherExercise(Context context, long idExercise, long idUser) {


        Log.d(Constans.TAG_DB, "queryExercise");
        Log.d(Constans.TAG_DB, "idUser: " + idUser + ", idExercise: " + idExercise);

        InfoExerciseModel infoExerciseModel = null;


        SQLiteDatabase database = userSQLiteOpenHelper.getReadableDatabase();

        String[] columns = new String[]{Constans.DAY_DB, Constans.MONTH_DB, Constans.YEAR_DB, Constans.NAME_DB, Constans.TYPE_TRAINING_DB, Constans.WEIGHT_DB, Constans.REPETITIONS_DB};
        Cursor cursor = database.query(Constans.NAME_TABLE_USER_OTHER_EXERCISES_DB, columns, Constans.ID_DB + " =? AND " + Constans.ID_USER_DB + " =?", new String[]{idExercise + "", idUser + ""}, null, null, null);

        if (cursor.moveToFirst()) {
            Log.d(Constans.TAG_DB, "EnterFirstOther");
            int typeTraining = cursor.getInt(4);
            String[] trainings = context.getResources().getStringArray(R.array.trainings);
            infoExerciseModel = new InfoExerciseModel(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), typeTraining, trainings[typeTraining - 1], cursor.getInt(5), ItemDropsetAndNegativePositive.toListRepetitions(cursor.getString(6)));

        }
        database.close();

        return infoExerciseModel;
    }


    public List<ItemDropsetAndNegativePositive> queryRepetitions(long idExercise) {


        Log.d(Constans.TAG_DB, "queryRepetitions");
        Log.d(Constans.TAG_DB, "idExercise: " + idExercise);

        List<ItemDropsetAndNegativePositive> list = null;

        SQLiteDatabase database = userSQLiteOpenHelper.getReadableDatabase();

        String[] columns = new String[]{Constans.REPETITIONS_DB};
        Cursor cursor = database.query(Constans.NAME_TABLE_USER_EXERCISES_DB, columns, Constans.ID_DB + " =? ", new String[]{idExercise + "",}, null, null, null);

        if (cursor.moveToFirst()) {
            Log.d(Constans.TAG_DB, "EnterFirst");
            String repetitions = cursor.getString(0);
            list = ItemDropsetAndNegativePositive.toListRepetitions(repetitions);
        }
        database.close();

        return list;
    }


    public List<ItemDropsetAndNegativePositive> queryOtherRepetitions(long idExercise) {


        Log.d(Constans.TAG_DB, "queryOtherRepetitions");
        Log.d(Constans.TAG_DB, "idExercise: " + idExercise);

        List<ItemDropsetAndNegativePositive> list = null;

        SQLiteDatabase database = userSQLiteOpenHelper.getReadableDatabase();

        String[] columns = new String[]{Constans.REPETITIONS_DB};
        Cursor cursor = database.query(Constans.NAME_TABLE_USER_OTHER_EXERCISES_DB, columns, Constans.ID_DB + " =? ", new String[]{idExercise + "",}, null, null, null);

        if (cursor.moveToFirst()) {
            Log.d(Constans.TAG_DB, "EnterFirstOther");

            String repetitions = cursor.getString(0);
            list = ItemDropsetAndNegativePositive.toListRepetitions(repetitions);
        }
        database.close();

        return list;
    }


}
