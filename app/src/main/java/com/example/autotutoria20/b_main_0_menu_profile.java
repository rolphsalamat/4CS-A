package com.example.autotutoria20;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory;
import com.google.firebase.appcheck.FirebaseAppCheck;

import org.mindrot.jbcrypt.BCrypt;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;


public class b_main_0_menu_profile extends AppCompatActivity {
    private Uri imageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private AlertDialog changePasswordDialog;
    private AlertDialog changeUsernameDialog;
    private AlertDialog changeEmailDialog;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText profileName;
    private EditText profileCategory;
    private EditText profileUsername;
    private EditText profileEmail;

    private Button buttonExitProfile;
    private Button buttonChangeEmail;
    private Button buttonChangePassword;
    private Button buttonChangeUsername;

    // Initialize FirebaseAuth
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = mAuth.getCurrentUser();
    private String userId = currentUser.getUid();

    private ShapeableImageView profileImageView;
    private FrameLayout profileFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_main_0_menu_profile);

        // Initialize Firebase App Check
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
            SafetyNetAppCheckProviderFactory.getInstance());

        if (currentUser != null) {
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
        buttonChangeUsername = findViewById(R.id.button_change_username);
        buttonChangePassword = findViewById(R.id.button_change_password);

        // ShapableImageView
        profileImageView = findViewById(R.id.user_profile_picture);

        // Profile Picture
        profileFrameLayout = findViewById(R.id.profile_frame_layout);

        profileFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change profile picture
                changeProfilePicture();
            }
        });


        buttonExitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // no need to start activity.
//                startActivity(new Intent(b_main_0_menu_profile.this, b_main_0_menu.class));
            }
        });

        buttonChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open change email dialog
                Toast.makeText(b_main_0_menu_profile.this, "We're still working on this feature.", Toast.LENGTH_SHORT).show();
//                showChangeEmail();
            }
        });

        buttonChangeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open change email dialog
                showChangeUsername();
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

    private void changeProfilePicture() {
        // Step 1: Open image picker
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri sourceUri = data.getData();
            Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "cropped_image.jpg"));

            // Start uCrop activity
            UCrop.of(sourceUri, destinationUri)
                    .withAspectRatio(1, 1)
                    .withMaxResultSize(512, 512)
                    .start(this);
        } else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            final Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null) {
                imageUri = resultUri;
                uploadImageToFirebase();  // Upload the cropped image
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            Toast.makeText(this, "Crop error: " + cropError, Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImageToFirebase() {
        if (imageUri != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                byte[] data = baos.toByteArray();

                String userId = mAuth.getCurrentUser().getUid();
                StorageReference storageReference = FirebaseStorage.getInstance().getReference("profile_pictures/" + userId + ".jpg");

                Log.d("FirebaseStorage", "Uploading image to: " + storageReference.getPath());
                Log.d("FirebaseStorage", "Image URI: " + imageUri.toString());

                UploadTask uploadTask = storageReference.putBytes(data);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageUrl = uri.toString();
                                Log.d("FirebaseStorage", "Image uploaded successfully. URL: " + imageUrl);
                                saveImageUrlToFirestore(imageUrl);
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("FirebaseStorage", "Error uploading image", e);
                        Toast.makeText(b_main_0_menu_profile.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("FirebaseStorage", "Error converting image to bitmap", e);
            }
        } else {
            Log.e("FirebaseStorage", "Image URI is null");
            Toast.makeText(b_main_0_menu_profile.this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }


    private void saveImageUrlToFirestore(String imageUrl) {
        String userId = mAuth.getCurrentUser().getUid();
        DocumentReference userRef = db.collection("users").document(userId);

        userRef.update("profilePictureUrl", imageUrl, "hasCustomProfilePicture", true)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(b_main_0_menu_profile.this, "Profile picture updated", Toast.LENGTH_SHORT).show();
                        Picasso.get().load(imageUrl).into(profileImageView);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firestore", "Error updating Firestore", e);
                        Toast.makeText(b_main_0_menu_profile.this, "Failed to update profile picture: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void showChangeUsername() {
        // Create an AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.b_main_0_menu_profile_username, null);
        builder.setView(dialogView);

        // Find views in the dialog layout
        EditText newUsernameText = dialogView.findViewById(R.id.new_username);
        EditText passwordText = dialogView.findViewById(R.id.password);

        ImageView exitButton = dialogView.findViewById(R.id.close_button);
        Button cancelButton = dialogView.findViewById(R.id.cancel_button);
        Button submitButton = dialogView.findViewById(R.id.change_username_button);

        // Create the dialog
        changeUsernameDialog = builder.create();

        // Set up cancel button
        cancelButton.setOnClickListener(v -> {
            // Dismiss the dialog
            changeUsernameDialog.dismiss();
        });

        // Set up exit button
        exitButton.setOnClickListener(v -> {
            // Dismiss the dialog
            changeUsernameDialog.dismiss();
        });

        // Set up submit button
        submitButton.setOnClickListener(v -> {

            String oldUsername = profileUsername.getText().toString();
            String newUsername = newUsernameText.getText().toString();
            String password = passwordText.getText().toString();

            verifyAndUpdateUsername(oldUsername, password, newUsername);

        });

        changeUsernameDialog.show(); // Show the dialog
    }

    private void verifyAndUpdateUsername(String currentUsername, String providedPassword, String newUsername) {
        String TAG = "verifyAndUpdateUsername";

        // Step 1: Retrieve all users from the Firestore database
        db.collection("users")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    boolean usernameFound = false;
                    String userId = null;
                    String email = null;
                    String storedHashedPassword = null;

                    Log.e(TAG, "Searching for username in Firestore...");

                    // Step 2: Search for the username in the users collection
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        String dbUsername = document.getString("Username");

                        if (dbUsername != null && dbUsername.equals(currentUsername)) {
                            usernameFound = true;
                            userId = document.getId(); // Get the document's UID
                            email = document.getString("Email Address");
                            storedHashedPassword = document.getString("Password");
                            break; // Exit loop if username is found
                        }
                    }

                    if (usernameFound && userId != null) {
                        Log.e(TAG, "Username found. Validating password...");

                        // Step 3: Verify the provided password
                        if (a_user_3_password_encryption.checkPassword(providedPassword, storedHashedPassword)) {
//                        if (isPasswordCorrect(providedPassword, storedHashedPassword)) {
                            Log.e(TAG, "Password validated successfully. Updating username...");

                            // Step 4: Update the username in Firestore
                            Map<String, Object> updates = new HashMap<>();
                            updates.put("Username", newUsername);

                            db.collection("users")
                                    .document(userId)
                                    .update(updates)
                                    .addOnSuccessListener(aVoid -> {
                                        Log.d(TAG, "Username successfully updated.");
                                        Toast.makeText(this, "Username updated successfully!", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.e(TAG, "Error updating Username", e);
                                        Toast.makeText(this, "Error updating username. Please try again.", Toast.LENGTH_SHORT).show();
                                    });
                            changeUsernameDialog.dismiss();
                        } else {
                            Log.e(TAG, "Invalid password.");
                            Toast.makeText(this, "Invalid password. Update failed.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e(TAG, "Username not found.");
                        Toast.makeText(this, "Username not found. Please check and try again.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error retrieving users from Firestore", e);
                    Toast.makeText(this, "Error accessing the database. Please try again.", Toast.LENGTH_SHORT).show();
                });
    }

//    // Helper method to compare passwords
//    private boolean isPasswordCorrect(String providedPassword, String storedHashedPassword) {
//        // Replace this with your actual password verification logic (e.g., hashing and comparison)
//        return providedPassword.equals(storedHashedPassword);
//    }


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
        changeEmailDialog = builder.create();

        // Set up cancel button
        cancelButton.setOnClickListener(v -> {
            // Dismiss the dialog
            changeEmailDialog.dismiss();
        });

        // Set up exit button
        exitButton.setOnClickListener(v -> {
            // Dismiss the dialog
            changeEmailDialog.dismiss();
        });

        // Set up submit button
        submitButton.setOnClickListener(v -> {

            String email = newEmailAddress.getText().toString();
            String password = passwordText.getText().toString();

            if (isValidEmail(email)) {

                changeEmailAddress(email, password); // Call your method with new email

            } else {
                Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
            }

        });

        changeEmailDialog.show(); // Show the dialog
    }

    // Helper method to validate email format
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void changeEmailAddress(String newEmail, String password) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Check if user is signed in
        if (user == null) {
            Toast.makeText(getApplicationContext(), "No user is signed in.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the new email format is valid
        if (!isValidEmail(newEmail)) {
            Toast.makeText(getApplicationContext(), "Invalid email format.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the email is verified
        if (!user.isEmailVerified()) {
            Toast.makeText(getApplicationContext(), "Please verify your current email before updating.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Re-authenticate the user
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), password);
        user.reauthenticate(credential)
                .addOnCompleteListener(reauthTask -> {
                    if (reauthTask.isSuccessful()) {
                        Log.d("Auth", "Reauthentication successful");

                        // Update email in Firebase Authentication
                        user.updateEmail(newEmail)
                                .addOnCompleteListener(updateTask -> {
                                    if (updateTask.isSuccessful()) {
                                        Log.d("Auth", "Email updated successfully in Authentication");
                                        Toast.makeText(getApplicationContext(), "Email updated successfully! A verification email has been sent.", Toast.LENGTH_SHORT).show();

                                        // Send verification email
                                        user.sendEmailVerification()
                                                .addOnCompleteListener(verificationTask -> {
                                                    if (verificationTask.isSuccessful()) {
                                                        Log.d("Auth", "Verification email sent to: " + newEmail);
                                                    } else {
                                                        Log.e("Auth", "Error sending verification email: ", verificationTask.getException());
                                                    }
                                                });

                                        // Now update Firestore
                                        DocumentReference userRef = FirebaseFirestore.getInstance()
                                                .collection("users")
                                                .document(user.getUid());
                                        userRef.update("Email Address", newEmail)
                                                .addOnSuccessListener(aVoid -> {
                                                    Log.d("Firestore", "Email Address updated successfully in Firestore");
                                                    Toast.makeText(getApplicationContext(), "Email Address updated in Firestore!", Toast.LENGTH_SHORT).show();
                                                })
                                                .addOnFailureListener(e -> {
                                                    Log.e("Firestore", "Error updating email in Firestore", e);
                                                    Toast.makeText(getApplicationContext(), "Error updating email in Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                });
                                    } else {
                                        // Handle specific FirebaseAuth exceptions
                                        if (updateTask.getException() instanceof FirebaseAuthException) {
                                            String errorCode = ((FirebaseAuthException) updateTask.getException()).getErrorCode();
                                            if ("ERROR_OPERATION_NOT_ALLOWED".equals(errorCode)) {
                                                Toast.makeText(getApplicationContext(), "Sign-in provider is disabled. Please enable it in Firebase console.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        Log.e("Auth", "Error updating email in Authentication", updateTask.getException());
                                        Toast.makeText(getApplicationContext(), "Error updating email: " + updateTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Log.e("Auth", "Reauthentication failed.", reauthTask.getException());
                        Toast.makeText(getApplicationContext(), "Reauthentication failed: " + reauthTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


//    // Edit email
//    private void changeEmailAddress(String newEma il) {
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
        changePasswordDialog = builder.create();

        // Handle the Cancel button click
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog
                changePasswordDialog.dismiss();
            }
        });
    
        // Handle the Change Password button click
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPassword = currentPasswordInput.getText().toString();
                String newPassword = newPasswordInput.getText().toString();
                String repeatNewPassword = repeatNewPasswordInput.getText().toString();

                if (!newPassword.equals(repeatNewPassword))
                    Toast.makeText(b_main_0_menu_profile.this, "Make sure new password matches with the confirmation", Toast.LENGTH_SHORT).show();

                else if (currentPassword.isEmpty())
                    Toast.makeText(b_main_0_menu_profile.this, "Please fill the current password field", Toast.LENGTH_SHORT).show();

                else if (newPassword.isEmpty())
                    Toast.makeText(b_main_0_menu_profile.this, "Please fill the new password field", Toast.LENGTH_SHORT).show();

                else if (repeatNewPassword.isEmpty())
                    Toast.makeText(b_main_0_menu_profile.this, "Please fill the confirm new password field", Toast.LENGTH_SHORT).show();

                else
                    changePassword(currentPassword, newPassword, userId);

            }
        });

        changePasswordDialog.show();
    }

    // Function to change password
    public void changePassword(String currentPassword, String newPassword, String userId) {
        Log.e("changePassword()", "I am inside changePassword() method");

        // Reference to the user's document in Firestore
        DocumentReference userRef = FirebaseFirestore.getInstance().collection("users").document(userId);

        // Fetch the stored password hash from Firestore
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("changePassword()", "Successfully retrieved stored password hash.");
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String storedHash = document.getString("Password");

                    // Verify the current password
                    if (BCrypt.checkpw(currentPassword, storedHash)) {
                        Log.d("changePassword()", "Current password is correct. Proceeding to update password.");

                        // Hash the new password
                        String newHash = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                        userRef.update("Password", newHash).addOnCompleteListener(updateTask -> {
                            if (updateTask.isSuccessful()) {
                                Log.d("changePassword()", "Password updated successfully.");
                                Toast.makeText(b_main_0_menu_profile.this, "Password updated successfully.", Toast.LENGTH_SHORT).show();
                                changePasswordDialog.dismiss();
                            } else {
                                Log.e("changePassword()", "Error updating password: " + updateTask.getException().getMessage());
                            }
                        });
                    } else {
                        Log.e("changePassword()", "Current password is incorrect.");
                        System.out.println("Current password is incorrect."); // Consider using a more user-friendly message in production.
                    }
                } else {
                    Log.e("changePassword()", "No such user document exists.");
                }
            } else {
                Log.e("changePassword()", "Error retrieving stored password hash: " + task.getException().getMessage());
            }
        });
    }

    public interface PasswordValidationCallback {
        void onValidationResult(boolean isValid);
    }

    private void validatePasswords(String current, String newPass, String repeat, PasswordValidationCallback callback) {

        String TAG = "validatePasswords()";

        Log.e(TAG, "ROP CHECK NATIN KUNG VALID HA");

        // Check if any fields are empty or if new passwords do not match
        if (current.isEmpty() || newPass.isEmpty() || !newPass.equals(repeat)) {
            Log.e(TAG, "Validation failed: Empty fields or passwords do not match.");
            callback.onValidationResult(false); // Notify failure via callback
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Retrieve the user's document from Firestore
        db.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener(document -> {
                    // Check if the document exists
                    if (document.exists()) {
                        // Retrieve the stored hashed password
                        String storedHashedPassword = document.getString("Password");

                        // Verify the entered password against the stored hash
                        boolean isMatch = a_user_3_password_encryption.checkPassword(current, storedHashedPassword);
                        Log.d(TAG, "Password match status: " + isMatch);
                        callback.onValidationResult(isMatch); // Notify result via callback
                    } else {
                        Log.e(TAG, "User document does not exist.");
                        callback.onValidationResult(false); // Notify failure via callback
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error retrieving document", e);
                    Toast.makeText(b_main_0_menu_profile.this, "Error retrieving user data", Toast.LENGTH_SHORT).show();
                    callback.onValidationResult(false); // Notify failure via callback
                });
    }

    // change password
    private void changePassword(String currentPassword, String newPassword) {

        Toast.makeText(getApplicationContext(), "We're still working on this feature.", Toast.LENGTH_SHORT).show();

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        String userId = user.getUid();
//
//        String TAG = "changePassword("+currentPassword+","+newPassword+");";
//
//        Log.e(TAG,"Original New Pasword: " + newPassword);
//
//        String newHashedPassword = a_user_3_password_encryption.hashPassword(newPassword);
//
//        Log.e(TAG,"New Hashed Pasword: " + newHashedPassword);
//
//        // Construct the field path using dot notation
//        String path = "Password";
//
//        Log.e(TAG, "Field path for update: " + path + " | New Password: " + newPassword);
//
//        Map<String, Object> updates = new HashMap<>();
//        updates.put(path, newHashedPassword); // Update only the specific field
//
//        db.collection("users")
//                .document(userId)
//                .update(updates)
//                .addOnSuccessListener(aVoid -> {
//                    Log.d(TAG, "Password successfully updated");
//                    finish(); // Finish the activity after successful update
//                })
//                .addOnFailureListener(e -> {
//                    Log.e(TAG, "Error updating Username", e);
//                    Toast.makeText(b_main_0_menu_profile.this, "Error Updating Username", Toast.LENGTH_SHORT).show();
//                    finish();
//                });


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