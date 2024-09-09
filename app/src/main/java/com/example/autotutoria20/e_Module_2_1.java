package com.example.autotutoria20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class e_Module_2_1 {

    /*
    +----------------------+
    |       PRE-TEST       |
    |        LESSON 2.1    |
    +----------------------+
    */

    public static e_Question[] get_PreTest_Lesson2_Questions() {
        return e_Module_2.getPreTestQuestions(
                pre_test_lesson_2_questions,
                pre_test_lesson_2_choices,
                pre_test_lesson_2_answers
        );
    }

    public static final String[] pre_test_lesson_2_questions = {
            /*1*/    "What is Automata Theory primarily concerned with?",
            /*2*/    "What is the primary purpose of a grammar in automata theory?",
            /*3*/    "Which type of automaton depends only on input for its output?",
            /*4*/    "What is the difference between deterministic and non-deterministic finite automata?",
            /*5*/    "Which of the following is an example of a language recognized by a Pushdown Automaton?",
            /*6*/    "Which type of automaton is used in the lexical analysis phase of a compiler?",
            /*7*/    "What additional feature does a Pushdown Automaton have compared to a Finite Automaton?",
            /*8*/    "Which machine can simulate any algorithm and is the most powerful among abstract machines?",
            /*9*/    "In which phase of compiler design is a Pushdown Automaton typically used?",
            /*10*/   "What is a key application of automata in natural language processing?",
            /*11*/   "What is the key feature of a Turing Machine that distinguishes it from other automata?",
            /*12*/   "Which type of machine is capable of recognizing recursively enumerable languages?",
            /*13*/   "What is the transition function in automata theory?",
            /*14*/   "What is the key characteristic of a Moore Machine?",
            /*15*/   "What kind of automaton would you use for recognizing palindromes over the alphabet {ab}?"
    };

    public static final String[][] pre_test_lesson_2_choices = {
            /*1*/    {"Data structures and algorithms", "Abstract machines and the problems they solve", "Object-oriented programming", "Machine learning and artificial intelligence"},
            /*2*/    {"To recognize languages", "To simulate abstract machines", "To generate sentences in a language", "To design programming languages"},
            /*3*/    {"Mealy Machine", "Moore Machine", "Automaton without memory", "Pushdown Automaton"},
            /*4*/    {"DFA has no transitions", "NFA has multiple transitions for each symbol", "DFA has infinite states", "NFA has no output"},
            /*5*/    {"Binary strings ending in '01'", "Balanced parentheses", "Palindromes over the alphabet {ab}", "Context-free grammars"},
            /*6*/    {"Pushdown Automaton", "Finite Automaton", "Turing Machine", "Moore Machine"},
            /*7*/    {"Infinite states", "A stack for memory", "Multiple tape heads", "A transition function"},
            /*8*/    {"Deterministic Finite Automaton", "Pushdown Automaton", "Mealy Machine", "Turing Machine"},
            /*9*/    {"Lexical analysis", "Syntax analysis", "Code generation", "Optimization"},
            /*10*/   {"Model checking", "String matching", "Parsing and analyzing language structure", "Syntax error detection"},
            /*11*/   {"It has a finite number of states", "It uses an infinite tape for reading and writing", "It has no memory", "It only recognizes regular languages"},
            /*12*/   {"DFA", "PDA", "Turing Machine", "NFA"},
            /*13*/   {"A function that defines how the automaton generates outputs", "A function that defines the next state based on the current state and input", "A function that determines the number of states in the automaton", "A function that checks if the input is valid"},
            /*14*/   {"Output depends on input only", "Output depends on both the state and input", "Output depends only on the states of the machine", "Output is undefined"},
            /*15*/   {"Deterministic Finite Automaton (DFA)", "Pushdown Automaton (PDA)", "Moore Machine", "Mealy Machine"}
    };

    public static final int[] pre_test_lesson_2_answers = {
            /*1*/    1, // 'Abstract machines and the problems they solve'
            /*2*/    2, // 'To generate sentences in a language'
            /*3*/    0, // 'Mealy Machine'
            /*4*/    1, // 'NFA has multiple transitions for each symbol'
            /*5*/    1, // 'Balanced parentheses'
            /*6*/    1, // 'Finite Automaton'
            /*7*/    1, // 'A stack for memory'
            /*8*/    3, // 'Turing Machine'
            /*9*/    1, // 'Syntax analysis'
            /*10*/   2, // 'Parsing and analyzing language structure'
            /*11*/   1, // 'It uses an infinite tape for reading and writing'
            /*12*/   2, // 'Turing Machine'
            /*13*/   1, // 'A function that defines the next state based on the current state and input'
            /*14*/   2, // 'Output depends only on the states of the machine'
            /*15*/   1  // 'Pushdown Automaton (PDA)'
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |        EASY          |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson2_Easy_Questions() {
        return e_Module_2.get_PostTest_EasyMedium_Questions(
                post_test_lesson_2_questions_easy,
                post_test_lesson_2_choices_easy,
                post_test_lesson_2_answers_easy,
                e_Question.Difficulty.EASY
        );
    }

    public static final String[] post_test_lesson_2_questions_easy = {
            /*1*/    "A regular language can always be recognized by a finite automaton.",
            /*2*/    "Regular expressions are used to define patterns in strings.",
            /*3*/    "Deterministic finite automata (DFA) and non-deterministic finite automata (NFA) cannot recognize the same set of regular languages.",
            /*4*/    "Regular languages are closed under the operation of union.",
            /*5*/    "A regular expression can define an infinite number of strings.",
            /*6*/    "Finite automata can keep track of how many times a switch was turned on and off.",
            /*7*/    "A regular language can be recognized only by deterministic finite automata (DFA).",
            /*8*/    "Regular expressions are not used in compiler design.",
            /*9*/    "A finite automaton can model problems that require a large amount of memory.",
            /*10*/   "The Kleene star operation allows for the repetition of symbols in a regular language.",
            /*11*/   "A regular language is a set of strings formed from an infinite alphabet.",
            /*12*/   "Regular expressions are used in data validation such as for checking email formats.",
            /*13*/   "Regular languages can be described by both regular expressions and finite automata.",
            /*14*/   "Finite automata can be used for text search operations.",
            /*15*/   "Regular languages are not closed under concatenation."
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
            /*4*/    0, // True
            /*5*/    0, // True
            /*6*/    1, // False
            /*7*/    1, // False
            /*8*/    1, // False
            /*9*/    1, // False
            /*10*/   0, // True
            /*11*/   1, // False
            /*12*/   0, // True
            /*13*/   0, // True
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
        return e_Module_2.get_PostTest_EasyMedium_Questions(
                post_test_lesson_2_questions_medium,
                post_test_lesson_2_choices_medium,
                post_test_lesson_2_answers_medium,
                e_Question.Difficulty.MEDIUM
        );
    }

    public static final String[] post_test_lesson_2_questions_medium = {
            /*1*/    "What is a regular expression?",
            /*2*/    "Which of the following can recognize a regular language?",
            /*3*/    "What is the operation that allows for zero or more repetitions of a symbol in regular expressions?",
            /*4*/    "Regular languages are closed under which of the following operations?",
            /*5*/    "Which automaton uses a stack for memory and cannot recognize regular languages?",
            /*6*/    "Which of the following is NOT a property of regular languages?",
            /*7*/    "What is the role of lexical analysis in compilers?",
            /*8*/    "Which of the following regular expressions matches zero or more occurrences of 'a'?",
            /*9*/    "What kind of machine can recognize regular languages but cannot keep track of memory or counters?",
            /*10*/   "In the five-tuple representation of a finite automaton, which component represents the set of input symbols?",
            /*11*/   "What does the union operation do in regular languages?",
            /*12*/   "Which of the following is NOT an application of regular expressions?",
            /*13*/   "Which automaton accepts strings by reaching a final state?",
            /*14*/   "Which of the following expressions matches exactly one 'a' followed by one 'b'?",
            /*15*/   "Which regular operation allows for combining two regular languages end-to-end?"
    };

    public static final String[][] post_test_lesson_2_choices_medium = {
            /*1*/    {"A type of automaton", "A sequence of characters that defines a search pattern", "A mathematical function", "A Turing machine"},
            /*2*/    {"Turing machine", "Context-free grammar", "Finite automaton", "Pushdown automaton"},
            /*3*/    {"Concatenation", "Union", "Kleene star", "Intersection"},
            /*4*/    {"Division", "Concatenation", "Exponentiation", "Subtraction"},
            /*5*/    {"Deterministic finite automaton", "Non-deterministic finite automaton", "Pushdown automaton", "Turing machine"},
            /*6*/    {"Closure under union", "Closure under intersection", "Closure under Kleene star", "Closure under concatenation"},
            /*7*/    {"To analyze the structure of a program", "To define the lexical structure of programming languages", "To generate machine code", "To perform logical operations"},
            /*8*/    {"a*", "a+", "ab*", "a?"},
            /*9*/    {"Finite automaton", "Pushdown automaton", "Turing machine", "Context-free grammar"},
            /*10*/   {"Q", "Σ", "δ", "F"},
            /*11*/   {"Repeats a symbol zero or more times", "Combines two languages into one", "Splits a language into two parts", "Matches an exact string"},
            /*12*/   {"Text search", "Data validation", "Modeling context-free languages", "Lexical analysis"},
            /*13*/   {"Pushdown automaton", "Deterministic finite automaton", "Turing machine", "Context-sensitive grammar"},
            /*14*/   {"ab*", "a*b", "ab", "a+b"},
            /*15*/   {"Kleene star", "Concatenation", "Union", "Intersection"}
    };

    public static final int[] post_test_lesson_2_answers_medium = {
            /*1*/    1, // A sequence of characters that defines a search pattern
            /*2*/    2, // Finite automaton
            /*3*/    2, // Kleene star
            /*4*/    1, // Concatenation
            /*5*/    2, // Pushdown automaton
            /*6*/    1, // Closure under intersection
            /*7*/    1, // To define the lexical structure of programming languages
            /*8*/    0, // a*
            /*9*/    0, // Finite automaton
            /*10*/   1, // Σ
            /*11*/   1, // Combines two languages into one
            /*12*/   2, // Modeling context-free languages
            /*13*/   1, // Deterministic finite automaton
            /*14*/   2, // ab
            /*15*/   1  // Concatenation
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |         HARD         |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson2_Hard_Questions() {
        return e_Module_2.get_PostTest_Hard_Questions(
                post_test_lesson_2_questions_hard,
                post_test_lesson_2_answers_hard,
                e_Question.Difficulty.HARD
        );
    }

    public static final String[] post_test_lesson_2_questions_hard = {
            /*1*/    "What type of automata is used to recognize regular languages?",
            /*2*/    "What operation allows a regular language to repeat a symbol zero or more times?",
            /*3*/    "What type of expressions define search patterns in strings?",
            /*4*/    "What type of automaton is used for non-deterministic recognition of regular languages?",
            /*5*/    "What class of languages can be expressed with regular expressions?",
            /*6*/    "What operation on two regular languages results in another regular language?",
            /*7*/    "Which automaton has a single possible action for each input?",
            /*8*/    "What property of regular languages allows them to be combined using concatenation?",
            /*9*/    "What tool is used in compilers to define the structure of programming languages?",
            /*10*/   "What is the set of all possible input symbols for a language called?",
            /*11*/   "Which automaton type has multiple possible transitions for the same input?",
            /*12*/   "What type of problems can be solved using finite automata?",
            /*13*/   "What is the formal description of a finite automaton called?",
            /*14*/   "What is the result of applying the union operation to two regular languages?",
            /*15*/   "What concept refers to repeating an operation zero or more times in regular languages?"
    };

    public static final String[] post_test_lesson_2_answers_hard = {
            /*1*/    "finite automaton",
            /*2*/    "kleene star",
            /*3*/    "regular expressions",
            /*4*/    "non-deterministic automaton",
            /*5*/    "regular languages",
            /*6*/    "union operation",
            /*7*/    "deterministic automaton",
            /*8*/    "closure property",
            /*9*/    "lexical analysis",
            /*10*/   "input alphabet",
            /*11*/   "non-deterministic automaton",
            /*12*/   "computational problems",
            /*13*/   "five tuples",
            /*14*/   "regular language",
            /*15*/   "kleene star"
    };

}