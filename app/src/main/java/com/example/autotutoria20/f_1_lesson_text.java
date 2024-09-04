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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class f_1_lesson_text extends Fragment {

    private static final String ARG_KEY = "key";
    private static final String ARG_PAGE_NUMBER = "pageNumber"; // New argument
    private static final String ARG_MODULE = "currentModule"; // New argument
    private static final String ARG_LESSON = "currentLesson"; // New argument

    private String key;
    private String currentModule; // Variable to store currentModule
    private String currentLesson; // Variable to store currentLesson

    private TextView titleTextView;
    private TextView
            contentTextView_1,
            contentTextView_2,
            contentTextView_3;
    private LinearLayout nextButton;
    private Boolean isTextLessonDone = false;
    private Button tapToContinueButton;
    private int currentStep = 0; // Track which content is currently shown
    private int pageNumber = 1; // will be incremented after page is done :)

    // Initialize arrays for step counts
    // nagtataka ako dito eh, bakit hindi nalang gamitin yung LessonSequence Class
    // para mai-store dito yung length nung sequence per individual na module??
    // tapos pano maii-store kung ilang page ba sa isang module lesson??
//    private int[] module1Steps = {3, 2, 2, 2};
//    private int[] module2Steps = {2};
//    private int[] module3Steps = {2, 2, 2};
//    private int[] module4Steps = {2, 2, 2};
//    private int[] module5Steps = {2, 2, 2};
//    private int[] module6Steps = {2, 2, 2};
//    private int[] module7Steps = {3};
//    private int[] module8Steps = {2, 2, 2};

    // Gaano katagal bago mag show yung buttons and others??
    private TextLessonCompleteListener TextLessonCompleteListener;
    private OnNextButtonClickListener callback;
    private int totalSteps = 2; // Default total steps

    public static f_1_lesson_text newInstance(String key, int pageNumber) {

        Log.e("IM HERE", "IM HERE, nasa newInstance nako...");
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
        Log.d("f_text_lesson", "onResume called");
        Log.e("f_text_lesson", "loadTextContentForKey(" + key + ");");
        loadTextContentForKey(key, pageNumber);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_1_lesson_text, container, false);

        // Reset??
        currentStep = 0;

        // Retrieve the key from the arguments
        if (getArguments() != null) {
            key = getArguments().getString(ARG_KEY);
            pageNumber = getArguments().getInt(ARG_PAGE_NUMBER, 1); // Retrieve page number
        } else {
            Log.e("f_text_lesson", "Arguments bundle is null. Key and page number are not set.");
        }

        if (key == null) {
            Log.e("f_text_lesson", "Key is null in onCreateView");
        }

        // Initialize views and other logic
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize TextView objects
        titleTextView = view.findViewById(R.id.text_lesson_title);
        contentTextView_1 = view.findViewById(R.id.text_lesson_content_1);
        contentTextView_2 = view.findViewById(R.id.text_lesson_content_2);
        contentTextView_3 = view.findViewById(R.id.text_lesson_content_3);
        nextButton = view.findViewById(R.id.next_button);
        tapToContinueButton = view.findViewById(R.id.tap_to_continue);

//        // Disable nextButton temporarily and set tapToContinueButton to GONE
//        nextButton.setEnabled(false);
//        tapToContinueButton.setVisibility(View.GONE);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Enable nextButton and make tapToContinueButton visible after n seconds
//                nextButton.setEnabled(true);
//                tapToContinueButton.setVisibility(View.VISIBLE);
//            }
//        }, delayInSeconds * 750);  // 5000 milliseconds = 5 seconds

        nextButton.setEnabled(true);
        tapToContinueButton.setVisibility(View.VISIBLE);

        // Ensure TextViews and Button are not null
        if (titleTextView == null || contentTextView_1 == null || contentTextView_2 == null || contentTextView_3 == null || nextButton == null) {
            Log.e("f_text_lesson", "One or more views are null. Check your layout file.");
            return;  // Early return to prevent further crashes
        }

        contentTextView_1.setVisibility(View.GONE);
        contentTextView_2.setVisibility(View.GONE);
        contentTextView_3.setVisibility(View.GONE);

        // Proceed with your logic
        if (getArguments() != null) {
            key = getArguments().getString(ARG_KEY);
            pageNumber = getArguments().getInt(ARG_PAGE_NUMBER, 1); // Retrieve page number
            setTotalStepsForKey(key);
        }

        // Set OnClickListener for the nextButton
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Disable the button immediately
                nextButton.setEnabled(false);

                // Disable and Hide "Tap to Continue"
                tapToContinueButton.setEnabled(false);
                tapToContinueButton.setVisibility(View.GONE);

                handleNextButtonClick();

//                // Use a Handler to re-enable the button after 5 seconds and make it visible again
//                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        nextButton.setEnabled(true); // Re-enable the button
//                        // Make the buttons visible again
//                        nextButton.setVisibility(View.VISIBLE);
//                        if (!isTextLessonDone)
//                            tapToContinueButton.setVisibility(View.VISIBLE);
//                    }
//                }, delayInSeconds * 1000); // 5000 milliseconds = 5 seconds
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
        void onTextLessonComplete(boolean isCorrect);
    }

    // Define an interface to communicate with the container activity
    public interface OnNextButtonClickListener {
        void onNextButtonClicked();

    }

    private void setTotalStepsForKey(String key) {

        totalSteps = 0;  // Reset total steps

        // Generate resource names dynamically
        String baseName = "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber;

        Log.e("setTotalStepsForKey", "baseName: " + baseName);

        // Check if title and content resources exist and have values, increment totalSteps accordingly
        if (resourceHasValue(baseName + "_title")) totalSteps++;
        if (resourceHasValue(baseName + "_content_1")) totalSteps++;
        if (resourceHasValue(baseName + "_content_2")) totalSteps++;
        if (resourceHasValue(baseName + "_content_3")) totalSteps++;

        Log.e("setTotalStepsForKey", "totalSteps: " + totalSteps);
    }

    private boolean resourceHasValue(String resourceName) {
        int resId = getResources().getIdentifier(resourceName, "string", getContext().getPackageName());
        if (resId != 0) {  // Check if the resource ID is valid
            String value = getString(resId);
            Log.d("resourceHasValue", "Resource: " + resourceName + " Value: " + value); // Log the value
            return value != null && !value.trim().isEmpty();  // Check if the resource has a non-empty value
        }
        Log.d("resourceHasValue", "Resource: " + resourceName + " not found or empty.");
        return false;  // Return false if the resource does not exist or is empty
    }


    private void handleNextButtonClick() {
        Log.d("handleNextButtonClick()", "currentStep(" + currentStep + ") < totalSteps(" + totalSteps + ")");

        // after (n-2)th step, the Text Lesson should display Next Button
        if (currentStep == (totalSteps-2)) {
            if (TextLessonCompleteListener != null) {
                TextLessonCompleteListener.onTextLessonComplete(true);
            }
        }

        // (n-1) step, dapat di na mag show yung "Tap to Continue"
        // dapat yung Next Button nalang from d_Lesson_container ang mag-show
        if (currentStep == (totalSteps-1)) {
            isTextLessonDone = true;

            nextButton.setVisibility(View.GONE);
            nextButton.setEnabled(false);

            tapToContinueButton.setVisibility(View.GONE);
            tapToContinueButton.setEnabled(false);
        }

        if (currentStep < (totalSteps-1)) {
            showNextStep(currentStep);

            currentStep++;
        }
        else {

            // Notify the parent activity that the button was clicked
            if (callback != null) {
                callback.onNextButtonClicked();
//                // Reset currentStep for the next page
//                currentStep = 0;
            }

        }
    }

    private void showNextStep(int step) {
        String TAG = "showNextStep";
        int actualStep = 0;

//        nextButton.setVisibility(View.GONE);
        nextButton.setEnabled(false);

        tapToContinueButton.setVisibility(View.GONE);
        tapToContinueButton.setEnabled(false);

//        if ((step+2) == totalSteps) {
//            tapToContinueButton.setVisibility(View.GONE);
//            tapToContinueButton.setEnabled(false);
//
//            nextButton.setVisibility(View.VISIBLE);
//            nextButton.setEnabled(true);
//
////            currentStep++;
//        }

        // Title is shown by default, so we'll start checking content blocks
        // Step 1: Check if content_1 exists and has a value
        String content1Key = "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_1";
        if (resourceHasValue(content1Key)) {
            actualStep++;
            if (actualStep == step + 1) {
                Log.e(TAG, "Step: " + actualStep + " - Showing content_1");

                // Get the content string to determine delay
                int resId = getResources().getIdentifier(content1Key, "string", getContext().getPackageName());
                String content = getString(resId);
                int delayInSeconds = calculateDelayBasedOnLength(content.length());

                contentTextView_1.setVisibility(View.VISIBLE);
                // Delay showing the nextButton based on the content length

                Log.e(TAG, "show context 1 : if (" + currentStep + " <= (" + (totalSteps-2 + ");"));

                if (currentStep < (totalSteps-2)) {
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            nextButton.setEnabled(true); // Enable the button
                            tapToContinueButton.setVisibility(View.VISIBLE); // Show tapToContinueButton
                        }
                    }, delayInSeconds * 1000);
                }

                return;
            }
        } else {
            contentTextView_1.setVisibility(View.GONE); // Hide if no value
        }

        // Step 2: Check if content_2 exists and has a value
        String content2Key = "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_2";
        if (resourceHasValue(content2Key)) {
            actualStep++;
            if (actualStep == step + 1) {
                Log.e(TAG, "Step: " + actualStep + " - Showing content_2");

                // Get the content string to determine delay
                int resId = getResources().getIdentifier(content2Key, "string", getContext().getPackageName());
                String content = getString(resId);
                int delayInSeconds = calculateDelayBasedOnLength(content.length());

                contentTextView_2.setVisibility(View.VISIBLE);
                // Delay showing the nextButton based on the content length

                Log.e(TAG, "show context 2 : if (" + currentStep + " <= (" + (totalSteps-2 + ");"));

                if (currentStep < (totalSteps-2)) {
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            nextButton.setEnabled(true); // Enable the button
                            tapToContinueButton.setVisibility(View.VISIBLE); // Show tapToContinueButton
                        }
                    }, delayInSeconds * 1000);
                }

                return;
            }
        } else {
            contentTextView_2.setVisibility(View.GONE); // Hide if no value
        }

        // Step 3: Check if content_3 exists and has a value
        String content3Key = "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_3";
        if (resourceHasValue(content3Key)) {
            actualStep++;
            if (actualStep == step + 1) {
                Log.e(TAG, "Step: " + actualStep + " - Showing content_3");

                // Get the content string to determine delay
                int resId = getResources().getIdentifier(content3Key, "string", getContext().getPackageName());
                String content = getString(resId);
                int delayInSeconds = calculateDelayBasedOnLength(content.length());

                contentTextView_3.setVisibility(View.VISIBLE);
                // Delay showing the nextButton based on the content length

                Log.e(TAG, "show context 3 : if (" + currentStep + " <= (" + (totalSteps-2 + ");"));

                if (currentStep < (totalSteps-2)) {
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            nextButton.setEnabled(true); // Enable the button
                            tapToContinueButton.setVisibility(View.VISIBLE); // Show tapToContinueButton
                        }
                    }, delayInSeconds * 1000);
                }

                return;
            }
        } else {
            contentTextView_3.setVisibility(View.GONE); // Hide if no value
        }

        // If no more steps are available to show
        Log.e(TAG, "No more steps to show, STEP: " + (step + 1));
    }

    private int calculateDelayBasedOnLength(int length) {

        /**
         * This method calculates the delay based on the length of the string and the reading speed of the target audience.
         *
         * Reference Table for Multiplier:
         * +--------------------------------+-----------------+------------------------------+---------------------------+
         * | Educational Level              |   Average WPM   |  Characters Per Second (CPS) |  Multiplier (sec/char)    |
         * +--------------------------------+-----------------+------------------------------+---------------------------+
         * | 1. Elementary Student          |  100 - 150 WPM  |  8.33 - 12.50 CPS            |  0.0800 - 0.1200 sec/char |
         * | 2. High School Student         |  200 - 300 WPM  |  16.67 - 25.00 CPS           |  0.0400 - 0.0600 sec/char |
         * | 3. Senior High School Student  |  250 - 350 WPM  |  20.83 - 29.17 CPS           |  0.0343 - 0.0480 sec/char |
         * | 4. College Student             |  250 - 350 WPM  |  20.83 - 29.17 CPS           |  0.0343 - 0.0480 sec/char |
         * | 5. Graduate (Bachelor's)       |  300 - 400 WPM  |  25.00 - 33.33 CPS           |  0.0300 - 0.0400 sec/char |
         * | 6. Master's Student            |  350 - 450 WPM  |  29.17 - 37.50 CPS           |  0.0267 - 0.0343 sec/char |
         * | 7. Doctorate (PhD)             |  400 - 500 WPM  |  33.33 - 41.67 CPS           |  0.0240 - 0.0300 sec/char |
         * +--------------------------------+-----------------+------------------------------+---------------------------+
         */

        int level = 2; // Default to High School Student

        // Adjust the multiplier based on the educational level
        double multiplier;
        switch (level) {
            case 0: // User-Friendly
                multiplier = 0.15; // No Average Data, just for user-friendliness
                break;
            case 1: // Elementary Student
                multiplier = 0.10; // Average of 0.0800 - 0.1200
                break;
            case 2: // High School Student
                multiplier = 0.05; // Average of 0.0400 - 0.0600
                break;
            case 3: // Senior High School Student
                multiplier = 0.0412; // Average of 0.0343 - 0.0480
                break;
            case 4: // College Student
                multiplier = 0.0412; // Average of 0.0343 - 0.0480
                break;
            case 5: // Graduate (Bachelor's)
                multiplier = 0.035; // Average of 0.0300 - 0.0400
                break;
            case 6: // Master's Student
                multiplier = 0.0305; // Average of 0.0267 - 0.0343
                break;
            case 7: // Doctorate (PhD)
                multiplier = 0.027; // Average of 0.0240 - 0.0300
                break;
            default:
                multiplier = 0.05; // Default value if no valid level is provided
                break;
        }

        return (int) (length * multiplier);
    }


//    private int calculateDelayBasedOnLength(int length) {
//        // Adjust the multiplier according to your target audience.
//        // For example, using 0.05 seconds per character (which corresponds to 20 characters per second).
//        double multiplier = 0.06; // Adjust this value based on your target audience
//
//        return (int) (length * multiplier);
//    }



//    private void showNextStep(int step) {
//        step++;
//        String TAG = "showNextStep(" + step + ")";
//
//        switch (step) {
//            case 1:
//                Log.e(TAG, "Step: " + step);
//                contentTextView_2.setVisibility(View.VISIBLE);
//                break;
//            case 2:
//                Log.e(TAG, "Step: " + step);
//                contentTextView_3.setVisibility(View.VISIBLE);
//                break;
//            case 3:
//                Log.e(TAG, "NOTHING TO SHOW, STEP: " + step);
//                break;
//            case 4:
//                Log.e(TAG, "NOTHING TO SHOW, STEP: " + step);
//                break;
//            case 5:
//                Log.e(TAG, "NOTHING TO SHOW, STEP: " + step);
//                break;
//
//        }
//    }

//    private void updateNextButtonForFinalAction() {
//        nextButton.setText("Next");
//        nextButton.setTextColor(getResources().getColor(R.color.black));
//        nextButton.setBackgroundResource(android.R.color.darker_gray);
//        nextButton.setAllCaps(true);
//    }

    public void loadTextContentForKey(String key, int pageNumber) {

        String TAG = "LOAD TEXT LESSON TEXTS";
        Log.d(TAG, "Page Number: " + pageNumber);

        Log.e(TAG, "key: " + key);

        // Please note that ids should be:
        // for ex:

        // Page 1
        // module7_1_1_title
        // module7_1_1_content_1
        // module7_1_1_content_2
        // module7_1_1_content_3

        //           |
        // Page 2    v
        // module7_1_2_title
        // module7_1_2_content_1
        // module7_1_2_content_2
        // module7_1_2_content_3
        //           ^
        //           |

        String title = "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_title";
        String context1 = "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_1";
        String context2 = "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_2";
        String context3 = "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_3";

        // Retrieve the resource IDs
        int titleResId = getResources().getIdentifier(title, "string", getContext().getPackageName());
        int text1ResId = getResources().getIdentifier(context1, "string", getContext().getPackageName());
        int text2ResId = getResources().getIdentifier(context2, "string", getContext().getPackageName());
        int text3ResId = getResources().getIdentifier(context3, "string", getContext().getPackageName());

        String TEG = "auto resource ID generator";

        // pang check lang, ewan baka kasi may mali sa strings resource ko
        if (titleResId == 0)
            Log.e(TEG, "Title is  wrong: " + title);
        if (text1ResId == 0)
            Log.e(TEG, "Text 1 is wrong: " + context1);
        if (text2ResId == 0)
            Log.e(TEG, "Text 2 is wrong: " + context2);
        if (text3ResId == 0)
            Log.e(TEG, "Text 3 is wrong: " + context3);


        // Set the text content
        titleTextView.setText("");
        contentTextView_1.setText("");
        contentTextView_2.setText("");
        contentTextView_3.setText("");

        // Set the text content
        titleTextView.setText(titleResId);
        contentTextView_1.setText(text1ResId);
        contentTextView_2.setText(text2ResId);
        contentTextView_3.setText(text3ResId);

//        switch (key) {
//            // Module 1
//            case "M1_Lesson 1":
//                titleTextView.setText(R.string.module1_1_title);
//                contentTextView_1.setText(R.string.module1_1_content_1);
//                contentTextView_2.setText(R.string.module1_1_content_2);
//                contentTextView_3.setText(R.string.module1_1_content_3);
//                break;
//            case "M2_Lesson 1":
//                titleTextView.setText(R.string.module1_2_title);
//                contentTextView_1.setText(R.string.module1_2_content_1);
//                contentTextView_2.setText(R.string.module1_2_content_2);
//                contentTextView_3.setText(R.string.module1_2_content_3);
//                break;
//            case "M3_Lesson 1":
//                titleTextView.setText(R.string.module1_3_title);
//                contentTextView_1.setText(R.string.module1_3_content_1);
//                contentTextView_2.setText(R.string.module1_3_content_2);
//                contentTextView_3.setText(R.string.module1_3_content_3);
//                break;
//            case "M4_Lesson 1":
//                titleTextView.setText(R.string.module1_4_title);
//                contentTextView_1.setText(R.string.module1_4_content_1);
//                contentTextView_2.setText(R.string.module1_4_content_2);
//                contentTextView_3.setText(R.string.module1_4_content_3);
//                break;
//
//            // Module 2
//            case "M1_Lesson 2":
//                titleTextView.setText(R.string.module2_1_title);
//                contentTextView_1.setText(R.string.module2_1_content_1);
//                contentTextView_2.setText(R.string.module2_1_content_2);
//                contentTextView_3.setText(R.string.module2_1_content_3);
//                break;
//
//            // Module 3
//            case "M1_Lesson 3":
//                titleTextView.setText(R.string.module3_1_title);
//                contentTextView_1.setText(R.string.module3_1_content_1);
//                contentTextView_2.setText(R.string.module3_1_content_2);
//                contentTextView_3.setText(R.string.module3_1_content_3);
//                break;
//            case "M2_Lesson 3":
//                titleTextView.setText(R.string.module3_2_title);
//                contentTextView_1.setText(R.string.module3_2_content_1);
//                contentTextView_2.setText(R.string.module3_2_content_2);
//                contentTextView_3.setText(R.string.module3_2_content_3);
//                break;
//            case "M3_Lesson 3":
//                titleTextView.setText(R.string.module3_3_title);
//                contentTextView_1.setText(R.string.module3_3_content_1);
//                contentTextView_2.setText(R.string.module3_3_content_2);
//                contentTextView_3.setText(R.string.module3_3_content_3);
//                break;
//
//            // Module 4
//            case "M1_Lesson 4":
//                titleTextView.setText(R.string.module4_1_title);
//                contentTextView_1.setText(R.string.module4_1_content_1);
//                contentTextView_2.setText(R.string.module4_1_content_2);
//                contentTextView_3.setText(R.string.module4_1_content_3);
//                break;
//            case "M2_Lesson 4":
//                titleTextView.setText(R.string.module4_2_title);
//                contentTextView_1.setText(R.string.module4_2_content_1);
//                contentTextView_2.setText(R.string.module4_2_content_2);
//                contentTextView_3.setText(R.string.module4_2_content_3);
//                break;
//            case "M3_Lesson 4":
//                titleTextView.setText(R.string.module4_3_title);
//                contentTextView_1.setText(R.string.module4_3_content_1);
//                contentTextView_2.setText(R.string.module4_3_content_2);
//                contentTextView_3.setText(R.string.module4_3_content_3);
//                break;
//
//            // Module 5
//            case "M1_Lesson 5":
//                titleTextView.setText(R.string.module5_1_title);
//                contentTextView_1.setText(R.string.module5_1_content_1);
//                contentTextView_2.setText(R.string.module5_1_content_2);
//                contentTextView_3.setText(R.string.module5_1_content_3);
//                break;
//            case "M2_Lesson 5":
//                titleTextView.setText(R.string.module5_2_title);
//                contentTextView_1.setText(R.string.module5_2_content_1);
//                contentTextView_2.setText(R.string.module5_2_content_2);
//                contentTextView_3.setText(R.string.module5_2_content_3);
//                break;
//            case "M3_Lesson 5":
//                titleTextView.setText(R.string.module5_3_title);
//                contentTextView_1.setText(R.string.module5_3_content_1);
//                contentTextView_2.setText(R.string.module5_3_content_2);
//                contentTextView_3.setText(R.string.module5_3_content_3);
//                break;
//
//            // Module 6
//            case "M1_Lesson 6":
//                titleTextView.setText(R.string.module6_1_title);
//                contentTextView_1.setText(R.string.module6_1_content_1);
//                contentTextView_2.setText(R.string.module6_1_content_2);
//                contentTextView_3.setText(R.string.module6_1_content_3);
//                break;
//            case "M2_Lesson 6":
//                titleTextView.setText(R.string.module6_2_title);
//                contentTextView_1.setText(R.string.module6_2_content_1);
//                contentTextView_2.setText(R.string.module6_2_content_2);
//                contentTextView_3.setText(R.string.module6_2_content_3);
//                break;
//            case "M3_Lesson 6":
//                titleTextView.setText(R.string.module6_3_title);
//                contentTextView_1.setText(R.string.module6_3_content_1);
//                contentTextView_2.setText(R.string.module6_3_content_2);
//                contentTextView_3.setText(R.string.module6_3_content_3);
//                break;
//
//            // Module 7
//            case "M1_Lesson 7":
//
//                String M7 = "M1_Lesson 7";
//
//                Log.e(M7, "Page Number: " + pageNumber);
//
//
//                // Retrieve the resource IDs
//                int titleResId = getResources().getIdentifier("module7_1_" + pageNumber + "_title", "string", getContext().getPackageName());
//                int text1ResId = getResources().getIdentifier("module7_1_" + pageNumber + "_content_1", "string", getContext().getPackageName());
//                int text2ResId = getResources().getIdentifier("module7_1_" + pageNumber + "_content_2", "string", getContext().getPackageName());
//                int text3ResId = getResources().getIdentifier("module7_1_" + pageNumber + "_content_3", "string", getContext().getPackageName());
//
//                // Check if any of the resource IDs are 0 (not found)
//                if (titleResId == 0 || text1ResId == 0 || text2ResId == 0 || text3ResId == 0) {
//                    Log.e(M7, "One or more resource IDs were not found. Check the resource names.");
//                } else {
//                    // Set the text content using the resolved resource values
//                    titleTextView.setText(getString(titleResId));
//                    contentTextView_1.setText(getString(text1ResId));
//                    contentTextView_2.setText(getString(text2ResId));
//                    contentTextView_3.setText(getString(text3ResId));
//
//                    // Log the resource IDs
//                    Log.e(M7, "titleResId: " + getString(titleResId));
//                    Log.e(M7, "text1ResId: " + getString(text1ResId));
//                    Log.e(M7, "text2ResId: " + getString(text2ResId));
//                    Log.e(M7, "text3ResId: " + getString(text3ResId));
//                }
//
//                break;
//
//
//            // Module 8
//            case "M1_Lesson 8":
//                titleTextView.setText(R.string.module8_1_title);
//                contentTextView_1.setText(R.string.module8_1_content_1);
//                contentTextView_2.setText(R.string.module8_1_content_2);
//                contentTextView_3.setText(R.string.module8_1_content_3);
//                break;
//            case "M2_Lesson 8":
//                titleTextView.setText(R.string.module8_2_title);
//                contentTextView_1.setText(R.string.module8_2_content_1);
//                contentTextView_2.setText(R.string.module8_2_content_2);
//                contentTextView_3.setText(R.string.module8_2_content_3);
//                break;
//            case "M3_Lesson 8":
//                titleTextView.setText(R.string.module8_3_title);
//                contentTextView_1.setText(R.string.module8_3_content_1);
//                contentTextView_2.setText(R.string.module8_3_content_2);
//                contentTextView_3.setText(R.string.module8_3_content_3);
//                break;
//
//            // Fallback case
//            default:
//                titleTextView.setText("Default Title");
//                contentTextView_1.setText("Default Text 1");
//                contentTextView_2.setText("Default Text 2");
//                contentTextView_3.setText("Default Text 3");
//                break;
//        }
    }

}
