package com.example.autotutoria20;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
                    passwordTextView.setTransformationMethod(null); // Show the password
                    showHidePasswordButton.setBackgroundResource(R.drawable.hide_password); // Change icon to hide password
                } else {
                    passwordTextView.setTransformationMethod(new PasswordTransformationMethod()); // Hide the password
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

        ImageView pncGotoPage = findViewById(R.id.pnc_goto_page);
        pncGotoPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pnc.edu.ph/"));
                startActivity(browserIntent);
            }
        });
    }

    // Method to authenticate user using username and password
    private void loginUser(String username, String enteredPassword) {
        db.collection("users")
                .whereEqualTo("Username", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (!querySnapshot.isEmpty()) {
                                DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                                Log.d("LoginProcess", "Document retrieved: " + document.getData());

                                String email = document.getString("Email Address");
                                String storedHashedPassword = document.getString("Password");

                                if (email == null || email.isEmpty()) {
                                    Log.e("LoginProcess", "Email is null or empty for username: " + username);
                                    Toast.makeText(a_user_1_login.this, "Email is missing for this username", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if (storedHashedPassword == null || storedHashedPassword.isEmpty()) {
                                    Log.e("LoginProcess", "Password is missing for this username: " + username);
                                    Toast.makeText(a_user_1_login.this, "Password is missing for this username", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                // Verify the entered password against the stored hash
                                if (a_user_3_password_encryption.checkPassword(enteredPassword, storedHashedPassword)) {
                                    // Password matches, proceed with Firebase authentication
                                    mAuth.signInWithEmailAndPassword(email, enteredPassword)
                                            .addOnCompleteListener(a_user_1_login.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        FirebaseUser user = mAuth.getCurrentUser();
                                                        if (user != null) {
                                                            String userId = user.getUid();
                                                            Log.d("LoginProcess", "Login successful. User ID: " + userId);
                                                            fetchUserInfo(userId);
                                                        } else {
                                                            Log.d("LoginProcess", "User login failed: User object is null");
                                                            Toast.makeText(a_user_1_login.this, "Account does not exist", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        Log.e("Firebase Authentication", "Authentication failed: " + task.getException().getMessage());
                                                        Toast.makeText(a_user_1_login.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                } else {
                                    Log.e("LoginProcess", "Password does not match for username: " + username);
                                    Toast.makeText(a_user_1_login.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Log.d("LoginProcess", "Username does not exist: " + username);
                                Toast.makeText(a_user_1_login.this, "Username does not exist", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                            Log.e("Firestore", "Failed to retrieve username: " + errorMessage);
                            Toast.makeText(a_user_1_login.this, "Error retrieving username", Toast.LENGTH_SHORT).show();
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
                        Boolean tutorial = document.getBoolean("Tutorial");
                        String firstName = document.getString("First Name");
                        String lastName = document.getString("Last Name");
                        String email = document.getString("Email Address");
                        String gender = document.getString("Gender");
                        int age = document.getLong("Age").intValue(); // Assuming Age is stored as a Long

                        // Store user information in SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("tutorial", tutorial);
                        editor.putString("userId", userId);
                        editor.putString("firstName", firstName);
                        editor.putString("lastName", lastName);
                        editor.putString("email", email);
                        editor.putString("gender", gender);
                        editor.putInt("age", age);
                        editor.putBoolean("isLoggedIn", true);
                        editor.apply();

                        // Fetch lesson data and move to the main menu
                        fetchLessonData(userId, firstName, lastName, email, gender, age, tutorial);
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

    private void fetchLessonData(String userId, String firstName, String lastName, String email, String gender, int age, boolean tutorial) {
        fetchModeData(userId, "Progressive Mode", new OnDataFetchedCallback() {
            @Override
            public void onDataFetched() {
                fetchModeData(userId, "Free Use Mode", new OnDataFetchedCallback() {
                    @Override
                    public void onDataFetched() {
                        Log.d("Firestore", "All lesson data retrieved");
                        moveToMainMenu(firstName, lastName, email, gender, age, tutorial);
                    }
                });
            }
        });
    }

    private void fetchModeData(String userId, String mode, OnDataFetchedCallback callback) {
        CollectionReference modeRef = db.collection("users").document(userId).collection(mode);
        modeRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null && !querySnapshot.isEmpty()) {
                        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                            processLessonData(mode, document.getId(), document.getData());
                        }
                        Log.d("Firestore", mode + " data retrieved");
                    } else {
                        Log.d("Firestore", mode + " data not found");
                    }
                } else {
                    String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                    Log.e("Firestore", "Failed to retrieve " + mode + " data: " + errorMessage);
                }
                callback.onDataFetched();
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

    private void moveToMainMenu(String firstName, String lastName, String email, String gender, int age, boolean Tutorial) {
        Intent intent = new Intent(a_user_1_login.this, b_main_0_menu.class);
        intent.putExtra("firstName", firstName);
        intent.putExtra("lastName", lastName);
        intent.putExtra("email", email);
        intent.putExtra("gender", gender);
        intent.putExtra("age", age);
        intent.putExtra("tutorial", Tutorial);
        startActivity(intent);
        finish(); // Finish the login activity
    }

    // Method to show custom dialog for resetting password
    private void showForgotPasswordDialog() {
        // Inflate the custom layout
        View dialogView = getLayoutInflater().inflate(R.layout.a_user_3_forgot_password, null);

        // Initialize views
        EditText emailEditText = dialogView.findViewById(R.id.email_edittext);
        Button resetButton = dialogView.findViewById(R.id.btn_reset);
        Button cancelButton = dialogView.findViewById(R.id.btn_cancel);
        Button createAccount = dialogView.findViewById(R.id.btn_create_new_account);
        TextView loginButton = dialogView.findViewById(R.id.btn_login);

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

        loginButton.setOnClickListener(new View.OnClickListener() { // Set click listener for LinearLayout
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

    // Callback interface for fetching data
    interface OnDataFetchedCallback {
        void onDataFetched();
    }
}
