package com.mycompany.englishquiz.Code;

import static com.mycompany.englishquiz.Code.Utils.DATABASE_URL;
import com.mycompany.englishquiz.SqliteConnection;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DemoCode {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static boolean typeUserExists(int typeUser) {
        String sql = "SELECT COUNT(*) FROM User_type WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, typeUser);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking if typeUser exists: " + e.getMessage());
        }
        return false;
    }

    public static void insertOrUpdateUser(User user) {
        try (SqliteConnection conn = new SqliteConnection()) {
            // check if the given type_User exists in the User_type table
            String query = "SELECT COUNT(*) FROM User_type WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, user.getType_User());
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            if (count == 0) {
                System.err.println("Type user does not exist.");
                return;
            }

            // insert or update the user in the Users table
            query = "REPLACE INTO Users (hoTen, matKhau, queQuan, gioiTinh, ngaySinh, ngayGiaNhap, typeUser) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, user.getHoTen());
            stmt.setString(2, user.getMatKhau());
            stmt.setString(3, user.getQueQuan());
            stmt.setString(4, user.getGioiTinh());
            stmt.setString(5, user.getNgaySinh().format(DATE_FORMATTER));
            stmt.setString(6, user.getNgayGiaNhap().format(DATE_FORMATTER));
            stmt.setInt(7, user.getType_User());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting/updating user: " + e.getMessage());
        }
    }

    public static void printAllUsers() {
        String sql = "SELECT u.idUser, u.hoTen, u.matKhau, u.queQuan, u.gioiTinh, u.ngaySinh, u.ngayGiaNhap, t.type "
                + "FROM Users u "
                + "JOIN User_type t ON u.typeUser = t.id";

        try (SqliteConnection conn = new SqliteConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("idUser");
                String hoTen = rs.getString("hoTen");
                String matKhau = rs.getString("matKhau");
                String queQuan = rs.getString("queQuan");
                String gioiTinh = rs.getString("gioiTinh");
                LocalDate ngaySinh = LocalDate.parse(rs.getString("ngaySinh"));
                LocalDate ngayGiaNhap = LocalDate.parse(rs.getString("ngayGiaNhap"));
                String type = rs.getString("type");

                System.out.printf("%-5d%-20s%-20s%-20s%-10s%-20s%-20s%-10s%n", id, hoTen, matKhau, queQuan, gioiTinh, ngaySinh, ngayGiaNhap, type);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving users: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        printAllUsers();
    }
}
