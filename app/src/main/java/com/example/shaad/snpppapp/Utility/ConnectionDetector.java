package com.example.shaad.snpppapp.Utility;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Shaad on 31-03-2018.
 */

public class ConnectionDetector {

    Context mContext;
    public ConnectionDetector(Context context) {

        this.mContext = context;
    }
    public boolean isConnected(){
        ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Service.CONNECTIVITY_SERVICE);
        if(connectivity!=null){
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if(info != null){
                if(info.getState() == NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }
        return false;
    }

}
