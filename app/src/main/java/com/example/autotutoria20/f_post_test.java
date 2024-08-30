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

public class f_post_test extends Fragment {

    private static final String ARG_MODULE = "module";
    private static final String ARG_LESSON = "lesson";
    private static final String ARG_MODE = "mode";

    private int currentQuestionIndex = 0;
    private e_Question[] questions;
    private TextView questionText;
    private RadioGroup choicesGroup;
    private Button submitButton;
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

    public static f_post_test newInstance(String module, String lesson, String mode) {
        f_post_test fragment = new f_post_test();
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
        return inflater.inflate(R.layout.fragment_post_test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        questionText = view.findViewById(R.id.post_test_question); // Ensure this ID matches the layout
        choicesGroup = view.findViewById(R.id.choices_group); // Ensure this ID matches the layout
        submitButton = view.findViewById(R.id.submit_post_test);

        // Initialize BKT Model instance with appropriate parameters
        bktModel = x_bkt_algorithm.getInstance(0.3, 0.2, 0.1, 0.4);

        if (getArguments() != null) {
            String module = getArguments().getString(ARG_MODULE);
            String lesson = getArguments().getString(ARG_LESSON);

            Log.e("f_post_test.java", "module: " + module);
            Log.e("f_post_test.java", "lesson: " + lesson);

            // Determine if it's Progressive Mode
            if (getArguments() != null) {
                isProgressiveMode = getArguments().getString(ARG_MODE).equals("Progressive Mode");
            }

            int lessonNumber = getLessonIndex(lesson);

            // Initialize BKT Scores
            String collectionPath = isProgressiveMode ? "Progressive Mode" : "Free Use Mode";
            String documentName = "Lesson " + (lessonNumber + 1);
            bktModel.initializeBKTScores(collectionPath, documentName, bktScores -> {
                if (bktScores != null) {
                    Log.d("f_post_test", "BKT Scores initialized: " + bktScores);
                } else {
                    Log.e("f_post_test", "Failed to retrieve BKT Scores");
                }
            });

            // Retrieve questions based on module and lesson
            questions = getPostTestQuestions(module, lesson);

            // Load the first question
            loadQuestion();
        }

        submitButton.setOnClickListener(v -> {

            answerAttempt++;

            if (choicesGroup.getCheckedRadioButtonId() == -1) {
//                Toast.makeText(getContext(), "Please select an answer.", Toast.LENGTH_SHORT).show();
                return;
            } else {
                boolean correctAnswer = checkAnswer();
                Log.e("submitButton.onClick", "correctAnswer: " + correctAnswer);

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

                // Notify the listener
                if (postTestCompleteListener != null) {
                    postTestCompleteListener.onPostTestComplete(correctAnswer);
                }

                // Move to the next question
                if (currentQuestionIndex < questions.length - 1) {
                    currentQuestionIndex++;
                } else {
                    currentQuestionIndex = 0; // Reset to the first question if all are answered
//                    Toast.makeText(getContext(), "Post-test completed!", Toast.LENGTH_SHORT).show();
                    bktModel.logScores();
                }

                // to give student chance to get correct answer before loading another question
                if (answerAttempt >= attemptChances) {
                    loadQuestion(); // Load the next question
                    answerAttempt = 0;
                }
            }


        });
    }

    private e_Question[] getPostTestQuestions(String module, String lesson) {
        String key = module + "_" + lesson;

        switch (key) {
            /* ===== Module 1 ===== */
            case "M1_Lesson 1":
                return e_Module_1.getPostTestLesson1Questions();
            case "M2_Lesson 1":
                return e_Module_1.getPostTestLesson2Questions();
            case "M3_Lesson 1":
                return e_Module_1.getPostTestLesson3Questions();
            case "M4_Lesson 1":
                return e_Module_1.getPostTestLesson4Questions();

            /* ===== Module 2 ===== */
            case "M1_Lesson 2":
                return e_Module_2.getPostTestLesson1Questions();

            /* ===== Module 3 ===== */
            case "M1_Lesson 3":
                return e_Module_3.getPostTestLesson1Questions();
            case "M2_Lesson 3":
                return e_Module_3.getPostTestLesson2Questions();
            case "M3_Lesson 3":
                return e_Module_3.getPostTestLesson3Questions();

            /* ===== Module 4 ===== */
            case "M1_Lesson 4":
                return e_Module_4.getPostTestLesson1Questions();
            case "M2_Lesson 4":
                return e_Module_4.getPostTestLesson2Questions();
            case "M3_Lesson 4":
                return e_Module_4.getPostTestLesson3Questions();

            /* ===== Module 5 ===== */
            case "M1_Lesson 5":
                return e_Module_5.getPostTestLesson1Questions();
            case "M2_Lesson 5":
                return e_Module_5.getPostTestLesson2Questions();
            case "M3_Lesson 5":
                return e_Module_5.getPostTestLesson3Questions();

            /* ===== Module 6 ===== */
            case "M1_Lesson 6":
                return e_Module_6.getPostTestLesson1Questions();
            case "M2_Lesson 6":
                return e_Module_6.getPostTestLesson2Questions();
            case "M3_Lesson 6":
                return e_Module_6.getPostTestLesson3Questions();

            /* ===== Module 7 ===== */
            case "M1_Lesson 7":
                return e_Module_7.getPostTestLesson1Questions();

            /* ===== Module 8 ===== */
            case "M1_Lesson 8":
                return e_Module_8.getPostTestLesson1Questions();
            case "M2_Lesson 8":
                return e_Module_8.getPostTestLesson2Questions();
            case "M3_Lesson 8":
                return e_Module_8.getPostTestLesson3Questions();

            default:
                throw new IllegalArgumentException("Invalid module or lesson: " + key);
        }
    }


    private void loadQuestion() {
        // Reset isCorrect before loading a new question
        isCorrect = false;

        // Clear previous selection
        choicesGroup.clearCheck();

        e_Question currentQuestion = questions[currentQuestionIndex];
        questionText.setText(currentQuestion.getQuestion());

        choicesGroup.removeAllViews();
        String[] choices = currentQuestion.getChoices();
        for (int i = 0; i < choices.length; i++) {
            RadioButton choiceButton = new RadioButton(getContext());
            choiceButton.setId(i);
            choiceButton.setText(choices[i]);
            choicesGroup.addView(choiceButton);
        }
    }

    public boolean checkAnswer() {
        String TAG = "checkAnswer()";

        Log.e(TAG, "checkAnswer() method is CALLED");

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

        int selectedId = choicesGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            e_Question currentQuestion = questions[currentQuestionIndex];
            if (selectedId == currentQuestion.getCorrectAnswer()) {
                Context context = getContext();
//                if (context != null) {
//                    Toast.makeText(context, "Correct!", Toast.LENGTH_SHORT).show();
//                }
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
