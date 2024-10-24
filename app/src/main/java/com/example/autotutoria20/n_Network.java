package com.example.autotutoria20;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.Objects;

public class n_Network {

    public static Button button;


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        String TAG = "n_Network";

        if (connectivityManager != null) {
            Log.e(TAG, "connectivityManager: " + connectivityManager);
            NetworkCapabilities networkCapabilities =
                    connectivityManager
                            .getNetworkCapabilities
                                    (connectivityManager.getActiveNetwork());
            if (networkCapabilities != null) {
                Log.e(TAG, "networkCapabilities: " + networkCapabilities);
                return networkCapabilities
                        .hasCapability
                                (NetworkCapabilities.NET_CAPABILITY_INTERNET);
            }
        }

        Log.e(TAG, "if statements done.. returning false na..");

        // Inflate the custom layout for the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // Inflate the layout using LayoutInflater
        android.view.LayoutInflater inflater = android.view.LayoutInflater.from(context);
        android.view.View dialogView = inflater.inflate(R.layout.n_no_network_dialog, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();

        // Convert 450dp to pixels
        int heightInPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 450, context.getResources().getDisplayMetrics());

        // Adjust dialog window parameters
        Objects.requireNonNull(dialog.getWindow()).setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT, // Width = match_parent
                heightInPx // Height = 450dp in pixels
        );

        // Find the button inside the inflated view and set the OnClickListener
        button = dialogView.findViewById(R.id.button_okay_no_internet);
        button.setOnClickListener(v -> dialog.dismiss()); // dismiss the dialog on button click

        return false;

    }

    public static boolean isNetworkAvailable2(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        String TAG = "n_Network";

        if (connectivityManager != null) {
            Log.e(TAG, "connectivityManager: " + connectivityManager);
            NetworkCapabilities networkCapabilities =
                    connectivityManager
                            .getNetworkCapabilities
                                    (connectivityManager.getActiveNetwork());
            if (networkCapabilities != null) {
                Log.e(TAG, "networkCapabilities: " + networkCapabilities);
                return networkCapabilities
                        .hasCapability
                                (NetworkCapabilities.NET_CAPABILITY_INTERNET);
            }
        }

        Log.e(TAG, "if statements done.. returning false na..");

        // Inflate the custom layout for the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // Inflate the layout using LayoutInflater
        android.view.LayoutInflater inflater = android.view.LayoutInflater.from(context);
        android.view.View dialogView = inflater.inflate(R.layout.n_no_network_dialog, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();

        // Convert 450dp to pixels
        int heightInPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 450, context.getResources().getDisplayMetrics());

        // Adjust dialog window parameters
        Objects.requireNonNull(dialog.getWindow()).setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT, // Width = match_parent
                heightInPx // Height = 450dp in pixels
        );

        // Find the button inside the inflated view and set the OnClickListener
        button = dialogView.findViewById(R.id.button_okay_no_internet);
        button.setOnClickListener(v -> {
            if (context instanceof Activity) {
                ((Activity) context).finish(); // Use finish() on the Activity
            }
        });

        return false;

    }

    public static boolean isNetworkAvailable3(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        String TAG = "n_Network";

        if (connectivityManager != null) {
            Log.e(TAG, "connectivityManager: " + connectivityManager);
            NetworkCapabilities networkCapabilities =
                    connectivityManager
                            .getNetworkCapabilities
                                    (connectivityManager.getActiveNetwork());
            if (networkCapabilities != null) {
                Log.e(TAG, "networkCapabilities: " + networkCapabilities);
                return networkCapabilities
                        .hasCapability
                                (NetworkCapabilities.NET_CAPABILITY_INTERNET);
            }
        }

        Log.e(TAG, "if statements done.. returning false na..");
        return false;

    }

}