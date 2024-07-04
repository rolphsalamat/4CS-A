package com.example.autotutoria20;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

public class b_main_2_lesson_freeuse extends Fragment {

    private static View view;
    private ProgressBar progressBarCard1, progressBarCard2, progressBarCard3, progressBarCard4;
    private TextView progressTextCard1, progressTextCard2, progressTextCard3, progressTextCard4;
    private FrameLayout lockedOverlayCard1, lockedOverlayCard2, lockedOverlayCard3, lockedOverlayCard4;

    private int[] cardProgress = new int[4]; // Assuming there are 4 cards
    private boolean[] cardCompletionStatus = {false, false, false, false}; // Default is false for all cards

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.b_main_2_lesson_freeuse, container, false);

        // Initialize views
        initializeViews();

        // Retrieve user session data from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserSession", MODE_PRIVATE);

        // Initialize array to store cumulative module values for each lesson
        int[] cumulativeModuleValues = new int[4]; // Assuming 4 lessons

        // Iterate through lesson numbers 1, 2, 3, and 4
        for (int lessonNumber = 1; lessonNumber <= 4; lessonNumber++) {
            String lessonKey = "Lesson " + lessonNumber;

            // Retrieve lesson data for "Free Use Mode" for the current lesson
            HashMap<String, Map<String, Object>> freeuseModeData = getLessonDataForLesson(sharedPreferences, "Free Use Mode", lessonKey);

            // Log and process Free Use Mode data for the current lesson
            if (freeuseModeData != null) {
                for (Map<String, Object> lessonData : freeuseModeData.values()) {
                    int moduleValue = 0;
                    for (Object value : lessonData.values()) {
                        moduleValue += (int) value;
                    }
                    // Update cumulative module values
                    cumulativeModuleValues[lessonNumber - 1] += moduleValue;
                }
            } else {
                Log.d("No Free Use Mode", "No Free Use Mode data found for " + lessonKey);
            }
        }

        // Calculate and log the contribution percentages of each lesson
        for (int i = 0; i < cumulativeModuleValues.length; i++) {
            int moduleSteps = cumulativeModuleValues[i];

            // Retrieve complete steps from z_Lesson_steps
            int lessonCompleteSteps = getLessonCompleteSteps(i + 1);

            // Calculate contribution percentage
            double contributionPercentage = (moduleSteps / (double) lessonCompleteSteps) * 100;
            Log.d("LessonContribution FREEUSE", "Lesson " + (i + 1) + " Contribution: " + contributionPercentage + "%");

            // Update UI or perform further actions with contributionPercentage
            cardProgress[i] = (int) contributionPercentage;
            if (cardProgress[i] >= 100) {
                cardCompletionStatus[i] = true;
                hideLockedOverlay(i + 1);
            }
        }

        // Update the progress bars and texts
        updateProgressUI();

        // Set click listeners for the cards
        setCardClickListeners();

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

        lockedOverlayCard1 = view.findViewById(R.id.card1_locked_overlay);
        lockedOverlayCard2 = view.findViewById(R.id.card2_locked_overlay);
        lockedOverlayCard3 = view.findViewById(R.id.card3_locked_overlay);
        lockedOverlayCard4 = view.findViewById(R.id.card4_locked_overlay);
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
        // Log the retrieved data for verification
        Log.d("LessonData", "Retrieved data for " + lessonName + ": " + lessonData);
        return lessonData;
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

    private void updateProgressUI() {
        ProgressBar[] progressBars = {progressBarCard1, progressBarCard2, progressBarCard3, progressBarCard4};
        TextView[] progressTexts = {progressTextCard1, progressTextCard2, progressTextCard3, progressTextCard4};

        for (int i = 0; i < cardProgress.length; i++) {
            progressBars[i].setProgress(cardProgress[i]);
            progressTexts[i].setText(cardProgress[i] + "% Completed");
        }
    }

    // Method to set click listeners for the cards
    private void setCardClickListeners() {
        // Card 1 click listener
        view.findViewById(R.id.card1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLessonActivity(1);
            }
        });

        // Card 2 click listener
        view.findViewById(R.id.card2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLessonActivity(2);
            }
        });

        // Card 3 click listener
        view.findViewById(R.id.card3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLessonActivity(3);
            }
        });

        // Card 4 click listener
        view.findViewById(R.id.card4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLessonActivity(4);
            }
        });
    }

    // Method to launch activity related to the clicked card
    private void launchLessonActivity(int cardId) {
        Intent intent;
        switch (cardId) {
            case 1:
                intent = new Intent(getActivity(), c_Lesson_freeuse_1.class);
                break;
            case 2:
                intent = new Intent(getActivity(), c_Lesson_freeuse_2.class);
                break;
            case 3:
                intent = new Intent(getActivity(), c_Lesson_freeuse_3.class);
                break;
            case 4:
                intent = new Intent(getActivity(), c_Lesson_freeuse_4.class);
                break;
            default:
                return;
        }
        startActivity(intent);
    }

    private void hideLockedOverlay(int cardId) {
        // Ensure view is not null and get the root view
        if (view != null) {
            // Determine the correct locked overlay view ID based on the card ID
            int lockedOverlayId = getLockedOverlayId(cardId);

            View lockedOverlay = view.findViewById(lockedOverlayId);
            if (lockedOverlay != null) {
                lockedOverlay.setVisibility(View.GONE);
                Log.d("hideLockedOverlay", "Overlay for card " + cardId + " set to GONE");
            } else {
                Log.e("hideLockedOverlay", "Locked overlay view is null for card " + cardId);
            }
        } else {
            Log.e("hideLockedOverlay", "Root view is null");
        }
    }

    // Method to get the correct locked overlay view ID based on the card ID
    private int getLockedOverlayId(int cardId) {
        switch (cardId) {
            case 1:
                return R.id.card1_locked_overlay;
            case 2:
                return R.id.card2_locked_overlay;
            case 3:
                return R.id.card3_locked_overlay;
            case 4:
                return R.id.card4_locked_overlay;
            default:
                return -1; // Invalid card ID
        }
    }
}
