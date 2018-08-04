package com.example.shaad.snpppapp.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shaad.snpppapp.DashBoard.DashboardActivity;
import com.example.shaad.snpppapp.ForgotPassword.ForgotPasswordActivity;
import com.example.shaad.snpppapp.R;
import com.example.shaad.snpppapp.SignUp.SignUpActivity;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    RelativeLayout mLoginLayout;

    private MaterialEditText mUserEmail, mUserpassword;
    private Button mLoginBtn;
    private SignInButton mGoogleBtn;
    private TextView mForgotPassword;
    private Button mRegister;
    private ProgressBar mProgressBar;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginLayout = findViewById(R.id.login_activity_layout);
        mUserEmail = findViewById(R.id.login_email);
        mUserpassword = findViewById(R.id.login_password);

        mLoginBtn = findViewById(R.id.login_btn);
        mGoogleBtn = findViewById(R.id.login_google_btn);

        mForgotPassword = findViewById(R.id.login_forgot_password_txt);

        mRegister = findViewById(R.id.login_register_btn);
        mProgressBar = findViewById(R.id.login_progress);
        mProgressBar.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    mUserEmail.setText("");
                    mUserpassword.setText("");
                    startActivity(new Intent(LoginActivity.this,DashboardActivity.class));
                    finish();
                }
            }
        };

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                String email = mUserEmail.getText().toString().trim();
                String password = mUserpassword.getText().toString();
                boolean valid = true;

                if(TextUtils.isEmpty(email)){
                    mUserEmail.setError("Enter Your Email");
                    valid = false;
                }
                if(TextUtils.isEmpty(password) || password.length() < 6){
                    mUserpassword.setError("You must have 6 characters in your password");
                    valid = false;
                }
                if(valid == false){
                    return;
                }else{
                    loginUser(email, password);
                }
            }
        });

        //Google login

        //
        mGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                Toast.makeText(LoginActivity.this, "Google", Toast.LENGTH_SHORT).show();
            }
        });

        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mProgressBar.setVisibility(View.GONE);
                        if(!task.isSuccessful()){
                            Snackbar snackbar = Snackbar
                                    .make(mLoginLayout, "Login Failed", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }
                });
    }
}
