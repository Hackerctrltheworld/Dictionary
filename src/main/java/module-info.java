module com.example.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires freetts;


    opens DictionaryMain to javafx.fxml;
    exports DictionaryMain;
    exports DictionaryGui;
    opens DictionaryGui to javafx.fxml;
}