package com.example.autotutoria20;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Typeface;
import androidx.core.content.res.ResourcesCompat;


import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

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
import java.util.concurrent.atomic.AtomicInteger;

public class t_UserProgressFromDatabase {

    static int number_Of_Lessons = 0;
    static boolean hasWeakPoint;
    private static final String TAG = "ProgressDataManager";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    static String failedLesson = null;
    static int failedModule = 0;
    static Double failedBKTScore = 0.0;
    static Boolean isFirstFailDetected = false;

    static Map<String, Object> lessonData = new HashMap<>();
    static Map<String, Map<String, Map<String, Object>>> nestedFeedback = new HashMap<>();

    static Map<String, Object> moduleScore = new HashMap<>();
    static Map<String, Object> moduleProgress = new HashMap<>();
    static Map<String, Boolean> moduleBoolean = new HashMap<>();
    static int moduleCount = 0;
    public interface ProgressDataListener {
        void onProgressUpdated(int progress);

        void onDataFetched(String category, boolean allModulesComplete);

        void onError(Exception e);
    }

    public void ProgressDataManager() {
        db = FirebaseFirestore.getInstance();
        userId = FirebaseAuth.getInstance().getCurrentUser() != null
                ? FirebaseAuth.getInstance().getCurrentUser().getUid()
                : null;
    }

//    public void fetchAllProgressData(ProgressDataListener listener) {
//        if (userId == null) {
//            listener.onError(new Exception("User not authenticated"));
//            return;
//        }
//
//        CollectionReference progressRef = db.collection("users").document(userId).collection("Progressive Mode");
//
//        progressRef.get().addOnCompleteListener(task -> {
//
//            if (task.isSuccessful() && task.getResult() != null) {
//                int totalModules = 0;
//                int totalMaxProgress = 0;
//                AtomicInteger completedModules = new AtomicInteger(0);
//
//                String category = null;
//                int lessonNumber = 0;
//
//                moduleCount = 0;
//                for (DocumentSnapshot lessonDoc : task.getResult()) { // Iterate through "Lesson n" documents
//                    lessonNumber++;
//                    Map<String, Object> lessonData = lessonDoc.getData(); // Retrieve all fields in the document
//
//                    if (lessonData != null) {
//                        Log.i(TAG, "Processing Lesson " + lessonNumber);
//
//                        int totalProgress = 0; // Sum of all module progress
//                        int moduleCount = 0;   // Number of modules processed
//
//                        for (String moduleKey : lessonData.keySet()) { // Iterate through modules (e.g., "M1", "M2")
//                            Map<String, Object> moduleData = (Map<String, Object>) lessonData.get(moduleKey); // Get data for each module
//                            if (moduleData != null) {
//                                Long progress = (Long) moduleData.get("Progress"); // Retrieve progress value
//                                Number bktScore = (Number) moduleData.get("BKT Score");
//                                int maxProgress = lessonData.size(); // Placeholder logic for max progress
//
//                                Log.i(TAG, "Processing Module: " + moduleKey + " in Lesson " + lessonNumber);
//                                Log.i(TAG, "BKT Score: " + bktScore);
//
//                                if (progress != null) {
//                                    totalModules++;
//                                    totalMaxProgress += maxProgress;
//
//                                    String maxProgressKey = moduleKey + "_Lesson " + lessonNumber;
//                                    Log.i(TAG, "maxProgressKey: " + maxProgressKey);
//                                    int max_module_progress = t_LessonSequenceFromDatabase.getNumberOfSteps(maxProgressKey);
//                                    if (max_module_progress == 0) {
//                                        Log.e(TAG, "Max module progress is zero for key: " + moduleKey);
//                                        continue; // Skip invalid data
//                                    }
//
//                                    // Calculate progress percentage for the module
//                                    int progressPercentage = (int) ((progress / (double) max_module_progress) * 100);
//                                    totalProgress += progressPercentage; // Add progress to total
//                                    moduleCount++; // Increment module count
//
//                                    Log.i(TAG, "Module Progress: Module " + moduleKey + " in Lesson " + lessonNumber
//                                            + " - Progress: " + progressPercentage + "%");
//
//                                    // moduleKey = M1 -> M4
//                                    // lessonNumber = 1 -> 8
//
//                                    if (!isFirstFailDetected && progressPercentage < 100) {
//                                        isFirstFailDetected = true;
//                                        failedLesson = "Module " + lessonNumber;
//                                        failedModule = Integer.parseInt(String.valueOf(moduleKey.charAt(1)));
//
//                                        // Safely handle the `bktScore` as both Long and Double
//                                        if (bktScore instanceof Long) {
//                                            failedBKTScore = ((Long) bktScore).doubleValue();
//                                            Log.i(TAG, "Long | BKT Score: " + bktScore);
//                                        } else if (bktScore instanceof Double) {
//                                            failedBKTScore = (Double) bktScore;
//                                            Log.i(TAG, "Double | BKT Score: " + bktScore);
//                                        } else {
//                                            Log.e(TAG, "Unexpected BKT Score type: " + bktScore.getClass());
//                                            failedBKTScore = 0.0; // Default to 0.0 in case of an unexpected type
//                                        }
//
//                                        Log.i(TAG, "Module " + moduleKey.charAt(1) + " | Lesson " + lessonNumber + " failed! | " + failedBKTScore);
//                                    }
//
//                                    if (progress >= maxProgress) {
//                                        completedModules.incrementAndGet(); // Count completed modules
//                                    }
//                                }
//                            }
//                        }
//
//                        // Compute the average progress
//                        int averageProgress = moduleCount > 0 ? totalProgress / moduleCount : 0;
//                        Boolean isModuleFinished = averageProgress >= 100;
//                        Log.i("JOLLIBEE", "Module Total Progress: " + averageProgress);
//
//                        Log.e("JOLLIBEE", "moduleProgress.put(Lesson " + lessonNumber + ", " + averageProgress + ");");
//                        moduleProgress.put("Module " + lessonNumber, averageProgress);
//                        moduleBoolean.put("Module " + lessonNumber, isModuleFinished);
//
//                    }
//                    moduleCount++;
//                }
//
//                boolean allModulesComplete = completedModules.get() == totalModules;
//                double overallProgress = totalMaxProgress > 0
//                        ? (completedModules.get() / (double) totalModules) * 100 : 0;
//
//                listener.onProgressUpdated((int) overallProgress);
//                listener.onDataFetched(category, allModulesComplete);
//
//            } else {
//                listener.onError(task.getException());
//            }
//        });
//    }

    public void fetchAllProgressData(ProgressDataListener listener) {
        if (userId == null) {
            listener.onError(new Exception("User not authenticated"));
            return;
        }

        CollectionReference progressRef = db.collection("users").document(userId).collection("Progressive Mode");

        progressRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                moduleCount = 0;
                int totalModules = 0;
                int totalMaxProgress = 0;
                AtomicInteger completedModules = new AtomicInteger(0);
                String category = null;

                for (DocumentSnapshot lessonDoc : task.getResult()) {
                    Map<String, Object> lessonData = lessonDoc.getData();
                    if (lessonData == null) continue;

                    int lessonNumber = ++moduleCount;
                    double totalBKTScore = 0.0;
                    int totalProgress = 0;
                    int moduleCountInLesson = 0;

                    Long progress;
                    Number bktScore;

                    for (String moduleKey : lessonData.keySet()) {
                        Map<String, Object> moduleData = (Map<String, Object>) lessonData.get(moduleKey);
                        if (moduleData == null) continue;

                        progress = (Long) moduleData.get("Progress");
                        bktScore = (Number) moduleData.get("BKT Score");

                        Log.i(TAG, "CCS | I retrieved bktScore: " + bktScore);

                        double bktScoreValue;

                        bktScoreValue = (bktScore != null) ? bktScore.doubleValue()*100 : 0.0;

                        Log.i(TAG, "Converted BKT Score Value: " + bktScoreValue);

                        if (progress == null) continue;

                        int maxProgress = t_LessonSequenceFromDatabase.getNumberOfSteps(moduleKey + "_Lesson " + lessonNumber);
                        if (maxProgress <= 0) continue;

                        int progressPercentage = (int) ((progress / (double) maxProgress) * 100);

                        totalProgress += progressPercentage;
                        totalBKTScore += bktScoreValue;
                        moduleCountInLesson++;

                        // Determine feedback based on BKT score and user category
                        String feedback = t_SystemInterventionCategory.getWeakPointFeedback(bktScoreValue, b_main_0_menu_categorize_user.category);
                        Log.i(TAG, "Module " + moduleKey + " | Lesson " + lessonNumber + " | " + bktScoreValue + " | " + feedback);

                        // if feedback contains "weak"
                        // add it to the nested map nestedFeedback

                        String module = "Module " + moduleKey.charAt(1);
                        String lesson = "Lesson " + lessonNumber;

                        if (feedback.contains("weak")) {
                            addFeedback(nestedFeedback, module, lesson, bktScoreValue, feedback);
                            Log.i(TAG, "Weak Point detected at: " +
                                    "\n" + module + " | " + lesson + " |\n" + bktScoreValue + " | " + feedback);
                        }




                        Log.i(TAG, "MANGO | progressPercentage: " + progressPercentage);
                        Log.i(TAG, "MANGO | Module Progress: Module " + moduleKey + " in Lesson " + lessonNumber
                                            + " - Progress: " + progressPercentage + "%");
                        Log.i(TAG, "MANGO | BKT Score: " + bktScoreValue);

                        Log.i(TAG, "50 PLUS | isFirstFailDetected: " + isFirstFailDetected);

                        if (!isFirstFailDetected && !(progressPercentage < 100) ) {
                            Log.i(TAG, "MANGO | inside if statement");


                            Log.i(TAG, "CCS | bktScore: " + bktScore);
                            Log.i(TAG, "CCS | bktScoreValue: " + bktScoreValue);
                            Log.i(TAG, "CCS | progressPercentage: " + progressPercentage);

                            if ((bktScoreValue < 60)) {
                                // Module is marked as failed only if it has progress > 0 and BKT score < 60
                                Log.i(TAG, "MANGO | this one failed..");
                                isFirstFailDetected = true;
                                failedLesson = "Module " + lessonNumber;
                                failedModule = Integer.parseInt(String.valueOf(moduleKey.charAt(1)));
                                failedBKTScore = bktScore != null ? bktScore.doubleValue() : 0.0;

                                Log.i(TAG, "MANGO | failedLesson: " + failedLesson);
                                Log.i(TAG, "MANGO | failedModule: " + failedModule);

                                Log.i(TAG, "MAMA MO PASADO | failedBKTScore: " + failedBKTScore);
                                Log.i("ProgressCheck", "First failure detected for module.");
                            } else {
                                // Module progress is incomplete but not failed
                                Log.i("ProgressCheck", "Module is incomplete but not failed. BKT Score: " + bktScoreValue);
                            }


                        }

                        if (progress >= maxProgress) {
                            completedModules.incrementAndGet();
                        }
                    }

                    // Calculate the average BKT score for the lesson
                    double averageBKTScore = moduleCountInLesson > 0 ? totalBKTScore / moduleCountInLesson : 0.0;
                    moduleScore.put("Module " + lessonNumber, averageBKTScore);

                    int averageProgress = moduleCountInLesson > 0 ? totalProgress / moduleCountInLesson : 0;
                    moduleProgress.put("Module " + lessonNumber, averageProgress);
                    moduleBoolean.put("Module " + lessonNumber, averageProgress >= 100);
                }

                listener.onProgressUpdated((int) ((completedModules.get() / (double) totalModules) * 100));
                listener.onDataFetched(category, completedModules.get() == totalModules);

            } else {
                listener.onError(task.getException());
            }
        });
    }

    public static void addFeedback(Map<String, Map<String, Map<String, Object>>> nestedFeedback,
                                   String module, String lesson, double bktScore, String feedback) {

        hasWeakPoint = true;

        // Get or create the module map
        nestedFeedback.putIfAbsent(module, new HashMap<>());

        // Get or create the lesson map within the module
        nestedFeedback.get(module).putIfAbsent(lesson, new HashMap<>());

        // Add the BKT score and feedback to the lesson map
        Map<String, Object> lessonDetails = nestedFeedback.get(module).get(lesson);
        lessonDetails.put("BKT Score", bktScore);
        lessonDetails.put("Feedback", feedback);
    }

    public static void displayFeedback(Context context, Map<String, Map<String, Map<String, Object>>> nestedFeedback) {
        // Logs for debugging
        List<String> sortedModules = new ArrayList<>(nestedFeedback.keySet());
        Collections.sort(sortedModules);  // Sort the modules alphabetically or numerically

        // Inflate the custom layout for the dialog
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.feedback_dialog, null);

        // Find the scrollable container in the layout
        LinearLayout feedbackContainer = dialogView.findViewById(R.id.feedback_container);

        // Load the custom font from the resources
        Typeface customFont = ResourcesCompat.getFont(context, R.font.garfiey);

        // Populate the feedback container with details from nestedFeedback
        for (String module : sortedModules) {  // Iterate over sorted modules
            // Add Module title
            TextView moduleTitle = new TextView(context);
            moduleTitle.setText("Module: " + module);
            moduleTitle.setTypeface(null, Typeface.BOLD);  // This sets the text style to bold
            moduleTitle.setTextColor(ContextCompat.getColor(context, R.color.white));
            moduleTitle.setTypeface(customFont);
            moduleTitle.setTextSize(26);
            moduleTitle.setPadding(16, 16, 16, 8);
            feedbackContainer.addView(moduleTitle);

            Map<String, Map<String, Object>> lessons = nestedFeedback.get(module);
            // Sort lessons by lesson name (if applicable)
            List<String> sortedLessons = new ArrayList<>(lessons.keySet());
            Collections.sort(sortedLessons);  // Sort lessons alphabetically or numerically

            for (String lesson : sortedLessons) {  // Iterate over sorted lessons
                // Add Lesson title
                TextView lessonTitle = new TextView(context);
                lessonTitle.setText("\tLesson: " + lesson);
                lessonTitle.setTextColor(ContextCompat.getColor(context, R.color.white));
                lessonTitle.setTypeface(customFont);
                lessonTitle.setTextSize(20);
                lessonTitle.setPadding(16, 8, 16, 4);
                feedbackContainer.addView(lessonTitle);

                Map<String, Object> lessonDetails = lessons.get(lesson);

                // Add BKT Score
                TextView bktScoreView = new TextView(context);
                // Assuming lessonDetails.get("BKT Score") returns a Double
                Double bktScore = (Double) lessonDetails.get("BKT Score");  // Retrieve the BKT Score
                bktScoreView.setText("\t\tBKT Score: " + String.format("%.2f", bktScore));
                bktScoreView.setTextColor(ContextCompat.getColor(context, R.color.white));
                bktScoreView.setTypeface(customFont);
                bktScoreView.setTextSize(22);
                bktScoreView.setPadding(16, 4, 16, 2);
                feedbackContainer.addView(bktScoreView);

//                // Add Feedback
//                TextView feedbackView = new TextView(context);
//                feedbackView.setText("    Feedback: " + lessonDetails.get("Feedback"));
//                feedbackView.setTextColor(ContextCompat.getColor(context, R.color.white));
//                feedbackView.setTypeface(customFont);
//                feedbackView.setPadding(16, 2, 16, 16);
//                feedbackContainer.addView(feedbackView);

            }
        }

        // Add recommendation message
        TextView recommendationMessage = new TextView(context);
        recommendationMessage.setText("\nRecommendation: Retake the following modules and lessons for improvement.");
        recommendationMessage.setTextColor(ContextCompat.getColor(context, R.color.white));
        recommendationMessage.setTypeface(customFont);
        recommendationMessage.setTextSize(16);
        recommendationMessage.setPadding(16, 16, 16, 16);
        feedbackContainer.addView(recommendationMessage);

        // Create and show the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);

        // Add OK button to dismiss the dialog
        Button okayButton = dialogView.findViewById(R.id.okay_button);
        AlertDialog dialog = builder.create();
        okayButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }




}
