package com.example.shaad.snpppapp.Utility;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.shaad.snpppapp.R;

/**
 * Created by Shaad on 30-03-2018.
 */

public class OnlineUserViewHolder extends RecyclerView.ViewHolder {

    public TextView mOnlineUser;

    public OnlineUserViewHolder(View itemView) {
        super(itemView);
        mOnlineUser = itemView.findViewById(R.id.online_user_name);
    }
}