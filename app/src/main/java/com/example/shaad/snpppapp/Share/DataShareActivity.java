package com.example.shaad.snpppapp.Share;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.shaad.snpppapp.R;
import com.example.shaad.snpppapp.Utility.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class DataShareActivity extends AppCompatActivity {
    File root;
    private Button mCSV, mExcel, mOpen;
    private ArrayList<Product> productArrayList;

    public void makeCSVFile(String filename, String contents){
        File file = new File(root, filename + ".csv");

        try{
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.write(contents);

            myOutWriter.close();

            fOut.flush();
            fOut.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_share);

        setUpToolBar();

        mCSV = findViewById(R.id.create_csv);
        mExcel = findViewById(R.id.create_excel);
        mOpen  =findViewById(R.id.open_file);

        root = new File(Environment.getExternalStorageDirectory(), "SNPPPAPP");
        if(!root.exists()){
            root.mkdirs();
        }

        mCSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<Product> arrayList = new ArrayList<Product>();
                FirebaseDatabase.getInstance().getReference().child("Product")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Product p = snapshot.getValue(Product.class);
                                    arrayList.add(p);
                                }
                                Toast.makeText(DataShareActivity.this, "Data fetch done", Toast.LENGTH_SHORT).show();

                                Toast.makeText(DataShareActivity.this, "First element:", Toast.LENGTH_SHORT).show();

                                String fileName = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(new Date());

                                String contents = "";

                                for(Product p : arrayList)
                                    contents = contents + p.toString() + "\n";

                                DataShareActivity.this.makeCSVFile(fileName, contents);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(DataShareActivity.this, "Error. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
        mExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent.setType("file/*");

//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
//                            +  File.separator + "SNPPPAPP" + File.separator);
//                intent.setDataAndType(uri, "text/csv");
//                startActivity(Intent.createChooser(intent, "Open folder"));


//                startActivity(intent);
            }
        });

//		addNewProducts();

    }
    private void setUpToolBar() {
        Toolbar toolbar = findViewById(R.id.share_top_toolbar);
        setSupportActionBar(toolbar);

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle("Share Date");
//		mActionBar.setHomeAsUpIndicator(R.drawable.ic_back_arrow);

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
