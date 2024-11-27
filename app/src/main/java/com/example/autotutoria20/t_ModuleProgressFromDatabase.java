//package com.example.autotutoria20;
//
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QuerySnapshot;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.atomic.AtomicInteger;
//
//public class t_ModuleProgressFromDatabase {
//
//    static Map<String, Map<String, Object>> progressive_Data = new HashMap<>();
//    static String TAG = "fetchModuleProgress()";
//
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
//                    double totalAverageBKTScore = 0.0;
//                    int lessonCount = 0;
//                    int moduleCount = 0;
//                    int moduleCounter = 0;
//                    AtomicInteger totalCompletedModules = new AtomicInteger(0); // Track the number of fully completed modules
//
//                    for (DocumentSnapshot lessonDoc : task.getResult()) {
//
//                        moduleCount++;
//
//                        String lesson = lessonDoc.getId();
//                        int totalProgress = 0;
//                        int totalMaxProgress = 0;
//                        int lessonNumber = Integer.parseInt(lesson.substring(7).trim());
//                        int[] maxProgressValues = z_Lesson_steps.getLessonSteps(lessonNumber);
//                        totalModules += maxProgressValues.length;
//                        int completeCounter = 0;
//
//                        double totalBKTScore = 0.0;
//                        int bktScoreCount = 0;
//
//                        Map<String, Object> lessonProgress;
//                        for (int i = 0; i < maxProgressValues.length; i++) {
//
//                            lessonProgress = new HashMap<>();
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
//                        }
//
//                        if (completeCounter == maxProgressValues.length) {
//                            Log.e("TAG", "Module[" + lessonNumber + "] is Complete!");
//
//                            totalCompletedModules.incrementAndGet(); // Increment AtomicInteger
//
//                        } else {
//                            //Module is not yet passed
////                            c_Lesson_feedback.showModuleFailed(requireContext(), "Module " + lessonNumber);
//                        }
//
//                        double averageBKTScore = bktScoreCount > 0 ? totalBKTScore / bktScoreCount : 0.0;
//                        Log.e(TAG, "Average BKT Score for Lesson " + lessonNumber + ": " + averageBKTScore);
//
//                        totalAverageBKTScore += averageBKTScore;
//                        lessonCount++;
//
//                        int overallProgressInt = 0;
//                        if (totalMaxProgress > 0) {
//                            double overallProgress = ((double) totalProgress / totalMaxProgress) * 100;
//                            overallProgressInt = (int) Math.round(overallProgress);
////                            updateCardProgress(lessonNumber, overallProgressInt);
//                        }
//
//                        Log.i("TAG", "WALTER | updateCardProgress("+lessonNumber+", "+overallProgressInt+");");
//                        Log.e("TAG", "WALTER | completedModule: " + totalCompletedModules.get());
//                        Log.e("TAG", "WALTER | Module Count: " + maxProgressValues.length);
//                    }
//
//                    double overallAverageBKTScore = lessonCount > 0 ? totalAverageBKTScore / lessonCount : 0.0;
//                    overallAverageBKTScore *= 100;
//
//                    Log.e("fetchAllProgressData()", String.format("Overall Average BKT Score for All Lessons: %.2f%%", overallAverageBKTScore));
//
//                    String newCategory = getUserCategory(overallAverageBKTScore);
//                    Log.e("TAG", "WALTER | New Category: " + newCategory);
//
//
//                    incrementLoadingProgressBar(loadingDialog.getLoadingProgressBar(), 2000, new Runnable() {
//                        @Override
//                        public void run() {
//                            hideLoadingDialog();
//
//                            // Show the dialog only if all modules are complete
//
//                            // 8 or how do I make this dynamic??
//                            // Optionally, use moduleCount, since it will always be equal to total modules
//                            // concern is baka mag read sya na always true
//                            if (totalCompletedModules.get() == 8) { // Get value from AtomicInteger
//                                setCompletedStatus(true);
////                                showToast("show change category dialog");
//                                showChangeCategoryDialog(newCategory);
//                            }
//
//                        }
//                    });
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                    hideLoadingDialog();
//                }
//            }
//        });
//    }
//
//    static void fetchModuleProgress(String learningMode) {
//
//        Log.i(TAG, "Fetching module progress");
//
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//        CollectionReference progressRef = db.collection("users")
//                .document(userId)
//                .collection(learningMode);
//
//        progressRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (DocumentSnapshot lessonDoc : task.getResult()) {
//                        String lessonName = lessonDoc.getId();
//                        Map<String, Object> lessonData = lessonDoc.getData();
//                        progressive_Data.put(lessonName, lessonData);
//                        Log.d(TAG, "Data for " + lessonName + ": " + lessonData);
//                    }
//
////                    // Call a method to update the progressive class
////                    b_main_1_lesson_progressive.updateProgressiveData(progressive_Data);
//                } else {
//                    Log.d(TAG, "Error getting documents: ", task.getException());
//                }
//            }
//        });
//    }
//
//}
