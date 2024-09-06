package com.example.autotutoria20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class e_Module_1 {
//    // Pre-test questions, choices, and answers for Lesson 1
//    public static final String[] pre_test_lesson_1_questions = {
//            "What is an alphabet in the context of formal languages?",
//            "What is a string in formal languages?",
//            "How do you represent the empty string?"
//    };

    // Pre-test questions, choices, and answers for Lesson 1
    public static final String[] pre_test_lesson_1_questions = {
            "What is an alphabet in the context of formal languages?",
            "Which of the following is a string over the alphabet Σ = {a, b}?",
            "What is the length of the string \"abc\" over the alphabet Σ = {a, b, c}?",
            "What is the empty string commonly denoted by?",
            "Which of the following operations on strings does the Kleene star (*) represent?",
            "What is the concatenation of the strings \"ab\" and \"cd\"?",
            "Which of the following is NOT a property of a regular language?",
            "Which machine recognizes regular languages?",
            "If Σ = {0, 1}, which of the following is a language over Σ?",
            "Which of the following best describes a deterministic finite automaton (DFA)?",
            "In automata theory, what does a transition function define?",
            "What does it mean if a string is \"accepted\" by a finite automaton?",
            "What is a context-free grammar (CFG) used to describe?",
            "Which of the following is true about a nondeterministic finite automaton (NFA)?",
            "Which of the following best describes a Turing machine?"
    };

    public static final String[][] pre_test_lesson_1_choices = {
            {"A sequence of symbols", "A set of symbols", "A single symbol", "A string of characters"},
            {"abba", "123", "ab12", "abb@"},
            {"1", "2", "3", "4"},
            {"ε", "Ø", "{}", "∅"},
            {"Reversal of the string", "Concatenation of strings", "Repetition of strings, including the empty string", "Trimming spaces from the string"},
            {"abcd", "cdab", "ab, cd", "ab cd"},
            {"It can be recognized by a finite automaton", "It can be described by a regular expression", "It can be recognized by a Turing machine", "It requires a context-free grammar to be generated"},
            {"Finite Automaton", "Pushdown Automaton", "Turing Machine", "Context-Free Grammar"},
            {"All binary strings with an even number of 0s", "All strings with at least one 2", "All strings that are palindromes", "All uppercase English letters"},
            {"A machine that can recognize context-free languages", "A machine that uses a stack to store information", "A machine with a finite number of states and transitions", "A machine that can only process binary numbers"},
            {"The start state of the automaton", "The set of final states", "The mapping of states and input symbols to new states", "The length of the input string"},
            {"The string is too long", "The string is recognized by the automaton", "The string is invalid", "The automaton stops processing"},
            {"Regular languages", "Context-free languages", "Turing machines", "Finite automata"},
            {"It has only one possible transition for each state and input symbol", "It can have multiple possible transitions for each state and input symbol", "It cannot be converted to a DFA", "It is slower than a DFA"},
            {"A machine that can recognize regular languages", "A machine that can recognize recursively enumerable languages", "A machine with finite memory and states", "A machine that only processes strings of even length"}
    };

//    public static final String[][] pre_test_lesson_1_choices = {
//            {"A collection of symbols", "A sequence of characters", "A set of rules", "A type of automaton"},
//            {"A sequence of symbols", "A single character", "A grammar", "A state in an automaton"},
//            {"λ (lambda)", "ε (epsilon)", "0", "1"}
//    };

    public static final int[] pre_test_lesson_1_answers = {
            1,  // 'A set of symbols' is the correct answer for the first question.
            0,  // 'abba' is the correct answer for the second question.
            2,  // '3' is the correct answer for the third question.
            0,  // 'ε' is the correct answer for the fourth question.
            2,  // 'Repetition of strings, including the empty string' is the correct answer for the fifth question.
            0,  // 'abcd' is the correct answer for the sixth question.
            3,  // 'It requires a context-free grammar to be generated' is the correct answer for the seventh question.
            0,  // 'Finite Automaton' is the correct answer for the eighth question.
            0,  // 'All binary strings with an even number of 0s' is the correct answer for the ninth question.
            2,  // 'A machine with a finite number of states and transitions' is the correct answer for the tenth question.
            2,  // 'The mapping of states and input symbols to new states' is the correct answer for the eleventh question.
            1,  // 'The string is recognized by the automaton' is the correct answer for the twelfth question.
            1,  // 'Context-free languages' is the correct answer for the thirteenth question.
            1,  // 'It can have multiple possible transitions for each state and input symbol' is the correct answer for the fourteenth question.
            1   // 'A machine that can recognize recursively enumerable languages' is the correct answer for the fifteenth question.
    };

//    public static final int[] pre_test_lesson_1_answers = {
//            0,
//            0,
//            1
//    };

    // Post-test questions, choices, and answers for Lesson 1 (Easy)
    public static final String[] post_test_lesson_1_questions_easy = {
            "An alphabet is a finite non-empty set of symbols.",
            "The symbol Σ is commonly used to denote a string.",
            "The binary alphabet consists of the symbols {0, 1}.",
            "A string can have an infinite number of symbols.",
            "The length of the string \"abc\" is 3.",
            "The concatenation of the strings \"ab\" and \"c\" results in \"cab\".",
            "The reversal of the string \"racecar\" is \"racecar\".",
            "A string \"u\" is a prefix of string \"w\" if w = uv for some string v.",
            "A suffix of the string \"abc\" could be \"bc\".",
            "The set of all possible strings over an alphabet Σ is denoted by Σ+.",
            "The length of the empty string ε is 1.",
            "In formal languages, the alphabet {A, C, G, T} is commonly associated with DNA sequences.",
            "The string \"ba\" is a valid string over the alphabet Σ = {a, b}.",
            "A string \"v\" is a substring of \"w\" if w = u1vu2 for some strings u1 and u2.",
            "Every string over the alphabet {0, 1} that starts with '1' and ends with '0' is a regular language."
    };

    public static final String[][] post_test_lesson_1_choices_easy = {
            {"True", "False"},
            {"True", "False"},
            {"True", "False"},
            {"True", "False"},
            {"True", "False"},
            {"True", "False"},
            {"True", "False"},
            {"True", "False"},
            {"True", "False"},
            {"True", "False"},
            {"True", "False"},
            {"True", "False"},
            {"True", "False"},
            {"True", "False"},
            {"True", "False"}
    };

    public static final int[] post_test_lesson_1_answers_easy = {
            0, // True answer for number 1
            1, // False answer for number 2
            0, // True answer for number 3
            1, // False answer for number 4
            0, // True answer for number 5
            1, // False answer for number 6
            0, // True answer for number 7
            0, // True answer for number 8
            0, // True answer for number 9
            1, // False answer for number 10
            1, // False answer for number 11
            0, // True answer for number 12
            0, // True answer for number 13
            0, // True answer for number 14
            0  // True answer for number 15
    };

//    // Post-test questions, choices, and answers for Lesson 1
//    public static final String[] post_test_lesson_1_questions = {
//            "Which of the following is an alphabet?",
//            "What does a string represent in formal language?",
//            "How do you denote the empty string?"
//    };
//
//    public static final String[][] post_test_lesson_1_choices = {
//            {"{a, b}", "ab", "{ab}", "a"},
//            {"A set of symbols", "A sequence of symbols", "A rule", "A state"},
//            {"λ", "ε", "null", "0"}
//    };
//
//    public static final int[] post_test_lesson_1_answers = {0, 1, 1};

    // Post-test questions, choices, and answers for Lesson 1 (Medium)
    public static final String[] post_test_lesson_1_questions_medium = {
            "What is an alphabet in the context of formal languages?",
            "What symbol is typically used to denote an alphabet?",
            "Which of the following is an example of a binary alphabet?",
            "What is a string in the context of formal languages?",
            "How is the empty string denoted?",
            "What does the notation Σ (Sigma star) represent?",
            "What is the length of the string \"aab\"?",
            "Which of the following is a valid concatenation of the strings \"ab\" and \"c\"?",
            "What operation combines two strings end-to-end to form a new string?",
            "Which of the following is a prefix of the string \"abc\"?",
            "Which of the following is a suffix of the string \"abc\"?",
            "Which of the following is a substring of the string \"abc\"?",
            "What is the reversal of the string \"racecar\"?",
            "What does the notation |w| represent for a string w?",
            "Which of the following correctly represents the empty string's length?"
    };


    public static final String[][] post_test_lesson_1_choices_medium = {
            {"A set of numbers", "A finite non-empty set of symbols", "A sequence of symbols", "A string of characters"},
            {"Λ (Lambda)", "Σ (Sigma)", "Ω (Omega)", "ε (Epsilon)"},
            {"Σ = {A, C, G, T}", "Σ = {0, 1}", "Σ = {a, b, c}", "Σ = {1, 2, 3}"},
            {"A set of numbers", "A sequence of digits", "A finite sequence of symbols drawn from a given alphabet", "A non-empty set of characters"},
            {"Σ", "Λ", "ε", "Ω"},
            {"The set of all possible symbols in an alphabet", "The set of all possible strings over an alphabet including the empty string", "The set of all prefixes of a string", "The set of all suffixes of a string"},
            {"1", "2", "3", "4"},
            {"ba", "cab", "abc", "acb"},
            {"Subtraction", "Concatenation", "Division", "Prefixing"},
            {"bc", "c", "a", "ac"},
            {"a", "ab", "c", "ba"},
            {"ac", "ba", "b", "ca"},
            {"racecar", "carrace", "racearc", "racerac"},
            {"The alphabet of w", "The length of w", "The reverse of w", "The concatenation of w with itself"},
            {"|ε| = 1", "|ε| = 0", "|ε| = 2", "|ε| = ε"}
    };

    public static final int[] post_test_lesson_1_answers_medium = {
            1, // True answer for number 1: B) A finite non-empty set of symbols
            1, // True answer for number 2: B) Σ (Sigma)
            1, // True answer for number 3: B) Σ = {0, 1}
            2, // True answer for number 4: C) A finite sequence of symbols drawn from a given alphabet
            2, // True answer for number 5: C) ε
            1, // True answer for number 6: B) The set of all possible strings over an alphabet including the empty string
            2, // True answer for number 7: C) 3
            2, // True answer for number 8: C) abc
            1, // True answer for number 9: B) Concatenation
            2, // True answer for number 10: C) a
            2, // True answer for number 11: C) c
            2, // True answer for number 12: C) b
            0, // True answer for number 13: A) racecar
            1, // True answer for number 14: B) The length of w
            1  // True answer for number 15: B) |ε| = 0
    };

    // Pre-test questions, choices, and answers for Lesson 2
    public static final String[] pre_test_lesson_2_questions = {
            "What symbol is typically used to denote an alphabet?",
            "What is an alphabet in formal languages?",
            "Which of the following is an example of a binary alphabet?",
            "How is the empty string denoted?",
            "What is the length of the string \"aab\"?",
            "If Σ = {a, b}, which of the following is a string over Σ?",
            "What is the notation used to represent the set of all possible strings over an alphabet Σ, including the empty string?",
            "Which of the following is a prefix of the string \"abc\"?",
            "Which of the following is a suffix of the string \"abc\"?",
            "Which of the following is a substring of \"abc\"?",
            "What operation combines two strings end-to-end to form a new string?",
            "If u = \"ab\" and v = \"c\", what is the result of their concatenation?",
            "What is the reversal of the string \"abc\"?",
            "What is the length of the empty string ε?",
            "Which of the following is an alphabet of decimal digits?"
    };

    public static final String[][] pre_test_lesson_2_choices = {
            {"Ω", "Σ", "Δ", "π"},
            {"An infinite set of symbols", "A finite non-empty set of symbols", "A finite sequence of numbers", "A non-finite sequence of characters"},
            {"{A, B, C}", "{0, 1}", "{A, C, G, T}", "{0, 2, 4, 6}"},
            {"λ", "ε", "ϕ", "0"},
            {"1", "2", "3", "4"},
            {"\"ab\"", "\"abc\"", "\"ac\"", "\"ba\""},
            {"Σ+", "Σ^", "Σ*", "Σ!"},
            {"\"b\"", "\"bc\"", "\"ab\"", "\"ac\""},
            {"\"a\"", "\"ab\"", "\"bc\"", "\"ac\""},
            {"\"a\"", "\"abc\"", "\"bc\"", "All of the above"},
            {"Reversal", "Concatenation", "Prefixing", "Suffixing"},
            {"\"abc\"", "\"cba\"", "\"bac\"", "\"acb\""},
            {"\"abc\"", "\"cba\"", "\"bac\"", "\"bca\""},
            {"1", "0", "-1", "None of the above"},
            {"{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}", "{A, B, C, D, E, F}", "{a, b, c}", "{0, 1}"}
    };

    public static final int[] pre_test_lesson_2_answers = {
            1, // Answer for question 1: Σ
            1, // Answer for question 2: A finite non-empty set of symbols
            1, // Answer for question 3: {0, 1}
            1, // Answer for question 4: ε
            2, // Answer for question 5: 3
            0, // Answer for question 6: "ab"
            2, // Answer for question 7: Σ*
            2, // Answer for question 8: "ab"
            2, // Answer for question 9: "bc"
            3, // Answer for question 10: All of the above
            1, // Answer for question 11: Concatenation
            0, // Answer for question 12: "abc"
            1, // Answer for question 13: "cba"
            1, // Answer for question 14: 0
            0  // Answer for question 15: {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
    };

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

    // Post-test questions, choices, and answers for Lesson 2
    public static final String[] post_test_lesson_2_questions_easy = {
            "Concatenation is the operation of reversing a string.",
            "The length of a string is determined by counting the number of symbols it contains.",
            "The reversal of the string \"abcd\" is \"dcba\".",
            "A substring is a non-contiguous sequence of characters within a string.",
            "For the string \"abcdef\", \"abc\" is considered a prefix.",
            "The empty string is a string that contains exactly one character.",
            "The length of the empty string is zero.",
            "The notation for concatenating two strings s1 and s2 is s1s2.",
            "A suffix is a substring that starts at the beginning of a string.",
            "If s = \"hello\", then the length of s (|s|) is 4.",
            "Concatenating the empty string to the right of any string s results in the original string s.",
            "For the string \"automata\", \"auto\" is a valid substring.",
            "The reversal operation is denoted by se.",
            "Concatenating two strings results in a string whose length is the sum of the lengths of the two original strings.",
            "If s = \"abcdef\", \"def\" is both a substring and a suffix of s."
    };

    public static final String[][] post_test_lesson_2_choices_easy = {
            {"True", "False"},
            {"True", "False"},
            {"True", "False"},
            {"True", "False"},
            {"True", "False"},
            {"True", "False"},
            {"True", "False"},
            {"True", "False"},
            {"True", "False"},
            {"True", "False"},
            {"True", "False"},
            {"True", "False"},
            {"True", "False"},
            {"True", "False"},
            {"True", "False"}
    };

    public static final int[] post_test_lesson_2_answers_easy = {
            1, // Number 1 answer: False
            0, // Number 2 answer: True
            0, // Number 3 answer: True
            1, // Number 4 answer: False
            0, // Number 5 answer: True
            1, // Number 6 answer: False
            0, // Number 7 answer: True
            0, // Number 8 answer: True
            1, // Number 9 answer: False
            1, // Number 10 answer: False
            0, // Number 11 answer: True
            0, // Number 12 answer: True
            1, // Number 13 answer: False
            0, // Number 14 answer: True
            0  // Number 15 answer: True
    };

    public static final String[] post_test_lesson_2_questions_medium = {
            "What is a substring?",
            "In the string 'abcdef', which of the following is a prefix?",
            "What is the empty string?",
            "What is the length of the empty string?",
            "If s1 = 'abc' and s2 = 'def', what is the result of concatenation s1s2?",
            "What is a suffix in the context of strings?",
            "If s = 'hello', what is the length of s?",
            "What happens when you concatenate the empty string to the right of a string s?",
            "Which of the following is a valid substring of 'automata'?",
            "How is the reversal operation denoted in string operations?",
            "What is the result of concatenating two strings s1 and s2 in terms of length?",
            "Which of the following is both a substring and a suffix of the string 'abcdef'?"
    };

    public static final String[][] post_test_lesson_2_choices_medium = {
            {"A sequence of characters that are not contiguous within a string", "A sequence of characters that are contiguous within a string", "The first half of a string", "The last half of a string"},
            {"def", "abc", "cde", "f"},
            {"A string with one character", "A string with no characters", "A string that contains only spaces", "A string that starts with a space"},
            {"1", "0", "Undefined", "Equal to the length of any string"},
            {"abcdef", "abc def", "cdefabc", "defabc"},
            {"A substring that starts from the beginning of a string", "A substring that ends at the string's end", "A substring that is in the middle of a string", "A substring that starts after the first character"},
            {"4", "5", "6", "7"},
            {"The string s becomes longer by one character", "The string s remains the same", "The string s is reversed", "The string s becomes the empty string"},
            {"manual", "auto", "matauto", "tomato"},
            {"s*", "sR", "se", "s!"},
            {"The length is the average of the lengths of s1 and s2", "The length is the difference between the lengths of s1 and s2", "The length is the sum of the lengths of s1 and s2", "The length is equal to the length of s1"},
            {"abc", "cde", "def", "fgh"}
    };

    // Each number corresponds to the index of the correct answer for each question
    public static final int[] post_test_lesson_2_answers_medium = {
            1,  // Answer from question 4: "A sequence of characters that are contiguous within a string"
            1,  // Answer from question 5: "abc"
            1,  // Answer from question 6: "A string with no characters"
            1,  // Answer from question 7: "0"
            0,  // Answer from question 8: "abcdef"
            1,  // Answer from question 9: "A substring that ends at the string's end"
            1,  // Answer from question 10: "5"
            1,  // Answer from question 11: "The string s remains the same"
            1,  // Answer from question 12: "auto"
            1,  // Answer from question 13: "sR"
            2,  // Answer from question 14: "The length is the sum of the lengths of s1 and s2"
            2   // Answer from question 15: "def"
    };

    public static final String[] post_test_lesson_1_questions_hard = {
            "What is the finite non-empty set of symbols called?",
            "What symbol typically denotes an alphabet?",
            "What is a finite sequence of symbols drawn from an alphabet?",
            "What denotes the empty string?",
            "What is the operation that combines two strings end-to-end?",
            "What symbol denotes the set of all possible strings over an alphabet?",
            "What denotes the length of a string?",
            "What is the reversal of a string denoted by?",
            "What is a string that comes before another string?",
            "What is a string that comes after another string?",
            "What is a string that is part of another string?",
            "What is the length of the empty string?",
            "What are the characters in the binary alphabet?",
            "What set is {A, C, G, T} an example of?",
            "What does the concatenation of 'ab' and 'c' result in?"
    };

    public static final String[] post_test_lesson_1_answers_hard = {
            "alphabet symbols",
            "sigma",
            "string sequence",
            "epsilon",
            "string concatenation",
            "sigma star",
            "string length",
            "reversal",
            "prefix string",
            "suffix string",
            "substring string",
            "zero length",
            "0 and 1",
            "DNA alphabet",
            "string abc"
    };

    public static final String[] post_test_lesson_2_questions_hard = {
            "What is the operation of joining two strings end-to-end called?",
            "What is the number of symbols in a string referred to as?",
            "What do you call the string obtained by writing the original string’s characters in reverse order?",
            "What term describes a contiguous sequence of characters within a string?",
            "What is the substring that starts from the beginning of a string called?",
            "What is the substring that ends at the string's end called?",
            "What do you call a string with no characters?",
            "What is the length of the empty string?",
            "What symbol is commonly used to denote the empty string?",
            "What is the result of concatenating 'abc' and 'def'?",
            "What is the term for accessing individual characters in a string by their position?",
            "What operation extracts a portion of a string?",
            "Which operation is used to find the first character of a string?",
            "What is the symbol for denoting the reversal of a string s?",
            "What operation does not change the length of a string when applied?"
    };

    public static final String[] post_test_lesson_2_answers_hard = {
            "concatenation",
            "length",
            "reversal",
            "substring",
            "prefix",
            "suffix",
            "empty string",
            "zero",
            "epsilon",
            "abcdef",
            "indexing",
            "substring",
            "prefix",
            "sR",
            "reversal"
    };

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

//    // Method to get pre-test questions for a specific lesson
//    public static e_Question[] getPreTestQuestions(String lesson) {
//        switch (lesson) {
//            case "M1":
//                return getPreTestLesson1Questions();
//            case "M2":
//                return getPreTestLesson2Questions();
//            case "M3":
//                return getPreTestLesson3Questions();
//            case "M4":
//                return getPreTestLesson4Questions();
//            default:
//                throw new IllegalArgumentException("Invalid lesson: " + lesson);
//        }
//    }

//    // Method to get post-test questions for a specific lesson
//    private e_Question[] get_PostTest_EasyMedium_Questions(String module, String lesson) {
//        e_Question[] allQuestions = e_Module_1.get_PostTest_Lesson1_Easy_Questions();  // Fetch questions for this lesson/module
//
//        // Here you can loop through the questions and categorize them based on difficulty
//        for (e_Question question : allQuestions) {
//            switch (question.getDifficulty()) {
//                case EASY:
//                    question.setType(e_Question.QuestionType.TRUE_FALSE);
//                    break;
//                case MEDIUM:
//                    question.setType(e_Question.QuestionType.MULTIPLE_CHOICE);
//                    break;
//                case HARD:
//                    question.setType(e_Question.QuestionType.IDENTIFICATION);
//                    break;
//            }
//        }
//        return allQuestions;
//    }


    // Methods for specific lessons (these were missing before)

    public static e_Question[] getPreTestLesson2Questions() {
        return getPreTestQuestions(pre_test_lesson_2_questions, pre_test_lesson_2_choices, pre_test_lesson_2_answers);
    }

    public static e_Question[] getPreTestLesson3Questions() {
        return getPreTestQuestions(pre_test_lesson_3_questions, pre_test_lesson_3_choices, pre_test_lesson_3_answers);
    }

    public static e_Question[] getPreTestLesson4Questions() {
        return getPreTestQuestions(pre_test_lesson_4_questions, pre_test_lesson_4_choices, pre_test_lesson_4_answers);
    }

    public static e_Question[] get_PostTest_Lesson1_Easy_Questions() {
        return get_PostTest_EasyMedium_Questions(post_test_lesson_1_questions_easy, post_test_lesson_1_choices_easy, post_test_lesson_1_answers_easy, e_Question.Difficulty.EASY);  // Adjust difficulty if necessary
    }

    public static e_Question[] get_PostTest_Lesson1_Medium_Questions() {
        return get_PostTest_EasyMedium_Questions(post_test_lesson_1_questions_medium, post_test_lesson_1_choices_medium, post_test_lesson_1_answers_medium, e_Question.Difficulty.MEDIUM);  // Adjust difficulty if necessary
    }

    public static e_Question[] get_PostTest_Lesson1_Hard_Questions() {
        return get_PostTest_Hard_Questions(post_test_lesson_1_questions_hard, post_test_lesson_1_answers_hard, e_Question.Difficulty.HARD);  // Adjust difficulty if necessary
    }

    public static e_Question[] get_PostTest_Lesson2_Easy_Questions() {
        return get_PostTest_EasyMedium_Questions(post_test_lesson_2_questions_easy, post_test_lesson_2_choices_easy, post_test_lesson_2_answers_easy, e_Question.Difficulty.EASY);  // Adjust difficulty if necessary
    }

    public static e_Question[] get_PostTest_Lesson2_Medium_Questions() {
        return get_PostTest_EasyMedium_Questions(post_test_lesson_2_questions_medium, post_test_lesson_2_choices_medium, post_test_lesson_2_answers_medium, e_Question.Difficulty.MEDIUM);  // Adjust difficulty if necessary
    }

    public static e_Question[] get_PostTest_Lesson2_Hard_Questions() {
        return get_PostTest_Hard_Questions(post_test_lesson_2_questions_hard, post_test_lesson_2_answers_hard, e_Question.Difficulty.HARD);  // Adjust difficulty if necessary
    }

    public static e_Question[] getPostTestLesson3Questions() {
        return get_PostTest_EasyMedium_Questions(post_test_lesson_3_questions, post_test_lesson_3_choices, post_test_lesson_3_answers, e_Question.Difficulty.EASY);  // Adjust difficulty if necessary
    }

    public static e_Question[] getPostTestLesson4Questions() {
        return get_PostTest_EasyMedium_Questions(post_test_lesson_4_questions, post_test_lesson_4_choices, post_test_lesson_4_answers, e_Question.Difficulty.MEDIUM);  // Adjust difficulty if necessary
    }

    // Method to convert string arrays to e_Question objects
//    private static e_Question[] getQuestions(String[] questions, String[][] choices, int[] answers) {
//        e_Question[] questionArray = new e_Question[questions.length];
//        for (int i = 0; i < questions.length; i++) {
//            questionArray[i] = new e_Question(questions[i], choices[i], answers[i], e_Question.Difficulty.EASY); // Adjust difficulty if needed
//        }
//        return questionArray;
//    }

    private static e_Question[] getPreTestQuestions(String[] questions, String[][] choices, int[] answers) {
        List<e_Question> questionList = new ArrayList<>();
        for (int i = 0; i < questions.length; i++) {
            questionList.add(new e_Question(questions[i], choices[i], answers[i])); // Pre-test is always multiple choice
        }

        // Shuffle the list to randomize the order of the questions
        Collections.shuffle(questionList);

        // Convert the list back to an array
        return questionList.toArray(new e_Question[0]);
    }

    static e_Question[] get_PostTest_EasyMedium_Questions(String[] questions, String[][] choices, int[] answers, e_Question.Difficulty level) {
        List<e_Question> questionList = new ArrayList<>();
        for (int i = 0; i < questions.length; i++) {
            questionList.add(new e_Question(questions[i], choices[i], answers[i], level)); // Adjust based on difficulty
        }

        // Shuffle the list to randomize the order of the questions
        Collections.shuffle(questionList);

        // Convert the list back to an array
        return questionList.toArray(new e_Question[0]);
    }

    static e_Question[] get_PostTest_Hard_Questions(String[] questions, String[] answers, e_Question.Difficulty level) {
        List<e_Question> questionList = new ArrayList<>();
        for (int i = 0; i < questions.length; i++) {
            questionList.add(new e_Question(questions[i], answers[i], level)); // Adjust based on difficulty
        }

        // Shuffle the list to randomize the order of the questions
        Collections.shuffle(questionList);

        // Convert the list back to an array
        return questionList.toArray(new e_Question[0]);
    }


}
