package com.example.autotutoria20;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class a_user_1_login_handler extends AppCompatActivity {

    private static FirebaseAuth mAuth;
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "a_user_1_login_handler";
    public static boolean tutorialCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!n_Network.isNetworkAvailable3(this)) {
//            Toast.makeText(a_user_1_login_handler.this, "No Internet", Toast.LENGTH_SHORT).show();

            Intent loginIntent = new Intent(this, b_main_0_menu.class);
            startActivity(loginIntent);

        }

        // Initialize Firebase
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        if (FirebaseApp.getInstance() != null) {
            Log.e(TAG, "Firebase initialized successfully.");
        } else {
            Log.e(TAG, "Firebase initialization failed.");
        }

        // Check if user is logged in
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        Log.e(TAG, "retrieve isLoggedIn: " + isLoggedIn);

        // Check if user is logged in in SharedPreferences AND FirebaseAuth
        if (isLoggedIn && mAuth.getCurrentUser() != null) {
            Log.e(TAG, "You are already logged in!");

            checkTutorial();



        } else {

            // User is not logged in, redirect to login screen
            Log.e(TAG, "User is not logged in, redirecting to login.");
            Intent loginIntent = new Intent(this, a_user_1_login.class);
            startActivity(loginIntent);

        }

        createNotificationChannel();
        finish();
    }

    private void checkTutorial() {

        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();

        db.collection("users")
                .document(userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // User's Personal Information
                                tutorialCompleted = document.getBoolean("Tutorial");

                                if (tutorialCompleted) Log.e(TAG, "Tutorial is already finished! | GWEN");
                                else Log.e(TAG, "Tutorial is not yet finished. | GWEN");
                            }
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "I failed");
                    }
                });
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
