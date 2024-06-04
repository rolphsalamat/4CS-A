package com.example.autotutoria20;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class a_user_1_login extends AppCompatActivity {

    private boolean isPasswordVisible = false;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_user_1_login);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize views and buttons
        TextView loginButton = findViewById(R.id.btnLogin);
        TextView signupButton = findViewById(R.id.btnSignup);
        EditText usernameTextView = findViewById(R.id.textUsername);
        EditText passwordTextView = findViewById(R.id.textPassword);

        // FORGOT PASSWORD
        TextView forgotPassword = findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showForgotPasswordDialog();
            }
        });

        ImageButton showHidePasswordButton = findViewById(R.id.btnShowPassword);
        // Show Password
        showHidePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle password visibility
                isPasswordVisible = !isPasswordVisible;

                // Change the password visibility in the EditText
                if (isPasswordVisible) {
                    // Show password
                    passwordTextView.setTransformationMethod(null); // Set null to show the password
                    showHidePasswordButton.setBackgroundResource(R.drawable.hide_password); // Change icon to hide password
                } else {
                    // Hide password
                    passwordTextView.setTransformationMethod(new PasswordTransformationMethod()); // Use PasswordTransformationMethod to hide the password
                    showHidePasswordButton.setBackgroundResource(R.drawable.show_password); // Change icon to show password
                }
            }
        });

        // LOGIN
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameTextView.getText().toString().trim();
                String password = passwordTextView.getText().toString().trim();
                loginUser(username, password);
            }
        });

        // SIGNUP
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(a_user_1_login.this, a_user_2_signup.class));
            }
        });
    }

    // Method to authenticate user using Firebase Auth
    private void loginUser(String email, String password) {

//        Toast.makeText(a_user_1_login.this, "SIGN IN BUTTON CLICKED", Toast.LENGTH_SHORT).show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Authentication successful
                            Toast.makeText(a_user_1_login.this, "Authentication Successful", Toast.LENGTH_SHORT).show();

                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                // Fetch user details from Firestore
                                Toast.makeText(a_user_1_login.this, "user.getUid(): " + user.getUid(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(a_user_1_login.this, b_main_0_menu.class));
                                fetchUserDetails(user.getUid());
                            } else {
                                // User is null
                                Toast.makeText(a_user_1_login.this, "User is null", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Authentication failed
                            Toast.makeText(a_user_1_login.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("Firebase Authentication", "Authentication failed: " + task.getException().getMessage());
                        }
                    }
                });
    }

    private void fetchUserDetails(String userId) {
        // Initial Toast to confirm the method is called
        Toast.makeText(a_user_1_login.this, "fetchUserDetails called", Toast.LENGTH_SHORT).show();

        // Get reference to the user's document
        DocumentReference userRef = db.collection("users").document(userId);

        // Logging the user reference for debugging
        Log.d("Firestore", "UserRef: " + userRef.getPath());
        Toast.makeText(a_user_1_login.this, "UserRef: " + userRef.getPath(), Toast.LENGTH_SHORT).show();

        // Retrieve user data
        userRef.get().addOnCompleteListener(task -> {
            Log.d("Firestore", "onComplete called");
            Toast.makeText(a_user_1_login.this, "onComplete na", Toast.LENGTH_SHORT).show();
            if (task.isSuccessful()) {
                Toast.makeText(a_user_1_login.this, "task.isSuccessful()", Toast.LENGTH_SHORT).show();
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Document exists, retrieve modes data
                    Toast.makeText(a_user_1_login.this, "User document exists", Toast.LENGTH_SHORT).show();
                    Log.d("Firestore", "User document exists");

                    // Retrieve Free Use Mode lessons
                    retrieveModeData(userRef, "Free Use Mode");

                    // Retrieve Progressive Mode lessons
                    retrieveModeData(userRef, "Progressive Mode");
                } else {
                    // User document does not exist
                    Toast.makeText(a_user_1_login.this, "No such document", Toast.LENGTH_SHORT).show();
                    Log.d("Firestore", "No such document");
                }
            } else {
                // Error getting user document
                String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                Toast.makeText(a_user_1_login.this, "Failed to retrieve user data: " + errorMessage, Toast.LENGTH_SHORT).show();
                Log.d("Firestore", "Failed to retrieve user data: " + errorMessage);
            }
        });
    }

    private void retrieveModeData(DocumentReference userRef, String mode) {
        userRef.collection(mode).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (!querySnapshot.isEmpty()) {
                        for (DocumentSnapshot lessonDoc : querySnapshot.getDocuments()) {
                            // Process each lesson document
                            Map<String, Object> lessonData = lessonDoc.getData();
                            processModules(mode + " - " + lessonDoc.getId(), lessonData);
                        }
                        Toast.makeText(a_user_1_login.this, mode + " data retrieved", Toast.LENGTH_SHORT).show();
                    } else {
                        // No lessons found in the mode
                        Toast.makeText(a_user_1_login.this, mode + " data not found", Toast.LENGTH_SHORT).show();
                        Log.d("Firestore", mode + " data not found");
                    }
                } else {
                    // Error retrieving mode data
                    String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                    Toast.makeText(a_user_1_login.this, "Failed to retrieve " + mode + " data: " + errorMessage, Toast.LENGTH_SHORT).show();
                    Log.d("Firestore", "Failed to retrieve " + mode + " data: " + errorMessage);
                }
            }
        });
    }

    // Method to process modules data
    private void processModules(String mode, Map<String, Object> modeData) {
        for (Map.Entry<String, Object> entry : modeData.entrySet()) {
            String module = entry.getKey();
            Map<String, Object> moduleData = (Map<String, Object>) entry.getValue();
            // Process moduleData for each module
            Toast.makeText(a_user_1_login.this, mode + " - Module: " + module, Toast.LENGTH_SHORT).show();
            // Here you can further process the moduleData as needed
        }
    }


    // Method to show custom dialog for resetting password
    private void showForgotPasswordDialog() {
        // Inflate the custom layout
        View dialogView = getLayoutInflater().inflate(R.layout.a_user_3_forgot_password, null);

        // Initialize views
        EditText emailEditText = dialogView.findViewById(R.id.email_edittext);
        Button resetButton = dialogView.findViewById(R.id.btn_reset);
        Button cancelButton = dialogView.findViewById(R.id.btn_cancel);

        // Create AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setCancelable(false); // Prevent dialog from being canceled outside

        // Create AlertDialog
        final AlertDialog dialog = builder.create();

        // Set click listener for Reset button
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                if (!TextUtils.isEmpty(email)) {
                    resetPassword(email);
                    dialog.dismiss(); // Dismiss dialog after action
                } else {
                    Toast.makeText(a_user_1_login.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set click listener for Cancel button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Dismiss dialog without action
            }
        });

        // Show the dialog
        dialog.show();
    }

    // Method to reset password
    private void resetPassword(String email) {
        mAuth.sendPasswordResetEmail(email)
        .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task){
                if (task.isSuccessful()) {
                    Toast.makeText(a_user_1_login.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(a_user_1_login.this, "Failed to send reset email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
