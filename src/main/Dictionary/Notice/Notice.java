package Notice;

import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Notice {
    public Alert alertConfirmation(String title, String path) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setGraphic(setImage(path));
        return alert;
    }

    public Alert alertWarning(String title, String path, String context) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(context);
        alert.setHeaderText(null);
        alert.setGraphic(setImage(path));
        return alert;
    }

    public ImageView setImage(String URL) {
        return new ImageView(Objects.requireNonNull(getClass().getResource(URL)).toExternalForm());
    }
}
