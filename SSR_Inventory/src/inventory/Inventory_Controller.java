package inventory;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;


import java.net.URL;

import java.util.Random;
import java.util.ResourceBundle;

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

        splitPane.getItems().add(0,SearchBox);


        Inventorytb.setText("Inventory");
        Importtb.setText("Import");
        Salestb.setText("Sales");
        Historytb.setText("History");

        Inventorytb.setContent(getInventoryContent(new Random().nextInt(10)));
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

    private TabPane getTabContent(){
        TabPane tabPane = new TabPane();
        tabPane.setMinWidth(Integer.MAX_VALUE);

        Tab InventoryTable = new Tab("Inventory");
        InventoryTable.setContent(getInventoryContent(new Random().nextInt(10)));

        Tab ImportTab = new Tab("Import");
        //ImportTab.setContent(getInventoryContent(new Random().nextInt(10)));

        Tab SalesTab = new Tab("Sales");
        SalesTab.setContent(getInventoryContent(new Random().nextInt(10)));

        Tab HistoryTab = new Tab("History");
        HistoryTab.setContent(getInventoryContent(new Random().nextInt(10)));


        tabPane.getTabs().addAll(InventoryTable,ImportTab,SalesTab,HistoryTab);
        return tabPane;
    }

    private Pane getInventoryContent(int i){
        AnchorPane InventoryPane = new AnchorPane();
        TableView<String> Table = new InventoryTable();
        Table.setEditable(true);

        Table.setMinWidth(Integer.MAX_VALUE);
        Table.setMinHeight(Integer.MAX_VALUE);

        //TableColumn<String,String> labelColumn = new TableColumn<String,String>("CustomLabel");
        //labelColumn.setCellValueFactory(new PropertyValueFactory<>("CustomLabel"));

        //Table.setItems(items);
        //Table.getColumns().add(labelColumn);
        //Table.setItems(items);
        InventoryPane.getChildren().add(Table);
        AnchorPane.setTopAnchor(InventoryPane.getChildren().get(0),50.0);
        return InventoryPane;
    }

    @Override
    public void handle(ActionEvent event) {
        System.out.print("rg");
    }
}
