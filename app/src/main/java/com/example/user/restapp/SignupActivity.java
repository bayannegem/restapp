package com.example.user.restapp;

import android.content.Intent;
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

public class SignupActivity extends AppCompatActivity {
    private EditText etNameSignup;
    private EditText etUsernameSignup;
    private EditText etPasswordSignup;
    private EditText etConfirmPassword;
    private Button btnSignUp;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etNameSignup = (EditText)findViewById(R.id.etNameSignup);
        etUsernameSignup = (EditText)findViewById(R.id.etUsernameSignup);
        etPasswordSignup = (EditText)findViewById(R.id.etPasswordSignup);
        etConfirmPassword = (EditText)findViewById(R.id.etConfirmPassword);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    //Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    //Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


    }

    public void registerUser(View view) {
        String username = etUsernameSignup.getText().toString();
        String password = etPasswordSignup.getText().toString();
        String confirmpassword = etConfirmPassword.getText().toString();

        if (!password.equals(confirmpassword))
        {
            Toast.makeText(SignupActivity.this, R.string.do_not_match,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, R.string.auth_register_success, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignupActivity.this, RestFeedActivity.class));
                        }
                        else
                        {
                            Toast.makeText(SignupActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
