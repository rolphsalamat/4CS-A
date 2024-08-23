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
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.concurrent.CountDownLatch;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class n_Worker extends Worker {

    private static final String TAG = "NotificationWorker";
    public static final String CHANNEL_ID = "reminder_channel";
    private static final int NOTIFICATION_ID = 1;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private static final String[][] REMINDERS = {
            {"Time to Learn!", "Come back to learn more about Context Free Grammar."},
            {"Don't Miss Out!", "Continue your journey on Context Free Grammar now."},
            {"Grammar Boost!", "Enhance your skills in Context Free Grammar today."},
            {"Learning Awaits!", "Your Context Free Grammar lessons are waiting for you."},
            {"Stay Sharp!", "Keep up with your Context Free Grammar studies."},
            {"Boost Your Knowledge!", "Dive back into Context Free Grammar."},
            {"Keep Learning!", "Context Free Grammar lessons are just a tap away."},
            {"Refresh Your Skills!", "Review Context Free Grammar to stay ahead."},
            {"Learning Reminder!", "Don’t forget to study Context Free Grammar."},
            {"Continue Learning!", "Your Context Free Grammar lessons are waiting."}
    };

    public n_Worker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: Worker is executing");

        if (!MyApplication.isAppInForeground()) {
            boolean reminderNotification = getReminderNotificationSetting();

            if (reminderNotification) {
                Log.e(TAG, "Reminder Notification is ON");
                try {
                    sendNotification();
                    scheduleNextNotification(8);
                    return Result.success();
                } catch (Exception e) {
                    Log.e(TAG, "Error sending notification", e);
                    return Result.failure();
                }
            } else {
                Log.d(TAG, "Reminder notification is disabled");
                return Result.success();
            }
        } else {
            Log.d(TAG, "App is in foreground; skipping notification.");
            return Result.success();
        }
    }

    private void scheduleNextNotification(int delayTime) {
        OneTimeWorkRequest oneTimeWorkRequest =
                new OneTimeWorkRequest.Builder(n_Worker.class)
                        .setInitialDelay(delayTime, TimeUnit.HOURS)
                        .build();

        WorkManager.getInstance(getApplicationContext()).enqueue(oneTimeWorkRequest);
        Log.e(TAG, "Scheduled next notification in " + delayTime + " hours");
    }


    private boolean getReminderNotificationSetting() {
        final boolean[] reminderNotification = {false};
        final CountDownLatch latch = new CountDownLatch(1);

        String userId = mAuth.getCurrentUser().getUid();
        DocumentReference userRef = db.collection("users").document(userId);
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Boolean reminderNotif = document.getBoolean("Reminder Notification");
                    reminderNotification[0] = reminderNotif != null && reminderNotif;
                }
            } else {
                Log.e(TAG, "Error getting document", task.getException());
            }
            latch.countDown();
        });

        try {
            latch.await(); // Wait for Firestore callback to complete
        } catch (InterruptedException e) {
            Log.e(TAG, "Latch interrupted", e);
        }

        return reminderNotification[0];
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

        // Select a random reminder message
        Random random = new Random();
        int index = random.nextInt(REMINDERS.length);
        String title = REMINDERS[index][0];
        String text = REMINDERS[index][1];

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
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true); // Automatically removes the notification when clicked

        notificationManager.notify(NOTIFICATION_ID, builder.build());
        Log.e(TAG, "Notification sent");
    }
}
