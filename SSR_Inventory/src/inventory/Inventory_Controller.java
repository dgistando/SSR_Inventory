package inventory;

import com.sun.javafx.scene.control.skin.ButtonBarSkin;
import com.sun.javafx.scene.control.skin.ContextMenuContent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by SSR on 6/29/2016.
 */
public class Inventory_Controller implements Initializable{

    @FXML
    TabPane TPinventory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert TPinventory != null : "tabpane TPinventory doesn't exist";

        Tab tab = new Tab();
        HBox box = new HBox();
        ContextMenu cm = new ContextMenu();
        cm.getItems().add(new MenuItem("some text", new Circle(5.0,Color.RED)));
        box.getChildren().add(new ContextMenuContent(cm));
        box.getChildren().add(new Button("text on button"));
        tab.setContent(box);
        //makes the image on the tab(upsidedown triangle)
        //tab.setGraphic();
        TPinventory.getTabs().add(tab);
    }
}
