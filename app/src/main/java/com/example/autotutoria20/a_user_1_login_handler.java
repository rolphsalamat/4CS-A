package com.example.autotutoria20;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class a_user_1_login_handler extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String TAG = "a_user_1_login_handler";

        // Check if user is logged in
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        Log.e(TAG, "retrieve isLoggedIn: " + isLoggedIn);

        if (isLoggedIn) {
            Log.e(TAG, "You are already logged in!");

            // User is logged in, check if they have completed the tutorial
            boolean isTutorialCompleted = sharedPreferences.getBoolean("isTutorialCompleted", false);
            Log.e(TAG, "retrieve isTutorialCompleted: " + isTutorialCompleted);

            if (isTutorialCompleted) {
                Log.e(TAG, "isTutorialCompleted: " + isTutorialCompleted);
                // User has completed the tutorial, redirect to main menu
                Intent mainMenuIntent = new Intent(this, b_main_0_menu.class);
                startActivity(mainMenuIntent);
            } else {
                // User is logged in but has not completed the tutorial, redirect to tutorial
                Intent tutorialIntent = new Intent(this, b_main_0_menu_tutorial.class);
                startActivity(tutorialIntent);
            }
        } else {
            // User is not logged in, redirect to login screen
            Intent loginIntent = new Intent(this, a_user_1_login.class);
            startActivity(loginIntent);
        }

        createNotificationChannel();
        finish();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel Name";
            String description = "Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(n_Worker.CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
