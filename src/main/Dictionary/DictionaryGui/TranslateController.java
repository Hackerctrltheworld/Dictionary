package DictionaryGui;

import TranslateAPI.GoogleAPI;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Locale;

public class TranslateController {
    @FXML
    TextField wordTarget, wordExplain;
    String sourceLang = "en";
    String toLang = "vi";
//    GoogleAPI googleAPI = new GoogleAPI();

    @FXML
    private void handleTranslation() {
        String wordTyped = wordTarget.getText().toLowerCase(Locale.ROOT).trim();
        try {
            String wordTranslated = GoogleAPI.translate(sourceLang,toLang,wordTyped);
            wordExplain.setText(wordTranslated);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
