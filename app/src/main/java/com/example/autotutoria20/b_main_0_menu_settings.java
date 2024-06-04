package com.example.autotutoria20;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class b_main_0_menu_settings extends AppCompatActivity {

    private Switch switchAppUpdate;
    private Switch switchNewCourse;
    private Switch switchReminder;
    private Button backButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_main_0_menu_settings);

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

        // Set listeners to show toast on toggle
        switchAppUpdate.setOnCheckedChangeListener((buttonView, isChecked) -> {
            showToast("App Update Notification toggled");
        });

        switchNewCourse.setOnCheckedChangeListener((buttonView, isChecked) -> {
            showToast("New Course Available Notification toggled");
        });

        switchReminder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            showToast("Reminder Notification toggled");
        });
    }

    // Method to show toast
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
