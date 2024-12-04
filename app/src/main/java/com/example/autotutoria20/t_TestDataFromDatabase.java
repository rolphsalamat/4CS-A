package com.example.autotutoria20;


import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class t_TestDataFromDatabase {
    private static final Random random = new Random();  // For generating random numbers
    static Map<String, Object> moduleData = new HashMap<>();

    public static void logMapData(Map<String, Object> dataMap) {
        // // Log.e("logMapData", "ETO NA ILO-LOG KO NA!");

        if (dataMap == null || dataMap.isEmpty()) {
            // // Log.i("logMapData", "The map is empty or null.");
            return;
        }

        StringBuilder logBuilder = new StringBuilder();
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            logBuilder.append("Key: ").append(key).append(", Value: ").append(value).append("\n");
        }

        // Log all entries at once
        // // Log.i("logMapData", logBuilder.toString());
    }

    static void getTestQuestionsFromDatabase(String testMode) {

        String TAG = testMode;

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        moduleData = new HashMap<>(); // Initialize moduleData

        // Log.d(TAG, "Fetching all modules and lessons data");

        db.collection("Questions")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot moduleSnapshot : task.getResult()) {
                            String module = moduleSnapshot.getId(); // e.g., "Module 1"

                            if (module.contains("Module ")) {
                                 Log.d(TAG, "Module data found for: " + module);

                                // Create a map to store lessons for the module
                                Map<String, Object> moduleLessonData = new HashMap<>();

                                // Loop through the lessons inside the module
                                for (String lesson : moduleSnapshot.getData().keySet()) {
                                    if (lesson.contains("Lesson ")) {
                                         Log.d("getTestQuestionsFromDatabase", "MindSync | Lesson data found for: " + lesson);
                                        Map<String, Object> lessonData = (Map<String, Object>) moduleSnapshot.get(lesson);

                                        // Proceed only if a valid test mode was found
                                        if (testMode != null) {
                                            Map<String, Object> testDataMap = (Map<String, Object>) lessonData.get(testMode);
                                            if (testDataMap != null) {
                                                // Log.d("getTestQuestionsFromDatabase", testMode + " data found for: " + lesson);

                                                // Continue processing as before...
                                                Map<String, Object> questionsMap = new HashMap<>();
                                                Map<String, Object> questionsObj = null;
                                                Map<String, Object> choicesObj = null;
                                                Map<String, Object> answersObj = null;

                                                // Pre-Test | Post-Test {Easy, Medium}
                                                if (testMode.equals("Pre-Test") ||
                                                        testMode.equals("Post-Test Easy") ||
                                                        testMode.equals("Post-Test Medium")) {
                                                    questionsObj = (Map<String, Object>) testDataMap.get("Questions");

                                                    if (questionsObj == null)
                                                        questionsObj = (Map<String, Object>) testDataMap.get("Qusetions");

                                                    choicesObj = (Map<String, Object>) testDataMap.get("Choices");
                                                    answersObj = (Map<String, Object>) testDataMap.get("Answers");
                                                }
                                                // Post-Test Hard
                                                else if (testMode.equals("Post-Test Hard")) {
                                                    questionsObj = (Map<String, Object>) testDataMap.get("Questions");
                                                    answersObj = (Map<String, Object>) testDataMap.get("Answers");
                                                }

                                                // Log.i(TAG, "Test Mode: " + testMode);
                                                // Log.i(TAG, "questionsObj: " + questionsObj);
                                                // Log.i(TAG, "choicesObj: " + choicesObj);
                                                // Log.i(TAG, "answersObj: " + answersObj);

                                                // Proceed only if they are Maps and not null
                                                if ((questionsObj instanceof Map && choicesObj instanceof Map && answersObj instanceof Map) ||
                                                        (questionsObj instanceof Map && answersObj instanceof Map)) {

                                                    // Loop through each question and store it in the map
                                                    for (String questionKey : questionsObj.keySet()) {

//                                                        b_main_0_menu.updateProgress(1);

                                                        String question = (String) questionsObj.get(questionKey);

//                                                        String questionNumber = questionKey.split(" ")[1]; // This gets the number part from "Question X"

                                                        String questionNumber = "";
                                                        if (questionKey != null && questionKey.contains(" ")) {
                                                            String[] parts = questionKey.split(" ");
                                                            if (parts.length > 1) {
                                                                questionNumber = parts[1]; // Safely get the second part
                                                            } else {
                                                                 // Log.e("getTestQuestionsFromDatabase", "Invalid questionKey format: " + questionKey);
                                                            }
                                                        } else {
                                                             // Log.e("getTestQuestionsFromDatabase", "Invalid or missing questionKey: " + questionKey);
                                                        }

                                                        List<String> choiceList = null;

                                                        if (choicesObj != null) {
                                                            choiceList = (List<String>) choicesObj.get("Choice " + questionNumber);
                                                        }

                                                        Number answerNumber = null;
                                                        String answerText = null;

                                                         // Log.e(TAG, "Hey Rop, Choice retrieved: " + choiceList);
                                                        Object answerObj = answersObj.get("Answer " + questionNumber);
                                                         // Log.e("ROP CHECK MO TO", "Checking Answer " + questionNumber);

                                                        // Check the type of answerObj before casting
                                                        if (answerObj instanceof Long) { // If it's a Long, convert it to String
                                                             Log.d("ROP CHECK MO TO", String.valueOf(answerObj));
                                                            answerNumber = (Number) answerObj;
                                                        } else if (answerObj instanceof String) { // If it's already a String, just log it
                                                             Log.d("ROP CHECK MO TO", (String) answerObj);
                                                            answerText = (String) answerObj;
                                                        } else { // Handle other unexpected types, if any
                                                             // Log.e("ROP CHECK MO TO", "Unexpected type for Answer: " + answerObj);
                                                        }

                                                         // Log.e(TAG, "Fetching data for " + questionKey);
                                                         Log.d(TAG, "Question: " + question);

                                                        if (testMode.equals("Post-Test Hard")) {
                                                             Log.d(TAG, "Answer: " + answerText);
                                                        } else {
                                                             Log.d(TAG, "Choices: " + choiceList);
                                                             Log.d(TAG, "Answer: " + answerNumber);
                                                        }

                                                        // Check if any of the data is null before assigning
                                                        if ((question != null && choiceList != null && answerNumber != null) ||
                                                                (question != null && answerText != null)) {

                                                            // Store data for this question
                                                            Map<String, Object> questionData = new HashMap<>();
                                                            questionData.put("Question", question);
//                                                            questionData.put("Choices", choiceList);

                                                            String answerVariable = null;

                                                            if (testMode.equals("Post-Test Hard")) {
                                                                // // Log.e(TAG, "Post-Test Hard | Answer: " + answerText);
                                                                answerVariable = "String";
                                                                questionData.put("Answer", answerText);
                                                            } else {
                                                                // // Log.e(TAG, "Post-Test Easy or Medium | Answer: " + answerNumber);
                                                                answerVariable = "Number";
                                                                questionData.put("Choices", choiceList);
                                                                questionData.put("Answer", answerNumber);
                                                            }

                                                             // Log.i(TAG, "Question Key: " + questionKey);
                                                             // Log.i(TAG, "Question Data: " + questionData);
                                                             // Log.i(TAG, "Answer Variable: " + answerVariable);

                                                            questionsMap.put(questionKey, questionData); // Add to questions map
                                                        } else {
                                                             // Log.e(TAG, "Missing data for " + questionKey);
                                                        }
                                                    }

                                                     // Log.e(TAG, testMode + " data retrieval done!");

                                                    // Add questions map to lesson data
                                                    lessonData.put("Questions", questionsMap);

                                                    // Now add lesson data to module lesson data
                                                    moduleLessonData.put(lesson, lessonData);
                                                } else {
                                                     // Log.e(TAG, "Invalid data structure for key: " + testMode);
                                                }
                                            }
                                        }
                                    }
                                }

                                // Add module lesson data to moduleData map
                                moduleData.put(module, moduleLessonData);  // Add the complete module data to moduleData

                                // Optionally log the data (this can be removed if unnecessary)
                                logMapData(moduleData);

                            }
                        }
                    }
                });
        if (testMode.equals("Post-Test Hard")) {
            // Log.i(TAG, "Done! let's show the application...");
//                                    b_main_1_lesson_progressive.hideLoadingDialog();
            b_main_0_menu.viewPager.setAdapter(b_main_0_menu.pagerAdapter);
//            b_main_0_menu.hideLoadingDialog();
        }
    }

    public static List<e_Question> getRandomQuestionsData(String module, String lesson, String testMode) {

        String TAG = "getRandomQuestionsData";
        // // Log.e(TAG, "IM HERE");
        // // Log.e(TAG, "Module: " + module);
        // // Log.e(TAG, "Lesson: " + lesson);
        // // Log.e(TAG, "Test Mode: " + testMode);

        List<e_Question> randomQuestions = new ArrayList<>();

        if (t_TestDataFromDatabase.moduleData.containsKey(module)) {
            Object moduleObj = t_TestDataFromDatabase.moduleData.get(module);

            if (moduleObj instanceof Map) {
                 // Log.e(TAG, "---> Module " + module.charAt(7) + " [Map]");
                Map<String, Object> moduleLessons = (Map<String, Object>) moduleObj;


                if (moduleLessons.containsKey(lesson)) {
                    Object lessonObj = moduleLessons.get(lesson);

                    if (lessonObj instanceof Map) {
                         // Log.e(TAG, "------> Lesson " + lesson.charAt(7) + " [Map]");
                        Map<String, Object> lessonData = (Map<String, Object>) lessonObj;

                        if (lessonData.containsKey(testMode)) {
                            Object testModeObj = lessonData.get(testMode);

                            if (testModeObj instanceof Map) {
                                 // Log.e(TAG, "---------> " + testMode + " [Map]");
                                Map<String, Object> testModeMap = (Map<String, Object>) testModeObj;

                                // Check if all required maps exist: Questions, Choices, Answers
                                if (testModeMap.containsKey("Questions")) {

                                    Map<String, Object> questionsMap = null;
                                    Map<String, Object> choicesMap = null;
                                    Map<String, Object> answersMap = null;
                                     // Log.e(TAG, "------------> " + "Questions" + " [Map]");

                                    questionsMap = (Map<String, Object>) testModeMap.get("Questions");

                                    if (!testMode.equals("Post-Test Hard"))
                                        choicesMap = (Map<String, Object>) testModeMap.getOrDefault("Choices", new HashMap<>());

                                    answersMap = (Map<String, Object>) testModeMap.getOrDefault("Answers", new HashMap<>());

                                     // Log.i(TAG, "Initialize Question, Choices, and Answers Map");

                                    List<String> questionKeys = new ArrayList<>(questionsMap.keySet());

                                    int minIndex = 1; // Minimum index (inclusive)
                                    int maxIndex = questionKeys.size(); // Maximum index (exclusive)

                                    // Step 1: Generate unique random indices for preTestQuestions
                                    Set<Integer> randomIndicesSet = new HashSet<>();

                                    // Pre-Test || Post-Test Medium = 5
                                    // Post-Test Easy = 10
                                    // Post-Test Hard = 3
                                    switch (testMode) {
                                        case "Post-Test Hard":
                                            maxIndex = Math.min(questionKeys.size(), 3);
                                            // // Log.i(TAG, "Post-Test Hard | maxIndex: " + maxIndex);
                                            break;
                                        case "Post-Test Medium":
                                        case "Pre-Test":
                                            maxIndex = Math.min(questionKeys.size(), 5);
                                            // // Log.i(TAG, "Post-Test Medium || Pre-Test | maxIndex: " + maxIndex);
                                            break;
                                        case "Post-Test Easy":
                                            maxIndex = Math.min(questionKeys.size(), 10);
                                            // // Log.i(TAG, "Post-Test Easy | maxIndex: " + maxIndex);
                                            break;
                                    }

                                    // Ensure minIndex is less than or equal to maxIndex
                                    if (minIndex > maxIndex) {
                                        // // Log.e(TAG, "Invalid range: minIndex should be less than or equal to maxIndex.");
                                    } else {
                                        // // Log.i(TAG, "After setting Maximum Size");
                                        // // Log.i(TAG, "Minimum Size: " + minIndex);
                                        // // Log.i(TAG, "Maximum Size: " + maxIndex);

                                        // Adjust the range for random number generation
                                        int range = maxIndex - minIndex + 1; // This should still be correct

                                        while (randomIndicesSet.size() < (maxIndex - minIndex + 1)) { // Ensure we get the right number of unique indices
                                            int randomIndex = random.nextInt(questionKeys.size()) + minIndex; // Generates [minIndex, maxIndex]
                                            if (randomIndicesSet.add(randomIndex)) { // Adds if unique
                                                // // Log.i(TAG, "Added: " + randomIndex);
                                            }
                                        }
                                    }

                                    // Now you can convert randomIndicesSet to a List or use it as needed

                                    // Convert the set to a list if needed
                                    List<Integer> randomIndices = new ArrayList<>(randomIndicesSet);

                                    // Shuffle the list to randomize order
                                    Collections.shuffle(randomIndices);

                                     // Log the generated random indices
                                     // Log.e(TAG, "randomIndices[]: " + Arrays.toString(randomIndices.toArray()));

                                    String questionKey = null;
                                    String choicesKey1 = null;
                                    String choicesKey2 = null;
                                    String answerKey = null;

                                    // Step 2: Use the generated random indices to retrieve questions, choices, and answers
                                    for (int i = 1; i <= maxIndex; i++) {

                                        questionKey = "Question " + randomIndices.get(i-1);
                                        // // Log.e(TAG, "questionKey: " + questionKey);

                                        if (!testMode.equals("Post-Test Hard")) {
                                            choicesKey1 = "Choice " + randomIndices.get(i-1);
                                            choicesKey2 = "Choices " + randomIndices.get(i-1);

                                        }

                                        answerKey = "Answer " + randomIndices.get(i-1);
                                        // // Log.e(TAG, "answerKey: " + answerKey);


                                        // Retrieve question data
                                        String questionText = (String) questionsMap.getOrDefault(questionKey, "Default question text");
                                         Log.d(TAG, "Question: " + questionText);

                                        // Retrieve choices (skip for "Post-Test Hard")
                                        List<String> choices = null;

                                        // Original Code
                                        if (!testMode.equals("Post-Test Hard")
                                                && (choicesMap.containsKey(choicesKey1)
                                                || choicesMap.containsKey(choicesKey2))) {
                                            // Retrieve "Choice n"
                                            choices = (List<String>) choicesMap.get(choicesKey1);
                                            if (choices == null)
                                                // Retrieve Choices n"
                                                choices = (List<String>) choicesMap.get(choicesKey2);

                                        }

                                        if (choices == null && !testMode.equals("Post-Test Hard")) {
                                            choices = Arrays.asList("Option 1", "Option 2", "Option 3", "Option 4");
                                        }

                                        if (!testMode.equals("Post-Test Hard"))
                                            Log.d(TAG, "Choices: " + choices);

                                        // Retrieve answer
                                        Object answerObj = answersMap.get(answerKey);
                                        String answerText;
                                        int answerIndex;

                                        if (testMode.equals("Post-Test Hard")) {
                                            answerText = (String) answersMap.getOrDefault(answerKey, "Default Answer :D");// Create and add the question object to the list

                                            // // Log.i(TAG, "String | Answer: " + answerText);
                                            randomQuestions.add(new e_Question(questionText, answerText, e_Question.Difficulty.HARD));
                                        } else {
                                            if (answerObj instanceof Long) {
                                                // // Log.i(TAG, "answerOjb is Long");
                                                answerIndex = ((Long) answerObj).intValue(); // Convert Long to int
                                            } else if (answerObj instanceof Integer) {
                                                // // Log.i(TAG, "answerOjb is Integer");
                                                answerIndex = (Integer) answerObj; // Use directly if it's already an Integer
                                            } else if (answerObj instanceof Number) {
                                                // // Log.i(TAG, "answerOjb is Number");
                                                answerIndex = ((Number) answerObj).intValue(); // Convert Number to int safely
                                            } else {
                                                // Log an error and assign a default value
                                                // // Log.e("AnswerTypeError", "Unexpected type for answerObj: " + answerObj);
                                                answerIndex = -1; // Default invalid value
                                            }

                                            // // Log.i(TAG, "Integer | Answer: " + answerIndex);
                                            // Add the question to the list
                                            randomQuestions.add(new e_Question(questionText, choices, answerIndex));
                                        }

                                         Log.d(TAG, "Answer: " + answerObj);

                                        // // Log.i(TAG, "Screen Lock");

                                    }


                                    return randomQuestions;
                                } else {
                                     // Log.e(TAG, "No questions found for test mode: " + testMode);
                                }
                            } else {
                                 // Log.e(TAG, "Test mode data is not a Map.");
                            }
                        } else {
                             // Log.e(TAG, "Test mode not found: " + testMode);
                        }
                    } else {
                         // Log.e(TAG, "Lesson data is not a Map.");
                    }
                } else {
                     // Log.e(TAG, "Lesson not found: " + lesson);
                }
            } else {
                 // Log.e(TAG, "Module data is not a Map.");
            }
        } else {
             // Log.e(TAG, "Module not found: " + module);
        }

        return randomQuestions;
    }


}