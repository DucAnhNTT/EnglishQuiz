    package com.mycompany.englishquiz;

import com.mycompany.englishquiz.Code.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class SqliteConnection implements AutoCloseable {

    private Connection conn;

    public SqliteConnection() throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:src\\main\\resources\\Database\\Users.db");
    }

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

    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE Users SET hoTen = ?, matKhau = ?, queQuan = ?, gioiTinh = ?, ngaySinh = ?, ngayGiaNhap = ? WHERE idUser = ?";
        try ( PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, user.getHoTen());
            statement.setString(2, user.getMatKhau());
            statement.setString(3, user.getQueQuan());
            statement.setString(4, user.getGioiTinh());
            statement.setString(5, user.getNgaySinh().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            statement.setString(6, user.getNgayGiaNhap().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            statement.setInt(7, user.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void close() throws Exception {
        disconnect();
    }
}
