package com.example.autotutoria20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class e_Module_7_1 {

    /*
    +----------------------+
    |       PRE-TEST       |
    +----------------------+
    */

    public static e_Question[] get_PreTest_Questions() {
        return e_Module_7.getPreTestQuestions(
                pre_test_lesson_1_questions,
                pre_test_lesson_1_choices,
                pre_test_lesson_1_answers
        );
    }

    public static final String[] pre_test_lesson_1_questions = {
            /*1*/    "What is the key challenge of inherent ambiguity in grammar?",
            /*2*/    "Inherent ambiguity most often appears in which type of languages?",
            /*3*/    "What is a common symptom of inherent ambiguity in a grammar?",
            /*4*/    "Which strategy helps reduce the risk of ambiguity in grammar?",
            /*5*/    "What does inherent ambiguity imply about the grammar?",
            /*6*/    "Why is ambiguity in grammar problematic in programming languages?",
            /*7*/    "In which situation can ambiguity be beneficial or intentional?",
            /*8*/    "What is a possible consequence of not addressing ambiguity in technical documentation?",
            /*9*/    "Which method helps in detecting ambiguity in grammar?",
            /*10*/   "How does left recursion contribute to ambiguity in grammar?",
            /*11*/   "What does lexical ambiguity involve?",
            /*12*/   "Which type of ambiguity arises from the arrangement of words in a sentence?",
            /*13*/   "Why is inherent ambiguity difficult to eliminate in natural languages?",
            /*14*/   "What does pragmatic ambiguity depend on?",
            /*15*/   "Which aspect of inherent ambiguity is relevant in legal contracts?"
    };

    public static final String[][] pre_test_lesson_1_choices = {
            /*1*/    {"It improves clarity in sentences.", "It allows for multiple valid interpretations of the same structure.", "It simplifies the grammar rules.", "It ensures a single correct interpretation."},
            /*2*/    {"Programming languages", "Natural languages", "Machine code", "Regular languages"},
            /*3*/    {"The presence of recursion", "Multiple derivation trees for the same string", "An increase in non-terminal symbols", "A reduction in parse tree complexity"},
            /*4*/    {"Adding more non-terminal symbols", "Simplifying the production rules", "Introducing right recursion", "Allowing leftmost derivations"},
            /*5*/    {"The grammar can be easily modified to remove ambiguity.", "The ambiguity cannot be resolved, regardless of changes.", "The grammar allows multiple leftmost derivations.", "The grammar only affects natural languages."},
            /*6*/    {"It can lead to multiple valid parse trees, complicating syntax analysis.", "It makes the code generation process faster.", "It ensures unique interpretations of code.", "It simplifies the debugging process."},
            /*7*/    {"In legal writing", "In poetic or creative writing", "In programming languages", "In technical documentation"},
            /*8*/    {"Improved accuracy", "Faster comprehension", "Misinterpretation of key information", "Increased efficiency in editing"},
            /*9*/    {"Constructing multiple parse trees for different derivations", "Increasing the number of terminal symbols", "Using left recursion to resolve conflicts", "Ignoring recursive elements in grammar"},
            /*10*/   {"It ensures that terminal symbols appear first.", "It creates overlapping derivations for the same string.", "It eliminates multiple interpretations of the same string.", "It simplifies the parsing process."},
            /*11*/   {"Ambiguity due to the structure of a sentence", "Ambiguity resulting from the multiple meanings of words", "Ambiguity that arises in parsing algorithms", "Ambiguity due to non-terminal symbols"},
            /*12*/   {"Syntactic ambiguity", "Lexical ambiguity", "Pragmatic ambiguity", "Structural ambiguity"},
            /*13*/   {"Natural languages use a strict set of grammar rules.", "The complexity of meaning in natural languages allows for multiple interpretations.", "Natural languages lack the flexibility for ambiguity.", "Natural languages rely solely on syntax."},
            /*14*/   {"The syntax of the sentence", "The broader context of the statement", "The specific lexical choices in a sentence", "The lack of terminal symbols in grammar"},
            /*15*/   {"It helps in defining clear terms.", "It can lead to multiple interpretations, potentially causing disputes.", "It simplifies contract negotiations.", "It ensures all parties agree on a single meaning."}
    };

    public static final int[] pre_test_lesson_1_answers = {
            /*1*/    1,  // 'It allows for multiple valid interpretations of the same structure.'
            /*2*/    1,  // 'Natural languages'
            /*3*/    1,  // 'Multiple derivation trees for the same string'
            /*4*/    1,  // 'Simplifying the production rules'
            /*5*/    1,  // 'The ambiguity cannot be resolved, regardless of changes.'
            /*6*/    0,  // 'It can lead to multiple valid parse trees, complicating syntax analysis.'
            /*7*/    1,  // 'In poetic or creative writing'
            /*8*/    2,  // 'Misinterpretation of key information'
            /*9*/    0,  // 'Constructing multiple parse trees for different derivations'
            /*10*/   1,  // 'It creates overlapping derivations for the same string.'
            /*11*/   1,  // 'Ambiguity resulting from the multiple meanings of words'
            /*12*/   0,  // 'Syntactic ambiguity'
            /*13*/   1,  // 'The complexity of meaning in natural languages allows for multiple interpretations.'
            /*14*/   1,  // 'The broader context of the statement'
            /*15*/   1   // 'It can lead to multiple interpretations, potentially causing disputes.'
    };

    /*
    +----------------------+
    |   POST-TEST EASY     |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson1_Easy_Questions() {
        return e_Module_7.get_PostTest_EasyMedium_Questions(
                post_test_lesson_1_questions_easy,
                post_test_lesson_1_choices_easy,
                post_test_lesson_1_answers_easy,
                e_Question.Difficulty.EASY
        );
    }

    public static final String[] post_test_lesson_1_questions_easy = {
            /*1*/    "A Context-Free Grammar (CFG) consists of four components: G, T, V, and P.",
            /*2*/    "In Chomsky Normal Form (CNF), a production rule can have more than two non-terminals on the right-hand side.",
            /*3*/    "Greibach Normal Form (GNF) allows productions to start with non-terminal symbols.",
            /*4*/    "One of the purposes of normal forms is to simplify the analysis and manipulation of CFGs.",
            /*5*/    "In CNF, unit productions are allowed as long as they simplify the grammar.",
            /*6*/    "The process of converting a CFG to CNF involves eliminating null productions.",
            /*7*/    "GNF ensures that every production starts with a terminal followed by any number of non-terminals.",
            /*8*/    "In CNF, a rule with the form A → BCD is valid.",
            /*9*/    "Eliminating left recursion is part of converting a CFG to GNF.",
            /*10*/   "Chomsky Normal Form allows epsilon (ε) productions only for non-start symbols.",
            /*11*/   "In CFGs, terminals are represented by uppercase letters.",
            /*12*/   "Both CNF and GNF contribute to making CFGs easier to parse.",
            /*13*/   "The start symbol in a CFG is always a terminal symbol.",
            /*14*/   "Removing null productions is a necessary step in CNF conversion.",
            /*15*/   "In GNF, epsilon (ε) productions are allowed for any non-terminal."
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
            /*2*/    1,  // False
            /*3*/    1,  // False
            /*4*/    0,  // True
            /*5*/    1,  // False
            /*6*/    0,  // True
            /*7*/    0,  // True
            /*8*/    1,  // False
            /*9*/    0,  // True
            /*10*/   0,  // True
            /*11*/   1,  // False
            /*12*/   0,  // True
            /*13*/   1,  // False
            /*14*/   0,  // True
            /*15*/   1   // False
    };

    /*
    +----------------------+
    |  POST-TEST MEDIUM    |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson1_Medium_Questions() {
        return e_Module_7.get_PostTest_EasyMedium_Questions(
                post_test_lesson_1_questions_medium,
                post_test_lesson_1_choices_medium,
                post_test_lesson_1_answers_medium,
                e_Question.Difficulty.MEDIUM
        );
    }

    public static final String[] post_test_lesson_1_questions_medium = {
            /*1*/    "What is the purpose of normal forms in CFG?",
            /*2*/    "Which of the following is NOT a component of a CFG?",
            /*3*/    "What does Chomsky Normal Form (CNF) restrict?",
            /*4*/    "What is an important feature of Greibach Normal Form (GNF)?",
            /*5*/    "Which is the first step in converting a CFG to CNF?",
            /*6*/    "In CNF, what happens when a terminal is mixed with non-terminals on the right-hand side of a production?",
            /*7*/    "In GNF, what happens to left recursion?",
            /*8*/    "Which algorithm benefits from using CNF for parsing?",
            /*9*/    "In CNF, how many non-terminals are allowed on the right-hand side of a production?",
            /*10*/   "What does the 'G' in CFG stand for?",
            /*11*/   "What must be done to a CFG before converting it to GNF?",
            /*12*/   "In CNF, which production rule is allowed?",
            /*13*/   "What is removed during the CNF conversion process?",
            /*14*/   "Which of the following describes a unit production?",
            /*15*/   "Which normal form ensures that all productions start with a terminal?"
    };

    public static final String[][] post_test_lesson_1_choices_medium = {
            /*1*/    {"To complicate CFG structures", "To simplify CFGs for easier parsing", "To eliminate all productions", "To create infinite recursion"},
            /*2*/    {"Grammar (G)", "Terminal symbols (T)", "Variables (V)", "Production chains (C)"},
            /*3*/    {"Productions with only non-terminals", "Productions with one terminal or exactly two non-terminals", "Productions with an arbitrary number of terminals", "Productions with epsilon (ε)"},
            /*4*/    {"Productions start with two non-terminals", "Productions start with a terminal symbol", "Epsilon productions are allowed everywhere", "Only unit productions are allowed"},
            /*5*/    {"Removing null productions", "Introducing a new start symbol", "Replacing non-terminal productions", "Removing left recursion"},
            /*6*/    {"It is left as is", "It is replaced by another production", "The production is eliminated", "The terminal is turned into a non-terminal"},
            /*7*/    {"It is ignored", "It is converted into a right recursion", "It is eliminated", "It is expanded further"},
            /*8*/    {"DFS algorithm", "CYK algorithm", "Bellman-Ford algorithm", "Topological Sort algorithm"},
            /*9*/    {"One", "Two", "Three", "Unlimited"},
            /*10*/   {"Grammar", "Group", "Generalization", "Generation"},
            /*11*/   {"Remove all null productions", "Convert it to CNF", "Add more terminals", "Introduce recursion"},
            /*12*/   {"A → BCD", "A → aB", "A → BC", "A → ε for all symbols"},
            /*13*/   {"Unit productions", "Terminal symbols", "Context-free rules", "Start symbols"},
            /*14*/   {"A → BC", "A → a", "A → B", "A → ε"},
            /*15*/   {"CNF", "GNF", "DNF", "ENF"}
    };

    public static final int[] post_test_lesson_1_answers_medium = {
            /*1*/    1, // "To simplify CFGs for easier parsing"
            /*2*/    3, // "Production chains (C)"
            /*3*/    1, // "Productions with one terminal or exactly two non-terminals"
            /*4*/    1, // "Productions start with a terminal symbol"
            /*5*/    0, // "Removing null productions"
            /*6*/    1, // "It is replaced by another production"
            /*7*/    2, // "It is eliminated"
            /*8*/    1, // "CYK algorithm"
            /*9*/    1, // "Two"
            /*10*/   0, // "Grammar"
            /*11*/   0, // "Remove all null productions"
            /*12*/   2, // "A → BC"
            /*13*/   0, // "Unit productions"
            /*14*/   2, // "A → B"
            /*15*/   1  // "GNF"
    };

    /*
    +----------------------+
    |  POST-TEST HARD      |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson1_Hard_Questions() {
        return e_Module_7.get_PostTest_Hard_Questions(
                post_test_lesson_1_questions_hard,
                post_test_lesson_1_answers_hard,
                e_Question.Difficulty.HARD
        );
    }

    public static final String[] post_test_lesson_1_questions_hard = {
            /*1*/    "What form simplifies the structure of a grammar by ensuring that each production has exactly two non-terminals or one terminal?",
            /*2*/    "Which normal form ensures that each production starts with a terminal symbol, making it useful for top-down parsing?",
            /*3*/    "In CNF, which symbol is allowed to produce the empty string (ε)?",
            /*4*/    "What type of productions must be eliminated to convert a CFG to Chomsky Normal Form (CNF)?",
            /*5*/    "What set in a CFG contains the symbols that represent variables and can be replaced by other symbols?",
            /*6*/    "In Greibach Normal Form (GNF), what must every production rule start with on the right-hand side?",
            /*7*/    "What is the purpose of removing unit productions when converting a CFG to CNF?",
            /*8*/    "Which set in CFG represents the final set of symbols typically denoted by lowercase letters?",
            /*9*/    "In GNF, what follows the initial terminal symbol in a production rule?",
            /*10*/   "What key process is used to derive strings in a CFG by replacing non-terminal symbols?",
            /*11*/   "Which theory in computer science benefits from the simplified structure of CNF for efficient parsing?",
            /*12*/   "When converting a CFG to CNF, what is done to production rules that contain terminals mixed with non-terminals?",
            /*13*/   "What is the goal of eliminating left recursion when converting a CFG to Greibach Normal Form (GNF)?",
            /*14*/   "What type of algorithm benefits from using Chomsky Normal Form due to its structured derivation steps?",
            /*15*/   "What is the first step when converting a CFG to CNF if the start symbol appears on the right-hand side of a production?"
    };

    public static final String[] post_test_lesson_1_answers_hard = {
            /*1*/    "chomsky normal form",
            /*2*/    "greibach normal form",
            /*3*/    "start symbol",
            /*4*/    "null productions",
            /*5*/    "non-terminal symbols",
            /*6*/    "terminal symbol symbols",
            /*7*/    "simplify grammar",
            /*8*/    "terminal symbols",
            /*9*/    "non-terminal symbols",
            /*10*/   "production rules",
            /*11*/   "automata theory",
            /*12*/   "replace terminals",
            /*13*/   "prevent recursion",
            /*14*/   "parsing algorithm",
            /*15*/   "introduce"
    };

}