package com.example.autotutoria20;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

public class t_UserDataFromDatabase {

    static Boolean isDataRetrieved = false;
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    static FirebaseUser currentUser = mAuth.getCurrentUser();
    static String userId = currentUser.getUid();

    static Map<String, Object> userData = new HashMap<>();
    static Boolean isTutorialCompleted;

    static void getUserDataFromDatabase(a_user_1_login_handler activityInstance) {
        DocumentReference userRef = db.collection("users").document(userId);
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null && document.exists()) {
                    Map<String, Object> data = document.getData();
                    if (data != null) {
                        int totalEntries = data.size();
                        int progressIncrement = totalEntries > 0 ? 100 / totalEntries : 0;
                        int[] currentProgress = {0};

                        for (Map.Entry<String, Object> entry : data.entrySet()) {
                            userData.put(entry.getKey(), entry.getValue());
                            currentProgress[0] += progressIncrement;
                            a_user_1_login_handler.updateProgress(Math.min(currentProgress[0], 100));
                        }

                        isTutorialCompleted = userData.containsKey("Tutorial");
                        isDataRetrieved = true;

                        final Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(() -> {
                            a_user_1_login_handler.updateProgress(100);
                            activityInstance.hideLoadingDialog(); // Use the passed instance
                        });
                    }
                } else {
                    Log.e("UserData", "Document does not exist");
                }
            } else {
                Log.e("UserData", "Task failed", task.getException());
            }
        });
    } // end of getUserDataFromDatabase() method



}
