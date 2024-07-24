package com.example.autotutoria20;

import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.autotutoria20.CustomLoadingDialog;
import com.example.autotutoria20.R;
import com.example.autotutoria20.c_Lesson_progressive_1;
import com.example.autotutoria20.c_Lesson_progressive_2;
import com.example.autotutoria20.c_Lesson_progressive_3;
import com.example.autotutoria20.c_Lesson_progressive_4;
import com.example.autotutoria20.z_Lesson_steps;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import com.example.autotutoria20.CustomLoadingDialog;

public class b_main_1_lesson_progressive extends Fragment {
    private static TextView learningModeText;
    private FrameLayout lockedOverlayCard1, lockedOverlayCard2, lockedOverlayCard3, lockedOverlayCard4;
    private ProgressBar progressBarCard1, progressBarCard2, progressBarCard3, progressBarCard4;
    private TextView progressTextCard1, progressTextCard2, progressTextCard3, progressTextCard4;
    private Button incrementCard1Button;
    private boolean isProgressiveMode = true; // Default mode is progressive mode
    private static View view;
    private CustomLoadingDialog loadingDialog;

    // Define card progress array
    private int[] cardProgress = new int[z_Lesson_steps.total_module_count]; // refer to the assigned value

    // Track completion status of each card
    private boolean[] cardCompletionStatus = {false, false, false, false}; // Default is false for all cards

    public interface ProgressUpdateListener {
        void onProgressUpdated();
    }

    private void incrementLoadingProgressBar(final ProgressBar progressBar, final int duration, final Runnable onComplete) {
        final Handler handler = new Handler(Looper.getMainLooper());
        final long startTime = System.currentTimeMillis();
        final long endTime = startTime + duration;

        handler.post(new Runnable() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                float progressFraction = (float) (currentTime - startTime) / duration;
                int currentProgress = (int) (progressFraction * 100);

                progressBar.setProgress(currentProgress);

                if (currentTime < endTime) {
                    handler.postDelayed(this, 16); // approximately 60fps
                } else {
                    progressBar.setProgress(100); // ensure we end exactly at 100%
                    onComplete.run();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.b_main_3_lesson_progressive, container, false);

        // Initialize views
        initializeViews();

        // Show loading dialog
        showLoadingDialog();

        return view;
    }

    private void showLoadingDialog() {
        loadingDialog = new CustomLoadingDialog(getActivity());
        loadingDialog.setCancelable(false); // Prevent closing the dialog
        loadingDialog.show();
    }

    private void updateProgress(int progress) {
        if (loadingDialog != null) {
            loadingDialog.setProgress(progress);
        }
    }

    private void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    // onResume is called every time the user returns to main menu
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

        CollectionReference progressRef = db.collection("users").document(userId).collection("Progressive Mode");

        progressRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int totalModules = 0;

                    for (DocumentSnapshot lessonDoc : task.getResult()) {
                        int lessonNumber = Integer.parseInt(lessonDoc.getId().substring(7).trim());
                        int[] maxProgressValues = z_Lesson_steps.getLessonSteps(lessonNumber);
                        totalModules += maxProgressValues.length;
                    }

                    int moduleCounter = 0;
                    final Handler handler = new Handler(Looper.getMainLooper());

                    for (DocumentSnapshot lessonDoc : task.getResult()) {
                        String lesson = lessonDoc.getId();
                        int totalProgress = 0;
                        int totalMaxProgress = 0;
                        int lessonNumber = Integer.parseInt(lesson.substring(7).trim());
                        int[] maxProgressValues = z_Lesson_steps.getLessonSteps(lessonNumber);

                        for (int i = 0; i < maxProgressValues.length; i++) {
                            String key = "M" + (i + 1);
                            Long moduleProgress = lessonDoc.getLong(key);
                            if (moduleProgress != null) {
                                totalProgress += moduleProgress;
                                totalMaxProgress += maxProgressValues[i];
                            }

                            moduleCounter++;
                            final int progress = (int) ((moduleCounter / (float) totalModules) * 100);
                            updateProgress(progress);
                        }

                        if (totalMaxProgress > 0) {
                            double overallProgress = ((double) totalProgress / totalMaxProgress) * 100;
                            int overallProgressInt = (int) Math.round(overallProgress);

                            updateCompletionStatus(lessonNumber);
                            incrementCard(lessonNumber, overallProgressInt, new ProgressUpdateListener() {
                                @Override
                                public void onProgressUpdated() {
                                    incrementLoadingProgressBar(loadingDialog.getLoadingProgressBar(), 3000, new Runnable() {
                                        @Override
                                        public void run() {
                                            hideLoadingDialog();
                                        }
                                    });
                                }
                            });
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
        if (isPreviousCardCompleted(cardId) || cardId == 1) {
            launchLessonActivity(cardId);
        } else {
            showCustomDialog();
        }
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_complete_previous_lesson, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        Button exitButton = dialogView.findViewById(R.id.okay_button);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                int width = (int) (350 * getResources().getDisplayMetrics().density);
                int height = (int) (350 * getResources().getDisplayMetrics().density);
                dialog.getWindow().setLayout(width, height);
            }
        });

        dialog.show();
    }

    private void updateCompletionStatus(int cardId) {
        if (cardId > 1) {
            cardCompletionStatus[cardId - 2] = true;
        }
    }

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

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private boolean isPreviousCardCompleted(int cardId) {
        int index = cardId - 1;
        if (index == 0) {
            return true;
        }
        boolean isCompleted = cardCompletionStatus[index - 1];
        Log.d("CardCheck", "Card " + cardId + " previous card completion status: " + isCompleted);
        return isCompleted;
    }

    private void incrementCard(int cardId, int incrementValue, ProgressUpdateListener listener) {
        cardId -= 1;
        String TAG = "incrementCard()";
        int newProgress = cardProgress[cardId] + incrementValue;
        cardProgress[cardId] = Math.min(newProgress, 100);

        if (cardProgress[cardId] >= 100) {
            cardCompletionStatus[cardId] = true;
            if (cardId < cardCompletionStatus.length) {
                hideLockedOverlay(cardId + 2);
            }
        }

        cardId += 1;
        ProgressBar progressBar = view.findViewById(getProgressBarId(cardId));
        TextView progressText = view.findViewById(getProgressTextId(cardId));
        cardId -= 1;

        progressBar.setProgress(cardProgress[cardId]);
        progressText.setText(cardProgress[cardId] + "% Completed");

        if (listener != null) {
            listener.onProgressUpdated();
        }

        if (cardId == 3) {
            incrementLoadingProgressBar(loadingDialog.getLoadingProgressBar(), 3000, new Runnable() {
                @Override
                public void run() {
                    showToast("This is the end my friend");
                    Log.e(TAG, "This is the end my friend");

                    double delayInSeconds = 2.5;
                    int delayMillis = (int) (delayInSeconds * 1000);

                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            hideLoadingDialog();
                        }
                    }, delayMillis);
                }
            });
        }
    }

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
        if (view != null) {
            int lockedOverlayId = getLockedOverlayId(cardId);
            View lockedOverlay = view.findViewById(lockedOverlayId);
            if (lockedOverlay != null) {
                lockedOverlay.setVisibility(View.GONE);
            } else {
                Log.e("hideLockedOverlay", "Locked overlay view is null for card " + cardId);
            }
        } else {
            Log.e("hideLockedOverlay", "Root view is null");
        }
    }

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
                return -1;
        }
    }
}
