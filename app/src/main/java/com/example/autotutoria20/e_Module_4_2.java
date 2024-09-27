package com.example.autotutoria20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class e_Module_4_2 {

    /*
    +----------------------+
    |       PRE-TEST       |
    |       LESSON 2       |
    +----------------------+
    */

    public static e_Question[] get_PreTest_Lesson2_Questions() {
        return e_Module_4.getPreTestQuestions(
                pre_test_lesson_2_questions,
                pre_test_lesson_2_choices,
                pre_test_lesson_2_answers
        );
    }

    public static final String[] pre_test_lesson_2_questions = {
            /*1*/    "What does a grammar consist of?",
            /*2*/    "What does CFG stand for?",
            /*3*/    "What is a CFG used for?",
            /*4*/    "In the CFG notation G = (V, Σ, R, S), what does V represent?",
            /*5*/    "What are terminals in a CFG?",
            /*6*/    "Which of the following is an example of a terminal in programming languages?",
            /*7*/    "What are non-terminal symbols in a CFG?",
            /*8*/    "What is the role of the start symbol in CFG?",
            /*9*/    "What does a production rule do in a CFG?",
            /*10*/   "Which of the following is an example of a CFG for balanced parentheses?",
            /*11*/   "In the CFG for arithmetic expressions, what does a 'Factor' represent?",
            /*12*/   "What is the CFG for strings of the form {anbn | n ≥ 0}?",
            /*13*/   "What is the significance of the production rule S → ε?",
            /*14*/   "What is a key component of CFG?",
            /*15*/   "What is the primary use of CFG in computer science?"
    };

    public static final String[][] pre_test_lesson_2_choices = {
            /*1*/    {"A set of rules to check if a string belongs to a language.", "A list of valid strings.", "A program that generates strings automatically.", "A set of terminals and non-terminals only."},
            /*2*/    {"Context-Free Group", "Context-Free Grammar", "Conditional Formatting Grammar", "Content-Free Grammar"},
            /*3*/    {"Describing the syntax of programming languages.", "Checking the spelling of words.", "Generating random strings.", "Translating languages."},
            /*4*/    {"The set of terminal symbols.", "The start symbol.", "The set of non-terminal symbols.", "The production rules."},
            /*5*/    {"Symbols that can be replaced by other symbols.", "The basic symbols from which strings are formed.", "Symbols that are not part of the grammar.", "Special characters used in programming languages."},
            /*6*/    {"Variable names", "Keywords, operators, or punctuation marks", "Expression", "Start symbol"},
            /*7*/    {"Symbols that represent sets of strings.", "Symbols that can never be replaced.", "Symbols used only in arithmetic expressions.", "The actual characters of the language."},
            /*8*/    {"It defines the end of the string.", "It represents the whole language.", "It creates the rules for terminals.", "It checks the validity of the string."},
            /*9*/    {"It checks if a string is valid.", "It defines how terminals and non-terminals are combined.", "It starts the derivation process.", "It creates new non-terminals."},
            /*10*/   {"S → (S) | SS | ε", "S → aSb | ε", "Expression → Expression + Term | Term", "Factor → ( Expression ) | number"},
            /*11*/   {"A number or an expression enclosed in parentheses.", "A string of terminals.", "A combination of terminals and non-terminals.", "A single operator."},
            /*12*/   {"S → (S) | SS | ε", "S → aSb | ε", "Expression → Expression + Term | Term", "Term → Term * Factor | Factor"},
            /*13*/   {"It generates an empty string.", "It creates an infinite string.", "It starts the derivation process.", "It replaces non-terminals with terminals."},
            /*14*/   {"Production rules", "Start symbol", "Terminals and non-terminals", "All of the above"},
            /*15*/   {"To create new programming languages.", "To describe syntax in programming languages and structured data formats.", "To perform mathematical calculations.", "To analyze data from strings."}
    };

    public static final int[] pre_test_lesson_2_answers = {
            /*1*/    0, // 'A set of rules to check if a string belongs to a language.'
            /*2*/    1, // 'Context-Free Grammar'
            /*3*/    0, // 'Describing the syntax of programming languages.'
            /*4*/    2, // 'The set of non-terminal symbols.'
            /*5*/    1, // 'The basic symbols from which strings are formed.'
            /*6*/    1, // 'Keywords, operators, or punctuation marks'
            /*7*/    0, // 'Symbols that represent sets of strings.'
            /*8*/    1, // 'It represents the whole language.'
            /*9*/    1, // 'It defines how terminals and non-terminals are combined.'
            /*10*/   0, // 'S → (S) | SS | ε'
            /*11*/   0, // 'A number or an expression enclosed in parentheses.'
            /*12*/   1, // 'S → aSb | ε'
            /*13*/   0, // 'It generates an empty string.'
            /*14*/   3, // 'All of the above'
            /*15*/   1  // 'To describe syntax in programming languages and structured data formats.'
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |          EASY        |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson2_Easy_Questions() {
        return e_Module_4.get_PostTest_EasyMedium_Questions(
                post_test_lesson_2_questions_easy,
                post_test_lesson_2_choices_easy,
                post_test_lesson_2_answers_easy,
                e_Question.Difficulty.EASY
        );
    }

    public static final String[] post_test_lesson_2_questions_easy = {
            /*1*/    "A context-free grammar (CFG) is used to define recursive patterns of strings.",
            /*2*/    "Terminals in a CFG are non-divisible and represent the smallest units of a language.",
            /*3*/    "Non-terminals cannot be expanded further by production rules.",
            /*4*/    "The start symbol in a CFG is always a terminal.",
            /*5*/    "Production rules define how to transform non-terminals into terminals and other non-terminals.",
            /*6*/    "Terminals are the placeholders for non-terminals in a context-free grammar.",
            /*7*/    "A CFG can have more than one start symbol.",
            /*8*/    "In a CFG, production rules can replace a non-terminal with a combination of terminals and non-terminals.",
            /*9*/    "The start symbol is used to terminate the derivation process in a CFG.",
            /*10*/   "Terminals represent the structure of a language, while non-terminals represent the smallest indivisible units.",
            /*11*/   "Production rules in a CFG always involve replacing a terminal with a non-terminal.",
            /*12*/   "The terminals in a CFG for arithmetic expressions can include operators like +, -, *, and /.",
            /*13*/   "The format for writing production rules is A → α, where A is a terminal and α is a string of non-terminals.",
            /*14*/   "Context-free grammars are commonly used in artificial intelligence and programming languages.",
            /*15*/   "In a CFG, non-terminals are used to define the 'alphabet' of the language."
    };

    public static final String[][] post_test_lesson_2_choices_easy = {
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

    public static final int[] post_test_lesson_2_answers_easy = {
            /*1*/    0, // True
            /*2*/    0, // True
            /*3*/    1, // False
            /*4*/    1, // False
            /*5*/    0, // True
            /*6*/    1, // False
            /*7*/    1, // False
            /*8*/    0, // True
            /*9*/    1, // False
            /*10*/    1, // False
            /*11*/   1, // False
            /*12*/   0, // True
            /*13*/   1, // False
            /*14*/   0, // True
            /*15*/   1  // False
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |        MEDIUM        |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson2_Medium_Questions() {
        return e_Module_4.get_PostTest_EasyMedium_Questions(
                post_test_lesson_2_questions_medium,
                post_test_lesson_2_choices_medium,
                post_test_lesson_2_answers_medium,
                e_Question.Difficulty.MEDIUM
        );
    }

    public static final String[] post_test_lesson_2_questions_medium = {
            /*1*/    "Which of the following is a component of a context-free grammar (CFG)?",
            /*2*/    "In a CFG, which component acts as the starting point for generating strings?",
            /*3*/    "What is the primary role of production rules in a CFG?",
            /*4*/    "Which of the following is NOT an example of a terminal?",
            /*5*/    "Which symbol in a CFG is used to represent the entire sentence or program?",
            /*6*/    "What is the format of a production rule in a CFG?",
            /*7*/    "Which of the following is true about non-terminals in a CFG?",
            /*8*/    "Which of these symbols would most likely be considered a terminal in a CFG for arithmetic expressions?",
            /*9*/    "In a CFG, what are terminals used to represent?",
            /*10*/   "What is the start symbol typically denoted by in a CFG?",
            /*11*/   "Which of the following defines how terminals and non-terminals are combined in a CFG?",
            /*12*/   "In a CFG, what does the left-hand side of a production rule represent?",
            /*13*/   "Which of the following is an example of a non-terminal in an arithmetic expression CFG?",
            /*14*/   "What does the right-hand side of a production rule represent in a CFG?",
            /*15*/   "Which of the following is true about the role of the start symbol in a CFG?"
    };

    public static final String[][] post_test_lesson_2_choices_medium = {
            /*1*/    {"Terminals", "Keywords", "Statements", "Variables"},
            /*2*/    {"Production Rule", "Non-Terminal", "Start Symbol", "Terminal"},
            /*3*/    {"To define the structure of non-terminals", "To define how non-terminals are replaced with terminals and other non-terminals", "To replace terminals with keywords", "To terminate the derivation process"},
            /*4*/    {"(", "Expr", "+", "1"},
            /*5*/    {"Start Symbol", "Terminal", "Non-terminal", "Production Rule"},
            /*6*/    {"A → α", "α → A", "A + B = C", "Term → Factor"},
            /*7*/    {"They represent the 'alphabet' of the language.", "They define the structure of the language.", "They cannot be replaced by other symbols.", "They are always literals."},
            /*8*/    {"Expr", "Term", "+", "Factor"},
            /*9*/    {"The smallest indivisible units of the language", "Placeholders for patterns of terminals and non-terminals", "The start symbol", "Production rules"},
            /*10*/   {"T", "S", "A", "F"},
            /*11*/   {"Terminals", "Non-terminals", "Start symbol", "Production rules"},
            /*12*/   {"A terminal", "A non-terminal being replaced", "The start symbol", "A string of terminals"},
            /*13*/   {"3", "Expr", "+", "/"},
            /*14*/   {"The start symbol", "The non-terminal being replaced", "A sequence of terminals and non-terminals", "A terminal"},
            /*15*/   {"It terminates the production process.", "It is a terminal that starts all derivations.", "It is a non-terminal from which string generation begins.", "It defines the structure of the language."}
    };

    public static final int[] post_test_lesson_2_answers_medium = {
            /*1*/    0, // Terminals
            /*2*/    2, // Start Symbol
            /*3*/    1, // To define how non-terminals are replaced with terminals and other non-terminals
            /*4*/    1, // Expr
            /*5*/    0, // Start Symbol
            /*6*/    0, // A → α
            /*7*/    1, // They define the structure of the language.
            /*8*/    2, // +
            /*9*/    0, // The smallest indivisible units of the language
            /*10*/   1, // S
            /*11*/   3, // Production rules
            /*12*/   1, // A non-terminal being replaced
            /*13*/   1, // Expr
            /*14*/   2, // A sequence of terminals and non-terminals
            /*15*/   2  // It is a non-terminal from which string generation begins.
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |         HARD         |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson2_Hard_Questions() {
        return e_Module_4.get_PostTest_Hard_Questions(
                post_test_lesson_2_questions_hard,
                post_test_lesson_2_answers_hard,
                e_Question.Difficulty.HARD
        );
    }

    public static final String[] post_test_lesson_2_questions_hard = {
            /*1*/    "What defines the sequence of terminals and non-terminals in CFG?",
            /*2*/    "Which symbols are used to construct sentences in CFG?",
            /*3*/    "Which grammar component specifies sentence structure?",
            /*4*/    "What is the initial symbol in CFG derivation?",
            /*5*/    "Which rules direct the expansion of non-terminals?",
            /*6*/    "What defines the indivisible symbols in CFG?",
            /*7*/    "Which component holds place for complex expressions?",
            /*8*/    "What is the first non-terminal in CFG?",
            /*9*/    "Which rules replace non-terminals in CFG?",
            /*10*/   "What part of CFG defines string generation?",
            /*11*/   "Which symbols represent operations or values?",
            /*12*/   "What determines the structure of the entire sentence?",
            /*13*/   "Which rules are applied recursively in CFG?",
            /*14*/   "What specifies the smallest units of language?",
            /*15*/   "What governs the transformation of symbols?"
    };

    public static final String[] post_test_lesson_2_answers_hard = {
            /*1*/    "production rules",
            /*2*/    "terminal symbols",
            /*3*/    "non-terminals",
            /*4*/    "start symbol",
            /*5*/    "production rules",
            /*6*/    "terminal symbols",
            /*7*/    "non-terminals",
            /*8*/    "start symbol",
            /*9*/    "production rules",
            /*10*/   "production rules",
            /*11*/   "terminal symbols",
            /*12*/   "start symbol",
            /*13*/   "production rules",
            /*14*/   "terminal symbols",
            /*15*/   "production rules"
    };
}
