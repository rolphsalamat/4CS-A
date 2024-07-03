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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class c_Lesson_progressive_4 extends AppCompatActivity {

    private AlertDialog dialog;
    private boolean[] cardCompletionStatus = {false, false, false}; // Track completion status of each card

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_lesson_progressive_4);

        FrameLayout card1 = findViewById(R.id.card1);
        FrameLayout card2 = findViewById(R.id.card2);
        FrameLayout card3 = findViewById(R.id.card3);


        // Assuming numberOfSteps is determined based on your logic
        int numberOfStepsForCard1 = 6; // Example value, replace with your logic
        int numberOfStepsForCard2 = 7; // Example value, replace with your logic
        int numberOfStepsForCard3 = 8; // Example value, replace with your logic

        setCardClickListener(card1, 1, numberOfStepsForCard1);
        setCardClickListener(card2, 2, numberOfStepsForCard2);
        setCardClickListener(card3, 3, numberOfStepsForCard3);

        // Retrieve user session data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);

        // Retrieve lesson data for "Progressive Mode" for Lesson 4 only
        HashMap<String, Map<String, Object>> progressiveModeData = getLessonDataForLesson(sharedPreferences, "Progressive Mode", "Lesson 4");

        // Log and process Progressive Mode data for Lesson 4 only
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
                    if (iteration < z_Lesson_steps.lesson_4_steps.length) {
                        int lessonStep = z_Lesson_steps.lesson_4_steps[iteration];

                        if ((int) moduleValue == lessonStep) {
                            showToast((int) moduleValue + " == " + lessonStep + "!!! NEXT PLEASE!!");

                            // Mark the current card as completed
                            setCardCompletionStatus(iteration, true);

                            // Update the locked overlay visibility for the next card
                            updateLockedOverlayVisibility(iteration + 2); // +2 because cardIndex starts from 1 and next card index is iteration + 2
                        }

                        // Example: Updating text values for module progress
                        updateModuleProgressText("progressive_lesson_4_module_" + moduleName.charAt(1), moduleValue + "/" + lessonStep);

                        Log.d("LessonData", "Progressive Mode: " + lessonName + ", Field: " + moduleName + ", Value: " + moduleValue);
                        iteration++;
                    } else {
                        Log.e("LessonData", "Iteration exceeds moduleSteps array length.");
                    }
                }
            }
        } else {
            Toast.makeText(this, "No Progressive Mode data found for Lesson 4", Toast.LENGTH_SHORT).show();
            Log.d("No Progressive Mode", "No Progressive Mode data found for Lesson 4");
        }

        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitConfirmationDialog();
            }
        });
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
            switch (cardNumber) {
                case 1:
                    navigateToModuleActivity(d_Lesson_container.class, numberOfSteps);
                    break;
                case 2:
                    navigateToModuleActivity(d_Lesson_container.class, numberOfSteps);
                    break;
                case 3:
                    navigateToModuleActivity(d_Lesson_container.class, numberOfSteps);
                    break;
                default:
                    break;
            }
        } else {
            showCustomDialog();
        }
    }

    private void navigateToModuleActivity(Class<?> moduleActivityClass, int numberOfSteps) {
        // Store user information in SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("ModulePreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("numberOfSteps", numberOfSteps);
        editor.apply();

        Intent intent = new Intent(c_Lesson_progressive_4.this, moduleActivityClass);
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

    private void openModuleActivity() {
        Intent intent = new Intent(c_Lesson_progressive_4.this, d_Lesson_container.class);
        startActivity(intent);
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

        Button btnYes = dialogView.findViewById(R.id.exit_module);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c_Lesson_progressive_4.this, "Exiting module", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        Button btnCancel = dialogView.findViewById(R.id.cancel_exit_module_);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c_Lesson_progressive_4.this, "Cancel Exit", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog = builder.create();
        dialog.show();
    }

    private boolean isPreviousCardCompleted(int cardNumber) {
        if (cardNumber == 1) {
            return true;
        } else {
            int previousCardIndex = cardNumber - 2;
            if (previousCardIndex >= 0 && previousCardIndex < cardCompletionStatus.length) {
                return cardCompletionStatus[previousCardIndex];
            } else {
                return false;
            }
        }
    }

    // Method to show the custom dialog
    private void showCustomDialog() {
        // Create a dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Inflate the custom dialog layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_complete_previous_lesson, null);
        builder.setView(dialogView);

        // Create the AlertDialog
        AlertDialog dialog = builder.create();

        // Find the "Okay" button in    the custom layout
        Button exitButton = dialogView.findViewById(R.id.okay_button);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog when the button is clicked
                dialog.dismiss();
            }
        });

        // Set the dialog window attributes
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                // Convert dp to pixels
                int width = (int) (350 * getResources().getDisplayMetrics().density);
                int height = (int) (350 * getResources().getDisplayMetrics().density);

                // Set the fixed size for the dialog
                dialog.getWindow().setLayout(width, height);
            }
        });

        // Show the dialog
        dialog.show();
    }
    private HashMap<String, Map<String, Object>> getLessonDataFromPreferences(SharedPreferences sharedPreferences, String mode) {
        HashMap<String, Map<String, Object>> lessonData = new HashMap<>();
        Map<String, ?> allEntries = sharedPreferences.getAll();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String key = entry.getKey();
            if (key.startsWith(mode + ": ")) {
                String[] keyParts = key.split(", ");
                if (keyParts.length == 2) {
                    String lessonName = keyParts[0].substring((mode + ": ").length());
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

    private void updateLockedOverlayVisibility(int cardIndex) {
        String overlayId = "card" + cardIndex + "_locked_overlay";
        int resourceId = getResources().getIdentifier(overlayId, "id", getPackageName());

        Log.d("Overlay Visibility", "Overlay ID: " + overlayId + ", Resource ID: " + resourceId);

        if (resourceId != 0) {
            FrameLayout lockedOverlay = findViewById(resourceId);

            if (lockedOverlay == null) {
                Log.e("Overlay Visibility", "Locked overlay not found for resource ID: " + resourceId);
                showToast("Locked overlay not found for resource ID: " + resourceId);
                return;
            }

            // Proceed with setting visibility based on completion status
            if (cardIndex > 0 && cardIndex - 1 < cardCompletionStatus.length && !cardCompletionStatus[cardIndex - 1]) {
                lockedOverlay.setVisibility(View.VISIBLE);
                Log.d("Overlay Visibility", "Showing locked overlay for card " + cardIndex);
                showToast("Showing locked overlay for card " + cardIndex);
            } else {
                lockedOverlay.setVisibility(View.GONE);
                Log.d("Overlay Visibility", "Hiding locked overlay for card " + cardIndex);
                showToast("Hiding locked overlay for card " + cardIndex);
            }
        } else {
            Log.e("Overlay Visibility", "Resource ID not found for " + overlayId);
            showToast("Resource ID not found for " + overlayId);
        }

    }


}
