package com.example.autotutoria20;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class f_1_lesson_text extends Fragment {
    int contentIndex = 1;
    int contentCount = 0;
    Map <String, String> contentMap = new HashMap<>();
    Map <String, List<Integer>> bulletMap = new HashMap<>();
    Map<String, Map<String, Object>> pages = new HashMap<>();
    List<Integer> pageSteps = new ArrayList<>();
    List<Integer> contentCount2 = new ArrayList<>();
    private static final String ARG_KEY = "key";
    private static final String ARG_PAGE_NUMBER = "pageNumber"; // New argument
    private static final String ARG_MODULE = "currentModule"; // New argument
    private static final String ARG_LESSON = "currentLesson"; // New argument

    private int contentKey;
    private String key;
    private String currentModule; // Variable to store currentModule
    private String currentLesson; // Variable to store currentLesson

    private TextView titleTextView;
    private LinearLayout
            contentLayout_01, contentLayout_02, contentLayout_03, contentLayout_04, contentLayout_05,
            contentLayout_06, contentLayout_07, contentLayout_08, contentLayout_09, contentLayout_10;
    LinearLayout[] contentLayouts = {
            contentLayout_01, contentLayout_02, contentLayout_03, contentLayout_04, contentLayout_05,
            contentLayout_06, contentLayout_07, contentLayout_08, contentLayout_09, contentLayout_10
    };
    ImageView
            contentImageView_01, contentImageView_02, contentImageView_03, contentImageView_04, contentImageView_05,
            contentImageView_06, contentImageView_07, contentImageView_08, contentImageView_09, contentImageView_10;
    TextView
            contentTextView_01, contentTextView_02, contentTextView_03, contentTextView_04, contentTextView_05,
            contentTextView_06, contentTextView_07, contentTextView_08, contentTextView_09, contentTextView_10;
    TextView[] contentTextViews = {
            contentTextView_01, contentTextView_02, contentTextView_03, contentTextView_04, contentTextView_05,
            contentTextView_06, contentTextView_07, contentTextView_08, contentTextView_09, contentTextView_10
    };

    ImageView[] contentImageViews = {
            contentImageView_01, contentImageView_02, contentImageView_03, contentImageView_04, contentImageView_05,
            contentImageView_06, contentImageView_07, contentImageView_08, contentImageView_09, contentImageView_10
    };
    private Handler handler = new Handler();
    private int numberOfTexts = 11;
    private LinearLayout nextButton;
    private Boolean isTextLessonDone = false;
    private Button tapToContinueButton;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private int delayFinish = 0;
    private int currentStep = 1; // Track which content is currently shown
    private int pageNumber = 1; // will be incremented after page is done :)
    private TextLessonCompleteListener TextLessonCompleteListener;
    private OnNextButtonClickListener callback;

    public static f_1_lesson_text newInstance(String key, int pageNumber) {

        // // Log.e("IM HERE", "IM HERE, nasa newInstance nako...");
        f_1_lesson_text fragment = new f_1_lesson_text();
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);
        args.putInt(ARG_PAGE_NUMBER, pageNumber); // Pass the page number
        fragment.setArguments(args);

        return fragment;

    }

    public static f_1_lesson_text newInstance(String key) {
        f_1_lesson_text fragment = new f_1_lesson_text();
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();

        String page = "Page " + pageNumber;
        loadTextContentForKey2(key, page);

        // no need na dito, kasi may setPageContent na sa loob ng loadTextContentForKey2
//        setPageContent();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_1_lesson_text, container, false);

        // Reset??
        currentStep = 1;

        // Retrieve the key from the arguments
        if (getArguments() != null) {
            key = getArguments().getString(ARG_KEY);
            pageNumber = getArguments().getInt(ARG_PAGE_NUMBER, 1); // Retrieve page number
        } else {
             // Log.e("f_text_lesson", "Arguments bundle is null. Key and page number are not set.");
        }

        if (key == null) {
             // Log.e("f_text_lesson", "Key is null in onCreateView");
        }

        // Initialize views and other logic
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize TextView objects
        titleTextView = view.findViewById(R.id.text_lesson_title);

        // Initialize Layouts
        contentLayout_01 = view.findViewById(R.id.content_01);
        contentLayout_02 = view.findViewById(R.id.content_02);
        contentLayout_03 = view.findViewById(R.id.content_03);
        contentLayout_04 = view.findViewById(R.id.content_04);
        contentLayout_05 = view.findViewById(R.id.content_05);
        contentLayout_06 = view.findViewById(R.id.content_06);
        contentLayout_07 = view.findViewById(R.id.content_07);
        contentLayout_08 = view.findViewById(R.id.content_08);
        contentLayout_09 = view.findViewById(R.id.content_09);
        contentLayout_10 = view.findViewById(R.id.content_10);

        // Now populate the arrays after initializing the layouts
        contentLayouts = new LinearLayout[]{
                contentLayout_01, contentLayout_02, contentLayout_03, contentLayout_04, contentLayout_05,
                contentLayout_06, contentLayout_07, contentLayout_08, contentLayout_09, contentLayout_10
        };

        // Initialize TextViews
        contentTextView_01 = view.findViewById(R.id.text_lesson_content_01);
        contentTextView_02 = view.findViewById(R.id.text_lesson_content_02);
        contentTextView_03 = view.findViewById(R.id.text_lesson_content_03);
        contentTextView_04 = view.findViewById(R.id.text_lesson_content_04);
        contentTextView_05 = view.findViewById(R.id.text_lesson_content_05);
        contentTextView_06 = view.findViewById(R.id.text_lesson_content_06);
        contentTextView_07 = view.findViewById(R.id.text_lesson_content_07);
        contentTextView_08 = view.findViewById(R.id.text_lesson_content_08);
        contentTextView_09 = view.findViewById(R.id.text_lesson_content_09);
        contentTextView_10 = view.findViewById(R.id.text_lesson_content_10);

        // Initialize ImageViews
        contentImageView_01 = view.findViewById(R.id.image_lesson_content_01);
        contentImageView_02 = view.findViewById(R.id.image_lesson_content_02);
        contentImageView_03 = view.findViewById(R.id.image_lesson_content_03);
        contentImageView_04 = view.findViewById(R.id.image_lesson_content_04);
        contentImageView_05 = view.findViewById(R.id.image_lesson_content_05);
        contentImageView_06 = view.findViewById(R.id.image_lesson_content_06);
        contentImageView_07 = view.findViewById(R.id.image_lesson_content_07);
        contentImageView_08 = view.findViewById(R.id.image_lesson_content_08);
        contentImageView_09 = view.findViewById(R.id.image_lesson_content_09);
        contentImageView_10 = view.findViewById(R.id.image_lesson_content_10);

        // Now populate the arrays after initializing the views
        contentTextViews = new TextView[]{
                contentTextView_01, contentTextView_02, contentTextView_03, contentTextView_04, contentTextView_05,
                contentTextView_06, contentTextView_07, contentTextView_08, contentTextView_09, contentTextView_10
        };

        contentImageViews = new ImageView[]{
                contentImageView_01, contentImageView_02, contentImageView_03, contentImageView_04, contentImageView_05,
                contentImageView_06, contentImageView_07, contentImageView_08, contentImageView_09, contentImageView_10
        };

        // Initialize buttons
        nextButton = view.findViewById(R.id.next_button);
        tapToContinueButton = view.findViewById(R.id.tap_to_continue);

        // Ensure TextViews and Button are not null
        if (titleTextView == null || nextButton == null || tapToContinueButton == null)
            return;

        // Proceed with your logic
        if (getArguments() != null) {
            key = getArguments().getString(ARG_KEY);
            pageNumber = getArguments().getInt(ARG_PAGE_NUMBER, -1); // Retrieve page number
//            setTotalStepsForKey(key);
//            // Log.i("setPageContent(" + pageNumber + ")", "setPageContent(" + pageNumber + "); | onViewCreated();");
//            setPageContent(pageNumber);
        }

        // Set OnClickListener for the nextButton
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log.i("nextButton", "nextButton clicked!");
                nextButton.setEnabled(false);
                tapToContinueButton.setEnabled(false);
                tapToContinueButton.setVisibility(View.GONE);

                showNextStep();
                showTextView(currentStep);

                handleNextButtonClick();

            }
        });


//        nextButton.performClick();

        // "ALSO" Set OnClickListener for the tapToContinueButton

//        tap_to_continue
        tapToContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log.i("tapToContinueButton", "tapToContinueButton clicked!");

                tapToContinueButton.setEnabled(false);
                tapToContinueButton.setVisibility(View.GONE);

                showNextStep();
                showTextView(currentStep);

                handleNextButtonClick();

            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Ensure that the host activity implements the callback interface
        try {
            callback = (OnNextButtonClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnNextButtonClickListener");
        }
        if (context instanceof f_1_lesson_text.TextLessonCompleteListener) {
            TextLessonCompleteListener = (f_1_lesson_text.TextLessonCompleteListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement TextLessonCompleteListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Avoid memory leaks
        callback = null;
        TextLessonCompleteListener = null;
    }

    // Interface to notify the container activity when pre-test is complete
    public interface TextLessonCompleteListener {
        void onTextLessonComplete(boolean isCorrect, int delayFinish);
    }

    // Define an interface to communicate with the container activity
    public interface OnNextButtonClickListener {
        void onNextButtonClicked();

    }

    private void handleNextButtonClick() {

        // Log.i("setPageContent", "setPageContent | handleNextButtonClick()");
        setPageContent();

        showTextView(currentStep);
        showNextStep();

        // Execute additional code after a delay of 600 milliseconds
        new Handler().postDelayed(() -> {

            // disable if want to show contentTextViews  one by one
            d_Lesson_container.nextButton.setEnabled(true);
            d_Lesson_container.nextButton.setVisibility(View.VISIBLE);

        }, 1000);

    }

    void showTextView(int step) {
        String TAG = "showTextView";

        contentTextView_01.setVisibility(View.VISIBLE);
        contentTextView_02.setVisibility(View.VISIBLE);
        contentTextView_03.setVisibility(View.VISIBLE);
        contentTextView_04.setVisibility(View.VISIBLE);
        contentTextView_05.setVisibility(View.VISIBLE);
        contentTextView_06.setVisibility(View.VISIBLE);
        contentTextView_07.setVisibility(View.VISIBLE);
        contentTextView_08.setVisibility(View.VISIBLE);
        contentTextView_09.setVisibility(View.VISIBLE);
        contentTextView_10.setVisibility(View.VISIBLE);

        nextButton.setVisibility(View.VISIBLE);
        nextButton.setEnabled(true);

    }


//    private void setBullet(int pageNumber, int step, ImageView contentImageView, TextView contentTextView, LinearLayout linearLayout) {
//        String TAG = "setBullet()";
//
//        // Convert dp to pixels
//        float scale = contentImageView.getContext().getResources().getDisplayMetrics().density;
//        int sizeInDp = (int) (20 * scale + 0.5f); // 25dp to pixels
//
//        int layoutMargin = 16;
//
////        // Get the bullet types dynamically based on the key and pageNumber
////        int[][] moduleArray = f_1_lesson_text_bullets.getIndentation(key, pageNumber);
////
////        if (moduleArray.length == 0 || pageNumber > moduleArray.length) {
////            // // Log.e(TAG, "Invalid page number. No bullets available for page " + pageNumber);
////            return; // Invalid page number, return early
////        }
////
////        if (step >= moduleArray[pageNumber - 1].length) {
////            // // Log.e(TAG, "Invalid step number for page " + pageNumber + ". Step: " + step);
////            return; // Invalid step number, return early
////        }
////
////        int bulletType = moduleArray[pageNumber - 1][step];  // Get bullet type for the step
//
//
//
//        // Set bullet image and padding based on the bullet type
//        if (bulletType == 1) {
//            linearLayout.setPadding((24 + layoutMargin), layoutMargin, layoutMargin, layoutMargin);
//            contentImageView.getLayoutParams().width = sizeInDp;
//            contentImageView.getLayoutParams().height = sizeInDp;
//            contentImageView.setImageResource(R.drawable.bullet_1);  // Set bullet_1 image
//        } else if (bulletType == 2) {
//            linearLayout.setPadding((75 + layoutMargin), layoutMargin, layoutMargin, layoutMargin);
//            contentImageView.getLayoutParams().width = sizeInDp;
//            contentImageView.getLayoutParams().height = sizeInDp;
//            contentImageView.setImageResource(R.drawable.bullet_2);  // Set bullet_2 image
//        } else {
//            contentImageView.setImageDrawable(null);
//            contentTextView.setPadding(0, 0, 0, 0);  // No indentation
//        }
//
//        contentImageView.requestLayout();  // Request layout update to apply the new size
//    }

    private void showNextStep() {

        nextButton.setEnabled(false);
        tapToContinueButton.setVisibility(View.GONE);
        tapToContinueButton.setEnabled(false);

        nextButton.setVisibility(View.VISIBLE);
        nextButton.setEnabled(true);

    }
//
//    static int calculateDelayBasedOnLength(int length) {
//
//        /**
//         * This method calculates the delay based on the length of the string and the reading speed of the target audience.
//         *
//         * Reference Table for Multiplier:
//         * +--------------------------------+-----------------+------------------------------+---------------------------+
//         * | Educational Level              |   Average WPM   |  Characters Per Second (CPS) |  Multiplier (sec/char)    |
//         * +--------------------------------+-----------------+------------------------------+---------------------------+
//         * | 1. Elementary Student          |  100 - 150 WPM  |  8.33 - 12.50 CPS            |  0.0800 - 0.1200 sec/char |
//         * | 2. High School Student         |  200 - 300 WPM  |  16.67 - 25.00 CPS           |  0.0400 - 0.0600 sec/char |
//         * | 3. Senior High School Student  |  250 - 350 WPM  |  20.83 - 29.17 CPS           |  0.0343 - 0.0480 sec/char |
//         * | 4. College Student             |  250 - 350 WPM  |  20.83 - 29.17 CPS           |  0.0343 - 0.0480 sec/char |
//         * | 5. Graduate (Bachelor's)       |  300 - 400 WPM  |  25.00 - 33.33 CPS           |  0.0300 - 0.0400 sec/char |
//         * | 6. Master's Student            |  350 - 450 WPM  |  29.17 - 37.50 CPS           |  0.0267 - 0.0343 sec/char |
//         * | 7. Doctorate (PhD)             |  400 - 500 WPM  |  33.33 - 41.67 CPS           |  0.0240 - 0.0300 sec/char |
//         * +--------------------------------+-----------------+------------------------------+---------------------------+
//         */
//
//
//        // 0 -
//        // n -
//
//        // use 8 on publish
//        // use 10 for testing
//        int level = 3; // Default to High School Student
//
//        // Adjust the multiplier based on the educational level
//        double multiplier;
//        switch (level) {
//            case 0: // User-Friendly
//                multiplier = 0.15; // No Average Data, just for user-friendliness
//                break;
//            case 1: // Elementary Student
//                multiplier = 0.10; // Average of 0.0800 - 0.1200
//                break;
//            case 2: // High School Student
//                multiplier = 0.05; // Average of 0.0400 - 0.0600
//                break;
//            case 3: // Senior High School Student
//                multiplier = 0.0412; // Average of 0.0343 - 0.0480
//                break;
//            case 4: // College Student
//                multiplier = 0.0378; // Average of 0.0343 - 0.0480
//                break;
//            case 5: // Graduate (Bachelor's)
//                multiplier = 0.035; // Average of 0.0300 - 0.0400
//                break;
//            case 6: // Master's Student
//                multiplier = 0.0305; // Average of 0.0267 - 0.0343
//                break;
//            case 7: // Doctorate (PhD)
//                multiplier = 0.027; // Average of 0.0240 - 0.0300
//                break;
//            case 8: // Test User #1
//                multiplier = 0.02508; // Average of 0.0240 - 0.0300
//                break;
//            case 9: // Test User #2
//                multiplier = 0.02338; // Ewan kung anong average na to, kung di pa kayo makuntento dito ewan ko nalang
//                break;
//            case 10: // Test User #3
//                multiplier = 0.02067; // please makuntento na kayo :)
//                break;
//            default:
//                multiplier = 0.05; // Default value if no valid level is provided
//                break;
//        }
//
//        return (int) (length * multiplier);
//    }

    public void loadTextContentForKey2(String key, String page) {
        String TAG = "loadTextContentForKey2";

        String module = "Module " + key.charAt(10);
        String lesson = "Lesson " + key.charAt(1);

        Log.i(TAG, "PASTEURIZED | Module: " + module);
        Log.i(TAG, "PASTEURIZED | Lesson: " + lesson);

        clearAllContentTextViews();

        Log.i(TAG, "PASTEURIZED | after clearAllContent();");

        // Reference to Firestore document
        DocumentReference userRef = db
                .collection("text lesson")
                .document(module);

        /*
        Collection -> text lesson /
        Document -> Module n /
         */


        Log.i(TAG, "PASTEURIZED | before addOnCompleteListener();");

        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {

                Log.i(TAG, "PASTEURIZED | task.isSuccessful();");
                Map<String, Object> lessonMap = (Map<String, Object>) task.getResult().get(lesson);

                /*
                Collection -> text lesson /
                Document -> Module n /
                Field -> Lesson n /
                 */

                if (lessonMap != null) {

                    Log.i(TAG, "PASTEURIZED | lessonMap != null");
                    clearAllContentTextViews();

                    // Iterate : Lesson n
                    for (Map.Entry<String, Object> lessonEntry : lessonMap.entrySet()) {

                        Log.i(TAG, "PASTEURIZED | inside for each loop");

                        String pageKey2 = lessonEntry.getKey(); // Key for the current lesson/page
                        Map<String, Object> pageMap;

                        // Safely cast the value to Map<String, Object>
                        try {
                            pageMap = (Map<String, Object>) lessonEntry.getValue();
                            Log.e(TAG, "Valid data structure for page: " + pageKey2);
                        } catch (ClassCastException e) {
                            Log.e(TAG, "Invalid data structure for page: " + pageKey2, e);
                            continue; // Skip invalid entries
                        }

                        Log.i(TAG, "PASTEURIZED | pageMap: " + pageMap);
                        Log.i(TAG, "PASTEURIZED | Page: " + page);

                        if (pageMap != null
//                                && pageMap.containsKey(pageKey2)
                        ) {

                            Log.i(TAG, "PASTEURIZED | inside pageMap != null!");
                            Map<String, Object> contentWithBulletsMap = new HashMap<>(); // To store content and bullets together

                            // Iterate through the page's data
                            for (Map.Entry<String, Object> entry : pageMap.entrySet()) {
                                String keyName = entry.getKey();
                                Object value = entry.getValue();

                                Log.i(TAG, "Processing Page Data: Key = " + keyName + ", Value = " + value);

                                // Extract title
                                if (keyName.contains("Title") && value instanceof String) {
                                    String title = (String) value;
                                    Log.i(TAG, "Extracted Title: " + title);
                                    contentWithBulletsMap.put(keyName, title);
                                }

                                // Extract content
                                if (keyName.contains("Content ") && value instanceof String) {
                                    String content = (String) value;
                                    Log.i(TAG, "Extracted Content: " + content);
                                    contentWithBulletsMap.put(keyName, content);
                                }

                                // Extract bullets
                                if (keyName.contains("Bullet") && value instanceof List<?>) {
                                    List<?> rawBulletList = (List<?>) value;

                                    // Safely convert raw bullet list to List<Integer>
                                    List<Integer> bulletList = new ArrayList<>();
                                    for (Object item : rawBulletList) {
                                        try {
                                            int bulletValue;
                                            if (item instanceof Number) {
                                                bulletValue = ((Number) item).intValue();
                                            } else if (item instanceof String) {
                                                bulletValue = Integer.parseInt((String) item);
                                            } else {
                                                Log.w(TAG, "Skipping non-numeric bullet item: " + item);
                                                continue; // Skip invalid entries
                                            }
                                            bulletList.add(bulletValue);
                                        } catch (Exception e) {
                                            Log.e(TAG, "Error processing bullet item: " + item, e);
                                        }
                                    }

                                    Log.i(TAG, "Processed Bullet List: " + bulletList);
                                    contentWithBulletsMap.put("Bullet", bulletList); // Add processed bullets to the map
                                }
                            }

                            // Update the pageMap with the processed content
                            pageMap.put(pageKey2, contentWithBulletsMap);

                            // Update the outer structure (optional, based on requirements)
                            pages.put(pageKey2, contentWithBulletsMap);
                        }
                    }

                    setPageContent();
                } else {
                    // Log.d(TAG, "No data found for Lesson " + lesson);
                }
            } else {
                // Log.e(TAG, "Error retrieving document: ", task.getException());
            }
        });
    }


    // Original Code
//    public void loadTextContentForKey2(String key) {
//        String TAG = "loadTextContentForKey2";
//
////        // // Log.d(TAG, "Page Number: " + pageNumber);
//        // // Log.d(TAG, "Key: " + key);
//
//        String module = "Module " + key.charAt(10);
//        String lesson = "Lesson " + key.charAt(1);
////        String pageKey = "Page " + pageNumber;
//
//        // // Log.i(TAG, "Module: " + module);
//        // // Log.i(TAG, "Lesson: " + lesson);
////        // // Log.i(TAG, "Page: " + pageKey);
//
//        // Reference to Firestore document
//        DocumentReference userRef = db.collection("text lesson").document(module);
//
//        userRef.get().addOnCompleteListener(task -> {
//            if (task.isSuccessful() && task.getResult() != null) {
//                Map<String, Object> lessonMap = (Map<String, Object>) task.getResult().get(lesson);
//
//                if (lessonMap != null) {
//                    // Iterate over each entry in the lessonMap
//                    for (Map.Entry<String, Object> lessonEntry : lessonMap.entrySet()) {
//                        String pageKey2 = lessonEntry.getKey(); // Get the current page key
//                        Map<String, Object> pageMap = (Map<String, Object>) lessonEntry.getValue(); // Get the corresponding page map
//
//                        // Assuming you want to use the page number based on the current iteration
//                        int pageNumberKey = Integer.parseInt(pageKey2.replaceAll("[^0-9]", "")); // Extracting number from key
//                        String page = "Page " + pageNumberKey;
//                        // // Log.i(TAG, "Page: " + page);
//
//                        contentMap = new HashMap<>(); // Create a new inner map for this page
//                        bulletMap = new HashMap<>();
//
//                        if (pageMap != null) {
//                            // Retrieve the Title
//                            String title = (String) pageMap.getOrDefault("Title", "");
//                            contentMap.put("Title", title); // Store Title in the inner map
//
//                            int[] bullets;
//                            Object bulletData = pageMap.get("Bullet"); // Retrieve the value for the "Bullet" key
//
//                            if (bulletData == null)
//                                // // Log.i("loadTextContentForKey2", "Bullet is NOT in the Database, default tayo pre");
//
//                            if (bulletData instanceof List) {
//                                // // Log.i("loadTextContentForKey2", "Bullet is a List");
//                                // Safely cast to a List of Numbers
//                                List<Number> bulletList = (List<Number>) bulletData;
//                                // Convert the List<Number> to an int[]
//                                bullets = bulletList.stream().mapToInt(Number::intValue).toArray();
//                            } else {
//                                // // Log.i("loadTextContentForKey2", "Bullet is NOT a List");
//                                // Default value if the key is missing or not a List
//                                bullets = new int[]{0};
//                            }
//
//                            // // Log.i("loadTextContentForKey2", "Bullets: " + Arrays.toString(bullets));
//
//                            // Clear all content TextViews initially
////                            clearAllContentTextViews();
//
//                            // Iterate over all keys in the pageMap
//                            contentIndex = 1; // To match Content X numbering
//
//                            int iteration = 1;
//                            int stopLoop;
//                            boolean addIteration = false;
//
//                            while (iteration != 10) {
//                                stopLoop = 0; // Reset stopLoop for each iteration of the outer loop
//                                boolean foundMatch = false; // Flag to track if we found a match in this iteration
//
//                                for (Map.Entry<String, Object> entry : pageMap.entrySet()) {
//                                    String keyName = entry.getKey();
//
//                                    // // Log.e("loadTextContentForKey2", "before if (keyName.startsWith(\"Content \"" + iteration + "))");
//                                    // // Log.i(TAG, "keyName: " + keyName);
//                                    // // Log.i(TAG, "Expected Name: " + "Content " + iteration);
//
//                                    // Check for matching key
//                                    if (keyName.startsWith("Content " + iteration)) {
//                                        // // Log.i(TAG, "YES MATCH!");
//
//                                        String content = (String) entry.getValue();
//                                        contentKey++;
//
//                                        // Store each content in the inner map with a key of "Content X"
//                                        contentMap.put("Content " + contentIndex, content);
//                                        contentIndex++;
//
//                                         // Log.i("loadTextContentForKey2", "Mitsushi | Module: " + module);
//                                         // Log.i("loadTextContentForKey2", "Mitsushi | Lesson: " + lesson);
//                                         // Log.i("loadTextContentForKey2", "Mitsushi | Page: " + page);
//                                         // Log.i("loadTextContentForKey2", "Mitsushi | Content Number: " + keyName);
//                                        // // Log.i("loadTextContentForKey2", "Mitsushi | Content : " + content);
//                                        // // Log.i("loadTextForContentKey2", "Mitsushi | Bullet: " + Arrays.toString(bullets));
//
//                                        // // Log.e("loadTextContentForKey2", "Mitsushi | ise-save ko na Content sa index: " + contentIndex);
//                                        // // Log.d("loadTextContentForKey2", "Mitsushi | Content " + contentIndex + ": " + content);
//
//                                        // Check if bulletData is a list and safely cast
//                                        if (bulletData instanceof List<?>) {
//                                            List<?> rawBulletList = (List<?>) bulletData;
//
//                                            // Convert each element to Integer
//                                            List<Integer> bulletList = new ArrayList<>();
//                                            for (Object item : rawBulletList) {
//                                                if (item instanceof Number) { // Ensure it's a number
//                                                    bulletList.add(((Number) item).intValue());
//                                                }
//                                            }
//
//                                            // Put the List<Integer> into the bulletMap
//                                            bulletMap.put("Bullet", bulletList);
//                                        } else {
//                                            // Default empty list if no valid data is found
//                                            bulletMap.put("Bullet", Collections.emptyList());
//                                        }
//
//                                        // Log.i(TAG, "ASCORBIC ACID | Bullet: " + bulletMap);
//
//
//                                        // // Log.d(TAG, "foundMatch! | addIteration!");
//                                        // Mark that we found a match
//                                        foundMatch = true;
//                                        addIteration = true;
//                                    }
//
//                                    stopLoop++;
//                                    // // Log.i(TAG, "stopLoop: " + stopLoop);
//                                    // // Log.i(TAG, "pageMap.size(): " + pageMap.size());
//
//                                    // Break if we've processed enough entries
//                                    if (stopLoop == (pageMap.size()+1)) {
//                                        break;
//                                    }
//                                }
//
//                                // // Log.i(TAG, "Iteration: " + iteration);
//
//                                // Only increment iteration if we found a match
//                                if (foundMatch) {
//                                    // // Log.e(TAG, "foundMath! | increment iteration: " + (iteration+1));
//                                    iteration++;
//                                } else {
//                                    // If no matches were found for this iteration, break to avoid infinite loop
//                                    break;
//                                }
//
//                            }
//
//                            if (addIteration && iteration >= 1) {
//                                // // Log.i(TAG, "addIteration | contentCount2: " + (iteration-2));
//                                contentCount2.add(iteration-2);
//                                addIteration = !addIteration;
//                            }
//
//                            // Sort using TreeMap
////                            Map<String, String> sortedMap = new TreeMap<>(contentMap);
//
//                            setTextViewContent();
//
//                            // // Log.i(TAG, "pageSteps.add(" + contentIndex + ")");
//                            pageSteps.add(contentIndex);
//
//                            // After processing all contents for this page, add the inner map to the pages map
//                            pages.put(page, contentMap); // Use 'page' as the key in pages map
//                            // Log.i(TAG, "DUET | pages: " + pages);
//
//                            // // Log.i(TAG, "Pages <Map> : " + pages);
//
//                        } else {
//                            // // Log.d(TAG, "No data found for Page " + page);
//                        }
//                    }
//                    // // Log.i(TAG, "Hash Map: " + pages);
//                } else {
//                    // // Log.d(TAG, "No data found for Lesson " + key.charAt(10));
//                }
//            } else {
//                // // Log.e(TAG, "Error retrieving document: ", task.getException());
//            }
//        });
//    }

    // Helper method to clear all content TextViews
    private void clearAllContentTextViews() {

        titleTextView.setText("");

        contentTextView_01.setText("");
        contentTextView_02.setText("");
        contentTextView_03.setText("");
        contentTextView_04.setText("");
        contentTextView_05.setText("");
        contentTextView_06.setText("");
        contentTextView_07.setText("");
        contentTextView_08.setText("");
        contentTextView_09.setText("");
        contentTextView_10.setText("");
    }

    public void setPageContent() {
        String TAG = "setPageContent(" + pageNumber + ");";
        // Log.i(TAG, "setPageContent(" + pageNumber + "); | setPageContent();");

        // Array of TextViews and ImageViews for content and bullets
        TextView[] contentTextViews = {
                contentTextView_01, contentTextView_02,
                contentTextView_03, contentTextView_04,
                contentTextView_05, contentTextView_06,
                contentTextView_07, contentTextView_08,
                contentTextView_09, contentTextView_10
        };
        ImageView[] contentImageViews = {
                contentImageView_01, contentImageView_02,
                contentImageView_03, contentImageView_04,
                contentImageView_05, contentImageView_06,
                contentImageView_07, contentImageView_08,
                contentImageView_09, contentImageView_10
        };
        LinearLayout[] contentLayouts = {
                contentLayout_01, contentLayout_02,
                contentLayout_03, contentLayout_04,
                contentLayout_05, contentLayout_06,
                contentLayout_07, contentLayout_08,
                contentLayout_09, contentLayout_10
        };

        // Clear all TextViews
        for (TextView textView : contentTextViews)
            textView.setText(null);

        String newPageNumber = "Page " + pageNumber;

        // Retrieve content map for the page
        Map<String, Object> contentMap = pages.get(newPageNumber);

        if (contentMap != null) {
            // Set the title`
            String title = (String) contentMap.getOrDefault("Title", null);

            if (title != null) {
                titleTextView.setText(title);
            }
            else {
                titleTextView.setVisibility(View.GONE);
                contentTextView_01.setPadding(0, 70, 0, 0);
                contentImageView_01.setPadding(0,70, 0, 0);
            }

            // Get bullets list
            List<Integer> bullets = (List<Integer>) contentMap.getOrDefault("Bullet", new ArrayList<>());

            for (int i = 0; i < contentTextViews.length; i++) {
                String contentKey = "Content " + (i + 1);
                String content = (String) contentMap.getOrDefault(contentKey, "");

                // Set content text
                contentTextViews[i].setText(content);

                if (i < bullets.size()) {
                    int bulletType = bullets.get(i);
                    setBullet(bulletType, contentImageViews[i], contentTextViews[i], contentLayouts[i]);
                } else {
                    // No bullet, reset ImageView and layout
                    contentImageViews[i].setImageDrawable(null);
//                    contentLayouts[i].setPadding(0, 0, 0, 0);
                }
            }
        } else {
            // Log.d(TAG, "No data found for Page " + newPageNumber);
        }
    }

    // New setBullet method
    private void setBullet(int bulletType, ImageView contentImageView, TextView contentTextView, LinearLayout linearLayout) {
        String TAG = "setBullet()";

        // Convert dp to pixels
        float scale = contentImageView.getContext().getResources().getDisplayMetrics().density;
        int sizeInDp = (int) (20 * scale + 0.5f); // 20dp to pixels
        int layoutMargin = 16; // Margin in dp

        // Set bullet image and layout properties based on bullet type
        if (bulletType == 1) {
            linearLayout.setPadding((24 + layoutMargin), layoutMargin, layoutMargin, layoutMargin);
            contentImageView.getLayoutParams().width = sizeInDp;
            contentImageView.getLayoutParams().height = sizeInDp;
            contentImageView.setImageResource(R.drawable.bullet_1);
            contentImageView.setVisibility(View.VISIBLE);
        } else if (bulletType == 2) {
            linearLayout.setPadding((75 + layoutMargin), layoutMargin, layoutMargin, layoutMargin);
            contentImageView.getLayoutParams().width = sizeInDp;
            contentImageView.getLayoutParams().height = sizeInDp;
            contentImageView.setImageResource(R.drawable.bullet_2);
            contentImageView.setVisibility(View.VISIBLE);
        } else {
            contentImageView.setImageDrawable(null);
            contentImageView.setVisibility(View.INVISIBLE);
            linearLayout.setPadding(0, 0, 0, 0); // Reset padding
        }

        // Request layout update
        contentImageView.requestLayout();
    }

    private void setTextViewContent() {
        String TAG = "Running Devices";

        String outerKey = "Page " + pageNumber; // Assuming currentStep is defined
        // // Log.i(TAG, "outerKey: " + outerKey);

        // Check if the outerKey exists in the pages map
        if (pages.containsKey(outerKey)) {
            // Retrieve the inner map associated with outerKey
            Map<String, Object> innerMap = pages.get(outerKey);

            // Check if innerMap is not null
            if (innerMap != null) {
                // Retrieve the title
                String title = (String) innerMap.get("Title");
                titleTextView.setText(title);
                // // Log.i(TAG, "Title: " + title);

                List<String> contents = new ArrayList<>(10);

                for (String key : innerMap.keySet()) {
                    // // Log.i(TAG, "Doraemon | Key: " + key);
                    if (key.startsWith("Content ")) {
                        contents.add(key);
                        // // Log.d(TAG, "Found Content Key: " + key);
                        // // Log.i(TAG, "Content: " + innerMap.get(key));
                        int index = Integer.parseInt(key.substring(8));
//                        String content = innerMap.get("Content " + index);
//                        contents.add(index, content);

//                        contents.add(content);
//
//                        // // Log.i(TAG, "Index " + index + " | Content " + content);
                    }
                }
                // // Log.i(TAG, "Total contents found: " + contents.size());
                contentCount = contents.size();

                Collections.sort(contents);

                for (int i=0;i< contents.size();i++) {
                    // // Log.i(TAG, "Content[" + i +"]: " + contents.get(i));
                }

                for (int i=contents.size(); i< 10; i++) {
                    setContextView(i, "");
                }

                // Loop through and retrieve contents based on actual count
                for (int i = 0; i < contents.size(); i++) { // Start from 0
                    String contentKey = contents.get(i); // Get content key
                    // // Log.i(TAG, "contentKey: " + contents.get(i));
                    String content = (String) innerMap.get(contentKey); // Retrieve content

                    if (content != null) {
                        // // Log.i(TAG, contentKey + ": " + content);
                        // // Log.d(TAG, contentKey + " | Setting contentTextView_" + (i + 1) + ": " + content);

                        setContextView(i, content);

                    } else {
//                        // Log.w(TAG, "No content found for key: " + contentKey);
                    }
                }
            } else {
//                // // Log.e(TAG, "Inner map for key '" + outerKey + "' is null.");
            }
        } else {
//            // // Log.e(TAG, "Outer key '" + outerKey + "' not found in pages map.");
        }
    }

    private void setContextView(int i, String content) {
        // Dynamically set the text for each Content TextViews
        switch (i) {
            case 0:
                contentTextView_01.setText(content);
                break;
            case 1:
                contentTextView_02.setText(content);
                break;
            case 2:
                contentTextView_03.setText(content);
                break;
            case 3:
                contentTextView_04.setText(content);
                break;
            case 4:
                contentTextView_05.setText(content);
                break;
            case 5:
                contentTextView_06.setText(content);
                break;
            case 6:
                contentTextView_07.setText(content);
                break;
            case 7:
                contentTextView_08.setText(content);
                break;
            case 8:
                contentTextView_09.setText(content);
                break;
            case 9:
                contentTextView_10.setText(content);
                break;
            default:
                // Log.w("setContextViewt", "No more TextViews available for Content: " + (i + 1));
        }
    }

//    public void showContentTextViews(Context context) {
//
//
//        for (int i = 1; i <= contentIndex; i++) {
//            final int index = i;
//
//            handler.postDelayed(() -> {
//                // Dynamically resolve TextView ID
//                int resId = context.getResources().getIdentifier(
//                        "contentTextView_" + String.format("%02d", index),
//                        "id",
//                        context.getPackageName()
//                );
//
//                contentTextViews[Integer.parseInt(String.format("%02d", index))].setVisibility(View.VISIBLE);
//
////                // Use rootView to find the TextView
////                TextView textView = getView().findViewById(resId);
////                if (textView != null) {
////                    textView.setVisibility(View.VISIBLE);
////                } else {
////                    // // Log.e("showContentTextViews", "TextView contentTextView_" + String.format("%02d", index) + " not found");
////                }
//            }, 600L * i); // Delay by 600ms per item
//        }
//    }


//    public void loadTextContentForKey(String key, int pageNumber) {
//        String TAG = "LOAD TEXT LESSON TEXTS";
//        // // Log.d(TAG, "Page Number: " + pageNumber);
//        // // Log.e(TAG, "key: " + key);
//
//        // Construct resource names
//        String title = "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_title";
//        String context1 = "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_1";
//        String context2 = "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_2";
//        String context3 = "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_3";
//        String context4 = "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_4";
//        String context5 = "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_5";
//        String context6 = "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_6";
//        String context7 = "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_7";
//        String context8 = "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_8";
//        String context9 = "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_9";
//        String context10 = "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_10";
//
//        // Retrieve the resource IDs
//        int titleResId = getResources().getIdentifier(title, "string", getContext().getPackageName());
//        int text1ResId = getResources().getIdentifier(context1, "string", getContext().getPackageName());
//        int text2ResId = getResources().getIdentifier(context2, "string", getContext().getPackageName());
//        int text3ResId = getResources().getIdentifier(context3, "string", getContext().getPackageName());
//        int text4ResId = getResources().getIdentifier(context4, "string", getContext().getPackageName());
//        int text5ResId = getResources().getIdentifier(context5, "string", getContext().getPackageName());
//        int text6ResId = getResources().getIdentifier(context6, "string", getContext().getPackageName());
//        int text7ResId = getResources().getIdentifier(context7, "string", getContext().getPackageName());
//        int text8ResId = getResources().getIdentifier(context8, "string", getContext().getPackageName());
//        int text9ResId = getResources().getIdentifier(context9, "string", getContext().getPackageName());
//        int text10ResId = getResources().getIdentifier(context10, "string", getContext().getPackageName());
//
//        // Log errors for missing resources
//        String TEG = "auto resource ID generator";
//
//
//
//        if (titleResId == 0) {
//            // // Log.e(TEG, "Title is wrong: " + title);
//            numberOfTexts--;
//        }
//        if (text1ResId == 0) {
//            // // Log.e(TEG, "Text 1 is wrong: " + context1);
//            numberOfTexts--;
//        }
//        if (text2ResId == 0) {
//            // // Log.e(TEG, "Text 2 is wrong: " + context2);
//            numberOfTexts--;
//        }
//        if (text3ResId == 0) {
//            // // Log.e(TEG, "Text 3 is wrong: " + context3);
//            numberOfTexts--;
//        }
//        if (text4ResId == 0) {
//            // // Log.e(TEG, "Text 4 is wrong: " + context4);
//            numberOfTexts--;
//        }
//        if (text5ResId == 0) {
//            // // Log.e(TEG, "Text 5 is wrong: " + context5);
//            numberOfTexts--;
//        }
//        if (text6ResId == 0) {
//            // // Log.e(TEG, "Text 6 is wrong: " + context6);
//            numberOfTexts--;
//        }
//        if (text7ResId == 0) {
//            // // Log.e(TEG, "Text 7 is wrong: " + context7);
//            numberOfTexts--;
//        }
//        if (text8ResId == 0) {
//            // // Log.e(TEG, "Text 8 is wrong: " + context8);
//            numberOfTexts--;
//        }
//        if (text9ResId == 0) {
//            // // Log.e(TEG, "Text 9 is wrong: " + context9);
//            numberOfTexts--;
//        }
//        if (text10ResId == 0) {
//            // // Log.e(TEG, "Text 10 is wrong: " + context10);
//            numberOfTexts--;
//        }
//
//
//        // Clear previous content
//        titleTextView.setText("");
//        contentTextView_01.setText("");
//        contentTextView_02.setText("");
//        contentTextView_03.setText("");
//        contentTextView_04.setText("");
//        contentTextView_05.setText("");
//        contentTextView_06.setText("");
//        contentTextView_07.setText("");
//        contentTextView_08.setText("");
//        contentTextView_09.setText("");
//        contentTextView_10.setText("");
//
//        // Set the text content with try-catch for safety
//        try {
//            if (titleResId != 0) {
//                titleTextView.setText(titleResId);
//            } else {
//                titleTextView.setText(""); // Fallback in case of missing title
//            }
//
//            // Set each content TextView with validation
//            setContentWithFallback(contentTextView_01, text1ResId);
//            setContentWithFallback(contentTextView_02, text2ResId);
//            setContentWithFallback(contentTextView_03, text3ResId);
//            setContentWithFallback(contentTextView_04, text4ResId);
//            setContentWithFallback(contentTextView_05, text5ResId);
//            setContentWithFallback(contentTextView_06, text6ResId);
//            setContentWithFallback(contentTextView_07, text7ResId);
//            setContentWithFallback(contentTextView_08, text8ResId);
//            setContentWithFallback(contentTextView_09, text9ResId);
//            setContentWithFallback(contentTextView_10, text10ResId);
//
//        } catch (Exception e) {
//            // // Log.e(TAG, "Error setting text content: ", e); // Log any exceptions
//        }
//    }

    // Helper method to set TextViews with fallback
//    private void setContentWithFallback(TextView textView, int resId) {
//        if (resId != 0) {
//            textView.setText(resId);
//        } else {
//            textView.setText(""); // Fallback in case of missing content
//        }
//    }


}
