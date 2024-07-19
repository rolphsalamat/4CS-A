package com.example.autotutoria20;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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
    private Dialog loadingDialog;

    // Define card progress array
    private int[] cardProgress = new int[z_Lesson_steps.total_module_count]; // refer to the assigned value

    // Track completion status of each card
    private boolean[] cardCompletionStatus = {false, false, false, false}; // Default is false for all cards

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.b_main_3_lesson_progressive, container, false);

        // Initialize views
        initializeViews();

        // Show loading dialog
        showLoadingDialog();

//        // Set up the increment button
//        Button increment = view.findViewById(R.id.increment_progress);
//        increment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for (int i = 0; i < 4; i++) {
//                    if (cardProgress[i] < 100) {
//                        incrementCard(i, 25);
//                        break;
//                    }
//                    if (i == 3 && cardProgress[i] >= 100) {
//                        showToast("Progressive Mode is Complete.");
//                    }
//                }
//            }
//        });

        return view;
    }

        private void showLoadingDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_loading, null);
            builder.setView(dialogView);
            builder.setCancelable(false); // Prevent closing the dialog
            loadingDialog = builder.create();
            loadingDialog.show();
        }

        private void hideLoadingDialog() {
            if (loadingDialog != null && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
        }

    // onResume is called everytime the user returns to main menu
    // From the Lessons Layout
    @Override
    public void onResume() {
        super.onResume();
        fetchAllProgressData();
    }

    private void fetchAllProgressData() {
        Log.d("fetchAllProgressData", "Method called");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String TAG = "fetchAllProgressData()";

        // Reference to the user's progress data collection
        CollectionReference progressRef =
                db.collection("users")
                .document(userId)
                .collection("Progressive Mode");

        // Directory ends at Progressive Mode
        // Meaning, this retrieves all of the Lessons

        // Fetch all lessons
        progressRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot lessonDoc : task.getResult()) {
                        String lesson = lessonDoc.getId();
                        Log.d(TAG, "Lesson: " + lesson);

                        // Initialize variables for total progress calculation
                        int totalProgress = 0;
                        int totalMaxProgress = 0;

                        // Get lesson number from the lesson name

                        // substring 7 kasi nasa 7th character yung number ng Lesson
                        int lessonNumber = Integer.parseInt(lesson.substring(7).trim());
                        int[] maxProgressValues = z_Lesson_steps.getLessonSteps(lessonNumber);

                        // This part of the code retrieves the individual modules
                        // within a certain Lesson

                        // Iterate over all fields to get module progress
                        for (int i = 0; i < maxProgressValues.length; i++) {
                            String key = "M" + (i + 1);
                            Long moduleProgress = lessonDoc.getLong(key);
                            if (moduleProgress != null) {
                                // Update total progress and max progress
                                totalProgress += moduleProgress;
                                totalMaxProgress += maxProgressValues[i];

                                // Log the progress for checking
                                Log.d(TAG, "Progressive Mode | " + lesson + " | " + key + " Progress: " + moduleProgress + "/" + maxProgressValues[i]);
                            } else {
                                Log.d(TAG, lesson + " | " + key + " Progress data not found.");
                            }
                        }

                        // Calculate overall progress for the lesson
                        if (totalMaxProgress > 0) {
                            double overallProgress = ((double) totalProgress / totalMaxProgress) * 100;
                            int overallProgressInt = (int) Math.round(overallProgress);

                            Log.d(TAG, lesson + " | Overall Progress: " + overallProgress + "%");

                            // Update the layout based on overallProgress
                            updateCompletionStatus(lessonNumber);
                            Log.e(TAG, "Let's call incrementCard() method");
                            incrementCard(lessonNumber, overallProgressInt);
                        } else {
                            Log.d(TAG, lesson + " | No progress data available.");
                        }
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void initializeViews() {

        // Progress Bar
        progressBarCard1 = view.findViewById(R.id.progressBar_card_1);
        progressBarCard2 = view.findViewById(R.id.progressBar_card_2);
        progressBarCard3 = view.findViewById(R.id.progressBar_card_3);
        progressBarCard4 = view.findViewById(R.id.progressBar_card_4);

        // Progress Text
        progressTextCard1 = view.findViewById(R.id.progressText_card_1);
        progressTextCard2 = view.findViewById(R.id.progressText_card_2);
        progressTextCard3 = view.findViewById(R.id.progressText_card_3);
        progressTextCard4 = view.findViewById(R.id.progressText_card_4);

        // Learning Mode? Ewan para san ba to
        // PWEDE I-REMOVE ITO PWEDE I-REMOVE ITO PWEDE I-REMOVE ITO PWEDE I-REMOVE ITO PWEDE I-REMOVE ITO PWEDE I-REMOVE ITO
        learningModeText = view.findViewById(R.id.learning_mode_text);

        // Locked Overlay
        lockedOverlayCard1 = view.findViewById(R.id.card1_locked_overlay);
        lockedOverlayCard2 = view.findViewById(R.id.card2_locked_overlay);
        lockedOverlayCard3 = view.findViewById(R.id.card3_locked_overlay);
        lockedOverlayCard4 = view.findViewById(R.id.card4_locked_overlay);

        setCardClickListeners();

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

        // checks if previous card is complete, but also allows if cardId is 1
        // because Card 1 should always be accessible as it is the beginning of the lesson
        if (isPreviousCardCompleted(cardId) || cardId == 1) {
            launchLessonActivity(cardId);
        }
        // Else, if clicked Card is not yet unlocked...
        else {
            showCustomDialog();
        }
    }

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
        // Adjust cardId to array index
        int index = cardId - 1;
        if (index == 0) {
            // If it's the first card, there's no previous card, so it's considered completed
            return true;
        }
        boolean isCompleted = cardCompletionStatus[index - 1];
        Log.d("CardCheck", "Card " + cardId + " previous card completion status: " + isCompleted);
        return isCompleted;
    }


        private void incrementCard(int cardId, int incrementValue) {

        // Card ID from called method ranges from [1 -> 4]
        // But the Array              ranges from [0 -> 3]
        // So decrementing by 1 fixes the problem.
        cardId -= 1;

        String TAG = "incrementCard()"; // For debugging purposes

        // add the current progress with the increment value
        // and set to an int variable
        int newProgress = cardProgress[cardId] + incrementValue;

        // set the new value
        // Math.min is used to ensure it will only be maxed to 100%
        cardProgress[cardId] = Math.min(newProgress, 100);

        // if current card is complete
        // array is still from [0 -> 3]
        // so no need to increment the cardId yet.
        if (cardProgress[cardId] >= 100) {
            cardCompletionStatus[cardId] = true;

            // Hide locked overlay for the next card
            // But uses if statement to ensure there is a next card
            // if cardId reaches 3, the array is maxed at 3
            // so it will not increment anymore, making this part of the code
            // a double for error handling
            if (cardId < cardCompletionStatus.length) {
                // +2 because decrement by 1 is called above
                // and the layout which is being used as parameter
                // starts from 1 not 0
                hideLockedOverlay(cardId + 2);
            }
        }

        // increment cardId in this part
        // because the layout uses range from [1 -> 4]
        // rather than how array works   from [0 -> 3]
        cardId += 1;

        // Update UI for the current card
        ProgressBar progressBar = view.findViewById(getProgressBarId(cardId));
        TextView progressText = view.findViewById(getProgressTextId(cardId));

        // decrement again
        // same reason, but why??
        // because the progressBar and progressText
        // should be initialized first
        cardId -= 1;

        progressBar.setProgress(cardProgress[cardId]);
        progressText.setText(cardProgress[cardId] + "% Completed");

        if (cardId == 3) {

            showToast("This is the end my friend");
            Log.e(TAG, "This is the end my friend");

            // Change this value accordingly
            double delayInSeconds = 2.5;
            int delayMillis = (int) (delayInSeconds * 1000);

            // Add a delay before hiding the loading dialog
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    hideLoadingDialog();
                    // Show main menu or update UI accordingly
                }
            }, delayMillis);
        }

    }

    // Helper methods to get the ProgressBar and TextView IDs
    private int getProgressBarId(int index) {
        switch (index) {
            case 1:
                return R.id.progressive_progressbar_lesson_1;
            case 2:
                return R.id.progressive_progressbar_lesson_2;
            case 3:
                return R.id.progressive_progressbar_lesson_3;
            case 4:
                return R.id.progressive_progressbar_lesson_4;
            default:
                return 0;
        }
    }

    private int getProgressTextId(int index) {
        switch (index) {
            case 1:
                return R.id.progressive_progresstext_lesson_1;
            case 2:
                return R.id.progressive_progresstext_lesson_2;
            case 3:
                return R.id.progressive_progresstext_lesson_3;
            case 4:
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

            // If it is not null, or if the object is find within the layout
            if (lockedOverlay != null) {
                lockedOverlay.setVisibility(View.GONE);
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
