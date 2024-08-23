package com.example.autotutoria20;

public class e_Question {
    private String question;
    private String[] choices;
    private int correctAnswer;
    private Difficulty difficulty;

    public enum Difficulty {
        EASY, MEDIUM, HARD
    }

    public e_Question(String question, String[] choices, int correctAnswer, Difficulty difficulty) {
        this.question = question;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
    }

    // Getters and setters for the class fields
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
