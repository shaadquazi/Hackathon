package com.example.shaad.snpppapp.DashBoard;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.shaad.snpppapp.AddProduct.AddProductActivity;
import com.example.shaad.snpppapp.Analysis.AnalysisActivity;
import com.example.shaad.snpppapp.Authenticate.AuthenticateActivity;
import com.example.shaad.snpppapp.EditProfile.EditProfileActivity;
import com.example.shaad.snpppapp.FAQ.FaqActivity;
import com.example.shaad.snpppapp.History.HistoryActivity;
import com.example.shaad.snpppapp.Profile.ProfileActivity;
import com.example.shaad.snpppapp.R;
import com.example.shaad.snpppapp.Setting.SettingActivity;
import com.example.shaad.snpppapp.Share.DataShareActivity;
import com.example.shaad.snpppapp.Utility.ConnectionDetector;
import com.example.shaad.snpppapp.Utility.OnlineUserViewHolder;
import com.example.shaad.snpppapp.Utility.Product;
import com.example.shaad.snpppapp.Utility.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Shaad on 05-03-2018.
 */

public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = "DashboardActivity";
    private DrawerLayout mDashboardDrawerLayout;
    private FloatingActionButton mFloatingActionButton;

    FirebaseUser mUser;
    DatabaseReference onlineRef, currentUserRef, counterRef;
    FirebaseRecyclerAdapter<User,OnlineUserViewHolder> adapter;

    private RecyclerView mOnlineUserList;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        mFloatingActionButton = findViewById(R.id.dashboard_flt_btn);

        mOnlineUserList = findViewById(R.id.show_online_user);
        layoutManager = new LinearLayoutManager(this);
        mOnlineUserList.setLayoutManager(layoutManager);

        setupDashboardToolBar();
        ConnectionDetector cd = new ConnectionDetector(this);
        if(!cd.isConnected()){
            Snackbar snackbar = Snackbar
                    .make(mDashboardDrawerLayout, "No Internet Connection", Snackbar.LENGTH_LONG)
                    .setAction("Turn on", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                            wifi.setWifiEnabled(true);
                        }
                    });

            snackbar.show();
        }

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, AddProductActivity.class));
            }
        });
        getAllPermission();

        onlineRef = FirebaseDatabase.getInstance().getReference().child(".info/connected");
        counterRef = FirebaseDatabase.getInstance().getReference("lastOnline");
        currentUserRef = FirebaseDatabase.getInstance().getReference("lastOnline").child(mUser.getUid());

        setUpOnlineSystem();

        updateUserList();

    }

    @Override
    public void onBackPressed() {
        logoutConfirm();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDashboardDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    private void setupDashboardToolBar() {
        Toolbar toolbar = findViewById(R.id.dashboard_top_toolbar);
        mDashboardDrawerLayout = findViewById(R.id.dashboard_drawer_layout);
        NavigationView navigationView = findViewById(R.id.dashboard_side_nav_bar);

        setSupportActionBar(toolbar);
        setUpNavigationHeader(navigationView);

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                // close drawer when item is tapped
                mDashboardDrawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.side_nav_add_product:
                        startActivity(new Intent(DashboardActivity.this, AddProductActivity.class));
                        return true;
                    case R.id.side_nav_profile:
                        startActivity(new Intent(DashboardActivity.this, ProfileActivity.class));
                        return true;
                    case R.id.side_nav_analysis:
                        startActivity(new Intent(DashboardActivity.this, AnalysisActivity.class));
                        return true;
                    case R.id.side_nav_history:
                        startActivity(new Intent(DashboardActivity.this, HistoryActivity.class));
                        return true;
                    case R.id.side_nav_share:
                        startActivity(new Intent(DashboardActivity.this, DataShareActivity.class));
                        return true;
                    case R.id.side_nav_authenticate:
                        startActivity(new Intent(DashboardActivity.this, AuthenticateActivity.class));
                        return true;
                    case R.id.side_nav_faq:
                        startActivity(new Intent(DashboardActivity.this, FaqActivity.class));
                        return true;
                    case R.id.side_nav_setting:
                        startActivity(new Intent(DashboardActivity.this, SettingActivity.class));
                        return true;
                    case R.id.side_nav_logout:
                        logoutConfirm();
                        return true;
                }
                return true;
            }
        });



    }

    private void setUpNavigationHeader(NavigationView navigationView) {
        View header = navigationView.getHeaderView(0);

        CircleImageView mNavPic = header.findViewById(R.id.side_nav_head_icon);
        TextView mNavName = header.findViewById(R.id.side_nav_head_name);
        TextView mNavEmail = header.findViewById(R.id.side_nav_head_email);



        if(mUser != null){
            try{
                Picasso.get().load(mUser.getPhotoUrl().toString()).into(mNavPic);
            }catch (Exception e){
                Log.d(TAG, "Could Not Load Side Nav Header photo");
                e.printStackTrace();
            }
            try {
                mNavName.setText(mUser.getDisplayName());
                mNavEmail.setText(mUser.getEmail());
            }catch (Exception e){
                Log.d(TAG, "Could Not Load Side Nav Header name or email");
                e.printStackTrace();
            }
        }else {
            Log.d(TAG, "Firebase User is Null");
        }
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
                        currentUserRef.removeValue();
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    private void updateUserList() {

        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(counterRef, User.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<User, OnlineUserViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OnlineUserViewHolder holder, int position, @NonNull User model) {
                holder.mOnlineUser.setText(model.getName());
            }

            @NonNull
            @Override
            public OnlineUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_user_online_layout, parent, false);

                return new OnlineUserViewHolder(view);
            }

        };
        adapter.notifyDataSetChanged();
        mOnlineUserList.setAdapter(adapter);

    }

    private void setUpOnlineSystem() {
        onlineRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue(Boolean.class)){
                    currentUserRef.onDisconnect().removeValue();
                    counterRef.child(mUser.getUid())
                            .setValue(new User(mUser.getEmail(),"Online"));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        counterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    User user = postSnapshot.getValue(User.class);
                    Log.d(TAG,user.getName()+"\t\t"+user.getStatus());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void getAllPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"WRITE_EXTERNAL_STORAGE Permission is granted");
            } else {
                Log.v(TAG,"WRITE_EXTERNAL_STORAGE Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"READ_EXTERNAL_STORAGE Permission is granted");
            } else {
                Log.v(TAG,"READ_EXTERNAL_STORAGE Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);
            }
            if (checkSelfPermission(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"INTERNET Permission is granted");
            } else {
                Log.v(TAG,"INTERNET Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 15);
            }
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"CAMERA Permission is granted");
            } else {
                Log.v(TAG,"CAMERA Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},11);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"ACCESS_WIFI_STATE Permission is granted");
            } else {
                Log.v(TAG,"ACCESS_WIFI_STATE Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_WIFI_STATE},1021);
            }
            if (checkSelfPermission(Manifest.permission.CHANGE_WIFI_STATE) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"CHANGE_WIFI_STATE Permission is granted");
            } else {
                Log.v(TAG,"CHANGE_WIFI_STATE Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CHANGE_WIFI_STATE},1201);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"ACCESS_FINE_LOCATION Permission is granted");
            } else {
                Log.v(TAG,"ACCESS_FINE_LOCATION Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1201);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"ACCESS_COARSE_LOCATION Permission is granted");
            } else {
                Log.v(TAG,"ACCESS_COARSE_LOCATION Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1201);
            }

        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
        }
    }



}
