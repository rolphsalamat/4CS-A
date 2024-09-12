package com.example.autotutoria20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class e_Module_1_4 {

    /*
    +----------------------+
    |       PRE-TEST       |
    |        LESSON 4      |
    +----------------------+
    */

    public static e_Question[] get_PreTest_Lesson4_Questions() {
        return e_Module_1.getPreTestQuestions(
                pre_test_lesson_4_questions,
                pre_test_lesson_4_choices,
                pre_test_lesson_4_answers
        );
    }

    public static final String[] pre_test_lesson_4_questions = {
            /*1*/    "What is a formal language?",
            /*2*/    "What is an alphabet in the context of formal languages?",
            /*3*/    "Which of the following is an example of a string in a formal language?",
            /*4*/    "What is the purpose of grammar in a formal language?",
            /*5*/    "Which of the following is an example of a formal language?",
            /*6*/    "In the binary language, what is the alphabet used?",
            /*7*/    "What is the significance of balanced parentheses in formal languages?",
            /*8*/    "How are formal languages used in programming languages?",
            /*9*/    "Which of the following fields uses formal languages?",
            /*10*/   "What role do formal languages play in compilers and interpreters?",
            /*11*/   "Which class in the Chomsky hierarchy is characterized by the ability to be recognized by a Turing machine?",
            /*12*/   "What is a string in the context of formal languages?",
            /*13*/   "In automata theory, how can a formal language be specified?",
            /*14*/   "What is the role of formal languages in natural language processing?",
            /*15*/   "Which of the following is NOT a component of a formal language?"
    };

    public static final String[][] pre_test_lesson_4_choices = {
            /*1*/    {"A natural language used in daily communication", "A set of strings of symbols defined by grammatical rules", "A programming language used for developing software", "A set of predefined words used in a specific context"},
            /*2*/    {"A sequence of letters from A to Z", "A finite set of symbols from which strings are constructed", "A collection of numbers used in mathematical calculations", "A set of rules for forming strings"},
            /*3*/    {"Hello", "1010", "ABC123", "x + y = z"},
            /*4*/    {"To define the meaning of words", "To specify how strings can be formed from the alphabet", "To translate strings into another language", "To check the spelling of words in a string"},
            /*5*/    {"English", "Python programming language", "Binary language", "Spanish"},
            /*6*/    {"Σ = {01}", "Σ = {AB}", "Σ = {0123456789}", "Σ = {a-z}"},
            /*7*/    {"They are used in mathematical equations", "They represent correctly nested structures", "They define the order of operations", "They are used to separate words in a sentence"},
            /*8*/    {"To define the syntax and semantics of the language", "To generate random strings", "To store data in memory", "To create user interfaces"},
            /*9*/    {"Physics", "Chemistry", "Automata theory", "History"},
            /*10*/   {"They define the graphical interface", "They parse and translate high-level code into machine code", "They manage memory allocation", "They create database connections"},
            /*11*/   {"Regular languages", "Context-free languages", "Context-sensitive languages", "Recursively enumerable languages"},
            /*12*/   {"A finite sequence of symbols from an alphabet", "An infinite sequence of numbers", "A random sequence of characters", "A paragraph of text"},
            /*13*/   {"By a natural language sentence", "By a set of rules or a formal machine", "By an image or a picture", "By a voice command"},
            /*14*/   {"To model and analyze the structure of natural languages", "To translate languages in real-time", "To generate natural language texts", "To learn new languages quickly"},
            /*15*/   {"Alphabet", "Grammar", "Syntax", "Translator"}
    };

    public static final int[] pre_test_lesson_4_answers = {
            /*1*/    1, // 'A set of strings of symbols defined by grammatical rules'
            /*2*/    1, // 'A finite set of symbols from which strings are constructed'
            /*3*/    1, // '1010'
            /*4*/    1, // 'To specify how strings can be formed from the alphabet'
            /*5*/    2, // 'Binary language'
            /*6*/    0, // 'Σ = {01}'
            /*7*/    1, // 'They represent correctly nested structures'
            /*8*/    0, // 'To define the syntax and semantics of the language'
            /*9*/    2, // 'Automata theory'
            /*10*/   1, // 'They parse and translate high-level code into machine code'
            /*11*/   3, // 'Recursively enumerable languages'
            /*12*/   0, // 'A finite sequence of symbols from an alphabet'
            /*13*/   1, // 'By a set of rules or a formal machine'
            /*14*/   0, // 'To model and analyze the structure of natural languages'
            /*15*/   3  // 'Translator'
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |        EASY          |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson4_Easy_Questions() {
        return e_Module_1.get_PostTest_EasyMedium_Questions(
                post_test_lesson_4_questions_easy,
                post_test_lesson_4_choices_easy,
                post_test_lesson_4_answers_easy,
                e_Question.Difficulty.EASY
        );
    }

    public static final String[] post_test_lesson_4_questions_easy = {
            /*1*/    "Automata theory is primarily concerned with physical machines and their construction.",
            /*2*/    "A Finite Automaton can have an infinite number of states.",
            /*3*/    "A Pushdown Automaton uses a stack as its additional memory.",
            /*4*/    "A Deterministic Finite Automaton (DFA) allows multiple transitions for a single input symbol from a given state.",
            /*5*/    "Turing Machines are capable of recognizing any language that a Finite Automaton can recognize.",
            /*6*/    "A Turing Machine can simulate any algorithm.",
            /*7*/    "The output of a Moore Machine depends only on its current state.",
            /*8*/    "A Pushdown Automaton can recognize context-free languages.",
            /*9*/    "In a Nondeterministic Finite Automaton (NFA), each state can have at most one transition for each input symbol.",
            /*10*/   "A Turing Machine has a finite tape for input and output.",
            /*11*/   "Finite Automata are used in the lexical analysis phase of compilers.",
            /*12*/   "A Mealy Machine's output depends only on its current state.",
            /*13*/   "Turing Machines can recognize recursively enumerable languages.",
            /*14*/   "Finite Automata are capable of recognizing context-sensitive languages.",
            /*15*/   "Automata theory is closely related to formal language theory."
    };

    public static final String[][] post_test_lesson_4_choices_easy = {
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

    public static final int[] post_test_lesson_4_answers_easy = {
            /*1*/    1, // False
            /*2*/    1, // False
            /*3*/    0, // True
            /*4*/    1, // False
            /*5*/    0, // True
            /*6*/    0, // True
            /*7*/    0, // True
            /*8*/    0, // True
            /*9*/    1, // False
            /*10*/   1, // False
            /*11*/   0, // True
            /*12*/   1, // False
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

    public static e_Question[] get_PostTest_Lesson4_Medium_Questions() {
        return e_Module_1.get_PostTest_EasyMedium_Questions(
                post_test_lesson_4_questions_medium,
                post_test_lesson_4_choices_medium,
                post_test_lesson_4_answers_medium,
                e_Question.Difficulty.MEDIUM
        );
    }

    public static final String[] post_test_lesson_4_questions_medium = {
            /*1*/    "What is the primary focus of automata theory?",
            /*2*/    "Which of the following automata uses a stack for memory?",
            /*3*/    "Which type of automaton can recognize regular languages?",
            /*4*/    "What is the key difference between a Deterministic Finite Automaton (DFA) and a Nondeterministic Finite Automaton (NFA)?",
            /*5*/    "Which automaton is considered the most powerful in terms of computational capabilities?",
            /*6*/    "Which automaton is typically used for lexical analysis in compilers?",
            /*7*/    "In a Moore Machine, what does the output depend on?",
            /*8*/    "Which type of automaton is used to recognize context-free languages?",
            /*9*/    "What type of language can a Turing Machine recognize?",
            /*10*/   "Which of the following is NOT a type of automaton?",
            /*11*/   "Which automaton can simulate any other computational device?",
            /*12*/   "Which automaton is best suited for recognizing patterns within input strings?",
            /*13*/   "What is the output of a Mealy Machine dependent on?",
            /*14*/   "Which of the following is used in the parsing phase of compilers?",
            /*15*/   "Which automaton is used in the Knuth-Morris-Pratt (KMP) string matching algorithm?"
    };

    public static final String[][] post_test_lesson_4_choices_medium = {
            /*1*/    {"The construction of physical machines", "The study of abstract machines and computational problems", "The development of programming languages", "The design of hardware components"},
            /*2*/    {"Finite Automaton (FA)", "Pushdown Automaton (PDA)", "Turing Machine (TM)", "Moore Machine"},
            /*3*/    {"Pushdown Automaton (PDA)", "Turing Machine (TM)", "Finite Automaton (FA)", "Mealy Machine"},
            /*4*/    {"DFA allows multiple transitions for each input symbol", "NFA allows multiple transitions for each input symbol", "DFA uses a stack for memory", "NFA has an infinite number of states"},
            /*5*/    {"Finite Automaton (FA)", "Pushdown Automaton (PDA)", "Turing Machine (TM)", "Moore Machine"},
            /*6*/    {"Finite Automaton (FA)", "Pushdown Automaton (PDA)", "Turing Machine (TM)", "Mealy Machine"},
            /*7*/    {"Input only", "Current state only", "Both input and current state", "Transition function"},
            /*8*/    {"Finite Automaton (FA)", "Turing Machine (TM)", "Pushdown Automaton (PDA)", "Moore Machine"},
            /*9*/    {"Regular languages", "Context-free languages", "Context-sensitive languages", "Recursively enumerable languages"},
            /*10*/   {"Finite Automaton (FA)", "Pushdown Automaton (PDA)", "Lexical Automaton (LA)", "Turing Machine (TM)"},
            /*11*/   {"Finite Automaton (FA)", "Pushdown Automaton (PDA)", "Turing Machine (TM)", "Mealy Machine"},
            /*12*/   {"Finite Automaton (FA)", "Pushdown Automaton (PDA)", "Turing Machine (TM)", "Moore Machine"},
            /*13*/   {"Input only", "Current state only", "Both input and current state", "Transition function only"},
            /*14*/   {"Finite Automaton (FA)", "Pushdown Automaton (PDA)", "Turing Machine (TM)", "Mealy Machine"},
            /*15*/   {"Finite Automaton (FA)", "Pushdown Automaton (PDA)", "Turing Machine (TM)", "Moore Machine"}
    };

    public static final int[] post_test_lesson_4_answers_medium = {
            /*1*/    1, // The study of abstract machines and computational problems
            /*2*/    1, // Pushdown Automaton (PDA)
            /*3*/    2, // Finite Automaton (FA)
            /*4*/    1, // NFA allows multiple transitions for each input symbol
            /*5*/    2, // Turing Machine (TM)
            /*6*/    0, // Finite Automaton (FA)
            /*7*/    1, // Current state only
            /*8*/    2, // Pushdown Automaton (PDA)
            /*9*/    3, // Recursively enumerable languages
            /*10*/   2, // Lexical Automaton (LA)
            /*11*/   2, // Turing Machine (TM)
            /*12*/   0, // Finite Automaton (FA)
            /*13*/   2, // Both input and current state
            /*14*/   1, // Pushdown Automaton (PDA)
            /*15*/   0  // Finite Automaton (FA)
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |         HARD         |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson4_Hard_Questions() {
        return e_Module_1.get_PostTest_Hard_Questions(
                post_test_lesson_4_questions_hard,
                post_test_lesson_4_answers_hard,
                e_Question.Difficulty.HARD
        );
    }

    public static final String[] post_test_lesson_4_questions_hard = {
            /*1*/    "Abstract machine in automata theory.",
            /*2*/    "Automaton with stack memory.",
            /*3*/    "Most powerful automaton.",
            /*4*/    "Deterministic automaton type.",
            /*5*/    "Automaton for lexical analysis.",
            /*6*/    "Automaton for context-free languages.",
            /*7*/    "Output depends on state.",
            /*8*/    "Recognizes regular languages.",
            /*9*/    "Simulates any algorithm.",
            /*10*/   "Finite state transitions.",
            /*11*/   "Context-free language recognizer.",
            /*12*/   "Uses infinite tape.",
            /*13*/   "Two-state automaton.",
            /*14*/   "Recognizes recursively enumerable languages.",
            /*15*/   "Input-driven automaton."
    };

    public static final String[] post_test_lesson_4_answers_hard = {
            /*1*/    "finite automaton",
            /*2*/    "pushdown automaton",
            /*3*/    "turing machine",
            /*4*/    "deterministic automaton",
            /*5*/    "finite automaton",
            /*6*/    "pushdown automaton",
            /*7*/    "moore machine",
            /*8*/    "finite automaton",
            /*9*/    "turing machine",
            /*10*/   "finite automaton",
            /*11*/   "pushdown automaton",
            /*12*/   "turing machine",
            /*13*/   "deterministic automaton",
            /*14*/   "turing machine",
            /*15*/   "mealy machine"
    };
}