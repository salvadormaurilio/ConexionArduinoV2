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
import com.google.android.gms.example.conexionarduinov2.utils.interfaces.HistoryExerciseInterface;
import com.google.android.gms.example.conexionarduinov2.utils.interfaces.OnOpenExerciseListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sati on 08/02/2015.
 */
public class HistoryExerciseOtherAdapter extends BaseAdapter implements View.OnClickListener, HistoryExerciseInterface {


    private List<InfoExerciseModel> infoExerciseModelList;
    private LayoutInflater inflater;
    private OnOpenExerciseListener onOpenExerciseListener;


    public HistoryExerciseOtherAdapter(Context context, OnOpenExerciseListener onOpenExerciseListener) {
        inflater = LayoutInflater.from(context);
        this.infoExerciseModelList = new ArrayList<>();
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
            view = inflater.inflate(R.layout.item_listview_history_exercise_other, parent, false);
            viewHolder = new ViewHolder(view, R.id.textViewDate, R.id.textViewNameExercise, R.id.textViewTraining, R.id.textViewWeight, R.id.textViewNumRep, R.id.buttonViewSet, R.id.buttonViewRepeatSet);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        InfoExerciseModel exerciseModel = infoExerciseModelList.get(position);

        viewHolder.getTextViewDate().setText(exerciseModel.getDate());
        viewHolder.getTextViewName().setText(exerciseModel.getName());
        viewHolder.getTextViewTraining().setText(exerciseModel.getTraining());
        viewHolder.getTextViewWeight().setText(exerciseModel.getWeight() + " lb");
        viewHolder.getTextViewNumRep().setText(exerciseModel.getRepetitions());
        viewHolder.setButtonViewSetListener(HistoryExerciseOtherAdapter.this, position);
        viewHolder.setButtonRepetaSetListener(HistoryExerciseOtherAdapter.this, position);
        return view;
    }

    @Override
    public void onClick(View v) {

        int position = (int) v.getTag();

        if (v.getId() == R.id.buttonViewSet) {
            onOpenExerciseListener.onViewSet(position);
        } else {
            onOpenExerciseListener.onRepeatSet(position);
        }
    }

    @Override
    public void setInfoExerciseModelList(List<InfoExerciseModel> infoExerciseModelList) {
        this.infoExerciseModelList = infoExerciseModelList;
        notifyDataSetChanged();
    }

    @Override
    public long getIdExercise(int position) {
        return infoExerciseModelList.get(position).getIdExercise();
    }


    @Override
    public int getTypeTraining(int position) {
        return infoExerciseModelList.get(position).getTypeTraining();
    }

    @Override
    public int getWeight(int position) {
        return infoExerciseModelList.get(position).getWeight();
    }

    @Override
    public String getNameExercise(int position) {
        return infoExerciseModelList.get(position).getName();
    }


    private static class ViewHolder {
        private TextView textViewDate;
        private TextView textViewName;
        private TextView textViewTraining;
        private TextView textViewWeight;
        private TextView textViewNumRep;
        private Button buttonViewSet;
        private Button buttonRepeatSet;

        private ViewHolder(View container, int idTextViewDate, int idTextViewName, int idTextViewTraining, int idTextViewWeight, int idtextViewNumRep, int idButtonViewSet, int idButtonRepeatSet) {

            textViewDate = (TextView) container.findViewById(idTextViewDate);
            textViewName = (TextView) container.findViewById(idTextViewName);
            textViewTraining = (TextView) container.findViewById(idTextViewTraining);
            textViewWeight = (TextView) container.findViewById(idTextViewWeight);
            textViewNumRep = (TextView) container.findViewById(idtextViewNumRep);
            buttonViewSet = (Button) container.findViewById(idButtonViewSet);
            buttonRepeatSet = (Button) container.findViewById(idButtonRepeatSet);
        }

        public TextView getTextViewDate() {
            return textViewDate;
        }

        public TextView getTextViewName() {
            return textViewName;
        }

        public TextView getTextViewTraining() {
            return textViewTraining;
        }

        public TextView getTextViewWeight() {
            return textViewWeight;
        }

        public TextView getTextViewNumRep() {
            return textViewNumRep;
        }

        public void setButtonViewSetListener(View.OnClickListener listener, int position) {
            buttonViewSet.setOnClickListener(listener);
            buttonViewSet.setTag(position);
        }

        public void setButtonRepetaSetListener(View.OnClickListener listener, int position) {
            buttonRepeatSet.setOnClickListener(listener);
            buttonRepeatSet.setTag(position);
        }

    }


}
