package com.google.android.gms.example.conexionarduinov2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.android.gms.example.conexionarduinov2.R;
import com.google.android.gms.example.conexionarduinov2.models.InfoExerciseModel;
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

    public long insertExercise(long idUser, int typeExercise, int typeTraining, String date, int weight) {

        Log.d(Constans.TAG_DB, "insertExercise");
        Log.d(Constans.TAG_DB, "idUser: " + idUser+", TypeExer: "+typeExercise+", typeTra: "+typeTraining);


        long id;
        SQLiteDatabase database = userSQLiteOpenHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constans.ID_USER_DB, idUser);
        contentValues.put(Constans.TYPE_EXERCISE_DB, typeExercise);
        contentValues.put(Constans.TYPE_TRAINING_DB, typeTraining);
        contentValues.put(Constans.DATE_DB, date);
        contentValues.put(Constans.WEIGHT_DB, weight);

        id = database.insert(Constans.NAME_TABLE_USER_EXERCISES_DB, null, contentValues);
        database.close();

        Log.d(Constans.TAG_DB, "IdExcer "+id );
        return id;
    }


    public List<InfoExerciseModel> queryExercises(Context context, long idUser, int typeExercise) {


        Log.d(Constans.TAG_DB, "queryExercises");
        Log.d(Constans.TAG_DB, "idUser: " + idUser+", TypeExer: "+typeExercise);

        List<InfoExerciseModel> infoExerciseModelList = new ArrayList<>();

        SQLiteDatabase database = userSQLiteOpenHelper.getReadableDatabase();

        String[] columns = new String[]{Constans.ID_DB, Constans.TYPE_TRAINING_DB, Constans.DATE_DB, Constans.WEIGHT_DB};
        Cursor cursor = database.query(Constans.NAME_TABLE_USER_EXERCISES_DB, columns, Constans.ID_USER_DB + " =? AND " + Constans.TYPE_EXERCISE_DB + " =?", new String[]{idUser+"", typeExercise+""}, null, null, null);

        if (cursor.moveToFirst()) {

            int typeTraining;
            String[] trainings = context.getResources().getStringArray(R.array.trainings);
            do {
                typeTraining = cursor.getInt(1);
                infoExerciseModelList.add(new InfoExerciseModel(cursor.getLong(0), typeTraining, trainings[typeTraining], cursor.getString(2), cursor.getInt(3)));
            } while (cursor.moveToNext());

        }
        database.close();

        return infoExerciseModelList;
    }


}
