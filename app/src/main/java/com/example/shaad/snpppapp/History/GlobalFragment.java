package com.example.shaad.snpppapp.History;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shaad.snpppapp.R;
import com.example.shaad.snpppapp.Utility.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GlobalFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Product> productList;

    public GlobalFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        recyclerView = view.findViewById(R.id.user_list);
        RecyclerViewAdapter recyclerAdapter = new RecyclerViewAdapter(getContext(), productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(recyclerAdapter);
        return view;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        productList = new ArrayList<>();

        DatabaseReference dbProducts = FirebaseDatabase.getInstance().getReference("NewProductsWithGmail");

        dbProducts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    productList.clear();
                    for (DataSnapshot productSnapshot : dataSnapshot.getChildren()){
                        Product p = productSnapshot.getValue(Product.class);
                        productList.add(p);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
