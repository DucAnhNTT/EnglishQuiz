package com.mycompany.englishquiz;

import com.mycompany.englishquiz.Code.User;
import static com.mycompany.englishquiz.Code.Utils.DATABASE_URL;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SearchUsers implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TableView<User> tableView;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, String> addressColumn;

    @FXML
    private TableColumn<User, String> genderColumn;

    @FXML
    private TableColumn<User, LocalDate> dobColumn;

    @FXML
    private TableColumn<User, LocalDate> dojColumn;

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private RadioButton maleRadio;

    @FXML
    private RadioButton femaleRadio;

    @FXML
    private DatePicker dobPicker;

    @FXML
    private Button bt_back;

    private UserDAO userDAO;

    private FilteredList<User> filteredList;

    public void switchToMain(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void filterTable() {
        String name = nameField.getText().trim();
        String address = addressField.getText().trim();
        String gender = maleRadio.isSelected() ? "Male" : femaleRadio.isSelected() ? "Female" : "";
        LocalDate dob = dobPicker.getValue();

        try ( Connection conn = DriverManager.getConnection(DATABASE_URL);  UserDAO userDAO = new UserDAO(conn)) {

            List<User> filteredUsers = userDAO.searchUsers(name, address, gender, dob);
            ObservableList<User> userList = FXCollections.observableArrayList(filteredUsers);
            filteredList = new FilteredList<>(userList, p -> true);
            tableView.setItems(filteredList);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Initialize the UserDAO object
            userDAO = new UserDAO(new SqliteConnection().connect());
        } catch (SQLException ex) {
            Logger.getLogger(SearchUsers.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Set up the table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("queQuan"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        dojColumn.setCellValueFactory(new PropertyValueFactory<>("ngayGiaNhap"));

        // Load all the users into the table
        try {
            ObservableList<User> userList = FXCollections.observableArrayList(userDAO.getAllUsers());
            filteredList = new FilteredList<>(userList, p -> true);
            tableView.setItems(filteredList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTable();
        });

        addressField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTable();
        });

        maleRadio.selectedProperty().addListener((observable, oldValue, newValue) -> {
            filterTable();
        });

        femaleRadio.selectedProperty().addListener((observable, oldValue, newValue) -> {
            filterTable();
        });

        dobPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            filterTable();
        });
    }

    // Close the database connection when the window is closed
    @FXML
    private void handleCloseButtonAction(ActionEvent event) {
        try {
            userDAO.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Platform.exit();
    }
}
