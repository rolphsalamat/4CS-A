package com.example.autotutoria20;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

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

        // Load settings from Firestore
        loadSettingsFromFirestore();

        // Set listeners to show toast on toggle
        switchAppUpdate.setOnCheckedChangeListener((buttonView, isChecked) -> {
            showToast("App Update Notification toggled");
            updateDatabase("App Update Notification", isChecked);
        });

        switchNewCourse.setOnCheckedChangeListener((buttonView, isChecked) -> {
            showToast("New Course Available Notification toggled");
            updateDatabase("New Course Available Notification", isChecked);
        });

        switchReminder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            showToast("Reminder Notification toggled");
            updateDatabase("Reminder Notification", isChecked);
            scheduleNotificationWorker();  // Schedule the worker regardless of the switch state
        });

//        Switch reminderSwitch = findViewById(R.id.switch_reminder);
//        LinearLayout reminderIntervalLayout = findViewById(R.id.reminder_interval_layout);
//        Button confirmIntervalButton = findViewById(R.id.confirm_interval_button);
//
//        reminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    reminderIntervalLayout.setVisibility(View.VISIBLE);
//                    confirmIntervalButton.setVisibility(View.VISIBLE);
//                } else {
//                    reminderIntervalLayout.setVisibility(View.GONE);
//                    confirmIntervalButton.setVisibility(View.GONE);
//                }
//            }
//        });
//
//        confirmIntervalButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EditText intervalEditText = findViewById(R.id.edit_text_interval);
//                Spinner intervalUnitSpinner = findViewById(R.id.spinner_interval_unit);
//
//                String interval = intervalEditText.getText().toString();
//                String intervalUnit = intervalUnitSpinner.getSelectedItem().toString();
//
//                // Handle the confirmed interval and unit (e.g., save to preferences, update UI, etc.)
//                Toast.makeText(MyActivity.this, "Interval: " + interval + " " + intervalUnit, Toast.LENGTH_SHORT).show();
//            }
//        });


    }

    // Method to show toast
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Method to load settings from Firestore
    private void loadSettingsFromFirestore() {
        String userId = mAuth.getCurrentUser().getUid();
        DocumentReference userRef = db.collection("users").document(userId);
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {

                    Boolean updateNotif = document.getBoolean("App Update Notification");
                    Boolean newCourse = document.getBoolean("New Course Available Notification");
                    Boolean reminderNotif = document.getBoolean("Reminder Notification");

                    // Provide default values if the retrieved values are null
                    boolean updateNotifValue = (updateNotif != null) ? updateNotif : false;
                    boolean newCourseValue = (newCourse != null) ? newCourse : false;
                    boolean reminderNotifValue = (reminderNotif != null) ? reminderNotif : false;

                    // Update Firestore with default values if any of the values were null
                    Map<String, Object> updates = new HashMap<>();
                    if (updateNotif == null) updates.put("App Update Notification", false);
                    if (newCourse == null) updates.put("New Course Available Notification", false);
                    if (reminderNotif == null) updates.put("Reminder Notification", false);

                    // Actually if newly registered lahat di na need tong line of code for verification eh.. LOL
                    if (!updates.isEmpty()) {
                        userRef.update(updates)
                                .addOnSuccessListener(aVoid -> Log.d("Settings.java", "Default values set in Firestore"))
                                .addOnFailureListener(e -> Log.e("Settings.java", "Error setting default values", e));
                    }

                    // Set switches according to the saved values
                    switchAppUpdate.setChecked(updateNotifValue);
                    switchNewCourse.setChecked(newCourseValue);
                    switchReminder.setChecked(reminderNotifValue);

                    scheduleNotificationWorker();  // Schedule the worker based on the retrieved value

                    String TAG = "Settings.java";

                    Log.e(TAG, "App Update Notification: " + updateNotifValue);
                    Log.e(TAG, "New Course Available Notification: " + newCourseValue);
                    Log.e(TAG, "Reminder Notification: " + reminderNotifValue);

                } else {
                    // Document does not exist, set default values and update Firestore
                    Map<String, Object> defaultValues = new HashMap<>();
                    defaultValues.put("App Update Notification", false);
                    defaultValues.put("New Course Available Notification", false);
                    defaultValues.put("Reminder Notification", false);

                    userRef.set(defaultValues)
                            .addOnSuccessListener(aVoid -> Log.d("Settings.java", "Default document created in Firestore"))
                            .addOnFailureListener(e -> Log.e("Settings.java", "Error creating default document", e));

                    switchAppUpdate.setChecked(false);
                    switchNewCourse.setChecked(false);
                    switchReminder.setChecked(false);

                    scheduleNotificationWorker();
                }
            } else {
                showToast("Failed to load settings");
                Log.e("Settings.java", "get failed with ", task.getException());
            }
        });
    }

    // Method to update settings in Firestore
    private void updateDatabase(String key, boolean value) {
        String userId = mAuth.getCurrentUser().getUid();
        DocumentReference userRef = db.collection("users").document(userId);
        userRef.update(key, value)
                .addOnSuccessListener(aVoid -> showToast("Database updated"))
                .addOnFailureListener(e -> showToast("Failed to update database"));

        Log.e("Settings.java", "updateDatabase(" + key + ", " + value + ");");
    }

    // Method to schedule notification worker
    private void scheduleNotificationWorker() {
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(NotificationWorker.class)
                .addTag("ReminderNotification")
                .build();

        WorkManager.getInstance(this).enqueue(workRequest);
    }
}
