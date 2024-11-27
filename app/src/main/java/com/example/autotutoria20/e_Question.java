package com.example.autotutoria20;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class e_Question {
    private String question;
    private Difficulty difficulty; // Field to store difficulty level
    private List<String> choices; // Store choices as List<String>
    private String answer;
    private int correctAnswer_preTest; // Assuming this is an index or identifier for correct answer
    private int correctAnswer_EASY_MEDIUM;
    private String correctAnswer_HARD; // Store correct answer for HARD difficulty
    private QuestionType type;  // New field to store the type of question

    public enum Difficulty {
        EASY, MEDIUM, HARD
    }

    public enum QuestionType {
        TRUE_FALSE,
        MULTIPLE_CHOICE,
        IDENTIFICATION
    }


    // Pre-Test || Post-Test Medium
    public e_Question(String question, List<String> choices, int correctAnswer) {
        String TAG = "e_Question() constructor for Pre-Test or Post-Test Medium";

        this.question = question;
        this.choices = choices;
        this.correctAnswer_preTest = correctAnswer; // Store the index of the correct answer
        this.correctAnswer_EASY_MEDIUM = correctAnswer;

        Log.i(TAG, "Question: " + question);
        Log.i(TAG, "Choices: " + choices);
        Log.i(TAG, "Answer: " + correctAnswer);
    }

    // Difficulty.HARD
    public e_Question(String question, String answer, Difficulty level) {
        String TAG = "e_Question() constructor for Pre-Test Hard";

        this.question = question;
        this.correctAnswer_HARD = answer;
        this.difficulty = level;

        Log.i(TAG,"Ryan nandito ako");
        Log.i(TAG, "Question: " + question);
        Log.i(TAG, "Answer: " + answer);
        Log.i(TAG, "Difficulty: " + level);
    }


    public e_Question(String question, List<String> choices, int correctAnswer, Difficulty difficulty) {
        this.question = question;
        this.choices = choices;
        this.correctAnswer_EASY_MEDIUM = correctAnswer;
        this.difficulty = difficulty;

        // Set question type based on difficulty
        switch (difficulty) {
            case EASY:
                this.type = QuestionType.TRUE_FALSE;
                break;
            case MEDIUM:
                this.type = QuestionType.MULTIPLE_CHOICE;
                break;
        }
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getChoices() {
        return choices;
    }

    public Difficulty getDifficulty() {
        return difficulty; // Getter for difficulty
    }

    public int getCorrectAnswer_preTest() {
        return correctAnswer_preTest; // Return the index or identifier for the correct answer
    }

    public int getCorrectAnswer_EASY_MEDIUM() {
        return correctAnswer_EASY_MEDIUM;
    }

    public String getCorrectAnswer_HARD() {
        return correctAnswer_HARD; // Return the correct answer for HARD difficulty
    }
}