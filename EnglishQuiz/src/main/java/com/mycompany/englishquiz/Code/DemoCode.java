package com.mycompany.englishquiz.Code;

import static com.mycompany.englishquiz.Code.Utils.DATABASE_URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class DemoCode {

public static void insertOrUpdateUser(User user) {
    String sql = "UPDATE Users SET queQuan=?, gioiTinh=?, ngaySinh=?, ngayGiaNhap=? WHERE hoTen=?";

    try (Connection conn = DriverManager.getConnection(DATABASE_URL);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        // set the values of the parameters for the prepared statement
        pstmt.setString(1, user.getQueQuan());
        pstmt.setString(2, user.getGioiTinh());

        // format the LocalDate objects as Strings using SimpleDateFormat
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String ngaySinhStr = dateFormat.format(user.getNgaySinh());
        String ngayGiaNhapStr = dateFormat.format(user.getNgayGiaNhap());

        pstmt.setString(3, ngaySinhStr);
        pstmt.setString(4, ngayGiaNhapStr);
        pstmt.setString(5, user.getHoTen());

        // execute the prepared statement
        int rowsUpdated = pstmt.executeUpdate();
        if (rowsUpdated == 0) {
            // if no rows were updated, the user does not exist, so insert a new user
            sql = "INSERT INTO Users (hoTen, queQuan, gioiTinh, ngaySinh, ngayGiaNhap) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt2 = conn.prepareStatement(sql)) {
                pstmt2.setString(1, user.getHoTen());
                pstmt2.setString(2, user.getQueQuan());
                pstmt2.setString(3, user.getGioiTinh());
                pstmt2.setString(4, ngaySinhStr);
                pstmt2.setString(5, ngayGiaNhapStr);
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
                String queQuan = rs.getString("queQuan");
                String gioiTinh = rs.getString("gioiTinh");
                String ngaySinh = rs.getString("ngaySinh");
                String ngayGiaNhap = rs.getString("ngayGiaNhap");

                System.out.println(hoTen + " \t " + queQuan + " \t " + gioiTinh + " \t " + ngaySinh + " \t " + ngayGiaNhap);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving users: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws ParseException {
        // print the contents of the User table
        System.out.println("User table:");
        printUsers();
    }
}