package com.mycompany.englishquiz;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import com.mycompany.englishquiz.Code.User;

public class ModifyAccount {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private SqliteConnection sqlite;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private RadioButton maleRadioButton;

    @FXML
    private RadioButton femaleRadioButton;

    @FXML
    private TextField addressField;

    @FXML
    private DatePicker dateOfBirthPicker;

    @FXML
    private Label messageLabel;

    public ModifyAccount() {
        sqlite = new SqliteConnection();
    }

    public void changePassword() {
        String newPassword = passwordField.getText();
        int userId = UserSession.getInstance().getLoggedInUserId();
        try ( Connection conn = sqlite.connect()) {
            String sql = "UPDATE users SET mat_khau = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newPassword);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
            messageLabel.setText("Password changed successfully");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            messageLabel.setText("Failed to change password");
        }
    }

    public void updateName() {
        String newName = nameField.getText();
        int userId = UserSession.getInstance().getLoggedInUserId();
        try ( Connection conn = sqlite.connect()) {
            String sql = "UPDATE users SET ho_ten = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newName);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
            messageLabel.setText("Name updated successfully");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            messageLabel.setText("Failed to update name");
        }
    }

    public void updateGender() {
        String newGender = maleRadioButton.isSelected() ? "Nam" : "Nữ";
        int userId = UserSession.getInstance().getLoggedInUserId();
        try ( Connection conn = sqlite.connect()) {
            String sql = "UPDATE users SET gioi_tinh = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newGender);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
            messageLabel.setText("Gender updated successfully");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            messageLabel.setText("Failed to update gender");
        }
    }

    public void updateAddress() {
        String newAddress = addressField.getText();
        int userId = UserSession.getInstance().getLoggedInUserId();
        try ( Connection conn = sqlite.connect()) {
            String sql = "UPDATE users SET que_quan = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newAddress);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
            messageLabel.setText("Address updated successfully");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            messageLabel.setText("Failed to update address");
        }
    }

    public void updateDateOfBirth() {
        LocalDate newDateOfBirth = dateOfBirthPicker.getValue();
        int userId = UserSession.getInstance().getLoggedInUserId();
        try ( Connection conn = sqlite.connect()) {
            String sql = "UPDATE users SET ngay_sinh = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newDateOfBirth.toString());
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
            messageLabel.setText("Date of birth updated successfully");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            messageLabel.setText("Failed to update date of birth");
        }
    }

    public void updateUserInfo() {
        String newName = nameField.getText();
        String newGender = maleRadioButton.isSelected() ? "Nam" : "Nữ";
        String newAddress = addressField.getText();
        LocalDate newDateOfBirth = dateOfBirthPicker.getValue();
        int userId = UserSession.getInstance().getLoggedInUserId();
        try ( Connection conn = sqlite.connect()) {
            String sql = "UPDATE users SET ho_ten = ?, gioi_tinh = ?, que_quan = ?, ngay_sinh = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newName);
            pstmt.setString(2, newGender);
            pstmt.setString(3, newAddress);
            pstmt.setString(4, newDateOfBirth.toString());
            pstmt.setInt(5, userId);
            pstmt.executeUpdate();
            messageLabel.setText("User info updated successfully");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            messageLabel.setText("Failed to update user info");
        }
    }

    public void back(ActionEvent event) throws IOException {
        // load the main menu
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        // switch to the main menu
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public Parent getRoot() {
        if (root == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyAccount.fxml"));
                loader.setController(this);
                root = loader.load();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return root;
    }

    public Stage getStage() {
        if (stage == null) {
            stage = new Stage();
            stage.setScene(new Scene(getRoot()));
        }
        return stage;
    }
}
