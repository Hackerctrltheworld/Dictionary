package DictionaryController;

import DictionaryMain.Dictionary;
import DictionaryMain.DictionaryCommandline;
import DictionaryMain.Word;
import Notice.Notice;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import static DictionaryController.FunctionController.observableList;

public class AddController implements Initializable {
    @FXML
    Button addButton;
    @FXML
    TextField wordTarget, wordExplanation;
    @FXML
    Label successLabel;
    int found;
    DictionaryCommandline dictionaryCommandline = new DictionaryCommandline();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        successLabel.setVisible(false);
    }

    public void addWord() {
        if(wordTarget.getText().isEmpty()) {
            return;
        }
        successLabel.setVisible(true);
        String addWordTarget = wordTarget.getText().toLowerCase(Locale.ROOT).trim();
        String addWordExplanation = wordExplanation.getText().toLowerCase(Locale.ROOT).trim();
        found = dictionaryCommandline.dictionarySearcherBinary(addWordTarget);
        if(found != -1) {
            successLabel.setText("Từ này đã tồn tại!");

        } else {
            Dictionary.listWord.add(new Word(addWordTarget,addWordExplanation));
            successLabel.setText("Thêm từ mới thành công!");
        }
    }
}
