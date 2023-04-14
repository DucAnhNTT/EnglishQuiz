/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.englishquiz;

import java.util.List;

/**
 *
 * @author Bee
 */
public class Passage {
    private int id;
    private String passageText;
    private List<Question> questions;

    public Passage(int id, String passageText, List<Question> questions) {
        this.id = id;
        this.passageText = passageText;
        this.questions = questions;
    }

    public int getId() {
        return id;
    }

    public String getPassageText() {
        return passageText;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
