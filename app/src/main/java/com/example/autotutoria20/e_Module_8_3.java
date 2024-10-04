package com.example.autotutoria20;

public class e_Module_8_3 {

    /*
    +----------------------+
    |       PRE-TEST       |
    |        LESSON   3    |
    +----------------------+
    */

    public static e_Question[] get_PreTest_Lesson3_Questions() {
        return e_Module_8.getPreTestQuestions(
                pre_test_lesson_3_questions,
                pre_test_lesson_3_choices,
                pre_test_lesson_3_answers
        );
    }

    public static final String[] pre_test_lesson_3_questions = {
            /*1*/    "What is the primary role of bottom-up parsing?",
            /*2*/    "What does shift-reduce parsing use to hold grammar symbols?",
            /*3*/    "In shift-reduce parsing, what is the function of 'shifting'?",
            /*4*/    "What does 'reduce' mean in shift-reduce parsing?",
            /*5*/    "Which type of parsing works by reading input from left to right and producing a rightmost derivation in reverse?",
            /*6*/    "Which of the following is NOT a type of LR parser?",
            /*7*/    "What is a 'handle' in shift-reduce parsing?",
            /*8*/    "Which parsing technique can handle a wider range of grammars than LL(1)?",
            /*9*/    "Which of the following is a more practical version of LR parsing, often used in compilers?",
            /*10*/   "What does the action 'reduce (F → id)' represent in shift-reduce parsing?",
            /*11*/   "In the example 'id + id', what is the first action taken in shift-reduce parsing?",
            /*12*/   "What symbol is reduced to 'F' in shift-reduce parsing for the input 'id + id'?",
            /*13*/   "In the LR parsing process, which part is crucial for determining whether to shift or reduce?",
            /*14*/   "Which type of LR parser is considered the simplest form?",
            /*15*/   "What is the final step of the shift-reduce parsing process?"
    };

    public static final String[][] pre_test_lesson_3_choices = {
            /*1*/    {"To analyze the structure of the parse tree from top to bottom", "To start from the input symbols and work towards the start symbol", "To build the parse tree from the root to the leaves", "To work only with terminal symbols"},
            /*2*/    {"A table", "A queue", "A stack", "An array"},
            /*3*/    {"Adding an input symbol to the stack", "Removing a handle from the stack", "Applying a production rule", "Looking ahead at the next symbol"},
            /*4*/    {"Adding an input symbol to the stack", "Removing a handle from the stack and applying a production rule", "Shifting all symbols from the stack", "Combining all input symbols"},
            /*5*/    {"LL Parsing", "LR Parsing", "Top-down Parsing", "Recursive Descent Parsing"},
            /*6*/    {"Canonical LR", "SLR", "LALR", "BLR"},
            /*7*/    {"A symbol on the input buffer", "A specific substring on the stack that matches a production rule", "The start symbol of the grammar", "The root of the parse tree"},
            /*8*/    {"Shift-reduce parsing", "LR Parsing", "Recursive Descent Parsing", "Predictive Parsing"},
            /*9*/    {"SLR", "LALR", "Canonical LR", "Recursive Descent"},
            /*10*/   {"Shifting an 'id' symbol onto the stack", "Replacing 'id' on the stack with 'F'", "Reducing the entire input to a start symbol", "Producing a rightmost derivation"},
            /*11*/   {"Shift 'id' onto the stack", "Reduce 'id' to 'F'", "Shift '+' onto the stack", "Accept the input"},
            /*12*/   {"+", "id", "T", "E"},
            /*13*/   {"The stack", "The input string", "The parsing table", "The start symbol"},
            /*14*/   {"Canonical LR", "SLR", "LALR", "None of the above"},
            /*15*/   {"Shifting all symbols from the stack", "Reducing all symbols to a start symbol", "Accepting the input", "Rejecting the input"}
    };

    public static final int[] pre_test_lesson_3_answers = {
            /*1*/    1,  // "To start from the input symbols and work towards the start symbol"
            /*2*/    2,  // "A stack"
            /*3*/    0,  // "Adding an input symbol to the stack"
            /*4*/    1,  // "Removing a handle from the stack and applying a production rule"
            /*5*/    1,  // "LR Parsing"
            /*6*/    3,  // "BLR"
            /*7*/    1,  // "A specific substring on the stack that matches a production rule"
            /*8*/    1,  // "LR Parsing"
            /*9*/    1,  // "LALR"
            /*10*/   1,  // "Replacing 'id' on the stack with 'F'"
            /*11*/   0,  // "Shift 'id' onto the stack"
            /*12*/   1,  // "id"
            /*13*/   2,  // "The parsing table"
            /*14*/   1,  // "SLR"
            /*15*/   2   // "Accepting the input"
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
            /*1*/    "The CYK algorithm can be used on any context-free grammar (CFG) without converting it to Chomsky Normal Form (CNF).",
            /*2*/    "The CYK algorithm is a dynamic programming approach used to parse a string efficiently.",
            /*3*/    "In Chomsky Normal Form, every production rule must generate at least one terminal and one non-terminal symbol.",
            /*4*/    "The first step in the CYK algorithm is converting the CFG to Chomsky Normal Form.",
            /*5*/    "The CYK algorithm fills a parsing table using a triangular structure.",
            /*6*/    "A CFG in CNF can only produce two non-terminal symbols or one terminal symbol.",
            /*7*/    "The table used in the CYK algorithm is filled by combining smaller substrings.",
            /*8*/    "The CYK algorithm can parse strings that contain ε-productions (productions that generate the empty string).",
            /*9*/    "The top-right cell of the CYK table indicates whether the string is accepted by the grammar.",
            /*10*/   "The grammar for the CYK algorithm does not require the elimination of unreachable productions.",
            /*11*/   "In the CYK algorithm, all rows represent the length of the substrings.",
            /*12*/   "The CYK algorithm only works with grammars that have recursive rules.",
            /*13*/   "The CYK algorithm can reject strings that belong to a CFG if the CFG is not converted to CNF.",
            /*14*/   "The CYK algorithm requires a parsing table that must be completely filled before making a decision about the string.",
            /*15*/   "The CYK algorithm always accepts every string as valid, regardless of the grammar."
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
            /*1*/    1,  // False
            /*2*/    0,  // True
            /*3*/    1,  // False
            /*4*/    0,  // True
            /*5*/    0,  // True
            /*6*/    0,  // True
            /*7*/     0, // True
            /*8*/     1, // False
            /*9*/     0, // True
            /*10*/    1, // False
            /*11*/    0, // True
            /*12*/    1, // False
            /*13*/    0, // True
            /*14*/    0, // True
            /*15*/    1  // False
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
            /*1*/    "What is the first step in applying the CYK algorithm?",
            /*2*/    "What kind of grammar does the CYK algorithm require?",
            /*3*/    "What does the cell T[i,j] in the CYK table represent?",
            /*4*/    "In CNF, which of the following is a valid production rule?",
            /*5*/    "Which of the following is NOT a step in the CYK algorithm?",
            /*6*/    "Which of the following is true about the CYK algorithm?",
            /*7*/    "When parsing a string, where do you check if it is accepted by the grammar?",
            /*8*/    "Which type of symbols does a CNF production rule A → a generate?",
            /*9*/    "What happens if the start symbol is found in the top-right cell of the CYK table?",
            /*10*/   "What does CNF stand for?",
            /*11*/   "Which of the following is not allowed in a CNF grammar?",
            /*12*/   "What type of structure does the CYK algorithm use for its parsing table?",
            /*13*/   "Which symbol represents the starting point in a CFG?",
            /*14*/   "What does dynamic programming help with in the CYK algorithm?",
            /*15*/   "Which of the following is NOT an example of a terminal symbol?"
    };

    public static final String[][] post_test_lesson_8_3_choices_medium = {
            /*1*/    {"Filling the parsing table", "Converting CFG to CNF", "Checking the start symbol", "Combining substrings"},
            /*2*/    {"Unrestricted Grammar", "Regular Grammar", "Chomsky Normal Form (CNF)", "Context-Sensitive Grammar"},
            /*3*/    {"The starting position of a substring", "The non-terminals generating a substring", "The terminal symbols of a string", "The start symbol of the grammar"},
            /*4*/    {"A → BCD", "A → ε", "A → BC", "A → abc"},
            /*5*/    {"Initialize the parsing table", "Fill the parsing table", "Convert CNF back to CFG", "Check the start symbol"},
            /*6*/    {"It is a bottom-up parsing algorithm", "It does not use dynamic programming", "It works with unrestricted grammars", "It parses a string in linear time"},
            /*7*/    {"Bottom-left cell", "Bottom-right cell", "Top-left cell", "Top-right cell"},
            /*8*/    {"Terminal symbol", "Non-terminal symbol", "Recursive symbol", "Empty string"},
            /*9*/    {"The string is rejected", "The string is accepted", "The string is ambiguous", "The parsing is incomplete"},
            /*10*/   {"Context-Free Normalization", "Chomsky Normal Form", "Complex Normal Form", "Conjunctive Normal Form"},
            /*11*/   {"A → BC", "A → a", "A → ε", "A → B"},
            /*12*/   {"Circular", "Triangular", "Square", "Rectangular"},
            /*13*/   {"Terminal symbol", "Non-terminal symbol", "Start symbol", "Recursive symbol"},
            /*14*/   {"Breaking strings into substrings", "Combining two grammars", "Speeding up the parsing process", "Verifying terminal symbols"},
            /*15*/   {"a", "b", "S", "c"}
    };

    public static final int[] post_test_lesson_8_3_answers_medium = {
            /*1*/    1,  // "Converting CFG to CNF"
            /*2*/    2,  // "Chomsky Normal Form (CNF)"
            /*3*/    1,  // "The non-terminals generating a substring"
            /*4*/    2,  // "A → BC"
            /*5*/    2,  // "Convert CNF back to CFG"
            /*6*/    0,  // "It is a bottom-up parsing algorithm"
            /*7*/    3,  // "Top-right cell"
            /*8*/    0,  // "Terminal symbol"
            /*9*/    1,  // "The string is accepted"
            /*10*/   1,  // "Chomsky Normal Form"
            /*11*/   2,  // "A → ε"
            /*12*/   1,  // "Triangular"
            /*13*/   2,  // "Start symbol"
            /*14*/   2,  // "Speeding up the parsing process"
            /*15*/   2   // "S"
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
            /*1*/    "Which form must a CFG be converted to for the CYK algorithm?",
            /*2*/    "What algorithm is used for parsing a string in CNF?",
            /*3*/    "What data structure is essential in the CYK algorithm?",
            /*4*/    "What does CNF stand for?",
            /*5*/    "Which symbol initiates parsing in a CFG?",
            /*6*/    "What does each CYK table cell represent?",
            /*7*/    "What approach is used by the CYK algorithm?",
            /*8*/    "Which table cell checks if the string is accepted?",
            /*9*/    "What rule involves two non-terminals in CNF?",
            /*10*/   "What does a single terminal rule in CNF produce?",
            /*11*/   "What production rule is eliminated in CNF conversion?",
            /*12*/   "Where is the start symbol checked in CYK?",
            /*13*/   "What is the main benefit of dynamic programming in CYK?",
            /*14*/   "What kind of parsing does the CYK algorithm perform?",
            /*15*/   "What two symbols are combined in CNF rules?"
    };

    public static final String[] post_test_lesson_8_3_answers_hard = {
            /*1*/    "chomsky normal form",
            /*2*/    "CYK algorithm",
            /*3*/    "parsing table",
            /*4*/    "chomsky normal form",
            /*5*/    "start symbol",
            /*6*/    "non-terminal symbols",
            /*7*/    "dynamic programming",
            /*8*/    "top-right cell",
            /*9*/    "binary production",
            /*10*/   "terminal symbol",
            /*11*/   "empty production",
            /*12*/   "top-right cell",
            /*13*/   "efficient parsing",
            /*14*/   "bottom-up parsing",
            /*15*/   "non-terminal pairs"
    };

}