package com.example.autotutoria20;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

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

import java.util.HashMap;
import java.util.Map;

public class a_user_0_database {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Context context;

    public a_user_0_database(Context context) {
        this.context = context;
        this.mAuth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();
    }

    // Method to authenticate user using Firebase Auth
    public void loginUser(String email, String password, final LoginCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                callback.onSuccess(user.getUid());
                            } else {
                                callback.onFailure("Account does not exist");
                            }
                        } else {
                            callback.onFailure("Authentication failed: " + task.getException().getMessage());
                        }
                    }
                });
    }

    // Method to fetch user information from Firestore
    public void fetchUserInfo(String userId, final UserInfoCallback callback) {
        DocumentReference userRef = db.collection("users").document(userId);
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        callback.onSuccess(document.getData());
                    } else {
                        callback.onFailure("User document does not exist");
                    }
                } else {
                    callback.onFailure("Failed to retrieve user information: " + task.getException().getMessage());
                }
            }
        });
    }

    // Method to fetch lesson data from Firestore
    public void fetchLessonData(String userId, final LessonDataCallback callback) {
        final Map<String, Map<String, Object>> progressiveModeData = new HashMap<>();
        final Map<String, Map<String, Object>> freeUseModeData = new HashMap<>();

        CollectionReference progressiveModeRef = db.collection("users").document(userId).collection("Progressive Mode");
        CollectionReference freeUseModeRef = db.collection("users").document(userId).collection("Free Use Mode");

        progressiveModeRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null && !querySnapshot.isEmpty()) {
                        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                            progressiveModeData.put(document.getId(), document.getData());
                        }
                    }

                    // Fetch Free Use Mode data
                    freeUseModeRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                QuerySnapshot querySnapshot = task.getResult();
                                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                                        freeUseModeData.put(document.getId(), document.getData());
                                    }
                                }
                                callback.onSuccess(progressiveModeData, freeUseModeData);
                            } else {
                                callback.onFailure("Failed to retrieve Free Use Mode data: " + task.getException().getMessage());
                            }
                        }
                    });
                } else {
                    callback.onFailure("Failed to retrieve Progressive Mode data: " + task.getException().getMessage());
                }
            }
        });
    }

    // Method to reset password
    public void resetPassword(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Password reset email sent", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Failed to send reset email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // Callback interfaces
    public interface LoginCallback {
        void onSuccess(String userId);
        void onFailure(String message);
    }

    public interface UserInfoCallback {
        void onSuccess(Map<String, Object> userInfo);
        void onFailure(String message);
    }

    public interface LessonDataCallback {
        void onSuccess(Map<String, Map<String, Object>> progressiveModeData, Map<String, Map<String, Object>> freeUseModeData);
        void onFailure(String message);
    }
}
