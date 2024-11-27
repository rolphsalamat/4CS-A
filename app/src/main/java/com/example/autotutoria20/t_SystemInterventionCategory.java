package com.example.autotutoria20;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;

public class t_SystemInterventionCategory {

    static List<String> categories = Arrays.asList("Novice", "Beginner", "Intermediate", "Advance", "Expert");
    static String TAG = "Change Category";


    static void changeCategoryFromDatabase(String newCategory) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference userRef = db.collection("users").document(userId);

        // Update user category
        userRef.update("User Category", newCategory)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "User category updated successfully"))
                .addOnFailureListener(e -> Log.e("Firestore", "Error updating user category", e));

    }

    public static String changeCategory(String currentCategory, double bktScore) {

        int currentIndex = categories.indexOf(currentCategory);
        int totalCategories = categories.size();

        String newCategory = null;

        // Calculate ranges dynamically
        double[] downgradeRanges = calculateDynamicRanges(0, 60, currentIndex, true);
        double[] upperRanges = calculateDynamicRanges(60, 100, totalCategories - currentIndex - 1, false);

        double retentionMin = upperRanges[0]; // Retention starts at lower end of upper ranges
        double retentionMax = upperRanges[1]; // Upper end of retention
        double upgradeMin = upperRanges[1]; // Upgrade starts where retention ends
        double upgradeMax = 100; // Max score

        // Check for retention range
        if (bktScore >= retentionMin && bktScore < retentionMax) {
            newCategory = currentCategory;
            return currentCategory;
        }

        // Check downgrade ranges
        if (bktScore < retentionMin) {
            for (int i = 0; i < currentIndex; i++) {
                if (bktScore < downgradeRanges[i]) {
                    newCategory = categories.get(i);
                    changeCategoryFromDatabase(categories.get(i));
                    return categories.get(i);
                }
            }
        }

        // Check upgrade range
        if (bktScore >= upgradeMin && currentIndex < totalCategories - 1) {
            newCategory = categories.get(currentIndex + 1);
            return categories.get(currentIndex + 1);
        }

        Log.i(TAG, "Old Category: " + currentCategory);
        Log.i(TAG, "New Category: " + newCategory);

        return currentCategory;
    }

    /**
     * Dynamically calculates ranges.
     * @param start The starting value (e.g., 0 for downgrades, 60 for upper ranges).
     * @param end The ending value (e.g., 60 for downgrades, 100 for upper ranges).
     * @param levels The number of levels to divide into (e.g., levels down or levels up).
     * @param isDowngrade Whether calculating downgrade ranges (affects weight distribution).
     * @return An array of range boundaries.
     */
    private static double[] calculateDynamicRanges(double start, double end, int levels, boolean isDowngrade) {
        if (levels <= 0) return new double[0]; // No levels, return empty

        double[] ranges = new double[levels];
        double totalRange = end - start;

        // Use weights for unequal distribution
        double[] weights = new double[levels];
        double weightSum = 0;

        for (int i = 0; i < levels; i++) {
            weights[i] = isDowngrade ? levels - i : i + 1; // Higher weight for closer levels
            weightSum += weights[i];
        }

        double cumulative = start;
        for (int i = 0; i < levels; i++) {
            ranges[i] = cumulative + (weights[i] / weightSum) * totalRange;
            cumulative = ranges[i];
        }

        return ranges;
    }


}
