package com.aarushi.crime_mappingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.format.Time;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aarushi.crime_mappingapp.DB.DatabaseHelper;
import com.aarushi.crime_mappingapp.upload.AutoUploadService;
import com.aarushi.crime_mappingapp.utility.LocationUtils;
import com.aarushi.crime_mappingapp.utility.PermissionViewModel;
import com.aarushi.crime_mappingapp.utility.PreferenceManagerUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ComplaintActivity extends BaseActivity implements LocationUtils.LocationUtilListener {

    EditText et_location, crimeType, crimeDescription, crimeDate, crimeTime, isVerified;
    Button btn_complaint;
    DatabaseHelper myDb;
//    private GPSTracker mGPSTracker;
//    private double mLatitude, mLongitude;
    private LocationUtils mLocationUtils;
    private PreferenceManagerUtils preferenceManagerUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        setTitle(R.string.title_activity_complain_register);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLocationUtils = new LocationUtils(this, this);

        myDb = new DatabaseHelper(this);
//        mGPSTracker = new GPSTracker(this);
        preferenceManagerUtils = new PreferenceManagerUtils(ComplaintActivity.this);

        final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final Date currentTime = Calendar.getInstance().getTime();
        final Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
//        if (mGPSTracker.canGetLocation()) {
//            mLatitude = mGPSTracker.getLatitude();
//            mLongitude = mGPSTracker.getLongitude();
//            Log.d("Database", mLatitude + " " + mLongitude);
//        }
        et_location = (EditText) findViewById(R.id.et_location);
        crimeType = (EditText) findViewById(R.id.crimeType);
        crimeDescription = (EditText) findViewById(R.id.crimeDescription);
        crimeDate = (EditText) findViewById(R.id.crimeDate);
        crimeTime = (EditText) findViewById(R.id.crimeTime);
        isVerified = (EditText) findViewById(R.id.isVerified);
        btn_complaint = (Button) findViewById(R.id.btn_complaint);

        btn_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("ComplaintActivity", crimeDate.getText().toString());
                Log.i("ComplaintActivity", crimeTime.getText().toString());
                Location location = getCurrentLocation();
                boolean isInserted = myDb.insertData(
                        preferenceManagerUtils.getUsername(),
                        crimeType.getText().toString(),
                        location.getLatitude() + "",
                        location.getLongitude() + "",
                        et_location.getText().toString(),
                        crimeDescription.getText().toString(),
                        crimeDate.getText().toString(),
                        crimeTime.getText().toString(),
                        timeFormat.format(currentTime),
                        dateFormat.format(currentTime),
                        isVerified.getText().toString());
                if(isInserted == true) {
                    cleanFields();
                    Toast.makeText(ComplaintActivity.this, getString(R.string.info_data_inserted), Toast.LENGTH_LONG).show();
                    Intent serviceIntent = new Intent(ComplaintActivity.this, AutoUploadService.class);
                    startService(serviceIntent);
                }
                else
                    Toast.makeText(ComplaintActivity.this, getString(R.string.info_data_not_inserted), Toast.LENGTH_LONG).show();
            }
        });
//        btn_viewAll.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Cursor res = myDb.getAllData();
//                        if (res.getCount() == 0) {
//                            // show message
//                            Log.d("Database", "Nothing found");
//                            return;
//                        }
//
//                        StringBuffer buffer = new StringBuffer();
//                        while (res.moveToNext()) {
//                            buffer.append("Id :" + res.getString(0) + "\n");
//                            buffer.append("Name :" + res.getString(1) + "\n");
//                            buffer.append("Surname :" + res.getString(2) + "\n");
//                            buffer.append("Marks :" + res.getString(3) + "\n\n");
//                        }
//
//                        // Show all data
//                        Log.d("Database", buffer.toString());
//                    }
//                }
//        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mLocationUtils != null) {
            mLocationUtils.stopLocationUpdates();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mLocationUtils != null) {
            mLocationUtils.startLocation();
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (PermissionViewModel.isPermissionRight(permissions, PermissionViewModel.PERMISSION_LOCATION)) {
            PermissionViewModel.onPermissionResult(this, requestCode, mLocationUtils, permissions, grantResults);
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public Location getCurrentLocation() {
        Location location = mLocationUtils.getCurrentLocation();
        if (location != null) {
            return location;
        } else {
            Location l = new Location("None");
            l.setLongitude(0.0);
            l.setLatitude(0.0);
            return l;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mLocationUtils != null) {
            mLocationUtils.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void updateUI() {

    }

    @Override
    public void onLocationDenied() {
        Toast.makeText(getApplicationContext(), getString(R.string.location_permission_info_text), Toast.LENGTH_LONG).show();
        onBackPressed();
        finish();
    }

    private void cleanFields() {
        et_location.setText("");
        crimeDescription.setText("");
        crimeType.setText("");
        crimeDate.setText("");
        crimeTime.setText("");
        isVerified.setText("");
    }
}
