package com.example.autotutoria20;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import java.util.Random;

import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class f_0_lesson_pre_test extends Fragment {

    private Button choiceButton;
    private static final String ARG_MODULE = "module";
    private static final String ARG_LESSON = "lesson";
    private static final String ARG_MODE = "mode";
    private static final Random random = new Random();  // For generating random numbers

    private LinearLayout imageContainer;
    private int currentQuestionIndex = 0;
    private e_Question[] questions;
    private TextView questionText;
    private int incorrect = 0;
    public static RadioGroup choicesGroup;
    private int answerAttempt = 0;
    private int attemptChances = 1;
    private int questionsAnswered = 1;
    static int preTestQuestions = 5;
    private Button submitButton;
    private TextView correct, mistake;
    private TextView total;
    private boolean isCorrect = false;
    public static ShapeableImageView currentButton;
    private boolean isProgressiveMode = true; // Default to Progressive Mode

    private PreTestCompleteListener preTestCompleteListener;

    // BKT Model instance
    private x_bkt_algorithm bktModel;
    private int moduleIndex;
    private int lessonIndex;

    // Interface to notify the container activity when pre-test is complete
    public interface PreTestCompleteListener {
//        void onPreTestComplete(boolean isCorrect);
        void onPreTestComplete(
//                boolean isCorrect,
//                double preTestScore,
                int score);
    }

    public static f_0_lesson_pre_test newInstance(String module, String lesson, String mode) {
        f_0_lesson_pre_test fragment = new f_0_lesson_pre_test();
        Bundle args = new Bundle();
        args.putString(ARG_MODULE, module);
        args.putString(ARG_LESSON, lesson);
        args.putString(ARG_MODE, mode);  // Pass the learningMode here
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PreTestCompleteListener) {
            preTestCompleteListener = (PreTestCompleteListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement PreTestCompleteListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        preTestCompleteListener = null; // Avoid memory leaks
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isCorrect", isCorrect);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the Singleton instance
        bktModel = x_bkt_algorithm.getInstance(0.3, 0.2, 0.1, 0.4);

        // Determine if it's Progressive Mode
        if (getArguments() != null) {
            isProgressiveMode = getArguments().getString(ARG_MODE).equals("Progressive Mode");
        }

        // Initialize BKT Scores
        String collectionPath = isProgressiveMode ? "Progressive Mode" : "Free Use Mode";
        String documentName = "Lesson " + (getLessonIndex(getArguments().getString(ARG_LESSON)) + 1);
        String module = "M" + (getModuleIndex(getArguments().getString(ARG_MODULE)) + 1);
//        bktModel.getBKTScore(module, documentName, collectionPath,);

        int moduleIndex = Integer.parseInt(String.valueOf(module.charAt(1)));
        int lessonIndex = Integer.parseInt(String.valueOf(documentName.charAt(7)));

        x_bkt_algorithm.getBKTScore(moduleIndex, lessonIndex, isProgressiveMode, new x_bkt_algorithm.ScoreCallback() {
            @Override
            public void onScoreRetrieved(float score) {
                Log.d("TAG", "The BKT Score is: " + score);
                // Handle the retrieved score here

                // Reset only if di pa tapos yung lesson..
                // kelangan completed sya, pero dapat pasado din
                // Case 1: Completed, not Passed - RETAKE
                // Case 2: Completed, Passed - OK (do not reset)
                // Case 3: InComplete, not Passed - RETAKE
                // Case 4: InComplete, Passed - RETAKE (pwede kasi siyang passed if mataas ang Pre-Test)

//                Boolean lessonPassed = score < b_main_0_menu_categorize_user.passingGrade;
//
//                Log.d("TAG", "Lesson Complete? " + d_Lesson_container.isCompleted);
//                Log.d("TAG", "Lesson Passed? " + lessonPassed);
//
//                Log.e("TAG", "Let's Check Case...");
//
//                // Reset logic based on completion and passing status
//                if (d_Lesson_container.isCompleted) {
//                    if (!lessonPassed) {
//                        // Case 1: Completed, not Passed - RETAKE
//                        Log.e("TAG", "Case 1: Completed, not Passed - RETAKE");
//                        c_Lesson_feedback.resetResult();
//                        x_bkt_algorithm.resetScore(moduleIndex, lessonIndex, isProgressiveMode);
//                    }
//                    Log.e("TAG", "Case 2: Completed, Passed - OK (do nothing)");
//                    // Case 2: Completed, Passed - OK (do nothing)
//                } else {
//                    // If the lesson is incomplete
//                    if (!lessonPassed) {
//                        Log.e("TAG", "Case 3: Incomplete, not Passed - RETAKE");
//                        // Case 3: Incomplete, not Passed - RETAKE
//                        c_Lesson_feedback.resetResult();
//                        x_bkt_algorithm.resetScore(moduleIndex, lessonIndex, isProgressiveMode);
//                    } else {
//                        Log.e("TAG", "Case 4: Incomplete, Passed - RETAKE (possible because of high Pre-Test)");
//                        // Case 4: Incomplete, Passed - RETAKE (possible because of high Pre-Test)
//                        c_Lesson_feedback.resetResult();
//                        x_bkt_algorithm.resetScore(moduleIndex, lessonIndex, isProgressiveMode);
//                    }
//                }
            }
        });

//        bktModel.initializeBKTScores(collectionPath, documentName, bktScores -> {
//            if (bktScores != null) {
//                Log.d("f_pre_test", "BKT Scores initialized: " + bktScores);
//            } else {
//                Log.e("f_pre_test", "Failed to retrieve BKT Scores");
//            }
//        });

//        bktModel.initializeBKTScores(collectionPath, documentName,
//                module,
//                bktScores -> {
//            if (bktScores != null) {
//                Log.d("f_pre_test", "BKT Scores initialized: " + bktScores);
//            } else {
//                Log.e("f_pre_test", "Failed to retrieve BKT Scores");
//            }
//        });

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Log.d("FragmentLifecycle", "onCreateView");
        if (savedInstanceState != null) {
            isCorrect = savedInstanceState.getBoolean("isCorrect", false);
        }
        return inflater.inflate(R.layout.f_0_lesson_pre_test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Log.e("Pre-Test onViewCreated", "Load Images");


        // Ensure imageContainer has MATCH_PARENT width and fixed height in XML
        imageContainer = requireView().findViewById(R.id.image_container);

        if (imageContainer == null) {
//            Log.e("InitializationError", "imageContainer is null!");
            return;
        }

        // Set container height to 60dp
        int containerHeight = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics());
        imageContainer.getLayoutParams().height = containerHeight;

        // Set container orientation to horizontal
        imageContainer.setOrientation(LinearLayout.HORIZONTAL);

        // Total horizontal spacing: 16dp as a base value for margin adjustment
        int baseSpacing = 16;

        // Calculate dynamic margin based on the number of images
        int marginInPixels = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, baseSpacing / Math.max(1, preTestQuestions), getResources().getDisplayMetrics());

        // Iterate through the number of images
        for (int i = 0; i < preTestQuestions; i++) {

            ImageView imageView = new ImageView(requireContext());

            // Set each image to take an equal part of the width
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0, // Width: Dynamic, evenly spaced
                    LinearLayout.LayoutParams.MATCH_PARENT, // Height matches the container
                    1f // Weight to divide equally
            );

            // Apply dynamic margins between images
            if (i > 0) { // No margin for the first image
                params.setMargins(marginInPixels, 0, 0, 0);
            }

            imageView.setLayoutParams(params);

            // Set the tag
            imageView.setTag("testAnswer_" + (i + 1));

            // Set the image resource or background
            imageView.setImageResource(R.drawable.test_answer_blank);
//            imageView.setColorFilter(Color.GRAY);
//            imageView.setBackgroundColor(Color.RED);

            // Add the ImageView to the container
            imageContainer.addView(imageView);

//            Log.e("Pre-Test onViewCreated", "load image done");

        }

        questionText = view.findViewById(R.id.question_text);
        choicesGroup = view.findViewById(R.id.choices_group);
        submitButton = view.findViewById(R.id.submit_pre_test);

//        submitButton.setVisibility(View.GONE);

//        currentButton = view.findViewById(R.id.currentButton);
//        correct = view.findViewById(R.id.answers_correct);
//        mistake = view.findViewById(R.id.answers_wrong);
        total = view.findViewById(R.id.answers_total);

        total.setText("Item: " + c_Lesson_feedback.preTestAttemptAnswers
                + "/"
                +  preTestQuestions);

//        if (!d_Lesson_container.isPreTestComplete) {
//            currentButton.setEnabled(false);
//            currentButton.setVisibility(View.GONE);
//        } else {
//            currentButton.setEnabled(true);
//            currentButton.setVisibility(View.VISIBLE);
//        }

        // Ensure valid indices are used
        moduleIndex = getModuleIndex(getArguments().getString(ARG_MODULE));
        lessonIndex = getLessonIndex(getArguments().getString(ARG_LESSON));

        Log.i("481", "481 | pre-test | moduleIndex: " + moduleIndex);
        Log.i("481", "481 | pre-test | lessonIndex: " + lessonIndex);

        if (moduleIndex < 0 || lessonIndex < 0) {
//            Log.e("submitButton.onClick", "Invalid module or lesson index");
            return;
        }

        // lipat ko na to sa d_Lesson_container para di na mag loko :>

//        Log.e("TAG", "pasado naba? kasi kung hindi i-reset kona to");
//        // Reset Test Score only if not yet passed
//        if (!d_Lesson_container.lessonPassed) {
//            Log.e("TAG", "di pa :>");
//            x_bkt_algorithm.updateTestScore(
//                    isProgressiveMode,
//                    moduleIndex, lessonIndex,
//                    "Pre-Test",
//                    0); // reset the pre-test :D
//
//            c_Lesson_feedback.resetResult();
//        }


//        currentButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) { d_Lesson_container.onGoToCurrent(); }
//        });

        if (getArguments() != null) {

            String module = getArguments().getString(ARG_MODULE);
            String lesson = getArguments().getString(ARG_LESSON);

            String TAG = "ANAK KO";

            Log.i(TAG, TAG + "| ARG_MODULE | Module: " + module);
            Log.i(TAG, TAG + "| ARG_MODULE | Lesson: " + lesson);

            Log.i(TAG, TAG + " Module: " + module);
            Log.i(TAG, TAG + " Lesson: " + lesson);

            String formattedModule = "Module " + lesson.charAt(7);
            String formattedLesson = "Lesson " + module.charAt(1);
            Log.i(TAG, TAG + " formattedModule: " + formattedModule);
            Log.i(TAG, TAG + " formattedLesson: " + formattedLesson);

            questions = t_TestDataFromDatabase
                    .getRandomQuestionsData(
                            formattedModule,
                            formattedLesson,
                            "Pre-Test")
                    .toArray(new e_Question[0]);

            // Retrieve questions based on module and lesson
//            questions = getPreTestQuestions(formattedModule, lesson, "Pre-Test");

//            getPreTestFromDatabase(module, lesson);

            // Load the first question
            loadQuestion();
        }

        submitButton.setOnClickListener(v -> {

//            Log.e("HEY!", "Submit Button Clicked!");

//            if (d_Lesson_container.isPreTestComplete)
//                d_Lesson_container.onGoToCurrent();

//            choiceButton.

//            if (!(choicesGroup.getCheckedRadioButtonId() == -1)) {

                // Dito originally yung answerAttempt++;
                answerAttempt++;
                currentQuestionIndex++;

//                boolean correctAnswer = checkAnswer(); // Check if the answer is correct

//                Log.e("HEY!", "questionsAnswered("+questionsAnswered+") < preTestQuestions("+(preTestQuestions)+")");
                // Ensure that questions answered does not exceed total pre-test questions.


        });
    }

//    private e_Question[] getPreTestQuestions(String module, String lesson, String testMode) {
//        String TAG = "getPreTestQuestions";
//        Log.e(TAG, "I am here!");
//
//        // Log available modules for debugging
//        Log.d(TAG, "Available modules: " + t_TestDataFromDatabase.modules.keySet());
//
//        // Retrieve the module data from the modules map
//        Map<String, Object> moduleData = t_TestDataFromDatabase.modules.get(module);
//
//
//        if (moduleData == null) {
//            Log.e("PreTest", "Module not found: " + module);
//            return new e_Question[0]; // Return empty array if module not found
//        }
//
//        // Retrieve the lesson data from the module data
//        Map<String, Object> lessonData = (Map<String, Object>) moduleData.get(lesson);
//        if (lessonData == null) {
//            Log.e("PreTest", "Lesson not found: " + lesson);
//            return new e_Question[0]; // Return empty array if lesson not found
//        }
//
//        // Retrieve pre-test questions from lesson data
//        Map<String, Object> preTestData = (Map<String, Object>) lessonData.get(testMode);
//        if (preTestData == null) {
//            Log.e("PreTest", "Pre-Test data not found for: " + lesson);
//            return new e_Question[0]; // Return empty array if pre-test data not found
//        }
//
//        // Prepare to collect questions
//        List<e_Question> questionsList = new ArrayList<>();
//
//        // Loop through each question in pre-test data
//        for (String questionKey : preTestData.keySet()) {
//            Map<String, Object> questionDetails = (Map<String, Object>) preTestData.get(questionKey);
//
//            // Create a new e_Question object based on retrieved details
//            String questionText = (String) questionDetails.get("Question");
//
//            // Convert List<String> choices to String[]
//            List<String> choicesList = (List<String>) questionDetails.get("Choices");
//            String[] choices = choicesList.toArray(new String[0]); // Convert List to Array
//
//            Number answer = (Number) questionDetails.get("Answer");
//
//            // Create an e_Question instance and add it to the list
//            e_Question question = new e_Question(questionText, choices, answer.intValue());
//            questionsList.add(question);
//        }
//
//        // Convert list to array and return
//        return questionsList.toArray(new e_Question[0]);
//    }

//    private e_Question[] getPreTestQuestions(String module, String lesson) {
//        String key = module + "_" + lesson;
//
//        switch (key) {
//            /* ===== Module 1 ===== */
//            case "M1_Lesson 1":
//                return e_Module_1_1.get_PreTest_Lesson1_Questions();
//            case "M2_Lesson 1":
//                return e_Module_1_2.get_PreTest_Lesson2_Questions();
//            case "M3_Lesson 1":
//                return e_Module_1_3.get_PreTest_Lesson3_Questions();
//            case "M4_Lesson 1":
//                return e_Module_1_4.get_PreTest_Lesson4_Questions();
//
//            /* ===== Module 2 ===== */
//            case "M1_Lesson 2":
//                return e_Module_2_1.get_PreTest_Lesson2_Questions();
//
//            /* ===== Module 3 ===== */
//            case "M1_Lesson 3":
//                return e_Module_3_1.get_PreTest_Lesson1_Questions();
//            case "M2_Lesson 3":
//                return e_Module_3_2.get_PreTest_Lesson2_Questions();
//            case "M3_Lesson 3":
////                return e_Module_3_3.get_PreTest_Lesson2_Questions();
//
//                /* ===== Module 4 ===== */
//            case "M1_Lesson 4":
//                return e_Module_4_1.get_PreTest_Lesson1_Questions();
//            case "M2_Lesson 4":
//                return e_Module_4_2.get_PreTest_Lesson2_Questions();
//            case "M3_Lesson 4":
////                return e_Module_4_3.get_PreTest_Lesson3_Q
//                /* ===== Module 5 ===== */
//            case "M1_Lesson 5":
//                return e_Module_5_1.get_PreTest_Lesson1_Questions();
//            case "M2_Lesson 5":
//                return e_Module_5_2.get_PreTest_Lesson2_Questions();
//            case "M3_Lesson 5":
//                return e_Module_5_3.get_PreTest_Lesson3_Questions();
//
//            /* ===== Module 6 ===== */
//            case "M1_Lesson 6":
//                return e_Module_6_1.get_PreTest_Lesson1_Questions();
//            case "M2_Lesson 6":
//                return e_Module_6_2.get_PreTest_Lesson2_Questions();
//            case "M3_Lesson 6":
//                return e_Module_6_3.get_PreTest_Lesson3_Questions();
//
//            /* ===== Module 7 ===== */
//            case "M1_Lesson 7":
//                return e_Module_7_1.get_PreTest_Questions();
//
//            /* ===== Module 8 ===== */
//            case "M1_Lesson 8":
//                return e_Module_8_1.get_PreTest_Lesson1_Questions();
//            case "M2_Lesson 8":
//                return e_Module_8_2.get_PreTest_Lesson2_Questions();
//            case "M3_Lesson 8":
//                return e_Module_8_3.get_PreTest_Lesson3_Questions();
//
//            default:
//                throw new IllegalArgumentException("Invalid module or lesson: " + key);
//        }
//    }



    private void loadQuestion() {

        // Log.e("loadQuestion", "loadQuestion();");

        d_Lesson_container.startCountdown(requireContext(), "Pre-Test", questionsAnswered >= (preTestQuestions + 1));

        isCorrect = false;

        // Clear previous selection
        choicesGroup.clearCheck();

        // Ensure that questions[currentQuestionIndex] is valid
        if (questions != null && questions.length > currentQuestionIndex) {
            e_Question currentQuestion = questions[currentQuestionIndex];
            questionText.setText(currentQuestion.getQuestion());

            choicesGroup.removeAllViews();

            // Retrieve the choices, and handle the case where choices might be null
            List<String> choices = currentQuestion.getChoices();

            // Example logging to check choices
            Log.e("e_Question", "Creating question with choices: " + choices);

            // Check if choices is null or empty
            if (choices == null || choices.isEmpty()) {
                // Log an error or show a fallback message
                Log.e("loadQuestion", "Choices list is null or empty.");
                // Optionally, you could set a fallback UI or exit the method early
                return;  // Exit early if choices are unavailable
            }

            // Validate context before creating RadioButton instances
            Context context = getContext();
            if (context == null) {
                // Log.e("f_pre_test", "Context is null, cannot create RadioButtons");
                return;  // Exit early if context is null to prevent a crash
            }

            Typeface customFont = ResourcesCompat.getFont(context, R.font.garfiey);

            // Convert 8dp to pixels
            int paddingInDp = 12;
            int paddingInPx = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, paddingInDp, context.getResources().getDisplayMetrics()
            );

            for (int i = 0; i < choices.size(); i++) {
                Button choiceButton = new Button(context);
                choiceButton.setId(i); // Set the ID to match the choice index
                choiceButton.setText(choices.get(i));
                choiceButton.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
                choiceButton.setBackground(getResources().getDrawable(R.drawable.rounded_corners));
                choiceButton.setTextColor(getResources().getColor(R.color.black));
                choiceButton.setTextSize(12);
                choiceButton.setPadding(paddingInPx, paddingInPx, paddingInPx, paddingInPx);
                choiceButton.setEnabled(false);
                choiceButton.setVisibility(View.GONE);
//                choiceButton.setTypeface(customFont);

                // Create LayoutParams for margin settings
                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.MATCH_PARENT,
                        RadioGroup.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 8, 0, 8);  // Set margins (left, top, right, bottom) in pixels
                choiceButton.setLayoutParams(params);  // Apply the margins to the RadioButton

                // Handle button click
                choiceButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer(choiceButton.getId());
                        choiceButton.setEnabled(false);
                    }
                });

                // Add button to the container (or choicesGroup if still used for layout)
                choicesGroup.addView(choiceButton);

            }

            // Delay for 2 seconds before making the choicesGroup visible
            new android.os.Handler().postDelayed(() -> {
                for (int i = 0; i < choicesGroup.getChildCount(); i++) {
                    View child = choicesGroup.getChildAt(i);
                    if (child instanceof Button) {
                        child.setEnabled(true);
                        child.setVisibility(View.VISIBLE);
                    }
                }
            }, 2000);


            // Create RadioButton for each choice
//            for (int i = 0; i < choices.size(); i++) {
//
//                choiceButton = new Button(context);
//                choiceButton.setId(i);
//                choiceButton.setPadding(16, 0, 0, 0);
//                choiceButton.setText(choices.get(i));
//                choiceButton.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
//                choiceButton.setTextColor(getResources().getColor(R.color.black));
//                choiceButton.setBackground(getResources().getDrawable(R.drawable.rounded_corners));
//                choiceButton.setTextSize(14);
//                choiceButton.setTypeface(customFont);
//                choiceButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        submitButton.performClick();
//                    }
//                });
//
//
//
//
////                RadioButton choiceButton = new RadioButton(context);
////                choiceButton.setId(i);
////                choiceButton.setPadding(16, 0, 0, 0);
////                choiceButton.setText(choices.get(i));
////                choiceButton.setTextColor(getResources().getColor(R.color.white));  // Set text color to white
////                choiceButton.setTextSize(16);  // Set text size to 18sp (you can adjust this size)
//
//                // Create LayoutParams for margin settings
//                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
//                        RadioGroup.LayoutParams.MATCH_PARENT,
//                        RadioGroup.LayoutParams.WRAP_CONTENT
//                );
//                params.setMargins(0, 8, 0, 8);  // Set margins (left, top, right, bottom) in pixels
//
//                choiceButton.setLayoutParams(params);  // Apply the margins to the RadioButton
//
//                choicesGroup.addView(choiceButton);
//            }
        } else {
            Log.e("loadQuestion", "Invalid question index or questions array is null.");
        }
    }

    public void submitButton(boolean correctAnswer) {

        if (questionsAnswered <= preTestQuestions) { // <= talaga to

//                    Log.e("TAG", "ROP CHECK THIS: | isLessonFinished: " + x_bkt_algorithm.isLessonFinished);

//                    if (!x_bkt_algorithm.isLessonFinished)
//                        bktModel.updateScore(moduleIndex, lessonIndex, isProgressiveMode, correctAnswer, answerAttempt);

            if (!d_Lesson_container.isCompleted)
                bktModel.updateBKTScore(moduleIndex, lessonIndex, isProgressiveMode, correctAnswer, answerAttempt);

            // Check if we need to move to the next question based on attempts or correctness.
//            if (answerAttempt >= attemptChances || correctAnswer) {

                String TAG = "EVOPlus";

                c_Lesson_feedback.preTestAttemptAnswers++;

                Log.i(TAG, "Roadshow | preTestAttemptAnswers: " + c_Lesson_feedback.preTestAttemptAnswers);
                Log.i(TAG, "Roadshow | questionsAnswered: " + questionsAnswered);
                Log.i(TAG, "Roadshow | preTestQuestions: " + preTestQuestions);

                if (questionsAnswered <= preTestQuestions) {
                    currentQuestionIndex++;
                    loadQuestion(); // Always load the next question after updating the index
                    answerAttempt = 0;
                } else {
                    // Handle pre-test completion
                    preTestCompleteListener.onPreTestComplete(c_Lesson_feedback.preTestCorrectAnswers);
                }

//                if (currentQuestionIndex < questions.length - 1) {
//                    currentQuestionIndex++;
////                            Log.e(TAG, "currentQuestionIndex++; ["+currentQuestionIndex+"]");
//                } else {
//                    currentQuestionIndex = 0; // Reset for new round
//                    bktModel.logScores(); // Log scores at reset point
////                            Log.e(TAG, "reset currentQuestionIndex["+currentQuestionIndex+"]");
//                }

                questionsAnswered++; // Increment answered count
//                        Log.e(TAG, "increment questionsAnswered["+questionsAnswered+"]");

                // Load next question only if still within pre-test limits.
//                        Log.d(TAG, "if (correctAnswer["+correctAnswer+"] || currentQuestionIndex["+currentQuestionIndex
//                        + "] < preTestQuestions["+preTestQuestions+"] && answerAttempt["+answerAttempt+"] >= attemptChances["+attemptChances+"]");
                if (correctAnswer || currentQuestionIndex < preTestQuestions && answerAttempt >= attemptChances) {

                    if (!(questionsAnswered == (preTestQuestions+1)))
                        loadQuestion();

                    answerAttempt = 0;

                }

//            }

            // may plus 1 to kanina
            if (questionsAnswered == (preTestQuestions+1)) {
                Log.d("TESTING", "Pre-test complete!");

                // Handle completion of pre-test.
                if (preTestCompleteListener != null) {
                    Log.d("TESTING", "Pre-Test BKT Score: " + x_bkt_algorithm.getKnowledge());
                    Log.d("TESTING", "Pre-Test Score: " + c_Lesson_feedback.preTestCorrectAnswers);
                    double bktScore = x_bkt_algorithm.getKnowledge();

                    // Pre-Test BKT Score
                    x_bkt_algorithm.updateTestBKTScore(
                            isProgressiveMode,
                            moduleIndex, lessonIndex,
                            "Pre-Test",
                            bktScore
                    );

                    preTestCompleteListener.onPreTestComplete(
//                                    correctAnswer,
                            c_Lesson_feedback.preTestCorrectAnswers);
                    c_Lesson_feedback.printResult("Pre-Test");
                    d_Lesson_container.isPreTestComplete = true;

                    // Removed, wag na daw balikan pre-test :D
//                            loadQuestion();

                }
            }

        } else {

            String feedback = correctAnswer ? "Correct!" : "Incorrect :(";

            // Logic for after pre-test is complete
            Toast.makeText(getContext(), feedback, Toast.LENGTH_SHORT).show();

            // Allow answering questions without changing scores
            if (answerAttempt >= attemptChances || correctAnswer) {
                if (currentQuestionIndex < questions.length - 1) {
                    currentQuestionIndex++;
                } else {
                    currentQuestionIndex = 0; // Reset for new round
                }

                // Load next question without affecting score
                loadQuestion();
                answerAttempt = 0; // Reset attempts for new question
            }
        }



        choicesGroup.clearCheck(); // Clear selected choices at the end of processing.
//            } // end of getChecked == -1

        if (c_Lesson_feedback.preTestAttemptAnswers <= preTestQuestions)
            total.setText("Item: " + c_Lesson_feedback.preTestAttemptAnswers
                    + "/"
                    +  preTestQuestions);

    }

    public boolean checkAnswer(int selectedId) {

        // Ensure the question exists
        if (questions != null && questions.length > currentQuestionIndex) {
            e_Question currentQuestion = questions[currentQuestionIndex];

            int correctAnswer = currentQuestion.getCorrectAnswer_EASY_MEDIUM();
            // Check if the selectedId matches the correct answer
            isCorrect = (selectedId == correctAnswer);

            // Determine feedback and update scores
            String toastMessage;
            if (isCorrect) {
                toastMessage = "Correct answer!";
                if (!x_bkt_algorithm.isLessonFinished) {
                    if (!d_Lesson_container.isCompleted) {
                        x_bkt_algorithm.updateTestScore(
                                isProgressiveMode,
                                moduleIndex, lessonIndex,
                                "Pre-Test",
                                c_Lesson_feedback.preTestCorrectAnswers);
                    }
                }
                c_Lesson_feedback.preTestCorrectAnswers = Math.min(c_Lesson_feedback.preTestCorrectAnswers + 1, preTestQuestions);
            } else {
                toastMessage = "Incorrect answer.";
                incorrect++;
            }

            // Display feedback to the user
            Toast.makeText(getContext(), toastMessage, Toast.LENGTH_SHORT).show();

            // Update UI, such as marking the answer as correct/incorrect
            updateAnswerFeedback(isCorrect);
            updateChoiceButton(isCorrect, selectedId, currentQuestion.getCorrectAnswer_preTest());

            return isCorrect;

        } else {
            Toast.makeText(getContext(), "No question available.", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private void setButtonBackgroundWithColor(Button button, int color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color); // Set the desired color
        drawable.setCornerRadius(40); // Adjust for your rounded corners
        button.setBackground(drawable); // Apply the drawable to the button
    }


    private void updateChoiceButton(boolean isCorrect, int selectedId, int correctId) {
        // Find the selected and correct buttons
        Button selectedButton = choicesGroup.findViewById(selectedId);
        Button correctButton = choicesGroup.findViewById(correctId);

        // Set colors for feedback
        if (selectedButton != null) {
            setButtonBackgroundWithColor(selectedButton, Color.RED);
//            selectedButton.setBackgroundResource(R.drawable.rounded_corners);
//            selectedButton.setBackgroundColor(Color.RED); // Incorrect choice turns red
//            selectedButton.setTextColor(Color.WHITE);    // Adjust text color for visibility
        }

        if (correctButton != null) {
            setButtonBackgroundWithColor(correctButton, Color.GREEN);
//            correctButton.setBackgroundResource(R.drawable.rounded_corners);
//            correctButton.setBackgroundColor(Color.GREEN); // Correct choice turns green
//            correctButton.setTextColor(Color.WHITE);       // Adjust text color for visibility
        }

        // Delay to reset the buttons
        new Handler().postDelayed(() -> {
            if (selectedButton != null) {
                selectedButton.setBackgroundResource(R.drawable.rounded_corners); // Reset to original background
                selectedButton.setTextColor(getResources().getColor(R.color.black)); // Reset text color
            }

            if (correctButton != null) {
                correctButton.setBackgroundResource(R.drawable.rounded_corners); // Reset to original background
                correctButton.setTextColor(getResources().getColor(R.color.black)); // Reset text color
            }

            submitButton(isCorrect);
        }, 2000); // Delay of 2 seconds


    }


    private void updateAnswerFeedback(boolean isCorrect) {

        String targetTag = "testAnswer_" + c_Lesson_feedback.preTestAttemptAnswers;
        ImageView targetImageView = imageContainer.findViewWithTag(targetTag);

        if (targetImageView != null) {
            if (isCorrect) {
                targetImageView.setImageResource(R.drawable.test_answer_check);
            } else {
                targetImageView.setImageResource(R.drawable.test_answer_cross);
            }
        }
    }



//    public boolean checkAnswer() {
//
//        int selectedId == choiceButton.getsele
////        int selectedId = choicesGroup.getCheckedRadioButtonId();
//
//        if (selectedId != -1) {
//            e_Question currentQuestion = questions[currentQuestionIndex];
//            isCorrect = (selectedId == currentQuestion.getCorrectAnswer_preTest());
//
//            // Determine the background color based on correctness
//            int backgroundColor;
//            String toastMessage;
//
//            if (!isCorrect) {
//                backgroundColor = Color.RED;
//                toastMessage = "Incorrect answer.";
//                incorrect++;
//            } else {
//                backgroundColor = Color.GREEN;
//                toastMessage = "Correct answer!";
//            }
//
//            // Update feedback and scores based on correctness
//            if (isCorrect) {
////                if (!x_bkt_algorithm.isLessonFinished) {
//                if (!d_Lesson_container.isCompleted) {
//                    x_bkt_algorithm.updateTestScore(
//                            isProgressiveMode,
//                            moduleIndex, lessonIndex,
//                            "Pre-Test",
//                            c_Lesson_feedback.preTestCorrectAnswers);
//                }
//                c_Lesson_feedback.preTestCorrectAnswers
//                        = Math.min(
//                        c_Lesson_feedback.preTestCorrectAnswers + 1, preTestQuestions);
//                Log.i("TAG", "CONSUMPTION | Pre-Test Score: " + c_Lesson_feedback.preTestCorrectAnswers);
//
//            }
//
//            if (answerAttempt >= attemptChances || isCorrect) {
//                // Construct the tag based on currentQuestionIndex
//                String targetTag = "testAnswer_" + c_Lesson_feedback.preTestAttemptAnswers;
//                Log.d("TagCheck", "Looking for ImageView with tag: " + targetTag);
//
//                // Find the ImageView with the matching tag
//                ImageView targetImageView = imageContainer.findViewWithTag(targetTag);
//
//                // Check if the ImageView exists, then update its resource
//                if (targetImageView != null) {
//                    if (isCorrect) {
//                        targetImageView.setImageResource(R.drawable.test_answer_check);
//                    } else {
//                        targetImageView.setImageResource(R.drawable.test_answer_cross);
//                    }
//                } else {
////                    Log.e("ImageViewError", "No ImageView found with tag: " + targetTag);
//                }
//
//                // Add a value in the database,
//
//            }
//
//            // Create a GradientDrawable for rounded corners
//            GradientDrawable drawable = new GradientDrawable();
//            drawable.setColor(backgroundColor);
//            drawable.setCornerRadius(40); // Adjust this value for corner radius
//            submitButton.setBackground(drawable);
//            submitButton.setTextColor(Color.WHITE);
//
//            // Create a Handler to revert the colors after 1 second (1000 milliseconds)
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    // Revert to original colors
//                    submitButton.setBackgroundResource(R.drawable.rounded_corners); // Restore original drawable
//                    submitButton.setTextColor(Color.BLACK); // Restore original text color
//                }
//            }, 1000);
//
//            Toast.makeText(getContext(), toastMessage, Toast.LENGTH_SHORT).show();
//            d_Lesson_container.startCountdown(requireContext(), "Pre-Test", questionsAnswered >= (preTestQuestions + 1));
//
//        } else {
//            Toast.makeText(getContext(), "Please select an answer.", Toast.LENGTH_SHORT).show();
//            d_Lesson_container.startCountdown(requireContext(), "Pre-Test", questionsAnswered >= (preTestQuestions + 1));
//        }
//        return isCorrect; // Return whether the answer was correct or not
//    }

    // Helper methods to get module and lesson indices
    private int getModuleIndex(String module) {
        switch (module) {
            case "M1": return 0;
            case "M2": return 1;
            case "M3": return 2;
            case "M4": return 3;
            // Add more cases as needed
            default: throw new IllegalArgumentException("Invalid module: " + module);
        }
    }

    private int getLessonIndex(String lesson) {
        switch (lesson) {
            case "Lesson 1": return 0;
            case "Lesson 2": return 1;
            case "Lesson 3": return 2;
            case "Lesson 4": return 3;
            case "Lesson 5": return 4;
            case "Lesson 6": return 5;
            case "Lesson 7": return 6;
            case "Lesson 8": return 7;
            // Add more cases as needed
            default:
                //Log.e("getLessonIndex", "Invalid lesson: " + lesson);
                throw new IllegalArgumentException("Invalid lesson: " + lesson);
        }
    }
}