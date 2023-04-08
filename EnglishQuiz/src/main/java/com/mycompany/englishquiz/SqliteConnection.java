package com.mycompany.englishquiz;


import java.sql.Connection;
import java.sql.DriverManager;
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
}

