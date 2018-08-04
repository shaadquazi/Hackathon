package com.example.shaad.snpppapp.FAQ;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.example.shaad.snpppapp.R;
import com.example.shaad.snpppapp.Utility.FAQ;
import com.example.shaad.snpppapp.Utility.RecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FaqActivity extends AppCompatActivity {
    private static final String TAG = "FaqActivity";

    private RelativeLayout mFaqLayout;
    private RecyclerView mFaqRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;

    private List<FAQ> mList = new ArrayList<>();

    private DatabaseReference mDatabaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        setUpToolBar();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("FAQ");

        mFaqRecyclerView = findViewById(R.id.faq_recycler_view);
        mRecyclerAdapter = new RecyclerAdapter(mList, FaqActivity.this);
        mFaqRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mFaqRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mFaqRecyclerView.setHasFixedSize(true);
        mFaqRecyclerView.setAdapter(mRecyclerAdapter);

        data();


    }

    private void setUpToolBar() {
        Toolbar toolbar = findViewById(R.id.faq_top_toolbar);
        setSupportActionBar(toolbar);

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle("F.A.Q");
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

    private void data() {
        //get data from fireBase
        mList.add(new FAQ("This is question 1","Answer is here "));
        mList.add(new FAQ("This is question 1","Answer is here "));
        mList.add(new FAQ("This is question 1","Answer is here "));
        mList.add(new FAQ("This is question 1","Answer is here "));
        mList.add(new FAQ("This is question 1","Answer is here "));
        mList.add(new FAQ("This is question 1","Answer is here "));
        mList.add(new FAQ("This is question 1","Answer is here "));
        mList.add(new FAQ("This is question 1","Answer is here "));


        mRecyclerAdapter.notifyDataSetChanged();
    }


}
