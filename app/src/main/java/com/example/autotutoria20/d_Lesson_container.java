package com.example.autotutoria20;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Space;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class d_Lesson_container extends AppCompatActivity {

    private static final String TAG = "Module3Steps";
    private GridLayout gridLayout;
    private AlertDialog dialog;
    private int currentStep = 0; // Track the current step
    private int numberOfSteps = 0; // Track the number of steps

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_3_steps);

        gridLayout = findViewById(R.id.gridLayout);

        // Retrieve the number of steps from SharedPreferences or Intent extras
        SharedPreferences sharedPreferences = getSharedPreferences("ModulePreferences", MODE_PRIVATE);
        numberOfSteps = sharedPreferences.getInt("numberOfSteps", 5); // Default to 5 if not set


        Log.d(TAG, "Number of steps: " + numberOfSteps);

        // Dynamically populate the GridLayout with steps
        populateGridLayout();

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextButtonClicked();
            }
        });

        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitConfirmationDialog();
            }
        });
    }

    private void populateGridLayout() {
        gridLayout.removeAllViews(); // Clear existing views

        for (int i = 0; i < numberOfSteps; i++) {
            // Create a new View for each step
            View stepView = new View(this);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = (int) (10 * getResources().getDisplayMetrics().density);
            params.columnSpec = GridLayout.spec(i * 2, 1, 1f); // Span one column with equal weight
            stepView.setLayoutParams(params);
            stepView.setBackgroundResource(R.drawable.rounded_corners);
            stepView.setTag(i); // Tag the view with its index
            gridLayout.addView(stepView);

            // Log each added step
            Log.d(TAG, "Added step view " + (i + 1));

            // Add a Space between steps except for the last one
            if (i < numberOfSteps - 1) {
                Space space = new Space(this);
                GridLayout.LayoutParams spaceParams = new GridLayout.LayoutParams();
                spaceParams.width = (int) (20 * getResources().getDisplayMetrics().density);
                spaceParams.height = (int) (10 * getResources().getDisplayMetrics().density);
                space.setLayoutParams(spaceParams);
                gridLayout.addView(space);

                // Log each added space
                Log.d(TAG, "Added space after step " + (i + 1));
            }
        }

        // Adjust GridLayout column count
        gridLayout.setColumnCount(numberOfSteps * 2 - 1);
        Log.d(TAG, "GridLayout column count set to " + gridLayout.getColumnCount());
    }

    private void onNextButtonClicked() {
        if (currentStep < numberOfSteps) {
            // Find the current step view by its tag
            View stepView = gridLayout.findViewWithTag(currentStep);
            if (stepView != null) {
                // Change the background color of the current step to blue
                stepView.setBackgroundResource(R.color.facebook);
            }

            currentStep++; // Move to the next step
            Log.d(TAG, "Current step: " + currentStep);
        }

        if (currentStep == numberOfSteps) {
            Toast.makeText(this, "All steps completed!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void showExitConfirmationDialog() {
        // Create a dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Inflate the custom dialog layout
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_exit_confirmation, null);
        builder.setView(dialogView);

        // Initialize the dialog
        dialog = builder.create();

        // Find "Yes" button in custom layout
        Button btnYes = dialogView.findViewById(R.id.exit_module);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle "Yes" button click
                finish(); // Finish the activity
            }
        });

        // Find "No" button in custom layout
        Button btnCancel = dialogView.findViewById(R.id.cancel_exit_module_);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Dismiss the dialog
            }
        });

        // Show the dialog
        dialog.show();
    }

}
