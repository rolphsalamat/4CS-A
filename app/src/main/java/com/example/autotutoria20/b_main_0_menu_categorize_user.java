package com.example.autotutoria20;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class b_main_0_menu_categorize_user extends AppCompatActivity {

    public static String category;
    public static double passingGrade;
    private String novice = "Novice";
    private String beginner = "Beginner";
    private String intermediate = "Intermediate";
    private String advanced = "Advanced";
    private String expert = "Expert";
    private static FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_main_0_menu_categorize_user);

        mAuth = FirebaseAuth.getInstance();

        // Button setup for category selection
        Button buttonNovice = findViewById(R.id.buttonNovice);
        Button buttonBeginner = findViewById(R.id.buttonBeginner);
        Button buttonIntermediate = findViewById(R.id.buttonIntermediate);
        Button buttonAdvanced = findViewById(R.id.buttonAdvanced);
        Button buttonExpert = findViewById(R.id.buttonExpert);

        buttonNovice.setOnClickListener(v -> {
            categorizeUser(novice);
            Toast.makeText(b_main_0_menu_categorize_user.this, "Selected: Novice", Toast.LENGTH_SHORT).show();
        });

        buttonBeginner.setOnClickListener(v -> {
            categorizeUser(beginner);
            Toast.makeText(b_main_0_menu_categorize_user.this, "Selected: Beginner", Toast.LENGTH_SHORT).show();
        });

        buttonIntermediate.setOnClickListener(v -> {
            categorizeUser(intermediate);
            Toast.makeText(b_main_0_menu_categorize_user.this, "Selected: Intermediate", Toast.LENGTH_SHORT).show();
        });

        buttonAdvanced.setOnClickListener(v -> {
            categorizeUser(advanced);
            Toast.makeText(b_main_0_menu_categorize_user.this, "Selected: Advanced", Toast.LENGTH_SHORT).show();
        });

        buttonExpert.setOnClickListener(v -> {
            categorizeUser(expert);
            Toast.makeText(b_main_0_menu_categorize_user.this, "Selected: Expert", Toast.LENGTH_SHORT).show();
        });

    }

    public static double passingCategory(String category) {

        // default 60% passing grade
        passingGrade = 0.60;


        Log.d("category", "Category: " + category);
        Log.d("category", "Passing Grade: " + passingGrade);

        return 0;
    }

    public void categorizeUser(String category) {
        // Create a map to store the field to update
        Map<String, Object> updates = new HashMap<>();
        updates.put("User Category", category);

        // Update user data in Firestore
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();
        FirebaseFirestore db =
                FirebaseFirestore.getInstance();

        db.collection("users")
                .document(userId)
                .update(updates)
                .addOnSuccessListener(aVoid -> {
                    // Handle success
                    System.out.println("User category successfully updated!");
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    System.err.println("Error updating user category: " + e.getMessage());
                });

        // TAPOS NA! ALIS NA!
        Intent mainMenuIntent = new Intent(b_main_0_menu_categorize_user.this, b_main_0_menu.class);
        startActivity(mainMenuIntent);
        Log.d("Categorize User Class", "Redirecting to Main Menu");

        finish();

        // launch isStudent class
        Intent intent = new Intent(b_main_0_menu_categorize_user.this, b_main_0_menu_isStudent.class);
        startActivity(intent);

    }

    public void retrieveCategory(OnCategoryRetrievedListener listener) {
        // Update user data in Firestore
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            listener.onError("User not authenticated");
            return; // Exit early if user is not authenticated
        }

        String userId = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Retrieve the "Category" field
                            category = document.getString("Category");
                            listener.onCategoryRetrieved(category);
                        } else {
                            listener.onError("No such document");
                        }
                    } else {
                        listener.onError("Error getting documents: " + task.getException());
                    }
                });
    }

    // Define your callback interface
    public interface OnCategoryRetrievedListener {
        void onCategoryRetrieved(String category);
        void onError(String errorMessage);
    }


}
