package com.example.autotutoria20;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class a_user_1_login_handler extends AppCompatActivity {

    static CustomLoadingDialog loadingDialog;
    private static final int MY_REQUEST_CODE = 100;
    private static FirebaseAuth mAuth;
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "a_user_1_login_handler";
    public static boolean tutorialCompleted;

    private static t_UserDataFromDatabase context;

    public a_user_1_login_handler(t_UserDataFromDatabase context) {
        this.context = context;
    }

    public a_user_1_login_handler() {
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!n_Network.isNetworkAvailable3(this)) {
            Toast.makeText(a_user_1_login_handler.this, "No Internet", Toast.LENGTH_SHORT).show();
            Log.i("Login Handler", "No Internet!");

            // show can't use app dialog
            // finish
            finish();


//            Intent loginIntent = new Intent(this, b_main_0_menu.class);
//            startActivity(loginIntent);

        } else {
//            Toast.makeText(a_user_1_login_handler.this, "Has Internet", Toast.LENGTH_SHORT).show();
//            Log.i("Login Handler", "Has Internet!");
        }

//        checkForAppUpdate();

        // Initialize Firebase
        FirebaseApp.initializeApp(this);
        if (FirebaseApp.getApps(this).isEmpty()) {
            Log.e(TAG, "Firebase initialization failed.");
            return;
        }

        mAuth = FirebaseAuth.getInstance();

        if (FirebaseApp.getInstance() != null) {
            Log.e(TAG, "Firebase initialized successfully.");
        } else {
            Log.e(TAG, "Firebase initialization failed.");
        }

        // Check if user is logged in
        // can be false if cleared data
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        Log.e(TAG, "retrieve isLoggedIn: " + isLoggedIn);

        // Check if user is logged in in SharedPreferences AND FirebaseAuth
        if (isLoggedIn && mAuth.getCurrentUser() != null) {
            Log.e(TAG, "You are already logged in!");

            showLoadingDialog();

        } else {

            // User is not logged in, redirect to login screen
            Log.e(TAG, "User is not logged in, redirecting to login.");
            Intent loginIntent = new Intent(this, a_user_1_login.class);
            startActivity(loginIntent);

        }

        createNotificationChannel();
    }

//    public void startApplication() {
//        Intent intent = new Intent(a_user_1_login_handler.this, b_main_0_menu.class);
//        startActivity(intent);
//        finish(); // Close current activity to prevent stacking
//    }


    private void checkTutorial() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
//            runOnUiThread(this::showLoadingDialog); // Show loading dialog immediately

            db.collection("users")
                    .document(userId)
                    .get()
                    .addOnCompleteListener(task -> {
//                        runOnUiThread(this::hideLoadingDialog); // Ensure dialog is hidden on completion

                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists() && document.contains("Tutorial")) {
                                tutorialCompleted = document.getBoolean("Tutorial");

                                Intent intent = tutorialCompleted
                                        ? new Intent(a_user_1_login_handler.this, b_main_0_menu.class)
                                        : new Intent(a_user_1_login_handler.this, b_main_0_menu_tutorial.class);

                                startActivity(intent);
                            } else {
                                Log.e(TAG, "Tutorial data missing.");
                            }
                        } else {
                            Log.e(TAG, "Task failed: ", task.getException());
                        }
                    })
                    .addOnFailureListener(e -> {
//                        runOnUiThread(this::hideLoadingDialog); // Ensure dialog is hidden on failure
                        Log.e(TAG, "Error fetching tutorial data", e);
                    });
        } else {
            Log.e(TAG, "Firebase user is null.");
        }
    }


//    private void checkTutorial() {
//
//        Log.e(TAG, "Let's do checkTutorial()");
//
//        FirebaseUser user = mAuth.getCurrentUser();
//        if (user != null) {
//            String userId = user.getUid();
//            Log.e(TAG, "Query na natin! | checkTutorial()");
//
//            showLoadingDialog();
//
//            db.collection("users")
//                    .document(userId)
//                    .get()
//                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                            if (task.isSuccessful()) {
//                                DocumentSnapshot document = task.getResult();
//                                if (document != null && document.exists()) {
//                                    if (document.contains("Tutorial")) {
//                                        tutorialCompleted = document.getBoolean("Tutorial");
//                                        Log.e(TAG, "Tutorial Completed: " + tutorialCompleted + " | checkTutorial()");
//
//                                        if (tutorialCompleted) {
//                                            Intent intent = new Intent(a_user_1_login_handler.this, b_main_0_menu.class);
//                                            startActivity(intent);
//
//                                        } else {
//                                            Intent intent = new Intent(a_user_1_login_handler.this, b_main_0_menu_tutorial.class);
//                                            startActivity(intent);
//                                        }
//
//
//                                    } else {
//                                        Log.e(TAG, "Tutorial field is missing in the document.");
//                                    }
//                                } else {
//                                    Log.e(TAG, "Document does not exist or user data is missing.");
//                                }
//                            } else {
//                                Log.e(TAG, "Task failed: ", task.getException());
//                            }
//
//                            Log.e(TAG, "Done Query | checkTutorial()"); // Moved inside onComplete
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.e(TAG, "I failed", e);
//                        }
//                    });
//
//        } else {
//            Log.e(TAG, "Firebase user is null, cannot check tutorial.");
//        }
//    }

    private void showLoadingDialog() {
        if (this != null && !this.isFinishing()) {
            loadingDialog = new CustomLoadingDialog(this);
            loadingDialog.setCancelable(false);
            loadingDialog.show();

            // Trigger data retrieval after showing the dialog
            t_UserDataFromDatabase.getUserDataFromDatabase(this);
        }
    }

    static void updateProgress(int progress) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.setProgress(progress); // Ensure setProgress updates UI elements
        }
    }

    public void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            if (t_UserDataFromDatabase.isDataRetrieved) {
                startApplication(); // Navigate to the next screen
            }
        }
    }



    public void startApplication() {
        Intent intent = new Intent(this, b_main_0_menu.class);
        startActivity(intent);
        finish();
    }


    // Move the checkTutorialCompletion method outside the onCreate method
    static void checkTutorialCompletion(TutorialCompletionCallback callback) {
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DocumentReference userRef = db.collection("users").document(userId);

            userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Boolean isComplete = document.getBoolean("Tutorial");

                            int tokenCount = Math.toIntExact(document.getLong("Token"));

                            Log.i(TAG, "NEVER STOP | Token Count: " + tokenCount);

                            // isama kona dito yung sa token hehe
                            b_main_0_menu.tokenCount.setText("" + tokenCount);

                            callback.onTutorialChecked(isComplete != null ? isComplete : false);
                        } else {
                            Log.e(TAG, "No user data found");
                            callback.onTutorialChecked(false);
                        }
                    } else {
                        Log.e(TAG, "Error fetching user data", task.getException());
                        callback.onTutorialChecked(false);
                    }
                }
            });
        } else {
            Log.e(TAG, "No authenticated user found.");
            callback.onTutorialChecked(false);
        }
    }

    // Interface for callback
    interface TutorialCompletionCallback {
        void onTutorialChecked(boolean isComplete);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel Name";
            String description = "Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(n_Worker.CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
