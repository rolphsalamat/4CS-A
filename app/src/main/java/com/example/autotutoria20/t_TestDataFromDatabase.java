package com.example.autotutoria20;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class t_TestDataFromDatabase {

    static void getTestQuestionsFromDatabase(String testMode) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Log.d(testMode, "Fetching all modules and lessons data");

        db.collection("Questions")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot moduleSnapshot : task.getResult()) {
                            String module = moduleSnapshot.getId(); // e.g., "Module 1"

                            if (module.contains("Module ")) {
                                Log.d(testMode, "Module data found for: " + module);
                                Map<String, Object> moduleData = new HashMap<>();

                                for (String lesson : moduleSnapshot.getData().keySet()) {
                                    if (lesson.contains("Lesson ")) {
                                        Log.d(testMode, "Lesson data found for: " + lesson);
                                        Map<String, Object> lessonData = (Map<String, Object>) moduleSnapshot.get(lesson);
                                        Map<String, Object> testDataMap = (Map<String, Object>) lessonData.get(testMode);

                                        if (testDataMap != null) {
                                            Log.d(testMode, testMode + " data found for: " + lesson);
                                            Map<String, Object> questionsMap = new HashMap<>();

                                            Map<String, Object> questionsObj = null;
                                            Map<String, Object> choicesObj = null;
                                            Map<String, Object> answersObj = null;

                                            // Pre-Test | Post-Test {Easy, Medium}
                                            if (testMode.equals("Pre-Test") || testMode.equals("Post-Test Easy") || testMode.equals("Post-Test Medium")) {
                                                questionsObj = (Map<String, Object>) testDataMap.get("Questions");
                                                choicesObj = (Map<String, Object>) testDataMap.get("Choices");
                                                answersObj = (Map<String, Object>) testDataMap.get("Answers");
                                            }
                                            // Post-Test Hard
                                            else if (testMode.equals("Post-Test Hard")) {
                                                questionsObj = (Map<String, Object>) testDataMap.get("Questions");
                                                answersObj = (Map<String, Object>) testDataMap.get("Answers");
                                            }

                                            // Proceed only if they are Maps and not null
                                            if ((questionsObj instanceof Map && choicesObj instanceof Map && answersObj instanceof Map) ||
                                                    (questionsObj instanceof Map && answersObj instanceof Map)) {

                                                // Loop through each question and store it in the map
                                                for (String questionKey : questionsObj.keySet()) {
                                                    String question = (String) questionsObj.get(questionKey);
                                                    String questionNumber = questionKey.split(" ")[1]; // This gets the number part from "Question X"

                                                    List<String> choiceList = null;
                                                    if (choicesObj != null) {
                                                        choiceList = (List<String>) choicesObj.get("Choice " + questionNumber);
                                                    }

                                                    Number answerNumber = null;
                                                    String answerText = null;

                                                    Object answerObj = answersObj.get("Answer " + questionNumber);
                                                    if (testMode.equals("Post-Test Hard")) {
                                                        if (answerObj instanceof Number) {
                                                            answerNumber = (Number) answerObj; // Keep as Number for Post-Test Hard
                                                        } else {
                                                            Log.e(testMode, "Expected Number for Answer " + questionNumber + ", but got: " + answerObj);
                                                            answerText = (String) answerObj;
                                                        }
                                                    } else {
                                                        if (answerObj instanceof Long) {
                                                            answerText = String.valueOf(answerObj); // Convert Long to String for other test modes
                                                        } else if (answerObj instanceof String) {
                                                            answerText = (String) answerObj; // Directly cast if it's already a String
                                                        } else {
                                                            Log.e(testMode, "Expected String for Answer " + questionNumber + ", but got: " + answerObj);
                                                        }
                                                    }

                                                    Log.e(testMode, "Fetching data for " + questionKey);
                                                    Log.d(testMode, "Question: " + question);

                                                    if (testMode.equals("Post-Test Hard")) {
                                                        Log.d(testMode, "Answer: " + answerText);
                                                    } else {
                                                        Log.d(testMode, "Choices: " + choiceList);
                                                        Log.d(testMode, "Answer: " + answerNumber);
                                                    }

                                                    // Check if any of the data is null before assigning
                                                    if ((question != null && choiceList != null && answerNumber != null) ||
                                                        (question != null && answerText != null)) {

                                                        // Store data for this question
                                                        Map<String, Object> questionData = new HashMap<>();
                                                        questionData.put("Question", question);
                                                        questionData.put("Choices", choiceList);

                                                        if (testMode.equals("Post-Test Hard")) {
                                                            questionData.put("Answer", answerNumber);
                                                        } else {
                                                            questionData.put("Answer", answerText);
                                                        }

                                                        questionsMap.put(questionKey, questionData); // Add to questions map
                                                    } else {
                                                        Log.e(testMode, "Missing data for " + questionKey);
                                                    }
                                                }

                                                Log.e(testMode, testMode + " data retrieval done!");

                                                // Add questions map to lesson data
                                                lessonData.put("Questions", questionsMap);
                                                moduleData.put(lesson, lessonData); // Add lesson to module data

                                            } else {
                                                Log.e(testMode, "Invalid data structure for key: test mode");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Log.e(testMode, "Failed to fetch modules data: ", task.getException());
                    }
                });
    }
}