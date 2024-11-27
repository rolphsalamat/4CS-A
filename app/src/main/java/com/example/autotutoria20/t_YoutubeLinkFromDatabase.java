package com.example.autotutoria20;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class t_YoutubeLinkFromDatabase {

    public static Map<String, String> youtubeLinksMap;

    static void fetchYoutubeLinks() {
        String TAG = "fetchYoutubeLinks()";

        // Log.e(TAG, "Fetching YouTube Links...");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        youtubeLinksMap = new HashMap<>(); // Ensure this is initialized

        db.collection("Youtube Links")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Log.e(TAG, "Modules fetched successfully");

                        for (QueryDocumentSnapshot moduleDoc : task.getResult()) {
                            String moduleName = moduleDoc.getId(); // e.g., "Module 4"
                            String moduleNumber = moduleName.replace("Module ", ""); // Extract the number (e.g., "4")

                            // Log.e(TAG, "Processing module: " + moduleName);

                            // Iterate through each lesson field in the module document
                            for (String lessonField : moduleDoc.getData().keySet()) {
                                // Log.e(TAG, "Processing field: " + lessonField);

                                // Check if the field matches the expected format ("Lesson X")
                                if (lessonField.startsWith("Lesson")) {
                                    String lessonNumber = lessonField.replace("Lesson ", ""); // Extract the number (e.g., "2")
                                    String retrievedString = moduleDoc.getString(lessonField);

                                    if (retrievedString != null) {
                                        // Construct the key in "M(lessonN)_Lesson (moduleN)" format
                                        String key = "M" + lessonNumber + "_Lesson " + moduleNumber;

                                        // Add to map
                                        youtubeLinksMap.put(key, retrievedString);

                                        // Log.e(TAG, "Added YouTube link: " + key + " -> " + retrievedString);
                                    } else {
                                        // Log.e(TAG, "No data found for: " + lessonField + " in module: " + moduleName);
                                    }
                                } else {
                                    // Log.e(TAG, "Skipped non-lesson field: " + lessonField);
                                }
                            }
                        }

                        // Log the final map
                        // Log.d("YoutubeLinksMap", youtubeLinksMap.toString());
                    } else {
                        // Log.e(TAG, "Error fetching modules: ", task.getException());
                    }
                });
    }

    public static String getLessonVideoLinks2(String module, String lesson) {
        String TAG = "getLessonVideoLinks2";
        String videoLink = null;  // Default to null if no link is found

        // Log.d(TAG, "Module: " + module);
        // Log.d(TAG, "Lesson: " + lesson);

        // Log.i(TAG, "t_YoutubeLinkFromDatabase.youtubeLinksMap.get(" + module + "_" + lesson);
        videoLink = t_YoutubeLinkFromDatabase.youtubeLinksMap.get(module + "_" + lesson);

    return videoLink; // This will return the video link (or null if it wasn't found)
}

}
