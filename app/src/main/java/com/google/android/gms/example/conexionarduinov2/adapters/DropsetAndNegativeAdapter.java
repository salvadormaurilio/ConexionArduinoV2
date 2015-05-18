package com.google.android.gms.example.conexionarduinov2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.gms.example.conexionarduinov2.R;
import com.google.android.gms.example.conexionarduinov2.models.ItemDropsetAndNegativePositive;

import java.util.List;


/**
 * Created by sati on 22/10/2014.
 */
public class DropsetAndNegativeAdapter extends BaseAdapter {

    private List<ItemDropsetAndNegativePositive> itemDropsetAndNegativePositives;
    private LayoutInflater inflater;
    private String[] arrayWeights;
    private String repetition;

    private int typeItem;

    private int positionItem;


    public DropsetAndNegativeAdapter(Context context, int typeItem) {
        this.itemDropsetAndNegativePositives = typeItem == 1 ? ItemDropsetAndNegativePositive.creataArrayDropset() : ItemDropsetAndNegativePositive.creataArrayPositiveNegative();
        initAttributes(context, typeItem);
    }

    public DropsetAndNegativeAdapter(Context context, int typeItem, List<ItemDropsetAndNegativePositive> itemDropsetAndNegativePositives) {
        this.itemDropsetAndNegativePositives = itemDropsetAndNegativePositives;
        initAttributes(context, typeItem);
    }

    private void initAttributes(Context context, int typeItem) {
        this.typeItem = typeItem;
        inflater = LayoutInflater.from(context);

        arrayWeights = context.getResources().getStringArray(R.array.weights);

        repetition = " " + context.getString(R.string.repetition);
        positionItem = 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View container = convertView;
        ViewHolderDropset viewHolderDropset;

        if (container == null) {
            container = inflater.inflate(typeItem == 1 ? R.layout.item_excersise_dropset : R.layout.item_excersise_posi_nega, parent, false);

            viewHolderDropset = new ViewHolderDropset(container, R.id.textViewNumWeight, R.id.textViewNumRep);

            container.setOnClickListener(null);
            container.setOnLongClickListener(null);
            container.setTag(viewHolderDropset);
        } else {
            viewHolderDropset = (ViewHolderDropset) container.getTag();
        }

        viewHolderDropset.getTextViewNumWeight().setText(arrayWeights[position]);
        viewHolderDropset.getTextViewNumRep().setText(itemDropsetAndNegativePositives.get(position).getRepetitionsCounts() + repetition);

        return container;
    }


    public void incrementRepetitions() {
        itemDropsetAndNegativePositives.get(positionItem).incrementRepetitionsCounts();
        notifyDataSetChanged();
    }

    public void incrementItemPosition() {
        positionItem++;
    }

    public int getPositionItem() {
        return positionItem;
    }

    public List<ItemDropsetAndNegativePositive> getItemDropsetAndNegativePositives() {
        return itemDropsetAndNegativePositives;
    }


    public void  clearTable ()
    {
        itemDropsetAndNegativePositives = typeItem == 1 ? ItemDropsetAndNegativePositive.creataArrayDropset() : ItemDropsetAndNegativePositive.creataArrayPositiveNegative();
        notifyDataSetChanged();

    }


    @Override
    public int getCount() {
        return itemDropsetAndNegativePositives.size();
    }

    @Override
    public Object getItem(int position) {
        return itemDropsetAndNegativePositives.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itemDropsetAndNegativePositives.indexOf(itemDropsetAndNegativePositives.get(position));
    }


    private static class ViewHolderDropset {

        private TextView textViewNumWeight;
        private TextView textViewNumRep;

        private ViewHolderDropset(View container, int numWeightContainerId, int numRepContainerId) {

            textViewNumWeight = (TextView) container.findViewById(numWeightContainerId);
            textViewNumRep = (TextView) container.findViewById(numRepContainerId);
        }

        public TextView getTextViewNumWeight() {
            return textViewNumWeight;
        }

        public TextView getTextViewNumRep() {
            return textViewNumRep;
        }
    }

}
