package com.example.autotutoria20;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class f_3_lesson_post_test extends Fragment {

    private static final String ARG_MODULE = "module";
    private static final String ARG_LESSON = "lesson";
    private static final String ARG_MODE = "mode";

    private LinearLayout imageContainer_postTest;
    private ImageButton hintButton;
    private FrameLayout hint_frameLayout;
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

        hintButton = view.findViewById(R.id.hintButton);
        hint_frameLayout = view.findViewById(R.id.hint_frameLayout);

        hintButton.setEnabled(false);
        hintButton.setVisibility(View.GONE);

        hint_frameLayout.setEnabled(false);
        hint_frameLayout.setVisibility(View.GONE);

        hintButton.setOnClickListener(v -> {

            if (b_main_0_menu.token < 20)
                Toast.makeText(requireContext(), "Insufficient Token", Toast.LENGTH_SHORT).show();
            else {

                Toast.makeText(requireContext(), "Generate Hint", Toast.LENGTH_SHORT).show();

                e_Question currentQuestion = questions[currentQuestionIndex];
                correctAnswer = currentQuestion.getCorrectAnswer_HARD();

                hint = generateHint(correctAnswer, b_main_0_menu_categorize_user.category);

                showHintDialog(hint);

                // deduct 20 from the token from the database

                // Initialize FirebaseAuth
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                FirebaseAuth mAuth;
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                String userId = currentUser.getUid();

                // Reference to the user's document in Firestore
                DocumentReference userDoc = db.collection("users").document(userId);

                // Get the current token count and add 10 tokens
                userDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            // Get the current token count
                            Long currentTokens = documentSnapshot.getLong("Token");

                            // If the current token value is null (if it's the user's first time), set it to 0
                            if (currentTokens == null) {
                                currentTokens = 0L;
                            }

                            // Add 10 tokens to the current count
                            Long newTokenCount = currentTokens - 20;

                            String TAG = "generateHint";

                            Log.e(TAG, "Token before hint: " + currentTokens);
                            Log.e(TAG, "Token after hint: " + newTokenCount);

                            // Update the token count in Firestore
                            userDoc.update("Token", newTokenCount);
                        }
                    }
                });

            }
        });


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

                // enable and show hint button
                hintButton.setEnabled(true);
                hintButton.setVisibility(View.VISIBLE);

                hint_frameLayout.setEnabled(true);
                hint_frameLayout.setVisibility(View.VISIBLE);

            }

            Log.e(TAG, "Post-Test | Load Images");

            // Ensure imageContainer has MATCH_PARENT width and fixed height in XML
            imageContainer_postTest = requireView().findViewById(R.id.image_container);

            // Set container height to 60dp
            int containerHeight = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics());
            imageContainer_postTest.getLayoutParams().height = containerHeight;

            // Set container orientation to horizontal
            imageContainer_postTest.setOrientation(LinearLayout.HORIZONTAL);

            // Total horizontal spacing: 16dp as a base value for margin adjustment
            int baseSpacing = 16;

            // Calculate dynamic margin based on the number of images
            int marginInPixels = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, baseSpacing / Math.max(1, postTestQuestions), getResources().getDisplayMetrics());

            // Iterate through the number of images
            for (int i = 0; i < postTestQuestions; i++) {
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
                Log.e("Post-Test imageContainer", "testAnswer_" + (i+1));

                // Set the image resource or background
                imageView.setImageResource(R.drawable.test_answer_blank);
//            imageView.setColorFilter(Color.GRAY);

                // Add the ImageView to the container
                imageContainer_postTest.addView(imageView);

                Log.e(TAG, "Post-Test | Loading Images...");

            }

            Log.e(TAG, "Post-Test | DONE Load Images");

            TextView items = view.findViewById(R.id.answers_total);

            items.setText("Item: " + c_Lesson_feedback.postTestAttemptAnswers
                    + "/"
                    + postTestQuestions);

            Log.e("f_post_test", "dahil " + difficultyLevel + " ang difficulty, gawin nating " + attemptChances + " ang chances");

//                bktModel.setBKTParameters(difficultyLevel);

            Log.e(TAG, "I will pass these:");
            Log.e(TAG, "module: " + module);
            Log.e(TAG, "lesson: " + lesson);

            // Retrieve post-test questions based on difficulty level
            questions = getPostTestQuestionsBasedOnDifficulty(module, lesson, difficultyLevel);
            loadQuestion();

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

                Log.e("HEY!", "questionsAnswered("+questionsAnswered+") < postTestQuestions("+(postTestQuestions)+")");
                // Ensure that questions answered does not exceed total pre-test questions.
                if (questionsAnswered <= (postTestQuestions)) {

                    // Update feedback and scores based on correctness
                    if (correctAnswer) {
                        if (!x_bkt_algorithm.isLessonFinished) {
                            x_bkt_algorithm.updateTestScore(
                                    isProgressiveMode,
                                    moduleIndex, lessonIndex,
                                    "Post-Test",
                                    c_Lesson_feedback.postTestCorrectAnswers);
                        }
                        c_Lesson_feedback.postTestCorrectAnswers
                                = Math.min(
                                c_Lesson_feedback.postTestCorrectAnswers + 1, postTestQuestions);
                    }

                    if (!x_bkt_algorithm.isLessonFinished)
                        bktModel.updateScore(moduleIndex, lessonIndex, isProgressiveMode, correctAnswer);

                    Log.d("TESTING", "Answer: " + correctAnswer);

                    // Check if we need to move to the next question based on attempts or correctness.
                    if (answerAttempt >= attemptChances || correctAnswer) {

                        String TAG = "EVOPlus";

                        c_Lesson_feedback.postTestAttemptAnswers
                                = Math.min(
                                        c_Lesson_feedback.postTestAttemptAnswers + 1, postTestQuestions);

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
                        Log.d("TESTING", "Post-Test complete!");

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

        d_Lesson_container.startCountdown(requireContext(), "Post-Test", questionsAnswered >= (postTestQuestions+1));

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
        Log.e(TAG, "postTestAttemptAnswers: " + c_Lesson_feedback.postTestAttemptAnswers);

        if (difficultyLevel == e_Question.Difficulty.EASY || difficultyLevel == e_Question.Difficulty.MEDIUM) {
            if (choicesGroup == null) {
                Log.e(TAG, "choicesGroup is null!");
                return false;
            } else {
                int selectedId = choicesGroup.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    e_Question currentQuestion = questions[currentQuestionIndex];
                    isCorrect = (selectedId == currentQuestion.getCorrectAnswer_EASY_MEDIUM());

                    if (answerAttempt >= attemptChances || isCorrect) {
                        // Change button appearance based on correctness
                        int backgroundColor = isCorrect ? Color.GREEN : Color.RED;
                        changeButtonAppearance(backgroundColor, Color.WHITE);

                        // Update target ImageView
                        String targetTag = "testAnswer_" + c_Lesson_feedback.postTestAttemptAnswers;
                        Log.d("TagCheck", "Post-Test | Looking for ImageView with tag: " + targetTag);

                        ImageView targetImageView = imageContainer_postTest.findViewWithTag(targetTag);
                        if (targetImageView != null) {
                            Log.d("ImageViewFound", "Found ImageView with tag: " + targetTag);

                            int imageResource = isCorrect ? R.drawable.test_answer_check : R.drawable.test_answer_cross;
                            targetImageView.setImageResource(imageResource);
                            targetImageView.invalidate(); // Force redraw

                        } else {
                            Log.e("ImageViewError", "No ImageView found with tag: " + targetTag);
                        }
                    }

                    // Display feedback and manage attempts
                    String feedbackMessage = isCorrect ? "Correct!" : "Incorrect, Try Again.";
                    Toast.makeText(getContext(), feedbackMessage, Toast.LENGTH_SHORT).show();

                    if (!isCorrect) {
                        d_Lesson_container.startCountdown(requireContext(), "Post-Test", questionsAnswered >= (postTestQuestions + 1));
                    }

                    return isCorrect;
                } else {
                    Toast.makeText(getContext(), "Please select an answer.", Toast.LENGTH_SHORT).show();
                    d_Lesson_container.startCountdown(requireContext(), "Post-Test", questionsAnswered >= (postTestQuestions + 1));
                    Log.e(TAG, "No answer selected | isCorrect: " + isCorrect);
                    return false;
                }
            }
        } else if (difficultyLevel == e_Question.Difficulty.HARD) {
            e_Question currentQuestion = questions[currentQuestionIndex];
            correctAnswer = currentQuestion.getCorrectAnswer_HARD();

            if (identificationAnswer != null) {
                String inputAnswer = String.valueOf(identificationAnswer.getText()).trim();
                if (!inputAnswer.isEmpty()) {
                    isCorrect = inputAnswer.equalsIgnoreCase(correctAnswer);

                    if (answerAttempt >= attemptChances || isCorrect) {
                        // Change button appearance based on correctness
                        int backgroundColor = isCorrect ? Color.GREEN : Color.RED;
                        changeButtonAppearance(backgroundColor, Color.WHITE);

                        // Update target ImageView
                        String targetTag = "testAnswer_" + c_Lesson_feedback.postTestAttemptAnswers;
                        Log.d("TagCheck", "Post-Test | Looking for ImageView with tag: " + targetTag);

                        ImageView targetImageView = imageContainer_postTest.findViewWithTag(targetTag);
                        if (targetImageView != null) {
                            Log.d("ImageViewFound", "Found ImageView with tag: " + targetTag);

                            int imageResource = isCorrect ? R.drawable.test_answer_check : R.drawable.test_answer_cross;
                            targetImageView.setImageResource(imageResource);
                            targetImageView.invalidate(); // Force redraw
                        } else {
                            Log.e("ImageViewError", "No ImageView found with tag: " + targetTag);
                        }
                    }

                    if (!isCorrect) {
                        d_Lesson_container.startCountdown(requireContext(), "Post-Test", questionsAnswered >= (postTestQuestions + 1));
                        Toast.makeText(getContext(), "Incorrect! Chance: " + answerAttempt + "/" + attemptChances, Toast.LENGTH_SHORT).show();
                        identificationAnswer.setText("");

                        if (answerAttempt == (attemptChances - 1)) {
                            showHintDialog(correctAnswer);
                        }
                    } else {
                        Toast.makeText(getContext(), "Correct!", Toast.LENGTH_SHORT).show();
                    }

                    return isCorrect;
                } else {
                    Toast.makeText(getContext(), "Please enter an answer.", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "No answer provided | isCorrect: " + isCorrect);
                    return false;
                }
            } else {
                Log.e(TAG, "identificationAnswer is null!");
                return false;
            }
        }


        return false; // Default return
    }


    // Method to change button appearance temporarily
    private void changeButtonAppearance(int backgroundColor, int textColor) {
        submitButton.setBackgroundResource(R.drawable.rounded_corners); // Keep rounded corners

        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(backgroundColor);
        drawable.setCornerRadius(40); // Adjust this value for corner radius
        submitButton.setBackground(drawable);
        submitButton.setTextColor(textColor);

        // Revert colors after 1 second
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                submitButton.setBackgroundResource(R.drawable.rounded_corners); // Restore original drawable
                submitButton.setTextColor(Color.BLACK); // Restore original text color
            }
        }, 1000);
    }

    // Method to show hint dialog when needed
    private void showHintDialog(String correctAnswer) {

        // Generate the hint based on the correct answer and category
        String hint = generateHint(correctAnswer, b_main_0_menu_categorize_user.category);

        // Inflate the custom layout
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View customView = inflater.inflate(R.layout.c_lesson_answer_hint, null);

        // Set the hint text to the TextView in the custom layout
        TextView hintTextView = customView.findViewById(R.id.hint);
        Button okayButton = customView.findViewById(R.id.okay);

        hintTextView.setText(hint);

        // Build the AlertDialog with the custom view
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setView(customView); // Set the custom view
        builder.setCancelable(false);

        // Create the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        okayButton.setOnClickListener(v -> {
            dialog.dismiss();
        });

        // Adjust the dialog dimensions
        dialog.getWindow().setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT, // Width: Wrap content
                ViewGroup.LayoutParams.WRAP_CONTENT  // Height: Wrap content
        );


        // Log the generated hint
        Log.e("Generate Hint", "Hint: " + hint);

        d_Lesson_container.d_lesson_container_token -= 20;

        // refresh token in d_lesson_container
        d_Lesson_container.tokenCount.setText("" + d_Lesson_container.d_lesson_container_token);

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
