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
    private int[] module1Steps = {3, 2, 2, 2};
    private int[] module2Steps = {2};
    private int[] module3Steps = {2, 2, 2};
    private int[] module4Steps = {2, 2, 2};
    private int[] module5Steps = {2, 2, 2};
    private int[] module6Steps = {2, 2, 2};
    private int[] module7Steps = {5};
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

        if (getArguments() != null) {
            key = getArguments().getString(ARG_KEY);
            pageNumber = getArguments().getInt(ARG_PAGE_NUMBER, 1); // Retrieve page number
            setTotalStepsForKey(key);
            loadTextContentForKey(key);
        }
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

    private void handleNextButtonClick() {
        Log.d("handleNextButtonClick()", "currentStep(" + currentStep + ") < totalSteps(" + totalSteps + ")");

        if (currentStep < totalSteps) {
            showNextStep(currentStep);
            currentStep++;
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
//                pageNumber++;

                Log.e("M1_Lesson 7", "Page Number: " + pageNumber);

                // Determine the resource names dynamically
                String titleName = "module7_1_" + pageNumber + "_title";
                String text1Name = "module7_1_" + pageNumber + "_content_1";
                String text2Name = "module7_1_" + pageNumber + "_content_2";
                String text3Name = "module7_1_" + pageNumber + "_content_3";

                Log.e(M7, titleName);
                Log.e(M7, text1Name);
                Log.e(M7, text2Name);
                Log.e(M7, text3Name);

                // Retrieve the resource IDs
                int titleResId = getResources().getIdentifier(titleName, "string", getContext().getPackageName());
                int text1ResId = getResources().getIdentifier(text1Name, "string", getContext().getPackageName());
                int text2ResId = getResources().getIdentifier(text2Name, "string", getContext().getPackageName());
                int text3ResId = getResources().getIdentifier(text3Name, "string", getContext().getPackageName());

                if (pageNumber == 1) {
                    Log.e(M7, "Page Number 1");
                    titleTextView.setText(R.string.module7_1_1_title);
                    contentTextView_1.setText(R.string.module7_1_1_content_1);
                    contentTextView_2.setText(R.string.module7_1_1_content_2);
                    contentTextView_3.setText(R.string.module7_1_1_content_3);
                }
                if (pageNumber == 2) {
                    Log.e(M7, "Page Number 2");
                    titleTextView.setText(R.string.module7_1_2_title);
                    contentTextView_1.setText(R.string.module7_1_2_content_1);
                    contentTextView_2.setText(R.string.module7_1_2_content_2);
                    contentTextView_3.setText(R.string.module7_1_2_content_3);
                }

//                // Set the text content
//                titleTextView.setText(titleResId);
//                contentTextView_1.setText(text1ResId);
//                contentTextView_2.setText(text2ResId);
//                contentTextView_3.setText(text3ResId);
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
