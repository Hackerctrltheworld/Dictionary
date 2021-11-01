package DictionaryGui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Layout.fxml"));
        stage.setTitle("DictionaryMain.Dictionary app");
        Scene scene = new Scene(root);
        Image icon = new Image("book.png");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }
}