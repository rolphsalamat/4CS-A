package com.example.autotutoria20;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class b_main_0_menu_isStudent extends AppCompatActivity {

    static String TAG = "b_main_0_menu_isStudent";
    static Boolean isStudent = true;
    private static FirebaseAuth mAuth;

    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    static String userId = user != null ? user.getUid() : null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_main_0_menu_isstudent);

        mAuth = FirebaseAuth.getInstance();

        Button btnStuddent = findViewById(R.id.buttonStudent);
        Button btnUser = findViewById(R.id.buttonUser);

        btnStuddent.setOnClickListener(v -> {
            retrieveUserStatus(true);
            Toast.makeText(b_main_0_menu_isStudent.this, "Selected: Student", Toast.LENGTH_SHORT).show();
        });

        btnUser.setOnClickListener(v -> {
            retrieveUserStatus(false);
            Toast.makeText(b_main_0_menu_isStudent.this, "Selected: User", Toast.LENGTH_SHORT).show();
        });

    }

    public static void setStatusAuto() {

        // If "isStudent" does not exist, set it to true and update the database
        Log.d(TAG, "'isStudent' field does not exist. Setting default value to true.");
        db.collection("users").document(userId)
                .update("isStudent", true)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "'isStudent' field added with default value: " + true);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error adding 'isStudent' field", e);
                });

        b_main_0_menu.isStudent = true;

        // If "isComplete" does not exist, set it to true and update the database
        Log.d(TAG, "'isComplete' field does not exist. Setting default value to true.");
        db.collection("users").document(userId)
                .update("isComplete", false)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "'isComplete' field added with default value: " + false);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error adding 'isComplete' field", e);
                });

        b_main_0_menu.isProgressiveCompleted = false;
    }

    public void setStatus(Boolean status) {

        // If "isStudent" does not exist, set it to true and update the database
        Log.d(TAG, "'isStudent' field does not exist. Setting default value to true.");
        db.collection("users").document(userId)
                .update("isStudent", status)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "'isStudent' field added with default value: " + status);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error adding 'isStudent' field", e);
                });

    }

    public void retrieveUserStatus(Boolean status) {

        if (userId == null) {
            Log.e(TAG, "User not authenticated");
            return;
        }

        db.collection("users").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Check if the "isStudent" field exists
                        if (documentSnapshot.contains("isStudent")) {
                            // Retrieve the value of "isStudent"
                            isStudent = documentSnapshot.getBoolean("isStudent");
                            Log.d(TAG, "User status retrieved: " + isStudent);
                        } else {

                            setStatus(status);

                        }
                    } else {
                        Log.e(TAG, "No such document");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error retrieving User Status", e);
                });

        Log.e(TAG, "retrueveUserStatus is complete!");
        Log.e(TAG, "User is a student?: " + isStudent);

        finish();

    }



}
