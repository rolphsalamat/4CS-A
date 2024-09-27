package com.example.autotutoria20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class e_Module_1_2 {

    /*
    +----------------------+
    |       PRE-TEST       |
    |        LESSON 2      |
    +----------------------+
    */

    public static e_Question[] get_PreTest_Lesson2_Questions() {
        return e_Module_1.getPreTestQuestions(
                pre_test_lesson_2_questions,
                pre_test_lesson_2_choices,
                pre_test_lesson_2_answers
        );
    }

    public static final String[] pre_test_lesson_2_questions = {
            /*1*/    "What is the purpose of an alphabet in formal languages?",
            /*2*/    "How is the length of a string 'bbccc' determined?",
            /*3*/    "Which of the following is NOT an example of an alphabet?",
            /*4*/    "What happens to a string when concatenated with the empty string ε?",
            /*5*/    "Which of the following is true about prefixes of strings?",
            /*6*/    "In a DNA sequence, what is an example of an alphabet?",
            /*7*/    "What defines a string reversal in formal language theory?",
            /*8*/    "Which operation is used to combine two strings into one?",
            /*9*/    "If a string 'abc' is reversed, what is the outcome?",
            /*10*/   "How can the set Σ* be described?",
            /*11*/   "What distinguishes a suffix from a prefix in a string?",
            /*12*/   "How is a finite string defined?",
            /*13*/   "What operation is applied when two strings are combined into one?",
            /*14*/   "Which of the following can be considered a substring of 'network'?",
            /*15*/   "What is the Greek symbol for an alphabet in formal languages?"
    };

    public static final String[][] pre_test_lesson_2_choices = {
            /*1*/    {"To create a rule for strings", "To define a finite set of symbols for constructing strings", "To represent numbers", "To organize data"},
            /*2*/    {"Count the unique characters", "Count the total number of symbols in the string", "Count the number of distinct symbols", "Multiply the number of characters by two"},
            /*3*/    {"{a, b, c}", "{0, 1}", "{A, B, C, D}", "'hello'"},
            /*4*/    {"The string is removed", "The string remains unchanged", "The empty string takes over", "The string is reversed"},
            /*5*/    {"A prefix includes all characters except the last", "A prefix must always contain the first symbol of the string", "Prefixes can be any part of the string", "A prefix and suffix are always the same"},
            /*6*/    {"{G, T, A, C}", "{A, B, C}", "{1, 2, 3}", "{X, Y, Z}"},
            /*7*/    {"Adding extra symbols to a string", "Reading the string backwards", "Removing characters from a string", "Doubling the length of a string"},
            /*8*/    {"Reversal", "Concatenation", "Substitution", "Prefixing"},
            /*9*/    {"cab", "bca", "cba", "acb"},
            /*10*/   {"All possible strings over an alphabet, including the empty string", "A set of only one string", "The reversal of an alphabet", "The sum of all characters in an alphabet"},
            /*11*/   {"A suffix appears at the beginning, while a prefix appears at the end", "A suffix appears at the end, while a prefix appears at the beginning", "A suffix includes the entire string, but a prefix doesn't", "They are both interchangeable in a string"},
            /*12*/   {"A string that contains an infinite number of characters", "A sequence of symbols of limited length", "A string that starts with the empty string", "A rule applied to a string"},
            /*13*/   {"Concatenation", "Substring operation", "String duplication", "Inversion"},
            /*14*/   {"net", "work", "ork", "All of the above"},
            /*15*/   {"Σ", "ε", "δ", "π"}
    };

    public static final int[] pre_test_lesson_2_answers = {
            /*1*/    1, // 'To define a finite set of symbols for constructing strings'
            /*2*/    1, // 'Count the total number of symbols in the string'
            /*3*/    3, // "'hello'"
            /*4*/    1, // 'The string remains unchanged'
            /*5*/    1, // 'A prefix must always contain the first symbol of the string'
            /*6*/    0, // '{G, T, A, C}'
            /*7*/    1, // 'Reading the string backwards'
            /*8*/    1, // 'Concatenation'
            /*9*/    2, // 'cba'
            /*10*/   0, // 'All possible strings over an alphabet, including the empty string'
            /*11*/   1, // 'A suffix appears at the end, while a prefix appears at the beginning'
            /*12*/   1, // 'A sequence of symbols of limited length'
            /*13*/   0, // 'Concatenation'
            /*14*/   3, // 'All of the above'
            /*15*/   0  // 'Σ'
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |        EASY          |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson2_Easy_Questions() {
        return e_Module_1.get_PostTest_EasyMedium_Questions(
                post_test_lesson_2_questions_easy,
                post_test_lesson_2_choices_easy,
                post_test_lesson_2_answers_easy,
                e_Question.Difficulty.EASY
        );
    }

    public static final String[] post_test_lesson_2_questions_easy = {
            /*1*/    "Concatenation is the operation of combining two strings to form a new string.",
            /*2*/    "The length of a string is defined as the number of symbols it contains.",
            /*3*/    "Reversing the string 'abcd' results in 'dbca'.",
            /*4*/    "A prefix is a substring that ends at the string's end.",
            /*5*/    "The length of the string 'automata' is 7.",
            /*6*/    "An empty string has no characters but can still be concatenated with other strings.",
            /*7*/    "The notation for the length of a string s is |s|.",
            /*8*/    "The reversal of a string changes the number of symbols it contains.",
            /*9*/    "The empty string can be concatenated with any other string to change its value.",
            /*10*/   "A suffix is a substring that starts from the beginning of a string.",
            /*11*/   "A substring must always contain the entire string to be valid.",
            /*12*/   "The length of the empty string ϵ is 0.",
            /*13*/   "The string 'abc' is a substring of 'abcdef'.",
            /*14*/   "Concatenating a string with itself results in a new string.",
            /*15*/   "The reversal of 'hello' is 'olleh'."
    };

    public static final String[][] post_test_lesson_2_choices_easy = {
            /*1*/    {"True", "False"},
            /*2*/    {"True", "False"},
            /*3*/    {"True", "False"},
            /*4*/    {"True", "False"},
            /*5*/    {"True", "False"},
            /*6*/    {"True", "False"},
            /*7*/    {"True", "False"},
            /*8*/    {"True", "False"},
            /*9*/    {"True", "False"},
            /*10*/   {"True", "False"},
            /*11*/   {"True", "False"},
            /*12*/   {"True", "False"},
            /*13*/   {"True", "False"},
            /*14*/   {"True", "False"},
            /*15*/   {"True", "False"}
    };

    public static final int[] post_test_lesson_2_answers_easy = {
            /*1*/    0, // True
            /*2*/    0, // True
            /*3*/    1, // False
            /*4*/    1, // False
            /*5*/    1, // False
            /*6*/    0, // True
            /*7*/    0, // True
            /*8*/    1, // False
            /*9*/    1, // False
            /*10*/   1, // False
            /*11*/   1, // False
            /*12*/   0, // True
            /*13*/   0, // True
            /*14*/   0, // True
            /*15*/   0  // True
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |        MEDIUM        |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson2_Medium_Questions() {
        return e_Module_1.get_PostTest_EasyMedium_Questions(
                post_test_lesson_2_questions_medium,
                post_test_lesson_2_choices_medium,
                post_test_lesson_2_answers_medium,
                e_Question.Difficulty.MEDIUM
        );
    }

    public static final String[] post_test_lesson_2_questions_medium = {
            /*1*/    "What is the operation of joining two strings end-to-end called?",
            /*2*/    "Which of the following represents the length of the string 'hello'?",
            /*3*/    "What does the notation sR represent in string operations?",
            /*4*/    "Which of the following is a valid prefix of the string 'abcdef'?",
            /*5*/    "The length of the string 'automata' is denoted as:",
            /*6*/    "What is the result of concatenating an empty string to any other string?",
            /*7*/    "The reversal of the string 'abcd' is:",
            /*8*/    "Which operation extracts a portion of a string?",
            /*9*/    "For the string 'hello', which is a valid suffix?",
            /*10*/   "If s = 'hello', what is the length of the string s?",
            /*11*/   "Which of the following operations reverses the characters of a string?",
            /*12*/   "Which of the following is an empty string property?",
            /*13*/   "The string 'auto' is a valid ____ of 'automata'.",
            /*14*/   "The length of the string s = 'automata' is denoted by:",
            /*15*/   "Which of the following describes a substring?"
    };

    public static final String[][] post_test_lesson_2_choices_medium = {
            /*1*/    {"Substring", "Prefix", "Suffix", "Concatenation"},
            /*2*/    {"4", "5", "6", "3"},
            /*3*/    {"Concatenation of string", "Reversal of string", "Length of string", "Suffix of string"},
            /*4*/    {"'def'", "'f'", "'abc'", "'bcd'"},
            /*5*/    {"5", "7", "8", "6"},
            /*6*/    {"The empty string", "A longer string", "The same string", "A different string"},
            /*7*/    {"'dbca'", "'abcd'", "'dcba'", "'bcda'"},
            /*8*/    {"Concatenation", "Reversal", "Substring", "Prefix"},
            /*9*/    {"'he'", "'o'", "'ell'", "'hello'"},
            /*10*/   {"4", "5", "6", "3"},
            /*11*/   {"Concatenation", "Suffix extraction", "Reversal", "Substring extraction"},
            /*12*/   {"Length is 1", "It changes a string when concatenated", "It can be reversed", "Its length is 0"},
            /*13*/   {"Prefix", "Suffix", "Substring", "Concatenation"},
            /*14*/   {"|sR|", "|ϵ|", "|s|", "s"},
            /*15*/   {"A string obtained by reversing the original string", "A string obtained by combining two strings", "A sequence of characters extracted from another string", "A string that starts at the end of the original string"}
    };

    public static final int[] post_test_lesson_2_answers_medium = {
            /*1*/    3, // 'Concatenation'
            /*2*/    1, // '5'
            /*3*/    1, // 'Reversal of string'
            /*4*/    2, // "'abc'"
            /*5*/    1, // '7'
            /*6*/    2, // 'The same string'
            /*7*/    2, // "'dcba'"
            /*8*/    2, // 'Substring'
            /*9*/    1, // "'o'"
            /*10*/   1, // '5'
            /*11*/   2, // 'Reversal'
            /*12*/   3, // 'Its length is 0'
            /*13*/   0, // 'Prefix'
            /*14*/   2, // '|s|'
            /*15*/   2  // 'A sequence of characters extracted from another string'
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |         HARD         |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson2_Hard_Questions() {
        return e_Module_1.get_PostTest_Hard_Questions(
                post_test_lesson_2_questions_hard,
                post_test_lesson_2_answers_hard,
                e_Question.Difficulty.HARD
        );
    }

    public static final String[] post_test_lesson_2_questions_hard = {
            /*1*/    "The operation of joining two strings to form a new string is called ____. ",
            /*2*/    "The number of characters in a string is called its ____.",
            /*3*/    "The string obtained by writing the characters in reverse order is called ____.",
            /*4*/    "A sequence of characters within another string is known as a ____.",
            /*5*/    "A substring that begins at the start of a string is called a ____.",
            /*6*/    "A substring that ends at the end of a string is called a ____.",
            /*7*/    "The string with no characters is called the ____.",
            /*8*/    "The length of the empty string is ____. ",
            /*9*/    "If s = 'abcd', the reversal of s is ____. ",
            /*10*/   "The notation used for the reversal of a string is ____. ",
            /*11*/   "The operation of extracting a portion of a string is called ____. ",
            /*12*/   "The concatenation of 'abc' and 'def' results in ____. ",
            /*13*/   "The length of the string 'abcdef' is ____. ",
            /*14*/   "The empty string is denoted by the symbol ____. ",
            /*15*/   "The substring 'auto' is a ____ of 'automata'."
    };

    public static final String[] post_test_lesson_2_answers_hard = {
            /*1*/    "concatenation",
            /*2*/    "length",
            /*3*/    "reversal",
            /*4*/    "substring",
            /*5*/    "prefix",
            /*6*/    "suffix",
            /*7*/    "empty string",
            /*8*/    "0",
            /*9*/    "dcba",
            /*10*/   "sR",
            /*11*/   "substring extraction",
            /*12*/   "abcdef",
            /*13*/   "6",
            /*14*/   "epsilon",
            /*15*/   "prefix"
    };
}
