package com.google.android.gms.example.conexionarduinov2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.example.conexionarduinov2.R;
import com.google.android.gms.example.conexionarduinov2.models.InfoExerciseModel;

import java.util.List;

/**
 * Created by sati on 08/02/2015.
 */
public class HistoryExerciseAdapter extends BaseAdapter{


    private List<InfoExerciseModel> infoExerciseModelList;
    private LayoutInflater inflater;
    private OnOpenExerciseListener onOpenExerciseListener;



    public interface OnOpenExerciseListener
    {
        public void onOpenExercise(int position);
    }


    public HistoryExerciseAdapter(Context context, List<InfoExerciseModel> infoExerciseModelList, OnOpenExerciseListener onOpenExerciseListener) {
        inflater = LayoutInflater.from(context);
        this.infoExerciseModelList = infoExerciseModelList;
        this.onOpenExerciseListener = onOpenExerciseListener;
    }

    @Override
    public int getCount() {
        return infoExerciseModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return infoExerciseModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder viewHolder;


        if (view == null) {
            view = inflater.inflate(R.layout.item_listview_history_exercise, parent, false);
            viewHolder = new ViewHolder(view, R.id.textViewDate, R.id.textViewTraining, R.id.textViewWeight, R.id.buttonViewRepeatSet);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        InfoExerciseModel exerciseModel = infoExerciseModelList.get(position);

        viewHolder.getTextViewDate().setText(exerciseModel.getDate());
        viewHolder.getTextViewTraining().setText(exerciseModel.getTraining());
        viewHolder.getTextViewWeight().setText(exerciseModel.getWeight()+" lb");
        viewHolder.getButtonRepeatSet().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOpenExerciseListener.onOpenExercise(position);
            }
        });

        return view;
    }


    private static class ViewHolder {
        private TextView textViewDate;
        private TextView textViewTraining;
        private TextView textViewWeight;
        private Button buttonRepeatSet;

        private ViewHolder(View container, int idTextViewDate, int idTextViewTraining, int idTextViewWeight, int idButtonRepeatSet) {

            textViewDate = (TextView) container.findViewById(idTextViewDate);
            textViewTraining = (TextView) container.findViewById(idTextViewTraining);
            textViewWeight = (TextView) container.findViewById(idTextViewWeight);
            buttonRepeatSet = (Button) container.findViewById(idButtonRepeatSet);
        }

        public TextView getTextViewDate() {
            return textViewDate;
        }

        public TextView getTextViewTraining() {
            return textViewTraining;
        }

        public TextView getTextViewWeight() {
            return textViewWeight;
        }

        public Button getButtonRepeatSet() {
            return buttonRepeatSet;
        }
    }


}
