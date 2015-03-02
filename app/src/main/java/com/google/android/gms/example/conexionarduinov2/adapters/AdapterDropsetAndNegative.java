package com.google.android.gms.example.conexionarduinov2.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.example.conexionarduinov2.R;
import com.google.android.gms.example.conexionarduinov2.utils.ItemDropsetNegative;
import com.google.android.gms.example.conexionarduinov2.utils.PlaceWeightListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sati on 22/10/2014.
 */
public class AdapterDropsetAndNegative extends BaseAdapter {

    private List<ItemDropsetNegative> itemDropsetNegatives;
    private Context context;
    private LayoutInflater inflater;
    private PlaceWeightListener placeWeightListener;


    private String[] arrayWeights;
    private String repetition;
    private String lb;
    private String placeWeight;

    private int typeItem;
    private boolean isClickable;

    private int currentPosition;
    private boolean isFullTable;

    private int positionItem;


    public AdapterDropsetAndNegative(Context context, int typeItem, PlaceWeightListener placeWeightListener) {

        itemDropsetNegatives = new ArrayList<>();
        itemDropsetNegatives.add(new ItemDropsetNegative(0));

        inicialize(context, typeItem, placeWeightListener);

    }

    public AdapterDropsetAndNegative(Context context, int typeItem, PlaceWeightListener placeWeightListener, List<ItemDropsetNegative> itemDropsetNegatives) {

        this.itemDropsetNegatives = itemDropsetNegatives;

        inicialize(context, typeItem, placeWeightListener);
    }

    private void inicialize(Context context, int typeItem,  PlaceWeightListener placeWeightListener) {

        this.context = context;
        inflater = LayoutInflater.from(context);
        this.placeWeightListener = placeWeightListener;
        this.typeItem = typeItem;

        arrayWeights = context.getResources().getStringArray(R.array.weights);

        repetition = " " + context.getString(R.string.repetition);
        lb = " " + context.getString(R.string.lb);
        placeWeight = context.getString(R.string.input_weight);

        isClickable = true;
        isFullTable = true;
        currentPosition = 0;

        isFullTable = true;
        positionItem = 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View container = convertView;
        ViewHolderDropset viewHolderDropset;

        if (container == null) {
            if (typeItem == 1) {
                container = inflater.inflate(R.layout.item_excersise_dropset, parent, false);
            } else if (typeItem == 2) {
                container = inflater.inflate(R.layout.item_excersise_negative, parent, false);
            }

            viewHolderDropset = new ViewHolderDropset(container, R.id.textViewNumWeight, R.id.textViewWeight, R.id.textViewNumRep);

            container.setOnClickListener(null);
            container.setOnLongClickListener(null);
            container.setTag(viewHolderDropset);
        } else {
            viewHolderDropset = (ViewHolderDropset) container.getTag();
        }

        viewHolderDropset.getTextViewNumWeight().setText(arrayWeights[position]);

        if (itemDropsetNegatives.get(position).getWeight() > -1) {
            viewHolderDropset.getTextViewWeight().setText(itemDropsetNegatives.get(position).getWeight() + lb);
        } else {
            viewHolderDropset.getTextViewWeight().setText(placeWeight);
            isFullTable = false;
        }

        viewHolderDropset.getTextViewNumRep().setText(itemDropsetNegatives.get(position).getRepetitionsCounts() + repetition);

        if (isClickable) {

            if (position != 0) {

                viewHolderDropset.getTextViewWeight().setBackgroundResource(typeItem == 1 ? R.drawable.back_textview_dropset : R.drawable.back_textview_negative);
                viewHolderDropset.getTextViewWeight().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (position == 1 && itemDropsetNegatives.get(0).getWeight() < 5) {
                            Toast.makeText(context, R.string.warning_message_weight_min_drop_neg, Toast.LENGTH_SHORT).show();
                        } else if (itemDropsetNegatives.get(position - 1).getWeight() > -1) {

                            double percentageMin = 1.0 - (0.2 * (position + 1.0));
                            double percentageMax = 1.0 - (0.2 * position);

                            Log.d("POCE MAX", Math.rint(percentageMax)+"");
                            Log.d("POCE MIN", Math.rint(percentageMin)+"");


                            currentPosition = position;
                            placeWeightListener.onDialogoInputWeight((int) Math.rint(itemDropsetNegatives.get(0).getWeight() * percentageMin) + 1, (int) Math.rint(itemDropsetNegatives.get(0).getWeight() * percentageMax), false);
                        } else {
                            Toast.makeText(context, R.string.warning_message_weight_previous, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                viewHolderDropset.getTextViewWeight().setBackgroundResource(R.drawable.back_item_table);
                viewHolderDropset.getTextViewWeight().setOnClickListener(null);
            }

        } else {
            viewHolderDropset.getTextViewWeight().setBackgroundResource(R.drawable.back_item_table);
            viewHolderDropset.getTextViewWeight().setOnClickListener(null);
        }


        return container;
    }

    public void addItemDropsetNegative() {
        itemDropsetNegatives.add(new ItemDropsetNegative());
        notifyDataSetChanged();
    }

    public void setWeightInitial(int weight) {

        itemDropsetNegatives.get(0).setWeight(weight);

        for (int i = 1; i < itemDropsetNegatives.size(); i++) {
            itemDropsetNegatives.get(i).setWeight(-1);
        }

        isFullTable = true;
        positionItem = 0;
        notifyDataSetChanged();
    }

    public int getWeightInitial () {
      return   itemDropsetNegatives.get(0).getWeight();
    }

    public void incrementRepetitions() {
        itemDropsetNegatives.get(positionItem).incrementRepetitionsCounts();
        notifyDataSetChanged();
    }

    public void incrementItemPosition() {
        positionItem++;
    }

    public int getPositionItem() {
        return positionItem;
    }


    public void setWeight(int weight) {

        if (itemDropsetNegatives.get(currentPosition).getWeight() != weight) {
            itemDropsetNegatives.get(currentPosition).setWeight(weight);

            for (int i = currentPosition + 1; i < itemDropsetNegatives.size(); i++) {
                itemDropsetNegatives.get(i).setWeight(-1);
            }
            isFullTable = true;
            notifyDataSetChanged();
        }
    }

    public boolean isFullTable() {
        return isFullTable;
    }

    public void setClickable(boolean isClickable) {
        this.isClickable = isClickable;
    }


    public List<Integer> getWeights () {
        List<Integer> weights = new ArrayList<>();

        for (ItemDropsetNegative itemDropsetNegative:itemDropsetNegatives) {
            weights.add(itemDropsetNegative.getWeight());
        }

        return weights;
    }


    @Override
    public int getCount() {
        return itemDropsetNegatives.size();
    }

    @Override
    public Object getItem(int position) {
        return itemDropsetNegatives.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itemDropsetNegatives.indexOf(itemDropsetNegatives.get(position));
    }


    private static class ViewHolderDropset {

        private TextView textViewNumWeight;
        private TextView textViewWeight;
        private TextView textViewNumRep;

        private ViewHolderDropset(View container, int numWeightContainerId, int weightContainerId, int numRepContainerId) {

            textViewNumWeight = (TextView) container.findViewById(numWeightContainerId);
            textViewWeight = (TextView) container.findViewById(weightContainerId);
            textViewNumRep = (TextView) container.findViewById(numRepContainerId);
        }

        public TextView getTextViewNumWeight() {
            return textViewNumWeight;
        }

        public TextView getTextViewWeight() {
            return textViewWeight;
        }

        public TextView getTextViewNumRep() {
            return textViewNumRep;
        }
    }

}
