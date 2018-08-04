package com.example.shaad.snpppapp.ForgotPassword;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.shaad.snpppapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class ForgotPasswordActivity extends AppCompatActivity {

    private RelativeLayout mForgotLayout;
    private MaterialEditText mEmail;
    private Button mForgotBtn;
    private ProgressBar mProgressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mForgotLayout = findViewById(R.id.forgot_password_layout);

        mEmail = findViewById(R.id.forgot_password_email);
        mForgotBtn = findViewById(R.id.forgot_password_btn);

        mProgressBar = findViewById(R.id.forgot_password_progress);
        mProgressBar.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();

        mForgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                String email = mEmail.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Enter Email");
                    mProgressBar.setVisibility(View.INVISIBLE);
                    return;
                }

                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                mProgressBar.setVisibility(View.INVISIBLE);
                                if(!task.isSuccessful()){
                                    Snackbar snackbar = Snackbar
                                            .make(mForgotLayout, "Failed to send Email. Try Again!", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }else {
                                    Snackbar snackbar = Snackbar
                                            .make(mForgotLayout, "Check Email to reset password.", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            finish();
                                        }
                                    }, 3500);
                                }
                            }
                        });

            }


        });
    }
}
