package com.example.autotutoria20;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class b_main_0_menu_settings extends AppCompatActivity {

    private Switch switchAppUpdate;
    private Switch switchNewCourse;
    private Switch switchReminder;
    private Button backButton;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_main_0_menu_settings);

        Log.e("Settings.java", "IM HERE!!!");

        // Initialize Firestore and FirebaseAuth
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // Initialize switches
        switchAppUpdate = findViewById(R.id.switch_app_update);
        switchNewCourse = findViewById(R.id.switch_new_course);
        switchReminder = findViewById(R.id.switch_reminder);

        // Initialize Back Button
        backButton = findViewById(R.id.settings_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Load settings from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserSettings", MODE_PRIVATE);
        boolean updateNotif = sharedPreferences.getBoolean("App Update Notification", false);
        boolean newCourse = sharedPreferences.getBoolean("New Course Available Notification", false);
        boolean reminderNotif = sharedPreferences.getBoolean("Reminder Notification", false);

        // Set switches according to the saved values
        switchAppUpdate.setChecked(updateNotif);
        switchNewCourse.setChecked(newCourse);
        switchReminder.setChecked(reminderNotif);

        String TAG = "Settings.java";

        Log.e(TAG, "App Update Notification: " + updateNotif);
        Log.e(TAG, "New Course Available Notification: " + newCourse);
        Log.e(TAG, "Reminder Notification: " + reminderNotif);

        // Set listeners to show toast on toggle
        switchAppUpdate.setOnCheckedChangeListener((buttonView, isChecked) -> {
            showToast("App Update Notification toggled");
            saveSetting("AppUpdateNotification", isChecked);
            updateDatabase("App Update Notification", isChecked);
        });

        switchNewCourse.setOnCheckedChangeListener((buttonView, isChecked) -> {
            showToast("New Course Available Notification toggled");
            saveSetting("NewCourseNotification", isChecked);
            updateDatabase("New Course Available Notification", isChecked);
        });

        switchReminder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            showToast("Reminder Notification toggled");
            saveSetting("ReminderNotification", isChecked);
            updateDatabase("Reminder Notification", isChecked);
        });
    }

    // Method to show toast
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Method to save settings to SharedPreferences
    private void saveSetting(String key, boolean value) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSettings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    // Method to update settings in Firestore
    private void updateDatabase(String key, boolean value) {
        String userId = mAuth.getCurrentUser().getUid();
        DocumentReference userRef = db.collection("users").document(userId);
        userRef.update(key, value)
                .addOnSuccessListener(aVoid -> showToast("Database updated"))
                .addOnFailureListener(e -> showToast("Failed to update database"));
    }
}
