package com.example.autotutoria20;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class a_user_1_login_handler extends AppCompatActivity {

    private static FirebaseAuth mAuth;
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "a_user_1_login_handler";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        // Check if user is logged in
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        Log.e(TAG, "retrieve isLoggedIn: " + isLoggedIn);

        if (isLoggedIn) {
            Log.e(TAG, "You are already logged in!");

            checkTutorialCompletion(new TutorialCompletionCallback() {
                @Override
                public void onTutorialChecked(boolean isComplete) {
                    if (isComplete) {
                        Log.e(TAG, "Tutorial has been completed.");
                        // User has completed the tutorial, redirect to main menu
                        Intent mainMenuIntent = new Intent(a_user_1_login_handler.this, b_main_0_menu.class);
                        startActivity(mainMenuIntent);
                    } else {
                        Log.e(TAG, "Tutorial has not been completed.");
                        // User is logged in but has not completed the tutorial, redirect to tutorial
                        Intent tutorialIntent = new Intent(a_user_1_login_handler.this, b_main_0_menu_tutorial.class);
                        startActivity(tutorialIntent);
                    }
                }
            });
        } else {
            // User is not logged in, redirect to login screen
            Intent loginIntent = new Intent(this, a_user_1_login.class);
            startActivity(loginIntent);
        }

        createNotificationChannel();
        finish();
    }

    interface TutorialCompletionCallback {
        void onTutorialChecked(boolean isComplete);
    }

    // may kasama daw tong email verifier?? what for??

//    static void checkTutorialCompletion(TutorialCompletionCallback callback) {
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) {
//            if (currentUser.isEmailVerified()) {  // Check if email is verified
//                String userId = currentUser.getUid();
//                DocumentReference userRef = db.collection("users").document(userId);
//
//                userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot document = task.getResult();
//                            if (document.exists()) {
//                                Boolean isComplete = document.getBoolean("Tutorial");
//                                if (isComplete != null) {
//                                    callback.onTutorialChecked(isComplete);
//                                } else {
//                                    callback.onTutorialChecked(false);
//                                }
//                            } else {
//                                Log.e(TAG, "No user data found");
//                                callback.onTutorialChecked(false);
//                            }
//                        } else {
//                            Log.e(TAG, "Error fetching user data", task.getException());
//                            callback.onTutorialChecked(false);
//                        }
//                    }
//                });
//            } else {
//                Log.e(TAG, "Email not verified");
//                Toast.makeText(a_user_1_login_handler.this, "Please verify your email to continue.", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            callback.onTutorialChecked(false);
//        }
//    }


    static void checkTutorialCompletion(TutorialCompletionCallback callback) {
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
                            if (isComplete != null) {
                                callback.onTutorialChecked(isComplete);
                            } else {
                                callback.onTutorialChecked(false);
                            }
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
            callback.onTutorialChecked(false);
        }
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

