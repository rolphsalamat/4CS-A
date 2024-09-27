package com.example.autotutoria20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class e_Module_2_1 {

    /*
    +----------------------+
    |       PRE-TEST       |
    |       LESSON 1       |
    +----------------------+
    */

    public static e_Question[] get_PreTest_Lesson2_Questions() {
        return e_Module_2.getPreTestQuestions(
                pre_test_lesson_1_questions,
                pre_test_lesson_1_choices,
                pre_test_lesson_1_answers
        );
    }

    public static final String[] pre_test_lesson_1_questions = {
            /*1*/    "What is Automata Theory mainly concerned with?",
            /*2*/    "What does an automaton transition between?",
            /*3*/    "Which type of machine recognizes context-free languages and includes a stack?",
            /*4*/    "Which of the following best describes a Moore Machine?",
            /*5*/    "What type of automaton is used to recognize strings that follow a specific pattern?",
            /*6*/    "Which automaton can handle both input and state to determine its output?",
            /*7*/    "In automata theory, what does the concept of 'state transition' refer to?",
            /*8*/    "What does a Turing Machine's infinite tape symbolize in computational terms?",
            /*9*/    "Which machine uses a stack to recognize nested structures such as parentheses?",
            /*10*/   "Which type of machine has the capability to simulate any computable algorithm?",
            /*11*/   "Which of the following represents the fundamental characteristic of a Finite Automaton?",
            /*12*/   "Which of the following operations does not involve a pushdown automaton?",
            /*13*/   "What automaton is involved in the verification of both hardware and software systems?",
            /*14*/   "What is a common application of a Finite Automaton in compiler design?",
            /*15*/   "Which machine can recognize languages that a finite automaton cannot?"
    };

    public static final String[][] pre_test_lesson_1_choices = {
            /*1*/    {"Physical machines", "Computational processes and their capabilities", "Error correction", "Software design"},
            /*2*/    {"Inputs and outputs", "States", "Symbols and languages", "Stacks"},
            /*3*/    {"Finite Automaton", "Turing Machine", "Pushdown Automaton", "Mealy Machine"},
            /*4*/    {"Its output depends solely on the state", "It recognizes regular languages", "It includes a stack for memory", "It simulates algorithms"},
            /*5*/    {"Turing Machine", "Finite Automaton", "Pushdown Automaton", "Mealy Machine"},
            /*6*/    {"Moore Machine", "Mealy Machine", "Turing Machine", "Pushdown Automaton"},
            /*7*/    {"Moving between different automata", "Switching between input alphabets", "Changing from one state to another based on input", "Processing the output"},
            /*8*/    {"Finite memory", "Infinite storage capacity", "Regular expressions", "Binary representation"},
            /*9*/    {"Turing Machine", "Pushdown Automaton", "Finite Automaton", "Moore Machine"},
            /*10*/   {"Pushdown Automaton", "Finite Automaton", "Turing Machine", "Mealy Machine"},
            /*11*/   {"Infinite number of states", "Limited memory and fixed states", "Recursively enumerable languages", "Unbounded tape"},
            /*12*/   {"Lexical analysis in compilers", "Recognizing context-free grammars", "Simulating algorithms", "Recognizing nested structures"},
            /*13*/   {"Finite Automaton", "Pushdown Automaton", "Model Checking Automaton", "Mealy Machine"},
            /*14*/   {"Parsing syntax trees", "Tokenizing input code during lexical analysis", "Running complex algorithms", "Recognizing context-free languages"},
            /*15*/   {"Finite Automaton", "Pushdown Automaton", "Turing Machine", "Mealy Machine"}
    };

    public static final int[] pre_test_lesson_1_answers = {
            /*1*/    1,  // Computational processes and their capabilities
            /*2*/    1,  // States
            /*3*/    2,  // Pushdown Automaton
            /*4*/    0,  // Its output depends solely on the state
            /*5*/    1,  // Finite Automaton
            /*6*/    1,  // Mealy Machine
            /*7*/    2,  // Changing from one state to another based on input
            /*8*/    1,  // Infinite storage capacity
            /*9*/    1,  // Pushdown Automaton
            /*10*/   2,  // Turing Machine
            /*11*/   1,  // Limited memory and fixed states
            /*12*/   2,  // Simulating algorithms
            /*13*/   2,  // Model Checking Automaton
            /*14*/   1,  // Tokenizing input code during lexical analysis
            /*15*/   2   // Turing Machine
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |        EASY          |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson2_Easy_Questions() {
        return e_Module_2.get_PostTest_EasyMedium_Questions(
                post_test_lesson_1_easy_questions,
                post_test_lesson_1_choices_easy,
                post_test_lesson_1_answers_easy,
                e_Question.Difficulty.EASY
        );
    }

    public static final String[] post_test_lesson_1_easy_questions = {
            /*1*/    "Regular languages are recognized by both deterministic and nondeterministic finite automata.",
            /*2*/    "Regular expressions are capable of recognizing context-free languages.",
            /*3*/    "The Kleene star (*) operation is one of the closure properties of regular languages.",
            /*4*/    "Finite automata can keep track of how many times a specific action has occurred.",
            /*5*/    "Regular languages can be described using regular expressions and finite automata.",
            /*6*/    "A DFA allows multiple transitions for a single symbol in the alphabet.",
            /*7*/    "Concatenation is a closure property of regular languages.",
            /*8*/    "A regular expression defines a set of rules for parsing natural languages.",
            /*9*/    "Finite automata are limited to modeling problems that require a small amount of memory.",
            /*10*/   "Regular expressions are not used in text search engines.",
            /*11*/   "A finite automaton consists of five main components: states, input alphabet, transition function, initial state, and final state.",
            /*12*/   "Lexical analysis is an application of regular languages in the field of compiler design.",
            /*13*/   "NFA and DFA differ in their number of transitions for each input symbol.",
            /*14*/   "A regular expression is the same as a context-free grammar.",
            /*15*/   "Finite automata can recognize infinite sets of strings."
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
            /*3*/    0,  // True
            /*4*/    1,  // False
            /*5*/    0,  // True
            /*6*/    1,  // False
            /*7*/    0,  // True
            /*8*/    1,  // False
            /*9*/    0,  // True
            /*10*/   1,  // False
            /*11*/   0,  // True
            /*12*/   0,  // True
            /*13*/   0,  // True
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
        return e_Module_2.get_PostTest_EasyMedium_Questions(
                post_test_lesson_1_questions_medium,
                post_test_lesson_1_choices_medium,
                post_test_lesson_1_answers_medium,
                e_Question.Difficulty.MEDIUM
        );
    }

    public static final String[] post_test_lesson_1_questions_medium = {
            /*1*/    "What is a regular language?",
            /*2*/    "Which of the following is a closure property of regular languages?",
            /*3*/    "What is a regular expression used for?",
            /*4*/    "Which automaton is capable of recognizing regular languages?",
            /*5*/    "In which field is a regular expression commonly applied?",
            /*6*/    "What does the Kleene star (*) operator do in regular expressions?",
            /*7*/    "Which of the following is NOT an example of a regular language application?",
            /*8*/    "What is the key difference between a DFA and an NFA?",
            /*9*/    "Which component is NOT part of a finite automaton?",
            /*10*/   "Which of the following operations is a regular language closed under?",
            /*11*/   "Which of the following can be recognized by a regular expression?",
            /*12*/   "Which automaton can recognize a finite set of strings?",
            /*13*/   "What is a common use for regular expressions in compiler design?",
            /*14*/   "In a finite automaton, what is a state?",
            /*15*/   "Which of the following properties does a regular language NOT have?"
    };

    public static final String[][] post_test_lesson_1_choices_medium = {
            /*1*/    {"A language that can be expressed using finite automata or regular expressions", "A natural language", "A language used only in mathematics", "A programming language"},
            /*2*/    {"Subtraction", "Union", "Exponentiation", "Division"},
            /*3*/    {"Solving mathematical problems", "Defining search patterns for text", "Compiling code", "Encrypting data"},
            /*4*/    {"Pushdown Automaton", "Turing Machine", "Deterministic Finite Automaton", "Quantum Computer"},
            /*5*/    {"Data encryption", "Text search engines", "Web development frameworks", "Cryptography"},
            /*6*/    {"Repeats the preceding symbol zero or more times", "Negates the preceding symbol", "Adds two symbols together", "Creates a new language"},
            /*7*/    {"Text processing", "Context-free grammar parsing", "Data validation", "Lexical analysis"},
            /*8*/    {"DFA has a stack memory", "NFA can have multiple transitions for a symbol", "DFA operates faster than NFA", "NFA cannot recognize regular languages"},
            /*9*/    {"Input alphabet", "Stack", "Transition function", "Final state"},
            /*10*/   {"Subtraction", "Complement", "Exponentiation", "Division"},
            /*11*/   {"Context-free languages", "Palindromes", "Regular languages", "Natural languages"},
            /*12*/   {"Turing Machine", "Finite Automaton", "Pushdown Automaton", "Neural Network"},
            /*13*/   {"Writing machine code", "Lexical analysis", "Data encryption", "Parsing syntax trees"},
            /*14*/   {"A set of output symbols", "A configuration the automaton can be in at any time", "A memory storage unit", "A computational procedure"},
            /*15*/   {"Closed under union", "Closed under concatenation", "Closed under intersection", "Closed under Kleene star"}
    };

    public static final int[] post_test_lesson_1_answers_medium = {
            /*1*/    0,  // A language that can be expressed using finite automata or regular expressions
            /*2*/    1,  // Union
            /*3*/    1,  // Defining search patterns for text
            /*4*/    2,  // Deterministic Finite Automaton
            /*5*/    1,  // Text search engines
            /*6*/    0,  // Repeats the preceding symbol zero or more times
            /*7*/    1,  // Context-free grammar parsing
            /*8*/    1,  // NFA can have multiple transitions for a symbol
            /*9*/    1,  // Stack
            /*10*/   1,  // Complement
            /*11*/   2,  // Regular languages
            /*12*/   1,  // Finite Automaton
            /*13*/   1,  // Lexical analysis
            /*14*/   1,  // A configuration the automaton can be in at any time
            /*15*/   2   // Closed under intersection
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |         HARD         |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson2_Hard_Questions() {
        return e_Module_2.get_PostTest_Hard_Questions(
                post_test_lesson_1_questions_hard,
                post_test_lesson_1_answers_hard,
                e_Question.Difficulty.HARD
        );
    }

    public static final String[] post_test_lesson_1_questions_hard = {
            /*1*/    "What function determines the next state based on the current state and input in an automaton?",
            /*2*/    "What type of abstract machine can move between a finite number of states based on input symbols?",
            /*3*/    "What notation is used to describe patterns in regular languages for search and matching purposes?",
            /*4*/    "What kind of automaton is used to recognize regular languages through state transitions?",
            /*5*/    "Which automaton uses a stack to handle recursive structures like balanced parentheses?",
            /*6*/    "Which abstract machine has an infinite tape and can simulate any algorithm?",
            /*7*/    "What regular language operation allows a symbol to repeat zero or more times?",
            /*8*/    "What property ensures that regular languages remain closed under the union of two languages?",
            /*9*/    "Which application of regular expressions is commonly used to check the correctness of user input data?",
            /*10*/   "What set of symbols is used as input for finite automata to process and generate languages?",
            /*11*/   "What category of formal languages can be described using deterministic or nondeterministic finite automata?",
            /*12*/   "What process involves moving from one state to another based on an input symbol in automata theory?",
            /*13*/   "What phase in compiler design uses regular expressions to break down source code into tokens?",
            /*14*/   "What type of language consists of strings that can be accepted by finite automata?",
            /*15*/   "What term refers to an abstract computational model that processes input symbols through state changes?"
    };

    public static final String[] post_test_lesson_1_answers_hard = {
            /*1*/    "transition function",
            /*2*/    "state machine",
            /*3*/    "regular expressions",
            /*4*/    "finite automaton",
            /*5*/    "pushdown automaton",
            /*6*/    "turing machine",
            /*7*/    "kleene star",
            /*8*/    "closure properties",
            /*9*/    "data validation",
            /*10*/   "input symbols",
            /*11*/   "regular languages",
            /*12*/   "state transition",
            /*13*/   "lexical analysis",
            /*14*/   "regular language",
            /*15*/   "abstract machine"
    };
}
