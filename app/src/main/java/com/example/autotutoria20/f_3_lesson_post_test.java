package com.example.autotutoria20;

import android.content.Context;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class f_3_lesson_post_test extends Fragment {

    private static final String ARG_MODULE = "module";
    private static final String ARG_LESSON = "lesson";
    private static final String ARG_MODE = "mode";

    private int currentQuestionIndex = 0;
    private e_Question[] questions;
    private TextView questionText;
    public static RadioGroup choicesGroup;
    public static String hint;
    public static String correctAnswer;
    public static EditText identificationAnswer;
    private TextView total;
    private Button submitButton;
    public static e_Question.Difficulty difficultyLevel;
    private int answerAttempt = 0;
    private int attemptChances = 0;
    private int questionsAnswered = 1;
    static int postTestQuestions = 10;
    private boolean isCorrect = false;
    private boolean isProgressiveMode = true; // Default to Progressive Mode

    private PostTestCompleteListener postTestCompleteListener;

    // BKT Model instance
    private x_bkt_algorithm bktModel;

    // Interface to notify the container activity when post-test is complete
    public interface PostTestCompleteListener {
        void onPostTestComplete(boolean isCorrect, int score, boolean isPassed);
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

//        TextView items = view.findViewById(R.id.answers_total);
//
//        items.setText();
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

                // Set attempt chances and number of questions based on difficulty level
                if (difficultyLevel == e_Question.Difficulty.EASY) {
                    attemptChances = 1;
                    postTestQuestions = 10;
                }
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM) {
                    attemptChances = 2;
                    postTestQuestions = 5;
                }
                else if (difficultyLevel == e_Question.Difficulty.HARD) {
                    attemptChances = 3;
                    postTestQuestions = 3;
                }

                TextView items = view.findViewById(R.id.answers_total);

                items.setText("Item: " + c_Lesson_feedback.postTestAttemptAnswers
                        + "/"
                        + postTestQuestions);

                // ang inaalam mo ngayon, kung ilang questions itatanong base on difficulty??

                // tapos, yung post-test na identification, hindi nagpo-process ng answer na nandon sa identification

            // dapat merong if statement, para nakabukod yung pag-process ng easy, medium, and hard

                Log.e("f_post_test", "dahil " + difficultyLevel + " ang difficulty, gawin nating " + attemptChances + " ang chances");

//                bktModel.setBKTParameters(difficultyLevel);

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

            // Easy | Medium
            if (!(choicesGroup.getCheckedRadioButtonId() == -1)
                ||
            // Hard
                !identificationAnswer.getText().toString().trim().isEmpty()) {

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
                if (questionsAnswered <= (postTestQuestions)) {

                    // Update feedback and scores based on correctness
                    if (correctAnswer) {
                        if (!x_bkt_algorithm.isLessonFinished) {
                            x_bkt_algorithm.updateTestScore(
                                    isProgressiveMode,
                                    moduleIndex, lessonIndex,
                                    "Post-Test",
                                    c_Lesson_feedback.preTestCorrectAnswers);
                        }
                        c_Lesson_feedback.postTestCorrectAnswers++;
                    }

                    if (!x_bkt_algorithm.isLessonFinished)
                        bktModel.updateScore(moduleIndex, lessonIndex, isProgressiveMode, correctAnswer);

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

                    //                     == to dapat.. ginawa ko lang >=
                     if (questionsAnswered >= (postTestQuestions+1)) {
                        Log.d("TESTING", "Post-test complete!");

//                        c_Lesson_feedback.postTestAttemptAnswers++;

                        // Handle completion of pre-test.
                        if (postTestCompleteListener != null) {

                            double bktscore = x_bkt_algorithm.getKnowledge();

                            postTestCompleteListener.onPostTestComplete(
                                    correctAnswer,
                                    c_Lesson_feedback.postTestCorrectAnswers,
                                    bktscore >= b_main_0_menu_categorize_user.passingGrade
                            );
                            c_Lesson_feedback.printResult("Post-Test");
                            d_Lesson_container.isPostTestComplete = true;

                            loadQuestion(); // Load a different question for user return
                        }
                    }

                }

                choicesGroup.clearCheck(); // Clear selected choices at the end of processing.
            }

            if (c_Lesson_feedback.postTestAttemptAnswers <= postTestQuestions)
                total.setText("Item: " + c_Lesson_feedback.postTestAttemptAnswers
                        + "/"
                        + postTestQuestions);

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

    public static e_Question.Difficulty getDifficulty() {
        return difficultyLevel;
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

//    private e_Question.Difficulty getDifficultyLevel(double bktScore) {
//        if (bktScore >= 0.67) {
//            return e_Question.Difficulty.HARD;
//        } else if (bktScore >= 0.34) {
//            return e_Question.Difficulty.MEDIUM;
//        } else {
//            return e_Question.Difficulty.EASY;
//        }
//    }

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

        d_Lesson_container.startCountdown(requireContext(), "Post-Test");

        hint = ""; // reset hint

        Log.e("Generate Hint", "Cleared Hint: " + hint);

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

    public boolean checkAnswer() {
        String TAG = "checkAnswer()";

        Log.e(TAG, "checkAnswer() method is CALLED");

//        // Check if choicesGroup is null
//        if (choicesGroup == null
//        ||
//        identificationAnswer.getText().toString().trim().isEmpty()) {
//
//            Log.e(TAG, "identification answer is null");
//            Context context = getContext();
//            if (context != null) {
//                Toast.makeText(context, "Error: Choices group is missing.", Toast.LENGTH_SHORT).show();
//            } else {
//                Log.e(TAG, "Context is null, cannot show Toast.");
//            }
//
//        }

        Log.e(TAG, "Difficulty: " + difficultyLevel);

        // Handling EASY and MEDIUM difficulty levels
        if (difficultyLevel == e_Question.Difficulty.EASY || difficultyLevel == e_Question.Difficulty.MEDIUM) {

            if (choicesGroup == null) {
                Log.e(TAG, "choicesGroup is null!");
                return false;  // Exit early to avoid crash
            } else {
                int selectedId = choicesGroup.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    e_Question currentQuestion = questions[currentQuestionIndex];
                    if (selectedId == currentQuestion.getCorrectAnswer_EASY_MEDIUM()) {
                        Toast.makeText(getContext(), "Correct!", Toast.LENGTH_SHORT).show();
                        isCorrect = true;
                        Log.e(TAG, "Answer is Correct! | isCorrect: " + isCorrect);
                        return true;  // Correct answer
                    } else {
                        d_Lesson_container.startCountdown(requireContext(), "Post-Test");
                        Toast.makeText(getContext(), "Incorrect, Try Again.", Toast.LENGTH_SHORT).show();
                        isCorrect = false;
                        Log.e(TAG, "Answer is Incorrect! | isCorrect: " + isCorrect);
                        return false;  // Incorrect answer
                    }
                } else {
                    Context context = getContext();
                    if (context != null) {
                        Toast.makeText(context, "Please select an answer.", Toast.LENGTH_SHORT).show();
                        d_Lesson_container.startCountdown(requireContext(), "Pre-Test");
                    }
                    Log.e(TAG, "No answer selected | isCorrect: " + isCorrect);
                    return false;  // No answer selected
                }
            }


        }
        // Handling HARD difficulty level
        else if (difficultyLevel == e_Question.Difficulty.HARD) {

            e_Question currentQuestion = questions[currentQuestionIndex];

            // get answer
            correctAnswer = currentQuestion.getCorrectAnswer_HARD();

            if (identificationAnswer != null) {
                String inputAnswer = String.valueOf(identificationAnswer.getText()).trim();
                if (!inputAnswer.isEmpty()) {

                    // Use .equals() to compare String values
                    if (inputAnswer.equalsIgnoreCase(correctAnswer)) {
                        isCorrect = true;
                        Log.e(TAG, "Answer is Correct! | isCorrect: " + isCorrect);
                        return true;  // Correct answer
                    } else {
                        Context context = getContext();
                        if (context != null) {
                            d_Lesson_container.startCountdown(requireContext(), "Post-Test");
                            Toast.makeText(context, "Incorrect! Chance: " + answerAttempt + "/" + attemptChances, Toast.LENGTH_SHORT).show();
                            identificationAnswer.setText("");
                        }

                        if (answerAttempt == (attemptChances-1)) {
                            Log.e("Generate Hint", "Correct Answer: " + correctAnswer);

                            hint = generateHint(correctAnswer, b_main_0_menu_categorize_user.category);

//                d_Lesson_container.showDialog(
//                        "Answer Hint",
//                        "The Answer is: " + hint
//                );


//      Create a dialog to show the user's score
                            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                            builder.setTitle("Answer Hint");

                            builder.setMessage("Answer is: " + hint);

                            // Add a button to dismiss the dialog
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss(); // Close the dialog
                                }
                            });

                            builder.setCancelable(false);

                            // Create and show the dialog
                            AlertDialog dialog = builder.create();
                            dialog.show();

                            Log.e("Generate Hint", "Hint: " + hint);
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

    public static String generateHint(String originalString, String category) {
        // Define the number of underscores based on the category
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
                replaceChar = (int) (answerLength * 0.7); // Cast to int
                Log.e(TAG, "Advanced | replaceChar["+replaceChar+"] = answerLength["+answerLength+"] * 0.3");
                break;
            case "Expert":
                replaceChar = (int) (answerLength * 0.9); // Cast to int
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

        // Perplexity
//        int replacePerWord = replaceChar / parts.length;

        // Copilot
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
