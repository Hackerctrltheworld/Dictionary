module com.example.Dictionary {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires freetts;
    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;


    opens DictionaryMain to javafx.fxml;
    exports DictionaryMain;
    exports DictionaryController;
    opens DictionaryController to javafx.fxml;
    exports Database;
    opens Database to javafx.fxml;
}