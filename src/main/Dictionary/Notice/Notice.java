package Notice;

import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;

public class Notice {
    public Alert alertConfirmation(String title, String path) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setGraphic(new ImageView(path));
        return alert;
    }
}
