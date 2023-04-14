/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.englishquiz;

/**
 *
 * @author Bee
 */
public class Option {

    private int id;
    private String optionText;
    private boolean isCorrect;

    public Option(int id, String optionText, boolean isCorrect) {
        this.id = id;
        this.optionText = optionText;
        this.isCorrect = isCorrect;
    }

    public int getId() {
        return id;
    }

    public String getOptionText() {
        return optionText;
    }

    public boolean isCorrect() {
        return isIsCorrect();
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param optionText the optionText to set
     */
    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    /**
     * @return the isCorrect
     */
    public boolean isIsCorrect() {
        return isCorrect;
    }

    /**
     * @param isCorrect the isCorrect to set
     */
    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}
