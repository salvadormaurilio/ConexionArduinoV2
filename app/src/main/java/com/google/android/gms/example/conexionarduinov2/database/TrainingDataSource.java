package com.google.android.gms.example.conexionarduinov2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.android.gms.example.conexionarduinov2.models.WeightsPositiveNegativeModel;
import com.google.android.gms.example.conexionarduinov2.utils.Constans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sati on 01/02/2015.
 */
public class TrainingDataSource {

    private UserSQLiteOpenHelper userSQLiteOpenHelper;

    public TrainingDataSource(Context context) {
        this.userSQLiteOpenHelper = new UserSQLiteOpenHelper(context, "DBUsers", null, 1);
    }

    public long insertTrainingDropsetNegative(int idExercise, List<Integer> weightList, int typeTraining) {

        long id;
        SQLiteDatabase database = userSQLiteOpenHelper.getWritableDatabase();

        String weights = "";

        for (int i = 0; i < weightList.size(); i++) {
            if (i < weightList.size() - 1) {
                weights += weightList.get(i) + "-";
            } else {
                weights += weightList.get(i) + "";
            }
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constans.ID_EXERCISE_DB, idExercise);
        contentValues.put(Constans.WEIGHTS_DB, weights);

        id = database.insert(typeTraining == 1 ? Constans.NAME_TABLE_TRAINING_DROSET_DB : Constans.NAME_TABLE_TRAINING_NEGATIVE_DB, null, contentValues);
        database.close();

        Log.d("TAG_DB", id + "");
        return id;
    }

    public long insertTrainingNegativePositive(int idExercise, List<WeightsPositiveNegativeModel> weightsPositiveNegativeModels) {

        long id;
        SQLiteDatabase database = userSQLiteOpenHelper.getWritableDatabase();

        String weightsNegatives = "";
        String weightsPositives = "";

        for (int i = 0; i < weightsPositiveNegativeModels.size(); i++) {
            if (i < weightsPositiveNegativeModels.size() - 1) {
                weightsNegatives += weightsPositiveNegativeModels.get(i).getWeightNegative() + "-";
                weightsPositives += weightsPositiveNegativeModels.get(i).getWeightNegative() + "-";
            } else {
                weightsNegatives += weightsPositiveNegativeModels.get(i).getWeightPositive() + "";
                weightsPositives += weightsPositiveNegativeModels.get(i).getWeightPositive() + "";
            }
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constans.ID_EXERCISE_DB, idExercise);
        contentValues.put(Constans.WEIGHTS_NEGA_DB, weightsNegatives);
        contentValues.put(Constans.WEIGHTS_POSI_DB, weightsPositives);

        id = database.insert(Constans.NAME_TABLE_TRAINING_NEG_POS_DB, null, contentValues);
        database.close();

        Log.d("TAG_DB", id + "");
        return id;
    }


    public List<Integer> queryTraininDropsetNegative(int idExercise, int typeTraining) {


        List<Integer> weights = new ArrayList<>();

        SQLiteDatabase database = userSQLiteOpenHelper.getReadableDatabase();

        String[] columns = new String[]{Constans.WEIGHTS_DB};
        Cursor cursor = database.query(typeTraining == 1 ? Constans.NAME_TABLE_TRAINING_DROSET_DB : Constans.NAME_TABLE_TRAINING_NEGATIVE_DB, columns, Constans.ID_EXERCISE_DB + " =?",
                new String[]{idExercise + ""}, null, null, null);

        if (cursor.moveToFirst()) {

            String weightsString[] = cursor.getString(0).split("-");

            for (String weight : weightsString) {
                weights.add(Integer.parseInt(weight));
            }
        }
        database.close();

        return weights;
    }

    public List<WeightsPositiveNegativeModel> queryTraininDropsetNegative(int idExercise) {


        List<WeightsPositiveNegativeModel> positiveNegativeModels = new ArrayList<>();

        SQLiteDatabase database = userSQLiteOpenHelper.getReadableDatabase();

        String[] columns = new String[]{Constans.WEIGHTS_NEGA_DB, Constans.WEIGHTS_POSI_DB};
        Cursor cursor = database.query(Constans.NAME_TABLE_TRAINING_NEG_POS_DB, columns, Constans.ID_EXERCISE_DB + " =?",
                new String[]{idExercise + ""}, null, null, null);

        if (cursor.moveToFirst()) {

            String weightsNegativeString[] = cursor.getString(0).split("-");
            String weightsPositiveString[] = cursor.getString(1).split("-");

            for (int i = 0; i < weightsNegativeString.length; i++) {
                positiveNegativeModels.add(new WeightsPositiveNegativeModel(Integer.parseInt(weightsNegativeString[i]), Integer.parseInt(weightsPositiveString[i])));

            }

        }
        database.close();

        return positiveNegativeModels;
    }



}
