package com.mycompany.englishquiz;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.mycompany.englishquiz.Code.User;
import java.text.ParseException;
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
import javafx.util.StringConverter;

public class SignUpController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    // sign up
    @FXML
    private PasswordField tf_SU_password;
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

    public void signUpButtonOnAction(ActionEvent e) throws SQLException, ParseException, IOException {
        if (!tf_SU_username.getText().isBlank() && !tf_SU_password.getText().isBlank() && date_user.getValue() != null
                && !tf_address.getText().isBlank() && (rd_SUMale.isSelected() || rd_SUFemale.isSelected())) {
            // Create a new user object with the input values
            String hoTen = tf_SU_username.getText();
            String matKhau = tf_SU_password.getText();
            String queQuan = tf_address.getText();
            String gioiTinh = rd_SUMale.isSelected() ? "Male" : "Female";
            LocalDate ngaySinh = date_user.getValue();
            String ngaySinhStr = ngaySinh != null ? ngaySinh.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "";
            String ngayGiaNhapStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            User user = new User(hoTen, matKhau, queQuan, gioiTinh, ngaySinhStr, ngayGiaNhapStr);
            // Insert the user into the database
            SqliteConnection sqliteConnection = new SqliteConnection();
            UserDAO userDAO = new UserDAO(sqliteConnection.connect());
            try {
                userDAO.addUser(user);
                lb_signUpMessage.setText("User has been signed up successfully.");
            } catch (SQLException ex) {
                lb_signUpMessage.setText("Username already exists. Please choose a different username.");
            }
            userDAO.close();
            sqliteConnection.disconnect();
        } else {
            lb_signUpMessage.setText("Please fill all value or enter correct date format!");
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
    }

    public void initialize() {
        System.out.println("Initializing SignUpController...");
        // Set the converter to format the date in the "yyyy-MM-dd" format
        date_user.setConverter(new StringConverter<LocalDate>() {
            private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }

}