package com.example.autotutoria20;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
                showChangeEmail(new EmailChangeListener() {
                    @Override
                    public void onEmailChanged(String newEmail) {
                        changeEmailAddress(newEmail); // Call your method with new email
                    }
                });
            }
        });

        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open change password dialog
//                changePassword(currentPassword, newPassword);
            }
        });

    }

    public interface EmailChangeListener {
        void onEmailChanged(String newEmail);
    }

    private void showChangeEmail(EmailChangeListener emailChangeListener) {
        // Create an AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.b_main_0_menu_profile_email, null);
        builder.setView(dialogView);

        // Find views in the dialog layout
        EditText newEmailAddress = dialogView.findViewById(R.id.newEmailAddress);
        Button exitButton = dialogView.findViewById(R.id.exitButton);
        Button submitButton = dialogView.findViewById(R.id.submitButton);

        // Create the dialog
        AlertDialog dialog = builder.create();

        // Set up exit button
        exitButton.setOnClickListener(v -> {
            // Dismiss the dialog
            dialog.dismiss();
        });

        // Set up submit button
        submitButton.setOnClickListener(v -> {
            String email = newEmailAddress.getText().toString();
            if (isValidEmail(email)) {
                changeEmailAddress(email); // Call your method with new email
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

    // Edit email
    private void changeEmailAddress(String newEmail) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference userRef = db.collection("users").document(userId);
        userRef.update("Email Address", newEmail)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Email Address updated successfully"))
                .addOnFailureListener(e -> Log.e("Firestore", "Error updating email", e));

    }

    // change password
    private void changePassword(String currentPassword, String newPassword) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Re-authenticate the user
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);

            user.reauthenticate(credential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Now update the user's password
                    user.updatePassword(newPassword).addOnCompleteListener(updateTask -> {
                        if (updateTask.isSuccessful()) {
                            // Password updated successfully
                            String hashedNewPassword = a_user_3_password_encryption.hashPassword(newPassword);
                            updateHashedPasswordInDatabase(user.getUid(), hashedNewPassword);
                            Toast.makeText(getApplicationContext(), "Password updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            // Handle failure
                            Toast.makeText(getApplicationContext(), "Error updating password: " + updateTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // Handle re-authentication failure
                    Toast.makeText(getApplicationContext(), "Re-authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // User is not logged in
            Toast.makeText(getApplicationContext(), "No user is logged in", Toast.LENGTH_SHORT).show();
        }
    }

    // Helper method to update hashed password in Firebase Realtime Database
    private void updateHashedPasswordInDatabase(String uid, String hashedPassword) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(uid);
        databaseReference.child("hashed_password").setValue(hashedPassword)
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
