package com.mycompany.englishquiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import com.mycompany.englishquiz.Code.User;
import com.mycompany.englishquiz.Code.Utils;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserDAO {

    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
        createTable();
    }

    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Users (\n"
                    + "    hoTen TEXT PRIMARY KEY,\n"
                    + "    matKhau TEXT NOT NULL,\n"
                    + "    queQuan TEXT NOT NULL,\n"
                    + "    gioiTinh TEXT NOT NULL,\n"
                    + "    ngaySinh TEXT NOT NULL,\n"
                    + "    ngayGiaNhap TEXT NOT NULL\n"
                    + ");";
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertUser(User user) throws SQLException {
        String sql = "INSERT INTO Users (hoTen, matKhau, queQuan, gioiTinh, ngaySinh, ngayGiaNhap) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getHoTen());
        statement.setString(2, user.getMatKhau());
        statement.setString(3, user.getQueQuan());
        statement.setString(4, user.getGioiTinh());
        statement.setString(5, Utils.f.format(user.getNgaySinh()));
        statement.setString(6, Utils.f.format(new java.util.Date()));
        statement.executeUpdate();
        statement.close();
    }

    public User getUserByHoTen(String hoTen) throws SQLException, ParseException {
        String sql = "SELECT * FROM Users WHERE hoTen = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, hoTen);
        ResultSet result = statement.executeQuery();
        User user = null;
        if (result.next()) {
            String matKhau = result.getString("matKhau");
            String queQuan = result.getString("queQuan");
            String gioiTinh = result.getString("gioiTinh");
            String ngaySinhString = result.getString("ngaySinh");
            Date ngaySinh = new SimpleDateFormat("dd/MM/yyyy").parse(ngaySinhString);
            String ngayGiaNhap = result.getString("ngayGiaNhap");
            user = new User(hoTen, matKhau, queQuan, gioiTinh, Utils.f.format(ngaySinh), ngayGiaNhap);
        }
        result.close();
        statement.close();
        return user;
    }

    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE Users SET matKhau = ?, queQuan = ?, gioiTinh = ?, ngaySinh = ? WHERE hoTen = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getMatKhau());
        statement.setString(2, user.getQueQuan());
        statement.setString(3, user.getGioiTinh());
        statement.setString(4, Utils.f.format(user.getNgaySinh()));
        statement.setString(5, user.getHoTen());
        statement.executeUpdate();
        statement.close();
    }

    public void deleteUser(String hoTen) throws SQLException {
        String sql = "DELETE FROM Users WHERE hoTen = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, hoTen);
        statement.executeUpdate();
        statement.close();
    }

    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
