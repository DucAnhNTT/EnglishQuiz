package com.mycompany.englishquiz;

import com.mycompany.englishquiz.Code.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    public void addUser(User user) throws SQLException {
        // Check if a user with the same username already exists
        String sql = "SELECT COUNT(*) FROM Users WHERE hoTen = ?";
        try ( PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, user.getHoTen());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                // A user with the same username already exists
                throw new SQLException("Username already exists");
            }
        }
        // Insert the new user
        sql = "INSERT INTO Users(hoTen, matKhau, queQuan, gioiTinh, ngaySinh, ngayGiaNhap) VALUES(?,?,?,?,?,?)";
        try ( PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, user.getHoTen());
            statement.setString(2, user.getMatKhau());
            statement.setString(3, user.getQueQuan());
            statement.setString(4, user.getGioiTinh());
            statement.setString(5, user.getNgaySinh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            statement.setString(6, user.getNgayGiaNhap());
            statement.executeUpdate();
        }
    }

    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE Users SET hoTen = ?, matKhau = ?, queQuan = ?, gioiTinh = ?, ngaySinh = ?, ngayGiaNhap = ? WHERE idUser = ?";
        try ( PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, user.getHoTen());
            statement.setString(2, user.getMatKhau());
            statement.setString(3, user.getQueQuan());
            statement.setString(4, user.getGioiTinh());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            statement.setString(5, dateFormat.format(user.getNgaySinh()));
            statement.setString(6, user.getNgayGiaNhap());
            statement.setInt(7, user.getId());
            statement.executeUpdate();
        }
    }

    public void deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM Users WHERE idUser = ?";
        try ( PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public User getUserById(int idUser) throws SQLException {
        String sql = "SELECT * FROM Users WHERE idUser = ?";
        try ( PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idUser);
            try ( ResultSet result = statement.executeQuery()) {
                User user = null;
                if (result.next()) {
                    // get the values from the result set
                    String hoTen = result.getString("hoTen");
                    String matKhau = result.getString("matKhau");
                    String queQuan = result.getString("queQuan");
                    String gioiTinh = result.getString("gioiTinh");
                    LocalDate ngaySinh = LocalDate.parse(result.getString("ngaySinh"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    String ngayGiaNhap = result.getString("ngayGiaNhap");

                    // create a new User object with the retrieved values
                    user = new User(idUser, hoTen, matKhau, queQuan, gioiTinh, ngaySinh, ngayGiaNhap);
                }
                System.out.println("User: " + user);
                return user;
            }
        }
    }

    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM Users WHERE hoTen = ?";
        try ( PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, username);
            try ( ResultSet result = statement.executeQuery()) {
                User user = null;
                if (result.next()) {
                    // get the values from the result set
                    int idUser = result.getInt("idUser");
                    String hoTen = result.getString("hoTen");
                    String matKhau = result.getString("matKhau");
                    String queQuan = result.getString("queQuan");
                    String gioiTinh = result.getString("gioiTinh");
                    LocalDate ngaySinh = LocalDate.parse(result.getString("ngaySinh"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    String ngayGiaNhap = result.getString("ngayGiaNhap");

                    // create a new User object with the retrieved values
                    user = new User(idUser, hoTen, matKhau, queQuan, gioiTinh, ngaySinh, ngayGiaNhap);
                }
                System.out.println("User: " + user);
                return user;
            }
        }
    }

    public List<User> getAllUsers() throws SQLException {
        String sql = "SELECT * FROM Users";
        try ( PreparedStatement statement = conn.prepareStatement(sql);  ResultSet result = statement.executeQuery()) {
            List<User> userList = new ArrayList<>();
            while (result.next()) {
                int idUser = result.getInt("idUser");
                String hoTen = result.getString("hoTen");
                String matKhau = result.getString("matKhau");
                String queQuan = result.getString("queQuan");
                String gioiTinh = result.getString("gioiTinh");
                LocalDate ngaySinh = LocalDate.parse(result.getString("ngaySinh"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String ngayGiaNhap = result.getString("ngayGiaNhap");
                User user = new User(idUser, hoTen, matKhau, queQuan, gioiTinh, ngaySinh, ngayGiaNhap);
                userList.add(user);
            }
            return userList;
        }
    }

    public void close() throws SQLException {
        conn.close();

    }
}
