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

public class c_Lesson_progressive_7 extends AppCompatActivity {

    private AlertDialog dialog;
    private boolean[] cardCompletionStatus = {false}; // Track completion status of each card
    private CustomLoadingDialog loadingDialog;
    private int[] moduleProgress;
    private c_Lesson_feedback feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_lesson_progressive_7);

        feedback = new c_Lesson_feedback(); // Initialize feedback object


        FrameLayout card1 = findViewById(R.id.card1);

        // Assuming numberOfSteps is determined based on your logic
        int numberOfStepsForCard1 = z_Lesson_steps.lesson_7_steps[0];

        setCardClickListener(card1, 1, numberOfStepsForCard1);

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

        DocumentReference progressRef =
                db.collection("users")
                        .document(userId)
                        .collection("Progressive Mode")
                        .document("Lesson 7");

        progressRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String TAG = "fetchProgressData()";
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> progressData = document.getData();
                        if (progressData != null) {
                            moduleProgress = new int[z_Lesson_steps.lesson_7_steps.length];
                            for (Map.Entry<String, Object> entry : progressData.entrySet()) {
                                String key = entry.getKey();
                                Object value = entry.getValue();
                                if (value instanceof Long) {
                                    int progress = ((Long) value).intValue();
                                    int moduleNumber = Character.getNumericValue(key.charAt(1));

                                    if (moduleNumber >= 1 && moduleNumber <= moduleProgress.length) {
                                        moduleProgress[moduleNumber - 1] = progress;
                                    }

                                    updateUI(moduleNumber, progress);
                                }
                            }

                            checkProgress();
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

    private void checkProgress() {
        for (int i = 0; i < moduleProgress.length; i++) {
            int progress = moduleProgress[i];
            int maxSteps = z_Lesson_steps.lesson_7_steps[i];
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
        TextView module1ProgressText = findViewById(R.id.progressive_lesson_7_module_1);

        // Update locked overlays visibility
        FrameLayout card1LockedOverlay = findViewById(R.id.card1_locked_overlay);

        String newText;

        card1LockedOverlay.setVisibility(View.GONE);

        switch (key) {
            case 1:
                newText = progress + "/" + L_lesson_sequence.getNumberOfSteps("M1_Lesson 7");
                module1ProgressText.setText(newText);
                if (progress >= L_lesson_sequence.getNumberOfSteps("M1_Lesson 7")) {
                    setCardCompletionStatus(key, true);
                    Log.d("Completed Lesson!", "Lesson 7 Completed! :D");

                    Log.e("Comleted Lesson!", "Calling Feedback Class");
                    feedback.retrieveBKTScore("Progressive Mode", "Lesson 7");

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
        AlertDialog.Builder builder = new AlertDialog.Builder(c_Lesson_progressive_7.this);
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
