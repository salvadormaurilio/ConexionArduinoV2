package com.google.android.gms.example.conexionarduinov2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.example.conexionarduinov2.R;
import com.google.android.gms.example.conexionarduinov2.models.WeightsPositiveNegativeModel;
import com.google.android.gms.example.conexionarduinov2.utils.ItemPositiveNegative;
import com.google.android.gms.example.conexionarduinov2.utils.PlaceWeightListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sati on 22/10/2014.
 */
public class AdapterNegativePositive extends BaseAdapter {

    private List<ItemPositiveNegative> itemPositiveNegatives;
    private Context context;
    private LayoutInflater inflater;
    private PlaceWeightListener placeWeightListener;

    private String[] arrayWeights;
    private String repetition;
    private String lb;
    private String placeWeight;

    private boolean isClickable;


    private int currentPosition;
    private int idTextViewWeight;

    private boolean isFullTable;

    private int positionItemPositiveNegatives;


    public AdapterNegativePositive(Context context, PlaceWeightListener placeWeightListener) {
        itemPositiveNegatives = new ArrayList<>();
        itemPositiveNegatives.add(new ItemPositiveNegative(0));
        inicialize(context, placeWeightListener);

    }

    public AdapterNegativePositive(Context context, PlaceWeightListener placeWeightListener, List<ItemPositiveNegative> itemPositiveNegatives) {
        this.itemPositiveNegatives = itemPositiveNegatives;
        inicialize(context, placeWeightListener);

    }


    private void inicialize(Context context, PlaceWeightListener placeWeightListener) {

        this.context = context;
        inflater = LayoutInflater.from(context);
        this.placeWeightListener = placeWeightListener;

        arrayWeights = context.getResources().getStringArray(R.array.weights);

        repetition = " " + context.getString(R.string.repetition);
        lb = " " + context.getString(R.string.lb);
        placeWeight = context.getString(R.string.input_weight);

        isClickable = true;
        isFullTable = true;
        currentPosition = 0;

        idTextViewWeight = R.id.textViewPositiveWeight;
        positionItemPositiveNegatives = 0;

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View container = convertView;
        ViewHolderDropset viewHolderDropset;

        if (container == null) {
            container = inflater.inflate(R.layout.item_excersise_posi_nega, parent, false);
            viewHolderDropset = new ViewHolderDropset(container, R.id.textViewNumWeight, R.id.textViewNegativeWeight,
                    R.id.textViewPositiveWeight, R.id.textViewNumRep);

            container.setOnClickListener(null);
            container.setOnLongClickListener(null);

            container.setTag(viewHolderDropset);
        } else {
            viewHolderDropset = (ViewHolderDropset) container.getTag();
        }

        viewHolderDropset.getTextViewNumWeight().setText(arrayWeights[position]);

        if (itemPositiveNegatives.get(position).getWeightNegative() > -1) {
            viewHolderDropset.getTextViewNegativeWeight().setText(itemPositiveNegatives.get(position).getWeightNegative() + lb);

        } else {
            isFullTable = false;
            viewHolderDropset.getTextViewNegativeWeight().setText(placeWeight);
        }

        if (itemPositiveNegatives.get(position).getWeightPositive() > -1) {
            viewHolderDropset.getTextViewPositiveWeight().setText(itemPositiveNegatives.get(position).getWeightPositive() + lb);
        } else {
            isFullTable = false;
            viewHolderDropset.getTextViewPositiveWeight().setText(placeWeight);
        }

        viewHolderDropset.getTextViewNumRep().setText(itemPositiveNegatives.get(position).getRepetitionsCounts() + repetition);

        if (isClickable) {

            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (v.getId()) {
                        case R.id.textViewNegativeWeight:

                            if (itemPositiveNegatives.get(position - 1).getWeightNegative() >=2) {
                                currentPosition = position;
                                idTextViewWeight = R.id.textViewNegativeWeight;
                                placeWeightListener.onDialogoInputWeight(2, itemPositiveNegatives.get(position - 1).getWeightNegative(), true);
                            } else {
                                Toast.makeText(context, R.string.warning_message_weight_min_neg, Toast.LENGTH_SHORT).show();
                            }

                            break;
                        case R.id.textViewPositiveWeight:

                            if (itemPositiveNegatives.get(position).getWeightNegative() > -1) {

                                if (position > 0 || itemPositiveNegatives.get(0).getWeightNegative() >= 2) {
                                    currentPosition = position;
                                    idTextViewWeight = R.id.textViewPositiveWeight;
                                    placeWeightListener.onDialogoInputWeight(itemPositiveNegatives.get(position).getWeightNegative() / 2, 720, false);
                                } else {
                                    Toast.makeText(context, R.string.warning_message_weight_min_pos, Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(context, R.string.warning_message_weight_min_pos, Toast.LENGTH_SHORT).show();
                            }
                            break;
                    }
                }
            };

            if (position != 0) {
                viewHolderDropset.getTextViewNegativeWeight().setOnClickListener(onClickListener);
            } else {
                viewHolderDropset.getTextViewNegativeWeight().setOnClickListener(null);
                viewHolderDropset.getTextViewNegativeWeight().setBackgroundResource(R.drawable.back_item_table);
            }
            viewHolderDropset.getTextViewPositiveWeight().setBackgroundResource(R.drawable.back_textview_posi_nega);
            viewHolderDropset.getTextViewPositiveWeight().setOnClickListener(onClickListener);
        } else {

            viewHolderDropset.getTextViewNegativeWeight().setBackgroundResource(R.drawable.back_item_table);
            viewHolderDropset.getTextViewPositiveWeight().setBackgroundResource(R.drawable.back_item_table);

            viewHolderDropset.getTextViewNegativeWeight().setOnClickListener(null);
            viewHolderDropset.getTextViewPositiveWeight().setOnClickListener(null);
        }


        return container;
    }


    public void addItemPositiveNegative() {
        itemPositiveNegatives.add(new ItemPositiveNegative());
        notifyDataSetChanged();
    }

    public void setNewWeightInitial(int weight) {
        itemPositiveNegatives.get(0).setWeightNegative(weight);
        itemPositiveNegatives.get(0).setWeightPositive(-1);

        for (int i = 1; i < itemPositiveNegatives.size(); i++) {
            itemPositiveNegatives.get(i).setWeightNegative(-1);
            itemPositiveNegatives.get(i).setWeightPositive(-1);
        }

        isFullTable = true;
        positionItemPositiveNegatives = 0;
        notifyDataSetChanged();
    }

    public int getWeightInitial () {
        return   itemPositiveNegatives.get(0).getWeightNegative();
    }

    public void incrementRepetitions() {
        itemPositiveNegatives.get(positionItemPositiveNegatives).incrementRepetitionsCounts();
        notifyDataSetChanged();
    }

    public void incrementItemPosition() {
        positionItemPositiveNegatives++;
    }

    public int getPositionItemPositiveNegatives() {
        return positionItemPositiveNegatives;
    }

    public void setWeight(int weight) {

        switch (idTextViewWeight) {
            case R.id.textViewNegativeWeight:
                if (itemPositiveNegatives.get(currentPosition).getWeightNegative() != weight) {
                    itemPositiveNegatives.get(currentPosition).setWeightNegative(weight);
                    if (currentPosition < itemPositiveNegatives.size()-1) {
                        itemPositiveNegatives.get(currentPosition + 1).setWeightNegative(-1);
                    }
                    itemPositiveNegatives.get(currentPosition).setWeightPositive(-1);
                    isFullTable = true;
                    notifyDataSetChanged();
                }
                break;
            case R.id.textViewPositiveWeight:

                if (itemPositiveNegatives.get(currentPosition).getWeightPositive() != weight) {
                    itemPositiveNegatives.get(currentPosition).setWeightPositive(weight);
                    isFullTable = true;
                    notifyDataSetChanged();
                }
                break;
        }
    }

    public boolean isFullTable() {
        return isFullTable;
    }

    public void setClickable(boolean isClickable) {
        this.isClickable = isClickable;
    }


    public List<WeightsPositiveNegativeModel> getWeights() {
        List<WeightsPositiveNegativeModel> weights = new ArrayList<>();

        for (ItemPositiveNegative itemPositiveNegative : itemPositiveNegatives) {
            weights.add(new WeightsPositiveNegativeModel(itemPositiveNegative.getWeightNegative(), itemPositiveNegative.getWeightPositive()));
        }

        return weights;
    }

    @Override
    public int getCount() {
        return itemPositiveNegatives.size();
    }

    @Override
    public Object getItem(int position) {
        return itemPositiveNegatives.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itemPositiveNegatives.indexOf(itemPositiveNegatives.get(position));
    }

    private static class ViewHolderDropset {

        private TextView textViewNumWeight;
        private TextView textViewNegativeWeight;
        private TextView textViewPositiveWeight;
        private TextView textViewNumRep;

        private ViewHolderDropset(View container, int numWeightContainerId, int weightNegativeContainerId, int weightPositiveContainerId, int numRepContainerId) {

            textViewNumWeight = (TextView) container.findViewById(numWeightContainerId);
            textViewNegativeWeight = (TextView) container.findViewById(weightNegativeContainerId);
            textViewPositiveWeight = (TextView) container.findViewById(weightPositiveContainerId);
            textViewNumRep = (TextView) container.findViewById(numRepContainerId);
        }

        public TextView getTextViewNumWeight() {
            return textViewNumWeight;
        }

        public TextView getTextViewNegativeWeight() {
            return textViewNegativeWeight;
        }

        public TextView getTextViewPositiveWeight() {
            return textViewPositiveWeight;
        }

        public TextView getTextViewNumRep() {
            return textViewNumRep;
        }
    }

}
