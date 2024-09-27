package com.example.autotutoria20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class e_Module_6_3 {

    /*
    +----------------------+
    |       PRE-TEST       |
    |      LESSON 3        |
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
            /*1*/ "What is the primary purpose of unambiguous grammar in computer languages?",
            /*2*/ "How does removing left recursion contribute to unambiguous grammar?",
            /*3*/ "What is the role of parse trees in syntactic analysis?",
            /*4*/ "Why is ambiguity undesirable in context-free grammar used for compilers?",
            /*5*/ "Which method is commonly used to ensure grammar is unambiguous?",
            /*6*/ "When is context-free grammar said to be ambiguous?",
            /*7*/ "How does left factoring help in grammar simplification?",
            /*8*/ "Which aspect of grammar ensures deterministic parsing in programming languages?",
            /*9*/ "What does constructing a parse tree allow you to determine grammar?",
            /*10*/ "Which of the following statements about unambiguous grammar is true?",
            /*11*/ "What is the impact of ambiguity on parsing algorithms?",
            /*12*/ "How is ambiguity typically identified in grammar?",
            /*13*/ "Which of the following best describes context-free grammar?",
            /*14*/ "Which type of grammar simplifies parsing by ensuring no ambiguity in derivation?",
            /*15*/ "Which strategy can reduce the complexity of grammar without introducing ambiguity?"
    };

    public static final String[][] pre_test_lesson_3_choices = {
            /*1*/ {"To reduce the complexity of the grammar", "To ensure each valid string has exactly one parse tree", "To simplify the derivation process", "To allow multiple interpretations of strings"},
            /*2*/ {"It prevents multiple leftmost derivations", "It allows terminal symbols to appear first", "It simplifies the parse tree construction", "It reduces ambiguity by ensuring a single derivation path"},
            /*3*/ {"They define the grammar rules", "They provide a visual representation of sentence structure", "They help remove ambiguous grammar rules", "They represent ambiguous derivations"},
            /*4*/ {"It slows down code generation", "It can lead to multiple interpretations of the same code", "It simplifies the writing of production rules", "It makes syntax analysis more efficient"},
            /*5*/ {"Adding more non-terminal symbols", "Simplifying the grammar rules", "Creating unique parse trees for each string", "Introducing terminal symbols in each derivation"},
            /*6*/ {"When it has multiple derivation trees for a single string", "When it generates only terminal symbols", "When it lacks non-terminal symbols", "When every derivation is rightmost"},
            /*7*/ {"It eliminates unnecessary non-terminal symbols", "It reduces recursion and ambiguity", "It ensures terminal symbols come first", "It merges overlapping production rules"},
            /*8*/ {"Right recursion", "Unambiguity", "Ambiguity", "Terminal symbols"},
            /*9*/ {"The number of derivations", "The ambiguity of the grammar", "The non-terminal symbols used", "The context-sensitive rules"},
            /*10*/ {"They always generate multiple parse trees", "They ensure each string has a unique derivation", "They allow overlapping derivations", "They simplify natural language parsing"},
            /*11*/ {"It makes parsing more efficient", "It complicates the parsing process by allowing multiple parse trees", "It simplifies the syntax analysis", "It improves the accuracy of derivations"},
            /*12*/ {"By analyzing the number of derivations", "By creating parse trees for different derivations of the same string", "By testing the grammar with non-terminal symbols", "By rewriting the grammar into Chomsky Normal Form"},
            /*13*/ {"A grammar that generates multiple parse trees for every string", "A grammar that uses both terminal and non-terminal symbols", "A grammar that has overlapping rules for every derivation", "A grammar that only allows leftmost derivations"},
            /*14*/ {"Ambiguous grammar", "Context-sensitive grammar", "Unambiguous grammar", "Recursive grammar"},
            /*15*/ {"Adding additional non-terminal symbols", "Removing left recursion", "Ignoring ambiguous strings", "Rewriting the grammar with more rules"}
    };

    public static final int[] pre_test_lesson_3_answers = {
            /*1*/ 1, // 'To ensure each valid string has exactly one parse tree'
            /*2*/ 3, // 'It reduces ambiguity by ensuring a single derivation path'
            /*3*/ 1, // 'They provide a visual representation of sentence structure'
            /*4*/ 1, // 'It can lead to multiple interpretations of the same code'
            /*5*/ 2, // 'Simplifying the grammar rules'
            /*6*/ 0, // 'When it has multiple derivation trees for a single string'
            /*7*/ 1, // 'It reduces recursion and ambiguity'
            /*8*/ 1, // 'Unambiguity'
            /*9*/ 1, // 'The ambiguity of the grammar'
            /*10*/ 1, // 'They ensure each string has a unique derivation'
            /*11*/ 1, // 'It complicates the parsing process by allowing multiple parse trees'
            /*12*/ 1, // 'By creating parse trees for different derivations of the same string'
            /*13*/ 1, // 'A grammar that uses both terminal and non-terminal symbols'
            /*14*/ 2, // 'Unambiguous grammar'
            /*15*/ 1  // 'Removing left recursion'
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |        EASY          |
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
            /*1*/ "Ambiguity in grammar is always undesirable.",
            /*2*/ "Inherent ambiguity refers to ambiguity that cannot be removed from grammar.",
            /*3*/ "Ambiguous grammar can always be rewritten as unambiguous grammar.",
            /*4*/ "Context-free grammar can be inherently ambiguous.",
            /*5*/ "Inherent ambiguity only occurs in natural languages, not programming languages.",
            /*6*/ "Ambiguity in grammar may lead to multiple valid parse trees for a single string.",
            /*7*/ "Grammar with inherent ambiguity can have multiple leftmost derivations for the same string.",
            /*8*/ "Inherent ambiguity is always a sign of poor grammar design.",
            /*9*/ "Left recursion in a grammar is always responsible for ambiguity.",
            /*10*/ "Ambiguity in grammar can sometimes be useful in natural language processing.",
            /*11*/ "All context-free grammar is inherently ambiguous.",
            /*12*/ "Inherent ambiguity can be tested by generating multiple parse trees for the same sentence.",
            /*13*/ "A grammar can be ambiguous if it produces multiple derivation trees for some sentences but not others.",
            /*14*/ "Removing ambiguity from grammar is always possible.",
            /*15*/ "Inherent ambiguity means that the ambiguity cannot be eliminated, regardless of how the grammar is modified."
    };

    public static final String[][] post_test_lesson_3_choices_easy = {
            /*1*/ {"True", "False"},
            /*2*/ {"True", "False"},
            /*3*/ {"True", "False"},
            /*4*/ {"True", "False"},
            /*5*/ {"True", "False"},
            /*6*/ {"True", "False"},
            /*7*/ {"True", "False"},
            /*8*/ {"True", "False"},
            /*9*/ {"True", "False"},
            /*10*/ {"True", "False"},
            /*11*/ {"True", "False"},
            /*12*/ {"True", "False"},
            /*13*/ {"True", "False"},
            /*14*/ {"True", "False"},
            /*15*/ {"True", "False"}
    };

    public static final int[] post_test_lesson_3_answers_easy = {
            /*1*/ 0, // True
            /*2*/ 0, // True
            /*3*/ 1, // False
            /*4*/ 0, // True
            /*5*/ 1, // False
            /*6*/ 0, // True
            /*7*/ 0, // True
            /*8*/ 1, // False
            /*9*/ 1, // False
            /*10*/ 0, // True
            /*11*/ 1, // False
            /*12*/ 0, // True
            /*13*/ 0, // True
            /*14*/ 1, // False
            /*15*/ 0  // True
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
            /*1*/ "What does inherent ambiguity in a grammar mean?",
            /*2*/ "What is one potential consequence of inherent ambiguity in programming languages?",
            /*3*/ "How can inherent ambiguity in a grammar be identified?",
            /*4*/ "In which type of language does inherent ambiguity frequently occur?",
            /*5*/ "Which of the following cannot eliminate inherent ambiguity in grammar?",
            /*6*/ "Why is inherent ambiguity problematic in compiler design?",
            /*7*/ "Inherent ambiguity leads to?",
            /*8*/ "Which of the following is true about context-free grammar?",
            /*9*/ "Which strategy is not effective in resolving inherent ambiguity?",
            /*10*/ "What is the difference between inherent ambiguity and removable ambiguity?",
            /*11*/ "Which of the following best describes an inherently ambiguous language?",
            /*12*/ "Which of these techniques can help detect ambiguity in grammar?",
            /*13*/ "Inherent ambiguity is most often associated with which type of grammar?",
            /*14*/ "Which type of derivation can help identify whether grammar is ambiguous?",
            /*15*/ "Which of the following is an example of ambiguous grammar?"
    };

    public static final String[][] post_test_lesson_3_choices_medium = {
            /*1*/ {"The grammar has no ambiguity", "The ambiguity cannot be removed", "The grammar can be easily rewritten", "The grammar has too many terminals"},
            /*2*/ {"Increased parsing efficiency", "Multiple valid interpretations of code", "Faster compilation", "No effect on parsing"},
            /*3*/ {"By analyzing only the terminals", "By checking if there are multiple derivations for some strings", "By removing recursion from the grammar", "By simplifying the production rules"},
            /*4*/ {"Natural language", "Assembly language", "Machine code", "Regular languages"},
            /*5*/ {"Rewriting the production rules", "Removing non-terminal symbols", "Testing derivations", "Introducing left factoring"},
            /*6*/ {"It reduces the size of the code", "It ensures deterministic parsing", "It makes the syntax analysis phase difficult", "It eliminates the need for recursion"},
            /*7*/ {"Multiple derivation trees for the same sentence", "Unique parse trees for all strings", "A grammar that is always unambiguous", "Terminal symbols having more than one derivation"},
            /*8*/ {"They can never be ambiguous", "Some of them may be inherently ambiguous", "They only generate regular languages", "They cannot represent complex languages"},
            /*9*/ {"Left factoring", "Right recursion", "Simplifying the grammar", "Adding more non-terminals"},
            /*10*/ {"Inherent ambiguity can be removed by adding more rules", "Removable ambiguity cannot be detected", "Inherent ambiguity cannot be eliminated", "Removable ambiguity cannot be resolved through any transformation"},
            /*11*/ {"A language that can only be generated by ambiguous grammar", "A language that can be parsed using any context-free grammar", "A language with a unique parse tree for each string", "A language that avoids recursion in its derivations"},
            /*12*/ {"Constructing parse trees for different derivations", "Ignoring non-terminal symbols", "Removing all terminals", "Adding left recursion"},
            /*13*/ {"Context-sensitive grammar", "Context-free grammar", "Regular grammar", "Chomsky Normal Form grammar"},
            /*14*/ {"Leftmost derivation", "Bottom-up derivation", "Rightmost derivation", "Both leftmost and rightmost derivations"},
            /*15*/ {"A grammar where every string has a unique derivation", "A grammar with overlapping rules that lead to multiple derivations", "A grammar where every terminal has a unique derivation", "A grammar that cannot generate any strings"}
    };

    public static final int[] post_test_lesson_3_answers_medium = {
            /*1*/ 1, // The ambiguity cannot be removed
            /*2*/ 1, // Multiple valid interpretations of code
            /*3*/ 1, // By checking if there are multiple derivations for some strings
            /*4*/ 0, // Natural language
            /*5*/ 1, // Removing non-terminal symbols
            /*6*/ 2, // It makes the syntax analysis phase difficult
            /*7*/ 0, // Multiple derivation trees for the same sentence
            /*8*/ 1, // Some of them may be inherently ambiguous
            /*9*/ 3, // Adding more non-terminals
            /*10*/ 2, // Inherent ambiguity cannot be eliminated
            /*11*/ 0, // A language that can only be generated by ambiguous grammar
            /*12*/ 0, // Constructing parse trees for different derivations
            /*13*/ 1, // Context-free grammar
            /*14*/ 3, // Both leftmost and rightmost derivations
            /*15*/ 1  // A grammar with overlapping rules that lead to multiple derivations
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |        HARD          |
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
            /*1*/ "What concept involves a grammar that cannot eliminate ambiguity?",
            /*2*/ "What structure visually represents the derivation of a sentence?",
            /*3*/ "What ambiguity results from multiple valid derivations for a string?",
            /*4*/ "What method is used to detect ambiguity in grammars?",
            /*5*/ "What process simplifies a grammar by removing recursion?",
            /*6*/ "What term describes multiple valid interpretations of a grammar?",
            /*7*/ "What technique identifies ambiguous grammars using multiple derivations?",
            /*8*/ "What term is used when more than one parse tree exists for a single string?",
            /*9*/ "What type of grammar inherently contains ambiguity?",
            /*10*/ "What method constructs derivation trees for ambiguity detection?",
            /*11*/ "What approach ensures unique parsing by modifying grammar rules?",
            /*12*/ "What describes ambiguity that cannot be eliminated by rewriting?",
            /*13*/ "What term refers to the existence of more than one parse tree for a string?",
            /*14*/ "What is the key issue that inherent ambiguity causes in parsing?",
            /*15*/ "What is the primary tool used to identify structural ambiguity?"
    };

    public static final String[] post_test_lesson_3_answers_hard = {
            /*1*/ "inherent ambiguity",
            /*2*/ "parse tree",
            /*3*/ "syntactic ambiguity",
            /*4*/ "parse analysis",
            /*5*/ "left factoring",
            /*6*/ "grammar ambiguity",
            /*7*/ "derivation analysis",
            /*8*/ "multiple parsing",
            /*9*/ "ambiguous grammar",
            /*10*/ "tree construction",
            /*11*/ "grammar refinement",
            /*12*/ "inherent ambiguity",
            /*13*/ "parse conflict",
            /*14*/ "ambiguous interpretation",
            /*15*/ "parse tree"
    };
}
