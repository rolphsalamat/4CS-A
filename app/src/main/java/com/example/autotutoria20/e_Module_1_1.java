package com.example.autotutoria20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class e_Module_1_1 {

    /*
    +----------------------+
    |       PRE-TEST       |
    |        LESSON 1      |
    +----------------------+
    */

    public static e_Question[] get_PreTest_Lesson1_Questions() {
        return e_Module_1.getPreTestQuestions(
                pre_test_lesson_1_questions,
                pre_test_lesson_1_choices,
                pre_test_lesson_1_answers);
    }

    public static final String[] pre_test_lesson_1_questions = {
            /*1*/ "What is an alphabet in formal language theory?",
            /*2*/ "Which symbol is commonly used to represent an alphabet?",
            /*3*/ "What is a string?",
            /*4*/ "Which of the following is the correct notation for an empty string?",
            /*5*/ "What is the length of the string 'aab'?",
            /*6*/ "Which of the following is an example of an alphabet?",
            /*7*/ "What does the notation Σ (Sigma star) represent?",
            /*8*/ "Which of the following is a prefix of the string 'hello'?",
            /*9*/ "What is the concatenation of the strings 'abc' and 'de'?",
            /*10*/ "Which of the following is a suffix of the string 'world'?",
            /*11*/ "What is the reversal of the string 'abc'?",
            /*12*/ "Which of the following strings is NOT over the alphabet Σ = {0, 1}?",
            /*13*/ "What is the length of the empty string ε?",
            /*14*/ "If w = 'ab', what is ww (concatenation of w with itself)?",
            /*15*/ "Which of the following statements is true?"
    };

    public static final String[][] pre_test_lesson_1_choices = {
            /*1*/ {"A sequence of words", "A set of rules", "A finite, non-empty set of symbols", "A single character"},
            /*2*/ {"Σ", "α", "ε", "λ"},
            /*3*/ {"A single character from an alphabet", "A finite sequence of symbols from an alphabet", "An infinite set of characters", "A rule to define languages"},
            /*4*/ {"λ", "∅", "ε", "δ"},
            /*5*/ {"1", "2", "3", "4"},
            /*6*/ {"{A, B, C}", "{1, 2, 3, 4}", "{a, b, c, d}", "All of the above"},
            /*7*/ {"A finite set of characters", "The empty string", "All possible strings, including the empty string, from an alphabet Σ", "The concatenation of two strings"},
            /*8*/ {"'he'", "'lo'", "'el'", "'world'"},
            /*9*/ {"'abcd'", "'abdec'", "'abcde'", "'deabc'"},
            /*10*/ {"'wor'", "'ld'", "'lo'", "'wo'"},
            /*11*/ {"'abc'", "'cba'", "'bac'", "'cab'"},
            /*12*/ {"'01'", "'10'", "'2'", "'0011'"},
            /*13*/ {"0", "1", "2", "Undefined"},
            /*14*/ {"'ab'", "'aba'", "'abab'", "'baab'"},
            /*15*/ {"The reversal of 'racecar' is 'racecar'", "The length of 'abc' is 2", "The string 'a' has no prefixes", "The empty string has a length of 1"}
    };

    public static final int[] pre_test_lesson_1_answers = {
            /*1*/ 2,  // 'A finite, non-empty set of symbols'
            /*2*/ 0,  // 'Σ'
            /*3*/ 1,  // 'A finite sequence of symbols from an alphabet'
            /*4*/ 2,  // 'ε'
            /*5*/ 2,  // '3'
            /*6*/ 3,  // 'All of the above'
            /*7*/ 2,  // 'All possible strings, including the empty string, from an alphabet Σ'
            /*8*/ 0,  // "'he'"
            /*9*/ 2,  // "'abcde'"
            /*10*/ 1, // "'ld'"
            /*11*/ 1, // "'cba'"
            /*12*/ 2, // "'2'"
            /*13*/ 0, // '0'
            /*14*/ 2, // "'abab'"
            /*15*/ 0  // 'The reversal of "racecar" is "racecar"'
    };

    /*
    +----------------------+
    |      POST-TEST       |
    |        EASY          |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson1_Easy_Questions() {
        return e_Module_1.get_PostTest_EasyMedium_Questions(
                post_test_lesson_1_questions_easy,
                post_test_lesson_1_choices_easy,
                post_test_lesson_1_answers_easy,
                e_Question.Difficulty.EASY
        );
    }

    public static final String[] post_test_lesson_1_questions_easy = {
            /*1*/ "An alphabet is a set of finite characters.",
            /*2*/ "The string 'w' represents any possible string over an alphabet.",
            /*3*/ "The empty string has a length of 1.",
            /*4*/ "Concatenating a string with the empty string does not change the original string.",
            /*5*/ "The set of all possible strings over an alphabet Σ, including the empty string, is denoted by Σ^.",
            /*6*/ "The reversal of the string 'abc' is 'abc.'",
            /*7*/ "A string of length zero is also known as the empty string.",
            /*8*/ "The alphabet Σ = {0, 1} is a binary alphabet.",
            /*9*/ "The string 'aaab' has a length of 5.",
            /*10*/ "If u and v are strings, uv represents their concatenation.",
            /*11*/ "A prefix of a string must always include the entire string.",
            /*12*/ "A suffix of a string is any string that can be found at the beginning of a string.",
            /*13*/ "The string 'ab' is a substring of 'abcde'.",
            /*14*/ "The length of a string is the number of symbols it contains.",
            /*15*/ "An alphabet can be infinite."
    };

    public static final String[][] post_test_lesson_1_choices_easy = {
            /*1*/ {"True", "False"},
            /*2*/ {"True", "False"},
            /*3*/ {"True", "False"},
            /*4*/ {"True", "False"},
            /*5*/ {"True", "False"},
            /*6*/ {"True", "False"},
            /*7*/ {"True", "False"},
            /*8*/ {"True", "False"},
            /*9*/ {"True", "False"},
            /*10*/ {"True", "False"},
            /*11*/ {"True", "False"},
            /*12*/ {"True", "False"},
            /*13*/ {"True", "False"},
            /*14*/ {"True", "False"},
            /*15*/ {"True", "False"}
    };

    public static final int[] post_test_lesson_1_answers_easy = {
            /*1*/ 0,  // True
            /*2*/ 0,  // True
            /*3*/ 1,  // False
            /*4*/ 0,  // True
            /*5*/ 0,  // True
            /*6*/ 1,  // False
            /*7*/ 0,  // True
            /*8*/ 0,  // True
            /*9*/ 1,  // False
            /*10*/ 0,  // True
            /*11*/ 1,  // False
            /*12*/ 1,  // False
            /*13*/ 0,  // True
            /*14*/ 0,  // True
            /*15*/ 1   // False
    };

    /*
    +----------------------+
    |      POST-TEST       |
    |       MEDIUM         |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson1_Medium_Questions() {
        return e_Module_1.get_PostTest_EasyMedium_Questions(
                post_test_lesson_1_questions_medium,
                post_test_lesson_1_choices_medium,
                post_test_lesson_1_answers_medium,
                e_Question.Difficulty.MEDIUM
        );
    }

    public static final String[] post_test_lesson_1_questions_medium = {
            /*1*/ "What does the symbol Σ typically represent in formal languages?",
            /*2*/ "What is a finite sequence of characters from a given alphabet called?",
            /*3*/ "What is the notation for an empty string?",
            /*4*/ "Which of the following is a valid binary alphabet?",
            /*5*/ "What is the length of the string 'abccba'?",
            /*6*/ "What operation combines two strings end-to-end?",
            /*7*/ "Which of the following is a valid string over the alphabet Σ = {a, b}?",
            /*8*/ "What is the reversal of the string 'abc'?",
            /*9*/ "What is the concatenation of 'hello' and 'world'?",
            /*10*/ "Which of the following is a suffix of 'discreet'?",
            /*11*/ "Which of the following strings is over the alphabet Σ = {A, B}?",
            /*12*/ "The length of the empty string ε is:",
            /*13*/ "Which of the following is a substring of 'formal'?",
            /*14*/ "What does the operation Σ represent?",
            /*15*/ "Which of the following is an alphabet?"
    };

    public static final String[][] post_test_lesson_1_choices_medium = {
            /*1*/ {"A sequence of numbers", "A set of rules", "An alphabet", "A language"},
            /*2*/ {"Symbol", "Word", "Set", "String"},
            /*3*/ {"λ", "Σ", "ε", "δ"},
            /*4*/ {"{0, 2}", "{A, B}", "{0, 1}", "{X, Y}"},
            /*5*/ {"5", "6", "7", "4"},
            /*6*/ {"Union", "Intersection", "Concatenation", "Substring"},
            /*7*/ {"'abc'", "'baa'", "'caa'", "'acb'"},
            /*8*/ {"'cab'", "'cba'", "'bac'", "'abc'"},
            /*9*/ {"'hello world'", "'helloworld'", "'hell wor'", "'worhell'"},
            /*10*/ {"'creet'", "'dis'", "'discre'", "'scr'"},
            /*11*/ {"'ABB'", "'AB1'", "'ABZ'", "'AAA1'"},
            /*12*/ {"1", "0", "2", "Undefined"},
            /*13*/ {"'fo'", "'mal'", "'for'", "All of the above"},
            /*14*/ {"The set of all possible strings over an alphabet Σ", "The empty string", "The concatenation of two strings", "The reversal of a string"},
            /*15*/ {"{A, C, G, T}", "'Hello'", "{1, 2, 3}", "Both a and c"}
    };

    public static final int[] post_test_lesson_1_answers_medium = {
            /*1*/ 2,  // 'An alphabet'
            /*2*/ 3,  // 'String'
            /*3*/ 2,  // 'ε'
            /*4*/ 2,  // '{0, 1}'
            /*5*/ 1,  // '6'
            /*6*/ 2,  // 'Concatenation'
            /*7*/ 1,  // "'baa'"
            /*8*/ 1,  // "'cba'"
            /*9*/ 1,  // "'helloworld'"
            /*10*/ 0,  // "'creet'"
            /*11*/ 0,  // "'ABB'"
            /*12*/ 1,  // '0'
            /*13*/ 3,  // 'All of the above'
            /*14*/ 0,  // 'The set of all possible strings over an alphabet Σ'
            /*15*/ 3   // 'Both a and c'
    };

    /*
    +----------------------+
    |      POST-TEST       |
    |        HARD          |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson1_Hard_Questions() {
        return e_Module_1.get_PostTest_Hard_Questions(
                post_test_lesson_1_questions_hard,
                post_test_lesson_1_answers_hard,
                e_Question.Difficulty.HARD
        );
    }

    public static final String[] post_test_lesson_1_questions_hard = {
            /*1*/ "What type of string has no characters?",
            /*2*/ "What set contains all valid symbols in a formal language?",
            /*3*/ "What is the term for the process of reversing a string?",
            /*4*/ "What symbols are used to define binary operations?",
            /*5*/ "What do we call the length of a string?",
            /*6*/ "What is the formal operation to combine two strings?",
            /*7*/ "Which part of a string comes at the end?",
            /*8*/ "What is the term for any portion found inside a string?",
            /*9*/ "What is a sequence of characters called?",
            /*10*/ "What does the symbol Σ represent?",
            /*11*/ "What is the combination of two strings called?",
            /*12*/ "What is the Greek symbol for an alphabet?",
            /*13*/ "What is the notation for all possible strings?",
            /*14*/ "What do you call the characters found at the start of a string?",
            /*15*/ "Which string includes zero characters?"
    };

    public static final String[] post_test_lesson_1_answers_hard = {
            /*1*/ "empty string",
            /*2*/ "alphabet set",
            /*3*/ "string reversal",
            /*4*/ "binary alphabet",
            /*5*/ "string length",
            /*6*/ "string concatenation",
            /*7*/ "string suffix",
            /*8*/ "string substring",
            /*9*/ "finite string",
            /*10*/ "language set",
            /*11*/ "string combination",
            /*12*/ "sigma symbol",
            /*13*/ "star notation",
            /*14*/ "string prefix",
            /*15*/ "empty sequence"
    };
}
