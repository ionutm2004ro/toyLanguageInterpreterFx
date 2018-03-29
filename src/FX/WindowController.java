package FX;
import Controller.Controller;
import Model.Containers.MyIHeap;
import Model.Containers.Pair;
import Model.Containers.PrgState;
import Model.Expressions.*;
import Model.Statements.*;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class WindowController {
    ArrayList<IStmt> prgs;
    Controller ctr;

    @FXML
    Label prgCount;

    @FXML
    TableView<StringPair> heapTbl;
    @FXML
    TableColumn<StringPair,String> heapAddr;
    @FXML
    TableColumn<StringPair,String> heapVal;

    @FXML
    TableView<StringPair> fTbl;
    @FXML
    TableColumn<StringPair,String> fileId;
    @FXML
    TableColumn<StringPair,String> fileName;

    @FXML
    TableView<StringPair> curSymTbl;
    @FXML
    TableColumn<StringPair,String> curVar;
    @FXML
    TableColumn<StringPair,String> curVal;


    @FXML
    TableView<StringPair> procTbl;
    @FXML
    TableColumn<StringPair,String> procName;
    @FXML
    TableColumn<StringPair,String> procBody;


    @FXML
    ListView<Integer> outList = new ListView<Integer>();
    private ObservableList<Integer> observableList = FXCollections.observableArrayList();

    @FXML ListView<Integer> prgId = new ListView<Integer>();
    private ObservableList<Integer> prgIdList = FXCollections.observableArrayList();

    @FXML ListView<String> curExeStk = new ListView<String>();
    private ObservableList<String> exeList = FXCollections.observableArrayList();

    Integer selectedId=1;


    @FXML
    public void init(){
        ctr = new Controller();
        //make a list of programs
        prgs = new ArrayList<IStmt>();

        //stmtArray proof of concept
        StmtArray stmtArr = new StmtArray();
        stmtArr.add(new AssignStmt("v", new ConstExp(3)));
        stmtArr.add(new PrintStmt(new VarExp("v")));
        stmtArr.add(new newStmt("a",new ArithExp('+',new ConstExp(1),new VarExp("v"))));
        stmtArr.add(new wHStmt("a",new ConstExp(0)));
        stmtArr.add(new PrintStmt(new rHExp("a")));
        stmtArr.add(new openRFile("f","test.in"));
        stmtArr.add(new readFile(new VarExp("f"),"v"));
        stmtArr.add(new readFile(new VarExp("f"),"v"));
        stmtArr.add(new closeRFile(new VarExp("f")));
        stmtArr.add(new skip());
        prgs.add(stmtArr);

        StmtArray stmtArr2 = new StmtArray();
        stmtArr2.add(new ForkStmt(new CompStmt(new AssignStmt("v1",new ConstExp(1)),new ForkStmt(new AssignStmt("v1",new ConstExp(1))))));
        stmtArr2.add(new skip());stmtArr2.add(new skip());stmtArr2.add(new skip());
        prgs.add(stmtArr2);

        prgs.add(new PrintStmt(new ConstExp(3)));

        prgs.add(new PrintStmt(new ArithExp('/',new ConstExp(3),new ConstExp(0))));


        List<Exp> vwExpList = new ArrayList<>();
        vwExpList.add(new VarExp("v"));
        vwExpList.add(new VarExp("w"));
        List<Exp> arithVarExpList = new ArrayList<>();
        arithVarExpList.add(new ArithExp('*',new VarExp("v"),new ConstExp(10)));
        arithVarExpList.add(new VarExp("w"));
        prgs.add(new CompStmt(
                    new AssignStmt("v",new ConstExp(2)),
                    new CompStmt(
                            new AssignStmt("w",new ConstExp(5)),
                                    new CompStmt(
                                            new call(
                                                    "sum",
                                                    arithVarExpList),
                                            new CompStmt(
                                                    new PrintStmt(new VarExp("v")),
                                                    new ForkStmt(
                                                            new CompStmt(
                                                                    new call("product",vwExpList),
                                                                    new ForkStmt(new call("sum",vwExpList))
                                                            )
                                                    )
                                            )
                                    )
                            )
        ));

        prgs.add(
                new CompStmt(
                        new AssignStmt("v",new ConstExp(10)),
                        new CompStmt(
                                new ForkStmt(
                                        new CompStmt(
                                                new AssignStmt("v",new ArithExp('-',new VarExp("v"),new ConstExp(1))),
                                                new CompStmt(
                                                        new AssignStmt("v",new ArithExp('-',new VarExp("v"),new ConstExp(1))),
                                                        new PrintStmt(new VarExp("v"))
                                                        )
                                        )
                                ),
                                new CompStmt(
                                        new sleep(10),
                                        new PrintStmt(new ArithExp('*',new VarExp("v"),new ConstExp(10)))
                                )
                        )
                )
        );

        prgCount.setText(((Integer)prgs.size()).toString());
        //Heap table
        heapAddr.setCellValueFactory(new PropertyValueFactory<StringPair,String>("string1"));
        heapVal.setCellValueFactory(new PropertyValueFactory<StringPair,String>("string2"));
        //File Table
        fileId.setCellValueFactory(new PropertyValueFactory<StringPair,String>("string1"));
        fileName.setCellValueFactory(new PropertyValueFactory<StringPair,String>("string2"));
        //Symbol Table
        curVar.setCellValueFactory(new PropertyValueFactory<StringPair,String>("string1"));
        curVal.setCellValueFactory(new PropertyValueFactory<StringPair,String>("string2"));

        //Procedure Table
        procName.setCellValueFactory(new PropertyValueFactory<StringPair,String>("string1"));
        procBody.setCellValueFactory(new PropertyValueFactory<StringPair,String>("string2"));

    }


    @FXML
    public void selectProgram() throws Exception{
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProgramSelect.fxml"));
        Parent prgSelect = loader.load();

        stage.setTitle("Select a program");
        stage.setScene(new Scene(prgSelect, 700, 300));
        stage.sizeToScene();

        stage.setUserData(this.prgs);
        ProgramSelectController cont = loader.getController();
        cont.init();
        stage.showAndWait();

        //after selection has happened, populate containers
        if (stage.getUserData().getClass()!= prgs.getClass()){
            ctr.addPrg(prgs.get((int)stage.getUserData()));
            //System.out.println(prgs.get((int)stage.getUserData()));
            refreshContainers();
        }

    }

    @FXML
    public void selectId(){
        selectedId = prgId.getSelectionModel().getSelectedItem();
        if(selectedId != null){
            refreshContainers();
        }
    }

    @FXML
    public void oneStep() throws Exception{
        if(!ctr.isExeStackEmpty()){
            try{
                ctr.oneStep();
                refreshContainers();
                if(ctr.isExeStackEmpty()){
                    ctr.allStep();
                }
            }
            catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Look, an error!");
                alert.setHeaderText(e.getMessage());
                alert.setContentText(e.getLocalizedMessage());

                alert.showAndWait();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No step to execute");
            alert.setHeaderText("You have no program to execute");
            alert.setContentText("Mabye add another program?");

            alert.showAndWait();
        }
    }
    @FXML
    public void refreshContainers(){
        //System.out.println(ctr.getPrgList().get(0).toString());

        //heapTable
        ObservableList<StringPair> heapList = FXCollections.observableArrayList();
        for(Map.Entry<Integer,Integer> e : ctr.getPrgList().get(0).getHeap().getContent().entrySet()){
            heapList.add(new StringPair(e.getKey().toString(),e.getValue().toString()));
        }
        heapTbl.setItems(heapList);

        //FileTable
        ObservableList<StringPair> list = FXCollections.observableArrayList();
        for(Map.Entry<Integer,Pair<String,BufferedReader>> e : ctr.getPrgList().get(0).getFileTable().getContent().entrySet()){
            list.add(new StringPair(e.getKey().toString(),e.getValue().left()));
        }
        fTbl.setItems(list);

        //Output
        observableList = FXCollections.observableArrayList();
        for (Integer e : ctr.getPrgList().get(0).getOut().getContainer()){
            observableList.add(e);
        }
        outList.setItems(observableList);

        //IdList
        prgIdList = FXCollections.observableArrayList();
        for (PrgState e : ctr.getPrgList()){
            prgIdList.add(e.getId());
        }
        prgId.setItems(prgIdList);


        //SymbolTable
        ObservableList<StringPair> symbolList = FXCollections.observableArrayList();
        for(PrgState prg : ctr.getPrgList()){
            if(prg.getId()==selectedId){
                for(Map.Entry<String,Integer> e : prg.getSymTable().getContent().entrySet()){
                    symbolList.add(new StringPair(e.getKey(),e.getValue().toString()));
                }
            }
        }
        curSymTbl.setItems(symbolList);

        //Procedure Table
        ObservableList<StringPair> procList = FXCollections.observableArrayList();
        for(Map.Entry<String,Pair<List<String>,IStmt>> e : ctr.getPrgList().get(0).getProcTbl().getContent().entrySet()){
            procList.add(new StringPair(e.getKey().toString()+e.getValue().left(),e.getValue().right().toString()));
        }
        procTbl.setItems(procList);


        //Execution Stack
        exeList = FXCollections.observableArrayList();
        for(PrgState prg : ctr.getPrgList()){
            if(prg.getId()==selectedId){
                Stack<IStmt> tempStk = prg.getStk().getContent();
                while(!tempStk.isEmpty()){
                    IStmt e = tempStk.pop();
                    exeList.add(e.toString());
                }
            }
        }
        curExeStk.setItems(exeList);

    }

}
