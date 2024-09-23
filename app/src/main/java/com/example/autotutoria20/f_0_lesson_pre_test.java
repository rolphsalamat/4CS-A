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

public class f_0_lesson_pre_test extends Fragment {

    private static final String ARG_MODULE = "module";
    private static final String ARG_LESSON = "lesson";
    private static final String ARG_MODE = "mode";

    private int currentQuestionIndex = 0;
    private e_Question[] questions;
    private TextView questionText;
    private RadioGroup choicesGroup;
    private int answerAttempt = 0;
    private int attemptChances = 2;
    private int preTestQuestions = 5;
    private Button submitButton;
    private boolean isCorrect = false;
    private boolean isProgressiveMode = true; // Default to Progressive Mode

    private PreTestCompleteListener preTestCompleteListener;

    // BKT Model instance
    private x_bkt_algorithm bktModel;

    // Interface to notify the container activity when pre-test is complete
    public interface PreTestCompleteListener {
        void onPreTestComplete(boolean isCorrect);
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
        bktModel.initializeBKTScores(collectionPath, documentName, bktScores -> {
            if (bktScores != null) {
                Log.d("f_pre_test", "BKT Scores initialized: " + bktScores);
            } else {
                Log.e("f_pre_test", "Failed to retrieve BKT Scores");
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("FragmentLifecycle", "onCreateView");
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

        if (getArguments() != null) {
            String module = getArguments().getString(ARG_MODULE);
            String lesson = getArguments().getString(ARG_LESSON);

            Log.e("f_pre_test.java", "module: " + module);
            Log.e("f_pre_test.java", "lesson: " + lesson);

            // Retrieve questions based on module and lesson
            questions = getPreTestQuestions(module, lesson);

            // Load the first question
            loadQuestion();
        }

        submitButton.setOnClickListener(v -> {

            if (!(choicesGroup.getCheckedRadioButtonId() == -1)) {

                answerAttempt++;

                c_Lesson_feedback.preTestAttemptAnswers++;

                // n <= 5
                if (answerAttempt <= preTestQuestions) {

                    boolean correctAnswer = checkAnswer();
                    if (correctAnswer) c_Lesson_feedback.preTestCorrectAnswers++;

//                    // Update BKT model with the result of the answer
//                    bktModel.updateKnowledge(correctAnswer);

                    // Log the updated knowledge probability
                    double knowledgeProb = bktModel.getKnowledgeProbability();
                    Log.e("submitButton.onClick", "Updated Knowledge Probability: " + knowledgeProb);

                    // Ensure valid indices are used
                    int moduleIndex = getModuleIndex(getArguments().getString(ARG_MODULE));
                    int lessonIndex = getLessonIndex(getArguments().getString(ARG_LESSON));

                    if (moduleIndex < 0 || lessonIndex < 0) {
                        Log.e("submitButton.onClick", "Invalid module or lesson index");
                        return;
                    }


                    // Update the BKT Score of the module, lesson, and mode of the user
                    bktModel.updateScore(moduleIndex, lessonIndex,
                            knowledgeProb,
                            isProgressiveMode,
                            correctAnswer);

                    if (correctAnswer)
                        x_bkt_algorithm.updateTestScore(
                                isProgressiveMode,
                                moduleIndex, lessonIndex,
                                "Pre-Test",
                                c_Lesson_feedback.preTestCorrectAnswers);

                    String TAG = "TESTING";

                    Log.d(TAG, "answerAttempt: " + answerAttempt);
                    Log.d(TAG, "attemptChances: " + attemptChances);

                    // Check if we need to move to the next question
                    if (answerAttempt >= attemptChances || correctAnswer) {
                        Log.e(TAG, "currentQuestionIndex(" + currentQuestionIndex + ") < questions.length - 1(" + (questions.length - 1) + ")");
                        Log.e(TAG, "questions.length: " + questions.length);

                        // Move to the next question or reset if all questions are answered
                        if (currentQuestionIndex < questions.length - 1) {
                            currentQuestionIndex++;
                            Log.e(TAG, "currentQuestionIndex++;" + currentQuestionIndex);
                        } else {
                            currentQuestionIndex = 0; // Reset to the first question if all are answered
                            // Optionally, you might want to show a message to the user here
                            // Toast.makeText(getContext(), "Pre-test completed!", Toast.LENGTH_SHORT).show();
                            bktModel.logScores();
                        }
                    }

                    Log.e(TAG, "currentQuestionIndex("+currentQuestionIndex+") == " + preTestQuestions + "?");
                    if (currentQuestionIndex < preTestQuestions) {
                        // to give student chance to get correct answer before loading another question
                        if (answerAttempt >= attemptChances) {

                            Log.e(TAG, "Attempt Chances exceeded!");

                            loadQuestion(); // Load the next question
                            answerAttempt = 0;

                        }
                    }
                    else {
                        Log.e(TAG,"YES!! TAPOS NA YUNG PRE TEST!");
                        if (preTestCompleteListener != null) {
                            Log.e(TAG,"FINISH!!!");
                            preTestCompleteListener.onPreTestComplete(correctAnswer);
                            c_Lesson_feedback.printResult();
                        }
                    }
                    choicesGroup.clearCheck();
                }
            }
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

        Log.e("loadQuestion", "loadQuestion();");

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
            Log.e("f_pre_test", "Context is null, cannot create RadioButtons");
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
                Toast.makeText(getContext(), "Incorrect answer.", Toast.LENGTH_SHORT).show();
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
                Log.e("getLessonIndex", "Invalid lesson: " + lesson);
                throw new IllegalArgumentException("Invalid lesson: " + lesson);
        }
    }
}
