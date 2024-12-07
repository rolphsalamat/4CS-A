package com.example.autotutoria20;

import android.content.DialogInterface;
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

public class c_Lesson_progressive_7 extends AppCompatActivity {

    private AlertDialog dialog;
    public static  boolean[] cardCompletionStatus = {false}; // Track completion status of each card
    private CustomLoadingDialog loadingDialog;
    private int[] moduleProgress;
    private double passingGrade;
    private c_Lesson_feedback feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_lesson_progressive_7);

        FrameLayout card1 = findViewById(R.id.card1);

        // Assuming numberOfSteps is determined based on your logic
        int numberOfStepsForCard1 = z_Lesson_steps.lesson_7_steps[0];

        setCardClickListener(card1, 1, numberOfStepsForCard1);

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

        c_Lesson_a_retrieveScore.fetchModuleProgress(
                "Progressive Mode", "Lesson 7");

        passingGrade = b_main_0_menu_categorize_user.passingGrade;

        // Fetch the latest progress data
        fetchProgressData();

    }

    private void fetchProgressData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference progressRef = db.collection("users")
                .document(userId)
                .collection("Progressive Mode")
                .document("Lesson 7");

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
                            moduleProgress = new int[z_Lesson_steps.lesson_7_steps.length];

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
                                        // Handle missing Progress key
                                        Log.e(TEST, "No Progress key found for module: " + key);

                                        int progress = 0; // Default progress value
                                        int moduleNumber = Character.getNumericValue(key.charAt(1)); // Extract number from key
                                        Log.e(TEST, "Module Number: " + moduleNumber);

                                        // Store default progress in the array if within bounds
                                        if (moduleNumber >= 1 && moduleNumber <= moduleProgress.length) {
                                            moduleProgress[moduleNumber - 1] = progress;
                                        }

                                        // Log the module number and default progress
                                        Log.d(TAG, "Module: " + moduleNumber + " | Progress: " + progress);

                                        updateUI(moduleNumber, progress); // Update UI with default progress
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

        int progress = moduleProgress[0];
        int maxSteps = z_Lesson_steps.lesson_7_steps[0];
        if (progress < maxSteps) {
            Log.d("checkProgress", "Module 1 is not completed. Progress: " + progress + "/" + maxSteps);
        } else {
            Log.d("checkProgress", "Module 1 is completed. Progress: " + progress + "/" + maxSteps);
        }

    }

    private void updateUI(int key, int progress) {
        Log.d("updateUI()", "ETO NA MAG A-UPDATE NA AKOOOO LEZGOOO");

        // Update progress text views
        TextView module1ProgressText = findViewById(R.id.progressive_lesson_7_module_1);

        // Update locked overlays visibility
        FrameLayout card1LockedOverlay = findViewById(R.id.card1_locked_overlay);

        String newText;

        card1LockedOverlay.setVisibility(View.GONE);

        String TAG = "BKT Score";

        switch (key) {
            case 1:
                newText = progress + "/" + t_LessonSequenceFromDatabase.getNumberOfSteps("M1_Lesson 7");
                module1ProgressText.setText(newText);

                double M1_Score = c_Lesson_a_retrieveScore.bktScores.get(0); // Accessing first module's score

                // Check if the lesson is finished
                if (progress >= t_LessonSequenceFromDatabase.getNumberOfSteps("M1_Lesson 7")) {
                    if (M1_Score < passingGrade && M1_Score != 0) {
                        c_Lesson_feedback.showDialog(this, M1_Score, passingGrade, "Lesson 1");
                    } else {
//                    card4LockedOverlay.setVisibility(View.GONE);
                        setCardCompletionStatus(key, true);

                        Log.e("Completed Lesson!", "Calling Feedback Class");
                        feedback = new c_Lesson_feedback(this); // Initialize feedback object
                        feedback.retrieveBKTScore("Progressive Mode", "Lesson 7");
                    }
                }

                break;
            default:
                Log.d("updateUI", "Invalid module number: " + key);
                break;
        }
    }

    private void showDialog(String title, String message) {

//      Create a dialog to show the user's score
        AlertDialog.Builder builder = new AlertDialog.Builder(c_Lesson_progressive_7.this);
        builder.setTitle(title);

        builder.setMessage(message);

        // Add a button to dismiss the dialog
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Close the dialog
            }
        });

        builder.setCancelable(false);

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
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
        editor.putString("currentLesson", "Lesson 7");
        editor.putString("currentModule", "M" + cardNumber);
        editor.putBoolean("isCompleted", cardCompletionStatus[cardNumber - 1]);
        editor.apply();

        Intent intent = new Intent(c_Lesson_progressive_7.this, moduleActivityClass);
        intent.putExtra("currentProgress", moduleProgress[cardNumber - 1]);
        startActivity(intent);
    }

    private void setCardClickListener(FrameLayout card, int cardNumber, int numberOfSteps) {
        if (n_Network.isNetworkAvailable(getBaseContext())) {
            card.setOnClickListener(v -> navigateToModule(cardNumber, numberOfSteps));
        }
    }

//    private void showExitConfirmationDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.dialog_logout, null);
//        builder.setView(dialogView);
//
//        Button cancelButton = dialogView.findViewById(R.id.cancel_exit_module);
//        Button exitButton = dialogView.findViewById(R.id.exit_module);
//
//        AlertDialog alert = builder.create();
//
//        cancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alert.dismiss();
//            }
//        });
//
//        exitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//        alert.show();
//    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(c_Lesson_progressive_7.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_complete_previous_module, null);
        builder.setView(dialogView);

        Button exitButton = dialogView.findViewById(R.id.exit_button);
        Button okayButton = dialogView.findViewById(R.id.okay_button);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

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
