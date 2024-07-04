package com.example.autotutoria20;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class b_main_1_lesson_progressive extends Fragment {
    private static TextView learningModeText;
    private FrameLayout lockedOverlayCard1, lockedOverlayCard2, lockedOverlayCard3, lockedOverlayCard4;
    private ProgressBar progressBarCard1, progressBarCard2, progressBarCard3, progressBarCard4;
    private TextView progressTextCard1, progressTextCard2, progressTextCard3, progressTextCard4;
    private Button incrementCard1Button;
    private boolean isProgressiveMode = true; // Default mode is progressive mode
    private static View view;
    private Dialog dialog;

    // Define card progress array
    private int[] cardProgress = new int[4]; // Assuming there are 4 cards

    // Track completion status of each card
    private boolean[] cardCompletionStatus = {false, false, false, false}; // Default is false for all cards

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.b_main_3_lesson_progressive, container, false);

        // Initialize views
        initializeViews();

        // Retrieve user session data from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserSession", MODE_PRIVATE);

        // Initialize array to store cumulative module values for each lesson
        int[] cumulativeModuleValues = new int[4]; // Assuming 4 lessons

        // Iterate through lesson numbers 1, 2, 3, and 4
        for (int lessonNumber = 1; lessonNumber <= 4; lessonNumber++) {
            String lessonKey = "Lesson " + lessonNumber;

            // Retrieve lesson data for "Progressive Mode" for the current lesson
            HashMap<String, Map<String, Object>> progressiveModeData = getLessonDataForLesson(sharedPreferences, "Progressive Mode", lessonKey);

            // Log and process Progressive Mode data for the current lesson
            if (progressiveModeData != null) {
                List<String> sortedLessonNames = new ArrayList<>(progressiveModeData.keySet());
                Collections.sort(sortedLessonNames); // Sort lesson names alphabetically

                for (String lessonName : sortedLessonNames) {
                    Map<String, Object> lessonData = progressiveModeData.get(lessonName);

                    // Reset iteration for each lessonName
                    int iteration = 0;

                    for (Map.Entry<String, Object> lessonEntry : lessonData.entrySet()) {
                        String moduleName = lessonEntry.getKey();
                        int moduleValue = (int) lessonEntry.getValue();

                        // Log current module being checked
                        Log.d("ModuleData", "Lesson " + lessonNumber + ", Module: " + moduleName + ", Value: " + moduleValue);

                        // Check if iteration exceeds moduleSteps array length
                        int[] lessonSteps = z_Lesson_steps.getLessonSteps(lessonNumber);
                        if (iteration < lessonSteps.length) {
                            // Increment the array value with the module value
                            cumulativeModuleValues[lessonNumber - 1] += moduleValue;

                            iteration++;
                        } else {
                            Log.e("LessonData", "Iteration exceeds moduleSteps array length.");
                        }
                    }
                }
            } else {
//                Toast.makeText(getActivity(), "No Progressive Mode data found for " + lessonKey, Toast.LENGTH_SHORT).show();
                Log.d("No Progressive Mode", "No Progressive Mode data found for " + lessonKey);
            }
        }

        // Calculate total steps for all lessons
        int totalSteps = calculateTotalSteps();

        // Calculate and log the contribution percentages of each lesson
        for (int i = 0; i < cumulativeModuleValues.length; i++) {
            int moduleSteps = cumulativeModuleValues[i];

            // Retrieve complete steps from z_Lesson_steps
            int lessonCompleteSteps = getLessonCompleteSteps(i + 1);

            // Calculate contribution percentage
            double contributionPercentage = (moduleSteps / (double) lessonCompleteSteps) * 100;
            Log.d("LessonContribution", "Lesson " + (i + 1) + " Contribution: " + contributionPercentage + "%");

            // Update UI or perform further actions with contributionPercentage
            incrementCard(i, (int) contributionPercentage);
        }

        Button increment = view.findViewById(R.id.increment_progress);
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 4; i++) {
                    if (cardProgress[i] < 100) {
                        incrementCard(i, 25);
                        break;
                    }
                    if (i == 3 && cardProgress[i] >= 100) {
                        showToast("Progressive Mode is Complete.");
                    }
                }
            }
        });


        return view;
    }

    private void initializeViews() {
        progressBarCard1 = view.findViewById(R.id.progressBar_card_1);
        progressBarCard2 = view.findViewById(R.id.progressBar_card_2);
        progressBarCard3 = view.findViewById(R.id.progressBar_card_3);
        progressBarCard4 = view.findViewById(R.id.progressBar_card_4);

        progressTextCard1 = view.findViewById(R.id.progressText_card_1);
        progressTextCard2 = view.findViewById(R.id.progressText_card_2);
        progressTextCard3 = view.findViewById(R.id.progressText_card_3);
        progressTextCard4 = view.findViewById(R.id.progressText_card_4);

        learningModeText = view.findViewById(R.id.learning_mode_text);

        lockedOverlayCard1 = view.findViewById(R.id.card1_locked_overlay);
        lockedOverlayCard2 = view.findViewById(R.id.card2_locked_overlay);
        lockedOverlayCard3 = view.findViewById(R.id.card3_locked_overlay);
        lockedOverlayCard4 = view.findViewById(R.id.card4_locked_overlay);

        setCardClickListeners();


    }


    // Method to calculate total steps for all lessons
    private int calculateTotalSteps() {
        int totalSteps = 0;
        for (int lessonNumber = 1; lessonNumber <= 4; lessonNumber++) {
            int[] lessonSteps = z_Lesson_steps.getLessonSteps(lessonNumber);
            for (int steps : lessonSteps) {
                totalSteps += steps;
            }
        }
        return totalSteps;
    }

    // Method to get lesson complete steps
    private int getLessonCompleteSteps(int lessonNumber) {
        switch (lessonNumber) {
            case 1:
                return z_Lesson_steps.lesson_1_complete;
            case 2:
                return z_Lesson_steps.lesson_2_complete;
            case 3:
                return z_Lesson_steps.lesson_3_complete;
            case 4:
                return z_Lesson_steps.lesson_4_complete;
            default:
                return 0;
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

    // Method to set click listeners for the cards
    private void setCardClickListeners() {
        // Card 1 click listener
        view.findViewById(R.id.card1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCardClick(1);
            }
        });

        // Card 2 click listener
        view.findViewById(R.id.card2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCardClick(2);
            }
        });

        // Card 3 click listener
        view.findViewById(R.id.card3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCardClick(3);
            }
        });

        // Card 4 click listener
        view.findViewById(R.id.card4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCardClick(4);
            }
        });
    }

    // Method to handle click on a card
    private void handleCardClick(int cardId) {
        Log.d("CardClick", "Card " + cardId + " clicked.");
        if (isPreviousCardCompleted(cardId)) {
//            Intent intent = new Intent(getActivity(), progressive_lessons.class);
//            intent.putExtra("lesson_id", cardId + 1);
//            startActivity(intent);
            launchLessonActivity(cardId);
        } else {
            // Create a dialog builder
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            // Inflate the custom dialog layout
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_complete_previous_lesson, null);
            builder.setView(dialogView);

            // Create the AlertDialog
            AlertDialog dialog = builder.create();

            // Find the "Okay" button in the custom layout
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
    }



    // Method to show the custom dialog
    private void showCustomDialog() {
        // Create a dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Inflate the custom dialog layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_complete_previous_lesson, null);
        builder.setView(dialogView);

        // Create the AlertDialog
        AlertDialog dialog = builder.create();

        // Find the "Okay" button in the custom layout
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

    // Method to get the lesson description of the previous card
    private String getPreviousLessonDescription(int cardId) {
        if (cardId > 1) {
            int previousCardId = cardId - 1;
            TextView previousLessonDescription = null;
            switch (previousCardId) {
                case 1:
                    previousLessonDescription = view.findViewById(R.id.lesson_description_1);
                    break;
                case 2:
                    previousLessonDescription = view.findViewById(R.id.lesson_description_2);
                    break;
                case 3:
                    previousLessonDescription = view.findViewById(R.id.lesson_description_3);
                    break;
                case 4:
                    previousLessonDescription = view.findViewById(R.id.lesson_description_4);
                    break;
                default:
                    break;
            }
            if (previousLessonDescription != null) {
                return previousLessonDescription.getText().toString();
            }
        }
        return "";
    }

    // Method to update the completion status of the previous card
    private void updateCompletionStatus(int cardId) {
        if (cardId > 1) {
            cardCompletionStatus[cardId - 2] = true; // Adjust index since arrays are zero-based
        }
    }

    // Method to launch activity related to the clicked card
    private void launchLessonActivity(int cardId) {
        Intent intent;
//        showToast("launchLessonActivity(" + cardId + ")");
        switch (cardId) {
            case 1:
                intent = new Intent(getActivity(), c_Lesson_progressive_1.class);
                break;
            case 2:
                intent = new Intent(getActivity(), c_Lesson_progressive_2.class);
                break;
            case 3:
                intent = new Intent(getActivity(), c_Lesson_progressive_3.class);
                break;
            case 4:
                intent = new Intent(getActivity(), c_Lesson_progressive_4.class);
                break;
            default:
                return;
        }
        startActivity(intent);
    }

    // Method to display a toast message
    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    // Method to check if the previous card is completed to unlock the current card
    private boolean isPreviousCardCompleted(int cardId) {
        if (cardId == 0) {
            // If it's the first card, there's no previous card, so it's considered completed
            return true;
        }
        boolean isCompleted = cardCompletionStatus[cardId - 1];
        Log.d("CardCheck", "Card " + cardId + " previous card completion status: " + isCompleted);
        return isCompleted;
    }


//    private void incrementCardProgress() {
//        for (int i = 0; i < cardCompletionStatus.length; i++) {
//            // If the current card is not complete, increment its progress
//            if (!cardCompletionStatus[i]) {
//                int currentProgress = cardProgress[i];
//                Log.d("incrementCardProgress()", "current progress: " + currentProgress);
//                int incrementValue = 25; // Increment progress by 25%
//                Log.d("incrementCardProgress()", "increment value: " + incrementValue);
//                int newProgress = Math.min(currentProgress + incrementValue, 100); // Ensure it does not exceed 100%
//                Log.d("incrementCardProgress()", "new progress: " + newProgress);
//
//
//                incrementCard(i, incrementValue); // Pass the difference as the increment value
//                showToast("Increment Button [" + i + "] : " + newProgress + "%");
//
////                incrementCard(i, newProgress - currentProgress); // Pass the difference as the increment value
////                showToast("Increment Button [" + (i) + "] : " + (newProgress - currentProgress) + "%");
//                return; // Exit the loop and method after incrementing the first incomplete card
//            }
//        }
//
//        Toast.makeText(getActivity(), "All cards are already completed", Toast.LENGTH_SHORT).show();
//    }

    private void incrementCard(int cardId, int incrementValue) {
        if (cardId >= 0 && cardId < cardProgress.length) {
            // Increment progress by the specified value
            int newProgress = cardProgress[cardId] + incrementValue;

            // Ensure progress does not exceed 100%
            cardProgress[cardId] = Math.min(newProgress, 100);

            // Check if the card is completed
            if (cardProgress[cardId] >= 100) {
                cardCompletionStatus[cardId] = true;
                Log.d("incrementCard()", "Card " + (cardId + 1) + " completed.");

                // Hide locked overlay for this card
                hideLockedOverlay(cardId + 1);

                // Unlock the next card if there is one and all previous cards are completed
                if (cardId < cardCompletionStatus.length - 1 && areAllPreviousCardsCompleted(cardId + 1)) {
                    cardCompletionStatus[cardId + 1] = true;
                    Log.d("incrementCard()", "Card " + (cardId + 2) + " unlocked.");
                }
            }

            // Update UI for the current card
            ProgressBar progressBar = view.findViewById(getProgressBarId(cardId));
            TextView progressText = view.findViewById(getProgressTextId(cardId));

            progressBar.setProgress(cardProgress[cardId]);
            progressText.setText(cardProgress[cardId] + "% Completed");
        }
    }

    private boolean areAllPreviousCardsCompleted(int cardId) {
        for (int i = 0; i < cardId - 1; i++) {
            if (!cardCompletionStatus[i]) {
                return false;
            }
        }
        return true;
    }


    // Helper methods to get the ProgressBar and TextView IDs
    private int getProgressBarId(int index) {
        switch (index) {
            case 0:
                return R.id.progressive_progressbar_lesson_1;
            case 1:
                return R.id.progressive_progressbar_lesson_2;
            case 2:
                return R.id.progressive_progressbar_lesson_3;
            case 3:
                return R.id.progressive_progressbar_lesson_4;
            default:
                return 0;
        }
    }

    private int getProgressTextId(int index) {
        switch (index) {
            case 0:
                return R.id.progressive_progresstext_lesson_1;
            case 1:
                return R.id.progressive_progresstext_lesson_2;
            case 2:
                return R.id.progressive_progresstext_lesson_3;
            case 3:
                return R.id.progressive_progresstext_lesson_4;
            default:
                return 0;
        }
    }

    private void hideLockedOverlay(int cardId) {
        // Ensure view is not null and get the root view
        if (view != null) {
            // Determine the correct locked overlay view ID based on the card ID
            int lockedOverlayId = getLockedOverlayId(cardId);

            View lockedOverlay = view.findViewById(lockedOverlayId);
            if (lockedOverlay != null) {
                lockedOverlay.setVisibility(View.GONE);
                Log.d("hideLockedOverlay", "Overlay for card " + (cardId + 1) + " set to GONE");
            } else {
                Log.e("hideLockedOverlay", "Locked overlay view is null for card " + (cardId + 1));
            }
        } else {
            Log.e("hideLockedOverlay", "Root view is null");
        }
    }

    // Method to get the correct locked overlay view ID based on the card ID
    private int getLockedOverlayId(int cardId) {
        switch (cardId) {
            case 0:
                return R.id.card1_locked_overlay;
            case 1:
                return R.id.card2_locked_overlay;
            case 2:
                return R.id.card3_locked_overlay;
            case 3:
                return R.id.card4_locked_overlay;
            default:
                return -1; // Invalid card ID
        }
    }

}
