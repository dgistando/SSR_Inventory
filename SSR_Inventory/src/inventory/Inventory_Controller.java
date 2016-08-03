package inventory;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
import javafx.util.StringConverter;
import javafx.util.converter.ShortStringConverter;
import org.junit.FixMethodOrder;


import java.io.File;
import java.net.URL;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

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
    private RadioButton radioSales,radioNewInventory,radioReceiving;
    @FXML
    private AnchorPane listReviewPane;
    @FXML
    private Button browse,addButton;
    @FXML
    private ChoiceBox<Sales> filesAddedBox;

    public static AutoCompleteTextField SearchBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        assert filesAddedBox != null:"";

        initSearch();
        initTabPane();



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
        radioSales.setToggleGroup(group);;
        radioNewInventory.setToggleGroup(group);
        radioReceiving.setToggleGroup(group);

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(newValue == radioSales){
                    System.out.print("\n radio initialized and selectd");
                    getReviewListContent();
                }
            }
        });

        radioSales.setSelected(true);
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
        p.setMaxSize(100,100);

        table.getAllInventory();

        Task<ObservableList<Items>> task = new GetInventoryTask();
        p.progressProperty().bind(task.progressProperty());
        veil.visibleProperty().bind(task.runningProperty());
        p.visibleProperty().bind(task.runningProperty());
        table.itemsProperty().bind(task.valueProperty());


        InventoryPane.getChildren().addAll(table,veil,p,getInventoryFilters());

        AnchorPane.setTopAnchor(InventoryPane.getChildren().get(0),100.0);
        AnchorPane.setRightAnchor(InventoryPane.getChildren().get(0),0.0);
        AnchorPane.setLeftAnchor(InventoryPane.getChildren().get(0),0.0);
        AnchorPane.setBottomAnchor(InventoryPane.getChildren().get(0),0.0);

        AnchorPane.setTopAnchor(InventoryPane.getChildren().get(3),0.0);
        AnchorPane.setRightAnchor(InventoryPane.getChildren().get(3),0.0);
        AnchorPane.setLeftAnchor(InventoryPane.getChildren().get(3),0.0);

        AnchorPane.setTopAnchor(InventoryPane.getChildren().get(2),150.0);
        AnchorPane.setRightAnchor(InventoryPane.getChildren().get(2),150.0);
        AnchorPane.setLeftAnchor(InventoryPane.getChildren().get(2),150.0);
        AnchorPane.setBottomAnchor(InventoryPane.getChildren().get(2),150.0);

        /*ObservableList<Items> items = FXCollections.observableArrayList();
        items.add(new Items("example",4,false,false,false,new Date(0),"somenotes"));

        table.setItems(items);*/
        Inventorytb.setContent(InventoryPane);
        new Thread(task).start();
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

        Label changesMade = new Label("# Unsaved Changes!");
        filters.setRowIndex(changesMade,1);
        filters.setColumnIndex(changesMade,0);
        changesMade.setStyle("-fx-text-fill:#FF3200;-fx-font-weight: bold");
        changesMade.setVisible(false);

        Button save = new Button("save");
        filters.setRowIndex(save,1);
        filters.setColumnIndex(save,1);
        save.setPrefWidth(50);
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
        filesAddedBox.setConverter(sc);

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
                for (File file : FileList) {
                    System.out.println("browsed for: "+ file.getAbsolutePath().toString());
                    fileLocationTextField.appendText("\""+file.getName().toString()+"\" , ");
                }
            }
           fileLocationTextField.requestFocus();
        });

        addButton.setOnAction(event -> {
            if(FileList != null) {
                for (File file : FileList) {
                    Sales sales = new Sales(file);
                    filesAddedBox.getItems().add(sales);
                }
            }
            fileLocationTextField.clear();
            FileList.clear();
        });


    }

    private void getReviewListContent(){

       Region veil = new Region();
        veil.setStyle("-fx-background-color: rgba(0,0,0,0.4)");
        ProgressIndicator p = new ProgressIndicator();
        //p.setPrefSize(150,150);
        p.setMaxSize(100,100);

       Task<ObservableList<Sales>> task = new GetSalesTask();
        p.progressProperty().bind(task.progressProperty());
        veil.visibleProperty().bind(task.runningProperty());
        p.visibleProperty().bind(task.runningProperty());
        reviewList.itemsProperty().bind(task.valueProperty());



        listReviewPane.getChildren().addAll(veil,p);

        reviewList.setCellFactory(new Callback<ListView<Sales>, ListCell<Sales>>() {
                @Override
                public ListCell<Sales> call(ListView<Sales> list) {
                    return new Sales();
                }
            }
        );

        reviewList.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Sales>() {
                    public void changed(ObservableValue<? extends Sales> ov, Sales old_val, Sales new_val) {
                        System.out.println("old val: "+ old_val + "new val: "+ new_val);
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
        new Thread(task).start();
    }

    @Override
    public void handle(ActionEvent event) {
        System.out.print("rg");
    }
}
