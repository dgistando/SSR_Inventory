package inventory;

import com.sun.javafx.scene.control.skin.ButtonBarSkin;
import com.sun.javafx.scene.control.skin.ContextMenuContent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by SSR on 6/29/2016.
 */
public class Inventory_Controller implements Initializable{

    @FXML
    TabPane TPinventory;
    @FXML
    AnchorPane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert TPinventory != null : "tabpane TPinventory doesn't exist";
        assert pane != null:"not here";

        Tab InventoryTable = new Tab("Inventory");
        InventoryTable.setContent(GetInventoryContent(new Random().nextInt(10)));


        Tab InventoryTable1 = new Tab("Inventory1");
        InventoryTable1.setContent(GetInventoryContent(new Random().nextInt(10)));


        TPinventory.getTabs().addAll(InventoryTable,InventoryTable1);
















        /*assert CMinventory != null : "tabpane CMinventory doesn't exist";
        assert CMTab != null : "the tab doesn't  exist";
        assert pane != null : "";
        assert button != null : "dd";
        assert label != null :" dd";

        /*TPinventory.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.PRIMARY){
                    System.out.println("you clicked this left");
                }
                //System.out.println("you clicked menu");
            }
        });
        */
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
        /*
        button.setOnAction(event -> {
            label.setText("feiwfjwiefj epijrpi4jpi2jpi \n oneunfeowfu \n enufnewfoewunfoeufoeu \ndepiwfjpijwpiwjepfij");
        });
        /*Tab tab = new Tab();
        HBox box = new HBox();
        ContextMenu cm = new ContextMenu();
        cm.getItems().add(new MenuItem("some text", new Circle(5.0,Color.RED)));
        box.getChildren().add(new ContextMenuContent(cm));
        box.getChildren().add(new Button("text on button"));
        tab.setContent(box);
        //makes the image on the tab(upsidedown triangle)
        //tab.setGraphic();
        TPinventory.getTabs().add(tab);*/
    }

    private Pane GetInventoryContent(int i){
        BorderPane border = new BorderPane();
        border.setPadding(new Insets(20,0,20,20));

        ListView<String> lvList = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList(
                "Hot dog", "Hamburger", "French fries",
                "Carrot sticks", "Chicken salad");
        lvList.setItems(items);
        lvList.setMaxHeight(Control.USE_PREF_SIZE);
        lvList.setPrefWidth(150.0);

        border.setLeft(lvList);
        border.setBottom(new Button(""+i));
  // Uses a tile pane for sizing
//        border.setBottom(createButtonBox());  // Uses an HBox, no sizing

        return border;
    }
}
