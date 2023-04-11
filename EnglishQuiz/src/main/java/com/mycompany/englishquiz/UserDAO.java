package com.mycompany.englishquiz;

import com.mycompany.englishquiz.Code.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements AutoCloseable {

    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    public UserDAO() {
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
            statement.setString(5, user.getNgaySinh().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            statement.setString(6, user.getNgayGiaNhap().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            statement.executeUpdate();
        }
    }

    public List<User> searchUsers(String name, String address, String gender, LocalDate dob) {
        List<User> users = new ArrayList<>();

        try ( PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE hoTen LIKE ? AND queQuan LIKE ? AND gioiTinh LIKE ? AND ngaySinh LIKE ?")) {

            stmt.setString(1, "%" + name + "%");
            stmt.setString(2, "%" + address + "%");
            stmt.setString(3, "%" + gender + "%");
            stmt.setString(4, dob != null ? dob.toString() : "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idUser");
                String hoTen = rs.getString("hoTen");
                String matKhau = rs.getString("matKhau");
                String queQuan = rs.getString("queQuan");
                String gioiTinh = rs.getString("gioiTinh");
                LocalDate ngaySinh = LocalDate.parse(rs.getString("ngaySinh"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate ngayGiaNhap = LocalDate.parse(rs.getString("ngayGiaNhap"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                User user = new User(id, hoTen, matKhau, queQuan, gioiTinh, ngaySinh, ngayGiaNhap);
                users.add(user);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return users;
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

    public void deleteUser(String hoTen) throws SQLException {
        String sql = "DELETE FROM Users WHERE hoTen = ?";
        try ( PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, hoTen);
            int rowsDeleted = statement.executeUpdate();
            System.out.println(rowsDeleted + " rows deleted");
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            throw e;
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
                    LocalDate ngaySinh = LocalDate.parse(result.getString("ngaySinh"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    LocalDate ngayGiaNhap = LocalDate.parse(result.getString("ngayGiaNhap"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

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
        User user = null;

        try ( PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try ( ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String hoTen = rs.getString("hoTen");
                    String matKhau = rs.getString("matKhau");
                    String queQuan = rs.getString("queQuan");
                    String gioiTinh = rs.getString("gioiTinh");
                    LocalDate ngaySinh = LocalDate.parse(rs.getString("ngaySinh"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    LocalDate ngayGiaNhap = LocalDate.parse(rs.getString("ngayGiaNhap"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                    user = new User(hoTen, matKhau, queQuan, gioiTinh, ngaySinh, ngayGiaNhap);
                }
            }
        }

        return user;
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
                LocalDate ngaySinh = LocalDate.parse(result.getString("ngaySinh"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate ngayGiaNhap = LocalDate.parse(result.getString("ngayGiaNhap"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                User user = new User(idUser, hoTen, matKhau, queQuan, gioiTinh, ngaySinh, ngayGiaNhap);
                userList.add(user);
            }
            return userList;
        }
    }

    public LocalDate getDateOfBirth(String username) throws Exception {
        String sql = "SELECT ngaySinh FROM Users WHERE hoTen = ?";
        try ( SqliteConnection connection = new SqliteConnection();  Connection cn = connection.connect();  PreparedStatement pstmt = cn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try ( ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    try {
                        // Parse the date string using a DateTimeFormatter
                        String dateString = resultSet.getString("ngaySinh");
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        return LocalDate.parse(dateString, formatter);
                    } catch (DateTimeParseException e) {
                        // Use the current date as the default value
                        return LocalDate.now();
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public List<User> getUsersByAddress(String address) throws SQLException {
        String sql = "SELECT * FROM Users WHERE queQuan = ?";
        try ( PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, address);
            try ( ResultSet result = statement.executeQuery()) {
                List<User> userList = new ArrayList<>();
                while (result.next()) {
                    int idUser = result.getInt("idUser");
                    String hoTen = result.getString("hoTen");
                    String matKhau = result.getString("matKhau");
                    String queQuan = result.getString("queQuan");
                    String gioiTinh = result.getString("gioiTinh");
                    LocalDate ngaySinh = LocalDate.parse(result.getString("ngaySinh"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    LocalDate ngayGiaNhap = LocalDate.parse(result.getString("ngayGiaNhap"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    User user = new User(idUser, hoTen, matKhau, queQuan, gioiTinh, ngaySinh, ngayGiaNhap);
                    userList.add(user);
                }
                return userList;
            }
        }
    }

    public void close() throws SQLException {
        conn.close();

    }
}
