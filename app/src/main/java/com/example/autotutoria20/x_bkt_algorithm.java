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

    // Singleton instance (if you need it to be a singleton)
    private static x_bkt_algorithm instance;

    private x_bkt_algorithm(double pInit, double learnRate, double forgetRate, double slip) {
        this.db = FirebaseFirestore.getInstance();
        this.userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.knowledgeProbability = pInit;
        this.learnRate = learnRate;
        this.forgetRate = forgetRate;
    }


//    Gamitin nalang to kapag napag usapan na yung grading system???

//    public void initializeBKTModel(String userLevel) {
//        double pInit, learnRate, forgetRate, slip;
//
//        switch (userLevel) {
//            case "Beginner":
//                pInit = 0.2;
//                learnRate = 0.4;
//                forgetRate = 0.05;
//                slip = 0.2;
//                break;
//            case "Intermediate":
//                pInit = 0.5;
//                learnRate = 0.3;
//                forgetRate = 0.15;
//                slip = 0.1;
//                break;
//            case "Expert":
//                pInit = 0.8;
//                learnRate = 0.1;
//                forgetRate = 0.4;
//                slip = 0.05;
//                break;
//            default:
//                throw new IllegalArgumentException("Invalid user level: " + userLevel);
//        }
//
//        bktModel = x_bkt_algorithm.getInstance(pInit, learnRate, forgetRate, slip);
//    }


    public static x_bkt_algorithm getInstance(double pInit, double learnRate, double forgetRate, double slip) {
        if (instance == null) {
            instance = new x_bkt_algorithm(pInit, learnRate, forgetRate, slip);
        }
        return instance;
    }

    // Initialize BKT Scores method
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

//    public void initializeBKTScores(String collectionPath, String documentName, BKTCallback callback) {
//        Log.d(TAG, "Attempting to retrieve document from path: " + collectionPath + "/" + documentName);
//
//        db.collection("users").document(userId).collection(collectionPath).document(documentName)
//                .get()
//                .addOnSuccessListener(documentSnapshot -> {
//                    if (documentSnapshot.exists()) {
//                        bktScores = (List<Double>) documentSnapshot.get("BKT Scores");
//                        callback.onBKTRetrieved(bktScores);
//                    } else {
//                        Log.e(TAG, "Document does not exist at path: " + collectionPath + "/" + documentName);
//                        callback.onBKTRetrieved(null);
//                    }
//                })
//                .addOnFailureListener(e -> {
//                    Log.e(TAG, "Error retrieving BKT Scores", e);
//                    callback.onBKTRetrieved(null);
//                });
//    }

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
                .update("BKT Scores." + moduleIndex, newScore)  // Directly updates the score at the correct index
                .addOnSuccessListener(aVoid -> Log.d(TAG, "BKT Scores successfully updated in Firestore"))
                .addOnFailureListener(e -> Log.e(TAG, "Error updating BKT Scores in Firestore", e));
    }


    // Example BKT algorithm logic for updating knowledge
    public void updateKnowledge(boolean correct) {
        if (correct) {
            knowledgeProbability = knowledgeProbability * (1 - forgetRate) + (1 - knowledgeProbability) * learnRate;
        } else {
            knowledgeProbability = knowledgeProbability * forgetRate;
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
