package com.aarushi.crime_mappingapp;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import com.aarushi.crime_mappingapp.utility.PreferenceManagerUtils;

import java.util.Locale;

public class LocaleHelper {


    public static Context setLocale(Context c) {
        return setNewLocale(c, getLanguage(c));
    }

    public static Context setNewLocale(Context c, String language) {
        persistLanguage(c, language);
        return updateResources(c, language);
    }

    public static String getLanguage(Context c) {
        PreferenceManagerUtils ssn = new PreferenceManagerUtils(c);
        return ssn.retrieveVariable(c.getString(R.string.pref_locale), c.getString(R.string.pref_lang));
    }

    private static void persistLanguage(Context c, String language) {
        PreferenceManagerUtils ssn = new PreferenceManagerUtils(c);
        ssn.storeVariable(c.getString(R.string.pref_locale), language);
    }

    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }
}
