package com.example.autotutoria20;

public class e_Module_7_1 {

    /*
    +----------------------+
    |       PRE-TEST       |
    +----------------------+
    */

    public static e_Question[] get_PreTest_Questions() {
        return e_Module_7.getPreTestQuestions(
                pre_test_questions,
                pre_test_choices,
                pre_test_answers
        );
    }

    public static final String[] pre_test_questions = {
            /*1*/    "What is inherent ambiguity?",
            /*2*/    "Which of the following is an example of inherent ambiguity?",
            /*3*/    "What can help resolve inherent ambiguity in language?",
            /*4*/    "Which field is least likely to accept inherent ambiguity?",
            /*5*/    "In legal documents, what often causes inherent ambiguity?",
            /*6*/    "Which of the following sentences contains inherent ambiguity?",
            /*7*/    "In programming, inherent ambiguity can lead to:",
            /*8*/    "How can inherent ambiguity affect communication?",
            /*9*/    "Why is inherent ambiguity sometimes used in creative writing?",
            /*10*/   "What term refers to reducing the number of possible meanings in an ambiguous situation?",
            /*11*/   "In which of the following can inherent ambiguity be a problem?",
            /*12*/   "A phrase is inherently ambiguous if:",
            /*13*/   "What is an important factor in resolving inherent ambiguity?",
            /*14*/   "Which of the following fields actively embraces inherent ambiguity?",
            /*15*/   "Inherent ambiguity can sometimes lead to which of the following?"
    };

    public static final String[][] pre_test_choices = {
            /*1*/    {"A situation with only one interpretation", "A situation that cannot be resolved", "A situation with multiple possible interpretations", "A situation where ambiguity is not possible"},
            /*2*/    {"I will meet you tomorrow.", "The bank was closed.", "She fed her cat food.", "The sun rises in the east."},
            /*3*/    {"Removing context", "Adding more complexity", "Providing clear context", "Ignoring the ambiguity"},
            /*4*/    {"Creative writing", "Law", "Mathematics", "Art"},
            /*5*/    {"Lack of vocabulary", "Vague terms and clauses", "Specific and clear language", "Mathematical errors"},
            /*6*/    {"I parked my car outside the mall.", "The chicken is ready to eat.", "She walked quickly down the street.", "Water freezes at 0°C."},
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

    public static final int[] pre_test_answers = {
            /*1*/    2,  // 'A situation with multiple possible interpretations'
            /*2*/    2,  // 'She fed her cat food.'
            /*3*/    2,  // 'Providing clear context'
            /*4*/    2,  // 'Mathematics'
            /*5*/    1,  // 'Vague terms and clauses'
            /*6*/    1,  // 'The chicken is ready to eat.'
            /*7*/    1,  // 'Multiple possible outputs'
            /*8*/    1,  // 'It can lead to misinterpretation'
            /*9*/    2,  // 'To encourage multiple interpretations'
            /*10*/   1,  // 'Clarification'
            /*11*/   2,  // 'Programming'
            /*12*/   1,  // 'It can be interpreted in several ways'
            /*13*/   2,  // 'Considering the context'
            /*14*/   0,  // 'Creative writing'
            /*15*/   2   // 'Misunderstandings in communication'
    };

    /*
    +----------------------+
    |   POST-TEST EASY     |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson1_Easy_Questions() {
        return e_Module_7.get_PostTest_EasyMedium_Questions(
                post_test_easy_questions,
                post_test_easy_choices,
                post_test_easy_answers,
                e_Question.Difficulty.EASY
        );
    }

    public static final String[] post_test_easy_questions = {
            /*1*/    "Context-Free Grammars (CFGs) are used to define the syntax of programming languages.",
            /*2*/    "A CFG can have more than one start symbol.",
            /*3*/    "In Chomsky Normal Form (CNF), each production rule can have either two non-terminals or one terminal.",
            /*4*/    "The CNF allows epsilon (ε) as a production for any non-terminal.",
            /*5*/    "Greibach Normal Form (GNF) requires that every production begins with a terminal symbol.",
            /*6*/    "Left recursion is allowed in Greibach Normal Form (GNF).",
            /*7*/    "In CNF, a terminal symbol can appear alongside non-terminals in the same production rule.",
            /*8*/    "CNF simplifies CFGs for use in parsing algorithms like the CYK algorithm.",
            /*9*/    "A CFG consists of a set of production rules, a set of terminals, a set of non-terminals, and a start symbol.",
            /*10*/   "In Greibach Normal Form (GNF), production rules can begin with non-terminal symbols.",
            /*11*/   "Removing null productions is a step in converting a CFG to CNF.",
            /*12*/   "The conversion to CNF or GNF always results in a more complex CFG.",
            /*13*/   "In GNF, there must be exactly two non-terminal symbols on the right-hand side of every production rule.",
            /*14*/   "Left recursion must be eliminated when converting a CFG to Greibach Normal Form (GNF).",
            /*15*/   "Greibach Normal Form is particularly useful for bottom-up parsing algorithms."
    };

    public static final String[][] post_test_easy_choices = {
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

    public static final int[] post_test_easy_answers = {
            /*1*/    0,  // 'True'
            /*2*/    1,  // 'False'
            /*3*/    0,  // 'True'
            /*4*/    1,  // 'False'
            /*5*/    0,  // 'True'
            /*6*/    1,  // 'False'
            /*7*/    1,  // 'False'
            /*8*/    0,  // 'True'
            /*9*/    0,  // 'True'
            /*10*/   1,  // 'False'
            /*11*/   0,  // 'True'
            /*12*/   1,  // 'False'
            /*13*/   1,  // 'False'
            /*14*/   0,  // 'True'
            /*15*/   0   // 'True'
    };

    /*
    +----------------------+
    |   POST-TEST MEDIUM   |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson1_Medium_Questions() {
        return e_Module_7.get_PostTest_EasyMedium_Questions(
                post_test_medium_questions,
                post_test_medium_choices,
                post_test_medium_answers,
                e_Question.Difficulty.MEDIUM
        );
    }

    public static final String[] post_test_medium_questions = {
            /*1*/    "What is a key characteristic of Chomsky Normal Form (CNF)?",
            /*2*/    "Which of the following is not part of a Context-Free Grammar (CFG)?",
            /*3*/    "In Greibach Normal Form (GNF), production rules must start with:",
            /*4*/    "Which of the following must be eliminated to convert a CFG to CNF?",
            /*5*/    "In CNF, what is the rule for the start symbol?",
            /*6*/    "Which of the following is a step in converting a CFG to GNF?",
            /*7*/    "What is a benefit of converting a CFG to Chomsky Normal Form (CNF)?",
            /*8*/    "What is the form of a production in Greibach Normal Form (GNF)?",
            /*9*/    "Which algorithm benefits from CFGs in Chomsky Normal Form?",
            /*10*/   "Which of the following forms is used to simplify CFGs in theoretical computer science?",
            /*11*/   "What is the main goal of normalizing CFGs into GNF or CNF?",
            /*12*/   "Which of the following is a feature of Greibach Normal Form (GNF)?",
            /*13*/   "In a CFG, what does the set of non-terminals represent?",
            /*14*/   "Which of the following is NOT allowed in Chomsky Normal Form?",
            /*15*/   "What happens to left recursion in a CFG when converting to GNF?"
    };

    public static final String[][] post_test_medium_choices = {
            /*1*/    {"Every production starts with a terminal symbol", "Each production rule generates at most two non-terminals or one terminal", "Production rules must begin with a non-terminal symbol", "It allows left recursion"},
            /*2*/    {"Production rules", "Start symbol", "Terminals", "Deterministic finite automaton"},
            /*3*/    {"A non-terminal", "A terminal", "A combination of terminal and non-terminal", "The start symbol"},
            /*4*/    {"Terminal symbols", "Left recursion", "Null and unit productions", "Start symbol"},
            /*5*/    {"It generates only non-terminals", "It generates exactly one terminal", "It can generate the empty string (ε)", "It does not participate in derivation"},
            /*6*/    {"Removing left recursion", "Introducing new terminal symbols", "Introducing unit productions", "Allowing null productions"},
            /*7*/    {"Easier to generate random strings from the grammar", "Simplifies parsing algorithms like the CYK algorithm", "Ensures every derivation starts with a terminal", "Guarantees linear time parsing"},
            /*8*/    {"A → BC", "A → B", "A → aA", "A → ε"},
            /*9*/    {"Top-down parsing", "CYK algorithm", "Earley's parser", "Recursive descent parser"},
            /*10*/   {"Regular Expressions", "Context-Sensitive Grammars", "Chomsky Normal Form (CNF)", "Finite State Machines"},
            /*11*/   {"To make grammars non-deterministic", "To simplify their structure for easier parsing and manipulation", "To reduce the number of productions", "To introduce ambiguity in the grammar"},
            /*12*/   {"Each production rule begins with a non-terminal", "Left recursion is allowed", "Each production rule starts with a terminal", "Every rule produces exactly one terminal"},
            /*13*/   {"The final symbols of the grammar", "The symbols that generate other non-terminals or terminals", "The end of the grammar's derivation", "Invalid productions"},
            /*14*/   {"A → a", "A → BC", "A → ε", "A → B"},
            /*15*/   {"It is allowed", "It is eliminated", "It is replaced with unit productions", "It becomes nullable"}
    };

    public static final int[] post_test_medium_answers = {
            /*1*/    1,  // 'Each production rule generates at most two non-terminals or one terminal'
            /*2*/    3,  // 'Deterministic finite automaton'
            /*3*/    1,  // 'A terminal'
            /*4*/    1,  // 'Left recursion'
            /*5*/    2,  // 'It can generate the empty string (ε)'
            /*6*/    0,  // 'Removing left recursion'
            /*7*/    1,  // 'Simplifies parsing algorithms like the CYK algorithm'
            /*8*/    2,  // 'A → aA'
            /*9*/    1,  // 'CYK algorithm'
            /*10*/   2,  // 'Chomsky Normal Form (CNF)'
            /*11*/   1,  // 'To simplify their structure for easier parsing and manipulation'
            /*12*/   2,  // 'Each production rule starts with a terminal'
            /*13*/   1,  // 'The symbols that generate other non-terminals or terminals'
            /*14*/   2,  // 'A → ε'
            /*15*/   1   // 'It is eliminated'
    };

    /*
    +----------------------+
    |    POST-TEST HARD    |
    +----------------------+
    */

    public static e_Question[] get_PostTest_Lesson1_Hard_Questions() {
        return e_Module_7.get_PostTest_Hard_Questions(
                post_test_hard_questions,
                post_test_hard_answers,
                e_Question.Difficulty.HARD
        );
    }

    public static final String[] post_test_hard_questions = {
            /*1*/    "What must be eliminated to convert to GNF?",
            /*2*/    "What symbol represents the empty string?",
            /*3*/    "What does a CFG generate?",
            /*4*/    "What does CNF stand for?",
            /*5*/    "What symbol begins CFG derivation?",
            /*6*/    "What are non-terminal symbols replaced by?",
            /*7*/    "What type of parser benefits from GNF?",
            /*8*/    "What is the process of simplifying CFGs called?",
            /*9*/    "What algorithm uses CNF?",
            /*10*/   "What is used to generate terminal symbols?",
            /*11*/   "What must a CFG eventually derive?",
            /*12*/   "What normal form requires exactly two non-terminals?",
            /*13*/   "What eliminates unit productions in CFGs?",
            /*14*/   "What is replaced by production rules?",
            /*15*/   "What do you call left-hand side symbols in CFGs?"
    };

    public static final String[] post_test_hard_answers = {
            /*1*/    "left recursion",
            /*2*/    "epsilon",
            /*3*/    "strings",
            /*4*/    "chomsky normal form",
            /*5*/    "start symbol",
            /*6*/    "terminals",
            /*7*/    "top-down parser",
            /*8*/    "normalization",
            /*9*/    "CYK algorithm",
            /*10*/   "production rules",
            /*11*/   "terminals",
            /*12*/   "chomsky normal form",
            /*13*/   "CNF conversion",
            /*14*/   "non-terminals",
            /*15*/   "non-terminals"
    };
}