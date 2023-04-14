/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.englishquiz;

/**
 *
 * @author Bee
 */
public class Blank {
    private int id;
    private int blankNumber;
    private String correctAnswer;

    public Blank(int id, int blankNumber, String correctAnswer) {
        this.id = id;
        this.blankNumber = blankNumber;
        this.correctAnswer = correctAnswer;
    }

    public int getId() {
        return id;
    }

    public int getBlankNumber() {
        return blankNumber;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param blankNumber the blankNumber to set
     */
    public void setBlankNumber(int blankNumber) {
        this.blankNumber = blankNumber;
    }

    /**
     * @param correctAnswer the correctAnswer to set
     */
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
