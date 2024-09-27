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
                pre_test_lesson_3_questions,
                pre_test_lesson_3_choices,
                pre_test_lesson_3_answers
        );
    }

    public static final String[] pre_test_lesson_3_questions = {
            /*1*/    "Which component of a parse tree indicates the rules applied during a derivation?",
            /*2*/    "What do the branches of a parse tree represent?",
            /*3*/    "How is ambiguity in a grammar identified through a parse tree?",
            /*4*/    "What aspect of a parse tree helps in detecting syntactic errors in a program?",
            /*5*/    "In what way does a parse tree assist in the code generation phase of a compiler?",
            /*6*/    "What is the significance of the start symbol in both derivations and parse trees?",
            /*7*/    "What is the primary use of a context-free grammar (CFG) in relation to parse trees?",
            /*8*/    "In a leftmost derivation, how does the parser proceed through the parse tree?",
            /*9*/    "What does an edge in a parse tree indicate?",
            /*10*/   "What happens to a string during the derivation process in relation to parse trees?",
            /*11*/   "How does a rightmost derivation differ from a leftmost derivation in terms of parse trees?",
            /*12*/   "What is a recursive derivation in the context of parse trees?",
            /*13*/   "How does a production rule contribute to the structure of a parse tree?",
            /*14*/   "Why is it important for a parse tree to accurately represent the syntactic structure of a program?",
            /*15*/   "In a compiler, what is the function of the syntax analysis phase, which utilizes parse trees?"
    };

    public static final String[][] pre_test_lesson_3_choices = {
            /*1*/    {"Leaf nodes", "Internal nodes", "Root node", "Edges"},
            /*2*/    {"The connection between non-terminal symbols and their derivations.", "The sequence of operations in code execution.", "The relationship between different terminals.", "The arrangement of production rules."},
            /*3*/    {"When the parse tree has more than one root node.", "When the tree has multiple valid structures for a single input string.", "When there are no terminal symbols in the tree.", "When the tree contains recursive elements."},
            /*4*/    {"The presence of leaf nodes.", "The internal structure of the tree.", "The production rules used in the tree.", "The root node's relationship to other nodes."},
            /*5*/    {"By representing the structure of grammar rules.", "By directly converting non-terminals into machine code.", "By organizing the hierarchy of the program’s statements.", "By creating new syntactical rules."},
            /*6*/    {"It is the first production rule applied in a derivation.", "It is always a terminal symbol in the parse tree.", "It defines the final output of the parse tree.", "It cannot be expanded further."},
            /*7*/    {"To generate terminals directly.", "To define the hierarchical structure of a string.", "To optimize the syntax of a program.", "To establish the order of token recognition."},
            /*8*/    {"By converting terminals into non-terminals.", "By expanding the rightmost non-terminal.", "By replacing the leftmost non-terminal first.", "By processing all terminals simultaneously."},
            /*9*/    {"The application of a production rule.", "The final state of the derivation.", "A leaf node containing a terminal symbol.", "The termination of the parse tree."},
            /*10*/   {"It is broken down into non-terminal symbols.", "It is transformed into a terminal string step by step.", "It is rearranged into a new order of symbols.", "It remains unchanged throughout the process."},
            /*11*/   {"It replaces the rightmost non-terminal in each step.", "It always results in ambiguous parse trees.", "It applies all production rules simultaneously.", "It leads to a different root node in the parse tree."},
            /*12*/   {"A process where the same non-terminal symbol is repeatedly expanded.", "A derivation that involves terminals and non-terminals equally.", "A process that creates new syntactic rules dynamically.", "A derivation that skips over terminal symbols."},
            /*13*/   {"It defines how non-terminal symbols are expanded.", "It is only used for terminal symbols in the tree.", "It dictates the final form of the parse tree.", "It prevents ambiguity in the grammar."},
            /*14*/   {"It helps reduce the complexity of the program's source code.", "It ensures that the program can be translated into machine code correctly.", "It improves the execution speed of the compiled program.", "It organizes the grammar into simpler terms for readability."},
            /*15*/   {"To generate the final executable code.", "To check the semantic meaning of the code.", "To analyze the program's structure according to grammar rules.", "To manage memory usage during program execution."}
    };

    public static final int[] pre_test_lesson_3_answers = {
            /*1*/    1, // Internal nodes
            /*2*/    0, // The connection between non-terminal symbols and their derivations.
            /*3*/    1, // When the tree has multiple valid structures for a single input string.
            /*4*/    1, // The internal structure of the tree.
            /*5*/    2, // By organizing the hierarchy of the program’s statements.
            /*6*/    0, // It is the first production rule applied in a derivation.
            /*7*/    1, // To define the hierarchical structure of a string.
            /*8*/    2, // By replacing the leftmost non-terminal first.
            /*9*/    0, // The application of a production rule.
            /*10*/   1, // It is transformed into a terminal string step by step.
            /*11*/   0, // It replaces the rightmost non-terminal in each step.
            /*12*/   0, // A process where the same non-terminal symbol is repeatedly expanded.
            /*13*/   0, // It defines how non-terminal symbols are expanded.
            /*14*/   1, // It ensures that the program can be translated into machine code correctly.
            /*15*/   2  // To analyze the program's structure according to grammar rules.
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |          EASY        |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson3_Easy_Questions() {
        return e_Module_5.get_PostTest_EasyMedium_Questions(
                post_test_lesson_3_questions_easy,
                post_test_lesson_3_choices_easy,
                post_test_lesson_3_answers_easy,
                e_Question.Difficulty.EASY
        );
    }

    public static final String[] post_test_lesson_3_questions_easy = {
            /*1*/    "Leftmost derivation replaces the leftmost non-terminal symbol at each step in the process.",
            /*2*/    "In rightmost derivation, non-terminal symbols are replaced from the rightmost to the left.",
            /*3*/    "Both leftmost and rightmost derivations can be used to generate the same final string from a grammar.",
            /*4*/    "In derivation, the goal is to replace non-terminal symbols with terminal symbols to form a valid string in the language.",
            /*5*/    "Leftmost and rightmost derivations are only useful in context-sensitive grammars.",
            /*6*/    "The replacement order in leftmost derivation starts from the leftmost part of the string and works towards the right.",
            /*7*/    "The order of derivation, whether leftmost or rightmost, determines how efficiently a grammar produces strings.",
            /*8*/    "In a valid grammar, the derivation process may involve replacing both terminal and non-terminal symbols.",
            /*9*/    "A derivation ends when all non-terminal symbols have been replaced by terminal symbols, completing the string.",
            /*10*/   "In rightmost derivation, non-terminal symbols are replaced from the leftmost side of the string.",
            /*11*/   "Derivations in a context-free grammar must always begin with a terminal symbol.",
            /*12*/   "Both leftmost and rightmost derivations lead to valid strings, provided the grammar is unambiguous.",
            /*13*/   "In derivations, the final string can contain non-terminal symbols as long as it is in the grammar's language.",
            /*14*/   "In a leftmost derivation, non-terminal symbols are always replaced from right to left.",
            /*15*/   "A valid grammar requires that each derivation begins with the replacement of terminal symbols."
    };

    public static final String[][] post_test_lesson_3_choices_easy = {
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

    public static final int[] post_test_lesson_3_answers_easy = {
            /*1*/    0, // True
            /*2*/    0, // True
            /*3*/    0, // True
            /*4*/    0, // True
            /*5*/    1, // False
            /*6*/    0, // True
            /*7*/    1, // False
            /*8*/    0, // True
            /*9*/    0, // True
            /*10*/   1, // False
            /*11*/   1, // False
            /*12*/   0, // True
            /*13*/   1, // False
            /*14*/   1, // False
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
                post_test_lesson_3_questions_medium,
                post_test_lesson_3_choices_medium,
                post_test_lesson_3_answers_medium,
                e_Question.Difficulty.MEDIUM
        );
    }

    public static final String[] post_test_lesson_3_questions_medium = {
            /*1*/    "What is the main characteristic of leftmost derivation?",
            /*2*/    "In a rightmost derivation, what symbol is replaced first?",
            /*3*/    "Which of the following is an accurate description of the derivation process?",
            /*4*/    "Which of the following grammars allows for both leftmost and rightmost derivations?",
            /*5*/    "In the process of derivation, what are non-terminal symbols replaced with?",
            /*6*/    "Which of the following describes a leftmost derivation?",
            /*7*/    "What does the derivation process in grammars aim to produce?",
            /*8*/    "What type of derivation begins by replacing the rightmost non-terminal symbol?",
            /*9*/    "What is replaced during a derivation process in a context-free grammar?",
            /*10*/   "Which process involves creating valid strings from non-terminal symbols?",
            /*11*/   "What is the key difference between leftmost and rightmost derivations?",
            /*12*/   "Which of the following symbols are the focus of replacement during derivations?",
            /*13*/   "In what type of derivation is the leftmost non-terminal symbol replaced first?",
            /*14*/   "Which type of grammar uses both leftmost and rightmost derivations?",
            /*15*/   "What is the goal of the derivation process in formal grammars?"
    };

    public static final String[][] post_test_lesson_3_choices_medium = {
            /*1*/    {"Replacing the leftmost non-terminal symbol first", "Replacing the rightmost terminal symbol first", "Replacing all terminal symbols simultaneously", "Ignoring non-terminal symbols"},
            /*2*/    {"Leftmost terminal symbol", "Rightmost non-terminal symbol", "Rightmost terminal symbol", "Leftmost non-terminal symbol"},
            /*3*/    {"Replacing terminal symbols with non-terminal symbols", "Replacing non-terminal symbols with terminal symbols", "Replacing terminal symbols with terminal symbols", "Replacing non-terminal symbols with themselves"},
            /*4*/    {"Context-free grammar", "Context-sensitive grammar", "Deterministic grammar", "Terminal grammar"},
            /*5*/    {"Other non-terminal symbols", "Terminal symbols", "A combination of both", "Numbers"},
            /*6*/    {"It replaces the rightmost non-terminal symbol first", "It replaces the leftmost non-terminal symbol first", "It starts with terminal symbols", "It only replaces terminal symbols"},
            /*7*/    {"A list of non-terminal symbols", "A valid string in the language", "Only terminal symbols", "A parsing tree"},
            /*8*/    {"Leftmost derivation", "Rightmost derivation", "Random derivation", "None of the above"},
            /*9*/    {"Terminal symbols", "Non-terminal symbols", "Leftmost terminals", "Numbers and symbols"},
            /*10*/   {"Tokenization", "Parsing", "Derivation", "Compilation"},
            /*11*/   {"The order in which non-terminal symbols are replaced", "The number of non-terminal symbols used", "Leftmost derivations only replace terminal symbols", "Rightmost derivations require no replacements"},
            /*12*/   {"Only terminal symbols", "Both terminal and non-terminal symbols", "Only non-terminal symbols", "Any symbols found in the grammar"},
            /*13*/   {"Rightmost derivation", "Leftmost derivation", "Deterministic derivation", "No derivation"},
            /*14*/   {"Context-free grammar", "Deterministic grammar", "Unstructured grammar", "Context-sensitive grammar"},
            /*15*/   {"To replace terminal symbols with non-terminal symbols", "To generate a valid string by replacing non-terminal symbols", "To determine the validity of terminal symbols", "To eliminate all non-terminal symbols"}
    };

    public static final int[] post_test_lesson_3_answers_medium = {
            /*1*/    0, // Replacing the leftmost non-terminal symbol first
            /*2*/    1, // Rightmost non-terminal symbol
            /*3*/    1, // Replacing non-terminal symbols with terminal symbols
            /*4*/    0, // Context-free grammar
            /*5*/    1, // Terminal symbols
            /*6*/    1, // It replaces the leftmost non-terminal symbol first
            /*7*/    1, // A valid string in the language
            /*8*/    1, // Rightmost derivation
            /*9*/    1, // Non-terminal symbols
            /*10*/   2, // Derivation
            /*11*/   0, // The order in which non-terminal symbols are replaced
            /*12*/   1, // Both terminal and non-terminal symbols
            /*13*/   1, // Leftmost derivation
            /*14*/   0, // Context-free grammar
            /*15*/   1  // To generate a valid string by replacing non-terminal symbols
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |         HARD         |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson3_Hard_Questions() {
        return e_Module_5.get_PostTest_Hard_Questions(
                post_test_lesson_3_questions_hard,
                post_test_lesson_3_answers_hard,
                e_Question.Difficulty.HARD
        );
    }

    public static final String[] post_test_lesson_3_questions_hard = {
            /*1*/    "What process produces a string from non-terminal symbols in a grammar?",
            /*2*/    "What term describes replacing the leftmost non-terminal first?",
            /*3*/    "Which process replaces the rightmost non-terminal first in a string?",
            /*4*/    "What are the symbols that get replaced during derivation?",
            /*5*/    "What method transforms non-terminals into terminals?",
            /*6*/    "What process leads to a valid string in a language?",
            /*7*/    "Which type of derivation replaces symbols from right to left?",
            /*8*/    "Which part of a string is replaced first in leftmost derivation?",
            /*9*/    "What is the goal of derivation in formal languages?",
            /*10*/   "What symbols remain after derivation completes?",
            /*11*/   "What concept involves following production rules to generate a string?",
            /*12*/   "Which symbol is replaced first in rightmost derivation?",
            /*13*/   "What term refers to the rules applied to replace non-terminals?",
            /*14*/   "What kind of grammar supports leftmost and rightmost derivations?",
            /*15*/   "What process constructs valid strings from grammar rules?"
    };

    public static final String[] post_test_lesson_3_answers_hard = {
            /*1*/    "string derivation",
            /*2*/    "leftmost derivation",
            /*3*/    "rightmost derivation",
            /*4*/    "non-terminal symbols",
            /*5*/    "symbol replacement",
            /*6*/    "string generation",
            /*7*/    "rightmost derivation",
            /*8*/    "leftmost symbol",
            /*9*/    "string creation",
            /*10*/   "terminal symbols",
            /*11*/   "grammar derivation",
            /*12*/   "rightmost symbol",
            /*13*/   "production rules",
            /*14*/   "context-free grammar",
            /*15*/   "string derivation"
    };

}