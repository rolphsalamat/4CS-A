package com.example.autotutoria20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class e_Module_6_2 {

    /*
    +----------------------+
    |       PRE-TEST       |
    |        LESSON   2    |
    +----------------------+
    */

    public static e_Question[] get_PreTest_Lesson2_Questions() {
        return e_Module_6.getPreTestQuestions(
                pre_test_lesson_2_questions,
                pre_test_lesson_2_choices,
                pre_test_lesson_2_answers
        );
    }

    public static final String[] pre_test_lesson_2_questions = {
            /*1*/    "What defines an ambiguous grammar?",
            /*2*/    "Which of the following is true about ambiguous grammars?",
            /*3*/    "What is the primary challenge of ambiguity in grammars?",
            /*4*/    "Which of the following is an example of an ambiguous expression?",
            /*5*/    "Why is ambiguity undesirable in programming language grammars?",
            /*6*/    "In which type of language is ambiguity more frequently encountered?",
            /*7*/    "A grammar is called ambiguous if it:",
            /*8*/    "Which type of grammar does not allow ambiguity?",
            /*9*/    "Ambiguous grammar is problematic in:",
            /*10*/   "What is a possible solution to resolve ambiguity in a grammar?",
            /*11*/   "Ambiguity in context-free grammar often leads to:",
            /*12*/   "Which of the following helps resolve ambiguity in grammars?",
            /*13*/   "Which of the following sentences would most likely have an ambiguous parse tree?",
            /*14*/   "What makes a sentence ambiguous in a CFG?",
            /*15*/   "In terms of CFG what is ambiguity?"
    };

    public static final String[][] pre_test_lesson_2_choices = {
            /*1*/    {"A grammar with no production rules", "A grammar with more than one parse tree for some sentences", "A grammar that only generates one parse tree", "A grammar used only for arithmetic expressions"},
            /*2*/    {"They can generate more than one parse tree for a sentence", "They are always preferable for language design", "They eliminate the need for parsing", "They only exist in natural language processing"},
            /*3*/    {"It simplifies language interpretation", "It can lead to multiple interpretations of a sentence", "It eliminates the need for parsing algorithms", "It makes grammars unusable"},
            /*4*/    {"id+id∗id", "id→id+id", "id→id", "id−id"},
            /*5*/    {"It requires fewer production rules", "It leads to different interpretations of code", "It simplifies the parsing process", "It enhances compiler efficiency"},
            /*6*/    {"Natural languages", "Programming languages", "Formal languages", "None of the above"},
            /*7*/    {"Has only one derivation for each sentence", "Has multiple derivations for at least one sentence", "Does not produce any sentences", "Only generates natural language sentences"},
            /*8*/    {"Ambiguous grammar", "Context-free grammar", "Unambiguous grammar", "All grammars allow ambiguity"},
            /*9*/    {"Compilers and parsers", "Syntax analysis of programming languages", "Both a and b", "None of the above"},
            /*10*/   {"Ignoring it", "Rewriting the production rules", "Adding more symbols", "None of the above"},
            /*11*/   {"One unique derivation", "Faster parsing times", "Multiple valid parse trees", "No valid sentences being generated"},
            /*12*/   {"Removing recursion", "Using more non-terminal symbols", "Refining production rules", "Changing the grammar to a different language"},
            /*13*/   {"A sentence with a single noun and verb", "A complex arithmetic expression with multiple operators", "A sentence with only one possible interpretation", "None of the above"},
            /*14*/   {"It cannot be derived using the grammar", "It can be derived using only one production rule", "It can be derived using multiple parse trees", "It is not part of the language"},
            /*15*/   {"A feature that simplifies parsing", "The presence of multiple interpretations for a single sentence", "The use of only terminal symbols in a grammar", "A solution to eliminate all derivations"}
    };

    public static final int[] pre_test_lesson_2_answers = {
            /*1*/    1, // 'A grammar with more than one parse tree for some sentences'
            /*2*/    0, // 'They can generate more than one parse tree for a sentence'
            /*3*/    1, // 'It can lead to multiple interpretations of a sentence'
            /*4*/    0, // 'id+id∗id'
            /*5*/    1, // 'It leads to different interpretations of code'
            /*6*/    0, // 'Natural languages'
            /*7*/    1, // 'Has multiple derivations for at least one sentence'
            /*8*/    2, // 'Unambiguous grammar'
            /*9*/    2, // 'Both a and b'
            /*10*/   1, // 'Rewriting the production rules'
            /*11*/   2, // 'Multiple valid parse trees'
            /*12*/   2, // 'Refining production rules'
            /*13*/   1, // 'A complex arithmetic expression with multiple operators'
            /*14*/   2, // 'It can be derived using multiple parse trees'
            /*15*/   1  // 'The presence of multiple interpretations for a single sentence'
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |          EASY        |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson2_Easy_Questions() {
        return e_Module_6.get_PostTest_EasyMedium_Questions(
                post_test_lesson_2_questions_easy,
                post_test_lesson_2_choices_easy,
                post_test_lesson_2_answers_easy,
                e_Question.Difficulty.EASY
        );
    }

    public static final String[] post_test_lesson_2_questions_easy = {
            /*1*/    "Ambiguity in a sentence occurs when a sentence has more than one possible meaning.",
            /*2*/    "Parentheses can be used to remove ambiguity in arithmetic expressions.",
            /*3*/    "\"I saw her duck\" is an example of an unambiguous sentence.",
            /*4*/    "An unambiguous grammar ensures that each valid sentence has only one correct parse tree.",
            /*5*/    "A sentence is considered ambiguous only if its meaning can change depending on the context.",
            /*6*/    "Adding a modifier can help make sentences more ambiguous.",
            /*7*/    "The use of commas can help clarify meaning and reduce ambiguity in complex sentences.",
            /*8*/    "The phrase \"The chicken is ready to eat\" is unambiguous.",
            /*9*/    "In computational contexts ambiguity can cause syntax errors in programming.",
            /*10*/   "Removing ambiguity from grammar is essential for accurate communication in natural and programming languages.",
            /*11*/   "In formal grammars ambiguity occurs when two different parse trees are possible for the same sentence.",
            /*12*/   "Using parentheses to clarify an equation is a method to avoid ambiguity.",
            /*13*/   "\"He saw the girl with the telescope\" is an example of an unambiguous sentence.",
            /*14*/   "Ambiguous grammar can lead to misinterpretation in formal logic and computational algorithms.",
            /*15*/   "All languages whether natural or computational can be completely free from ambiguity."
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
            /*5*/    1, // False
            /*6*/    1, // False
            /*7*/    0, // True
            /*8*/    1, // False
            /*9*/    0, // True
            /*10*/   0, // True
            /*11*/   0, // True
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
        return e_Module_6.get_PostTest_EasyMedium_Questions(
                post_test_lesson_2_questions_medium,
                post_test_lesson_2_choices_medium,
                post_test_lesson_2_answers_medium,
                e_Question.Difficulty.MEDIUM
        );
    }

    public static final String[] post_test_lesson_2_questions_medium = {
            /*1*/    "What does it mean for a sentence to be ambiguous?",
            /*2*/    "Which punctuation can help clarify the meaning of a sentence?",
            /*3*/    "Which of the following is a method to make sentences unambiguous?",
            /*4*/    "The sentence \"He fed her cat food\" could mean:",
            /*5*/    "In programming ambiguity can lead to:",
            /*6*/    "What kind of ambiguity arises from phrases like \"He saw the man with the telescope\"?",
            /*7*/    "What is one solution to resolve ambiguity in formal grammar?",
            /*8*/    "Which of the following best describes \"unambiguous grammar\" in computational terms?",
            /*9*/    "In which case is ambiguity most likely to occur?",
            /*10*/   "What is one common source of ambiguity in natural language?",
            /*11*/   "Which of the following strategies helps avoid ambiguity in writing?",
            /*12*/   "What type of grammar is essential for programming languages?",
            /*13*/   "In the sentence \"The professor discussed the project with the student in the office\" the ambiguity arises from:",
            /*14*/   "What is the function of parentheses in mathematical expressions?",
            /*15*/   "What does the phrase \"a woman without her man is nothing\" exemplify?"
    };

    public static final String[][] post_test_lesson_2_choices_medium = {
            /*1*/    {"It has no meaning", "It has multiple possible interpretations", "It is too long", "It contains complex vocabulary"},
            /*2*/    {"Exclamation mark", "Comma", "Hyphen", "Parentheses"},
            /*3*/    {"Using metaphors", "Adding context", "Removing verbs", "Using vague language"},
            /*4*/    {"He fed the cat food to her", "He fed her food to the cat", "Both a and b", "Neither a nor b"},
            /*5*/    {"Syntax errors", "Logical clarity", "Faster computation", "Automatic error correction"},
            /*6*/    {"Lexical", "Structural", "Contextual", "Grammatical"},
            /*7*/    {"Add more rules", "Use recursion", "Rewrite the grammar", "Ignore it"},
            /*8*/    {"A set of rules with multiple interpretations", "A set of rules with only one valid parse tree for each sentence", "A grammar that allows multiple sentence structures", "A grammar without symbols"},
            /*9*/    {"When using specific vocabulary", "When there is more than one subject", "When sentence structure is unclear", "When the sentence is too short"},
            /*10*/   {"Vague pronouns", "Mathematical formulas", "Clear definitions", "Specific measurements"},
            /*11*/   {"Using short sentences", "Using passive voice", "Clarifying pronouns", "Adding more adjectives"},
            /*12*/   {"Ambiguous grammar", "Unambiguous grammar", "Informal grammar", "Vague grammar"},
            /*13*/   {"The verb \"discussed\"", "The phrase \"with the student\"", "The phrase \"in the office\"", "The word \"project\""},
            /*14*/   {"To introduce ambiguity", "To clarify the order of operations", "To separate variables", "To hide operations"},
            /*15*/   {"A well-structured sentence", "An unambiguous sentence", "A sentence needing punctuation to avoid ambiguity", "A sentence that needs more adjectives"}
    };

    public static final int[] post_test_lesson_2_answers_medium = {
            /*1*/    1, // 'It has multiple possible interpretations'
            /*2*/    3, // 'Parentheses'
            /*3*/    1, // 'Adding context'
            /*4*/    2, // 'Both a and b'
            /*5*/    0, // 'Syntax errors'
            /*6*/    1, // 'Structural'
            /*7*/    2, // 'Rewrite the grammar'
            /*8*/    1, // 'A set of rules with only one valid parse tree for each sentence'
            /*9*/    2, // 'When sentence structure is unclear'
            /*10*/   0, // 'Vague pronouns'
            /*11*/   2, // 'Clarifying pronouns'
            /*12*/   1, // 'Unambiguous grammar'
            /*13*/   1, // 'The phrase "with the student"'
            /*14*/   1, // 'To clarify the order of operations'
            /*15*/   2  // 'A sentence needing punctuation to avoid ambiguity'
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |         HARD         |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson2_Hard_Questions() {
        return e_Module_6.get_PostTest_Hard_Questions(
                post_test_lesson_2_questions_hard,
                post_test_lesson_2_answers_hard,
                e_Question.Difficulty.HARD
        );
    }

    public static final String[] post_test_lesson_2_questions_hard = {
            /*1*/    "Identify the punctuation that helps remove ambiguity in a sentence.",
            /*2*/    "What is the term for when a sentence can be interpreted in more than one way?",
            /*3*/    "What type of ambiguity is caused by unclear sentence structure?",
            /*4*/    "What do you call a grammar that ensures only one interpretation of a sentence?",
            /*5*/    "Identify the term used when multiple parse trees are possible for a single sentence in formal language.",
            /*6*/    "In formal grammars what is the term for rewriting rules to remove ambiguity?",
            /*7*/    "What symbol is commonly used to clarify the order of operations in arithmetic expressions?",
            /*8*/    "What is the cause of ambiguity in the sentence \"He saw the girl with the telescope\"?",
            /*9*/    "What method is used to resolve ambiguity in programming languages?",
            /*10*/   "What grammatical element can clarify whether the subject or object is acting in a sentence?",
            /*11*/   "What is the term for multiple interpretations of the same sentence due to lexical choices?",
            /*12*/   "Identify the process of breaking down ambiguous sentences into parts to analyze their meaning.",
            /*13*/   "What is the term used when vague pronouns lead to unclear sentences?",
            /*14*/   "What is an effective way to eliminate ambiguity in instructions?",
            /*15*/   "What is the result of using ambiguous grammar in formal language systems?"
    };

    public static final String[] post_test_lesson_2_answers_hard = {
            /*1*/    "parentheses",
            /*2*/    "ambiguity",
            /*3*/    "structural ambiguity",
            /*4*/    "unambiguous grammar",
            /*5*/    "ambiguous grammar",
            /*6*/    "grammar refinement",
            /*7*/    "parentheses",
            /*8*/    "unclear modifier",
            /*9*/    "syntax rules",
            /*10*/   "subject-verb agreement",
            /*11*/   "lexical ambiguity",
            /*12*/   "parsing",
            /*13*/   "pronoun ambiguity",
            /*14*/   "clarifying language",
            /*15*/   "multiple interpretations"
    };
}