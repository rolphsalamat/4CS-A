package com.example.autotutoria20;

import android.content.Intent;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class d_Lesson_container extends AppCompatActivity {

    private static final String TAG = "Module3Steps";
    private GridLayout gridLayout;
    private AlertDialog dialog;
    private int currentStep = 0; // Track the current step
    private int numberOfSteps = 0; // Track the number of steps
    private FirebaseFirestore db;
    private String learningMode;
    private String currentLesson;
    private String currentModule;
    private Map<String, Class<?>> navigationMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_3_steps);

        // Initialize the navigation map
        navigationMap = new HashMap<>();
        navigationMap.put("Progressive Mode_Lesson 1", c_Lesson_progressive_1.class);
        navigationMap.put("Progressive Mode_Lesson 2", c_Lesson_progressive_2.class);
        navigationMap.put("Progressive Mode_Lesson 3", c_Lesson_progressive_3.class);
        navigationMap.put("Progressive Mode_Lesson 4", c_Lesson_progressive_4.class);
        navigationMap.put("Free Use Mode_Lesson 1", c_Lesson_freeuse_1.class);
        navigationMap.put("Free Use Mode_Lesson 2", c_Lesson_freeuse_2.class);
        navigationMap.put("Free Use Mode_Lesson 3", c_Lesson_freeuse_3.class);
        navigationMap.put("Free Use Mode_Lesson 4", c_Lesson_freeuse_4.class);
        // Add more mappings as needed

        gridLayout = findViewById(R.id.gridLayout);
        db = FirebaseFirestore.getInstance();

        // Retrieve the data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("ModulePreferences", MODE_PRIVATE);
        numberOfSteps = sharedPreferences.getInt("numberOfSteps", -1); // Default to -1 if not set
        learningMode = sharedPreferences.getString("learningMode", null);
        currentLesson = sharedPreferences.getString("currentLesson", null);
        currentModule = sharedPreferences.getString("currentModule", null);

        Log.d(TAG, "Number of steps: " + numberOfSteps);
        Log.d(TAG, "Learning Mode: " + learningMode);
        Log.d(TAG, "Current Lesson: " + currentLesson);
        Log.d(TAG, "Current Module: " + currentModule);

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

        // This code works, just need to implement a sample of Pre-Test and Post-Test
        if (currentStep == 0) { // First [0] Step | Pre-Test
            Toast.makeText(d_Lesson_container.this, "Pre-Test", Toast.LENGTH_SHORT).show();
            // Show the pre-test in this part of the code

        } else if (currentStep == numberOfSteps-1) { // Last [n] Step | Post-Test
            Toast.makeText(d_Lesson_container.this, "Post-Test", Toast.LENGTH_SHORT).show();
            // Show the post-test in this part of the code
        }

        for (int i = 0; i < numberOfSteps; i++) {


            // Create a new View for each step
            View stepView = new View(this);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = (int) (10 * getResources().getDisplayMetrics().density);
            params.columnSpec = GridLayout.spec(i * 2, 1, 1f); // Span one column with equal weight
            stepView.setLayoutParams(params);

            // Highlight the completed steps and current step
            if (i < currentStep) {
                stepView.setBackgroundResource(R.drawable.rounded_corners_completed); // Define a different drawable for completed steps
            } else if (i == currentStep) {
                stepView.setBackgroundResource(R.drawable.rounded_corners); // Define a different drawable for the current step
            } else {
                stepView.setBackgroundResource(R.drawable.rounded_corners); // Default drawable for uncompleted steps
            }

            stepView.setTag(i); // Tag the view with its index
            gridLayout.addView(stepView);

            // Log each added step
//            Log.d(TAG, "Added step view " + (i + 1));

            // Add a Space between steps except for the last one
            if (i < numberOfSteps - 1) {
                Space space = new Space(this);
                GridLayout.LayoutParams spaceParams = new GridLayout.LayoutParams();
                spaceParams.width = (int) (20 * getResources().getDisplayMetrics().density);
                spaceParams.height = (int) (10 * getResources().getDisplayMetrics().density);
                space.setLayoutParams(spaceParams);
                gridLayout.addView(space);

                // Log each added space
//                Log.d(TAG, "Added space after step " + (i + 1));
            }
        }

        // Adjust GridLayout column count
        gridLayout.setColumnCount(numberOfSteps * 2 - 1);
//        Log.d(TAG, "GridLayout column count set to " + gridLayout.getColumnCount());
    }

    private void onNextButtonClicked() {

        currentStep++; // Increment the current step

        updateCurrentModuleInDatabase();
        Log.d(TAG, "Next button clicked. Current step: " + currentStep);

        if (currentStep >= numberOfSteps) {
            Log.d(TAG, "Next button clicked. Final step: " + currentStep);

            // Construct the key for the navigation map
            String key = learningMode + "_" + currentLesson;

            Log.d("Next Button", "String key: " + key);

            // Retrieve the target class from the map
            Class<?> targetClass = navigationMap.get(key);

            if (targetClass != null) {

//                // Create an intent to navigate to the target class
//                Intent intent = new Intent(d_Lesson_container.this, targetClass);
//
//                // Optionally, add any data you want to pass back
//                intent.putExtra("moduleCompleted", currentModule);
//                intent.putExtra("numberOfStepsCompleted", numberOfSteps); // Pass the number of steps
//
//                // Store user information in SharedPreferences
//                SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                editor.putString("Current Module", currentModule);
//                editor.putInt("Module Progress", currentStep); // Save the progress here
//
//                Log.d("exit lesson container","Module: " + currentModule);
//                Log.d("exit lesson container","Step: " + currentStep);
//
//
//                editor.apply();
//
//                startActivity(intent);

                // Finish this activity to prevent going back here
                finish();

            } else {
                // Handle the case where the target class is not found in the map
                Toast.makeText(this, "Navigation target not found for " + key, Toast.LENGTH_SHORT).show();
            }
        } else {
            updateCurrentModuleInDatabase();
            populateGridLayout(); // Refresh the GridLayout to reflect the updated step
        }
    }

    private void updateCurrentModuleInDatabase() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");

        if (!userId.isEmpty()) {
            // Reference to the user's document in the database
            DocumentReference userRef = db.collection("users").document(userId)
                    .collection(learningMode).document(currentLesson);

            // Attempt to update the current step value in the database
            userRef.update(currentModule, currentStep)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Current step updated successfully in the database.");
                            } else {
                                Log.d(TAG, "Failed to update current step: " +  task.getException().getMessage());
                                if (task.getException() instanceof FirebaseFirestoreException) {
                                    FirebaseFirestoreException firestoreException = (FirebaseFirestoreException) task.getException();
                                    if (firestoreException.getCode() == FirebaseFirestoreException.Code.NOT_FOUND) {
                                        Log.d(TAG, "The specified document does not exist.");
                                    }
                                }
                            }
                        }
                    });
        } else {
            Log.d(TAG, "User ID not found in SharedPreferences.");
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
        Button btnCancel = dialogView.findViewById(R.id.cancel_exit_module);
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
