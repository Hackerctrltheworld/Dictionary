package DictionaryController;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;


public class MenuController implements Initializable {
    @FXML
    Label dateLabel;
    @FXML
    Button startButton, exitButton;
    @FXML
    AnchorPane parentContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTime();
        startButton.setOnAction(event -> {
            try {
                loadTranslationPage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        exitButton.setOnMouseClicked(mouseEvent -> System.exit(0));
    }

    //Hiện thời gian hiện tại của người dùng
    private void setTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String time = dtf.format(now);
        dateLabel.setText(" Your local date time: " + time);
    }

    //Load ứng dụng từ điển
    public void loadTranslationPage() throws IOException {
        Runnable task = () -> Platform.runLater(() -> {
            Parent root = null;
            try {
                root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/views/FunctionGUI.fxml"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = startButton.getScene();
            assert root != null;
            root.translateXProperty().set(scene.getHeight());
            parentContainer = (AnchorPane) scene.getRoot();
            parentContainer.getChildren().add(root);
            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.LINEAR);
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
            timeline.getKeyFrames().add(kf);
            timeline.play();
        });
        new Thread(task).start();
    }
}



