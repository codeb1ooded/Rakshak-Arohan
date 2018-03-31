package com.aarushi.crime_mappingapp.utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.aarushi.crime_mappingapp.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

/**
 * Location Related utility methods
 * @author Ajay Bhatt (ajaybhatt17@gmail.com)
 */
public class LocationUtils implements PermissionViewModel.PermissionListener {

    private static final String TAG = "ATTENDENCE";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 500*4;
    private static final int REQUEST_CHECK_SETTINGS = 999;

    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;
    private Activity mContext;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private Boolean mRequestingLocationUpdates = true;
    private LocationUtilListener mLocationUtilListener;

    private PermissionViewModel locationPermissionViewModel;

    public LocationUtils(Activity activity, LocationUtilListener locationUtilListener) {
        mContext = activity;
        locationPermissionViewModel = new PermissionViewModel(PermissionViewModel.PERMISSION_LOCATION);
        mLocationUtilListener = locationUtilListener;
        if (isGooglePlayServicesAvailable()) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
            mSettingsClient = LocationServices.getSettingsClient(mContext);
            createLocationCallback();
            createLocationRequest();
            buildLocationSettingsRequest();
        } else {
            Toast.makeText(mContext, "Google Play Service is not active", Toast.LENGTH_LONG).show();
        }
    }

    public void startLocation() {
        if (!locationPermissionViewModel.isPermissionApproved(mContext)) {
            locationPermissionViewModel.requestPermission(mContext);
        } else {
            startLocationUpdates();
        }
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mContext);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, mContext, 0).show();
            return false;
        }
    }

    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                mCurrentLocation = locationResult.getLastLocation();
                if (mLocationUtilListener != null) {
                    mLocationUtilListener.updateUI();
                }
            }
        };
    }

    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    @SuppressLint("MissingPermission")
    public void startLocationUpdates() {
        // Begin by checking if the device has the necessary location settings.
        if (mSettingsClient == null) {
            Toast.makeText(mContext, "Need Play Service for this", Toast.LENGTH_LONG).show();
            return;
        }
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(mContext, new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

                        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // GPS location can be null if GPS is switched off
                                if (location != null) {
                                    mCurrentLocation = location;
                                }
                            }
                        });

                        if (locationPermissionViewModel.isPermissionApproved(mContext)) {
                            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                    mLocationCallback, Looper.myLooper());
                        }

                        if (mLocationUtilListener != null) {
                            mLocationUtilListener.updateUI();
                        }
                    }
                })
                .addOnFailureListener(mContext, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(mContext, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);
                                Toast.makeText(mContext, errorMessage, Toast.LENGTH_LONG).show();
                                mRequestingLocationUpdates = false;
                        }

                        if (mLocationUtilListener != null) {
                            mLocationUtilListener.updateUI();
                        }
                    }
                });
    }

    public void stopLocationUpdates() {

        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback)
                    .addOnCompleteListener(mContext, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            mRequestingLocationUpdates = false;
//                        setButtonsEnabledState();
                        }
                    });
        }
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    public void enableLocationDialog() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                .setMessage(mContext.getString(R.string.msg_need_location))
                .setPositiveButton(mContext.getString(R.string.button_enable), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        locationPermissionViewModel.requestPermissionDirectly(mContext);
                    }
                }).setNegativeButton(mContext.getString(R.string.button_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mLocationUtilListener.onLocationDenied();
                    }
                })
                .create();
        alertDialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.i(TAG, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        final Handler handler = new Handler();
                        //Do something after 100ms
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startLocationUpdates();
                            }
                        }, 1000);
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(TAG, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        if (mLocationUtilListener != null) {
                            mLocationUtilListener.updateUI();
                        }
                        break;
                }
                break;
        }
    }

    @Override
    public void showEnableDialog(int... permissionType) {
        enableLocationDialog();
    }

    @Override
    public void permissionRequested(int requestCode) {

    }

    @Override
    public void onPermissionAccepted(int... permissionType) {
        startLocationUpdates();
    }

    @Override
    public void onPermissionDenied(int... permissionType) {
        if (mLocationUtilListener != null) {
            mLocationUtilListener.onLocationDenied();
        }
    }

    public Location getCurrentLocation() {
        return mCurrentLocation;
    }

    public interface LocationUtilListener {

        void updateUI();

        void onLocationDenied();

    }

}