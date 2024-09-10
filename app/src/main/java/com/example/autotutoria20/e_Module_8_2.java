package com.example.autotutoria20;

public class e_Module_8_2 {

    /*
    +----------------------+
    |       PRE-TEST       |
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
            /*1*/    "What is parsing in the context of computer science?",
            /*2*/    "Which of the following is a top-down parsing technique?",
            /*3*/    "What type of grammar does parsing analyze?",
            /*4*/    "What does the '1' in LL(1) parsing refer to?",
            /*5*/    "Which parsing method involves constructing the parse tree from input to the start symbol?",
            /*6*/    "Which of the following grammars can be parsed by a recursive descent parser?",
            /*7*/    "In LL(1) parsing, the table is constructed based on:",
            /*8*/    "Which of these is a key feature of LL(1) parsing?",
            /*9*/    "What is the primary goal of parsing?",
            /*10*/   "Which method constructs the parse tree from the root to the terminals?",
            /*11*/   "Which of these is not a characteristic of recursive descent parsing?",
            /*12*/   "Which of these parsing techniques produces a leftmost derivation?",
            /*13*/   "In parsing, which symbol typically starts the parsing process?",
            /*14*/   "What type of grammar must be used for LL(1) parsing to work efficiently?",
            /*15*/   "Which of the following is a method for rewriting grammars to remove left recursion?"
    };

    public static final String[][] pre_test_lesson_8_2_choices = {
            /*1*/    {"A method of memory allocation", "A process of analyzing grammatical structure", "A form of data encryption", "A network routing technique"},
            /*2*/    {"Shift-reduce parsing", "Recursive descent parsing", "LR(1) parsing", "CYK parsing"},
            /*3*/    {"Regular expressions", "Context-Free Grammar (CFG)", "Context-Sensitive Grammar", "None of the above"},
            /*4*/    {"One production rule", "One lookahead symbol", "One parsing tree", "One derivation step"},
            /*5*/    {"Top-down parsing", "Recursive descent parsing", "Bottom-up parsing", "LL(1) parsing"},
            /*6*/    {"Left-recursive grammars", "LL(1) grammars", "Right-recursive grammars", "LR(1) grammars"},
            /*7*/    {"The parse tree", "The start symbol and input", "The current symbol and lookahead symbol", "None of the above"},
            /*8*/    {"Backtracking", "Non-backtracking", "Left recursion", "Rightmost derivation"},
            /*9*/    {"To check memory usage", "To analyze the syntax of a given input", "To optimize code execution", "To generate machine-level code"},
            /*10*/   {"Bottom-up parsing", "Top-down parsing", "LR parsing", "CYK parsing"},
            /*11*/   {"Simple to implement", "Handles grammars with left recursion", "May involve backtracking", "Works for LL(1) grammars"},
            /*12*/   {"Recursive descent parsing", "Bottom-up parsing", "LR(1) parsing", "Shift-reduce parsing"},
            /*13*/   {"Terminal symbol", "Non-terminal symbol", "Input symbol", "Start symbol"},
            /*14*/   {"Context-Free Grammar", "Right-recursive Grammar", "LL(1) Grammar", "Regular Grammar"},
            /*15*/   {"Recursive descent method", "Parse tree construction", "Grammar transformation", "LR parsing"}
    };

    public static final int[] pre_test_lesson_8_2_answers = {
            /*1*/    1, // 'A process of analyzing grammatical structure'
            /*2*/    1, // 'Recursive descent parsing'
            /*3*/    1, // 'Context-Free Grammar (CFG)'
            /*4*/    1, // 'One lookahead symbol'
            /*5*/    0, // 'Top-down parsing'
            /*6*/    1, // 'LL(1) grammars'
            /*7*/    2, // 'The current symbol and lookahead symbol'
            /*8*/    1, // 'Non-backtracking'
            /*9*/    1, // 'To analyze the syntax of a given input'
            /*10*/   1, // 'Top-down parsing'
            /*11*/   1, // 'Handles grammars with left recursion'
            /*12*/   0, // 'Recursive descent parsing'
            /*13*/   3, // 'Start symbol'
            /*14*/   2, // 'LL(1) Grammar'
            /*15*/   2  // 'Grammar transformation'
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
            /*1*/    "Shift-reduce parsing is a top-down parsing approach.",
            /*2*/    "In bottom-up parsing, the parser starts from the leaves and works up to the root of the parse tree.",
            /*3*/    "The shift-reduce parsing technique uses a stack and input buffer for processing strings.",
            /*4*/    "LR parsers read input from right to left.",
            /*5*/    "Canonical LR is a more powerful form of LR parsing compared to SLR and LALR.",
            /*6*/    "In shift-reduce parsing, ‘shift’ means pushing the input symbol onto the stack.",
            /*7*/    "LR parsers can only parse a small class of grammars.",
            /*8*/    "In bottom-up parsing, reductions are applied using production rules.",
            /*9*/    "SLR parsers are more complex than Canonical LR parsers.",
            /*10*/   "In LR parsing, the table directs the parser to shift, reduce, accept, or show an error.",
            /*11*/   "Bottom-up parsers work by predicting the grammar structure from the start symbol.",
            /*12*/   "The main components of shift-reduce parsing are a stack and an input buffer.",
            /*13*/   "LR parsers produce a rightmost derivation of the input in reverse.",
            /*14*/   "The shift-reduce parsing method is a form of top-down parsing.",
            /*15*/   "In shift-reduce parsing, ‘reduce’ means popping symbols from the stack and applying a production rule."
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
            /*1*/    1, // False
            /*2*/    0, // True
            /*3*/    0, // True
            /*4*/    1, // False
            /*5*/    0, // True
            /*6*/    0, // True
            /*7*/    1, // False
            /*8*/    0, // True
            /*9*/    1, // False
            /*10*/   0, // True
            /*11*/   1, // False
            /*12*/   0, // True
            /*13*/   0, // True
            /*14*/   1, // False
            /*15*/   0  // True
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
            /*11*/   "What does ‘F’ typically represent in parsing grammars like the one used in this lesson?",
            /*12*/   "Which of the following correctly defines LALR parsing?",
            /*13*/   "Which parsing technique uses both a stack and input buffer?",
            /*14*/   "In bottom-up parsing, the grammar production rule applied to reduce symbols is based on:",
            /*15*/   "Which of the following is a key characteristic of LR parsers?"
    };

    public static final String[][] post_test_lesson_8_2_choices_medium = {
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

    public static final int[] post_test_lesson_8_2_answers_medium = {
            /*1*/    1, // To construct the parse tree from the leaves
            /*2*/    1, // Canonical LR
            /*3*/    1, // Push the input symbol onto the stack
            /*4*/    3, // Predictive LR
            /*5*/    1, // Error
            /*6*/    2, // Handles
            /*7*/    1, // Left to right parsing of rightmost derivation in reverse
            /*8*/    2, // SLR
            /*9*/    2, // Grammar symbols
            /*10*/   2, // Handling a wider range of grammars
            /*11*/   1, // Factor
            /*12*/   0, // Lookahead LR
            /*13*/   1, // Shift-reduce parsing
            /*14*/   1, // Rightmost derivation in reverse
            /*15*/   3  // They use a parsing table for decision making
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
            /*1*/    "What parsing technique uses a stack and input buffer?",
            /*2*/    "What is the simplest form of LR parsing?",
            /*3*/    "What type of parser reads left to right?",
            /*4*/    "What is used to decide actions in LR parsing?",
            /*5*/    "What parser variation is commonly used in compilers?",
            /*6*/    "What is a substring reduced during parsing?",
            /*7*/    "What does LR parsing handle more than LL(1)?",
            /*8*/    "What parsing method produces reverse derivation?",
            /*9*/    "What is the second step in shift-reduce parsing?",
            /*10*/   "What is the starting point for bottom-up parsing?",
            /*11*/   "What represents a non-terminal in grammars?",
            /*12*/   "What structure is used for reduction in shift-reduce parsing?",
            /*13*/   "What does LR stand for?",
            /*14*/   "What action does shift-reduce parsing take when no rule applies?",
            /*15*/   "What method processes context-free grammars?"
    };

    public static final String[] post_test_lesson_8_2_answers_hard = {
            /*1*/    "shift-reduce",
            /*2*/    "simple LR",
            /*3*/    "LR parser",
            /*4*/    "parsing table",
            /*5*/    "lookahead LR",
            /*6*/    "Handle symbol",
            /*7*/    "wider grammars",
            /*8*/    "LR parsing",
            /*9*/    "reduce action",
            /*10*/   "input symbols",
            /*11*/   "grammar symbol",
            /*12*/   "parse tree",
            /*13*/   "left-right",
            /*14*/   "error state",
            /*15*/   "bottom-up parsing"
    };
}