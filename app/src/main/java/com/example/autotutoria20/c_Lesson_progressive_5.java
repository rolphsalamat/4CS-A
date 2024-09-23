package com.example.autotutoria20;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class c_Lesson_progressive_5 extends AppCompatActivity {

    private AlertDialog dialog;
    private boolean[] cardCompletionStatus = {false, false, false}; // Track completion status of each card
    private CustomLoadingDialog loadingDialog;
    private int[] moduleProgress;
    private c_Lesson_feedback feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_lesson_progressive_5);

        FrameLayout card1 = findViewById(R.id.card1);
        FrameLayout card2 = findViewById(R.id.card2);
        FrameLayout card3 = findViewById(R.id.card3);

        // Assuming numberOfSteps is determined based on your logic
        int numberOfStepsForCard1 = z_Lesson_steps.lesson_5_steps[0];
        int numberOfStepsForCard2 = z_Lesson_steps.lesson_5_steps[1];
        int numberOfStepsForCard3 = z_Lesson_steps.lesson_5_steps[2];

        setCardClickListener(card1, 1, numberOfStepsForCard1);
        setCardClickListener(card2, 2, numberOfStepsForCard2);
        setCardClickListener(card3, 3, numberOfStepsForCard3);

        fetchProgressData();

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
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference progressRef = db.collection("users")
                .document(userId)
                .collection("Progressive Mode")
                .document("Lesson 5");

        // Retrieve "Progress" from each map
        progressRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String TAG = "fetchProgressData()";
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> progressData = document.getData();
                        if (progressData != null) {
                            // Initialize the array with the length of the lesson steps
                            moduleProgress = new int[z_Lesson_steps.lesson_1_steps.length];

                            String TEST = "HERE!";
                            Log.e(TEST, "Entering for loop");

                            for (Map.Entry<String, Object> entry : progressData.entrySet()) {
                                String key = entry.getKey(); // e.g., M1, M2, etc.
                                Log.e(TEST, "Key: " + key);
                                Object value = entry.getValue(); // The value associated with the key
                                Log.e(TEST, "Value: " + value);

                                // Check if the value is a Map and contains the "Progress" key
                                if (value instanceof Map) {
                                    Map<String, Object> moduleData = (Map<String, Object>) value;

                                    // Check for a Progress key in the module data
                                    if (moduleData.containsKey("Progress")) {
                                        Object progressValue = moduleData.get("Progress");
                                        Log.e(TEST, "Value for Progress: " + progressValue);

                                        // Ensure that the value is of type Long
                                        if (progressValue instanceof Long) {
                                            int progress = ((Long) progressValue).intValue();
                                            Log.e(TEST, "Progress: " + progress);
                                            int moduleNumber = Character.getNumericValue(key.charAt(1)); // Extract number from key
                                            Log.e(TEST, "Module Number: " + moduleNumber);

                                            // Store progress in the array if within bounds
                                            if (moduleNumber >= 1 && moduleNumber <= moduleProgress.length) {
                                                moduleProgress[moduleNumber - 1] = progress;
                                            }

                                            // Log the module number and progress
                                            Log.d(TAG, "Module: " + moduleNumber + " | Progress: " + progress);

                                            updateUI(moduleNumber, progress); // Update UI with new progress
                                        } else {
                                            Log.e(TEST, "Progress value is not a Long for key: " + key);
                                        }
                                    } else {
                                        Log.e(TEST, "No Progress key found for module: " + key);
                                    }
                                } else {
                                    Log.e(TEST, "Value is not a Map for key: " + key);
                                }
                            }

                            // Call method to check progress after retrieving all data
                            checkProgress();
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }

                // Hide the loading dialog after data is fetched and processed
                hideLoadingDialog();
            }
        });
    }

    private void checkProgress() {
        for (int i = 0; i < moduleProgress.length; i++) {
            int progress = moduleProgress[i];
            int maxSteps = z_Lesson_steps.lesson_5_steps[i];
            if (progress < maxSteps) {
                Log.d("checkProgress", "Module " + (i + 1) + " is not completed. Progress: " + progress + "/" + maxSteps);
            } else {
                Log.d("checkProgress", "Module " + (i + 1) + " is completed. Progress: " + progress + "/" + maxSteps);
                setCardCompletionStatus(i + 1, true);
            }
        }
    }

    private void updateUI(int key, int progress) {
        Log.d("updateUI()", "ETO NA MAG A-UPDATE NA AKOOOO LEZGOOO");

        // Update progress text views
        TextView module1ProgressText = findViewById(R.id.progressive_lesson_5_module_1);
        TextView module2ProgressText = findViewById(R.id.progressive_lesson_5_module_2);
        TextView module3ProgressText = findViewById(R.id.progressive_lesson_5_module_3);

        // Update locked overlays visibility
        FrameLayout card1LockedOverlay = findViewById(R.id.card1_locked_overlay);
        FrameLayout card2LockedOverlay = findViewById(R.id.card2_locked_overlay);
        FrameLayout card3LockedOverlay = findViewById(R.id.card3_locked_overlay);

        String newText;

        card1LockedOverlay.setVisibility(View.GONE);

        switch (key) {
            case 1:
                newText = progress + "/" + L_lesson_sequence.getNumberOfSteps("M1_Lesson 5");
                module1ProgressText.setText(newText);
                if (progress >= L_lesson_sequence.getNumberOfSteps("M1_Lesson 5")) {
                    card2LockedOverlay.setVisibility(View.GONE);
                    setCardCompletionStatus(key, true);
                }
                break;
            case 2:
                newText = progress + "/" + L_lesson_sequence.getNumberOfSteps("M2_Lesson 5");
                module2ProgressText.setText(newText);
                if (progress >= L_lesson_sequence.getNumberOfSteps("M2_Lesson 5")) {
                    card3LockedOverlay.setVisibility(View.GONE);
                    setCardCompletionStatus(key, true);
                }
                break;
            case 3:
                newText = progress + "/" + L_lesson_sequence.getNumberOfSteps("M3_Lesson 5");
                module3ProgressText.setText(newText);
                if (progress >= L_lesson_sequence.getNumberOfSteps("M3_Lesson 5")) {
                    setCardCompletionStatus(key, true);
                    Log.d("Completed Lesson!", "Lesson 5 Completed! :D");

                    Log.e("Comleted Lesson!", "Calling Feedback Class");
                    feedback = new c_Lesson_feedback(this); // Initialize feedback object
                    feedback.retrieveBKTScore("Progressive Mode", "Lesson 5");

                }
                break;
            default:
                Log.d("updateUI", "Invalid module number: " + key);
                break;
        }
    }

    private void setCardCompletionStatus(int cardIndex, boolean isCompleted) {
        cardIndex -= 1; // Because Card starts at 0 :>
        Log.d("setCardStatus", "Card " + cardIndex + " Completed!");
        if (cardIndex >= 0 && cardIndex < cardCompletionStatus.length) {
            cardCompletionStatus[cardIndex] = isCompleted;
        }
    }

    private void navigateToModule(int cardNumber, int numberOfSteps) {
        switch (cardNumber) {
            case 1:
                navigateToModuleActivity(d_Lesson_container.class, numberOfSteps, cardNumber);
                break;
            case 2:
                if (cardCompletionStatus[0])
                    navigateToModuleActivity(d_Lesson_container.class, numberOfSteps, cardNumber);
                else
                    showCustomDialog();
                break;
            case 3:
                if (cardCompletionStatus[1])
                    navigateToModuleActivity(d_Lesson_container.class, numberOfSteps, cardNumber);
                else
                    showCustomDialog();
                break;
            default:
                Log.e("navigateToModule()", "Invalid Card: Card " + cardNumber);
                break;
        }
    }

    private void navigateToModuleActivity(Class<?> moduleActivityClass, int numberOfSteps, int cardNumber) {
        SharedPreferences sharedPreferences = getSharedPreferences("ModulePreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("numberOfSteps", numberOfSteps);
        editor.putString("learningMode", "Progressive Mode");
        editor.putString("currentLesson", "Lesson 5");
        editor.putString("currentModule", "M" + cardNumber);
        editor.putBoolean("isCompleted", cardCompletionStatus[cardNumber - 1]);
        editor.apply();

        Intent intent = new Intent(c_Lesson_progressive_5.this, moduleActivityClass);
        intent.putExtra("currentProgress", moduleProgress[cardNumber - 1]);
        startActivity(intent);
    }

    private void setCardClickListener(FrameLayout card, int cardNumber, int numberOfSteps) {
        if (n_Network.isNetworkAvailable(getBaseContext())) {
            card.setOnClickListener(v -> navigateToModule(cardNumber, numberOfSteps));
        }
    }

    private void showExitConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_exit_confirmation, null);
        builder.setView(dialogView);

        Button cancelButton = dialogView.findViewById(R.id.cancel_exit_module);
        Button exitButton = dialogView.findViewById(R.id.exit_module);

        AlertDialog alert = builder.create();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        alert.show();
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(c_Lesson_progressive_5.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_complete_previous_lesson, null);
        builder.setView(dialogView);

        Button okayButton = dialogView.findViewById(R.id.okay_button);
        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog = builder.create();
        dialog.show();
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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
