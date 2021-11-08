package DictionaryController;

import TranslateAPI.GoogleAPI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class TranslateController implements Initializable {
    @FXML
    TextField wordTarget, wordExplain, sourceText, toText;
    @FXML
    Button vietnameseToEnglish, englishToVietnamese, translateButton;
    @FXML
    Label sourceFlag, desFlag;
    String sourceLang = null;
    String toLang = null;


    @FXML
    private void handleTranslation() {
        if(sourceText.getText().trim().isBlank()) {
            return;
        }
        String wordTyped = wordTarget.getText().toLowerCase(Locale.ROOT).trim();
        try {
            String wordTranslated = GoogleAPI.translate(sourceLang, toLang, wordTyped);
            wordExplain.setText(wordTranslated);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSwitchLanguageVietToEng() {
        vietnameseToEnglish.setOnAction(event -> {
            handleTranslation("vi", "en", "Vietnamese", "English");
            setFlagLayout(54, 280);
        });
    }

    @FXML
    private void handleSwitchLanguageEngToVie() {

        englishToVietnamese.setOnAction(event -> {
            handleTranslation("en", "vi", "English", "Vietnamese");
            setFlagLayout(280, 54);
        });
    }

    private void setFlagLayout(double x, double y) {
        sourceFlag.setLayoutY(x);
        desFlag.setLayoutY(y);
    }

    private void handleTranslation(String sourceLang, String toLang, String setSourceText, String setToText) {
        translateButton.setDisable(false);
        wordExplain.clear();
        this.sourceLang = sourceLang;
        this.toLang = toLang;
        sourceText.setText(setSourceText);
        toText.setText(setToText);
        wordTarget.setEditable(true);
        sourceText.setText("From: " + setSourceText);
        toText.setText("To: " + setToText);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (sourceText.getText().isEmpty()) {
            translateButton.setDisable(true);
        }
        wordTarget.setEditable(false);
        sourceText.setText("From: ");
        toText.setText("To: ");
        vietnameseToEnglish.setOnAction(event -> handleSwitchLanguageVietToEng());
        englishToVietnamese.setOnAction(event -> handleSwitchLanguageEngToVie());
    }
}
