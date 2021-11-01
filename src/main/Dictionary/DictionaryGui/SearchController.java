package DictionaryGui;

import Database.MySQLConnection;
import DictionaryMain.Dictionary;
import DictionaryMain.DictionaryCommandline;
import DictionaryMain.DictionaryManagement;
import Notice.Notice;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    String selectedItem;
    DictionaryManagement management = new DictionaryManagement();
    DictionaryCommandline commandline = new DictionaryCommandline();
    MySQLConnection connection = new MySQLConnection();
    private ObservableList<String> observableList = FXCollections.observableArrayList();
    @FXML
    private ListView<String> myListView;
    @FXML
    private TextField textField, displayWord;
    @FXML
    private Button deleteButton, addButton, editButton;
    @FXML
    private Tooltip deleteTooltip, speakTooltip, editTooltip;
    @FXML
    private TextArea explanationField;
    int index;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        management.insertFromFile();
        connection.Connection();
        myListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        textField.setOnKeyTyped(keyEvent -> {
            if (textField.getText().isEmpty()) {
                observableList.clear();
                selectedItem = null;
            } else {
                search();
            }
        });

        deleteTooltip.setShowDelay(LayoutController.DURATION);
        speakTooltip.setShowDelay(LayoutController.DURATION);
        speakTooltip.setShowDelay(LayoutController.DURATION);
    }

    public void search() {
        observableList.clear();
        String search = textField.getText().trim().toLowerCase(Locale.ROOT);
        observableList = management.dictionaryLookup(search);
        myListView.setItems(observableList);
        myListView.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            selectedItem = myListView.getSelectionModel().getSelectedItem();
            displayWord.setText(selectedItem);
        });
    }

    @FXML
    public void showExplainaton() {
        if(selectedItem != null) {
            int found = commandline.dictionarySearcherBinary(selectedItem);
            if(found == -1) {
                return;
            }
            explanationField.setText(Dictionary.listWord.get(found).getWord_explain());
        }
    }

    public void speech() {
        try {
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us" + ".cmu_us_kal.KevinVoiceDirectory");
            Voice voice = VoiceManager.getInstance().getVoice("kevin16");
            voice.allocate();
            if (selectedItem != null) {
                voice.speak(selectedItem);
            } else {
                voice.speak("No word selected");
            }
        } catch (Exception e) {
            System.out.println("Voice not found");
            e.printStackTrace();
        }
    }

    public void delete() {
        Notice noticeDelete = new Notice();
        String title = "Delete!";
        String path = "/delete_30px.png";
        Alert alert = noticeDelete.alertConfirmation(title, path);
        alert.setHeaderText(null);
        if(selectedItem == null) {
            alert.setContentText("Hãy chọn từ để xoá");
            alert.showAndWait();
        }
        else if(selectedItem != null) {
            alert.setContentText("Bạn có chắc muốn xoá từ này?");
            Optional<ButtonType> buttonType = alert.showAndWait();
            if(buttonType.get() == ButtonType.OK) {
                observableList.remove(selectedItem);
                alert.setTitle("Successfully!");
                alert.setContentText("Thành công");
                alert.setGraphic(new ImageView("/checked_32px.png"));
                explanationField.setText(null);
                alert.showAndWait();
            }
        }
    }

    public void edit() {
        explanationField.setEditable(true);
    }
}


