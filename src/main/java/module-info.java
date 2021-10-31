module com.example.Dictionary {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires freetts;
    requires javafx.graphics;
    requires javafx.base;


    opens DictionaryMain to javafx.fxml;
    exports DictionaryMain;
    exports DictionaryGui;
    opens DictionaryGui to javafx.fxml;
}