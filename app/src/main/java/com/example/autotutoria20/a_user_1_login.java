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
    private b_main_0_menu activity; // Assuming you have a reference to the activity

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

//        Toast.makeText(a_user_1_login.this, "SIGN IN BUTTON CLICKED", Toast.LENGTH_SHORT).show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) { // Email and Password found on Firestore Authentication
                            FirebaseUser user = mAuth.getCurrentUser(); // Get user ID
                            if (user != null) { // if user is found
                                String userId = user.getUid();
                                fetchUserDetails(userId);
                            } else // If user is not found, or no data matched
                                Toast.makeText(a_user_1_login.this, "Account does not exist", Toast.LENGTH_SHORT).show();

                        } else { // Email and Password is not found on Firestore Authentication
                            Toast.makeText(a_user_1_login.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("Firebase Authentication", "Authentication failed: " + task.getException().getMessage());
                        }
                    }
                });
    }

    private void fetchUserDetails(String userId) {
        // Get reference to the user's document
        DocumentReference userRef = db.collection("users").document(userId);


        // Call the new method to fetch user info
        fetchUserInfo(userId);
        // Call retrieveLessonData for Progressive Mode
        retrieveLessonData("Progressive Mode", userId); // Change this later to Progressive <space> Mode
        // Call retrieveLessonData for Free Use Mode
        retrieveLessonData("Free Use Mode", userId);
    }

    private void fetchUserInfo(String userId) {
        DocumentReference userRef = db.collection("users").document(userId);

        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String firstName = document.getString("First name");
                            String lastName = document.getString("Last name");
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

                            Intent intent = new Intent(a_user_1_login.this, b_main_0_menu.class);

                            // Put User Data Here
                            intent.putExtra("firstName", firstName); // for Greeting "Hello, " + firstName


                            startActivity(intent);


                        } else {
                            Log.d("Firestore", "User document does not exist");
                        }
                    } else {
                        String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                        Log.d("Firestore", "Failed to retrieve user information: " + errorMessage);
                    }
                }
            }
        });
    }


    private void retrieveLessonData(String modeName, String userId) {
        // Get reference to the user's lesson collection within the specified mode
        CollectionReference lessonRef = db.collection("users").document(userId).collection(modeName);

        lessonRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (!querySnapshot.isEmpty()) {
                        for (DocumentSnapshot lessonDoc : querySnapshot.getDocuments()) {
                            // Process each lesson document
                            String lessonName = lessonDoc.getId(); // Get the lesson name (document ID)
                            Map<String, Object> lessonData = lessonDoc.getData();
                            processLessonData(modeName, lessonName, lessonData);
                        }
                        Toast.makeText(a_user_1_login.this, modeName + " data retrieved", Toast.LENGTH_SHORT).show();
                        Log.d("Firestore", modeName + " data retrieved");
                    } else {
                        // No lessons found in the specified mode
                        Toast.makeText(a_user_1_login.this, modeName + " data not found", Toast.LENGTH_SHORT).show();
                        Log.d("Firestore", modeName + " data not found");
                    }
                } else {
                    // Error retrieving lesson data
                    String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                    Toast.makeText(a_user_1_login.this, "Failed to retrieve " + modeName + " data: " + errorMessage, Toast.LENGTH_SHORT).show();
                    Log.d("Firestore", "Failed to retrieve " + modeName + " data: " + errorMessage);
                }
            }
        });
    }


    private void processLessonData(String modeName, String lessonName, Map<String, Object> lessonData) {
        // Access individual fields within lessonData (M1, M2, etc.)
        // You can use a loop or conditional statements to access specific fields
        for (Map.Entry<String, Object> entry : lessonData.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();

            // Process the field name and value as needed
            Log.d("LessonData", modeName + ": " + lessonName + ", Field: " + fieldName + ", Value: " + fieldValue);
            // You can perform actions based on the field name and value here
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
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(a_user_1_login.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(a_user_1_login.this, "Failed to send reset email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
//    }

//    private void handleFacebookAccessToken(AccessToken token) {
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        FirebaseAuth.getInstance().signInWithCredential(credential)
//                .addOnCompleteListener(this, task -> {
//                    if (task.isSuccessful()) {
//                        // Sign in success
//                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                        Log.d(TAG, "signInWithCredential:success: " + user.getDisplayName());
//                    } else {
//                        // If sign in fails
//                        Log.w(TAG, "signInWithCredential:failure", task.getException());
//                    }
//                });
//    }
//
//    // Facebook login callback
//    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {
//        @Override
//        public void onSuccess(LoginResult loginResult) {
//            handleFacebookAccessToken(loginResult.getAccessToken());
//        }
//
//        @Override
//        public void onCancel() {
//            // Handle cancel
//        }
//
//        @Override
//        public void onError(FacebookException error) {
//            // Handle error
//        }
//    };
//
//// In your onCreate or onActivityResult
//LoginManager.getInstance().registerCallback(callbackManager, mCallback);
//
//
//}