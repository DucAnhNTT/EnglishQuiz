package com.mycompany.englishquiz.Code;

import com.mycompany.englishquiz.Blank;
import static com.mycompany.englishquiz.Code.Utils.DATABASE_URL;
import com.mycompany.englishquiz.DifficultyLevel;
import com.mycompany.englishquiz.PartOfSpeech;
import com.mycompany.englishquiz.Question;
import com.mycompany.englishquiz.SqliteConnection;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class DemoCode {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static boolean typeUserExists(int typeUser) {
        String sql = "SELECT COUNT(*) FROM User_type WHERE id = ?";
        try ( Connection conn = DriverManager.getConnection(DATABASE_URL);  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, typeUser);
            try ( ResultSet rs = pstmt.executeQuery()) {
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
        try ( SqliteConnection conn = new SqliteConnection()) {
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

        try ( SqliteConnection conn = new SqliteConnection();  Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
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
        Question question = new Question(
                1,
                "The effects of overpopulation are quite severe. One of these is rise in unemployment. When a country (4) ___________ overpopulated, it gives rise to unemployment as there are (5) ___________ jobs to support a large number of people. Rise in unemployment gives rise to (6) ___________ as people will steal various items to feed their family and (7)___________them basic amenities of life.\n\nHigh cost of living is another effect. As difference (8) ___________ demand and supply continues to expand due to overpopulation, it raises the (9) ___________ of various commodities including food, shelter and healthcare. This means that people have to pay (10)___________to survive and feed their families.",
                "Fill-in-the-Blank",
                PartOfSpeech.NOUN,
                DifficultyLevel.MEDIUM,
                null,
                Arrays.asList(
                        new Blank(4, "becomes", "becomes"),
                        new Blank(5, "fewer", "fewer"),
                        new Blank(6, "crime", "crime"),
                        new Blank(7, "deprives", "deprives"),
                        new Blank(8, "between", "between"),
                        new Blank(9, "prices", "prices"),
                        new Blank(10, "more", "more")
                ),
                null
        );

// Add answers to the blanks
        question.getBlanks().get(0).setAnswer("becomes");
        question.getBlanks().get(1).setAnswer("fewer");
        question.getBlanks().get(2).setAnswer("crime");
        question.getBlanks().get(3).setAnswer("deprives");
        question.getBlanks().get(4).setAnswer("between");
        question.getBlanks().get(5).setAnswer("prices");
        question.getBlanks().get(6).setAnswer("more");

// Print out the question and blanks
        System.out.println(question.getQuestionText());
        for (Blank blank : question.getBlanks()) {
            System.out.println(blank.getId() + ". " + blank.getText());
        }

// Print out the answers to the blanks
        for (Blank blank : question.getBlanks()) {
            System.out.println(blank.getId() + ". " + blank.getAnswer());
        }
    }
}
