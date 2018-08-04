package com.example.shaad.snpppapp.Analysis;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.shaad.snpppapp.R;
import com.example.shaad.snpppapp.Utility.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CityFromProductActivity extends AppCompatActivity {
    private AutoCompleteTextView mAutoInput;
    private Button mSubmit;
    private ListView mShowList;
    private boolean finished = false;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_from_product);
        setUpToolBar();

        String[] product = getResources().getStringArray(R.array.product_list);

        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, product);
        mAutoInput = findViewById(R.id.c2p_input);
        mAutoInput.setThreshold(1);
        mAutoInput.setAdapter(adapter);

        mShowList = findViewById(R.id.c2p_list_view);

        mSubmit = findViewById(R.id.c2p_submit);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String productName = mAutoInput.getText().toString().trim();

                FirebaseDatabase.getInstance().getReference("NewProducts")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                progressBar.setVisibility(View.VISIBLE);
                                ArrayList<String> productList = new ArrayList<>();
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Product product = snapshot.getValue(Product.class);
                                    if (productName.equals(product.getName()))
                                        productList.add(product.getCity());
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter(CityFromProductActivity.this,android.R.layout.simple_list_item_1, productList);
                                progressBar.setVisibility(View.GONE);
                                mShowList.setAdapter(adapter);
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });


            }
        });
    }

    private void setUpToolBar() {
        Toolbar toolbar = findViewById(R.id.c2p_top_toolbar);
        setSupportActionBar(toolbar);

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle("Get List of Cities");
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
