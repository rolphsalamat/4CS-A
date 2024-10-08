package com.example.autotutoria20;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class c_Lesson_a_retrieveScore {
    public static List<Double> bktScores = new ArrayList<>(); // List to hold BKT Scores

    public static void fetchModuleProgress(String collectionPath, String lessonKey) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference progressRef = db.collection("users")
                .document(userId)
                .collection(collectionPath)
                .document(lessonKey);

        // Retrieve progress for the specified lesson
        progressRef.get().addOnCompleteListener(task -> {
            String TAG = "TANGINAMO HANS";
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Map<String, Object> progressData = document.getData();
                    if (progressData != null) {
                        // Iterate over each module key in the progress data
                        for (String moduleKey : progressData.keySet()) {
                            Object value = progressData.get(moduleKey);

                            // Ensure the value is a Map and contains the "BKT Score" key
                            if (value instanceof Map) {
                                Map<String, Object> moduleData = (Map<String, Object>) value;

                                // Inside fetchModuleProgress method
                                if (moduleData.containsKey("BKT Score")) {
                                    Object progressValue = moduleData.get("BKT Score");

                                    // Try to cast to Long first, then catch for Double
                                    try {
                                        double score = ((Long) progressValue).doubleValue(); // Store score as double
                                        addOrUpdateScore(moduleKey, score); // Add or update score in the list
                                        Log.d(TAG, moduleKey + "_" + lessonKey + " | BKT Score: " + score);
                                    } catch (ClassCastException e) {
                                        // If it's not a Long, try casting it to Double
                                        try {
                                            double score = (Double) progressValue; // Store score as double
                                            addOrUpdateScore(moduleKey, score); // Add or update score in the list
                                            Log.d(TAG, moduleKey + "_" + lessonKey + " | BKT Score: " + score);
                                        } catch (ClassCastException e2) {
                                            Log.e(TAG, "BKT Score value is neither Long nor Double for module: " + moduleKey);
                                        }
                                    }
                                } else {
                                    Log.e(TAG, "No BKT Score key found for module: " + moduleKey);
                                }
                            } else {
                                Log.e(TAG, "Value is not a Map for module: " + moduleKey);
                            }
                        }
                    }
                } else {
                    Log.d(TAG, "No such document");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });
    }

    private static void addOrUpdateScore(String moduleKey, double score) {
        int index = Integer.parseInt(moduleKey.substring(1)) - 1; // Extract index from key (e.g., M1 -> 0)

        // Ensure the list has enough elements
        while (bktScores.size() <= index) {
            bktScores.add(0.0); // Initialize with default value if needed
        }

        bktScores.set(index, score); // Store score in the corresponding index
    }

}