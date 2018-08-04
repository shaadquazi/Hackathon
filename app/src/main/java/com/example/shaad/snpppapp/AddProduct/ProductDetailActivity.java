package com.example.shaad.snpppapp.AddProduct;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shaad.snpppapp.R;
import com.example.shaad.snpppapp.Utility.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ProductDetailActivity extends AppCompatActivity {

    private static final String TAG = "ProductDetailActivity";
    private static final int REQUEST_LOCATION = 1;
    private RelativeLayout mDetailProduct;
    private LocationManager locationManager;

    private String latitude, longitude;

    private Button mDataSubmitBtn;
    private MaterialEditText mMarketName, mPinCode;
    private MaterialEditText mSellingPrice, mMRP;
    private MaterialEditText mDescription, mName;
    private Spinner mCategory;
    private TextView mBarcode;
    private AutoCompleteTextView mCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        mDetailProduct = findViewById(R.id.product_detail);
        setUpToolBar();
        initiallization();

        String[] cities = getResources().getStringArray(R.array.indian_cities);
        Intent intent = getIntent();
        final String barcodeValue = intent.getStringExtra("barcodeValue");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, cities);
        mCity = findViewById(R.id.apm_city);
        mCity.setThreshold(1);
        mCity.setAdapter(adapter);



        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        }

        if(barcodeValue != null){
            mBarcode.setText(barcodeValue);
        }
        Toast.makeText(this, "Barcode = "+barcodeValue+"\nLat = "+latitude+"\nLon = "+longitude, Toast.LENGTH_SHORT).show();

        mDataSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String category = mCategory.getSelectedItem().toString();

                String name = mName.getText().toString().trim();
                String description = mDescription.getText().toString().trim();
                String selling_price = mSellingPrice.getText().toString().trim();
                String mrp = mMRP.getText().toString().trim();
                String shop_name = mMarketName.getText().toString().trim();
                String city = mCity.getText().toString().trim();
                String pincode = mPinCode.getText().toString().trim();

                if(TextUtils.equals(category,"Select Category")){
                    Toast.makeText(ProductDetailActivity.this, "Please Select a Category", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(name) || name.equals("")){
                    mName.setError("Invalid Input");
                    return;
                }
                if(TextUtils.isEmpty(description) || description.equals("")){
                    mDescription.setError("Invalid Input");
                    return;
                }
                if(TextUtils.isEmpty(selling_price) || selling_price.equals("")){
                    mSellingPrice.setError("Invalid Input");
                    return;
                }
                if(TextUtils.isEmpty(mrp) || mrp.equals("")){
                    mMRP.setError("Invalid Input");
                    return;
                }
                if(TextUtils.isEmpty(shop_name) || shop_name.equals("")){
                    mMarketName.setError("Invalid Input");
                    return;
                }
                if(TextUtils.isEmpty(city) || city.equals("")){
                    mCity.setError("Invalid Input");
                    return;
                }
                if(TextUtils.isEmpty(pincode) || pincode.equals("") || pincode.length() != 6 ){
                    mPinCode.setError("Invalid Pin Code");
                    return;
                }

                String user_name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                if(TextUtils.isEmpty(user_name)){
                    user_name = "";
                }

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                Date currentDate = Calendar.getInstance().getTime();
                String timeStamp = df.format(currentDate);

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Product");
                Product product = new Product(barcodeValue,category,name,description,selling_price,mrp,timeStamp,"",user_name,"",latitude,longitude,shop_name,city,pincode);
                reference.push().setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ProductDetailActivity.this, "Product Added Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });

            }
        });





    }

    private void initiallization() {
        mDataSubmitBtn = findViewById(R.id.apm_submit_btn);
        mMarketName = findViewById(R.id.apm_market_name);
        mPinCode = findViewById(R.id.apm_pincode);
        mSellingPrice = findViewById(R.id.apm_selling_price);
        mMRP = findViewById(R.id.apm_mrp);
        mDescription = findViewById(R.id.apm_description);
        mName = findViewById(R.id.apm_name);
        mCategory = findViewById(R.id.apm_categories_spn);
        mBarcode = findViewById(R.id.apm_set_barcode);
    }


    private void setUpToolBar() {
        Toolbar toolbar = findViewById(R.id.product_detail_top_toolbar);
        setSupportActionBar(toolbar);

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle("Enter Product Details");
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

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(ProductDetailActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (ProductDetailActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager
                .PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(ProductDetailActivity.this, new String[]{Manifest.permission
                    .ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

            if (location != null) {
                latitude = String.valueOf(location.getLatitude());
                longitude = String.valueOf(location.getLongitude());
            } else  if (location1 != null) {
                latitude = String.valueOf(location1.getLatitude());
                longitude = String.valueOf(location1.getLongitude());

            } else  if (location2 != null) {
                latitude = String.valueOf(location2.getLatitude());
                longitude = String.valueOf(location2.getLongitude());
            }else{
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.ic_location_failed)
                        .setTitle("Failed")
                        .setMessage("Failed To Fetch Location! Try Again Later")
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }

                        })
                        .setNegativeButton("Cancel", null)
                        .show();
                Log.d(TAG,"Fetching Lat & Lon Failed");
            }
        }
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }



}
