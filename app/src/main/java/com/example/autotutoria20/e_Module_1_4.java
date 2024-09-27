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
            /*1*/    "What is the main function of grammar in a formal language?",
            /*2*/    "What is an example of a formal language used in computer science?",
            /*3*/    "What is the primary component required to build strings in a formal language?",
            /*4*/    "Which of the following operations can be applied to a string in a formal language?",
            /*5*/    "In the binary alphabet Σ = {0,1}, which of the following is a valid string?",
            /*6*/    "What is a common application of formal languages in software development?",
            /*7*/    "What does it mean for a string to 'belong' to a formal language?",
            /*8*/    "What is the role of automata in relation to formal languages?",
            /*9*/    "Which of the following is an example of a formal language used in natural language processing (NLP)?",
            /*10*/   "If the alphabet is Σ = {a, b}, what is a valid string?",
            /*11*/   "Which of the following statements is TRUE about formal languages?",
            /*12*/   "In automata theory, how are formal languages typically represented?",
            /*13*/   "Which field does NOT commonly apply formal languages?",
            /*14*/   "How is the length of a string defined in formal language theory?",
            /*15*/   "What is one of the most well-known hierarchies that classify formal languages?"
    };

    public static final String[][] pre_test_lesson_4_choices = {
            /*1*/    {"To define the structure and rules for forming valid strings", "To determine the length of a string", "To reverse a string", "To check if a string is empty"},
            /*2*/    {"Python code", "Spoken English", "Musical notes", "Traffic signals"},
            /*3*/    {"Numbers", "Symbols from the alphabet", "Words", "Punctuation marks"},
            /*4*/    {"Adding numbers", "Multiplying symbols", "Concatenating strings", "Subtracting alphabets"},
            /*5*/    {"\"0\"", "\"2\"", "\"A1\"", "\"01A\""},
            /*6*/    {"Writing novels", "Designing user interfaces", "Defining the syntax of programming languages", "Measuring system performance"},
            /*7*/    {"It follows the rules of the language", "It contains numbers only", "It is longer than 10 characters", "It can be used in any sentence"},
            /*8*/    {"To design games", "To recognize or accept valid strings", "To create random strings", "To translate human languages"},
            /*9*/    {"The English alphabet", "Regular expressions", "Spoken sentences", "Computer hardware"},
            /*10*/   {"\"ba\"", "\"123\"", "\"A1B\"", "\"\" (empty string)"},
            /*11*/   {"They consist of infinite symbols", "They are only used in mathematical computations", "They have clearly defined grammatical rules", "They cannot be applied in real-world problems"},
            /*12*/   {"Through algorithms only", "As a set of rules or by a formal machine", "As spoken languages", "As numbers and mathematical expressions"},
            /*13*/   {"Linguistics", "Computer science", "Physics", "Accounting"},
            /*14*/   {"By the number of vowels in the string", "By the number of symbols it contains", "By its complexity", "By its grammar rules"},
            /*15*/   {"Boolean hierarchy", "Von Neumann hierarchy", "Chomsky hierarchy", "Aristotle hierarchy"}
    };

    public static final int[] pre_test_lesson_4_answers = {
            /*1*/    0, // "To define the structure and rules for forming valid strings"
            /*2*/    0, // "Python code"
            /*3*/    1, // "Symbols from the alphabet"
            /*4*/    2, // "Concatenating strings"
            /*5*/    0, // "0"
            /*6*/    2, // "Defining the syntax of programming languages"
            /*7*/    0, // "It follows the rules of the language"
            /*8*/    1, // "To recognize or accept valid strings"
            /*9*/    1, // "Regular expressions"
            /*10*/   0, // "ba"
            /*11*/   2, // "They have clearly defined grammatical rules"
            /*12*/   1, // "As a set of rules or by a formal machine"
            /*13*/   3, // "Accounting"
            /*14*/   1, // "By the number of symbols it contains"
            /*15*/   2  // "Chomsky hierarchy"
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |         EASY         |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson4_Easy_Questions() {
        return e_Module_1.get_PostTest_EasyMedium_Questions(
                post_test_lesson_4_easy_questions,
                post_test_lesson_4_easy_choices,
                post_test_lesson_4_answers_easy,
                e_Question.Difficulty.EASY
        );
    }

    public static final String[] post_test_lesson_4_easy_questions = {
            /*1*/    "Automata theory focuses on the study of abstract machines and the problems they can solve.",
            /*2*/    "A deterministic finite automaton (DFA) allows multiple transitions for the same symbol in the alphabet.",
            /*3*/    "In theoretical computer science, automata theory is completely unrelated to formal language theory.",
            /*4*/    "A Moore machine is an automaton where the output depends only on the states of the machine.",
            /*5*/    "Pushdown automata are used to recognize regular languages.",
            /*6*/    "Finite automata are capable of recognizing context-free languages.",
            /*7*/    "Turing machines can simulate any algorithm.",
            /*8*/    "In a Mealy machine, the output depends only on the state of the machine.",
            /*9*/    "A finite automaton can have an infinite number of states.",
            /*10*/   "Automata theory has applications in natural language processing.",
            /*11*/   "A nondeterministic finite automaton (NFA) can have zero or multiple transitions for each symbol in the alphabet.",
            /*12*/   "The output in a Turing machine is directly related to the state and input combination.",
            /*13*/   "Automata without memory are called Moore machines.",
            /*14*/   "Lexical analysis in compilers is performed using finite automata.",
            /*15*/   "Model checking is an application of automata theory in verifying the correctness of hardware systems."
    };

    public static final String[][] post_test_lesson_4_easy_choices = {
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
            /*1*/    0, // True
            /*2*/    1, // False
            /*3*/    1, // False
            /*4*/    0, // True
            /*5*/    1, // False
            /*6*/    1, // False
            /*7*/    0, // True
            /*8*/    1, // False
            /*9*/    1, // False
            /*10*/   0, // True
            /*11*/   0, // True
            /*12*/   0, // True
            /*13*/   1, // False
            /*14*/   0, // True
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
            /*1*/    "Which of the following automata has the highest computational power?",
            /*2*/    "Which type of automaton is most used to recognize regular languages?",
            /*3*/    "Why is the Turing machine considered more powerful than a finite automaton?",
            /*4*/    "In a pushdown automaton, what structure is used to store information during computation?",
            /*5*/    "Which machine's output depends solely on the current state?",
            /*6*/    "Which of the following problems can be solved by a Turing machine but not by a finite automaton?",
            /*7*/    "What distinguishes a deterministic finite automaton (DFA) from a nondeterministic finite automaton (NFA)?",
            /*8*/    "In automata theory, which machine is used to recognize context-free languages?",
            /*9*/    "What is the primary advantage of using a Mealy machine over a Moore machine?",
            /*10*/   "Which of the following is an example of an application of finite automata?",
            /*11*/   "Why are Turing machines fundamental in theoretical computer science?",
            /*12*/   "What is the function of the transition table in a finite automaton?",
            /*13*/   "What kind of machine would you use to recognize patterns like 'balanced parentheses'?",
            /*14*/   "Which of the following languages cannot be recognized by a finite automaton?",
            /*15*/   "Which automaton is used in model checking to verify the correctness of software systems?"
    };

    public static final String[][] post_test_lesson_4_choices_medium = {
            /*1*/    {"Finite Automaton", "Pushdown Automaton", "Turing Machine", "Nondeterministic Finite Automaton"},
            /*2*/    {"Pushdown Automaton", "Finite Automaton", "Turing Machine", "Moore Machine"},
            /*3*/    {"It has a stack for memory", "It has an infinite tape and can simulate any algorithm", "It recognizes regular languages", "It can operate with multiple states simultaneously"},
            /*4*/    {"Queue", "Tape", "Stack", "Register"},
            /*5*/    {"Mealy Machine", "Moore Machine", "Turing Machine", "Pushdown Automaton"},
            /*6*/    {"String matching", "Palindrome recognition", "Arithmetic operations on large numbers", "Recognizing regular expressions"},
            /*7*/    {"DFA has multiple transitions per symbol", "NFA can have zero, one, or multiple transitions per symbol", "DFA can use a stack for additional memory", "DFA can only recognize context-free languages"},
            /*8*/    {"Finite Automaton", "Pushdown Automaton", "Moore Machine", "Turing Machine"},
            /*9*/    {"Less state complexity", "Output depends on the input and the current state", "More efficient for regular language recognition", "Can be used to recognize context-free languages"},
            /*10*/   {"Palindrome checking", "Lexical analysis in compilers", "Context-free grammar parsing", "Universal computation"},
            /*11*/   {"They simulate only finite automata", "They can simulate any algorithm", "They only recognize context-free languages", "They require a memory stack to operate"},
            /*12*/   {"To store input alphabets", "To define state transitions based on input", "To execute output commands", "To record final states"},
            /*13*/   {"Finite Automaton", "Mealy Machine", "Pushdown Automaton", "Turing Machine"},
            /*14*/   {"Palindromes", "Binary strings ending with '01'", "Regular expressions", "Strings with an odd number of 'a's"},
            /*15*/   {"Finite Automaton", "Pushdown Automaton", "Turing Machine", "Moore Machine"}
    };

    public static final int[] post_test_lesson_4_answers_medium = {
            /*1*/    2, // Turing Machine
            /*2*/    1, // Finite Automaton
            /*3*/    1, // It has an infinite tape and can simulate any algorithm
            /*4*/    2, // Stack
            /*5*/    1, // Moore Machine
            /*6*/    2, // Arithmetic operations on large numbers
            /*7*/    1, // NFA can have zero, one, or multiple transitions per symbol
            /*8*/    1, // Pushdown Automaton
            /*9*/    1, // Output depends on the input and the current state
            /*10*/   1, // Lexical analysis in compilers
            /*11*/   1, // They can simulate any algorithm
            /*12*/   1, // To define state transitions based on input
            /*13*/   2, // Pushdown Automaton
            /*14*/   0, // Palindromes
            /*15*/   2  // Turing Machine
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
            /*1*/    "What automaton uses a stack to provide additional memory?",
            /*2*/    "Which machine can recognize recursively enumerable languages?",
            /*3*/    "What is the term for the finite sequence of symbols that an automaton processes?",
            /*4*/    "Which type of automaton can have multiple transitions for a single input symbol?",
            /*5*/    "What is the concept of moving from one state to another based on input symbols?",
            /*6*/    "What is the formal model used to define the syntax and structure of programming languages?",
            /*7*/    "Which automaton's output is determined only by the current state and not the input?",
            /*8*/    "What is the fundamental difference between a DFA and an NFA?",
            /*9*/    "What do we call the collection of rules that determine the next state of an automaton?",
            /*10*/   "Which machine is used to simulate any computational problem solvable by an algorithm?",
            /*11*/   "What component does a pushdown automaton use to handle nested structures?",
            /*12*/   "Which phase of compiler design uses finite automata to break down input code?",
            /*13*/   "What kind of language can be recognized by a finite automaton?",
            /*14*/   "Which machine is used in the verification of hardware and software system correctness?",
            /*15*/   "What term describes the set of all possible inputs for an automaton?"
    };

    public static final String[] post_test_lesson_4_answers_hard = {
            /*1*/    "pushdown automaton",
            /*2*/    "turing machine",
            /*3*/    "input string",
            /*4*/    "nondeterministic automaton",
            /*5*/    "state transition",
            /*6*/    "formal grammar",
            /*7*/    "moore machine",
            /*8*/    "transition function",
            /*9*/    "transition table",
            /*10*/   "turing machine",
            /*11*/   "memory stack",
            /*12*/   "lexical analysis",
            /*13*/   "regular language",
            /*14*/   "model checking",
            /*15*/   "input alphabet"
    };
}
