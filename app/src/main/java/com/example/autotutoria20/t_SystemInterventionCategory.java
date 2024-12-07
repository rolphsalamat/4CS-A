package com.example.autotutoria20;

import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class t_SystemInterventionCategory {

    public static String NLG_Feedback;
    public static double NLG_Score;

    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    static List<String> categories = Arrays.asList("Novice", "Beginner", "Intermediate", "Advance", "Expert");
    static String TAG = "Change Category";

    static void retrieveCategories() {
        DocumentReference categoryRef = db.collection("Categories").document("Categories");

        categoryRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                List<String> retrievedCategories = (List<String>) documentSnapshot.get("Categories");
                if (retrievedCategories != null) {
                    categories.clear();
                    categories.addAll(retrievedCategories);
                    Log.d(TAG, "Categories retrieved: " + categories);
                } else {
                    Log.e(TAG, "Categories field is empty or null");
                }
            } else {
                Log.e(TAG, "Document does not exist");
            }
        }).addOnFailureListener(e -> Log.e(TAG, "Error retrieving categories", e));
    }

    static void changeCategoryFromDatabase(String newCategory) {
        DocumentReference userRef = db.collection("users").document(userId);

        Log.i(TAG, "Changing category to: " + newCategory);

        userRef.update("User Category", newCategory)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "User category updated successfully to " + newCategory))
                .addOnFailureListener(e -> Log.e(TAG, "Error updating user category", e));

        resetAllData();

    }

    public static void resetAllData() {
        String TAG = "ResetAllData";

        // Firebase references
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user != null ? user.getUid() : null;

        if (userId == null) {
            Log.e(TAG, "User not authenticated");
            return; // Exit if the user is not logged in
        }

        // Collection paths
        String[] modes = {"Progressive Mode", "Free Use Mode"};

        // Reference "text lesson" and iterate dynamically
        db.collection("text lesson")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (querySnapshot.isEmpty()) {
                        Log.w(TAG, "No lessons found in 'text lesson' collection");
                        return;
                    }

                    // Iterate through each lesson document (e.g., "Lesson 1", "Lesson 2")
                    for (DocumentSnapshot lessonDoc : querySnapshot.getDocuments()) {
                        String lessonName = lessonDoc.getId(); // e.g., "Lesson 1"

                        // Fetch the content inside the lesson to determine module count
                        db.collection("text lesson")
                                .document(lessonName)
                                .get()
                                .addOnSuccessListener(lessonSnapshot -> {
                                    if (!lessonSnapshot.exists()) {
                                        Log.w(TAG, "No data found in " + lessonName);
                                        return;
                                    }

                                    // Get the keys inside the lesson document to determine the number of modules
                                    Map<String, Object> lessonData = lessonSnapshot.getData();
                                    if (lessonData == null) {
                                        Log.w(TAG, "Lesson data is empty for " + lessonName);
                                        return;
                                    }

                                    int moduleCount = lessonData.size(); // Count modules in this lesson
                                    Map<String, Object> resetData = new HashMap<>();

                                    // Generate module maps dynamically
                                    for (int i = 1; i <= moduleCount; i++) {
                                        String moduleKey = "M" + i;
                                        Map<String, Object> moduleData = new HashMap<>();
                                        moduleData.put("Attempts", 0);
                                        moduleData.put("Learning Status", "Unknown");
                                        moduleData.put("BKT Score", 0.0);
                                        moduleData.put("Pre-Test BKT Score", 0.0);
                                        moduleData.put("Post-Test BKT Score", 0.0);
                                        moduleData.put("Pre-Test Score", 0.0);
                                        moduleData.put("Post-Test Score", 0.0);
                                        resetData.put(moduleKey, moduleData);
                                    }

                                    // Update data for both modes
                                    for (String mode : modes) {
                                        String updatedLessonName = lessonName.replace("Lesson", "M");
                                        db.collection("users").document(userId)
                                                .collection(mode)
                                                .document(updatedLessonName)
                                                .set(resetData)
                                                .addOnSuccessListener(aVoid -> Log.d(TAG, "Reset data for " + mode + " | " + updatedLessonName))
                                                .addOnFailureListener(e -> Log.e(TAG, "Failed to reset data for " + mode + " | " + updatedLessonName, e));
                                    }
                                })
                                .addOnFailureListener(e -> Log.e(TAG, "Failed to fetch data for " + lessonName, e));
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, "Failed to fetch 'text lesson' collection", e));

        Log.d(TAG, "Reset all data process initiated.");
    }

    public static void calculateGrowth(
            int preTestScore, int postTestScore,
            int postTestQuestions, e_Question.Difficulty difficulty)
    {

        String TAG = "calculateGrowth()";

        double weight = 0.0;

        switch (difficulty) {
            case EASY: weight = 0.8; break;
            case MEDIUM: weight = 1.0; break;
            case HARD: weight = 1.2; break;
        }

        double adjusted_post_test_score = postTestScore * weight;

        double NLG = (adjusted_post_test_score - preTestScore)
                    / (postTestQuestions - preTestScore);

        if (NLG > 0.7) {
            // Strong Learning
            Log.i(TAG, "Strong Learning");
            NLG_Feedback = "Strong Learning";
        }
        else if ((NLG >= 0.3 && NLG <= 0.7)) {
            // Moderate Improvement
            Log.i(TAG, "Moderate Improvement");
            NLG_Feedback = "Moderate Improvement";
        }
        else if (NLG < 0.3) {
                // Minimal Improvement
            Log.i(TAG, "Minimal Improvement");
            NLG_Feedback = "Minimal Improvement";
        }

        NLG_Score = (NLG * 100);

    }

    public static String changeCategory(String currentCategory, double previousBKTScore, double currentBKTScore) {
        int currentIndex = categories.indexOf(currentCategory);
        int totalCategories = categories.size();

        if (currentIndex == -1) {
            Log.e(TAG, "Invalid category: " + currentCategory);
            return currentCategory;
        }

        double scoreDifference = currentBKTScore - previousBKTScore; // Calculate the difference
        Log.i(TAG, "Score Difference: " + scoreDifference);

        if (scoreDifference > 0) { // Improvement logic
            double[] upgradeRanges = calculateDynamicRanges(60, 100, totalCategories - currentIndex - 1, false);
            for (int i = 0; i < upgradeRanges.length; i++) {
                if (currentBKTScore >= upgradeRanges[i] && (i == upgradeRanges.length - 1 || currentBKTScore < upgradeRanges[i + 1])) {
                    String newCategory = categories.get(currentIndex + i + 1);
                    Log.d(TAG, "Upgrading to: " + newCategory);
                    changeCategoryFromDatabase(newCategory);
                    return newCategory;
                }
            }
        } else if (scoreDifference < 0) { // Decline logic
            double[] downgradeRanges = calculateDynamicRanges(0, 60, currentIndex, true);
            for (int i = 0; i < downgradeRanges.length; i++) {
                if (currentBKTScore < downgradeRanges[i]) {
                    String newCategory = categories.get(i);
                    Log.d(TAG, "Downgrading to: " + newCategory);
                    changeCategoryFromDatabase(newCategory);
                    return newCategory;
                }
            }
        }

        // Retention logic
        Log.d(TAG, "No category change. Remaining at: " + currentCategory);
        return currentCategory;
    }


//    public static String changeCategory(String currentCategory, double bktScore) {
//        int currentIndex = categories.indexOf(currentCategory);
//        int totalCategories = categories.size();
//
//        if (currentIndex == -1) {
//            Log.e(TAG, "Invalid category: " + currentCategory);
//            return currentCategory;
//        }
//
//        bktScore *= 100; // Convert to percentage
//        Log.i(TAG, "Adjusted BKT Score: " + bktScore);
//
//        double[] downgradeRanges = new double[currentIndex];
//        double[] upgradeRanges = new double[totalCategories - currentIndex - 1];
//
//        if (currentCategory.equals("Novice")) {
//            upgradeRanges = calculateDynamicRanges(60, 100, totalCategories - currentIndex - 1, false);
//        } else if (currentCategory.equals("Expert")) {
//            downgradeRanges = calculateDynamicRanges(0, 60, currentIndex, true);
//        } else {
//            downgradeRanges = calculateDynamicRanges(0, 60, currentIndex, true);
//            upgradeRanges = calculateDynamicRanges(60, 100, totalCategories - currentIndex - 1, false);
//        }
//
//        Log.d(TAG, "Downgrade ranges: " + Arrays.toString(downgradeRanges));
//        Log.d(TAG, "Upgrade ranges: " + Arrays.toString(upgradeRanges));
//
//        // Retention logic
//        if (currentCategory.equals("Novice") && bktScore < 60) {
//            Log.d(TAG, "Retaining: Novice");
//            return currentCategory;
//        }
//        if (currentCategory.equals("Expert") && bktScore >= 60) {
//            Log.d(TAG, "Retaining: Expert");
//            return currentCategory;
//        }
//
//        for (int i = 0; i < downgradeRanges.length; i++) {
//            if (bktScore < downgradeRanges[i]) {
//                String newCategory = categories.get(i);
//                Log.d(TAG, "Downgrading to: " + newCategory);
//                changeCategoryFromDatabase(newCategory);
//                return newCategory;
//            }
//        }
//
//        for (int i = 0; i < upgradeRanges.length; i++) {
//            if (bktScore >= upgradeRanges[i] && (i == upgradeRanges.length - 1 || bktScore < upgradeRanges[i + 1])) {
//                String newCategory = categories.get(currentIndex + i + 1);
//                Log.d(TAG, "Upgrading to: " + newCategory);
//                changeCategoryFromDatabase(newCategory);
//                return newCategory;
//            }
//        }
//
//        Log.d(TAG, "No category change. Remaining at: " + currentCategory);
//        return currentCategory;
//    }


//    public static String changeCategory(String currentCategory, double bktScore) {
//        int currentIndex = categories.indexOf(currentCategory);
//        int totalCategories = categories.size();
//
//        if (currentIndex == -1) {
//            Log.e(TAG, "Invalid category: " + currentCategory);
//            return currentCategory;
//        }
//
//        bktScore *= 100; // Convert to percentage
//        Log.i(TAG, "Adjusted BKT Score: " + bktScore);
//
//        double[] downgradeRanges = new double[currentIndex];
//        double[] upgradeRanges = new double[totalCategories - currentIndex - 1];
//
//        if (currentCategory.equals("Novice")) {
//            upgradeRanges = calculateDynamicRanges(60, 100, totalCategories - currentIndex - 1, false);
//        } else if (currentCategory.equals("Expert")) {
//            downgradeRanges = calculateDynamicRanges(0, 60, currentIndex, true);
//        } else {
//            downgradeRanges = calculateDynamicRanges(0, 60, currentIndex, true);
//            upgradeRanges = calculateDynamicRanges(60, 100, totalCategories - currentIndex - 1, false);
//        }
//
//        Log.d(TAG, "Downgrade ranges: " + Arrays.toString(downgradeRanges));
//        Log.d(TAG, "Upgrade ranges: " + Arrays.toString(upgradeRanges));
//
//        // Retention logic
//        if (currentCategory.equals("Novice") && bktScore < 60) return currentCategory;
//        if (currentCategory.equals("Expert") && bktScore >= 60) return currentCategory;
//
//        for (int i = 0; i < downgradeRanges.length; i++) {
//            if (bktScore < downgradeRanges[i]) {
//                String newCategory = categories.get(i);
//                changeCategoryFromDatabase(newCategory);
//                return newCategory;
//            }
//        }
//
//        for (int i = 0; i < upgradeRanges.length; i++) {
//            if (bktScore >= upgradeRanges[i] && (i == upgradeRanges.length - 1 || bktScore < upgradeRanges[i + 1])) {
//                String newCategory = categories.get(currentIndex + i + 1);
//                changeCategoryFromDatabase(newCategory);
//                return newCategory;
//            }
//        }
//
//        return currentCategory;
//    }

    private static double[] calculateDynamicRanges(double start, double end, int levels, boolean isDowngrade) {
        if (levels <= 0) return new double[0];

        double[] ranges = new double[levels];
        double totalRange = end - start;

        double[] weights = new double[levels];
        double weightSum = 0;

        for (int i = 0; i < levels; i++) {
            weights[i] = isDowngrade ? levels - i : i + 1;
            weightSum += weights[i];
        }

        double cumulative = start;
        for (int i = 0; i < levels; i++) {
            ranges[i] = cumulative + (weights[i] / weightSum) * totalRange;
            cumulative = ranges[i];
        }

        return ranges;
    }

    public static void showChangeCategoryDialog(String currentCategory, String newCategory, android.content.Context context) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);

        android.view.LayoutInflater inflater = android.view.LayoutInflater.from(context);
        android.view.View dialogView = inflater.inflate(R.layout.b_main_5_change_category, null);
        builder.setView(dialogView);

        android.widget.ImageView exit = dialogView.findViewById(R.id.close_button);
        android.widget.TextView proficiency = dialogView.findViewById(R.id.proficiency_text);
        android.widget.TextView retake = dialogView.findViewById(R.id.retake_text);
        android.widget.Button okay = dialogView.findViewById(R.id.okay_button);

        android.app.AlertDialog dialog = builder.create();

        int currentIndex = categories.indexOf(currentCategory);
        int newIndex = categories.indexOf(newCategory);

        if (newIndex > currentIndex) {
            proficiency.setText("Congratulations! You've moved up from " + currentCategory + " to the " + newCategory + " level.");
        } else if (newIndex < currentIndex) {
            proficiency.setText("Your proficiency level has adjusted to " + newCategory + ".");
        } else {
            proficiency.setText("Your proficiency level remains at: " + newCategory + ".");
        }

        retake.setText("Would you like to retake the Automata course under the [" + newCategory + "] classification?");

        exit.setOnClickListener(v -> dialog.dismiss());
        okay.setOnClickListener(v -> {
            changeCategoryFromDatabase(newCategory);
            dialog.dismiss();
        });

        dialog.show();
    }

    static String getWeakPointFeedback
            (double bktScore
//            , String currentCategory
    ) {
        if (bktScore < 60) {
            return "Error: Scores below 60 are not considered weak points but failures.";
        }

        if (bktScore > 70)
            return "Strong Learning";
        else if (bktScore >= 30 && bktScore < 70)
            return "Moderate Learning";
        else
            return "Minimal Learning";
    }



}
