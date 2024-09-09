package com.example.autotutoria20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class e_Module_4_1 {

    /*
    +----------------------+
    |       PRE-TEST       |
    |        LESSON 1      |
    +----------------------+
    */

    public static e_Question[] get_PreTest_Lesson1_Questions() {
        return e_Module_4.getPreTestQuestions(
                pre_test_lesson_1_questions,
                pre_test_lesson_1_choices,
                pre_test_lesson_1_answers
        );
    }

    public static final String[] pre_test_lesson_1_questions = {
            /*1*/    "What machine uses a finite number of states to recognize regular languages?",
            /*2*/    "Which language requires matching opening and closing parentheses?",
            /*3*/    "What is used to prove that a language is not regular?",
            /*4*/    "What kind of machine uses a stack for memory?",
            /*5*/    "Which language cannot be described by regular expressions?",
            /*6*/    "What machine is the most powerful in recognizing languages?",
            /*7*/    "Which language reads the same forward and backward?",
            /*8*/    "Which theorem distinguishes between regular and non-regular languages?",
            /*9*/    "What is needed to recognize context-free languages?",
            /*10*/   "What grammar generates languages with nested structures?",
            /*11*/   "Which machine has finite memory and states?",
            /*12*/   "What kind of language includes strings like 'aabb'?",
            /*13*/   "What machine can recognize non-regular languages?",
            /*14*/   "Which machine uses states to determine if a string belongs to a language?",
            /*15*/   "Which computational model can handle strings with nested patterns?"
    };

    public static final String[][] pre_test_lesson_1_choices = {
            /*1*/    {"Finite Automaton", "Pushdown Automaton", "Turing Machine", "Context-free Grammar"},
            /*2*/    {"Equal symbols", "Palindromes", "Balanced Parentheses", "Finite Languages"},
            /*3*/    {"Turing Machine", "Pushdown Automaton", "Pumping Lemma", "Finite Automaton"},
            /*4*/    {"Finite Automaton", "Pushdown Automaton", "Context-free Grammar", "Regular Grammar"},
            /*5*/    {"Regular Language", "Finite Language", "Non-regular Language", "Palindromes"},
            /*6*/    {"Finite Automaton", "Turing Machine", "Pushdown Automaton", "Context-free Grammar"},
            /*7*/    {"Balanced Parentheses", "Finite Language", "Regular Language", "Palindromes"},
            /*8*/    {"Myhill-Nerode Theorem", "Pumping Lemma", "Context-free Grammar", "Turing Machine"},
            /*9*/    {"Finite Automaton", "Pushdown Automaton", "Turing Machine", "Regular Expression"},
            /*10*/   {"Regular Grammar", "Finite Grammar", "Context-free Grammar", "Palindrome Grammar"},
            /*11*/   {"Pushdown Automaton", "Turing Machine", "Finite Automaton", "None of the above"},
            /*12*/   {"Palindromes", "Balanced Parentheses", "Equal symbols", "Regular Language"},
            /*13*/   {"Turing Machine", "Finite Automaton", "Regular Grammar", "None of the above"},
            /*14*/   {"Pushdown Automaton", "Finite Automaton", "Turing Machine", "Context-free Grammar"},
            /*15*/   {"Finite Automaton", "Regular Grammar", "Context-free Grammar", "None of the above"}
    };

    public static final int[] pre_test_lesson_1_answers = {
            /*1*/    0, // 'Finite Automaton'
            /*2*/    2, // 'Balanced Parentheses'
            /*3*/    2, // 'Pumping Lemma'
            /*4*/    1, // 'Pushdown Automaton'
            /*5*/    2, // 'Non-regular Language'
            /*6*/    1, // 'Turing Machine'
            /*7*/    3, // 'Palindromes'
            /*8*/    0, // 'Myhill-Nerode Theorem'
            /*9*/    1, // 'Pushdown Automaton'
            /*10*/   2, // 'Context-free Grammar'
            /*11*/   2, // 'Finite Automaton'
            /*12*/   2, // 'Equal symbols'
            /*13*/   0, // 'Turing Machine'
            /*14*/   1, // 'Finite Automaton'
            /*15*/   2  // 'Context-free Grammar'
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |        EASY          |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson1_Easy_Questions() {
        return e_Module_4.get_PostTest_EasyMedium_Questions(
                post_test_lesson_1_questions_easy,
                post_test_lesson_1_choices_easy,
                post_test_lesson_1_answers_easy,
                e_Question.Difficulty.EASY
        );
    }

    public static final String[] post_test_lesson_1_questions_easy = {
            /*1*/    "A Context-Free Grammar (CFG) consists of a set of production rules used to generate strings in a formal language.",
            /*2*/    "Terminals in a CFG represent placeholders that can be replaced by non-terminals.",
            /*3*/    "A start symbol in a CFG represents the entire language and is always a non-terminal symbol.",
            /*4*/    "Context-Free Grammars can describe languages that regular expressions cannot.",
            /*5*/    "In a CFG non-terminals are the actual characters used to form strings.",
            /*6*/    "CFGs are used to describe the syntax of both natural and programming languages.",
            /*7*/    "Production rules in a CFG always replace non-terminals with terminals.",
            /*8*/    "CFGs are less powerful than regular languages and can only describe simple structures.",
            /*9*/    "A CFG can generate an infinite number of strings if the production rules allow recursion.",
            /*10*/   "In the CFG for balanced parentheses the production rule 'S → (S)' helps generate pairs of parentheses.",
            /*11*/   "The production rule 'S → ε' in a CFG means that the string can terminate without producing any more symbols.",
            /*12*/   "The terminals in a CFG are the symbols that represent variables in programming languages.",
            /*13*/   "A CFG can describe both arithmetic expressions and balanced parentheses.",
            /*14*/   "CFGs cannot generate strings with patterns like equal numbers of 'a's followed by 'b's.",
            /*15*/   "The start symbol in a CFG can only be replaced by terminals directly, not by non-terminals."
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
            /*1*/    0, // 'True'
            /*2*/    1, // 'False'
            /*3*/    0, // 'True'
            /*4*/    0, // 'True'
            /*5*/    1, // 'False'
            /*6*/    0, // 'True'
            /*7*/    1, // 'False'
            /*8*/    1, // 'False'
            /*9*/    0, // 'True'
            /*10*/   0, // 'True'
            /*11*/   0, // 'True'
            /*12*/   1, // 'False'
            /*13*/   0, // 'True'
            /*14*/   1, // 'False'
            /*15*/   1  // 'False'
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |        MEDIUM        |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson1_Medium_Questions() {
        return e_Module_4.get_PostTest_EasyMedium_Questions(
                post_test_lesson_1_questions_medium,
                post_test_lesson_1_choices_medium,
                post_test_lesson_1_answers_medium,
                e_Question.Difficulty.MEDIUM

        );
    }

    public static final String[] post_test_lesson_1_questions_medium = {
            /*1*/    "What does a Context-Free Grammar (CFG) primarily consist of?",
            /*2*/    "Which of the following is a terminal in a CFG?",
            /*3*/    "What does the start symbol in a CFG represent?",
            /*4*/    "Which of the following is NOT a component of a CFG?",
            /*5*/    "In the CFG 'S → aSb | ε' what does 'ε' represent?",
            /*6*/    "Which of the following languages can a CFG describe?",
            /*7*/    "What is the role of production rules in a CFG?",
            /*8*/    "In a CFG what does a non-terminal symbol represent?",
            /*9*/    "Which of the following is an example of a CFG generating a string of balanced parentheses?",
            /*10*/   "What type of language can CFGs describe that regular languages cannot?",
            /*11*/   "In a CFG for arithmetic expressions which component is likely to be a non-terminal?",
            /*12*/   "What does the production rule 'S → aSb' generate?",
            /*13*/   "Which of the following is a valid string generated by the CFG 'S → aSb | ε'?",
            /*14*/   "What kind of recursion is used in the CFG 'S → aSb'?",
            /*15*/   "Which of the following production rules is used to end the recursion in a CFG?"
    };

    public static final String[][] post_test_lesson_1_choices_medium = {
            /*1*/    {"Terminals, Non-terminals, Production rules, and Start symbol", "Only Terminals and Non-terminals", "Only Start symbols and Production rules", "Terminals and Variables"},
            /*2*/    {"A non-terminal symbol", "An actual character in the language", "A placeholder for a set of rules", "The start symbol"},
            /*3*/    {"A sequence of terminals", "The entire language to be generated", "A terminal symbol", "A production rule"},
            /*4*/    {"Terminals", "Non-terminals", "Syntax trees", "Production rules"},
            /*5*/    {"An empty string", "A terminal", "A non-terminal", "A recursive symbol"},
            /*6*/    {"Arithmetic expressions", "Balanced parentheses", "Both a and b", "None of the above"},
            /*7*/    {"To specify how terminals and non-terminals can be replaced", "To enforce syntax errors", "To check for valid input", "To define the length of strings"},
            /*8*/    {"A final character in the string", "A symbol that can be replaced by terminals or other non-terminals", "A special operator", "An undefined variable"},
            /*9*/    {"S → (S)", "S → (S) | ε", "S → (S) | SS | ε", "S → (())"},
            /*10*/   {"Languages with balanced parentheses", "Languages with a fixed number of symbols", "Simple languages with no recursion", "Languages with finite patterns"},
            /*11*/   {"A number", "An operator like '+' or '*'", "An expression", "A parenthesis"},
            /*12*/   {"Strings with an equal number of 'a's and 'b's", "Strings with more 'a's than 'b's", "Strings with more 'b's than 'a's", "Strings with only 'a's"},
            /*13*/   {"ab", "aab", "aabb", "aaabbb"},
            /*14*/   {"Left recursion", "Right recursion", "Direct recursion", "Indirect recursion"},
            /*15*/   {"S → aSb", "S → SS", "S → ε", "S → SSS"}
    };

    public static final int[] post_test_lesson_1_answers_medium = {
            /*1*/    0, // 'Terminals, Non-terminals, Production rules, and Start symbol'
            /*2*/    1, // 'An actual character in the language'
            /*3*/    1, // 'The entire language to be generated'
            /*4*/    2, // 'Syntax trees'
            /*5*/    0, // 'An empty string'
            /*6*/    2, // 'Both a and b'
            /*7*/    0, // 'To specify how terminals and non-terminals can be replaced'
            /*8*/    1, // 'A symbol that can be replaced by terminals or other non-terminals'
            /*9*/    2, // 'S → (S) | SS | ε'
            /*10*/   0, // 'Languages with balanced parentheses'
            /*11*/   2, // 'An expression'
            /*12*/   0, // 'Strings with an equal number of 'a's and 'b's'
            /*13*/   2, // 'aabb'
            /*14*/   1, // 'Right recursion'
            /*15*/   2  // 'S → ε'
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |         HARD         |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson1_Hard_Questions() {
        return e_Module_4.get_PostTest_Hard_Questions(
                post_test_lesson_1_questions_hard,
                post_test_lesson_1_answers_hard,
                e_Question.Difficulty.HARD
        );
    }

    public static final String[] post_test_lesson_1_questions_hard = {
            /*1*/    "What is the component of a CFG that consists of symbols representing sets of strings?",
            /*2*/    "What is the actual symbol that forms strings in a CFG?",
            /*3*/    "Which symbol in CFG represents the entire language from where derivation begins?",
            /*4*/    "What are the rules called that define how symbols in a CFG are replaced?",
            /*5*/    "What term describes a CFG rule that ends the derivation process?",
            /*6*/    "What is the name for symbols that are replaced during the derivation process?",
            /*7*/    "What type of formal grammar is used to describe programming syntax?",
            /*8*/    "What kind of grammar can generate balanced parentheses?",
            /*9*/    "What is the rule in CFG called that allows for recursion to terminate?",
            /*10*/   "What is the term for characters like parentheses or numbers in a CFG?",
            /*11*/   "What kind of recursion happens when the non-terminal appears at the right end of the production rule?",
            /*12*/   "What is the formal grammar used for describing the syntax of structured data?",
            /*13*/   "What is a rule called when a non-terminal can be replaced by two symbols?",
            /*14*/   "What type of CFG generates equal numbers of 'a's followed by 'b's?",
            /*15*/   "What CFG component is responsible for creating valid strings?"
    };

    public static final String[] post_test_lesson_1_answers_hard = {
            /*1*/    "non-terminals",
            /*2*/    "terminal symbols",
            /*3*/    "start symbol",
            /*4*/    "production rules",
            /*5*/    "empty string",
            /*6*/    "non-terminal",
            /*7*/    "context-free grammar",
            /*8*/    "context-free grammar",
            /*9*/    "base case",
            /*10*/   "terminal symbols",
            /*11*/   "right recursion",
            /*12*/   "context-free grammar",
            /*13*/   "binary production",
            /*14*/   "context-free grammar",
            /*15*/   "production rules"
    };

}