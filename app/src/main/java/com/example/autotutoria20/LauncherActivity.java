package com.example.autotutoria20;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class LauncherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if user is logged in
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            // User is logged in, redirect to main menu
            Intent mainMenuIntent = new Intent(this, b_main_0_menu.class);
            startActivity(mainMenuIntent);
        } else {
            // User is not logged in, redirect to login screen
            Intent loginIntent = new Intent(this, a_user_1_login.class);
            startActivity(loginIntent);
        }

        // Close this activity
        finish();
    }
}
