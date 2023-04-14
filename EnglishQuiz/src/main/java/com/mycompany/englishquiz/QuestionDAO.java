package com.mycompany.englishquiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
private Connection conn;

public QuestionDAO(Connection conn) {
    this.conn = conn;
}

public List<Question> getAllQuestions() {
    List<Question> questions = new ArrayList<>();
    String sql = "SELECT * FROM questions";
    try {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int id = rs.getInt("id");
            String questionText = rs.getString("question_text");
            String questionType = rs.getString("question_type");
            Question question = new Question(id, questionText, questionType);
            questions.add(question);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return questions;
}

public Question getQuestionById(int id) {
    Question question = null;
    String sql = "SELECT * FROM questions WHERE id = ?";
    try {
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String questionText = rs.getString("question_text");
            String questionType = rs.getString("question_type");
            question = new Question(id, questionText, questionType);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return question;
}

public List<Option> getOptions(int questionId, Class<?> type) {
    List<Option> options = new ArrayList<>();
    String sql = "SELECT * FROM options WHERE question_id = ?";
    try {
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, questionId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String optionText = rs.getString("option_text");
            boolean isCorrect = rs.getBoolean("is_correct");
            Option option = (Option) type.cast(type.newInstance());
            option.setId(id);
            option.setOptionText(optionText);
            option.setIsCorrect(isCorrect);
            options.add(option);
        }
    } catch (SQLException | InstantiationException | IllegalAccessException e) {
        e.printStackTrace();
    }
    return options;
}

public List<Blank> getBlanks(int questionId, Class<?> type) {
    List<Blank> blanks = new ArrayList<>();
    String sql = "SELECT * FROM blanks WHERE question_id = ?";
    try {
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, questionId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            int blankNumber = rs.getInt("blank_number");
            String correctAnswer = rs.getString("correct_answer");
            Blank blank = (Blank) type.newInstance();
            blank.setId(id);
            blank.setBlankNumber(blankNumber);
            blank.setCorrectAnswer(correctAnswer);
            blanks.add(blank);
        }
    } catch (SQLException | InstantiationException | IllegalAccessException e) {
        e.printStackTrace();
    }
    return blanks;
}

public List<Question> getQuestions(int testId) {
    List<Question> questions = new ArrayList<>();
    String sql = "SELECT * FROM questions WHERE test_id = ?";
    try {
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, testId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String questionText = rs.getString("question_text");
            String questionType = rs.getString("question_type");
            List<Option> options = getOptions(id, Option.class);
            List<Blank> blanks = getBlanks(id, Blank.class);
            Question question = new Question(id, questionText, questionType, options, blanks);
            questions.add(question);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return questions;
}

private List<Option> getOptions(int questionId) {
    List<Option> options = new ArrayList<>();
    String sql = "SELECT * FROM options WHERE question_id = ?";
    try {
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, questionId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String optionText = rs.getString("option_text");
            boolean isCorrect = rs.getBoolean("is_correct");
            Option option = new Option(id, optionText, isCorrect);
            options.add(option);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return options;
}

private List<Blank> getBlanks(int questionId) {
    List<Blank> blanks = new ArrayList<>();
    String sql = "SELECT * FROM blanks WHERE question_id = ?";
    try {
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, questionId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            int blankNumber = rs.getInt("blank_number");
            String correctAnswer = rs.getString("correct_answer");
            Blank blank = new Blank(id, blankNumber, correctAnswer);
            blanks.add(blank);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return blanks;
}
}

