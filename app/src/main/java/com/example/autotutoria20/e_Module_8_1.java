package com.example.autotutoria20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class e_Module_8_1 {

    /*
    +----------------------+
    |       PRE-TEST       |
    |        LESSON 1      |
    +----------------------+
    */

    public static e_Question[] get_PreTest_Lesson1_Questions() {
        return e_Module_8.getPreTestQuestions(
                pre_test_lesson_1_questions,
                pre_test_lesson_1_choices,
                pre_test_lesson_1_answers
        );
    }

    public static final String[] pre_test_lesson_1_questions = {
            /*1*/    "What is the purpose of normal forms in Context-Free Grammars (CFGs)?",
            /*2*/    "Which of the following is a normal form used in CFGs?",
            /*3*/    "In Chomsky Normal Form (CNF), what is the form of the production rule?",
            /*4*/    "Which of the following is a step in converting a CFG to CNF?",
            /*5*/    "In CNF, what should the start symbol generate if the language contains ε?",
            /*6*/    "Which of the following is NOT a valid production rule in CNF?",
            /*7*/    "What does the Greibach Normal Form (GNF) ensure in production rules?",
            /*8*/    "Which parsing approach benefits most from CFGs in GNF?",
            /*9*/    "What is an example of a GNF production rule?",
            /*10*/   "What does CNF simplify in terms of parsing algorithms?",
            /*11*/   "Why is left recursion eliminated when converting a CFG to GNF?",
            /*12*/   "Which of the following CFG conversions is a necessary step for both CNF and GNF?",
            /*13*/   "What type of productions does Chomsky Normal Form (CNF) prohibit?",
            /*14*/   "When converting a CFG to CNF, what is done if the start symbol appears on the right-hand side of a rule?",
            /*15*/   "In GNF, what is a common use of the grammar form?"
    };

    public static final String[][] pre_test_lesson_1_choices = {
            /*1*/    {"To make CFGs more complex", "To simplify the structure of CFGs", "To eliminate CFGs from parsing", "To convert CFGs into regular expressions"},
            /*2*/    {"Kleene Normal Form", "Chomsky Normal Form (CNF)", "Recursive Normal Form", "Kleene Closure"},
            /*3*/    {"A → BC or A → c", "A → B or A → ε", "A → a or B → b", "A → BCD"},
            /*4*/    {"Remove terminals", "Remove null, unit, and useless productions", "Add more production rules", "Eliminate left recursion"},
            /*5*/    {"S → ε", "S → AB", "S → a", "S → A"},
            /*6*/    {"S → AB", "S → a", "S → aA", "A → BC"},
            /*7*/    {"Productions start with a terminal symbol", "Productions start with a non-terminal symbol", "Productions have exactly two non-terminal symbols", "Productions must be recursive"},
            /*8*/    {"Bottom-up parsing", "Top-down parsing", "Predictive parsing", "Recursive descent parsing"},
            /*9*/    {"S → AB", "S → aA", "S → BA", "A → ε"},
            /*10*/   {"Recursive descent parsing", "Syntax tree generation", "The CYK algorithm", "Lexical analysis"},
            /*11*/   {"To simplify parsing", "To comply with CNF rules", "To ensure productions start with a terminal symbol", "To reduce the number of rules"},
            /*12*/   {"Removing unit productions", "Removing left recursion", "Adding new terminal symbols", "Eliminating non-terminal symbols"},
            /*13*/   {"Unit productions", "Recursive productions", "Mixed terminal and non-terminal symbols on the right-hand side", "Productions that start with a terminal symbol"},
            /*14*/   {"The rule is removed", "The rule is converted to left recursion", "A new start symbol is introduced", "The rule is left unchanged"},
            /*15*/   {"Eliminating null productions", "Handling ambiguous grammars", "Constructing top-down parsers", "Generating leftmost derivations"}
    };

    public static final int[] pre_test_lesson_1_answers = {
            /*1*/    1, // "To simplify the structure of CFGs"
            /*2*/    1, // "Chomsky Normal Form (CNF)"
            /*3*/    0, // "A → BC or A → c"
            /*4*/    1, // "Remove null, unit, and useless productions"
            /*5*/    0, // "S → ε"
            /*6*/    2, // "S → aA"
            /*7*/    0, // "Productions start with a terminal symbol"
            /*8*/    1, // "Top-down parsing"
            /*9*/    1, // "S → aA"
            /*10*/   2, // "The CYK algorithm"
            /*11*/   2, // "To ensure productions start with a terminal symbol"
            /*12*/   1, // "Removing left recursion"
            /*13*/   2, // "Mixed terminal and non-terminal symbols on the right-hand side"
            /*14*/   2, // "A new start symbol is introduced"
            /*15*/   2  // "Constructing top-down parsers"
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |          EASY        |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson1_Easy_Questions() {
        return e_Module_8.get_PostTest_EasyMedium_Questions(
                post_test_lesson_1_questions_easy,
                post_test_lesson_1_choices_easy,
                post_test_lesson_1_answers_easy,
                e_Question.Difficulty.EASY
        );
    }

    public static final String[] post_test_lesson_1_questions_easy = {
            /*1*/    "Parsing is the process of determining the grammatical structure of a string of symbols.",
            /*2*/    "Bottom-up parsing builds the parse tree from the root to the input.",
            /*3*/    "Recursive descent parsing is a bottom-up parsing method.",
            /*4*/    "LL(1) parsing uses multiple lookahead symbols to determine the correct production rule.",
            /*5*/    "Top-down parsing constructs the parse tree from the start symbol downward.",
            /*6*/    "Parsing is used to analyze a string of symbols based on a given grammar.",
            /*7*/    "Recursive descent parsing works well for grammars that contain left recursion.",
            /*8*/    "In LL(1) parsing, the input is read from right to left.",
            /*9*/    "Top-down parsing is not suitable for grammars with left recursion.",
            /*10*/   "LL(1) parsers always involve backtracking.",
            /*11*/   "Parsing is essential for understanding programming language syntax.",
            /*12*/   "In LL(1) parsing, a parsing table is used to guide decisions based on the current symbol and lookahead.",
            /*13*/   "Top-down parsers are less efficient than LL(1) parsers.",
            /*14*/   "Recursive descent parsing always requires backtracking.",
            /*15*/   "Context-Free Grammars (CFGs) are not used in parsing."
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
            /*2*/    1, // False
            /*3*/    1, // False
            /*4*/    1, // False
            /*5*/    0, // True
            /*6*/    0, // True
            /*7*/    1, // False
            /*8*/    1, // False
            /*9*/    0, // True
            /*10*/   1, // False
            /*11*/   0, // True
            /*12*/   0, // True
            /*13*/   1, // False
            /*14*/   1, // False
            /*15*/   1  // False
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |        MEDIUM        |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson1_Medium_Questions() {
        return e_Module_8.get_PostTest_EasyMedium_Questions(
                post_test_lesson_1_questions_medium,
                post_test_lesson_1_choices_medium,
                post_test_lesson_1_answers_medium,
                e_Question.Difficulty.MEDIUM
        );
    }

    public static final String[] post_test_lesson_1_questions_medium = {
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

    public static final String[][] post_test_lesson_1_choices_medium = {
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

    public static final int[] post_test_lesson_1_answers_medium = {
            /*1*/    1, // "A process of analyzing grammatical structure"
            /*2*/    1, // "Recursive descent parsing"
            /*3*/    1, // "Context-Free Grammar (CFG)"
            /*4*/    1, // "One lookahead symbol"
            /*5*/    2, // "Bottom-up parsing"
            /*6*/    1, // "LL(1) grammars"
            /*7*/    2, // "The current symbol and lookahead symbol"
            /*8*/    1, // "Non-backtracking"
            /*9*/    1, // "To analyze the syntax of a given input"
            /*10*/   1, // "Top-down parsing"
            /*11*/   1, // "Handles grammars with left recursion"
            /*12*/   0, // "Recursive descent parsing"
            /*13*/   3, // "Start symbol"
            /*14*/   2, // "LL(1) Grammar"
            /*15*/   2  // "Grammar transformation"
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |         HARD         |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson1_Hard_Questions() {
        return e_Module_8.get_PostTest_Hard_Questions(
                post_test_lesson_1_questions_hard,
                post_test_lesson_1_answers_hard,
                e_Question.Difficulty.HARD);
    }

    public static final String[] post_test_lesson_1_questions_hard = {
            /*1*/    "What is the process of analyzing a string of symbols to determine its grammatical structure?",
            /*2*/    "What type of parsing builds the parse tree from the root symbol down to the input?",
            /*3*/    "What is the technique of top-down parsing that recursively applies grammar rules?",
            /*4*/    "What parsing technique uses one lookahead symbol to predict the correct production rule?",
            /*5*/    "What is the grammar called that parsing typically analyzes?",
            /*6*/    "Which parsing method constructs the parse tree from the input back to the start symbol?",
            /*7*/    "What is the term for rewriting a grammar to remove left recursion?",
            /*8*/    "Which table is constructed in LL(1) parsing to guide decisions based on the current input symbol?",
            /*9*/    "What is the name of the symbol from which parsing starts in top-down parsing?",
            /*10*/   "What method of parsing works without backtracking?",
            /*11*/   "What is the name of the method that involves reading input from left to right and producing leftmost derivation?",
            /*12*/   "What does the '1' in LL(1) stand for?",
            /*13*/   "Which parsing method does not handle left-recursive grammars?",
            /*14*/   "What type of grammar is required for LL(1) parsing to work efficiently?",
            /*15*/   "What term is used for a grammar rule that refers to itself on the left side of a production?"
    };

    public static final String[] post_test_lesson_1_answers_hard = {
            /*1*/    "parsing",
            /*2*/    "top-down parsing",
            /*3*/    "recursive descent parsing",
            /*4*/    "LL(1) parsing",
            /*5*/    "context-free grammar",
            /*6*/    "bottom-up parsing",
            /*7*/    "grammar transformation",
            /*8*/    "parsing table",
            /*9*/    "start symbol",
            /*10*/   "LL(1) parsing",
            /*11*/   "LL(1) parsing",
            /*12*/   "one lookahead symbol",
            /*13*/   "recursive descent parsing",
            /*14*/   "LL(1) grammar",
            /*15*/   "left recursion"
    };
}