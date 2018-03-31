package com.aarushi.crime_mappingapp;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.aarushi.crime_mappingapp.utility.PreferenceManagerUtils;

import java.util.Locale;

public class MyApplication extends Application {

    private Locale locale = null;

    @Override
    public void onCreate() {
        super.onCreate();
        setLocale();
    }

    @Override
    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (locale != null) {
            newConfig.locale = locale;
            Locale.setDefault(locale);
            getBaseContext().getResources().updateConfiguration(newConfig, getBaseContext().getResources().getDisplayMetrics());
        }
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    public void reloadLocale() {
        setLocale();
    }

    private void setLocale() {
        //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        PreferenceManagerUtils ssn = new PreferenceManagerUtils(this);

        android.content.res.Configuration config = getBaseContext().getResources().getConfiguration();
        String lang = ssn.retrieveVariable(getString(R.string.pref_locale), getString(R.string.pref_lang));
        locale = new Locale(lang);
        Locale.setDefault(locale);
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }
}
