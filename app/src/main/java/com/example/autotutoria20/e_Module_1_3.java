package com.example.autotutoria20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class e_Module_1_3 {

    /*
    +----------------------+
    |       PRE-TEST       |
    |        LESSON 3      |
    +----------------------+
    */

    public static e_Question[] get_PreTest_Lesson3_Questions() {
        return e_Module_1.getPreTestQuestions(
                pre_test_lesson_3_questions,
                pre_test_lesson_3_choices,
                pre_test_lesson_3_answers
        );
    }

    public static final String[] pre_test_lesson_3_questions = {
            /*1*/    "What is the outcome when you concatenate two strings, \"xyz\" and \"123\"?",
            /*2*/    "If the string s = \"computer\", which of the following is not a substring?",
            /*3*/    "The string s = \"hello\". What is the length of the substring \"hell\"?",
            /*4*/    "If s = \"automation\", what would be the reverse of the string \"autom\"?",
            /*5*/    "Which of the following is not an operation on strings?",
            /*6*/    "What is the result of reversing the string \"level\"?",
            /*7*/    "For the string s = \"sunshine\", which is a valid prefix?",
            /*8*/    "What happens when you concatenate a string with itself, such as s = \"abc\"?",
            /*9*/    "The notation |s|, where s is a string, refers to:",
            /*10*/   "What is the correct way to represent an empty string?",
            /*11*/   "For the string s = \"algorithm\", which of the following is a valid suffix?",
            /*12*/   "The length of a string is defined as:",
            /*13*/   "Which of the following is a characteristic of an empty string?",
            /*14*/   "What is the result of concatenating an empty string (ϵ) with \"abcd\"?",
            /*15*/   "Which operation would extract \"comp\" from the string \"computer\"?"
    };

    public static final String[][] pre_test_lesson_3_choices = {
            /*1*/    {"\"xyz123\"", "\"123xyz\"", "\"xy\"", "\"yz123\""},
            /*2*/    {"\"comp\"", "\"puter\"", "\"compt\"", "\"uter\""},
            /*3*/    {"3", "4", "5", "6"},
            /*4*/    {"\"motua\"", "\"otuma\"", "\"tomau\"", "\"tamou\""},
            /*5*/    {"Concatenation", "Substring extraction", "Multiplication", "Reversal"},
            /*6*/    {"\"level\"", "\"evell\"", "\"lleve\"", "\"vel\""},
            /*7*/    {"\"shine\"", "\"uns\"", "\"sun\"", "\"hine\""},
            /*8*/    {"\"abcabc\"", "\"abccba\"", "\"cababc\"", "\"bacabc\""},
            /*9*/    {"The reverse of the string", "The length of the string", "The substring of the string", "The concatenation of the string"},
            /*10*/   {"∅", "ϵ", "𝜎", "𝛿"},
            /*11*/   {"\"go\"", "\"rithm\"", "\"ith\"", "\"algor\""},
            /*12*/   {"The number of unique characters in the string", "The number of symbols in the string", "The number of consonants in the string", "The number of vowels in the string"},
            /*13*/   {"It contains one character", "Its length is always zero", "Its reversal results in a non-empty string", "Concatenating it with another string alters the original string"},
            /*14*/   {"\"abcd\"", "\"cbda\"", "\"dcba\"", "\"bcda\""},
            /*15*/   {"Reversal", "Concatenation", "Substring extraction", "Prefix"}
    };

    public static final int[] pre_test_lesson_3_answers = {
            /*1*/    0, // "xyz123"
            /*2*/    2, // "compt"
            /*3*/    1, // 4
            /*4*/    0, // "motua"
            /*5*/    2, // Multiplication
            /*6*/    0, // "level"
            /*7*/    2, // "sun"
            /*8*/    0, // "abcabc"
            /*9*/    1, // The length of the string
            /*10*/   1, // ϵ
            /*11*/   1, // "rithm"
            /*12*/   1, // The number of symbols in the string
            /*13*/   1, // Its length is always zero
            /*14*/   0, // "abcd"
            /*15*/   2  // Substring extraction
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |        EASY MODE     |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson3_Easy_Questions() {
        return e_Module_1.get_PostTest_EasyMedium_Questions(
                post_test_lesson_3_questions_easy,
                post_test_lesson_3_choices_easy,
                post_test_lesson_3_answers_easy,
                e_Question.Difficulty.EASY
        );
    }

    public static final String[] post_test_lesson_3_questions_easy = {
            /*1*/    "A formal language is defined by specific grammatical rules.",
            /*2*/    "An alphabet in formal languages can consist of an infinite set of symbols.",
            /*3*/    "Strings in a formal language are composed of symbols from the alphabet.",
            /*4*/    "In automata theory, a formal language is recognized by a formal machine.",
            /*5*/    "Programming languages do not rely on formal languages for syntax definition.",
            /*6*/    "The Chomsky hierarchy groups formal languages into classes based on the types of machines that recognize them.",
            /*7*/    "The English alphabet is an example of a binary alphabet.",
            /*8*/    "Balanced parentheses are an example of a formal language.",
            /*9*/    "A string is defined as an infinite sequence of symbols from an alphabet.",
            /*10*/   "Formal languages have no application in automata theory.",
            /*11*/   "Regular expressions can be used to specify a formal language.",
            /*12*/   "Compilers use formal languages to translate high-level code into machine code.",
            /*13*/   "Strings that do not follow grammatical rules still belong to a formal language.",
            /*14*/   "Natural languages can be analyzed using the concepts of formal languages.",
            /*15*/   "The binary alphabet consists of the symbols {a, b, c, ..., z}."
    };

    public static final String[][] post_test_lesson_3_choices_easy = {
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

    public static final int[] post_test_lesson_3_answers_easy = {
            /*1*/    0, // True
            /*2*/    1, // False
            /*3*/    0, // True
            /*4*/    0, // True
            /*5*/    1, // False
            /*6*/    0, // True
            /*7*/    1, // False
            /*8*/    0, // True
            /*9*/    1, // False
            /*10*/   1, // False
            /*11*/   0, // True
            /*12*/   0, // True
            /*13*/   1, // False
            /*14*/   0, // True
            /*15*/   1  // False
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |       MEDIUM MODE    |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson3_Medium_Questions() {
        return e_Module_1.get_PostTest_EasyMedium_Questions(
                post_test_lesson_3_questions_medium,
                post_test_lesson_3_choices_medium,
                post_test_lesson_3_answers_medium,
                e_Question.Difficulty.MEDIUM
        );
    }

    public static final String[] post_test_lesson_3_questions_medium = {
            /*1*/    "What is a formal language?",
            /*2*/    "What is an alphabet in the context of formal languages?",
            /*3*/    "Which of the following is an example of a binary alphabet?",
            /*4*/    "What is a string in a formal language?",
            /*5*/    "Which of the following represents a valid string for the binary alphabet Σ = {0 1}?",
            /*6*/    "What is grammar in the context of formal languages?",
            /*7*/    "Which of the following is an example of a formal language with balanced parentheses?",
            /*8*/    "What is the primary application of formal languages in computer science?",
            /*9*/    "In automata theory what does a formal language consist of?",
            /*10*/   "What is a Chomsky hierarchy?",
            /*11*/   "What is an example of an application of formal languages in natural language processing?",
            /*12*/   "What does a compiler use formal languages for?",
            /*13*/   "Which of the following is NOT a component of a formal language?",
            /*14*/   "What defines whether a string belongs to a formal language?",
            /*15*/   "What is the significance of formal languages in automata theory?"
    };

    public static final String[][] post_test_lesson_3_choices_medium = {
            /*1*/    {"A spoken language used in formal settings", "A set of strings defined by grammatical rules", "A language without any grammar", "A casual language used in everyday conversation"},
            /*2*/    {"A sequence of words", "A set of rules", "A finite set of symbols", "A grammar book"},
            /*3*/    {"Σ = {a b c}", "Σ = {0 1}", "Σ = {A B C}", "Σ = {x y z}"},
            /*4*/    {"A combination of rules", "A finite sequence of symbols from an alphabet", "A set of grammatical rules", "An infinite sequence of numbers"},
            /*5*/    {"\"a01b\"", "\"0101\"", "\"xyz\"", "\"abc\""},
            /*6*/    {"The rules for forming strings from an alphabet", "A set of spoken words", "A dictionary of symbols", "The order of letters in an alphabet"},
            /*7*/    {"\"(()))\"", "\"(())\"", "\"((\"", "\")((\""},
            /*8*/    {"Writing novels", "Defining the syntax and semantics of programming languages", "Translating spoken languages", "Designing video games"},
            /*9*/    {"Only symbols without rules", "A set of strings drawn from a finite alphabet", "A spoken language", "A collection of numbers"},
            /*10*/   {"A classification of languages based on their spoken complexity", "A grouping of formal languages into successively larger classes", "A list of alphabets used in languages", "A ranking of languages based on cultural importance"},
            /*11*/   {"Studying computational problems", "Modeling and analyzing the structure of natural languages", "Building video games", "Writing stories"},
            /*12*/   {"To parse and translate high-level programming code", "To write stories", "To build websites", "To create graphic designs"},
            /*13*/   {"Alphabet", "String", "Grammar", "Sound"},
            /*14*/   {"The string's length", "Specific grammatical rules", "The string's color", "The string's meaning"},
            /*15*/   {"They define spoken languages", "They form the basis for studying abstract machines and computational problems", "They create visual art", "They classify natural languages"}
    };

    public static final int[] post_test_lesson_3_answers_medium = {
            /*1*/    1, // A set of strings defined by grammatical rules
            /*2*/    2, // A finite set of symbols
            /*3*/    1, // Σ = {0 1}
            /*4*/    1, // A finite sequence of symbols from an alphabet
            /*5*/    1, // "0101"
            /*6*/    0, // The rules for forming strings from an alphabet
            /*7*/    1, // "(())"
            /*8*/    1, // Defining the syntax and semantics of programming languages
            /*9*/    1, // A set of strings drawn from a finite alphabet
            /*10*/   1, // A grouping of formal languages into successively larger classes
            /*11*/   1, // Modeling and analyzing the structure of natural languages
            /*12*/   0, // To parse and translate high-level programming code
            /*13*/   3, // Sound
            /*14*/   1, // Specific grammatical rules
            /*15*/   1  // They form the basis for studying abstract machines and computational problems
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |         HARD         |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson3_Hard_Questions() {
        return e_Module_1.get_PostTest_Hard_Questions(
                post_test_lesson_3_questions_hard,
                post_test_lesson_3_answers_hard,
                e_Question.Difficulty.HARD
        );
    }

    public static final String[] post_test_lesson_3_questions_hard = {
            /*1*/    "What is the finite set of symbols used to construct strings in a formal language?",
            /*2*/    "What do we call a finite sequence of symbols from an alphabet?",
            /*3*/    "What component of a formal language specifies the structure and rules for forming strings?",
            /*4*/    "Which theory studies abstract machines and the problems they can solve using formal languages?",
            /*5*/    "What is the classification of formal languages into successively larger classes?",
            /*6*/    "Which component of formal languages is essential in defining programming languages?",
            /*7*/    "In what field are formal languages used to parse and translate high-level programming code?",
            /*8*/    "What is the term for a set of rules that generates a formal language?",
            /*9*/    "What type of language consists of all strings made up of 0s and 1s?",
            /*10*/   "What do you call a string in a formal language that does not follow the grammatical rules?",
            /*11*/   "Which language uses the alphabet Σ = {()} and consists of all strings with balanced parentheses?",
            /*12*/   "In computer science, what is the use of formal languages in ensuring program correctness?",
            /*13*/   "What is a key application of formal languages in modeling and analyzing natural languages?",
            /*14*/   "Which structure defines the syntax and semantics of programming languages?",
            /*15*/   "What is the process of converting high-level programming language to machine code using formal languages?"
    };

    public static final String[] post_test_lesson_3_answers_hard = {
            /*1*/    "formal alphabet",
            /*2*/    "formal string",
            /*3*/    "formal grammar",
            /*4*/    "automata theory",
            /*5*/    "chomsky hierarchy",
            /*6*/    "formal syntax",
            /*7*/    "compiler design",
            /*8*/    "grammar rules",
            /*9*/    "binary language",
            /*10*/   "invalid string",
            /*11*/   "parenthesis language",
            /*12*/   "formal verification",
            /*13*/   "linguistic analysis",
            /*14*/   "formal grammar",
            /*15*/   "code compilation"
    };
}
