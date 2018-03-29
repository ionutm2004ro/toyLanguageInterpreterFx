package FX;

import Model.Statements.IStmt;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProgramSelectController {
    @FXML
    ListView<String> prgList = new ListView<String>();
    Stage stage;

    private ObservableList<String> observableList = FXCollections.observableArrayList();

    public void init(){
        stage = (Stage) prgList.getScene().getWindow();
        setPrgList((ArrayList<IStmt>) stage.getUserData());
    }

    public void setPrgList(ArrayList<IStmt> prgs){
        for (IStmt e : prgs){
            observableList.add(e.toString());
        }
        prgList.setItems(observableList);
    }

    public void selectedProgram(){
         int index = prgList.getSelectionModel().getSelectedIndex();
         stage.setUserData(index);
         stage.close();
    }
}
