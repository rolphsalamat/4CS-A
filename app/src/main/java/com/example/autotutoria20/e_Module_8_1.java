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
            /*1*/    "What is the main purpose of converting a CFG into normal forms like CNF or GNF?",
            /*2*/    "Which component in a CFG represents the non-terminal symbols?",
            /*3*/    "When converting a CFG to CNF, what is done to unit productions?",
            /*4*/    "What is the maximum number of non-terminals allowed on the right-hand side of a production rule in CNF?",
            /*5*/    "In GNF, which of the following is true for a production rule?",
            /*6*/    "What must be eliminated from a CFG before converting it to CNF?",
            /*7*/    "Why is CNF particularly useful for certain parsing algorithms?",
            /*8*/    "Which parsing algorithm benefits directly from the use of CNF?",
            /*9*/    "In CFG, what is the role of terminal symbols (T)?",
            /*10*/   "What is the primary challenge addressed by Greibach Normal Form (GNF)?",
            /*11*/   "What is the significance of the start symbol in a CFG?",
            /*12*/   "What does the conversion from CFG to CNF generally involve regarding null productions?",
            /*13*/   "What follows the initial terminal symbol in GNF production rules?",
            /*14*/   "Why is left recursion eliminated in CFG when converting to GNF?",
            /*15*/   "Which step is taken first when converting a CFG to CNF if the start symbol appears on the right-hand side of a production?"
    };

    public static final String[][] pre_test_lesson_1_choices = {
            /*1*/    {"To make CFG more ambiguous", "To simplify CFG for parsing and manipulation", "To increase the complexity of grammar", "To add more production rules"},
            /*2*/    {"G", "T", "P", "V"},
            /*3*/    {"They are left unchanged", "They are replaced with terminals", "They are removed or rewritten", "They are converted into null productions"},
            /*4*/    {"One", "Two", "Three", "Unlimited"},
            /*5*/    {"It starts with two non-terminals", "It starts with a terminal followed by non-terminals", "It allows ε-productions for all non-terminals", "It begins with null production"},
            /*6*/    {"Terminals", "Unit and null productions", "Start symbols", "Non-terminal symbols"},
            /*7*/    {"It allows recursion in grammar", "It standardizes production rules for easier analysis", "It creates more complex parse trees", "It eliminates the need for non-terminals"},
            /*8*/    {"CYK algorithm", "Depth-First Search algorithm", "Bellman-Ford algorithm", "Topological Sort algorithm"},
            /*9*/    {"They represent rules to replace non-terminals", "They represent the output symbols of the grammar", "They help eliminate ambiguity", "They are used to produce non-terminal symbols"},
            /*10*/   {"Removing null productions", "Starting production rules with terminal symbols", "Eliminating ambiguity in CFGs", "Reducing the number of terminals"},
            /*11*/   {"It is used only for complex grammars", "It generates the initial production of strings", "It represents null productions", "It cannot be used on the right-hand side of any production"},
            /*12*/   {"Adding more non-terminals", "Rewriting or eliminating them", "Simplifying terminals", "Introducing new start symbols"},
            /*13*/   {"A null symbol", "Any number of non-terminals", "Two terminals", "A new production rule"},
            /*14*/   {"To increase the complexity of the grammar", "To ensure terminals appear first in production rules", "To allow more non-terminal derivations", "To simplify null productions"},
            /*15*/   {"Eliminate recursion", "Introduce a new start symbol", "Replace terminals with non-terminals", "Remove unit productions"}
    };

    public static final int[] pre_test_lesson_1_answers = {
            /*1*/    1,  // "To simplify CFG for parsing and manipulation"
            /*2*/    3,  // "V"
            /*3*/    2,  // "They are removed or rewritten"
            /*4*/    1,  // "Two"
            /*5*/    1,  // "It starts with a terminal followed by non-terminals"
            /*6*/    1,  // "Unit and null productions"
            /*7*/    1,  // "It standardizes production rules for easier analysis"
            /*8*/    0,  // "CYK algorithm"
            /*9*/    1,  // "They represent the output symbols of the grammar"
            /*10*/   1,  // "Starting production rules with terminal symbols"
            /*11*/   1,  // "It generates the initial production of strings"
            /*12*/   1,  // "Rewriting or eliminating them"
            /*13*/   1,  // "Any number of non-terminals"
            /*14*/   1,  // "To ensure terminals appear first in production rules"
            /*15*/   1   // "Introduce a new start symbol"
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
            /*1*/    "Parsing helps in determining the grammatical structure of a string of symbols based on a given grammar.",
            /*2*/    "Top-down parsing builds the parse tree from the input and works up to the start symbol.",
            /*3*/    "Recursive descent parsers can handle grammars with left recursion without modification.",
            /*4*/    "LL(1) parsing requires two lookahead symbols to determine the correct production rule.",
            /*5*/    "Recursive descent parsing is simple to implement but requires backtracking.",
            /*6*/    "LL(1) parsing is predictive and non-backtracking.",
            /*7*/    "Top-down parsers construct the parse tree from leaves to the root.",
            /*8*/    "Recursive descent parsers work only for grammars without left recursion.",
            /*9*/    "In LL(1) parsing, lookahead is used to predict the next production rule.",
            /*10*/   "Recursive descent parsers do not need to rewrite grammars to handle left recursion.",
            /*11*/   "Top-down parsing can handle both left and right recursion without modification.",
            /*12*/   "LL(1) parsing reads input from right to left.",
            /*13*/   "Backtracking is required in LL(1) parsers.",
            /*14*/   "LL(1) parsers use a predictive table for decision-making during parsing.",
            /*15*/   "Recursive descent parsers can use predictive parsing for certain grammars."
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
            /*4*/    1,  // False
            /*5*/    0,  // True
            /*6*/    0,  // True
            /*7*/    1,  // False
            /*8*/    0,  // True
            /*9*/    0,  // True
            /*10*/   1,  // False
            /*11*/   1,  // False
            /*12*/   1,  // False
            /*13*/   1,  // False
            /*14*/   0,  // True
            /*15*/   0   // True
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
            /*1*/    "Which type of parsing constructs the parse tree from the root to the leaves?",
            /*2*/    "Which of the following is a characteristic of recursive descent parsing?",
            /*3*/    "What does LL(1) stand for in LL(1) parsing?",
            /*4*/    "Which of the following is a correct production rule in recursive descent parsing?",
            /*5*/    "What is the first step in recursive descent parsing?",
            /*6*/    "Which parsing technique builds the parse tree from the input and works up to the start symbol?",
            /*7*/    "Which of the following is a type of top-down parsing?",
            /*8*/    "In LL(1) parsing, how many symbols are looked ahead to determine the production rule?",
            /*9*/    "Which of the following is NOT a characteristic of LL(1) parsing?",
            /*10*/   "Which grammar rule must be rewritten to avoid left recursion?",
            /*11*/   "Which parsing technique is most efficient for grammars that do not require backtracking?",
            /*12*/   "Which of the following symbols is a terminal in the example grammar?",
            /*13*/   "What is the purpose of the match function in recursive descent parsing?",
            /*14*/   "Which of the following is a benefit of LL(1) parsing?",
            /*15*/   "In the CFG example for LL(1) parsing, which rule does 'B' derive into?"
    };

    public static final String[][] post_test_lesson_1_choices_medium = {
            /*1*/    {"Bottom-up parsing", "Recursive parsing", "Top-down parsing", "None of the above"},
            /*2*/    {"It uses backtracking", "It cannot handle grammars with left recursion", "It reads input from right to left", "It requires an LL(1) table"},
            /*3*/    {"Left-to-Left derivation with 1 symbol lookahead", "Leftmost derivation with 1 symbol lookahead", "Largest lookahead with Left recursion", "Left-Right derivation with 1 symbol lookahead"},
            /*4*/    {"E→ E + T", "F→ T + E", "T→ E F'", "F→ ( E ) | id"},
            /*5*/    {"Implement parsing functions", "Define grammar rules as functions", "Rewrite the grammar", "Verify token matches"},
            /*6*/    {"LL(1) Parsing", "Bottom-up Parsing", "Top-down Parsing", "Predictive Parsing"},
            /*7*/    {"LR Parsing", "LL(1) Parsing", "CYK Parsing", "None of the above"},
            /*8*/    {"0", "1", "2", "3"},
            /*9*/    {"It is predictive", "It uses backtracking", "It reads input from left to right", "It requires an LL(1) grammar"},
            /*10*/   {"E→ T E'", "E→ E + T", "T→ T F'", "F→ ( E ) | id"},
            /*11*/   {"Bottom-up Parsing", "Recursive Descent Parsing", "LL(1) Parsing", "None of the above"},
            /*12*/   {"T", "E", "id", "E'"},
            /*13*/   {"To define grammar rules as functions", "To verify token matches", "To look ahead at input symbols", "To backtrack when necessary"},
            /*14*/   {"It is more complex to implement", "It handles left-recursive grammars", "It does not involve backtracking", "It requires multiple lookahead symbols"},
            /*15*/   {"B → a", "B → ε", "B → b", "B → b | ε"}
    };

    public static final int[] post_test_lesson_1_answers_medium = {
            /*1*/    2,  // "Top-down parsing"
            /*2*/    0,  // "It uses backtracking"
            /*3*/    1,  // "Leftmost derivation with 1 symbol lookahead"
            /*4*/    3,  // "F→ ( E ) | id"
            /*5*/    1,  // "Define grammar rules as functions"
            /*6*/    2,  // "Top-down Parsing"
            /*7*/    1,  // "LL(1) Parsing"
            /*8*/    1,  // "1"
            /*9*/    1,  // "It uses backtracking"
            /*10*/   1,  // "E→ E + T"
            /*11*/   2,  // "LL(1) Parsing"
            /*12*/   2,  // "id"
            /*13*/   1,  // "To verify token matches"
            /*14*/   2,  // "It does not involve backtracking"
            /*15*/   3   // "B → b | ε"
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
                e_Question.Difficulty.HARD
        );
    }

    public static final String[] post_test_lesson_1_questions_hard = {
            /*1*/    "Which parsing method handles backtracking?",
            /*2*/    "What process builds syntax trees?",
            /*3*/    "Which symbol initiates parsing?",
            /*4*/    "What identifies the current input symbol?",
            /*5*/    "What does LL(1) parsing avoid?",
            /*6*/    "Which parsing table helps decision making?",
            /*7*/    "What rule guides grammar expansions?",
            /*8*/    "What eliminates left recursion?",
            /*9*/    "What component analyzes grammar?",
            /*10*/   "What grammar causes backtracking issues?",
            /*11*/   "What technique predicts input tokens?",
            /*12*/   "What structure results from parsing?",
            /*13*/   "What parsing method reads left-to-right?",
            /*14*/   "Which method constructs derivations?",
            /*15*/   "What parses from root downward?"
    };

    public static final String[] post_test_lesson_1_answers_hard = {
            /*1*/    "recursive parsing",
            /*2*/    "parse tree",
            /*3*/    "start symbol",
            /*4*/    "lookahead symbol",
            /*5*/    "backtracking error",
            /*6*/    "LL(1) table",
            /*7*/    "production rule",
            /*8*/    "grammar rewriting",
            /*9*/    "parsing function",
            /*10*/   "left-recursive grammar",
            /*11*/   "predictive parsing",
            /*12*/   "parse tree",
            /*13*/   "LL(1) parsing",
            /*14*/   "leftmost derivation",
            /*15*/   "top down"
    };

}