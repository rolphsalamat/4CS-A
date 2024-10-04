//package com.example.autotutoria20;
//
//import android.content.Context;
//
//import androidx.annotation.NonNull;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class c_Lesson_fetchProgress extends Context {
//
//    private CustomLoadingDialog loadingDialog; // Loading dialog instance
//
//    // Show the loading dialog
//    private void showLoadingDialog() {
//        loadingDialog = new CustomLoadingDialog(this);
//        loadingDialog.setCancelable(false); // Prevent the dialog from being closed
//        loadingDialog.show();
//    }
//
//    // Hide the loading dialog
//    private void hideLoadingDialog() {
//        if (loadingDialog != null && loadingDialog.isShowing()) {
//            loadingDialog.dismiss();
//        }
//    }
//
//    public static Task<Map<Integer, Integer>> fetchProgressData() {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        DocumentReference progressRef = db.collection("users")
//                .document(userId)
//                .collection("Progressive Mode")
//                .document("Lesson 1");
//
//        // Return a Task with a map of module numbers and progress values
//        return progressRef.get().continueWith(task -> {
//            Map<Integer, Integer> moduleProgressMap = new HashMap<>();
//            if (task.isSuccessful()) {
//                DocumentSnapshot document = task.getResult();
//                if (document.exists() && document.getData() != null) {
//                    Map<String, Object> progressData = document.getData();
//
//                    for (Map.Entry<String, Object> entry : progressData.entrySet()) {
//                        String key = entry.getKey();
//                        Object value = entry.getValue();
//
//                        if (key.startsWith("M") && value instanceof Map) {
//                            Map<String, Object> moduleMap = (Map<String, Object>) value;
//
//                            if (moduleMap.containsKey("Progress")) {
//                                Long progress = (Long) moduleMap.get("Progress");
//                                int moduleNumber = Character.getNumericValue(key.charAt(1));
//                                moduleProgressMap.put(moduleNumber, progress != null ? progress.intValue() : 0);
//                            }
//                        }
//                    }
//                } else {
//                    // Handle the case where the document does not exist
//                    throw new Exception("No such document");
//                }
//            } else {
//                // Propagate error if the task fails
//                throw task.getException();
//            }
//
//            return moduleProgressMap;
//        });
//    }
//}


