package com.aarushi.crime_mappingapp.login_module;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.FirebaseAuth;

import com.aarushi.crime_mappingapp.DashboardActivity;
import com.aarushi.crime_mappingapp.R;

public class SignupActivity extends AppCompatActivity {

    EditText userId1,password1,confirmPassword,phoneno;
    Button signup2;
    final Context context=this;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        userId1=(EditText)findViewById(R.id.userId1);
        password1=(EditText)findViewById(R.id.password1);
        confirmPassword=(EditText)findViewById(R.id.confirmpassword);
        phoneno=(EditText)findViewById(R.id.phone_no);
        signup2=(Button)findViewById(R.id.signup2);
        signup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=userId1.getText().toString();
                String password=password1.getText().toString();
                String confirm=confirmPassword.getText().toString();
                String phno=phoneno.getText().toString();

//                SharedPreferences sharedPref = PreferenceManagerUtils.getDefaultSharedPreferences(context);
//                SharedPreferences.Editor editor = sharedPref.edit();
//                editor.putBoolean("Registered", true);
//                editor.putString("Email", mail);
//                editor.putString("Password", password);
//                editor.apply();
                createAccount(mail,password,phno);

                Intent i=new Intent(SignupActivity.this,DashboardActivity.class);
                startActivity(i);
            }
        });
    }

    private void createAccount(String email, final String password, final String phno){



        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Authenticate", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putBoolean("Registered", true);
                        editor.putString("Number", phno);
                        //editor.putString("Email", mail);
                        //editor.putString("Password", password);
                        editor.apply();
                        if (!task.isSuccessful()) {
                            Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });


    }
}
