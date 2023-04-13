package com.mycompany.englishquiz;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Bee
 */
import java.util.List;

public class Question {

    private String question;
    private List choices;
    private int correctAnswerIndex;
    private String difficulty;

    Question(String question, List<String> choices, int correctAnswerIndex, String difficulty) {
        this.question = question;
        this.choices = choices;
        this.correctAnswerIndex = correctAnswerIndex;
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getChoices() {
        return choices;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public boolean checkAnswer(String answer) {
        return choices.get(correctAnswerIndex).equals(answer);
    }
}
