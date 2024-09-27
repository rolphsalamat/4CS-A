package com.example.autotutoria20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class e_Module_5_1 {

    /*
    +----------------------+
    |       PRE-TEST       |
    |        LESSON 1      |
    +----------------------+
    */

    public static e_Question[] get_PreTest_Lesson1_Questions() {
        return e_Module_5.getPreTestQuestions(
                pre_test_lesson_1_questions,
                pre_test_lesson_1_choices,
                pre_test_lesson_1_answers
        );
    }

    public static final String[] pre_test_lesson_1_questions = {
            /*1*/    "Which of the following components of CFG is responsible for representing the structural rules of the language?",
            /*2*/    "What does the term 'start symbol' signify in a context-free grammar?",
            /*3*/    "In CFG, how is the derivation process typically initiated?",
            /*4*/    "Which component in CFG holds the basic symbols that cannot be broken down further?",
            /*5*/    "What type of symbols in a CFG are used to define syntactical patterns that can be expanded into terminals and other non-terminals?",
            /*6*/    "What is the role of production rules in a context-free grammar?",
            /*7*/    "In a CFG, which of the following is a terminal in arithmetic expressions?",
            /*8*/    "Which part of a production rule specifies what will be replaced?",
            /*9*/    "How are complex expressions built in a CFG?",
            /*10*/   "What is the significance of the start symbol in CFG derivation?",
            /*11*/   "Which symbol in a CFG can be described as non-divisible and represents an atomic unit of the language?",
            /*12*/   "Which of the following correctly describes a production rule format?",
            /*13*/   "In CFG, how are non-terminals transformed during string derivation?",
            /*14*/   "Which of the following statements about terminals is true in a CFG for arithmetic expressions?",
            /*15*/   "What is the purpose of the production rule S → ε in a CFG?"
    };

    public static final String[][] pre_test_lesson_1_choices = {
            /*1*/    {"Terminals", "Start Symbol", "Non-terminals", "Production Rules"},
            /*2*/    {"It marks the end of the derivation process.", "It is the first non-terminal used to begin generating strings.", "It defines the transformation rules for terminals.", "It represents the 'alphabet' of the language."},
            /*3*/    {"By using production rules to replace terminals", "By expanding the start symbol into a sequence of terminals and non-terminals", "By defining non-terminals", "By creating terminals from non-terminals"},
            /*4*/    {"Production rules", "Non-terminals", "Start symbol", "Terminals"},
            /*5*/    {"Non-terminals", "Terminals", "Production Rules", "Start Symbol"},
            /*6*/    {"To define the 'alphabet' of the language", "To specify the transformations from non-terminals to terminals", "To establish the hierarchy of terminals", "To mark the beginning of string derivation"},
            /*7*/    {"Expression (Expr)", "Term", "+", "Factor"},
            /*8*/    {"The terminal", "The right-hand side", "The non-terminal on the left-hand side", "The start symbol"},
            /*9*/    {"By combining terminals and non-terminals through production rules", "By generating strings directly from the start symbol", "By using only terminals", "By defining special symbols"},
            /*10*/   {"It defines the structure of a non-terminal", "It is the point from which the grammar begins generating strings", "It represents a sequence of terminals", "It terminates the generation process"},
            /*11*/   {"Start Symbol", "Non-terminal", "Terminal", "Production Rule"},
            /*12*/   {"A → α", "α → A", "A → T", "S → α + β"},
            /*13*/   {"They are transformed into terminals only", "They are expanded according to the production rules", "They represent the final output of the derivation process", "They are used to halt the generation process"},
            /*14*/   {"Terminals are placeholders for other terminals and non-terminals.", "Terminals include operators like +, -, *, and /.", "Terminals define the structure of the expression.", "Terminals are always enclosed in parentheses."},
            /*15*/   {"It defines an empty string", "It starts the derivation process", "It specifies a string of terminals and non-terminals", "It terminates the derivation process"}
    };

    public static final int[] pre_test_lesson_1_answers = {
            /*1*/    3,  // 'Production Rules'
            /*2*/    1,  // 'It is the first non-terminal used to begin generating strings.'
            /*3*/    1,  // 'By expanding the start symbol into a sequence of terminals and non-terminals'
            /*4*/    3,  // 'Terminals'
            /*5*/    0,  // 'Non-terminals'
            /*6*/    1,  // 'To specify the transformations from non-terminals to terminals'
            /*7*/    2,  // '+'
            /*8*/    2,  // 'The non-terminal on the left-hand side'
            /*9*/    0,  // 'By combining terminals and non-terminals through production rules'
            /*10*/   1,  // 'It is the point from which the grammar begins generating strings'
            /*11*/   2,  // 'Terminal'
            /*12*/   0,  // 'A → α'
            /*13*/   1,  // 'They are expanded according to the production rules'
            /*14*/   1,  // 'Terminals include operators like +, -, *, and /.'
            /*15*/   0   // 'It defines an empty string'
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |        EASY          |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson1_Easy_Questions() {
        return e_Module_5.get_PostTest_EasyMedium_Questions(
                post_test_lesson_1_questions_easy,
                post_test_lesson_1_choices_easy,
                post_test_lesson_1_answers_easy,
                e_Question.Difficulty.EASY
        );
    }

    public static final String[] post_test_lesson_1_questions_easy = {
            /*1*/    "A parse tree visually represents the hierarchical structure of a given sentence according to a set of grammar rules.",
            /*2*/    "Each leaf node in a parse tree represents a terminal symbol from the grammar.",
            /*3*/    "A parse tree can have more than one root node.",
            /*4*/    "Non-terminal symbols appear as the internal nodes of a parse tree.",
            /*5*/    "Parse trees can represent both ambiguous and unambiguous grammar.",
            /*6*/    "All parse trees for a particular string represent exactly the same derivation.",
            /*7*/    "A parse tree is generated from a grammar that must be context-free.",
            /*8*/    "The leftmost derivation in a parse tree means constructing the tree from the right side of the production rules.",
            /*9*/    "The root node of a parse tree is always a terminal symbol.",
            /*10*/   "In ambiguous grammars, there can be more than one valid parse tree for a given string.",
            /*11*/   "Parse trees are essential for evaluating the semantics of a language in compilers.",
            /*12*/   "Rightmost derivation in a parse tree starts from the right side of the production rule.",
            /*13*/   "Terminal nodes in a parse tree can be further broken down into non-terminal nodes.",
            /*14*/   "Parse trees are applicable only in natural language processing.",
            /*15*/   "All context-free grammars can be represented using parse trees."
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
            /*1*/    0,  // True
            /*2*/    0,  // True
            /*3*/    1,  // False
            /*4*/    0,  // True
            /*5*/    0,  // True
            /*6*/    1,  // False
            /*7*/    0,  // True
            /*8*/    1,  // False
            /*9*/    1,  // False
            /*10*/   0,  // True
            /*11*/   0,  // True
            /*12*/   0,  // True
            /*13*/   1,  // False
            /*14*/   1,  // False
            /*15*/   0   // True
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |       MEDIUM         |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson1_Medium_Questions() {
        return e_Module_5.get_PostTest_EasyMedium_Questions(
                post_test_lesson_1_questions_medium,
                post_test_lesson_1_choices_medium,
                post_test_lesson_1_answers_medium,
                e_Question.Difficulty.MEDIUM
        );
    }

    public static final String[] post_test_lesson_1_questions_medium = {
            /*1*/    "What is the root node of a parse tree typically associated with?",
            /*2*/    "Which part of the parse tree represents the terminal symbols?",
            /*3*/    "What does a parse tree mainly represent in the context of formal grammar?",
            /*4*/    "Which of the following best describes an ambiguous grammar?",
            /*5*/    "In the context of compilers, what is the main use of a parse tree?",
            /*6*/    "What is another name for a parse tree?",
            /*7*/    "Which of the following is NOT a component of a parse tree?",
            /*8*/    "Which derivation does a leftmost derivation in a parse tree follow?",
            /*9*/    "What defines the internal structure of a parse tree?",
            /*10*/   "Which symbol is typically found at the leaves of a parse tree?",
            /*11*/   "What is the main difference between a syntax tree and a parse tree?",
            /*12*/   "What kind of grammar is required to create a parse tree?",
            /*13*/   "Which derivation is used in a parse tree when constructing from the rightmost non-terminal?",
            /*14*/   "What happens when a grammar is ambiguous?",
            /*15*/   "What does a node in the parse tree represent?"
    };

    public static final String[][] post_test_lesson_1_choices_medium = {
            /*1*/    {"Terminal symbol", "Start symbol", "Non-terminal symbol", "Leaf node"},
            /*2*/    {"Leaf nodes", "Root nodes", "Internal nodes", "Branches"},
            /*3*/    {"A sequence of non-terminal symbols", "The hierarchical structure of a string", "The final string output", "The syntax error locations"},
            /*4*/    {"It has multiple valid parse trees for a single string", "It has a single unique parse tree for each string", "It cannot be used for parsing", "It always generates invalid strings"},
            /*5*/    {"Code optimization", "Syntax checking", "Lexical analysis", "Parsing ambiguous grammar"},
            /*6*/    {"Syntax tree", "Derivation tree", "Grammar tree", "Rule tree"},
            /*7*/    {"Non-terminal nodes", "Leaf nodes", "Edges", "Tokens"},
            /*8*/    {"It starts with the rightmost non-terminal", "It expands the leftmost non-terminal first", "It expands the rightmost terminal", "It always expands the root first"},
            /*9*/    {"Non-terminal symbols", "Terminal symbols", "Both terminal and non-terminal symbols", "Root nodes"},
            /*10*/   {"Non-terminal", "Root", "Terminal", "Production rule"},
            /*11*/   {"A syntax tree ignores certain grammatical rules", "A parse tree contains all production rules", "A syntax tree represents lexical analysis", "A parse tree skips certain non-terminals"},
            /*12*/   {"Context-sensitive grammar", "Regular grammar", "Context-free grammar", "Ambiguous grammar"},
            /*13*/   {"Top-down derivation", "Leftmost derivation", "Bottom-up derivation", "Rightmost derivation"},
            /*14*/   {"It always leads to a syntax error", "It produces multiple parse trees for the same input", "It simplifies the derivation process", "It eliminates terminal symbols"},
            /*15*/   {"A production rule", "A grammar token", "A syntax error", "A terminal or non-terminal symbol"}
    };

    public static final int[] post_test_lesson_1_answers_medium = {
            /*1*/    1,  // Start symbol
            /*2*/    0,  // Leaf nodes
            /*3*/    1,  // The hierarchical structure of a string
            /*4*/    0,  // It has multiple valid parse trees for a single string
            /*5*/    1,  // Syntax checking
            /*6*/    1,  // Derivation tree
            /*7*/    3,  // Tokens
            /*8*/    1,  // It expands the leftmost non-terminal first
            /*9*/    0,  // Non-terminal symbols
            /*10*/   2,  // Terminal
            /*11*/   1,  // A parse tree contains all production rules
            /*12*/   2,  // Context-free grammar
            /*13*/   3,  // Rightmost derivation
            /*14*/   1,  // It produces multiple parse trees for the same input
            /*15*/   3   // A terminal or non-terminal symbol
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |        HARD          |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson1_Hard_Questions() {
        return e_Module_5.get_PostTest_Hard_Questions(
                post_test_lesson_1_questions_hard,
                post_test_lesson_1_answers_hard,
                e_Question.Difficulty.HARD
        );
    }

    public static final String[] post_test_lesson_1_questions_hard = {
            /*1*/    "What is the name of the tree structure used to represent the syntactic structure of a string according to a given grammar?",
            /*2*/    "What is the term for a tree that represents multiple derivations for a single input string?",
            /*3*/    "What are the terminal symbols in a parse tree represented by?",
            /*4*/    "Which kind of derivation expands the leftmost non-terminal first?",
            /*5*/    "What is another term for a parse tree used in formal language theory?",
            /*6*/    "What is the main application of parse trees in compilers?",
            /*7*/    "What type of symbols do the internal nodes in a parse tree represent?",
            /*8*/    "What is produced when a parse tree follows rightmost derivation?",
            /*9*/    "What does an ambiguous grammar lead to in terms of parse trees?",
            /*10*/   "What is the process of applying grammar rules to derive a string called?",
            /*11*/   "What type of tree results when every derivation rule in a parse tree corresponds to a unique derivation?",
            /*12*/   "What do we call the nodes in a parse tree that represent non-terminal symbols?",
            /*13*/   "What is the term used when the parse tree is derived by starting with the start symbol and applying production rules?",
            /*14*/   "What is the structural term for the 'branches' connecting nodes in a parse tree?",
            /*15*/   "Which specific node in a parse tree corresponds to the rule starting point of the grammar?"
    };

    public static final String[] post_test_lesson_1_answers_hard = {
            /*1*/    "parse tree",
            /*2*/    "ambiguous parse tree",
            /*3*/    "leaf nodes",
            /*4*/    "leftmost derivation",
            /*5*/    "derivation tree",
            /*6*/    "syntax checking",
            /*7*/    "non-terminal symbols",
            /*8*/    "rightmost derivation",
            /*9*/    "multiple parse trees",
            /*10*/   "derivation",
            /*11*/   "unambiguous parse tree",
            /*12*/   "internal nodes",
            /*13*/   "top-down derivation",
            /*14*/   "edge",
            /*15*/   "root node"
    };

}