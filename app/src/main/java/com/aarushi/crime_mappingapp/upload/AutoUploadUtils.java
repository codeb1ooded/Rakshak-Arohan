package com.aarushi.crime_mappingapp.upload;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import com.aarushi.crime_mappingapp.utility.PreferenceManagerUtils;

/**
 * Created by megha on 30/03/18.
 */

public class AutoUploadUtils {

    private PreferenceManagerUtils preferenceManager;

    private Context mContext;
    private ConnectivityManager mConnectivityManager;
    private NetworkInfo mNetworkInfo;;

    public AutoUploadUtils(Context mContext) {

        this.mContext = mContext;
        preferenceManager = new PreferenceManagerUtils(mContext);
        mConnectivityManager = (ConnectivityManager) mContext.getSystemService(mContext.CONNECTIVITY_SERVICE);
        mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
    }

    /**
     *  called whenever there  is  a change in the connectivity and if conditions are met it starts the autouploading process
     */
    public void onConnectionChanged() {

        if (mNetworkInfo != null &&
                mNetworkInfo.isConnected() &&
                isRequiredConnection()) {
            // check if data to be uploaded exists
            if(isComplaintsToBeUploaded())
                startUploadService();
        }
    }

    private void startUploadService() {
        Intent serviceIntent = new Intent(mContext, AutoUploadService.class);
        mContext.startService(serviceIntent);
    }

    /**
     * checks whether the network conditions are met for autouploading to take place
     * @return true if conditions are met
     */
    private boolean isRequiredConnection() {
        int networkType = mNetworkInfo.getType();

        if(networkType == ConnectivityManager.TYPE_WIFI) {
            return true;
        }

        else if(networkType == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }

        return false;
    }

    private boolean isComplaintsToBeUploaded(){
        return true;
    }
}

