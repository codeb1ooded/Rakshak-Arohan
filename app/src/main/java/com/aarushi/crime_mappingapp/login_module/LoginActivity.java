package com.aarushi.crime_mappingapp.login_module;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aarushi.crime_mappingapp.DashboardActivity;
import com.aarushi.crime_mappingapp.R;
import com.aarushi.crime_mappingapp.utility.PreferenceManagerUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText et_email, et_password;
    Button bt_signup, bt_login;
    private FirebaseAuth mAuth;
    private PreferenceManagerUtils ssn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ssn = new PreferenceManagerUtils(this);
        boolean isRegistered = ssn.retrieveVariable("Registered", false);
        if (isRegistered) {
            navigateToDashboard();
            finish();
        }

        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        bt_signup = (Button) findViewById(R.id.bt_signup);
        bt_login = (Button) findViewById(R.id.bt_login);
        mAuth = FirebaseAuth.getInstance();
        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        if (et_email.getText().toString().isEmpty() || et_password.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.info_valid_details, Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(et_email.getText().toString(), et_password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }


                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user == null) return;
        ssn.storeVariable("email", user.getEmail());
        ssn.storeVariable("uuid", user.getUid());
        ssn.storeVariable("Registered", true);
        navigateToDashboard();
    }

    private void navigateToDashboard() {
        Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
        i.putExtra("is_public_user", true);
        startActivity(i);
    }

}
