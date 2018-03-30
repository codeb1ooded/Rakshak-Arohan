package com.aarushi.crime_mappingapp.login_module;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aarushi.crime_mappingapp.ComplaintActivity;
import com.aarushi.crime_mappingapp.R;

public class LoginPoliceActivity extends AppCompatActivity {

    EditText et_policeemail, et_policepassword;
    Button btn_policelogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_police);
        et_policeemail = (EditText)findViewById(R.id.et_policeemail);
        et_policepassword = (EditText)findViewById(R.id.et_policepassword);
        btn_policelogin = (Button) findViewById(R.id.bt_policelogin);


        btn_policelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginPoliceActivity.this,ComplaintActivity.class);
                startActivity(i);
            }
        });
    }
}
