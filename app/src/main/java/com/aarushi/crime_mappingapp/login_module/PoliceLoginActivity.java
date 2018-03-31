package com.aarushi.crime_mappingapp.login_module;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aarushi.crime_mappingapp.BaseActivity;
import com.aarushi.crime_mappingapp.ComplaintActivity;
import com.aarushi.crime_mappingapp.DashboardActivity;
import com.aarushi.crime_mappingapp.R;
import com.aarushi.crime_mappingapp.utility.PreferenceManagerUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PoliceLoginActivity extends BaseActivity {

    EditText et_policeemail, et_policepassword;
    Button btn_policelogin;
    private FirebaseAuth mAuth;
    private PreferenceManagerUtils preferenceManagerUtils;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_login);

        preferenceManagerUtils = new PreferenceManagerUtils(PoliceLoginActivity.this);

        et_policeemail = (EditText)findViewById(R.id.et_policeemail);
        et_policepassword = (EditText)findViewById(R.id.et_policepassword);
        btn_policelogin = (Button) findViewById(R.id.bt_policelogin);
        mAuth = FirebaseAuth.getInstance();

        if(!preferenceManagerUtils.isLogin()){
            btn_policelogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: authenticate from server (Subsidiary)
                    preferenceManagerUtils.login(et_policeemail.getText().toString());
                    Intent i = new Intent(PoliceLoginActivity.this, DashboardActivity.class);
                    i.putExtra("is_public_user", false);
                    startActivity(i);
                    finish();
                }
            });
        }
        else{
            Intent i = new Intent(PoliceLoginActivity.this, DashboardActivity.class);
            i.putExtra("is_public_user", false);
            startActivity(i);
            finish();
        }

    }

    private void loginUser() {
        if (et_policeemail.getText().toString().isEmpty() || et_policepassword.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.info_valid_details, Toast.LENGTH_SHORT).show();
            return;
        }

        showProgressDialog();
        mAuth.signInWithEmailAndPassword(et_policeemail.getText().toString(), et_policepassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideProgressDialog();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(PoliceLoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user == null) return;
        preferenceManagerUtils.storeVariable("email", user.getEmail());
        preferenceManagerUtils.storeVariable("uuid", user.getUid());
        preferenceManagerUtils.storeVariable("Registered", true);
        navigateToDashboard();
    }

    private void navigateToDashboard() {
        Intent i = new Intent(this, DashboardActivity.class);
        i.putExtra("is_public_user", false);
        startActivity(i);
    }

    private void showProgressDialog() {
        if (pd == null) {
            pd = new ProgressDialog(this);
            pd.setIndeterminate(true);
            pd.setMessage("Please wait..");
            pd.setCancelable(false);
        }
        if (!pd.isShowing()) pd.show();
    }

    private void hideProgressDialog() {
        if (pd!=null && pd.isShowing()) {
            pd.dismiss();
        }
    }
}
