package DictionaryController;

import Notice.Notice;
import TranslateAPI.GoogleAPI;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class TranslateController implements Initializable,Speak {
    @FXML
    TextField wordTarget, wordExplain, sourceText, toText;
    @FXML
    Button vietnameseToEnglish, englishToVietnamese, translateButton;
    @FXML
    Label sourceFlag, desFlag;
    String sourceLang = null;
    String toLang = null;
    Notice notice;
    Notice translate;

    @FXML
    private void handleTranslation() {
        if(sourceText.getText().equals("From: ")) {
            translate = new Notice();
            Alert chooseWord = translate.alertWarning("Unidentified action!", "/icon/ginger_man_question_mark_32px.png", "Hãy chọn ngôn ngữ cần dịch");
            chooseWord.showAndWait();
            return;
        }
        if(wordTarget.getText().trim().isBlank()) {
            notice = new Notice();
            Alert alerWarning = notice.alertWarning("Empty","/icon/collaboration_32px.png","Hãy nhập từ cần dịch");
            alerWarning.showAndWait();
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
        wordTarget.setPromptText("Word to be translated");
        wordExplain.setPromptText("Explanation");
        wordTarget.setEditable(false);
        sourceText.setText("From: ");
        toText.setText("To: ");
        vietnameseToEnglish.setOnAction(event -> handleSwitchLanguageVietToEng());
        englishToVietnamese.setOnAction(event -> handleSwitchLanguageEngToVie());
    }

    @Override
    public void speech() {
        try {
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us" + ".cmu_us_kal.KevinVoiceDirectory");
            Voice voice = VoiceManager.getInstance().getVoice("kevin16");
            voice.allocate();
            if(!wordExplain.getText().isEmpty()) {
                voice.speak(wordExplain.getText());
            }
        } catch (Exception e) {
            System.out.println("Voice not found");
            e.printStackTrace();
        }
    }
}
