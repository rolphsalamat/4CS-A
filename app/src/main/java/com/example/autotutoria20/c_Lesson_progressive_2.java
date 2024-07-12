package com.example.autotutoria20;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class c_Lesson_progressive_2 extends AppCompatActivity {

    private AlertDialog dialog;
    private boolean[] cardCompletionStatus = {false, false, false}; // Track completion status of each card

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_lesson_progressive_2);

        FrameLayout card1 = findViewById(R.id.card1);
        FrameLayout card2 = findViewById(R.id.card2);
        FrameLayout card3 = findViewById(R.id.card3);

        // Assuming numberOfSteps is determined based on your logic
        int numberOfStepsForCard1 = 4; // Example value, replace with your logic
        int numberOfStepsForCard2 = 5; // Example value, replace with your logic
        int numberOfStepsForCard3 = 6; // Example value, replace with your logic

        setCardClickListener(card1, 1, numberOfStepsForCard1);
        setCardClickListener(card2, 2, numberOfStepsForCard2);
        setCardClickListener(card3, 3, numberOfStepsForCard3);

        // Retrieve user session data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);

        // Initialize progress based on saved data
        refreshProgress(sharedPreferences);

        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitConfirmationDialog();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Retrieve user session data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        refreshProgress(sharedPreferences);
    }

    private void refreshProgress(SharedPreferences sharedPreferences) {
        // Retrieve lesson data for "Progressive Mode" for Lesson 2 only
        HashMap<String, Map<String, Object>> progressiveModeData = getLessonDataForLesson(sharedPreferences, "Progressive Mode", "Lesson 2");

        // Log and process Progressive Mode data for Lesson 2 only
        if (progressiveModeData != null) {
            List<String> sortedLessonNames = new ArrayList<>(progressiveModeData.keySet());
            Collections.sort(sortedLessonNames); // Sort lesson names alphabetically

            for (String lessonName : sortedLessonNames) {
                Map<String, Object> lessonData = progressiveModeData.get(lessonName);

                // Reset iteration for each lessonName
                int iteration = 0;

                for (Map.Entry<String, Object> lessonEntry : lessonData.entrySet()) {
                    String moduleName = lessonEntry.getKey();
                    Object moduleValue = lessonEntry.getValue();

                    // Check if iteration exceeds moduleSteps array length
                    if (iteration < z_Lesson_steps.lesson_2_steps.length) {
                        int lessonStep = z_Lesson_steps.lesson_2_steps[iteration];

                        if ((int) moduleValue == lessonStep) {
//                            showToast((int) moduleValue + " == " + lessonStep + "!!! NEXT PLEASE!!");

                            // Mark the current card as completed
                            setCardCompletionStatus(iteration, true);

                            // Update the locked overlay visibility for the next card
                            updateLockedOverlayVisibility(iteration + 2); // +2 because cardIndex starts from 1 and next card index is iteration + 2
                        }

//                        showToast("Module Progress: " + moduleValue);

                        // Example: Updating text values for module progress
                        updateModuleProgressText("progressive_lesson_2_module_" + moduleName.charAt(1), moduleValue + "/" + lessonStep);

                        Log.d("LessonData", "Progressive Mode: " + lessonName + ", Field: " + moduleName + ", Value: " + moduleValue);
                        iteration++;
                    } else {
                        Log.e("LessonData", "Iteration exceeds moduleSteps array length.");
                    }
                }
            }
        } else {
            Toast.makeText(this, "No Progressive Mode data found for Lesson 2", Toast.LENGTH_SHORT).show();
            Log.d("No Progressive Mode", "No Progressive Mode data found for Lesson 2");
        }

        // Update locked overlay visibility for all cards based on their completion status
        for (int i = 0; i < cardCompletionStatus.length; i++) {
            updateLockedOverlayVisibility(i + 1);
        }
    }

    private void setCardCompletionStatus(int cardIndex, boolean isCompleted) {
        if (cardIndex >= 0 && cardIndex < cardCompletionStatus.length) {
            cardCompletionStatus[cardIndex] = isCompleted;
        }
    }

    private HashMap<String, Map<String, Object>> getLessonDataForLesson(SharedPreferences sharedPreferences, String mode, String lessonName) {
        HashMap<String, Map<String, Object>> lessonData = new HashMap<>();
        Map<String, ?> allEntries = sharedPreferences.getAll();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String key = entry.getKey();
            if (key.startsWith(mode + ": " + lessonName)) {
                String[] keyParts = key.split(", ");
                if (keyParts.length == 2) {
                    String fieldName = keyParts[1];
                    int value = (int) entry.getValue();

                    if (!lessonData.containsKey(lessonName)) {
                        lessonData.put(lessonName, new HashMap<String, Object>());
                    }
                    lessonData.get(lessonName).put(fieldName, value);
                }
            }
        }
        return lessonData;
    }


    private void navigateToModule(int cardNumber, int numberOfSteps) {
        if (isPreviousCardCompleted(cardNumber)) {
            navigateToModuleActivity(d_Lesson_container.class, numberOfSteps, cardNumber);
        } else {
            showCustomDialog();
        }
    }

    private void navigateToModuleActivity(Class<?> moduleActivityClass, int numberOfSteps, int cardNumber) {
        // Store user information in SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("ModulePreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("numberOfSteps", numberOfSteps);
        editor.putString("learningMode", "Progressive Mode");
        editor.putString("currentLesson", "Lesson 2");
        editor.putString("currentModule", "M" + cardNumber);
        editor.apply();

        Intent intent = new Intent(c_Lesson_progressive_2.this, moduleActivityClass);
        startActivity(intent);
    }

    private void updateModuleProgressText(String textViewId, String newText) {
        TextView textView = findViewById(getResources().getIdentifier(textViewId, "id", getPackageName()));
        if (textView != null) {
            textView.setText(newText);
        } else {
            Log.e("TextView Error", "TextView with id " + textViewId + " not found.");
        }
    }

    private void setCardClickListener(FrameLayout frame, final int cardNumber, final int numberOfSteps) {
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToModule(cardNumber, numberOfSteps);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showExitConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_exit_confirmation, null);
        builder.setView(dialogView);
        builder.setCancelable(true);

        Button btnCancel = dialogView.findViewById(R.id.cancel_exit_module);
        Button btnExit = dialogView.findViewById(R.id.exit_module);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dialog = builder.create();
        dialog.show();

        // Adjust dialog window size
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(layoutParams);
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_complete_previous_lesson, null);
        builder.setView(dialogView);

        builder.setCancelable(true);

        Button btnOk = dialogView.findViewById(R.id.okay_button);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog = builder.create();
        dialog.show();
    }

    private boolean isPreviousCardCompleted(int cardIndex) {
        if (cardIndex == 1) {
            return true; // First card is always available
        }

        if (cardIndex > 1 && cardIndex <= cardCompletionStatus.length) {
            return cardCompletionStatus[cardIndex - 2]; // Check the completion status of the previous card
        }

        return false;
    }

    private void updateLockedOverlayVisibility(int cardIndex) {

//        cardIndex -= 1;

        String overlayId = "card" + cardIndex + "_locked_overlay";
        View lockedOverlay = findViewById(getResources().getIdentifier(overlayId, "id", getPackageName()));

        showToast("Card #" + cardIndex);

        if (lockedOverlay != null) {
            if (cardIndex == 0 || isPreviousCardCompleted(cardIndex)) {
                lockedOverlay.setVisibility(View.GONE); // Hide locked overlay if previous card is completed
            } else {
                lockedOverlay.setVisibility(View.VISIBLE); // Show locked overlay if previous card is not completed
            }
        } else {
            Log.e("Overlay Error", "Locked overlay with id " + overlayId + " not found.");
        }
    }
}
