package inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.apache.poi.ss.formula.functions.T;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;


/**
 * This class controls the login page for Sling Shot racing Inventory
 * The controller is linked to the login.fxml and uses the database backend to verify
 * accounts. There are no inputs or outputs to this page.
 *
 * @author  David Gistand of SSR
 *
 */
public class Controller implements Initializable{

    @FXML
    Button Btnlogin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert Btnlogin != null : "Button Btnlogin not found";


        Btnlogin.setOnAction(e-> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("view_inventory.fxml"));
                //Scene scene = new Scene(root,450,450);

                //TPinventory.getTabs().addAll(InventoryTable, InventoryTable1);

                Stage stage = new Stage();
                stage.setTitle("SSR_Inventory");
                stage.setScene(new Scene(getMainContent(), 450, 450));
                stage.show();


                //hide this current window
                ((Node) (e.getSource())).getScene().getWindow().hide();
            }catch (IOException err){
                err.printStackTrace();
            }
        });

    }

    private AnchorPane getMainContent(){
        AnchorPane Pane = new AnchorPane();
        ComboBox searchBox = initSearch();
        TabPane TP = getTabContent();
        TP.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        //TP.setPadding(new Insets(50,0,0,0));//this adds an untouchable space under the padding.


        Pane.getChildren().addAll(getMenuContent(),TP,searchBox);

        AnchorPane.setTopAnchor(Pane.getChildren().get(1),50.0);
        AnchorPane.setLeftAnchor(Pane.getChildren().get(1),0.0);
        AnchorPane.setTopAnchor(Pane.getChildren().get(2),27.0);
        AnchorPane.setLeftAnchor(Pane.getChildren().get(2),0.0);
        return Pane;
    }

    private ComboBox initSearch(){
        ComboBox search = new ComboBox();
        search.setMinWidth(Integer.MAX_VALUE);
        search.resize(100,100);

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
        TableView<String> Table = new TableView<String>();
        Table.setEditable(true);

        //InventoryTable table1 = new InventoryTable();

        //Table = table1.getAllInventory(Table);
        Table.setMinWidth(Integer.MAX_VALUE);
        Table.setMinHeight(Integer.MAX_VALUE);


        ObservableList items = FXCollections.observableArrayList(
                new Items("first thing"),
                new Items("second thing")
        );

        TableColumn<String,String> labelColumn = new TableColumn<String,String>("CustomLabel");
        labelColumn.setCellValueFactory(new PropertyValueFactory<>("CustomLabel"));

        //Table.setItems(items);
        Table.getColumns().add(labelColumn);
        Table.setItems(items);
        InventoryPane.getChildren().add(Table);
        AnchorPane.setTopAnchor(InventoryPane.getChildren().get(0),50.0);
        return InventoryPane;
    }

}
