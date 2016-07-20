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


import java.net.URL;

import java.sql.Date;
import java.util.Random;
import java.util.ResourceBundle;

import static inventory.Controller.dbHelper;

/**
 * Created by SSR on 6/29/2016.
 */
public class Inventory_Controller implements Initializable,EventHandler<ActionEvent>{

    @FXML
    TabPane TPinventory;
    @FXML
    BorderPane pane;
    @FXML
    Tab Inventorytb,Importtb,Salestb,Historytb;
    @FXML
    TextField SearchBox1;
    @FXML
    SplitPane splitPane;

    AutoCompleteTextField SearchBox;

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

        ObservableList<String> testd = FXCollections.observableArrayList("ff","vfv","a","aab","aba","bba","aaaaaaaaab","zxcv","qwertyuiopasdfghjklzxcvbnm");
        SearchBox = new AutoCompleteTextField();
        SearchBox.getEntries().addAll(testd);
        SearchBox.setPromptText("Search");

        splitPane.getItems().add(0,SearchBox);


        Inventorytb.setText("Inventory");
        Importtb.setText("Import");
        Salestb.setText("Sales");
        Historytb.setText("History");

        getInventoryContent(0);
        //Inventorytb.setContent(getInventoryContent(new Random().nextInt(10)));
        //Importtb.setContent(getInventoryContent(new Random().nextInt(10)));
        //Salestb.setContent(getInventoryContent(new Random().nextInt(10)));
        //Historytb.setContent(getInventoryContent(new Random().nextInt(10)));

        TPinventory.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                        System.out.println("tab changed");
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


    private ComboBox initSearch(ComboBox search){
        //search.setMinWidth(Integer.MAX_VALUE);
        return search;
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
        p.setMaxSize(150,150);

        table.getAllInventory();

        Task<ObservableList<Items>> task = new GetInventoryTask();
        p.progressProperty().bind(task.progressProperty());
        veil.visibleProperty().bind(task.runningProperty());
        p.visibleProperty().bind(task.runningProperty());
        table.itemsProperty().bind(task.valueProperty());



        InventoryPane.getChildren().addAll(table,veil,p,getInventoryFilters());

        AnchorPane.setTopAnchor(InventoryPane.getChildren().get(0),50.0);
        AnchorPane.setRightAnchor(InventoryPane.getChildren().get(0),0.0);
        AnchorPane.setLeftAnchor(InventoryPane.getChildren().get(0),0.0);
        AnchorPane.setBottomAnchor(InventoryPane.getChildren().get(0),0.0);

        AnchorPane.setTopAnchor(InventoryPane.getChildren().get(3),0.0);
        AnchorPane.setRightAnchor(InventoryPane.getChildren().get(3),0.0);
        AnchorPane.setLeftAnchor(InventoryPane.getChildren().get(3),0.0);



        /*ObservableList<Items> items = FXCollections.observableArrayList();
        items.add(new Items("example",4,false,false,false,new Date(0),"somenotes"));

        table.setItems(items);*/
        Inventorytb.setContent(InventoryPane);
        new Thread(task).start();
    }

    private GridPane getInventoryFilters(){
        final int NUMBER_OF_COLUMNS = 6;

        GridPane filters = new GridPane();
        filters.setMaxHeight(50.0);
        filters.setPrefHeight(50.0);
        filters.setMinHeight(50.0);
        filters.setHgap(5.0);

        filters.setStyle("-fx-background-color: rgba(0,0,0,0.3)");

        Label rowsLabel = new Label("Rows: ");
        filters.setRowIndex(rowsLabel,0);
        filters.setColumnIndex(rowsLabel,0);

        ComboBox<Integer> numRows = new ComboBox<Integer>(FXCollections.observableArrayList(25,50,75,100));
        numRows.setPrefWidth(75.0);
        filters.setRowIndex(numRows,0);
        filters.setColumnIndex(numRows,1);


        Label filterLabel = new Label("Filters: ");
        filters.setRowIndex(filterLabel,0);
        filters.setColumnIndex(filterLabel,2);

        ComboBox<String> filterCombobox = new ComboBox<String>(FXCollections.observableArrayList("All","Incomplete","Returns","Unverified","Verified","Net Saleable"));
        filters.setRowIndex(filterCombobox,0);
        filters.setColumnIndex(filterCombobox,3);

        Label dateRange = new Label("Date Range: ");
        filters.setRowIndex(dateRange,0);
        filters.setColumnIndex(dateRange,4);

        DatePicker from = new DatePicker();
        from.setPromptText("From");
        filters.setRowIndex(from,0);
        filters.setColumnIndex(from,5);

        DatePicker to = new DatePicker();
        to.setPromptText("To");
        filters.setRowIndex(to,1);
        filters.setColumnIndex(to,5);


        //for(int i=0;i<NUMBER_OF_COLUMNS;i++){
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(100/NUMBER_OF_COLUMNS);
          //  col.setHalignment();
            filters.getColumnConstraints().add(col);
      //  }

        filters.getChildren().addAll(rowsLabel, numRows, filterLabel, filterCombobox, dateRange, from, to);


        filters.setMargin(filters.getChildren().get(1),new Insets(0,40,0,0));
        filters.setMargin(filters.getChildren().get(3),new Insets(0,40,0,0));
        return filters;
    }

    @Override
    public void handle(ActionEvent event) {
        System.out.print("rg");
    }
}
