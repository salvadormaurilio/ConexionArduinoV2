package com.google.android.gms.example.conexionarduinov2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.gms.example.conexionarduinov2.R;

/**
 * Created by sati on 08/02/2015.
 */
public class ExercisesAdapter extends BaseAdapter implements View.OnClickListener {

    private String[] arrayExercises;
    private LayoutInflater inflater;

    public ExercisesAdapter(Context context, String[] arrayExercises) {
        this.arrayExercises = arrayExercises;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrayExercises.length;
    }

    @Override
    public Object getItem(int position) {
        return arrayExercises[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (position < arrayExercises.length - 1) {
            view = inflater.inflate(R.layout.item_listview_exercise, parent, false);
//            view.findViewById(R.id.buttonViewRecomme).setOnClickListener(this);
            
        } else {
            view = inflater.inflate(R.layout.item_listview_exercise_other, parent, false);
        }

        TextView textViewExercise = (TextView) view.findViewById(R.id.textViewExercise);
        textViewExercise.setText(arrayExercises[position]);

        return view;
    }


    @Override
    public void onClick(View v) {

    }
}
