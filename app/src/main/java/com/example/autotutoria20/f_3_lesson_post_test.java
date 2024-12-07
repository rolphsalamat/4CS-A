package com.example.autotutoria20;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class f_3_lesson_post_test extends Fragment {

    private static final String ARG_MODULE = "module";
    private static final String ARG_LESSON = "lesson";
    private static final String ARG_MODE = "mode";

    public static int attempts;
    public static double bktScore;
    public static int progress;
    public static int preTestScore;
    public static int postTestScore;
    public static double preTestBktScore;
    public static double postTestBktScore;
    public static String learningStatus;


    private String difficultyString;
    private int moduleIndex;
    private int lessonIndex;
    private LinearLayout imageContainer_postTest;
    private ImageButton hintButton;
    private String[] options; // Assuming there are options for the question
    private FrameLayout hint_frameLayout;
    private int currentQuestionIndex = 0;
    private e_Question[] questions;
    private TextView questionText;
    private e_Question currentQuestion;
    public static RadioGroup choicesGroup;
    public static String hint;
    public static String correctAnswer;
    public static EditText identificationAnswer;
    private TextView total;
    private Button submitButton;
    public static e_Question.Difficulty difficultyLevel;
    private int answerAttempt = 0;
    private int attemptChances = 0;
    private RadioButton choiceButton;
    private int questionsAnswered = 1;
    static int postTestQuestions = 10;
    private boolean isCorrect = false;
    private boolean isProgressiveMode = true; // Default to Progressive Mode
    private AlertDialog hintDialog;
    private AlertDialog correctAnswerDialog;
    private PostTestCompleteListener postTestCompleteListener;

    // BKT Model instance
    private x_bkt_algorithm bktModel;

    // Interface to notify the container activity when post-test is complete
    public interface PostTestCompleteListener {
        void onPostTestComplete(int score, boolean isPassed);
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

        attempts = 0;
        bktScore = 0;
        progress = 0;
        preTestScore = 0;
        postTestScore = 0;
        preTestBktScore = 0.0;
        postTestBktScore = 0.0;
        learningStatus = "UNKNOWN";

        hintButton = view.findViewById(R.id.hintButton);
        hint_frameLayout = view.findViewById(R.id.hint_frameLayout);

        hintButton.setEnabled(false);
        hintButton.setVisibility(View.GONE);

        hint_frameLayout.setEnabled(false);
        hint_frameLayout.setVisibility(View.GONE);

        // Ensure valid indices are used
        moduleIndex = getModuleIndex(getArguments().getString(ARG_MODULE));
        lessonIndex = getLessonIndex(getArguments().getString(ARG_LESSON));

        Log.i("481", "481 | post-test | moduleIndex: " + moduleIndex);
        Log.i("481", "481 | post-test | lessonIndex: " + lessonIndex);


        hintButton.setOnClickListener(v -> {

            if (b_main_0_menu.token < 20)
                Toast.makeText(requireContext(), "Insufficient Token", Toast.LENGTH_SHORT).show();
            else {

                Toast.makeText(requireContext(), "Generate Hint", Toast.LENGTH_SHORT).show();

                e_Question currentQuestion = questions[currentQuestionIndex];
                correctAnswer = currentQuestion.getCorrectAnswer_HARD();

                // Log.i("Call me maybe", "I'm calling generateHint");
                hint = f_3_lesson_post_test_generateHint.generateHint(correctAnswer, b_main_0_menu_categorize_user.category);

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

                            // Log.e(TAG, "Token before hint: " + currentTokens);
                            // Log.e(TAG, "Token after hint: " + newTokenCount);

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

            String TAG = "PEANUT BUTTER";

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

            Log.i(TAG, TAG + " | Module Index: " + moduleIndex);
            Log.i(TAG, TAG + " | Lesson Index: " + lessonIndex);

            x_bkt_algorithm.retrieveAllFields(true, moduleIndex, lessonIndex, moduleData -> {

                if (moduleData != null) {
                    Log.i("MAIN", "All Retrieved Data: " + moduleData);

                    // Access "M1" data (or iterate over all modules if needed)
                    Map<String, Object> data = (Map<String, Object>) moduleData.get(mod);
                    if (data != null) {
                        Log.i("MAIN", mod + " Data: " + data);

                        attempts = data.get("Attempts") instanceof Number
                                ? ((Number) data.get("Attempts")).intValue()
                                : 0;

                        // Extract individual fields
                        bktScore = data.get("BKT Score") instanceof Number
                                ? ((Number) data.get("BKT Score")).doubleValue()
                                : 0.0;

                        progress = data.get("Progress") instanceof Number
                                ? ((Number) data.get("Progress")).intValue()
                                : 0;

                        preTestScore = data.get("Pre-Test Score") instanceof Number
                                ? ((Number) data.get("Pre-Test Score")).intValue()
                                : 0;

                        postTestScore = data.get("Post-Test Score") instanceof Number
                                ? ((Number) data.get("Post-Test Score")).intValue()
                                : 0;

                        preTestBktScore = data.get("Pre-Test BKT Score") instanceof Number
                                ? ((Number) data.get("Pre-Test BKT Score")).doubleValue()
                                : 0.0;

                        postTestBktScore = data.get("Post-Test BKT Score") instanceof Number
                                ? ((Number) data.get("Post-Test BKT Score")).doubleValue()
                                : 0.0;

                        learningStatus = data.get("Learning Status") instanceof String
                                ? ((String) data.get("Learning Status")).toString()
                                : "UNKNOWN";

                        // Log extracted fields
                        Log.i("MAIN", "Attempts: " + attempts);
                        Log.i("MAIN", "BKT Score: " + bktScore);
                        Log.i("MAIN", "Progress: " + progress);
                        Log.i("MAIN", "Pre-Test Score: " + preTestScore);
                        Log.i("MAIN", "Post-Test Score: " + postTestScore);
                        Log.i("MAIN", "Pre-Test BKT Score: " + preTestBktScore);
                        Log.i("MAIN", "Post-Test BKT Score: " + postTestBktScore);
                        Log.i("MAIN", "Learning Status: " + learningStatus);

//                        int attempts, String learningStatus, double preTestScore,
//                        double bktScore, int maxScore) {

                        // Optionally, calculate difficulty level or perform further logic
                        difficultyLevel = bktModel.getDifficultyLevel2(
                                attempts,
                                learningStatus, // Replace with a valid learning status if available
                                preTestScore,
                                bktScore
                        );
                        Log.i("MAIN", "Calculated Difficulty Level: " + difficultyLevel);

                        if (difficultyLevel == e_Question.Difficulty.EASY) {
                            difficultyString = "Post-Test Easy";
//                attemptChances = 1;
                            postTestQuestions = 10;
                            c_Lesson_feedback.postTestQuestions = 10;
                        }
                        else if (difficultyLevel == e_Question.Difficulty.MEDIUM) {
                            difficultyString = "Post-Test Medium";
//                attemptChances = 2;
                            postTestQuestions = 5;
                            c_Lesson_feedback.postTestQuestions = 5;
                        }
                        else if (difficultyLevel == e_Question.Difficulty.HARD) {
                            difficultyString = "Post-Test Hard";
//                attemptChances = 3;
                            postTestQuestions = 3;
                            c_Lesson_feedback.postTestQuestions = 3;

                            // enable and show hint button
                            hintButton.setEnabled(true);
                            hintButton.setVisibility(View.VISIBLE);

                            hint_frameLayout.setEnabled(true);
                            hint_frameLayout.setVisibility(View.VISIBLE);

                        }

                        String formattedModule = "Module " + module.charAt(1);

                        Log.i(TAG, TAG + " | let's call t_TestDataFromDatabase.getRandomQuestionsData");
                        Log.i(TAG, TAG + " | Module: " + formattedModule);
                        Log.i(TAG, TAG + " | Lesson: " + lesson);
                        Log.i(TAG, TAG + " | Difficulty: " + difficultyString);
                        questions = t_TestDataFromDatabase.getRandomQuestionsData(formattedModule, lesson, difficultyString).toArray(new e_Question[0]);

                        Log.i(TAG, TAG + " | let's call getPostTestQuestionsBasedOnDifficulty");
                        Log.i(TAG, TAG + " | Module: " + module);
                        Log.i(TAG, TAG + " | Lesson: " + lesson);
                        Log.i(TAG, TAG + " | Difficulty: " + difficultyLevel);

                        // Retrieve post-test questions based on difficulty level
                        questions = getPostTestQuestionsBasedOnDifficulty(module, lesson, difficultyLevel);

                        // images for Correct : Incorrect Answers.
                        loadCheckAnswers();

                        total.setText("Item: " + c_Lesson_feedback.postTestAttemptAnswers
                                + "/"
                                + postTestQuestions);

                        Log.i(TAG, TAG + " | let's loadQuestion()");
                        loadQuestion();

                        attemptChances = 1;

                    } else {
                        Log.e("MAIN", "No data found for " + mod + ".");
                    }

                } else {
                    Log.e("MAIN", "Failed to retrieve data or document does not exist.");
                }
            });

        }

        submitButton.setOnClickListener(v -> {

            // Log.e("HEY!", "Submit Button Clicked!");

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

//                // Ensure valid indices are used
//                moduleIndex = getModuleIndex(getArguments().getString(ARG_MODULE));
//                lessonIndex = getLessonIndex(getArguments().getString(ARG_LESSON));
//
//                Log.i("481", "481 | post-test | moduleIndex: " + moduleIndex);
//                Log.i("481", "481 | post-test | lessonIndex: " + lessonIndex);

                if (moduleIndex < 0 || lessonIndex < 0) {
                    // Log.e("submitButton.onClick", "Invalid module or lesson index");
                    return;
                }

                // Log.e("HEY!", "questionsAnswered("+questionsAnswered+") < postTestQuestions("+(postTestQuestions)+")");
                // Ensure that questions answered does not exceed total post-test questions.
                if (questionsAnswered <= (postTestQuestions)) {

                    // Update feedback and scores based on correctness
                    if (correctAnswer) {
                        if (!x_bkt_algorithm.isLessonFinished) {
                            if (!d_Lesson_container.isCompleted) {
                                x_bkt_algorithm.updateTestScore(
                                        isProgressiveMode,
                                        moduleIndex, lessonIndex,
                                        "Post-Test",
                                        c_Lesson_feedback.postTestCorrectAnswers);
                            }
                        }
                        c_Lesson_feedback.postTestCorrectAnswers
                                = Math.min(
                                c_Lesson_feedback.postTestCorrectAnswers + 1, postTestQuestions);
                    }

//                    if (!x_bkt_algorithm.isLessonFinished)
                    if (!d_Lesson_container.isCompleted)
                        bktModel.updateBKTScore(moduleIndex, lessonIndex, isProgressiveMode, correctAnswer, answerAttempt);

                    // Log.d("TESTING", "Answer: " + correctAnswer);

                    // Check if we need to move to the next question based on attempts or correctness.
                    if (answerAttempt >= attemptChances || correctAnswer) {

                        String TAG = "EVOPlus";

                        c_Lesson_feedback.postTestAttemptAnswers
                                = Math.min(
                                c_Lesson_feedback.postTestAttemptAnswers + 1, postTestQuestions);

                        // Move to next question or reset if all have been answered.
                        // Log.d(TAG, "currentQuestionInddex["+currentQuestionIndex+"] < questions.length-1["+(questions.length-1)+"])");
                        if (currentQuestionIndex < questions.length - 1) {
                            currentQuestionIndex++;
                            // Log.e(TAG, "currentQuestionIndex++; ["+currentQuestionIndex+"]");
                        } else {
                            currentQuestionIndex = 0; // Reset for new round
                            bktModel.logScores(); // Log scores at reset point
                            // Log.e(TAG, "reset currentQuestionIndex["+currentQuestionIndex+"]");
                        }

                        questionsAnswered++; // Increment answered count
                        // Log.e(TAG, "increment questionsAnswered["+questionsAnswered+"]");

                        // Load next question only if still within post-test limits.
//                         Log.d(TAG, "if (correctAnswer["+correctAnswer+"] || currentQuestionIndex["+currentQuestionIndex
//                                + "] < postTestQuestions["+postTestQuestions+"] && answerAttempt["+answerAttempt+"] >= attemptChances["+attemptChances+"]");
                        if (correctAnswer || currentQuestionIndex < postTestQuestions && answerAttempt >= attemptChances) {
                            loadQuestion(); // Load next question
                            // Log.e(TAG, "loadQuestion();");
                            answerAttempt = 0; // Reset attempts for new question
                            // Log.e(TAG, "reset answerAttempt["+answerAttempt+"]");
                        }
                    } else {
                        // Log.d("TESTING", "Not moving to next question yet.");
                    }

                    //                     == to dapat.. ginawa ko lang >=
                    if (questionsAnswered >= (postTestQuestions+1)) {
                        // Log.d("TESTING", "Post-Test complete!");

//                        c_Lesson_feedback.postTestAttemptAnswers++;

                        // Handle completion of post-test.
                        if (postTestCompleteListener != null) {

                            double bktscore = x_bkt_algorithm.getKnowledge();
                            Log.d("TESTING", "Post-Test BKT Score: " + x_bkt_algorithm.getKnowledge());
                            Log.d("TESTING", "Post-Test Score: " + c_Lesson_feedback.postTestCorrectAnswers);
                            Log.d("TESTING", "Passing Score: " + b_main_0_menu_categorize_user.passingGrade);

                            double bktScore = x_bkt_algorithm.getKnowledge();

                            // Post-Test BKT Score
                            x_bkt_algorithm.updateTestBKTScore(
                                    isProgressiveMode,
                                    moduleIndex, lessonIndex,
                                    "Post-Test",
                                    bktScore);

                            postTestCompleteListener.onPostTestComplete(
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

    private void loadCheckAnswers() {

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
            // Log.e("Post-Test imageContainer", "testAnswer_" + (i+1));

            // Set the image resource or background
            imageView.setImageResource(R.drawable.test_answer_blank);
//            imageView.setColorFilter(Color.GRAY);

            // Add the ImageView to the container
            imageContainer_postTest.addView(imageView);

            // Log.e(TAG, "Post-Test | Loading Images...");

        }

    }

    @Override
    public String toString() {
        return "Question: " + questionText + ", Options: " + Arrays.toString(options) + ", Correct Answer: " + correctAnswer;
    }

    public static e_Question.Difficulty getDifficulty() {
        return difficultyLevel;
    }

    private e_Question[] getPostTestQuestions(String module, String lesson) {
        String key = module + "_" + lesson;

        Log.e("NEVER", "NEVER | Module: " + module);
        Log.e("NEVER", "NEVER | Lesson: " + lesson);
        Log.e("NEVER", "NEVER | Difficulty: " + difficultyLevel);
        Log.e("NEVER", "NEVER | Key: " + key);

        switch (key) {
            /* ===== Module 1 ===== */
            case "M1_Lesson 1":
                // Log.e("NEVER", "I am inside M1_Lesson 1");
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_1_1.get_PostTest_Lesson1_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_1_1.get_PostTest_Lesson1_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_1_1.get_PostTest_Lesson1_Hard_Questions();
                break;
            case "M2_Lesson 1":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_1_2.get_PostTest_Lesson2_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_1_2.get_PostTest_Lesson2_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_1_2.get_PostTest_Lesson2_Hard_Questions();
                break;
            case "M3_Lesson 1":
//                return e_Module_1.getPostTestLesson3Questions();
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_1_3.get_PostTest_Lesson3_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_1_3.get_PostTest_Lesson3_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_1_3.get_PostTest_Lesson3_Hard_Questions();
                break;
            case "M4_Lesson 1":
//                return e_Module_1.getPostTestLesson4Questions();
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_1_4.get_PostTest_Lesson4_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_1_4.get_PostTest_Lesson4_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_1_4.get_PostTest_Lesson4_Hard_Questions();
                break;

                /* ===== Module 2 ===== */
            case "M1_Lesson 2":
//                return e_Module_2_1.getPostTestLesson1Questions();
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_2_1.get_PostTest_Lesson2_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_2_1.get_PostTest_Lesson2_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_2_1.get_PostTest_Lesson2_Hard_Questions();
                break;

                /* ===== Module 3 ===== */
            case "M1_Lesson 3":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_3_1.get_PostTest_Lesson1_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_3_1.get_PostTest_Lesson1_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_3_1.get_PostTest_Lesson1_Hard_Questions();
                break;
            case "M2_Lesson 3":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_3_2.get_PostTest_Lesson2_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_3_2.get_PostTest_Lesson2_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_3_2.get_PostTest_Lesson2_Hard_Questions();
                break;

                /* ===== Module 4 ===== */
            case "M1_Lesson 4":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_4_1.get_PostTest_Lesson1_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_4_1.get_PostTest_Lesson1_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_4_1.get_PostTest_Lesson1_Hard_Questions();
                break;
            case "M2_Lesson 4":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_4_2.get_PostTest_Lesson2_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_4_2.get_PostTest_Lesson2_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_4_2.get_PostTest_Lesson2_Hard_Questions();
                break;

                /* ===== Module 5 ===== */
            case "M1_Lesson 5":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_5_1.get_PostTest_Lesson1_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_5_1.get_PostTest_Lesson1_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_5_1.get_PostTest_Lesson1_Hard_Questions();
                break;
            case "M2_Lesson 5":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_5_2.get_PostTest_Lesson2_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_5_2.get_PostTest_Lesson2_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_5_2.get_PostTest_Lesson2_Hard_Questions();
                break;
            case "M3_Lesson 5":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_5_3.get_PostTest_Lesson3_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_5_3.get_PostTest_Lesson3_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_5_3.get_PostTest_Lesson3_Hard_Questions();
                break;

                /* ===== Module 6 ===== */
            case "M1_Lesson 6":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_6_1.get_PostTest_Lesson1_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_6_1.get_PostTest_Lesson1_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_6_1.get_PostTest_Lesson1_Hard_Questions();
                break;
            case "M2_Lesson 6":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_6_2.get_PostTest_Lesson2_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_6_2.get_PostTest_Lesson2_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_6_2.get_PostTest_Lesson2_Hard_Questions();
                break;
            case "M3_Lesson 6":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_6_3.get_PostTest_Lesson3_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_6_3.get_PostTest_Lesson3_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_6_3.get_PostTest_Lesson3_Hard_Questions();
                break;

                /* ===== Module 7 ===== */
            case "M1_Lesson 7":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_7_1.get_PostTest_Lesson1_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_7_1.get_PostTest_Lesson1_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_7_1.get_PostTest_Lesson1_Hard_Questions();
                break;

                /* ===== Module 8 ===== */
            case "M1_Lesson 8":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_8_1.get_PostTest_Lesson1_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_8_1.get_PostTest_Lesson1_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_8_1.get_PostTest_Lesson1_Hard_Questions();
                break;
            case "M2_Lesson 8":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_8_2.get_PostTest_Lesson2_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_8_2.get_PostTest_Lesson2_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_8_2.get_PostTest_Lesson2_Hard_Questions();
                break;
            case "M3_Lesson 8":
                if (difficultyLevel == e_Question.Difficulty.EASY)
                    return e_Module_8_3.get_PostTest_Lesson3_Easy_Questions();
                else if (difficultyLevel == e_Question.Difficulty.MEDIUM)
                    return e_Module_8_3.get_PostTest_Lesson3_Medium_Questions();
                else if (difficultyLevel == e_Question.Difficulty.HARD)
                    return e_Module_8_3.get_PostTest_Lesson3_Hard_Questions();
                break;

            default:
                throw new IllegalArgumentException("Invalid module or lesson: " + key);
        }
        return new e_Question[0];
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

        String TAG = "getPostTestQuestionsBasedOnDifficulty";

        Log.i(TAG, "MEG | Module: " + module);
        Log.i(TAG, "MEG | Lesson: " + lesson);
        Log.i(TAG, "MEG | Difficulty: " + difficulty);

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
            // Log.e("f_post_test", "No questions match the difficulty level.");
            return allQuestions;
        }

        return filteredQuestions.toArray(new e_Question[0]);
    }

    private void loadQuestion() {
        String TAG = "loadQuestion";

        d_Lesson_container.startCountdown(requireContext(), "Post-Test", questionsAnswered >= (postTestQuestions+1));

        hint = ""; // reset hint

        // Log.e("Generate Hint", "Cleared Hint: " + hint);

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

//         Log.e(TAG, "Loading question at index: " + currentQuestionIndex);

        // Reset isCorrect before loading a new question
        isCorrect = false;

        // Get the current question
        currentQuestion = questions[currentQuestionIndex];
         Log.e(TAG, "ACER | Current question: " + currentQuestion.getQuestion());

         if (difficultyLevel != e_Question.Difficulty.HARD)
             Log.e(TAG, "ACER | Current Choices: " + currentQuestion.getChoices());

         Log.e(TAG, "ACER | Current answer: " + currentQuestion.getCorrectAnswer_EASY_MEDIUM());

        // Clear previous selection
        choicesGroup.clearCheck();
        identificationAnswer.setText("");

        questionText.setText(currentQuestion.getQuestion());

        Log.d(TAG, "Leakproof | Difficulty: " + difficultyLevel);
        Log.i(TAG, "Leakproof | Question: " + currentQuestion.getQuestion());

        if (!(difficultyLevel == e_Question.Difficulty.HARD))
            Log.i(TAG, "Leakproof | Choices: " + currentQuestion.getChoices());

        Log.i(TAG, "Leakproof | HARD Answer: " + currentQuestion.getCorrectAnswer_HARD());
        Log.i(TAG, "Leakproof | EASY|MEDIUM Answer: " + currentQuestion.getCorrectAnswer_EASY_MEDIUM());
        Log.i(TAG, "Leakproof | Pre-Test Answer: " + currentQuestion.getCorrectAnswer_preTest());

        choicesGroup.removeAllViews();

        // Render buttons for choices or input field for HARD difficulty
        Context context = getContext();
        if (difficultyLevel == e_Question.Difficulty.EASY || difficultyLevel == e_Question.Difficulty.MEDIUM) {
            List<String> choices = currentQuestion.getChoices();
            submitButton.setEnabled(false);
            submitButton.setVisibility(View.GONE);

            // Check if choices is null or empty
            if (choices != null && !choices.isEmpty()) {

// Convert 8dp to pixels
                int paddingInDp = 12;
                int paddingInPx = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, paddingInDp, context.getResources().getDisplayMetrics()
                );

                for (int i = 0; i < choices.size(); i++) {
                    Button choiceButton = new Button(context);
                    choiceButton.setId(i);
                    choiceButton.setText(choices.get(i));
                    choiceButton.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
                    choiceButton.setTextSize(16);
                    choiceButton.setPadding(paddingInPx, paddingInPx, paddingInPx, paddingInPx);
                    choiceButton.setBackgroundResource(R.drawable.rounded_corners);
                    choiceButton.setTextColor(getResources().getColor(R.color.black));
                    choiceButton.setEnabled(false);
                    choiceButton.setVisibility(View.GONE);

                    // Create LayoutParams for margin settings
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(0, 8, 0, 8); // Set margins (left, top, right, bottom) in pixels
                    choiceButton.setLayoutParams(params);

                    // Add click listener
                    choiceButton.setOnClickListener(v -> {
                        checkAnswer(choiceButton.getId(), null);
//                        checkAnswer();
                        choiceButton.setEnabled(false);
                    });

                    // Add to layout
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


//                for (int i = 0; i < choices.size(); i++) {
//                    choiceButton = new RadioButton(getContext());
//                    choiceButton.setId(i);
//                    choiceButton.setText(choices.get(i));
//                    choiceButton.setTextColor(getResources().getColor(R.color.white));
//                    choiceButton.setTextSize(18);
//
//                    RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
//                            RadioGroup.LayoutParams.WRAP_CONTENT,
//                            RadioGroup.LayoutParams.WRAP_CONTENT
//                    );
//                    params.setMargins(0, 8, 0, 8);
//
//                    choiceButton.setLayoutParams(params);
//                    choicesGroup.addView(choiceButton);
//                }
            } else {
                Log.i(TAG, "choices is empty or null!");
            }

            choicesGroup.setVisibility(View.VISIBLE);
            identificationAnswer.setVisibility(View.GONE);
        }

        // Original Code
//        if (difficultyLevel == e_Question.Difficulty.EASY || difficultyLevel == e_Question.Difficulty.MEDIUM) {
//            List<String> choices = currentQuestion.getChoices();
//
//            if (!choices.isEmpty())
//                for (int i = 0; i < choices.size(); i++) {
//                    RadioButton choiceButton = new RadioButton(getContext());
//                    choiceButton.setId(i);
//                    choiceButton.setText(choices.get(i));
//                    choiceButton.setTextColor(getResources().getColor(R.color.white));
//                    choiceButton.setTextSize(18);
//
//                    RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
//                            RadioGroup.LayoutParams.WRAP_CONTENT,
//                            RadioGroup.LayoutParams.WRAP_CONTENT
//                    );
//                    params.setMargins(0, 8, 0, 8);
//
//                    choiceButton.setLayoutParams(params);
//                    choicesGroup.addView(choiceButton);
//                }
//            else
//                // Log.i(TAG, "choices is empty!");
//
//            choicesGroup.setVisibility(View.VISIBLE);
//            identificationAnswer.setVisibility(View.GONE);
//
//        }

        else if (difficultyLevel == e_Question.Difficulty.HARD) {
            identificationAnswer.setVisibility(View.VISIBLE);
            choicesGroup.setVisibility(View.GONE);
            identificationAnswer.setText("");
        }
    }

    private void checkAnswer(int selectedId, String answer) {
        boolean isCorrect = false;

        if (difficultyLevel == e_Question.Difficulty.EASY || difficultyLevel == e_Question.Difficulty.MEDIUM) {
            if (choicesGroup == null) {
                Log.e("checkAnswer", "choicesGroup is null!");
                return;
            }

            if (selectedId != -1) {
                e_Question currentQuestion = questions[currentQuestionIndex];

                int correctAnswer = currentQuestion.getCorrectAnswer_EASY_MEDIUM();
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
                                    "Post-Test",
                                    c_Lesson_feedback.postTestCorrectAnswers);
                        }
                    }
                    c_Lesson_feedback.postTestCorrectAnswers = Math.min(c_Lesson_feedback.postTestCorrectAnswers + 1, postTestQuestions);
                } else {
                    toastMessage = "Incorrect answer.";
                }


//                        answerAttempt >= attemptChances ||
//                if (isCorrect) {
//                    int backgroundColor = isCorrect ? Color.GREEN : Color.RED;
//
//                    Button selectedButton = choicesGroup.findViewById(checkedId);
//                    if (selectedButton != null) {
//                        selectedButton.setBackgroundColor(backgroundColor);
//                    } else {
//                        Log.e("checkAnswer", "Selected RadioButton is null!");
//                    }
//
//                    String targetTag = "testAnswer_" + c_Lesson_feedback.postTestAttemptAnswers;
//                    ImageView targetImageView = imageContainer_postTest.findViewWithTag(targetTag);
//                    if (targetImageView != null) {
//                        int imageResource = isCorrect ? R.drawable.test_answer_check : R.drawable.test_answer_cross;
//                        targetImageView.setImageResource(imageResource);
//                        targetImageView.invalidate();
//                    } else {
//                        Log.e("ImageViewError", "No ImageView found with tag: " + targetTag);
//                    }
//                }

                Toast.makeText(getContext(), toastMessage, Toast.LENGTH_SHORT).show();

                updateAnswerFeedback(isCorrect);
                updateChoiceButton(isCorrect, selectedId, currentQuestion.getCorrectAnswer_EASY_MEDIUM());

                return;
            } else {
                Toast.makeText(getContext(), "Please select an answer.", Toast.LENGTH_SHORT).show();
                d_Lesson_container.startCountdown(requireContext(), "Post-Test", questionsAnswered >= (postTestQuestions + 1));
                return;
            }

        } else if (difficultyLevel == e_Question.Difficulty.HARD) {
            e_Question currentQuestion = questions[currentQuestionIndex];
//            String correctAnswer_Hard = currentQuestion.getCorrectAnswer_HARD();

            if (identificationAnswer != null) {
//                String inputAnswer = String.valueOf(identificationAnswer.getText()).trim();

                Log.i("HARD POST-TEST", "HEY | answer: " + answer);
                Log.i("HARD POST-TEST", "HEY | correct answer: " + correctAnswer);

                if (!answer.isEmpty()) {
                    isCorrect = answer.equalsIgnoreCase(correctAnswer);

                    int backgroundColor = isCorrect ? Color.GREEN : Color.RED;
                    changeButtonAppearance(backgroundColor, Color.WHITE);

                    if (answerAttempt >= attemptChances || isCorrect) {

                        updateAnswerFeedback(isCorrect);

//                        String targetTag = "testAnswer_" + c_Lesson_feedback.postTestAttemptAnswers;
//                        ImageView targetImageView = imageContainer_postTest.findViewWithTag(targetTag);
//                        if (targetImageView != null) {
//                            int imageResource = isCorrect ? R.drawable.test_answer_check : R.drawable.test_answer_cross;
//                            targetImageView.setImageResource(imageResource);
//                            targetImageView.invalidate();
//                        } else {
//                            Log.e("ImageViewError", "No ImageView found with tag: " + targetTag);
//                        }
                    }

                    if (!isCorrect) {
                        Toast.makeText(getContext(), "Incorrect!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Correct!", Toast.LENGTH_SHORT).show();
                    }

                    d_Lesson_container.startCountdown(requireContext(), "Post-Test", questionsAnswered >= (postTestQuestions + 1));
//                    Toast.makeText(getContext(), "Incorrect! Chance: " + answerAttempt + "/" + attemptChances, Toast.LENGTH_SHORT).show();
                    identificationAnswer.setText("");

                    if (c_Lesson_feedback.postTestAttemptAnswers <= postTestQuestions)
                        total.setText("Item: "
                                + c_Lesson_feedback.postTestAttemptAnswers
                                //                    + questionsAnswered
                                + "/"
                                +  postTestQuestions);

                    return;
                } else {
                    Toast.makeText(getContext(), "Please enter an answer.", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                Log.e("checkAnswer", "identificationAnswer is null!");
                return;
            }
        }
    }

    //    updateChoiceButton(isCorrect, checkedId, currentQuestion.getCorrectAnswer_EASY_MEDIUM());
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

    public void submitButton(boolean correctAnswer) {

        if (questionsAnswered <= postTestQuestions) { // <= talaga to

//                    Log.e("TAG", "ROP CHECK THIS: | isLessonFinished: " + x_bkt_algorithm.isLessonFinished);

//                    if (!x_bkt_algorithm.isLessonFinished)
//                        bktModel.updateScore(moduleIndex, lessonIndex, isProgressiveMode, correctAnswer, answerAttempt);

            if (!d_Lesson_container.isCompleted)
                bktModel.updateBKTScore(moduleIndex, lessonIndex, isProgressiveMode, correctAnswer, answerAttempt);

            // Check if we need to move to the next question based on attempts or correctness.
//            if (answerAttempt >= attemptChances || correctAnswer) {

            String TAG = "EVOPlus";

            c_Lesson_feedback.postTestAttemptAnswers++;

            Log.i(TAG, "Roadshow | postTestAttemptAnswers: " + c_Lesson_feedback.postTestAttemptAnswers);
            Log.i(TAG, "Roadshow | questionsAnswered: " + questionsAnswered);
            Log.i(TAG, "Roadshow | postTestQuestions: " + postTestQuestions);

            if (questionsAnswered <= postTestQuestions) {
                currentQuestionIndex++;
                loadQuestion(); // Always load the next question after updating the index
                answerAttempt = 0;
            } else {

                double bktScore = x_bkt_algorithm.getKnowledge();

                // Handle post-test completion
//                postTestCompleteListener.onPostTestComplete(c_Lesson_feedback.postTestCorrectAnswers);
                postTestCompleteListener.onPostTestComplete(
                        c_Lesson_feedback.postTestCorrectAnswers,
                        bktScore >= b_main_0_menu_categorize_user.passingGrade
                );
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
//                        + "] < postTestQuestions["+postTestQuestions+"] && answerAttempt["+answerAttempt+"] >= attemptChances["+attemptChances+"]");
            if (correctAnswer || currentQuestionIndex < postTestQuestions && answerAttempt >= attemptChances) {

                if (!(questionsAnswered == (postTestQuestions+1)))
                    loadQuestion();

                answerAttempt = 0;

            }

//            }

            // may plus 1 to kanina
            if (questionsAnswered == (postTestQuestions+1)) {
                Log.d("TESTING", "Post-test complete!");

                // Handle completion of pre-test.
                if (postTestCompleteListener != null) {
                    Log.d("TESTING", "Post-Test BKT Score: " + x_bkt_algorithm.getKnowledge());
                    Log.d("TESTING", "Post-Test Score: " + c_Lesson_feedback.postTestCorrectAnswers);
                    double bktScore = x_bkt_algorithm.getKnowledge();

                    // Pre-Test BKT Score
                    x_bkt_algorithm.updateTestBKTScore(
                            isProgressiveMode,
                            moduleIndex, lessonIndex,
                            "Post-Test",
                            bktScore
                    );

                    postTestCompleteListener.onPostTestComplete(
                            c_Lesson_feedback.postTestCorrectAnswers,
                            bktScore >= b_main_0_menu_categorize_user.passingGrade
                    );

                    c_Lesson_feedback.printResult("Post-Test");
                    d_Lesson_container.isPostTestComplete = true;

                }
            }

        } else {

            String feedback = correctAnswer ? "Correct!" : "Incorrect :(";

            // Logic for after post-test is complete
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

        if (c_Lesson_feedback.postTestAttemptAnswers <= postTestQuestions)
            total.setText("Item: "
                    + c_Lesson_feedback.postTestAttemptAnswers
//                    + questionsAnswered
                    + "/"
                    +  postTestQuestions);

    }


    private void setButtonBackgroundWithColor(Button button, int color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color); // Set the desired color
        drawable.setCornerRadius(40); // Adjust for your rounded corners
        button.setBackground(drawable); // Apply the drawable to the button
    }

    private void updateAnswerFeedback(boolean isCorrect) {

        String targetTag = "testAnswer_" + (c_Lesson_feedback.postTestAttemptAnswers);
        ImageView targetImageView = imageContainer_postTest.findViewWithTag(targetTag);

        if (targetImageView != null) {
            if (isCorrect) {
                targetImageView.setImageResource(R.drawable.test_answer_check);
            } else {
                targetImageView.setImageResource(R.drawable.test_answer_cross);
            }
        }
    }


    public boolean checkAnswer() {
        String TAG = "checkAnswer()";
        // Log.e(TAG, "checkAnswer() method is CALLED");
        // Log.e(TAG, "postTestAttemptAnswers: " + c_Lesson_feedback.postTestAttemptAnswers);

        if (difficultyLevel == e_Question.Difficulty.EASY || difficultyLevel == e_Question.Difficulty.MEDIUM) {
            if (choicesGroup == null) {
                // Log.e(TAG, "choicesGroup is null!");
                return false;
            } else {
                int selectedId = choicesGroup.getCheckedRadioButtonId();
                if (selectedId != -1) {

                    e_Question currentQuestion = questions[currentQuestionIndex];
                    int correctAnswer = currentQuestion.getCorrectAnswer_EASY_MEDIUM();
                    isCorrect = (selectedId == correctAnswer);

                    if (answerAttempt >= attemptChances || isCorrect) {
                        // Change button appearance based on correctness
                        int backgroundColor = isCorrect ? Color.GREEN : Color.RED;
                        changeButtonAppearance(backgroundColor, Color.WHITE);

                        // Update target ImageView
                        String targetTag = "testAnswer_" + c_Lesson_feedback.postTestAttemptAnswers;
                        // Log.d("TagCheck", "Post-Test | Looking for ImageView with tag: " + targetTag);

                        ImageView targetImageView = imageContainer_postTest.findViewWithTag(targetTag);

                        if (targetImageView != null) {
                            int imageResource = isCorrect ? R.drawable.test_answer_check : R.drawable.test_answer_cross;
                            targetImageView.setImageResource(imageResource);
                            targetImageView.invalidate(); // Force redraw
                        } else {
                            Log.e("ImageViewError", "No ImageView found with tag: " + targetTag);
                        }

                    } else {

                        choiceButton.setBackgroundColor(Color.RED);

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
                    // Log.e(TAG, "No answer selected | isCorrect: " + isCorrect);
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

                    // Change button appearance based on correctness
                    int backgroundColor = isCorrect ? Color.GREEN : Color.RED;
                    changeButtonAppearance(backgroundColor, Color.WHITE);

                    // Log.i(TAG, "ELECTRONICS | correctAnswer: " + correctAnswer);
                    // Log.i(TAG, "ELECTRONICS | Answer: " + inputAnswer);

                    if (answerAttempt >= attemptChances || isCorrect) {

                        // Update target ImageView
                        String targetTag = "testAnswer_" + c_Lesson_feedback.postTestAttemptAnswers;
                        // Log.d("TagCheck", "Post-Test | Looking for ImageView with tag: " + targetTag);

                        ImageView targetImageView = imageContainer_postTest.findViewWithTag(targetTag);
                        if (targetImageView != null) {
                            // Log.d("ImageViewFound", "Found ImageView with tag: " + targetTag);

                            int imageResource = isCorrect ? R.drawable.test_answer_check : R.drawable.test_answer_cross;
                            targetImageView.setImageResource(imageResource);
                            targetImageView.invalidate(); // Force redraw
                        } else {
                            // Log.e("ImageViewError", "No ImageView found with tag: " + targetTag);
                        }
                    }

                    if (!isCorrect) {
                        d_Lesson_container.startCountdown(requireContext(), "Post-Test", questionsAnswered >= (postTestQuestions + 1));
                        Toast.makeText(getContext(), "Incorrect! Chance: " + answerAttempt + "/" + attemptChances, Toast.LENGTH_SHORT).show();
                        identificationAnswer.setText("");

//                        if (answerAttempt == (attemptChances - 1)) {
//                            showHintDialog(correctAnswer);
//                        }

                    } else {
                        Toast.makeText(getContext(), "Correct!", Toast.LENGTH_SHORT).show();
                    }

                    return isCorrect;
                } else {
                    Toast.makeText(getContext(), "Please enter an answer.", Toast.LENGTH_SHORT).show();
                    // Log.e(TAG, "No answer provided | isCorrect: " + isCorrect);
                    return false;
                }
            } else {
                // Log.e(TAG, "identificationAnswer is null!");
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

    private void showCorrecAnswerDialog(String correctAnswer) {

        String TAG = "showCorrecAnswerDialog()";

        // Inflate the custom layout
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View customView = inflater.inflate(R.layout.c_lesson_answer_hint, null);

        // Set the hint text to the TextView in the custom layout
        TextView hintTextView = customView.findViewById(R.id.hint);
        Button okayButton = customView.findViewById(R.id.okay);

        hintTextView.setText(correctAnswer);

        // Build the AlertDialog with the custom view
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setView(customView); // Set the custom view
        builder.setCancelable(false);

        // Create the dialog
        correctAnswerDialog = builder.create();
        correctAnswerDialog.show();

        okayButton.setOnClickListener(v -> {
            correctAnswerDialog.dismiss();
        });

        // Adjust the dialog dimensions
        correctAnswerDialog.getWindow().setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT, // Width: Wrap content
                ViewGroup.LayoutParams.WRAP_CONTENT  // Height: Wrap content
        );

    }

    // Method to show hint dialog when needed
    private void showHintDialog(String correctAnswer) {

        String TAG = "showHintDialog()";

        // Log.i(TAG, "Correct Answer: " + correctAnswer);
//        // Generate the hint based on the correct answer and category
//        String hint = f_3_lesson_post_test_generateHint.generateHint(correctAnswer, b_main_0_menu_categorize_user.category);

        // Inflate the custom layout
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View customView = inflater.inflate(R.layout.c_lesson_answer_hint, null);

        // Set the hint text to the TextView in the custom layout
        TextView hintTextView = customView.findViewById(R.id.hint);
        Button okayButton = customView.findViewById(R.id.okay);

        hintTextView.setText(correctAnswer);

        // Build the AlertDialog with the custom view
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setView(customView); // Set the custom view
        builder.setCancelable(false);

        // Create the dialog
        hintDialog = builder.create();
        hintDialog.show();

        okayButton.setOnClickListener(v -> {
            hintDialog.dismiss();
        });

        // Adjust the dialog dimensions
        hintDialog.getWindow().setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT, // Width: Wrap content
                ViewGroup.LayoutParams.WRAP_CONTENT  // Height: Wrap content
        );


        // Log the generated hint
        // Log.e("Generate Hint", "Hint: " + hint);

        if (d_Lesson_container.d_lesson_container_token >= 20) {
            f_3_lesson_post_test_generateHint.takeToken(false, 20);
            d_Lesson_container.d_lesson_container_token -= 20;
            // refresh token in d_lesson_container
            d_Lesson_container.tokenCount.setText("" + d_Lesson_container.d_lesson_container_token);
        } else{
            Toast.makeText(requireContext(), "Insufficient Funds", Toast.LENGTH_SHORT).show();
        }


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
                // Log.e("getLessonIndex", "Invalid lesson: " + lesson);
                throw new IllegalArgumentException("Invalid lesson: " + lesson);
        }
    }
}