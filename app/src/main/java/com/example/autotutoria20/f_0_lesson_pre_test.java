package com.example.autotutoria20;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.imageview.ShapeableImageView;

public class f_0_lesson_pre_test extends Fragment {

    private static final String ARG_MODULE = "module";
    private static final String ARG_LESSON = "lesson";
    private static final String ARG_MODE = "mode";

    private int currentQuestionIndex = 0;
    private e_Question[] questions;
    private TextView questionText;
    private int incorrect = 0;
    private RadioGroup choicesGroup;
    private int answerAttempt = 0;
    private int attemptChances = 2;
    private int questionsAnswered = 1;
    private int preTestQuestions = 5;
    private Button submitButton;
    private TextView correct, mistake, total;
    private boolean isCorrect = false;
    public static ShapeableImageView currentButton;
    private boolean isProgressiveMode = true; // Default to Progressive Mode

    private PreTestCompleteListener preTestCompleteListener;

    // BKT Model instance
    private x_bkt_algorithm bktModel;

    // Interface to notify the container activity when pre-test is complete
    public interface PreTestCompleteListener {
//        void onPreTestComplete(boolean isCorrect);
        void onPreTestComplete(boolean isCorrect, int score);
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

//        bktModel.initializeBKTScores(collectionPath, documentName, bktScores -> {
//            if (bktScores != null) {
//                Log.d("f_pre_test", "BKT Scores initialized: " + bktScores);
//            } else {
//                Log.e("f_pre_test", "Failed to retrieve BKT Scores");
//            }
//        });

        bktModel.initializeBKTScores(collectionPath, documentName,
                module,
                bktScores -> {
            if (bktScores != null) {
                Log.d("f_pre_test", "BKT Scores initialized: " + bktScores);
            } else {
                Log.e("f_pre_test", "Failed to retrieve BKT Scores");
            }
        });

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

        questionText = view.findViewById(R.id.question_text);
        choicesGroup = view.findViewById(R.id.choices_group);
        submitButton = view.findViewById(R.id.submit_pre_test);
        currentButton = view.findViewById(R.id.currentButton);
//        correct = view.findViewById(R.id.answers_correct);
//        mistake = view.findViewById(R.id.answers_wrong);
        total = view.findViewById(R.id.answers_total);

        if (!d_Lesson_container.isPreTestComplete) {
            currentButton.setEnabled(false);
            currentButton.setVisibility(View.GONE);
        } else {
            currentButton.setEnabled(true);
            currentButton.setVisibility(View.VISIBLE);
        }

        // Ensure valid indices are used
        int moduleIndex = getModuleIndex(getArguments().getString(ARG_MODULE));
        int lessonIndex = getLessonIndex(getArguments().getString(ARG_LESSON));

        if (moduleIndex < 0 || lessonIndex < 0) {
            Log.e("submitButton.onClick", "Invalid module or lesson index");
            return;
        }

        x_bkt_algorithm.updateTestScore(
                isProgressiveMode,
                moduleIndex, lessonIndex,
                "Pre-Test",
                0); // reset the pre-test :D

        c_Lesson_feedback.resetResult();

        currentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { d_Lesson_container.onGoToCurrent(); }
        });

        if (getArguments() != null) {
            String module = getArguments().getString(ARG_MODULE);
            String lesson = getArguments().getString(ARG_LESSON);

            //Log.e("f_pre_test.java", "module: " + module);
            //Log.e("f_pre_test.java", "lesson: " + lesson);

            // Retrieve questions based on module and lesson
            questions = getPreTestQuestions(module, lesson);

            // Load the first question
            loadQuestion();
        }

        submitButton.setOnClickListener(v -> {

            Log.e("HEY!", "Submit Button Clicked!");

//            if (d_Lesson_container.isPreTestComplete)
//                d_Lesson_container.onGoToCurrent();

            if (!(choicesGroup.getCheckedRadioButtonId() == -1)) {

                // Dito originally yung answerAttempt++;
                answerAttempt++;

                boolean correctAnswer = checkAnswer(); // Check if the answer is correct

                Log.e("HEY!", "questionsAnswered("+questionsAnswered+") < preTestQuestions("+(preTestQuestions)+")");
                // Ensure that questions answered does not exceed total pre-test questions.
                if (questionsAnswered <= preTestQuestions) {

                    // Update feedback and scores based on correctness
                    if (correctAnswer) {
                        c_Lesson_feedback.preTestCorrectAnswers++;
                        x_bkt_algorithm.updateTestScore(
                                isProgressiveMode,
                                moduleIndex, lessonIndex,
                                "Pre-Test",
                                c_Lesson_feedback.preTestCorrectAnswers);
//                        correct.setText("Correct Answers: " + c_Lesson_feedback.preTestCorrectAnswers);
                    }

                    // Update knowledge probability and score based on current answer.
                    double knowledgeProb = bktModel.getKnowledgeProbability();
                    bktModel.updateScore(moduleIndex, lessonIndex, knowledgeProb, isProgressiveMode, correctAnswer);

                    Log.d("TESTING", "Answer: " + correctAnswer);

                    // Check if we need to move to the next question based on attempts or correctness.
                    if (answerAttempt >= attemptChances || correctAnswer) {

                        String TAG = "EVOPlus";

                        c_Lesson_feedback.preTestAttemptAnswers++;

                        // Move to next question or reset if all have been answered.
                        Log.d(TAG, "currentQuestionInddex["+currentQuestionIndex+"] < questions.length-1["+(questions.length-1)+"])");
                        if (currentQuestionIndex < questions.length - 1) {
                            currentQuestionIndex++;
                            Log.e(TAG, "currentQuestionIndex++; ["+currentQuestionIndex+"]");
                        } else {
                            currentQuestionIndex = 0; // Reset for new round
                            bktModel.logScores(); // Log scores at reset point
                            Log.e(TAG, "reset currentQuestionIndex["+currentQuestionIndex+"]");
                        }

                        questionsAnswered++; // Increment answered count
                        Log.e(TAG, "increment questionsAnswered["+questionsAnswered+"]");

                        // Load next question only if still within pre-test limits.
                        Log.d(TAG, "if (correctAnswer["+correctAnswer+"] || currentQuestionIndex["+currentQuestionIndex
                        + "] < preTestQuestions["+preTestQuestions+"] && answerAttempt["+answerAttempt+"] >= attemptChances["+attemptChances+"]");
                        if (correctAnswer || currentQuestionIndex < preTestQuestions && answerAttempt >= attemptChances) {
                            loadQuestion(); // Load next question
                            Log.e(TAG, "loadQuestion();");
                            answerAttempt = 0; // Reset attempts for new question
                            Log.e(TAG, "reset answerAttempt["+answerAttempt+"]");
                        }
                    } else {

                        Log.d("TESTING", "Not moving to next question yet.");

                        int mistakes = 0;
                        mistakes = c_Lesson_feedback.preTestAttemptAnswers - c_Lesson_feedback.preTestCorrectAnswers;

//                        mistake.setText("Incorrect Answers: " + mistakes);
                    }

                    if (questionsAnswered == (preTestQuestions+1)) {
                        Log.d("TESTING", "Pre-test complete!");

                        // Handle completion of pre-test.
                        if (preTestCompleteListener != null) {
                            preTestCompleteListener.onPreTestComplete(correctAnswer, c_Lesson_feedback.preTestCorrectAnswers);
                            c_Lesson_feedback.printResult("Pre-Test");
                            d_Lesson_container.isPreTestComplete = true;

                            loadQuestion(); // Load a different question for user return
                        }
                    }

                }

                choicesGroup.clearCheck(); // Clear selected choices at the end of processing.
            }

            total.setText("Score: " + c_Lesson_feedback.preTestCorrectAnswers
                    + "/"
                    + c_Lesson_feedback.preTestAttemptAnswers);

        });
    }

    private e_Question[] getPreTestQuestions(String module, String lesson) {
        String key = module + "_" + lesson;

        switch (key) {
            /* ===== Module 1 ===== */
            case "M1_Lesson 1":
                return e_Module_1_1.get_PreTest_Lesson1_Questions();
            case "M2_Lesson 1":
                return e_Module_1_2.get_PreTest_Lesson2_Questions();
            case "M3_Lesson 1":
                return e_Module_1_3.get_PreTest_Lesson3_Questions();
            case "M4_Lesson 1":
                return e_Module_1_4.get_PreTest_Lesson4_Questions();

            /* ===== Module 2 ===== */
            case "M1_Lesson 2":
                return e_Module_2_1.get_PreTest_Lesson2_Questions();

            /* ===== Module 3 ===== */
            case "M1_Lesson 3":
                return e_Module_3_1.get_PreTest_Lesson1_Questions();
            case "M2_Lesson 3":
                return e_Module_3_2.get_PreTest_Lesson2_Questions();
            case "M3_Lesson 3":
//                return e_Module_3_3.get_PreTest_Lesson2_Questions();

                /* ===== Module 4 ===== */
            case "M1_Lesson 4":
                return e_Module_4_1.get_PreTest_Lesson1_Questions();
            case "M2_Lesson 4":
                return e_Module_4_2.get_PreTest_Lesson2_Questions();
            case "M3_Lesson 4":
//                return e_Module_4_3.get_PreTest_Lesson3_Q
                /* ===== Module 5 ===== */
            case "M1_Lesson 5":
                return e_Module_5_1.get_PreTest_Lesson1_Questions();
            case "M2_Lesson 5":
                return e_Module_5_2.get_PreTest_Lesson2_Questions();
            case "M3_Lesson 5":
                return e_Module_5_3.get_PreTest_Lesson3_Questions();

            /* ===== Module 6 ===== */
            case "M1_Lesson 6":
                return e_Module_6_1.get_PreTest_Lesson1_Questions();
            case "M2_Lesson 6":
                return e_Module_6_2.get_PreTest_Lesson2_Questions();
            case "M3_Lesson 6":
                return e_Module_6_3.get_PreTest_Lesson3_Questions();

            /* ===== Module 7 ===== */
            case "M1_Lesson 7":
                return e_Module_7_1.get_PreTest_Questions();

            /* ===== Module 8 ===== */
            case "M1_Lesson 8":
                return e_Module_8_1.get_PreTest_Lesson1_Questions();
            case "M2_Lesson 8":
                return e_Module_8_2.get_PreTest_Lesson2_Questions();
            case "M3_Lesson 8":
                return e_Module_8_3.get_PreTest_Lesson3_Questions();

            default:
                throw new IllegalArgumentException("Invalid module or lesson: " + key);
        }
    }

    private void loadQuestion() {

        //Log.e("loadQuestion", "loadQuestion();");

        isCorrect = false;

        // Clear previous selection
        choicesGroup.clearCheck();

        e_Question currentQuestion = questions[currentQuestionIndex];
        questionText.setText(currentQuestion.getQuestion());

        choicesGroup.removeAllViews();
        String[] choices = currentQuestion.getChoices();

        // Validate context before creating RadioButton instances
        Context context = getContext();
        if (context == null) {
            //Log.e("f_pre_test", "Context is null, cannot create RadioButtons");
            return;  // Exit early if context is null to prevent a crash
        }

        for (int i = 0; i < choices.length; i++) {
            RadioButton choiceButton = new RadioButton(context);
            choiceButton.setId(i);
            choiceButton.setPadding(16, 0, 0, 0);
            choiceButton.setText(choices[i]);
            choiceButton.setTextColor(getResources().getColor(R.color.white));  // Set text color to white
            choiceButton.setTextSize(16);  // Set text size to 18sp (you can adjust this size)

            // Create LayoutParams for margin settings
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 8, 0, 8);  // Set margins (left, top, right, bottom) in pixels

            choiceButton.setLayoutParams(params);  // Apply the margins to the RadioButton

            choicesGroup.addView(choiceButton);
        }
    }


    public boolean checkAnswer() {
        int selectedId = choicesGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            e_Question currentQuestion = questions[currentQuestionIndex];
            isCorrect = (selectedId == currentQuestion.getCorrectAnswer_preTest());

            if (!isCorrect) {

                if (answerAttempt == attemptChances) incorrect++;

                Toast.makeText(getContext(), "Incorrect answer.", Toast.LENGTH_SHORT).show();
//                mistake.setText("Incorrect Answers: " + incorrect);
            }
            return isCorrect;  // Return if the answer is correct
        } else {
            Toast.makeText(getContext(), "Please select an answer.", Toast.LENGTH_SHORT).show();
            return false;  // No answer selected
        }
    }

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