package com.mycompany.englishquiz;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqliteConnection {
    private Connection conn;

    public Connection connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:src\\main\\resources\\Database\\Users.db");
            System.out.println("Connection to SQLite database established.");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getMessage());
        }
        return conn;
    }
    

    public void disconnect() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Connection to SQLite database closed.");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public void updateUser(String username, String password, String name, String email) {
    try {
        conn.setAutoCommit(false);
        String sql = "UPDATE Users SET password = ?, name = ?, email = ? WHERE username = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, password);
        pstmt.setString(2, name);
        pstmt.setString(3, email);
        pstmt.setString(4, username);
        pstmt.executeUpdate();
        conn.commit();
        System.out.println("User updated successfully.");
    } catch (SQLException e) {
        System.err.println(e.getMessage());
        try {
            conn.rollback();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    } finally {
        try {
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}

}

