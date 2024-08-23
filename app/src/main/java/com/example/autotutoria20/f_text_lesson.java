package com.example.autotutoria20;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.autotutoria20.R;

public class f_text_lesson extends Fragment {
    private static final String ARG_KEY = "key";
    private static final String ARG_PAGE_NUMBER = "pageNumber"; // New argument
    private String key;


    private TextView titleTextView;
    private TextView contentTextView_1, contentTextView_2, contentTextView_3;
    private LinearLayout nextButton;
    private int currentStep = 0; // Track which content is currently shown
    private int pageNumber = 1; // will be incremented after page is done :)

    // Initialize arrays for step counts
    // nagtataka ako dito eh, bakit hindi nalang gamitin yung LessonSequence Class
    // para mai-store dito yung length nung sequence per individual na module??
    // tapos pano maii-store kung ilang page ba sa isang module lesson??
    private int[] module1Steps = {3, 2, 2, 2};
    private int[] module2Steps = {2};
    private int[] module3Steps = {2, 2, 2};
    private int[] module4Steps = {2, 2, 2};
    private int[] module5Steps = {2, 2, 2};
    private int[] module6Steps = {2, 2, 2};
    private int[] module7Steps = {3};
    private int[] module8Steps = {2, 2, 2};

    private OnNextButtonClickListener callback;
    private int totalSteps = 2; // Default total steps

    public static f_text_lesson newInstance(String key, int pageNumber) {
        f_text_lesson fragment = new f_text_lesson();
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);
        args.putInt(ARG_PAGE_NUMBER, pageNumber); // Pass the page number
        fragment.setArguments(args);
        return fragment;
    }

    public static f_text_lesson newInstance(String key) {
        f_text_lesson fragment = new f_text_lesson();
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text, container, false);
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

        // Ensure TextViews and Button are not null
        if (titleTextView == null || contentTextView_1 == null || contentTextView_2 == null || contentTextView_3 == null || nextButton == null) {
            Log.e("f_text_lesson", "One or more views are null. Check your layout file.");
            return;  // Early return to prevent further crashes
        }

        contentTextView_2.setVisibility(View.GONE);
        contentTextView_3.setVisibility(View.GONE);

        // Proceed with your logic
        if (getArguments() != null) {
            key = getArguments().getString(ARG_KEY);
            pageNumber = getArguments().getInt(ARG_PAGE_NUMBER, 1); // Retrieve page number
            setTotalStepsForKey(key);
            loadTextContentForKey(key);

            Log.e("AWJDNLAWJKDN", "key: " + key);
        }

        // Set OnClickListener for the nextButton
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleNextButtonClick(key);
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
    }

    // Define an interface to communicate with the container activity
    public interface OnNextButtonClickListener {
        void onNextButtonClicked();
    }

    private void setTotalStepsForKey(String key) {
        switch (key) {
            // Module 1
            case "M1_Lesson 1":
                totalSteps = module1Steps[0];
                break;
            case "M2_Lesson 1":
                totalSteps = module1Steps[1];
                break;
            case "M3_Lesson 1":
                totalSteps = module1Steps[2];
                break;
            case "M4_Lesson 1":
                totalSteps = module1Steps[3];
                break;

            // Module 2
            case "M1_Lesson 2":
                totalSteps = module2Steps[0];
                break;

            // Module 3
            case "M1_Lesson 3":
                totalSteps = module3Steps[0];
                break;
            case "M2_Lesson 3":
                totalSteps = module3Steps[1];
                break;
            case "M3_Lesson 3":
                totalSteps = module3Steps[2];
                break;

            // Module 4
            case "M1_Lesson 4":
                totalSteps = module4Steps[0];
                break;
            case "M2_Lesson 4":
                totalSteps = module4Steps[1];
                break;
            case "M3_Lesson 4":
                totalSteps = module4Steps[2];
                break;

            // Module 5
            case "M1_Lesson 5":
                totalSteps = module5Steps[0];
                break;
            case "M2_Lesson 5":
                totalSteps = module5Steps[1];
                break;
            case "M3_Lesson 5":
                totalSteps = module5Steps[2];
                break;

            // Module 6
            case "M1_Lesson 6":
                totalSteps = module6Steps[0];
                break;
            case "M2_Lesson 6":
                totalSteps = module6Steps[1];
                break;
            case "M3_Lesson 6":
                totalSteps = module6Steps[2];
                break;

            // Module 7
            case "M1_Lesson 7":
                totalSteps = module7Steps[0];
                break;

            // Module 8
            case "M1_Lesson 8":
                totalSteps = module8Steps[0];
                break;
            case "M2_Lesson 8":
                totalSteps = module8Steps[1];
                break;
            case "M3_Lesson 8":
                totalSteps = module8Steps[2];
                break;

            // Fallback/default
            default:
                totalSteps = 2; // Fallback default steps
                break;
        }
    }

    private void handleNextButtonClick(String key) {
        Log.d("handleNextButtonClick()", "currentStep(" + currentStep + ") < totalSteps(" + totalSteps + ")");


        if (currentStep < (totalSteps-1)) {
            showNextStep(currentStep);
        } else {
            // Reset currentStep for the next page
            currentStep = 0;
            pageNumber++;

            // Increment the pageNumber for the next set of content
            loadTextContentForKey(key);

////             Load the first step of the new content
//            showNextStep(currentStep);

            // Notify the parent activity that the button was clicked
            if (callback != null) {
                callback.onNextButtonClicked();
            }
        }
        currentStep++;
    }

    private void showNextStep(int step) {
        step++;
        String TAG = "showNextStep(" + step + ")";
        switch (step) {
            case 1:
                Log.e(TAG, "Step: " + step);
                contentTextView_2.setVisibility(View.VISIBLE);
                break;
            case 2:
                Log.e(TAG, "Step: " + step);
                contentTextView_3.setVisibility(View.VISIBLE);
                break;
            case 3:
                Log.e(TAG, "NOTHING TO SHOW, STEP: " + step);
                break;
            case 4:
                Log.e(TAG, "NOTHING TO SHOW, STEP: " + step);
                break;
            case 5:
                Log.e(TAG, "NOTHING TO SHOW, STEP: " + step);
                break;

        }
    }

//    private void updateNextButtonForFinalAction() {
//        nextButton.setText("Next");
//        nextButton.setTextColor(getResources().getColor(R.color.black));
//        nextButton.setBackgroundResource(android.R.color.darker_gray);
//        nextButton.setAllCaps(true);
//    }

    private void loadTextContentForKey(String key) {
        String TAG = "LOAD TEXT LESSON TEXTS";
        Log.d(TAG, "Page Number: " + pageNumber);


        // Set the text content
        titleTextView.setText("");
        contentTextView_1.setText("");
        contentTextView_2.setText("");
        contentTextView_3.setText("");

        switch (key) {
            // Module 1
            case "M1_Lesson 1":
                titleTextView.setText(R.string.module1_1_title);
                contentTextView_1.setText(R.string.module1_1_content_1);
                contentTextView_2.setText(R.string.module1_1_content_2);
                contentTextView_3.setText(R.string.module1_1_content_3);
                break;
            case "M2_Lesson 1":
                titleTextView.setText(R.string.module1_2_title);
                contentTextView_1.setText(R.string.module1_2_content_1);
                contentTextView_2.setText(R.string.module1_2_content_2);
                contentTextView_3.setText(R.string.module1_2_content_3);
                break;
            case "M3_Lesson 1":
                titleTextView.setText(R.string.module1_3_title);
                contentTextView_1.setText(R.string.module1_3_content_1);
                contentTextView_2.setText(R.string.module1_3_content_2);
                contentTextView_3.setText(R.string.module1_3_content_3);
                break;
            case "M4_Lesson 1":
                titleTextView.setText(R.string.module1_4_title);
                contentTextView_1.setText(R.string.module1_4_content_1);
                contentTextView_2.setText(R.string.module1_4_content_2);
                contentTextView_3.setText(R.string.module1_4_content_3);
                break;

            // Module 2
            case "M1_Lesson 2":
                titleTextView.setText(R.string.module2_1_title);
                contentTextView_1.setText(R.string.module2_1_content_1);
                contentTextView_2.setText(R.string.module2_1_content_2);
                contentTextView_3.setText(R.string.module2_1_content_3);
                break;

            // Module 3
            case "M1_Lesson 3":
                titleTextView.setText(R.string.module3_1_title);
                contentTextView_1.setText(R.string.module3_1_content_1);
                contentTextView_2.setText(R.string.module3_1_content_2);
                contentTextView_3.setText(R.string.module3_1_content_3);
                break;
            case "M2_Lesson 3":
                titleTextView.setText(R.string.module3_2_title);
                contentTextView_1.setText(R.string.module3_2_content_1);
                contentTextView_2.setText(R.string.module3_2_content_2);
                contentTextView_3.setText(R.string.module3_2_content_3);
                break;
            case "M3_Lesson 3":
                titleTextView.setText(R.string.module3_3_title);
                contentTextView_1.setText(R.string.module3_3_content_1);
                contentTextView_2.setText(R.string.module3_3_content_2);
                contentTextView_3.setText(R.string.module3_3_content_3);
                break;

            // Module 4
            case "M1_Lesson 4":
                titleTextView.setText(R.string.module4_1_title);
                contentTextView_1.setText(R.string.module4_1_content_1);
                contentTextView_2.setText(R.string.module4_1_content_2);
                contentTextView_3.setText(R.string.module4_1_content_3);
                break;
            case "M2_Lesson 4":
                titleTextView.setText(R.string.module4_2_title);
                contentTextView_1.setText(R.string.module4_2_content_1);
                contentTextView_2.setText(R.string.module4_2_content_2);
                contentTextView_3.setText(R.string.module4_2_content_3);
                break;
            case "M3_Lesson 4":
                titleTextView.setText(R.string.module4_3_title);
                contentTextView_1.setText(R.string.module4_3_content_1);
                contentTextView_2.setText(R.string.module4_3_content_2);
                contentTextView_3.setText(R.string.module4_3_content_3);
                break;

            // Module 5
            case "M1_Lesson 5":
                titleTextView.setText(R.string.module5_1_title);
                contentTextView_1.setText(R.string.module5_1_content_1);
                contentTextView_2.setText(R.string.module5_1_content_2);
                contentTextView_3.setText(R.string.module5_1_content_3);
                break;
            case "M2_Lesson 5":
                titleTextView.setText(R.string.module5_2_title);
                contentTextView_1.setText(R.string.module5_2_content_1);
                contentTextView_2.setText(R.string.module5_2_content_2);
                contentTextView_3.setText(R.string.module5_2_content_3);
                break;
            case "M3_Lesson 5":
                titleTextView.setText(R.string.module5_3_title);
                contentTextView_1.setText(R.string.module5_3_content_1);
                contentTextView_2.setText(R.string.module5_3_content_2);
                contentTextView_3.setText(R.string.module5_3_content_3);
                break;

            // Module 6
            case "M1_Lesson 6":
                titleTextView.setText(R.string.module6_1_title);
                contentTextView_1.setText(R.string.module6_1_content_1);
                contentTextView_2.setText(R.string.module6_1_content_2);
                contentTextView_3.setText(R.string.module6_1_content_3);
                break;
            case "M2_Lesson 6":
                titleTextView.setText(R.string.module6_2_title);
                contentTextView_1.setText(R.string.module6_2_content_1);
                contentTextView_2.setText(R.string.module6_2_content_2);
                contentTextView_3.setText(R.string.module6_2_content_3);
                break;
            case "M3_Lesson 6":
                titleTextView.setText(R.string.module6_3_title);
                contentTextView_1.setText(R.string.module6_3_content_1);
                contentTextView_2.setText(R.string.module6_3_content_2);
                contentTextView_3.setText(R.string.module6_3_content_3);
                break;

            // Module 7
            case "M1_Lesson 7":

                String M7 = "M1_Lesson 7";

                Log.e(M7, "Page Number: " + pageNumber);


                // Retrieve the resource IDs
                int titleResId = getResources().getIdentifier("module7_1_" + pageNumber + "_title", "string", getContext().getPackageName());
                int text1ResId = getResources().getIdentifier("module7_1_" + pageNumber + "_content_1", "string", getContext().getPackageName());
                int text2ResId = getResources().getIdentifier("module7_1_" + pageNumber + "_content_2", "string", getContext().getPackageName());
                int text3ResId = getResources().getIdentifier("module7_1_" + pageNumber + "_content_3", "string", getContext().getPackageName());

                // Log the resource IDs
                Log.e(M7, "titleResId: " + getString(titleResId));
                Log.e(M7, "text1ResId: " + getString(text1ResId));
                Log.e(M7, "text2ResId: " + getString(text2ResId));
                Log.e(M7, "text3ResId: " + getString(text3ResId));

                // Check if any of the resource IDs are 0 (not found)
                if (titleResId == 0 || text1ResId == 0 || text2ResId == 0 || text3ResId == 0) {
                    Log.e(M7, "One or more resource IDs were not found. Check the resource names.");
                } else {
                    // Set the text content using the resolved resource values
                    titleTextView.setText(getString(titleResId));
                    contentTextView_1.setText(getString(text1ResId));
                    contentTextView_2.setText(getString(text2ResId));
                    contentTextView_3.setText(getString(text3ResId));
                }

                break;


            // Module 8
            case "M1_Lesson 8":
                titleTextView.setText(R.string.module8_1_title);
                contentTextView_1.setText(R.string.module8_1_content_1);
                contentTextView_2.setText(R.string.module8_1_content_2);
                contentTextView_3.setText(R.string.module8_1_content_3);
                break;
            case "M2_Lesson 8":
                titleTextView.setText(R.string.module8_2_title);
                contentTextView_1.setText(R.string.module8_2_content_1);
                contentTextView_2.setText(R.string.module8_2_content_2);
                contentTextView_3.setText(R.string.module8_2_content_3);
                break;
            case "M3_Lesson 8":
                titleTextView.setText(R.string.module8_3_title);
                contentTextView_1.setText(R.string.module8_3_content_1);
                contentTextView_2.setText(R.string.module8_3_content_2);
                contentTextView_3.setText(R.string.module8_3_content_3);
                break;

            // Fallback case
            default:
                titleTextView.setText("Default Title");
                contentTextView_1.setText("Default Text 1");
                contentTextView_2.setText("Default Text 2");
                contentTextView_3.setText("Default Text 3");
                break;
        }
    }

}
