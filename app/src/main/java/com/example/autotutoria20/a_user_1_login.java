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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
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
    private TextView loginButton, forgotPassword;
    private LinearLayout signupButton, signWithGoogleButton;
    private EditText
        emailAddressTextView,
        usernameTextView,
        passwordTextView;
    private ImageButton showHidePasswordButton;
    private ImageView pncGotoPage;
    private static final int RC_SIGN_IN = 9001; // Define the request code
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_user_1_login_with_google);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize views and buttons
        loginButton = findViewById(R.id.btnLogin);
        signWithGoogleButton = findViewById(R.id.btnLoginWithGoogle);
        signupButton = findViewById(R.id.btnSignup);
        emailAddressTextView = findViewById(R.id.textUsername);
        usernameTextView = findViewById(R.id.textUsername);
        passwordTextView = findViewById(R.id.textPassword);
        forgotPassword = findViewById(R.id.forgotPassword);
        showHidePasswordButton = findViewById(R.id.btnShowPassword);
        pncGotoPage = findViewById(R.id.pnc_logo);

        // FORGOT PASSWORD
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showForgotPasswordDialog();
            }
        });

        // Show Password
        // Show Password
        showHidePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPassword();
            }
        });

        // LOGIN
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // this is modified to use Username and Password for logging in.
                String email = emailAddressTextView.getText().toString().trim();

//                String username = usernameTextView.getText().toString().trim();
                String password = passwordTextView.getText().toString().trim();

                loginUser(email, password);
            }
        });

        signWithGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Login with Google
                loginWithGoogle();
            }
        });

        // SIGNUP
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Signup Page
                startActivity(new Intent(a_user_1_login.this, a_user_2_signup.class));
            }
        });

        pncGotoPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFacebookPage();
            }
        });
    }

    private void loginWithGoogle() {
        // Configure Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);

        // Start the sign-in intent
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // Handle the result of the sign-in intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, authenticate with Firebase
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign-in success
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            // Update UI with the signed-in user's information
                        } else {
                            // If sign-in fails, display a message to the user.
                        }
                    });
        } catch (ApiException e) {
            // Handle sign-in failure
        }
    }

    private void showPassword() {
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

    private void openFacebookPage() {
        String facebookUrl = "https://www.facebook.com/ucpncofficial";
        String facebookAppUrl = "fb://facewebmodal/f?href=" + facebookUrl;
        String facebookLiteUrl = "fb://lite/";

        // Try to open Facebook App
        try {
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookAppUrl));
            facebookIntent.setPackage("com.facebook.katana"); // Facebook App package name
            startActivity(facebookIntent);
        } catch (Exception e) {
            // Try to open Facebook Lite
            try {
                Intent facebookLiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookLiteUrl));
                facebookLiteIntent.setPackage("com.facebook.lite"); // Facebook Lite package name
                startActivity(facebookLiteIntent);
            } catch (Exception liteException) {
                // Try to open Browser
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl));
                    startActivity(browserIntent);
                } catch (Exception browserException) {
                    // In case no browser is available, show error or handle fallback
                    Toast.makeText(this, "No application available to open Facebook", Toast.LENGTH_LONG).show();
                } // Catch no Browser Application
            } // Catch no Facebook Lite Application
        } // Catch no Facebook Application
    } // Method

    private void loginUser(String username, String enteredPassword) {

        // Query Firestore for the email associated with this username
        db.collection("users")

                // Go to User's Personal Details "Username", for the logging in process.
                .whereEqualTo("Username", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // Check if the task was successful
                        if (task.isSuccessful()) {
                            Log.d("Firestore", "Firestore query completed successfully");

                            QuerySnapshot querySnapshot = task.getResult();
                            if (!querySnapshot.isEmpty()) {

                                // A matching username is found
                                DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                                Log.d("LoginProcess", "Document retrieved: " + document.getData());

                                // Store retrieved data to String for later logging in
                                String email = document.getString("Email Address");
                                String storedHashedPassword = document.getString("Password");

                                // Log the retrieved email and password
                                Log.d("LoginProcess", "Retrieved email: " + email);
                                Log.d("LoginProcess", "Retrieved hashed password: " + storedHashedPassword);

                                // if Email has no value
                                if (email == null || email.isEmpty()) {
                                    Log.e("LoginProcess", "Email is missing for username: " + username);
                                    Toast.makeText(a_user_1_login.this, "Email is missing for this username", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                // if Password has no value
                                if (storedHashedPassword == null || storedHashedPassword.isEmpty()) {
                                    Log.e("LoginProcess", "Password is missing for username: " + username);
                                    Toast.makeText(a_user_1_login.this, "Password is missing for this username", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                // Verify the entered password against the stored hash (if using hashed passwords)
                                boolean passwordMatch = a_user_3_password_encryption.checkPassword(enteredPassword, storedHashedPassword);
                                Log.d("LoginProcess", "Password match status: " + passwordMatch);

                                if (passwordMatch) {
                                    Log.d("LoginProcess", "Password matches, attempting Firebase authentication");

                                    // Password matches, proceed with Firebase authentication using the retrieved email
                                    mAuth.signInWithEmailAndPassword(email, enteredPassword)
                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        FirebaseUser user = mAuth.getCurrentUser();
                                                        if (user != null) {
                                                            if (user.isEmailVerified()) {  // Check if the email is verified
                                                                String userId = user.getUid();
                                                                Log.d("LoginProcess", "Login successful. User ID: " + userId);
                                                                fetchUserInfo(userId);
                                                            } else {
                                                                Log.d("LoginProcess", "Email not verified");
                                                                Toast.makeText(a_user_1_login.this, "Please verify your email before logging in.", Toast.LENGTH_SHORT).show();
                                                                user.sendEmailVerification();  // Optionally resend the verification email
                                                            }
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
                            Log.e("Firestore", "Failed to retrieve username. Error: " + errorMessage);

                            if (task.getException() != null && task.getException().getMessage().contains("PERMISSION_DENIED")) {
                                Log.e("Firestore", "Permission Denied: Ensure Firestore security rules allow reading of 'Username' and 'Email Address'.");
                            }

                            Toast.makeText(a_user_1_login.this, errorMessage, Toast.LENGTH_SHORT).show();
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
//        Button createAccount = dialogView.findViewById(R.id.btn_create_new_account);
//        TextView loginButton = dialogView.findViewById(R.id.btn_login);`

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

//        createAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                startActivity(new Intent(a_user_1_login.this, a_user_2_signup.class));
//            }
//        });

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
