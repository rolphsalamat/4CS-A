package com.example.autotutoria20;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class x_bkt_algorithm {

    private static final String TAG = "x_bkt_algorithm";
    private Context context;

    private FirebaseFirestore db;
    private String userId;
    public static boolean isLessonFinished;
    public static boolean isLessonPassed = false;
    public static boolean isLessonCompleted = false;

    private List<Double> bktScores;
    private static double knowledgeProbability;
    private static double learnRate;
    private static double forgetRate;
    private static double slipRate;
    private static double guessRate;
    private static double softeningRate;
    // Feedback ranges
    double hard = 0.63;
    double medium = 0.28;
    double easy = 0.00;

    // Floats for rating
    float very_bad = .20F;
    float bad = .30F;
    float neutral = .50F;
    float good = .60F;
    float very_good = .70F;

    String very_bad_string = "Very Bad";
    String bad_string = "Bad";
    String neutral_string = "Neutral";
    String good_string = "Good";
    String very_good_string = "Very Good";
    String undefined_string = "Undefined";

    String category;


    // Singleton instance (if you need it to be a singleton)
    private static x_bkt_algorithm instance;

    // Constructor and methods to initialize the fields
    public x_bkt_algorithm() {
        // Initialize with default values
        this.very_bad = 0.2F;
        this.bad = 0.4F;
        this.neutral = 0.6F;
        this.good = 0.8F;
        this.very_good = 1.0F;

        this.very_bad_string = "Very Bad";
        this.bad_string = "Bad";
        this.neutral_string = "Neutral";
        this.good_string = "Good";
        this.very_good_string = "Very Good";
        this.undefined_string = "Undefined";
    }

    private x_bkt_algorithm(double pInit, double learnRate, double forgetRate, double slip) {
        this.db = FirebaseFirestore.getInstance();
        this.userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.knowledgeProbability = pInit;
        this.learnRate = learnRate;
        this.forgetRate = forgetRate;
        this.slipRate = slip;
    }

    public interface FetchCategoryCallback {
        void onCategoryFetched(String category);
    }


    public void retrieveUserCategory(FetchCategoryCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user != null ? user.getUid() : null;

        if (userId == null) {
            Log.e(TAG, "User not authenticated");
            callback.onCategoryFetched(null);
            return;
        }

        db.collection("users").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {

                        category = documentSnapshot.getString("User Category");

                        Log.d(TAG, "User Category retrieved: " + category);
                        callback.onCategoryFetched(category);

//                        updateKnowledgeProbability();

                        // Pag di mapagana to putanginamo wag nalang
//                        // SharedPreferences nalang
//                        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                        editor.putString("User Category", category);

                    } else {
                        Log.e(TAG, "No such document");
                        callback.onCategoryFetched(null);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error retrieving User Category", e);
                    callback.onCategoryFetched(null);
                });
    }


    public void updateKnowledgeProbability(boolean answeredCorrectly) {
        double pKnow = knowledgeProbability;

        String TAG = "updateKnowledgeProbability";

        Log.e(TAG, "Answer: " + answeredCorrectly);
        Log.e(TAG, "knowledgeProbability: " + knowledgeProbability);
        Log.e(TAG, "Learn Rate: " + learnRate);
        Log.e(TAG, "Guess Rate: " + guessRate);
        Log.e(TAG, "Slip Rate: "  + slipRate);
        Log.e(TAG, "Softening Rate: " + softeningRate);

        if (answeredCorrectly) {
            // Update with guess rate when answered correctly
            // Formula: pKnow + (learnRate * (1 - pKnow)) * (1 - guessRate)
            knowledgeProbability = pKnow + (learnRate * (1 - pKnow)) * (1 - guessRate);
        } else {
            // Softened adjustment for incorrect answers
            // Rather than a drastic reduction, use a milder decrease by incorporating the softening rate
            // Formula: pKnow * (1 - slipRate * softeningRate)
            knowledgeProbability = pKnow * (1 - slipRate * softeningRate);

//            // Optionally include a forget rate to ensure the knowledge doesn't hold at maximum indefinitely
//            knowledgeProbability *= (1 - forgetRate);
        }

        // Ensure knowledgeProbability stays within [0, 1]
        knowledgeProbability = Math.max(0.0, Math.min(1.0, knowledgeProbability));

        Log.d(TAG, "Updated Knowledge Probability: " + knowledgeProbability);
    }


    // Original Code
//    public void updateKnowledgeProbability(boolean answeredCorrectly) {
//        double pKnow = knowledgeProbability;
//
//        String TAG = "updateKnowledgeProbability";
//
//        Log.e(TAG, "Answer: " + answeredCorrectly);
//        Log.e(TAG, "knowledgeProbability: " + knowledgeProbability);
//        Log.e(TAG, "Learn Rate: " + learnRate);
//        Log.e(TAG, "Guess Rate: " + guessRate);
//        Log.e(TAG, "Slip Rate: "  + slipRate);
//        Log.e(TAG, "Softening Rate: " + softeningRate);
//
//        if (answeredCorrectly) {
//            // Update with guess rate
//            // Formula: pKnow + (learnRate * (1 - pKnow)) * (1 - guessRate)
//            knowledgeProbability = pKnow + (learnRate * (1 - pKnow)) * (1 - guessRate);
//        } else {
//            // Adjusted formula for incorrect answers
//            // Instead of a drastic reduction, we decrease it by a smaller factor
//            // Formula: pKnow * (1 - slipRate)
//            // This reduces the score but not drastically
//            knowledgeProbability = pKnow * (1 - slipRate);
//
//            // Optionally include a small adjustment for forget rate to soften the impact
//            knowledgeProbability *= (1 - forgetRate);
//        }
//
//        // Ensure knowledgeProbability stays within [0, 1]
//        knowledgeProbability = Math.max(0.0, Math.min(1.0, knowledgeProbability));
//
//        Log.d(TAG, "Updated Knowledge Probability: " + knowledgeProbability);
//    }

//    public void updateKnowledgeProbability(boolean answeredCorrectly) {
//        double pKnow = knowledgeProbability;
//
//        String TAG = "updateKnowledgeProbability";
//
//        Log.e(TAG, "Answer: " + answeredCorrectly);
//        Log.e(TAG, "knowledgeProbability: " + knowledgeProbability);
//        Log.e(TAG, "Learn Rate: " + learnRate);
//        Log.e(TAG, "Guess Rate: " + guessRate);
//        Log.e(TAG, "Slip Rate: "  + slipRate);
//        Log.e(TAG, "Softening Rate: " + softeningRate);
//
////        case "Expert":
////        learnRate = 0.9;
////        slipRate = 0.05;
////        guessRate = 0.05;
////        forgetRate = 0.01;  // Experts rarely forget
////        softeningRate = 0.4;
////        knowledgeProbability = 0.9;  // Initial probability for Expert
////        break;
//
//        if (answeredCorrectly) {
//            // Update with guess rate
//            // Formula: pKnow + (learnRate * (1 - pKnow)) * (1 - guessRate)
//            // Example Calculation:
//            // 0.9 + (0.9 * (1 - 0.9)) * (1 - 0.05)
//            // 0.9 + (0.09) * (0.95)
//            // 0.9 + 0.0855
//
//            // Result: 0.9855
//            knowledgeProbability = pKnow + (learnRate * (1 - pKnow)) * (1 - guessRate);
////            Log.e(TAG, "Score = (" + pKnow + " + (" + (learnRate*(1 - pKnow)) + " * " + (1-guessRate) + ")");
//        } else {
//            // Update with slip rate and forget rate
//            // Formula: pKnow + (1 - forgetRate) * slipRate * softeningRate
//            // Example Calculation:
//            // 0.9 * (1 - 0.01) * 0.05 * 0.4
//            // 0.9 * 0.99 * 0.02
//
//            // Result: 0.03588
//            knowledgeProbability = pKnow + (1 - forgetRate) * slipRate * softeningRate;
////            Log.e(TAG, "Score = (" + pKnow + " * (" + (1-forgetRate) + " * "  + slipRate + " * " + softeningRate + ")");
//        }
//
//        Log.d(TAG, "Updated Knowledge Probability: " + knowledgeProbability);
//    }

//    public void updateKnowledgeProbability() {
//
//
//        switch (category) {
//
//            case "Novice": // Level 1 [0.0 - 0.1] Novice
//                knowledgeProbability = 0.0 + (0.1 - 0.0) * Math.random(); break;
//            case "Beginner":  // Level 2 [0.1 - 0.3] Beginner
//                knowledgeProbability = 0.1 + (0.3 - 0.1) * Math.random(); break;
//            case "Intermediate": // Level 3 [0.3 - 0.5] Intermediate
//                knowledgeProbability = 0.3 + (0.5 - 0.3) * Math.random(); break;
//            case "Advanced": // Level 4 [0.5 - 0.7] Advanced
//                knowledgeProbability = 0.5 + (0.7 - 0.5) * Math.random(); break;
//            case "Expert": // Level 5 [0.7 - 0.9] Expert
//                knowledgeProbability = 0.7 + (0.9 - 0.7) * Math.random(); break;
//        }
//
//        Log.e("User Category", "Category: " + category);
//        Log.e("User Category", "pKnow: " + knowledgeProbability);
//
//    }


    public e_Question.Difficulty getDifficultyLevel(double bktScore) {

        String TAG = "getDifficultyLevel()";

        Log.e(TAG, "bktScore: " + bktScore);

        Log.e(TAG, "Hard: " + hard);
        Log.e(TAG, "Hard: " + medium);
        Log.e(TAG, "Hard: " + easy);

        if (bktScore >= hard)
            // 0.67 ~ positive infinity
            return e_Question.Difficulty.HARD;
        else if (bktScore >= medium)
            // 0.34 ~ 0.66
            return e_Question.Difficulty.MEDIUM;
        else
            // negative infinity ~ 0.33
            return e_Question.Difficulty.EASY;

    }

//    public static void setBKTCategory(String category) {
//
//        switch (category) {
//            case "Novice":
//                learnRate = 0.3;
//                slipRate = 0.3;
//                guessRate = 0.3;
//                forgetRate = 0.1;
//                softeningRate = 0.9;
//                knowledgeProbability = 0.1;  // Initial probability for Novice
//                break;
//            case "Beginner":
//                learnRate = 0.4;
//                slipRate = 0.2;
//                guessRate = 0.2;
//                forgetRate = 0.05;
//                softeningRate = 0.8;
//                knowledgeProbability = 0.3;  // Initial probability for Beginner
//                break;
//            case "Intermediate":
//                learnRate = 0.5;
//                slipRate = 0.1;
//                guessRate = 0.1;
//                forgetRate = 0.03;
//                softeningRate = 0.7;
//                knowledgeProbability = 0.5;  // Initial probability for Intermediate
//                break;
//            case "Advanced":
//                learnRate = 0.7;
//                slipRate = 0.05;
//                guessRate = 0.05;
//                forgetRate = 0.02;
//                softeningRate = 0.6;
//                knowledgeProbability = 0.7;  // Initial probability for Advanced
//                break;
//            case "Expert":
//                learnRate = 0.9;
//                slipRate = 0.05;
//                guessRate = 0.05;
//                forgetRate = 0.01;  // Experts rarely forget
//                softeningRate = 0.5;
//                knowledgeProbability = 0.9;  // Initial probability for Expert
//                break;
//            default:
//                throw new IllegalArgumentException("Invalid category: " + category);
//        }
//
//        String TAG = "Set BKT Category";
//
//        Log.d(TAG, "Category: " + category);
//        Log.d(TAG, "Learn Rate: " + learnRate);
//        Log.d(TAG, "Slip Rate: " + slipRate);
//
//    }

    public static void setBKTCategory(String category) {

        switch (category) {
            case "Novice":
                // Increase learn rate and reduce slip rate for easier learning
                learnRate = 0.4;  // Increased learn rate for better progress
                slipRate = 0.25;  // Lower slip rate to prevent forgetting too quickly
                guessRate = 0.3;  // Maintain guessing rate
                forgetRate = 0.1; // Keep forget rate for Novice, slightly higher
                softeningRate = 0.8; // Reduced softening to allow some error correction
                knowledgeProbability = 0.1;  // Start with low knowledge probability
                break;
            case "Beginner":
                // Increase learn rate and adjust slip rate for smoother progress
                learnRate = 0.5;  // Increase learn rate for better knowledge gain
                slipRate = 0.2;   // Moderate slip rate to balance learning and forgetting
                guessRate = 0.25; // Reduce guessing rate to ensure more accurate answers
                forgetRate = 0.07; // Reduce forget rate slightly
                softeningRate = 0.75; // Moderate softening for more forgiving incorrect answers
                knowledgeProbability = 0.3;  // Start with a higher initial probability
                break;
            case "Intermediate":
                learnRate = 0.5;  // Maintain a moderate learning rate
                slipRate = 0.1;   // Low slip rate for stable retention
                guessRate = 0.1;  // Keep low guess rate for more accurate answers
                forgetRate = 0.03; // Low forget rate for Intermediate level
                softeningRate = 0.7; // Maintain moderate softening rate
                knowledgeProbability = 0.5;  // Higher probability as user progresses
                break;
            case "Advanced":
                learnRate = 0.7;  // Keep high learning rate for faster mastery
                slipRate = 0.05;  // Very low slip rate for minimal forgetting
                guessRate = 0.05; // Low guess rate, expert-level accuracy
                forgetRate = 0.02; // Very low forget rate for advanced users
                softeningRate = 0.6; // Gentle softening for minor errors
                knowledgeProbability = 0.7;  // High initial knowledge probability
                break;
            case "Expert":
                learnRate = 0.9;  // Maximal learning rate for expert learners
                slipRate = 0.05;  // Minimal slip rate
                guessRate = 0.05; // Minimal guess rate for near-perfect knowledge
                forgetRate = 0.01;  // Experts rarely forget
                softeningRate = 0.5; // Least softening for expert learners
                knowledgeProbability = 0.9;  // Near-max probability for experts
                break;
            default:
                throw new IllegalArgumentException("Invalid category: " + category);
        }

        String TAG = "Set BKT Category";
        Log.d(TAG, "Category: " + category);
        Log.d(TAG, "Learn Rate: " + learnRate);
        Log.d(TAG, "Slip Rate: " + slipRate);
    }


    public void setBKTParameters(e_Question.Difficulty difficulty) {
        // Adjust BKT Model parameters based on difficulty level
        double pInit, pTransit, pSlip, pGuess;

        switch (difficulty) {
            case EASY:
                // Set parameters for EASY difficulty
                pInit = 0.5;    // Example initial probability of knowledge
                pTransit = 0.2; // Example probability of learning the skill after practice
                pSlip = 0.1;    // Example probability of making a mistake despite knowing the skill
                pGuess = 0.4;   // Example probability of guessing the correct answer without knowing the skill
                Log.d("f_post_test", "BKT Difficulty Level: Easy | " + difficulty);
                break;
            case MEDIUM:
                // Set parameters for MEDIUM difficulty
                // Example values for medium difficulty
                pInit = 0.4;
                pTransit = 0.3;
                pSlip = 0.15;
                pGuess = 0.3;
                Log.d("f_post_test", "(pInit:"+pInit+"pTransit:"+pTransit+"pSlip:"+pSlip+"pGuess:"+pGuess);
                Log.d("f_post_test", "BKT Difficulty Level: Medium | " + difficulty);
                break;
            case HARD:
                // Set parameters for HARD difficulty
                pInit = 0.3;
                pTransit = 0.4;
                pSlip = 0.2;
                pGuess = 0.2;
                Log.d("f_post_test", "(pInit:"+pInit+"pTransit:"+pTransit+"pSlip:"+pSlip+"pGuess:"+pGuess);
                Log.d("f_post_test", "BKT Difficulty Level: Hard | " + difficulty);
                break;
            default:
                // Fallback to default values
                pInit = 0.3;
                pTransit = 0.2;
                pSlip = 0.1;
                pGuess = 0.4;
                Log.d("f_post_test", "(pInit:"+pInit+"pTransit:"+pTransit+"pSlip:"+pSlip+"pGuess:"+pGuess);
                Log.d("f_post_test", "BKT Difficulty Level: Default | " + difficulty);
                break;
        }
        String TAG = "Parameter Check";

        Log.e(TAG, "Difficulty: " + difficulty);
        Log.e(TAG, "pInit: " + pInit);
        Log.e(TAG, "pTransit: " + pTransit);
        Log.e(TAG, "pSlip: " + pSlip);
        Log.e(TAG, "pGuess: " + pGuess);

        // Initialize BKT Model instance with the parameters based on difficulty
        x_bkt_algorithm.getInstance(pInit, pTransit, pSlip, pGuess);
    }

    public static x_bkt_algorithm getInstance(double pInit, double learnRate, double forgetRate, double slip) {
        if (instance == null) {
            instance = new x_bkt_algorithm(pInit, learnRate, forgetRate, slip);
        }
        return instance;
    }

//    public void initializeBKTScores(String collectionPath, String documentName, String module, BKTCallback callback) {
//        Log.d(TAG, "initializeBKTScores: Start - Attempting to retrieve document from path: " + collectionPath + "/" + documentName);
//
//        Log.d(TAG, "collectionPath: " + collectionPath);
//        Log.d(TAG, "documentName: " + documentName);
//        Log.d(TAG, "module: " + module);
//        Log.d(TAG, "callback: " + callback);
//
//
//        Log.d(TAG, "path: " + "users/"+userId+"/"+collectionPath+"/"+documentName+"/"+module+"/BKT Score");
//
////        FirebaseFirestore.setLoggingEnabled(true);
//
//        db.collection("users") // Mother Folder
//                .document(userId) // Unique ID
//                .collection(collectionPath) // Learning Mode
//                .document(documentName) // Lesson [1 -> 8]
//                .get()
//                .addOnSuccessListener(documentSnapshot -> {
//
//                    Log.d(TAG, "Document retrieved successfully: " + documentSnapshot.getData());
//
//                    if (documentSnapshot.exists()) {
//                        Log.d(TAG, "Document Data: " + documentSnapshot.getData());
//                        Map<String, Object> moduleMap = (Map<String, Object>) documentSnapshot.get(module);
//                        if (moduleMap != null) {
//                            Log.d(TAG, "Module found: " + moduleMap);
//                            if (moduleMap.containsKey("BKT Score")) {
//
//                                Object bktScoreObject = moduleMap.get("BKT Score");
//
//                                Double bktScore = null;
//                                if (bktScoreObject instanceof Long) {
//                                    bktScore = ((Long) bktScoreObject).doubleValue();  // Convert Long to Double
//                                } else if (bktScoreObject instanceof Double) {
//                                    bktScore = (Double) bktScoreObject;
//                                }
//                                Log.d(TAG, "BKT Score for " + module + ": " + bktScore);
//                            } else {
//                                Log.e(TAG, "No BKT Score found in module " + module);
//                            }
//                        } else {
//                            Log.e(TAG, "Module map is null for module: " + module);
//                        }
//                    } else {
//                        Log.e(TAG, "Document does not exist at path: " + collectionPath + "/" + documentName);
//                    }
//
//                })
//                .addOnFailureListener(e -> {
//                    Log.e(TAG, "Error retrieving document from path: " + collectionPath + "/" + documentName, e);
//                    callback.onBKTRetrieved(null);
//                });
//
//
//        Log.d(TAG, "initializeBKTScores: End - Firestore request has been initiated, waiting for callback.");
//    }

    public void initializeBKTScores(String collectionPath, String documentName, String module) {
        String TAG = "WARNING";

        db.collection("users")
                .document(userId)
                .collection(collectionPath) // This should be "Progressive Mode"
                .document(documentName) // This should be "Lesson n"
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();

                            if (document.exists()) {
                                // Retrieve all data from the document as a Map
                                Map<String, Object> lessonData = document.getData();

                                if (lessonData != null) {
                                    // Check if the specific module exists in the lesson data
                                    if (lessonData.containsKey(module)) {
                                        Object moduleValue = lessonData.get(module);

                                        // Check if the value is a Map
                                        if (moduleValue instanceof Map) {
                                            Map<String, Object> moduleData = (Map<String, Object>) moduleValue;

                                            isLessonPassed = false;
                                            isLessonCompleted = false;

                                            if (moduleData.containsKey("BKT Score") && moduleData.containsKey("Progress")) {

                                                Double bktScore;
                                                Object bktScoreObject = moduleData.get("BKT Score");
                                                if (bktScoreObject instanceof Long) {
                                                    bktScore = ((Long) bktScoreObject).doubleValue(); // Convert Long to Double
                                                } else if (bktScoreObject instanceof Double) {
                                                    bktScore = (Double) bktScoreObject; // Use Double directly
                                                } else {
                                                    bktScore = 0.0; // Default or handle as needed
                                                    Log.e(TAG, "Unexpected type for BKT Score: " + bktScoreObject.getClass().getName());
                                                }

                                                Double progress = moduleData.get("Progress") instanceof Number
                                                        ? ((Number) moduleData.get("Progress")).doubleValue()
                                                        : 0.0; // Use default if Progress is missing or not a number

                                                String module_lesson = module + "_" + documentName;
                                                int steps = L_lesson_sequence.getNumberOfSteps(module_lesson);

                                                isLessonPassed = bktScore > b_main_0_menu_categorize_user.passingGrade;
                                                isLessonCompleted = progress >= steps;

                                                resetModule(isLessonCompleted, isLessonPassed);

                                                Log.d(TAG, "Module: " + module + " | BKT Score: " + bktScore);
                                            } else {
                                                Log.e(TAG, "No BKT Score found for module: " + module);
                                            }

                                        } else {
                                            Log.e(TAG, "Value for module is not a Map: " + module);
//                                            callback.onBKTRetrieved(null);
                                        }
                                    } else {
                                        Log.e(TAG, "Module does not exist in lesson data: " + module);
//                                        callback.onBKTRetrieved(null);
                                    }
                                } else {
                                    Log.e(TAG, "Lesson data is null.");
//                                    callback.onBKTRetrieved(null);
                                }
                            } else {
                                Log.e(TAG, "Document does not exist at path: " + collectionPath + "/" + documentName);
//                                callback.onBKTRetrieved(null);
                            }
                        } else {
                            Log.e(TAG, "Failed to retrieve document: ", task.getException());
//                            callback.onBKTRetrieved(null);
                        }
                    }
                });
    }

    private void resetModule(boolean isLessonCompleted, boolean isLessonPassed) {

        {


            // Reset only if di pa tapos yung lesson..
            // kelangan completed sya, pero dapat pasado din
            // Case 1: Completed, not Passed - RETAKE
            // Case 2: Completed, Passed - OK (do not reset)
            // Case 3: InComplete, not Passed - RETAKE
            // Case 4: InComplete, Passed - RETAKE (pwede kasi siyang passed if mataas ang Pre-Test)

            int moduleIndex = Integer.parseInt(String.valueOf(d_Lesson_container.currentModule.charAt(1)));
            int lessonIndex = Integer.parseInt(String.valueOf(d_Lesson_container.currentLesson.charAt(7)));

            boolean isProgressiveMode = d_Lesson_container.learningMode.equalsIgnoreCase("Progressive Mode");

            // Reset logic based on completion and passing status
            if (isLessonCompleted) {
                if (!isLessonPassed) {
                    // Case 1: Completed, not Passed - RETAKE
                    Log.e("TAG", "Case 1: Completed, not Passed - RETAKE");
                    c_Lesson_feedback.resetResult();
                    resetScore(moduleIndex, lessonIndex, isProgressiveMode);
//                                showToast("You completed the lesson but did not pass, please retake the lesson");
                } else {
                    Log.e("TAG", "Case 2: Completed, Passed - OK (do nothing)");
                    // Case 2: Completed, Passed - OK (do nothing)
//                                showToast("You completed the lesson and passed! Great job!");
                }
            } else {
                // If the lesson is incomplete
                if (!isLessonPassed) {
                    Log.e("TAG", "Case 3: Incomplete, not Passed - RETAKE");
                    // Case 3: Incomplete, not Passed - RETAKE
                    c_Lesson_feedback.resetResult();
                    resetScore(moduleIndex, lessonIndex, isProgressiveMode);
//                                showToast("you completed the lesson but failed, please retake it.");
                } else {
                    Log.e("TAG", "Case 4: Incomplete, Passed - RETAKE (possible because of high Pre-Test)");
                    // Case 4: Incomplete, Passed - RETAKE (possible because of high Pre-Test)
                    c_Lesson_feedback.resetResult();
                    resetScore(moduleIndex, lessonIndex, isProgressiveMode);
//                                showToast("You did not complete and failed the lesson, please retake it.");
                }
            }

        }

    }

    // RECOMMENDED BY ChatGPT 4o Plus
//    public void initializeBKTScores(String collectionPath, String documentName, String module, BKTCallback callback) {
//        Log.d(TAG, "Attempting to retrieve document from path: " +
//                "users/" + userId + "/" + collectionPath + "/" + documentName);
//
//        Log.e(TAG, "MARJON");
//
//        // Testing ko
////        db.collection("users")
////                .document(userId)
////                .collection(collectionPath)
////                .document(documentName)
////                .get()
////                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
////                    @Override
////                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
////                        String TAG = "WARNING";
////                        if (task.isSuccessful()) {
////                            DocumentSnapshot document = task.getResult();
////                            if (document.exists()) {
////                                if ()
////                            }
////                        }
////                    }
////                })
//
//
//
//
//                // Original code
////                .addOnSuccessListener(documentSnapshot -> {
////                    if (documentSnapshot.exists()) {
////                        // Retrieve the BKT Scores as a Map
////                        Map<String, Double> bktScoresMap = (Map<String, Double>) documentSnapshot
////                                .get(module + ".BKT Scores"); // walang tuldok talaga to?
////
////                        if (bktScoresMap != null) {
////                            // Convert the map to a list
////                            bktScores = new ArrayList<>(bktScoresMap.values());
////                            Log.e(TAG, "YEHEY I GOT IT!");
////                            Log.e(TAG, "bktScores: " + bktScores);
////                        } else {
////                            bktScores = new ArrayList<>();  // Initialize an empty list if null
////                            Log.e(TAG, "I failed to retrieve it :(");
////                            Log.e(TAG, "bktScores: " + bktScores);
////                        }
////
////                        callback.onBKTRetrieved(bktScores);
////                    } else {
////                        Log.e(TAG, "Document does not exist at path: " + collectionPath + "/" + documentName);
////                        callback.onBKTRetrieved(null);
////                    }
////                })
////                .addOnFailureListener(e -> {
////                    Log.e(TAG, "Error retrieving BKT Scores", e);
////                    callback.onBKTRetrieved(null);
////                });
//    }

    public static void resetScore(int moduleIndex, int lessonIndex,
                            boolean isProgressiveMode) {

        String TAG = "BKT | resetScore()";

        Log.e(TAG, " resetScore() method is called!");

//        isLessonFinished = true;

        if (
//                bktScores == null ||
                moduleIndex < 0 ||
                lessonIndex < 0) {
            Log.e(TAG, "Invalid BKT score update request");
            Log.e(TAG, "moduleIndex: " + moduleIndex);
            Log.e(TAG, "lessonIndex: " + lessonIndex);
            return;
        }

        // wala to sa Reset Score method, kasi direct na yung values na fini-feed dito.
//        moduleIndex += 1;
//        lessonIndex += 1;

        // Determine the correct collection based on the learning mode
        String collectionPath = isProgressiveMode ? "Progressive Mode" : "Free Use Mode";
        String documentName = "Lesson " + lessonIndex;

        Log.e(TAG, "moduleIndex: " + moduleIndex);
        Log.e(TAG, "lessonIndex: " + lessonIndex);

        // Construct the field path using dot notation
        String moduleFieldPath = "M" + moduleIndex + ".BKT Score";
        String progressFieldPath = "M" + moduleIndex + ".Progress";
        String preTestFieldPath = "M" + moduleIndex + ".Pre-Test Score";
        String postTestFieldPath = "M" + moduleIndex + ".Post-Test Score";

        Log.e(TAG, "Field path for update: " + moduleFieldPath + " | Lesson " + lessonIndex);

        Map<String, Object> updates = new HashMap<>();

        // My own code:
        updates.put(moduleFieldPath, 0);
        updates.put(progressFieldPath, 0);
        updates.put(preTestFieldPath, 0);
        updates.put(postTestFieldPath, 0);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Log.e(TAG, "ABDUL path: users/"+userId+"/"+collectionPath+"/"+documentName+"/"+updates);

        int finalModuleIndex = moduleIndex;

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(userId)
                .collection(collectionPath)
                .document(documentName)
                .update(updates)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "M" + finalModuleIndex + " M fields successfully updated"))
                .addOnFailureListener(e -> Log.e(TAG, "Error updating M" + finalModuleIndex + " M fields", e));
    }

    public interface ScoreCallback {
        void onScoreRetrieved(float score);
    }

    public static void getBKTScore(
            int moduleIndex, int lessonIndex,
            boolean isProgressiveMode,
            ScoreCallback callback
    ) {
        String TAG = "BKT | getBKTScore()";

        if (
                moduleIndex < 0 ||
                        lessonIndex < 0) {
            Log.e(TAG, "Invalid BKT score update request");
            Log.e(TAG, "moduleIndex: " + moduleIndex);
            Log.e(TAG, "lessonIndex: " + lessonIndex);
            callback.onScoreRetrieved(0); // Return 0 via callback
            return;
        }

//        moduleIndex += 1;

        // Determine the correct collection based on the learning mode
        String collectionPath = isProgressiveMode ? "Progressive Mode" : "Free Use Mode";
        String documentName = "Lesson " + lessonIndex;

        Log.e(TAG, "moduleIndex: " + moduleIndex);
        Log.e(TAG, "lessonIndex: " + lessonIndex);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Construct the field path using dot notation
        String moduleFieldPath = "M" + moduleIndex + ".BKT Score";

        Log.e(TAG, "Field path for update: " + moduleFieldPath + " | Lesson " + lessonIndex);


        Log.e(TAG, "Path: " + "users/"+userId+"/"+collectionPath+"/"+documentName+"/"+moduleFieldPath);

        db.collection("users").document(userId)
                .collection(collectionPath)
                .document(documentName)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Double scoreValue = documentSnapshot.getDouble(moduleFieldPath);
                        float score = (scoreValue != null) ? scoreValue.floatValue() : 0f;
                        Log.d(TAG, "Retrieved BKT Score: " + score);
                        callback.onScoreRetrieved(score); // Return score via callback
                    } else {
                        Log.e(TAG, "Document does not exist");
                        callback.onScoreRetrieved(0f); // Return 0 if document doesn't exist
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error getting document", e);
                    callback.onScoreRetrieved(0f); // Return 0 on error
                });
    }

    public void updateScore(
            int moduleIndex, int lessonIndex,
            boolean isProgressiveMode,
            boolean isCorrect
    ) {

        String TAG = "BKT | updateScore()";

//        updateKnowledge(isCorrect); // Call to update knowledge based on correctness
        updateKnowledgeProbability(isCorrect); // eto ba dapat??

        if (
//                bktScores == null ||
            moduleIndex < 0 ||
            lessonIndex < 0) {
                Log.e(TAG, "Invalid BKT score update request");
                Log.e(TAG, "bktScores: " + bktScores);
                Log.e(TAG, "moduleIndex: " + moduleIndex);
                Log.e(TAG, "lessonIndex: " + lessonIndex);
                return;
        }

        Log.e("updateScore", "Category: " + category);
        Log.e("updateScore", "pKnow: " + knowledgeProbability);

        moduleIndex += 1;
        lessonIndex += 1;

        // Determine the correct collection based on the learning mode
        String collectionPath = isProgressiveMode ? "Progressive Mode" : "Free Use Mode";
        String documentName = "Lesson " + lessonIndex;

        Log.e(TAG, "moduleIndex: " + moduleIndex);
        Log.e(TAG, "lessonIndex: " + lessonIndex);

        // Construct the field path using dot notation
        String moduleFieldPath = "M" + moduleIndex + ".BKT Score";

        Log.e(TAG, "Field path for update: " + moduleFieldPath + " | Lesson " + lessonIndex);

        Map<String, Object> updates = new HashMap<>();

        Log.e(TAG, "Knowledge Probability: " + knowledgeProbability);

        // My own code:
        updates.put(moduleFieldPath, knowledgeProbability);


//        // Original Code
//        updates.put(moduleFieldPath, newScore); // Update only the specific field

        Log.e(TAG, "ABDUL path: users/"+userId+"/"+collectionPath+"/"+documentName+"/"+updates);

        int finalModuleIndex = moduleIndex;

        db.collection("users").document(userId)
                .collection(collectionPath)
                .document(documentName)
                .update(updates)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "M" + finalModuleIndex + " M fields successfully updated"))
                .addOnFailureListener(e -> Log.e(TAG, "Error updating M" + finalModuleIndex + " M fields", e));
    }

    public static void updateTestScore(boolean learningMode,
                                       int moduleIndex, int lessonIndex,
                                       String testMode, int score) {

        String TAG = "BKT | updateTestSCore()";

        if (moduleIndex < 0 || lessonIndex < 0) {
            Log.e(TAG, "Invalid module or lesson index");
            return;
        }

        // Determine the correct collection based on the learning mode
        String collectionPath = learningMode ? "Progressive Mode" : "Free Use Mode";

        // Increment indices for display purposes
        lessonIndex += 1;
        moduleIndex += 1;

        Log.e(TAG, "moduleIndex: " + moduleIndex);
        Log.e(TAG, "lessonIndex: " + lessonIndex);

        String documentName = "Lesson " + lessonIndex; // Corrected to use incremented lessonIndex

        // Determine the correct test score field name
        String testField = null;
        if (testMode.equals("Pre-Test")) {
            testField = "Pre-Test Score";
        } else if (testMode.equals("Post-Test")) {
            testField = "Post-Test Score";
        } else {
            Log.e(TAG, "Invalid test mode");
            return; // Exit if the test mode is invalid
        }

        // Construct the field path using dot notation
        String moduleFieldPath = "M" + moduleIndex + "." + testField;

        Log.e(TAG, "Field path for update: " + moduleFieldPath + " | Lesson " + lessonIndex);

        Map<String, Object> updates = new HashMap<>();
        updates.put(moduleFieldPath, score); // Update only the specific field

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user != null ? user.getUid() : null;

        if (userId == null) {
            Log.e(TAG, "User not authenticated");
            return; // Exit if user is not authenticated
        }

        db.collection("users").document(userId)
                .collection(collectionPath)
                .document(documentName)
                .update(updates)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Test score successfully updated"))
                .addOnFailureListener(e -> Log.e(TAG, "Error updating test score", e));
    }

    // Level 1 [0.0 - 0.1] Novice
    // Level 2 [0.1 - 0.3] Beginner
    // Level 3 [0.3 - 0.5] Intermediate
    // Level 4 [0.5 - 0.7] Advanced
    // Level 5 [0.7 - 0.9] Expert

    // optimized version daw??
//    public void updateKnowledge(boolean correct) {
//
//        String TAG = "updateKnowledge";
//
//        Log.e(TAG, "Answer: " + correct);
//        Log.e(TAG, "Category: " + category);
//        Log.e(TAG, "Knowledge Probability: " + knowledgeProbability);
//        Log.e(TAG, "Slip Rate: " + slipRate);
//        Log.e(TAG, "Guess Rate: " + guessRate);
//
//        double newKnowledgeProbability;
//
//        if (correct) {
//            // Update probability of knowing the skill after a correct answer
//            newKnowledgeProbability = (knowledgeProbability * (1 - slipRate)) /
//                    (knowledgeProbability * (1 - slipRate) + (1 - knowledgeProbability) * guessRate);
//        } else {
//            // Update probability of knowing the skill after an incorrect answer
//            newKnowledgeProbability = (knowledgeProbability * slipRate) /
//                    (knowledgeProbability * slipRate + (1 - knowledgeProbability) * (1 - guessRate));
//        }
//
//        // Apply learning rate to update knowledge probability
//        knowledgeProbability = newKnowledgeProbability + (1 - newKnowledgeProbability) * learnRate;
//
//        Log.e("updateKnowledge", "Updated pKnow is: " + knowledgeProbability + " because Answer is: " + correct);
//
//        // Ensure knowledgeProbability stays within bounds [0, 1]
//        knowledgeProbability = Math.max(0, Math.min(1, knowledgeProbability));
//
//        Log.e("updateKnowledge", "Final knowledgeProbability: " + knowledgeProbability);
//    }


    public static double getKnowledge() {
        Log.e("getKnowledge()", "knowledgeProbability: " + knowledgeProbability);
        return knowledgeProbability;
    }

    public double getKnowledgeProbability() {
        return knowledgeProbability;
    }

    // Interface for callback when BKT scores are retrieved
    public interface BKTCallback {
        void onBKTRetrieved(List<Double> bktScores);
    }

    public void logScores() {
//        if (bktScores != null) {
//            Log.d(TAG, "BKT Scores:");
//            for (int i = 0; i < bktScores.size(); i++) {
//                Log.d(TAG, "Score " + i + ": " + bktScores.get(i));
//            }
//        } else {
//            Log.d(TAG, "BKT Scores are not initialized.");
//        }
    }

}
