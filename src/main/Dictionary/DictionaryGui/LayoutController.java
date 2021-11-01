package DictionaryGui;

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
    @FXML
    Button searchButton;
    @FXML
    Tooltip searchTooltip, addTooltip;
    @FXML
    Tooltip exitTooltip;
    @FXML
    Button exitButton;
    @FXML
    AnchorPane mainAnchorpane = new AnchorPane();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDefaultScreen();
        searchButton.setOnAction(event -> initSelectedScene("/views/SearchGUI.fxml"));
        exitButton.setOnMouseClicked(mouseEvent -> System.exit(0));


        searchTooltip.setShowDelay(DURATION);
        exitTooltip.setShowDelay(DURATION);
        addTooltip.setShowDelay(DURATION);
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


