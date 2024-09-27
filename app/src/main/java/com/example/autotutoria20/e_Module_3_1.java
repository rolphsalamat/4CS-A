package com.example.autotutoria20;

public class e_Module_3_1 {

    /*
    +----------------------+
    |       PRE-TEST       |
    |        LESSON 2.1    |
    +----------------------+
    */

    public static e_Question[] get_PreTest_Lesson1_Questions() {
        return e_Module_3.getPreTestQuestions(
                pre_test_lesson_1_questions,
                pre_test_lesson_1_choices,
                pre_test_lesson_1_answers
        );
    }

    public static final String[] pre_test_lesson_1_questions = {
            /*1*/    "Which of the following describes regular languages?",
            /*2*/    "What type of memory requirement do regular languages have?",
            /*3*/    "Which operation is applicable to regular languages?",
            /*4*/    "What is the primary use of regular expressions?",
            /*5*/    "Which automata can recognize regular languages?",
            /*6*/    "Which structure represents a regular expression?",
            /*7*/    "What role does finite automata play in computational problems?",
            /*8*/    "Which closure property is true for regular languages?",
            /*9*/    "Which of the following is an application of regular languages?",
            /*10*/   "What does a Kleene star (*) in a regular expression represent?",
            /*11*/   "Which automaton can simulate both deterministic and nondeterministic behavior?",
            /*12*/   "Regular expressions are used to describe which of the following?",
            /*13*/   "Which is not a component of a finite automaton?",
            /*14*/   "How is text search implemented using regular expressions?",
            /*15*/   "Which of the following describes deterministic finite automata (DFA)?"
    };

    public static final String[][] pre_test_lesson_1_choices = {
            /*1*/    {"A type of programming language", "A category of formal languages recognized by finite automata", "A machine learning algorithm", "A hardware design system"},
            /*2*/    {"Large, infinite memory", "Small, limited memory", "Dynamic memory allocation", "No memory required"},
            /*3*/    {"Differentiation", "Union", "Multiplication", "Exponentiation"},
            /*4*/    {"Machine learning", "Text pattern matching", "Data encryption", "Image processing"},
            /*5*/    {"Finite automata", "Pushdown automata", "Turing machines", "All of the above"},
            /*6*/    {"Tree", "Stack", "Set of strings accepted by a finite automaton", "Linked list"},
            /*7*/    {"Track infinite states", "Solve complex mathematical equations", "Model problems requiring small amounts of memory", "Process natural languages"},
            /*8*/    {"They are closed under subtraction", "They are closed under union", "They are closed under differentiation", "They are not closed under any operation"},
            /*9*/    {"Database management", "Lexical analysis in compiler design", "Artificial intelligence", "3D rendering"},
            /*10*/   {"Repeat the preceding character zero or more times", "Optional character", "End of the line", "Negation of the preceding character"},
            /*11*/   {"Finite automaton", "Pushdown automaton", "Mealy machine", "Moore machine"},
            /*12*/   {"Recursive structures", "Regular languages", "Context-free languages", "Data structures"},
            /*13*/   {"Stack memory", "States", "Transition function", "Input alphabet"},
            /*14*/   {"By creating data structures", "By defining patterns to match substrings", "By generating machine code", "By encrypting text data"},
            /*15*/   {"Allows multiple transitions for a single input symbol", "Allows only one transition for a single input symbol", "Uses a stack to track states", "Requires infinite memory to process languages"}
    };

    public static final int[] pre_test_lesson_1_answers = {
            /*1*/    1,  // "A category of formal languages recognized by finite automata"
            /*2*/    1,  // "Small, limited memory"
            /*3*/    1,  // "Union"
            /*4*/    1,  // "Text pattern matching"
            /*5*/    0,  // "Finite automata"
            /*6*/    2,  // "Set of strings accepted by a finite automaton"
            /*7*/    2,  // "Model problems requiring small amounts of memory"
            /*8*/    1,  // "They are closed under union"
            /*9*/    1,  // "Lexical analysis in compiler design"
            /*10*/   0,  // "Repeat the preceding character zero or more times"
            /*11*/   0,  // "Finite automaton"
            /*12*/   1,  // "Regular languages"
            /*13*/   0,  // "Stack memory"
            /*14*/   1,  // "By defining patterns to match substrings"
            /*15*/   1   // "Allows only one transition for a single input symbol"
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |        EASY          |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson1_Easy_Questions() {
        return e_Module_3.get_PostTest_EasyMedium_Questions(
                post_test_lesson_1_questions_easy,
                post_test_lesson_1_choices_easy,
                post_test_lesson_1_answers_easy,
                e_Question.Difficulty.EASY
        );
    }

    public static final String[] post_test_lesson_1_questions_easy = {
            /*1*/    "The Pumping Lemma can be used to prove that a language is regular.",
            /*2*/    "Finite automata can handle arbitrarily deep nested structures, such as balanced parentheses.",
            /*3*/    "The Pumping Lemma states that sufficiently long strings in a regular language can be split into three parts.",
            /*4*/    "Finite automata have infinite memory to process complex patterns.",
            /*5*/    "The word 'lemma' in Pumping Lemma refers to a general rule in language theory.",
            /*6*/    "If a language fails the Pumping Lemma, it is definitely not regular.",
            /*7*/    "Nested structures, such as balanced parentheses, can be recognized by regular languages.",
            /*8*/    "The Pumping Lemma only applies to context-free languages.",
            /*9*/    "Regular languages are closed under operations such as union and Kleene star.",
            /*10*/   "Finite automata can count an arbitrarily high number of occurrences.",
            /*11*/   "The Pumping Lemma provides a method to divide any string into two parts.",
            /*12*/   "A regular language always satisfies the Pumping Lemma.",
            /*13*/   "Finite automata are incapable of handling infinite memory problems.",
            /*14*/   "If a string can be 'pumped,' the resulting string must still be within the regular language.",
            /*15*/   "Finite automata can always recognize languages that require complex memory management."
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
            /*1*/    0,  // True
            /*2*/    1,  // False
            /*3*/    0,  // True
            /*4*/    1,  // False
            /*5*/    0,  // True
            /*6*/    0,  // True
            /*7*/    1,  // False
            /*8*/    1,  // False
            /*9*/    0,  // True
            /*10*/   1,  // False
            /*11*/   1,  // False
            /*12*/   0,  // True
            /*13*/   0,  // True
            /*14*/   0,  // True
            /*15*/   1   // False
    };

    /*
    +----------------------+
    |       POST-TEST      |
    |        MEDIUM        |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson1_Medium_Questions() {
        return e_Module_3.get_PostTest_EasyMedium_Questions(
                post_test_lesson_1_questions_medium,
                post_test_lesson_1_choices_medium,
                post_test_lesson_1_answers_medium,
                e_Question.Difficulty.MEDIUM
        );
    }

    public static final String[] post_test_lesson_1_questions_medium = {
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

    public static final String[][] post_test_lesson_1_choices_medium = {
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

    public static final int[] post_test_lesson_1_answers_medium = {
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

    public static e_Question[] get_PostTest_Lesson1_Hard_Questions() {
        return e_Module_3.get_PostTest_Hard_Questions(
                post_test_lesson_1_hard_questions,
                post_test_lesson_1_answers_hard,
                e_Question.Difficulty.HARD
        );
    }

    public static final String[] post_test_lesson_1_hard_questions = {
            /*1*/    "What is the pumping lemma used to prove?",
            /*2*/    "What type of automata recognizes regular languages?",
            /*3*/    "What is the limitation of regular languages in memory?",
            /*4*/    "Into how many parts is a string divided in the Pumping Lemma?",
            /*5*/    "What does the 'p' in the Pumping Lemma stand for?",
            /*6*/    "Which type of structures cannot be handled by regular languages?",
            /*7*/    "What must a string in a regular language satisfy according to the Pumping Lemma?",
            /*8*/    "What type of language does finite automata fail to recognize?",
            /*9*/    "What part of a string can be repeated in the Pumping Lemma?",
            /*10*/   "Which type of language requires more expressive grammars than regular languages?",
            /*11*/   "What is the main limitation of regular expressions?",
            /*12*/   "How many conditions must be satisfied in the Pumping Lemma?",
            /*13*/   "What mathematical model describes regular languages?",
            /*14*/   "What happens if a language fails the Pumping Lemma test?",
            /*15*/   "What is the intermediate result in a proof called?"
    };

    public static final String[] post_test_lesson_1_answers_hard = {
            /*1*/    "non-regular languages",
            /*2*/    "finite automata",
            /*3*/    "finite memory",
            /*4*/    "three parts",
            /*5*/    "pumping length",
            /*6*/    "nested structures",
            /*7*/    "xyz conditions",
            /*8*/    "non-regular languages",
            /*9*/    "middle section",
            /*10*/   "context-free languages",
            /*11*/   "complex patterns",
            /*12*/   "three conditions",
            /*13*/   "finite automata",
            /*14*/   "irregular",
            /*15*/   "lemma"
    };
}
