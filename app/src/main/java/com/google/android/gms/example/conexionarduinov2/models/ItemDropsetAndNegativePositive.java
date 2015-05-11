package com.google.android.gms.example.conexionarduinov2.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sati on 23/10/2014.
 */
public class ItemDropsetAndNegativePositive {

    private int repetitionsCounts;

    public ItemDropsetAndNegativePositive() {
        repetitionsCounts = 0;
    }

    public ItemDropsetAndNegativePositive(int repetitionsCounts) {
        this.repetitionsCounts = repetitionsCounts;
    }

    public int getRepetitionsCounts() {
        return repetitionsCounts;
    }

    public void incrementRepetitionsCounts() {
        repetitionsCounts++;

    }


    public static List<ItemDropsetAndNegativePositive> creataArrayDropset() {
        List<ItemDropsetAndNegativePositive> itemDropsetAndNegativePositives = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            itemDropsetAndNegativePositives.add(new ItemDropsetAndNegativePositive());
        }

        return itemDropsetAndNegativePositives;
    }

    public static List<ItemDropsetAndNegativePositive> creataArrayPositiveNegative() {
        List<ItemDropsetAndNegativePositive> itemDropsetAndNegativePositives = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            itemDropsetAndNegativePositives.add(new ItemDropsetAndNegativePositive());
        }

        return itemDropsetAndNegativePositives;
    }


    public static String toStringRetitions(List<ItemDropsetAndNegativePositive> itemDropsetAndNegativePositives) {

        String repetitions = "";

        for (int i = 0; i < itemDropsetAndNegativePositives.size(); i++) {

            if (i < itemDropsetAndNegativePositives.size() - 1) {
                repetitions = repetitions + itemDropsetAndNegativePositives.get(i).getRepetitionsCounts() + ",";
            } else {
                repetitions = repetitions + itemDropsetAndNegativePositives.get(i).getRepetitionsCounts() + "";
            }
        }

        return repetitions;
    }

    public static List<ItemDropsetAndNegativePositive> toListRepetitions(String repetitions) {
        List<ItemDropsetAndNegativePositive> itemDropsetAndNegativePositives = new ArrayList<>();

        String[] split = repetitions.split(",");

        for (String repetition: split) {
            itemDropsetAndNegativePositives.add(new ItemDropsetAndNegativePositive(Integer.parseInt(repetition)));
        }

        return itemDropsetAndNegativePositives;
    }


}
