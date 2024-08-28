package com.example.autotutoria20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class e_Module_2 {
    // Pre-test questions, choices, and answers for Lesson 1
    public static final String[] pre_test_lesson_1_questions = {
            "What is a regular language?",
            "What is a regular expression?",
            "How do you denote the Kleene star operation?"
    };

    public static final String[][] pre_test_lesson_1_choices = {
            {"A language recognized by a finite automaton", "A language recognized by a Turing machine", "A language with context-free grammar", "A natural language"},
            {"A sequence of symbols", "A set of rules", "A pattern describing a set of strings", "A type of automaton"},
            {"*", "+", "?", "."}
    };

    public static final int[] pre_test_lesson_1_answers = {0, 2, 0};

    // Post-test questions, choices, and answers for Lesson 1
    public static final String[] post_test_lesson_1_questions = {
            "Which of the following is a regular language?",
            "What does a regular expression describe?",
            "Which symbol represents the Kleene star operation?"
    };

    public static final String[][] post_test_lesson_1_choices = {
            {"{a, b}", "{a^n b^n | n ≥ 0}", "{0^n 1^n | n ≥ 0}", "English"},
            {"A set of symbols", "A pattern of strings", "A set of rules", "A type of grammar"},
            {"*", "+", "?", "."}
    };

    public static final int[] post_test_lesson_1_answers = {0, 1, 0};

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
