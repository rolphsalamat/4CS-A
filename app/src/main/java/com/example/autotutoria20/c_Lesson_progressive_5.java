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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class c_Lesson_progressive_5 extends AppCompatActivity {

    private AlertDialog dialog;
    private boolean[] cardCompletionStatus = {false, false, false}; // Track completion status of each card

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

//        // Retrieve user session data from SharedPreferences
//        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
//
//        // Initialize progress based on saved data
//        refreshProgress(sharedPreferences);

        fetchProgressData();

        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                showExitConfirmationDialog();
            }
        });
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
                        .collection("Progressive Mode")
                        .document("Lesson 5"); // pinalitan ko ng CAPITAL L

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
            }
        });
    }

    private void updateUI(int key, int progress) {

        //ETO NA I-A-UPDATE NAAAA!!!
        Log.d("updateUI()", "ETO NA MAG A-UPDATE NA AKOOOO LEZGOOO");

        // Update progress text views
        TextView module1ProgressText = findViewById(R.id.progressive_lesson_5_module_1);
        TextView module2ProgressText = findViewById(R.id.progressive_lesson_5_module_2);
        TextView module3ProgressText = findViewById(R.id.progressive_lesson_5_module_3);

        // Update locked overlays visibility
        FrameLayout card1LockedOverlay = findViewById(R.id.card1_locked_overlay);
        FrameLayout card2LockedOverlay = findViewById(R.id.card2_locked_overlay);
        FrameLayout card3LockedOverlay = findViewById(R.id.card3_locked_overlay);

        // Verify passed values...
        Log.d("updateUI()", "Module: " + key + " | Progress : " + progress);

        String newText;

        card1LockedOverlay.setVisibility(View.GONE);

        switch (key) {
            case 1:
                newText = progress + "/" + z_Lesson_steps.lesson_5_steps[0];
                module1ProgressText.setText(newText);

                if (progress >= z_Lesson_steps.lesson_5_steps[0]) {
                    card2LockedOverlay.setVisibility(View.GONE);
                    setCardCompletionStatus(key, true);
                }

                break;
            case 2:
                newText = progress + "/" + z_Lesson_steps.lesson_5_steps[1];
                module2ProgressText.setText(newText);

                if (progress >= z_Lesson_steps.lesson_5_steps[1]) {
                    card3LockedOverlay.setVisibility(View.GONE);
                    setCardCompletionStatus(key, true);
                }


                break;
            case 3:
                newText = progress + "/" + z_Lesson_steps.lesson_5_steps[2];
                module3ProgressText.setText(newText);

                if (progress >= z_Lesson_steps.lesson_5_steps[2]) {
                    setCardCompletionStatus(key, true);
//                    showToast("Lesson 4 Completed! :D");
                    Log.d("Completed Lesson!", "Lesson 5 Completed! :D");
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

            Log.d("setCardStatus", "Lemme set it");
            cardCompletionStatus[cardIndex] = isCompleted;
        }
    }

    private void navigateToModule(int cardNumber, int numberOfSteps) {

//        showToast("navigateToModule(), Card#" + cardNumber);


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
        // Store user information in SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("ModulePreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("numberOfSteps", numberOfSteps);
        editor.putString("learningMode", "Progressive Mode");
        editor.putString("currentLesson", "Lesson 5");
        editor.putString("currentModule", "M" + cardNumber);
        editor.apply();

//        showToast("Start Card " + cardNumber);

        Intent intent = new Intent(c_Lesson_progressive_5.this, moduleActivityClass);
        startActivity(intent);

        // bat naka-comment? ewan ko din
//        finish();
    }

    private void setCardClickListener(FrameLayout card, int cardNumber, int numberOfSteps) {
        card.setOnClickListener(v -> navigateToModule(cardNumber, numberOfSteps));
    }

    private void showExitConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_exit_confirmation, null);
        builder.setView(dialogView);

        // Find the buttons in the custom layout
        Button cancelButton = dialogView.findViewById(R.id.cancel_exit_module);
        Button exitButton = dialogView.findViewById(R.id.exit_module);

        // Create and show the dialog
        AlertDialog alert = builder.create();

        // Set up the button click listeners
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // or other logic to exit the module
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
                // Add any additional actions you want to perform when the button is clicked
            }
        });

        dialog = builder.create();
        dialog.show();
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
