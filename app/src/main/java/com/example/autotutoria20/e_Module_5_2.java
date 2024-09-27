package com.example.autotutoria20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class e_Module_5_2 {

    /*
    +----------------------+
    |       PRE-TEST       |
    |        LESSON 2      |
    +----------------------+
    */

    public static e_Question[] get_PreTest_Lesson2_Questions() {
        return e_Module_5.getPreTestQuestions(
                pre_test_lesson_2_questions,
                pre_test_lesson_2_choices,
                pre_test_lesson_2_answers
        );
    }

    public static final String[] pre_test_lesson_2_questions = {
            /*1*/    "What is the primary difference between terminals and non-terminals in a parse tree?",
            /*2*/    "In which phase of a compiler is the parse tree most actively used?",
            /*3*/    "What is the role of a context-free grammar (CFG) in constructing a parse tree?",
            /*4*/    "Which of the following statements is true about ambiguous grammars and parse trees?",
            /*5*/    "In a parse tree, the internal nodes represent which of the following?",
            /*6*/    "What does the root node of a parse tree correspond to?",
            /*7*/    "What is the main advantage of using parse trees in programming language compilers?",
            /*8*/    "How does a leftmost derivation in a parse tree proceed?",
            /*9*/    "What does a production rule define in the context of a parse tree?",
            /*10*/   "Which of the following best describes the structure of a parse tree?",
            /*11*/   "In a parse tree, what is the relationship between the root node and the leaf nodes?",
            /*12*/   "Why is it important that a parse tree be unambiguous in a compiler?",
            /*13*/   "What is the main purpose of a parse tree in natural language processing (NLP)?",
            /*14*/   "What is the significance of edges in a parse tree?",
            /*15*/   "Which of the following is a common error if a parse tree is constructed incorrectly in a compiler?"
    };

    public static final String[][] pre_test_lesson_2_choices = {
            /*1*/    {"Terminals can be broken down further, while non-terminals cannot.", "Non-terminals are internal nodes, while terminals are leaf nodes.", "Terminals represent the root of the tree, while non-terminals are leaf nodes.", "Non-terminals define the structure, while terminals represent operators."},
            /*2*/    {"Lexical analysis", "Syntax analysis", "Code generation", "Semantic analysis"},
            /*3*/    {"It specifies how a string of terminals is derived.", "It generates all possible outputs of a program.", "It verifies the correctness of a derivation.", "It defines the order of mathematical operations."},
            /*4*/    {"Ambiguous grammars have multiple parse trees for a single string.", "Ambiguous grammars generate only one parse tree for all inputs.", "Ambiguous grammars cannot generate any valid parse trees.", "Ambiguous grammars are error-free in terms of syntax."},
            /*5*/    {"Characters in the input string", "Operations or non-terminal symbols", "Final output of the program", "The start symbol"},
            /*6*/    {"The first terminal symbol", "The start symbol of the grammar", "The most complex production rule", "The longest terminal string"},
            /*7*/    {"They allow for code optimization.", "They simplify debugging.", "They help visualize the hierarchical structure of the code.", "They reduce memory usage."},
            /*8*/    {"By replacing the rightmost non-terminal first", "By expanding the leftmost non-terminal first", "By converting terminals into non-terminals", "By ignoring non-terminal symbols"},
            /*9*/    {"How a non-terminal can be expanded into terminals and other non-terminals", "How terminals are directly converted into program output", "The speed of program execution", "The size of the parse tree"},
            /*10*/   {"It has a root node, branches, and leaf nodes.", "It has a root node and multiple terminals as branches.", "It consists only of leaf nodes.", "It does not have a hierarchical structure."},
            /*11*/   {"The root node is always a terminal symbol, and leaf nodes are non-terminals.", "The root node is derived first, and leaf nodes represent terminal symbols.", "The leaf nodes are derived first, and the root node is always non-terminal.", "The leaf nodes contain production rules, and the root node represents the final string."},
            /*12*/   {"To ensure efficient program execution", "To guarantee a single, correct structure for any input", "To allow for multiple interpretations of the input", "To simplify the derivation of new languages"},
            /*13*/   {"To identify syntactical errors in a program", "To structure sentences according to grammar rules", "To create new programming languages", "To increase computational efficiency"},
            /*14*/   {"They define the sequence of input tokens.", "They connect the parent node to its children, representing the application of production rules.", "They separate non-terminals from terminals.", "They help organize the branches based on memory usage."},
            /*15*/   {"Lexical errors", "Syntactic ambiguity", "Memory overflow", "Semantic correctness"}
    };

    public static final int[] pre_test_lesson_2_answers = {
            /*1*/    1, // Non-terminals are internal nodes, while terminals are leaf nodes.
            /*2*/    1, // Syntax analysis
            /*3*/    0, // It specifies how a string of terminals is derived.
            /*4*/    0, // Ambiguous grammars have multiple parse trees for a single string.
            /*5*/    1, // Operations or non-terminal symbols
            /*6*/    1, // The start symbol of the grammar
            /*7*/    2, // They help visualize the hierarchical structure of the code.
            /*8*/    1, // By expanding the leftmost non-terminal first
            /*9*/    0, // How a non-terminal can be expanded into terminals and other non-terminals
            /*10*/   0, // It has a root node, branches, and leaf nodes.
            /*11*/   1, // The root node is derived first, and leaf nodes represent terminal symbols.
            /*12*/   1, // To guarantee a single, correct structure for any input
            /*13*/   1, // To structure sentences according to grammar rules
            /*14*/   1, // They connect the parent node to its children, representing the application of production rules.
            /*15*/   1  // Syntactic ambiguity
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |          EASY        |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson2_Easy_Questions() {
        return e_Module_5.get_PostTest_EasyMedium_Questions(
                post_test_lesson_2_questions_easy,
                post_test_lesson_2_choices_easy,
                post_test_lesson_2_answers_easy,
                e_Question.Difficulty.EASY
        );
    }

    public static final String[] post_test_lesson_2_questions_easy = {
            /*1*/    "The leftmost derivation expands the leftmost non-terminal first.",
            /*2*/    "A rightmost derivation expands non-terminals in a left-to-right sequence.",
            /*3*/    "Every parse tree corresponds to a unique leftmost derivation.",
            /*4*/    "A derivation process can have multiple corresponding parse trees.",
            /*5*/    "Both leftmost and rightmost derivations yield the same parse tree for a given string.",
            /*6*/    "Derivations and parse trees are distinct but related representations of grammar structure.",
            /*7*/    "A derivation tree and a parse tree are two names for the same structure.",
            /*8*/    "Leftmost derivation always results in ambiguous grammar.",
            /*9*/    "A parse tree can represent both terminals and non-terminals.",
            /*10*/   "The root of a parse tree represents the start symbol of the grammar.",
            /*11*/   "In a parse tree, leaf nodes are always non-terminals.",
            /*12*/   "Rightmost derivation expands the rightmost non-terminal first.",
            /*13*/   "A parse tree cannot be constructed from a rightmost derivation.",
            /*14*/   "An ambiguous grammar has more than one possible derivation for some strings.",
            /*15*/   "The number of nodes in a parse tree is directly related to the number of terminals in the input string."
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
            /*2*/    1, // False
            /*3*/    0, // True
            /*4*/    0, // True
            /*5*/    0, // True
            /*6*/    0, // True
            /*7*/    0, // True
            /*8*/    1, // False
            /*9*/    0, // True
            /*10*/   0, // True
            /*11*/   1, // False
            /*12*/   0, // True
            /*13*/   1, // False
            /*14*/   0, // True
            /*15*/   0  // True
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |       MEDIUM         |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson2_Medium_Questions() {
        return e_Module_5.get_PostTest_EasyMedium_Questions(
                post_test_lesson_2_questions_medium,
                post_test_lesson_2_choices_medium,
                post_test_lesson_2_answers_medium,
                e_Question.Difficulty.MEDIUM
        );
    }

    public static final String[] post_test_lesson_2_questions_medium = {
            /*1*/    "What is the primary relationship between derivations and parse trees?",
            /*2*/    "Which derivation process involves replacing the leftmost non-terminal first?",
            /*3*/    "What does the root node of a parse tree represent?",
            /*4*/    "Which derivation expands the rightmost non-terminal first?",
            /*5*/    "In a parse tree, the leaf nodes represent:",
            /*6*/    "What kind of grammar typically generates a parse tree?",
            /*7*/    "Which of the following statements about ambiguous grammars is correct?",
            /*8*/    "A parse tree represents the hierarchical structure of:",
            /*9*/    "Which of the following is true about a derivation in a grammar?",
            /*10*/   "A parse tree is useful in which of the following fields?",
            /*11*/   "Which part of a parse tree represents the non-terminal symbols?",
            /*12*/   "What defines an ambiguous grammar in terms of parse trees?",
            /*13*/   "Which derivation process is most commonly visualized with a parse tree?",
            /*14*/   "How does a parse tree relate to a derivation process?",
            /*15*/   "In a parse tree, what is represented by the root node?"
    };

    public static final String[][] post_test_lesson_2_choices_medium = {
            /*1*/    {"Derivations build the parse tree node by node", "Parse trees visualize the steps of derivations", "Parse trees are derived from the terminal symbols only", "Derivations occur only for ambiguous grammars"},
            /*2*/    {"Leftmost derivation", "Rightmost derivation", "Bottom-up derivation", "Top-down derivation"},
            /*3*/    {"The last production rule applied", "The first terminal in the string", "The start symbol of the grammar", "The result of the derivation process"},
            /*4*/    {"Leftmost derivation", "Rightmost derivation", "Center-out derivation", "Topmost derivation"},
            /*5*/    {"Non-terminal symbols", "Terminal symbols", "Start symbols", "Production rules"},
            /*6*/    {"Regular grammar", "Context-free grammar", "Context-sensitive grammar", "Phrase structure grammar"},
            /*7*/    {"Ambiguous grammars allow only one parse tree per string.", "Ambiguous grammars have more than one derivation for some strings.", "Ambiguous grammars are not related to parse trees.", "Ambiguous grammars are errors in programming."},
            /*8*/    {"Tokens", "Operators", "Sentences according to grammar rules", "Programming logic"},
            /*9*/    {"It defines the flow of control in a program", "It breaks down a string into terminals and non-terminals", "It checks for syntax errors in a program", "It is used only in machine learning algorithms"},
            /*10*/   {"Arithmetic calculations", "Syntax analysis in compilers", "Database optimization", "Physical simulations"},
            /*11*/   {"Root nodes", "Leaf nodes", "Interior nodes", "Edge nodes"},
            /*12*/   {"It has the same parse tree for all strings.", "It generates multiple valid parse trees for a single string.", "It cannot generate a parse tree at all.", "It only generates invalid strings."},
            /*13*/   {"Semantic derivation", "Leftmost derivation", "Lexical derivation", "Functional derivation"},
            /*14*/   {"It shows the chronological steps of the derivation.", "It reverses the derivation order.", "It skips non-terminal steps.", "It only shows the end result of the derivation."},
            /*15*/   {"The last non-terminal in the string", "The start symbol of the grammar", "The last terminal in the string", "The first production rule"}
    };

    public static final int[] post_test_lesson_2_answers_medium = {
            /*1*/    1, // Parse trees visualize the steps of derivations
            /*2*/    0, // Leftmost derivation
            /*3*/    2, // The start symbol of the grammar
            /*4*/    1, // Rightmost derivation
            /*5*/    1, // Terminal symbols
            /*6*/    1, // Context-free grammar
            /*7*/    1, // Ambiguous grammars have more than one derivation for some strings.
            /*8*/    2, // Sentences according to grammar rules
            /*9*/    1, // It breaks down a string into terminals and non-terminals
            /*10*/   1, // Syntax analysis in compilers
            /*11*/   2, // Interior nodes
            /*12*/   1, // It generates multiple valid parse trees for a single string.
            /*13*/   1, // Leftmost derivation
            /*14*/   0, // It shows the chronological steps of the derivation.
            /*15*/   1  // The start symbol of the grammar
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |         HARD         |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson2_Hard_Questions() {
        return e_Module_5.get_PostTest_Hard_Questions(
                post_test_lesson_2_questions_hard,
                post_test_lesson_2_answers_hard,
                e_Question.Difficulty.HARD
        );
    }

    public static final String[] post_test_lesson_2_questions_hard = {
            /*1*/    "What is the process called where a string is repeatedly transformed by applying production rules until it consists only of terminal symbols?",
            /*2*/    "What graphical tool is used to represent both the syntactic structure and derivation process in a formal grammar?",
            /*3*/    "What is the term for a sequence of production rules that leads from the start symbol to a terminal string?",
            /*4*/    "In formal grammar, what is used to describe the set of rules that governs the syntax of a language?",
            /*5*/    "What type of derivation starts by expanding the leftmost non-terminal symbol and continues until only terminals remain?",
            /*6*/    "What are the non-terminal symbols that can be further expanded using production rules in a derivation process?",
            /*7*/    "What term describes a grammar that has more than one valid derivation or parse tree for some strings?",
            /*8*/    "What kind of node in a parse tree directly corresponds to the application of a production rule?",
            /*9*/    "In a context-free grammar, what is the term for the initial symbol from which derivations begin?",
            /*10*/   "Which grammar rule format is used to transform non-terminal symbols into other non-terminals or terminals?",
            /*11*/   "What is the step-by-step process of applying production rules to generate a terminal string called?",
            /*12*/   "What is the term for the hierarchical representation of syntactic rules used to analyze sentences in programming languages?",
            /*13*/   "What do we call the sequence of transformations applied to derive a string from a grammar’s start symbol?",
            /*14*/   "What part of a parse tree contains both the terminal and non-terminal symbols?",
            /*15*/   "What is the formal system that defines how sentences in a language are generated and structured?"
    };

    public static final String[] post_test_lesson_2_answers_hard = {
            /*1*/    "string derivation",
            /*2*/    "derivation tree",
            /*3*/    "derivation sequence",
            /*4*/    "context-free grammar",
            /*5*/    "leftmost derivation",
            /*6*/    "variables",
            /*7*/    "ambiguous grammar",
            /*8*/    "parent node",
            /*9*/    "start symbol",
            /*10*/   "production rule",
            /*11*/   "recursive derivation",
            /*12*/   "syntax tree",
            /*13*/   "production sequence",
            /*14*/   "branches",
            /*15*/   "generative grammar"
    };
}
