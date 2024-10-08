package com.example.autotutoria20;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class f_3_lesson_post_test extends Fragment {

    private static final String ARG_MODULE = "module";
    private static final String ARG_LESSON = "lesson";
    private static final String ARG_MODE = "mode";

    private int currentQuestionIndex = 0;
    private e_Question[] questions;
    private TextView questionText;
    private RadioGroup choicesGroup;
    private EditText identificationAnswer;
    private TextView total;
    private Button submitButton;
    private e_Question.Difficulty difficultyLevel;
    private int answerAttempt = 0;
    private int attemptChances = 2;
    private int questionsAnswered = 1;
    private int postTestQuestions = 10;
    private boolean isCorrect = false;
    private boolean isProgressiveMode = true; // Default to Progressive Mode

    private PostTestCompleteListener postTestCompleteListener;

    // BKT Model instance
    private x_bkt_algorithm bktModel;

    // Interface to notify the container activity when post-test is complete
    public interface PostTestCompleteListener {
        void onPostTestComplete(boolean isCorrect, double knowledgeProb);
    }

    public static f_3_lesson_post_test newInstance(String module, String lesson, String mode) {
        f_3_lesson_post_test fragment = new f_3_lesson_post_test();
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
        if (context instanceof PostTestCompleteListener) {
            postTestCompleteListener = (PostTestCompleteListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement PostTestCompleteListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        postTestCompleteListener = null; // Avoid memory leaks
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isCorrect", isCorrect);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            isCorrect = savedInstanceState.getBoolean("isCorrect", false);
        }
        return inflater.inflate(R.layout.f_3_lesson_post_test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        questionText = view.findViewById(R.id.post_test_question);
        choicesGroup = view.findViewById(R.id.choices_group);
        submitButton = view.findViewById(R.id.submit_post_test);
        identificationAnswer = view.findViewById(R.id.identification_answer);
        total = view.findViewById(R.id.answers_total);

//        // DAPAT MAG-IBA TO DEPENDE SA DIFFICULTY...
//        // Initialize BKT Model instance with appropriate parameters
        bktModel = x_bkt_algorithm.getInstance(0.3, 0.2, 0.1, 0.4);

//         dapat ma move to kasi nag n null yung bktModel

        // Retrieve arguments passed from the parent container
        if (getArguments() != null) {

            String TAG = "GWENCHANA";

            Log.e(TAG, "PASOK NA ROP");

            String module = getArguments().getString(ARG_MODULE);
            String lesson = getArguments().getString(ARG_LESSON);

            // Determine if it's Progressive Mode
            isProgressiveMode = getArguments().getString(ARG_MODE).equals("Progressive Mode");

            int lessonNumber = getLessonIndex(lesson);
            int moduleNumber = getModuleIndex(module);

            // Initialize BKT Scores
            String collectionPath = isProgressiveMode ? "Progressive Mode" : "Free Use Mode";
            String documentName = "Lesson " + (lessonNumber + 1);
            String mod = "M" + (moduleNumber + 1);

//            Log.e(TAG, "Try natin pumasok sa initializaBKTScores()");
//            bktModel.initializeBKTScores(collectionPath, documentName, bktScores -> {
//                Log.e(TAG, "ABDUL POST TEST INITIALIZE BKT SCORE YEHEY");
//            });

//            try {
//                bktModel.initializeBKTScores(collectionPath, documentName, mod, bktScores -> {
//                    Log.e(TAG, "NAKAPASOK AKO");
//
//                    // Extract the module number from the string (M1, M2, etc.)
//                    char moduleChar = module.charAt(1);  // Get the second character, e.g., '1' from "M1"
//                    Log.e(TAG, "moduleChar: " + moduleChar);
//
//                    // Convert it to an integer and decrement by 1 to get the zero-based index
//                    int moduleIndex = Character.getNumericValue(moduleChar) - 1; // Adjust for zero-based index
//                    Log.e(TAG, "moduleIndex: " + moduleIndex);
//
//                    // Ensure the moduleIndex is within valid range
//                    if (moduleIndex >= 0 && moduleIndex < bktScores.size()) {
//                        double userScore = bktScores.get(moduleIndex);  // Use the moduleIndex to get the correct score
//                        Log.e("f_post_test", "bktScores: " + bktScores.toString());
//                        Log.d("f_post_test", "User BKT Score for Module " + module + ": " + userScore);
//
//                        // Determine difficulty based on score
//                        difficultyLevel = bktModel.getDifficultyLevel(userScore);
//                        Log.d("f_post_test", "Determined Difficulty Level: " + difficultyLevel);
//
//                        // Set attempt chances based on difficulty level
//                        if (difficultyLevel == e_Question.Difficulty.EASY)
//                            attemptChances = 1;
//                        else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
//                            attemptChances = 2;
//                        else if (difficultyLevel == e_Question.Difficulty.HARD)
//                            attemptChances = 3;
//
//                        Log.e("f_post_test", "dahil " + difficultyLevel + " ang difficulty, gawin nating " + attemptChances + " ang chances");
//
//                        bktModel.setBKTParameters(difficultyLevel);
//
//                        Log.e(TAG, "I will pass these:");
//                        Log.e(TAG, "module: " + module);
//                        Log.e(TAG, "lesson: " + lesson);
//
//                        // Retrieve post-test questions based on difficulty level
//                        questions = getPostTestQuestionsBasedOnDifficulty(module, lesson, difficultyLevel);
//                        loadQuestion();
//                    } else {
//                        Log.e("f_post_test", "Invalid moduleIndex: " + moduleIndex + ", cannot retrieve BKT score.");
//                    }
//                });
//
//            } catch (Exception e) {

                double userScore = x_bkt_algorithm.getKnowledge();

                // Determine difficulty based on score
                difficultyLevel = bktModel.getDifficultyLevel(userScore);
                Log.d("f_post_test", "Determined Difficulty Level: " + difficultyLevel);

                // Set attempt chances based on difficulty level
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    attemptChances = 1;
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    attemptChances = 2;
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    attemptChances = 3;

                Log.e("f_post_test", "dahil " + difficultyLevel + " ang difficulty, gawin nating " + attemptChances + " ang chances");

                bktModel.setBKTParameters(difficultyLevel);

                Log.e(TAG, "I will pass these:");
                Log.e(TAG, "module: " + module);
                Log.e(TAG, "lesson: " + lesson);

                // Retrieve post-test questions based on difficulty level
                questions = getPostTestQuestionsBasedOnDifficulty(module, lesson, difficultyLevel);
                loadQuestion();

//                throw new RuntimeException(e);
//            }



            Log.e(TAG, "wala :( loadQuestion nalang :(");
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

                // Ensure valid indices are used
                int moduleIndex = getModuleIndex(getArguments().getString(ARG_MODULE));
                int lessonIndex = getLessonIndex(getArguments().getString(ARG_LESSON));

                if (moduleIndex < 0 || lessonIndex < 0) {
                    Log.e("submitButton.onClick", "Invalid module or lesson index");
                    return;
                }

                Log.e("HEY!", "questionsAnswered("+questionsAnswered+") < preTestQuestions("+(postTestQuestions)+")");
                // Ensure that questions answered does not exceed total pre-test questions.
                if (questionsAnswered < (postTestQuestions)) {

                    // Update feedback and scores based on correctness
                    if (correctAnswer) {
                        c_Lesson_feedback.postTestCorrectAnswers++;
                        x_bkt_algorithm.updateTestScore(
                                isProgressiveMode,
                                moduleIndex, lessonIndex,
                                "Pre-Test",
                                c_Lesson_feedback.postTestCorrectAnswers);
                    }

                    // Update knowledge probability and score based on current answer.
                    double knowledgeProb = bktModel.getKnowledgeProbability();
                    bktModel.updateScore(moduleIndex, lessonIndex, knowledgeProb, isProgressiveMode, correctAnswer);

                    Log.d("TESTING", "Answer: " + correctAnswer);

                    // Check if we need to move to the next question based on attempts or correctness.
                    if (answerAttempt >= attemptChances || correctAnswer) {

                        String TAG = "EVOPlus";

                        c_Lesson_feedback.postTestAttemptAnswers++;

                        // Move to next question or reset if all have been answered.
                        Log.d(TAG, "currentQuestionInddex["+currentQuestionIndex+"] < questions.length-1["+(questions.length-1)+"])");
                        if (currentQuestionIndex < questions.length - 1) {
                            currentQuestionIndex++;
                            Log.e(TAG, "currentQuestionIndex++; ["+currentQuestionIndex+"]");
                        } else {
                            currentQuestionIndex = 0; // Reset for new round
                            bktModel.logScores(); // Log scores at reset point
                            Log.e(TAG, "reset currentQuestionIndex["+currentQuestionIndex+"]");
                            Log.e(TAG, "reset currentQuestionIndex["+currentQuestionIndex+"]");
                        }

                        questionsAnswered++; // Increment answered count
                        Log.e(TAG, "increment questionsAnswered["+questionsAnswered+"]");

                        // Load next question only if still within pre-test limits.
                        Log.d(TAG, "if (correctAnswer["+correctAnswer+"] || currentQuestionIndex["+currentQuestionIndex
                                + "] < postTestQuestions["+postTestQuestions+"] && answerAttempt["+answerAttempt+"] >= attemptChances["+attemptChances+"]");
                        if (correctAnswer || currentQuestionIndex < postTestQuestions && answerAttempt >= attemptChances) {
                            loadQuestion(); // Load next question
                            Log.e(TAG, "loadQuestion();");
                            answerAttempt = 0; // Reset attempts for new question
                            Log.e(TAG, "reset answerAttempt["+answerAttempt+"]");
                        }
                    } else {
                        Log.d("TESTING", "Not moving to next question yet.");
                    }

                     if (questionsAnswered == (postTestQuestions+1)) {
                        Log.d("TESTING", "Pre-test complete!");

                        c_Lesson_feedback.postTestAttemptAnswers++;

                        // Handle completion of pre-test.
                        if (postTestCompleteListener != null) {
                            postTestCompleteListener.onPostTestComplete(correctAnswer, c_Lesson_feedback.postTestCorrectAnswers);
                            c_Lesson_feedback.printResult("Post-Test");
                            d_Lesson_container.isPostTestComplete = true;

                            loadQuestion(); // Load a different question for user return
                        }
                    }

                }

                choicesGroup.clearCheck(); // Clear selected choices at the end of processing.
            }

            total.setText("Score: " + c_Lesson_feedback.postTestCorrectAnswers
                    + "/"
                    + c_Lesson_feedback.postTestAttemptAnswers);

        });

//        submitButton.setOnClickListener(v -> {
//            Log.e("HEY!", "Submit Button Clicked!");
//
//            // Check if any choice is selected
//            if (choicesGroup.getCheckedRadioButtonId() != -1) {
//                answerAttempt++;
//                c_Lesson_feedback.postTestAttemptAnswers++;
//
//                // Ensure valid indices are used
//                int moduleIndex = getModuleIndex(getArguments().getString(ARG_MODULE));
//                int lessonIndex = getLessonIndex(getArguments().getString(ARG_LESSON));
//
//                if (moduleIndex < 0 || lessonIndex < 0) {
//                    Log.e("submitButton.onClick", "Invalid module or lesson index");
//                    return;
//                }
//
//                boolean correctAnswer = checkAnswer(); // Check if the answer is correct
//
//                // Check if questions answered does not exceed total post-test questions
//                if (questionsAnswered < postTestQuestions) {
//
//                    // Update knowledge probability
//                    double knowledgeProb = bktModel.getKnowledgeProbability();
//                    Log.e("submitButton.onClick", "Updated Knowledge Probability: " + knowledgeProb);
//
//                    // Update feedback and scores based on correctness
//                    if (correctAnswer) {
//                        c_Lesson_feedback.postTestCorrectAnswers++;
//                        x_bkt_algorithm.updateTestScore(
//                                isProgressiveMode,
//                                moduleIndex, lessonIndex,
//                                "Post-Test",
//                                c_Lesson_feedback.postTestCorrectAnswers);
//                    }
//
//                    String TAG = "TESTING";
//                    Log.d(TAG, "answerAttempt: " + answerAttempt);
//                    Log.d(TAG, "attemptChances: " + attemptChances);
//                    Log.d(TAG, "currentQuestionIndex: " + currentQuestionIndex);
//                    Log.d(TAG, "questions.length-1: " + (questions.length - 1));
//
//                    // Check if we need to move to the next question based on attempts or correctness
//                    if (answerAttempt >= attemptChances || correctAnswer) {
//                        Log.e(TAG, "Moving to next question...");
//
//                        // Move to the next question or reset if all questions are answered
//                        if (currentQuestionIndex < questions.length - 1) {
//                            currentQuestionIndex++;
//                            Log.e(TAG, "currentQuestionIndex++; [" + currentQuestionIndex + "]");
//                        } else {
//                            currentQuestionIndex = 0; // Reset for new round
//                            bktModel.logScores(); // Log scores at reset point
//                            Log.e(TAG, "reset currentQuestionIndex[" + currentQuestionIndex + "]");
//                        }
//
//                        questionsAnswered++; // Increment answered count
//
//                        // Load next question only if still within post-test limits
//                        if (currentQuestionIndex < postTestQuestions && answerAttempt >= attemptChances) {
//                            loadQuestion(); // Load next question
//                            Log.e(TAG, "loadQuestion();");
//                            answerAttempt = 0; // Reset attempts for new question
//                            Log.e(TAG, "reset answerAttempt[" + answerAttempt + "]");
//                        }
//                    } else {
//                        Log.d("TESTING", "Not moving to next question yet.");
//                    }
//                } else {
//                    Log.d("TESTING", "Post-test complete!");
//
//                    // Handle completion of post-test
//                    if (postTestCompleteListener != null) {
//                        postTestCompleteListener.onPostTestComplete(correctAnswer, bktModel.getKnowledgeProbability());
//                        c_Lesson_feedback.printResult("Post-Test");
//                        Log.e("TESTING", "FINISH!!!");
//                    }
//                }
//
//                choicesGroup.clearCheck(); // Clear selected choices at the end of processing.
//            }
//        });
    }

    private e_Question[] getPostTestQuestions(String module, String lesson) {
        String key = module + "_" + lesson;

        switch (key) {
            /* ===== Module 1 ===== */
            case "M1_Lesson 1":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_1_1.get_PostTest_Lesson1_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_1_1.get_PostTest_Lesson1_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_1_1.get_PostTest_Lesson1_Hard_Questions();
            case "M2_Lesson 1":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_1_2.get_PostTest_Lesson2_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_1_2.get_PostTest_Lesson2_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_1_2.get_PostTest_Lesson2_Hard_Questions();
            case "M3_Lesson 1":
//                return e_Module_1.getPostTestLesson3Questions();
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_1_3.get_PostTest_Lesson3_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_1_3.get_PostTest_Lesson3_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_1_3.get_PostTest_Lesson3_Hard_Questions();
            case "M4_Lesson 1":
//                return e_Module_1.getPostTestLesson4Questions();
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_1_4.get_PostTest_Lesson4_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_1_4.get_PostTest_Lesson4_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_1_4.get_PostTest_Lesson4_Hard_Questions();

            /* ===== Module 2 ===== */
            case "M1_Lesson 2":
//                return e_Module_2_1.getPostTestLesson1Questions();
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_2_1.get_PostTest_Lesson2_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_2_1.get_PostTest_Lesson2_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_2_1.get_PostTest_Lesson2_Hard_Questions();

            /* ===== Module 3 ===== */
            case "M1_Lesson 3":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_3_1.get_PostTest_Lesson1_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_3_1.get_PostTest_Lesson1_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_3_1.get_PostTest_Lesson1_Hard_Questions();
            case "M2_Lesson 3":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_3_2.get_PostTest_Lesson2_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_3_2.get_PostTest_Lesson2_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_3_2.get_PostTest_Lesson2_Hard_Questions();
//            case "M3_Lesson 3":
//                if (difficultyLevel == e_Question.Difficulty.EASY)
//                    return e_Module_3_3.get_PostTest_Lesson3_Easy_Questions();
//                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
//                    return e_Module_3_3.get_PostTest_Lesson3_Medium_Questions();
//                else if (difficultyLevel == e_Question.Difficulty.HARD)
//                    return e_Module_3_3.get_PostTest_Lesson3_Hard_Questions();

            /* ===== Module 4 ===== */
            case "M1_Lesson 4":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_4_1.get_PostTest_Lesson1_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_4_1.get_PostTest_Lesson1_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_4_1.get_PostTest_Lesson1_Hard_Questions();
            case "M2_Lesson 4":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_4_2.get_PostTest_Lesson2_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_4_2.get_PostTest_Lesson2_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_4_2.get_PostTest_Lesson2_Hard_Questions();
//            case "M3_Lesson 4":
//                if (difficultyLevel == e_Question.Difficulty.EASY)
//                    return e_Module_4_3.get_PostTest_Lesson3_Easy_Questions();
//                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
//                    return e_Module_4_3.get_PostTest_Lesson3_Medium_Questions();
//                else if (difficultyLevel == e_Question.Difficulty.HARD)
//                    return e_Module_4_3.get_PostTest_Lesson3_Hard_Questions();

            /* ===== Module 5 ===== */
            case "M1_Lesson 5":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_5_1.get_PostTest_Lesson1_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_5_1.get_PostTest_Lesson1_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_5_1.get_PostTest_Lesson1_Hard_Questions();
            case "M2_Lesson 5":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_5_2.get_PostTest_Lesson2_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_5_2.get_PostTest_Lesson2_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_5_2.get_PostTest_Lesson2_Hard_Questions();
            case "M3_Lesson 5":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_5_3.get_PostTest_Lesson3_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_5_3.get_PostTest_Lesson3_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_5_3.get_PostTest_Lesson3_Hard_Questions();

            /* ===== Module 6 ===== */
            case "M1_Lesson 6":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_6_1.get_PostTest_Lesson1_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_6_1.get_PostTest_Lesson1_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_6_1.get_PostTest_Lesson1_Hard_Questions();
            case "M2_Lesson 6":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_6_2.get_PostTest_Lesson2_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_6_2.get_PostTest_Lesson2_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_6_2.get_PostTest_Lesson2_Hard_Questions();
            case "M3_Lesson 6":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_6_3.get_PostTest_Lesson3_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_6_3.get_PostTest_Lesson3_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_6_3.get_PostTest_Lesson3_Hard_Questions();

            /* ===== Module 7 ===== */
            case "M1_Lesson 7":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_7_1.get_PostTest_Lesson1_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_7_1.get_PostTest_Lesson1_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_7_1.get_PostTest_Lesson1_Hard_Questions();

            /* ===== Module 8 ===== */
            case "M1_Lesson 8":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_8_1.get_PostTest_Lesson1_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_8_1.get_PostTest_Lesson1_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_8_1.get_PostTest_Lesson1_Hard_Questions();
            case "M2_Lesson 8":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_8_2.get_PostTest_Lesson2_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_8_2.get_PostTest_Lesson2_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_8_2.get_PostTest_Lesson2_Hard_Questions();
            case "M3_Lesson 8":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_8_3.get_PostTest_Lesson3_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_8_3.get_PostTest_Lesson3_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_8_3.get_PostTest_Lesson3_Hard_Questions();

            default:
                throw new IllegalArgumentException("Invalid module or lesson: " + key);
        }
    }

    private e_Question.Difficulty getDifficultyLevel(double bktScore) {
        if (bktScore >= 0.67) {
            return e_Question.Difficulty.HARD;
        } else if (bktScore >= 0.34) {
            return e_Question.Difficulty.MEDIUM;
        } else {
            return e_Question.Difficulty.EASY;
        }
    }

    private e_Question[] getPostTestQuestionsBasedOnDifficulty(String module, String lesson, e_Question.Difficulty difficulty) {

        String TAG = "GWEN";

        Log.e(TAG, "I RECEIVED these:");
        Log.e(TAG, "module: " + module);
        Log.e(TAG, "lesson: " + lesson);


        e_Question[] allQuestions = getPostTestQuestions(module, lesson);  // Fetch all questions for the lesson

        // Filter questions based on difficulty
        List<e_Question> filteredQuestions = new ArrayList<>();
        for (e_Question question : allQuestions) {
            if (question.getDifficulty() == difficulty) {
                filteredQuestions.add(question);
            }
        }

        // Fallback to all questions if no questions match the difficulty
        if (filteredQuestions.isEmpty()) {
            Log.e("f_post_test", "No questions match the difficulty level.");
            return allQuestions;
        }

        return filteredQuestions.toArray(new e_Question[0]);
    }

    private void loadQuestion() {
        String TAG = "loadQuestion";

        // ang problema mo nalang dito, bakit null yung questions?

        // Check if questions array is null or empty
        if (questions == null) {
            Log.e(TAG, "Error: Questions array is null.");
            Toast.makeText(getContext(), "Error: Questions not initialized.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (questions.length == 0) {
            Log.e(TAG, "Error: Questions array is empty.");
            Toast.makeText(getContext(), "Error: No questions available.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if currentQuestionIndex is within bounds
        if (currentQuestionIndex >= questions.length) {
            Log.e(TAG, "Error: Invalid currentQuestionIndex (" + currentQuestionIndex + "). Out of bounds.");
            return;
        }

        Log.e(TAG, "Loading question at index: " + currentQuestionIndex);

        // Reset isCorrect before loading a new question
        isCorrect = false;

        // Clear previous selection
        choicesGroup.clearCheck();
        identificationAnswer.setText("");

        // Get the current question
        e_Question currentQuestion = questions[currentQuestionIndex];
        Log.e(TAG, "Current question: " + currentQuestion.getQuestion());

        questionText.setText(currentQuestion.getQuestion());
        choicesGroup.removeAllViews();

        if (difficultyLevel == e_Question.Difficulty.EASY || difficultyLevel == e_Question.Difficulty.MEDIUM) {
            String[] choices = currentQuestion.getChoices();
            for (int i = 0; i < choices.length; i++) {
                RadioButton choiceButton = new RadioButton(getContext());
                choiceButton.setId(i);
                choiceButton.setText(choices[i]);
                choiceButton.setTextColor(getResources().getColor(R.color.white));
                choiceButton.setTextSize(18);

                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.WRAP_CONTENT,
                        RadioGroup.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 8, 0, 8);

                choiceButton.setLayoutParams(params);
                choicesGroup.addView(choiceButton);
            }

            choicesGroup.setVisibility(View.VISIBLE);
            identificationAnswer.setVisibility(View.GONE);

        } else if (difficultyLevel == e_Question.Difficulty.HARD) {
            identificationAnswer.setVisibility(View.VISIBLE);
            choicesGroup.setVisibility(View.GONE);
            identificationAnswer.setText("");
        }
    }


//    private void loadQuestion() {
//
//        String TAG = "loadQuestion";
//
//        Log.e(TAG, "54545454545454545454545454545454545454545454545454545454");
//
//        // Reset isCorrect before loading a new question
//        isCorrect = false;
//
//        // Clear previous selection
//        choicesGroup.clearCheck();
//        identificationAnswer.setText("");
//
//        Log.e(TAG, "currentQuestionIndex: " + currentQuestionIndex);
//
//        // Get the current question
//        e_Question currentQuestion = questions[currentQuestionIndex];
//
//        Log.e(TAG, "PAG DI LUMABAS TO, ETONG NASA TAAS ANG ERROR..");
//
//        questionText.setText(currentQuestion.getQuestion());
//
//        // Clear previous views
//        choicesGroup.removeAllViews();
//
//        // HINDI NAG LO-LOG TO, PATI SA CURRENT FRAGMENT.. BALIKAN KO TOMORROW
//
////        // Determine the difficulty level of the current question
////        e_Question.Difficulty difficultyLevel = currentQuestion.getDifficulty();
////        Log.e(TAG, "difficulty: " + difficultyLevel);
//
//        if (difficultyLevel == e_Question.Difficulty.EASY || difficultyLevel == e_Question.Difficulty.MEDIUM) {
//            // Load multiple-choice options for EASY and MEDIUM questions
//            String[] choices = currentQuestion.getChoices();
//            for (int i = 0; i < choices.length; i++) {
//                RadioButton choiceButton = new RadioButton(getContext());
//                choiceButton.setId(i);
//                choiceButton.setPadding(16, 0, 0, 0);
//                choiceButton.setText(choices[i]);
//                choiceButton.setTextColor(getResources().getColor(R.color.white));  // Set text color to white
//                choiceButton.setTextSize(18);  // Set text size to 18sp
//
//                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
//                        RadioGroup.LayoutParams.WRAP_CONTENT,
//                        RadioGroup.LayoutParams.WRAP_CONTENT
//                );
//                params.setMargins(0, 8, 0, 8);  // Set margins
//
//                choiceButton.setLayoutParams(params);  // Apply the margins to the RadioButton
//                choicesGroup.addView(choiceButton);  // Add the choice to the RadioGroup
//            }
//
//            // Show the choicesGroup and hide the EditText
//            choicesGroup.setVisibility(View.VISIBLE);
//            identificationAnswer.setVisibility(View.GONE);
//
//        } else if (difficultyLevel == e_Question.Difficulty.HARD) {
//            // Load input field for HARD questions
//            identificationAnswer.setVisibility(View.VISIBLE);
//            choicesGroup.setVisibility(View.GONE);
//
//            // Clear the EditText for new input
//            identificationAnswer.setText("");
//        }
//    }


//    private void loadQuestion() {
//        // Reset isCorrect before loading a new question
//        isCorrect = false;
//
//        // Clear previous selection
//        choicesGroup.clearCheck();
//        identificationAnswer.setText("");
//
//        e_Question currentQuestion = questions[currentQuestionIndex];
//        questionText.setText(currentQuestion.getQuestion());
//
//        choicesGroup.removeAllViews();
//        String[] choices = currentQuestion.getChoices();
//        for (int i = 0; i < choices.length; i++) {
//            RadioButton choiceButton = new RadioButton(getContext());
//            choiceButton.setId(i);
//            choiceButton.setText(choices[i]);
//            choiceButton.setTextColor(getResources().getColor(R.color.white));  // Set text color to white
//            choiceButton.setTextSize(18);  // Set text size to 18sp
//
//            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
//                    RadioGroup.LayoutParams.WRAP_CONTENT,
//                    RadioGroup.LayoutParams.WRAP_CONTENT
//            );
//            params.setMargins(0, 8, 0, 8);  // Set margins
//
//            choiceButton.setLayoutParams(params);  // Apply the margins to the RadioButton
//            choicesGroup.addView(choiceButton);
//        }
//    }

//    private void loadQuestion() {
//        // Reset isCorrect before loading a new question
//        isCorrect = false;
//
//        // Clear previous selection
//        choicesGroup.clearCheck();
//        identificationAnswer.setText("");
//
//        e_Question currentQuestion = questions[currentQuestionIndex];
//        questionText.setText(currentQuestion.getQuestion());
//
//        choicesGroup.removeAllViews();
//        String[] choices = currentQuestion.getChoices();
//        for (int i = 0; i < choices.length; i++) {
//            RadioButton choiceButton = new RadioButton(getContext());
//            choiceButton.setId(i);
//            choiceButton.setText(choices[i]);
//            choiceButton.setTextColor(getResources().getColor(R.color.white));  // Set text color to white
//            choiceButton.setTextSize(18);  // Set text size to 18sp (you can adjust this size)
//
//            // Create LayoutParams for margin settings
//            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
//                    RadioGroup.LayoutParams.WRAP_CONTENT,
//                    RadioGroup.LayoutParams.WRAP_CONTENT
//            );
//            params.setMargins(0, 8, 0, 8);  // Set margins (left, top, right, bottom) in pixels
//
//            choiceButton.setLayoutParams(params);  // Apply the margins to the RadioButton
//
//            choicesGroup.addView(choiceButton);
//        }
//    }

    public boolean checkAnswer() {
        String TAG = "checkAnswer()";

        Log.e(TAG, "checkAnswer() method is CALLED");

        // Check if choicesGroup is null
        if (choicesGroup == null) {
            Log.e(TAG, "choicesGroup is null!");
            Context context = getContext();
            if (context != null) {
                Toast.makeText(context, "Error: Choices group is missing.", Toast.LENGTH_SHORT).show();
            } else {
                Log.e(TAG, "Context is null, cannot show Toast.");
            }
            return false;  // Exit early to avoid crash
        }

        Log.e(TAG, "Difficulty: " + difficultyLevel);

        // Handling EASY and MEDIUM difficulty levels
        if (difficultyLevel == e_Question.Difficulty.EASY || difficultyLevel == e_Question.Difficulty.MEDIUM) {

            int selectedId = choicesGroup.getCheckedRadioButtonId();
            if (selectedId != -1) {
                e_Question currentQuestion = questions[currentQuestionIndex];
                if (selectedId == currentQuestion.getCorrectAnswer_EASY_MEDIUM()) {
                    isCorrect = true;
                    Log.e(TAG, "Answer is Correct! | isCorrect: " + isCorrect);
                    return true;  // Correct answer
                } else {
                    Context context = getContext();
                    if (context != null) {
                        Toast.makeText(context, "Incorrect! Try again.", Toast.LENGTH_SHORT).show();
                    }
                    isCorrect = false;
                    Log.e(TAG, "Answer is Incorrect! | isCorrect: " + isCorrect);
                    return false;  // Incorrect answer
                }
            } else {
                Context context = getContext();
                if (context != null) {
                    Toast.makeText(context, "Please select an answer.", Toast.LENGTH_SHORT).show();
                }
                Log.e(TAG, "No answer selected | isCorrect: " + isCorrect);
                return false;  // No answer selected
            }
        }
        // Handling HARD difficulty level
        else if (difficultyLevel == e_Question.Difficulty.HARD) {
            if (identificationAnswer != null) {
                String inputAnswer = String.valueOf(identificationAnswer.getText()).trim();
                if (!inputAnswer.isEmpty()) {
                    e_Question currentQuestion = questions[currentQuestionIndex];
                    // Use .equals() to compare String values
                    if (inputAnswer.equals(currentQuestion.getCorrectAnswer_HARD())) {
                        isCorrect = true;
                        Log.e(TAG, "Answer is Correct! | isCorrect: " + isCorrect);
                        return true;  // Correct answer
                    } else {
                        Context context = getContext();
                        if (context != null) {
                            Toast.makeText(context, "Incorrect! Try again.", Toast.LENGTH_SHORT).show();
                        }
                        isCorrect = false;
                        Log.e(TAG, "Answer is Incorrect! | isCorrect: " + isCorrect);
                        return false;  // Incorrect answer
                    }
                } else {
                    Context context = getContext();
                    if (context != null) {
                        Toast.makeText(context, "Please enter an answer.", Toast.LENGTH_SHORT).show();
                    }
                    Log.e(TAG, "No answer provided | isCorrect: " + isCorrect);
                    return false;  // No answer provided
                }
            } else {
                Log.e(TAG, "identificationAnswer is null!");
                return false;  // Exit early to avoid crash
            }
        }

        return false;  // Default return in case of unhandled difficulty
    }


    // Helper methods to get module and lesson indices
    private int getModuleIndex(String module) {
        switch (module) {
            case "M1": return 0;
            case "M2": return 1;
            case "M3": return 2;
            case "M4": return 3;
            case "M5": return 4;
            case "M6": return 5;
            case "M7": return 6;
            case "M8": return 7;
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
            default:
                Log.e("getLessonIndex", "Invalid lesson: " + lesson);
                throw new IllegalArgumentException("Invalid lesson: " + lesson);
        }
    }
}
