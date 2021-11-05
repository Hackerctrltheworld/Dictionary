package DictionaryGui;

import Database.MySQLConnection;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class SearchController extends Thread implements Initializable {
    String selectedItem;
    DictionaryManagement management = new DictionaryManagement();
    DictionaryCommandline commandline = new DictionaryCommandline();
    int found;
    private ObservableList<String> observableList = FXCollections.observableArrayList();
    @FXML
    private ListView<String> myListView;
    @FXML
    private TextField textField, displayWord;
    @FXML
    private Button acceptButton;
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
    public void showExplainaton() {
        if (selectedItem != null) {
            found = commandline.dictionarySearcherBinary(selectedItem);
            if (found == -1) {
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
        if (selectedItem == null) {
            alert.setContentText("Hãy chọn từ để xoá");
            alert.showAndWait();
        } else if (selectedItem != null) {
            alert.setContentText("Bạn có chắc muốn xoá từ này?");
            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.get() == ButtonType.OK) {
//                observableList.remove(selectedItem);
                Dictionary.listWord.removeIf(o -> o.getWord_target().equals(selectedItem));
                observableList.remove(selectedItem);
                alert.setTitle("Successfully!");
                alert.setContentText("Thành công");
                alert.setGraphic(new ImageView("/checked_32px.png"));
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
        Alert alert = notice.alertConfirmation(title, "/question_mark_32px.png");
        alert.setContentText("Bạn chắc chắn chỉnh sửa?");
        Optional<ButtonType> editWord = alert.showAndWait();
        if (editWord.get() == ButtonType.OK) {
            explanationField.setEditable(false);
            alert.setContentText("Thành công");
            alert.setGraphic(new ImageView("/checked_32px.png"));
            acceptButton.setVisible(false);
        } else if (editWord.get() == ButtonType.CANCEL) {
            return;
        }
        alert.showAndWait();
    }


    public void edit() {
        if (selectedItem == null) {
            return;
        }
        Notice editNotice = new Notice();
        explanationField.setEditable(true);
        acceptButton.setVisible(true);
        acceptButton.setOnAction(event -> {
            setAlertToEdit(editNotice);
            management.editWord(found, explanationField.getText());
        });

        explanationField.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER) {
                setAlertToEdit(editNotice);
            } else {
                return;
            }
        });
    }
}


