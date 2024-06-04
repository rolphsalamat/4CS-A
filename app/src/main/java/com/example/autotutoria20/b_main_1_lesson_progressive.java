package com.example.autotutoria20;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


public class b_main_1_lesson_progressive extends Fragment {
    private static TextView learningModeText;
    private ProgressBar progressBarCard1, progressBarCard2, progressBarCard3, progressBarCard4;
    private TextView progressTextCard1, progressTextCard2, progressTextCard3, progressTextCard4;
    private TextView card_number, increment_value;
    private Button incrementCard1Button;
    private boolean isProgressiveMode = true; // Default mode is progressive mode
    private static View view;
    private Dialog dialog;
    private Button increment;
    // Define card progress array
    private int[] cardProgress = new int[4]; // Assuming there are 4 cards

    // Track completion status of each card
    private boolean[] cardCompletionStatus = {false, false, false, false}; // Default is false for all cards

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.b_main_3_lesson_progressive, container, false);

        // Initialize views
        progressBarCard1 = view.findViewById(R.id.progressBar_card_1);
        progressBarCard2 = view.findViewById(R.id.progressBar_card_2);
        progressBarCard3 = view.findViewById(R.id.progressBar_card_3);
        progressBarCard4 = view.findViewById(R.id.progressBar_card_4);

        progressTextCard1 = view.findViewById(R.id.progressText_card_1);
        progressTextCard2 = view.findViewById(R.id.progressText_card_2);
        progressTextCard3 = view.findViewById(R.id.progressText_card_3);
        progressTextCard4 = view.findViewById(R.id.progressText_card_4);

        // Initialize views
        learningModeText = view.findViewById(R.id.learning_mode_text);

        // Set click listeners for the cards
        setCardClickListeners();

        increment = view.findViewById(R.id.increment_progress); // Call findViewById on the view object
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCardProgress();
            }
        });

        return view;
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

    // Method to update the learning mode text
    public static void updateLearningModeText(String mode) {
        if (learningModeText != null) {
            learningModeText.setText(mode);
        }
    }

    // Method to handle click on a card
    private void handleCardClick(int cardId) {
        // Check if the previous card is completed
        if (isPreviousCardCompleted(cardId)) {
            // If previous card is completed, launch the lesson activity
            launchLessonActivity(cardId);
        } else {
            // If previous card is not completed, show a toast message
            showToast("Complete " + getPreviousLessonDescription(cardId) + " to access this lesson");

            // Show "dialog_complete_previous_lesson.xml" here...
            showCustomDialog();
            // The dialog contains only a single button
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
        // Assuming cardIds start from 1
        int previousCardId = cardId - 1;

        // If the previous card is the first card, it is always considered completed
        if (previousCardId <= 0)
            return true;
        else
            return cardCompletionStatus[previousCardId - 1]; // Adjust index since arrays are zero-based
    }
    // Method to increment the progress of the first incomplete card
    private void incrementCardProgress() {
        for (int i = 0; i < cardCompletionStatus.length; i++) {
            // If the current card is not complete, increment its progress
            if (!cardCompletionStatus[i]) {
                incrementCard(i, 25); // Increment progress by 25%
                return;
            }
        }

        Toast.makeText(getActivity(), "All cards are already completed", Toast.LENGTH_SHORT).show();
    }

    // Helper method to increment progress of a specific card
    private void incrementCard(int cardId, int incrementValue) {
        // Ensure the cardId is within the valid range
        if (cardId >= 0 && cardId < cardProgress.length) {
            // Increment progress by the specified value
            cardProgress[cardId] = Math.min(cardProgress[cardId] + incrementValue, 100);

            // Check if the card is completed
            if (cardProgress[cardId] == 100) {
                // Update card completion status
                cardCompletionStatus[cardId] = true;
                hideLockedOverlay(cardId);
                Toast.makeText(getActivity(), "Card " + (cardId + 1) + " completed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Card " + (cardId + 1) + " progress: " + cardProgress[cardId] + "%", Toast.LENGTH_SHORT).show();
            }

            // Update the progress bar and text for the current card
            ProgressBar progressBar = getActivity().findViewById(getProgressBarId(cardId));
            TextView progressText = getActivity().findViewById(getProgressTextId(cardId));

            if (progressBar != null && progressText != null) {
                progressBar.setProgress(cardProgress[cardId]);
                progressText.setText(cardProgress[cardId] + "% Completed");
            }
        } else {
            // Handle invalid cardId here, maybe show a toast indicating the issue
            Toast.makeText(getActivity(), "Invalid card ID", Toast.LENGTH_SHORT).show();
        }
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
    // Method to hide the locked overlay for a completed card
    private void hideLockedOverlay(int cardId) {
        Toast.makeText(getActivity(), "UNLOCK " + cardId, Toast.LENGTH_SHORT).show();
        switch (cardId) {
            case 0:
                getView().findViewById(R.id.card2_locked_overlay).setVisibility(View.GONE);
                break;
            case 1:
                getView().findViewById(R.id.card3_locked_overlay).setVisibility(View.GONE);
                break;
            case 2:
                getView().findViewById(R.id.card4_locked_overlay).setVisibility(View.GONE);
                break;
//            case 3:
//                getView().findViewById(R.id.card4_locked_overlay).setVisibility(View.GONE);
//                break;
            default:
                break;
        }
    }
}
