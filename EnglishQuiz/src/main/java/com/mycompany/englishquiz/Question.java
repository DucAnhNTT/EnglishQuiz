package com.mycompany.englishquiz;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Bee
 */
import java.util.ArrayList;
import java.util.List;

public class Question {

    private int id;
    private String questionText;
    private String questionType;
    private PartOfSpeech partOfSpeech;
    private DifficultyLevel difficultyLevel;
    private List<Option> options;
    private List<Blank> blanks;
    private Passage passage;

    public Question(int id, String questionText, String questionType) {
        this.id = id;
        this.questionText = questionText;
        this.questionType = questionType;
    }

    public Question(int id, String questionText, String questionType, List<Option> options, List<Blank> blanks) {
        this.id = id;
        this.questionText = questionText;
        this.questionType = questionType;
        this.options = options;
        this.blanks = blanks;
    }

    public Question(int id, String questionText, OptionList options, PartOfSpeech partOfSpeech, DifficultyLevel difficultyLevel) {
        this.id = id;
        this.questionText = questionText;
        this.questionType = "multiple_choice";
        this.options = options;
        this.partOfSpeech = partOfSpeech;
        this.difficultyLevel = difficultyLevel;
    }

    public Question(int id, String questionText, List<Blank> blanks, PartOfSpeech partOfSpeech, DifficultyLevel difficultyLevel) {
        this.id = id;
        this.questionText = questionText;
        this.questionType = "fill_in_the_blank";
        this.blanks = blanks;
        this.partOfSpeech = partOfSpeech;
        this.difficultyLevel = difficultyLevel;
    }

    // getters and setters for the new fields
    public PartOfSpeech getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(PartOfSpeech partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the questionText
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * @param questionText the questionText to set
     */
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    /**
     * @return the questionType
     */
    public String getQuestionType() {
        return questionType;
    }

    /**
     * @param questionType the questionType to set
     */
    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    /**
     * @return the options
     */
    public List<Option> getOptions() {
        return options;
    }

    /**
     * @param options the options to set
     */
    public void setOptions(List<Option> options) {
        this.options = options;
    }

    /**
     * @return the blanks
     */
    public List<Blank> getBlanks() {
        return blanks;
    }

    /**
     * @param blanks the blanks to set
     */
    public void setBlanks(List<Blank> blanks) {
        this.blanks = blanks;
    }

    /**
     * @return the passage
     */
    public Passage getPassage() {
        return passage;
    }

    /**
     * @param passage the passage to set
     */
    public void setPassage(Passage passage) {
        this.passage = passage;
    }
}
