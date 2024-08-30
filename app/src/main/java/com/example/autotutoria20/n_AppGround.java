package com.example.autotutoria20;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class n_AppGround extends Application implements LifecycleObserver {

    private static boolean isAppInForeground;

    @Override
    public void onCreate() {
        super.onCreate();
        // Register lifecycle observer
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onEnterForeground() {
        isAppInForeground = true;
        Log.d("MyApplication", "App is in foreground");

        // Cancel any scheduled notifications when the app is in the foreground
        WorkManager.getInstance(this).cancelAllWorkByTag("notificationWork");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onEnterBackground() {
        isAppInForeground = false;
        Log.d("MyApplication", "App is in background");

        // Schedule a notification after 8 hours when the app goes to the background
        scheduleNotificationWorker();
    }

    public static boolean isAppInForeground() {
        return isAppInForeground;
    }

    private void scheduleNotificationWorker() {
        OneTimeWorkRequest notificationWorkRequest =
                new OneTimeWorkRequest.Builder(n_Worker.class)
                        .setInitialDelay(8, TimeUnit.HOURS)  // Set delay to 8 hours
                        .addTag("notificationWork")  // Add a tag for easy cancellation
                        .build();

        WorkManager.getInstance(this).enqueue(notificationWorkRequest);
        Log.d("MyApplication", "Notification worker scheduled to run in 8 hours.");
    }
}
