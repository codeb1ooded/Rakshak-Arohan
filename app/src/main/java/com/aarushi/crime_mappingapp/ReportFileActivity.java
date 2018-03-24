package com.aarushi.crime_mappingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ReportFileActivity extends AppCompatActivity {
    private EditText _complaintby, _phone, _fir_location, _complaint_time, _aadhaar, _crime_type, _crime_description, _crime_date, _crime_time, _lat_crime, _long_crime, _status;
    private Button _submit;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_file);
        _complaintby = (EditText)findViewById(R.id.name1);
        _phone = (EditText)findViewById(R.id.phone);
        _fir_location = (EditText)findViewById(R.id.your_location);
        _complaint_time = (EditText)findViewById(R.id.complaintTime);
        _aadhaar = (EditText)findViewById(R.id.aadhaar);
        _crime_type = (EditText)findViewById(R.id.crimeType);
        _crime_description = (EditText)findViewById(R.id.describeCrime);
        _crime_date = (EditText)findViewById(R.id.dateCrime);
        _crime_time = (EditText)findViewById(R.id.timeCrime);
        _lat_crime = (EditText)findViewById(R.id.latCrime);
        _long_crime = (EditText)findViewById(R.id.longCrime);
        _status = (EditText) findViewById(R.id.status_);
        _submit = (Button)findViewById(R.id.btn_submit);



    }
}
