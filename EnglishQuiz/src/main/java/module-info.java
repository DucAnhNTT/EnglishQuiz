module com.mycompany.englishquiz {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.englishquiz to javafx.fxml;
    exports com.mycompany.englishquiz;
}
