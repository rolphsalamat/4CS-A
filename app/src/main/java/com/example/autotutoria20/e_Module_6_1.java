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
            /*1*/    "What is the main characteristic of a leftmost derivation?",
            /*2*/    "In a rightmost derivation which non-terminal is expanded first?",
            /*3*/    "What do leftmost and rightmost derivations have in common?",
            /*4*/    "Which parsing method typically uses leftmost derivations?",
            /*5*/    "Which parsing method typically uses rightmost derivations?",
            /*6*/    "Which of the following is an example of a terminal symbol in a derivation?",
            /*7*/    "In leftmost derivation which non-terminal is expanded at each step?",
            /*8*/    "What is the result of both leftmost and rightmost derivations?",
            /*9*/    "Which of the following describes a rightmost derivation?",
            /*10*/   "In the context of derivations what is a start symbol?",
            /*11*/   "Which symbol in a derivation is typically expanded during a derivation step?",
            /*12*/   "What do derivations help in analyzing?",
            /*13*/   "What is the key difference between leftmost and rightmost derivations?",
            /*14*/   "Which of the following is a non-terminal symbol in grammar?",
            /*15*/   "Which of the following statements is true regarding derivations in formal grammar?"
    };

    public static final String[][] pre_test_lesson_1_choices = {
            /*1*/    {"Expands the rightmost non-terminal first", "Expands the leftmost non-terminal first", "Expands both left and right non-terminals simultaneously", "Expands only terminal symbols"},
            /*2*/    {"The leftmost non-terminal", "The rightmost non-terminal", "Any non-terminal can be expanded first", "Only the start symbol"},
            /*3*/    {"They both expand the leftmost non-terminal first", "They both expand the rightmost non-terminal first", "They both follow the same grammar rules to generate strings", "They both result in different final strings"},
            /*4*/    {"Bottom-up parsing", "Top-down parsing", "In-order parsing", "Pre-order parsing"},
            /*5*/    {"Top-down parsing", "Pre-order parsing", "Bottom-up parsing", "In-order parsing"},
            /*6*/    {"Start symbol", "A specific letter or digit in a string", "Non-terminal symbols", "Grammar rules"},
            /*7*/    {"The rightmost non-terminal", "Any non-terminal", "The leftmost non-terminal", "Only terminal symbols"},
            /*8*/    {"The same final string", "Different strings", "Incomplete derivations", "No valid output"},
            /*9*/    {"It expands all non-terminals simultaneously", "It always starts with terminal symbols", "It expands the rightmost non-terminal at each step", "It only applies to recursive grammars"},
            /*10*/   {"The symbol that represents terminal symbols", "The first non-terminal symbol from which derivation starts", "The last symbol of a string", "A symbol that ends a derivation"},
            /*11*/   {"Only terminal symbols", "The leftmost or rightmost non-terminal symbol", "Any terminal symbol", "Start symbol only"},
            /*12*/   {"Semantics of a language", "Syntactic structure of a language", "The meaning of programming keywords", "The compilation process"},
            /*13*/   {"The final output string", "The order in which terminal symbols are expanded", "The order in which non-terminal symbols are expanded", "The grammar rules applied"},
            /*14*/   {"A lowercase letter", "An uppercase letter", "A punctuation mark", "A keyword"},
            /*15*/   {"Derivations cannot use the same rules for both leftmost and rightmost expansions", "Derivations are used to generate valid strings based on grammar rules", "Only rightmost derivations are used in syntax analysis", "Derivations ignore non-terminal symbols"}
    };

    public static final int[] pre_test_lesson_1_answers = {
            /*1*/    1, // 'Expands the leftmost non-terminal first'
            /*2*/    1, // 'The rightmost non-terminal'
            /*3*/    2, // 'They both follow the same grammar rules to generate strings'
            /*4*/    1, // 'Top-down parsing'
            /*5*/    2, // 'Bottom-up parsing'
            /*6*/    1, // 'A specific letter or digit in a string'
            /*7*/    2, // 'The leftmost non-terminal'
            /*8*/    0, // 'The same final string'
            /*9*/    2, // 'It expands the rightmost non-terminal at each step'
            /*10*/   1, // 'The first non-terminal symbol from which derivation starts'
            /*11*/   1, // 'The leftmost or rightmost non-terminal symbol'
            /*12*/   1, // 'Syntactic structure of a language'
            /*13*/   2, // 'The order in which non-terminal symbols are expanded'
            /*14*/   1, // 'An uppercase letter'
            /*15*/   1  // 'Derivations are used to generate valid strings based on grammar rules'
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |        EASY          |
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
            /*1*/    "A grammar is ambiguous if it generates exactly one parse tree for every sentence.",
            /*2*/    "Every ambiguous grammar has at least one sentence with multiple parse trees.",
            /*3*/    "Ambiguity in grammar can complicate the process of parsing a sentence.",
            /*4*/    "A sentence with more than one derivation is always unambiguous.",
            /*5*/    "Context-free grammars (CFGs) are used in defining programming languages.",
            /*6*/    "Ambiguity is a desirable property in grammars used for programming languages.",
            /*7*/    "It is impossible to eliminate ambiguity in all ambiguous grammars.",
            /*8*/    "Ambiguous grammars can lead to multiple interpretations of the same input string.",
            /*9*/    "All CFGs are inherently ambiguous.",
            /*10*/   "Ambiguity affects both natural languages and programming languages.",
            /*11*/   "A grammar is unambiguous if it has more than one derivation for at least one sentence.",
            /*12*/   "Ambiguity can arise when there are two or more valid parse trees for a single sentence.",
            /*13*/   "Eliminating ambiguity is always straightforward in grammars used for natural languages.",
            /*14*/   "Ambiguous grammars always result in multiple derivations for every sentence.",
            /*15*/   "Context-free grammar can represent recursive structures."
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
            /*1*/    1, // False
            /*2*/    0, // True
            /*3*/    0, // True
            /*4*/    1, // False
            /*5*/    0, // True
            /*6*/    1, // False
            /*7*/    0, // True
            /*8*/    0, // True
            /*9*/    1, // False
            /*10*/   0, // True
            /*11*/   1, // False
            /*12*/   0, // True
            /*13*/   1, // False
            /*14*/   1, // False
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
            /*1*/    "What defines an ambiguous grammar?",
            /*2*/    "Which of the following is true about ambiguous grammars?",
            /*3*/    "What is the primary challenge of ambiguity in grammars?",
            /*4*/    "Which of the following is an example of an ambiguous expression?",
            /*5*/    "Why is ambiguity undesirable in programming language grammars?",
            /*6*/    "In which type of language is ambiguity more frequently encountered?",
            /*7*/    "A grammar is called ambiguous if it:",
            /*8*/    "Which type of grammar does not allow ambiguity?",
            /*9*/    "Ambiguous grammar is problematic in:",
            /*10*/   "What is a possible solution to resolve ambiguity in a grammar?",
            /*11*/   "Ambiguity in context-free grammar often leads to:",
            /*12*/   "Which of the following helps resolve ambiguity in grammars?",
            /*13*/   "Which of the following sentences would most likely have an ambiguous parse tree?",
            /*14*/   "What makes a sentence ambiguous in a CFG?",
            /*15*/   "In terms of CFG, what is ambiguity?"
    };

    public static final String[][] post_test_lesson_1_choices_medium = {
            /*1*/    {"A grammar with no production rules", "A grammar with more than one parse tree for some sentences", "A grammar that only generates one parse tree", "A grammar used only for arithmetic expressions"},
            /*2*/    {"They can generate more than one parse tree for a sentence", "They are always preferable for language design", "They eliminate the need for parsing", "They only exist in natural language processing"},
            /*3*/    {"It simplifies language interpretation", "It can lead to multiple interpretations of a sentence", "It eliminates the need for parsing algorithms", "It makes grammars unusable"},
            /*4*/    {"id+id*id", "id→id+id", "id→id", "id-id"},
            /*5*/    {"It requires fewer production rules", "It leads to different interpretations of code", "It simplifies the parsing process", "It enhances compiler efficiency"},
            /*6*/    {"Natural languages", "Programming languages", "Formal languages", "None of the above"},
            /*7*/    {"Has only one derivation for each sentence", "Has multiple derivations for at least one sentence", "Does not produce any sentences", "Only generates natural language sentences"},
            /*8*/    {"Ambiguous grammar", "Context-free grammar", "Unambiguous grammar", "All grammars allow ambiguity"},
            /*9*/    {"Compilers and parsers", "Syntax analysis of programming languages", "Both a and b", "None of the above"},
            /*10*/   {"Ignoring it", "Rewriting the production rules", "Adding more symbols", "None of the above"},
            /*11*/   {"One unique derivation", "Faster parsing times", "Multiple valid parse trees", "No valid sentences being generated"},
            /*12*/   {"Removing recursion", "Using more non-terminal symbols", "Refining production rules", "Changing the grammar to a different language"},
            /*13*/   {"A sentence with a single noun and verb", "A complex arithmetic expression with multiple operators", "A sentence with only one possible interpretation", "None of the above"},
            /*14*/   {"It cannot be derived using the grammar", "It can be derived using only one production rule", "It can be derived using multiple parse trees", "It is not part of the language"},
            /*15*/   {"A feature that simplifies parsing", "The presence of multiple interpretations for a single sentence", "The use of only terminal symbols in a grammar", "A solution to eliminate all derivations"}
    };

    public static final int[] post_test_lesson_1_answers_medium = {
            /*1*/    1, // A grammar with more than one parse tree for some sentences
            /*2*/    0, // They can generate more than one parse tree for a sentence
            /*3*/    1, // It can lead to multiple interpretations of a sentence
            /*4*/    0, // id+id*id
            /*5*/    1, // It leads to different interpretations of code
            /*6*/    0, // Natural languages
            /*7*/    1, // Has multiple derivations for at least one sentence
            /*8*/    2, // Unambiguous grammar
            /*9*/    2, // Both a and b
            /*10*/   1, // Rewriting the production rules
            /*11*/   2, // Multiple valid parse trees
            /*12*/   2, // Refining production rules
            /*13*/   1, // A complex arithmetic expression with multiple operators
            /*14*/   2, // It can be derived using multiple parse trees
            /*15*/   1  // The presence of multiple interpretations for a single sentence
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
            /*1*/    "The type of grammar where a sentence can have multiple parse trees.",
            /*2*/    "The structure used to show the syntactic structure of a sentence derived from a grammar.",
            /*3*/    "The process of transforming a string of symbols into a parse tree.",
            /*4*/    "The property of a grammar that guarantees one unique parse tree for each sentence.",
            /*5*/    "A problem in grammars where the same sentence can be parsed in different ways.",
            /*6*/    "The formal language used to define the syntax of programming languages.",
            /*7*/    "A grammar that produces multiple derivations for at least one sentence.",
            /*8*/    "The field of study that involves analyzing the structure of sentences in programming and natural languages.",
            /*9*/    "A grammar used to generate sentences in a language while maintaining a hierarchical structure.",
            /*10*/   "A formal structure that represents a derivation of a sentence using production rules.",
            /*11*/   "A syntactic construct that can cause different interpretations in both programming and natural languages.",
            /*12*/   "The process of simplifying a grammar to eliminate ambiguity.",
            /*13*/   "The tree representation used to disambiguate sentences in CFGs.",
            /*14*/   "The type of ambiguity that occurs in arithmetic expressions such as operator precedence.",
            /*15*/   "The grammar used to describe recursive language patterns like nested expressions."
    };

    public static final String[] post_test_lesson_1_answers_hard = {
            /*1*/    "ambiguous grammar",
            /*2*/    "parse tree",
            /*3*/    "parsing",
            /*4*/    "unambiguous grammar",
            /*5*/    "ambiguity",
            /*6*/    "context-free grammar",
            /*7*/    "ambiguous grammar",
            /*8*/    "syntax analysis",
            /*9*/    "context-free grammar",
            /*10*/   "parse tree",
            /*11*/   "ambiguity",
            /*12*/   "grammar refinement",
            /*13*/   "parse tree",
            /*14*/   "structural ambiguity",
            /*15*/   "context-free grammar"
    };
}