package com.mycompany.englishquiz;

import com.mycompany.englishquiz.Code.User;
import static com.mycompany.englishquiz.Code.User.DATE_FORMATTER;
import static com.mycompany.englishquiz.Code.Utils.DATABASE_URL;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SearchUsers implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private UserDAO userDAO = new UserDAO();
    private boolean isManager;

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
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, Integer> idColumn;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, String> addressColumn;
    @FXML
    private TableColumn<User, String> genderColumn;
    @FXML
    private TableColumn<User, LocalDate> dobColumn;

    @FXML
    private TableColumn<User, LocalDate> dojColumn;
    @FXML
    private TableColumn<User, String> typeColumn;
    @FXML
    private Button bt_add;
    @FXML
    private Button bt_modify;
    @FXML
    private Button bt_delete;

    private ObservableList<User> userList; // Define userList here

    private FilteredList<User> filteredList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        typeColumn.setCellValueFactory(cellData -> {
            int typeUser = cellData.getValue().getType_User();
            String typeString = typeUser == 1 ? "Manager" : "User";
            return new SimpleStringProperty(typeString);
        });
        dojColumn.setCellValueFactory(new PropertyValueFactory<>("ngayGiaNhap"));

        // Load the data into the table
        userList = FXCollections.observableArrayList();
        try {
            SqliteConnection connection = new SqliteConnection(); // Create a new connection
            connection.connect(); // Connect to the database
            UserDAO userDAO = new UserDAO(connection); // Create an instance of UserDAO with the connection
            userList.addAll(userDAO.getAllUsers()); // Call getAllUsers() on the instance
            connection.close(); // Close the database connection
        } catch (SQLException ex) {
            Logger.getLogger(SearchUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableView.setItems(userList);

        // Set the isManager flag based on the logged in user's type
        UserSession userSession = UserSession.getInstance();
        boolean isManager = userSession.isLoggedIn() && userSession.getUser().getType_User() == 1;
        setIsManager(isManager);

        // Disable add, modify, and delete buttons if user is not a manager
        if (!isManager) {
            bt_add.setDisable(true);
            bt_modify.setDisable(true);
            bt_delete.setDisable(true);
            tableView.getColumns().remove(passwordColumn);
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

    @FXML
    public void filterTable() {
        String name = nameField.getText().trim();
        String address = addressField.getText().trim();
        final String gender;
        if (maleRadio.isSelected()) {
            gender = "Nam";
        } else if (femaleRadio.isSelected()) {
            gender = "Nữ";
        } else {
            gender = "";
        }
        LocalDate dob = dobPicker.getValue();

        try (Connection conn = DriverManager.getConnection(DATABASE_URL); UserDAO userDAO = new UserDAO(conn)) {

            List<User> filteredUsers = userDAO.searchUsers(name, address, gender, dob);
            ObservableList<User> userList = FXCollections.observableArrayList(filteredUsers);
            filteredList = new FilteredList<>(userList);
            filteredList.setPredicate(user -> {
                if (gender.isEmpty()) {
                    return true;
                } else {
                    String gioiTinh = user.getGioiTinh();
                    return gioiTinh != null && gioiTinh.equals(gender);
                }
            });
            tableView.setItems(filteredList);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void deleteUser() throws SQLException {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete User");
            alert.setContentText("Are you sure you want to delete this user?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                int idUser = selectedUser.getId();
                String sql = "DELETE FROM Users WHERE idUser = ?";
                try (Connection conn = DriverManager.getConnection(DATABASE_URL); PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setInt(1, idUser);
                    int rowsDeleted = statement.executeUpdate();
                    System.out.println(rowsDeleted + " rows deleted");
                    userList.remove(selectedUser);
                    tableView.refresh(); // Refresh the table view
                } catch (SQLException e) {
                    System.err.println("Error deleting user: " + e.getMessage());
                    throw e;
                }
            }
        }
    }

    @FXML
    private void addUser() {
        try {
            // Get the user information from the UI components
            String name = nameField.getText();
            String address = addressField.getText();
            String gender = null;
            if (maleRadio.isSelected()) {
                gender = "Male";
            } else if (femaleRadio.isSelected()) {
                gender = "Female";
            }
            LocalDate dob = dobPicker.getValue();
            LocalDate doj = LocalDate.now();

            // Prompt the user to enter a password
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add User");
            dialog.setHeaderText("Enter a password for the new user:");
            dialog.setContentText("Password:");
            Optional<String> result = dialog.showAndWait();

            // Check if the password and date of birth are entered
            if (result.isPresent() && dob != null) {
                String password = result.get();
                int typeUser = 0;
                User newUser = new User(name, password, address, gender, dob, doj, typeUser);

                // Add the new user to the database
                userDAO.addUser(newUser);

                // Clear the UI components
                nameField.clear();
                addressField.clear();
                maleRadio.setSelected(false);
                femaleRadio.setSelected(false);
                dobPicker.setValue(null);

                // Refresh the table view to show the new user
                filterTable();
            } else {
                // Show an error message if the password or date of birth is not entered
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Input");
                alert.setContentText("Please enter a password and select a date of birth.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

@FXML
void addUser(ActionEvent event) {
    // Get the user information from the UI components
    String name = nameField.getText();
    String address = addressField.getText();
    String gender;
    if (maleRadio.isSelected()) {
        gender = "Male";
    } else if (femaleRadio.isSelected()) {
        gender = "Female";
    } else {
        // No gender selected
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Input");
        alert.setContentText("Please select a gender.");
        alert.showAndWait();
        return;
    }
    LocalDate dob = dobPicker.getValue();
    LocalDate doj = LocalDate.now();

    // Validate the input
    if (name.isEmpty() || address.isEmpty() || dob == null || dob.isAfter(LocalDate.now())) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Input");
        alert.setContentText("Please enter valid information.");
        alert.showAndWait();
        return;
    }

    // Prompt the user to enter a password
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Enter Password");
    dialog.setHeaderText("Please enter a password:");
    dialog.setContentText("Password:");

    Optional<String> result = dialog.showAndWait();
    if (!result.isPresent()) {
        // User cancelled the dialog
        return;
    }

    String password = result.get();

    // Create a new user object
    User newUser = new User(name, password, address, gender, dob, doj, 0);

    // Add the new user to the database
    try {
        userDAO.addUser(newUser);
    } catch (SQLException e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Database Error");
        alert.setContentText("An error occurred while adding the user to the database.");
        alert.showAndWait();
        e.printStackTrace();
        return;
    }

    // Clear the UI components
    nameField.clear();
    addressField.clear();
    dobPicker.setValue(null);

    // Refresh the table view
    filterTable();
}

    private boolean isValidPassword(String password) {
        // Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
        return password.matches(pattern);
    }

    @FXML
    private void modifyUser() {
        try {
            // Get the selected user from the table view
            User selectedUser = tableView.getSelectionModel().getSelectedItem();

            // Get the updated user information from the UI components
            String name = nameField.getText();
            String address = addressField.getText();
            String gender = null;
            if (maleRadio.isSelected()) {
                gender = "Male";
            } else if (femaleRadio.isSelected()) {
                gender = "Female";
            }
            LocalDate dob = dobPicker.getValue();

            // Prompt the user to enter a new password
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Modify User");
            dialog.setHeaderText("Enter a new password for the user:");
            dialog.setContentText("New Password:");
            Optional<String> result = dialog.showAndWait();

            // Check if the new name and address already exist in the database
            boolean isDuplicate = userDAO.checkDuplicateName(name);

            // Check if the password and date of birth are entered
            if (result.isPresent() && dob != null) {
                // Show an error message if the new name and address already exist in the database
                if (isDuplicate) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Duplicate User");
                    alert.setContentText("A user with the same name and address already exists in the database.");
                    alert.showAndWait();
                } else {
                    // Update the selected user with the new information and password
                    String password = result.get();
                    selectedUser.setHoTen(name);
                    selectedUser.setMatKhau(password);
                    selectedUser.setQueQuan(address);
                    selectedUser.setGioiTinh(gender);
                    selectedUser.setNgaySinh(dob);

                    // Update the user in the database
                    userDAO.updateUser(selectedUser);

                    // Clear the UI components
                    nameField.clear();
                    addressField.clear();
                    maleRadio.setSelected(false);
                    femaleRadio.setSelected(false);
                    dobPicker.setValue(null);

                    // Refresh the table view to show the updated user
                    filterTable();
                }
            } else {
                // Show an error message if the password or date of birth is not entered
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Input");
                alert.setContentText("Please enter a new password and select a date of birth.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void switchToMain(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void close() throws SQLException {
        userDAO.close();
    }

    public void setIsManager(boolean isManager) {
        System.out.println("Setting isManager to " + isManager);
        this.isManager = isManager;
        if (UserSession.getInstance().isLoggedIn() && UserSession.getInstance().getUser().getType_User() == 1) {
            System.out.println("User is logged in as a manager");
            bt_add.setDisable(false);
            bt_modify.setDisable(false);
            bt_delete.setDisable(false);
            if (!tableView.getColumns().contains(passwordColumn)) {
                tableView.getColumns().add(passwordColumn);
            }
        } else {
            System.out.println("User is not logged in as a manager");
            bt_add.setDisable(true);
            bt_modify.setDisable(true);
            bt_delete.setDisable(true);
            tableView.getColumns().remove(passwordColumn);
        }
    }
}
