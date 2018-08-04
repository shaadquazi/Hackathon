package com.example.shaad.snpppapp.SignUp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.shaad.snpppapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.rengwuxian.materialedittext.MaterialEditText;


public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";
    private TextView mAlreadyLogin;
    private Button mSignUpBtn;
    private MaterialEditText mUserName, mUserEmail, mUserpassword;
    private ProgressBar mProgressBar;

    private FirebaseAuth mAuth;
    private RelativeLayout mSignUpLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mSignUpLayout = findViewById(R.id.sign_up_activity_layout);

        mUserName = findViewById(R.id.sign_up_name);
        mUserEmail = findViewById(R.id.sign_up_email);
        mUserpassword = findViewById(R.id.sign_up_password);

        mSignUpBtn = findViewById(R.id.sign_up_btn);

        mAlreadyLogin = findViewById(R.id.sign_up_already_register_txt);

        mProgressBar = findViewById(R.id.sign_up_progress);
        mProgressBar.setVisibility(View.GONE);



        mAuth = FirebaseAuth.getInstance();


        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mUserName.getText().toString().trim();
                String email = mUserEmail.getText().toString().trim();
                String password = mUserpassword.getText().toString();
                boolean valid = true;

                if(TextUtils.isEmpty(name)){
                    mUserName.setError("Enter Your Name");
                    valid = false;
                }
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
                    createNewUser(name, email, password);
                }
            }
        });

        mAlreadyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void createNewUser(final String name, String email, String password) {
        mProgressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mProgressBar.setVisibility(View.GONE);
                        mUserName.setText("");
                        mUserEmail.setText("");
                        mUserpassword.setText("");

                        if(task.isSuccessful()){
                            //add username to firebase


                            //test
                            FirebaseUser user = mAuth.getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User Name updated.");
                                            }else {
                                                Log.d(TAG, "User Name failed.");
                                            }
                                        }
                                    });
                            //test end
                            finish();
                        }else{
                            Snackbar snackbar = Snackbar
                                    .make(mSignUpLayout, "User Not Created! Try Again", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }
                });
    }



}
