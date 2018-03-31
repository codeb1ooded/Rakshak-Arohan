package com.aarushi.crime_mappingapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.aarushi.crime_mappingapp.DB.DatabaseHelper;
import com.aarushi.crime_mappingapp.login_module.SignupActivity;
import com.aarushi.crime_mappingapp.upload.AutoUploadService;
import com.aarushi.crime_mappingapp.utility.PreferenceManagerUtils;

public class ComplaintActivity extends AppCompatActivity {

    EditText et_location, crimeType, crimeDescription,crimeDate,crimeTime,isVerified,isUploaded;
    Button btn_complaint,btn_viewAll;

    DatabaseHelper myDb;
    private GPSTracker mGPSTracker;
    private PreferenceManagerUtils preferenceManagerUtils;

    private double mLatitude, mLongitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        myDb = new DatabaseHelper(this);
        mGPSTracker = new GPSTracker(this);
        preferenceManagerUtils = new PreferenceManagerUtils(ComplaintActivity.this);

        final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final Date currentTime = Calendar.getInstance().getTime();
        final Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        if(mGPSTracker.canGetLocation()){
            mLatitude=mGPSTracker.getLatitude();
            mLongitude=mGPSTracker.getLongitude();
            Log.d("Database",mLatitude+" "+mLongitude);
        }

        et_location = (EditText)findViewById(R.id.et_location);
        crimeType = (EditText) findViewById(R.id.crimeType);
        crimeDescription = (EditText)findViewById(R.id.crimeDescription);
        crimeDate=(EditText)findViewById(R.id.crimeDate);
        crimeTime=(EditText)findViewById(R.id.crimeTime);
        isVerified=(EditText)findViewById(R.id.isVerified);
        btn_complaint= (Button) findViewById(R.id.btn_complaint);
        btn_viewAll=(Button)findViewById(R.id.btn_viewAll);

        btn_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("ComplaintActivity", crimeDate.getText().toString());
                Log.i("ComplaintActivity", crimeTime.getText().toString());
                boolean isInserted = myDb.insertData(
                        preferenceManagerUtils.getUsername(),
                        crimeType.getText().toString(),
                        mLatitude + "",
                        mLongitude + "",
                        et_location.getText().toString(),
                        crimeDescription.getText().toString(),
                        crimeDate.getText().toString(),
                        crimeTime.getText().toString(),
                        timeFormat.format(currentTime),
                        dateFormat.format(currentTime),
                        isVerified.getText().toString());
                if(isInserted == true) {
                    Toast.makeText(ComplaintActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                    Intent serviceIntent = new Intent(ComplaintActivity.this, AutoUploadService.class);
                    startService(serviceIntent);
                }
                else
                    Toast.makeText(ComplaintActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
            }
        });
        btn_viewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                            // show message
                            Log.d("Database","Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Id :"+ res.getString(0)+"\n");
                            buffer.append("Username :"+ res.getString(1)+"\n");
                            buffer.append("Crime Type :"+ res.getString(2)+"\n");
                            buffer.append("Marks :"+ res.getString(3)+"\n\n");
                        }

                        // Show all data
                        Log.d("Database",buffer.toString());
                    }
                }
        );
    }

}
