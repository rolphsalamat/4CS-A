package com.example.autotutoria20;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NotificationWorker extends Worker {

    private static final String TAG = "NotificationWorker";
    public static final String CHANNEL_ID = "reminder_channel";
    private static final int NOTIFICATION_ID = 1;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            sendNotification();
            return Result.success();
        } catch (Exception e) {
            Log.e(TAG, "Error sending notification", e);
            return Result.failure();
        }
    }

    private void sendNotification() {
        NotificationManager notificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager == null) {
            Log.e(TAG, "NotificationManager is null");
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Reminder Channel";
            String description = "Channel for reminders";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
            Log.e(TAG, "Notification channel created");
        }

        // Create an intent that will be fired when the user clicks the notification
        Intent intent = new Intent(getApplicationContext(), LauncherActivity.class); // Replace MainActivity with the activity you want to open
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE // Use FLAG_IMMUTABLE if targeting Android 12 or higher
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.autotutoria_logo) // Ensure this icon resource exists and is valid
                .setContentTitle("Reminder")
                .setContentText("Angeeeel wash my ass")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true); // Automatically removes the notification when clicked

        notificationManager.notify(NOTIFICATION_ID, builder.build());
        Log.e(TAG, "Notification sent");
    }


//    private void sendNotification() {
//        NotificationManager notificationManager =
//                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//
//        if (notificationManager == null) {
//            Log.e(TAG, "NotificationManager is null");
//            return;
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = "Reminder Channel";
//            String description = "Channel for reminders";
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            notificationManager.createNotificationChannel(channel);
//            Log.e(TAG, "Notification channel created");
//        }
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
//                .setSmallIcon(R.drawable.autotutoria_logo) // Ensure this icon resource exists and is valid
//                .setContentTitle("Reminder")
//                .setContentText("Angeeeel wash my ass")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        notificationManager.notify(NOTIFICATION_ID, builder.build());
//        Log.e(TAG, "Notification sent");
//    }
}
