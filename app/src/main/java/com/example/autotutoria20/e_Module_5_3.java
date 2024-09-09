package com.example.autotutoria20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class e_Module_5_3 {

    /*
    +----------------------+
    |       PRE-TEST       |
    |        LESSON 3      |
    +----------------------+
    */

    public static e_Question[] get_PreTest_Lesson3_Questions() {
        return e_Module_5.getPreTestQuestions(
                pre_test_lesson3_questions,
                pre_test_lesson3_choices,
                pre_test_lesson3_answers
        );
    }

    public static final String[] pre_test_lesson3_questions = {
            /*1*/    "What does a derivation represent in the context of formal grammars?",
            /*2*/    "What is the root node of a parse tree always associated with?",
            /*3*/    "Which derivation process involves replacing the leftmost non-terminal first?",
            /*4*/    "What is the purpose of a parse tree?",
            /*5*/    "In which type of derivation are the rightmost non-terminals replaced first?",
            /*6*/    "How many parse trees can represent a valid string in a grammar with ambiguous rules?",
            /*7*/    "What do the internal nodes of a parse tree represent?",
            /*8*/    "Which statement is true about a leftmost and rightmost derivation for the same string?",
            /*9*/    "What do the leaf nodes of a parse tree represent?",
            /*10*/   "Which term refers to the structure that can be used to determine whether a string belongs to a language?",
            /*11*/   "What is the primary difference between a leftmost derivation and a rightmost derivation?",
            /*12*/   "In a derivation what are non-terminal symbols replaced with?",
            /*13*/   "How does ambiguity in a grammar affect the parse trees?",
            /*14*/   "In which phase of language processing is a parse tree typically constructed?",
            /*15*/   "Which part of a grammar corresponds to the leaf nodes of a parse tree?"
    };

    public static final String[][] pre_test_lesson3_choices = {
            /*1*/    {"A graphical representation of a string", "A step-by-step process to generate a string", "A set of rules for parsing a string", "A tree diagram"},
            /*2*/    {"A terminal symbol", "The start symbol of the grammar", "The end of a derivation", "The longest string in the language"},
            /*3*/    {"Rightmost derivation", "Mixed derivation", "Leftmost derivation", "Bottom-up derivation"},
            /*4*/    {"To show the sequence of tokenization", "To visually represent the structure of a derivation", "To define the semantics of a string", "To describe the lexical analysis phase"},
            /*5*/    {"Top-down derivation", "Leftmost derivation", "Pre-order derivation", "Rightmost derivation"},
            /*6*/    {"Only one", "Exactly two", "More than one", "None"},
            /*7*/    {"Terminal symbols", "Non-terminal symbols", "Token types", "Derivation steps"},
            /*8*/    {"They always generate different parse trees", "They always generate the same parse tree", "They may generate the same parse tree", "Only the leftmost derivation can generate a parse tree"},
            /*9*/    {"Terminal symbols", "Non-terminal symbols", "Production rules", "Start symbols"},
            /*10*/   {"Parse tree", "Derivation graph", "Syntax chart", "Finite automaton"},
            /*11*/   {"The order in which terminal symbols are replaced", "The position of non-terminal symbols being replaced", "The complexity of the parse tree", "The number of production rules used"},
            /*12*/   {"Other non-terminals", "Terminals according to production rules", "Keywords of the language", "Lexical tokens"},
            /*13*/   {"It results in more than one possible parse tree", "It makes parse trees invalid", "It restricts the number of parse trees to one", "It prevents the creation of parse trees"},
            /*14*/   {"Semantic analysis", "Lexical analysis", "Syntax analysis", "Optimization"},
            /*15*/   {"Non-terminal symbols", "Production rules", "Terminal symbols", "The start symbol"}
    };

    public static final int[] pre_test_lesson3_answers = {
            /*1*/    1, // A step-by-step process to generate a string
            /*2*/    1, // The start symbol of the grammar
            /*3*/    2, // Leftmost derivation
            /*4*/    1, // To visually represent the structure of a derivation
            /*5*/    3, // Rightmost derivation
            /*6*/    2, // More than one
            /*7*/    1, // Non-terminal symbols
            /*8*/    2, // They may generate the same parse tree
            /*9*/    0, // Terminal symbols
            /*10*/   0, // Parse tree
            /*11*/   1, // The position of non-terminal symbols being replaced
            /*12*/   1, // Terminals according to production rules
            /*13*/   0, // It results in more than one possible parse tree
            /*14*/   2, // Syntax analysis
            /*15*/   2  // Terminal symbols
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |        EASY          |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson3_Easy_Questions() {
        return e_Module_5.get_PostTest_EasyMedium_Questions(
                post_test_lesson3_questions_easy,
                post_test_lesson3_choices_easy,
                post_test_lesson3_answers_easy,
                e_Question.Difficulty.EASY
        );
    }

    public static final String[] post_test_lesson3_questions_easy = {
            /*1*/    "In a leftmost derivation the leftmost non-terminal is expanded first.",
            /*2*/    "A rightmost derivation always expands the leftmost non-terminal first.",
            /*3*/    "Both leftmost and rightmost derivations can generate the same final string.",
            /*4*/    "A derivation sequence can only be either leftmost or rightmost but not both.",
            /*5*/    "Leftmost derivations always result in a different final string compared to rightmost derivations.",
            /*6*/    "In syntax analysis leftmost and rightmost derivations are used to process non-terminal symbols in different ways.",
            /*7*/    "Derivations are a key component of generating strings in formal grammars.",
            /*8*/    "In a rightmost derivation the rightmost non-terminal is expanded first.",
            /*9*/    "The starting point for both leftmost and rightmost derivations is always a terminal symbol.",
            /*10*/   "The process of derivation can be performed without following any specific order of non-terminal expansion.",
            /*11*/   "Both leftmost and rightmost derivations follow a set of grammar rules for expansion.",
            /*12*/   "Rightmost derivations are primarily used in bottom-up parsing techniques.",
            /*13*/   "Leftmost derivations are typically used in top-down parsing techniques.",
            /*14*/   "Derivations in formal grammar are necessary to analyze and generate syntactic structures of programming languages.",
            /*15*/   "The choice between leftmost and rightmost derivation affects the final output of the parsing process."
    };

    public static final String[][] post_test_lesson3_choices_easy = {
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

    public static final int[] post_test_lesson3_answers_easy = {
            /*1*/    0, // True
            /*2*/    1, // False
            /*3*/    0, // True
            /*4*/    1, // False
            /*5*/    1, // False
            /*6*/    0, // True
            /*7*/    0, // True
            /*8*/    0, // True
            /*9*/    1, // False
            /*10*/   1, // False
            /*11*/   0, // True
            /*12*/   0, // True
            /*13*/   0, // True
            /*14*/   0, // True
            /*15*/   1  // False
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |       MEDIUM         |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson3_Medium_Questions() {
        return e_Module_5.get_PostTest_EasyMedium_Questions(
                post_test_lesson3_questions_medium,
                post_test_lesson3_choices_medium,
                post_test_lesson3_answers_medium,
                e_Question.Difficulty.MEDIUM
        );
    }

    public static final String[] post_test_lesson3_questions_medium = {
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

    public static final String[][] post_test_lesson3_choices_medium = {
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
            /*15*/   {"Derivations cannot use the same rules for both leftmost and rightmost expansions.", "Derivations are used to generate valid strings based on grammar rules.", "Only rightmost derivations are used in syntax analysis.", "Derivations ignore non-terminal symbols."}
    };

    public static final int[] post_test_lesson3_answers_medium = {
            /*1*/    1, // Expands the leftmost non-terminal first
            /*2*/    1, // The rightmost non-terminal
            /*3*/    2, // They both follow the same grammar rules to generate strings
            /*4*/    1, // Top-down parsing
            /*5*/    2, // Bottom-up parsing
            /*6*/    1, // A specific letter or digit in a string
            /*7*/    2, // The leftmost non-terminal
            /*8*/    0, // The same final string
            /*9*/    2, // It expands the rightmost non-terminal at each step
            /*10*/   1, // The first non-terminal symbol from which derivation starts
            /*11*/   1, // The leftmost or rightmost non-terminal symbol
            /*12*/   1, // Syntactic structure of a language
            /*13*/   2, // The order in which non-terminal symbols are expanded
            /*14*/   1, // An uppercase letter
            /*15*/   1  // Derivations are used to generate valid strings based on grammar rules.
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |        HARD          |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson3_Hard_Questions() {
        return e_Module_5.get_PostTest_Hard_Questions(
                post_test_lesson3_questions_hard,
                post_test_lesson3_answers_hard,
                e_Question.Difficulty.HARD
        );
    }

    public static final String[] post_test_lesson3_questions_hard = {
            /*1*/    "Identify the type of derivation where the leftmost non-terminal is always expanded first.",
            /*2*/    "What is the term for the process of expanding the rightmost non-terminal in a grammar rule first?",
            /*3*/    "What symbol do you typically begin with in both leftmost and rightmost derivations?",
            /*4*/    "Which derivation is commonly associated with top-down parsing?",
            /*5*/    "Which derivation is often used in bottom-up parsing?",
            /*6*/    "What is the term for symbols that cannot be expanded further in a derivation?",
            /*7*/    "What kind of symbol such as 'A' or 'B' can be expanded into other symbols in a derivation?",
            /*8*/    "Identify the type of parsing that generally uses rightmost derivations in reverse order.",
            /*9*/    "What is the final result called in a derivation sequence regardless of whether it's leftmost or rightmost?",
            /*10*/   "What process describes breaking down a start symbol into a string of terminal symbols using grammar rules?",
            /*11*/   "Which type of derivation expands non-terminal symbols from left to right?",
            /*12*/   "Identify the type of derivation where the non-terminal symbols are expanded from right to left.",
            /*13*/   "What is the formal grammar concept that includes rules for generating strings using symbols like terminal and non-terminal?",
            /*14*/   "Which derivation method ensures that the leftmost unresolved non-terminal is always expanded next?",
            /*15*/   "What is the term for the rules used to expand non-terminal symbols in both leftmost and rightmost derivations?"
    };

    public static final String[] post_test_lesson3_answers_hard = {
            /*1*/    "leftmost derivation",
            /*2*/    "rightmost derivation",
            /*3*/    "start symbol",
            /*4*/    "leftmost derivation",
            /*5*/    "rightmost derivation",
            /*6*/    "terminal symbols",
            /*7*/    "non-terminal symbol",
            /*8*/    "bottom-up parsing",
            /*9*/    "final string",
            /*10*/   "derivation",
            /*11*/   "leftmost derivation",
            /*12*/   "rightmost derivation",
            /*13*/   "production rules",
            /*14*/   "leftmost derivation",
            /*15*/   "grammar rules"
    };
}