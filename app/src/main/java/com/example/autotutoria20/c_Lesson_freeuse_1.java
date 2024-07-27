package com.example.autotutoria20;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class c_Lesson_freeuse_1 extends AppCompatActivity {

    private AlertDialog dialog; // Declare the dialog variable outside

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_lesson_freeuse_1);

        // Find all card views
        CardView card1 = findViewById(R.id.card1);
        CardView card2 = findViewById(R.id.card2);
        CardView card3 = findViewById(R.id.card3);
        CardView card4 = findViewById(R.id.card4);

        // Assuming numberOfSteps is determined based on your logic
        int numberOfStepsForCard1 = z_Lesson_steps.lesson_1_steps[0];
        int numberOfStepsForCard2 = z_Lesson_steps.lesson_1_steps[1];
        int numberOfStepsForCard3 = z_Lesson_steps.lesson_1_steps[2];
        int numberOfStepsForCard4 = z_Lesson_steps.lesson_1_steps[3];

        // Set click listeners for each card
        setCardClickListener(card1, 1, numberOfStepsForCard1);
        setCardClickListener(card2, 2, numberOfStepsForCard2);
        setCardClickListener(card3, 3, numberOfStepsForCard3);
        setCardClickListener(card4, 4, numberOfStepsForCard4);

        // Fetch progress data from the database
        fetchProgressData();

        // Find exit button
        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                showExitConfirmationDialog();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.e("onResume()", "I has returned");

        // Fetch the latest progress data
        fetchProgressData();
    }

    private void fetchProgressData() {
        // Assuming you are using Firebase Firestore to store progress data
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Reference to the user's progress data
        DocumentReference progressRef =
                db.collection("users")
                        .document(userId)
                        .collection("Free Use Mode")
                        .document("Lesson 1");

        progressRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String TAG = "fetchProgressData()";
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "Document exists fetchProgressData()");

                        // Get all fields and their values
                        Map<String, Object> progressData = document.getData();

                        if (progressData != null) {
                            for (Map.Entry<String, Object> entry : progressData.entrySet()) {
                                String key = entry.getKey();
                                Object value = entry.getValue();

                                if (value instanceof Long) {
                                    int progress = ((Long) value).intValue();
                                    Log.d(TAG, key + " Progress: " + progress);

                                    // Extract the second character and convert it to an integer
                                    int moduleNumber = Character.getNumericValue(key.charAt(1));

                                    // Update the UI or process the progress value as needed
                                    updateUI(moduleNumber, progress);

                                } else {
                                    Log.d(TAG, key + " is not of expected type.");
                                }
                            }
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void updateUI(int key, int progress) {
        //ETO NA I-A-UPDATE NAAAA!!!
        Log.d("updateUI()", "ETO NA MAG A-UPDATE NA AKOOOO LEZGOOO");

        // Update progress text views
        TextView module1ProgressText = findViewById(R.id.freeuse_lesson_1_module_1);
        TextView module2ProgressText = findViewById(R.id.freeuse_lesson_2_module_1);
        TextView module3ProgressText = findViewById(R.id.freeuse_lesson_3_module_1);
        TextView module4ProgressText = findViewById(R.id.freeuse_lesson_4_module_1);

        // Verify passed values...
        Log.d("updateUI()", "Module: " + key + " | Progress : " + progress);

        String newText;

        switch (key) {
            case 1:
                newText = progress + "/" + z_Lesson_steps.lesson_1_steps[0];
                if (module1ProgressText != null) {
                    module1ProgressText.setText(newText);
                } else {
                    showToast("TextView for module 1 not found");
                    Log.e("updateUI", "TextView for module 1 not found");
                }
                break;
            case 2:
                newText = progress + "/" + z_Lesson_steps.lesson_1_steps[1];
                if (module2ProgressText != null) {
                    module2ProgressText.setText(newText);
                } else {
                    showToast("TextView for module 2 not found");
                    Log.e("updateUI", "TextView for module 2 not found");
                }
                break;
            case 3:
                newText = progress + "/" + z_Lesson_steps.lesson_1_steps[2];
                if (module3ProgressText != null) {
                    module3ProgressText.setText(newText);
                } else {
                    showToast("TextView for module 3 not found");
                    Log.e("updateUI", "TextView for module 3 not found");
                }
                break;
            case 4:
                newText = progress + "/" + z_Lesson_steps.lesson_1_steps[3];
                if (module4ProgressText != null) {
                    module4ProgressText.setText(newText);
                } else {
                    showToast("TextView for module 4 not found");
                    Log.e("updateUI", "TextView for module 4 not found");
                }
                break;
            default:
                Log.d("updateUI", "Invalid module number: " + key);
                break;
        }
    }


    // Method to set click listener for each card
    private void setCardClickListener(CardView cardView, final int cardNumber, int numberOfSteps) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToModuleActivity(d_Lesson_container.class, cardNumber, numberOfSteps);
//                showToast("Open Card " + cardNumber);
            }
        });
    }

    private void navigateToModuleActivity(Class<?> moduleActivityClass, int cardNumber, int numberOfSteps) {
        // Store user information in SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("ModulePreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("numberOfSteps", numberOfSteps);
        editor.putString("learningMode", "Free Use Mode");
        editor.putString("currentLesson", "Lesson 1");
        editor.putString("currentModule", "M" + cardNumber);
        editor.apply();

//        showToast("Start Card " + cardNumber);

        Intent intent = new Intent(c_Lesson_freeuse_1.this, moduleActivityClass);
        startActivity(intent);

        // bat naka-comment? ewan ko din
//        finish();
    }

    // Helper method to show toast message
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showExitConfirmationDialog() {
        // Create a dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Inflate the custom dialog layout
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_exit_confirmation, null);
        builder.setView(dialogView);

        // Find "Yes" button in custom layout
        Button btnYes = dialogView.findViewById(R.id.exit_module);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle "Yes" button click
                Toast.makeText(c_Lesson_freeuse_1.this, "Exiting module", Toast.LENGTH_SHORT).show();
                finish(); // Finish the activity
            }
        });

        // Find "No" button in custom layout
        Button btnCancel = dialogView.findViewById(R.id.cancel_exit_module);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle "No" button click
                Toast.makeText(c_Lesson_freeuse_1.this, "Cancel Exit", Toast.LENGTH_SHORT).show();
                dialog.dismiss(); // Dismiss the dialog
            }
        });

        // Create and show the dialog
        dialog = builder.create();
        dialog.show();
    }
}
