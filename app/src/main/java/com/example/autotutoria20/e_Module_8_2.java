package com.example.autotutoria20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class e_Module_8_2 {

    /*
    +----------------------+
    |       PRE-TEST       |
    |        LESSON   2    |
    +----------------------+
    */

    public static e_Question[] get_PreTest_Lesson2_Questions() {
        return e_Module_8.getPreTestQuestions(
                pre_test_lesson_8_2_questions,
                pre_test_lesson_8_2_choices,
                pre_test_lesson_8_2_answers
        );
    }

    public static final String[] pre_test_lesson_8_2_questions = {
            /*1*/    "What is parsing?",
            /*2*/    "Which of the following is not a type of parsing?",
            /*3*/    "What is the goal of top-down parsing?",
            /*4*/    "In recursive descent parsing, what is used to apply grammar rules?",
            /*5*/    "Which of the following statements is true about recursive descent parsing?",
            /*6*/    "Which parsing technique does LL(1) belong to?",
            /*7*/    "What does the 'LL' in LL(1) parsing stand for?",
            /*8*/    "How many lookahead symbols does LL(1) parsing use?",
            /*9*/    "Which of the following grammar rules is an example of left recursion?",
            /*10*/   "What needs to be done to handle left recursion in recursive descent parsing?",
            /*11*/   "Which of the following is an example of a rewritten grammar after removing left recursion?",
            /*12*/   "What is the primary feature of LL(1) parsing?",
            /*13*/   "Which of the following is not a characteristic of top-down parsing?",
            /*14*/   "What is one advantage of LL(1) parsing over recursive descent parsing?",
            /*15*/   "Which symbol in a CFG grammar represents an empty string?"
    };

    public static final String[][] pre_test_lesson_8_2_choices = {
            /*1*/    {"The process of analyzing a string of symbols to determine its grammatical structure", "A way of storing data in a file", "A mathematical operation", "A search algorithm"},
            /*2*/    {"Top-down parsing", "Bottom-up parsing", "Inside-out parsing", "Left-to-right parsing"},
            /*3*/    {"To build a parse tree from the input and work up to the start symbol", "To build a parse tree from the start symbol and work down to the input", "To scan the input from right to left", "To remove recursion from the grammar"},
            /*4*/    {"Loops", "Functions", "Arrays", "Symbols"},
            /*5*/    {"It can handle grammars with left recursion.", "It cannot handle grammars with left recursion.", "It is only used for natural language parsing.", "It works for any type of grammar without restrictions."},
            /*6*/    {"Bottom-up", "Top-down", "Inside-out", "Right-to-left"},
            /*7*/    {"Left-to-right and Leftmost derivation", "Lookahead-to-right and Leftmost derivation", "Left-to-left parsing", "Language Learning"},
            /*8*/    {"2", "3", "1", "0"},
            /*9*/    {"E → E + T", "T → T * F", "F → ( E )", "T → F T'"},
            /*10*/   {"The grammar must be rewritten", "Backtracking must be used", "The input must be reversed", "The parse tree must be built bottom-up"},
            /*11*/   {"E → E + T", "E → T E'", "T → T * F", "F → ( E )"},
            /*12*/   {"It uses multiple lookahead symbols.", "It is non-backtracking and predictive.", "It requires backtracking to function.", "It can parse any type of grammar."},
            /*13*/   {"It works from the start symbol down to the input.", "It can involve backtracking.", "It builds the parse tree from input symbols up to the start symbol.", "It includes techniques like recursive descent parsing."},
            /*14*/   {"LL(1) does not require backtracking", "LL(1) uses a rightmost derivation", "LL(1) can handle any grammar", "LL(1) uses loops instead of functions"},
            /*15*/   {"ε", "λ", "∅", "*"}
    };

    public static final int[] pre_test_lesson_8_2_answers = {
            /*1*/    0,  // "The process of analyzing a string of symbols to determine its grammatical structure"
            /*2*/    2,  // "Inside-out parsing"
            /*3*/    1,  // "To build a parse tree from the start symbol and work down to the input"
            /*4*/    1,  // "Functions"
            /*5*/    1,  // "It cannot handle grammars with left recursion."
            /*6*/    1,  // "Top-down"
            /*7*/    0,  // "Left-to-right and Leftmost derivation"
            /*8*/    2,  // "1"
            /*9*/    0,  // "E → E + T"
            /*10*/   0,  // "The grammar must be rewritten"
            /*11*/   1,  // "E → T E'"
            /*12*/   1,  // "It is non-backtracking and predictive."
            /*13*/   2,  // "It builds the parse tree from input symbols up to the start symbol."
            /*14*/   0,  // "LL(1) does not require backtracking"
            /*15*/   0   // "ε"
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |          EASY        |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson2_Easy_Questions() {
        return e_Module_8.get_PostTest_EasyMedium_Questions(
                post_test_lesson_8_2_questions_easy,
                post_test_lesson_8_2_choices_easy,
                post_test_lesson_8_2_answers_easy,
                e_Question.Difficulty.EASY
        );
    }

    public static final String[] post_test_lesson_8_2_questions_easy = {
            /*1*/    "Shift-Reduce Parsing uses a stack to hold grammar symbols and an input buffer for the remaining string.",
            /*2*/    "In Shift-Reduce Parsing, reduction always happens before shifting symbols onto the stack.",
            /*3*/    "LR parsing stands for Left-to-right parsing of a leftmost derivation in reverse.",
            /*4*/    "Shift-Reduce Parsing is an example of a top-down parsing technique.",
            /*5*/    "In LR parsing, the parsing table helps in deciding whether to shift or reduce based on the current state and input symbol.",
            /*6*/    "SLR is the most general form of LR parsing.",
            /*7*/    "In LR parsing, the process starts with the root of the parse tree and works its way to the leaves.",
            /*8*/    "The canonical LR parser can handle the largest class of grammars among all LR parsers.",
            /*9*/    "In Shift-Reduce Parsing, every symbol shifted onto the stack is immediately reduced.",
            /*10*/   "Bottom-up parsers work their way from the leaves of the parse tree to the start symbol.",
            /*11*/   "LALR parsing is less practical than SLR parsing for compiler design.",
            /*12*/   "The steps of shift-reduce parsing include shift, reduce, accept, and error actions.",
            /*13*/   "LR parsers read the input from right to left and generate a leftmost derivation.",
            /*14*/   "LR parsers are limited to LL(1) grammars.",
            /*15*/   "Bottom-up parsing cannot be implemented using a stack."
    };

    public static final String[][] post_test_lesson_8_2_choices_easy = {
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

    public static final int[] post_test_lesson_8_2_answers_easy = {
            /*1*/    0,  // True
            /*2*/    1,  // False
            /*3*/    1,  // False
            /*4*/    1,  // False
            /*5*/    0,  // True
            /*6*/    1,  // False
            /*7*/    1,  // False
            /*8*/    0,  // True
            /*9*/    1,  // False
            /*10*/   0,  // True
            /*11*/   1,  // False
            /*12*/   0,  // True
            /*13*/   1,  // False
            /*14*/   1,  // False
            /*15*/   1   // False
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |        MEDIUM        |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson2_Medium_Questions() {
        return e_Module_8.get_PostTest_EasyMedium_Questions(
                post_test_lesson_8_2_questions_medium,
                post_test_lesson_8_2_choices_medium,
                post_test_lesson_8_2_answers_medium,
                e_Question.Difficulty.MEDIUM
        );
    }

    public static final String[] post_test_lesson_8_2_questions_medium = {
            /*1*/    "Which parsing technique does Shift-Reduce Parsing belong to?",
            /*2*/    "What is the function of a stack in Shift-Reduce Parsing?",
            /*3*/    "What does LR in LR parsing stand for?",
            /*4*/    "What is the simplest form of LR parsing?",
            /*5*/    "Which of the following is not an action in the parsing table for LR parsers?",
            /*6*/    "In Shift-Reduce Parsing, which of the following happens after shifting a symbol onto the stack?",
            /*7*/    "Which of the following parsers can handle the largest class of grammars?",
            /*8*/    "Which technique is used to produce a rightmost derivation in reverse?",
            /*9*/    "What is the purpose of the parsing table in LR Parsing?",
            /*10*/   "In the grammar: E→ E + T | T, what would be the first step in Shift-Reduce Parsing for the input 'id + id'?",
            /*11*/   "Which parser variation is most commonly used in compilers?",
            /*12*/   "What does LR parsing do when it encounters an error?",
            /*13*/   "Which of the following is a key characteristic of LR parsing?",
            /*14*/   "Which parser variation allows for lookahead to make parsing decisions?",
            /*15*/   "Which of the following is an example of bottom-up parsing?"
    };

    public static final String[][] post_test_lesson_8_2_choices_medium = {
            /*1*/    {"Top-Down", "Bottom-Up", "Recursive", "Predictive"},
            /*2*/    {"To hold input symbols", "To store grammar rules", "To hold grammar symbols and input buffer", "To store errors"},
            /*3*/    {"Left to Right", "Lookahead Right", "Last Right", "Left Recursive"},
            /*4*/    {"Canonical LR", "SLR", "LALR", "CLR"},
            /*5*/    {"Shift", "Reduce", "Derive", "Accept"},
            /*6*/    {"Parsing complete", "Immediate reduction", "Comparison with the input buffer", "Application of production rules"},
            /*7*/    {"SLR", "Canonical LR", "LALR", "Recursive Descent"},
            /*8*/    {"LL Parsing", "Recursive Parsing", "LR Parsing", "Predictive Parsing"},
            /*9*/    {"To store tokens", "To decide shift and reduce actions", "To generate errors", "To create parse trees"},
            /*10*/   {"Reduce E to id", "Shift the first token id", "Reduce T to E", "Shift the '+' symbol"},
            /*11*/   {"SLR", "LALR", "Canonical LR", "LL(1)"},
            /*12*/   {"Shifts the next token", "Performs a reduction", "Accepts the input", "Signals an error in the table"},
            /*13*/   {"Top-down parsing", "Uses predictive methods", "Rightmost derivation in reverse", "Immediate left recursion"},
            /*14*/   {"Canonical LR", "SLR", "LALR", "Predictive"},
            /*15*/   {"LL(1) Parsing", "Recursive Descent", "Shift-Reduce Parsing", "Predictive Parsing"}
    };

    public static final int[] post_test_lesson_8_2_answers_medium = {
            /*1*/    1,  // "Bottom-Up"
            /*2*/    2,  // "To hold grammar symbols and input buffer"
            /*3*/    0,  // "Left to Right"
            /*4*/    1,  // "SLR"
            /*5*/    2,  // "Derive"
            /*6*/    3,  // "Application of production rules"
            /*7*/    1,  // "Canonical LR"
            /*8*/    2,  // "LR Parsing"
            /*9*/    1,  // "To decide shift and reduce actions"
            /*10*/   1,  // "Shift the first token id"
            /*11*/   1,  // "LALR"
            /*12*/   3,  // "Signals an error in the table"
            /*13*/   2,  // "Rightmost derivation in reverse"
            /*14*/   2,  // "LALR"
            /*15*/   2   // "Shift-Reduce Parsing"
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |         HARD         |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson2_Hard_Questions() {
        return e_Module_8.get_PostTest_Hard_Questions(
                post_test_lesson_8_2_questions_hard,
                post_test_lesson_8_2_answers_hard,
                e_Question.Difficulty.HARD
        );
    }

    public static final String[] post_test_lesson_8_2_questions_hard = {
            /*1*/    "Identify the parsing technique that uses a stack to hold grammar symbols and an input buffer for the remaining string.",
            /*2*/    "Name the parsing table used in LR Parsing to decide actions.",
            /*3*/    "Which bottom-up parsing approach is the most common?",
            /*4*/    "Identify the simplest form of LR Parsing.",
            /*5*/    "What does the abbreviation LR stand for in LR parsing?",
            /*6*/    "Which type of parser produces a rightmost derivation in reverse?",
            /*7*/    "Name the action taken in Shift-Reduce Parsing when a symbol is pushed onto the stack.",
            /*8*/    "What is the most general form of LR Parsing?",
            /*9*/    "Identify the type of grammar that LR parsing can handle that is more powerful than LL(1) grammar.",
            /*10*/   "Name the action in LR parsing when a handle is replaced by a non-terminal.",
            /*11*/   "What is the key characteristic that distinguishes LALR from other LR parsers?",
            /*12*/   "Which action in Shift-Reduce Parsing corresponds to accepting the input string?",
            /*13*/   "Name the grammar symbol that Shift-Reduce Parsing aims to reduce the input to.",
            /*14*/   "What does LALR stand for in LR Parsing?",
            /*15*/   "Which parser is used most in modern compiler design?"
    };

    public static final String[] post_test_lesson_8_2_answers_hard = {
            /*1*/    "shift-reduce parsing",
            /*2*/    "parsing table",
            /*3*/    "shift-reduce parsing",
            /*4*/    "SLR",
            /*5*/    "left to right",
            /*6*/    "LR parser",
            /*7*/    "shift",
            /*8*/    "canonical LR",
            /*9*/    "context-free grammar",
            /*10*/   "reduce",
            /*11*/   "lookahead",
            /*12*/   "accept",
            /*13*/   "start Symbol",
            /*14*/   "lookahead LR",
            /*15*/   "LALR parser"
    };

}
