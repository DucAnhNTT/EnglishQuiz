package com.mycompany.englishquiz;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class ModifyAccount {

    public ModifyAccount() throws SQLException {
        sqlite = new SqliteConnection();
    }

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

public void updateUserInfo() throws IOException {
    System.out.println("updateUserInfo() called");
    String newName = nameField.getText();
    String newPassword = passwordField.getText();
    String newGender = maleRadioButton.isSelected() ? "Nam" : "Nữ";
    String newAddress = addressField.getText();
    LocalDate newDateOfBirth = dateOfBirthPicker.getValue();
    String userName = UserSession.getInstance().getLoggedInUserName();
    String sql = "UPDATE Users SET hoTen = ?, matKhau = ?, gioiTinh = ?, queQuan = ?, ngaySinh = ? WHERE hoTen = ?";

    System.out.println("newName: " + newName);
    System.out.println("newPassword: " + newPassword);
    System.out.println("newGender: " + newGender);
    System.out.println("newAddress: " + newAddress);
    System.out.println("newDateOfBirth: " + newDateOfBirth);

    // Check if any of the text fields are empty
    if (newName.isEmpty() || newPassword.isEmpty() || newAddress.isEmpty() || newDateOfBirth == null) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("Please fill in all the fields.");
        alert.showAndWait();
        return;
    }

    // Show a confirmation alert before updating the user info
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Confirmation");
    alert.setHeaderText(null);
    alert.setContentText("Are you sure you want to update your user info?");
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
        try ( Connection conn = DriverManager.getConnection("jdbc:sqlite:src\\main\\resources\\Database\\Users.db");  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newName);
            pstmt.setString(2, newPassword);
            pstmt.setString(3, newGender);
            pstmt.setString(4, newAddress);
            pstmt.setString(5, newDateOfBirth.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            pstmt.setString(6, userName);
            System.out.println("SQL query: " + sql);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User info updated successfully");
                messageLabel.setText("User info updated successfully");

                // switch to the login screen
                FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginMain.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);

                Stage stage = (Stage) messageLabel.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } else {
                System.out.println("Failed to update user info");
                messageLabel.setText("Failed to update user info");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            messageLabel.setText("Failed to update user info");
        }
    }
}

    public void back(ActionEvent event) throws IOException {
        // load the main menu
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
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
