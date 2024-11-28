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
                .addOnSuccessListener(aVoid -> Log.d(TAG, "User category updated successfully to " + newCategory))
                .addOnFailureListener(e -> Log.e(TAG, "Error updating user category", e));
    }

    public static String changeCategory(String currentCategory, double bktScore) {

        int currentIndex = categories.indexOf(currentCategory);
        int totalCategories = categories.size();

        Log.i(TAG, "Current Category: " + currentCategory);
        Log.i(TAG, "BKT Score: " + bktScore);

        if (currentIndex == -1) {
            Log.e(TAG, "Current category is not valid: " + currentCategory);
            return currentCategory;
        }

        String newCategory = null;

        // Calculate ranges dynamically
        double[] downgradeRanges = calculateDynamicRanges(0, 60, currentIndex, true);
        double[] upgradeRanges = calculateDynamicRanges(60, 100, totalCategories - currentIndex - 1, false);

        Log.i(TAG, "Downgrade Ranges: " + Arrays.toString(downgradeRanges));
        Log.i(TAG, "Upgrade Ranges: " + Arrays.toString(upgradeRanges));

        double retentionMin = (currentIndex < totalCategories - 1) ? upgradeRanges[0] : 60;
        double retentionMax = (currentIndex < totalCategories - 1) ? upgradeRanges[1] : 100;

        Log.i(TAG, "Retention Range: " + retentionMin + " -> " + retentionMax);

        // Check for retention range
        if (bktScore >= retentionMin && bktScore < retentionMax) {
            Log.i(TAG, "BKT Score falls in retention range. No category change.");
            return currentCategory;
        }

        // Check downgrade ranges
        if (bktScore < retentionMin) {
            for (int i = 0; i < currentIndex; i++) {
                if (bktScore < downgradeRanges[i]) {
                    newCategory = categories.get(i);
                    Log.i(TAG, "BKT Score falls in downgrade range: " + downgradeRanges[i]);
                    Log.i(TAG, "New Category: " + newCategory);
                    changeCategoryFromDatabase(newCategory);
                    return newCategory;
                }
            }
        }

        // Check upgrade ranges
        if (bktScore >= retentionMax && currentIndex < totalCategories - 1) {
            for (int i = currentIndex + 1; i < totalCategories; i++) {
                if (bktScore < upgradeRanges[i - currentIndex - 1]) {
                    newCategory = categories.get(i);
                    Log.i(TAG, "BKT Score falls in upgrade range: " + upgradeRanges[i - currentIndex - 1]);
                    Log.i(TAG, "New Category: " + newCategory);
                    changeCategoryFromDatabase(newCategory);
                    return newCategory;
                }
            }
        }

        Log.i(TAG, "Old Category: " + currentCategory);
        Log.i(TAG, "New Category (unchanged): " + newCategory);

        return currentCategory;
    }

    /**
     * Dynamically calculates ranges.
     * @param start The starting value (e.g., 0 for downgrades, 60 for upgrades).
     * @param end The ending value (e.g., 60 for downgrades, 100 for upgrades).
     * @param levels The number of levels to divide into (e.g., levels down or levels up).
     * @param isDowngrade Whether calculating downgrade ranges (affects weight distribution).
     * @return An array of range boundaries.
     */

    private static double[] calculateDynamicRanges(double start, double end, int levels, boolean isDowngrade) {
        if (levels <= 0) {
            Log.w(TAG, "No levels to calculate ranges for. Returning empty array.");
            return new double[0]; // No levels, return empty
        }

        double[] ranges = new double[levels];
        double totalRange = end - start;

        // Use weights for unequal distribution
        double[] weights = new double[levels];
        double weightSum = 0;

        for (int i = 0; i < levels; i++) {
            weights[i] = isDowngrade ? levels - i : i + 1; // Higher weight for closer levels
            weightSum += weights[i];
        }

        Log.i(TAG, "Weights: " + Arrays.toString(weights));

        double cumulative = start;
        for (int i = 0; i < levels; i++) {
            ranges[i] = cumulative + (weights[i] / weightSum) * totalRange;
            cumulative = ranges[i];
        }

        Log.i(TAG, "Calculated Ranges: " + Arrays.toString(ranges));
        return ranges;
    }
}
