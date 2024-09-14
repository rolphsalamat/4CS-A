package com.example.autotutoria20;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class c_Lesson_feedback {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance(); // FirebaseAuth instance
    private x_bkt_algorithm algo_feedback;
    private Context context; // Add context field

    // Constructor
    public c_Lesson_feedback(Context context) {

        this.context = context;

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

        int moduleCount = 0;
        double feedbackScore = 0.0;

        // Iterate through BKT Scores for each module and analyze
        for (Map.Entry<String, Object> entry : bktScores.entrySet()) {
            String module = entry.getKey();
            Double score = (Double) entry.getValue();

            // increment the feedbackScore
            feedbackScore += score;

            moduleCount++;

            Log.e("LessonFeedback", "Module " + moduleCount + ": " + score);
            Log.e("LessonFeedback", "feedbackScore += score;");
            Log.e("LessonFeedback", "feedbackScore: " + feedbackScore);

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
        }

        // Calculate and round off the total feedback score
        double totalFeedback = feedbackScore / moduleCount;
        Log.e("LessonFeedback", "totalFeedback: " + totalFeedback);
//        int roundedFeedback = (int) Math.round(totalFeedback);
//        Log.e("LessonFeedback", "roundedFeedback: " + roundedFeedback);

        // Show the dialog with the rounded feedback score
//        Log.e("LessonFeedback", "showDialog("+roundedFeedback+");");
        Log.e("LessonFeedback", "showDialog("+totalFeedback+");");
        showDialog(totalFeedback);

    }

    // Show feedback dialog based on score
    private void showFeedbackDialog(String feedback) {

        final int min = 20;
        final int max = 80;
        final int random = new Random().nextInt((max - min) + 1) + min;

        // Display the feedback to the user
        switch (feedback) {
            case "Very Bad":
//                if (random == 0)
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

    public void showDialog(double doubleScore) {

//        int score = (int) Math.round(doubleScore);

        // para di na mag rename isa-isa :D
        double score = doubleScore;

        Toast.makeText(context, "HOY!", Toast.LENGTH_SHORT).show();
        Log.e("c_Lesson_feedback", "showDialog()");

        Log.e("c_Lesson_feedback", "Score: " + score);

        int layoutId = 0;
        int category = 0;

        double veryBad = 0.2; // or 0.20 to negative infintie
        double bad = 0.4;
        double neutral = 0.6;
        double good = 0.8;
        double veryGood = 1.0; // or 0.81 to positive infinite

        if (score <= veryBad) {
            category = 1;
            Log.e("c_Lesson_feedback", "Layout: " + "very bad");
            layoutId = R.layout.c_lesson_feedback_0_very_bad;
        }
        else if (score > veryBad && score <= bad) {
            category = 2;
            Log.e("c_Lesson_feedback", "Layout: " + "bad");
            layoutId = R.layout.c_lesson_feedback_1_bad;
        }
        else if (score > bad && score <= neutral) {
            category = 3;
            Log.e("c_Lesson_feedback", "Layout: " + "neutral");
            layoutId = R.layout.c_lesson_feedback_2_neutral;
        }
        else if (score > neutral && score <= good) {
            category = 4;
            Log.e("c_Lesson_feedback", "Layout: " + "good");
            layoutId = R.layout.c_lesson_feedback_3_good;
        }
        else {
            category = 5;
            Log.e("c_Lesson_feedback", "Layout: " + "very good");
            layoutId = R.layout.c_lesson_feedback_4_very_good;
        }
        
        if (layoutId == 0) {
            Log.e("showDialog", "layoutId is 0?? :<");
        } else {

            // Inflate the layout
            View customView = View.inflate(context, layoutId, null);

            score = score*100;

            // ROUND OFF
            int decimalPlaces = 2; // Change this to the number of decimal places you want
            // Convert double to BigDecimal
            BigDecimal bd = new BigDecimal(score);
            // Round BigDecimal to specified number of decimal places
            BigDecimal rounded = bd.setScale(decimalPlaces, RoundingMode.HALF_UP);
            // Convert back to double if needed
            double roundedScore = rounded.doubleValue();

            // Create and show the dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(customView);
            AlertDialog dialog = builder.create();
            dialog.show();

            // Convert 450dp to pixels
            int heightInPx = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 450, context.getResources().getDisplayMetrics());

            // Adjust dialog window parameters
            Objects.requireNonNull(dialog.getWindow()).setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT, // Width = match_parent
                    heightInPx // Height = 450dp in pixels
            );

            switch(category) {
                case 1:
                    // Find the TextView and set the new text
                    TextView veryBadScoreTextView = customView.findViewById(R.id.very_bad_score);
                    veryBadScoreTextView.setText(roundedScore + "%");  // Change the text here
                    break;
                case 2:
                    // Find the TextView and set the new text
                    TextView badScoreTextView = customView.findViewById(R.id.bad_score);
                    badScoreTextView.setText(roundedScore + "%");  // Change the text here
                    break;
                case 3:
                    // Find the TextView and set the new text
                    TextView neutralScoreTextView = customView.findViewById(R.id.neutral_score);
                    neutralScoreTextView.setText(roundedScore + "%");  // Change the text here
                    break;
                case 4:
                    // Find the TextView and set the new text
                    TextView goodScoreTextView = customView.findViewById(R.id.good_score);
                    goodScoreTextView.setText(roundedScore + "%");  // Change the text here
                    break;
                case 5:
                    // Find the TextView and set the new text
                    TextView veryGoodScoreTextView = customView.findViewById(R.id.very_good_score);
                    veryGoodScoreTextView.setText(roundedScore + "%");  // Change the text here
                    break;
                default:
                    Log.e("switchCase", "ERROR! T_T");
                    break;
            }

            // Find the button and set the OnClickListener to dismiss the dialog
            Button button = customView.findViewById(R.id.button_okay);
            button.setOnClickListener(v -> dialog.dismiss()); // dismiss the dialog on button click

//            Log.e("showDialog", "OKAY ETO NA!!!");
//            // Create and show the dialog with the selected layout
//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            builder.setView(View.inflate(context, layoutId, null));
//            AlertDialog dialog = builder.create();
//            dialog.show();
        }
    }

}
