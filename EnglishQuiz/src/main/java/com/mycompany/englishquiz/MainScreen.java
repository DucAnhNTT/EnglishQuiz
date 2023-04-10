package com.mycompany.englishquiz;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainScreen implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private String username;
    private String password;

    @FXML
    private Button bt_main_practice;
    @FXML
    private Button bt_main_test;
    @FXML
    private Button bt_main_manage;
    @FXML
    private Label lb_welcome;
    @FXML
    private Button bt_backToLogin;

    public MainScreen() {
    }

    public void backToLoginWithAlert(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
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

    public void welcome_User(String username) {
        lb_welcome.setText("Welcome " + username + "!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set the action for the bt_main_practice button
        bt_main_practice.setOnAction(event -> {
            // Code to handle the button click goes here
            System.out.println("Practice button clicked");
        });

        // Set the action for the bt_main_test button
        bt_main_test.setOnAction(event -> {
            // Code to handle the button click goes here
            System.out.println("Test button clicked");
        });

        // Set the action for the bt_main_manage button
        bt_main_manage.setOnAction(event -> {
            try {
                switchToManage(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Set the action for the bt_backToLogin button
        bt_backToLogin.setOnAction(event -> {
            try {
                backToLoginWithAlert(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void switchToManage(ActionEvent event) throws IOException {
        try {
            root = FXMLLoader.load(getClass().getResource("Manage.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
