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

    private Map<String, Object> previousState = new HashMap<>();
    private Map<String, Object> currentState = new HashMap<>();


    static Double preTestBKTScore;
    static Double preTestScore;
    static Double postTestBKTScore;
    static Double postTestScore;


    private static final String TAG = "x_bkt_algorithm";
    private Context context;
    static String userCategory;
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
    private static double baseGain;
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
                        userCategory = category;
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


    public static void setBKTCategory(String category, double knowledgeGain, double retention, double mastery) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }

        // Weights for performance criteria
        double learnRateWeight = 0.5;  // Knowledge gain is the most significant factor
        double slipRateWeight = 0.3;  // Mastery inversely impacts slip rate
        double guessRateWeight = 0.2; // Guessing is least affected by mastery

        // Compute weighted parameters
        double weightedLearnRate = (knowledgeGain * learnRateWeight) + (mastery * 0.3) + (retention * 0.2);
        double weightedSlipRate = 1.0 - mastery; // Slip rate inversely proportional to mastery
        double weightedGuessRate = 0.5 - (knowledgeGain * guessRateWeight); // Guess rate reduces as knowledge increases
        double weightedForgetRate = 1.0 - retention; // Retention inversely impacts forgetting

        // Assign parameters based on category
        switch (category) {
            case "Novice":
                learnRate = Math.min(0.6, weightedLearnRate); // Cap learning rate for beginners
                slipRate = Math.max(0.15, weightedSlipRate);  // Maintain a baseline slip rate
                guessRate = Math.max(0.4, weightedGuessRate); // Reflects guessing tendency
                forgetRate = Math.min(0.02, weightedForgetRate); // Caps novice forget rate
                knowledgeProbability = 0.4; // Fixed starting knowledge
                break;

            case "Beginner":
                learnRate = Math.min(0.75, weightedLearnRate);
                slipRate = Math.max(0.1, weightedSlipRate);
                guessRate = Math.max(0.3, weightedGuessRate);
                forgetRate = Math.min(0.01, weightedForgetRate);
                knowledgeProbability = 0.55;
                break;

            case "Intermediate":
                learnRate = Math.min(0.85, weightedLearnRate);
                slipRate = Math.max(0.08, weightedSlipRate);
                guessRate = Math.max(0.2, weightedGuessRate);
                forgetRate = Math.min(0.008, weightedForgetRate);
                knowledgeProbability = 0.7;
                break;

            case "Advanced":
                learnRate = Math.min(0.9, weightedLearnRate);
                slipRate = Math.max(0.05, weightedSlipRate);
                guessRate = Math.max(0.1, weightedGuessRate);
                forgetRate = Math.min(0.005, weightedForgetRate);
                knowledgeProbability = 0.85;
                break;

            case "Expert":
                learnRate = Math.min(0.95, weightedLearnRate);
                slipRate = Math.max(0.02, weightedSlipRate);
                guessRate = Math.max(0.05, weightedGuessRate);
                forgetRate = Math.min(0.001, weightedForgetRate);
                knowledgeProbability = 0.95;
                break;

            default:
                throw new IllegalArgumentException("Invalid category: " + category);
        }

        // Debug log for confirmation
        Log.d("Set BKT Category", "Category: " + category);
        Log.d("Set BKT Category", "Learn Rate: " + learnRate);
        Log.d("Set BKT Category", "Slip Rate: " + slipRate);
        Log.d("Set BKT Category", "Guess Rate: " + guessRate);
        Log.d("Set BKT Category", "Forget Rate: " + forgetRate);
        Log.d("Set BKT Category", "Knowledge Probability: " + knowledgeProbability);
    }

    public static double calculateKnowledgeGain(double preTestScore, double postTestScore) {
        return Math.max(0, (postTestScore - preTestScore) / 100.0); // Normalize to [0, 1]
    }

    public static double calculateRetention(double previousSessionScore, double currentSessionScore) {
        return Math.max(0, Math.min(1, currentSessionScore / previousSessionScore)); // Ratio in [0, 1]
    }

    public static double calculateMastery(int correctAnswers, int totalQuestions) {
        return Math.max(0, Math.min(1, (double) correctAnswers / totalQuestions)); // Ratio in [0, 1]
    }




    public static void setBKTCategory(String category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }

        switch (category) {
            case "Novice":
                learnRate = 0.75;          // Adjusted for slower novice learning
                slipRate = 0.15;           // Slightly higher slip rate for early learners
                guessRate = 0.50;          // Higher guess rate reflecting uncertainty
                forgetRate = 0.010;        // Slightly higher forget rate for novices
                baseGain = 0.15;           // Encouraging improvement
                knowledgeProbability = 0.4; // Lower starting knowledge
                break;

            case "Beginner":
                learnRate = 0.80;          // Steady learning rate
                slipRate = 0.10;           // Reduced slip rate
                guessRate = 0.40;          // Reflects improving confidence
                forgetRate = 0.007;        // Reduced forget rate
                baseGain = 0.12;           // Encourages improvement
                knowledgeProbability = 0.55; // Slightly higher knowledge
                break;

            case "Intermediate":
                learnRate = 0.85;          // Balanced learning rate
                slipRate = 0.08;           // Lower slip rate
                guessRate = 0.30;          // Lower guess rate
                forgetRate = 0.005;        // Minimal forgetting
                baseGain = 0.10;           // Moderate improvement
                knowledgeProbability = 0.70; // Reflecting experience
                break;

            case "Advance":
            case "Advanced":
                learnRate = 0.90;          // High learning rate
                slipRate = 0.05;           // Very low slip rate
                guessRate = 0.20;          // Low guess rate
                forgetRate = 0.003;        // Minimal forgetting
                baseGain = 0.08;           // Incremental improvement
                knowledgeProbability = 0.85; // High starting knowledge
                break;

            case "Expert":
                learnRate = 0.95;          // Near-max learning rate
                slipRate = 0.02;           // Minimal slip rate
                guessRate = 0.10;          // Very low guess rate
                forgetRate = 0.001;        // Negligible forgetting
                baseGain = 0.05;           // Subtle fine-tuning
                knowledgeProbability = 0.95; // Reflects mastery
                break;

            default:
                throw new IllegalArgumentException("Invalid category: " + category);
        }

        // Debug log for confirmation
        Log.d("Set BKT Category", "Category: " + category);
        Log.d("Set BKT Category", "Learn Rate: " + learnRate);
        Log.d("Set BKT Category", "Slip Rate: " + slipRate);
        Log.d("Set BKT Category", "Guess Rate: " + guessRate);
        Log.d("Set BKT Category", "Forget Rate: " + forgetRate);
        Log.d("Set BKT Category", "Base Gain: " + baseGain);
        Log.d("Set BKT Category", "Knowledge Probability: " + knowledgeProbability);
    }



    public void updateKnowledgeProbability(boolean answeredCorrectly, int attempts) {

        double pKnow = knowledgeProbability;
        double adjustedLearnRate = learnRate * (1 + (Math.min(attempts, 10) / 15.0)); // Scale learn rate but cap at 10 attempts
        double streakBonus = answeredCorrectly ? 0.05 * Math.min(attempts, 5) : 0; // Bonus for streaks of correct answers

        if (answeredCorrectly) {
            double gain = (adjustedLearnRate * (1 - pKnow) * (1 - guessRate)) + baseGain + streakBonus;
            knowledgeProbability = pKnow + gain;
        } else {
            double penalty = (slipRate * (1 - pKnow)) / 2;
            knowledgeProbability = pKnow - penalty;
            knowledgeProbability *= (1 - forgetRate); // Apply forgetting only for incorrect answers
        }

        // Ensure the probability stays within valid bounds
        knowledgeProbability = Math.max(baseGain, Math.min(1.0, knowledgeProbability)); // Minimum raised to 0.3 for usability

        Log.d("Update Knowledge Probability", "Updated Knowledge Probability: " + knowledgeProbability);

    }



//    public void updateKnowledgeProbability(boolean answeredCorrectly) {
//        double pKnow = knowledgeProbability;
//
//        String TAG = "updateKnowledgeProbability";
//
//        Log.e(TAG, "Answer: " + answeredCorrectly);
//        Log.e(TAG, "knowledgeProbability (before): " + pKnow);
//        Log.e(TAG, "Learn Rate: " + learnRate);
//        Log.e(TAG, "Guess Rate: " + guessRate);
//        Log.e(TAG, "Slip Rate: " + slipRate);
//        Log.e(TAG, "Softening Rate: " + softeningRate);
//
//        if (answeredCorrectly) {
//            // Adjust the gain for lower categories (Novice/Beginner)
//            double gainMultiplier = (pKnow < 0.8) ? 1.5 : 1.0; // More gain for lower probabilities
//            double categoryMultiplier = getCategoryMultiplier(true);
//
//            double gain = learnRate * gainMultiplier * categoryMultiplier * (1 - pKnow) * (1 - guessRate);
//            knowledgeProbability = pKnow + Math.min(gain, 0.3); // Capped max gain
//        } else {
//            // Adjust penalty for higher categories (Intermediate/Advanced/Expert)
//            double penaltyMultiplier = (pKnow > 0.8) ? 1.2 : 0.8; // Stricter penalty for higher probabilities
//            double categoryMultiplier = getCategoryMultiplier(false);
//
//            double penalty = slipRate * softeningRate * penaltyMultiplier * categoryMultiplier;
//            knowledgeProbability = pKnow * (1 - penalty);
//        }
//
//        // Bound knowledgeProbability between 0.6 (minimum) and 1.0 (maximum)
//        knowledgeProbability = Math.max(0.6, Math.min(1.0, knowledgeProbability));
//
//        Log.d(TAG, "Updated Knowledge Probability: " + knowledgeProbability);
//    }




    private double getCategoryMultiplier(boolean isCorrect) {
        // Example multipliers based on category
        switch (b_main_0_menu_categorize_user.category) {
            case "Novice":
                return isCorrect ? 1.2 : 0.8; // Higher increase, lower decrease
            case "Beginner":
                return isCorrect ? 1.1 : 0.9;
            case "Intermediate":
                return isCorrect ? 1.0 : 1.0;
            case "Advanced":
                return isCorrect ? 0.9 : 1.1;
            case "Expert":
                return isCorrect ? 0.8 : 1.2; // Lower increase, higher decrease
            default:
                return 1.0; // Neutral multiplier for unhandled categories
        }
    }


    /*
    0.00 -> 0.33 [Easy]
    0.34 -> 0.66 [Medium]
    0.67 -> 1.00 [Hard]
     */

    public e_Question.Difficulty getDifficultyLevel(String category, double bktScore) {
        Log.d("getDifficultyLevel()", "Category: " + category + ", BKT Score: " + bktScore);

        switch (category) {

            case "Novice":
                if (bktScore < 0.450) { // Easy dominates for Novices
                    return e_Question.Difficulty.EASY;
                } else if (bktScore <= 0.650) { // Medium range for upper Novices
                    return e_Question.Difficulty.MEDIUM;
                } else { // Hard is rare for Novices
                    return e_Question.Difficulty.HARD;
                }

            case "Beginner":
                if (bktScore < 0.400) { // Easy questions for lower Beginners
                    return e_Question.Difficulty.EASY;
                } else if (bktScore <= 0.700) { // Medium dominates for most Beginners
                    return e_Question.Difficulty.MEDIUM;
                } else { // Hard questions for upper Beginners
                    return e_Question.Difficulty.HARD;
                }

            case "Intermediate":
                if (bktScore < 0.350) { // Few Easy questions for lower Intermediates
                    return e_Question.Difficulty.EASY;
                } else if (bktScore <= 0.650) { // Medium is common for most Intermediates
                    return e_Question.Difficulty.MEDIUM;
                } else { // Hard questions for upper Intermediates
                    return e_Question.Difficulty.HARD;
                }

            case "Advance":
            case "Advanced":
                if (bktScore < 0.300) { // Rare Easy questions for lower Advanced
                    return e_Question.Difficulty.EASY;
                } else if (bktScore <= 0.550) { // Medium for some Advanced users
                    return e_Question.Difficulty.MEDIUM;
                } else { // Hard dominates for Advanced users
                    return e_Question.Difficulty.HARD;
                }

            case "Expert":
                if (bktScore < 0.250) { // Very rare Easy questions for Experts
                    return e_Question.Difficulty.EASY;
                } else if (bktScore <= 0.400) { // Medium for lower Experts
                    return e_Question.Difficulty.MEDIUM;
                } else { // Hard is almost always assigned to Experts
                    return e_Question.Difficulty.HARD;
                }

            default:
                throw new IllegalArgumentException("Unknown category: " + category);
        }
    }




    // Original Code
//    public e_Question.Difficulty getDifficultyLevel(double bktScore) {
//
//        String TAG = "getDifficultyLevel()";
//
//        Log.e(TAG, "bktScore: " + bktScore);
//
//        Log.e(TAG, "Hard: " + hard);
//        Log.e(TAG, "Medium: " + medium);
//        Log.e(TAG, "Easy: " + easy);
//
//        if (bktScore >= hard)
//            // 0.67 ~ positive infinity
//            return e_Question.Difficulty.HARD;
//        else if (bktScore >= medium)
//            // 0.34 ~ 0.66
//            return e_Question.Difficulty.MEDIUM;
//        else
//            // negative infinity ~ 0.33
//            return e_Question.Difficulty.EASY;
//
//    }

    public static x_bkt_algorithm getInstance(double pInit, double learnRate, double forgetRate, double slip) {
        if (instance == null) {
            instance = new x_bkt_algorithm(pInit, learnRate, forgetRate, slip);
        }
        return instance;
    }

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
                                                int steps = t_LessonSequenceFromDatabase.getNumberOfSteps(module_lesson);

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
            boolean isCorrect,
            int attempt
    ) {

        String TAG = "BKT | updateScore()";

//        updateKnowledge(isCorrect); // Call to update knowledge based on correctness
        updateKnowledgeProbability(isCorrect, attempt); // eto ba dapat??

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
        updates.put(moduleFieldPath, (score+1)); // Update only the specific field

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

    public static void updateTestBKTScore(boolean learningMode,
                                       int moduleIndex, int lessonIndex,
                                       String testMode, double score) {

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
            testField = "Pre-Test BKT Score";
        } else if (testMode.equals("Post-Test")) {
            testField = "Post-Test BKT Score";
        } else {
            Log.e(TAG, "Invalid test mode");
            return; // Exit if the test mode is invalid
        }

        // Construct the field path using dot notation
        String moduleFieldPath = "M" + moduleIndex + "." + testField;

        Log.e(TAG, "Field path for update: " + moduleFieldPath + " | Lesson " + lessonIndex);

        Map<String, Object> updates = new HashMap<>();
        updates.put(moduleFieldPath, (score)); // Update only the specific field
        Log.i(TAG, "COLGATE | " + testField + ": " + score);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user != null ? user.getUid() : null;

        if (userId == null) {
            Log.e(TAG, "User not authenticated");
            return; // Exit if user is not authenticated
        }

        int finalModuleIndex = moduleIndex;

        if (testMode.equalsIgnoreCase("Post-Test")) {
            // Reference to the user's Firestore document
            db.collection("users").document(userId)
                    .collection(collectionPath)
                    .document(documentName)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            // Retrieve existing scores
                            Map<String, Object> moduleData = (Map<String, Object>) documentSnapshot.get("M" + finalModuleIndex);

                            preTestBKTScore = moduleData != null && moduleData.containsKey("Pre-Test BKT Score")
                                    ? Double.parseDouble(moduleData.get("Pre-Test BKT Score").toString())
                                    : 0.0f;

                            preTestScore = moduleData != null && moduleData.containsKey("Pre-Test Score")
                                    ? Double.parseDouble(moduleData.get("Pre-Test Score").toString())
                                    : 0.0f;

                            postTestBKTScore = score;

                            postTestScore = moduleData != null && moduleData.containsKey("Post-Test Score")
                                    ? Double.parseDouble(moduleData.get("Post-Test Score").toString())
                                    : 0.0f;

                            Log.d(TAG, "Retrieved Pre-Test BKT Score: " + preTestBKTScore);
                            Log.d(TAG, "Retrieved Pre-Test Score: " + preTestScore);
                            Log.d(TAG, "Retrieved Post-Test Score: " + postTestScore);

                        }
                    }).addOnFailureListener(e -> Log.e(TAG, "Error retrieving document", e));
        }

        db.collection("users").document(userId)
                .collection(collectionPath)
                .document(documentName)
                .update(updates)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Test score successfully updated"))
                .addOnFailureListener(e -> Log.e(TAG, "Error updating test score", e));
    }

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
        Log.i(TAG, "logScores()");
//        if (bktScores != null) {
//            Log.d(TAG, "BKT Scores:");
//            for (int i = 0; i < bktScores.size(); i++) {
//                Log.d(TAG, "Score " + i + ": " + bktScores.get(i));
//            }
//        } else {
//            Log.d(TAG, "BKT Scores are not initialized.");
//        }
    }

    public void updateLessonState(boolean isLessonComplete, double bktScore, double progress,
                                  double preTestScore, double postTestScore,
                                  int moduleIndex, int lessonIndex, boolean isProgressiveMode) {

        if (moduleIndex < 0 || lessonIndex < 0) {
            Log.e(TAG, "Invalid BKT score update request");
            return;
        }

        moduleIndex += 1; // Adjust to 1-based index
        lessonIndex += 1;

        // Determine the correct collection based on the learning mode
        String collectionPath = isProgressiveMode ? "Progressive Mode" : "Free Use Mode";
        String documentName = "Lesson " + lessonIndex;
        String moduleName = "M" + moduleIndex;

        Log.d(TAG, "Module: " + moduleName + ", Lesson: " + documentName);

        // Create Firestore references
        Map<String, Object> moduleUpdates = new HashMap<>();
        Map<String, Object> currentState = new HashMap<>();
        Map<String, Object> previousState = new HashMap<>();

        if (isLessonComplete) {
            // Prepare the current state
            currentState.put("BKT Score", bktScore);
            currentState.put("Progress", progress);
            currentState.put("Pre-Test Score", preTestScore);
            currentState.put("Post-Test Score", postTestScore);

            // Add "Current" to the module
            moduleUpdates.put(moduleName + ".Current", currentState);

            // Retrieve the "Previous" state if it exists
            db.collection("users").document(userId)
                    .collection(collectionPath)
                    .document(documentName)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            Map<String, Object> moduleData = (Map<String, Object>) documentSnapshot.get(moduleName);

                            if (moduleData != null && moduleData.containsKey("Current")) {
                                // Move the existing "Current" to "Previous"
                                previousState.putAll((Map<String, Object>) moduleData.get("Current"));
                                moduleUpdates.put(moduleName + ".Previous", previousState);
                            }
                        }

                        // Save the updated states
                        db.collection("users").document(userId)
                                .collection(collectionPath)
                                .document(documentName)
                                .update(moduleUpdates)
                                .addOnSuccessListener(aVoid -> Log.d(TAG, moduleName + " states updated successfully"))
                                .addOnFailureListener(e -> Log.e(TAG, "Error updating " + moduleName, e));
                    })
                    .addOnFailureListener(e -> Log.e(TAG, "Error retrieving module data for " + moduleName, e));

        } else {
            // Update "Previous" state
            previousState.put("BKT Score", bktScore);
            previousState.put("Progress", progress);
            previousState.put("Pre-Test Score", preTestScore);
            previousState.put("Post-Test Score", postTestScore);

            // Add "Previous" to the module
            moduleUpdates.put(moduleName + ".Previous", previousState);

            // Save the updated state
            db.collection("users").document(userId)
                    .collection(collectionPath)
                    .document(documentName)
                    .update(moduleUpdates)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, moduleName + " Previous state updated successfully"))
                    .addOnFailureListener(e -> Log.e(TAG, "Error updating " + moduleName, e));
        }
    }


}

//    public void updateScore(
//            int moduleIndex, int lessonIndex,
//            boolean isProgressiveMode,
//            boolean isCorrect,
//            int attempt
//    ) {
//
//        String TAG = "BKT | updateScore()";
//
////        updateKnowledge(isCorrect); // Call to update knowledge based on correctness
//        updateKnowledgeProbability(isCorrect, attempt); // eto ba dapat??
//
//        if (
////                bktScores == null ||
//                moduleIndex < 0 ||
//                        lessonIndex < 0) {
//            Log.e(TAG, "Invalid BKT score update request");
//            Log.e(TAG, "bktScores: " + bktScores);
//            Log.e(TAG, "moduleIndex: " + moduleIndex);
//            Log.e(TAG, "lessonIndex: " + lessonIndex);
//            return;
//        }
//
//        Log.e("updateScore", "Category: " + category);
//        Log.e("updateScore", "pKnow: " + knowledgeProbability);
//
//        moduleIndex += 1;
//        lessonIndex += 1;
//
//        // Determine the correct collection based on the learning mode
//        String collectionPath = isProgressiveMode ? "Progressive Mode" : "Free Use Mode";
//        String documentName = "Lesson " + lessonIndex;
//
//        Log.e(TAG, "moduleIndex: " + moduleIndex);
//        Log.e(TAG, "lessonIndex: " + lessonIndex);
//
//        // Construct the field path using dot notation
//        String moduleFieldPath = "M" + moduleIndex + ".BKT Score";
//
//        Log.e(TAG, "Field path for update: " + moduleFieldPath + " | Lesson " + lessonIndex);
//
//        Map<String, Object> updates = new HashMap<>();
//
//        Log.e(TAG, "Knowledge Probability: " + knowledgeProbability);
//
//        // My own code:
//        updates.put(moduleFieldPath, knowledgeProbability);
//
//
////        // Original Code
////        updates.put(moduleFieldPath, newScore); // Update only the specific field
//
//        Log.e(TAG, "ABDUL path: users/"+userId+"/"+collectionPath+"/"+documentName+"/"+updates);
//
//        int finalModuleIndex = moduleIndex;
//
//        db.collection("users").document(userId)
//                .collection(collectionPath)
//                .document(documentName)
//                .update(updates)
//                .addOnSuccessListener(aVoid -> Log.d(TAG, "M" + finalModuleIndex + " M fields successfully updated"))
//                .addOnFailureListener(e -> Log.e(TAG, "Error updating M" + finalModuleIndex + " M fields", e));
//    }
// }

