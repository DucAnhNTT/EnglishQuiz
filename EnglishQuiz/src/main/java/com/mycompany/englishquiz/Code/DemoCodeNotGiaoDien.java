package com.mycompany.englishquiz.Code;

import static com.mycompany.englishquiz.Code.Utils.DATABASE_URL;
import java.sql.*;

public class DemoCodeNotGiaoDien {

    
    private static void insertUser(String hoTen, String queQuan, String gioiTinh, String ngaySinh, String ngayGiaNhap) {
        String sql = "INSERT INTO User (hoTen, queQuan, gioiTinh, ngaySinh, ngayGiaNhap) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the values of the parameters for the prepared statement
            pstmt.setString(1, hoTen);
            pstmt.setString(2, queQuan);
            pstmt.setString(3, gioiTinh);
            pstmt.setString(4, ngaySinh);
            pstmt.setString(5, ngayGiaNhap);

            // execute the prepared statement
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new user has been inserted.");
            }

        } catch (SQLException e) {
            System.err.println("Error inserting user: " + e.getMessage());
        }
    }

    private static void printUsers() {
        String sql = "SELECT * FROM User";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // iterate over the result set and print each row
            while (rs.next()) {
                int userId = rs.getInt("userId");
                String hoTen = rs.getString("hoTen");
                String queQuan = rs.getString("queQuan");
                String gioiTinh = rs.getString("gioiTinh");
                String ngaySinh = rs.getString("ngaySinh");
                String ngayGiaNhap = rs.getString("ngayGiaNhap");

                System.out.println(userId + " | " + hoTen + " | " + queQuan + " | " + gioiTinh + " | " + ngaySinh + " | " + ngayGiaNhap);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving users: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // insert a new user
        insertUser("Hung", "sadasd", "Male", "1990-01-01", "2022-04-08");

        // print the contents of the User table
        System.out.println("User table:");
        printUsers();
    }
}

