package com.mycompany.englishquiz.Code;

import static com.mycompany.englishquiz.Code.Utils.DATABASE_URL;
import com.mycompany.englishquiz.SqliteConnection;
import com.mycompany.englishquiz.UserDAO;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DemoCode {

    public static void insertOrUpdateUser(User user) {
        String sql = "UPDATE Users SET matKhau = ?, queQuan=?, gioiTinh=?, ngaySinh=?, ngayGiaNhap=? WHERE hoTen=?";

        try ( Connection conn = DriverManager.getConnection(DATABASE_URL);  PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // check if the matKhau field is null
            if (user.getMatKhau() == null) {
                throw new IllegalArgumentException("matKhau field cannot be null");
            }

            // set the values of the parameters for the prepared statement
            pstmt.setString(1, user.getQueQuan());
            pstmt.setString(2, user.getGioiTinh());

            // convert the ngaySinh and ngayGiaNhap strings to LocalDate objects
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            LocalDate ngaySinh = LocalDate.parse(dateFormat.format(user.getNgaySinh()), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate ngayGiaNhap = LocalDate.parse(user.getNgayGiaNhap(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            pstmt.setString(3, dateFormat.format(Date.from(ngaySinh.atStartOfDay(ZoneId.systemDefault()).toInstant())));
            pstmt.setString(4, dateFormat.format(Date.from(ngayGiaNhap.atStartOfDay(ZoneId.systemDefault()).toInstant())));
            pstmt.setString(5, user.getHoTen());

            // execute the prepared statement
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated == 0) {
                // if no rows were updated, the user does not exist, so insert a new user
                sql = "INSERT INTO Users (hoTen, matKhau, queQuan, gioiTinh, ngaySinh, ngayGiaNhap) VALUES (?, ?, ?, ?, ?, ?)";
                try ( PreparedStatement pstmt2 = conn.prepareStatement(sql)) {
                    pstmt2.setString(1, user.getHoTen());
                    pstmt2.setString(2, user.getMatKhau());
                    pstmt2.setString(3, user.getQueQuan());
                    pstmt2.setString(4, user.getGioiTinh());
                    pstmt2.setString(5, dateFormat.format(Date.from(ngaySinh.atStartOfDay(ZoneId.systemDefault()).toInstant())));
                    pstmt2.setString(6, dateFormat.format(Date.from(ngayGiaNhap.atStartOfDay(ZoneId.systemDefault()).toInstant())));
                    rowsUpdated = pstmt2.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("A new user has been inserted.");
                    }
                }
            } else {
                System.out.println("The user has been updated.");
            }

        } catch (SQLException e) {
            System.err.println("Error inserting/updating user: " + e.getMessage());
        }
    }

    private static void printUsers() {
        String sql = "SELECT * FROM Users";

        try ( Connection conn = DriverManager.getConnection(DATABASE_URL);  Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {

            // iterate over the result set and print each row
            while (rs.next()) {
                String hoTen = rs.getString("hoTen");
                String matKhau = rs.getString("matKhau");
                String queQuan = rs.getString("queQuan");
                String gioiTinh = rs.getString("gioiTinh");
                String ngaySinh = rs.getString("ngaySinh");
                String ngayGiaNhap = rs.getString("ngayGiaNhap");

                System.out.println(hoTen + " \t " + matKhau + " \t " + queQuan + " \t " + gioiTinh + " \t " + ngaySinh + " \t " + ngayGiaNhap);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving users: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException {
        SqliteConnection sqliteConnection = new SqliteConnection();
        Connection conn = sqliteConnection.connect();
        UserDAO userDAO = new UserDAO(conn);

        // Retrieve all users from the database
        List<User> users = userDAO.getAllUsers();

        // Print the users to the console in the desired format
        System.out.printf("%-20s %-20s %-20s %-10s %-15s %-15s\n", "Username", "Password", "Address", "Gender", "Date of Birth", "Date Joined");
        for (User user : users) {
            System.out.printf("%-20s %-20s %-20s %-10s %-15s %-15s\n", user.getHoTen(), user.getMatKhau(), user.getQueQuan(), user.getGioiTinh(), user.getNgaySinh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), user.getNgayGiaNhap());
        }

        userDAO.close();
        sqliteConnection.disconnect();
    }
}
