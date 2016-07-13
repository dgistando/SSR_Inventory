package inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

/**
 * This table class gives the possibility of adding dynamic instances
 * of a table to the system at all times. The class holds a list of item
 * that can be changed at the appropriate time to be displayed in the table.
 *
 *Created by SSR on 7/12/2016.
 */
public class InventoryTable {
    ObservableList<Items> items;
    DBHelper dbHelper;

    public InventoryTable() {
        items = FXCollections.observableArrayList(
                new Items("first thing"),
                new Items("second thing")
        );
        //dbHelper = new DBHelper();
        //items = BDHelper.getallitmes or something like this; // do something like this here.
    }

    public TableView<Items> getAllInventory(TableView<Items> tableView){

        //make database call to fill the items list
        TableColumn<Items,String> labelColumn = new TableColumn<Items,String>("CustomLabel");
        labelColumn.setCellValueFactory(new PropertyValueFactory<>("CustomLabel"));

        //tableView.setItems();
        tableView.getColumns().addAll(labelColumn);

        return tableView;
    }





}
