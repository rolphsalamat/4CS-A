package com.example.autotutoria20;

public class e_Module_6 {
    // Pre-test questions, choices, and answers for Lesson 1
    public static final String[] pre_test_lesson_1_questions = {
            "What is an ambiguous grammar?",
            "What is an unambiguous grammar?",
            "What is inherent ambiguity?"
    };

    public static final String[][] pre_test_lesson_1_choices = {
            {"A grammar that generates multiple parse trees for some strings", "A grammar that generates a single parse tree for all strings", "A grammar that generates no parse trees", "A grammar that generates multiple strings"},
            {"A grammar that generates a single parse tree for all strings", "A grammar that generates multiple parse trees for some strings", "A grammar that generates no parse trees", "A grammar that generates multiple strings"},
            {"Ambiguity that cannot be resolved", "Ambiguity that can be resolved", "Ambiguity in lexical analysis", "Ambiguity in phonetic analysis"}
    };

    public static final int[] pre_test_lesson_1_answers = {0, 0, 0};

    // Post-test questions, choices, and answers for Lesson 1
    public static final String[] post_test_lesson_1_questions = {
            "What does ambiguous grammar lead to?",
            "What is the feature of an unambiguous grammar?",
            "What does inherent ambiguity mean?"
    };

    public static final String[][] post_test_lesson_1_choices = {
            {"Multiple parse trees", "Single parse tree", "No parse tree", "Multiple strings"},
            {"Single parse tree for all strings", "Multiple parse trees for some strings", "No parse tree", "Multiple strings"},
            {"Unresolvable ambiguity", "Resolvable ambiguity", "Lexical ambiguity", "Phonetic ambiguity"}
    };

    public static final int[] post_test_lesson_1_answers = {0, 0, 0};

    // Pre-test questions, choices, and answers for Lesson 2
    public static final String[] pre_test_lesson_2_questions = {
            "What makes a grammar unambiguous?",
            "How does an unambiguous grammar differ from an ambiguous one?",
            "Why is unambiguous grammar preferred in programming languages?"
    };

    public static final String[][] pre_test_lesson_2_choices = {
            {"It generates a single parse tree for each string", "It generates multiple parse trees for some strings", "It generates no parse trees", "It generates multiple strings"},
            {"It generates a single parse tree for all strings", "It generates multiple parse trees for some strings", "It generates a single string for all trees", "It generates multiple strings"},
            {"Because it ensures consistent interpretation", "Because it allows multiple interpretations", "Because it is easier to parse", "Because it reduces the complexity"}
    };

    public static final int[] pre_test_lesson_2_answers = {0, 0, 0};

    // Post-test questions, choices, and answers for Lesson 2
    public static final String[] post_test_lesson_2_questions = {
            "What is the key feature of an unambiguous grammar?",
            "Why is an unambiguous grammar important in parsing?",
            "How does unambiguous grammar affect compiler design?"
    };

    public static final String[][] post_test_lesson_2_choices = {
            {"It generates a single parse tree for each string", "It generates multiple parse trees for each string", "It generates no parse trees", "It generates multiple strings"},
            {"It ensures a single interpretation for each string", "It allows multiple interpretations", "It simplifies the lexical analysis", "It reduces the number of syntax errors"},
            {"It simplifies the parsing process", "It complicates the parsing process", "It has no impact", "It allows for faster code execution"}
    };

    public static final int[] post_test_lesson_2_answers = {0, 0, 0};

    // Pre-test questions, choices, and answers for Lesson 3
    public static final String[] pre_test_lesson_3_questions = {
            "What is inherent ambiguity in a grammar?",
            "Why is inherent ambiguity problematic?",
            "Can inherent ambiguity be resolved by modifying the grammar?"
    };

    public static final String[][] pre_test_lesson_3_choices = {
            {"Ambiguity that cannot be resolved by any means", "Ambiguity that can be resolved by rewriting rules", "Ambiguity in lexical analysis", "Ambiguity in phonetic analysis"},
            {"Because it leads to multiple interpretations", "Because it can be easily misunderstood", "Because it complicates lexical analysis", "Because it has no impact"},
            {"No, it cannot be resolved", "Yes, by modifying the grammar", "Yes, by adding more rules", "Yes, by simplifying the grammar"}
    };

    public static final int[] pre_test_lesson_3_answers = {0, 0, 0};

    // Post-test questions, choices, and answers for Lesson 3
    public static final String[] post_test_lesson_3_questions = {
            "What does inherent ambiguity in a grammar imply?",
            "How does inherent ambiguity affect language design?",
            "Can inherent ambiguity be eliminated completely?"
    };

    public static final String[][] post_test_lesson_3_choices = {
            {"That the grammar will always have ambiguity", "That ambiguity can be resolved with more rules", "That the grammar is unambiguous", "That the grammar is inconsistent"},
            {"It makes language processing more complex", "It simplifies the language design", "It has no effect", "It only affects lexical analysis"},
            {"No, it is a fundamental property", "Yes, with enough modifications", "Yes, by simplifying the language", "Yes, by adding more rules"}
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
        e_Question[] questionArray = new e_Question[questions.length];
        for (int i = 0; i < questions.length; i++) {
            questionArray[i] = new e_Question(questions[i], choices[i], answers[i], e_Question.Difficulty.EASY); // Adjust difficulty if needed
        }
        return questionArray;
    }
}
