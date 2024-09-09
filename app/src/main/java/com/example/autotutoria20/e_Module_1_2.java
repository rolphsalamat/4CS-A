package com.example.autotutoria20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class e_Module_1_2 {

    /*
    +----------------------+
    |       PRE-TEST       |
    |        LESSON 1      |
    +----------------------+
    */

    public static e_Question[] get_PreTest_Lesson2_Questions() {
        return e_Module_1.getPreTestQuestions(
                pre_test_lesson_2_questions,
                pre_test_lesson_2_choices,
                pre_test_lesson_2_answers
        );
    }

    public static final String[] pre_test_lesson_2_questions = {
            /*1*/     "What is an alphabet in the context of formal languages?",
            /*2*/     "Which of the following is a string over the alphabet Σ = {a, b}?",
            /*3*/     "What is the length of the string \"abc\" over the alphabet Σ = {a, b, c}?",
            /*4*/     "What is the empty string commonly denoted by?",
            /*5*/     "Which of the following operations on strings does the Kleene star (*) represent?",
            /*6*/     "What is the concatenation of the strings \"ab\" and \"cd\"?",
            /*7*/     "Which of the following is NOT a property of a regular language?",
            /*8*/     "Which machine recognizes regular languages?",
            /*9*/     "If Σ = {0, 1}, which of the following is a language over Σ?",
            /*10*/     "Which of the following best describes a deterministic finite automaton (DFA)?",
            /*11*/     "In automata theory, what does a transition function define?",
            /*12*/     "What does it mean if a string is \"accepted\" by a finite automaton?",
            /*13*/     "What is a context-free grammar (CFG) used to describe?",
            /*14*/     "Which of the following is true about a nondeterministic finite automaton (NFA)?",
            /*15*/     "Which of the following best describes a Turing machine?"
    };

    public static final String[][] pre_test_lesson_2_choices = {
            /*1*/      {"A sequence of symbols", "A set of symbols", "A single symbol", "A string of characters"},
            /*2*/      {"abba", "123", "ab12", "abb@"},
            /*3*/      {"1", "2", "3", "4"},
            /*4*/      {"ε", "Ø", "{}", "∅"},
            /*5*/      {"Reversal of the string", "Concatenation of strings", "Repetition of strings, including the empty string", "Trimming spaces from the string"},
            /*6*/      {"abcd", "cdab", "ab, cd", "ab cd"},
            /*7*/      {"It can be recognized by a finite automaton", "It can be described by a regular expression", "It can be recognized by a Turing machine", "It requires a context-free grammar to be generated"},
            /*8*/      {"Finite Automaton", "Pushdown Automaton", "Turing Machine", "Context-Free Grammar"},
            /*9*/      {"All binary strings with an even number of 0s", "All strings with at least one 2", "All strings that are palindromes", "All uppercase English letters"},
            /*10*/      {"A machine that can recognize context-free languages", "A machine that uses a stack to store information", "A machine with a finite number of states and transitions", "A machine that can only process binary numbers"},
            /*11*/       {"The start state of the automaton", "The set of final states", "The mapping of states and input symbols to new states", "The length of the input string"},
            /*12*/       {"The string is too long", "The string is recognized by the automaton", "The string is invalid", "The automaton stops processing"},
            /*13*/       {"Regular languages", "Context-free languages", "Turing machines", "Finite automata"},
            /*14*/       {"It has only one possible transition for each state and input symbol", "It can have multiple possible transitions for each state and input symbol", "It cannot be converted to a DFA", "It is slower than a DFA"},
            /*15*/       {"A machine that can recognize regular languages", "A machine that can recognize recursively enumerable languages", "A machine with finite memory and states", "A machine that only processes strings of even length"}
    };

    public static final int[] pre_test_lesson_2_answers = {
            /*1*/       1, // 'A set of symbols'
            /*2*/       0, // 'abba'
            /*3*/       2, // '3'
            /*4*/       0, // 'ε'
            /*5*/       2, // 'Repetition of strings, including the empty string'
            /*6*/       0, // 'abcd'
            /*7*/       3, // 'It requires a context-free grammar to be generated'
            /*8*/       0, // 'Finite Automaton'
            /*9*/       0, // 'All binary strings with an even number of 0s'
            /*10*/       2, // 'A machine with a finite number of states and transitions'
            /*11*/       2, // 'The mapping of states and input symbols to new states'
            /*12*/       1, // 'The string is recognized by the automaton'
            /*13*/       1, // 'Context-free languages'
            /*14*/       1, // 'It can have multiple possible transitions for each state and input symbol'
            /*15*/       1  // 'A machine that can recognize recursively enumerable languages'
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |          EASY        |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson2_Easy_Questions() {
        return e_Module_1.get_PostTest_EasyMedium_Questions(
                post_test_lesson_2_questions_easy,
                post_test_lesson_2_choices_easy,
                post_test_lesson_2_answers_easy,
                e_Question.Difficulty.EASY
        );
    }

    public static final String[] post_test_lesson_2_questions_easy = {
            /*1*/      "An alphabet is a finite non-empty set of symbols.",
            /*2*/      "The symbol Σ is commonly used to denote a string.",
            /*3*/      "The binary alphabet consists of the symbols {0, 1}.",
            /*4*/      "A string can have an infinite number of symbols.",
            /*5*/      "The length of the string \"abc\" is 3.",
            /*6*/      "The concatenation of the strings \"ab\" and \"c\" results in \"cab\".",
            /*7*/      "The reversal of the string \"racecar\" is \"racecar\".",
            /*8*/      "A string \"u\" is a prefix of string \"w\" if w = uv for some string v.",
            /*9*/      "A suffix of the string \"abc\" could be \"bc\".",
            /*10*/      "The set of all possible strings over an alphabet Σ is denoted by Σ+.",
            /*11*/      "The length of the empty string ε is 1.",
            /*12*/     "In formal languages, the alphabet {A, C, G, T} is commonly associated with DNA sequences.",
            /*13*/      "The string \"ba\" is a valid string over the alphabet Σ = {a, b}.",
            /*14*/      "A string \"v\" is a substring of \"w\" if w = u1vu2 for some strings u1 and u2.",
            /*15*/      "Every string over the alphabet {0, 1} that starts with '1' and ends with '0' is a regular language."
    };

    public static final String[][] post_test_lesson_2_choices_easy = {
            /*1*/      {"True", "False"},
            /*2*/      {"True", "False"},
            /*3*/      {"True", "False"},
            /*4*/      {"True", "False"},
            /*5*/      {"True", "False"},
            /*6*/      {"True", "False"},
            /*7*/      {"True", "False"},
            /*8*/      {"True", "False"},
            /*9*/      {"True", "False"},
            /*10*/     {"True", "False"},
            /*11*/     {"True", "False"},
            /*12*/     {"True", "False"},
            /*13*/     {"True", "False"},
            /*14*/     {"True", "False"},
            /*15*/     {"True", "False"}
    };

    public static final int[] post_test_lesson_2_answers_easy = {
            /*1*/      0, // True
            /*2*/      1, // False
            /*3*/      0, // True
            /*4*/      1, // False
            /*5*/      0, // True
            /*6*/      1, // False
            /*7*/      0, // True
            /*8*/      0, // True
            /*9*/      0, // True
            /*10*/      1, // False
            /*11*/     1, // False
            /*12*/     0, // True
            /*13*/     0, // True
            /*14*/     0, // True
            /*15*/     0  // True
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |        MEDIUM        |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson2_Medium_Questions() {
        return e_Module_1.get_PostTest_EasyMedium_Questions(
                post_test_lesson_2_questions_medium,
                post_test_lesson_2_choices_medium,
                post_test_lesson_2_answers_medium,
                e_Question.Difficulty.MEDIUM
        );
    }

    public static final String[] post_test_lesson_2_questions_medium = {
            /*1*/     "What is an alphabet in the context of formal languages?",
            /*2*/     "What symbol is typically used to denote an alphabet?",
            /*3*/     "Which of the following is an example of a binary alphabet?",
            /*4*/     "What is a string in the context of formal languages?",
            /*5*/     "How is the empty string denoted?",
            /*6*/     "What does the notation Σ (Sigma star) represent?",
            /*7*/     "What is the length of the string \"aab\"?",
            /*8*/     "Which of the following is a valid concatenation of the strings \"ab\" and \"c\"?",
            /*9*/     "What operation combines two strings end-to-end to form a new string?",
            /*10*/      "Which of the following is a prefix of the string \"abc\"?",
            /*11*/      "Which of the following is a suffix of the string \"abc\"?",
            /*12*/      "Which of the following is a substring of the string \"abc\"?",
            /*13*/      "What is the reversal of the string \"racecar\"?",
            /*14*/      "What does the notation |w| represent for a string w?",
            /*15*/      "Which of the following correctly represents the empty string's length?"
    };

    public static final String[][] post_test_lesson_2_choices_medium = {
            /*1*/      {"A set of numbers", "A finite non-empty set of symbols", "A sequence of symbols", "A string of characters"},
            /*2*/       {"Λ (Lambda)", "Σ (Sigma)", "Ω (Omega)", "ε (Epsilon)"},
            /*3*/       {"Σ = {A, C, G, T}", "Σ = {0, 1}", "Σ = {a, b, c}", "Σ = {1, 2, 3}"},
            /*4*/       {"A set of numbers", "A sequence of digits", "A finite sequence of symbols drawn from a given alphabet", "A non-empty set of characters"},
            /*5*/       {"Σ", "Λ", "ε", "Ω"},
            /*6*/       {"The set of all possible symbols in an alphabet", "The set of all possible strings over an alphabet including the empty string", "The set of all prefixes of a string", "The set of all suffixes of a string"},
            /*7*/        {"1", "2", "3", "4"},
            /*8*/        {"ba", "cab", "abc", "acb"},
            /*9*/        {"Subtraction", "Concatenation", "Division", "Prefixing"},
            /*10*/       {"bc", "c", "a", "ac"},
            /*11*/       {"a", "ab", "c", "ba"},
            /*12*/       {"ac", "ba", "b", "ca"},
            /*13*/      {"racecar", "carrace", "racearc", "racerac"},
            /*14*/      {"The alphabet of w", "The length of w", "The reverse of w", "The concatenation of w with itself"},
            /*15*/     {"|ε| = 1", "|ε| = 0", "|ε| = 2", "|ε| = ε"}
    };

    public static final int[] post_test_lesson_2_answers_medium = {
            /*1*/     1, // A finite non-empty set of symbols
            /*2*/     1, // Σ (Sigma)
            /*3*/     1, // Σ = {0, 1}
            /*4*/     2, // A finite sequence of symbols drawn from a given alphabet
            /*5*/     2, // ε
            /*6*/     1, // The set of all possible strings over an alphabet including the empty string
            /*7*/      2, // 3
            /*8*/      2, // abc
            /*9*/      1, // Concatenation
            /*10*/      2, // a
            /*11*/      2, // c
            /*12*/      2, // b
            /*13*/      0, // racecar
            /*14*/      1, // The length of w
            /*15*/      1  // |ε| = 0
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |         HARD         |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson2_Hard_Questions() {
        return e_Module_1.get_PostTest_Hard_Questions(
                post_test_lesson_2_questions_hard,
                post_test_lesson_2_answers_hard,
                e_Question.Difficulty.HARD
        );
    }
    public static final String[] post_test_lesson_2_questions_hard = {
            /*1*/      "What is the finite non-empty set of symbols called?",
            /*2*/      "What symbol typically denotes an alphabet?",
            /*3*/      "What is a finite sequence of symbols drawn from an alphabet?",
            /*4*/      "What denotes the empty string?",
            /*5*/      "What is the operation that combines two strings end-to-end?",
            /*6*/      "What symbol denotes the set of all possible strings over an alphabet?",
            /*7*/      "What denotes the length of a string?",
            /*8*/      "What is the reversal of a string denoted by?",
            /*9*/      "What is a string that comes before another string?",
            /*10*/     "What is a string that comes after another string?",
            /*11*/     "What is a string that is part of another string?",
            /*12*/     "What is the length of the empty string?",
            /*13*/     "What are the characters in the binary alphabet?",
            /*14*/     "What set is {A, C, G, T} an example of?",
            /*15*/     "What does the concatenation of 'ab' and 'c' result in?"
    };

    public static final String[] post_test_lesson_2_answers_hard = {
            /*1*/      "alphabet symbols",
            /*2*/      "sigma",
            /*3*/      "string sequence",
            /*4*/      "epsilon",
            /*5*/      "string concatenation",
            /*6*/      "sigma star",
            /*7*/      "string length",
            /*8*/      "reversal",
            /*9*/      "prefix string",
            /*10*/     "suffix string",
            /*11*/     "substring string",
            /*12*/     "zero length",
            /*13*/     "0 and 1",
            /*14*/     "DNA alphabet",
            /*15*/     "string abc"
    };
}