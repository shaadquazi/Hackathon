package com.example.shaad.snpppapp.Authenticate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.shaad.snpppapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class AuthenticateActivity extends AppCompatActivity {

    private RelativeLayout mAuthenticateLayout;
    private MaterialEditText mAuthEmail;
    private Button mEmailBtn;



    private ProgressBar mProgressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);

        mAuthenticateLayout = findViewById(R.id.authenticate_layout);

        setUpToolBar();

        mAuthEmail = findViewById(R.id.authenticate_email);
        mEmailBtn = findViewById(R.id.authenticate_btn);

        mProgressBar = findViewById(R.id.authenticate_progress);
        mProgressBar.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();

        mEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mAuthEmail.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    mAuthEmail.setError("Enter Email");
                    return;
                }
                mProgressBar.setVisibility(View.VISIBLE);
                if(mAuth.getCurrentUser().isEmailVerified()){
                    mProgressBar.setVisibility(View.GONE);
                    Snackbar snackbar = Snackbar
                            .make(mAuthenticateLayout, "You Are Already Verified", Snackbar.LENGTH_LONG);
                    snackbar.show();

                }else {
                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            mProgressBar.setVisibility(View.GONE);
                            if(!task.isSuccessful()){
                                Snackbar snackbar = Snackbar
                                        .make(mAuthenticateLayout, "Failed to send Email. Try Again!", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }else {
                                Snackbar snackbar = Snackbar
                                        .make(mAuthenticateLayout, "Check Email to Verify your Account.", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        }
                    });
                }
                mAuthEmail.setText("");
            }
        });


    }

    private void setUpToolBar() {
        Toolbar toolbar = findViewById(R.id.authenticate_top_toolbar);
        setSupportActionBar(toolbar);

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle("Authenticate Your Account");
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_back_arrow);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
