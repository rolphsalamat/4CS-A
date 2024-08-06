package com.example.autotutoria20;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.autotutoria20.c_Lesson_freeuse_1;
import com.example.autotutoria20.c_Lesson_freeuse_2;
import com.example.autotutoria20.c_Lesson_freeuse_3;
import com.example.autotutoria20.c_Lesson_freeuse_4;
import com.example.autotutoria20.c_Lesson_freeuse_5;
import com.example.autotutoria20.c_Lesson_freeuse_6;
import com.example.autotutoria20.c_Lesson_freeuse_7;
import com.example.autotutoria20.c_Lesson_freeuse_8;
import com.example.autotutoria20.z_Lesson_steps;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class b_main_2_lesson_freeuse extends Fragment {
    private ProgressBar progressBarCard1, progressBarCard2, progressBarCard3, progressBarCard4,
            progressBarCard5, progressBarCard6, progressBarCard7, progressBarCard8;
    private TextView progressTextCard1, progressTextCard2, progressTextCard3, progressTextCard4,
            progressTextCard5, progressTextCard6, progressTextCard7, progressTextCard8;
    private static View view;

    private CustomLoadingDialog loadingDialog;

    // Define card progress array
    private int[] cardProgress = new int[z_Lesson_steps.total_module_count]; // refer to the assigned value

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.b_main_2_lesson_freeuse, container, false);

        String TAG = "Free Use Mode";

        Log.e(TAG, "Imma launch na already");

        // Initialize view
        initializeViews();

        Log.e(TAG, "Done na mag initializeViews()");

        // Show loading dialog
        showLoadingDialog();

        Log.e(TAG, "Done na mag showLoadingDialog");

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchAllProgressData();
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

    private void fetchAllProgressData() {
        Log.d("fetchAllProgressData", "Method called");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String TAG = "fetchAllProgressData()";

        CollectionReference progressRef = db.collection("users").document(userId).collection("Free Use Mode");

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

                            updateCardProgress(lessonNumber, overallProgressInt);
                        }
                    }

                    // Add delay similar to progressive mode
                    incrementLoadingProgressBar(loadingDialog.getLoadingProgressBar(), 3000, new Runnable() {
                        @Override
                        public void run() {
                            hideLoadingDialog();
                        }
                    });
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    hideLoadingDialog(); // Hide the dialog in case of failure as well
                }
            }
        });
    }

    private void updateCardProgress(int cardId, int progressValue) {
        ProgressBar progressBar = null;
        TextView progressText = null;

        switch (cardId) {
            case 1:
                progressBar = progressBarCard1;
                progressText = progressTextCard1;
                break;
            case 2:
                progressBar = progressBarCard2;
                progressText = progressTextCard2;
                break;
            case 3:
                progressBar = progressBarCard3;
                progressText = progressTextCard3;
                break;
            case 4:
                progressBar = progressBarCard4;
                progressText = progressTextCard4;
                break;
            case 5:
                progressBar = progressBarCard5;
                progressText = progressTextCard5;
                break;
            case 6:
                progressBar = progressBarCard6;
                progressText = progressTextCard6;
                break;
            case 7:
                progressBar = progressBarCard7;
                progressText = progressTextCard7;
                break;
            case 8:
                progressBar = progressBarCard8;
                progressText = progressTextCard8;
                break;
            default:
                Log.d("updateCardProgress", "Invalid cardId: " + cardId);
                return;
        }

        if (progressBar != null && progressText != null) {
            progressBar.setProgress(progressValue);
            progressText.setText(progressValue + "% Completed");
        } else {
            Log.d("updateCardProgress", "Progress bar or text view is null for cardId: " + cardId);
        }
    }

    private void initializeViews() {
        // Progress Bar
        progressBarCard1 = view.findViewById(R.id.progressBar_card_1);
        progressBarCard2 = view.findViewById(R.id.progressBar_card_2);
        progressBarCard3 = view.findViewById(R.id.progressBar_card_3);
        progressBarCard4 = view.findViewById(R.id.progressBar_card_4);
        progressBarCard5 = view.findViewById(R.id.progressBar_card_5);
        progressBarCard6 = view.findViewById(R.id.progressBar_card_6);
        progressBarCard7 = view.findViewById(R.id.progressBar_card_7);
        progressBarCard8 = view.findViewById(R.id.progressBar_card_8);

        // Progress Text
        progressTextCard1 = view.findViewById(R.id.progressText_card_1);
        progressTextCard2 = view.findViewById(R.id.progressText_card_2);
        progressTextCard3 = view.findViewById(R.id.progressText_card_3);
        progressTextCard4 = view.findViewById(R.id.progressText_card_4);
        progressTextCard5 = view.findViewById(R.id.progressText_card_5);
        progressTextCard6 = view.findViewById(R.id.progressText_card_6);
        progressTextCard7 = view.findViewById(R.id.progressText_card_7);
        progressTextCard8 = view.findViewById(R.id.progressText_card_8);

        setCardClickListeners();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    // Method to set click listeners for the cards
    private void setCardClickListeners() {
        // Card 1 click listener
        view.findViewById(R.id.card1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkConnected()) {
                    showToast("Please connect to internet first");
                    return;
                } else {
                    Intent intent = new Intent(getActivity(), c_Lesson_freeuse_1.class);
                    startActivity(intent);
                }
            }
        });

        // Card 2 click listener
        view.findViewById(R.id.card2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkConnected()) {
                    showToast("Please connect to internet first");
                    return;
                } else {
                    Intent intent = new Intent(getActivity(), c_Lesson_freeuse_2.class);
                    startActivity(intent);
                }
            }
        });

        // Card 3 click listener
        view.findViewById(R.id.card3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkConnected()) {
                    showToast("Please connect to internet first");
                    return;
                } else {
                    Intent intent = new Intent(getActivity(), c_Lesson_freeuse_3.class);
                    startActivity(intent);
                }
            }
        });

        // Card 4 click listener
        view.findViewById(R.id.card4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkConnected()) {
                    showToast("Please connect to internet first");
                    return;
                } else {
                    Intent intent = new Intent(getActivity(), c_Lesson_freeuse_4.class);
                    startActivity(intent);
                }
            }
        });

        // Card 5 click listener
        view.findViewById(R.id.card5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkConnected()) {
                    showToast("Please connect to internet first");
                    return;
                } else {
                    Intent intent = new Intent(getActivity(), c_Lesson_freeuse_5.class);
                    startActivity(intent);
                }
            }
        });

        // Card 6 click listener
        view.findViewById(R.id.card6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkConnected()) {
                    showToast("Please connect to internet first");
                    return;
                } else {
                    Intent intent = new Intent(getActivity(), c_Lesson_freeuse_6.class);
                    startActivity(intent);
                }
            }
        });

        // Card 7 click listener
        view.findViewById(R.id.card7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkConnected()) {
                    showToast("Please connect to internet first");
                    return;
                } else {
                    Intent intent = new Intent(getActivity(), c_Lesson_freeuse_7.class);
                    startActivity(intent);
                }
            }
        });

        // Card 8 click listener
        view.findViewById(R.id.card8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkConnected()) {
                    showToast("Please connect to internet first");
                    return;
                } else {
                    Intent intent = new Intent(getActivity(), c_Lesson_freeuse_8.class);
                    startActivity(intent);
                }
            }
        });
    }

    // Method to display a toast message
    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
