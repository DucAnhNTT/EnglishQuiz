module com.mycompany.englishquiz {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.mycompany.englishquiz to javafx.fxml;
    exports com.mycompany.englishquiz;
}
