package com.example.autotutoria20;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class f_3_lesson_post_test_generateHint {

    static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    static String generateHint(String originalString, String category) {

        // Define the number of underscores based on the category
        if (originalString == null) {
            Log.e("Error", "originalString is null");
            // Handle the error (e.g., return, throw an exception, or show a message)
            return originalString; // or handle accordingly
        }
        int answerLength = originalString.length(); // Now it's safe to call length()
        if (category == null) {
            Log.e("Error", "category is null");
            return originalString;
        }
        Log.e("Generate Hint", "Category: " + category);

        int replaceChar;
        String TAG = "Generate Hint";
        switch (category) {
            case "Beginner":
                replaceChar = (int) (answerLength * 0.3); // Cast to int
                Log.e(TAG, "Beginner | replaceChar["+replaceChar+"] = answerLength["+answerLength+"] * 0.8");
                break;
            case "Novice":
                replaceChar = (int) (answerLength * 0.4); // Cast to int
                Log.e(TAG, "Novice | replaceChar["+replaceChar+"] = answerLength["+answerLength+"] * 0.6");
                break;
            case "Intermediate":
                replaceChar = (int) (answerLength * 0.5); // Cast to int
                Log.e(TAG, "Intermediate | replaceChar["+replaceChar+"] = answerLength["+answerLength+"] * 0.4");
                break;
            case "Advanced":
                replaceChar = (int) (answerLength * 0.6); // Cast to int
                Log.e(TAG, "Advanced | replaceChar["+replaceChar+"] = answerLength["+answerLength+"] * 0.3");
                break;
            case "Expert":
                replaceChar = (int) (answerLength * 0.7); // Cast to int
                Log.e(TAG, "Expert | replaceChar["+replaceChar+"] = answerLength["+answerLength+"] * 0.2");
                break;
            default:
                replaceChar = 1; // Default to 1 if category is not recognized
        }
        Log.e("Generate Hint", "Characters to replace part 1: " + replaceChar);

        // Ensure replaceChar is at least 1
        replaceChar = Math.max(replaceChar, 1);
        Log.e("Generate Hint", "Characters to replace part 2: " + replaceChar);

        String[] parts = originalString.split(" ");

        // Create a new ArrayList
        List<String> stringList = new ArrayList<>();
        List<Integer> intList = new ArrayList<>();
        for (int i = 0; i < parts.length; i++) {
            stringList.add(parts[i]);
            Log.e("Seperate Answer Parts[" + i + "]:", stringList.get(i));
            // Add the length of each part to intList
            intList.add(parts[i].length());
        }

        int replacePerWord = Math.max(1, replaceChar / parts.length);

        Log.e("Replace per word:", replacePerWord + "");

        // To store the final modified string
        StringBuilder sampleStringName = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < stringList.size(); i++) {
            StringBuilder modifiedWord = new StringBuilder(stringList.get(i));
            int countReplacements = Math.min(replacePerWord, modifiedWord.length());
            Log.e("Count replacements for word[" + i + "]:", countReplacements + "");

            for (int j = 0; j < countReplacements; j++) {
                int indexToReplace;
                do {
                    indexToReplace = random.nextInt(modifiedWord.length());
                } while (modifiedWord.charAt(indexToReplace) == '_'); // Avoid replacing already replaced characters
                modifiedWord.setCharAt(indexToReplace, '_'); // Replace character with underscore
            }
            sampleStringName.append(modifiedWord);
            if (i < stringList.size() - 1) {
                sampleStringName.append(" "); // Add space between words
            }
        }

        Log.e("Final modified string:", sampleStringName.toString());

        // this method still returns a not perfect String..
        return sampleStringName.toString();

    }

    static void takeToken(boolean isAdding, int token) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            DocumentReference userDoc = db.collection("users").document(userId);

            userDoc.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    Long currentTokens = documentSnapshot.getLong("Token");
                    if (currentTokens == null) currentTokens = 0L;

                    if (isAdding)
                        userDoc.update("Token", currentTokens + token);
                    else
                        userDoc.update("Token", currentTokens - token);
                }
            });
        }
    }

}
