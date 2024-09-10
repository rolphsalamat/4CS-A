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

public class f_1_lesson_text extends Fragment {

    private static final String ARG_KEY = "key";
    private static final String ARG_PAGE_NUMBER = "pageNumber"; // New argument
    private static final String ARG_MODULE = "currentModule"; // New argument
    private static final String ARG_LESSON = "currentLesson"; // New argument

    private String key;
    private String currentModule; // Variable to store currentModule
    private String currentLesson; // Variable to store currentLesson

    private TextView titleTextView;
    private LinearLayout
            contentLayout_1, contentLayout_2, contentLayout_3, contentLayout_4,
            contentLayout_5, contentLayout_6, contentLayout_7, contentLayout_8;
    LinearLayout[] contentLayouts = {
            contentLayout_1, contentLayout_2, contentLayout_3, contentLayout_4,
            contentLayout_5, contentLayout_6, contentLayout_7, contentLayout_8
    };
    private ImageView
            contentImageView_1, contentImageView_2, contentImageView_3, contentImageView_4,
            contentImageView_5, contentImageView_6, contentImageView_7, contentImageView_8;
    private TextView
            contentTextView_1, contentTextView_2, contentTextView_3, contentTextView_4,
            contentTextView_5, contentTextView_6, contentTextView_7, contentTextView_8;

    // Array of content TextViews and ImageViews
    TextView[] contentTextViews = {
            contentTextView_1, contentTextView_2, contentTextView_3, contentTextView_4,
            contentTextView_5, contentTextView_6, contentTextView_7, contentTextView_8
    };

    ImageView[] contentImageViews = {
            contentImageView_1, contentImageView_2, contentImageView_3, contentImageView_4,
            contentImageView_5, contentImageView_6, contentImageView_7, contentImageView_8
    };
    private LinearLayout nextButton;
    private Boolean isTextLessonDone = false;
    private Button tapToContinueButton;
    private int delayFinish = 0;
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

        // Initialize Layouts
        contentLayout_1 = view.findViewById(R.id.content_1);
        contentLayout_2 = view.findViewById(R.id.content_2);
        contentLayout_3 = view.findViewById(R.id.content_3);
        contentLayout_4 = view.findViewById(R.id.content_4);
        contentLayout_5 = view.findViewById(R.id.content_5);
        contentLayout_6 = view.findViewById(R.id.content_6);
        contentLayout_7 = view.findViewById(R.id.content_7);
        contentLayout_8 = view.findViewById(R.id.content_8);

        // Now populate the arrays after initializing the layouts
        contentLayouts = new LinearLayout[]{
                contentLayout_1, contentLayout_2, contentLayout_3, contentLayout_4,
                contentLayout_5, contentLayout_6, contentLayout_7, contentLayout_8
        };

        // Initialize TextViews
        contentTextView_1 = view.findViewById(R.id.text_lesson_content_1);
        contentTextView_2 = view.findViewById(R.id.text_lesson_content_2);
        contentTextView_3 = view.findViewById(R.id.text_lesson_content_3);
        contentTextView_4 = view.findViewById(R.id.text_lesson_content_4);
        contentTextView_5 = view.findViewById(R.id.text_lesson_content_5);
        contentTextView_6 = view.findViewById(R.id.text_lesson_content_6);
        contentTextView_7 = view.findViewById(R.id.text_lesson_content_7);
        contentTextView_8 = view.findViewById(R.id.text_lesson_content_8);

        // Initialize ImageViews
        contentImageView_1 = view.findViewById(R.id.image_lesson_content_1);
        contentImageView_2 = view.findViewById(R.id.image_lesson_content_2);
        contentImageView_3 = view.findViewById(R.id.image_lesson_content_3);
        contentImageView_4 = view.findViewById(R.id.image_lesson_content_4);
        contentImageView_5 = view.findViewById(R.id.image_lesson_content_5);
        contentImageView_6 = view.findViewById(R.id.image_lesson_content_6);
        contentImageView_7 = view.findViewById(R.id.image_lesson_content_7);
        contentImageView_8 = view.findViewById(R.id.image_lesson_content_8);

        // Now populate the arrays after initializing the views
        contentTextViews = new TextView[]{
                contentTextView_1, contentTextView_2, contentTextView_3, contentTextView_4,
                contentTextView_5, contentTextView_6, contentTextView_7, contentTextView_8
        };

        contentImageViews = new ImageView[]{
                contentImageView_1, contentImageView_2, contentImageView_3, contentImageView_4,
                contentImageView_5, contentImageView_6, contentImageView_7, contentImageView_8
        };

        // Initialize buttons
        nextButton = view.findViewById(R.id.next_button);
        tapToContinueButton = view.findViewById(R.id.tap_to_continue);

        // Set visibility for buttons
        nextButton.setEnabled(true);
        tapToContinueButton.setVisibility(View.VISIBLE);

        // Ensure TextViews and Button are not null
        if (titleTextView == null || nextButton == null || tapToContinueButton == null) {
            Log.e("f_text_lesson", "One or more views are null. Check your layout file.");
            return;  // Early return to prevent further crashes
        }

        // Hide all content TextViews initially
        for (TextView textView : contentTextViews) {
            textView.setVisibility(View.GONE);
        }

        // Proceed with your logic
        if (getArguments() != null) {
            key = getArguments().getString(ARG_KEY);
            pageNumber = getArguments().getInt(ARG_PAGE_NUMBER, -1); // Retrieve page number
            setTotalStepsForKey(key);
        }

        // Set OnClickListener for the nextButton
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextButton.setEnabled(false);
                tapToContinueButton.setEnabled(false);
                tapToContinueButton.setVisibility(View.GONE);
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

    private void setTotalStepsForKey(String key) {

        totalSteps = 0;  // Reset total steps

        // Generate resource names dynamically

        // check pageNumber
        Log.e("setTotalStepsForKey", "Page Number: " + pageNumber);

//        // ginawa ko lang ganto kasi mali ata yung naming sa String resource file??
//        String baseName = "module" + key.charAt(10) + "_" + pageNumber + "_" + key.charAt(1);

        // pero eto talaga yung original...
        String baseName = "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber;

        Log.e("setTotalStepsForKey", "baseName: " + baseName);

        // Check if title and content resources exist and have values, increment totalSteps accordingly
        if (resourceHasValue(baseName + "_title")) totalSteps++;
        if (resourceHasValue(baseName + "_content_1")) totalSteps++;
        if (resourceHasValue(baseName + "_content_2")) totalSteps++;
        if (resourceHasValue(baseName + "_content_3")) totalSteps++;
        if (resourceHasValue(baseName + "_content_4")) totalSteps++;
        if (resourceHasValue(baseName + "_content_5")) totalSteps++;
        if (resourceHasValue(baseName + "_content_6")) totalSteps++;
        if (resourceHasValue(baseName + "_content_7")) totalSteps++;
        if (resourceHasValue(baseName + "_content_8")) totalSteps++;

        Log.e("setTotalStepsForKey", "totalSteps: " + totalSteps);
    }

    private boolean resourceHasValue(String resourceName) {
        int resId = getResources().getIdentifier(resourceName, "string", getContext().getPackageName());
        Log.e("resourceHasValue()", "checking: " + getResources().getIdentifier(resourceName, "string", getContext().getPackageName()));
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
                TextLessonCompleteListener.onTextLessonComplete(true, delayFinish);

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

    private void setBullet(int pageNumber, int step, ImageView contentImageView, TextView contentTextView, LinearLayout linearLayout) {
        String TAG = "setBullet()";

        // Convert dp to pixels
        float scale = contentImageView.getContext().getResources().getDisplayMetrics().density;
        int sizeInDp = (int) (25 * scale + 0.5f); // 25dp to pixels

        int layoutMargin = 16;

        Log.e(TAG, "calling getIndentation method");

        // Get the bullet types dynamically based on the key and pageNumber
        int[][] moduleArray = f_1_lesson_text_bullets.getIndentation(key, pageNumber);

        if (moduleArray.length == 0 || pageNumber > moduleArray.length) {
            Log.e(TAG, "Invalid page number. No bullets available for page " + pageNumber);
            return; // Invalid page number, return early
        }

        if (step >= moduleArray[pageNumber - 1].length) {
            Log.e(TAG, "Invalid step number for page " + pageNumber + ". Step: " + step);
            return; // Invalid step number, return early
        }

        int bulletType = moduleArray[pageNumber - 1][step];  // Get bullet type for the step

        // Set bullet image and padding based on the bullet type
        if (bulletType == 1) {
            linearLayout.setPadding((50 + layoutMargin), layoutMargin, layoutMargin, layoutMargin);
            contentImageView.getLayoutParams().width = sizeInDp;
            contentImageView.getLayoutParams().height = sizeInDp;
            contentImageView.setImageResource(R.drawable.bullet_1);  // Set bullet_1 image
        } else if (bulletType == 2) {
            linearLayout.setPadding((125 + layoutMargin), layoutMargin, layoutMargin, layoutMargin);
            contentImageView.getLayoutParams().width = sizeInDp;
            contentImageView.getLayoutParams().height = sizeInDp;
            contentImageView.setImageResource(R.drawable.bullet_2);  // Set bullet_2 image
        } else {
            contentImageView.setImageDrawable(null);
            contentTextView.setPadding(0, 0, 0, 0);  // No indentation
        }

        contentImageView.requestLayout();  // Request layout update to apply the new size
    }


    private void showNextStep(int step) {
        String TAG = "showNextStep";
        int actualStep = 0;

        nextButton.setEnabled(false);
        tapToContinueButton.setVisibility(View.GONE);
        tapToContinueButton.setEnabled(false);

        String[] contentKeys = {
                "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_1",
                "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_2",
                "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_3",
                "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_4",
                "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_5",
                "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_6",
                "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_7",
                "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_8"
        };

        for (int i = 0; i < contentKeys.length; i++) {
            String contentKey = contentKeys[i];
            if (resourceHasValue(contentKey)) {
                actualStep++;
                if (actualStep == step + 1) {
                    int resId = getResources().getIdentifier(contentKey, "string", getContext().getPackageName());
                    String content = getString(resId);
                    int delayInSeconds = calculateDelayBasedOnLength(content.length());
                    delayFinish = delayInSeconds;
                    Log.e(TAG, "delayFinish: " + delayFinish);

                    contentTextViews[i].setVisibility(View.VISIBLE);
                    contentTextViews[i].setText(content);
                    setBullet(pageNumber, i, contentImageViews[i], contentTextViews[i], contentLayouts[i]);

                    if (currentStep < (totalSteps - 2)) {
                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                            nextButton.setEnabled(true);
                            tapToContinueButton.setVisibility(View.VISIBLE);
                        }, delayInSeconds * 1000);
                    }

                    return;
                }
            } else {
                contentTextViews[i].setVisibility(View.GONE);
            }
        }
    }

    static int calculateDelayBasedOnLength(int length) {

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

        int level = 3; // Default to High School Student

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

        String title = "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_title";
        String context1 = "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_1";
        String context2 = "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_2";
        String context3 = "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_3";
        String context4 = "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_4";
        String context5 = "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_5";
        String context6 = "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_6";
        String context7 = "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_7";
        String context8 = "module" + key.charAt(10) + "_" + key.charAt(1) + "_" + pageNumber + "_content_8";

        // Retrieve the resource IDs
        int titleResId = getResources().getIdentifier(title, "string", getContext().getPackageName());
        int text1ResId = getResources().getIdentifier(context1, "string", getContext().getPackageName());
        int text2ResId = getResources().getIdentifier(context2, "string", getContext().getPackageName());
        int text3ResId = getResources().getIdentifier(context3, "string", getContext().getPackageName());
        int text4ResId = getResources().getIdentifier(context4, "string", getContext().getPackageName());
        int text5ResId = getResources().getIdentifier(context5, "string", getContext().getPackageName());
        int text6ResId = getResources().getIdentifier(context6, "string", getContext().getPackageName());
        int text7ResId = getResources().getIdentifier(context7, "string", getContext().getPackageName());
        int text8ResId = getResources().getIdentifier(context8, "string", getContext().getPackageName());

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
        if (text4ResId == 0)
            Log.e(TEG, "Text 4 is wrong: " + context4);
        if (text5ResId == 0)
            Log.e(TEG, "Text 5 is wrong: " + context5);
        if (text6ResId == 0)
            Log.e(TEG, "Text 6 is wrong: " + context6);
        if (text7ResId == 0)
            Log.e(TEG, "Text 7 is wrong: " + context7);
        if (text8ResId == 0)
            Log.e(TEG, "Text 8 is wrong: " + context8);


        // Set the text content
        titleTextView.setText("");
        contentTextView_1.setText("");
        contentTextView_2.setText("");
        contentTextView_3.setText("");
        contentTextView_4.setText("");
        contentTextView_5.setText("");
        contentTextView_6.setText("");
        contentTextView_7.setText("");
        contentTextView_8.setText("");

        // Set the text content
        titleTextView.setText(titleResId);
        contentTextView_1.setText(text1ResId);
        contentTextView_2.setText(text2ResId);
        contentTextView_3.setText(text3ResId);
        contentTextView_4.setText(text4ResId);
        contentTextView_5.setText(text5ResId);
        contentTextView_6.setText(text6ResId);
        contentTextView_7.setText(text7ResId);
        contentTextView_8.setText(text8ResId);

    }

}
