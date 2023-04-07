package com.mycompany.EnglishQuiz;

import com.mycompany.englishquiz.MainScreen;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

//sign up
    @FXML
    private TextField tf_SU_password;
    @FXML
    private TextField tf_SU_username;
    @FXML
    private DatePicker date_user;
    @FXML
    private TextField tf_address;
    @FXML
    private RadioButton rd_SUMale;
    @FXML
    private RadioButton rd_SUFemale;
    @FXML
    private Label lb_signUpMessage;

    public void logginButtonOnAction(ActionEvent e) {
        if (tf_username.getText().isBlank() == false && tf_password.getText().isBlank() == false) {
            lb_loginMessage.setText("You try to login!");
        } else {
            lb_loginMessage.setText("Please enter username and password");
        }
    }

    public void signUpButtonOnAction(ActionEvent e) {
        if (!tf_SU_username.getText().isBlank() && !tf_SU_password.getText().isBlank()
                && date_user.getValue() != null && !tf_address.getText().isBlank()
                && (rd_SUMale.isSelected() || rd_SUFemale.isSelected())) {
            //xu li sign up
            
        } else {
            lb_signUpMessage.setText("Please fill all value");
        }
    }

// xu li login
//    public void changeScene(ActionEvent event, String username) throws IOException {
//        if (username != null) {
//            try {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
//                Parent root = loader.load();
//                MainScreen controller = loader.getController();
//                controller.welcome_User(username);
//                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                scene = new Scene(root);
//                stage.setScene(scene);
//                stage.show();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            try {
//                root = FXMLLoader.load(getClass().getResource("LoginMain.fxml"));
//                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                scene = new Scene(root);
//                stage.setScene(scene);
//                stage.show();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } 
//        }
//    }
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
