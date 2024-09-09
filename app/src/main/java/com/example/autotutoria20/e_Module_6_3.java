package com.example.autotutoria20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class e_Module_6_3 {

    /*
    +----------------------+
    |       PRE-TEST       |
    |        LESSON 3      |
    +----------------------+
    */

    public static e_Question[] get_PreTest_Lesson3_Questions() {
        return e_Module_6.getPreTestQuestions(
                pre_test_lesson_3_questions,
                pre_test_lesson_3_choices,
                pre_test_lesson_3_answers
        );
    }

    public static final String[] pre_test_lesson_3_questions = {
            /*1*/    "What does it mean for a sentence to be ambiguous?",
            /*2*/    "Which punctuation can help clarify the meaning of a sentence?",
            /*3*/    "Which of the following is a method to make sentences unambiguous?",
            /*4*/    "The sentence 'He fed her cat food' could mean:",
            /*5*/    "In programming ambiguity can lead to:",
            /*6*/    "What kind of ambiguity arises from phrases like 'He saw the man with the telescope'?",
            /*7*/    "What is one solution to resolve ambiguity in formal grammar?",
            /*8*/    "Which of the following best describes 'unambiguous grammar' in computational terms?",
            /*9*/    "In which case is ambiguity most likely to occur?",
            /*10*/   "What is one common source of ambiguity in natural language?",
            /*11*/   "Which of the following strategies helps avoid ambiguity in writing?",
            /*12*/   "What type of grammar is essential for programming languages?",
            /*13*/   "In the sentence 'The professor discussed the project with the student in the office' the ambiguity arises from:",
            /*14*/   "What is the function of parentheses in mathematical expressions?",
            /*15*/   "What does the phrase 'a woman without her man is nothing' exemplify?"
    };

    public static final String[][] pre_test_lesson_3_choices = {
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
            /*13*/   {"The verb 'discussed'", "The phrase 'with the student'", "The phrase 'in the office'", "The word 'project'"},
            /*14*/   {"To introduce ambiguity", "To clarify the order of operations", "To separate variables", "To hide operations"},
            /*15*/   {"A well-structured sentence", "An unambiguous sentence", "A sentence needing punctuation to avoid ambiguity", "A sentence that needs more adjectives"}
    };

    public static final int[] pre_test_lesson_3_answers = {
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
    |          EASY        |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson3_Easy_Questions() {
        return e_Module_6.get_PostTest_EasyMedium_Questions(
                post_test_lesson_3_questions_easy,
                post_test_lesson_3_choices_easy,
                post_test_lesson_3_answers_easy,
                e_Question.Difficulty.EASY
        );
    }

    public static final String[] post_test_lesson_3_questions_easy = {
            /*1*/    "Inherent ambiguity only occurs in literature.",
            /*2*/    "Inherent ambiguity can exist in both language and programming.",
            /*3*/    "All types of ambiguity can always be resolved with enough context.",
            /*4*/    "Inherent ambiguity is always a problem and should be avoided.",
            /*5*/    "Ambiguity in legal texts can lead to multiple interpretations.",
            /*6*/    "Context is the only factor needed to resolve inherent ambiguity.",
            /*7*/    "A sentence that has more than one possible meaning is inherently ambiguous.",
            /*8*/    "Inherent ambiguity is never used intentionally in creative writing.",
            /*9*/    "In mathematics ambiguity is usually not allowed due to the precision required.",
            /*10*/   "Programming languages are designed to completely eliminate all forms of ambiguity.",
            /*11*/   "Ambiguity can arise in instructions if they are not clear enough.",
            /*12*/   "Inherent ambiguity does not affect decision-making in any context.",
            /*13*/   "Clarifying context is one of the methods to reduce ambiguity.",
            /*14*/   "Every ambiguous situation has only two possible interpretations.",
            /*15*/   "Ambiguity can make communication more flexible but less precise."
    };

    public static final String[][] post_test_lesson_3_choices_easy = {
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

    public static final int[] post_test_lesson_3_answers_easy = {
            /*1*/    1, // False
            /*2*/    0, // True
            /*3*/    1, // False
            /*4*/    1, // False
            /*5*/    0, // True
            /*6*/    1, // False
            /*7*/    0, // True
            /*8*/    1, // False
            /*9*/    0, // True
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

    public static e_Question[] get_PostTest_Lesson3_Medium_Questions() {
        return e_Module_6.get_PostTest_EasyMedium_Questions(
                post_test_lesson_3_questions_medium,
                post_test_lesson_3_choices_medium,
                post_test_lesson_3_answers_medium,
                e_Question.Difficulty.MEDIUM
        );
    }

    public static final String[] post_test_lesson_3_questions_medium = {
            /*1*/    "What is inherent ambiguity?",
            /*2*/    "Which of the following is an example of inherent ambiguity?",
            /*3*/    "What can help resolve inherent ambiguity in language?",
            /*4*/    "Which field is least likely to accept inherent ambiguity?",
            /*5*/    "In legal documents what often causes inherent ambiguity?",
            /*6*/    "Which of the following sentences contains inherent ambiguity?",
            /*7*/    "In programming inherent ambiguity can lead to:",
            /*8*/    "How can inherent ambiguity affect communication?",
            /*9*/    "Why is inherent ambiguity sometimes used in creative writing?",
            /*10*/   "What term refers to reducing the number of possible meanings in an ambiguous situation?",
            /*11*/   "In which of the following can inherent ambiguity be a problem?",
            /*12*/   "A phrase is inherently ambiguous if:",
            /*13*/   "What is an important factor in resolving inherent ambiguity?",
            /*14*/   "Which of the following fields actively embraces inherent ambiguity?",
            /*15*/   "Inherent ambiguity can sometimes lead to which of the following?"
    };

    public static final String[][] post_test_lesson_3_choices_medium = {
            /*1*/    {"A situation with only one interpretation", "A situation that cannot be resolved", "A situation with multiple possible interpretations", "A situation where ambiguity is not possible"},
            /*2*/    {"'I will meet you tomorrow.'", "'The bank was closed.'", "'She fed her cat food.'", "'The sun rises in the east.'"},
            /*3*/    {"Removing context", "Adding more complexity", "Providing clear context", "Ignoring the ambiguity"},
            /*4*/    {"Creative writing", "Law", "Mathematics", "Art"},
            /*5*/    {"Lack of vocabulary", "Vague terms and clauses", "Specific and clear language", "Mathematical errors"},
            /*6*/    {"'I parked my car outside the mall.'", "'The chicken is ready to eat.'", "'She walked quickly down the street.'", "'Water freezes at 0°C.'"},
            /*7*/    {"Correct outputs", "Multiple possible outputs", "No ambiguity at all", "Increased performance"},
            /*8*/    {"It makes communication faster", "It can lead to misinterpretation", "It always simplifies communication", "It guarantees clarity"},
            /*9*/    {"To make the text less interesting", "To confuse readers", "To encourage multiple interpretations", "To create uniformity"},
            /*10*/   {"Expansion", "Clarification", "Multiplication", "Obfuscation"},
            /*11*/   {"Painting", "Poetry", "Programming", "Sculpting"},
            /*12*/   {"It can only mean one thing", "It can be interpreted in several ways", "It has no meaning at all", "It follows strict grammar rules"},
            /*13*/   {"Changing the sentence structure", "Ignoring context", "Considering the context", "Removing the ambiguity entirely"},
            /*14*/   {"Creative writing", "Legal drafting", "Scientific reporting", "Financial analysis"},
            /*15*/   {"Clarification of complex ideas", "Simpler problem-solving", "Misunderstandings in communication", "Clearer interpretations"}
    };

    public static final int[] post_test_lesson_3_answers_medium = {
            /*1*/    2, // 'A situation with multiple possible interpretations'
            /*2*/    2, // 'She fed her cat food.'
            /*3*/    2, // 'Providing clear context'
            /*4*/    2, // 'Mathematics'
            /*5*/    1, // 'Vague terms and clauses'
            /*6*/    1, // 'The chicken is ready to eat.'
            /*7*/    1, // 'Multiple possible outputs'
            /*8*/    1, // 'It can lead to misinterpretation'
            /*9*/    2, // 'To encourage multiple interpretations'
            /*10*/   1, // 'Clarification'
            /*11*/   2, // 'Programming'
            /*12*/   1, // 'It can be interpreted in several ways'
            /*13*/   2, // 'Considering the context'
            /*14*/   0, // 'Creative writing'
            /*15*/   2  // 'Misunderstandings in communication'
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |         HARD         |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson3_Hard_Questions() {
        return e_Module_6.get_PostTest_Hard_Questions(
                post_test_lesson_3_questions_hard,
                post_test_lesson_3_answers_hard,
                e_Question.Difficulty.HARD
        );
    }

    public static final String[] post_test_lesson_3_questions_hard = {
            /*1*/    "Identify the term used for situations where multiple interpretations are possible.",
            /*2*/    "What do we call the process of clarifying multiple possible meanings?",
            /*3*/    "Which field is most likely to avoid ambiguity due to its need for precision?",
            /*4*/    "What type of language is commonly used in contracts to avoid ambiguity?",
            /*5*/    "Give an example of inherent ambiguity in a sentence.",
            /*6*/    "What strategy can be used to reduce inherent ambiguity?",
            /*7*/    "What kind of ambiguity can arise in programming when variables are not clearly defined?",
            /*8*/    "Which field might use ambiguity intentionally to foster multiple interpretations?",
            /*9*/    "In which type of writing is inherent ambiguity often considered problematic?",
            /*10*/   "Name one area where inherent ambiguity is embraced rather than avoided.",
            /*11*/   "Which concept involves choosing between multiple interpretations in language?",
            /*12*/   "Name a field where inherent ambiguity can lead to errors in instructions.",
            /*13*/   "What is one common effect of inherent ambiguity in communication?",
            /*14*/   "Give a phrase that may be ambiguous depending on its context.",
            /*15*/   "What is the term for deliberately using ambiguous language to create complexity in a text?"
    };

    public static final String[] post_test_lesson_3_answers_hard = {
            /*1*/    "inherent ambiguity",
            /*2*/    "clarification",
            /*3*/    "mathematics",
            /*4*/    "precise language",
            /*5*/    "'I saw her duck.'",
            /*6*/    "providing context",
            /*7*/    "inherent ambiguity",
            /*8*/    "creative writing",
            /*9*/    "legal writing",
            /*10*/   "literature",
            /*11*/   "ambiguity resolution",
            /*12*/   "programming",
            /*13*/   "misinterpretation",
            /*14*/   "'She fed her cat food.'",
            /*15*/   "intentional ambiguity"
    };
}