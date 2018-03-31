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

    public void storeImagePath(String path) {
        mEditor.putString("path", path);
        mEditor.apply();
    }

    public String getImagePath() {
        return mPref.getString("path", null);
    }

    public String retrieveVariable(String key, String defaultValue) {
        return mPref.getString(key, defaultValue);
    }

    public boolean retrieveVariable(String key, boolean defaultValue) {
        return mPref.getBoolean(key, defaultValue);
    }

    public void storeVariable(String key, String value) {
        mEditor.putString(key, value);
        mEditor.apply();
    }

    public void storeVariable(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.apply();
    }

    public void login(String username){
        mEditor.putString("username", username);
        mEditor.putBoolean("logged_in", true);
        mEditor.apply();
    }

    public String getUsername(){
        return mPref.getString("username",null);
    }

    public boolean isLogin(){
        return mPref.getBoolean("logged_in", false);
    }

}
