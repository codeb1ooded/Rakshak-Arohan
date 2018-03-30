package com.aarushi.crime_mappingapp.upload;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by megha on 30/03/18.
 */

public class ConnectionChangedReceiver extends BroadcastReceiver {

    private static final String TAG = "NetworkStateReceiver";

    @Override
    public void onReceive(final Context context, final Intent intent) {

        Log.d(TAG, "Network connectivity change");

        if (intent.getExtras() != null) {
            AutoUploadUtils autoUploadUtils = new AutoUploadUtils(context);
            autoUploadUtils.onConnectionChanged();
        }
    }
}

