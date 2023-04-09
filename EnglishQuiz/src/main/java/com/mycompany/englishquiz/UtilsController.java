package com.mycompany.EnglishQuiz;

import com.mycompany.englishquiz.SqliteConnection;
import com.mycompany.englishquiz.UserDAO;
import com.mycompany.englishquiz.Code.User;
import com.mycompany.englishquiz.Code.Utils;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UtilsController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private AnchorPane scenePane;

//button xu li
    @FXML
    private Button logoutButton;
    @FXML
    private Button logginButton;

//login
    @FXML
    private TextField tf_password;
    @FXML
    private TextField tf_username;
    @FXML
    private Label lb_loginMessage;

    public void loginButtonOnAction(ActionEvent e) throws SQLException, IOException {
        String username = tf_username.getText();
        String password = tf_password.getText();
        if (!username.isBlank() && !password.isBlank()) {
            if (checkLogin(username, password)) {
                lb_loginMessage.setText("Login successful!");
                switchToMain(e);
            } else {
                lb_loginMessage.setText("Invalid username or password!");
            }
        } else {
            lb_loginMessage.setText("Please enter username and password");
        }
    }

    public boolean checkLogin(String username, String password) throws SQLException {
        SqliteConnection sqliteConnection = new SqliteConnection();
        UserDAO userDAO = new UserDAO(sqliteConnection.connect());
        try {
            User user = userDAO.getUserByHoTen(username);
            if (user != null && user.getMatKhau().equals(password)) {
                return true;
            }
        } catch (SQLException | ParseException ex) {
            ex.printStackTrace();
        }
        userDAO.close();
        return false;
    }

    public void switchToManage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Manage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLoginMain(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("LoginMain.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToMain(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSignUp(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void backToLoginWithAlert(ActionEvent event) throws IOException {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Back to login!?");
        alert.setHeaderText("You're about to back to Login");
        alert.setContentText("Do you want to save your result!?: ");

        if (alert.showAndWait().get() == ButtonType.OK) {
            root = FXMLLoader.load(getClass().getResource("LoginMain.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    public void logout(ActionEvent event) {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You're about to logout!");
        alert.setContentText("Do you want to save before exiting?: ");

        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) scenePane.getScene().getWindow();
            System.out.println("You successfully logged out!");
            stage.close();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
