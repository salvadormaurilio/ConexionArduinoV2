package com.google.android.gms.example.conexionarduinov2.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sati on 23/10/2014.
 */
public class ItemDropsetNegative {

    private int repetitionsCounts;

    public ItemDropsetNegative() {
        repetitionsCounts = 0;
    }

    public int getRepetitionsCounts() {
        return repetitionsCounts;
    }

    public void incrementRepetitionsCounts() {
        repetitionsCounts++;

    }


    public static final List<ItemDropsetNegative> creataArrayDropset() {
        List<ItemDropsetNegative> itemDropsetNegatives = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            itemDropsetNegatives.add(new ItemDropsetNegative());
        }

        return itemDropsetNegatives;
    }

    public static final List<ItemDropsetNegative> creataArrayPositiveNegative() {
        List<ItemDropsetNegative> itemDropsetNegatives = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            itemDropsetNegatives.add(new ItemDropsetNegative());
        }

        return itemDropsetNegatives;
    }

}
