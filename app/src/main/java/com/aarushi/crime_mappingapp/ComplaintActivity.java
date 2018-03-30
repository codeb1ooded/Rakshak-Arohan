package com.aarushi.crime_mappingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aarushi.crime_mappingapp.login_module.PoliceLoginActivity;
import com.aarushi.crime_mappingapp.login_module.SignupActivity;

public class ComplaintActivity extends AppCompatActivity {
EditText et_location, crimeType, crimeDescription;
Button btn_complaint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        et_location = (EditText)findViewById(R.id.et_location);
        crimeType = (EditText) findViewById(R.id.crimeType);
        crimeDescription = (EditText)findViewById(R.id.crimeDescription);


        btn_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ComplaintActivity.this,SignupActivity.class);
                startActivity(i);
            }
        });
    }
}
