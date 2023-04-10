package com.mycompany.englishquiz;

import com.mycompany.englishquiz.Code.User;
import static com.mycompany.englishquiz.Code.Utils.DATABASE_URL;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ManageController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private AnchorPane scenePane;
    @FXML
    private Button bt_searchUser;
    @FXML
    private Button bt_searchQuestion;
    @FXML
    private Button bt_resultStat;
    @FXML
    private Button bt_modifyInfo;
    @FXML
    private Button bt_deleteUser;
    @FXML
    private Label lb_manageMessage;

    // ...
    public void switchToSearchUser(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("SearchUsers.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSearchQuestion(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("SearchQuestion.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void switchToLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("LoginMain.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) bt_deleteUser.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("Click OK to confirm.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage) scenePane.getScene().getWindow();
            System.out.println("You successfully logged out!");
            stage.close();
        }
    }

    public void switchToResultStat(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ResultStat.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToModifyAccount(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ModifyAccount.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

@FXML
private void buttonDeleteUserOnAction(ActionEvent event) {
    try {
        // Get the current user
        User currentUser = UserSession.getInstance().getUser();
        System.out.println("Deleting user: " + currentUser.getHoTen());

        // Delete the user from the database
        UserDAO userDAO = new UserDAO(DriverManager.getConnection(DATABASE_URL));
        userDAO.deleteUser(currentUser.getHoTen());
        userDAO.close();

        // Clear the user session and switch to the login screen
        UserSession.getInstance().clear();
        System.out.println("User session cleared");
        switchToLogin();
    } catch (SQLException e) {
        lb_manageMessage.setText("Error deleting user: " + e.getMessage());
        System.err.println("Error deleting user: " + e.getMessage());
    }
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

        // Call the initialize() method to enable/disable the buttons based on the user's login status
        initialize();
    }

    // Initialize the controller// Initialize the controller
    public void initialize() {
        System.out.println("Initializing buttons...");
        // Enable/disable the buttons based on the user's login status
        if (!UserSession.getInstance().isLoggedIn()) {
            bt_searchUser.setDisable(true);
            bt_searchQuestion.setDisable(true);
            bt_resultStat.setDisable(true);
            bt_modifyInfo.setDisable(true);
            bt_deleteUser.setDisable(true);
        } else {
            bt_searchUser.setDisable(false);
            bt_searchQuestion.setDisable(false);
            bt_resultStat.setDisable(false);
            bt_modifyInfo.setDisable(false);
            bt_deleteUser.setDisable(false);
        }
    }
}
