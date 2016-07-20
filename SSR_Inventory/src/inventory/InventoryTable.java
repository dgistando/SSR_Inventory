package inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.function.BooleanSupplier;

import static inventory.Controller.dbHelper;

/**
 * This table class gives the possibility of adding dynamic instances
 * of a table to the system at all times. The class holds a list of item
 * that can be changed at the appropriate time to be displayed in the table.
 *
 *Created by SSR on 7/12/2016.
 */
public class InventoryTable extends TableView{
    ObservableList<Items> items;
    DBHelper dbHelper;

    public InventoryTable() {
        /*items = FXCollections.observableArrayList(
                new Items("first thing"),
                new Items("second thing")
        );*/
        //dbHelper = new DBHelper();
        //items = BDHelper.getallitmes or something like this; // do something like this here.
    }

    public void getAllInventory(){
        //make database call to fill the items list
        TableColumn<Items,String> labelColumn = new TableColumn<Items,String>("Custom Label");
        labelColumn.setCellValueFactory(new PropertyValueFactory<Items,String>("customLabel"));
        labelColumn.setMinWidth(200.0);

        TableColumn<Items,Integer> quantityColumn = new TableColumn<Items,Integer>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Items,Integer>("quantity"));
        quantityColumn.setPrefWidth(60.0);
        quantityColumn.setMaxWidth(60.0);
        quantityColumn.setMinWidth(60.0);

        TableColumn<Items,Boolean> incompleteColumn = new TableColumn<Items,Boolean>("Incomplete");
        incompleteColumn.setCellValueFactory(new PropertyValueFactory<Items,Boolean>("incomplete"));
        incompleteColumn.setMinWidth(incompleteColumn.getWidth());
        incompleteColumn.setMaxWidth(incompleteColumn.getWidth());

        TableColumn<Items,Boolean> returnsColumn = new TableColumn<Items,Boolean>("Returns");
        returnsColumn.setCellValueFactory(new PropertyValueFactory<Items,Boolean>("returns"));
        returnsColumn.setMinWidth(returnsColumn.getWidth());
        returnsColumn.setMaxWidth(returnsColumn.getWidth());

        TableColumn<Items,String> notesColumn = new TableColumn<Items,String>("Notes");
        notesColumn.setCellValueFactory(new PropertyValueFactory<Items,String>("notes"));

        this.getColumns().addAll(labelColumn,quantityColumn,incompleteColumn,returnsColumn,notesColumn);

    }

}
