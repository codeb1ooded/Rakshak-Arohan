package com.aarushi.crime_mappingapp.login_module;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aarushi.crime_mappingapp.ComplaintActivity;
import com.aarushi.crime_mappingapp.R;
import com.aarushi.crime_mappingapp.utility.PreferenceManagerUtils;

public class PoliceLoginActivity extends AppCompatActivity {

    EditText et_policeemail, et_policepassword;
    Button btn_policelogin;

    PreferenceManagerUtils preferenceManagerUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_login);

        preferenceManagerUtils = new PreferenceManagerUtils(PoliceLoginActivity.this);

        et_policeemail = (EditText)findViewById(R.id.et_policeemail);
        et_policepassword = (EditText)findViewById(R.id.et_policepassword);
        btn_policelogin = (Button) findViewById(R.id.bt_policelogin);

        if(!preferenceManagerUtils.isLogin()){
            btn_policelogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: authenticate from server (Subsidiary)
                    preferenceManagerUtils.login(et_policeemail.getText().toString());
                    Intent i = new Intent(PoliceLoginActivity.this, ComplaintActivity.class);
                    startActivity(i);
                }
            });
        }
        else{
            Intent i = new Intent(PoliceLoginActivity.this, ComplaintActivity.class);
            startActivity(i);
        }
    }
}
