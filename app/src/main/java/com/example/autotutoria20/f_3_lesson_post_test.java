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
    private Button submitButton;
    private e_Question.Difficulty difficultyLevel;
    private int answerAttempt = 0;
    private int attemptChances = 2;
    private boolean isCorrect = false;
    private boolean isProgressiveMode = true; // Default to Progressive Mode

    private PostTestCompleteListener postTestCompleteListener;

    // BKT Model instance
    private x_bkt_algorithm bktModel;

    // Interface to notify the container activity when post-test is complete
    public interface PostTestCompleteListener {
        void onPostTestComplete(boolean isCorrect);
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

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        if (savedInstanceState != null) {
//            isCorrect = savedInstanceState.getBoolean("isCorrect", false);
//        }
//        return inflater.inflate(R.layout.f_3_lesson_post_test, container, false);
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        questionText = view.findViewById(R.id.post_test_question);
        choicesGroup = view.findViewById(R.id.choices_group);
        submitButton = view.findViewById(R.id.submit_post_test);
        identificationAnswer = view.findViewById(R.id.identification_answer);

//        // DAPAT MAG-IBA TO DEPENDE SA DIFFICULTY...
//        // Initialize BKT Model instance with appropriate parameters
        bktModel = x_bkt_algorithm.getInstance(0.3, 0.2, 0.1, 0.4);

//         dapat ma move to kasi nag n null yung bktModel

        // Retrieve arguments passed from the parent container
        if (getArguments() != null) {
            String module = getArguments().getString(ARG_MODULE);
            String lesson = getArguments().getString(ARG_LESSON);

            // Determine if it's Progressive Mode
            isProgressiveMode = getArguments().getString(ARG_MODE).equals("Progressive Mode");

            int lessonNumber = getLessonIndex(lesson);

            // Initialize BKT Scores
            String collectionPath = isProgressiveMode ? "Progressive Mode" : "Free Use Mode";
            String documentName = "Lesson " + (lessonNumber + 1);
            bktModel.initializeBKTScores(collectionPath, documentName, bktScores -> {

                // Extract the module number from the string (M1, M2, etc.)
                char moduleChar = module.charAt(1);  // Get the second character, e.g., '1' from "M1"

                // Convert it to an integer and decrement by 1 to get the zero-based index
                int moduleIndex = Character.getNumericValue(moduleChar) - 1;

                // Ensure the moduleIndex is within valid range
                if (moduleIndex >= 0 && moduleIndex < bktScores.size()) {
                    double userScore = bktScores.get(moduleIndex);  // Use the moduleIndex to get the correct score
                    Log.d("f_post_test", "User BKT Score for Module " + module + ": " + userScore);

                    // Determine difficulty based on score
                    difficultyLevel = bktModel.getDifficultyLevel(userScore);
                    Log.d("f_post_test", "Determined Difficulty Level: " + difficultyLevel);

                    bktModel.setBKTParameters(difficultyLevel);

//                    // Adjust BKT Model parameters based on difficulty level
//                    double pInit, pTransit, pSlip, pGuess;
//
//                    switch (difficultyLevel) {
//                        case EASY:
//                            // Set parameters for EASY difficulty
//                            pInit = 0.5;    // Example initial probability of knowledge
//                            pTransit = 0.2; // Example probability of learning the skill after practice
//                            pSlip = 0.1;    // Example probability of making a mistake despite knowing the skill
//                            pGuess = 0.4;   // Example probability of guessing the correct answer without knowing the skill
//                            Log.d("f_post_test", "BKT Difficulty Level: Easy | " + difficultyLevel);
//                            break;
//                        case MEDIUM:
//                            // Set parameters for MEDIUM difficulty
//                            // Example values for medium difficulty
//                            pInit = 0.4;
//                            pTransit = 0.3;
//                            pSlip = 0.15;
//                            pGuess = 0.3;
//                            Log.d("f_post_test", "(pInit:"+pInit+"pTransit:"+pTransit+"pSlip:"+pSlip+"pGuess:"+pGuess);
//                            Log.d("f_post_test", "BKT Difficulty Level: Medium | " + difficultyLevel);
//                            break;
//                        case HARD:
//                            // Set parameters for HARD difficulty
//                            pInit = 0.3;
//                            pTransit = 0.4;
//                            pSlip = 0.2;
//                            pGuess = 0.2;
//                            Log.d("f_post_test", "(pInit:"+pInit+"pTransit:"+pTransit+"pSlip:"+pSlip+"pGuess:"+pGuess);
//                            Log.d("f_post_test", "BKT Difficulty Level: Hard | " + difficultyLevel);
//                            break;
//                        default:
//                            // Fallback to default values
//                            pInit = 0.3;
//                            pTransit = 0.2;
//                            pSlip = 0.1;
//                            pGuess = 0.4;
//                            Log.d("f_post_test", "(pInit:"+pInit+"pTransit:"+pTransit+"pSlip:"+pSlip+"pGuess:"+pGuess);
//                            Log.d("f_post_test", "BKT Difficulty Level: Default | " + difficultyLevel);
//                            break;
//                    }
//                    String TAG = "Parameter Check";
//
//                    Log.e(TAG, "Difficulty: " + difficultyLevel);
//                    Log.e(TAG, "pInit: " + pInit);
//                    Log.e(TAG, "pTransit: " + pTransit);
//                    Log.e(TAG, "pSlip: " + pSlip);
//                    Log.e(TAG, "pGuess: " + pGuess);
//
//                    // Initialize BKT Model instance with the parameters based on difficulty
//                    bktModel = x_bkt_algorithm.getInstance(pInit, pTransit, pSlip, pGuess);

                    // Retrieve post-test questions based on difficulty level
                    questions = getPostTestQuestionsBasedOnDifficulty(module, lesson, difficultyLevel);
                    loadQuestion();

                } else {
                    Log.e("f_post_test", "Invalid moduleIndex: " + moduleIndex + ", cannot retrieve BKT score.");
                }

            });
        }

        submitButton.setOnClickListener(v -> {

            // add validator if no selected answer??
            // it should ask the user to select an answer otherwise this onClickListener will not do anything

            if (choicesGroup.isSelected()) {
//                Toast.makeText(getContext(), "Meron kang selected: " + choicesGroup.getCheckedRadioButtonId(), Toast.LENGTH_SHORT).show();
            }

            if (!(choicesGroup.getCheckedRadioButtonId() == -1)) {

                int hey = choicesGroup.getCheckedRadioButtonId();
                switch(hey) {
                    case 0:
                        Log.e("ROP", "A");
                        break;
                    case 1:
                        Log.e("ROP", "B");
                        break;
                    case 2:
                        Log.e("ROP", "C");
                        break;
                    case 3:
                        Log.e("ROP", "D");
                        break;
                }

                answerAttempt++;
                Log.e("ROP", "Answer Attempt: " + answerAttempt);

                boolean correctAnswer = checkAnswer();
                Log.e("submitButton.onClick", "correctAnswer: " + correctAnswer);

                if (!correctAnswer)
                    choicesGroup.clearCheck();

                // Update BKT model with the result of the answer
                bktModel.updateKnowledge(correctAnswer);

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

                // Update the score
                bktModel.updateScore(moduleIndex, lessonIndex, knowledgeProb, isProgressiveMode);



                // to give student chance to get correct answer before loading another question
                if (answerAttempt >= attemptChances && !correctAnswer) {
                    loadQuestion(); // Load the next question
                    answerAttempt = 0;
                } else if (answerAttempt <= attemptChances && correctAnswer) {
                    // Notify the listener
                    if (postTestCompleteListener != null && correctAnswer) {
                        postTestCompleteListener.onPostTestComplete(correctAnswer);
                    }

                    // Move to the next question
                    if (currentQuestionIndex < questions.length - 1) {
                        currentQuestionIndex++;
                    } else {
                        currentQuestionIndex = 0; // Reset to the first question if all are answered
//                    Toast.makeText(getContext(), "Pre-test completed!", Toast.LENGTH_SHORT).show();
                        bktModel.logScores();
                    }
                }
            }
        });
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        questionText = view.findViewById(R.id.post_test_question);
//        choicesGroup = view.findViewById(R.id.choices_group);
//        submitButton = view.findViewById(R.id.submit_post_test);
//        identificationAnswer = view.findViewById(R.id.identification_answer);
//
//        // Initialize BKT Model instance with appropriate parameters
//        bktModel = x_bkt_algorithm.getInstance(0.3, 0.2, 0.1, 0.4);
//
//        if (getArguments() != null) {
//            String module = getArguments().getString(ARG_MODULE);
//            String lesson = getArguments().getString(ARG_LESSON);
//
//            // Determine if it's Progressive Mode
//            isProgressiveMode = getArguments().getString(ARG_MODE).equals("Progressive Mode");
//
//            int lessonNumber = getLessonIndex(lesson);
//
//            // Initialize BKT Scores
//            String collectionPath = isProgressiveMode ? "Progressive Mode" : "Free Use Mode";
//            String documentName = "Lesson " + (lessonNumber + 1);
//            bktModel.initializeBKTScores(collectionPath, documentName, bktScores -> {
//                if (bktScores != null && !bktScores.isEmpty()) {
//                    double userScore = bktScores.get(0);  // Assuming the score is the first item
//                    Log.d("f_post_test", "User BKT Score: " + userScore);
//
//                    // Determine difficulty based on score
//                    e_Question.Difficulty difficultyLevel = getDifficultyLevel(userScore);
//                    Log.d("f_post_test", "Determined Difficulty Level: " + difficultyLevel);
//
//                    // Retrieve post-test questions based on difficulty level
//                    questions = getPostTestQuestionsBasedOnDifficulty(module, lesson, difficultyLevel);
//                    loadQuestion();
//                } else {
//                    Log.e("f_post_test", "Failed to retrieve BKT Scores");
//                }
//            });
//        }
//
//        submitButton.setOnClickListener(v -> {
//            // Handle the submission of answers (you already have this implemented)
//        });
//    }


//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        questionText = view.findViewById(R.id.post_test_question); // Ensure this ID matches the layout
//        choicesGroup = view.findViewById(R.id.choices_group); // Ensure this ID matches the layout
//        submitButton = view.findViewById(R.id.submit_post_test);
//        EditText identificationAnswer = view.findViewById(R.id.identification_answer);
//
//        // Initialize BKT Model instance with appropriate parameters
//        bktModel = x_bkt_algorithm.getInstance(0.3, 0.2, 0.1, 0.4);
//
//        if (getArguments() != null) {
//            String module = getArguments().getString(ARG_MODULE);
//            String lesson = getArguments().getString(ARG_LESSON);
//
//            Log.e("f_post_test.java", "module: " + module);
//            Log.e("f_post_test.java", "lesson: " + lesson);
//
//            // Determine if it's Progressive Mode
//            if (getArguments() != null) {
//                isProgressiveMode = getArguments().getString(ARG_MODE).equals("Progressive Mode");
//            }
//
//            int lessonNumber = getLessonIndex(lesson);
//
//            // Initialize BKT Scores
//            String collectionPath = isProgressiveMode ? "Progressive Mode" : "Free Use Mode";
//            String documentName = "Lesson " + (lessonNumber + 1);
//            bktModel.initializeBKTScores(collectionPath, documentName, bktScores -> {
//                if (bktScores != null) {
//                    Log.d("f_post_test", "BKT Scores initialized: " + bktScores);
//                } else {
//                    Log.e("f_post_test", "Failed to retrieve BKT Scores");
//                }
//            });
//
//            // Retrieve questions based on module and lesson
//            questions = getPostTestQuestions(module, lesson);
//
//            // Load the first question
//            loadQuestion();
//        }
//
//        submitButton.setOnClickListener(v -> {
//
//            if (choicesGroup.getCheckedRadioButtonId() == -1) {
//                Toast.makeText(getContext(), "Please select an answer.", Toast.LENGTH_SHORT).show();
//                return;
//            } else {
//
//                answerAttempt++;
//
//                boolean correctAnswer = checkAnswer();
//                Log.e("submitButton.onClick", "correctAnswer: " + correctAnswer);
//
//                // Update BKT model with the result of the answer
//                bktModel.updateKnowledge(correctAnswer);
//
//                // Log the updated knowledge probability
//                double knowledgeProb = bktModel.getKnowledgeProbability();
//                Log.e("submitButton.onClick", "Updated Knowledge Probability: " + knowledgeProb);
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
//                // Update the score
//                bktModel.updateScore(moduleIndex, lessonIndex, knowledgeProb, isProgressiveMode);
//
//                // Notify the listener
//                if (postTestCompleteListener != null) {
//                    postTestCompleteListener.onPostTestComplete(correctAnswer);
//                }
//
//                // Move to the next question
//                if (currentQuestionIndex < questions.length - 1) {
//                    currentQuestionIndex++;
//                } else {
//                    currentQuestionIndex = 0; // Reset to the first question if all are answered
////                    Toast.makeText(getContext(), "Post-test completed!", Toast.LENGTH_SHORT).show();
//                    bktModel.logScores();
//                }
//
//                // to give student chance to get correct answer before loading another question
//                if (answerAttempt == attemptChances) {
//                    if (!correctAnswer) {
//                        loadQuestion(); // Load the next question
//                        answerAttempt = 0;
//                    }
//                }
//            }
//
//
//        });
//    }

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
            case "M3_Lesson 3":
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
            case "M3_Lesson 4":
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

        // Reset isCorrect before loading a new question
        isCorrect = false;

        // Clear previous selection
        choicesGroup.clearCheck();
        identificationAnswer.setText("");

        // Get the current question
        e_Question currentQuestion = questions[currentQuestionIndex];
        questionText.setText(currentQuestion.getQuestion());

        // Clear previous views
        choicesGroup.removeAllViews();

        // HINDI NAG LO-LOG TO, PATI SA CURRENT FRAGMENT.. BALIKAN KO TOMORROW

//        // Determine the difficulty level of the current question
//        e_Question.Difficulty difficultyLevel = currentQuestion.getDifficulty();
//        Log.e(TAG, "difficulty: " + difficultyLevel);

        if (difficultyLevel == e_Question.Difficulty.EASY || difficultyLevel == e_Question.Difficulty.MEDIUM) {
            // Load multiple-choice options for EASY and MEDIUM questions
            String[] choices = currentQuestion.getChoices();
            for (int i = 0; i < choices.length; i++) {
                RadioButton choiceButton = new RadioButton(getContext());
                choiceButton.setId(i);
                choiceButton.setPadding(16, 0, 0, 0);
                choiceButton.setText(choices[i]);
                choiceButton.setTextColor(getResources().getColor(R.color.white));  // Set text color to white
                choiceButton.setTextSize(18);  // Set text size to 18sp

                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.WRAP_CONTENT,
                        RadioGroup.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 8, 0, 8);  // Set margins

                choiceButton.setLayoutParams(params);  // Apply the margins to the RadioButton
                choicesGroup.addView(choiceButton);  // Add the choice to the RadioGroup
            }

            // Show the choicesGroup and hide the EditText
            choicesGroup.setVisibility(View.VISIBLE);
            identificationAnswer.setVisibility(View.GONE);

        } else if (difficultyLevel == e_Question.Difficulty.HARD) {
            // Load input field for HARD questions
            identificationAnswer.setVisibility(View.VISIBLE);
            choicesGroup.setVisibility(View.GONE);

            // Clear the EditText for new input
            identificationAnswer.setText("");
        }
    }


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
