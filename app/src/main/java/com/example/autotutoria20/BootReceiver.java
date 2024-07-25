package com.example.autotutoria20;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import java.util.concurrent.TimeUnit;

public class BootReceiver extends BroadcastReceiver {

    private static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {

            // Minutes interval ng Notification Reminder
            int notificationReminderInterval = 1;

            // Create a periodic work request to be triggered every 15 minutes
            PeriodicWorkRequest periodicWorkRequest =
                    new PeriodicWorkRequest.Builder(NotificationWorker.class, notificationReminderInterval, TimeUnit.MINUTES)
                            .build();

            WorkManager.getInstance(context).enqueue(periodicWorkRequest);
            Log.e(TAG, "Periodic work request enqueued after boot");
        }
    }
}
