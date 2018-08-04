package com.example.shaad.snpppapp.History;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.shaad.snpppapp.R;
import com.example.shaad.snpppapp.Utility.Product;

public class DetailedProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_product);

        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra("selectedProduct");


        TextView name = findViewById(R.id.dp_name);
        name.setText(product.getName());

        TextView category = findViewById(R.id.dp_category);
        category.setText(product.getCategory());

        TextView desc = findViewById(R.id.dp_description);
        desc.setText(product.getDescription());

        TextView market = findViewById(R.id.dp_market);
        market.setText(product.getMarketName());

        TextView barcode  = findViewById(R.id.dp_barcode);
        barcode.setText(product.getBarcode());

        TextView cb  = findViewById(R.id.dp_created_by);
        cb.setText(product.getCreatedBy());

        TextView mb  = findViewById(R.id.dp_modified_by);
        mb.setText(product.getModifiedBy());

        TextView pin  = findViewById(R.id.db_pin_code);
        pin.setText(product.getPincode());

        TextView c  = findViewById(R.id.dp_city);
        c.setText(product.getCity());

        TextView lo  = findViewById(R.id.dp_long);
        lo.setText(product.getLongitude());

        TextView lt  = findViewById(R.id.dp_lat);
        lt.setText(product.getLatitude());

        TextView mr  = findViewById(R.id.dp_mrp);
        mr.setText(product.getMrp());

        TextView sp  = findViewById(R.id.dp_selling_price);
        sp.setText(product.getSellingPrice());

        TextView da  = findViewById(R.id.dp_date_added);
        da.setText(product.getDateAdded());

        TextView dm  = findViewById(R.id.dp_date_modified);
        dm.setText(product.getModifiedBy());


    }


}
