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
}
