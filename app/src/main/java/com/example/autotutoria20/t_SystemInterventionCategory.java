package com.example.autotutoria20;

import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;

public class t_SystemInterventionCategory {

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

        userRef.update("User Category", newCategory)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "User category updated successfully to " + newCategory))
                .addOnFailureListener(e -> Log.e(TAG, "Error updating user category", e));
    }

    public static String changeCategory(String currentCategory, double bktScore) {
        int currentIndex = categories.indexOf(currentCategory);
        int totalCategories = categories.size();

        if (currentIndex == -1) {
            Log.e(TAG, "Invalid category: " + currentCategory);
            return currentCategory;
        }

        bktScore *= 100; // Convert to percentage
        Log.i(TAG, "Adjusted BKT Score: " + bktScore);

        double[] downgradeRanges = new double[currentIndex];
        double[] upgradeRanges = new double[totalCategories - currentIndex - 1];

        if (currentCategory.equals("Novice")) {
            upgradeRanges = calculateDynamicRanges(60, 100, totalCategories - currentIndex - 1, false);
        } else if (currentCategory.equals("Expert")) {
            downgradeRanges = calculateDynamicRanges(0, 60, currentIndex, true);
        } else {
            downgradeRanges = calculateDynamicRanges(0, 60, currentIndex, true);
            upgradeRanges = calculateDynamicRanges(60, 100, totalCategories - currentIndex - 1, false);
        }

        // Retention logic
        if (currentCategory.equals("Novice") && bktScore < 60) return currentCategory;
        if (currentCategory.equals("Expert") && bktScore >= 60) return currentCategory;

        for (int i = 0; i < downgradeRanges.length; i++) {
            if (bktScore < downgradeRanges[i]) {
                String newCategory = categories.get(i);
                changeCategoryFromDatabase(newCategory);
                return newCategory;
            }
        }

        for (int i = 0; i < upgradeRanges.length; i++) {
            if (bktScore >= upgradeRanges[i] && (i == upgradeRanges.length - 1 || bktScore < upgradeRanges[i + 1])) {
                String newCategory = categories.get(currentIndex + i + 1);
                changeCategoryFromDatabase(newCategory);
                return newCategory;
            }
        }

        return currentCategory;
    }

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

    static String getWeakPointFeedback(double bktScore, String currentCategory) {
        if (bktScore < 60) {
            return "Error: Scores below 60 are not considered weak points but failures.";
        }

        switch (currentCategory) {
            case "Novice":
                if (bktScore <= 74) return "This score indicates a weak point for Novice.";
                else if (bktScore <= 89) return "This score is acceptable for Novice.";
                else return "This score is excellent for Novice.";
            case "Beginner":
                if (bktScore <= 69) return "This score indicates a weak point for Beginner.";
                else if (bktScore <= 84) return "This score is acceptable for Beginner.";
                else return "This score is excellent for Beginner.";
            case "Intermediate":
                if (bktScore <= 65) return "This score indicates a weak point for Intermediate.";
                else if (bktScore <= 84) return "This score is acceptable for Intermediate.";
                else return "This score is excellent for Intermediate.";
            case "Advanced":
                if (bktScore <= 64) return "This score indicates a weak point for Advanced.";
                else if (bktScore <= 83) return "This score is acceptable for Advanced.";
                else return "This score is excellent for Advanced.";
            case "Expert":
                if (bktScore <= 63) return "This score indicates a weak point for Expert.";
                else if (bktScore <= 82) return "This score is acceptable for Expert.";
                else return "This score is excellent for Expert.";
            default:
                return "Invalid category.";
        }
    }



}
