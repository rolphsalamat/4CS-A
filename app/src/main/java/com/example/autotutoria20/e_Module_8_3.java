package com.example.autotutoria20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class e_Module_8_3 {

    /*
    +----------------------+
    |       PRE-TEST       |
    |       LESSON 8-3     |
    +----------------------+
    */

    public static e_Question[] get_PreTest_Lesson3_Questions() {
        return e_Module_8.getPreTestQuestions(
                pre_test_lesson_8_3_questions,
                pre_test_lesson_8_3_choices,
                pre_test_lesson_8_3_answers
        );
    }

    public static final String[] pre_test_lesson_8_3_questions = {
            /*1*/    "What is the main purpose of bottom-up parsing?",
            /*2*/    "Which of the following is the most general form of LR parsing?",
            /*3*/    "In shift-reduce parsing, what does 'shift' mean?",
            /*4*/    "Which of the following is not a type of LR parser?",
            /*5*/    "The parsing table in LR parsing decides actions like shift, reduce, accept, or:",
            /*6*/    "In shift-reduce parsing, what is reduced to non-terminals?",
            /*7*/    "What does LR stand for in LR parsing?",
            /*8*/    "Which of the following is the simplest form of LR parsing?",
            /*9*/    "What does the stack in shift-reduce parsing hold?",
            /*10*/   "Which part of the parsing process does LR parsing handle more effectively than LL(1) parsing?",
            /*11*/   "What does ‘F’ typically represent in parsing grammars?",
            /*12*/   "Which of the following correctly defines LALR parsing?",
            /*13*/   "Which parsing technique uses both a stack and input buffer?",
            /*14*/   "In bottom-up parsing, the grammar production rule applied to reduce symbols is based on:",
            /*15*/   "Which of the following is a key characteristic of LR parsers?"
    };

    public static final String[][] pre_test_lesson_8_3_choices = {
            /*1*/    {"To construct the parse tree from the root", "To construct the parse tree from the leaves", "To predict the next input symbol", "To simplify the grammar"},
            /*2*/    {"SLR", "Canonical LR", "LALR", "Top-down"},
            /*3*/    {"Remove the symbol from the stack", "Push the input symbol onto the stack", "Apply a production rule", "Accept the input"},
            /*4*/    {"SLR", "LALR", "Canonical LR", "Predictive LR"},
            /*5*/    {"Insert", "Error", "Predict", "Parse"},
            /*6*/    {"Terminals", "Production rules", "Handles", "Tokens"},
            /*7*/    {"Left to right parsing of leftmost derivation", "Left to right parsing of rightmost derivation in reverse", "Right to left parsing of rightmost derivation", "Right to left parsing of leftmost derivation"},
            /*8*/    {"LALR", "Canonical LR", "SLR", "LL(1)"},
            /*9*/    {"Input buffer symbols", "Production rules", "Grammar symbols", "Terminals only"},
            /*10*/   {"Predicting terminal symbols", "Reducing non-terminals", "Handling a wider range of grammars", "Scanning input tokens"},
            /*11*/   {"Function", "Factor", "Fragment", "Formula"},
            /*12*/   {"Lookahead LR", "Left Action LR", "Less Ambiguous LR", "Logical Action LR"},
            /*13*/   {"LL(1)", "Shift-reduce parsing", "Predictive parsing", "Recursive descent parsing"},
            /*14*/   {"Leftmost derivation", "Rightmost derivation in reverse", "Context-free grammar simplification", "LL(1) predictive parsing"},
            /*15*/   {"They always produce a leftmost derivation", "They can handle ambiguous grammars", "They parse from the start symbol to terminals", "They use a parsing table for decision making"}
    };

    public static final int[] pre_test_lesson_8_3_answers = {
            /*1*/    1, // 'To construct the parse tree from the leaves'
            /*2*/    1, // 'Canonical LR'
            /*3*/    1, // 'Push the input symbol onto the stack'
            /*4*/    3, // 'Predictive LR'
            /*5*/    1, // 'Error'
            /*6*/    2, // 'Handles'
            /*7*/    1, // 'Left to right parsing of rightmost derivation in reverse'
            /*8*/    2, // 'SLR'
            /*9*/    2, // 'Grammar symbols'
            /*10*/   2, // 'Handling a wider range of grammars'
            /*11*/   1, // 'Factor'
            /*12*/   0, // 'Lookahead LR'
            /*13*/   1, // 'Shift-reduce parsing'
            /*14*/   1, // 'Rightmost derivation in reverse'
            /*15*/   3  // 'They use a parsing table for decision making'
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |          EASY        |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson3_Easy_Questions() {
        return e_Module_8.get_PostTest_EasyMedium_Questions(
                post_test_lesson_8_3_questions_easy,
                post_test_lesson_8_3_choices_easy,
                post_test_lesson_8_3_answers_easy,
                e_Question.Difficulty.EASY
        );
    }

    public static final String[] post_test_lesson_8_3_questions_easy = {
            /*1*/    "The CYK algorithm can be applied to any context-free grammar without modification.",
            /*2*/    "In Chomsky Normal Form, all productions must produce either two non-terminals or one terminal.",
            /*3*/    "The CYK algorithm is a bottom-up dynamic programming approach to parsing.",
            /*4*/    "The CYK algorithm uses a triangular table to store parsing information.",
            /*5*/    "In the CYK algorithm, the rows of the table represent the length of substrings.",
            /*6*/    "Chomsky Normal Form (CNF) allows productions that produce the empty string.",
            /*7*/    "The CYK algorithm is only applicable to regular grammars.",
            /*8*/    "The start symbol of a grammar must appear in the top-right cell of the CYK table for a string to be accepted.",
            /*9*/    "The CYK algorithm can handle context-free grammars directly without converting them to CNF.",
            /*10*/   "Every context-free grammar can be converted into Chomsky Normal Form.",
            /*11*/   "In the CYK algorithm, the terminal symbols are used to fill the table in step 3.",
            /*12*/   "The CYK algorithm is an example of a greedy algorithm.",
            /*13*/   "The CYK algorithm works by breaking down the input string into smaller and smaller substrings.",
            /*14*/   "For the CYK algorithm, the non-terminal symbols must generate exactly two non-terminals in CNF.",
            /*15*/   "The CYK algorithm's time complexity is polynomial in the length of the input string."
    };

    public static final String[][] post_test_lesson_8_3_choices_easy = {
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

    public static final int[] post_test_lesson_8_3_answers_easy = {
            /*1*/    1, // False
            /*2*/    0, // True
            /*3*/    0, // True
            /*4*/    0, // True
            /*5*/    0, // True
            /*6*/    1, // False
            /*7*/    1, // False
            /*8*/    0, // True
            /*9*/    1, // False
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
    |        MEDIUM        |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson3_Medium_Questions() {
        return e_Module_8.get_PostTest_EasyMedium_Questions(
                post_test_lesson_8_3_questions_medium,
                post_test_lesson_8_3_choices_medium,
                post_test_lesson_8_3_answers_medium,
                e_Question.Difficulty.MEDIUM
        );
    }

    public static final String[] post_test_lesson_8_3_questions_medium = {
            /*1*/    "What is the first step in the CYK algorithm?",
            /*2*/    "In Chomsky Normal Form, which of the following is a valid production rule?",
            /*3*/    "What is the purpose of the triangular table in the CYK algorithm?",
            /*4*/    "Which of the following grammars is suitable for the CYK algorithm?",
            /*5*/    "What does the CYK algorithm check to determine if a string is part of a language?",
            /*6*/    "In which scenario does the CYK algorithm reject a string?",
            /*7*/    "Which of the following is NOT a step in the CYK algorithm?",
            /*8*/    "How are the cells of the CYK table filled for length-1 substrings?",
            /*9*/    "What symbol is placed in the table for the first character of the string 'b'?",
            /*10*/   "Which of the following is the correct time complexity of the CYK algorithm?",
            /*11*/   "What do the rows of the CYK table represent?",
            /*12*/   "In the example provided, which of the following productions applies to the string 'ba'?",
            /*13*/   "Which of the following is an essential requirement for the CYK algorithm to work?",
            /*14*/   "How does the CYK algorithm handle ε-productions (empty strings)?",
            /*15*/   "Which of the following would lead to the rejection of a string by the CYK algorithm?"
    };

    public static final String[][] post_test_lesson_8_3_choices_medium = {
            /*1*/    {"Initialize the parsing table", "Convert the CFG to CNF", "Fill the table", "Check the start symbol"},
            /*2*/    {"A → B", "A → ε", "A → BC", "A → BCD"},
            /*3*/    {"To store terminal symbols only", "To store non-terminal symbols that generate substrings", "To store the input string characters", "To keep track of parsing steps"},
            /*4*/    {"Regular grammar", "Context-free grammar in Chomsky Normal Form", "Context-sensitive grammar", "Any type of grammar"},
            /*5*/    {"The number of terminal symbols", "The presence of the start symbol in the top-right cell", "The length of the input string", "The number of rows in the table"},
            /*6*/    {"If the input string is empty", "If the start symbol is not in the last cell of the table", "If the grammar contains ε-productions", "If the grammar is regular"},
            /*7*/    {"Initialize the parsing table", "Remove ε-productions", "Fill the table using the production rules", "Determine the shortest derivation"},
            /*8*/    {"Using non-terminal rules", "Using terminal rules from CNF", "By merging adjacent cells", "Through backtracking"},
            /*9*/    {"A", "B", "S", "C"},
            /*10*/   {"O(n)", "O(n^2)", "O(n^3)", "O(2^n)"},
            /*11*/   {"Start positions of substrings", "The length of substrings", "Non-terminal symbols", "Terminal symbols"},
            /*12*/   {"S → BC", "S → AB", "A → BA", "B → CC"},
            /*13*/   {"The grammar must be in CNF", "The string must contain only lowercase letters", "The parsing table must be square-shaped", "The grammar must contain ε-productions"},
            /*14*/   {"They are ignored", "They are used to initialize the table", "They are removed during conversion to CNF", "They are included in the final step"},
            /*15*/   {"The string is too long", "The top-right cell does not contain the start symbol", "The table contains a null value", "The grammar has too many non-terminal symbols"}
    };

    public static final int[] post_test_lesson_8_3_answers_medium = {
            /*1*/    1, // Convert the CFG to CNF
            /*2*/    2, // A → BC
            /*3*/    1, // To store non-terminal symbols that generate substrings
            /*4*/    1, // Context-free grammar in Chomsky Normal Form
            /*5*/    1, // The presence of the start symbol in the top-right cell
            /*6*/    1, // If the start symbol is not in the last cell of the table
            /*7*/    1, // Remove ε-productions
            /*8*/    1, // Using terminal rules from CNF
            /*9*/    1, // B
            /*10*/   2, // O(n^3)
            /*11*/   1, // The length of substrings
            /*12*/   0, // S → BC
            /*13*/   0, // The grammar must be in CNF
            /*14*/   2, // They are removed during conversion to CNF
            /*15*/   1  // The top-right cell does not contain the start symbol
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |         HARD         |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson3_Hard_Questions() {
        return e_Module_8.get_PostTest_Hard_Questions(
                post_test_lesson_8_3_questions_hard,
                post_test_lesson_8_3_answers_hard,
                e_Question.Difficulty.HARD
        );
    }

    public static final String[] post_test_lesson_8_3_questions_hard = {
            /*1*/    "What algorithm checks if a string belongs to a CFG?",
            /*2*/    "What is the first step in the CYK algorithm?",
            /*3*/    "What non-terminals generate substrings in CYK?",
            /*4*/    "What kind of symbols are in Chomsky Normal Form?",
            /*5*/    "What technique is used by the CYK algorithm?",
            /*6*/    "What is filled during the CYK process?",
            /*7*/    "What symbol must be found in the top-right cell?",
            /*8*/    "What form eliminates empty productions?",
            /*9*/    "What does each cell contain in the CYK table?",
            /*10*/   "Which symbols are used to fill initial table cells?",
            /*11*/   "What type of parsing does CYK algorithm use?",
            /*12*/   "What step involves checking the start symbol?",
            /*13*/   "What is the form of a two-symbol production in CNF?",
            /*14*/   "What grammar does the CYK algorithm require?",
            /*15*/   "Which cells are filled first in the CYK table?"
    };

    public static final String[] post_test_lesson_8_3_answers_hard = {
            /*1*/    "CYK algorithm",
            /*2*/    "CNF conversion",
            /*3*/    "production rules",
            /*4*/    "non-terminal",
            /*5*/    "dynamic programming",
            /*6*/    "parsing table",
            /*7*/    "start symbol",
            /*8*/    "chomsky normal form",
            /*9*/    "non-terminals",
            /*10*/   "terminal symbols",
            /*11*/   "bottom-up parsing",
            /*12*/   "final step",
            /*13*/   "binary rule",
            /*14*/   "context-free grammar",
            /*15*/   "single-character"
    };
}