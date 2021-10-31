package DictionaryGui;

import DictionaryMain.DictionaryManagement;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    private ObservableList<String> observableList = FXCollections.observableArrayList();
    String selectedItem;
    DictionaryManagement management = new DictionaryManagement();
    @FXML
    private ListView<String> myListView;
    @FXML
    private TextField textField;
    @FXML
    private Button deleteButton, addButton, changeButton;
    private int indexOfSelectedWord;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        management.insertFromFile();
        myListView.setItems(FXCollections.observableList(Arrays.asList("No result found!")));
        myListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        textField.setOnKeyTyped(keyEvent -> {
            if (textField.getText().isEmpty()) {
                observableList.clear();
                myListView.setItems(FXCollections.observableList(Arrays.asList("No result found!")));
                myListView.setFixedCellSize(50);
            } else {
                searchButtonClicked();
            }
        });
    }

    public void searchButtonClicked() {
        observableList.clear();
        String search = textField.getText().trim().toLowerCase(Locale.ROOT);
        observableList = management.dictionaryLookup(search);
        myListView.setItems(observableList);
        myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                selectedItem = myListView.getSelectionModel().getSelectedItem();
            }
        });
    }


    public void speech() {
        try {
            // Set property as Kevin DictionaryMain.Dictionary
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
}


