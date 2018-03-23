package com.aarushi.crime_mappingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity {

    EditText userId1,password1,confirmPassword;
    Button signup2;
    final Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        userId1=(EditText)findViewById(R.id.userId1);
        password1=(EditText)findViewById(R.id.password1);
        confirmPassword=(EditText)findViewById(R.id.confirmpassword);
        signup2=(Button)findViewById(R.id.signup2);
        signup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=userId1.getText().toString();
                String password=password1.getText().toString();
                String confirm=confirmPassword.getText().toString();
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("Registered", true);
                editor.putString("Email", mail);
                editor.putString("Password", password);
                editor.apply();

                Intent i=new Intent(SignupActivity.this,DashboardActivity.class);
                startActivity(i);
            }
        });
    }
}
