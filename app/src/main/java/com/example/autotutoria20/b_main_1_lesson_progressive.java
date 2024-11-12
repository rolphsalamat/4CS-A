package com.example.autotutoria20;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class b_main_1_lesson_progressive extends Fragment {


    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    static String userId = user != null ? user.getUid() : null;
    private FrameLayout lockedOverlayCard1, lockedOverlayCard2, lockedOverlayCard3, lockedOverlayCard4,
            lockedOverlayCard5, lockedOverlayCard6, lockedOverlayCard7, lockedOverlayCard8;
    private ProgressBar progressBarCard1, progressBarCard2, progressBarCard3, progressBarCard4,
            progressBarCard5, progressBarCard6, progressBarCard7, progressBarCard8;
    private TextView progressTextCard1, progressTextCard2, progressTextCard3, progressTextCard4,
            progressTextCard5, progressTextCard6, progressTextCard7, progressTextCard8;

    private boolean isProgressiveMode = true; // Default mode is progressive mode
    private static View view;
    private CustomLoadingDialog loadingDialog;

    // Define card progress array
    private int[] cardProgress = new int[z_Lesson_steps.total_module_count]; // refer to the assigned value

    // Track completion status of each card
    public static boolean[] cardCompletionStatus = {false, false, false, false, false, false, false, false}; // Default is false for all cards

    private n_Network network;
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

//        n_Context = getContext();

//        initializeModules();

        // Initialize views
        initializeViews();

        // Show loading dialog
        showLoadingDialog();

        return view;
    }

//    private void initializeModules() {
//
//        c_Lesson_progressive_1 mod1 = new c_Lesson_progressive_1();
//        mod1.onResume();
//
//        c_Lesson_progressive_2 mod2 = new c_Lesson_progressive_2();
//        mod2.onResume();
//
//        c_Lesson_progressive_3 mod3 = new c_Lesson_progressive_3();
//        mod3.onResume();
//
//        c_Lesson_progressive_4 mod4 = new c_Lesson_progressive_4();
//        mod4.onResume();
//
//        c_Lesson_progressive_5 mod5 = new c_Lesson_progressive_5();
//        mod5.onResume();
//
//        c_Lesson_progressive_6 mod6 = new c_Lesson_progressive_6();
//        mod6.onResume();
//
//        c_Lesson_progressive_7 mod7 = new c_Lesson_progressive_7();
//        mod7.onResume();
//
//        c_Lesson_progressive_8 mod8 = new c_Lesson_progressive_8();
//        mod8.onResume();
//
//    }

    private void showLoadingDialog() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            loadingDialog = new CustomLoadingDialog(getActivity());
            loadingDialog.setCancelable(false); // Prevent closing the dialog
            loadingDialog.show();
        }
    }

    private void updateProgress(int progress) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.setProgress(progress);
        }
    }

    private void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing() && getActivity() != null && !getActivity().isFinishing()) {
            loadingDialog.dismiss();
        }
    }

    // onResume is called every time the user returns to main menu
    @Override
    public void onResume() {
        super.onResume();
        fetchAllProgressData();

//        initializeModules();
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
                    double totalAverageBKTScore = 0.0;
                    int lessonCount = 0;
                    int moduleCount = 0;
                    int moduleCounter = 0;
                    AtomicInteger totalCompletedModules = new AtomicInteger(0); // Track the number of fully completed modules

                    for (DocumentSnapshot lessonDoc : task.getResult()) {

                        moduleCount++;

                        String lesson = lessonDoc.getId();
                        int totalProgress = 0;
                        int totalMaxProgress = 0;
                        int lessonNumber = Integer.parseInt(lesson.substring(7).trim());
                        int[] maxProgressValues = z_Lesson_steps.getLessonSteps(lessonNumber);
                        totalModules += maxProgressValues.length;
                        int completeCounter = 0;

                        double totalBKTScore = 0.0;
                        int bktScoreCount = 0;

                        for (int i = 0; i < maxProgressValues.length; i++) {
                            String keyProgress = "M" + (i + 1) + ".Progress";
                            String keyScore = "M" + (i + 1) + ".BKT Score";

                            Long moduleProgress = lessonDoc.getLong(keyProgress);
                            if (moduleProgress != null) {
                                totalProgress += moduleProgress;
                                totalMaxProgress += maxProgressValues[i];
                            }

                            Double moduleScore = lessonDoc.getDouble(keyScore);
                            if (moduleScore != null) {
                                totalBKTScore += moduleScore;
                                bktScoreCount++;
                            }

                            Log.e(TAG, "Lesson[" + (i + 1) + "] BKT Score: " + moduleScore);
                            Log.e(TAG, "Lesson[" + (i + 1) + "] Progress: " + moduleProgress);

                            if (moduleScore != null && moduleScore >= b_main_0_menu_categorize_user.passingGrade) {
                                String module = "M" + (i + 1);
                                String lesson_1 = "Lesson " + lessonNumber;
                                if (moduleProgress != null && moduleProgress >= getLessonSteps(module, lesson_1)) {
                                    completeCounter++;
                                    Log.e(TAG, "Lesson[" + (i + 1) + "] Passed | completeCounter: " + completeCounter);
                                }
                            }

                            moduleCounter++;
                            final int progress = (int) ((moduleCounter / (float) totalModules) * 100);
                            updateProgress(progress);
                        }

                        if (completeCounter == maxProgressValues.length) {
                            Log.e("TAG", "Module[" + lessonNumber + "] is Complete!");

//                            int moduleNumber = (lessonNumber-1);
//                            unlockPreviousCard(moduleNumber);

//                            if (cardCompletionStatus[lessonNumber - 2]) {
                                cardCompletionStatus[lessonNumber - 1] = true;
                                hideLockedOverlay(lessonNumber - 1);
//                            }


                            totalCompletedModules.incrementAndGet(); // Increment AtomicInteger
                        } else {
                            //Module is not yet passed
//                            c_Lesson_feedback.showModuleFailed(requireContext(), "Module " + lessonNumber);
                        }

                        double averageBKTScore = bktScoreCount > 0 ? totalBKTScore / bktScoreCount : 0.0;
                        Log.e(TAG, "Average BKT Score for Lesson " + lessonNumber + ": " + averageBKTScore);

                        totalAverageBKTScore += averageBKTScore;
                        lessonCount++;

                        if (totalMaxProgress > 0) {
                            double overallProgress = ((double) totalProgress / totalMaxProgress) * 100;
                            int overallProgressInt = (int) Math.round(overallProgress);
                            updateCardProgress(lessonNumber, overallProgressInt);
                        }

                        Log.e("TAG", "WALTER | completedModule: " + totalCompletedModules.get());
                        Log.e("TAG", "WALTER | Module Count: " + maxProgressValues.length);
                    }

                    double overallAverageBKTScore = lessonCount > 0 ? totalAverageBKTScore / lessonCount : 0.0;
                    overallAverageBKTScore *= 100;

                    Log.e("fetchAllProgressData()", String.format("Overall Average BKT Score for All Lessons: %.2f%%", overallAverageBKTScore));

                    String newCategory = getUserCategory(overallAverageBKTScore);
                    Log.e("TAG", "WALTER | New Category: " + newCategory);


                    incrementLoadingProgressBar(loadingDialog.getLoadingProgressBar(), 2000, new Runnable() {
                        @Override
                        public void run() {
                            hideLoadingDialog();

                            // Show the dialog only if all modules are complete

                            // 8 or how do I make this dynamic??
                            // Optionally, use moduleCount, since it will always be equal to total modules
                            // concern is baka mag read sya na always true
                            if (totalCompletedModules.get() == 8) { // Get value from AtomicInteger
                                setCompletedStatus(true);
//                                showToast("show change category dialog");
                                showChangeCategoryDialog(newCategory);
                            }

                        }
                    });
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    hideLoadingDialog();
                }
            }
        });
    }

    public void setCompletedStatus(Boolean status) {

        String TAG = "setCompletedStatus";

        b_main_0_menu.isProgressiveCompleted = true;

        db.collection("users").document(userId)
                .update("isComplete", status)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "'isComplete' field added with default value: true");
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error adding 'isComplete' field", e);
                });

    }

    public static void isComplete() {

        String TAG = "isComplete()";

        if (userId == null) {
            Log.e(TAG, "User not authenticated");
            return;
        }

        db.collection("users").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Check if the "isComplete" field exists
                        if (documentSnapshot.contains("isComplete")) {
                            // Retrieve the value of "isComplete"
                            b_main_0_menu.isProgressiveCompleted = documentSnapshot.getBoolean("isComplete");
                            Log.d(TAG, "Module Completed retrieved: " + b_main_0_menu.isProgressiveCompleted);
                        } else {
                            // If "isComplete" does not exist, set it to true and update the database
                            Log.d(TAG, "'isComplete' field does not exist. Setting default value to true.");
                            db.collection("users").document(userId)
                                    .update("isComplete", true)
                                    .addOnSuccessListener(aVoid -> {
                                        Log.d(TAG, "'isComplete' field added with default value: true");
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.e(TAG, "Error adding 'isComplete' field", e);
                                    });
                        }
                    } else {
                        Log.e(TAG, "No such document");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error retrieving User Status", e);
                });

        Log.e(TAG, "retrieving user isComplete | is complete!");
        Log.e(TAG, "User is completed?: " + b_main_0_menu.isProgressiveCompleted);

    }

//    public void unlockPreviousCard(int moduleNumber) {
//
//        int cardId = (moduleNumber+2);
//
//        int lockedOverlayId = getLockedOverlayId(cardId);
//        View lockedOverlay = view.findViewById(lockedOverlayId);
//        if (lockedOverlay != null) {
////            lockedOverlay.setVisibility(View.GONE);
//            if (lockedOverlay.getVisibility())
//        } else {
//            Log.e("hideLockedOverlay", "Locked overlay view is null for card " + cardId);
//        }
//
//        for (int i=0; i<moduleNumber; i++) {
//            if (cardCompletionStatus[i] && lockedOverlay.getVisibility() == View.GONE)
//                // do:
//        //        cardCompletionStatus[moduleNumber] = true;
//        //        hideLockedOverlay(moduleNumber);
//        }
//
//    }



    // hindi gumagana yung cardcompletionstatus and hidelockedoverlay
//    private void fetchAllProgressData() {
//        Log.d("fetchAllProgressData", "Method called");
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        String TAG = "fetchAllProgressData()";
//
//        CollectionReference progressRef = db.collection("users").document(userId).collection("Progressive Mode");
//
//        progressRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    int totalModules = 0;
//                    double totalAverageBKTScore = 0.0; // Track sum of average BKT scores for all lessons
//                    int lessonCount = 0; // Track the count of lessons processed
//
//                    int moduleCounter = 0;
//
//                    for (DocumentSnapshot lessonDoc : task.getResult()) {
//                        String lesson = lessonDoc.getId();
//                        int totalProgress = 0;
//                        int totalMaxProgress = 0;
//                        int lessonNumber = Integer.parseInt(lesson.substring(7).trim());
//                        int[] maxProgressValues = z_Lesson_steps.getLessonSteps(lessonNumber);
//                        totalModules += maxProgressValues.length;
//                        int iteration = 1;
//                        int completedModule = 0;
//
//                        Log.e(TAG, "Module[" + (iteration) + "]");
//                        iteration++;
//
//                        Log.e(TAG, "maxProgressValues.length: " + maxProgressValues.length);
//                        int completeCounter = 0;
//
//                        double totalBKTScore = 0.0;
//                        int bktScoreCount = 0;
//
//                        for (int i = 0; i < maxProgressValues.length; i++) {
//                            String keyProgress = "M" + (i + 1) + ".Progress";
//                            String keyScore = "M" + (i + 1) + ".BKT Score";
//
//                            Long moduleProgress = lessonDoc.getLong(keyProgress);
//                            if (moduleProgress != null) {
//                                totalProgress += moduleProgress;
//                                totalMaxProgress += maxProgressValues[i];
//                            }
//
//                            Double moduleScore = lessonDoc.getDouble(keyScore);
//                            if (moduleScore != null) {
//                                totalBKTScore += moduleScore;
//                                bktScoreCount++;
//                            }
//
//                            Log.e(TAG, "Lesson[" + (i + 1) + "] BKT Score: " + moduleScore);
//                            Log.e(TAG, "Lesson[" + (i + 1) + "] Progress: " + moduleProgress);
//
//                            // Check if the moduleScore meets the passing grade
//                            if (moduleScore != null && moduleScore >= b_main_0_menu_categorize_user.passingGrade) {
//                                String module = "M" + (i + 1);
//                                String lesson_1 = "Lesson " + (iteration - 1);
//                                if (moduleProgress != null && moduleProgress >= getLessonSteps(module, lesson_1)) {
//                                    completeCounter++;
//                                    Log.e(TAG, "Lesson[" + (i + 1) + "] Passed | completeCounter: " + completeCounter);
//                                }
//                            }
//
//                            moduleCounter++;
//                            final int progress = (int) ((moduleCounter / (float) totalModules) * 100);
//                            updateProgress(progress);
//
//                            if (completeCounter == maxProgressValues.length) {
//
//                                Log.e("TAG", "GOZ | Module["+iteration+"] is complete!");
//
//                                Log.e("TAG", "GOZ | Iteration: " + iteration);
//                                Log.e("TAG", "GOZ | ");
//
//                                Log.e("TAG", "Module[" + (iteration - 1) + "] is Complete!");
//                                cardCompletionStatus[iteration - 1] = true;
//                                hideLockedOverlay(iteration - 1);
//                                completedModule++;
//                            }
//
//                        }
//
//                        // Calculate and log average BKT score for the current lesson
//                        double averageBKTScore = bktScoreCount > 0 ? totalBKTScore / bktScoreCount : 0.0;
//                        Log.e(TAG, "Average BKT Score for Lesson " + lessonNumber + ": " + averageBKTScore);
//
//                        // Accumulate the average BKT score and increment lesson count
//                        totalAverageBKTScore += averageBKTScore;
//                        lessonCount++;
//
//                        if (totalMaxProgress > 0) {
//                            double overallProgress = ((double) totalProgress / totalMaxProgress) * 100;
//                            int overallProgressInt = (int) Math.round(overallProgress);
//                            updateCardProgress(lessonNumber, overallProgressInt);
//                        }
//
//                        Log.e("TAG", "WALTER | completedModule: " + completedModule);
//                        Log.e("TAG", "WALTER | Module Count: " + maxProgressValues.length);
//                    }
//
//                    // Calculate and log the overall average BKT score for all lessons
//                    double overallAverageBKTScore = lessonCount > 0 ? totalAverageBKTScore / lessonCount : 0.0;
//                    overallAverageBKTScore *= 100; // Convert to percentage
//
//                    Log.e("fetchAllProgressData()", String.format("Overall Average BKT Score for All Lessons: %.2f%%", overallAverageBKTScore));
//
//                    String newCategory = getUserCategory(overallAverageBKTScore);
//
//                    // dapat mag show lang to pag tapos na yung profressive mode
//                    showChangeCategoryDialog(newCategory);
//
//                    Log.e("TAG", "WALTER | New Category: " + newCategory);
//
//                    incrementLoadingProgressBar(loadingDialog.getLoadingProgressBar(), 3000, new Runnable() {
//                        @Override
//                        public void run() {
//                            hideLoadingDialog();
//                        }
//                    });
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                    hideLoadingDialog(); // Hide the dialog in case of failure as well
//                }
//            }
//        });
//    }

    private void showChangeCategoryDialog(String newCategory) {
        // Create an AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        // Inflate the custom layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.b_main_5_change_category, null);
        builder.setView(dialogView);

        // Initialize input fields
        ImageView exit = dialogView.findViewById(R.id.close_button);
        TextView proficiency = dialogView.findViewById(R.id.proficiency_text);
        TextView retake = dialogView.findViewById(R.id.retake_text);
        Button okay = dialogView.findViewById(R.id.okay_button);

        // Show the dialog
        AlertDialog dialog = builder.create();

        // Categories in order of proficiency
        List<String> categories = Arrays.asList("Novice", "Beginner", "Intermediate", "Advance", "Expert");

        // Get the current category and the index of both the current and new categories
        String currentCategory = b_main_0_menu_categorize_user.category; // Use your current category variable
        int currentCategoryIndex = categories.indexOf(currentCategory);
        int newCategoryIndex = categories.indexOf(newCategory);

        // Check if the new category is lower, higher, or the same as the current category
        if (newCategoryIndex > currentCategoryIndex) {
            // New category is higher proficiency
            proficiency.setText("Congratulations! You've moved up to the " + newCategory + " level.");
        } else if (newCategoryIndex < currentCategoryIndex) {
            // New category is lower proficiency
            proficiency.setText("Based on your recent performance, your proficiency level appears to align with: "
                    + newCategory
                    + ". However, you may continue to practice to maintain or improve your current level.");
        } else {
            // New category is the same as the current category
            proficiency.setText("Your proficiency level remains the same at: " + newCategory + ".");
        }

        // Set the retake message
        retake.setText("Would you like to retake the Automata course under the [" + newCategory + "] classification?");

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCategory(newCategory);
                dialog.dismiss(); // Optionally dismiss the dialog after changing category
            }
        });

        dialog.show();
    }


    private void changeCategory(String newCategory) {
        Log.e("changeCategory()", "Changing category to: " + newCategory);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference userRef = db.collection("users").document(userId);

        // Update user category
        userRef.update("User Category", newCategory)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "User category updated successfully"))
                .addOnFailureListener(e -> Log.e("Firestore", "Error updating user category", e));

        // Reset Progressive Mode
        resetProgressiveMode(db, userId);
    }

    private void resetProgressiveMode(FirebaseFirestore db, String userId) {
        // Define a reference to the Progressive Mode collection for the user
        CollectionReference progressiveModeRef = db.collection("users").document(userId).collection("Progressive Mode");

        // Delete existing Progressive Mode documents
        progressiveModeRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult()) {
                    document.getReference().delete();
                }
                Log.d("Firestore", "Existing Progressive Mode data deleted successfully.");

                // Reinitialize Progressive Mode data
                Map<String, Map<String, Object>> progressiveModeData = initializeProgressiveMode();
                for (Map.Entry<String, Map<String, Object>> entry : progressiveModeData.entrySet()) {
                    progressiveModeRef.document(entry.getKey()).set(entry.getValue())
                            .addOnSuccessListener(aVoid -> Log.d("Firestore", "Progressive Mode reset: " + entry.getKey()))
                            .addOnFailureListener(e -> Log.e("Firestore", "Error resetting Progressive Mode", e));
                }

                // restart or refresh this fragment?? or the AppCompatActivity it is connected to b_main_0_menu
//                // Inside your fragment
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.detach(this).attach(this).commit();

                Intent intent = new Intent(requireContext(), b_main_0_menu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                requireActivity().startActivity(intent);
                requireActivity().finish(); // This finishes the parent activity and restarts it.


            } else {
                Log.e("Firestore", "Error fetching Progressive Mode documents for reset.", task.getException());
            }
        });
    }

    // Create fresh Progressive Mode data similar to the setup in signup
    private Map<String, Map<String, Object>> initializeProgressiveMode() {
        Map<String, Map<String, Object>> progressiveModeData = new HashMap<>();
        String[] lessons = {"Lesson 1", "Lesson 2", "Lesson 3", "Lesson 4", "Lesson 5", "Lesson 6", "Lesson 7", "Lesson 8"};

        for (String lesson : lessons) {
            int stepLength = a_user_2_signup.getLessonStepsLength(Integer.parseInt(lesson.split(" ")[1])); // Retrieve step length
            progressiveModeData.put(lesson, a_user_2_signup.createModuleProgress(stepLength)); // Initialize progress data for each lesson
        }

        return progressiveModeData;
    }


    String getUserCategory(double overallAverageBKTScore) {
//        int scorePercentage =
//                (int) (
//                        overallAverageBKTScore
////                                * 100
//                )
//                ;

        int scorePercentage = (int) overallAverageBKTScore;

        Log.e("getUserCategory()", "Score: " + scorePercentage);

        if (scorePercentage < 69) {
            return "Novice";
        } else if (scorePercentage < 77) {
            return "Beginner";
        } else if (scorePercentage < 85) {
            return "Intermediate";
        } else if (scorePercentage < 93) {
            return "Advanced";
        } else {
            return "Expert";
        }

    }




    // Original Code
//        private void fetchAllProgressData() {
//            Log.d("fetchAllProgressData", "Method called");
//            FirebaseFirestore db = FirebaseFirestore.getInstance();
//            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//            String TAG = "fetchAllProgressData()";
//
//            CollectionReference progressRef = db.collection("users").document(userId).collection("Progressive Mode");
//
//            progressRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                    if (task.isSuccessful()) {
//                        int totalModules = 0;
//
//                        for (DocumentSnapshot lessonDoc : task.getResult()) {
//                            int lessonNumber = Integer.parseInt(lessonDoc.getId().substring(7).trim());
//                            int[] maxProgressValues = z_Lesson_steps.getLessonSteps(lessonNumber);
//                            totalModules += maxProgressValues.length;
//                        }
//
//                        int moduleCounter = 0;
//
//                        int iteration = 1;
//
//                        int completedModule = 0;
//
//                        for (DocumentSnapshot lessonDoc : task.getResult()) {
//                            String lesson = lessonDoc.getId();
//                            int totalProgress = 0;
//                            int totalMaxProgress = 0;
//                            int lessonNumber = Integer.parseInt(lesson.substring(7).trim());
//                            int[] maxProgressValues = z_Lesson_steps.getLessonSteps(lessonNumber);
//
//                            Log.e(TAG, "Module[" + (iteration) + "]");
//                            iteration++;
//
//                            Log.e(TAG, "maxProgressValues.length: " + maxProgressValues.length);
//                            int completeCounter = 0;
//
//                            double totalBKTScore = 0.0; // Initialize total BKT score
//                            int bktScoreCount = 0;      // Initialize count for BKT scores
//
//                            for (int i = 0; i < maxProgressValues.length; i++) {
//                                String keyProgress = "M" + (i + 1) + ".Progress";
//                                String keyScore = "M" + (i + 1) + ".BKT Score";
//
//                                Long moduleProgress = lessonDoc.getLong(keyProgress);
//                                if (moduleProgress != null) {
//                                    totalProgress += moduleProgress;
//                                    totalMaxProgress += maxProgressValues[i];
//                                }
//
//                                Double moduleScore = lessonDoc.getDouble(keyScore); // Get BKT Score
//                                if (moduleScore != null) {
//                                    totalBKTScore += moduleScore;  // Accumulate BKT score
//                                    bktScoreCount++;               // Increment count
//                                }
//
//                                Log.e(TAG, "Lesson[" + (i + 1) + "] BKT Score: " + moduleScore);
//                                Log.e(TAG, "Lesson[" + (i + 1) + "] Progress: " + moduleProgress);
//
//                                // Check if the moduleScore meets the passing grade
//                                if (moduleScore != null && moduleScore >= b_main_0_menu_categorize_user.passingGrade) {
//                                    String module = "M" + (i + 1);
//                                    String lesson_1 = "Lesson " + (iteration - 1);
//                                    if (moduleProgress != null && moduleProgress >= getLessonSteps(module, lesson_1)) {
//                                        completeCounter++;
//                                        Log.e(TAG, "Lesson[" + (i + 1) + "] Passed | completeCounter: " + completeCounter);
//                                    }
//                                }
//
//                                moduleCounter++;
//                                final int progress = (int) ((moduleCounter / (float) totalModules) * 100);
//                                updateProgress(progress);
//
//                                if (completeCounter == maxProgressValues.length) {
//                                    Log.e("TAG", "Module[" + (iteration - 1) + "] is Complete!");
//                                    cardCompletionStatus[iteration - 2] = true;
//                                    hideLockedOverlay(iteration);
//                                    completedModule++;
//                                }
//                            }
//
//                            // Calculate average BKT score for this lessonDoc iteration
//                            double averageBKTScore = bktScoreCount > 0 ? totalBKTScore / bktScoreCount : 0.0;
//                            Log.e(TAG, "Average BKT Score for Lesson " + lessonNumber + ": " + averageBKTScore);
//
//                            if (totalMaxProgress > 0) {
//                                double overallProgress = ((double) totalProgress / totalMaxProgress) * 100;
//                                int overallProgressInt = (int) Math.round(overallProgress);
//                                updateCardProgress(lessonNumber, overallProgressInt);
//                            }
//
//                            Log.e("TAG", "WALTER | completedModule: " + completedModule);
//                            Log.e("TAG", "WALTER | Module Count: " + maxProgressValues.length);
//                        }
//
//
//                        // Original Working Code
////                        for (DocumentSnapshot lessonDoc : task.getResult()) {
////                            String lesson = lessonDoc.getId();
////                            int totalProgress = 0;
////                            int totalMaxProgress = 0;
////                            int lessonNumber = Integer.parseInt(lesson.substring(7).trim());
////                            int[] maxProgressValues = z_Lesson_steps.getLessonSteps(lessonNumber);
////
////                            Log.e(TAG, "Module[" + (iteration) + "]");
////                            iteration++;
////
////                            Log.e(TAG, "maxProgressValues.length: " + maxProgressValues.length);
////                            int completeCounter = 0;
////                            for (int i = 0; i < maxProgressValues.length; i++) {
////                                String keyProgress = "M" + (i + 1) + ".Progress";
////                                String keyScore = "M" + (i + 1) + ".BKT Score";
////
////                                Long moduleProgress = lessonDoc.getLong(keyProgress);
////
////                                if (moduleProgress != null) {
////                                    totalProgress += moduleProgress;
////                                    totalMaxProgress += maxProgressValues[i];
////                                }
////
////                                Double moduleScore = lessonDoc.getDouble(keyScore); // Change this line
////                                //                            if (moduleProgress != null) {
////                                //
////                                //                            }
////
////                                Log.e(TAG, "Lesson[" + (i + 1) + "] BKT Score: " + moduleScore);
////                                Log.e(TAG, "Lesson[" + (i + 1) + "] Progress: " + moduleProgress);
////
////
////                                // if moduleScore >= passingGrade
////                                if (moduleScore >= b_main_0_menu_categorize_user.passingGrade) {
////
////                                    String module = "M" + (i + 1);
////                                    String lesson_1 = "Lesson " + (iteration - 1);
////
////                                    if (moduleProgress >= getLessonSteps(module, lesson_1)) {
////
////                                        Log.e("TAG", "HDMI | Module: M" + (i + 1));
////                                        Log.e("TAG", "HDMI | Lesson: Lesson " + (iteration - 1));
////
////                                        completeCounter++;
////                                        Log.e(TAG, "Lesson[" + (i + 1) + "] Passed | completeCounter: " + completeCounter);
////
////                                    }
////
////                                }
////
////                                moduleCounter++;
////                                final int progress = (int) ((moduleCounter / (float) totalModules) * 100);
////                                updateProgress(progress);
////
////                                if (completeCounter == maxProgressValues.length) {
////                                    Log.e("TAG", "Module[" + (iteration - 1) + "] is Complete!");
////
////
////                                    // Array starts at 0..
////                                    // Iteration started at 1..
////                                    // Iteration in this part of code is incremented already by 1..
////                                    // so deduct by 2..
////                                    cardCompletionStatus[iteration - 2] = true;
////                                    hideLockedOverlay(iteration);
////
////                                    completedModule++;
////
////                                }
////
////                            }
////
////                            if (totalMaxProgress > 0) {
////                                double overallProgress = ((double) totalProgress / totalMaxProgress) * 100;
////                                int overallProgressInt = (int) Math.round(overallProgress);
////
////                                updateCardProgress(lessonNumber, overallProgressInt);
////                            }
////
////                            Log.e("TAG", "WALTER | completedModule: " + completedModule);
//////                        maxProgressValues.length
////                            Log.e("TAG", "WALTER | Module Count: " + maxProgressValues.length);
////
////                        }
//
//
//
//                        // Add delay similar to progressive mode
//                        incrementLoadingProgressBar(loadingDialog.getLoadingProgressBar(), 3000, new Runnable() {
//                            @Override
//                            public void run() {
//
//                                // Use:
//                                // 1. requireContext()
//                                // 2. getActivity()
//
//    //                            if (!n_Network.isNetworkAvailable2(requireContext())) {
//    //                                Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();
//    //                            }
//
//                                hideLoadingDialog();
//                            }
//                        });
//                    } else {
//                        Log.d(TAG, "get failed with ", task.getException());
//                        hideLoadingDialog(); // Hide the dialog in case of failure as well
//                    }
//                }
//            });
//        }

    private int getLessonSteps(String module, String lesson) {

        int getLesson = Integer.parseInt(String.valueOf(lesson.charAt(7)));
        int getModule = Integer.parseInt(String.valueOf(module.charAt(1)));

        switch (getLesson) {
            case 1:
                switch (getModule) {
                    case 1: return L_lesson_sequence.getNumberOfSteps("M1_Lesson 1");
                    case 2: return L_lesson_sequence.getNumberOfSteps("M2_Lesson 1");
                    case 3: return L_lesson_sequence.getNumberOfSteps("M3_Lesson 1");
                    case 4: return L_lesson_sequence.getNumberOfSteps("M4_Lesson 1");
                }
            case 2:
                switch (getModule) {
                    case 1: return L_lesson_sequence.getNumberOfSteps("M1_Lesson 2");
                }
            case 3:
                switch (getModule) {
                    case 1: return L_lesson_sequence.getNumberOfSteps("M1_Lesson 3");
                    case 2: return L_lesson_sequence.getNumberOfSteps("M2_Lesson 3");
                }
            case 4:
                switch (getModule) {
                    case 1: return L_lesson_sequence.getNumberOfSteps("M1_Lesson 4");
                    case 2: return L_lesson_sequence.getNumberOfSteps("M2_Lesson 4");
                }
            case 5:
                switch (getModule) {
                    case 1: return L_lesson_sequence.getNumberOfSteps("M1_Lesson 5");
                    case 2: return L_lesson_sequence.getNumberOfSteps("M2_Lesson 5");
                    case 3: return L_lesson_sequence.getNumberOfSteps("M3_Lesson 5");
                }
            case 6:
                switch (getModule) {
                    case 1: return L_lesson_sequence.getNumberOfSteps("M1_Lesson 6");
                    case 2: return L_lesson_sequence.getNumberOfSteps("M2_Lesson 6");
                    case 3: return L_lesson_sequence.getNumberOfSteps("M3_Lesson 6");
                }
            case 7:
                switch (getModule) {
                    case 1: return L_lesson_sequence.getNumberOfSteps("M1_Lesson 7");
                }
            case 8:
                switch (getModule) {
                    case 1: return L_lesson_sequence.getNumberOfSteps("M1_Lesson 8");
                    case 2: return L_lesson_sequence.getNumberOfSteps("M2_Lesson 8");
                    case 3: return L_lesson_sequence.getNumberOfSteps("M3_Lesson 8");
                }
        }

        return 0;
    }

    private void resetCardProgress() {
        // Reset all progress to 0
        Arrays.fill(cardProgress, 0);
    }

    private boolean isModuleComplete(int cardId) {
        // Adjust cardId to match array index (1-based to 0-based)
//        cardId -= 1;

        Log.e("isModuleComplete", "checked Module[" + (cardId+1) + "]");

        switch (cardId) {
            case 0: // c_Lesson_progressive_1
                return areAllTrue(c_Lesson_progressive_1.cardCompletionStatus);
            case 1: // c_Lesson_progressive_2
                return areAllTrue(c_Lesson_progressive_2.cardCompletionStatus);
            case 2: // c_Lesson_progressive_3
                return areAllTrue(c_Lesson_progressive_3.cardCompletionStatus);
            case 3: // c_Lesson_progressive_4
                return areAllTrue(c_Lesson_progressive_4.cardCompletionStatus);
            case 4: // c_Lesson_progressive_5
                return areAllTrue(c_Lesson_progressive_5.cardCompletionStatus);
            case 5: // c_Lesson_progressive_6
                return areAllTrue(c_Lesson_progressive_6.cardCompletionStatus);
            case 6: // c_Lesson_progressive_7
                return areAllTrue(c_Lesson_progressive_7.cardCompletionStatus);
            case 7: // c_Lesson_progressive_8
                return areAllTrue(c_Lesson_progressive_8.cardCompletionStatus);
            default:
                return false; // Invalid cardId
        }

    }

    // Helper method to check if all values in the array are true
    private boolean areAllTrue(boolean[] completionStatus) {

        if (completionStatus == null) {
            return false; // Handle null case if necessary
        }

        int iteration = 1;
        for (boolean status : completionStatus) {
            Log.e("areAllTrue", "Lesson " + iteration + ": " + status);
            if (!status) {
                return false; // Return false if any status is false
            }
            iteration++;
        }

        Log.e("areAllTrue", "Card " + completionStatus + " are all true");

        return true; // All statuses are true
    }

    private void updateCardProgress(int cardId, int progress) {
        cardId -= 1;
        String TAG = "updateCardProgress()";
        cardProgress[cardId] = Math.min(progress, 100);

//        if (cardProgress[cardId] >= 100
////                && isModuleComplete(cardId)
//        ) {
//            Log.e("cardCompletionStatus[]", "cardProgress[" + (cardId-1) + "]: " + cardProgress[cardId] + " >= 100, so TRUE na to");
//            cardCompletionStatus[cardId] = true;
//            if (cardId < cardCompletionStatus.length && cardCompletionStatus[cardId]) {
//                hideLockedOverlay(cardId + 2);
//            }
//        }

        cardId += 1;
        ProgressBar progressBar = view.findViewById(getProgressBarId(cardId));
        TextView progressText = view.findViewById(getProgressTextId(cardId));
        cardId -= 1;

        progressBar.setProgress(cardProgress[cardId]);
        progressText.setText(cardProgress[cardId] + "% Completed");
    }

    private void initializeViews() {
        // Progress Bar
        progressBarCard1 = view.findViewById(R.id.progressive_progressbar_lesson_1);
        progressBarCard2 = view.findViewById(R.id.progressive_progressbar_lesson_2);
        progressBarCard3 = view.findViewById(R.id.progressive_progressbar_lesson_3);
        progressBarCard4 = view.findViewById(R.id.progressive_progressbar_lesson_4);
        progressBarCard5 = view.findViewById(R.id.progressive_progressbar_lesson_5);
        progressBarCard6 = view.findViewById(R.id.progressive_progressbar_lesson_6);
        progressBarCard7 = view.findViewById(R.id.progressive_progressbar_lesson_7);
        progressBarCard8 = view.findViewById(R.id.progressive_progressbar_lesson_8);

        // Progress Text
        progressTextCard1 = view.findViewById(R.id.progressive_progresstext_lesson_1);
        progressTextCard2 = view.findViewById(R.id.progressive_progresstext_lesson_2);
        progressTextCard3 = view.findViewById(R.id.progressive_progresstext_lesson_3);
        progressTextCard4 = view.findViewById(R.id.progressive_progresstext_lesson_4);
        progressTextCard5 = view.findViewById(R.id.progressive_progresstext_lesson_5);
        progressTextCard6 = view.findViewById(R.id.progressive_progresstext_lesson_6);
        progressTextCard7 = view.findViewById(R.id.progressive_progresstext_lesson_7);
        progressTextCard8 = view.findViewById(R.id.progressive_progresstext_lesson_8);

        // Locked Overlay
        lockedOverlayCard1 = view.findViewById(R.id.card1_locked_overlay);
        lockedOverlayCard2 = view.findViewById(R.id.card2_locked_overlay);
        lockedOverlayCard3 = view.findViewById(R.id.card3_locked_overlay);
        lockedOverlayCard4 = view.findViewById(R.id.card4_locked_overlay);
        lockedOverlayCard5 = view.findViewById(R.id.card5_locked_overlay);
        lockedOverlayCard6 = view.findViewById(R.id.card6_locked_overlay);
        lockedOverlayCard7 = view.findViewById(R.id.card7_locked_overlay);
        lockedOverlayCard8 = view.findViewById(R.id.card8_locked_overlay);

        setCardClickListeners();
    }

    // Method to set click listeners for the cards
    private void setCardClickListeners() {
        view.findViewById(R.id.card1).setOnClickListener(v -> handleCardClick(1));
        view.findViewById(R.id.card2).setOnClickListener(v -> handleCardClick(2));
        view.findViewById(R.id.card3).setOnClickListener(v -> handleCardClick(3));
        view.findViewById(R.id.card4).setOnClickListener(v -> handleCardClick(4));
        view.findViewById(R.id.card5).setOnClickListener(v -> handleCardClick(5));
        view.findViewById(R.id.card6).setOnClickListener(v -> handleCardClick(6));
        view.findViewById(R.id.card7).setOnClickListener(v -> handleCardClick(7));
        view.findViewById(R.id.card8).setOnClickListener(v -> handleCardClick(8));
    }

    private void handleCardClick(int cardId) {
//        if (network.isNetworkConnected(getActivity())) {
//            showToast("Please connect to internet first");
//            return;
//        }
        if (!n_Network.isNetworkAvailable(getContext())) {
            showToast("Please connect to a network.");
        } else {
            if (isPreviousCardCompleted(cardId)) {
                launchLessonActivity(cardId);
            } else {
                showCustomDialog();
            }
        }

    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_complete_previous_lesson, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        Button okayButton = dialogView.findViewById(R.id.okay_button);
        okayButton.setOnClickListener(v -> dialog.dismiss());

        Button exitButton = dialogView.findViewById(R.id.exit_button);
        exitButton.setOnClickListener(v -> dialog.dismiss());

//        dialog.setOnShowListener(dialogInterface -> {
//            int width = (int) (350 * getResources().getDisplayMetrics().density);
//            int height = (int) (350 * getResources().getDisplayMetrics().density);
//            dialog.getWindow().setLayout(width, height);
//        });

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
            case 5:
                intent = new Intent(getActivity(), c_Lesson_progressive_5.class);
                break;
            case 6:
                intent = new Intent(getActivity(), c_Lesson_progressive_6.class);
                break;
            case 7:
                intent = new Intent(getActivity(), c_Lesson_progressive_7.class);
                break;
            case 8:
                intent = new Intent(getActivity(), c_Lesson_progressive_8.class);
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
            case 5:
                return R.id.progressive_progressbar_lesson_5;
            case 6:
                return R.id.progressive_progressbar_lesson_6;
            case 7:
                return R.id.progressive_progressbar_lesson_7;
            case 8:
                return R.id.progressive_progressbar_lesson_8;
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
            case 5:
                return R.id.progressive_progresstext_lesson_5;
            case 6:
                return R.id.progressive_progresstext_lesson_6;
            case 7:
                return R.id.progressive_progresstext_lesson_7;
            case 8:
                return R.id.progressive_progresstext_lesson_8;
            default:
                return 0;
        }
    }

    private void hideLockedOverlay(int cardId) {

        cardId+=2;

        Log.e("TAG", "GOZ | hideLockedOverlay(" + cardId + ")");

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
            case 5:
                return R.id.card5_locked_overlay;
            case 6:
                return R.id.card6_locked_overlay;
            case 7:
                return R.id.card7_locked_overlay;
            case 8:
                return R.id.card8_locked_overlay;
            default:
                return -1;
        }
    }
}