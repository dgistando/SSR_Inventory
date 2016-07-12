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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

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
            Stage stage;

            TabPane TPinventory = new TabPane();

            Tab InventoryTable1 = new Tab("Inventory1");
            InventoryTable1.setContent(getInventoryContent(new Random().nextInt(10)));


            //TPinventory.getTabs().addAll(InventoryTable, InventoryTable1);


            stage = new Stage();
            stage.setTitle("SSR_Inventory");
            stage.setScene(new Scene(getMainContent(), 450, 450));
            stage.show();


            //hide this current window
            ((Node) (e.getSource())).getScene().getWindow().hide();
        });

    }

    private AnchorPane getMainContent(){
        AnchorPane Pane = new AnchorPane();
        TabPane TP = getTabContent();
        TP.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        //TP.setPadding(new Insets(50,0,0,0));//this adds an untouchable space under the padding.

        Pane.getChildren().addAll(getMenuContent(),TP);


        AnchorPane.setTopAnchor(Pane.getChildren().get(1),50.0);
        AnchorPane.setLeftAnchor(Pane.getChildren().get(1),0.0);
        return Pane;
    }

    private ComboBox searchBox(){

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
        ImportTab.setContent(getInventoryContent(new Random().nextInt(10)));

        Tab SalesTab = new Tab("Sales");
        SalesTab.setContent(getInventoryContent(new Random().nextInt(10)));

        Tab HistoryTab = new Tab("History");
        HistoryTab.setContent(getInventoryContent(new Random().nextInt(10)));


        tabPane.getTabs().addAll(InventoryTable,ImportTab,SalesTab,HistoryTab);
        return tabPane;
    }

    private Pane getInventoryContent(int i){
        AnchorPane InventoryPane = new AnchorPane();
        TableView<Items> Table = new TableView<Items>();
        InventoryTable table1 = new InventoryTable();

        Table = table1.getAllInventory(Table);
        Table.setMinWidth(Integer.MAX_VALUE);
        Table.setMinHeight(Integer.MAX_VALUE);

        InventoryPane.getChildren().addAll(Table);
        AnchorPane.setTopAnchor(InventoryPane.getChildren().get(0),50.0);
        return InventoryPane;
    }
}
