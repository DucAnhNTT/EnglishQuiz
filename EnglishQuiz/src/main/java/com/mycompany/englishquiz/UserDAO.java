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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO implements AutoCloseable {

    private Connection conn;
    private static final int SQLITE_CONSTRAINT = 19;

    public UserDAO(Connection conn) {
        this.conn = conn;
        System.out.println("UserDAO created with connection: " + conn);
    }

    

    public UserDAO() {
    }

    public boolean checkDuplicateName(String name) throws SQLException {
    String sql = "SELECT * FROM users WHERE hoTen = ?";
    try (PreparedStatement statement = conn.prepareStatement(sql)) {
        statement.setString(1, name);
        try (ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next();
        }
    }
}
    
    public Map<String, Double> getMarksForUser(int userId) throws SQLException {
        Map<String, Double> marks = new HashMap<>();
        String sql = "SELECT TestTypes.name, UserTestMarks.mark FROM UserTestMarks "
                + "INNER JOIN TestTypes ON UserTestMarks.test_type_id = TestTypes.id "
                + "WHERE UserTestMarks.user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String testName = rs.getString("name");
                double mark = rs.getDouble("mark");
                marks.put(testName, mark);
            }
        }
        return marks;
    }

    public void updateMarksForUser(int userId, String testName, double mark) throws SQLException {
        String sql = "INSERT INTO UserTestMarks (user_id, test_type_id, mark) "
                + "VALUES (?, (SELECT id FROM TestTypes WHERE name = ?), ?) "
                + "ON CONFLICT(user_id, test_type_id) DO UPDATE SET mark = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, testName);
            stmt.setDouble(3, mark);
            stmt.setDouble(4, mark);
            stmt.executeUpdate();
        }
    }

    public User getUserByUsernameAndPassword(String username, String password) throws SQLException {
        String sql = "SELECT * FROM Users WHERE hoTen = ? AND matKhau = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("idUser"));
                    user.setHoTen(rs.getString("hoTen"));
                    user.setMatKhau(rs.getString("matKhau"));
                    user.setQueQuan(rs.getString("queQuan"));
                    user.setGioiTinh(rs.getString("gioiTinh"));
                    user.setNgaySinh(rs.getDate("ngaySinh").toLocalDate());
                    user.setNgayGiaNhap(rs.getDate("ngayGiaNhap").toLocalDate());
                    user.setType_User(rs.getInt("typeUser"));
                    return user;
                } else {
                    return null;
                }
            }
        }
    }

    public void addUser(User user) throws SQLException {
        String query = "INSERT INTO Users (hoTen, matKhau, queQuan, gioiTinh, ngaySinh, ngayGiaNhap, typeUser) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getHoTen());
            stmt.setString(2, user.getMatKhau());
            stmt.setString(3, user.getQueQuan());
            stmt.setString(4, user.getGioiTinh());
            stmt.setString(5, user.getNgaySinh() != null ? user.getNgaySinh().format(formatter) : null);
            stmt.setString(6, user.getNgayGiaNhap() != null ? user.getNgayGiaNhap().format(formatter) : null);
            stmt.setInt(7, user.getType_User());
            stmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == SQLITE_CONSTRAINT && e.getMessage().contains("UNIQUE")) {
                throw new SQLException("Username already exists.", e);
            } else {
                throw e;
            }
        }
    }

    public List<User> searchUsers(String name, String address, String gender, LocalDate dob) {
        
        System.out.println("Searching users...");
        String sql = "SELECT * FROM Users WHERE LOWER(hoTen) LIKE LOWER(?) AND LOWER(queQuan) LIKE LOWER(?) AND LOWER(gioiTinh) LIKE LOWER(?) AND ngaySinh LIKE ? ORDER BY idUser";    List<User> userList = new ArrayList<>();
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, "%" + name + "%");
            statement.setString(2, "%" + address + "%");
            statement.setString(3, "%" + gender + "%");
            statement.setString(4, dob != null ? dob.toString() : "%");
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    int idUser = result.getInt("idUser");
                    String hoTen = result.getString("hoTen");
                    String matKhau = result.getString("matKhau");
                    String queQuan = result.getString("queQuan");
                    String gioiTinh = result.getString("gioiTinh");
                    LocalDate ngaySinh = LocalDate.parse(result.getString("ngaySinh"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    LocalDate ngayGiaNhap = LocalDate.parse(result.getString("ngayGiaNhap"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    int typeUser = result.getInt("typeUser");
                    User user = new User(idUser, hoTen, matKhau, queQuan, gioiTinh, ngaySinh, ngayGiaNhap, typeUser);
                    userList.add(user);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching users: " + e.getMessage());
        }
        return userList;
    }

    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE Users SET hoTen = ?, matKhau = ?, queQuan = ?, gioiTinh = ?, ngaySinh = ?, ngayGiaNhap = ? WHERE idUser = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
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
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
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
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idUser);
            try (ResultSet result = statement.executeQuery()) {
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
                    user = new User(idUser, hoTen, matKhau, queQuan, gioiTinh, ngaySinh, ngayGiaNhap, result.getInt("typeUser"));
                }
                System.out.println("User: " + user);
                return user;
            }
        }
    }

    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM Users WHERE hoTen = ?";
        User user = null;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String hoTen = rs.getString("hoTen");
                    String matKhau = rs.getString("matKhau");
                    String queQuan = rs.getString("queQuan");
                    String gioiTinh = rs.getString("gioiTinh");
                    LocalDate ngaySinh = LocalDate.parse(rs.getString("ngaySinh"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    LocalDate ngayGiaNhap = LocalDate.parse(rs.getString("ngayGiaNhap"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                    user = new User(rs.getInt("idUser"), hoTen, matKhau, queQuan, gioiTinh, ngaySinh, ngayGiaNhap, rs.getInt("typeUser"));
                }
            }
        }

        return user;
    }

    public List<User> getAllUsers() throws SQLException {
        String sql = "SELECT * FROM Users";
        List<User> userList = new ArrayList<>();
        try (PreparedStatement statement = conn.prepareStatement(sql); ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                int idUser = result.getInt("idUser");
                String hoTen = result.getString("hoTen");
                String matKhau = result.getString("matKhau");
                String queQuan = result.getString("queQuan");
                String gioiTinh = result.getString("gioiTinh");
                LocalDate ngaySinh = LocalDate.parse(result.getString("ngaySinh"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate ngayGiaNhap = LocalDate.parse(result.getString("ngayGiaNhap"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                int typeUser = result.getInt("typeUser");
                User user = new User(idUser, hoTen, matKhau, queQuan, gioiTinh, ngaySinh, ngayGiaNhap, typeUser);
                userList.add(user);
            }
        }
        return userList;
    }

    public LocalDate getDateOfBirth(String username) throws Exception {
        String sql = "SELECT ngaySinh FROM Users WHERE hoTen = ?";
        try (SqliteConnection connection = new SqliteConnection(); Connection cn = connection.connect(); PreparedStatement pstmt = cn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet resultSet = pstmt.executeQuery()) {
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
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, address);
            try (ResultSet result = statement.executeQuery()) {
                List<User> userList = new ArrayList<>();
                while (result.next()) {
                    int idUser = result.getInt("idUser");
                    String hoTen = result.getString("hoTen");
                    String matKhau = result.getString("matKhau");
                    String queQuan = result.getString("queQuan");
                    String gioiTinh = result.getString("gioiTinh");
                    LocalDate ngaySinh = LocalDate.parse(result.getString("ngaySinh"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    LocalDate ngayGiaNhap = LocalDate.parse(result.getString("ngayGiaNhap"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    User user = new User(idUser, hoTen, matKhau, queQuan, gioiTinh, ngaySinh, ngayGiaNhap, result.getInt("typeUser"));
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
