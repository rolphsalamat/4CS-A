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
            /*1*/    "Which of the following languages requires more than a finite automaton to be recognized due to its need for matching symbols?",
            /*2*/    "Why can't regular languages handle nested structures like balanced parentheses?",
            /*3*/    "What is the role of context-free grammars in language recognition?",
            /*4*/    "Which computational model uses a stack to manage symbols and is able to recognize context-free languages?",
            /*5*/    "What limitation of finite automata prevents them from recognizing languages like palindromes?",
            /*6*/    "Which theorem relies on the idea of indistinguishable strings to determine the regularity of a language?",
            /*7*/    "The Pumping Lemma helps prove the non-regularity of certain languages. What feature does this lemma primarily rely on?",
            /*8*/    "How do balanced parentheses demonstrate the limitations of regular languages?",
            /*9*/    "Why is the language of palindromes considered non-regular?",
            /*10*/   "What makes Turing machines more powerful than finite automata?",
            /*11*/   "Which language includes strings like 'aabb' or 'abab', where the number of different symbols must be equal?",
            /*12*/   "What does it mean when two strings are indistinguishable with respect to a language?",
            /*13*/   "In the Pumping Lemma, which part of a string can be repeated to demonstrate regularity or non-regularity?",
            /*14*/   "What key feature does a context-free grammar have that regular expressions lack?",
            /*15*/   "How does the Myhill-Nerode theorem contribute to understanding language regularity?"
    };

    public static final String[][] pre_test_lesson_1_choices = {
            /*1*/    {"{anbn | n is a natural number}", "{an | n is a natural number}", "{ab*}", "{ba}"},
            /*2*/    {"They require infinite states.", "They cannot recognize context-free languages.", "They rely on finite memory.", "They only work with palindromes."},
            /*3*/    {"To describe regular languages.", "To handle complex patterns like recursion and nesting.", "To replace finite automata.", "To solve all types of languages."},
            /*4*/    {"Finite Automata", "Pushdown Automata", "Regular Expressions", "Turing Machines"},
            /*5*/    {"They can't compare sections of a string.", "They require infinite states.", "They can only process strings of even length.", "They don’t work with context-sensitive grammars."},
            /*6*/    {"Pumping Lemma", "Myhill-Nerode Theorem", "Context-Free Grammar Theorem", "Turing Machine Theorem"},
            /*7*/    {"The ability to repeat parts of a string.", "The context-free nature of a language.", "The finite number of states in an automaton.", "The ability to reverse a string."},
            /*8*/    {"They require nested counting, which finite automata can't handle.", "They involve infinite repetition of symbols.", "They consist of finite states, which automata can recognize.", "They rely on matching strings of the same length."},
            /*9*/    {"It requires the comparison of the first and last symbols of a string.", "It can be generated by regular expressions.", "It only contains symmetric strings.", "It doesn't involve context-free grammar."},
            /*10*/   {"They can handle infinite recursion.", "They have infinite states.", "They can store and manipulate infinite amounts of data.", "They are used for regular expressions."},
            /*11*/   {"Balanced parentheses", "Palindromes", "Equal numbers of different symbols", "Context-free languages"},
            /*12*/   {"They belong to different languages.", "They cannot be distinguished by the language after appending the same string.", "They differ in length.", "They are both recognized by context-free grammar."},
            /*13*/   {"The first part.", "The middle section.", "The entire string.", "The last character."},
            /*14*/   {"The ability to match nested structures.", "Recognition of infinite loops.", "Handling of regular languages.", "Ability to describe finite strings only."},
            /*15*/   {"It shows how finite automata can recognize infinite strings.", "It identifies whether two strings are distinguishable within a language.", "It proves that all languages are regular.", "It simplifies recursive languages."}
    };

    public static final int[] pre_test_lesson_1_answers = {
            /*1*/    0,  // '{anbn | n is a natural number}'
            /*2*/    1,  // 'They cannot recognize context-free languages.'
            /*3*/    1,  // 'To handle complex patterns like recursion and nesting.'
            /*4*/    1,  // 'Pushdown Automata'
            /*5*/    0,  // 'They can't compare sections of a string.'
            /*6*/    1,  // 'Myhill-Nerode Theorem'
            /*7*/    2,  // 'The finite number of states in an automaton.'
            /*8*/    0,  // 'They require nested counting, which finite automata can't handle.'
            /*9*/    0,  // 'It requires the comparison of the first and last symbols of a string.'
            /*10*/   2,  // 'They can store and manipulate infinite amounts of data.'
            /*11*/   2,  // 'Equal numbers of different symbols'
            /*12*/   1,  // 'They cannot be distinguished by the language after appending the same string.'
            /*13*/   1,  // 'The middle section.'
            /*14*/   0,  // 'The ability to match nested structures.'
            /*15*/   1   // 'It identifies whether two strings are distinguishable within a language.'
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
            /*1*/    "Context-Free Grammar (CFG) is used to generate all possible strings in a regular language.",
            /*2*/    "Terminals in a CFG represent the basic symbols that form strings.",
            /*3*/    "The production rules of a CFG allow non-terminals to be replaced by both terminals and non-terminals.",
            /*4*/    "In the CFG G = (V, Σ, R, S), V represents the set of terminal symbols.",
            /*5*/    "In a CFG, the start symbol is always a terminal symbol.",
            /*6*/    "In the CFG for balanced parentheses, the production S → ε means that the string can terminate.",
            /*7*/    "A context-free grammar can describe a string with an equal number of 'a's and 'b's.",
            /*8*/    "CFGs are only applicable in linguistics and cannot be used to describe programming languages.",
            /*9*/    "Production rules in CFG are of the form A → γ, where A is a terminal and γ is a non-terminal.",
            /*10*/   "In CFG, non-terminals represent sets of strings and can be replaced according to production rules.",
            /*11*/   "The start symbol in a CFG represents the entire language.",
            /*12*/   "A CFG can describe the syntax of simple arithmetic expressions involving addition and multiplication.",
            /*13*/   "Balanced parentheses can be generated by a context-free grammar with the rule S → (S).",
            /*14*/   "CFG production rules cannot involve recursion, such as S → aSb.",
            /*15*/   "The CFG for the language {anbn | n ≥ 0} can terminate with the rule S → ε."
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
            /*1*/    1,  // False
            /*2*/    0,  // True
            /*3*/    0,  // True
            /*4*/    1,  // False
            /*5*/    1,  // False
            /*6*/    0,  // True
            /*7*/    0,  // True
            /*8*/    1,  // False
            /*9*/    1,  // False
            /*10*/   0,  // True
            /*11*/   0,  // True
            /*12*/   0,  // True
            /*13*/   0,  // True
            /*14*/   1,  // False
            /*15*/   0   // True
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
            /*1*/    "What does the set Σ represent in a CFG?",
            /*2*/    "Which of the following is an example of a non-terminal in a CFG for arithmetic expressions?",
            /*3*/    "What is the form of a production rule in a CFG?",
            /*4*/    "In the CFG for balanced parentheses, which of the following is a valid production rule?",
            /*5*/    "In CFG, what does the start symbol represent?",
            /*6*/    "Which of the following is a terminal symbol in the CFG for arithmetic expressions?",
            /*7*/    "In the CFG G = (V, Σ, R, S), what does the R represent?",
            /*8*/    "What is the purpose of a context-free grammar?",
            /*9*/    "Which of the following grammars can describe strings of equal numbers of 'a's and 'b's?",
            /*10*/   "What does the production rule S → ε mean in a CFG?",
            /*11*/   "In the CFG for arithmetic expressions, what can a Factor be replaced with?",
            /*12*/   "Which of the following best describes the role of production rules in a CFG?",
            /*13*/   "What is the correct production rule for generating arithmetic expressions with addition and multiplication?",
            /*14*/   "In the context-free grammar for balanced parentheses, how is the rule S → SS used?",
            /*15*/   "Which of the following languages is generated by the CFG S → aSb | ε?"
    };

    public static final String[][] post_test_lesson_1_choices_medium = {
            /*1*/    {"Start symbols", "Terminal symbols", "Non-terminal symbols", "Production rules"},
            /*2*/    {"+", "Term", "(", "number"},
            /*3*/    {"A → γ", "T → S", "a → A", "T → B"},
            /*4*/    {"S → ()", "S → (S)", "S → aSb", "S → aA"},
            /*5*/    {"A special terminal symbol", "The set of production rules", "The entire language", "The set of terminal symbols"},
            /*6*/    {"Expression", "Term", "+", "Factor"},
            /*7*/    {"Terminal symbols", "Production rules", "Non-terminal symbols", "Start symbols"},
            /*8*/    {"To define the syntax of languages", "To recognize regular languages", "To represent recursive algorithms", "To determine if a string is finite"},
            /*9*/    {"Regular grammar", "Context-free grammar", "Context-sensitive grammar", "Chomsky normal form grammar"},
            /*10*/   {"The string contains only non-terminals", "The string can terminate", "The string repeats", "The string contains only terminals"},
            /*11*/   {"Factor → number", "Factor → Expression * Factor", "Factor → Term", "Factor → +"},
            /*12*/   {"They generate strings by replacing non-terminals with terminals and non-terminals.", "They determine if a language is finite or infinite.", "They only generate terminal symbols.", "They represent the start of a string."},
            /*13*/   {"Expression → Expression + Factor", "Expression → Expression + Term", "Factor → Expression * Term", "Term → Expression"},
            /*14*/   {"To terminate the string", "To concatenate two balanced strings", "To delete parentheses", "To insert an operator"},
            /*15*/   {"{anbn | n ≥ 0}", "{ab}", "{aaa, bbb}", "{(n) | n ≥ 0}"}
    };

    public static final int[] post_test_lesson_1_answers_medium = {
            /*1*/    1,  // Terminal symbols
            /*2*/    1,  // Term
            /*3*/    0,  // A → γ
            /*4*/    1,  // S → (S)
            /*5*/    2,  // The entire language
            /*6*/    2,  // +
            /*7*/    1,  // Production rules
            /*8*/    0,  // To define the syntax of languages
            /*9*/    1,  // Context-free grammar
            /*10*/   1,  // The string can terminate
            /*11*/   0,  // Factor → number
            /*12*/   0,  // They generate strings by replacing non-terminals with terminals and non-terminals.
            /*13*/   1,  // Expression → Expression + Term
            /*14*/   1,  // To concatenate two balanced strings
            /*15*/   0   // {anbn | n ≥ 0}
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
            /*1*/    "What symbol begins the CFG derivation?",
            /*2*/    "What are the smallest elements of a CFG?",
            /*3*/    "Which symbols are replaced in CFG rules?",
            /*4*/    "What rule represents an empty string in CFG?",
            /*5*/    "What dictates how terminals and non-terminals combine?",
            /*6*/    "What elements are used to form valid strings in CFG?",
            /*7*/    "Which CFG rule creates arithmetic expressions?",
            /*8*/    "What component organizes non-terminal replacements?",
            /*9*/    "What term describes the recursive nature of CFG?",
            /*10*/   "What symbols form strings in CFG's output?",
            /*11*/   "Which non-terminal symbol is used for balanced parentheses?",
            /*12*/   "What structure controls arithmetic operations in CFG?",
            /*13*/   "What specifies the method to end CFG derivations?",
            /*14*/   "What CFG rule defines basic units like numbers?",
            /*15*/   "Which concept forms the foundation of context-free languages?"
    };

    public static final String[] post_test_lesson_1_answers_hard = {
            /*1*/    "start symbol",
            /*2*/    "terminal characters",
            /*3*/    "non-terminal symbols",
            /*4*/    "epsilon production",
            /*5*/    "grammar syntax",
            /*6*/    "terminal sequences",
            /*7*/    "expression formula",
            /*8*/    "production system",
            /*9*/    "recursive rule",
            /*10*/   "derived strings",
            /*11*/   "parenthesis symbol",
            /*12*/   "arithmetic expressions",
            /*13*/   "final rule",
            /*14*/   "factor production",
            /*15*/   "context-free grammar"
    };
}
