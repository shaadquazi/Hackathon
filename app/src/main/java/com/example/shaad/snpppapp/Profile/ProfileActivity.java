package com.example.shaad.snpppapp.Profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shaad.snpppapp.DashBoard.DashboardActivity;
import com.example.shaad.snpppapp.EditProfile.EditProfileActivity;
import com.example.shaad.snpppapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    private TextView mTotalProduct;
    private TextView mEmail;
    private TextView mName;
    private CircleImageView mPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setUpToolBar();

        mTotalProduct = findViewById(R.id.profile_total_steps);

        mEmail = findViewById(R.id.profile_email);
        mName = findViewById(R.id.profile_user_name);
        mPicture = findViewById(R.id.profile_user_image);

        setUpUserData();


    }

    private void setUpUserData() {
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

        if(mUser != null){
            try{
                Picasso.get().load(mUser.getPhotoUrl().toString()).into(mPicture);
            }catch (Exception e){
                Log.d(TAG, "Could Not load Profile  photo");
                e.printStackTrace();
            }
            try {
                mName.setText(mUser.getDisplayName());
                mEmail.setText(mUser.getEmail());
            }catch (Exception e){
                Log.d(TAG, "Could Not load name or email");
                e.printStackTrace();
            }
        }else {
            Log.d(TAG, "Firebase User is Null");
        }
    }

    private void setUpToolBar() {
        Toolbar toolbar = findViewById(R.id.share_top_toolbar);
        setSupportActionBar(toolbar);

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle("");
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_back_arrow);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.profile_option_edit:
                startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
                return true;
            case R.id.profile_option_logout:
                logoutConfirm();
                return true;
            case R.id.profile_option_help:
                Toast.makeText(this, "HEEEELLLLPPP!!!", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logoutConfirm() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_logout)
                .setTitle("Exit")
                .setMessage("Are you sure you want to Logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

}
