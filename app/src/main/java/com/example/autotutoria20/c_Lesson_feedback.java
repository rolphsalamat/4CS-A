package com.example.autotutoria20;

import android.util.Log;
import android.widget.Toast;

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

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance(); // FirebaseAuth instance
    private x_bkt_algorithm algo_feedback;

    // Constructor
    public c_Lesson_feedback() {
        // Initialize algo_feedback here
        algo_feedback = new x_bkt_algorithm();
    }

    // Retrieve BKTScore from the database
    public void retrieveBKTScore(String mode, String lesson) {

        Log.e("HI!", "I'm Here!");

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
//                                        while (bktScores != null) {
                                            // Analyze the BKTScore after all modules are retrieved
                                            BKTScoreAnalyzer(bktScores);
//                                        }
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
            if (score < algo_feedback.very_bad) {
                showFeedbackDialog(algo_feedback.very_bad_string);
                feedback = algo_feedback.very_bad_string;
            } else if (score >= algo_feedback.very_bad && score < algo_feedback.bad) {
                showFeedbackDialog(algo_feedback.bad_string);
                feedback = algo_feedback.bad_string;
            } else if (score >= algo_feedback.bad && score < algo_feedback.neutral) {
                showFeedbackDialog(algo_feedback.neutral_string);
                feedback = algo_feedback.neutral_string;
            } else if (score >= algo_feedback.neutral && score < algo_feedback.good) {
                showFeedbackDialog(algo_feedback.good_string);
                feedback = algo_feedback.good_string;
            } else if (score >= algo_feedback.good) {
                showFeedbackDialog(algo_feedback.very_good_string);
                feedback = algo_feedback.very_good_string;
            } else {
                showFeedbackDialog(algo_feedback.undefined_string);
                feedback = algo_feedback.undefined_string;

            }

            // Store the feedback in the list in the format "Module: Feedback"
            feedbackList.add(module + ": " + feedback);
        }


        // Log all the feedback
        for (String feedback : feedbackList) {
            Log.d("LessonFeedback", feedback);
            showFeedbackDialog(feedback);
        }
    }

    // Show feedback dialog based on score
    private void showFeedbackDialog(String feedback) {
        // Display the feedback to the user
        switch (feedback) {
            case "Very Bad":
//                Log.d("LessonFeedback","Feedback: " + very_bad_string);
                break;
            case "Bad":
//                Log.d("LessonFeedback","Feedback: " + bad_string);
                break;
            case "Neutral":
//                Log.d("LessonFeedback","Feedback: " + neutral_string);
                break;
            case "Good":
//                Log.d("LessonFeedback","Feedback: " + good_string);
                break;
            case "Very Good":
//                Log.d("LessonFeedback","Feedback: " + very_good_string);
                break;
            default:
//                Log.d("LessonFeedback","Feedback: " + undefined_string);
                break;
        }
    }

//    public void showToast (String message) {
//        Toast.makeText(d_Lesson_container., message, Toast.LENGTH_SHORT).show();
//    }

}
