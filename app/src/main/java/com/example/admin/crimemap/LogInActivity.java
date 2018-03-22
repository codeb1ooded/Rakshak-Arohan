package com.example.admin.crimemap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;

public class LogInActivity extends AppCompatActivity {
    EditText muserId;
    EditText mpassword;
    Button mlogIn, msignup1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        final Context context=this;














        muserId = (EditText) findViewById(R.id.userId);
        mpassword = (EditText) findViewById(R.id.password);
        mlogIn = (Button) findViewById(R.id.login);
        msignup1 = (Button) findViewById(R.id.signup1);
        mlogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DashboardActivity.class);
                startActivity(intent);

            }
        });

        //Handle the sign up button
        msignup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }





    }

