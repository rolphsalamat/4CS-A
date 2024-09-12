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

public class c_Lesson_freeuse_7 extends AppCompatActivity {

    private AlertDialog dialog; // Declare the dialog variable outside
    private CustomLoadingDialog loadingDialog;
    private boolean[] cardCompletionStatus = {false}; // Track completion status of the card
    private c_Lesson_feedback feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_lesson_freeuse_7);

        feedback = new c_Lesson_feedback(); // Initialize feedback object


        // Find all card views
        CardView card1 = findViewById(R.id.card1);

        // Assuming numberOfSteps is determined based on your logic
        int numberOfStepsForCard1 = z_Lesson_steps.lesson_7_steps[0];

        // Set click listeners for each card
        setCardClickListener(card1, 1, numberOfStepsForCard1);

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
        // Assuming you are using Firebase Firestore to store progress data
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Reference to the user's progress data
        DocumentReference progressRef =
                db.collection("users")
                        .document(userId)
                        .collection("Free Use Mode")
                        .document("Lesson 7");

        progressRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String TAG = "fetchProgressData()";
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "Document exists fetchProgressData()");

                        // Get all fields and their values
                        Map<String, Object> progressData = document.getData();

                        if (progressData != null) {
                            for (Map.Entry<String, Object> entry : progressData.entrySet()) {
                                String key = entry.getKey();
                                Object value = entry.getValue();

                                if (value instanceof Long) {
                                    int progress = ((Long) value).intValue();
                                    Log.d(TAG, key + " Progress: " + progress);

                                    // Extract the second character and convert it to an integer
                                    int moduleNumber = Character.getNumericValue(key.charAt(1));

                                    // Update the UI or process the progress value as needed
                                    updateUI(moduleNumber, progress);
                                } else {
                                    Log.d(TAG, key + " is not of expected type.");
                                }
                            }
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
                hideLoadingDialog();
            }
        });
    }

    private void updateUI(int key, int progress) {
        Log.d("updateUI()", "Updating UI for module " + key + " with progress " + progress);

        // Update progress text views
        TextView module1ProgressText = findViewById(R.id.freeuse_lesson_1_module_7);

        // Verify passed values...
        Log.d("updateUI()", "Module: " + key + " | Progress : " + progress);

        String newText;

        switch (key) {
            case 1:
                newText = progress + "/" + L_lesson_sequence.getNumberOfSteps("M1_Lesson 7");
                if (module1ProgressText != null) {
                    module1ProgressText.setText(newText);
                } else {
                    Log.e("updateUI", "TextView for module 1 not found");
                }
                if (progress >= L_lesson_sequence.getNumberOfSteps("M1_Lesson 7")) {
                    cardCompletionStatus[0] = true; // Mark card 1 as completed

                    Log.e("Comleted Lesson!", "Calling Feedback Class");
                    feedback.retrieveBKTScore("Free Use Mode", "Lesson 7");

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
        editor.putString("currentLesson", "Lesson 7");
        editor.putString("currentModule", "M" + cardNumber);
        editor.putBoolean("isCompleted", cardCompletionStatus[cardNumber - 1]); // Pass the completion status
        editor.apply();

        Intent intent = new Intent(c_Lesson_freeuse_7.this, moduleActivityClass);
        startActivity(intent);
    }

    // Helper method to show toast message
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showExitConfirmationDialog() {
        // Create a dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Inflate the custom dialog layout
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_exit_confirmation, null);
        builder.setView(dialogView);

        // Find "Yes" button in custom layout
        Button btnYes = dialogView.findViewById(R.id.exit_module);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle "Yes" button click
                Toast.makeText(c_Lesson_freeuse_7.this, "Exiting module", Toast.LENGTH_SHORT).show();
                finish(); // Finish the activity
            }
        });

        // Find "No" button in custom layout
        Button btnCancel = dialogView.findViewById(R.id.cancel_exit_module);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle "No" button click
                Toast.makeText(c_Lesson_freeuse_7.this, "Cancel Exit", Toast.LENGTH_SHORT).show();
                dialog.dismiss(); // Dismiss the dialog
            }
        });

        // Create and show the dialog
        dialog = builder.create();
        dialog.show();
    }

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
