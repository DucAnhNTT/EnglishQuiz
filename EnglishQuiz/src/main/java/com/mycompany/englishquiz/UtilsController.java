package com.mycompany.EnglishQuiz;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.mycompany.englishquiz.SqliteConnection;

import com.mycompany.englishquiz.SignUpController;
import com.mycompany.englishquiz.UserDAO;
import com.mycompany.englishquiz.UserSession;
import com.mycompany.englishquiz.Code.User;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class UtilsController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private UserDAO userDAO; // create a local UserDAO object

    @FXML
    private AnchorPane scenePane;

//button xu li
    @FXML
    private Button logoutButton;
    @FXML
    private Button loginButton;

//login
    @FXML
    private TextField tf_password;
    @FXML
    private TextField tf_username;
    @FXML
    private Label lb_loginMessage;

   
    
    @FXML
    public void loginButtonOnAction(ActionEvent event) throws SQLException, IOException {
        if (tf_username.getText().isBlank() || tf_password.getText().isBlank()) {
            lb_loginMessage.setText("Please enter both username and password");
        } else {
            // Check if the entered credentials are valid
            if (checkLogin(tf_username.getText(), new String(tf_password.getText()))) {
                // Clear the login message label
                lb_loginMessage.setText("");

                // Redirect to the MainScreen
                switchScene(event, "MainScreen.fxml");
            } else {
                lb_loginMessage.setText("Invalid login credentials");
            }
        }
    }

    public boolean checkLogin(String username, String password) throws SQLException {
        try (Connection conn = new SqliteConnection().connect()) {
            UserDAO dao = new UserDAO(conn);
            System.out.println("Username: " + username);
            User user = dao.getUserByUsername(username);
            System.out.println("User: " + user);

            if (user != null && user.getMatKhau().equals(password)) {
                // Set the UserSession to indicate that the user is logged in
                UserSession.getInstance().setUser(user);
                System.out.println("User logged in: " + user.getHoTen());
                if (user.isManager()) {
                    System.out.println("User is a manager");
                } else {
                    System.out.println("User is not a manager");
                }
                return true;
            }
            System.out.println("Invalid login credentials");
            return false;
        } catch (SQLException e) {
            // Display an error message if there was an error closing the database connection
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error closing database connection");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return false;
        }
    }

    public void switchToModifyAccount(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ModifyAccount.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLoginMain(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("LoginMain.fxml"));
        stage = new Stage(); // Add this line to create a new Stage object
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
        // Load the SignUp.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
        Parent root = loader.load();
        SignUpController signUpController = loader.getController();
        // Call the initialize() method to save the UserDAO object to the UserSession
        signUpController.initialize();

        // Switch to the SignUp screen
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void logout(ActionEvent event) {
        User loggedInUser = UserSession.getInstance().getUser();
        if (loggedInUser.getType_User() == 1) {
            // Show confirmation dialog
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Logout");
            alert.setHeaderText("You're about to logout!");
            alert.setContentText("Do you want to save before exiting?: ");

            if (alert.showAndWait().get() == ButtonType.OK) {
                // Save the result
            }
        } else {
            // Switch to login screen
            stage = (Stage) scenePane.getScene().getWindow();
            System.out.println("You successfully logged out!");
            stage.close();
        }
    }

    public void switchScene(ActionEvent event, String path) throws IOException {
        root = FXMLLoader.load(getClass().getResource(path));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Connection conn = null;
        // Create a new SqliteConnection object
        SqliteConnection sqliteConnection = null;
        try {
            sqliteConnection = new SqliteConnection();
        } catch (SQLException ex) {
            Logger.getLogger(UtilsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Connect to the database
        conn = sqliteConnection.connect();
        // Retrieve the UserDAO object from the UserSession
        UserDAO dao = UserSession.getInstance().getUserDAO();
        // If the UserDAO object is null, create a new one and save it to the UserSession
        if (dao == null) {
            dao = new UserDAO(conn);
            UserSession.getInstance().setUserDAO(dao);
        }
        // Save the UserDAO object to the local variable
        this.userDAO = dao;
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            // Display an error message if there was an error closing the database connection
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error closing database connection");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

}
