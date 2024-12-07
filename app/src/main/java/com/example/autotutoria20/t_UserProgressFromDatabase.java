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

import java.sql.Struct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class t_UserProgressFromDatabase {

    static int number_Of_Lessons = 0;
    static boolean hasWeakPoint;
    private static final String TAG = "ProgressDataManager";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//    public static boolean[] moduleHasFail = new boolean[8];
    public static ArrayList<Boolean> moduleHasFail = new ArrayList<>();
    static String failedLesson = null;
    static int failedModule = 0;
    static Double failedBKTScore = 0.0;
    static Boolean isFirstFailDetected = false;

    static Map<String, Object> lessonData = new HashMap<>();
    static Map<String, Map<String, Map<String, Object>>> nestedFeedback = new HashMap<>();

    static Map<String, Object> moduleScore = new HashMap<>();
    static Map<String, Object> moduleProgress_Progressive = new HashMap<>();
    static Map<String, Object> moduleProgress_FreeUse = new HashMap<>();
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

    public void fetchAllProgressData(String learningMode, ProgressDataListener listener) {
        if (userId == null) {
            listener.onError(new Exception("User not authenticated"));
            return;
        }

        CollectionReference progressRef =
                db.collection("users")
                .document(userId)
                .collection(learningMode);

        progressRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                moduleCount = 0;
                int totalModules = 0;
                int totalMaxProgress = 0;
                AtomicInteger completedModules = new AtomicInteger(0);
                String category = null;

                for (DocumentSnapshot lessonDoc : task.getResult()) {
                    Log.i(TAG, TAG + " | lessonDoc: " + lessonDoc);
                    Map<String, Object> lessonData = lessonDoc.getData();
                    if (lessonData == null) continue;

                    int lessonNumber = ++moduleCount;
                    double totalBKTScore = 0.0;
                    double totalProgress = 0;
                    int moduleCountInLesson = 0;

                    Long progress;
                    Number bktScore = null;

                    int moduleNumber = 1;

                    Log.i(TAG, TAG + "lessonData size: " + lessonData.size() + " | lessonData: " + lessonData);
                    for (String moduleKey : lessonData.keySet()) {

                        Log.i(TAG, TAG + " | moduleNumber: " + moduleNumber + " | moduleKey: M" + moduleNumber);
                        if (moduleKey.contains("M" + moduleNumber)) {

                            moduleNumber++;

                            Map<String, Object> moduleData = (Map<String, Object>) lessonData.get(moduleKey);
                            Log.i(TAG, TAG + " | moduleKey: " + moduleKey + " | moduleData: " + moduleData);

                            if (moduleData == null) {
                                Log.i(TAG, TAG + " | moduleData == null");
                                continue;
                            }

                            progress = (Long) moduleData.get("Progress");

                            if (progress == null) {
                                Log.i(TAG, TAG + " | Progress == null");
                                progress = (long) 0.0;
                                Log.i(TAG, TAG + " | Progress = 0.0");
                                // Comment out continue, because it is not retrieving it.
//                                continue;
                            }


                            Number tempPreTestBKTScore = (Number) moduleData.get("Pre-Test BKT Score");
                            Number tempBKTScore = (Number) moduleData.get("BKT Score");

                            // Check for Post-Test BKT Score, fallback to BKT Score if it's zero or null
                            if (moduleData.containsKey("Post-Test BKT Score") && moduleData.get("Post-Test BKT Score") instanceof Number) {
                                bktScore = (Number) moduleData.get("Post-Test BKT Score");
                                if (bktScore != null && bktScore.doubleValue() == 0) {
                                        bktScore = (Number) moduleData.get("BKT Score");
                                }
                            } else if (moduleData.containsKey("BKT Score")) {
                                if (!Objects.equals(tempPreTestBKTScore, tempBKTScore)) // BKT Score is also Pre-Test BKT Score
                                    bktScore = tempBKTScore;
                            }

                            // Convert to percentage
                            double bktScoreValue = (bktScore != null) ? (bktScore.doubleValue() * 100) : 0.0;

                            Log.i(TAG, "Converted BKT Score Value: " + bktScoreValue);

                            String lessonKey = moduleKey + "_Lesson " + lessonNumber;

                            Log.i(TAG, TAG  + " | lessonKey: " + lessonKey);
                            int maxProgress = t_LessonSequenceFromDatabase.getNumberOfSteps(lessonKey);

                            if (maxProgress <= 0)
                                continue;

                            // Progress of the current Lesson being iterated
                            // M1 : M2 : M3 : M4 Progress Percentage
                            int progressPercentage = (int) ((progress / (double) maxProgress) * 100);

                            // totalProgress should be divided:
                            // Module Progress = totalProgress / moduleCountInLesson
                            totalProgress += progressPercentage;
                            totalBKTScore += bktScoreValue;
                            moduleCountInLesson++;

                            if ((progress >= maxProgress)) {

                                String feedback =
                                        t_SystemInterventionCategory
                                                .getWeakPointFeedback
                                                        (bktScoreValue
                                                        );

                                String module = "Module " + lessonNumber;
                                String lesson = "Lesson " + moduleKey.charAt(1);

                                if (feedback.contains("Weak") || feedback.contains("Minimal"))
                                    addFeedback(nestedFeedback, module, lesson, bktScoreValue, feedback);

                                if (!isFirstFailDetected) { // No Fail yet..

                                    if (!(progressPercentage < 100)) { // Module is completed

                                        if (bktScoreValue < 60) { // Module is completed and failed

                                            isFirstFailDetected = true;
                                            failedLesson = "Module " + lessonNumber;
                                            failedModule = Integer.parseInt(String.valueOf(moduleKey.charAt(1)));

                                            // Update the failure status in the pre-initialized list
//                                            moduleHasFail.set(lessonNumber - 1, true);

                                            Log.i(TAG, TAG + " | YAKULT | bktScore: " + bktScore);
                                            Log.i(TAG, TAG + " | YAKULT | bktScoreValue: " + bktScoreValue);

                                            failedBKTScore = bktScore != null ? bktScoreValue : 0.0;

                                            Log.i(TAG, TAG + " | YAKULT | isFirstFailDetected: " + isFirstFailDetected);
                                            Log.i(TAG, TAG + " | YAKULT | failedLesson: " + failedLesson);
                                            Log.i(TAG, TAG + " | YAKULT | failedBKTScore: " + failedBKTScore);
                                        } else {
                                            // Module progress is complete and passed
                                        }
                                    } else {
                                        // Module is incomplete
                                    }

                                    // Ensure that non-failed modules are marked appropriately
//                                    moduleHasFail.set(lessonNumber - 1, false);
                                }


                                if (progress >= maxProgress) {
                                    completedModules.incrementAndGet();
                                }

                            } // end of if (progress >= maxProgress)
                        }
                        Log.i(TAG, TAG + " | moduleNumber incremented: " + moduleNumber);
                    } // End for for each loop

                    String moduleKey = "Module " + lessonNumber;

                    // Calculate the average BKT score for the lesson
                    double averageBKTScore = moduleCountInLesson > 0
                            ? totalBKTScore / moduleCountInLesson
                            : 0.0;
                    moduleScore.put(moduleKey, averageBKTScore);

                    Log.i("WIPES", "WIPES | totalProgress: " + totalProgress);
                    Log.i("WIPES", "WIPES | moduleCountInLesson: " + moduleCountInLesson);

                    double averageProgress = moduleCountInLesson > 0
                            ? totalProgress / moduleCountInLesson
                            : 0;

                    Log.i("WIPES", "WIPES | averageProgress: " + averageProgress);

                    if (learningMode.equalsIgnoreCase("Progressive Mode"))
                        moduleProgress_Progressive.put(moduleKey, averageProgress);
                    else if (learningMode.equalsIgnoreCase("Free Use Mode"))
                        moduleProgress_FreeUse.put(moduleKey, averageProgress);

                    moduleBoolean.put(moduleKey, averageProgress >= 100);
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
            moduleTitle.setText("" + module);
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
                lessonTitle.setText("\t\t" + lesson);
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
                bktScoreView.setText("\t\t" + String.format("%.2f", bktScore));
                bktScoreView.setTextColor(ContextCompat.getColor(context, R.color.white));
                bktScoreView.setTypeface(customFont);
                bktScoreView.setTextSize(20);
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
        recommendationMessage.setTextSize(20);
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
