package DictionaryGui;

import DictionaryMain.Dictionary;
import DictionaryMain.DictionaryCommandline;
import DictionaryMain.DictionaryManagement;
import Notice.Notice;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class SearchController extends Thread implements Initializable {
    String selectedItem ,current;
    DictionaryManagement management = new DictionaryManagement();
    DictionaryCommandline commandline = new DictionaryCommandline();
    int found;
    private ObservableList observableList = FXCollections.observableArrayList();
    @FXML
    private ListView<String> myListView;
    @FXML
    private TextField textField, displayWord;
    @FXML
    private Button acceptButton, editButton, speechButton;
    @FXML
    private Tooltip deleteTooltip, speakTooltip, editTooltip;
    @FXML
    private TextArea explanationField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        myListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        textField.setOnKeyTyped(keyEvent -> {
            if (textField.getText().isEmpty()) {
                observableList = FXCollections.observableList(management.addFromListWord());
                myListView.setItems(observableList);
            } else {
                search();
            }
        });
        for (Tooltip tooltip : Arrays.asList(deleteTooltip, speakTooltip, editTooltip)) {
            tooltip.setShowDelay(LayoutController.DURATION);
        }
    }

    public void search() {
        Runnable task = () -> Platform.runLater(() -> {
            observableList.clear();
            String search = textField.getText().trim().toLowerCase(Locale.ROOT);
            observableList = management.dictionaryLookup(search);
            myListView.setItems(observableList);
            myListView.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
                speechButton.setVisible(true);
                editButton.setVisible(true);
                selectedItem = myListView.getSelectionModel().getSelectedItem();
                displayWord.setText(selectedItem);
                acceptButton.setVisible(false);
                explanationField.setEditable(false);
                explanationField.setOnKeyPressed(keyEvent -> {
                    return;
                });
            });
        });

        new Thread(task).start();
    }


    @FXML
    public void showExplanation() {
        if (selectedItem != null) {
            found = commandline.dictionarySearcherBinary(selectedItem);
            if (found == -1) {
                return;
            }
            editButton.setVisible(true);
            speechButton.setVisible(true);
            displayWord.setText(selectedItem);
            current = selectedItem;
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
            }
        } catch (Exception e) {
            System.out.println("Voice not found");
            e.printStackTrace();
        }
    }

    public void delete() {
        Notice noticeDelete = new Notice();
        String title = "Delete!";
        String path = "/icon/delete_30px.png";
        String context;
        Alert alert;
        if (current == null) {
            context = "Hãy chọn từ để xoá";
            alert = noticeDelete.alertWarning(title,path,context);
            alert.showAndWait();
        } else if (selectedItem != null) {
            alert = noticeDelete.alertConfirmation(title, path);
            alert.setContentText("Bạn có chắc muốn xoá từ này?");
            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.get() == ButtonType.OK) {
                management.removeWord(selectedItem, observableList);
                System.out.println(selectedItem);
                editButton.setVisible(false);
                speechButton.setVisible(false);
                current = null;
//                alert.setContentText();
                title = "Successful!";
                path = "/icon/checked_32px.png";
                context = "Thành công";
                alert = noticeDelete.alertWarning(title,path,context);
                explanationField.setText(null);
                displayWord.setText(null);
                explanationField.setEditable(false);
                acceptButton.setDisable(true);
                alert.showAndWait();
            }
        }
    }

    private void setAlertToEdit(Notice notice) {
        String title = "Edit!";
        String path = "/icon/question_mark_32px.png";
        String context;
        Alert alert = notice.alertConfirmation(title, path);
        alert.setContentText("Bạn chắc chắn chỉnh sửa?");
        Optional<ButtonType> editWord = alert.showAndWait();
        if (editWord.get() == ButtonType.OK) {
            title = "Successful";
            path = "/icon/checked_32px.png";
            context = "Thành công";
            explanationField.setEditable(false);
            alert = notice.alertWarning(title,path,context);
            acceptButton.setVisible(false);
        } else if(editWord.get() == ButtonType.CANCEL) {
            return;
        }
        alert.showAndWait();
    }


    public void edit() {
        if (selectedItem == null) {
            return;
        } else {
            Notice editNotice = new Notice();
            explanationField.setEditable(true);
            acceptButton.setVisible(true);
            acceptButton.setDisable(false);
            acceptButton.setOnAction(event -> {
                setAlertToEdit(editNotice);
                management.editWord(found, explanationField.getText());
            });

            explanationField.setOnKeyPressed(keyEvent -> {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    setAlertToEdit(editNotice);
                } else {
                    return;
                }
            });
        }
    }
}


