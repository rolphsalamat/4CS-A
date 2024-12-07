package com.example.autotutoria20;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class b_main_0_menu_rateUs extends AppCompatActivity {

    private RatingBar ratingBar;
    private Button submitButton;
    private EditText ratingComment;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_main_0_menu_rateus);

        ratingBar = findViewById(R.id.ratingBar);
        ratingComment = findViewById(R.id.ratingComment);
        submitButton = findViewById(R.id.submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = ratingComment.getText().toString();
                float rating = ratingBar.getRating();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String email = user != null ? user.getEmail() : "No Email";

                Log.d("Feedback", "User Email: " + email);
                Log.d("Feedback", "Rating: " + rating + ", Comment: " + comment);

                sendFeedBack(email, comment, rating);

                Toast.makeText(b_main_0_menu_rateUs.this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void sendFeedBack(String email, String comment, float rating) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user != null ? user.getUid() : null;

        if (userId == null) {
            Log.e("Feedback", "User is not authenticated.");
            return;
        }

        // Debug logs for tracking progress
        Log.d("Feedback", "Adding feedback for user: " + email);

        // Create a single map to store all feedback fields
        Map<String, Object> feedbackData = new HashMap<>();
        feedbackData.put("Rating", rating);  // Store the rating
        feedbackData.put("Comment", comment);  // Store the comment
        feedbackData.put("Timestamp", FieldValue.serverTimestamp());  // Add the timestamp

        // Store feedback under Feedback/User Feedback/{email}
        db.collection("Feedback")
                .document("User Feedback")
                .collection("Feedback")
                .document(email)
                .set(feedbackData)
                .addOnSuccessListener(aVoid -> {
                    Log.d("Feedback", "Feedback successfully added for user: " + email);
                })
                .addOnFailureListener(e -> {
                    Log.e("Feedback", "Error adding feedback for user: " + email, e);
                });
    }



//    // Create the comment data
//        Map<String, Object> commentData = new HashMap<>();
//        commentData.put("Comment", comment);
//        commentData.put("Timestamp", FieldValue.serverTimestamp());
//
//        // Add comment under Feedback/User Feedback/Comments/{email}
//        db.collection("Feedback")
//                .document("User Feedback")
////                .collection("Comments")
////                .document(email)
//                .set(commentData)
//                .addOnSuccessListener(aVoid ->
//                        Log.d("Feedback", "Comment successfully added for user: " + email))
//                .addOnFailureListener(e ->
//                        Log.e("Feedback", "Error adding comment for user: " + email, e));
//
//        // Create the rating data
//        Map<String, Object> ratingData = new HashMap<>();
//        ratingData.put("Rating", rating);
//        ratingData.put("Timestamp", FieldValue.serverTimestamp());
//
//        // Add rating under Feedback/User Feedback/Rating/{email}
//        db.collection("Feedback")
//                .document("User Feedback")
////                .collection("Rating")
////                .document(email)
//                .set(ratingData)
//                .addOnSuccessListener(aVoid ->
//                        Log.d("Feedback", "Rating successfully added for user: " + email))
//                .addOnFailureListener(e ->
//                        Log.e("Feedback", "Error adding rating for user: " + email, e));

}
