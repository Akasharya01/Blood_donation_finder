package com.example.xyz;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class internet {
    public boolean isconnected(Context context){
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null){
            if(networkInfo.isConnected())
                return true;
            else
                return false;

        }
        else
            return false;
    }
}
