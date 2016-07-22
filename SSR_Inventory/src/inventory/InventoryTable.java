package inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.xmlbeans.impl.xb.xmlconfig.NamespaceList;

import java.sql.Date;
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

    public void getAllInventory(){this.getColumns().addAll(getLabelColumn(),getQuantityColumn(),getIncompleteColumn(),getReturnsColumn(),getNotesColumn());}

    public void getReturnsInventory(){this.getColumns().addAll(getLabelColumn(),getQuantityColumn());}

    private TableColumn<Items,String> getLabelColumn(){
        TableColumn<Items,String> labelColumn = new TableColumn<Items,String>("Custom Label");
        labelColumn.setCellValueFactory(new PropertyValueFactory<Items,String>("customLabel"));
        labelColumn.setMinWidth(200.0);
        labelColumn.setMaxWidth(300.0);
        return labelColumn;
    }

    private TableColumn<Items,Integer> getQuantityColumn(){
        TableColumn<Items,Integer> quantityColumn = new TableColumn<Items,Integer>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Items,Integer>("quantity"));
        quantityColumn.setPrefWidth(60.0);
        quantityColumn.setMaxWidth(60.0);
        quantityColumn.setMinWidth(60.0);
        return quantityColumn;
    }

    private TableColumn<Items,Boolean> getIncompleteColumn(){
        TableColumn<Items,Boolean> incompleteColumn = new TableColumn<Items,Boolean>("Incomplete");
        incompleteColumn.setCellValueFactory(new PropertyValueFactory<Items,Boolean>("incomplete"));
        incompleteColumn.setMinWidth(incompleteColumn.getWidth());
        incompleteColumn.setMaxWidth(incompleteColumn.getWidth());
        return incompleteColumn;
    }

    private TableColumn<Items,Boolean> getReturnsColumn(){
        TableColumn<Items,Boolean> returnsColumn = new TableColumn<Items,Boolean>("Returns");
        returnsColumn.setCellValueFactory(new PropertyValueFactory<Items,Boolean>("returns"));
        returnsColumn.setMinWidth(returnsColumn.getWidth());
        returnsColumn.setMaxWidth(returnsColumn.getWidth());
        return returnsColumn;
    }

    private TableColumn<Items,String> getNotesColumn(){
        TableColumn<Items,String> notesColumn = new TableColumn<Items,String>("Notes");
        notesColumn.setCellValueFactory(new PropertyValueFactory<Items,String>("notes"));
        return getNotesColumn();
    }

    private TableColumn<Items,Date> getDateColumn(){
        TableColumn<Items,Date> dateColumn = new TableColumn<Items,Date>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<Items,Date>("Date"));
        dateColumn.setPrefWidth(120.0);
        dateColumn.setMaxWidth(120.0);
        dateColumn.setMinWidth(120.0);
        return dateColumn;
    }

}
