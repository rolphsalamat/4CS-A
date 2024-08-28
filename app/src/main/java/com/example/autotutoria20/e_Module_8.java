package com.example.autotutoria20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class e_Module_8 {
    // Pre-test questions, choices, and answers for Lesson 1
    public static final String[] pre_test_lesson_1_questions = {
            "What is top-down parsing?",
            "Which parsing technique is used in recursive descent parsing?",
            "What is LL(1) parsing?"
    };

    public static final String[][] pre_test_lesson_1_choices = {
            {"Parsing from the start symbol to the input string", "Parsing from the input string to the start symbol", "Parsing from the middle of the string", "Parsing without using any grammar rules"},
            {"Top-down", "Bottom-up", "Left-to-right", "Right-to-left"},
            {"A top-down parsing method", "A bottom-up parsing method", "A left-to-right parsing method", "A right-to-left parsing method"}
    };

    public static final int[] pre_test_lesson_1_answers = {0, 0, 0};

    // Post-test questions, choices, and answers for Lesson 1
    public static final String[] post_test_lesson_1_questions = {
            "What characterizes top-down parsing?",
            "Which parsing technique is used in LL(1) parsing?",
            "What is the method of LL(1) parsing?"
    };

    public static final String[][] post_test_lesson_1_choices = {
            {"Parsing from the start symbol to the input string", "Parsing from the input string to the start symbol", "Parsing from the middle of the string", "Parsing without using any grammar rules"},
            {"Top-down", "Bottom-up", "Left-to-right", "Right-to-left"},
            {"A top-down method", "A bottom-up method", "A left-to-right method", "A right-to-left method"}
    };

    public static final int[] post_test_lesson_1_answers = {0, 0, 0};

    // Pre-test questions, choices, and answers for Lesson 2
    public static final String[] pre_test_lesson_2_questions = {
            "What is bottom-up parsing?",
            "Which parsing technique is used in shift-reduce parsing?",
            "What is LR parsing?"
    };

    public static final String[][] pre_test_lesson_2_choices = {
            {"Parsing from the input string to the start symbol", "Parsing from the start symbol to the input string", "Parsing from the middle of the string", "Parsing without using any grammar rules"},
            {"Bottom-up", "Top-down", "Left-to-right", "Right-to-left"},
            {"A bottom-up parsing method", "A top-down parsing method", "A left-to-right parsing method", "A right-to-left parsing method"}
    };

    public static final int[] pre_test_lesson_2_answers = {0, 0, 0};

    // Post-test questions, choices, and answers for Lesson 2
    public static final String[] post_test_lesson_2_questions = {
            "What characterizes bottom-up parsing?",
            "Which parsing technique is used in LR parsing?",
            "What is the method of LR parsing?"
    };

    public static final String[][] post_test_lesson_2_choices = {
            {"Parsing from the input string to the start symbol", "Parsing from the start symbol to the input string", "Parsing from the middle of the string", "Parsing without using any grammar rules"},
            {"Bottom-up", "Top-down", "Left-to-right", "Right-to-left"},
            {"A bottom-up method", "A top-down method", "A left-to-right method", "A right-to-left method"}
    };

    public static final int[] post_test_lesson_2_answers = {0, 0, 0};

    // Pre-test questions, choices, and answers for Lesson 3
    public static final String[] pre_test_lesson_3_questions = {
            "What is the CYK algorithm?",
            "What is the characteristic of CYK parsing?",
            "In which form should the grammar be for CYK parsing?"
    };

    public static final String[][] pre_test_lesson_3_choices = {
            {"An algorithm for parsing in CNF", "An algorithm for top-down parsing", "An algorithm for bottom-up parsing", "An algorithm for regular parsing"},
            {"It uses dynamic programming", "It uses backtracking", "It uses recursion", "It uses iterative deepening"},
            {"Chomsky Normal Form (CNF)", "Greibach Normal Form (GNF)", "Backus-Naur Form (BNF)", "Extended Backus-Naur Form (EBNF)"}
    };

    public static final int[] pre_test_lesson_3_answers = {0, 0, 0};

    // Post-test questions, choices, and answers for Lesson 3
    public static final String[] post_test_lesson_3_questions = {
            "What does the CYK algorithm do?",
            "What technique does CYK parsing utilize?",
            "Which form must the grammar be in for CYK parsing?"
    };

    public static final String[][] post_test_lesson_3_choices = {
            {"Parses strings using CNF", "Parses strings using GNF", "Parses strings using BNF", "Parses strings using EBNF"},
            {"Dynamic programming", "Backtracking", "Recursion", "Iterative deepening"},
            {"Chomsky Normal Form (CNF)", "Greibach Normal Form (GNF)", "Backus-Naur Form (BNF)", "Extended Backus-Naur Form (EBNF)"}
    };

    public static final int[] post_test_lesson_3_answers = {0, 0, 0};

    // Method to get pre-test questions for a specific lesson
    public static e_Question[] getPreTestQuestions(String lesson) {
        switch (lesson) {
            case "M1":
                return getPreTestLesson1Questions();
            case "M2":
                return getPreTestLesson2Questions();
            case "M3":
                return getPreTestLesson3Questions();
            default:
                throw new IllegalArgumentException("Invalid lesson: " + lesson);
        }
    }

    // Method to get post-test questions for a specific lesson
    public static e_Question[] getPostTestQuestions(String lesson) {
        switch (lesson) {
            case "M1":
                return getPostTestLesson1Questions();
            case "M2":
                return getPostTestLesson2Questions();
            case "M3":
                return getPostTestLesson3Questions();
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

    // Method to get pre-test questions for Lesson 2
    public static e_Question[] getPreTestLesson2Questions() {
        return getQuestions(pre_test_lesson_2_questions, pre_test_lesson_2_choices, pre_test_lesson_2_answers);
    }

    // Method to get post-test questions for Lesson 2
    public static e_Question[] getPostTestLesson2Questions() {
        return getQuestions(post_test_lesson_2_questions, post_test_lesson_2_choices, post_test_lesson_2_answers);
    }

    // Method to get pre-test questions for Lesson 3
    public static e_Question[] getPreTestLesson3Questions() {
        return getQuestions(pre_test_lesson_3_questions, pre_test_lesson_3_choices, pre_test_lesson_3_answers);
    }

    // Method to get post-test questions for Lesson 3
    public static e_Question[] getPostTestLesson3Questions() {
        return getQuestions(post_test_lesson_3_questions, post_test_lesson_3_choices, post_test_lesson_3_answers);
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
