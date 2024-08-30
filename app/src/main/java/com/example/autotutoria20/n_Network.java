package com.example.autotutoria20;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

public class n_Network {


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        String TAG = "n_Network";

        if (connectivityManager != null) {
            Log.e(TAG, "connectivityManager: " + connectivityManager);
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (networkCapabilities != null) {
                Log.e(TAG, "networkCapabilities: " + networkCapabilities);
                return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
            }
        }

        Log.e(TAG, "if statements done.. returning false na..");

        return false;
    }

//    private void showToast(String message) {
//        Toast.makeText(, message, Toast.LENGTH_SHORT).show();
//    }
}