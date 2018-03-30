package com.aarushi.crime_mappingapp.utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by megha on 30/03/18.
 */

public class PreferenceManagerUtils {

    public static String PREFERENCE_NAME = "RakshakArohan";

    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private Context mContext;

    //Required constructor
    public PreferenceManagerUtils(Context mContext) {
        this.mContext = mContext;
        mPref = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        mEditor = mPref.edit();
    }

}
