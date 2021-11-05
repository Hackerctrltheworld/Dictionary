package DictionaryGui;

import Database.MySQLConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LayoutController implements Initializable {
    public final static Duration DURATION = new Duration(0.1);
    MySQLConnection connection = new MySQLConnection();
    @FXML
    Button searchButton, exitButton, googleButton;
    @FXML
    Tooltip searchTooltip, addTooltip, exitTooltip, translateTooltip;
    @FXML
    AnchorPane mainAnchorpane = new AnchorPane();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection.Connection();
        setDefaultScreen();
        searchButton.setOnAction(event -> initSelectedScene("/views/SearchGUI.fxml"));
        exitButton.setOnMouseClicked(mouseEvent -> System.exit(0));
            googleButton.setOnAction(event -> {
            initSelectedScene("/views/GoogleAPI.fxml");
        });
        searchTooltip.setShowDelay(DURATION);
        exitTooltip.setShowDelay(DURATION);
        addTooltip.setShowDelay(DURATION);
        translateTooltip.setShowDelay(DURATION);

    }

    public void initSelectedScene(String path) {
        try {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));
            mainAnchorpane.getChildren().clear();
            mainAnchorpane.getChildren().add(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultScreen() {
        initSelectedScene("/views/SearchGUI.fxml");
    }
}


