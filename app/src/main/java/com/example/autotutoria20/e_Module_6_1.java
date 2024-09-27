package com.example.autotutoria20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class e_Module_6_1 {

    /*
    +----------------------+
    |       PRE-TEST       |
    |        LESSON 1      |
    +----------------------+
    */

    public static e_Question[] get_PreTest_Lesson1_Questions() {
        return e_Module_6.getPreTestQuestions(
                pre_test_lesson_1_questions,
                pre_test_lesson_1_choices,
                pre_test_lesson_1_answers
        );
    }

    public static final String[] pre_test_lesson_1_questions = {
            /*1*/    "Which derivation method is typically easier to understand for beginners learning grammars?",
            /*2*/    "In context-free grammar, what type of derivation is often used in predictive parsers?",
            /*3*/    "When using a rightmost derivation, how does the order of non-terminal replacements progress?",
            /*4*/    "Which of the following derivation methods is most commonly utilized in bottom-up parsing techniques?",
            /*5*/    "How can a grammar be determined to be ambiguous based on its derivations?",
            /*6*/    "In a context-free grammar, which type of derivation ensures that non-terminal symbols are expanded starting from the leftmost part of the string?",
            /*7*/    "Which derivation process is used to check for ambiguity in a given grammar?",
            /*8*/    "When expanding a context-free grammar using rightmost derivation, which part of the string is transformed first?",
            /*9*/    "In leftmost derivation, how does the order of production rule application affect the derivation process?",
            /*10*/   "Which of the following would you check to confirm that two derivation trees are identical?",
            /*11*/   "In what scenario would leftmost and rightmost derivations result in different parse trees?",
            /*12*/   "Why is rightmost derivation commonly used in conjunction with bottom-up parsers?",
            /*13*/   "Which derivation approach would you choose to trace the syntactic structure of a sentence from the start symbol?",
            /*14*/   "How does leftmost derivation differ from rightmost derivation in terms of parse tree generation?",
            /*15*/   "What ensures that both leftmost and rightmost derivations can generate the same final string?"
    };

    public static final String[][] pre_test_lesson_1_choices = {
            /*1*/    {"Rightmost Derivation", "Leftmost Derivation", "Random Derivation", "None of the above"},
            /*2*/    {"Rightmost Derivation", "Leftmost Derivation", "Ambiguous Derivation", "Mixed Derivation"},
            /*3*/    {"From left to right", "From right to left", "Randomly", "Simultaneously"},
            /*4*/    {"Leftmost Derivation", "Rightmost Derivation", "Recursive Derivation", "None of the above"},
            /*5*/    {"When multiple derivation trees are possible for a single string", "When only leftmost derivation is possible", "When all derivations lead to the same parse tree", "When derivation produces terminal symbols only"},
            /*6*/    {"Rightmost Derivation", "Leftmost Derivation", "Random Derivation", "Mixed Derivation"},
            /*7*/    {"Rightmost Derivation only", "Leftmost Derivation only", "Both Leftmost and Rightmost Derivations", "None of the above"},
            /*8*/    {"The first non-terminal from the left", "The first terminal from the right", "The rightmost non-terminal", "The middle non-terminal"},
            /*9*/    {"It starts with the last production rule", "It always begins by applying the leftmost rule first", "It applies rules in no specific order", "All rules are applied simultaneously"},
            /*10*/   {"The number of terminal symbols used", "The sequence in which non-terminal symbols were replaced", "The order of production rules applied", "The parse trees generated from the derivation"},
            /*11*/   {"When the grammar is ambiguous", "When the grammar is context-sensitive", "When there are no non-terminal symbols left", "When both derivations are incomplete"},
            /*12*/   {"It simplifies the derivation process", "It aligns with the way bottom-up parsers work, processing from right to left", "It is the only derivation method recognized by parsers", "It always results in fewer production rules"},
            /*13*/   {"Rightmost Derivation", "Leftmost Derivation", "Random Derivation", "Backward Derivation"},
            /*14*/   {"Leftmost derivation always creates a balanced tree", "Rightmost derivation applies rules more randomly", "Leftmost derivation constructs the tree from the top down", "Rightmost derivation skips non-terminal expansions"},
            /*15*/   {"The use of the same context-free grammar rules", "The replacement of terminal symbols only", "The application of different grammars", "The elimination of all non-terminal symbols"}
    };

    public static final int[] pre_test_lesson_1_answers = {
            /*1*/    1, // Leftmost Derivation
            /*2*/    1, // Leftmost Derivation
            /*3*/    1, // From right to left
            /*4*/    1, // Rightmost Derivation
            /*5*/    0, // When multiple derivation trees are possible for a single string
            /*6*/    1, // Leftmost Derivation
            /*7*/    2, // Both Leftmost and Rightmost Derivations
            /*8*/    2, // The rightmost non-terminal
            /*9*/    1, // It always begins by applying the leftmost rule first
            /*10*/   3, // The parse trees generated from the derivation
            /*11*/   0, // When the grammar is ambiguous
            /*12*/   1, // It aligns with the way bottom-up parsers work
            /*13*/   1, // Leftmost Derivation
            /*14*/   2, // Leftmost derivation constructs the tree from the top down
            /*15*/   0  // The use of the same context-free grammar rules
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |         EASY         |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson1_Easy_Questions() {
        return e_Module_6.get_PostTest_EasyMedium_Questions(
                post_test_lesson_1_questions_easy,
                post_test_lesson_1_choices_easy,
                post_test_lesson_1_answers_easy,
                e_Question.Difficulty.EASY
        );
    }

    public static final String[] post_test_lesson_1_questions_easy = {
            /*1*/    "Context-free grammars can generate ambiguous languages.",
            /*2*/    "Ambiguity in a grammar means that a string has more than one leftmost derivation.",
            /*3*/    "An ambiguous grammar always generates an ambiguous language.",
            /*4*/    "Every language that can be generated by an ambiguous grammar can also be generated by an unambiguous grammar.",
            /*5*/    "All context-free languages are inherently ambiguous.",
            /*6*/    "A context-free language can be ambiguous even if no context-free grammar for it is ambiguous.",
            /*7*/    "If a grammar is ambiguous, then there exists no unambiguous grammar for the language it generates.",
            /*8*/    "Parsing techniques like LL(1) and LR(1) are designed to handle ambiguous grammars.",
            /*9*/    "Ambiguity in a grammar can lead to multiple parse trees for the same string.",
            /*10*/   "Grammar ambiguity can be resolved by left factoring the grammar rules.",
            /*11*/   "The existence of ambiguity is a property of the language, not just the grammar.",
            /*12*/   "Ambiguous grammars are generally preferred for efficient parsing in compilers.",
            /*13*/   "A language is ambiguous if it is generated by an ambiguous grammar.",
            /*14*/   "Grammars that produce multiple parse trees for the same string are considered ambiguous.",
            /*15*/   "Ambiguity in grammars can complicate the design of parsers."
    };

    public static final String[][] post_test_lesson_1_choices_easy = {
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

    public static final int[] post_test_lesson_1_answers_easy = {
            /*1*/    0, // True
            /*2*/    0, // True
            /*3*/    1, // False
            /*4*/    1, // False
            /*5*/    1, // False
            /*6*/    0, // True
            /*7*/    1, // False
            /*8*/    1, // False
            /*9*/    0, // True
            /*10*/   0, // True
            /*11*/   0, // True
            /*12*/   1, // False
            /*13*/   0, // True
            /*14*/   0, // True
            /*15*/   0  // True
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |       MEDIUM         |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson1_Medium_Questions() {
        return e_Module_6.get_PostTest_EasyMedium_Questions(
                post_test_lesson_1_questions_medium,
                post_test_lesson_1_choices_medium,
                post_test_lesson_1_answers_medium,
                e_Question.Difficulty.MEDIUM
        );
    }

    public static final String[] post_test_lesson_1_questions_medium = {
            /*1*/    "What is the definition of ambiguity in context-free grammar?",
            /*2*/    "Which of the following is true for ambiguous grammars?",
            /*3*/    "Which of the following parsing techniques can handle ambiguous grammars?",
            /*4*/    "Which of the following is an approach to eliminate ambiguity in a grammar?",
            /*5*/    "What is a context-free grammar?",
            /*6*/    "Ambiguous languages can be:",
            /*7*/    "Which term refers to multiple derivation trees for the same string?",
            /*8*/    "What is a parse tree?",
            /*9*/    "Which of the following can lead to ambiguity in grammar?",
            /*10*/   "In context-free grammars, ambiguity can result in:",
            /*11*/   "What is the primary issue with ambiguous grammars in compiler design?",
            /*12*/   "Which of the following cannot be used to prove ambiguity in a grammar?",
            /*13*/   "Which type of grammar is designed to generate only one parse tree for each valid string?",
            /*14*/   "Which parsing method is incapable of handling ambiguity?",
            /*15*/   "Why is ambiguity problematic in grammars?"
    };

    public static final String[][] post_test_lesson_1_choices_medium = {
            /*1*/    {"A grammar that generates more than one language", "A grammar that produces more than one leftmost derivation for some string", "A grammar that generates context-sensitive languages", "A grammar that cannot generate a parse tree"},
            /*2*/    {"They always lead to parsing errors", "They generate a string with more than one parse tree", "They can only generate finite languages", "They only apply to deterministic parsers"},
            /*3*/    {"LL(1)", "LR(1)", "GLR", "SLR"},
            /*4*/    {"Introducing left recursion", "Left factoring", "Using LL(1) parser", "Adding more productions"},
            /*5*/    {"A grammar where the left-hand side of each rule contains exactly one non-terminal", "A grammar where each production has at least two non-terminals", "A grammar used only for arithmetic expressions", "A grammar that generates ambiguous languages"},
            /*6*/    {"Generated by only ambiguous grammars", "Generated by both ambiguous and unambiguous grammars", "Generated only by context-sensitive grammars", "Generated by deterministic parsers"},
            /*7*/    {"Syntax error", "Ambiguity", "Parsing failure", "Non-determinism"},
            /*8*/    {"A tree that represents all possible derivations of a string", "A tree that represents the hierarchical structure of a string according to a grammar", "A tree that represents a sequence of tokens", "A tree that shows only terminal symbols"},
            /*9*/    {"Left recursion", "Right recursion", "Overlapping productions", "Grammar simplification"},
            /*10*/   {"Infinite parse trees", "Multiple valid parse trees for a single string", "Syntax errors", "Deterministic parsing"},
            /*11*/   {"They increase the size of the language", "They make parsing deterministic", "They create confusion in parsing", "They require fewer parsing steps"},
            /*12*/   {"Left recursion", "Right recursion", "Multiple parse trees for the same string", "Multiple leftmost derivations for the same string"},
            /*13*/   {"Ambiguous grammar", "Unambiguous grammar", "Recursive grammar", "Non-terminal grammar"},
            /*14*/   {"GLR", "LR(1)", "LL(1)", "CYK"},
            /*15*/   {"It simplifies grammar analysis", "It leads to multiple parse trees, complicating the parsing process", "It improves parsing speed", "It can only be resolved using GLR parsing"}
    };

    public static final int[] post_test_lesson_1_answers_medium = {
            /*1*/    1, // A grammar that produces more than one leftmost derivation for some string
            /*2*/    1, // They generate a string with more than one parse tree
            /*3*/    2, // GLR
            /*4*/    1, // Left factoring
            /*5*/    0, // A grammar where the left-hand side of each rule contains exactly one non-terminal
            /*6*/    1, // Generated by both ambiguous and unambiguous grammars
            /*7*/    1, // Ambiguity
            /*8*/    1, // A tree that represents the hierarchical structure of a string according to a grammar
            /*9*/    2, // Overlapping productions
            /*10*/   1, // Multiple valid parse trees for a single string
            /*11*/   2, // They create confusion in parsing
            /*12*/   3, // Multiple leftmost derivations for the same string
            /*13*/   1, // Unambiguous grammar
            /*14*/   2, // LL(1)
            /*15*/   1  // It leads to multiple parse trees, complicating the parsing process
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |         HARD         |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson1_Hard_Questions() {
        return e_Module_6.get_PostTest_Hard_Questions(
                post_test_lesson_1_questions_hard,
                post_test_lesson_1_answers_hard,
                e_Question.Difficulty.HARD
        );
    }

    public static final String[] post_test_lesson_1_questions_hard = {
            /*1*/    "The algorithm that builds a tree structure from input to validate a grammar.",
            /*2*/    "The part of a parser that reads input and produces a parse tree.",
            /*3*/    "The process of analyzing whether a grammar can be parsed in more than one way.",
            /*4*/    "The element of a context-free grammar that cannot be further expanded into other symbols.",
            /*5*/    "The property of a context-free grammar that allows only one unique leftmost derivation per string.",
            /*6*/    "A data structure used in parsing to represent the syntax of strings.",
            /*7*/    "A type of recursion that does not contribute to ambiguity in grammars.",
            /*8*/    "The method used in compilers to remove unnecessary rules in grammars.",
            /*9*/    "The procedure of rewriting a grammar to remove common prefixes to help disambiguate.",
            /*10*/   "A characteristic of context-free languages where more than one derivation is possible for some strings.",
            /*11*/   "A grammar that allows both leftmost and rightmost derivations to produce different results.",
            /*12*/   "The concept of allowing multiple syntactic structures for the same sentence in natural language processing.",
            /*13*/   "A tool used by a compiler to convert a context-free grammar into machine-readable format.",
            /*14*/   "A type of language that cannot be parsed using a context-free grammar.",
            /*15*/   "A technique in parsing used to reduce ambiguity by assigning priority to certain rules."
    };

    public static final String[] post_test_lesson_1_answers_hard = {
            /*1*/    "parser",
            /*2*/    "syntax analyzer",
            /*3*/    "ambiguity detection",
            /*4*/    "terminal",
            /*5*/    "unambiguity",
            /*6*/    "parse tree",
            /*7*/    "left recursion",
            /*8*/    "grammar simplification",
            /*9*/    "left factoring",
            /*10*/   "ambiguity",
            /*11*/   "ambiguous grammar",
            /*12*/   "structural ambiguity",
            /*13*/   "parser generator",
            /*14*/   "context-sensitive language",
            /*15*/   "precedence"
    };

}