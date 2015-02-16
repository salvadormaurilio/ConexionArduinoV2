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
public class TrainingAdapter extends BaseAdapter{

    private String[] arrayTrainings;
    private LayoutInflater inflater;


    public TrainingAdapter(Context context, String[] arrayTrainings) {
        this.arrayTrainings = arrayTrainings;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrayTrainings.length;
    }

    @Override
    public Object getItem(int position) {
        return arrayTrainings[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView textViewTraining = (TextView) inflater.inflate(R.layout.item_listview_training, parent, false);

        textViewTraining.setText(arrayTrainings[position]);

        return textViewTraining;
    }
}
