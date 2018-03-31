package com.aarushi.crime_mappingapp.utility;

import java.util.ArrayList;

public class Utils {

    public static int[] arrayListToArray(ArrayList<Integer> data) {
        int[] d = new int[data.size()];
        for(int i=0; i < data.size(); i++) {
            d[i] = data.get(i);
        }
        return d;
    }

    public static boolean contains(final int[] array, final int v) {
        for (final int e : array)
            if (e == v)
                return true;

        return false;
    }
}
