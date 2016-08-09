package inventory;


import com.sun.jnlp.ApiDialog;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.Pair;
import javafx.util.StringConverter;
import javafx.util.converter.ShortStringConverter;
import org.junit.FixMethodOrder;
import sun.nio.ch.Net;
import sun.nio.ch.Util;


import java.awt.geom.Arc2D;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.net.URL;

import java.sql.Date;
import java.util.*;

import static inventory.Controller.dbHelper;

/**
 * Created by SSR on 6/29/2016.
 */
public class Inventory_Controller implements Initializable,EventHandler<ActionEvent>{

    @FXML
    private TabPane TPinventory;
    @FXML
    private BorderPane pane;
    @FXML
    private Tab Inventorytb,Importtb,Salestb,Historytb;
    @FXML
    private TextField SearchBox1,fileLocationTextField;
    @FXML
    private SplitPane splitPane;
    @FXML
    private Text importText;
    @FXML
    private ListView reviewList;
    @FXML
    private RadioButton radioSales,radioReturns,radioReceiving;
    @FXML
    private AnchorPane listReviewPane;
    @FXML
    private Button browse,addButton,confirmAndSave, removeImports, removeAllImports;
    @FXML
    private ChoiceBox filesAddedBox1;
    @FXML
    private Label lquantity,ldate,lsource,lpart, record_label;
    @FXML
    private TableView importTable;
    @FXML
    private HBox status;

    public static Label changesMade,currentUser, programInfo;
    public static HBox save;
    public static AutoCompleteTextField SearchBox;

    private final GetInventoryService InventoryService = new GetInventoryService();
    private final GetSalesService SalesService = new GetSalesService();
    private final GetReceivingService ReceivingService = new GetReceivingService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initThings();
        initSearch();
        initTabPane();

        currentUser.setMaxWidth(Double.MAX_VALUE);
        programInfo.setMaxWidth(Double.MAX_VALUE);

        currentUser.setAlignment(Pos.CENTER_RIGHT);
        status.getChildren().addAll(programInfo,currentUser);
        status.setSpacing(10);
        status.setHgrow(status.getChildren().get(0), Priority.ALWAYS);
        status.setHgrow(status.getChildren().get(1), Priority.ALWAYS);

        TPinventory.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                        System.out.print("tab changed");
                        if(newValue == Importtb){
                            System.out.print(" to import tab (initializing radio)");
                            initRadio();
                        }
                        //pane.setMinHeight();
                        //pane.setMinWidth(pane.getScene().getWidth());

                        //pane.getScene().getWindow().setHeight(pane.getScene().getHeight()+39);
                        //pane.getScene().getWindow().setWidth(pane.getScene().getWidth()+16);
                    }
                }
        );

        /*pane.getScene().setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    String filePath = null;
                    for (File file:db.getFiles()) {
                        filePath = file.getAbsolutePath();
                        System.out.println(filePath);
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }

        });*/

    }

    private void initThings(){
        assert TPinventory != null : "tabpane TPinventory doesn't exist";
        assert pane != null : "not here";
        assert Inventorytb != null : "";
        assert Importtb != null : "";
        assert Salestb != null : "";
        assert Historytb != null : "";
        assert SearchBox != null : "";
        assert splitPane != null : "";
        assert importText != null : "";
        assert browse != null : "";
        assert filesAddedBox1 != null:"";
        assert ldate != null:"";
        assert lquantity != null:"";
        assert lpart != null:"";
        assert lsource != null:"";
        assert record_label != null :"";
        assert reviewList != null:"";
        assert importTable != null:"";
        assert confirmAndSave != null :"";
        assert removeImports != null : "";
        assert removeAllImports != null : "";
        assert status != null:"";
        assert radioSales != null:"";
        assert radioReturns != null:"";
        assert radioReceiving != null:"";
        programInfo = new Label("program info");
        currentUser = new Label("Current User: " + dbHelper.getUSERNAME()+" ");
    }

    private void initSearch(){
        SearchBox = new AutoCompleteTextField();
        SearchBox.setPromptText("Search");
        SearchBox.setStyle("-fx-font: 15 Ariel");

        splitPane.getItems().add(0,SearchBox);
    }
    private void initTabPane(){
        Inventorytb.setText("Inventory");
        Inventorytb.setStyle("-fx-font: 15 Ariel;-fx-font-weight: bold");
        Importtb.setText("Import");
        Importtb.setStyle("-fx-font: 15 Ariel;-fx-font-weight: bold");
        Salestb.setText("Sales");
        Salestb.setStyle("-fx-font: 15 Ariel;-fx-font-weight: bold");
        Historytb.setText("History");
        Historytb.setStyle("-fx-font: 15 Ariel;-fx-font-weight: bold");

        getInventoryContent(0);
        getImportContent();

        TPinventory.setTabMinWidth(150);
        TPinventory.setTabMinHeight(30);
    }
    private void initRadio(){
        final ToggleGroup group = new ToggleGroup();
        radioSales.setToggleGroup(group);
        radioReturns.setToggleGroup(group);
        radioReceiving.setToggleGroup(group);


        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (newValue == radioSales && oldValue != radioSales)
                {
                    record_label.setText("Record of Sales");
                    System.out.print("\n radio initialized and selectd ");
                    getReviewListContent(SalesService);
                    setSalesImport(filesAddedBox1);
                }
                else if (newValue == radioReturns && oldValue != radioReturns)
                {
                    record_label.setText("Past Returns");

                }
                else if (newValue == radioReceiving && oldValue != radioReceiving)
                {
                    record_label.setText("Receiving");
                    getReviewListContent(ReceivingService);
                }
            }
        });

    }

    private MenuBar getMenuContent(){
        MenuBar menuBar = new MenuBar();
        menuBar.setMinWidth(Integer.MAX_VALUE);

        Menu File = new Menu("File");
        MenuItem Exit = new MenuItem("Exit");
        File.getItems().addAll(Exit);

        Menu Edit = new Menu("Edit");
        Menu View = new Menu("View");

        menuBar.getMenus().addAll(File,Edit,View);

        return menuBar;
    }

    private void getInventoryContent(int i){

        AnchorPane InventoryPane = new AnchorPane();
        InventoryTable table = new InventoryTable();
        table.setEditable(true);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Region veil = new Region();
        veil.setStyle("-fx-background-color: rgba(0,0,0,0.4)");
        ProgressIndicator p = new ProgressIndicator();
        //p.setPrefSize(150,150);
        p.setMaxSize(50,50);

        //adding columns to table
        table.getAllInventory();

        p.progressProperty().bind(InventoryService.progressProperty());
        veil.visibleProperty().bind(InventoryService.runningProperty());
        p.visibleProperty().bind(InventoryService.runningProperty());
        table.itemsProperty().bind(InventoryService.valueProperty());


        InventoryPane.getChildren().addAll(table,veil,p,getInventoryFilters());

        AnchorPane.setTopAnchor(InventoryPane.getChildren().get(0),100.0);
        AnchorPane.setRightAnchor(InventoryPane.getChildren().get(0),0.0);
        AnchorPane.setLeftAnchor(InventoryPane.getChildren().get(0),0.0);
        AnchorPane.setBottomAnchor(InventoryPane.getChildren().get(0),0.0);

        AnchorPane.setTopAnchor(InventoryPane.getChildren().get(3),0.0);
        AnchorPane.setRightAnchor(InventoryPane.getChildren().get(3),0.0);
        AnchorPane.setLeftAnchor(InventoryPane.getChildren().get(3),0.0);

        AnchorPane.setTopAnchor(InventoryPane.getChildren().get(2),200.0);
        AnchorPane.setRightAnchor(InventoryPane.getChildren().get(2),180.0);
        AnchorPane.setLeftAnchor(InventoryPane.getChildren().get(2),180.0);
        AnchorPane.setBottomAnchor(InventoryPane.getChildren().get(2),160.0);

        AnchorPane.setTopAnchor(InventoryPane.getChildren().get(1),100.0);
        AnchorPane.setRightAnchor(InventoryPane.getChildren().get(1),0.0);
        AnchorPane.setLeftAnchor(InventoryPane.getChildren().get(1),0.0);
        AnchorPane.setBottomAnchor(InventoryPane.getChildren().get(1),0.0);

        /*ObservableList<Items> items = FXCollections.observableArrayList();
        items.add(new Items("example",4,false,false,false,new Date(0),"somenotes"));

        table.setItems(items);*/
        Inventorytb.setContent(InventoryPane);
        InventoryService.start();
        programInfo.setText("Done.");
    }

    private GridPane getInventoryFilters(){
        final int NUMBER_OF_COLUMNS = 6;

        GridPane filters = new GridPane();
        filters.setGridLinesVisible(true);
        filters.setMaxHeight(100.0);
        filters.setPrefHeight(100.0);
        filters.setMinHeight(100.0);
        //added the padding
        filters.setPadding(new Insets(10,10,10,10));
        filters.setHgap(10.0);
        filters.setVgap(5.0);

        filters.setStyle("-fx-background-color: rgba(0,0,0,0.3)");

        /*Label rowsLabel = new Label("Rows: ");
        rowsLabel.setStyle("-fx-font: 16 Ariel");
        filters.setRowIndex(rowsLabel,0);
        filters.setColumnIndex(rowsLabel,0);

        ComboBox<Integer> numRows = new ComboBox<Integer>(FXCollections.observableArrayList(25,50,75,100));
        numRows.setPrefWidth(100.0);
        numRows.setStyle("-fx-font: 16 Ariel");
        filters.setRowIndex(numRows,0);
        filters.setColumnIndex(numRows,1);*/

        Label filterLabel = new Label("Filters:");
        filterLabel.setStyle("-fx-font: 16 Ariel");
        filters.setRowIndex(filterLabel,0);
        filters.setColumnIndex(filterLabel,0);

        ComboBox<String> filterCombobox = new ComboBox<String>(FXCollections.observableArrayList("All","Incomplete","Returns","Unverified","Verified","Net Saleable"));
        filterCombobox.setStyle("-fx-font: 16 Ariel");
        filters.setRowIndex(filterCombobox,0);
        filters.setColumnIndex(filterCombobox,1);

        Label dateRange = new Label("Date Range:");
        dateRange.setStyle("-fx-font: 16 Ariel");
        filters.setRowIndex(dateRange,0);
        filters.setColumnIndex(dateRange,2);

        DatePicker from = new DatePicker();
        from.setStyle("-fx-font: 16 Ariel");
        from.setPromptText("From");
        filters.setRowIndex(from,0);
        filters.setColumnIndex(from,3);

        DatePicker to = new DatePicker();
        to.setStyle("-fx-font: 16 Ariel");
        to.setPromptText("To");
        filters.setRowIndex(to,1);
        filters.setColumnIndex(to,3);

        Button newItemButton = new Button("Add New Items");
        filters.setRowIndex(newItemButton,1);
        filters.setColumnIndex(newItemButton,4);
        newItemButton.setOnAction(event -> addNewItem());

        //dont change anything after this.
        HBox emptySpace = new HBox();
        filters.setRowIndex(emptySpace,0);
        filters.setColumnIndex(emptySpace,6);

        Label visibleItems = new Label("# - # of #");
        visibleItems.setStyle("-fx-font: 16 Ariel");
        filters.setRowIndex(visibleItems,0);
        filters.setColumnIndex(visibleItems,7);

        Button pageForward = new Button("<-");
        filters.setRowIndex(pageForward,0);
        filters.setColumnIndex(pageForward,8);

        Button pageBack = new Button("->");
        filters.setRowIndex(pageBack,0);
        filters.setColumnIndex(pageBack,9);

        Button refresh = new Button("refresh");
        filters.setRowIndex(refresh,0);
        filters.setColumnIndex(refresh,10);
        refresh.setOnAction(event -> InventoryService.restart());

        changesMade = new Label("# Unsaved Changes!");
        filters.setRowIndex(changesMade,1);
        filters.setColumnIndex(changesMade,0);
        changesMade.setStyle("-fx-text-fill:#FF3200;-fx-font-weight: bold");
        changesMade.setVisible(false);

        Button savebtn = new Button("save");
        savebtn.setOnAction(event -> {
            dbHelper.commitChanges();
            dbHelper.removeEditor();
            save.setVisible(false);
            changesMade.setVisible(false);
        });

        Button discardbtn = new Button("discard changes");
        discardbtn.setOnAction(event -> {
            dbHelper.discardChanges();
            dbHelper.removeEditor();
            save.setVisible(false);
            changesMade.setVisible(false);
            InventoryService.restart();
        });


        save = new HBox(savebtn,discardbtn);
        filters.setRowIndex(save,1);
        filters.setColumnIndex(save,1);
        save.setHgrow(save.getChildren().get(0),Priority.ALWAYS);
        save.setHgrow(discardbtn,Priority.ALWAYS);
        save.setSpacing(15);
        save.setVisible(false);

        ColumnConstraints col6 = new ColumnConstraints();
        col6.setHgrow(Priority.ALWAYS);
        col6.setFillWidth(true);
        filters.getColumnConstraints().addAll(new ColumnConstraints(),
                                            new ColumnConstraints()
                                            ,new ColumnConstraints()
                                            ,new ColumnConstraints()
                                            ,new ColumnConstraints()
                                            ,new ColumnConstraints()
                                            ,col6);

        /*double[] arr = {5,21.7,5,21.7,5,21.7};
        for(int i=0;i<NUMBER_OF_COLUMNS;i++){
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(arr[i]);
         //   col.setHalignment();
            filters.getColumnConstraints().add(col);
        }*/

        //                              0           1         2             3       4        5             6        7          8         9         10         11       12
        filters.getChildren().addAll(filterLabel, filterCombobox, dateRange, from, to,newItemButton, visibleItems, pageForward, pageBack, refresh, changesMade, save);


        filters.setMargin(filters.getChildren().get(1),new Insets(0,30,0,0));
        filters.setMargin(filters.getChildren().get(3),new Insets(0,30,0,0));
        filters.setMargin(filters.getChildren().get(5),new Insets(0,0,0,0));
        return filters;
    }

    private void getImportContent(){
        StringConverter<Sales> sc = new StringConverter<Sales>() {
            @Override
            public String toString(Sales object) {
                return object.getFile().getName().toString().substring(0,object.getFile().getName().toString().lastIndexOf('.'));
            }

            @Override
            public Sales fromString(String string) {
                return null;
            }
        };

        filesAddedBox1.setConverter(sc);
        confirmAndSave.setDisable(true);
        confirmAndSave.setVisible(false);
        removeImports.setDisable(true);
        removeImports.setVisible(false);
        removeAllImports.setDisable(true);
        removeAllImports.setVisible(false);

        importText.setFont(Font.font("Ariel",16));
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Sales File");
        fileChooser.getExtensionFilters().addAll(
          new FileChooser.ExtensionFilter("XLS","*.xls")
        );
        List<File> FileList = new ArrayList<File>();

        browse.setOnAction(event -> {
            FileList.addAll(fileChooser.showOpenMultipleDialog(splitPane.getScene().getWindow()));
            if (FileList != null) {
                fileLocationTextField.setText("");
                for (File file : FileList) {
                    System.out.println("browsed for: "+ file.getAbsolutePath().toString());
                    fileLocationTextField.appendText("\""+file.getName().toString()+"\" , ");
                }
            }
           fileLocationTextField.requestFocus();
        });

        addButton.setOnAction(event -> {
            if(FileList != null && FileList.size()>0) {
                for (File file : FileList) {
                    Sales sales = new Sales(file);
                    filesAddedBox1.getItems().add(sales);
                }
                filesAddedBox1.setTooltip(new Tooltip("Select a file from list"));

                confirmAndSave.setVisible(true);
                confirmAndSave.setDisable(false);
                removeImports.setVisible(true);
                removeImports.setDisable(false);
                removeAllImports.setVisible(true);
                removeAllImports.setDisable(false);

            }else{
                return;
            }

            fileLocationTextField.clear();
            FileList.clear();

        });

       /* filesAddedBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(filesAddedBox.getItems() == null || filesAddedBox.getItems().size() <= 0){
                    return;
                }

                Sales entity = filesAddedBox.getItems().get(newValue.intValue());
                System.out.println("Viewing: " + entity.getFile().getName().toString());
                lsource.setText(entity.getSource());
                ldate.setText(entity.getDate());
                lpart.setText(entity.getPart()+"");
                lquantity.setText(entity.getAllQuantity()+"");

                importTable.setEditable(false);

                if(importTable.getItems() == null || importTable.getItems().size() <= 0) {
                    //new Table
                    importTable.getColumns().addAll(entity.setSalesTable());
                    importTable.setItems(entity.addTableData());
                }else{
                    importTable.getItems().removeAll();
                    importTable.setItems(entity.addTableData());
                }


            }
        });

        confirmAndSave.setOnAction(event -> {
            ObservableList<String> newInventory = FXCollections.observableArrayList();//sales list
            ObservableList<String> newInformation = FXCollections.observableArrayList();
            SortedSet<String> currentInventory = SearchBox.getEntries();//all inventory

            HashSet<String> set = new HashSet<String>();;
            HashMap<String,String> Set = new HashMap<String, String>();


            for(int i=0;i<filesAddedBox.getItems().size();i++){//Sales sheet
                for(int j=0; j < filesAddedBox.getItems().get(i).getItemCode().size();j++){//Strings for customlabel
                    if(!currentInventory.contains(filesAddedBox.getItems().get(i).getItemCode().get(j))){//part exists in inventory
                        //removing duplicates.
                        Set.put(filesAddedBox.getItems().get(i).getItemCode().get(j), filesAddedBox.getItems().get(i).getInformation().get(j));

                        //set.add(filesAddedBox.getItems().get(i).getItemCode().get(j));
                        //newInformation.add(filesAddedBox.getItems().get(i).getInformation().get(j));
                    }
                }
            }

            //newInventory.addAll(Set.keySet());

            //newInventory.addAll(set);
            if(Set.size() > 0){
            Set = confirmNewInventory(Set);
            }
            //set messagebox with list of new inventory(dont forget add all button)
            if(Set != null && Set.size() > 0){
                newInventory = FXCollections.observableArrayList(Set.keySet());
                //insert new ones into inventory with information
            }

            if(Set == null){
                return;
            }else{
                removeAllImports.fire();
                for(String s : newInventory){
                    System.out.println(s + " will be new inventory");
                }

                dbHelper.newAutoInventory(Set);

                //inserting sheets into database
                for (Sales sheet : filesAddedBox.getItems()) {
                    dbHelper.addSalesSheet(sheet);
                }
            }

        });

        removeImports.setOnAction(event -> {
            filesAddedBox.getItems().remove(filesAddedBox.getSelectionModel().getSelectedIndex());

            if(filesAddedBox.getItems().size() <= 0){
                confirmAndSave.setDisable(true);
                confirmAndSave.setVisible(false);
                removeImports.setDisable(true);
                removeImports.setVisible(false);
                removeAllImports.setDisable(true);
                removeAllImports.setVisible(false);
                filesAddedBox.getSelectionModel().clearSelection();
            }else{
                filesAddedBox.getSelectionModel().select(0);
            }

        });

        removeAllImports.setOnAction(event -> {
            //while(filesAddedBox.getItems().size() > 0){
            //    filesAddedBox.getItems().remove(filesAddedBox.getSelectionModel().getSelectedIndex());
            //}

            filesAddedBox.getItems().removeAll();

            confirmAndSave.setDisable(true);
            confirmAndSave.setVisible(false);
            removeImports.setDisable(true);
            removeImports.setVisible(false);
            removeAllImports.setDisable(true);
            removeAllImports.setVisible(false);

            filesAddedBox.getSelectionModel().clearSelection();
        });

        */
    }

    public void setNewInventoryImports(ChoiceBox<NewInventory> filesAddedBox){

    }

    public void setSalesImport(ChoiceBox<Sales> filesAddedBox){
        filesAddedBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(filesAddedBox.getItems() == null || filesAddedBox.getItems().size() <= 0){
                    return;
                }

                Sales entity = filesAddedBox.getItems().get(newValue.intValue());
                System.out.println("Viewing: " + entity.getFile().getName().toString());
                lsource.setText(entity.getSource());
                ldate.setText(entity.getDate());
                lpart.setText(entity.getPart()+"");
                lquantity.setText(entity.getAllQuantity()+"");

                importTable.setEditable(false);

                if(importTable.getItems() == null || importTable.getItems().size() <= 0) {
                    //new Table
                    importTable.getColumns().addAll(entity.setSalesTable());
                    importTable.setItems(entity.addTableData());
                }else{
                    importTable.getItems().removeAll();
                    importTable.setItems(entity.addTableData());
                }


            }
        });

        confirmAndSave.setOnAction(event -> {
            ObservableList<String> newInventory = FXCollections.observableArrayList();//sales list
            ObservableList<String> newInformation = FXCollections.observableArrayList();
            SortedSet<String> currentInventory = SearchBox.getEntries();//all inventory

            HashSet<String> set = new HashSet<String>();;
            HashMap<String,String> Set = new HashMap<String, String>();


            for(int i=0;i<filesAddedBox.getItems().size();i++){//Sales sheet
                for(int j=0; j < filesAddedBox.getItems().get(i).getItemCode().size();j++){//Strings for customlabel
                    if(!currentInventory.contains(filesAddedBox.getItems().get(i).getItemCode().get(j))){//part exists in inventory
                        //removing duplicates.
                        Set.put(filesAddedBox.getItems().get(i).getItemCode().get(j), filesAddedBox.getItems().get(i).getInformation().get(j));

                        //set.add(filesAddedBox.getItems().get(i).getItemCode().get(j));
                        //newInformation.add(filesAddedBox.getItems().get(i).getInformation().get(j));
                    }
                }
            }

            //newInventory.addAll(Set.keySet());

            //newInventory.addAll(set);
            if(Set.size() > 0){
                Set = confirmNewInventory(Set);
            }
            //set messagebox with list of new inventory(dont forget add all button)
            if(Set != null && Set.size() > 0){
                newInventory = FXCollections.observableArrayList(Set.keySet());
                //insert new ones into inventory with information
            }

            if(Set == null){
                return;
            }else{
                removeAllImports.fire();
                for(String s : newInventory){
                    System.out.println(s + " will be new inventory");
                }

                dbHelper.newAutoInventory(Set);

                //inserting sheets into database
                for (Sales sheet : filesAddedBox.getItems()) {
                    dbHelper.addSalesSheet(sheet);
                }
            }

        });

        removeImports.setOnAction(event -> {
            filesAddedBox.getItems().remove(filesAddedBox.getSelectionModel().getSelectedIndex());

            if(filesAddedBox.getItems().size() <= 0){
                confirmAndSave.setDisable(true);
                confirmAndSave.setVisible(false);
                removeImports.setDisable(true);
                removeImports.setVisible(false);
                removeAllImports.setDisable(true);
                removeAllImports.setVisible(false);
                filesAddedBox.getSelectionModel().clearSelection();
            }else{
                filesAddedBox.getSelectionModel().select(0);
            }

        });

        removeAllImports.setOnAction(event -> {
            //while(filesAddedBox.getItems().size() > 0){
            //    filesAddedBox.getItems().remove(filesAddedBox.getSelectionModel().getSelectedIndex());
            //}

            filesAddedBox.getItems().clear();

            confirmAndSave.setDisable(true);
            confirmAndSave.setVisible(false);
            removeImports.setDisable(true);
            removeImports.setVisible(false);
            removeAllImports.setDisable(true);
            removeAllImports.setVisible(false);

            filesAddedBox.getSelectionModel().clearSelection();
        });


    }


    public HashMap<String,String> confirmNewInventory(HashMap<String,String> newInventory){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Confirm new Inventory");
        dialog.setHeaderText("Select to remove");

        ButtonType AddAll = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(AddAll,ButtonType.CANCEL);

        BorderPane pane = new BorderPane();
        VBox vBox = new VBox();
        ListView<String> list = new ListView<>(FXCollections.observableArrayList(newInventory.keySet()));
        Button del = new Button("remove");
        del.setVisible(false);
        //list.setItems(newInventory);
        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                del.setDisable(false);
            }
        });

        vBox.getChildren().addAll(list);
        pane.setCenter(vBox);



        del.setOnAction(event -> {
            System.out.println("removed " + list.getSelectionModel().getSelectedIndex());
            list.getItems().remove(list.getSelectionModel().getSelectedIndex());
            list.getSelectionModel().clearSelection();
            del.setDisable(true);
        });

        pane.setBottom(del);



        dialog.getDialogPane().setContent(pane);

        Optional<ButtonType> result = dialog.showAndWait();

        if(result.get() == ButtonType.CANCEL){
            return null;
        }else{
            confirmAndSave.setVisible(false);
            confirmAndSave.setDisable(true);
            return newInventory;
        }
    }

    private<T> void getReviewListContent(Service service){

       Region veil = new Region();
        veil.setStyle("-fx-background-color: rgba(0,0,0,0.4)");
        ProgressIndicator p = new ProgressIndicator();
        //p.setPrefSize(150,150);
        p.setMaxSize(100,100);

        p.progressProperty().bind(service.progressProperty());
        veil.visibleProperty().bind(service.runningProperty());
        p.visibleProperty().bind(service.runningProperty());
        reviewList.itemsProperty().bind(service.valueProperty());

        listReviewPane.getChildren().add(2,veil);
        listReviewPane.getChildren().add(3,p);

        AnchorPane.setTopAnchor(listReviewPane.getChildren().get(2),0.0);
        AnchorPane.setRightAnchor(listReviewPane.getChildren().get(2),0.0);
        AnchorPane.setLeftAnchor(listReviewPane.getChildren().get(2),0.0);
        AnchorPane.setBottomAnchor(listReviewPane.getChildren().get(2),0.0);


        if(radioSales.isSelected()){
            reviewList.setCellFactory(new Callback<ListView<Sales>, ListCell<Sales>>() {
                                          @Override
                                          public ListCell<Sales> call(ListView<Sales> list) {
                                                return new Sales();
                                          }
                                      }
            );
        }
        else if(radioReceiving.isSelected())
        {
            reviewList = new ListView<NewInventory>();
            reviewList.setCellFactory(new Callback<ListView<NewInventory>, ListCell<NewInventory>>() {
                                          @Override
                                          public ListCell<NewInventory> call(ListView<NewInventory> list) {
                                              return new NewInventory();
                                          }
                                      }
            );

        }
        else
        {
            //return Returns();
        }


            reviewList.getSelectionModel().selectedItemProperty().addListener(
                    new ChangeListener<T>() {
                        public void changed(ObservableValue<? extends T> ov, T old_val, T new_val) {
                            System.out.println("old val: " + old_val + "new val: " + new_val);
                        }
                    });

        /*reviewList.setCellFactory(new Callback<ListView<Sales>, ListCell<Sales>>() {
            @Override
            public ListCell<Sales> call(ListView<Sales> param) {
                return new ListCell<Sales>(){
                    @Override
                    protected void updateItem(Sales item, boolean empty) {
                        super.updateItem(item, empty);
                        if(item != null){
                            VBox vBox = new VBox(new Text(item.getDate()));
                            //HBox hBox = new HBox(new Label("[Graphic]"), vBox);
                           // hBox.setSpacing(10);
                            setGraphic(vBox);
                        }
                    }
                };
            }
        });*/
        if(service.getState() == Worker.State.SUCCEEDED){
                service.restart();
        }else{
            service.start();
        }
    }

    public void addNewItem(){

        programInfo.setText("Adding new Item");

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add new Inventory");
        dialog.setHeaderText("Input Information");

        ButtonType AddAll = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(AddAll,ButtonType.CANCEL);

        BorderPane pane = new BorderPane();
        HBox hBox = new HBox();

        final TextField CustomLabel = new TextField();
        CustomLabel.setPromptText("Custom Label");
        CustomLabel.setMinWidth(50.0);
        final TextField NetSelable = new TextField();
        NetSelable.setPromptText("NetSaleable");
        NetSelable.setMinWidth(50.0);

        hBox.getChildren().addAll(CustomLabel,NetSelable);
        pane.getChildren().add(hBox);

        dialog.getDialogPane().setContent(pane);

        Optional<ButtonType> result = dialog.showAndWait();

        if(result.get() == ButtonType.CANCEL){
            dialog.close();
        }else if(result.get() == ButtonType.OK && CustomLabel.getText().equals("") || NetSelable.equals("")){
            dialog.showAndWait();
        }else {
            dbHelper.insertIntoInventory(new Items(CustomLabel.getText(),Integer.parseInt(NetSelable.getText()),0,0,0,"",0,"",new java.sql.Date(Calendar.getInstance().getTime().getTime())));
            InventoryService.restart();
        }
    }

    private void reloadSalesList(){
        if(reviewList.getItems() == null || reviewList.getItems().size() < 1){
            return;
        }

        reviewList.getItems().removeAll();

        Region veil = (Region) listReviewPane.getChildren().get(2);
        ProgressIndicator p = (ProgressIndicator) listReviewPane.getChildren().get(3);

        Task<ObservableList<Sales>> task = new GetSalesTask();
        p.progressProperty().bind(task.progressProperty());
        veil.visibleProperty().bind(task.runningProperty());
        p.visibleProperty().bind(task.runningProperty());
        reviewList.itemsProperty().bind(task.valueProperty());

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public void shutdown() {
        //future dialog box for exiting
    }

    @Override
    public void handle(ActionEvent event) {
        System.out.print("rg");
    }
}
