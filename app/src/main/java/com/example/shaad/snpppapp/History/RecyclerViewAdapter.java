package com.example.shaad.snpppapp.History;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shaad.snpppapp.R;
import com.example.shaad.snpppapp.Utility.Product;

import java.util.List;

/**
 * Created by Shaad on 31-03-2018.
 */

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    Context mContext;
    List<Product> mList;

    public RecyclerViewAdapter(Context mContext, List<Product> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_user_history, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        final Product product = mList.get(position);
        holder.mPName.setText(product.getName());
        holder.mPCity.setText(product.getCity());
        holder.mPDate.setText(product.getDateAdded());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailedProductActivity.class);
                intent.putExtra("selectedProduct", (Parcelable) product);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mPName, mPCity, mPDate;

        public MyViewHolder(View itemView) {
            super(itemView);

            mPName = itemView.findViewById(R.id.product_name);
            mPCity = itemView.findViewById(R.id.product_city);
            mPDate = itemView.findViewById(R.id.product_date_added);

        }
    }
}
