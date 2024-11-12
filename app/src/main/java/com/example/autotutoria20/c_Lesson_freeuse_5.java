package com.example.autotutoria20;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class c_Lesson_freeuse_5 extends AppCompatActivity {

    private AlertDialog dialog; // Declare the dialog variable outside
    private CustomLoadingDialog loadingDialog;
    private boolean[] cardCompletionStatus = {false, false, false}; // Track completion status of each card
    private c_Lesson_feedback feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_lesson_freeuse_5);

        // Find all card views
        CardView card1 = findViewById(R.id.card1);
        CardView card2 = findViewById(R.id.card2);
        CardView card3 = findViewById(R.id.card3);

        // Assuming numberOfSteps is determined based on your logic
        int numberOfStepsForCard1 = z_Lesson_steps.lesson_5_steps[0];
        int numberOfStepsForCard2 = z_Lesson_steps.lesson_5_steps[1];
        int numberOfStepsForCard3 = z_Lesson_steps.lesson_5_steps[2];

        // Set click listeners for each card
        setCardClickListener(card1, 1, numberOfStepsForCard1);
        setCardClickListener(card2, 2, numberOfStepsForCard2);
        setCardClickListener(card3, 3, numberOfStepsForCard3);

        // Fetch progress data from the database
        fetchProgressData();

        // Find exit button
        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showLoadingDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.e("onResume()", "I has returned");

        // Fetch the latest progress data
        fetchProgressData();
    }

    private void fetchProgressData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId;

        // Check for authenticated user
        try {
            userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        } catch (NullPointerException e) {
            Log.e("fetchProgressData", "User not authenticated", e);
            showToast("User not authenticated");
            return;
        }

        // Reference to the user's progress data in "Free Use Mode"
        DocumentReference progressRef = db.collection("users")
                .document(userId)
                .collection("Free Use Mode")
                .document("Lesson 5");

        // Fetch the progress data
        progressRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String TAG = "fetchProgressData()";
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "Document exists");

                        // Get all fields and their values
                        Map<String, Object> progressData = document.getData();
                        if (progressData != null) {
                            for (Map.Entry<String, Object> entry : progressData.entrySet()) {
                                String key = entry.getKey(); // e.g., M1, M2, etc.
                                Object value = entry.getValue(); // The value associated with the key

                                Log.d(TAG, "Key: " + key + ", Value: " + value);

                                // Check if the value is a Map
                                if (value instanceof Map) {
                                    Map<String, Object> moduleData = (Map<String, Object>) value;

                                    // Retrieve the "Progress" field from the map
                                    if (moduleData.containsKey("Progress")) {
                                        Object progressValue = moduleData.get("Progress");

                                        // Check if the progressValue is of type Long
                                        if (progressValue instanceof Long) {
                                            int progress = ((Long) progressValue).intValue();
                                            Log.d(TAG, key + " Progress: " + progress);

                                            // Extract the second character and convert it to an integer
                                            int moduleNumber = Character.getNumericValue(key.charAt(1));

                                            // Update the UI or process the progress value as needed
                                            updateUI(moduleNumber, progress);
                                        } else {
                                            Log.d(TAG, key + " Progress is not of expected type Long.");
                                        }
                                    } else {
                                        Log.d(TAG, key + " does not contain a 'Progress' field.");
                                    }
                                } else {
                                    Log.d(TAG, key + " is not a Map.");
                                }
                            }
                        } else {
                            Log.d(TAG, "Progress data is null");
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }

                // Hide loading dialog after fetching data
                hideLoadingDialog();
            }
        });
    }

    private void updateUI(int key, int progress) {
        Log.d("updateUI()", "Updating UI for module " + key + " with progress " + progress);

        // Update progress text views
        TextView module1ProgressText = findViewById(R.id.freeuse_lesson_1_module_5);
        TextView module2ProgressText = findViewById(R.id.freeuse_lesson_2_module_5);
        TextView module3ProgressText = findViewById(R.id.freeuse_lesson_3_module_5);

        // Verify passed values...
        Log.d("updateUI()", "Module: " + key + " | Progress : " + progress);

        String newText;

        switch (key) {
            case 1:
                newText = progress + "/" + L_lesson_sequence.getNumberOfSteps("M1_Lesson 5");
                if (module1ProgressText != null) {
                    module1ProgressText.setText(newText);
                } else {
                    Log.e("updateUI", "TextView for module 1 not found");
                }
                if (progress >= L_lesson_sequence.getNumberOfSteps("M1_Lesson 5")) {
                    cardCompletionStatus[0] = true; // Mark card 1 as completed
                }
                break;
            case 2:
                newText = progress + "/" + L_lesson_sequence.getNumberOfSteps("M2_Lesson 5");
                if (module2ProgressText != null) {
                    module2ProgressText.setText(newText);
                } else {
                    Log.e("updateUI", "TextView for module 2 not found");
                }
                if (progress >= L_lesson_sequence.getNumberOfSteps("M2_Lesson 5")) {
                    cardCompletionStatus[1] = true; // Mark card 2 as completed
                }
                break;
            case 3:
                newText = progress + "/" + L_lesson_sequence.getNumberOfSteps("M3_Lesson 5");
                if (module3ProgressText != null) {
                    module3ProgressText.setText(newText);
                } else {
                    Log.e("updateUI", "TextView for module 3 not found");
                }
                if (progress >= L_lesson_sequence.getNumberOfSteps("M3_Lesson 5")) {
                    cardCompletionStatus[2] = true; // Mark card 3 as completed

                    Log.e("Comleted Lesson!", "Calling Feedback Class");
                    feedback = new c_Lesson_feedback(this); // Initialize feedback object
                    feedback.retrieveBKTScore("Free Use Mode", "Lesson 5");

                }
                break;
            default:
                Log.d("updateUI", "Invalid module number: " + key);
                break;
        }
    }

    // Method to set click listener for each card
    private void setCardClickListener(CardView cardView, final int cardNumber, int numberOfSteps) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(n_Network.isNetworkAvailable(getBaseContext()))) {
                    showToast("Please connect to a network.");
                } else {
                    navigateToModuleActivity(d_Lesson_container.class, cardNumber, numberOfSteps);
                }
            }
        });
    }

    private void navigateToModuleActivity(Class<?> moduleActivityClass, int cardNumber, int numberOfSteps) {
        // Store user information in SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("ModulePreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("numberOfSteps", numberOfSteps);
        editor.putString("learningMode", "Free Use Mode");
        editor.putString("currentLesson", "Lesson 5");
        editor.putString("currentModule", "M" + cardNumber);
        editor.putBoolean("isCompleted", cardCompletionStatus[cardNumber - 1]); // Pass the completion status
        editor.apply();

        Intent intent = new Intent(c_Lesson_freeuse_5.this, moduleActivityClass);
        startActivity(intent);
    }

    // Helper method to show toast message
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

//    private void showExitConfirmationDialog() {
//        // Create a dialog builder
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        // Inflate the custom dialog layout
//        View dialogView = getLayoutInflater().inflate(R.layout.dialog_logout, null);
//        builder.setView(dialogView);
//
//        // Find "Yes" button in custom layout
//        Button btnYes = dialogView.findViewById(R.id.exit_module);
//        btnYes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Handle "Yes" button click
//                Toast.makeText(c_Lesson_freeuse_5.this, "Exiting module", Toast.LENGTH_SHORT).show();
//                finish(); // Finish the activity
//            }
//        });
//
//        // Find "No" button in custom layout
//        Button btnCancel = dialogView.findViewById(R.id.cancel_exit_module);
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Handle "No" button click
//                Toast.makeText(c_Lesson_freeuse_5.this, "Cancel Exit", Toast.LENGTH_SHORT).show();
//                dialog.dismiss(); // Dismiss the dialog
//            }
//        });
//
//        // Create and show the dialog
//        dialog = builder.create();
//        dialog.show();
//    }

    // Show the loading dialog
    private void showLoadingDialog() {
        loadingDialog = new CustomLoadingDialog(this);
        loadingDialog.setCancelable(false); // Prevent the dialog from being closed
        loadingDialog.show();
    }

    // Hide the loading dialog
    private void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}
