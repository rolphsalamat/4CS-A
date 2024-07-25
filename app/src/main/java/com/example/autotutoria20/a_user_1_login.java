package com.example.autotutoria20;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
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
        LinearLayout signupButton = findViewById(R.id.btnSignup);
        EditText emailAddressTextView = findViewById(R.id.textUsername);
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
                String email = emailAddressTextView.getText().toString().trim();
                String password = passwordTextView.getText().toString().trim();
                loginUser(email, password);
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
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) { // Email and Password found on Firestore Authentication
                            FirebaseUser user = mAuth.getCurrentUser(); // Get user ID
                            if (user != null) { // if user is found
                                String userId = user.getUid();
                                fetchUserInfo(userId); // Fetch user info and lesson data
                            } else { // If user is not found, or no data matched
                                Toast.makeText(a_user_1_login.this, "Account does not exist", Toast.LENGTH_SHORT).show();
                            }
                        } else { // Email and Password is not found on Firestore Authentication
                            Toast.makeText(a_user_1_login.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("Firebase Authentication", "Authentication failed: " + task.getException().getMessage());
                        }
                    }
                });
    }

    private void fetchUserInfo(String userId) {
        DocumentReference userRef = db.collection("users").document(userId);

        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        String firstName = document.getString("First Name");
                        String lastName = document.getString("Last Name");
                        String email = document.getString("Email Address");
                        String gender = document.getString("Gender");
                        String password = document.getString("Password");
                        int age = document.getLong("Age").intValue(); // Assuming Age is stored as a Long

                        Log.d("Firestore", "First Name: " + firstName);
                        Log.d("Firestore", "Last Name: " + lastName);
                        Log.d("Firestore", "Email Address: " + email);
                        Log.d("Firestore", "Gender: " + gender);
                        Log.d("Firestore", "Password: " + password);
                        Log.d("Firestore", "Age: " + age);

                        // Store user information in SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("userId", userId);
                        editor.putString("firstName", firstName);
                        editor.putString("lastName", lastName);
                        editor.putString("email", email);
                        editor.putString("gender", gender);
                        editor.putInt("age", age);
                        editor.putBoolean("isLoggedIn", true);

                        // Add Lessons here??


                        // Apply User Information
                        editor.apply();

                        // Fetch lesson data and pass it to the intent
                        fetchLessonData(userId, firstName, lastName, email, gender, age);
                    } else {
                        Log.d("Firestore", "User document does not exist");
                    }
                } else {
                    String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                    Log.d("Firestore", "Failed to retrieve user information: " + errorMessage);
                }
            }
        });
    }

    private void fetchLessonData(String userId, String firstName, String lastName, String email, String gender, int age) {
        final Map<String, Map<String, Object>> progressiveModeData = new HashMap<>();
        final Map<String, Map<String, Object>> freeUseModeData = new HashMap<>();

        CollectionReference progressiveModeRef = db.collection("users").document(userId).collection("Progressive Mode");
        CollectionReference freeUseModeRef = db.collection("users").document(userId).collection("Free Use Mode");

        progressiveModeRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null && !querySnapshot.isEmpty()) {
                        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                            progressiveModeData.put(document.getId(), document.getData());
                            processLessonData("Progressive Mode", document.getId(), document.getData());
                            verifySavedData(); // Verify saved data after processing each lesson
                        }
                        Log.d("Firestore", "Progressive Mode data retrieved");
                    } else {
                        Log.d("Firestore", "Progressive Mode data not found");
                    }

                    // After processing Progressive Mode, fetch Free Use Mode data
                    freeUseModeRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                QuerySnapshot querySnapshot = task.getResult();
                                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                                        freeUseModeData.put(document.getId(), document.getData());
                                        processLessonData("Free Use Mode", document.getId(), document.getData());
                                        verifySavedData(); // Verify saved data after processing each lesson
                                    }
                                    Log.d("Firestore", "Free Use Mode data retrieved");
                                } else {
                                    Log.d("Firestore", "Free Use Mode data not found");
                                }
                            } else {
                                String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                                Log.d("Firestore", "Failed to retrieve Free Use Mode data: " + errorMessage);
                            }

                            // Proceed to next activity after fetching all data
//                             moveToMainMenu(firstName, lastName, email, gender, age);
                            Intent intent = new Intent(a_user_1_login.this, b_main_0_menu.class);
                            startActivity(intent);
                            finish(); // Finish the login activity
                        }
                    });
                } else {
                    String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                    Log.d("Firestore", "Failed to retrieve Progressive Mode data: " + errorMessage);
                }
            }
        });
    }

    private void verifySavedData() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("SharedPreferences", entry.getKey() + ": " + entry.getValue().toString());
        }
    }

//    private void moveToMainMenu(String firstName, String lastName, String email, String gender, int age) {
//        Intent intent = new Intent(a_user_1_login.this, b_main_0_menu.class);
//        intent.putExtra("firstName", firstName);
//        intent.putExtra("lastName", lastName);
//        intent.putExtra("email", email);
//        intent.putExtra("gender", gender);
//        intent.putExtra("age", age);
//        startActivity(intent);
//        finish(); // Finish the login activity
//    }


    // Method to show custom dialog for resetting password
    private void showForgotPasswordDialog() {

        // eto yung technique pano na show ung rounded corners :D
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Inflate the custom layout
        View dialogView = getLayoutInflater().inflate(R.layout.a_user_3_forgot_password, null);

        // Initialize views
        EditText emailEditText = dialogView.findViewById(R.id.email_edittext);
        Button resetButton = dialogView.findViewById(R.id.btn_reset);
        Button cancelButton = dialogView.findViewById(R.id.btn_cancel);

        Button createAccount = dialogView.findViewById(R.id.btn_create_new_account);
        Button login = dialogView.findViewById(R.id.btn_login);

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

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(a_user_1_login.this, a_user_2_signup.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // Show the dialog
        dialog.show();

        // Ensure the dialog respects rounded corners
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }


    // Method to reset password
    private void resetPassword(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(a_user_1_login.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(a_user_1_login.this, "Failed to send reset email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void processLessonData(String modeName, String lessonName, Map<String, Object> lessonData) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for (Map.Entry<String, Object> entry : lessonData.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();

            if (fieldValue instanceof Long) {
                editor.putInt(modeName + ": " + lessonName + ", " + fieldName, ((Long) fieldValue).intValue());
            } else if (fieldValue instanceof String) {
                editor.putString(modeName + ": " + lessonName + ", " + fieldName, (String) fieldValue);
            }
        }

        editor.apply();
    }



}
