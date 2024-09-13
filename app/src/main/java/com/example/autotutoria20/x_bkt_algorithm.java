package com.example.autotutoria20;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class x_bkt_algorithm {

    private static final String TAG = "x_bkt_algorithm";
    private FirebaseFirestore db;
    private String userId;
    private List<Double> bktScores;
    private double knowledgeProbability;
    private double learnRate;
    private double forgetRate;
    private double slipRate;

    // Feedback ranges
    double hard = 0.67;
    double medium = 0.34;
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

    // RECOMMENDED BY ChatGPT 4o Plus
    public void initializeBKTScores(String collectionPath, String documentName, BKTCallback callback) {
        Log.d(TAG, "Attempting to retrieve document from path: " + collectionPath + "/" + documentName);

        db.collection("users").document(userId).collection(collectionPath).document(documentName)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Retrieve the BKT Scores as a Map
                        Map<String, Double> bktScoresMap = (Map<String, Double>) documentSnapshot.get("BKT Scores");

                        if (bktScoresMap != null) {
                            // Convert the map to a list
                            bktScores = new ArrayList<>(bktScoresMap.values());
                        } else {
                            bktScores = new ArrayList<>();  // Initialize an empty list if null
                        }

                        callback.onBKTRetrieved(bktScores);
                    } else {
                        Log.e(TAG, "Document does not exist at path: " + collectionPath + "/" + documentName);
                        callback.onBKTRetrieved(null);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error retrieving BKT Scores", e);
                    callback.onBKTRetrieved(null);
                });
    }


    // Update BKT Scores after computation
    public void updateScore(int moduleIndex, int lessonIndex, double newScore, boolean isProgressiveMode) {
        if (bktScores == null || moduleIndex < 0 || lessonIndex < 0) {
            Log.e(TAG, "Invalid BKT score update request");
            return;
        }

        // Determine the correct collection based on the learning mode
        String collectionPath = isProgressiveMode ? "Progressive Mode" : "Free Use Mode";
        String documentName = "Lesson " + (lessonIndex + 1);

        Log.e(TAG, "moduleIndex: " + moduleIndex);
        Log.e(TAG, "lessonIndex: " + lessonIndex);

        // Fetch the relevant Firestore document directly and update the corresponding score
        db.collection("users").document(userId).collection(collectionPath).document(documentName)
                // may tuldok (.) talaga sa dulo ng "BKT Scores." ????
                .update("BKT Scores." + moduleIndex, newScore)  // Directly updates the score at the correct index
                .addOnSuccessListener(aVoid -> Log.d(TAG, "BKT Scores successfully updated in Firestore"))
                .addOnFailureListener(e -> Log.e(TAG, "Error updating BKT Scores in Firestore", e));
    }


    // Example BKT algorithm logic for updating knowledge
    // ETO YUNG ORIGINAL CODE
//    public void updateKnowledge(boolean correct) {
//        if (correct) {
//            knowledgeProbability = knowledgeProbability * (1 - forgetRate) + (1 - knowledgeProbability) * learnRate;
//        } else {
//            knowledgeProbability = knowledgeProbability * forgetRate;
//        }
//    }

    // NEW CODE *suggested by ChatGPT 4o Plus
    public void updateKnowledge(boolean correct) {
        if (correct) {
            knowledgeProbability = (knowledgeProbability * (1 - forgetRate)) + ((1 - knowledgeProbability) * learnRate);
        } else {
            knowledgeProbability = (knowledgeProbability * forgetRate * slipRate);
        }
    }


    public double getKnowledgeProbability() {
        return knowledgeProbability;
    }

    // Interface for callback when BKT scores are retrieved
    public interface BKTCallback {
        void onBKTRetrieved(List<Double> bktScores);
    }

    public void logScores() {
        if (bktScores != null) {
            Log.d(TAG, "BKT Scores:");
            for (int i = 0; i < bktScores.size(); i++) {
                Log.d(TAG, "Score " + i + ": " + bktScores.get(i));
            }
        } else {
            Log.d(TAG, "BKT Scores are not initialized.");
        }
    }

}
