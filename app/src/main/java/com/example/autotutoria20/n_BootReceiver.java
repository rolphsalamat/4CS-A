package com.example.autotutoria20;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import java.util.concurrent.TimeUnit;

public class n_BootReceiver extends BroadcastReceiver {

    private static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {

            // Schedule the first notification with a delay of 15 seconds
            scheduleOneTimeWorkRequest(context, 8);
        }
    }

    private void scheduleOneTimeWorkRequest(Context context, int delayInHours) {
        OneTimeWorkRequest oneTimeWorkRequest =
                new OneTimeWorkRequest.Builder(n_Worker.class)
                        .setInitialDelay(delayInHours, TimeUnit.HOURS)
                        .build();

        WorkManager.getInstance(context).enqueue(oneTimeWorkRequest);
        Log.e(TAG, "One-time work request scheduled with " + delayInHours + " seconds delay");
    }
}


