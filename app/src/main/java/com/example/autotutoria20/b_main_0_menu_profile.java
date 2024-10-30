package com.example.autotutoria20;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Collections;


public class b_main_0_menu_profile extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText profileName;
    private EditText profileCategory;
    private EditText profileUsername;
    private EditText profileEmail;

    private Button buttonExitProfile;
    private Button buttonChangeEmail;
    private Button buttonChangePassword;


    private FirebaseAuth mAuth;
    private ShapeableImageView profileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_main_0_menu_profile);


        // Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            fetchUserData(userId);
        }

        // EditText
        profileName = findViewById(R.id.profile_name);
        profileCategory = findViewById(R.id.profile_category);
        profileUsername = findViewById(R.id.profile_username);
        profileEmail = findViewById(R.id.profile_email);

        // Button
        buttonExitProfile = findViewById(R.id.exit_profile);
        buttonChangeEmail = findViewById(R.id.button_change_email);
        buttonChangePassword = findViewById(R.id.button_change_password);

        // ShapableImageView
        profileImageView = findViewById(R.id.user_profile_picture);

        buttonExitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open change email dialog
                showChangeEmail();
            }
        });

        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open change password dialog
                showChangePasswordDialog();
//                changePassword(currentPassword, newPassword);
            }
        });

    }

    public interface EmailChangeListener {
        void onEmailChanged(String newEmail);
    }

    private void showChangeEmail() {
        // Create an AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.b_main_0_menu_profile_email, null);
        builder.setView(dialogView);

        // Find views in the dialog layout
        EditText newEmailAddress = dialogView.findViewById(R.id.new_email);
        EditText passwordText = dialogView.findViewById(R.id.password);

        ImageView exitButton = dialogView.findViewById(R.id.close_button);
        Button cancelButton = dialogView.findViewById(R.id.cancel_button);
        Button submitButton = dialogView.findViewById(R.id.change_email_button);

        // Create the dialog
        AlertDialog dialog = builder.create();

        // Set up cancel button
        cancelButton.setOnClickListener(v -> {
            // Dismiss the dialog
            dialog.dismiss();
        });

        // Set up exit button
        exitButton.setOnClickListener(v -> {
            // Dismiss the dialog
            dialog.dismiss();
        });

        // Set up submit button
        submitButton.setOnClickListener(v -> {
            String email = newEmailAddress.getText().toString();
            String password = passwordText.getText().toString();
            if (isValidEmail(email)) {
                changeEmailAddress(email, password); // Call your method with new email
                dialog.dismiss(); // Dismiss the dialog after submission
            } else {
                Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show(); // Show the dialog
    }

    // Helper method to validate email format
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void changeEmailAddress(String newEmail, String password) {

        Toast.makeText(getApplicationContext(), "We're still working on this feature.", Toast.LENGTH_SHORT).show();

//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        String userId = auth.getCurrentUser().getUid();
//
//        // Get current user
//        FirebaseUser user = auth.getCurrentUser();
//
//        // Re-authenticate the user
//        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), password);
//        user.reauthenticate(credential)
//                .addOnCompleteListener(reauthTask -> {
//                    if (reauthTask.isSuccessful()) {
//                        Log.d("Auth", "Reauthentication successful");
//
//                        // Update email in Firebase Authentication
//                        user.updateEmail(newEmail)
//                                .addOnCompleteListener(updateTask -> {
//                                    if (updateTask.isSuccessful()) {
//                                        Log.d("Auth", "Email updated successfully in Authentication");
//                                        Toast.makeText(getApplicationContext(), "Email updated successfully!", Toast.LENGTH_SHORT).show();
//
//                                        // Now update Firestore
//                                        DocumentReference userRef = db.collection("users").document(userId);
//                                        userRef.update("Email Address", newEmail)
//                                                .addOnSuccessListener(aVoid -> {
//                                                    Log.d("Firestore", "Email Address updated successfully in Firestore");
//                                                    Toast.makeText(getApplicationContext(), "Email Address updated in Firestore!", Toast.LENGTH_SHORT).show();
//                                                })
//                                                .addOnFailureListener(e -> {
//                                                    Log.e("Firestore", "Error updating email in Firestore", e);
//                                                    Toast.makeText(getApplicationContext(), "Error updating email in Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                                                });
//                                    } else {
//                                        Log.e("Auth", "Error updating email in Authentication", updateTask.getException());
//                                        Toast.makeText(getApplicationContext(), "Error updating email: " + updateTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                    } else {
//                        Log.e("Auth", "Reauthentication failed.", reauthTask.getException());
//                        Toast.makeText(getApplicationContext(), "Reauthentication failed: " + reauthTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
    }

//    // Edit email
//    private void changeEmailAddress(String newEmail) {
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        String userId = auth.getCurrentUser().getUid();
//
//        // Update email in Firebase Authentication
//        auth.getCurrentUser().updateEmail(newEmail)
//                .addOnCompleteListener(task -> {
//                    if (task.isComplete()) {
//                        // Email updated successfully in Authentication
//                        Log.d("Auth", "Email updated successfully in Authentication");
//
//                        // Now update Firestore
//                        DocumentReference userRef = db.collection("users").document(userId);
//                        userRef.update("Email Address", newEmail)
//                                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Email Address updated successfully"))
//                                .addOnFailureListener(e -> Log.e("Firestore", "Error updating email in Firestore", e));
//                    } else {
//                        Log.e("Auth", "Error updating email in Authentication", task.getException());
//                    }
//                });
//
//        // Optionally finish the activity here if you want to close it immediately
//        // finish();
//    }

    private void showChangePasswordDialog() {
        // Create an AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(b_main_0_menu_profile.this);

        // Inflate the custom layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.b_main_0_menu_profile_password, null);
        builder.setView(dialogView);

        // Initialize input fields
        EditText currentPasswordInput = dialogView.findViewById(R.id.current_password);
        EditText newPasswordInput = dialogView.findViewById(R.id.new_password);
        EditText repeatNewPasswordInput = dialogView.findViewById(R.id.repeat_new_password);

        // Set up the buttons
        Button cancelButton = dialogView.findViewById(R.id.cancel_button);
        Button changePasswordButton = dialogView.findViewById(R.id.change_password_button);

        // Show the dialog
        AlertDialog dialog = builder.create();

        // Handle the Cancel button click
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        // Handle the Change Password button click
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPassword = currentPasswordInput.getText().toString();
                String newPassword = newPasswordInput.getText().toString();
                String repeatNewPassword = repeatNewPasswordInput.getText().toString();

                // Validate input and process password change
                if (validatePasswords(currentPassword, newPassword, repeatNewPassword)) {
                    changePassword(currentPassword, newPassword);
                    dialog.dismiss();  // Close the dialog after processing
                } else {
                    Toast.makeText(b_main_0_menu_profile.this, "Passwords do not match or are invalid.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    // Method to validate passwords
    private boolean validatePasswords(String current, String newPass, String repeat) {
        return !current.isEmpty() && !newPass.isEmpty() && newPass.equals(repeat);
    }

    // change password
    private void changePassword(String currentPassword, String newPassword) {

        Toast.makeText(getApplicationContext(), "We're still working on this feature.", Toast.LENGTH_SHORT).show();

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        if (user != null) {
//            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);
//
//            // Re-authenticate the user
//            user.reauthenticate(credential).addOnCompleteListener(task -> {
//                if (task.isSuccessful()) {
//                    // Update the password in Firebase Authentication
//                    user.updatePassword(newPassword).addOnCompleteListener(updateTask -> {
//                        if (updateTask.isSuccessful()) {
//                            // Password updated successfully in Firebase Authentication
//                            String hashedNewPassword = a_user_3_password_encryption.hashPassword(newPassword);
//
//                            // Store the hashed password in the database for reference
//                            updateHashedPasswordInDatabase(user.getUid(), hashedNewPassword);
//
//                            Toast.makeText(getApplicationContext(), "Password updated successfully", Toast.LENGTH_SHORT).show();
//                        } else {
//                            // Handle failure in updating password
//                            Toast.makeText(getApplicationContext(), "Error updating password: " + updateTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "Re-authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        } else {
//            Toast.makeText(getApplicationContext(), "No user is logged in", Toast.LENGTH_SHORT).show();
//        }
    }



    // Helper method to update hashed password in Firebase Realtime Database
    private void updateHashedPasswordInDatabase(String uid, String hashedPassword) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(uid);
        databaseReference.child("Password").setValue(hashedPassword)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("UpdateHashedPassword", "Hashed password updated successfully.");
                    } else {
                        Log.e("UpdateHashedPassword", "Error updating hashed password: " + task.getException().getMessage());
                    }
                });
    }

    private void fetchUserData(String userId) {

        String TAG = "fetchUserData";

        DocumentReference userRef = db.collection("users").document(userId);
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        // User's Personal Information
                        profileName.setText(document.getString("First Name") + " " + document.getString("Last Name"));
                        profileCategory.setText(document.getString("User Category"));
                        profileUsername.setText(document.getString("Username"));
                        profileEmail.setText(document.getString("Email Address"));

                        String gender = document.getString("Gender");

                        // Check if the user has a custom profile picture
                        Boolean hasCustomProfilePicture = document.getBoolean("hasCustomProfilePicture");
                        if (hasCustomProfilePicture != null && hasCustomProfilePicture) {
                            Log.e(TAG, "I have my own Profile Picture!");
                            String customProfilePictureUrl = document.getString("profilePictureUrl");
                            if (customProfilePictureUrl != null) {
                                Picasso.get().load(customProfilePictureUrl).into(profileImageView);
                            }
                        } else {
                            Log.e(TAG, "I aint got my own Profile Picture!");
                            // User does not have a custom profile picture yet
                            if (gender != null) {
                                if (gender.equalsIgnoreCase("male")) {
                                    // Apply male anonymous avatar
                                    profileImageView.setImageResource(R.drawable.default_male);
                                } else if (gender.equalsIgnoreCase("female")) {
                                    // Apply female anonymous avatar
                                    profileImageView.setImageResource(R.drawable.default_female);
                                } else {
                                    profileImageView.setImageResource(R.drawable.default_generic);
                                }
                            } else {
                                // Handle case where gender is null
                                Toast.makeText(b_main_0_menu_profile.this, "Par wala kang Gender??", Toast.LENGTH_SHORT).show();
                            }
                        }


                    } else {
                        Toast.makeText(b_main_0_menu_profile.this, "No user data found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(b_main_0_menu_profile.this, "Error fetching user data", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
