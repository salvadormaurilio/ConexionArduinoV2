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

    public long insertExercise(long idUser, String date, int typeExercise, int typeTraining, List<ItemDropsetAndNegativePositive> itemDropsetAndNegativePositives, int weight) {

        Log.d(Constans.TAG_DB, "insertExercise");
        Log.d(Constans.TAG_DB, "idUser: " + idUser + ", TypeExer: " + typeExercise + ", typeTra: " + typeTraining);
        long id;

        String repetitions = ItemDropsetAndNegativePositive.toStringRetitions(itemDropsetAndNegativePositives);


        SQLiteDatabase database = userSQLiteOpenHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constans.ID_USER_DB, idUser);
        contentValues.put(Constans.TYPE_EXERCISE_DB, typeExercise);
        contentValues.put(Constans.TYPE_TRAINING_DB, typeTraining);
        contentValues.put(Constans.DATE_DB, date);
        contentValues.put(Constans.WEIGHT_DB, weight);
        contentValues.put(Constans.REPETITIONS_DB, repetitions);

        id = database.insert(Constans.NAME_TABLE_USER_EXERCISES_DB, null, contentValues);
        database.close();

        Log.d(Constans.TAG_DB, "IdExcer " + id);
        return id;
    }


    public long insertOtherExercise(long idUser, String date, String name, int typeExercise, int typeTraining, List<ItemDropsetAndNegativePositive> itemDropsetAndNegativePositives, int weight) {

        Log.d(Constans.TAG_DB, "insertExercise");
        Log.d(Constans.TAG_DB, "idUser: " + idUser + ", TypeExer: " + typeExercise + ", typeTra: " + typeTraining);
        long id;

        String repetitions = ItemDropsetAndNegativePositive.toStringRetitions(itemDropsetAndNegativePositives);


        SQLiteDatabase database = userSQLiteOpenHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constans.ID_USER_DB, idUser);
        contentValues.put(Constans.DATE_DB, date);
        contentValues.put(Constans.NAME_DB, name);
        contentValues.put(Constans.TYPE_EXERCISE_DB, typeExercise);
        contentValues.put(Constans.TYPE_TRAINING_DB, typeTraining);
        contentValues.put(Constans.WEIGHT_DB, weight);
        contentValues.put(Constans.REPETITIONS_DB, repetitions);

        id = database.insert(Constans.NAME_TABLE_USER_OTHER_EXERCISES_DB, null, contentValues);
        database.close();

        Log.d(Constans.TAG_DB, "IdExcer " + id);
        return id;
    }

    public List<InfoExerciseModel> queryExercises(Context context, long idUser, int typeExercise) {


        Log.d(Constans.TAG_DB, "queryExercises");
        Log.d(Constans.TAG_DB, "idUser: " + idUser + ", TypeExer: " + typeExercise);

        List<InfoExerciseModel> infoExerciseModelList = new ArrayList<>();

        SQLiteDatabase database = userSQLiteOpenHelper.getReadableDatabase();

        String[] columns = new String[]{Constans.ID_DB, Constans.DATE_DB, Constans.TYPE_TRAINING_DB, Constans.WEIGHT_DB, Constans.REPETITIONS_DB};
        Cursor cursor = database.query(Constans.NAME_TABLE_USER_EXERCISES_DB, columns, Constans.ID_USER_DB + " =? AND " + Constans.TYPE_EXERCISE_DB + " =?", new String[]{idUser + "", typeExercise + ""}, null, null, null);

        if (cursor.moveToFirst()) {

            int typeTraining;
            String[] trainings = context.getResources().getStringArray(R.array.trainings);
            do {
                typeTraining = cursor.getInt(2);
                infoExerciseModelList.add(new InfoExerciseModel(cursor.getLong(0), cursor.getString(1), typeTraining, trainings[typeTraining - 1], cursor.getInt(3), cursor.getString(4)));
            } while (cursor.moveToNext());

        }
        database.close();

        return infoExerciseModelList;
    }


    public List<InfoExerciseModel> queryOtherExercises(Context context, long idUser, int typeExercise) {


        Log.d(Constans.TAG_DB, "queryExercises");
        Log.d(Constans.TAG_DB, "idUser: " + idUser + ", TypeExer: " + typeExercise);

        List<InfoExerciseModel> infoExerciseModelList = new ArrayList<>();

        SQLiteDatabase database = userSQLiteOpenHelper.getReadableDatabase();

        String[] columns = new String[]{Constans.ID_DB, Constans.DATE_DB, Constans.NAME_DB, Constans.TYPE_TRAINING_DB, Constans.WEIGHT_DB, Constans.REPETITIONS_DB};
        Cursor cursor = database.query(Constans.NAME_TABLE_USER_OTHER_EXERCISES_DB, columns, Constans.ID_USER_DB + " =? AND " + Constans.TYPE_EXERCISE_DB + " =?", new String[]{idUser + "", typeExercise + ""}, null, null, null);

        if (cursor.moveToFirst()) {

            int typeTraining;
            String[] trainings = context.getResources().getStringArray(R.array.trainings);
            do {
                typeTraining = cursor.getInt(3);
                infoExerciseModelList.add(new InfoExerciseModel(cursor.getLong(0), cursor.getString(1), cursor.getString(2), typeTraining, trainings[typeTraining - 1], cursor.getInt(4), cursor.getString(5)));
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

        String[] columns = new String[]{Constans.DATE_DB, Constans.TYPE_TRAINING_DB, Constans.WEIGHT_DB, Constans.REPETITIONS_DB};
        Cursor cursor = database.query(Constans.NAME_TABLE_USER_EXERCISES_DB, columns, Constans.ID_DB + " =? AND " + Constans.ID_USER_DB + " =?", new String[]{idExercise + "", idUser + ""}, null, null, null);

        if (cursor.moveToFirst()) {

            int typeTraining = cursor.getInt(1);
            String[] trainings = context.getResources().getStringArray(R.array.trainings);
            infoExerciseModel = new InfoExerciseModel(cursor.getString(0), typeTraining, trainings[typeTraining - 1], cursor.getInt(2), ItemDropsetAndNegativePositive.toListRepetitions(cursor.getString(3)));

        }
        database.close();

        return infoExerciseModel;
    }


    public InfoExerciseModel queryOtherExercise(Context context, long idExercise, long idUser) {


        Log.d(Constans.TAG_DB, "queryExercise");
        Log.d(Constans.TAG_DB, "idUser: " + idUser + ", idExercise: " + idExercise);

        InfoExerciseModel infoExerciseModel = null;


        SQLiteDatabase database = userSQLiteOpenHelper.getReadableDatabase();

        String[] columns = new String[]{Constans.DATE_DB, Constans.NAME_DB, Constans.TYPE_TRAINING_DB, Constans.WEIGHT_DB, Constans.REPETITIONS_DB};
        Cursor cursor = database.query(Constans.NAME_TABLE_USER_OTHER_EXERCISES_DB, columns, Constans.ID_DB + " =? AND " + Constans.ID_USER_DB + " =?", new String[]{idExercise + "", idUser + ""}, null, null, null);

        if (cursor.moveToFirst()) {

            int typeTraining = cursor.getInt(2);
            String[] trainings = context.getResources().getStringArray(R.array.trainings);
            infoExerciseModel = new InfoExerciseModel(cursor.getString(0), cursor.getString(1), typeTraining, trainings[typeTraining - 1], cursor.getInt(3), ItemDropsetAndNegativePositive.toListRepetitions(cursor.getString(4)));

        }
        database.close();

        return infoExerciseModel;
    }


    public List<ItemDropsetAndNegativePositive> queryRepetitions(Context context, long idExercise) {


        Log.d(Constans.TAG_DB, "queryExercises");
        Log.d(Constans.TAG_DB, "idExercise: " + idExercise);

        List<ItemDropsetAndNegativePositive> list = null;

        SQLiteDatabase database = userSQLiteOpenHelper.getReadableDatabase();

        String[] columns = new String[]{Constans.REPETITIONS_DB};
        Cursor cursor = database.query(Constans.NAME_TABLE_USER_EXERCISES_DB, columns, Constans.ID_DB + " =? ", new String[]{idExercise + "",}, null, null, null);

        if (cursor.moveToFirst()) {

            String repetitions = cursor.getString(0);
            list = ItemDropsetAndNegativePositive.toListRepetitions(repetitions);
        }
        database.close();

        return list;
    }


    public List<ItemDropsetAndNegativePositive> queryOtherRepetitions(Context context, long idExercise) {


        Log.d(Constans.TAG_DB, "queryExercises");
        Log.d(Constans.TAG_DB, "idExercise: " + idExercise);

        List<ItemDropsetAndNegativePositive> list = null;

        SQLiteDatabase database = userSQLiteOpenHelper.getReadableDatabase();

        String[] columns = new String[]{Constans.REPETITIONS_DB};
        Cursor cursor = database.query(Constans.NAME_TABLE_USER_OTHER_EXERCISES_DB, columns, Constans.ID_DB + " =? ", new String[]{idExercise + "",}, null, null, null);

        if (cursor.moveToFirst()) {

            String repetitions = cursor.getString(0);
            list = ItemDropsetAndNegativePositive.toListRepetitions(repetitions);
        }
        database.close();

        return list;
    }


}
