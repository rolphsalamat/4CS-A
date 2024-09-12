package com.example.autotutoria20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class e_Module_3_2 {

    /*
    +----------------------+
    |       PRE-TEST       |
    |        LESSON 2      |
    +----------------------+
    */

    public static e_Question[] get_PreTest_Lesson2_Questions() {
        return e_Module_3.getPreTestQuestions(
                pre_test_lesson_2_questions,
                pre_test_lesson_2_choices,
                pre_test_lesson_2_answers
        );
    }

    public static final String[] pre_test_lesson_2_questions = {
            /*1*/    "Which of the following is a limitation of regular languages?",
            /*2*/    "The Pumping Lemma is primarily used to:",
            /*3*/    "What does the 'pumping' in Pumping Lemma refer to?",
            /*4*/    "If a language fails the Pumping Lemma test it is:",
            /*5*/    "What does the Pumping Lemma help prove?",
            /*6*/    "The pumping length (p) in the Pumping Lemma refers to:",
            /*7*/    "Regular languages cannot handle:",
            /*8*/    "The statement of the Pumping Lemma involves dividing a string into:",
            /*9*/    "Which of the following is true for regular languages?",
            /*10*/   "The Pumping Lemma can be used to show that a language is:",
            /*11*/   "Finite automata cannot recognize languages with:",
            /*12*/   "The Pumping Lemma can be applied to strings that are:",
            /*13*/   "Which of the following structures cannot be handled by regular languages?",
            /*14*/   "If a language satisfies the Pumping Lemma, it is:",
            /*15*/   "What is the role of the Pumping Lemma?"
    };

    public static final String[][] pre_test_lesson_2_choices = {
            /*1*/    {"Finite memory", "Infinite memory", "Ability to handle nested structures", "None of the above"},
            /*2*/    {"Prove a language is context-free", "Prove a language is regular", "Prove a language is not regular", "Prove a language is finite"},
            /*3*/    {"Compressing a string", "Expanding a string by repeating a section", "Deleting parts of a string", "Reversing a string"},
            /*4*/    {"Context-free", "Regular", "Not regular", "Finite"},
            /*5*/    {"Context-free languages", "Turing completeness", "Regular language limitations", "Machine learning models"},
            /*6*/    {"The maximum length of any regular string", "A positive integer such that any string of length greater than p can be pumped", "The length of finite automata", "A non-deterministic variable"},
            /*7*/    {"Finite memory", "Nested structures", "Strings of length p", "Arbitrary repetition"},
            /*8*/    {"Two parts: xy", "Three parts: xyz", "Four parts: wxyz", "One part: z"},
            /*9*/    {"They require infinite memory", "They are recognized by finite automata", "They can handle recursive structures", "They can remember unbounded information"},
            /*10*/   {"Context-sensitive", "Not regular", "Finite", "Regular"},
            /*11*/   {"Infinite memory", "Arbitrary repetition of symbols", "Simple regular expressions", "Fixed patterns"},
            /*12*/   {"Shorter than the pumping length", "Longer than the pumping length", "Of any length", "Of even length only"},
            /*13*/   {"Strings of arbitrary length", "Nested parentheses", "Palindromes", "Simple concatenations"},
            /*14*/   {"Regular", "Non-regular", "Context-sensitive", "Irregular"},
            /*15*/   {"To prove that a language is finite", "To prove that a language is non-regular", "To simplify regular expressions", "To identify context-free languages"}
    };

    public static final int[] pre_test_lesson_2_answers = {
            /*1*/    0, // 'Finite memory'
            /*2*/    2, // 'Prove a language is not regular'
            /*3*/    1, // 'Expanding a string by repeating a section'
            /*4*/    2, // 'Not regular'
            /*5*/    2, // 'Regular language limitations'
            /*6*/    1, // 'A positive integer such that any string of length greater than p can be pumped'
            /*7*/    1, // 'Nested structures'
            /*8*/    1, // 'Three parts: xyz'
            /*9*/    1, // 'They are recognized by finite automata'
            /*10*/   1, // 'Not regular'
            /*11*/   0, // 'Infinite memory'
            /*12*/   1, // 'Longer than the pumping length'
            /*13*/   1, // 'Nested parentheses'
            /*14*/   0, // 'Regular'
            /*15*/   1  // 'To prove that a language is non-regular'
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |        EASY          |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson2_Easy_Questions() {
        return e_Module_3.get_PostTest_EasyMedium_Questions(
                post_test_lesson_2_questions_easy,
                post_test_lesson_2_choices_easy,
                post_test_lesson_2_answers_easy,
                e_Question.Difficulty.EASY
        );
    }

    public static final String[] post_test_lesson_2_questions_easy = {
            /*1*/    "Regular languages can describe all possible languages.",
            /*2*/    "Non-regular languages cannot be recognized by finite automata.",
            /*3*/    "The language of balanced parentheses is regular.",
            /*4*/    "Palindromes can be recognized by finite automata.",
            /*5*/    "The Pumping Lemma helps prove non-regularity of languages.",
            /*6*/    "Context-free grammars are more expressive than regular grammars.",
            /*7*/    "Finite automata have an infinite memory.",
            /*8*/    "The language {anbn | n is a natural number} is regular.",
            /*9*/    "Pushdown automata can recognize context-free languages.",
            /*10*/   "Myhill-Nerode Theorem distinguishes between regular and non-regular languages.",
            /*11*/   "Turing Machines are more powerful than pushdown automata.",
            /*12*/   "Context-free languages can handle nested structures like parentheses.",
            /*13*/   "Regular languages can count the number of symbols in a string.",
            /*14*/   "The language {anbm | n ≠ m} is regular.",
            /*15*/   "Finite automata can match all possible numbers of a's and b's in a string."
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
            /*1*/    1, // 'False'
            /*2*/    0, // 'True'
            /*3*/    1, // 'False'
            /*4*/    1, // 'False'
            /*5*/    0, // 'True'
            /*6*/    0, // 'True'
            /*7*/    1, // 'False'
            /*8*/    1, // 'False'
            /*9*/    0, // 'True'
            /*10*/   0, // 'True'
            /*11*/   0, // 'True'
            /*12*/   0, // 'True'
            /*13*/   1, // 'False'
            /*14*/   1, // 'False'
            /*15*/   1  // 'False'
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |        MEDIUM        |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson2_Medium_Questions() {
        return e_Module_3.get_PostTest_EasyMedium_Questions(
                post_test_lesson_2_questions_medium,
                post_test_lesson_2_choices_medium,
                post_test_lesson_2_answers_medium,
                e_Question.Difficulty.MEDIUM
        );
    }

    public static final String[] post_test_lesson_2_questions_medium = {
            /*1*/    "Which of the following cannot be recognized by finite automata?",
            /*2*/    "What is the main limitation of finite automata?",
            /*3*/    "What is an example of a context-free language?",
            /*4*/    "Which of the following languages is regular?",
            /*5*/    "What machine is more powerful than pushdown automata?",
            /*6*/    "What method is commonly used to prove that a language is not regular?",
            /*7*/    "Which language can be described by regular expressions?",
            /*8*/    "Which of the following is required to recognize non-regular languages?",
            /*9*/    "Which of the following is NOT a non-regular language?",
            /*10*/   "Why can't finite automata recognize the language {anbn | n is a natural number}?",
            /*11*/   "Which computational model uses a stack as its memory structure?",
            /*12*/   "Which theorem can distinguish between regular and non-regular languages?",
            /*13*/   "What does the Pumping Lemma state about regular languages?",
            /*14*/   "Which of the following is true for context-free grammars?",
            /*15*/   "Which of the following languages is non-regular and requires more expressive grammars to be described?"
    };

    public static final String[][] post_test_lesson_2_choices_medium = {
            /*1*/    {"Regular languages", "Context-free languages", "Non-regular languages", "Both b and c"},
            /*2*/    {"They cannot count", "They cannot handle infinite memory", "They cannot handle nested structures", "All of the above"},
            /*3*/    {"Palindromes", "Balanced Parentheses", "Regular Expressions", "Both a and b"},
            /*4*/    {"{anbn | n is a natural number}", "Strings with equal numbers of a’s and b’s", "Strings with alternating 0s and 1s", "Strings recognized by finite automata"},
            /*5*/    {"Finite Automata", "Context-free Grammar", "Turing Machine", "None of the above"},
            /*6*/    {"Myhill-Nerode Theorem", "Pumping Lemma", "Context-free Grammar", "Turing Machine"},
            /*7*/    {"Palindromes", "Strings with equal numbers of a’s and b’s", "Finite language", "Balanced Parentheses"},
            /*8*/    {"Finite Automata", "Pushdown Automata", "Context-sensitive Grammars", "Both b and c"},
            /*9*/    {"Balanced Parentheses", "{anbn | n is a natural number}", "Strings recognized by finite automata", "Palindromes"},
            /*10*/   {"They lack sufficient memory", "They can only count to a finite number", "They require an infinite number of states", "All of the above"},
            /*11*/   {"Finite Automata", "Pushdown Automata", "Turing Machine", "Regular Grammar"},
            /*12*/   {"Pumping Lemma", "Context-sensitive Grammar", "Myhill-Nerode Theorem", "Finite Automata"},
            /*13*/   {"They can be broken down into smaller parts and still belong to the same language.", "They cannot handle nested patterns.", "They require an infinite number of states.", "They can handle palindromes."},
            /*14*/   {"They can generate languages with nested structures.", "They can be recognized by finite automata.", "They are less expressive than regular grammars.", "They cannot generate palindromes."},
            /*15*/   {"Strings with equal numbers of a’s and b’s", "Strings with alternating 0s and 1s", "Finite languages", "Strings recognized by finite automata"}
    };

    public static final int[] post_test_lesson_2_answers_medium = {
            /*1*/    3, // 'Both b and c'
            /*2*/    3, // 'All of the above'
            /*3*/    3, // 'Both a and b'
            /*4*/    3, // 'Strings recognized by finite automata'
            /*5*/    2, // 'Turing Machine'
            /*6*/    1, // 'Pumping Lemma'
            /*7*/    2, // 'Finite language'
            /*8*/    3, // 'Both b and c'
            /*9*/    2, // 'Strings recognized by finite automata'
            /*10*/   3, // 'All of the above'
            /*11*/   1, // 'Pushdown Automata'
            /*12*/   2, // 'Myhill-Nerode Theorem'
            /*13*/   0, // 'They can be broken down into smaller parts and still belong to the same language.'
            /*14*/   0, // 'They can generate languages with nested structures.'
            /*15*/   0  // 'Strings with equal numbers of a’s and b’s'
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |         HARD         |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson2_Hard_Questions() {
        return e_Module_3.get_PostTest_Hard_Questions(
                post_test_lesson_2_questions_hard,
                post_test_lesson_2_answers_hard,
                e_Question.Difficulty.HARD
        );
    }

    public static final String[] post_test_lesson_2_questions_hard = {
            /*1*/    "What kind of machine uses finite states to recognize regular languages?",
            /*2*/    "What type of language requires matching opening and closing parentheses?",
            /*3*/    "What is the term for languages that cannot be described by regular expressions?",
            /*4*/    "Which theorem helps to test if a language is regular?",
            /*5*/    "What machine is more powerful than a finite automaton and uses a stack?",
            /*6*/    "Which lemma can prove that a language is not regular?",
            /*7*/    "What type of automaton can recognize context-free languages?",
            /*8*/    "Which machine can handle more complex languages than pushdown automata?",
            /*9*/    "What kind of strings are palindromes?",
            /*10*/   "What is the name of the machine that uses a stack for memory to recognize certain languages?",
            /*11*/   "Which model can recognize languages that finite automata cannot handle?",
            /*12*/   "What grammar can generate languages with nested structures?",
            /*13*/   "What type of language includes strings like 'aabb'?",
            /*14*/   "What kind of machine is limited by finite memory and finite states?",
            /*15*/   "Which computational model is the most powerful and can recognize any computable language?"
    };

    public static final String[] post_test_lesson_2_answers_hard = {
            /*1*/    "finite automaton",
            /*2*/    "balanced parentheses",
            /*3*/    "non-regular languages",
            /*4*/    "myhill-nerode theorem",
            /*5*/    "pushdown automaton",
            /*6*/    "pumping lemma",
            /*7*/    "pushdown automaton",
            /*8*/    "turing machine",
            /*9*/    "reversed strings",
            /*10*/   "pushdown automaton",
            /*11*/   "pushdown automaton",
            /*12*/   "context-free grammar",
            /*13*/   "equal numbers",
            /*14*/   "finite automaton",
            /*15*/   "turing machine"
    };

}