package com.example.autotutoria20;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class t_LessonSequenceFromDatabase {

    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    static Map<String, Map<String, String>> lessonStepsMap = new HashMap<>();
    static Map<String, Map<String, Integer>> lessonStepsCount = new HashMap<>();
    private static final String TAG = "t_LessonSequenceFromDatabase";

    static void getLessonSequenceFromDatabase2() {
        String TAG = "getLessonSequenceFromDatabase2";

        db.collection("Lessons Sequence")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot moduleSnapshot : task.getResult()) {
                            String module = moduleSnapshot.getId(); // e.g., "Module 1"

                            Map<String, Object> lessons = moduleSnapshot.getData();
                            if (lessons != null) {

                                Map<String, String> lessonsMap = new HashMap<>();
                                Map<String, Integer> stepCountMap = new HashMap<>();

                                for (Map.Entry<String, Object> lessonEntry : lessons.entrySet()) {
                                    String lesson = lessonEntry.getKey();

                                    @SuppressWarnings("unchecked")
                                    List<String> steps = (List<String>) lessonEntry.getValue();

                                    if (steps != null) {

                                        // Get the size of the steps list
                                        int stepSize = steps.size(); // This gives you the number of steps
                                        Log.i(TAG, "Number of steps for lesson " + lesson + ": " + stepSize);

                                        String stepsAsString = String.join(", ", steps);
                                        lessonsMap.put(lesson, stepsAsString);

                                        // Assuming stepCount is meant to be the size of steps
                                        stepCountMap.put(lesson, stepSize); // Store the actual count

                                        Log.i(TAG, "stepCountMap.put(" + lesson + ", " + stepSize + ");");
                                    }

                                }

                                // Add the module and its lessons to the main map
                                lessonStepsMap.put(module, lessonsMap);
                                lessonStepsCount.put(module, stepCountMap);
                                Log.i(TAG, "lessonStepsCount.put(" + module + ", " + stepCountMap + ");");
                                Log.i(TAG, "Current lessonStepsCount: " + lessonStepsCount);
                            }
                        }
                    } else {
                        Log.e(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }



    static void getLessonSequenceFromDatabase() {
        try {
            // Synchronously get the Firestore collection
            Task<QuerySnapshot> task = db.collection("Lessons Sequence").get();
            QuerySnapshot querySnapshot = Tasks.await(task); // Waits for the task to complete

            List<DocumentSnapshot> documents = querySnapshot.getDocuments();

            for (DocumentSnapshot document : documents) {
                String module = document.getId(); // Module name (e.g., "Module 1")
                // Log.i(TAG, "Module: " + module);

                Map<String, Object> lessons = document.getData(); // All lessons in the module
                if (lessons != null) {
                    Map<String, String> lessonsMap = new HashMap<>();

                    // Iterate through each lesson in the module
                    for (Map.Entry<String, Object> lessonEntry : lessons.entrySet()) {
                        String lesson = lessonEntry.getKey(); // Lesson name (e.g., "Lesson 1")

                        @SuppressWarnings("unchecked")
                        List<String> steps = (List<String>) lessonEntry.getValue(); // Array of steps
                        if (steps != null) {
                            // Join steps into a single string (or store as needed)
                            String stepsAsString = String.join(", ", steps);
                            lessonsMap.put(lesson, stepsAsString);

                            // Log.i("loadLessonSteps", "Lesson: " + lesson + ", Steps: " + stepsAsString);
                        }
                    }
                    // Add the module and its lessons to the main map
                    lessonStepsMap.put(module, lessonsMap);
                    // Log.i("loadLessonSteps", "after .put() | Module: " + module + ", Lessons: " + lessonsMap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Log.e(TAG, "Error fetching data: ", e);
        }
        // Log.i(TAG, "LAST PART | lessonStepsMap: " + lessonStepsMap);
    }

    public static int getNumberOfSteps(String lessonModule) {

        String module = "Module " + String.valueOf(lessonModule.charAt(10));
        String lesson = "Lesson " + String.valueOf(lessonModule.charAt(1));

        String TAG = "Nitro";

        // Log.i(TAG, "getNumberOfSteps()");
        // Log.i(TAG, "Module: " + module);
        // Log.i(TAG, "Lesson: " + lesson);

        if (lessonStepsMap.containsKey(module)) {
            Map<String, String> lessonsMap = lessonStepsMap.get(module);
            // Log.d(TAG, module + " exists! LET'S GO!");

            if (lessonsMap != null && lessonsMap.containsKey(lesson)) {
                // Retrieve the list of steps for the lesson
                // Log.d(TAG, lesson + " exists! LET'S GO!");

                String stepsAsString = lessonsMap.get(lesson); // Retrieve the steps as a single string
                List<String> steps = Arrays.asList(stepsAsString.split(", ")); // Split the string into a list of steps
                // Log.i(TAG, "-_- | Step: " + steps);
                if (steps != null) {
                    // Log.i(TAG, "steps.size(): " + steps.size());
                    return steps.size(); // Return the number of steps
                } else {
                    // Log.e("RetrieveSteps", "No steps found for lesson: " + lesson);
                }
            } else {
                // Log.e("RetrieveSteps", "Lesson not found in module: " + lesson);
            }
        } else {
            // Log.e("RetrieveSteps", "Module not found: " + module);
        }


        return 0; // Return 0 if the lessonModule is not found
    }

}
