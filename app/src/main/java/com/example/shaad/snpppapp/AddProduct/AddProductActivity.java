package com.example.shaad.snpppapp.AddProduct;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import com.example.shaad.snpppapp.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.IOException;

public class AddProductActivity extends AppCompatActivity {

    private final int REQUEST_CAMERA_CODE = 1001;
    private SurfaceView mSurfaceView;
    private CameraSource mCameraSource;
    private Button mSubmit;
    private MaterialEditText mBarcodeInput;
    private BarcodeDetector mBarcodeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        setUpToolBar();

        mSurfaceView = findViewById(R.id.add_product_surface);
        mSubmit = findViewById(R.id.add_product_submit);
        mBarcodeInput = findViewById(R.id.add_product_barcode);

        mBarcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.EAN_13 | Barcode.EAN_8)
                .build();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mCameraSource = new CameraSource.Builder(this, mBarcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(metrics.heightPixels, metrics.widthPixels)
                .build();

        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback(){
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(AddProductActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(AddProductActivity.this,
                                new String[]{Manifest.permission_group.CAMERA},REQUEST_CAMERA_CODE);

                    }
                    mCameraSource.start(mSurfaceView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                mCameraSource.stop();

            }

        });

        mBarcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> code = detections.getDetectedItems();
                if(code.size() != 0){
                    Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(100);
                    String barcodeValue = code.valueAt(0).displayValue;
                    Intent i = new Intent(AddProductActivity.this,ProductDetailActivity.class);
                    //Extra Tabs Are Opening
                    i.putExtra("barcodeValue",barcodeValue);
                    startActivity(i);
                    finish();

                }
            }

        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String barcodeValue =  mBarcodeInput.getText().toString();

                if(TextUtils.isEmpty(barcodeValue) || (barcodeValue.length() != 8 && barcodeValue.length() != 13)){
                    mBarcodeInput.setError("Invalid Barcode");
                }else {
                    Intent intent = new Intent(AddProductActivity.this,ProductDetailActivity.class);
                    intent.putExtra("barcodeValue",barcodeValue);
                    startActivity(intent);
                    finish();
                }

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_CODE:
            {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddProductActivity.this,
                            new String[]{Manifest.permission_group.CAMERA},REQUEST_CAMERA_CODE);
                }
                try {
                    mCameraSource.start(mSurfaceView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            break;
        }
    }

    private void setUpToolBar() {
        Toolbar toolbar = findViewById(R.id.add_product_top_toolbar);
        setSupportActionBar(toolbar);

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle("Add Products");
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
