package com.example.autotutoria20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class e_Module_5_2 {

    /*
    +----------------------+
    |       PRE-TEST       |
    |        LESSON 2      |
    +----------------------+
    */

    public static e_Question[] get_PreTest_Lesson2_Questions() {
        return e_Module_5.getPreTestQuestions(
                pre_test_lesson2_questions,
                pre_test_lesson2_choices,
                pre_test_lesson2_answers
        );
    }

    public static final String[] pre_test_lesson2_questions = {
            /*1*/    "Which of the following is true about a parse tree?",
            /*2*/    "What do the leaves of a parse tree represent?",
            /*3*/    "The root of a parse tree is typically associated with which element?",
            /*4*/    "Which of the following is NOT true about parse trees?",
            /*5*/    "In a parse tree, which component is associated with non-terminal symbols?",
            /*6*/    "What is the purpose of constructing a parse tree?",
            /*7*/    "Which of the following best describes an ambiguous grammar?",
            /*8*/    "Which of the following statements is true about terminal symbols in a parse tree?",
            /*9*/    "What is the main difference between a parse tree and an abstract syntax tree (AST)?",
            /*10*/   "Which of the following is an essential step in constructing a parse tree?",
            /*11*/   "Which of the following is true about ambiguous grammars?",
            /*12*/   "In a parse tree, the process of applying production rules starts from:",
            /*13*/   "Which type of grammar typically constructs parse trees?",
            /*14*/   "Which of the following is NOT an element found in a parse tree?",
            /*15*/   "Which structure is used to represent the hierarchical structure of a language's syntax?"
    };

    public static final String[][] pre_test_lesson2_choices = {
            /*1*/     {"The root represents the terminal symbol", "The leaves represent the non-terminal symbols", "The internal nodes represent the production rules", "The leaves represent the start symbol"},
            /*2*/     {"Non-terminal symbols", "The input string", "Production rules", "Terminal symbols"},
            /*3*/     {"A terminal symbol", "The start symbol of the grammar", "A non-terminal symbol", "A production rule"},
            /*4*/     {"They represent the syntactic structure of a string", "They can have more than one valid form for ambiguous grammars", "They are only used in lexical analysis", "The leaves represent terminal symbols"},
            /*5*/     {"Internal nodes", "Leaf nodes", "Terminal symbols", "Edges"},
            /*6*/     {"To break down a sentence into words", "To represent the syntactic structure of a string based on grammar", "To generate lexical tokens", "To find ambiguities in the input string"},
            /*7*/     {"A grammar that cannot generate a valid parse tree", "A grammar with multiple start symbols", "A grammar that generates more than one valid parse tree for a single string", "A grammar with no terminal symbols"},
            /*8*/     {"They appear at the root of the tree", "They are located at the leaves of the tree", "They correspond to the non-terminal symbols of the grammar", "They represent the production rules"},
            /*9*/     {"An AST represents all the production rules while a parse tree does not", "A parse tree shows all the syntactic details while an AST abstracts unnecessary details", "A parse tree is used in lexical analysis and an AST is used in syntax analysis", "An AST is more commonly used in natural language processing"},
            /*10*/     {"Identifying the lexical tokens of the input string", "Starting from the terminal symbols and moving upward", "Applying production rules starting from the root node", "Labeling each node with a numerical value"},
            /*11*/    {"They can result in multiple valid parse trees for the same string", "They always produce only one parse tree", "They cannot be used to generate any valid parse tree", "They are used exclusively in machine learning"},
            /*12*/    {"The leaves and moves upwards", "The root and moves downwards", "The leftmost terminal symbol", "The rightmost non-terminal symbol"},
            /*13*/    {"Regular grammar", "Context-free grammar", "Context-sensitive grammar", "Unrestricted grammar"},
            /*14*/    {"Terminal symbols", "Production rules", "Non-terminal symbols", "Ambiguous rules"},
            /*15*/    {"Finite State Automaton", "Lexical Analyzer", "Parse Tree", "Transition Table"}
    };

    public static final int[] pre_test_lesson2_answers = {
            /*1*/     2, // The internal nodes represent the production rules
            /*2*/     3, // Terminal symbols
            /*3*/     1, // The start symbol of the grammar
            /*4*/     2, // They are only used in lexical analysis
            /*5*/     0, // Internal nodes
            /*6*/     1, // To represent the syntactic structure of a string based on grammar
            /*7*/     2, // A grammar that generates more than one valid parse tree for a single string
            /*8*/     1, // They are located at the leaves of the tree
            /*9*/     1, // A parse tree shows all the syntactic details while an AST abstracts unnecessary details
            /*10*/     2, // Applying production rules starting from the root node
            /*11*/    0, // They can result in multiple valid parse trees for the same string
            /*12*/    1, // The root and moves downwards
            /*13*/    1, // Context-free grammar
            /*14*/    3, // Ambiguous rules
            /*15*/    2  // Parse Tree
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |        EASY          |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson2_Easy_Questions() {
        return e_Module_5.get_PostTest_EasyMedium_Questions(
                post_test_lesson2_questions_easy,
                post_test_lesson2_choices_easy,
                post_test_lesson2_answers_easy,
                e_Question.Difficulty.EASY
        );
    }

    public static final String[] post_test_lesson2_questions_easy = {
            /*1*/    "A derivation is a sequence of production rule applications that generate a string.",
            /*2*/    "A parse tree visually represents the hierarchical structure of a derivation.",
            /*3*/    "In a parse tree the root node always represents the start symbol of the grammar.",
            /*4*/    "A leftmost derivation replaces the leftmost non-terminal at each step of the derivation process.",
            /*5*/    "A rightmost derivation replaces the rightmost non-terminal at each step of the derivation process.",
            /*6*/    "Every valid string in a language generated by a grammar can be represented by exactly one parse tree.",
            /*7*/    "A parse tree can only be constructed using a leftmost derivation.",
            /*8*/    "Parse trees can be used to check if a string belongs to the language defined by a grammar.",
            /*9*/    "Both leftmost and rightmost derivations can generate the same parse tree for a string.",
            /*10*/   "The internal nodes of a parse tree always represent terminal symbols.",
            /*11*/   "In a derivation the non-terminal symbols are replaced by terminal symbols according to the production rules of the grammar.",
            /*12*/   "A parse tree only represents the structure of a sentence not its meaning.",
            /*13*/   "The leaf nodes of a parse tree are always terminal symbols.",
            /*14*/   "A single derivation can correspond to multiple parse trees.",
            /*15*/   "A parse tree is a representation that only applies to lexical analysis."
    };

    public static final String[][] post_test_lesson2_choices_easy = {
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

    public static final int[] post_test_lesson2_answers_easy = {
            /*1*/    0, // True
            /*2*/    0, // True
            /*3*/    0, // True
            /*4*/    0, // True
            /*5*/    0, // True
            /*6*/    0, // True
            /*7*/    1, // False
            /*8*/    0, // True
            /*9*/    0, // True
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

    public static e_Question[] get_PostTest_Lesson2_Medium_Questions() {
        return e_Module_5.get_PostTest_EasyMedium_Questions(
                post_test_lesson2_questions_medium,
                post_test_lesson2_choices_medium,
                post_test_lesson2_answers_medium,
                e_Question.Difficulty.MEDIUM
        );
    }

    public static final String[] post_test_lesson2_questions_medium = {
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

    public static final String[][] post_test_lesson2_choices_medium = {
            /*1*/    {"A graphical representation of a string", "A step-by-step process to generate a string", "A set of rules for parsing a string", "A tree diagram"},
            /*2*/    {"A terminal symbol", "The start symbol of the grammar", "The end of a derivation", "The longest string in the language"},
            /*3*/    {"Rightmost derivation", "Mixed derivation", "Leftmost derivation", "Bottom-up derivation"},
            /*4*/    {"To show the sequence of tokenization", "To visually represent the structure of a derivation", "To define the semantics of a string", "To describe the lexical analysis phase"},
            /*5*/    {"Top-down derivation", "Leftmost derivation", "Pre-order derivation", "Rightmost derivation"},
            /*6*/    {"Only one", "Exactly two", "More than one", "None"},
            /*7*/    {"Terminal symbols", "Non-terminal symbols", "Token types", "Derivation steps"},
            /*8*/    {"They always generate different parse trees", "They always generate the same parse tree", "They may generate the same parse tree", "Only the leftmost derivation can generate a parse tree"},
            /*9*/    {"Terminal symbols", "Non-terminal symbols", "Production rules", "Start symbols"},
            /*10*/    {"Parse tree", "Derivation graph", "Syntax chart", "Finite automaton"},
            /*11*/    {"The order in which terminal symbols are replaced", "The position of non-terminal symbols being replaced", "The complexity of the parse tree", "The number of production rules used"},
            /*12*/    {"Other non-terminals", "Terminals according to production rules", "Keywords of the language", "Lexical tokens"},
            /*13*/    {"It results in more than one possible parse tree", "It makes parse trees invalid", "It restricts the number of parse trees to one", "It prevents the creation of parse trees"},
            /*14*/    {"Semantic analysis", "Lexical analysis", "Syntax analysis", "Optimization"},
            /*15*/    {"Non-terminal symbols", "Production rules", "Terminal symbols", "The start symbol"}
    };

    public static final int[] post_test_lesson2_answers_medium = {
            /*1*/    1, // A step-by-step process to generate a string
            /*2*/    1, // The start symbol of the grammar
            /*3*/    2, // Leftmost derivation
            /*4*/    1, // To visually represent the structure of a derivation
            /*5*/    3, // Rightmost derivation
            /*6*/    2, // More than one
            /*7*/    1, // Non-terminal symbols
            /*8*/    2, // They may generate the same parse tree
            /*9*/    0, // Terminal symbols
            /*10*/    0, // Parse tree
            /*11*/    1, // The position of non-terminal symbols being replaced
            /*12*/    1, // Terminals according to production rules
            /*13*/    0, // It results in more than one possible parse tree
            /*14*/    2, // Syntax analysis
            /*15*/    2  // Terminal symbols
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |        HARD          |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson2_Hard_Questions() {
        return e_Module_5.get_PostTest_Hard_Questions(
                post_test_lesson2_questions_hard,
                post_test_lesson2_answers_hard,
                e_Question.Difficulty.HARD
        );
    }

    public static final String[] post_test_lesson2_questions_hard = {
            /*1*/    "Identify the structure that represents the syntactic structure of a string based on a given grammar.",
            /*2*/    "What is the process called where production rules are applied to generate a string in a formal grammar?",
            /*3*/    "What is the starting symbol of a grammar represented in the root node of a parse tree?",
            /*4*/    "What do you call a derivation that replaces the leftmost non-terminal first?",
            /*5*/    "What type of derivation replaces the rightmost non-terminal at each step?",
            /*6*/    "What type of symbols are represented by internal nodes in a parse tree?",
            /*7*/    "What symbols are represented by the leaf nodes in a parse tree?",
            /*8*/    "What is a rule that defines how non-terminal symbols can be replaced by other symbols in a grammar?",
            /*9*/    "Identify the phenomenon where a grammar allows more than one parse tree for the same string.",
            /*10*/   "What is the process called when a string is checked to determine if it belongs to a language defined by a grammar?",
            /*11*/   "What is another term for a parse tree that represents the structure of a sentence based on syntax?",
            /*12*/   "In a leftmost derivation which side of the string contains the non-terminal that is replaced first?",
            /*13*/   "What term describes a grammar that can generate multiple parse trees for the same string?",
            /*14*/   "In which phase of a compiler is a parse tree typically constructed?",
            /*15*/   "Identify the structure that connects production rules with non-terminal and terminal symbols to illustrate a string's formation."
    };

    public static final String[] post_test_lesson2_answers_hard = {
            /*1*/    "parse tree",
            /*2*/    "derivation",
            /*3*/    "start symbol",
            /*4*/    "leftmost derivation",
            /*5*/    "rightmost derivation",
            /*6*/    "non-terminal symbols",
            /*7*/    "terminal symbols",
            /*8*/    "production rule",
            /*9*/    "ambiguity",
            /*10*/    "parsing",
            /*11*/    "syntax tree",
            /*12*/    "left",
            /*13*/    "ambiguous grammar",
            /*14*/    "syntax analysis",
            /*15*/    "parse tree"
    };
}