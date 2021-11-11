package DictionaryController;

import Database.MySQLConnection;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class FunctionController implements Initializable {
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
        exitButton.setOnAction(event -> backToMenu());
        googleButton.setOnAction(event -> initSelectedScene("/views/TranslationGUI.fxml"));
        searchTooltip.setShowDelay(DURATION);
        exitTooltip.setShowDelay(DURATION);
        addTooltip.setShowDelay(DURATION);
        translateTooltip.setShowDelay(DURATION);
    }

    public void initSelectedScene(String path) {
        try {
            AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
            mainAnchorpane.getChildren().clear();
            mainAnchorpane.getChildren().add(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultScreen() {
        initSelectedScene("/views/SearchGUI.fxml");
    }

    public void backToMenu() {
        Parent root = null;
        try {
            root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/views/MenuGUI.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = exitButton.getScene();
        assert root != null;
        root.translateYProperty().set(scene.getHeight());
        mainAnchorpane = (AnchorPane) scene.getRoot();
        mainAnchorpane.getChildren().add(root);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.LINEAR);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event -> mainAnchorpane.getChildren().remove(mainAnchorpane));
        timeline.play();
    }

}


