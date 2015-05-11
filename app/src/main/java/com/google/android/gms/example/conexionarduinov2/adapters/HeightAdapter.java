package com.google.android.gms.example.conexionarduinov2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.gms.example.conexionarduinov2.R;

import java.util.List;

/**
 * Created by sati on 11/01/2015.
 */
public class HeightAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<String> units;

    public HeightAdapter(Context context, List<String> units) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.units = units;
    }



    public void setUnits(List<String> units) {
        this.units = units;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return units.size() - 1;
    }

    @Override
    public Object getItem(int position) {
        return units.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, parent, R.layout.item_spinner_units);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, parent, R.layout.item_spinner_units_down);
    }

    public View getCustomView(int position, ViewGroup parent, int iditem) {
        TextView textView = (TextView) inflater.inflate(iditem, parent, false);

        textView.setText(units.get(position));
        return textView;
    }
}
