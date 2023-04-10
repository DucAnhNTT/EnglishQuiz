package com.mycompany.englishquiz.Code;

import static com.mycompany.englishquiz.Code.Utils.DATABASE_URL;
import com.mycompany.englishquiz.SqliteConnection;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            LocalDate ngaySinh = LocalDate.parse(dateFormat.format(user.getNgaySinh()), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate ngayGiaNhap = LocalDate.parse(dateFormat.format(user.getNgayGiaNhap()), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

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

    private static void printUsers() throws ParseException {
        String sql = "SELECT * FROM Users";

        try ( Connection conn = DriverManager.getConnection(DATABASE_URL);  Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {

            // iterate over the result set and print each row
            int i = 1;
            while (rs.next()) {
                String hoTen = rs.getString("hoTen");
                String matKhau = rs.getString("matKhau");
                String queQuan = rs.getString("queQuan");
                String gioiTinh = rs.getString("gioiTinh");
                String ngaySinhString = rs.getString("ngaySinh");
                String ngayGiaNhapString = rs.getString("ngayGiaNhap");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate ngaySinh = LocalDate.parse(ngaySinhString, formatter);
                LocalDate ngayGiaNhap = LocalDate.parse(ngayGiaNhapString, formatter);

                System.out.printf("%d\t%s\t%s\t%s\t%s\t%s\t%s\n", i, hoTen, matKhau, queQuan, gioiTinh, ngaySinh, ngayGiaNhap);
                i++;
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving users: " + e.getMessage());
        }
    }

public static void printAllUsers() throws Exception {
    try (SqliteConnection sqlite = new SqliteConnection()) {
        Connection conn = sqlite.connect();
        String sql = "SELECT * FROM Users";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        System.out.printf("%-5s%-20s%-15s%-15s%-10s%-15s%-15s\n", "ID", "Name", "Password", "Address", "Gender", "Date of Birth", "Date of Joining");
        int i = 0;
        while (rs.next()) {
            int id = rs.getInt("idUser");
            String name = rs.getString("hoTen");
            String password = rs.getString("matKhau");
            String address = rs.getString("queQuan");
            String gender = rs.getString("gioiTinh");
            LocalDate dateOfBirth = LocalDate.parse(rs.getString("ngaySinh"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate dateOfJoining = LocalDate.parse(rs.getString("ngayGiaNhap"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            System.out.printf("%-5d%-20s%-15s%-15s%-10s%-15s%-15s\n", i, name, password, address, gender, dateOfBirth, dateOfJoining);
            i++;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println(e.getMessage());
    }
}

    public static void main(String[] args) throws Exception {
        DemoCode.printAllUsers();
    }
}
