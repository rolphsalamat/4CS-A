package com.example.autotutoria20;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class c_Lesson_feedback {

    // Feedback ranges
    private final float very_bad = .20F;
    private final float bad = .30F;
    private final float neutral = .50F;
    private final float good = .60F;
    private final float very_good = .70F;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance(); // FirebaseAuth instance

    // Retrieve BKTScore from the database
    public void retrieveBKTScore(String mode, String lesson) {
        // Get the current user
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid(); // Get the user ID

            // Build the Firestore path based on the mode and lesson
            db.collection("users")
                    .document(userId) // Use the current user's document
                    .collection(mode) // Use the selected mode (Progressive Mode or Free Use Mode)
                    .document(lesson) // Use the lesson name or number
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    // Retrieve BKT Scores map
                                    Map<String, Object> bktScores = (Map<String, Object>) document.get("BKT Scores");
                                    if (bktScores != null) {
                                        // Analyze the BKTScore after all modules are retrieved
                                        BKTScoreAnalyzer(bktScores);
                                    } else {
                                        Log.d("LessonFeedback", "No BKT Scores found for lesson: " + lesson);
                                    }
                                } else {
                                    Log.d("LessonFeedback", "No such document for lesson: " + lesson);
                                }
                            } else {
                                Log.e("LessonFeedback", "Error retrieving BKT Scores: ", task.getException());
                            }
                        }
                    });
        } else {
            Log.e("LessonFeedback", "User is not logged in.");
        }
    }

    // Analyze the BKTScore
    private void BKTScoreAnalyzer(Map<String, Object> bktScores) {
        List<String> feedbackList = new ArrayList<>();

        // Iterate through BKT Scores for each module and analyze
        for (Map.Entry<String, Object> entry : bktScores.entrySet()) {
            String module = entry.getKey();
            Double score = (Double) entry.getValue();

            String feedback;
            if (score < very_bad) {
                feedback = "Very Bad";
            } else if (score >= very_bad && score < bad) {
                feedback = "Bad";
            } else if (score >= bad && score < neutral) {
                feedback = "Neutral";
            } else if (score >= neutral && score < good) {
                feedback = "Good";
            } else if (score >= good) {
                feedback = "Very Good";
            } else {
                feedback = "Undefined";
            }

            // Store the feedback in the list in the format "Module: Feedback"
            feedbackList.add(module + ": " + feedback);
        }

        // Log all the feedback
        for (String feedback : feedbackList) {
            Log.d("LessonFeedback", feedback);
        }
    }

    // Show feedback dialog based on score
    private void showFeedbackDialog(float score, String feedback) {
        // Display the feedback to the user
        switch (feedback) {
            case "Very Bad":
                Log.d("LessonFeedback", "Score: " + score + " - Feedback: Very Bad");

                break;
            case "Bad":
                Log.d("LessonFeedback", "Score: " + score + " - Feedback: Bad");
                break;
            case "Neutral":
                Log.d("LessonFeedback", "Score: " + score + " - Feedback: Neutral");
                break;
            case "Good":
                Log.d("LessonFeedback", "Score: " + score + " - Feedback: Good");
                break;
            case "Very Good":
                Log.d("LessonFeedback", "Score: " + score + " - Feedback: Very Good");
                break;
            default:
                Log.d("LessonFeedback", "Score: " + score + " - Feedback: Undefined");
                break;
        }
    }

}
