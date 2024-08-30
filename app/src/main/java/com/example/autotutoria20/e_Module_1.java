package com.example.autotutoria20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class e_Module_1 {
    // Pre-test questions, choices, and answers for Lesson 1
    public static final String[] pre_test_lesson_1_questions = {
            "What is an alphabet in the context of formal languages?",
            "What is a string in formal languages?",
            "How do you represent the empty string?"
    };

    public static final String[][] pre_test_lesson_1_choices = {
            {"A collection of symbols", "A sequence of characters", "A set of rules", "A type of automaton"},
            {"A sequence of symbols", "A single character", "A grammar", "A state in an automaton"},
            {"λ (lambda)", "ε (epsilon)", "0", "1"}
    };

    public static final int[] pre_test_lesson_1_answers = {
            0,
            0,
            1
    };

    // Post-test questions, choices, and answers for Lesson 1
    public static final String[] post_test_lesson_1_questions = {
            "Which of the following is an alphabet?",
            "What does a string represent in formal language?",
            "How do you denote the empty string?"
    };

    public static final String[][] post_test_lesson_1_choices = {
            {"{a, b}", "ab", "{ab}", "a"},
            {"A set of symbols", "A sequence of symbols", "A rule", "A state"},
            {"λ", "ε", "null", "0"}
    };

    public static final int[] post_test_lesson_1_answers = {0, 1, 1};

    public static final String[] post_test_lesson_1_questions_easy = {
            "Pogi ba ako",
            "What does a string represent in formal language?",
            "How do you denote the empty string?"
    };

    public static final String[][] post_test_lesson_1_choices_easy = {
            {"True", "False"},
            {"True", "False"},
            {"True", "False"},
    };

    public static final int[] post_test_lesson_1_answers_easy = {
            0,
            1,
            1
    };

    public static final String[] post_test_lesson_1_answers_hard = {
            "dalton",
            "allyzza",
            "jeffrey"
    };

    // Pre-test questions, choices, and answers for Lesson 2
    public static final String[] pre_test_lesson_2_questions = {
            "What is the concatenation of strings?",
            "What is the reverse of the string 'abc'?",
            "What is the length of the string 'hello'?"
    };

    public static final String[][] pre_test_lesson_2_choices = {
            {"Combining symbols", "Appending one string to another", "Reversing a string", "Finding the substring"},
            {"abc", "cba", "bca", "acb"},
            {"4", "5", "6", "3"}
    };

    public static final int[] pre_test_lesson_2_answers = {1, 1, 1};

    // Post-test questions, choices, and answers for Lesson 2
    public static final String[] post_test_lesson_2_questions = {
            "How do you perform concatenation on 'abc' and 'def'?",
            "What is the reverse of the string 'xyz'?",
            "What is the length of the string 'automata'?"
    };

    public static final String[][] post_test_lesson_2_choices = {
            {"abc + def", "abcdef", "abc def", "defabc"},
            {"yzx", "zyx", "xyz", "xzy"},
            {"6", "7", "8", "9"}
    };

    public static final int[] post_test_lesson_2_answers = {1, 1, 2};

    // Pre-test questions, choices, and answers for Lesson 3
    public static final String[] pre_test_lesson_3_questions = {
            "What defines a formal language?",
            "Which of the following is a formal language?",
            "What is a grammar in the context of formal languages?"
    };

    public static final String[][] pre_test_lesson_3_choices = {
            {"A set of strings", "A set of characters", "A set of rules", "A set of numbers"},
            {"English", "Java", "{a^n b^n | n ≥ 0}", "Mathematics"},
            {"A set of symbols", "A set of strings", "A set of rules for generating strings", "A type of automaton"}
    };

    public static final int[] pre_test_lesson_3_answers = {0, 2, 2};

    // Post-test questions, choices, and answers for Lesson 3
    public static final String[] post_test_lesson_3_questions = {
            "What constitutes a formal language?",
            "Which of the following is an example of a formal language?",
            "In formal languages, what does a grammar define?"
    };

    public static final String[][] post_test_lesson_3_choices = {
            {"A set of strings", "A set of characters", "A set of rules", "A set of numbers"},
            {"Python", "C++", "{0^n 1^n | n ≥ 0}", "Natural languages"},
            {"The syntax of a language", "The semantics of a language", "The rules for generating strings", "The set of strings"}
    };

    public static final int[] post_test_lesson_3_answers = {0, 2, 2};

    // Pre-test questions, choices, and answers for Lesson 4
    public static final String[] pre_test_lesson_4_questions = {
            "What is automata theory?",
            "Which of the following is a type of automaton?",
            "What is the main purpose of automata theory?"
    };

    public static final String[][] pre_test_lesson_4_choices = {
            {"The study of computation", "The study of strings", "The study of algorithms", "The study of languages"},
            {"Finite automaton", "Regular expression", "Context-free grammar", "Language"},
            {"To understand algorithms", "To understand languages", "To understand computation", "To understand grammars"}
    };

    public static final int[] pre_test_lesson_4_answers = {0, 0, 2};

    // Post-test questions, choices, and answers for Lesson 4
    public static final String[] post_test_lesson_4_questions = {
            "What does automata theory study?",
            "Which of these is an example of an automaton?",
            "Why is automata theory important?"
    };

    public static final String[][] post_test_lesson_4_choices = {
            {"The study of computation", "The study of strings", "The study of algorithms", "The study of languages"},
            {"Turing machine", "Java program", "Python script", "Natural language"},
            {"To understand computation", "To understand languages", "To understand algorithms", "To understand strings"}
    };

    public static final int[] post_test_lesson_4_answers = {0, 0, 0};

    // Method to get pre-test questions for a specific lesson
    public static e_Question[] getPreTestQuestions(String lesson) {
        switch (lesson) {
            case "M1":
                return getPreTestLesson1Questions();
            case "M2":
                return getPreTestLesson2Questions();
            case "M3":
                return getPreTestLesson3Questions();
            case "M4":
                return getPreTestLesson4Questions();
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
            case "M4":
                return getPostTestLesson4Questions();
            default:
                throw new IllegalArgumentException("Invalid lesson: " + lesson);
        }
    }

    // Methods for specific lessons (these were missing before)
    public static e_Question[] getPreTestLesson1Questions() {
        // working code:
        return getQuestions(pre_test_lesson_1_questions, pre_test_lesson_1_choices, pre_test_lesson_1_answers);

        // testing code (with difficulty):
//        return getQuestions(pre_test_lesson_1_questions, pre_test_lesson_1_choices, pre_test_lesson_1_answers, difficulty);

    }

//    public static e_Question getPreTestLesson1Questions() {
//        e_Question[] questions = getQuestions(pre_test_lesson_1_questions, pre_test_lesson_1_choices, pre_test_lesson_1_answers);
//        int randomIndex = (int) (Math.random() * questions.length);
//        return questions[randomIndex];
//    }

    public static e_Question[] getPreTestLesson2Questions() {
        return getQuestions(pre_test_lesson_2_questions, pre_test_lesson_2_choices, pre_test_lesson_2_answers);
    }

//    public static e_Question getPreTestLesson2Questions() {
//        e_Question[] questions = getQuestions(pre_test_lesson_2_questions, pre_test_lesson_2_choices, pre_test_lesson_2_answers);
//        int randomIndex = (int) (Math.random() * questions.length);
//        return questions[randomIndex];
//    }

    public static e_Question[] getPreTestLesson3Questions() {
        return getQuestions(pre_test_lesson_3_questions, pre_test_lesson_3_choices, pre_test_lesson_3_answers);
    }

//    public static e_Question getPreTestLesson3Questions() {
//        e_Question[] questions = getQuestions(pre_test_lesson_3_questions, pre_test_lesson_3_choices, pre_test_lesson_3_answers);
//        int randomIndex = (int) (Math.random() * questions.length);
//        return questions[randomIndex];
//    }

    public static e_Question[] getPreTestLesson4Questions() {
        return getQuestions(pre_test_lesson_4_questions, pre_test_lesson_4_choices, pre_test_lesson_4_answers);
    }

//    public static e_Question getPreTestLesson4Questions() {
//        e_Question[] questions = getQuestions(pre_test_lesson_4_questions, pre_test_lesson_4_choices, pre_test_lesson_4_answers);
//        int randomIndex = (int) (Math.random() * questions.length);
//        return questions[randomIndex];
//    }

    public static e_Question[] getPostTestLesson1Questions() {
        return getQuestions(post_test_lesson_1_questions, post_test_lesson_1_choices, post_test_lesson_1_answers);
    }

    public static e_Question[] getPostTestLesson2Questions() {
        return getQuestions(post_test_lesson_2_questions, post_test_lesson_2_choices, post_test_lesson_2_answers);
    }

    public static e_Question[] getPostTestLesson3Questions() {
        return getQuestions(post_test_lesson_3_questions, post_test_lesson_3_choices, post_test_lesson_3_answers);
    }

    public static e_Question[] getPostTestLesson4Questions() {
        return getQuestions(post_test_lesson_4_questions, post_test_lesson_4_choices, post_test_lesson_4_answers);
    }

    // Method to convert string arrays to e_Question objects
//    private static e_Question[] getQuestions(String[] questions, String[][] choices, int[] answers) {
//        e_Question[] questionArray = new e_Question[questions.length];
//        for (int i = 0; i < questions.length; i++) {
//            questionArray[i] = new e_Question(questions[i], choices[i], answers[i], e_Question.Difficulty.EASY); // Adjust difficulty if needed
//        }
//        return questionArray;
//    }

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

    private static e_Question[] getQuestions(String[] questions, String[][] choices, int[] answers, e_Question.Difficulty level) {
        List<e_Question> questionList = new ArrayList<>();
        for (int i = 0; i < questions.length; i++) {
            questionList.add(new e_Question(questions[i], choices[i], answers[i], level)); // Adjust difficulty if needed
        }

        // Shuffle the list to randomize the order of the questions
        Collections.shuffle(questionList);

        // Convert the list back to an array
        return questionList.toArray(new e_Question[0]);
    }

}
