package com.example.autotutoria20;


public class x_Question {
    public enum Difficulty {
        EASY, INTERMEDIATE, EXPERT
    }

    private String question;
    private String[] choices;
    private int correctAnswer;
    private Difficulty difficulty;

    public x_Question(String question, String[] choices, int correctAnswer, Difficulty difficulty) {
        this.question = question;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getChoices() {
        return choices;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public boolean isCorrect(int answer) {
        return answer == correctAnswer;
    }
}
