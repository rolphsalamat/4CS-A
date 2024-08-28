package com.example.autotutoria20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class e_Module_7 {
    // Pre-test questions, choices, and answers for Lesson 1
    public static final String[] pre_test_lesson_1_questions = {
            "What is Chomsky Normal Form (CNF)?",
            "What is the characteristic of a CNF grammar?",
            "Which of the following is a rule in CNF?"
    };

    public static final String[][] pre_test_lesson_1_choices = {
            {"A form where each rule is in a specific format", "A form where each rule is context-free", "A form where each rule is context-sensitive", "A form where each rule is regular"},
            {"Each production rule has exactly two non-terminal symbols or one terminal symbol", "Each production rule has one non-terminal symbol", "Each production rule has one terminal symbol", "Each production rule has exactly three non-terminal symbols"},
            {"A -> BC", "A -> aB", "A -> B", "A -> aBC"}
    };

    public static final int[] pre_test_lesson_1_answers = {0, 0, 0};

    // Post-test questions, choices, and answers for Lesson 1
    public static final String[] post_test_lesson_1_questions = {
            "What defines Chomsky Normal Form?",
            "What is a characteristic of CNF?",
            "Which production rule follows CNF?"
    };

    public static final String[][] post_test_lesson_1_choices = {
            {"Specific format for production rules", "Context-free production rules", "Context-sensitive production rules", "Regular production rules"},
            {"Two non-terminal symbols or one terminal symbol", "One non-terminal symbol", "One terminal symbol", "Three non-terminal symbols"},
            {"A -> BC", "A -> B", "A -> aB", "A -> aBC"}
    };

    public static final int[] post_test_lesson_1_answers = {0, 0, 0};

    // Method to get pre-test questions for a specific lesson
    public static e_Question[] getPreTestQuestions(String lesson) {
        switch (lesson) {
            case "M1":
                return getPreTestLesson1Questions();
            default:
                throw new IllegalArgumentException("Invalid lesson: " + lesson);
        }
    }

    // Method to get post-test questions for a specific lesson
    public static e_Question[] getPostTestQuestions(String lesson) {
        switch (lesson) {
            case "M1":
                return getPostTestLesson1Questions();
            default:
                throw new IllegalArgumentException("Invalid lesson: " + lesson);
        }
    }

    // Method to get pre-test questions for Lesson 1
    public static e_Question[] getPreTestLesson1Questions() {
        return getQuestions(pre_test_lesson_1_questions, pre_test_lesson_1_choices, pre_test_lesson_1_answers);
    }

    // Method to get post-test questions for Lesson 1
    public static e_Question[] getPostTestLesson1Questions() {
        return getQuestions(post_test_lesson_1_questions, post_test_lesson_1_choices, post_test_lesson_1_answers);
    }

    // Method to convert string arrays to e_Question objects
    private static e_Question[] getQuestions(String[] questions, String[][] choices, int[] answers) {
        List<e_Question> questionList = new ArrayList<>();
        for (int i = 0; i < questions.length; i++) {
            questionList.add(new e_Question(questions[i], choices[i], answers[i], e_Question.Difficulty.EASY)); // Adjust difficulty if needed
        }

        // Shuffle the list to randomize the order of the questions
        Collections.shuffle(questionList);

        // Convert the list back to an array
        return questionList.toArray(new e_Question[0]);
    }
}
