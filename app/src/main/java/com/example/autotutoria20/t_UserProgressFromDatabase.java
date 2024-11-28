package com.example.autotutoria20;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class t_UserProgressFromDatabase {

    static int number_Of_Lessons = 0;
    private static final String TAG = "ProgressDataManager";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    static String failedLesson = null;
    static int failedModule = 0;
    static Double failedBKTScore = 0.0;
    static Boolean isFirstFailDetected = false;

    static Map<String, Object> lessonData = new HashMap<>();
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
                        moduleCountInLesson++;

                        Log.i(TAG, "MANGO | progressPercentage: " + progressPercentage);
                        Log.i(TAG, "Module Progress: Module " + moduleKey + " in Lesson " + lessonNumber
                                            + " - Progress: " + progressPercentage + "%");

                        Log.i(TAG, "50 PLUS | isFirstFailDetected: " + isFirstFailDetected);

                        if (!isFirstFailDetected && (progressPercentage < 100) ) {
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



}
