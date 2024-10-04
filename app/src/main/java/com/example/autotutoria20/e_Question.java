package com.example.autotutoria20;

public class e_Question {
    private String question;
    private String[] choices;
    private int correctAnswer_preTest;
    private int correctAnswer_EASY_MEDIUM;
    private String correctAnswer_HARD;
    private Difficulty difficulty;
    private QuestionType type;  // New field to store the type of question

    public enum Difficulty {
        EASY, MEDIUM, HARD
    }

    public enum QuestionType {
        TRUE_FALSE,
        MULTIPLE_CHOICE,
        IDENTIFICATION
    }

    // Constructor for pre-test (default multiple choice)
    public e_Question(String question, String[] choices, int correctAnswer) {
        this.question = question;
        this.choices = choices;
        this.correctAnswer_preTest = correctAnswer;
        this.type = QuestionType.MULTIPLE_CHOICE; // Pre-test always multiple-choice
    }

    // Constructor for post-test (determined by difficulty)
    // For EASY and MEDIUM ONLY
    public e_Question(String question, String[] choices, int correctAnswer, Difficulty difficulty) {
        this.question = question;
        this.choices = choices;
        this.correctAnswer_EASY_MEDIUM = correctAnswer;
        this.difficulty = difficulty;

        // Set question type based on difficulty
        switch (difficulty) {
            case EASY:
                this.type = QuestionType.TRUE_FALSE;
                break;
            case MEDIUM:
                this.type = QuestionType.MULTIPLE_CHOICE;
                break;
//            case HARD:
//                this.type = QuestionType.IDENTIFICATION;
//                break;
        }
    }

    // Constructor for post-test (determined by difficulty)
    // For EASY and MEDIUM ONLY
    public e_Question(String question, String correctAnswer, Difficulty difficulty) {
        this.question = question;
        this.correctAnswer_HARD = correctAnswer;
        this.difficulty = difficulty;

        if (difficulty == Difficulty.HARD) {
            this.type = QuestionType.IDENTIFICATION;
        }

    }

    public String getQuestion() {
        return question;
    }

    public String[] getChoices() {
        return choices;
    }

    public int getCorrectAnswer_preTest() {
        return correctAnswer_preTest;
    }

    public int getCorrectAnswer_EASY_MEDIUM() {
        return correctAnswer_EASY_MEDIUM;
    }

    public String getCorrectAnswer_HARD() {
        return correctAnswer_HARD;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public static int getPreTestQuestionCount(String lesson, String module) {
        switch (lesson) {
            case "Lesson 1": return e_Module_1.getPreTestCount(module);
            case "Lesson 2": return e_Module_2.getPreTestCount(module);
            case "Lesson 3": return e_Module_3.getPreTestCount(module);
            case "Lesson 4": return e_Module_4.getPreTestCount(module);
            case "Lesson 5": return e_Module_5.getPreTestCount(module);
            case "Lesson 6": return e_Module_6.getPreTestCount(module);
            case "Lesson 7": return e_Module_7.getPreTestCount(module);
            case "Lesson 8": return e_Module_8.getPreTestCount(module);
            // Add more cases as needed
            default:
                //Log.e("getLessonIndex", "Invalid lesson: " + lesson);
                throw new IllegalArgumentException("Invalid lesson: " + lesson);
        }
    }

}
